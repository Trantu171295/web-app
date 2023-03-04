package com.example.webapp.singup;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SingupSqlMapper {

    /**
     *  ユーザ登録処理
     * @param form フォーム情報
     */
    public void insertUserInfo(@Param("form") SingupForm form);

    /**
     *  ユーザIDのカウント取得
     * @param userId ユーザID
     * @return 該当ユーザIDのレコード数
     *
     */
    public int getUserCnt(@Param("userId") String userId);

    /**
     *  メールアドレスのカウント取得
     * @param mail メールアドレス
     * @return 該当メールアドレスのレコード数
     */
    public int getMailCnt(@Param("mail") String mail);

    /**
     *  電話番号のカウント取得
     * @param num 電話番号
     * @return 該当電話番号のレコード数
     */
    public int getPhoneCnt(@Param("num") String num);

}
