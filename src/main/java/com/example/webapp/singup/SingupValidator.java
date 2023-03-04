package com.example.webapp.singup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SingupValidator implements Validator {

    @Autowired
    protected MessageSource msg;

    @Autowired
    SingupService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return SingupForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object Objform, Errors errors) {
        //SingupForm内の単項目チェックでエラーだった場合は何もしない
        if (errors.hasErrors()) {
            return;
        }

        SingupForm form = (SingupForm) Objform;
        // メールアドレス
        String mail = form.getMail();
        // パスワード
        String pass = form.getPass();
        // パスワード（確認用）
        String pass2 = form.getPass2();
        // 電話番号
        String phoneNum = form.getPhoneNumber();

        form.setBtnShowFlg("0");

        // 登録可否判定
        if (StringUtils.isNotEmpty(mail)) {
            if (checkMail(mail)) {
                form.setBtnShowFlg("1");
                errors.rejectValue("mail", "error-msg-003");
                return;
            }
        }
        // 電話番号形式チェック
        if (!checkPhoneNumber(phoneNum)) {
            errors.rejectValue("phoneNumber", "error-msg-004");
            return;
        } else {
            if (checkPhoneNumberExits(phoneNum)) {
                errors.rejectValue("phoneNumber", "error-msg-008");
                return;
            }
        }

        // パスワード入力チェック
        if (StringUtils.isEmpty(pass2)) {
            errors.rejectValue("pass2", "error-msg-005");
            return;
        } else if (!Objects.equals(pass, pass2)) {
            errors.rejectValue("pass2", "error-msg-006");
            return;
        }
    }

    /**
     *  電話番号形式チェックメソッド
     * @param phoneNumber 画面項目．電話番号
     * @return 形式判定結果：true 正しい電話番号、false 誤り電話番号
     */
    public boolean checkPhoneNumber(String phoneNumber) {

        // 判定フラグ初期化
        boolean resultFlg = false;

        // 電話番号が空文字列の場合はチェックしないで、falseを返す。
        if (StringUtils.isNotEmpty(phoneNumber)) {
            // 電話番号パータンリスト作成
            List<String> patternList = new ArrayList<String>();
            patternList.add("^0\\d-\\d{4}-\\d{4}$");
            patternList.add("^\\(0\\d\\)\\d{4}-\\d{4}$");
            patternList.add("^(070|080|090)-\\d{4}-\\d{4}$");
            patternList.add("^050-\\d{4}-\\d{4}$");
            patternList.add("^0120-\\d{3}-\\d{3}$");

            // 入力された電話番号をチェック
            for (String patternStr : patternList) {
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(phoneNumber);
                if (matcher.find()) {
                    resultFlg = true;
                    break;
                }
            }
        }
        return resultFlg;
    }

    /**
     *  メールアドレス存在チェックメソッド
     * @param mail 画面項目．メールアドレス
     * @return 存在判定結果：true 存在する、false 存在しない
     */
    public boolean checkMail(String mail) {

        // 判定フラグ初期化
        boolean resultFlg = false;

        if (service.getMailCnt(mail) != 0) {
            resultFlg = true;
        }

        return resultFlg;
    }

    public boolean checkPhoneNumberExits(String num) {
        boolean relFlg = false;

        if (service.getPhoneCnt(num) != 0) {
            relFlg = true;
        }

        return relFlg;
    }

}
