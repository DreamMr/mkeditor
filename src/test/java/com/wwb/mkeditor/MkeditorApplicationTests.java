package com.wwb.mkeditor;

import com.wwb.mkeditor.dao.elasticsearch.ArticleSearch.ArticleSearch;
import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MkeditorApplicationTests {
   @Autowired
    RedisSetDao redisSetDao;
   @Autowired
    ArticleSearch articleSearch;
    @Test
    public void contextLoads() {
    }
    @Test
    public void RedisSet(){
        Set<String> set=redisSetDao.getAll("123");
        if(set==null){System.out.println("null");}
        else if(set.size()==0){System.out.println("0");}
        else {System.out.println("wtf!");}
    }
    @Test
    public void ElstaticsearchSave(){
        ElasticesarchArticle article1=new ElasticesarchArticle();
        article1.setArticle_content("我想要找到实习！");
        article1.setArticle_id("1234");
        article1.setArticle_title("测试题目2");
        article1.setArticle_short("摘要1");

        ElasticesarchArticle article2=new ElasticesarchArticle();
        article2.setArticle_title("测试题目3");
        article2.setArticle_id("12345");
        article2.setArticle_content("我要读研究生");
        article2.setArticle_short("摘要2");

        boolean flag1=articleSearch.saveArticleEntity(article1);
        boolean flag2=articleSearch.saveArticleEntity(article2);
        if(flag1){System.out.println("article 1 is succeed!");}
        if(flag2){System.out.println("article2 is succeed!");}
    }

    @Test
    public void Elasticsearch(){
        String content="最长等差序列";
        String json=articleSearch.searchArticleEntityAnd(content);
        System.out.println(json);
        System.out.println("or :");
        json=articleSearch.searchArticleEntityWithSummary(content);
        System.out.println(json);
    }
    @Test
    public void ElasticsearchById(){
        String id="1234";
        //String json=articleSearch.SearchById(id);
        //System.out.println(json);
    }
}
