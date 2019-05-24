package com.wwb.mkeditor.controller.articlecontroller;

import com.wwb.mkeditor.entities.Article;
import com.wwb.mkeditor.service.articleservice.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wwb
 * 文章相关的控制器
 */
@RequestMapping(value = "/article")
@ResponseBody
@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @RequestMapping(value = "/upLoadArticle",method = RequestMethod.POST)
    String upLoadArticle(@Param("title")String title, @Param("summary")String summary, @Param("content")String content, HttpSession session){
        String userName=(String)session.getAttribute("userName");
        //********前起调试未登录用*******
        if(userName==null){
            userName="wwb";
        }
        //*******************************
        Article article=new Article();
        article.setUser_name(userName);
        article.setArticle_title(title);
        article.setArticle_short(summary);
        article.setArticle_content(content);
        boolean isSucceed=articleService.upLoadArticle(article);
        JSONObject obj=new JSONObject();
        obj.put("statu",isSucceed);
        return obj.toString();
    }

    @RequestMapping(value = "/getArticle",method = RequestMethod.POST)
    String getArticle(@Param("articleId")String articleId){
        Article article=articleService.getArticle(articleId.trim());
        JSONObject obj=new JSONObject();
        obj.put("statu",true);
        JSONObject articleJson=new JSONObject(article);
        obj.put("article",articleJson);
        return obj.toString();
    }

    @RequestMapping(value = "/addLike",method = RequestMethod.POST)
    String addLike(@Param("articleId")String articleId,HttpSession session){
        String userName=(String)session.getAttribute("userName");
        if(userName==null){userName="wwb";}
        JSONObject obj=new JSONObject();
        if(userName==null){
            obj.put("statu",false);
        }else{
            boolean isSucceed=articleService.addLike(articleId,userName);
            obj.put("statu",isSucceed);
        }
        return obj.toString();
    }

    @RequestMapping(value = "/TopNew",method = RequestMethod.POST)
    String TopNew(){
        return articleService.getTopNew();
    }

    @RequestMapping(value = "/TopHot",method = RequestMethod.POST)
    String TopHot(){
        return articleService.getTopHot();
    }

    @RequestMapping(value = "/SearchArticle",method = RequestMethod.POST)
    public String SearchArticle(HttpServletRequest request){
        String keyWord=request.getParameter("keyword");
        if(keyWord==null){return "";}
        String json= articleService.searchArticle(keyWord);
        return json;
    }
    @RequestMapping(value = "/SearchArticleWithSummary",method = RequestMethod.POST)
    public String SearchArticleByKeyWord(HttpServletRequest request){
        String keyWord=request.getParameter("searchKey");
        System.out.println(keyWord);
        if(keyWord==null){return "";}
        String json=articleService.searchArticleWithSummary(keyWord);
        System.out.println(json);
        return json;
    }
}
