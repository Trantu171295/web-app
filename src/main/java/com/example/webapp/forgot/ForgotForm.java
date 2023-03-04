package com.example.webapp.forgot;

import java.io.Serializable;

import javax.validation.constraints.Email;
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
public class ForgotForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** メールアドレス */
    @NotBlank
    @Email
    private String mail;

    /** 遷移元画面ID */
    private String senimotoGamenId;

    /** 画面名 */
    private String gamenNm;

    /** ニックネーム */
    private String nickName;

}
