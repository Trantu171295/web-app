package com.example.webapp.main;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MainService {

    @Autowired
    MainSqlMapper sqlMapper;

    /**
     *  ユーザ情報取得
     * @param userId ユーザID
     * @return ユーザ情報
     */
    @Transactional
    public List<MainFormT1Row> getUserInfo(String searchUserId, String accSttFlg) {
        return sqlMapper.getUserInfo(searchUserId, accSttFlg);
    }

    /**
     *  ユーザ情報削除
     * @param userId ユーザID
     * @return 削除結果
     */
    @Transactional
    public boolean delUserInfo(List<String> userIdList, LocalDateTime updTmstmp) {
        return sqlMapper.delUserInfo(userIdList, updTmstmp);
    }


    /**
     *  ユーザ情報復元
     * @param userId ユーザID
     * @return 削除結果
     */
    @Transactional
    public boolean refUserInfo(List<String> userIdList, LocalDateTime updTmstmp) {
        return sqlMapper.refUserInfo(userIdList, updTmstmp);
    }

    /**
     *  ロールID取得
     * @param userId ユーザID
     * @return ロールＩＤ
     */
    @Transactional
    public String getRoleId(String userId) {
        return sqlMapper.getRoleId(userId);
    }

}
