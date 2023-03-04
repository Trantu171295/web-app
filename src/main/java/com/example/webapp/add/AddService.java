package com.example.webapp.add;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddService {

    @Autowired
    AddSqlMapper sqlMapper;

    /**
     *  カテゴリー情報取得
     * @return カテゴリ情報
     */
    @Transactional
    public List<Map<String, String>> getCategoryCd() {
        return sqlMapper.getCategoryCd();
    }

    /**
     *  商品情報追加
     * @param productCd Mã sản phẩm
     * @param productNm Tên sản phẩm
     * @param categoryCd Loại mặt hàng
     * @param makerName Tên nhà sản xuất
     * @return 商品情報
     */
    @Transactional
    public boolean addProductInfo(AddForm form, LocalDateTime tmstmp) {
        return sqlMapper.addProductInfo(form, tmstmp);
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
    public boolean updProductInfo(AddForm form, LocalDateTime updTmstmp) {
        return sqlMapper.updProductInfo(form, updTmstmp);
    }
}
