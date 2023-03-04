package com.example.webapp.contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonController;
import com.example.webapp.CommonService;
import com.example.webapp.main.MainController;
import com.example.webapp.passchange.PassChangeController;

@Controller
@RequestMapping("contact")
public class ContactController {

    @Autowired
    CommonService commonService;

    @Autowired
    ContactService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "contact";

    /** 画面名*/
    private static final String GAMEN_NAME = "CONTACT画面";

    /**
     * 遷移パラメータ
     */
    public enum RedirectAttrsKey {
        /** ユーザーID */
        USER_ID,
        /** 遷移元画面ID */
        SENIMOTO_GAMEN_ID

    }

    @Autowired
    ContactValidator validator;

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
    public String init(@ModelAttribute ContactForm form, BindingResult result, Model model, RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);

        // 初期設定
        setInit(form);

        return "contact";
    }

    /**
     *  HOMEボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "homeBtn", method = RequestMethod.POST)
    public String doHomeBtn(@ModelAttribute ContactForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/main";
    }

    /**
     *  パスワード変更ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "passChangeBtn", method = RequestMethod.POST)
    public String doPassChangeBtn(@ModelAttribute ContactForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(PassChangeController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(PassChangeController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/passchange";
    }

    /**
     *  ログアウトボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "logoutBtn", method = RequestMethod.POST)
    public String doLogoutBtn(@ModelAttribute ContactForm form, BindingResult result, Model model,
            RedirectAttributes attrs, HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/logout";
    }

    /**
     *  遷移パラメータ設定
     * @param form フォームオブジェクト
     * @param model Springが用意するViewに渡すModelオブジェクト
     */
    public void setParameter(ContactForm form, Model model) {

        // ユーザーID
        if (model.asMap().containsKey(RedirectAttrsKey.USER_ID.name())) {
            form.setUserId((String) model.asMap().get(RedirectAttrsKey.USER_ID.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }

    }

    /**
     *  初期表示設定
     * @param form フォーム情報
     */
    public void setInit(ContactForm form) {
        // 画面項目の初期設定
        form.setGamenNm(GAMEN_NAME);
        String userId = CommonController.getUserId();

        // ログイン中のユーザーIDは空文字、NULLでない場合はユーザー情報取得
        if (StringUtils.isNotBlank(userId)) {
            form.setUserName(commonService.getUserName(userId));
            form.setNickName(commonService.getNickName(userId));

        }
    }

}
