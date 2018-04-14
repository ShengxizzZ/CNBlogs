package com.projects.shengxi.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.shengxi.bean.CommentsDataBean;
import com.projects.shengxi.bean.NewsContentBean;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.fragment.CommentsFragment;
import com.projects.shengxi.fragment.NewsContentFragment;
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
 * Created by ShengXi on 2016/6/27.
 */
public class NewsContentActivity extends AppCompatActivity{

    private ImageView back,comment;
    private TextView tv;
    private RelativeLayout re;
    private WebView wv;
    private DataBuiltUtils utils;
    public static String str;
    public static String comments;
    //private List<NewsContentBean> list;
   // private List<CommentsDataBean> commentsList;
    List<Fragment> listFragment;
    private ViewPager vp;
   // private DataBuiltUtils utils;
    public Fragment newsContentFragment,commentsFragmet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_content);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       initView();
    }

    /**
     * 初始化详细新闻的activity
     */
    private void initView() {

        Intent in = getIntent();
        String[] strTemp = in.getStringArrayExtra("data");
        str = strTemp[0];
        comments = strTemp[1];


        vp = (ViewPager) findViewById(R.id.content_vp);
        re = (RelativeLayout) findViewById(R.id.Re);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comment = (ImageView) findViewById(R.id.comments);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
                comment.setVisibility(View.INVISIBLE);
            }
        });


        re.setBackgroundResource(isColor(strTemp[2]));
        utils = new DataBuiltUtils();
        tv = (TextView) findViewById(R.id.tv);

        newsContentFragment = new NewsContentFragment(strTemp[2]);
        commentsFragmet = new CommentsFragment();

        listFragment = new ArrayList<Fragment>();
        //commentsList = new ArrayList<>();
        listFragment.add(newsContentFragment);
        listFragment.add(commentsFragmet);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", String.valueOf(position));
                if (position == 1) {

                    comment.setVisibility(View.INVISIBLE);
                } else {

                    comment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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



    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }


        @Override
        public int getCount() {
            return listFragment.size();
        }
    }


    OnDataChangeListener listener;
    public interface OnDataChangeListener{

        void setData(List<NewsContentBean> data);
       // void setCommentData(List<CommentsDataBean> data);
    }

    public void setOnDataChangedListener(OnDataChangeListener l){

        if (l!=null){
            this.listener = l;
        }

    }
}
