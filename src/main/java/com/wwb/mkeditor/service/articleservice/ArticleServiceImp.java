package com.wwb.mkeditor.service.articleservice;

import com.wwb.mkeditor.dao.ArticleDao.ArticleDao;
import com.wwb.mkeditor.dao.UserDao.UserDao;
import com.wwb.mkeditor.dao.elasticsearch.ArticleSearch.ArticleSearch;
import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import com.wwb.mkeditor.entities.Article;
import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import com.wwb.mkeditor.entities.mysqlentity.MysqlArticle;
import com.wwb.mkeditor.utils.TimeStamp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ArticleServiceImp implements ArticleService{
    @Autowired
    ArticleDao articleDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RedisSetDao redisSetDao;
    @Autowired
    ArticleSearch articleSearch;
    @Autowired
    ArticleDelete articleDelete;
    private static final String hotKey="hotId";
    private static final String newKey="newId";
    private static final Integer index=0;
    private static final Integer hotPsize=10;
    private static final Integer newPsize=6;
    private static final String padding=" ";
    private static final Integer titleSize=50;
    private static final Integer summarySize=150;

    @Override
    public boolean upLoadArticle(Article article) {
        String articleId= "a"+TimeStamp.timeStamp()+userDao.getUser(article.getUser_name()).getUser_id();
        article.setArticle_id(articleId);
        try {
            articleDao.InsertArticle(article.getArticle_id(), article.getUser_name(), article.getArticle_like(), article.getArticle_comment(),article.getArticle_short(), article.getArticle_title());
            articleSearch.saveArticleEntity(new ElasticesarchArticle(article.getArticle_id(),article.getArticle_content(),article.getArticle_title(),article.getArticle_short()));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Article getArticle(String articleId) {
        MysqlArticle article=articleDao.getArticle(articleId);
        ElasticesarchArticle elasticesarchArticle=articleSearch.SearchById(articleId);
        Article ansArticle=UnionArticle(article,elasticesarchArticle);
        return ansArticle;
    }

    /**
     * 将两个文章实体进行合并
     * @param mysqlArticle
     * @param elasticesarchArticle
     * @return
     */
    private Article UnionArticle(MysqlArticle mysqlArticle,ElasticesarchArticle elasticesarchArticle){
        if(mysqlArticle==null || elasticesarchArticle==null){return null;}
        Article article=new Article(mysqlArticle.getArticle_id(),mysqlArticle.getUser_name(),mysqlArticle.getArticle_like(),mysqlArticle.getArticle_comment(),elasticesarchArticle.getArticle_content(),mysqlArticle.getArticle_short(),mysqlArticle.getArticle_title());
        return article;
    }
    @Override
    public boolean addLike(String articleId,String userName) {
        System.out.println(articleId);
        if(redisSetDao.addElement(articleId,userName)){
            addLikeNumSingle(articleId);
            return true;
        }else{
            return false;
        }
    }
    synchronized private void addLikeNumSingle(String articleId){
        HashMap<String ,String>mp=new HashMap<>();
        mp.put("articleId",articleId);
        articleDao.addArticleLike(mp);
    }

    @Override
    public String getTopNew() {
        String articles=redisSetDao.getValue(newKey);
        if(articles==null || articles.equals("")){
            articles=TopNew();
        }
        return articles;
    }

    synchronized private String TopNew(){
        String articles=redisSetDao.getValue(newKey);
        if(articles==null || articles.equals("")){
            List<MysqlArticle> list=articleDao.topNew(index,newPsize);
            JSONArray array=new JSONArray();
            for(MysqlArticle article:list){
                JSONObject obj=new JSONObject();
                obj.put("article_id",article.getArticle_id());
                obj.put("article_short",article.getArticle_short());
                obj.put("article_title",article.getArticle_title());
                array.put(obj);
            }
            articles=array.toString();
            redisSetDao.addValue(newKey,articles,30,TimeUnit.MINUTES);
        }
        return articles;
    }
    @Override
    public String getTopHot() {
        String articles=redisSetDao.getValue(hotKey);
        if(articles==null || articles.equals("")){
            articles=TopHot();
        }
        return articles;
    }

    /**
     * 将最热写进redis缓存
     * @return
     */
    synchronized private String TopHot(){
        String articles=redisSetDao.getValue(hotKey);
        if(articles==null || articles.equals("")){
            List<MysqlArticle> list=articleDao.topLike(index,hotPsize);
            JSONArray array=new JSONArray();
            for(MysqlArticle article:list){
                JSONObject obj=new JSONObject();
                obj.put("article_id",article.getArticle_id());
                obj.put("article_title",article.getArticle_title());
                array.put(obj);
            }
            articles=array.toString();
            redisSetDao.addValue(hotKey,articles,1, TimeUnit.DAYS);
        }
        return articles;
    }

    @Override
    public String searchArticle(String keyWord) {
        String result=articleSearch.searchArticleEntityOr(keyWord);
        return result;
    }

    @Override
    public String searchArticleWithSummary(String keyWord) {
        String result=articleSearch.searchArticleEntityWithSummary(keyWord);
        return result;
    }

    @Override
    public List<MysqlArticle> getArticleListByAuthorName(String userName) {
        return articleDao.getArticleByAuthorName(userName);
    }

    @Override
    public boolean deleteArticle(String articleId) {
        boolean flag=articleDelete.deleteDataBaseArticle(articleId);
        if(flag){
            articleDelete.deleteFromRedis(articleId,hotKey,newKey);
        }
        return flag;
    }
}
