package com.projects.shengxi.bean;

/**
 * 解析的网址
 * 
 * @author SL
 *
 */
public class BlogURL {
	// 新闻部分
	/**
	 * 获取热门新闻列表
	 * 
	 * @param itemcount
	 *            新闻条数
	 * @return
	 */
	public static String hotNews(int itemcount) {
		String url = "http://wcf.open.cnblogs.com/news/hot/" + itemcount;
		return url;
	}

	/**
	 * 获取最新新闻列表
	 * 
	 * @param itemcount
	 * @return
	 */
	public static String lastestNews(int itemcount) {
		String url = "http://wcf.open.cnblogs.com/news/recent/" + itemcount;
		return url;
	}

	/**
	 * 分页获取推荐新闻
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String recommendNews(int pageIndex, int pageSize) {
		String url = "http://wcf.open.cnblogs.com/news/recommend/paged/" + pageIndex + "/" + pageSize;
		return url;
	}

	/**
	 * 获取新闻内容
	 * 
	 * @param contentId
	 * @return
	 */
	public static String newsContent(int contentId) {
		String url = "http://wcf.open.cnblogs.com/news/item/" + contentId;
		return url;
	}

	/**
	 * 获取新闻评论
	 * 
	 * @param newsId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String newsComments(int newsId, int pageIndex, int pageSize) {
		String url = "http://wcf.open.cnblogs.com/news/item/" + newsId + "/comments/" + pageIndex + "/" + pageSize;
		return url;
	}

	// 博客部分

	/**
	 * 获取48小时阅读排行
	 * 
	 * @param itemcount
	 * @return
	 */
	public static String hot48HBlogs(int itemcount) {
		String url = " http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/" + itemcount;
		return url;
	}

	/**
	 * 获取10天内推荐排行
	 * 
	 * @param itemcount
	 * @return
	 */
	public static String recommend10DBlogs(int itemcount) {
		String url = " http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/" + itemcount;
		return url;
	}

	/**
	 * 分页获取推荐博客
	 * 
	 * @param pageindex
	 * @param pageSize
	 * @return
	 */
	public static String blogsAll(int pageindex, int pageSize) {
		String url = "http://wcf.open.cnblogs.com/blog/bloggers/recommend/" + pageindex + "/" + pageSize;
		return url;
	}

	/**
	 * 获取博客内容
	 * 
	 * @param postid
	 * @return
	 */
	public static String blogContent(int postid) {
		String url = "http://wcf.open.cnblogs.com/blog/post/body/" + postid;
		return url;
	}

	/**
	 * 获取博客评论
	 * 
	 * @param blogsId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String blogsComments(int blogsId, int pageIndex, int pageSize) {

		String url = "http://wcf.open.cnblogs.com/blog/post/" + blogsId + "/comments/" + pageIndex + "/" + pageSize;
		return url;
	}

	// 搜索部分

	public static String searchBloger(String blogerName) {
		String url = "http://wcf.open.cnblogs.com/blog/bloggers/search?t=" + blogerName;
		return url;
	}

	/**
	 * 分页获取个人博客文章列表
	 * 
	 * @param blogerName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String searchBloglist(String blogerName, int pageIndex, int pageSize) {
		String url = "http://wcf.open.cnblogs.com/blog/u/" + blogerName + "/posts/" + pageIndex + "/" + pageSize;
		return url;
	}

}
