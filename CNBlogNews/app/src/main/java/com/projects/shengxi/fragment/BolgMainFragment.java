package com.projects.shengxi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;

import com.projects.shengxi.adapter.MainBolgAdapter;
import com.projects.shengxi.bean.MainBolgData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qbqw7 on 2016/6/24.
 */
public class BolgMainFragment extends Fragment{
    private FragmentManager fm;
    private FragmentTransaction ft;
    private BolgMainWebFragemnt b;
    private static final String TAG = BolgMainFragment.class.getSimpleName();
    private SwipeRefreshLayout  refreshLayout;
    private ListView showlist;
    private List<MainBolgData> list;
    private MainBolgAdapter mainbolgadapter;
    private Utils utils;
    private int count=5;
    private String path;
    private String url=path+count;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //收到子线程的消息进行UI数据装填
                    mainbolgadapter.setList(list);
                    break;
                case 2:
                    mainbolgadapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    break;


            }
      //      Log.i(TAG, String.valueOf(list.size()));
        }
    };
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_bolgmain,null);
        init(view);
        mainbolgadapter=new MainBolgAdapter(getContext());
        showlist.setAdapter(mainbolgadapter);
        Log.i(TAG, String.valueOf(null == list));
        goData(false);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               Toast.makeText(getContext(),"正在刷新数据。。。",Toast.LENGTH_SHORT).show();
               goData(true);
           }
       });
        //点击传一个link
        showlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                b=new BolgMainWebFragemnt();
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.show_web,b);
                ft.commit();
                String WebLink= list.get(position).getLink();
            }
        });


        return  view;
    }
   public BolgMainFragment(String path){

       this.path=path;

   }


    //初始化控件
    private void init(View view) {
        showlist=(ListView) view.findViewById(R.id.main_lv);
        refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.up_refresh);
        refreshLayout.setColorSchemeColors(R.color.one,R.color.two,R.color.three,R.color.four);

    }
   public  void goData(final boolean isupData){
       utils=new Utils();
       new Thread(new Runnable() {
           @Override
           public void run() {
               if (!isupData)
               //延时操作在子线程中启用
               {
                   list = utils.getData(path);
                   //发送一条消息让主线程更新UI
                   handler.sendEmptyMessage(1);

               }
               if (isupData) {
                   ArrayList<MainBolgData> res = null;
                   res = utils.getData(path);
                   // handler.sendEmptyMessageDelayed(2,3000);
                   if (res == null) {
                       //refreshLayout.setRefreshing(false);

                   } else {
                       handler.sendEmptyMessageDelayed(2, 3000);

                   }
               }
           }}).start();


   }
}


