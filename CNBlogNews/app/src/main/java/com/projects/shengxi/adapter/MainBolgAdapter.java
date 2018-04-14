package com.projects.shengxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.shengxi.bean.MainBolgData;
import com.projects.shengxi.cnblognews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qbqw7 on 2016/6/28.
 */
public class MainBolgAdapter extends BaseAdapter {
    List <MainBolgData> list=new ArrayList<>();
    Context context;
    public MainBolgAdapter(Context context){
        this.context=context;
    }
    //写一个内部类声明item自定义布局的控件
    public  final class ListItemView{
        TextView title;
        TextView updata;
        TextView summary;
        TextView autor;
    }

    public void setList(List<MainBolgData> list) {
        if( list == null) {
            return;
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
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
        ListItemView itemView;

        if(convertView==null) {//如果视图无内容时装填内容
            convertView= LayoutInflater.from(context).inflate(R.layout.mainbolg_item,null);
            itemView = new ListItemView();

            itemView.title = (TextView) convertView.findViewById(R.id.main_title_tv);
            itemView.updata = (TextView) convertView.findViewById(R.id.main_time_tv);
            itemView.summary = (TextView) convertView.findViewById(R.id.main_detail_tv);
            itemView.autor = (TextView) convertView.findViewById(R.id.main_name_tv);
            convertView.setTag(itemView);


        }else {
            itemView=(ListItemView)convertView.getTag();
        }
        itemView.title.setText(list.get(position).getTitle());
        itemView.updata.setText(list.get(position).getPublished());
        itemView.summary.setText(list.get(position).getSummary());
        itemView.autor.setText(list.get(position).getName());




        return convertView;

    }
}
