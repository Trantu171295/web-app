package com.example.webapp.passchange;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PassChangeSqlMapper {
    /**
     *  パスワード更新
     * @param form フォーム情報
     * @return 更新したレコード数
     */
    public boolean updPass(@Param("form") PassChangeForm form);

}