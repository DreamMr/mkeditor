package com.wwb.mkeditor.service.userservice;

import com.wwb.mkeditor.dao.UserDao.InfoDao;
import com.wwb.mkeditor.entities.Info;
import com.wwb.mkeditor.utils.Base64Util;
import com.wwb.mkeditor.utils.Logger;
import com.wwb.mkeditor.utils.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

/**
 * @author wwb
 */
@Component
public class InfoServiceImp implements InfoService{
    @Autowired
    InfoDao infoDao;
    private static String IMGPREFIX="i";
    private static String PREFIXFILE="F:/javaLearning/java/mkeditor/src/main/resources/static/img/";
    @Override
    public boolean updateInfo(String userName, String address, String hobby, String img) {
        if(img==null || img.equals("")){
            img=Info.DEFAULT_IMG;
        }
        String path=null;
        if(!img.substring(0,IMGPREFIX.length()).equals(IMGPREFIX)){
            String last=infoDao.getInfoImg(userName);
            if(last.equals(Info.DEFAULT_IMG)){
                path=IMGPREFIX+TimeStamp.timeStamp();
            }else{
                path=last;
            }
            byte[] bytes= Base64Util.decoder(img);
            boolean isSucceed=tranferToFile(bytes,path);
            if(!isSucceed){return false;}
            infoDao.updateInfoWithImg(address,hobby,userName,path);
        }else{
            infoDao.updateInfo(address,hobby,userName);
        }
        return true;
    }
    public boolean tranferToFile(byte[] img,String path){
        try{
            OutputStream outputStream=new FileOutputStream(PREFIXFILE+path,false);
            outputStream.write(img);
            outputStream.flush();
            outputStream.close();
//            FileWriter fw=new FileWriter(PREFIXFILE+path,false);
//            BufferedWriter bw=new BufferedWriter(fw);
//            bw.write(img);
//            bw.close();
//            fw.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Logger.PrintException(e.getMessage());
            return false;
        }
    }
    @Override
    public Info getUserInfo(String userName) {
        return infoDao.getInfo(userName);
    }

    @Override
    public String getUserInfoImg(String userName) {
        return infoDao.getInfoImg(userName);
    }
}
