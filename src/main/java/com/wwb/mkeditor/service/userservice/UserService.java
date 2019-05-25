package com.wwb.mkeditor.service.userservice;

import org.springframework.stereotype.Component;

/**
 * 用户个人处理
 * @author  wwb
 */
@Component
public interface UserService {
    /**
     * 验证用户名是否被使用过
     * @param newUserName
     * @return
     */
    boolean validateUserName(String newUserName);

    /**
     * 验证用户名密码是否正确
     * @param userName
     * @param passWord
     * @return true：成功 false：失败
     */
    boolean validatePassWord(String userName,String passWord);

    /**
     * 注册用户
     * @param userName
     * @param passWord
     * @return true：成功注册 false：注册失败
     */
    boolean register(String userName,String passWord);
}
