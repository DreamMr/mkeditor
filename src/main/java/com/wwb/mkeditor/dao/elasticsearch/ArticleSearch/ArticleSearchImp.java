package com.wwb.mkeditor.dao.elasticsearch.ArticleSearch;

import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleSearchImp implements ArticleSearch {
    @Autowired
    private JestClient jestClient;
    private static final String title="article_title";
    private static final String content="article_content";
    private static final String id="article_id";
    private static final String summary="article_short";
    @Override
    public boolean saveArticleEntity(ElasticesarchArticle entity) {
        Index.Builder builder=new Index.Builder(entity);
        builder.id(entity.getArticle_id());
        Index index=builder.index(ElasticesarchArticle.INDEX).type(ElasticesarchArticle.TYPE).build();
        try{
            jestClient.execute(index);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String searchArticleEntityAnd(String searchContent) {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder=QueryBuilders.boolQuery();
        try{
            String []keyWord=searchContent.split(" ");
            for(int i=0;i<keyWord.length;i++){
                System.out.println(keyWord[i]);
                queryBuilder.must(QueryBuilders.multiMatchQuery(keyWord[i],title,content));
            }
            searchSourceBuilder.query(queryBuilder).fetchSource(new String[]{id,title,summary},new String[]{content});
            Search search=new Search.Builder(searchSourceBuilder.toString()).addIndex(ElasticesarchArticle.INDEX).addType(ElasticesarchArticle.TYPE).build();
            JestResult result=jestClient.execute(search);
            return parseSearch(result.getJsonString());
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String searchArticleEntityOr(String searchContent) {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //BoolQueryBuilder queryBuilder=QueryBuilders.boolQuery();
        QueryBuilder queryBuilder=QueryBuilders.multiMatchQuery(searchContent,title,content);
        try{
            searchSourceBuilder.query(queryBuilder).fetchSource(new String[]{id,title},new String[]{content,summary});
            Search search=new Search.Builder(searchSourceBuilder.toString()).addIndex(ElasticesarchArticle.INDEX).addType(ElasticesarchArticle.TYPE).build();
            JestResult result=jestClient.execute(search);
            return parseSearch(result.getJsonString());
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public ElasticesarchArticle SearchById(String keyId){
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        QueryBuilder queryBuilder=QueryBuilders.constantScoreQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery(id,keyId)));
        try{
            searchSourceBuilder.query(queryBuilder);
            Search search=new Search.Builder(searchSourceBuilder.toString()).addIndex(ElasticesarchArticle.INDEX).addType(ElasticesarchArticle.TYPE).build();
            JestResult result=jestClient.execute(search);
            return result.getSourceAsObject(ElasticesarchArticle.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteArticleEntityById(String keyId) {
        Delete delete=new Delete.Builder(keyId).index(ElasticesarchArticle.INDEX).type(ElasticesarchArticle.TYPE).build();
        JestResult result=null;
        try{
            result=jestClient.execute(delete);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.isSucceeded();
    }

    @Override
    public String searchArticleEntityWithSummary(String searchContent) {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //BoolQueryBuilder queryBuilder=QueryBuilders.boolQuery();
        QueryBuilder queryBuilder=QueryBuilders.multiMatchQuery(searchContent,title,content);
        try{
            searchSourceBuilder.query(queryBuilder).fetchSource(new String[]{id,title,summary},new String[]{content});
            Search search=new Search.Builder(searchSourceBuilder.toString()).addIndex(ElasticesarchArticle.INDEX).addType(ElasticesarchArticle.TYPE).build();
            JestResult result=jestClient.execute(search);
            return parseSearch(result.getJsonString());
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private String parseSearch(String json){
        System.out.println(json);
        if(json.equals("")){return "";}
        JSONObject obj=new JSONObject(json);
        JSONArray hits=obj.getJSONObject("hits").getJSONArray("hits");
        if(hits.length()==0){return hits.toString();}
        JSONArray array=new JSONArray();
        for(int i=0;i<hits.length();i++){
            JSONObject o=hits.getJSONObject(i).getJSONObject("_source");
            array.put(o);
        }
        return array.toString();
    }


}
