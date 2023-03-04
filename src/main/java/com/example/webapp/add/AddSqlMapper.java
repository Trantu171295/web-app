package com.example.webapp.add;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddSqlMapper {

    /**
     *  カテゴリー情報取得
     * @return カテゴリー情報
     */
    public List<Map<String, String>> getCategoryCd();

    /**
     *  商品情報追加
     * @param productCd Mã sản phẩm
     * @return 商品情報
     */
    public boolean addProductInfo(@Param("form") AddForm form, @Param("tmstmp") LocalDateTime tmstmp);

    /**
     *  商品情報更新
     * @param productCd Mã sản phẩm
     * @return 商品情報
     */
    public boolean updProductInfo(@Param("form") AddForm form, @Param("updTmstmp") LocalDateTime updTmstmp);

}
