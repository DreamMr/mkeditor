package com.wwb.mkeditor.controller.usercontroller;

import com.wwb.mkeditor.entities.mysqlentity.MysqlArticle;
import com.wwb.mkeditor.service.articleservice.ArticleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@ResponseBody
@RequestMapping(value = "/userSpace")
@Controller
public class UserSpaceController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/GetUserInfo",method = RequestMethod.POST)
    public String GetUserInfo(HttpServletRequest request, HttpSession session){
        JSONObject obj=new JSONObject();
        obj.put("statu",true);
        String userName=request.getParameter("userName");
        String sessionName=(String)session.getAttribute("userName");
        if(sessionName!=null && sessionName.equals(userName)){
            obj.put("isUser",true);
        }else{
            obj.put("isUser",false);
        }
        List<MysqlArticle>list=articleService.getArticleListByAuthorName(userName);
        JSONArray array=new JSONArray();
        for(int i=0;i<list.size();i++){
            JSONObject tmp=new JSONObject(list.get(i));
            array.put(tmp);
        }
        obj.put("articles",array);
        return obj.toString();
    }

}
