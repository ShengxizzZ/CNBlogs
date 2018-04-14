package com.projects.shengxi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.shengxi.bean.PublicData;
import com.projects.shengxi.cnblognews.R;
import com.projects.shengxi.tools.CheckSwitchButton;

/**
 * 设置的fragment
 * 
 * @author SL
 *
 */
public class SettingFragment extends Fragment implements OnClickListener, OnCheckedChangeListener {


	private CheckSwitchButton flowSavingSwitch;// 自定义switch控件
	private CheckSwitchButton eyeProtectSwitch;
	private Button hotNumReduce;
	private Button hotNumAdd;
	private TextView hotNumTv;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				setSystemSetting();
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View settingView = inflater.inflate(R.layout.setting_fragment,null);
		flowSavingSwitch = (CheckSwitchButton) settingView.findViewById(R.id.flow_saving_switch);
		//eyeProtectSwitch = (CheckSwitchButton) settingView.findViewById(R.id.eye_protect_switch);
		hotNumReduce = (Button) settingView.findViewById(R.id.hot_num_reduce_btn);
		hotNumAdd = (Button) settingView.findViewById(R.id.hot_num_add_btn);
		hotNumTv = (TextView) settingView.findViewById(R.id.hotnews_num_tv);

		// 设置监听
		flowSavingSwitch.setOnCheckedChangeListener(this);
		//eyeProtectSwitch.setOnCheckedChangeListener(this);

		hotNumReduce.setOnClickListener(this);
		hotNumAdd.setOnClickListener(this);
		if (settingView == null) {
			intView(inflater, container);
		}
		return settingView;
	}

	public void intView(LayoutInflater inflater, ViewGroup container) {

	}

	// 设置保存系统信息
	public void setSystemSetting() {

	}

	// 按钮事件监听
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.hot_num_reduce_btn:
			if (PublicData.hotNum > PublicData.hotNumMin)
				PublicData.hotNum -= PublicData.hotNumUnit;// 减去一个单位数量
			else
				Toast.makeText(getContext(), "必须大于1条", Toast.LENGTH_SHORT).show();
			break;
		case R.id.hot_num_add_btn:
			if (PublicData.hotNum < PublicData.hotNumMax)
				PublicData.hotNum += PublicData.hotNumUnit;
			else
				Toast.makeText(getContext(), "必须小于50条", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		// 设置
		hotNumTv.setText(PublicData.hotNum + "");
		// 存储hotNum
	}

	// checkbox监听事件
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.flow_saving_switch:// 省流量模式选择
			if (isChecked) {// 选中的操作
				Toast.makeText(getContext(), "已选择省流量模式", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getContext(), "已取消省流量模式", Toast.LENGTH_SHORT).show();
			}
			// 存储状态

			break;
//		case R.id.eye_protect_switch:// 护眼模式选择
//			if (isChecked) {
//				// 传递消息修改fragment的背景颜色
//				Toast.makeText(getContext(), "已选择护眼模式", Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(getContext(), "已取消护眼模式", Toast.LENGTH_SHORT).show();
//
//			}
//			break;

		// 存储状态

		default:
			break;
		}
	}

}
