package com.wwb.mkeditor.service.articleservice;

import org.springframework.stereotype.Component;

/**
 * 删除文章
 * @author wwb
 */
@Component
public interface ArticleDelete {
    /**
     * 删除在mysql数据库和elasticsearch
     * @param articleId
     * @return
     */
    boolean deleteDataBaseArticle(String articleId);

    /**
     * 从redis数据库删除数据
     * @param articleId
     * @return
     */
    boolean deleteFromRedis(String articleId,String hotId,String newId);
}
