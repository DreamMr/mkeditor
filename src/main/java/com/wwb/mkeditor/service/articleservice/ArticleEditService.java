package com.wwb.mkeditor.service.articleservice;

import org.springframework.stereotype.Component;

/**
 * 文章编辑
 */
@Component
public interface ArticleEditService {
    boolean updateArticle(String articleId, String summary, String title, String content);
}
