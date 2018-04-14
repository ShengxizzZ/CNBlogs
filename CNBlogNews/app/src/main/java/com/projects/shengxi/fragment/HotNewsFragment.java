package com.projects.shengxi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.projects.shengxi.activity.NewsContentActivity;
import com.projects.shengxi.adapter.NewsListAdapter;
import com.projects.shengxi.bean.NewsDataBean;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.DataBuiltUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShengXi on 2016/6/24.
 */
public class HotNewsFragment extends android.support.v4.app.Fragment {

    private TextView hotNewsTile;
    private RelativeLayout re;
    private ListView listView;
    private List<NewsDataBean> dataBeanList;
    private DataBuiltUtils utils;
    private NewsListAdapter newsListAdapter;
    private PullToRefreshListView pullToRefreshListView;
    private String sitePath = PublicData.hotNewsPath;
    private String title;
    private int count = 10;
    private boolean isRefresh = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                newsListAdapter = new NewsListAdapter(getActivity(), dataBeanList);
                pullToRefreshListView.setAdapter(newsListAdapter);
                //  Toast.makeText(getActivity(), "获取数据成功", Toast.LENGTH_SHORT).show();


            } else if (msg.what == 2) {
                //Toast.makeText(getActivity(), "连接超时", Toast.LENGTH_SHORT).show();
                newsListAdapter = new NewsListAdapter(getActivity(), dataBeanList);
                pullToRefreshListView.setAdapter(newsListAdapter);
                Log.e("huooooo", "sssssssssssssssssss");

            } else if (msg.what == 3) {
                Toast.makeText(getActivity(), "连接超时", Toast.LENGTH_SHORT).show();
            }
            pullToRefreshListView.onRefreshComplete();
            utils.cacellProgressDialog();
            // setData();
        }


    };

    public HotNewsFragment(String path, String title) {
        this.sitePath = path;
        this.title = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_first, null);


        // Translucent status bar
//        getActivity().getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        utils = new DataBuiltUtils();
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.hot_news_pullReListView);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        hotNewsTile = (TextView) view.findViewById(R.id.hot_news_title);
        re = (RelativeLayout) view.findViewById(R.id.main_re);
        re.setBackgroundResource(bgColor(title));
        //hotNewsTile.setBackgroundResource(bgColor(title));
        hotNewsTile.setText(title);

        dataBeanList = new ArrayList<NewsDataBean>();


        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(getActivity(), NewsContentActivity.class);
                String[] temp = {dataBeanList.get(position - 1).getId(), dataBeanList.get(position - 1).getComments(), title};
                in.putExtra("data", temp);
                startActivity(in);
            }
        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // gotoGetNetSources();
                //utils.showProgressDialog(getActivity());
                if (!dataBeanList.isEmpty()) {
                    isRefresh = false;
                } else {
                    isRefresh = true;
                }

                if (utils.isNet(getContext())) {

                    gotoGetNetSources();
                } else {
                    //utils.cacellProgressDialog();
                    Toast.makeText(getActivity(), "无网络连接", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (!dataBeanList.isEmpty()) {
                    isRefresh = false;
                } else {
                    isRefresh = true;
                }
                //isRefresh = true;
                count += 10;
                if (utils.isNet(getContext())) {

                    gotoGetNetSources();
                } else {
                    Toast.makeText(getActivity(), "无网络连接", Toast.LENGTH_SHORT).show();
                }

            }
        });

        isRefresh = false;
        utils.showProgressDialog(getActivity());
        if (utils.isNet(getContext())) {

            gotoGetNetSources();
        } else {
            utils.cacellProgressDialog();
            Toast.makeText(getActivity(), "无网络连接", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private int bgColor(String title) {
        switch (title) {

            case "热门新闻":
                return R.mipmap.bg_cloudy;
            case "最新新闻":
                return R.mipmap.bg_sunny;
            case "推荐新闻":
                return R.mipmap.bg_sandy;
            default:
                return R.mipmap.bg_cloudy;
        }
    }

    private void gotoGetNetSources() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                // InputStream in =null;
                dataBeanList = utils.getData(sitePath + count);
                if (!dataBeanList.isEmpty()) {
                    if (!isRefresh) {
                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(2);
                    }

                } else {
                    handler.sendEmptyMessage(3);
                }
            }
        }).start();

    }
}
