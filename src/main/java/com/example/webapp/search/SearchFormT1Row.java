package com.example.webapp.search;

import java.io.Serializable;

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
public class SearchFormT1Row implements Serializable {

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
    private int price;

    /** Số lượn */
    private int quantity;

}
