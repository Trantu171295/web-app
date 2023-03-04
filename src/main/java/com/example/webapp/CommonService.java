package com.example.webapp;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonService {

    @Autowired
    CommonSqlMapper sqlMapper;

    /**
     *  ニックネーム取得
     * @param userId ユーザID
     * @return ニックネーム
     */
    @Transactional
    public String getNickName(String userId) {
        String nickname = "";
        if (StringUtils.isNotBlank(userId)) {
            nickname = sqlMapper.getNickName(userId);
        }
        return nickname;
    }

    /**
     *  ユーザ氏名取得
     * @param userId ユーザID
     * @return ユーザ氏名
     */
    @Transactional
    public String getUserName(String userId) {
        String userName = "";
        if (StringUtils.isNotBlank(userId)) {
            userName = sqlMapper.getUserName(userId);
        }
        return userName;
    }

    /**
     *  ロールID取得
     * @param userId ユーザID
     * @return ロールＩＤ
     */
    @Transactional
    public String getRoleId(String userId) {
        String roleId = "R00001";
        if (StringUtils.isNotBlank(userId)) {
            roleId = sqlMapper.getRoleId(userId);
        }
        return roleId;
    }

    /**
     *  カテゴリー情報取得
     * @return カテゴリ情報
     */
    @Transactional
    public List<Map<String, String>> getCategoryCd() {
        return sqlMapper.getCategoryCd();
    }

}
