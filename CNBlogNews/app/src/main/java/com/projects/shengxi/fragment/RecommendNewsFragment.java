package com.projects.shengxi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.shengxi.adapter.NewsListAdapter;
import com.projects.shengxi.bean.NewsDataBean;
import com.projects.shengxi.cnblognews.R;

import java.util.List;

/**
 * Created by ShengXi on 2016/6/27.
 */
public class RecommendNewsFragment extends Fragment{


    private List<NewsDataBean> list;
    private NewsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  =inflater.inflate(R.layout.recommendnews_fragment,null);


        return view;
    }
}
