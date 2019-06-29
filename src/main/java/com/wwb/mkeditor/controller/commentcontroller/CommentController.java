package com.wwb.mkeditor.controller.commentcontroller;

import com.wwb.mkeditor.service.commentservice.CommentService;
import io.lettuce.core.dynamic.annotation.Param;
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
import java.util.Map;

@RequestMapping(value = "/comment")
@ResponseBody
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @RequestMapping(value = "/getComment",method = RequestMethod.POST)
    public String getComment(HttpServletRequest request){
        String articleId=request.getParameter("articleId");
        List<Map<String,String>> ans=commentService.getCommentById(articleId);
        JSONObject obj=new JSONObject();
        if(ans!=null){
            JSONArray array=new JSONArray();
            obj.put("statu",true);
            System.out.println(ans.size());
            for(int i=0;i<ans.size();i++){
                Map<String,String>mp=ans.get(i);
                JSONObject tmp=new JSONObject(mp);
                array.put(tmp);
            }
            obj.put("comments",array);
        }else{
            obj.put("statu",false);
        }
        return obj.toString();
    }
    @RequestMapping(value = "/writeComment",method = RequestMethod.POST)
    public String writeComment(@Param("articleId")String articleId,@Param("content")String content,HttpSession session){
        String userName=(String) session.getAttribute("userName");
        JSONObject obj=new JSONObject();
        if(userName==null){
            obj.put("statu",false);
            return obj.toString();
        }
        boolean flag=commentService.writeComment(userName,content,articleId);
        obj.put("statu",flag);
        return obj.toString();
    }
}
