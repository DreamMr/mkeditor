package com.wwb.mkeditor.controller.usercontroller;

import com.wwb.mkeditor.entities.Info;
import com.wwb.mkeditor.service.userservice.InfoService;
import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/userInfo")
@ResponseBody
@Controller
public class InfoController {
    @Autowired
    InfoService infoService;

    @RequestMapping(value = "/upLoad",method = RequestMethod.POST)
    public String upLoad(@Param("img")String img, @Param("address")String address, @Param("hobby")String hobby, HttpSession session){
        String userName=(String)session.getAttribute("userName");
        boolean isSucceed=infoService.updateInfo(userName,address,hobby,img);
        JSONObject object=new JSONObject();
        object.put("statu",isSucceed);
        return object.toString();
    }
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public String getUserInfo(HttpServletRequest request){
        String userName=request.getParameter("userName");
        JSONObject obj=getUserInfo(userName);
        return obj.toString();
    }
    @RequestMapping(value = "/getUserInfoSelf",method = RequestMethod.POST)
    public String getUserInfoSelf(HttpSession session){
        String userName=(String)session.getAttribute("userName");
        JSONObject obj=getUserInfo(userName);
        obj.put("userName",userName);
        return obj.toString();
    }
    private JSONObject getUserInfo(String userName){
        Info info=infoService.getUserInfo(userName);
        JSONObject obj=new JSONObject();
        if(info==null){
            obj.put("statu",false);
            return obj;
        }else{
            obj.put("statu",true);
        }
        obj.put("address",info.getInfo_address());
        obj.put("hobby",info.getInfo_hobby());
        obj.put("img",info.getInfo_img());
        return obj;
    }
}
