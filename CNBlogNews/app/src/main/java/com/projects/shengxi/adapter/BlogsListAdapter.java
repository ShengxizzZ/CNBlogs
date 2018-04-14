package com.projects.shengxi.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.shengxi.bean.BlogDataBean;
import com.projects.shengxi.cnblognews.R;

import java.util.List;

/**
 * 博客列表的listview
 * 
 * @author SL
 *
 */
public class BlogsListAdapter extends BaseAdapter {
	private List<BlogDataBean> dataBeanList = null;
	private LayoutInflater blogInflater = null;

	public BlogsListAdapter(Context context, List<BlogDataBean> dataBeanList) {
		if (dataBeanList != null) {
			this.dataBeanList = dataBeanList;
			this.blogInflater = LayoutInflater.from(context);
		}
	}

	@Override
	public int getCount() {
		if (dataBeanList != null)
			return dataBeanList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int pos) {
		return dataBeanList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder item = null;
		if (convertView == null) {

			item = new ViewHolder();
			convertView = blogInflater.inflate(R.layout.blog_item, null);
			item.title = (TextView) convertView.findViewById(R.id.blogs_title_tv);
			item.name = (TextView) convertView.findViewById(R.id.bloger_name_tv);
			item.published = (TextView) convertView.findViewById(R.id.blogs_published_tv);
			item.summary = (TextView) convertView.findViewById(R.id.blogs_summary_tv);
			item.views = (TextView) convertView.findViewById(R.id.blogs_views_tv);
			item.comments = (TextView) convertView.findViewById(R.id.blogs_comments_tv);

			convertView.setTag(item);
		} else {
			item = (ViewHolder) convertView.getTag();
		}

		item.title.setText(dataBeanList.get(pos).getTitle());// 获取标题
		item.summary.setText(dataBeanList.get(pos).getSummary());// 设置新闻摘要
		//item.name.setText(dataBeanList.get(pos).getName());//设置作者名
		String update[] = dataBeanList.get(pos).getPublished().split("['T','+']");
		item.published.setText(update[0] + " " + update[1]);//设置出版日期
		// item.updated.setText(dataBeanList.get(position).getUpdated());
		item.views.setText("浏览：" + dataBeanList.get(pos).getViews());//设置浏览数;
		item.comments.setText("评论：" + dataBeanList.get(pos).getComments());//设置博客数
		
		
		return convertView;
	}

	private class ViewHolder {
		private TextView title;// 博客标题
		private TextView summary;// 博客摘要
		private TextView published;// 出版日期
		private TextView views;// 浏览数
		private TextView comments;// 评论数
		private TextView name;// 作者名

	}

}
