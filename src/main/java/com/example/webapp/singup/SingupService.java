package com.example.webapp.singup;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingupService {

    @Autowired
    SingupSqlMapper sqlMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void insertUserInfo(SingupForm form) {
        // 登録日時、更新日時設定
        LocalDateTime time = LocalDateTime.now();
        form.setRegstTmstmp(time);
        form.setUpdTmstmp(time);
        // パスワードハッシュ化
        form.setPass(passwordEncoder.encode(form.getPass()));
        sqlMapper.insertUserInfo(form);
    }

    @Transactional
    public int getUserCnt(String userId) {
        int cnt = sqlMapper.getUserCnt(userId);
        return cnt;
    }

    @Transactional
    public int getMailCnt(String mail) {
        int cnt = sqlMapper.getMailCnt(mail);
        return cnt;
    }

    @Transactional
    public int getPhoneCnt(String num) {
        int cnt = sqlMapper.getPhoneCnt(num);
        return cnt;
    }

}
