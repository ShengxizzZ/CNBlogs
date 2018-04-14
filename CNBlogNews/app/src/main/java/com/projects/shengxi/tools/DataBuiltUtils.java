package com.projects.shengxi.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.projects.shengxi.bean.CommentsDataBean;
import com.projects.shengxi.bean.NewsDataBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ShengXi on 2016/6/24.
 */
public class DataBuiltUtils {

    public static ArrayList<HashMap<String, String>> getMainMapList() {
        ArrayList<HashMap<String, String>> tempMapList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("menu", "菜单【" + (i + 1) + "】");
            tempMapList.add(tempMap);
        }
        return tempMapList;
    }

    public static ArrayList<HashMap<String, String>> getFirstMapList() {
        ArrayList<HashMap<String, String>> tempMapList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("menu", "FirstFragment菜单【" + (i + 1) + "】");
            tempMapList.add(tempMap);
        }
        return tempMapList;
    }

    public boolean isNet(Context context) {
        ConnectivityManager am = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = am.getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable();
        } else {
            return false;
        }
    }

    public List<NewsDataBean> getData(String path) {

        HttpURLConnection conn = null;
        InputStream in = null;
        List<NewsDataBean> list = new ArrayList<>();
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("连接网络", "失败");
            } else {
                in = conn.getInputStream();
                if (in != null) {
                    list = parserXml(in);
                    Log.e("list1", list.toString());
                    Iterator it = list.iterator();
                    for (NewsDataBean n : list) {

                        //
                        Log.d("title", n.getTitle());
                        Log.d("topic", n.getTopicIcon());
                        Log.d("link", n.getLink());
                        Log.d("views", n.getViews());
                        Log.d("topic", n.getUpdated());
                    }
                }
            }
            conn.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<NewsDataBean> parserXml(InputStream in) {

        List<NewsDataBean> list = new ArrayList<>();
        List<NewsDataBean> refreshLsit = null;
        NewsDataBean nb = null;
        boolean isEntry = false;
        try {

            //XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in, "utf-8");
            int eventType = parser.getEventType();
            list.clear();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //list = new ArrayList<>();
                        refreshLsit = new ArrayList<NewsDataBean>();
                        break;
                    case XmlPullParser.START_TAG:

                        if (tagName.equals("entry")) {

                            nb = new NewsDataBean();
                            isEntry = true;
                        } else if (tagName.equals("id")) {
                            if (isEntry == true) {
                                nb.setId(parser.nextText());
                                // System.out.println(parser.nextText());
                            }
                            //
                        } else if (tagName.equals("title")) {
                            //

                            if (isEntry == true) {
                                nb.setTitle(parser.nextText());
                                //System.out.println(parser.nextText());
                            }
                        } else if (tagName.equals("summary")) {
                            if (isEntry == true) {
                                //System.out.println(parser.nextText());
                            }
                            nb.setSummary(parser.nextText());
                        } else if (tagName.equals("link")) {
                            if (isEntry == true) {
                                nb.setLink(parser.getAttributeValue(1));
                            }
                            //
                        } else if (tagName.equals("views")) {
                            if (isEntry == true) {
                                //System.out.println(parser.nextText());
                                nb.setViews(parser.nextText());
                            }

                        } else if (tagName.equals("comments")) {
                            if (isEntry == true) {
                                //System.out.println(parser.nextText());
                            }
                            nb.setComments(parser.nextText());
                        } else if (tagName.equals("topicIcon")) {
                            if (isEntry == true) {
                                //System.out.println(parser.nextText());
                                nb.setTopicIcon(parser.nextText());
                            }

                        } else if (tagName.equals("sourceName")) {
                            if (isEntry == true) {
                                nb.setSourceName(parser.nextText());
                            }

                        } else if (tagName.equals("published")) {
                            if (isEntry == true) {
                                nb.setUpdated(parser.nextText());
                            }
                            //nb.setUpdated(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals("entry")) {
                            refreshLsit.add(nb);
                            isEntry = false;
                            nb = null;
                        }
                        break;

                }
                eventType = parser.next();
            }
            list.addAll(refreshLsit);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

