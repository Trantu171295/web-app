package com.example.webapp.main;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

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
public class MainForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 検索用ユーザID */
    private String searchUserId;

    /** 削除処理結果フラグ */
    private String delFlg;

    /** 管理者フラグ */
    private String adminFlg;

    /** アカウン状態 */
    private String accSttFlg;

    /** 明細１の行数 */
    private int t1Cnt;

    /** 更新日時 */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime updTmstmp;

    /** テーブル１ */
    @Valid
    private List<MainFormT1Row> t1 = new ArrayList<>();

    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();

}
