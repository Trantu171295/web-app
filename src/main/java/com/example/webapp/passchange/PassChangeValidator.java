package com.example.webapp.passchange;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PassChangeValidator implements Validator {

    @Autowired
    protected MessageSource msg;


    @Override
    public boolean supports(Class<?> clazz) {
        return PassChangeForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object Objform, Errors errors) {
        PassChangeForm form = (PassChangeForm) Objform;

        //SingupForm内の単項目チェックでエラーだった場合は何もしない
        if (errors.hasErrors()) {
            return;
        }

        String newPass = form.getNewPass();
        String newPass2 = form.getNewPass2();

        // パスワード入力チェック
        if (!StringUtils.equals(newPass, newPass2)) {
            errors.rejectValue("newPass2", "error-msg-006");
            return;
        }

    }

}
