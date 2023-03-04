package com.example.webapp.search;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchSqlMapper {

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
    * @param productNm Tên sản phẩm
    * @param categoryCd Loại mặt hàng
    * @param makerName Tên nhà sản xuất
    * @return 商品情報
    */
    public List<SearchFormT1Row> getProductInfo(@Param("productCd") String productCd,
            @Param("productNm") String productNm, @Param("categoryCd") String categoryCd,
            @Param("makerName") String makerName);

}
