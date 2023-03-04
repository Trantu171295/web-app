package com.example.webapp.forgot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ForgotSqlMapper {

    /**
     *  メールアドレスのカウント取得
     * @param mail メールアドレス
     * @return 該当メールアドレスのレコード数
     */
    public int getMailCnt(@Param("mail") String mail);

}
