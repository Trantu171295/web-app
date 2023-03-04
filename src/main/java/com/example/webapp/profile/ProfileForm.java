package com.example.webapp.profile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
public class ProfileForm extends CommonForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Role Id */
    private String roleId;

    /** Địa chỉ Email */
    private String mail;

    /** Số điện thoại */
    private String phoneNumber;

    /** Ngày đăng kí */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime regstTmstmp;

    /** Lần cập nhật cuối cùng */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss SSS")
    private LocalDateTime updTmstmp;

    /** Original Map */
    Map<String, Object> originalMap = new HashMap<>();

}
