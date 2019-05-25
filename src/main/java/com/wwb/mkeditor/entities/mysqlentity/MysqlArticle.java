package com.wwb.mkeditor.entities.mysqlentity;

public class MysqlArticle {
    private String article_id;
    private String user_name;
    private Integer article_like;
    private Integer article_comment;
    private String article_short;
    private String article_title;

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

    public Integer getArticle_like() {
        return article_like;
    }

    public void setArticle_like(Integer article_like) {
        this.article_like = article_like;
    }

    public Integer getArticle_comment() {
        return article_comment;
    }

    public void setArticle_comment(Integer article_comment) {
        this.article_comment = article_comment;
    }

    public String getArticle_short() {
        return article_short;
    }

    public void setArticle_short(String article_short) {
        this.article_short = article_short;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }
}
