package com.projects.shengxi.adapter;

import com.projects.shengxi.cnblognews.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 侧滑栏的Adapter
 * @author SL
 *
 */
public class LeftSlidingAdapter extends BaseAdapter {
	String[] itemName;
	// 数组加载对应图片
	int[] images = { 0, R.mipmap.heart_32, R.mipmap.rss_32, R.mipmap.globe_32, 0, R.mipmap.star_32,
			R.mipmap.chart_bar_down_32, R.mipmap.chart_area_32, 0, R.mipmap.book_bookmark_32,
			R.mipmap.document_32, 0, R.mipmap.magnifier_32, R.mipmap.gear_32, R.mipmap.exit_32 };

	public LeftSlidingAdapter(String[] itemName) {
		this.itemName = itemName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemName.length;
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return itemName[pos];
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		if (pos == 0 || pos == 4 || pos == 8 || pos == 11) {
			view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_item_title, null);
			TextView titleTv = (TextView) view.findViewById(R.id.sliding_item_title);
			titleTv.setText(itemName[pos]);
		} else {
			view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_item_class, null);
			ImageView itemImg = (ImageView) view.findViewById(R.id.sliding_item_img);
			TextView itemTv = (TextView) view.findViewById(R.id.sliding_item_tv);
			itemTv.setText(itemName[pos]);
			if (images[pos] != 0) {
				itemImg.setImageResource(images[pos]);
			}
		}
		return view;
	}

}
