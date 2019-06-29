package com.wwb.mkeditor.service.userservice;

import com.wwb.mkeditor.entities.Info;
import org.springframework.stereotype.Component;

/**
 * 用户信息调用接口
 * @author wwb
 */
@Component
public interface InfoService {
    boolean updateInfo(String userName,String address,String hobby,String img);
    Info getUserInfo(String userName);
    String getUserInfoImg(String userName);
}
