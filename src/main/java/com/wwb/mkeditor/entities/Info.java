package com.wwb.mkeditor.entities;

import org.springframework.stereotype.Component;

@Component
public class Info {
    private String user_name;
    private String info_address;
    private String info_hobby;
    private String info_img;
    public static String DEFAULT_IMG="i20190529164456";

    public Info(){
        this.user_name="";
        this.info_address="";
        this.info_hobby="";
        this.info_img=DEFAULT_IMG;
    }

    public Info(String user_name, String info_address, String info_hobby, String info_img) {
        this.user_name = user_name;
        this.info_address = info_address;
        this.info_hobby = info_hobby;
        this.info_img = info_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getInfo_address() {
        return info_address;
    }

    public void setInfo_address(String info_address) {
        this.info_address = info_address;
    }

    public String getInfo_hobby() {
        return info_hobby;
    }

    public void setInfo_hobby(String info_hobby) {
        this.info_hobby = info_hobby;
    }

    public String getInfo_img() {
        return info_img;
    }

    public void setInfo_img(String info_img) {
        this.info_img = info_img;
    }
}
