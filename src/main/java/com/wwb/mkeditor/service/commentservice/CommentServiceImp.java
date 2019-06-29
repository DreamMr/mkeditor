package com.wwb.mkeditor.service.commentservice;

import com.wwb.mkeditor.dao.ArticleDao.ArticleDao;
import com.wwb.mkeditor.dao.CommentDao.CommentDao;
import com.wwb.mkeditor.entities.Comment;
import com.wwb.mkeditor.utils.Logger;
import com.wwb.mkeditor.utils.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;

@Component
public class CommentServiceImp implements CommentService{
    @Autowired
    CommentDao commentDao;
    @Autowired
    ArticleDao articleDao;
    @Override
    public List<Map<String, String>> getCommentById(String articleId) {
       List<Map<String,String>>ans=commentDao.getCommentById(articleId);
       return ans;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean writeComment(String userName, String content, String articleId) {
        String time= TimeStamp.getTime();
        try{
            commentDao.insertComment(articleId,userName,content,time);
            articleDao.addArticleComment(articleId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Logger.PrintException(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
