package com.wwb.mkeditor.entities;

import org.springframework.stereotype.Component;

@Component
public class Comment {
    private String article_id;
    private String user_name;
    private String comment_content;
    private String comment_time;

    public Comment() {
    }

    public Comment(String article_id, String user_name, String comment_content, String comment_time) {
        this.article_id = article_id;
        this.user_name = user_name;
        this.comment_content = comment_content;
        this.comment_time = comment_time;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
}
