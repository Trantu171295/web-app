package com.example.webapp.detail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.example.webapp.CommonForm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class DetailForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tên sản phẩm */
    private String productNm;

    /** Mã sản phẩm */
    private String productCd;

    /** Loại sản phẩm */
    private String categoryCd;

    /** Tên nhà sản xuất */
    private String makerName;

    /** Giá bán */
    private String price;

    /** Số lượn */
    private int quantity;

    /** Trạng thái sản phẩm */
    private String saleFlag;

    /** Tên sản phẩm */
    private String senimaeProductNm;

    /** Mã sản phẩm */
    private String senimaeProductCd;

    /** Loại sản phẩm */
    private String senimaeCategoryCd;

    /** Tên nhà sản xuất */
    private String senimaeMakerName;

    /** 更新フラグ */
    private String updFlg;

    /** 管理者フラグ */
    private String adminFlg;

    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();

}