//    public List<NewsDataBean> parserXML(InputStream in) {
//
//        List<NewsDataBean> list = null;
//        NewsDataBean newsDB = null;
//        boolean isEntry = false;
//
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(in, "utf-8");
//            int eventType = parser.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String tagName = parser.getName();
//
//                switch (eventType) {
//
//                    case XmlPullParser.START_DOCUMENT:
//
//                        list = new ArrayList<>();
//                        break;
//                    case XmlPullParser.START_TAG:
//                        if (tagName.equals("entry")) {
//                            newsDB = new NewsDataBean();
//                            isEntry = true;
//                        } else if (tagName.equals("id")) {
//                            if (isEntry == true) {
//                                newsDB.setId(parser.nextText());
//                                //Log.e("id:",parser.nextText());
//                            }
//                        } else if (tagName.equals("title")) {
//                            if (isEntry == true) {
//                                newsDB.setTitle(parser.nextText());
//                                //Log.e("title:",parser.nextText());
//                            }
//                        } else if (tagName.equals("summary")) {
//                            if (isEntry == true) {
//                                newsDB.setSummary(parser.nextText());
//                                //Log.e("sumary:",parser.nextText());
//                            }
//
//                        } else if (tagName.equals("updated")) {
//                            if (isEntry == true) {
//                                newsDB.setUpdated(parser.nextText());
//
//                            }
//                        } else if (tagName.equals("views")) {
//                            if (isEntry == true) {
//                                newsDB.setViews(parser.nextText());
//                            }
//                        } else if (tagName.equals("link")) {
//                            if (isEntry == true) {
//                                newsDB.setLink(parser.getAttributeValue(1));
//
//                            }
//                        } else if (tagName.equals("comments")) {
//                            if (isEntry == true) {
//                                newsDB.setComments(parser.nextText());
//
//                            }
//                        } else if (tagName.equals("sourceName")) {
//                            if (isEntry == true) {
//                                newsDB.setSourceName(parser.nextText());
//                            }
//                        } else if (tagName.equals("topicIcon")) {
//                            if (isEntry == true) {
//                                newsDB.setTopicIcon(parser.nextText());
//                            }
//                        }
//
//                        break;
//                    case XmlPullParser.END_TAG:
//
//                        if (tagName.equals("entry")) {
//                            list.add(newsDB);
//                            newsDB = null;
//                            isEntry = false;
//                        }
//
//                        break;
//                }
//                eventType = parser.next();
//            }
//
//
//            Iterator it = list.iterator();
//            for (NewsDataBean nd : list) {
//                Log.e("id", nd.getId());
//                Log.e("title", nd.getTitle());
//                System.out.println(nd.getSummary());
//            }
//            // return lis
//
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }

    ProgressDialog dialog;

    public void showProgressDialog(Context context) {

        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("加载中。。。");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void cacellProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


//    public List<CommentsDataBean> getDataComment(String str) {
//        HttpURLConnection conn = null;
//        InputStream in = null;
//        List<CommentsDataBean> list = new ArrayList<CommentsDataBean>();
//        try {
//            URL url = new URL(str);
//            conn = (HttpURLConnection) url.openConnection();
//            //conn.setReadTimeout(3000);
//            conn.setConnectTimeout(3000);
//            conn.setRequestMethod("GET");
//
//            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                Log.e("连接网络", "失败");
//            } else {
//                in = conn.getInputStream();
//                list = parserCommentsXml(in);
//                Log.e("list2", list.toString());
//                Iterator it = list.iterator();
//                for (CommentsDataBean c : list) {
//                    Log.e("comments", c.getName());
//                    Log.e("comments", c.getContent());
//                }
//                //        Iterator it = list.iterator();
////                    for (NewsDataBean n : list) {
////
////                        //
////                        Log.d("title", n.getTitle());
////                        Log.d("topic", n.getTopicIcon());
////                        Log.d("link", n.getLink());
////                        Log.d("views", n.getViews());
////                        Log.d("topic", n.getUpdated());
////                    }
//
//            }
//            conn.disconnect();
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//    private List<CommentsDataBean> parserCommentsXml(InputStream in) {
//
//        List<CommentsDataBean> list = null;
//        CommentsDataBean content = null;
//        boolean isEntry = false;
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(in, "utf-8");
//            int eventType = parser.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String tagName = parser.getName();
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
//                        list = new ArrayList<CommentsDataBean>();
//                        break;
//                    case XmlPullParser.START_TAG:
//                        if (tagName.equals("entry")) {
//                            content = new CommentsDataBean();
//                            isEntry = true;
//                        } else if (tagName.equals("id")) {
//                            if (isEntry == true) {
//                                content.setId(parser.nextText());
//                            }
//                        } else if (tagName.equals("published")) {
//                            content.setPublished(parser.nextText());
//                        } else if (tagName.equals("name")) {
//
//                            content.setName(parser.nextText());
//                        } else if (tagName.equals("content")) {
//                            content.setContent(parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        if (tagName.equals("NewsBody")) {
//                            list.add(content);
//                            content = null;
//                            isEntry = false;
//                        }
//                        break;
//                }
//                eventType = parser.next();
//            }
//            return list;
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
