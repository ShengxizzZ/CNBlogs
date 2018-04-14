package com.projects.shengxi.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.shengxi.bean.BlogerDataBean;
import com.projects.shengxi.cnblognews.R;

/**
 * 搜索fragment的Adapter
 * 
 * @author SL
 *
 */
public class SearchBloggerAdapter extends BaseAdapter {
	private List<BlogerDataBean> blogerList = null;
	private LayoutInflater searchInflater = null;
	private ViewHolder item = null;

	public SearchBloggerAdapter(Context context, List<BlogerDataBean> blogerList) {
		if (blogerList != null) {
			this.blogerList = blogerList;
			this.searchInflater = LayoutInflater.from(context);
		}

	}

	@Override
	public int getCount() {
		if (blogerList != null)
			return blogerList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int pos) {
		return blogerList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			item = new ViewHolder();
			convertView = searchInflater.inflate(R.layout.search_bloger_item, null);
			// item.blogerImg = (ImageView)
			// convertView.findViewById(R.id.bloger_img);
			item.blogerImg = (ImageView) convertView.findViewById(R.id.bloger_img);
			item.blogerNameTv = (TextView) convertView.findViewById(R.id.bloger_name_tv);
			item.blogerUpdateTv = (TextView) convertView.findViewById(R.id.bloger_update_tv);
			item.blogerCountTv = (TextView) convertView.findViewById(R.id.bloger_count_tv);
			convertView.setTag(item);
		} else {
			item = (ViewHolder) convertView.getTag();
		}

		// 后台异步加载头像图片
		new DownloadImageTask().execute(blogerList.get(position).getAvatar());

		// item.blogerImg
		item.blogerNameTv.setText(blogerList.get(position).getTitle());// 设置博主名
		String update[] = blogerList.get(position).getUpdated().split("['T','+']");
		item.blogerUpdateTv.setText("最后更新于:" + update[0] + " " + update[1]);// 设置博主最后更新时间
		item.blogerCountTv.setText("共" + blogerList.get(position).getPostcount() + "篇博客");// 设置博主所有博客数量

		return convertView;
	}

	// 获取指定路径的图片
	// 获取指定路径的图片
	public static Bitmap getImage(String urlpath) throws Exception {
		URL url = new URL(urlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		Bitmap bitmap = null;
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		return bitmap;
	}

	// 获取网络图片的方法
	private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
		@Override
		protected Drawable doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			// super.onPostExecute(result);
			item.blogerImg.setImageDrawable(result);
		}

	}

	private Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			// 可以在这里通过文件名来判断，是否本地有此图片
			drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
			Log.d("ImgDownload", e.getMessage());
		}
		if (drawable == null) {
			Log.d("ImgDownload", "null drawable");
		} else {
			Log.d("ImgDownload", "not null drawable");
		}

		return drawable;
	}

	private class ViewHolder {
		private ImageView blogerImg;// 博主头像
		private TextView blogerNameTv;// 博主名
		private TextView blogerUpdateTv;// 博主最后更新时间
		private TextView blogerCountTv;// 博主博客总数
	}

}
