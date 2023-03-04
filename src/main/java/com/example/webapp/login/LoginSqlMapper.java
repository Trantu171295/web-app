package com.example.webapp.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginSqlMapper {

    public LoginForm selectUserInfo(@Param("userId") String userId);

}
