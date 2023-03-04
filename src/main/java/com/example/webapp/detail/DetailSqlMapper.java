package com.example.webapp.detail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DetailSqlMapper {

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

    /**
     *  商品情報取得
     * @param productCd Mã sản phẩm
     * @return 商品情報
     */
    public DetailForm getProductInfo(@Param("productCd") String productCd);

    /**
     *  商品情報更新
     * @param productCd Mã sản phẩm
     * @return 商品情報
     */
    public boolean updProductInfo(@Param("form") DetailForm form, @Param("updTmstmp") LocalDateTime updTmstmp);

}
