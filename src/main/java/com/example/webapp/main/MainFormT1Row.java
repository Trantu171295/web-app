package com.example.webapp.main;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

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
public class MainFormT1Row implements Serializable {

    private static final long serialVersionUID = 1L;

    /** チェックボックス選択フラグ */
    private String checkBoxFlg;

    /** ユーザーID */
    private String userId;

    /** メールアドレス */
    private String mail;

    /** ユーザー氏名 */
    private String userName;

    /** ニックネーム */
    private String nickName;

    /** 電話番号 */
    private String phoneNumber;

    /** Thoi gian dang ki */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime regstTmstmp;

    /** Thoi gian cap nhat */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime updTmstmp;

}
