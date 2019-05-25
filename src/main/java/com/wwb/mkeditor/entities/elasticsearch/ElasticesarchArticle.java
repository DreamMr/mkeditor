package com.wwb.mkeditor.entities.elasticsearch;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author wwb
 */
@Component
public class ElasticesarchArticle implements Serializable {

    public static final String INDEX="articles";
    public static final String TYPE="articleType";
    private static final long serialVersionUID = -8520340682495171024L;

    private String article_id;
    private String article_content;
    private String article_title;
    private String article_short;

    public ElasticesarchArticle(){
        super();
    }

    public ElasticesarchArticle(String article_id, String article_content, String article_title,String article_short) {
        this.article_id = article_id;
        this.article_content = article_content;
        this.article_title = article_title;
        this.article_short=article_short;
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

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }
}
