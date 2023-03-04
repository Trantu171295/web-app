package com.example.webapp.add;

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

public class AddForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tên sản phẩm */
    private String productNm;

    /** Mã sản phẩm */
    private String productCd;

    /** Loại sản phẩm */
    private String categoryCd;

    /** Tên nhà sản xuất */
    private String makerName;

    /** Tên nhà sản xuất */
    private int price;

    /** Tên nhà sản xuất */
    private int quantity;

    /** Tên nhà sản xuất */
    private String insFlg;

    /** Tên nhà sản xuất */
    private String updFlg;

    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();

}
