package com.example.webapp.main;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MainSqlMapper {
    /**
     *  登録済のユーザ情報取得
     * @param userId ユーザID
     * @param accSttFlg アカウン状態
     * @return 登録済のユーザ情報
     */
    public List<MainFormT1Row> getUserInfo(@Param("searchUserId") String searchUserId, @Param("accSttFlg") String accSttFlg);

    /**
     *  ユーザ情報削除
     * @param userId ユーザID
     * @return 削除結果
     */
    public boolean delUserInfo(@Param("userIdList") List<String> userIdList,
            @Param("updTmstmp") LocalDateTime updTmstmp);


    /**
     *  ユーザ情報復元
     * @param userId ユーザID
     * @return 削除結果
     */
    public boolean refUserInfo(@Param("userIdList") List<String> userIdList,
            @Param("updTmstmp") LocalDateTime updTmstmp);

    /**
     *  ロールID報取得
     * @param userId ユーザID
     * @return ロールＩＤ
     *
     */
    public String getRoleId(@Param("userId") String userId);

}
