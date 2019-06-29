package com.wwb.mkeditor.utils;

import java.util.Base64;

public class Base64Util {
    private final static String head="data:image/jpeg;base64,";
    final static Base64.Decoder decoder = Base64.getDecoder();
    public static byte[] decoder(String base64Code){
        try{
            if(base64Code.contains(head)){
                base64Code=base64Code.substring(head.length());
            }
            System.out.println(base64Code);
            byte[] bytes=decoder.decode(base64Code);
           return bytes;
        }catch (Exception e){
            e.printStackTrace();
            Logger.PrintException(e.getMessage());
            return null;
        }
    }
}
