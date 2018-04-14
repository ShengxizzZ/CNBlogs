package com.projects.shengxi.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

/**
 * 检查网络是否连接
 * 
 * @author SL
 *
 */
public class CheckNetWork {
	public static boolean checkNetworkInfo(final Context context) {
		boolean isNetWork = false;
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

		// 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			isNetWork = true;
		else if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			isNetWork = true;
		else
			isNetWork = false;
		if (!isNetWork) {
			// 如果没网则弹出一个提示对话框，询问用户是否进行网络设置
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle("没网 啊，少年！ ");
			alert.setMessage("有网走遍天下，没网蜗居一隅");
			alert.setPositiveButton("返回", null);
			alert.setNegativeButton("设置网络", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));// 进入无线网络配置界面
				}
			});
			alert.create().show();
		}
		return isNetWork;
	}
}
