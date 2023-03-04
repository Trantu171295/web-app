package com.example.webapp.passchange;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassChangeService {

    @Autowired
    PassChangeSqlMapper sqlMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     *  パスワード更新
     * @param form フォーム情報
     * @return 更新結果
     */
    @Transactional
    public boolean updPass(PassChangeForm form) {
        // パスワードハッシュ化
        form.setNewPass(passwordEncoder.encode(form.getNewPass()));
        LocalDateTime time = LocalDateTime.now();
        form.setUpdTmstmp(time);
        return sqlMapper.updPass(form);
    }

}
