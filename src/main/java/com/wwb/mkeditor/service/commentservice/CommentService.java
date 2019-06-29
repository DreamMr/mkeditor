package com.wwb.mkeditor.service.commentservice;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 评论服务层
 * @author wwb
 */
@Component
public interface CommentService {
    /**
     * 根据文章ID来获取评论
     * @param articleId
     * @return
     */
    List<Map<String,String>> getCommentById(String articleId);

    /**
     * 编写评论
     * @param userName
     * @param content
     * @param articleId
     * @return
     */
    boolean writeComment(String userName,String content,String articleId);
}
