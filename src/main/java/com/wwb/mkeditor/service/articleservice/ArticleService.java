package com.wwb.mkeditor.service.articleservice;

import com.wwb.mkeditor.entities.Article;
import com.wwb.mkeditor.entities.mysqlentity.MysqlArticle;
import org.springframework.stereotype.Component;

import java.util.List;
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
     * 根据作者名获取文章列表
     * @param userName
     * @return
     */
    List<MysqlArticle> getArticleListByAuthorName(String userName);

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

    /**
     * 根据文章id来删除文章：MySQL、Elasticsearch、Redis
     * @param articleId
     * @return
     */
    boolean deleteArticle(String articleId);

    /**
     * 根据文章ID来更新文章
     * @param articleId
     * @param summary
     * @param title
     * @param content
     * @return
     */
    boolean editArticle(String articleId,String summary,String title,String content);
}
