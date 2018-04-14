package com.projects.shengxi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.projects.shengxi.adapter.NewsListAdapter;
import com.projects.shengxi.bean.NewsDataBean;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.DataBuiltUtils;

import java.util.List;

/**
 * Created by ShengXi on 2016/6/27.
 */
public class ResentNewsFragment extends Fragment{

    private NewsDataBean dataBean;
    private NewsListAdapter adapter;
    private List<NewsDataBean> list;
    private PullToRefreshListView listView;
    private DataBuiltUtils utils;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){

                setData();

            }
        }


    };

    private void setData() {

        adapter = new NewsListAdapter(getActivity(),list);
        listView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resentnews_fragment,null);

        listView = (PullToRefreshListView) view.findViewById(R.id.resentNews_pullListView);
        utils = new DataBuiltUtils();
        if (utils.isNet(getActivity())){

            gotoThread();
        }else {
            Toast.makeText(getActivity(),"无网络连接",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void gotoThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {

               // list = utils.getData(PublicData.newsPath);
                handler.sendEmptyMessage(1);

            }
        }).start();

    }
}
