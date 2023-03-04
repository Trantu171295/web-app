package com.example.webapp.passchange;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonController;
import com.example.webapp.result.ResultController;

@Controller
@RequestMapping("passchange")
public class PassChangeController {

    @Autowired
    PassChangeService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "passchange";

    /** 画面名*/
    private static final String GAMEN_NAME = "Đổi mật khẩu";

    /**
     * 遷移パラメータ
     */
    public enum RedirectAttrsKey {
        /** ユーザーコード */
        USER_ID,
        /** 遷移元画面ID */
        SENIMOTO_GAMEN_ID
    }

    @Autowired
    PassChangeValidator validator;

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
    public String init(@ModelAttribute PassChangeForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);
        form.setUserId(CommonController.getUserId());
        form.setGamenNm(GAMEN_NAME);
        return "passchange";
    }

    /**
     *  変更登録を押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "changeBtn", method = RequestMethod.POST)
    public String doChangeBtnBtn(@Validated PassChangeForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        form.setGamenNm(GAMEN_NAME);

        // チェックエラーがある場合自画面再表示
        if (result.hasErrors()) {
            return "passchange";
        }

        // 正常に更新できた場合。
        if (service.updPass(form)) {
            // 遷移パラメータ設定
            attrs.addFlashAttribute(ResultController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
            attrs.addFlashAttribute(ResultController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

            // 画面遷移
            return "redirect:/result";
            // 上記以外
        } else {
            return "passchange";

        }
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
    public String doLogoutBtn(@ModelAttribute PassChangeForm form, BindingResult result, Model model,
            RedirectAttributes attrs, HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/logout";
    }

    /**
     *  戻るボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "modoruBtn", method = RequestMethod.POST)
    public String doModoruBtn(@ModelAttribute PassChangeForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 画面遷移
        if (StringUtils.isNotBlank(form.getSenimotoGamenId())) {
            return "redirect:/" + form.getSenimotoGamenId();
        } else {
            return "redirect:/login";
        }
    }

    /**
     *  遷移パラメータ設定
     * @param form フォームオブジェクト
     * @param model Springが用意するViewに渡すModelオブジェクト
     */
    public void setParameter(PassChangeForm form, Model model) {

        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }

    }
}
