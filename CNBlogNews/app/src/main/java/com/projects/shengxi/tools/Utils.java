package com.projects.shengxi.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import com.projects.shengxi.bean.BlogDataBean;
import com.projects.shengxi.bean.BlogerDataBean;
import com.projects.shengxi.bean.MainBolgData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Utils {
	/**
	 * 检查网络状态
	 * 
	 * @param context
	 * @return
	 */
//	public boolean netState(Context context) {
//		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo info = cm.getActiveNetworkInfo();
//		Toast.makeText(context, "正在检查网络状态", Toast.LENGTH_SHORT).show();
//
//		if (info != null) {
//			return info.isAvailable();
//		} else {
//			return false;
//		}
//	}

	/**
	 * 解析新闻列表xml
	 * 
	 * @param in
	 */
//	public List<NewsDataBean> parseNewsListXML(InputStream in) {
//		List<NewsDataBean> newsList = null;
//		boolean flag = false;// 是否是正确的titile等
//		try {
//			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//			XmlPullParser parser = factory.newPullParser();
//			parser.setInput(in, null);// 本是utf-8
//			// parser.setInput(in,"utf-8");
//			int enType = parser.getEventType();
//			NewsDataBean news = null;
//			while (enType != XmlPullParser.END_DOCUMENT) {
//				String tagName = parser.getName();
//
//				switch (enType) {
//				case XmlPullParser.START_DOCUMENT:
//					newsList = new ArrayList<NewsDataBean>();
//					break;
//				case XmlPullParser.START_TAG:
//					if (tagName.equals("entry")) {
//						news = new NewsDataBean();
//						flag = true;
//					} else if (tagName.equals("id") && flag == true) {// 获取ID，为联系详细文章内容
//						news.setId(parser.nextText());
//					} else if (tagName.equals("title") && flag == true) {
//						news.setTitle(parser.nextText());
//					} else if (tagName.equals("summary")) {
//						news.setSummary(parser.nextText());
//					} else if (tagName.equals("published") && flag == true) {
//						news.setPublished(parser.nextText());
//					} else if (tagName.equals("views")) {
//						news.setViews(parser.nextText());
//					} else if (tagName.equals("comments")) {
//						news.setComments(parser.nextText());
//					} else if (tagName.equals("topicIcon")) {
//						news.setTopicIcon(parser.nextText());
//					} else if (tagName.equals("sourceName")) {
//						news.setSourceName(parser.nextText());
//					}
//					break;
//				case XmlPullParser.END_TAG:
//					if (tagName.equals("entry")) {
//						newsList.add(news);
//						news = null;
//					}
//					break;
//				}
//
//				enType = parser.next();
//			}
//			// list.addAll(refreshListView);
//		} catch (XmlPullParserException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return newsList;
//	}

	/**
	 * 解析搜索博主xml
	 * 
	 * @param in
	 * @return
	 */
	public List<BlogerDataBean> parseSearchListXML(InputStream in) {
		List<BlogerDataBean> blogerList = null;
		boolean flag = false;// 是否是正确的titile等
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(in, null);// 本是utf-8
			// parser.setInput(in,"utf-8");
			int enType = parser.getEventType();
			BlogerDataBean bloger = null;
			while (enType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName();

				switch (enType) {
				case XmlPullParser.START_DOCUMENT:
					blogerList = new ArrayList<BlogerDataBean>();
					break;
				case XmlPullParser.START_TAG:
					if (tagName.equals("entry")) {
						bloger = new BlogerDataBean();
						flag = true;
					} else if (tagName.equals("id") && flag == true) {// 获取ID，为联系详细文章内容
						bloger.setId(parser.nextText());
					} else if (tagName.equals("title") && flag == true) {// 获取博主名
						bloger.setTitle(parser.nextText());
					} else if (tagName.equals("blogapp")) {//
						bloger.setBlogapp(parser.nextText());
					} else if (tagName.equals("postcount")) {// 发表的总博文数
						bloger.setPostcount(parser.nextText());
					} else if (tagName.equals("avatar")) {// 头像地址，可能为空
						bloger.setAvatar(parser.nextText());
					} else if (tagName.equals("updated") && flag == true) {// 设置最后更新时间
						bloger.setUpdated(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if (tagName.equals("entry")) {
						blogerList.add(bloger);
						bloger = null;
					}
					break;
				}

				enType = parser.next();
			}
			// list.addAll(refreshListView);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blogerList;
	}

	/**
	 * 解析博客列表xml
	 * 
	 * @param in
	 * @return
	 */
	public List<BlogDataBean> parseBlogListXML(InputStream in) {
		List<BlogDataBean> blogList = null;
		boolean flag = false;// 是否是正确的titile等
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(in, null);// 本是utf-8
			// parser.setInput(in,"utf-8");
			int enType = parser.getEventType();
			BlogDataBean blog = null;
			while (enType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName();

				switch (enType) {
				case XmlPullParser.START_DOCUMENT:
					blogList = new ArrayList<BlogDataBean>();
					break;
				case XmlPullParser.START_TAG:
					if (tagName.equals("entry")) {
						blog = new BlogDataBean();
						flag = true;
					} else if (tagName.equals("id") && flag == true) {// 获取ID，为联系详细文章内容
						blog.setId(parser.nextText());
					} else if (tagName.equals("title") && flag == true) {// 获取博主名
						blog.setTitle(parser.nextText());
					} else if (tagName.equals("summary")) {//
						blog.setSummary(parser.nextText());
					} else if (tagName.equals("published")) {// 发表的总博文数
						blog.setPublished(parser.nextText());
					} else if (tagName.equals("name") && flag == true) {// 头像地址，可能为空
						blog.setName(parser.nextText());
					} else if (tagName.equals("uri") && flag == true) {// 设置最后更新时间
						blog.setUri(parser.nextText());
					} else if (tagName.equals("views")) {
						blog.setViews(parser.nextText());
					} else if (tagName.equals("comments")) {
						blog.setComments(parser.nextText());
					} else if (tagName.equals("link")) {// 标签内的数据-----------------------------
						blog.setLink(parser.getAttributeValue(1));
					}
					break;
				case XmlPullParser.END_TAG:
					if (tagName.equals("entry")) {
						blogList.add(blog);
						blog = null;
					}
					break;
				}

				enType = parser.next();
			}
			// list.addAll(refreshListView);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blogList;
	}

	public ArrayList<MainBolgData> getData(String apiUrl) {

		ArrayList<MainBolgData> newses = null;
		InputStream in = null;

		try {
			URL url = new URL(apiUrl);

			Log.e("UUUUU", url.toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(10 * 1000);
			conn.setRequestMethod("GET");
			//conn.connect();

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				newses = parseXml(in);
			} else {
				Log.e("time out", "连接超时");
			}
			conn.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return newses;
	}


	private ArrayList<MainBolgData> parseXml(InputStream in) {
		ArrayList<MainBolgData> newses = null;
		try {
			//建立Pull解析的工厂
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//建立Pull解析器
			XmlPullParser parser = factory.newPullParser();

			parser.setInput(in, "utf-8");

			int eventType = parser.getEventType();

			MainBolgData mainBolgData = null;
			try {
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String tagName = parser.getName();
					switch (eventType) {
						case XmlPullParser.START_DOCUMENT:
							newses = new ArrayList<>();
							mainBolgData=new MainBolgData();
							break;
						case XmlPullParser.START_TAG:
							switch (tagName) {
								case "id": {
									mainBolgData = new MainBolgData();
									mainBolgData.setId(parser.nextText());
									break;
								}
								case "title": {
									mainBolgData.setTitle(parser.nextText());
									Log.e("ZZZZZ",mainBolgData.getTitle().toString() );
									break;
								}
								case "summary": {
									mainBolgData.setSummary(parser.nextText());

									break;
								}
								case "published": {
									mainBolgData.setPublished(parser.nextText());
									break;
								}
								case "updated": {
									mainBolgData.setUpdated(parser.nextText());
									break;
								}
								case "link": {
									mainBolgData.setLink(parser.getAttributeValue(0));
									break;
								}

								case "name": {
									mainBolgData.setName(parser.nextText());
									break;
								}

							}

							break;
						case XmlPullParser.END_TAG:
							if (tagName.equals("entry")) {
								newses.add(mainBolgData);

							}
							break;
						default:
							break;
					}

					eventType = parser.next();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Log.e("NNNNNN",newses.toString() );
		return newses;
	}



	public  Boolean netState(Context context){
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getActiveNetworkInfo();
		if (info!=null){

			return  info.isAvailable();

		}
		return false;
	}

}
