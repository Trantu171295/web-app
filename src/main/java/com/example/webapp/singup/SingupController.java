package com.example.webapp.singup;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonController;
import com.example.webapp.login.LoginController;
import com.example.webapp.main.MainController;

@Controller
@RequestMapping("singup")
public class SingupController {
    /** 画面ID*/
    private static final String GAMEN_ID = "singup";

    /** 画面名*/
    private static final String GAMEN_NAME = "Đăng kí";

    /**
     * 遷移パラメータ
     */
    public enum RedirectAttrsKey {
        /** ニックネーム */
        NICK_NAME,
        /** 遷移元画面ID */
        SENIMOTO_GAMEN_ID
    }

    @Autowired
    SingupValidator validator;

    @Autowired
    SingupService service;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    /**
     *  初期表示の処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(method = RequestMethod.GET)
    public String init(@ModelAttribute SingupForm form, BindingResult result, Model model, RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);
        setDefault(form);
        // メイン画面から遷移する場合は管理者登録として初期表示
        if (StringUtils.equals("main", form.getSenimotoGamenId())) {
            form.setRoleId("R99999");
        }
        return "singup";
    }

    /**
     * 登録ボタンを押下の処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return
     */
    @RequestMapping(params = "torokuBtn", method = RequestMethod.POST)
    public String doTorokuBtn(@Validated @ModelAttribute SingupForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // チェックエラーがある場合自画面再表示
        if (result.hasErrors()) {
            setDefault(form);
            return "singup";
        }

        // ユーザーID設定
        form.setUserId(getUserId());
        service.insertUserInfo(form);

        // 遷移パラメータ設定
        attrs.addFlashAttribute(LoginController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(LoginController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/result";

    }

    /**
     * ログインボタンを押下の処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return
     */
    @RequestMapping(params = "backBtn", method = RequestMethod.POST)
    public String doBackBtn(@ModelAttribute SingupForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 遷移パラメータ設定
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/main";

    }

    /**
     * ログインボタンを押下の処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return
     */
    @RequestMapping(params = "loginBtn", method = RequestMethod.POST)
    public String doLoginBtn(@ModelAttribute SingupForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 画面遷移
        return "redirect:/login";

    }

    /**
     *  遷移パラメータ設定
     * @param form フォームオブジェクト
     * @param model Springが用意するViewに渡すModelオブジェクト
     */
    public void setParameter(SingupForm form, Model model) {
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }
    }

    /**
     *  デフォルト項目設定
     * @param form フォーム情報
     */
    public void setDefault(SingupForm form) {
        form.setGamenNm(GAMEN_NAME);
        form.getOriginalMap().put("cnUserKbn", CommonController.getUserKbn());
        form.setRoleId("R00001");
    }

    /**
     *  ユーザID取得
     * @return
     */
    public String getUserId() {
        boolean checkFlg = true;
        String userId = "";

        // 存在する場合は繰り返し処理
        while (checkFlg) {
            userId = createUserId(true);

            // ユーザID存在チェック
            if (checkExitsUserId(userId)) {
                checkFlg = true;
            } else {
                checkFlg = false;
            }
        }
        return userId;
    }

    /**
     * Lay thoi gian dang ki lam UserId
     * @param proFlg
     * @return ma so
     */
    public String createUserId(boolean proFlg) {
        boolean flg = proFlg;
        String timeStr = "";
        if (flg) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyymmddhhmmss");

            timeStr = format.format(timestamp);
            flg = false;
        }

        return timeStr;
    }

    /**
     *  check UserId da ton tai hay chua
     * @param userId
     * @return ket qua kiem tra.TRUE: da ton tai, FALSE: chua ton tai
     */
    public boolean checkExitsUserId(String userId) {

        boolean checkFlg = true;
        // truong UserId de trong
        if (StringUtils.isBlank(userId)) {
            checkFlg = true;
        } else {
            // UserId Da ton tai
            if (service.getUserCnt(userId) != 0) {
                checkFlg = true;
            } else {
                checkFlg = false;
            }
        }

        return checkFlg;
    }

}
