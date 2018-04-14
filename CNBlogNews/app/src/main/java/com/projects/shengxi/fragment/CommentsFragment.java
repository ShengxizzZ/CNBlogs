package com.projects.shengxi.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.projects.shengxi.activity.NewsContentActivity;
import com.projects.shengxi.adapter.CommentsAdapter;
import com.projects.shengxi.bean.CommentsDataBean;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.DataBuiltUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShengXi on 2016/6/28.
 */
public class CommentsFragment extends Fragment{


    private DataBuiltUtils utils;
    private PullToRefreshListView refreshListView;
    private CommentsAdapter adapter;
    private List<CommentsDataBean> list  = new ArrayList<>();
    private List<CommentsDataBean> refreshList;
    public int count = 10;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                if (!list.isEmpty()){
                    adapter = new CommentsAdapter(getActivity(),list);
                    refreshListView.setAdapter(adapter);
                }

            }else if (msg.what == 2){
                adapter.notifyDataSetChanged();
            }
            refreshListView.onRefreshComplete();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coments_fragment, null);

        refreshListView = (PullToRefreshListView) view.findViewById(R.id.comments_pullListView);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (!list.isEmpty()){
                    gotoNet(PublicData.commentsUrl+NewsContentActivity.str+"/comments/1/"+count,true);
                }else{
                    gotoNet(PublicData.commentsUrl+NewsContentActivity.str+"/comments/1/"+count,false);
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                count+=10;
                if (!list.isEmpty()){
                    gotoNet(PublicData.commentsUrl+ NewsContentActivity.str+"/comments/1/"+count,true);
                }else{
                    gotoNet(PublicData.commentsUrl+ NewsContentActivity.str+"/comments/1/"+count,false);
                }

            }
        });
        utils = new DataBuiltUtils();
        if (utils.isNet(getActivity())){
            gotoNet(PublicData.commentsUrl+NewsContentActivity.str+"/comments/1/"+count,false);
        }else {
            Toast.makeText(getActivity(),"无网连接",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void gotoNet(final String commentsUrl, final boolean b) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData(commentsUrl);
                if(!b){
                    handler.sendEmptyMessage(1);
                }else {
                    handler.sendEmptyMessage(2);
                }

            }
        }).start();
    }
    public void getData(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                praseXML(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void praseXML(InputStream in){
        boolean isEnty = false;
        CommentsDataBean commentsDataBean = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in,"utf-8");
            int enType = parser.getEventType();
            list.clear();
            while(enType!=XmlPullParser.END_DOCUMENT){
                String TagName = parser.getName();
                switch (enType){
                    case XmlPullParser.START_DOCUMENT:

                        refreshList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        if(TagName.equals("entry")){
                            commentsDataBean = new CommentsDataBean();
                            isEnty = true;
                        }else  if(TagName.equals("id")){
                            if (isEnty == true){
                                commentsDataBean.setId(parser.nextText());
                            }
                        }else if (TagName.equals("name")){
                            commentsDataBean.setName(parser.nextText());
                        } else if (TagName.equals("published")){
                            commentsDataBean.setPublished(parser.nextText());
                        }else if (TagName.equals("content")){
                            commentsDataBean.setContent(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (TagName.equals("entry")){
                            refreshList.add(commentsDataBean);
                            commentsDataBean=null;
                            isEnty = false;
                        }
                        break;
                }

                enType = parser.next();
            }
            list.addAll(refreshList);
            //index = 0;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
