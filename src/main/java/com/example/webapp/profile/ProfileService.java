package com.example.webapp.profile;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    @Autowired
    ProfileSqlMapper sqlMapper;

    /**
     *  ユーザ情報取得
     * @param userId ユーザID
     * @return ユーザ情報
     */
    @Transactional
    public List<ProfileShowForm> getUserInfo(String userId) {
        return sqlMapper.selectUserInfo(userId);
    }


    /**
     *  ユーザ情報更新
     * @param form フォーム情報
     * @return 更新結果
     */
    @Transactional
    public boolean updUserInfo(ProfileForm form, LocalDateTime updTmstmp) {
        return sqlMapper.updUserInfo(form, updTmstmp);
    }

}
