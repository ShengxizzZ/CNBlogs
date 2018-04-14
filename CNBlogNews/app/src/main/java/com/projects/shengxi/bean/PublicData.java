package com.projects.shengxi.bean;

/**
 * Created by ShengXi on 2016/6/24.
 */
public class PublicData {

    public static final String recommend =  "http://wcf.open.cnblogs.com/news/recommend/paged/1/";
    public static final String hotNewsPath = "http://wcf.open.cnblogs.com/news/hot/";
    public static final String newsPath = "http://wcf.open.cnblogs.com/news/recent/";
    public static final String contentUrl = "http://wcf.open.cnblogs.com/news/item/";
    public static final String commentsUrl = "http://wcf.open.cnblogs.com/news/item/";



    public static boolean isDownImg = true;// 标记浏览时是否显示图片

    public static boolean flowSavingYes = true;// 省流量模式是否打开
    public static boolean eyeProtectYes = true;// 护眼模式是否打开

    public static int hotNum = 10;// 获取热门新闻的条数
    public static int hotNumUnit = 1;// 热门新闻条数改变单位
    public static int hotNumMin = 1;// 热门新闻最少条数
    public static int hotNumMax = 50;// 热门新闻最多条数

    public static int pageIndexInit = 1;//页面初始未知
    public static int pageSize = 10;//页面显示条数
}
