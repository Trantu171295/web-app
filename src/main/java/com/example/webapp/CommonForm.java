package com.example.webapp;

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
public class CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザーID */
    private String userId;

    /** ユーザー氏名 */
    private String userName;

    /** ニックネーム */
    private String nickName;

    /** 遷移元画面ID */
    private String senimotoGamenId;

    /** 画面名 */
    private String gamenNm;
}
