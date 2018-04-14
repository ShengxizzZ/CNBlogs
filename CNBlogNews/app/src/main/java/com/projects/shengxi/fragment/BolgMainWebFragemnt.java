package com.projects.shengxi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.projects.shengxi.cnblognews.R;


/**
 * Created by qbqw7 on 2016/6/29.
 */
public class BolgMainWebFragemnt extends Fragment {
    private WebView wb;
    private String link;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_mainweb,null);
        init(view);
        return view;
    }

    private void init(View view) {
        wb=(WebView)view.findViewById(R.id.main_wv);
        wb.loadUrl("http://www.baidu.com/");
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
       wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
            }
        });

    }
}
