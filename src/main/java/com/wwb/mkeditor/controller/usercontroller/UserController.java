package com.wwb.mkeditor.controller.usercontroller;

import com.wwb.mkeditor.dao.UserDao.UserDao;
import com.wwb.mkeditor.service.userservice.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@ResponseBody
@RequestMapping(value = "/user")
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public String getUserName(HttpSession session){
        String userName=(String)session.getAttribute("userName");
        JSONObject obj=new JSONObject();
        if(userName==null){
            obj.put("statu",false);
        }else{
            obj.put("statu",true);
            obj.put("userName",userName);
        }
        return obj.toString();
    }
    @RequestMapping(value = "/validateUserName",method = RequestMethod.POST)
    public String validateUserName(@Param("userName")String userName){
        System.out.println(userName);
        boolean flag=userService.validateUserName(userName);
        JSONObject obj=new JSONObject();
        if(flag){
            obj.put("statu",true);
        }else{
            obj.put("statu",false);
        }
        return obj.toString();
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@Param("userName")String userName,@Param("passWord")String passWord,HttpSession session){
        JSONObject obj=new JSONObject();
        boolean flag=userService.register(userName,passWord);
        obj.put("statu",flag);
        if(flag){
            session.setAttribute("userName",userName);
        }
        return obj.toString();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@Param("userName")String userName,@Param("passWord")String passWord,HttpSession session){
        JSONObject obj=new JSONObject();
        boolean flag=userService.validatePassWord(userName,passWord);
        obj.put("statu",flag);
        if(flag){
            session.setAttribute("userName",userName);
        }
        return obj.toString();
    }
    @RequestMapping(value = "/SignOut",method = RequestMethod.GET)
    public void signOut(HttpSession session){
        session.removeAttribute("userName");
        return;
    }
}
