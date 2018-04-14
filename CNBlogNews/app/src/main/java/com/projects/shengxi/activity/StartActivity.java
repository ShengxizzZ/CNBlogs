package com.projects.shengxi.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.projects.shengxi.cnblognews.R;


/**
 * 开始的activity。动画跳转到HomeActivity
 * @author SL
 *
 */
public class StartActivity extends Activity {

	private SharedPreferences sp;
	private SharedPreferences.Editor ed;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Intent intent = new Intent(StartActivity.this, HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);

		sp = getSharedPreferences("isFirst",MODE_PRIVATE);
		boolean isFirst = sp.getBoolean("first",true);
		if (isFirst){
			ed = sp.edit();
			ed.putBoolean("first", false);
			// 提交
			ed.apply();
			handler.sendEmptyMessageDelayed(1, 1000);// 延迟发送message
		}else {
			Intent intent = new Intent(StartActivity.this, HomeActivity.class);
			startActivity(intent);
			finish();
			//handler.sendEmptyMessageDelayed(1, 1000);// 延迟发送message
		}


	}

}
