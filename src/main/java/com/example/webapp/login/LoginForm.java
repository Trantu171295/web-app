package com.example.webapp.login;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
public class LoginForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザーID */
    @NotBlank
    private String userId;

    @NotBlank
    /** パスワード */
    private String pass;

    /** ニックネーム */
    private String nickName;

    /** 遷移元画面ID */
    private String senimotoGamenId;

    /** 画面名 */
    private String gamenNm;

}
