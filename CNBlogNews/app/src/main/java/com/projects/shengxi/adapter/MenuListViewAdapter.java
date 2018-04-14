package com.projects.shengxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.shengxi.cnblognews.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShengXi on 2016/6/24.
 */
public class MenuListViewAdapter extends BaseAdapter{

    private List<HashMap<String, String>> lists;
    private LayoutInflater mInflater;
    private Context mainContext;

    public MenuListViewAdapter(Context context,
                               List<HashMap<String, String>> dataList) {
        mInflater = LayoutInflater.from(context);
        mainContext = context;
        lists = dataList;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.main_listitem, null);
            holder.menu = (TextView) convertView.findViewById(R.id.menu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置背景色
        // if(position%2==1){
        // convertView.setBackgroundColor(Color.rgb(220, 220, 220));
        // }else {
        // convertView.setBackgroundColor(Color.rgb(255, 250, 250));
        // }
        if (lists.size() > 0) {
            final Map<String, String> map = lists.get(position);
            String number = (String) map.get("menu");
            holder.menu.setText(number);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView   menu;    //菜单名称
    }
}
