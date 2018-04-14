package com.projects.shengxi.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.shengxi.cnblognews.R;

/**
 * Created by ShengXi on 2016/6/24.
 */
public class NextFragment extends android.support.v4.app.Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.layout_next, null);
        return layout;
    }
}
