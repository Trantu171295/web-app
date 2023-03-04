package com.example.webapp.forgot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ForgotService {

    @Autowired
    ForgotSqlMapper sqlMapper;

    @Transactional
    public int getMailCnt(String mail) {
        int cnt = sqlMapper.getMailCnt(mail);
        return cnt;
    }

}
