package com.wwb.mkeditor.service.userservice;

import com.wwb.mkeditor.dao.UserDao.InfoDao;
import com.wwb.mkeditor.dao.UserDao.UserDao;
import com.wwb.mkeditor.entities.Info;
import com.wwb.mkeditor.entities.User;
import com.wwb.mkeditor.utils.Logger;
import com.wwb.mkeditor.utils.MDSecret;
import com.wwb.mkeditor.utils.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class UserServiceImp implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UUID uidUtils;
    @Autowired
    InfoDao infoDao;
    @Override
    public boolean validateUserName(String newUserName) {
        User user=userDao.getUser(newUserName);
        if(user!=null){return false;}
        return true;
    }

    @Override
    public boolean validatePassWord(String userName, String passWord) {
        User user=userDao.getUser(userName);
        if(user.getUser_password().equals(MDSecret.addSecret(passWord))){
            return true;
        }
        return false;
    }

    @Override
    public boolean register(String userName, String passWord) {
        String uuid= uidUtils.getUUID();
        return addUser(userName,MDSecret.addSecret(passWord),uuid);
    }

    synchronized private boolean addUser(String userName,String passWord,String uuid){
        try{
            userDao.addUser(userName,passWord,uuid);
            Info info=new Info();
            info.setUser_name(userName);
            infoDao.insertInfo(info.getUser_name(),info.getInfo_address(),info.getInfo_hobby(),info.getInfo_img());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Logger.PrintException(e.getMessage());
            return false;
        }
    }
}
