package com.projects.shengxi.bean;

/**
 * 博主姓名
 * 
 * @author SL
 *
 */
public class BlogerDataBean {
	private String id;// 博主id，相当于link
	private String title;// 博主名字
	private String updated;// 博主最新更新时间
	private String blogapp;// 博客地址
	private String avatar;// 博主头像地址
	private String postcount;// 博主所发文章总量

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getBlogapp() {
		return blogapp;
	}

	public void setBlogapp(String blogapp) {
		this.blogapp = blogapp;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPostcount() {
		return postcount;
	}

	public void setPostcount(String postcount) {
		this.postcount = postcount;
	}
}
