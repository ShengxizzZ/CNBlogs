package com.projects.shengxi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.shengxi.activity.NewsContentActivity;
import com.projects.shengxi.bean.CommentsDataBean;
import com.projects.shengxi.bean.NewsContentBean;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by ShengXi on 2016/6/28.
 */
public class NewsContentFragment extends Fragment {


    private TextView title;
    private DataBuiltUtils utils;
    private List<NewsContentBean> list;
    public static  List<CommentsDataBean> commentsDataBeen;
    private WebView webView;
    private String titleColor;
    private RelativeLayout re;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                if (list!=null){
                    webView.loadDataWithBaseURL(null, list.get(0).getContent(), "text/html", "utf-8", null);
                    title.setText(list.get(0).getTitle());
                }
            } else if (msg.what == 2) {
                Toast.makeText(getActivity(), "连接超时", Toast.LENGTH_SHORT).show();
            }
            utils.cacellProgressDialog();
        }
    };

    public NewsContentFragment(String titleColor) {
        this.titleColor = titleColor;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_content_fragment, null);

        utils = new DataBuiltUtils();
        title = (TextView) view.findViewById(R.id.content_title);
        webView = (WebView) view.findViewById(R.id.content_wv);
        // NewsContentActivity activity = (NewsContentActivity) getActivity();
        //activity.setOnDataChangedListener(this);
        re = (RelativeLayout) view.findViewById(R.id.news_content_re);
        re.setBackgroundResource(isColor(titleColor));


        utils.showProgressDialog(getActivity());
        if (utils.isNet(getActivity())) {
            gotoThread();
        }else {
            utils.cacellProgressDialog();
        }
        return view;
    }

    private void gotoThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               // InputStream in = getContentData(PublicData.contentUrl + NewsContentActivity.str);
                list = getContentData(PublicData.contentUrl + NewsContentActivity.str);
                //commentsDataBeen = utils.getDataComment(PublicData.commentsUrl);
                Log.e("content",list.toString());

                if (!list.isEmpty()){
                    handler.sendEmptyMessage(1);
                }else {
                    handler.sendEmptyMessage(2);
                }
            }
        }).start();
    }

//    @Override
//    public void setData(List<NewsContentBean> data) {
//        Log.e("ss",data.toString());
//        if (data!=null){
//            list = data;
//        }
//
//        handler.sendEmptyMessage(1);
//    }

    public List<NewsContentBean> paserXml(InputStream in) {

        List<NewsContentBean> list = null;
        NewsContentBean content = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in, "utf-8");
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equals("NewsBody")){
                            list = new ArrayList<NewsContentBean>();
                            content = new NewsContentBean();
                        }else if (tagName.equals("Title")) {
                            content.setTitle(parser.nextText());
                        } else if (tagName.equals("Content")) {
                            content.setContent(parser.nextText());
                        } else if (tagName.equals("SourceName")){
                            content.setSourceName(parser.nextText());
                        } else if (tagName.equals("SubmitDate")){
                            content.setSubmitDate(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals("NewsBody")) {
                            list.add(content);
                            //content = null;
                        }
                        break;
                }
                eventType = parser.next();

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }

    public List<NewsContentBean> getContentData(String s) {

        HttpURLConnection conn = null;
        InputStream in = null;
        List<NewsContentBean> list = new ArrayList<>();
        try {
            URL url = new URL(s);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                in = conn.getInputStream();
                if (in!=null){
                    list = paserXml(in);
                }

            }else {
                return null;
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private int isColor(String s) {
        switch (s) {

            case "热门新闻":
                return R.color.blue;
            case "最新新闻":
                return R.color.green;
            case "推荐新闻":
                return R.color.yellow;
            default:
                return R.color.blue;
        }
    }
}
