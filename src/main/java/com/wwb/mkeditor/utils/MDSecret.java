package com.wwb.mkeditor.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


public class MDSecret {
    public static String addSecret(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
