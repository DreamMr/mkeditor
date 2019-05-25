package com.wwb.mkeditor.service.articleservice;

import com.wwb.mkeditor.dao.ArticleDao.ArticleDao;
import com.wwb.mkeditor.dao.UserDao.UserDao;
import com.wwb.mkeditor.dao.elasticsearch.ArticleSearch.ArticleSearch;
import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class ArticleDeleteImp implements ArticleDelete {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    RedisSetDao redisSetDao;
    @Autowired
    ArticleSearch articleSearch;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteDataBaseArticle(String articleId) {
        try{
            articleDao.deleteArticle(articleId);
            boolean flag=articleSearch.deleteArticleEntityById(articleId);
            if(!flag){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFromRedis(String articleId, String hotId, String newId) {
        /**
         * 删除文章对应的赞
         */
        redisSetDao.deleteKey(articleId);
        inHotOrNew(hotId,articleId);
        inHotOrNew(newId,articleId);
        return true;
    }
    private void inHotOrNew(String Id,String articleId){
        JSONArray array=new JSONArray(redisSetDao.getValue(Id));
        for(int i=0;i<array.length();i++){
            if(array.getJSONObject(i).getString("article_id").equals(articleId)){
                deleteRedisHotOrNew(Id);
                return;
            }
        }
        return ;
    }
    synchronized private void deleteRedisHotOrNew(String id){
        redisSetDao.deleteKey(id);
    }
}
