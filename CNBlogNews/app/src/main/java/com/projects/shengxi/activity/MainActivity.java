package com.projects.shengxi.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.projects.shengxi.adapter.MenuListViewAdapter;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.fragment.HotNewsFragment;
import com.projects.shengxi.fragment.NextFragment;
import com.projects.shengxi.fragment.ResentNewsFragment;
import com.projects.shengxi.tools.DataBuiltUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    private SlidingMenu sm;
    private DrawerLayout mDrawer_layout;  //抽屉式布局
    private LinearLayout mMenu_layout;
    private FragmentTransaction ft;
    Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

//        timer  = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        },3000);

//        DisplayMetrics ds = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(ds);
//        int w = ds.widthPixels;
//
//        sm = new SlidingMenu(getApplicationContext());
//        sm.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.slidingmenu,null);
//        sm.addView(view);
//        sm.setMode(SlidingMenu.LEFT);
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        sm.setBehindOffset(w/3);

        ft = getSupportFragmentManager().beginTransaction();
        mDrawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenu_layout = (LinearLayout) findViewById(R.id.menu_layout);
        ListView menu_listview = (ListView) mMenu_layout.findViewById(R.id.menu_listView);
        ArrayList<HashMap<String, String>> tempMapList = DataBuiltUtils.getMainMapList();
        menu_listview.setAdapter(new MenuListViewAdapter( getApplicationContext(), tempMapList));
        //菜单ListView设置监听事件
        menu_listview.setOnItemClickListener(new DrawerItemClickListener());



        fragment =  new HotNewsFragment(PublicData.hotNewsPath,"热门新闻");
        ft.replace(R.id.fragment_layout, fragment);
        ft.commit();

        //mDrawer_layout.closeDrawer(mMenu_layout);//点击后关闭mMenu_layout

    }

    long first = 0;
    @Override
    public void onBackPressed() {

        long second  =first;
        first = System.currentTimeMillis();
        if(second - first <=2000){
            finish();
        }else{
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
        }
    }


    public class DrawerItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            ft = getSupportFragmentManager().beginTransaction();
           // Fragment fragment = null;
            //根据item点击行号判断启用指定Fragment
            switch (position){
                case 0:
                    fragment = new HotNewsFragment(PublicData.hotNewsPath,"热门新闻");
                    break;
                case 1:
                    fragment = new HotNewsFragment(PublicData.newsPath,"最新新闻");
                    break;
                case 2:
                    fragment = new HotNewsFragment(PublicData.recommend,"推荐新闻");
                    break;
                default:
                    fragment = new HotNewsFragment(PublicData.hotNewsPath,"热门新闻");
                    break;
            }
            ft.replace(R.id.fragment_layout, fragment);
            ft.commit();
            mDrawer_layout.closeDrawer(mMenu_layout);//点击后关闭mMenu_layout
        }
    }
}
