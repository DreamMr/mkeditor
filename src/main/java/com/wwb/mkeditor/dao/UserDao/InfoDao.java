package com.wwb.mkeditor.dao.UserDao;

import com.wwb.mkeditor.entities.Info;
import org.apache.ibatis.annotations.*;

/**
 * 用户个人信息表映射
 * @author wwb
 */
@Mapper
public interface InfoDao {
    @Insert("insert into table_info(user_name,info_address,info_hobby,info_img)values(#{userName},#{address},#{hobby},#{img})")
    void insertInfo(@Param("userName")String userName,@Param("address")String address,@Param("hobby")String hobby,@Param("img")String img);

    @Select("select * from table_info where user_name=#{userName}")
    Info getInfo(@Param("userName")String userName);

    @Select("select info_img from table_info where user_name=#{userName}")
    String getInfoImg(@Param("userName")String userName);

    @Update("update table_info set info_address=#{address},info_hobby=#{hobby},info_img=#{img} where user_name=#{userName}")
    void updateInfoWithImg(@Param("address")String address,@Param("hobby")String hobby,@Param("userName")String userName,@Param("img")String img);

    @Update("update table_info set info_address=#{address},info_hobby=#{hobby} where user_name=#{userName}")
    void updateInfo(@Param("address")String address,@Param("hobby")String hobby,@Param("userName")String userName);
}
