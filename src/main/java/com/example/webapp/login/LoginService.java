package com.example.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    //implements UserDetailsService {

    /** SQL Mapper */
    @Autowired
    LoginSqlMapper sqlMapper;

    @Transactional
    public LoginForm selectUserInfo(String userId) {
        return sqlMapper.selectUserInfo(userId);
    }

}
