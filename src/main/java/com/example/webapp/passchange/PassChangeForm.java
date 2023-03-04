package com.example.webapp.passchange;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PassChangeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザーID */
    @NotBlank
    private String userId;

    /** 新しいパスワード */
    @NotBlank
    private String newPass;

    /** 新しいパスワード（確認用） */
    private String newPass2;

    /** 遷移元画面ID */
    private String senimotoGamenId;

    /** 画面名 */
    private String gamenNm;

    /** ニックネーム */
    private String nickName;

    /** Thoi gian dang ki */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime regstTmstmp;

    /** Thoi gian cap nhat */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime updTmstmp;

}