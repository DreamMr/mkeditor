package com.wwb.mkeditor.dao.elasticsearch.ArticleSearch;

import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleSearch {
    boolean saveArticleEntity(ElasticesarchArticle entity);
    String searchArticleEntityAnd(String searchContent);
    String searchArticleEntityOr(String searchContent);
    String searchArticleEntityWithSummary(String searchContent);
    ElasticesarchArticle SearchById(String keyId);
    boolean deleteArticleEntityById(String keyId);
}
