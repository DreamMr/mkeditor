package com.wwb.mkeditor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
    public static String timeStamp(){
        Date now=new Date();
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMddHHmmss");
        String stamp=ft.format(now);
        return stamp;
    }
}
