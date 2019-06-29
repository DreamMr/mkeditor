package com.wwb.mkeditor;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.wwb.mkeditor.dao.ArticleDao.ArticleDao;
import com.wwb.mkeditor.dao.CommentDao.CommentDao;
import com.wwb.mkeditor.dao.elasticsearch.ArticleSearch.ArticleSearch;
import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import com.wwb.mkeditor.service.articleservice.ArticleService;
import com.wwb.mkeditor.service.userservice.InfoService;
import com.wwb.mkeditor.service.userservice.InfoServiceImp;
import com.wwb.mkeditor.utils.Base64Util;
import com.wwb.mkeditor.utils.TimeStamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MkeditorApplicationTests {
    @Autowired
    ArticleDao articleDao;
    @Test
    public void contextLoads() {
        System.out.println(Charset.defaultCharset());
    }

    /*
    @Autowired
    ArticleSearch articleSearch;
    @Autowired
    ArticleDao articleDao;
    @Test
    public void updateArticle(){
        ElasticesarchArticle article=new ElasticesarchArticle();
        article.setArticle_short("测试文章摘要1");
        article.setArticle_title("测试文章标题2");
        article.setArticle_content("测试文章内容1");
        article.setArticle_id("1234");
        boolean flag=articleSearch.saveArticleEntity(article);
        System.out.println(flag);
    }
    @Test
    public void updateArticleInMySQL(){
        articleDao.updateArticle("一道不知道什么的算法题","测试更新","a20190525170749c62476fe");
    }
    */
}
