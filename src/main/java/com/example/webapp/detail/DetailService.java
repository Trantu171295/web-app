package com.example.webapp.detail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailService {
    @Autowired
    DetailSqlMapper sqlMapper;

    /**
     *  ロールID取得
     * @param userId ユーザID
     * @return ロールＩＤ
     */
    @Transactional
    public String getRoleId(String userId) {
        String roleId = "R00001";
        if (StringUtils.isNotBlank(userId)) {
            roleId = sqlMapper.getRoleId(userId);
        }
        return roleId;
    }


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
    public DetailForm getProductInfo(String productCd) {
        return sqlMapper.getProductInfo(productCd);
    }

    /**
     *  商品情報更新
     * @param productCd Mã sản phẩm
     * @param productNm Tên sản phẩm
     * @param categoryCd Loại mặt hàng
     * @param makerName Tên nhà sản xuất
     * @return 商品情報
     */
    @Transactional
    public boolean updProductInfo(DetailForm form, LocalDateTime updTmstmp) {
        return sqlMapper.updProductInfo(form, updTmstmp);
    }
}
