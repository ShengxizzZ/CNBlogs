package com.projects.shengxi.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.shengxi.adapter.SearchBloggerAdapter;
import com.projects.shengxi.bean.BlogURL;
import com.projects.shengxi.bean.BlogerDataBean;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.Utils;

/**
 * 搜索的fragment
 * 
 * @author SL
 *
 */
public class SearchFragment extends Fragment implements OnItemClickListener, OnClickListener {
	private View searchView;// 搜索页面
	private Utils utils;

	private EditText searchText;// 搜索编辑框
	private Button searchGo;// 搜索按钮
	private Button searchClear;// 清除按钮
	private ListView resultListView;// 博主搜索结果显示
	private ProgressBar searchProgress;// 搜索进度条
	private TextView searchNull;// 未搜索到内容

	private List<BlogerDataBean> blogerList;

	private SearchBloggerAdapter searchAdapter = null;// 搜索的数据适配器

	private String blogerNameStr;// 博主名，搜索博客列表用

	// 搜索网址前缀
	private String searchURL = null;
	private String searchBy = null;// 搜索的内容

	// 搜索线程控制
	Handler searchHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			{
				if (msg.what == 1) {
					if (blogerList != null && blogerList.size() > 0) {
						searchAdapter = new SearchBloggerAdapter(getActivity(), blogerList);
						resultListView.setAdapter(searchAdapter);
						Toast.makeText(getActivity(), blogerList.get(2).getAvatar(), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "不知为何，没搜索结果", Toast.LENGTH_SHORT).show();
					}
					searchProgress.setVisibility(View.INVISIBLE);

					// 获取搜索结果
				}
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (searchView == null) {// 第一次进来初始化
			initView(inflater, container);
		}
		return searchView;
	}

	public boolean connectNet() {
		if (utils.netState(getActivity())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 初始化SearchFragment
	 * 
	 * @param inflater
	 * @param container
	 */
	private void initView(LayoutInflater inflater, ViewGroup container) {
		utils = new Utils();
		searchView = inflater.inflate(R.layout.search_fragment, container, false);

		searchText = (EditText) searchView.findViewById(R.id.search_text);// 搜索输入
		searchGo = (Button) searchView.findViewById(R.id.search_go);// 搜索按钮
		searchClear = (Button) searchView.findViewById(R.id.search_clear);// 清除按钮
		resultListView = (ListView) searchView.findViewById(R.id.resultList);// 结果列表
		searchProgress = (ProgressBar) searchView.findViewById(R.id.blog_progress);// 搜索过程
		searchNull = (TextView) searchView.findViewById(R.id.search_null);// 没有结果提示

		searchGo.setOnClickListener(this);
		searchClear.setOnClickListener(this);

		resultListView.setOnItemClickListener(this);

	}

	public void gotoNet(final String path) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getData(path);
				searchHandler.sendEmptyMessage(1);
			}
		}).start();
	}

	public void getData(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6 * 1000);
			conn.setReadTimeout(6 * 1000);
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				// hotNewsList = praseXML(in);
				blogerList = utils.parseSearchListXML(in);
			} else if (conn.getResponseCode() == 408) {
				searchHandler.sendEmptyMessage(3);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 点击操作
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.search_go:
			searchNull.setVisibility(View.INVISIBLE);
			searchBy = searchText.getText().toString();

			if (searchBy == null || searchBy == "" || searchBy.length() <= 0) {
				searchText.setError(Html.fromHtml("<font color=#808183>" + "输入内容不合规范" + "</font>"));
			} else {
				searchProgress.setVisibility(View.VISIBLE);
				gotoNet(BlogURL.searchBloger(searchBy));

				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
				}

				// }
			}
			break;

		case R.id.search_clear:
			searchAdapter = new SearchBloggerAdapter(getActivity(), null);
			resultListView.setAdapter(searchAdapter);
			searchNull.setVisibility(View.VISIBLE);
			searchProgress.setVisibility(View.INVISIBLE);
			searchNull.setText("没有可显示内容");
			searchText.setText("");

			break;
		default:
			break;

		}
	}

	// listview条目点击
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long arg3) {
		Toast.makeText(getActivity(), "点击第" + pos + "个项目！" + "当前点击Title:" + blogerList.get(pos).getBlogapp(),
				Toast.LENGTH_SHORT).show();
		// 发送广播
		blogerNameStr = blogerList.get(pos).getBlogapp();

		Intent intent = new Intent();
		intent.setAction("sendBlogerName");
		Bundle b = new Bundle();
		b.putSerializable("blogerName", blogerNameStr);
		intent.putExtra("sendBlogerNameBundle", b);
		getActivity().sendBroadcast(intent);

		Toast.makeText(getActivity(), "传递前的博主名" + blogerNameStr, Toast.LENGTH_SHORT).show();

		// 跳转到blogerListFragment
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.home_fl, new BlogsListFragment());
		ft.commit();
	}

}
