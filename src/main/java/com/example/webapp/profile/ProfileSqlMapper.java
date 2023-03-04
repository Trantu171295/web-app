package com.example.webapp.profile;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileSqlMapper {

    /**
     *  ユーザ情報取得
     * @param userId ユーザID
     * @return 登録済のユーザ情報
     */
    public List<ProfileShowForm> selectUserInfo(@Param("userId") String userId);

    /**
     *  ユーザ情報更新
     * @param form フォーム情報
     * @return 更新結果
     */
    public boolean updUserInfo(@Param("form") ProfileForm form, @Param("updTmstmp") LocalDateTime updTmstmp);

}
