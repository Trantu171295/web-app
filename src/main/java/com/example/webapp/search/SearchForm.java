package com.example.webapp.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

public class SearchForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tên sản phẩm */
    private String productNm;

    /** Mã sản phẩm */
    private String productCd;

    /** Loại sản phẩm */
    private String categoryCd;

    /** Tên nhà sản xuất */
    private String makerName;

    /** Mã sản phẩm xem chi tiết */
    private String productCdSubmit;

    /** Mã sản phẩm xem chi tiết */
    private String categoryCdSubmit;

    /** Mã sản phẩm xem chi tiết */
    private String adminFlg;

    /** テーブル１ */
    @Valid
    private List<SearchFormT1Row> t1 = new ArrayList<>();

    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();
}
