package com.wwb.mkeditor.service.articleservice;

import com.wwb.mkeditor.entities.Article;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wwb
 * 文章服务
 */
@Component
public interface ArticleService {
    /**
     * 文章上传功能
     * @param article
     * @return
     */
    boolean upLoadArticle(Article article);

    /**
     * 根据ID，获取文章
     * @param articleId
     * @return
     */
    Article getArticle(String articleId);

    /**
     * 文章点赞功能
     * @param articleId
     * @return
     */
    boolean addLike(String articleId,String uname);

    /**
     * 获取前6最新的文章
     * @return
     */
    String getTopNew();

    /**
     * 获取前10最热文章
     * @return
     */
    String getTopHot();

    /**
     * 文章搜索
     */
    String searchArticle(String keyWord);

    /**
     * 根据关键字进行查询搜索，同时返回带有摘要
     * @param keyWord
     * @return
     */
    String searchArticleWithSummary(String keyWord);
}
