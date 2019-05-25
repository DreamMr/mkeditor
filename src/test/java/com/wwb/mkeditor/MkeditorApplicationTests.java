package com.wwb.mkeditor;

import com.wwb.mkeditor.dao.elasticsearch.ArticleSearch.ArticleSearch;
import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import com.wwb.mkeditor.entities.elasticsearch.ElasticesarchArticle;
import com.wwb.mkeditor.service.articleservice.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MkeditorApplicationTests {

    @Test
    public void contextLoads() {
    }

}
