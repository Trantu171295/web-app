package com.example.webapp.singup;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class SingupForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** メールアドレス */
    @NotBlank
    @Email
    private String mail;

    /** ユーザーID */
    private String userId;

    /** ロールID */
    private String roleId;

    /** ユーザー氏名 */
    @NotBlank
    private String userName;

    /** ニックネーム */
    private String nickName;

    /** 電話番号 */
    private String phoneNumber;

    /** パスワード */
    @NotBlank
    private String pass;

    /** パスワード（確認用） */
    private String pass2;

    /** パスワード忘れボタン制御フラグ */
    private String btnShowFlg;

    /** 遷移元画面ID */
    private String senimotoGamenId;

    /** 画面名 */
    private String gamenNm;

    /** Thoi gian dang ki */
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime regstTmstmp;

    /** Thoi gian cap nhat */
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime updTmstmp;

    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();

}
