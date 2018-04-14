package com.projects.shengxi.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.projects.shengxi.adapter.BlogsListAdapter;
import com.projects.shengxi.bean.BlogDataBean;
import com.projects.shengxi.bean.BlogURL;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.Utils;

public class BlogsListFragment extends Fragment {
	private View blogslistView;// 博客列表界面
	private Utils utils;

	private BlogsListAdapter blogslistAdapter = null;// 博客的数据适配器
	private List<BlogDataBean> blogList;// 博客list
	// URL前缀
	private String blogListURL = null;// 博客列表URL
	private int pageIndex = 1;// 请求页
	private int pageSize = 10;// 每页行数

	private ListView blogsListlv;// 博客列表的下拉刷新listview
	private EditText jumpPageEdit;
	private Button jumpNowBtn;
	private Button movePreviousPageBtn;
	private Button moveNextPageBtn;

	// 负责接收部分
	private String blogerNameFromSearchStr;// 接收搜索发送的博主名

	/**
	 * 控制线程
	 */
	Handler blogListHander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (blogList != null && blogList.size() > 0) {
					blogslistAdapter = new BlogsListAdapter(getActivity(), blogList);
					blogsListlv.setAdapter(blogslistAdapter);
				} else {
					Toast.makeText(getActivity(), "可能网太差，没数据", Toast.LENGTH_SHORT).show();
				}
			} else if (msg.what == 2) {
				blogslistAdapter.notifyDataSetChanged();
			}
		};
	};

	/**
	 * 进入fragment调用
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (blogslistView == null) {
			initView(inflater, container);
		}

		return blogslistView;
	}

	/**
	 * 初始化界面
	 */
	private void initView(LayoutInflater inflater, ViewGroup container) {
		utils = new Utils();
		blogslistView = inflater.inflate(R.layout.blogs_list_fragment, container, false);

		jumpPageEdit = (EditText) blogslistView.findViewById(R.id.jump_page_edit);// 跳转输入
		jumpNowBtn = (Button) blogslistView.findViewById(R.id.jum_now_btn);
		moveNextPageBtn = (Button) blogslistView.findViewById(R.id.move_next_page_btn);
		movePreviousPageBtn = (Button) blogslistView.findViewById(R.id.move_previous_page_btn);
		blogsListlv = (ListView) blogslistView.findViewById(R.id.blog_list_lv);

		// 初始得传入个blogerName；Intent接收**************

		if (connectNet() == false) {
			Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
			blogListHander.sendEmptyMessage(3);
		} else {
			getActivity().registerReceiver(br, new IntentFilter("sendBlogerName"));// 接收发送博主名的广播

			// Toast.makeText(getActivity(), "得到的博主名：" +
			// blogerNameFromSearchStr, Toast.LENGTH_SHORT).show();

			blogListURL = BlogURL.searchBloglist("wangkangluo1", PublicData.pageIndexInit, PublicData.pageSize);
			gotoNet(blogListURL);
		}

		// 点击条目事件

		blogsListlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				Toast.makeText(getActivity(), "点击第" + i + "个项目！" + "当前点击ID:" + blogList.get(i).getName(),
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private BroadcastReceiver br = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("sendBlogerName")) {
				// 通过intent对象获得额外传递过来的值
				Bundle bundle = intent.getBundleExtra("sendBlogerNameBundle");
				blogerNameFromSearchStr = (String) bundle.getSerializable("blogerName");

				if (blogerNameFromSearchStr != null) {
					// Toast.makeText(getContext(),
					// dataBean.result.realtime.wind.power, 1000).show();
					blogListHander.sendEmptyMessage(1);// 发送消息
				}
			}
		}
	};

	// 检查联网状态
	public boolean connectNet() {
		if (utils.netState(getActivity())) {
			return true;
		} else {
			return false;
		}
	}

	// 联网线程
	public void gotoNet(final String path) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 注册广播
				getData(path);
				blogListHander.sendEmptyMessage(1);
			}
		}).start();
	}

	// 获取网络数据
	public void getData(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6 * 1000);
			conn.setReadTimeout(6 * 1000);
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				blogList = utils.parseBlogListXML(in);
			} else if (conn.getResponseCode() == 408) {
				blogListHander.sendEmptyMessage(3);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 销毁
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(br);
	}

}
