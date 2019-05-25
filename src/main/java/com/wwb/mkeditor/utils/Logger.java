package com.wwb.mkeditor.utils;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger {
    private static org.slf4j.Logger logger= LoggerFactory.getLogger("异常");
    public static void PrintException(String message){
        logger.warn(message);
    }
}
