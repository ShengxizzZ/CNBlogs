package com.projects.shengxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.shengxi.bean.CommentsDataBean;
import com.projects.shengxi.cnblognews.R;


import java.util.List;

/**
 * Created by ShengXi on 2016/6/28.
 */
public class CommentsAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<CommentsDataBean> list;

    public CommentsAdapter(Context context, List<CommentsDataBean> list) {

        this.inflater = LayoutInflater.from(context);
        if (!list.isEmpty()){
            this.list = list;

        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder item;
        if (convertView == null){
            convertView  = inflater.inflate(R.layout.comments_item,null);
            item  =new ViewHolder();

            item.name = (TextView) convertView.findViewById(R.id.comments_item_name);
            item.published = (TextView) convertView.findViewById(R.id.comments_item_published);
            item.content = (TextView) convertView.findViewById(R.id.comments_item_content);

            convertView.setTag(item);
        }else{
            item = (ViewHolder) convertView.getTag();
        }

        item.name.setText(list.get(position).getName());
        String[] temp = list.get(position).getPublished().split("['T','+']");
        item.published.setText(temp[0]+" "+temp[1]);
        item.content.setText(list.get(position).getContent());

        return convertView;
    }


    private class ViewHolder{

        private TextView name;
        private TextView published;
        private TextView content;

    }
}
