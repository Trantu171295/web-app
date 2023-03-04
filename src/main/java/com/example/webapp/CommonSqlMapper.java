package com.example.webapp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonSqlMapper {

    /**
     *  ニックネーム取得
     * @param userId ユーザID
     * @return ニックネーム
     *
     */
    public String getNickName(@Param("userId") String userId);

    /**
     *  ユーザ氏名取得
     * @param userId ユーザID
     * @return ユーザ氏名
     *
     */
    public String getUserName(@Param("userId") String userId);

    /**
     * ロールID取得
     * @param userId ユーザID
     * @return ロールID
     *
     */
    public String getRoleId(@Param("userId") String userId);

    /**
     *  カテゴリー情報取得
     * @return カテゴリー情報
     */
    public List<Map<String, String>> getCategoryCd();

}
