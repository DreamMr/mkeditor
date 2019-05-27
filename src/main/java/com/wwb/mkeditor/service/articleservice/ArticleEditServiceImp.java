package com.wwb.mkeditor.service.articleservice;

import com.wwb.mkeditor.dao.ArticleDao.ArticleDao;
import com.wwb.mkeditor.dao.elasticsearch.ArticleSearch.ArticleSearch;
import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import com.wwb.mkeditor.utils.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author wwb
 */
@Component
public class ArticleEditServiceImp implements ArticleEditService{
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ArticleSearch articleSearch;
    @Autowired
    RedisSetDao redisSetDao;
    private static final String hotKey="hotId";
    private static final String newKey="newId";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateArticle(String articleId, String summary, String title, String content) {
        boolean isInHot=isIn(hotKey,articleId);
        boolean isInNew=isIn(newKey,articleId);
        boolean isSucceed=updateArticleInDataBase(articleId,summary,title,content);
        if(isSucceed){
            if(isInHot){redisSetDao.deleteKey(hotKey);}
            if(isInNew){redisSetDao.deleteKey(newKey);}
            return true;
        }else{
            return false;
        }
    }
    private boolean isIn(String id,String articleId){
        String json=redisSetDao.getValue(id);
        if(json==null){return false;}
        JSONArray array=new JSONArray(json);
        for(int i=0;i<array.length();i++){
            JSONObject obj=array.getJSONObject(i);
            if(obj.getString("article_id").equals(articleId)){
                return true;
            }
        }
        return false;
    }
    synchronized boolean updateArticleInDataBase(String articleId, String summary, String title, String content){
        try{
            articleDao.updateArticle(summary,title,articleId);
            boolean isSucceed=articleSearch.saveArticleEntity(new ElasticesarchArticle(articleId,content,title,summary));
            if(!isSucceed){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            Logger.PrintException(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
