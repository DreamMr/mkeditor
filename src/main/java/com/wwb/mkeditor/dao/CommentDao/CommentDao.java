package com.wwb.mkeditor.dao.CommentDao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDao {
    @Select("select c.*,table_info.info_img from (SELECT table_comment.user_name,table_comment.comment_time,table_comment.comment_content from table_comment where article_id=#{articleId}) c INNER join table_info where table_info.user_name=c.user_name;")
    List<Map<String,String>>getCommentById(@Param("articleId")String articleId);

    @Insert("insert into table_comment(article_id,user_name,comment_content,comment_time)values(#{articleId},#{userName},#{comment_content},#{comment_time})")
    void insertComment(@Param("articleId")String articleId,@Param("userName")String userName,@Param("comment_content")String content,@Param("comment_time")String time);
}
