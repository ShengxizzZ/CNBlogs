package com.projects.shengxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.shengxi.bean.NewsDataBean;
import com.projects.shengxi.cnblognews.R;

import java.util.List;

/**
 * Created by ShengXi on 2016/6/26.
 */
public class NewsListAdapter extends BaseAdapter {

    private List<NewsDataBean> dataBeanList;
    private LayoutInflater mInflater;

    public NewsListAdapter(Context context, List<NewsDataBean> dataBeanList) {

        if (dataBeanList != null) {
            this.dataBeanList = dataBeanList;
            this.mInflater = LayoutInflater.from(context);
        }

    }

    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder item;
        if (convertView == null) {

            item = new ViewHolder();
            convertView = mInflater.inflate(R.layout.blog_list_item, null);
            item.title = (TextView) convertView.findViewById(R.id.blog_list_item_title);
            item.sourceName = (TextView) convertView.findViewById(R.id.blog_list_item_sourcename);
            item.summary = (TextView) convertView.findViewById(R.id.blog_list_item_summary);
            item.updated = (TextView) convertView.findViewById(R.id.blog_list_item_updated);
            item.views = (TextView) convertView.findViewById(R.id.blog_list_item_views);
            item.comments = (TextView) convertView.findViewById(R.id.blog_list_item_comments);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }


        item.title.setText(dataBeanList.get(position).getTitle());
        item.sourceName.setText(dataBeanList.get(position).getSourceName());
        item.summary.setText(dataBeanList.get(position).getSummary());
        String update[] =  dataBeanList.get(position).getUpdated().split("['T','+']");
        item.updated.setText(update[0]+" "+update[1]);
        item.views.setText("浏览："+dataBeanList.get(position).getViews());
        item.comments.setText("评论："+dataBeanList.get(position).getComments());
        return convertView;
    }


    private class ViewHolder {
        private TextView title;
        private TextView sourceName;
        private TextView summary;
        private TextView updated;
        private TextView views;
        private TextView comments;

    }
}
