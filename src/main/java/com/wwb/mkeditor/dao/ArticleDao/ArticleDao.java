package com.wwb.mkeditor.dao.ArticleDao;

import com.wwb.mkeditor.entities.Article;
import com.wwb.mkeditor.entities.mysqlentity.MysqlArticle;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * @author wwb
 * 文章数据访问层
 */
@Mapper
public interface ArticleDao {
    /**
     * #{id},#{name},#{like},#{comment},#{short},#{title}
     * 插入文章
     */
    @Insert("insert into table_article(article_id,user_name,article_like,article_comment,article_short,article_title)values(#{id},#{name},#{like},#{comment},#{short},#{title})")
    void InsertArticle(@Param("id")String id,@Param("name")String name,@Param("like")Integer like,@Param("comment")Integer comment,@Param("short")String summary,@Param("title")String title);

    /**
     * 根据文章ID来查找文章
     * @param articleId
     * @return
     */
    @Select("select * from table_article where article_id=#{articleId}")
    MysqlArticle getArticle(@Param("articleId")String articleId);

    /**
     * 添加喜欢数
     * @param params
     */
    @Update({"call addLike(#{articleId,mode=IN,jdbcType=VARCHAR})"})
    @Options(statementType = StatementType.CALLABLE)
    void addArticleLike(Map<String,String> params);

    /**
     * 获取最热
     * @param index
     * @param psize
     * @return
     */
    @Select("select * from table_article order by article_like desc limit #{index},#{psize}")
    List<MysqlArticle> topLike(@Param("index")Integer index,@Param("psize")Integer psize);

    /**
     * 获取最新
     * @param index
     * @param psize
     * @return
     */
    @Select("select * from table_article order by article_id desc limit #{index},#{psize}")
    List<MysqlArticle> topNew(@Param("index")Integer index,@Param("psize")Integer psize);
}
