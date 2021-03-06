package com.wwb.mkeditor.controller.articlecontroller;

import com.wwb.mkeditor.entities.Article;
import com.wwb.mkeditor.service.articleservice.ArticleService;
import com.wwb.mkeditor.utils.Logger;
import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

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
    final static String SUFFIX=".md";
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
    @RequestMapping(value = "/DeleteArticle",method = RequestMethod.POST)
    public String deleteArticle(@Param("articleId")String articleId){
        boolean flag=articleService.deleteArticle(articleId);
        JSONObject obj=new JSONObject();
        obj.put("statu",flag);
        return obj.toString();
    }

    @RequestMapping(value = "/editArticle",method = RequestMethod.POST)
    public String editArticle(@Param("articleId")String articleId,@Param("content")String content,@Param("title")String title,@Param("summary")String summary){
        boolean flag=articleService.editArticle(articleId,summary,title,content);
        JSONObject obj=new JSONObject();
        obj.put("statu",flag);
        return obj.toString();
    }

    @RequestMapping(value = "/articleDownload",method = RequestMethod.POST)
    public void downloadArticle(@Param("articleId")String articleId, HttpServletResponse response) {
        Article article=articleService.getArticle(articleId.trim());
        String content=article.getArticle_content();
        String fileName=article.getArticle_title();
        try{
            response.reset();
            response.setContentType("application/text;charset=utf-8");
            String filename=new String(fileName.getBytes("utf-8"),"ISO8859-1");//先用gb2312来进行编码，然后使用ISO8859-1来解码。但是在http传递数据的过程中会对数据使用ISO8859-1来进行编码，所以此时为gbk2312的字符编码序列。浏览器端就可以解析成中文
            response.setHeader("Content-Disposition", "attachment;filename=" +filename+SUFFIX);
            PrintWriter writer=response.getWriter();
            writer.write(content);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            Logger.PrintException(e.getMessage());
        }
    }
}
