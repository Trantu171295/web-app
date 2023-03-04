package com.example.webapp.search;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchService {

    @Autowired
    SearchSqlMapper sqlMapper;

    /**
     *  ロールID取得
     * @param userId ユーザID
     * @return ロールＩＤ
     */
//    @Transactional
//    public String getRoleId(String userId) {
//        String roleId = "R00001";
//        if (StringUtils.isNotBlank(userId)) {
//            roleId = sqlMapper.getRoleId(userId);
//        }
//        return roleId;
//    }

    /**
     *  カテゴリー情報取得
     * @return カテゴリ情報
     */
    @Transactional
    public List<Map<String, String>> getCategoryCd() {
        return sqlMapper.getCategoryCd();
    }

    /**
     *  商品情報取得
     * @param productCd Mã sản phẩm
     * @param productNm Tên sản phẩm
     * @param categoryCd Loại mặt hàng
     * @param makerName Tên nhà sản xuất
     * @return 商品情報
     */
    @Transactional
    public List<SearchFormT1Row> getProductInfo(String productCd, String productNm, String categoryCd,
            String makerName) {
        return sqlMapper.getProductInfo(productCd, productNm, categoryCd, makerName);
    }

}
