package com.example.webapp.forgot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ForgotValidator implements Validator {

    @Autowired
    protected MessageSource msg;

    @Autowired
    ForgotService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return ForgotForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object Objform, Errors errors) {
        ForgotForm form = (ForgotForm) Objform;

        //SingupForm内の単項目チェックでエラーだった場合は何もしない
        if (errors.hasErrors()) {
            return;
        }

        String mail = form.getMail();

        // メールアドレスの存在チェック
        if (!checkMailExist(mail)) {
            errors.rejectValue("mail", "error-msg-009");
            return;
        }
    }

    /**
     *  メールアドレス存在チェックメソッド
     * @param mail 画面項目．メールアドレス
     * @return 存在判定結果：true 存在する、false 存在しない
     */
    public boolean checkMailExist(String mail) {

        // 判定フラグ初期化
        boolean resultFlg = false;

        if (service.getMailCnt(mail) != 0) {
            resultFlg = true;
        }

        return resultFlg;
    }
}
