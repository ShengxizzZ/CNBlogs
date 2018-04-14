package com.projects.shengxi.bean;

/**
 * Created by ShengXi on 2016/6/27.
 */
public class NewsContentBean {

    private String title;
    private String sourceName;
    private String submitDate;
    private String content;
    private String imageUrl;
    private String prevNews;
    private String nextNews;
    private String commentCount;//评论数

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getNextNews() {
        return nextNews;
    }

    public void setNextNews(String nextNews) {
        this.nextNews = nextNews;
    }

    public String getPrevNews() {
        return prevNews;
    }

    public void setPrevNews(String prevNews) {
        this.prevNews = prevNews;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
