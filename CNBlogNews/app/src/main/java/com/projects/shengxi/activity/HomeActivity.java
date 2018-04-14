package com.projects.shengxi.activity;

import java.util.Timer;
import java.util.TimerTask;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.projects.shengxi.adapter.LeftSlidingAdapter;
import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.fragment.BolgMainFragment;
import com.projects.shengxi.fragment.HotNewsFragment;
import com.projects.shengxi.fragment.SearchFragment;
import com.projects.shengxi.fragment.SettingFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主activity，进行侧滑选择操作
 * 
 * @author SL
 *
 */
public class HomeActivity extends FragmentActivity implements OnItemClickListener {


	private String pathAll="http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/5";
	private String path10d="http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/";
	private String path48h="http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/";
	private SlidingMenu menu;// 定义侧滑栏
	private String[] itemName = { "新闻", "热门新闻", "最新新闻", "推荐新闻", "博客", "所有博客", "48小时阅读排行", "10天内推荐排行", "收藏", "书签",
			"离线浏览", "工具", "搜索", "设置", "退出" };// 侧边栏文字数据
	private LeftSlidingAdapter adapter;// 侧滑栏adapter

	private ListView leftLv;// 侧滑栏listview
	private TextView titleTv;// 标题栏textview
	// 自定义Fragment
	private FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

	private FrameLayout fl;
//	private Blogs10DFragment blog10Dfragment;
//	private Blogs48HFragment blog48Hfragment;
//	private BlogsAllFragment blogAllFragment;
	private HotNewsFragment newsHotFragment;
//	private NewsLastestFragment newsLastestFragment;
//	private NewsRecommendFragment newsRecommendFragment;
//	private BookMarkFragment bookMarkFragment;
//	private OfflineReadFragment offlineReadFragment;
	private SearchFragment searchFragment;
	private SettingFragment settingFragment;

	private Fragment allBolog,dbolg,hbolg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.home);
		initView();
		initSlidingMenu();

	}

	/**
	 * 双击返回键操作
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (menu.isMenuShowing()) {
			menu.toggle();// 关变开，开变关方法
		} else {
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 初始化界面
	 */
	public void initView() {
		fl = (FrameLayout) findViewById(R.id.home_fl);// framelayout

		//itleTv = (TextView) findViewById(R.id.title_tv);// 标题的title

	}

	// 初始化slidingmenu
	public void initSlidingMenu() {
		DisplayMetrics dm = new DisplayMetrics(); // 获取屏幕宽度
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		menu = new SlidingMenu(getApplicationContext());
		// 把slidingmenu和activity绑定，设置activity为内容部分
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// slidingmenu加入菜单部分
		View view = LayoutInflater.from(this).inflate(R.layout.left_menu, null);
		leftLv = (ListView) view.findViewById(R.id.left_lv);// 加入到menu而不是activity
		menu.setMenu(view);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffset(width / 3);

		adapter = new LeftSlidingAdapter(itemName);
		leftLv.setAdapter(adapter);
		leftLv.setOnItemClickListener(this);

		// 初始化fragment
//		newsHotFragment = new NewsHotFragment();
//		newsLastestFragment = new NewsLastestFragment();
//		newsRecommendFragment = new NewsRecommendFragment();
//		blog10Dfragment = new Blogs10DFragment();
//		blog48Hfragment = new Blogs48HFragment();
//		blogAllFragment = new BlogsAllFragment();
//		bookMarkFragment = new BookMarkFragment();
//		offlineReadFragment = new OfflineReadFragment();

		settingFragment = new SettingFragment();
		searchFragment = new SearchFragment();
		newsHotFragment =  new HotNewsFragment(PublicData.hotNewsPath,"热门新闻");
		// 初始化加入commit，不能定义为全局只能有一次commit()
		ft = getSupportFragmentManager().beginTransaction();// 得到fragment事务管理对象
		ft.add(R.id.home_fl, newsHotFragment);
		ft.commit();
	}

	/**
	 * 滑动菜单选择点击事件
	 * 
	 * @param adapterView
	 * @param view
	 * @param i
	 * @param l
	 */
	// @Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		if (menu.isMenuShowing() && !(i == 0 || i == 4 || i == 8 || i == 11)) {// 除去点击标题栏
			menu.toggle();// 关变开，开变关方法
		}

		switch (i) {
		case 1:// 热门新闻
			newsHotFragment =  new HotNewsFragment(PublicData.hotNewsPath,"热门新闻");
			//titleTv.setText(itemName[1]);// 设置为第一个的名字
			ft = getSupportFragmentManager().beginTransaction();// 得重新获取，不然一个ft只能commit一次
			ft.replace(R.id.home_fl, newsHotFragment);
			ft.commit();
			break;
		case 2:// 最新新闻
			newsHotFragment =  new HotNewsFragment(PublicData.newsPath,"最新新闻");
			//titleTv.setText(itemName[2]);
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.home_fl, newsHotFragment);
			ft.commit();
			break;
		case 3:// 推荐新闻
			newsHotFragment =  new HotNewsFragment(PublicData.recommend,"推荐新闻");
			//titleTv.setText(itemName[3]);
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.home_fl, newsHotFragment);
			ft.commit();
			break;
		case 5:// 所有博客
			allBolog=new BolgMainFragment(pathAll);
			//titleTv.setText(itemName[5]);
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.home_fl, allBolog);
			ft.commit();
			break;
//		case 6:// 48小时阅读排行
//			//titleTv.setText(itemName[6]);
//			ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.home_fl, blog48Hfragment);
//			ft.commit();
//			break;
//		case 7:// 10天推荐排行
//			titleTv.setText(itemName[7]);
//			ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.home_fl, blog10Dfragment);
//			ft.commit();
//			break;
//		case 9:// 书签
//			titleTv.setText(itemName[9]);
//			ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.home_fl, bookMarkFragment);
//			ft.commit();
//			break;
//		case 10:// 离线浏览
//			titleTv.setText(itemName[10]);
//			ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.home_fl, offlineReadFragment);
//			ft.commit();
//			break;
		case 12:// 搜索
			//titleTv.setText(itemName[12]);
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.home_fl, searchFragment);
			ft.commit();
			break;
		case 13:// 设置
			//titleTv.setText(itemName[13]);
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.home_fl, settingFragment);
			ft.commit();
			break;
		case 14:// 退出
			new AlertDialog.Builder(this).setMessage("您真的要退出吗？").setPositiveButton("是的！朕要", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
					System.exit(0);
				}
			}).setNegativeButton("再逗留一会吧", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			}).show();
			break;
		default:
			break;
		}

	}

	/**
	 * 返回键功能
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 设置界面特殊处理
		if (menu.isMenuShowing()) {
			menu.toggle();// 关变开，开变关方法
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	private static Boolean isExit = false;// 退出标识

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
			System.exit(0);
		}
	}

}
