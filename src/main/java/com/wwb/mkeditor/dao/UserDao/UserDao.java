package com.wwb.mkeditor.dao.UserDao;

import com.wwb.mkeditor.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from table_user where user_name=#{userName}")
    User getUser(@Param("userName")String userName);
    @Insert("insert into table_user(user_name,user_password,user_id)values(#{userName},#{passWord},#{user_id})")
    void addUser(@Param("userName")String userName,@Param("passWord")String passWord,@Param("user_id")String userId);
}
