package com.example.webapp.forgot;

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

import com.example.webapp.result.ResultController;

@Controller
@RequestMapping("forgot")
public class ForgotController {

    /** 画面ID*/
    private static final String GAMEN_ID = "forgot";

    /** 画面名*/
    private static final String GAMEN_NAME = "Quên mật khẩu";

    /**
     * 遷移パラメータ
     */
    public enum RedirectAttrsKey {
        /** 遷移元画面ID */
        SENIMOTO_GAMEN_ID

    }

    @Autowired
    ForgotValidator validator;

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
    public String init(@ModelAttribute ForgotForm form, BindingResult result, Model model, RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);
        form.setGamenNm(GAMEN_NAME);
        return "forgot";
    }

    /**
     * 登録ボタンを押下の処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return
     */
    @RequestMapping(params = "resetBtn", method = RequestMethod.POST)
    public String doResetBtn(@Validated @ModelAttribute ForgotForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // チェックエラーがある場合自画面再表示
        if (result.hasErrors()) {
            form.setGamenNm(GAMEN_NAME);
            return "forgot";
        }

//        Account account = application.sendPasswordResetEmail("john.smith@example.com");
//
        // 遷移パラメータ設定
        attrs.addFlashAttribute(ResultController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/result";

    }

    /**
     * 登録ボタンを押下の処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return
     */
    @RequestMapping(params = "backBtn", method = RequestMethod.POST)
    public String doBackBtn(@Validated @ModelAttribute ForgotForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 画面遷移
        return "redirect:/login";

    }

    /**
     *  遷移パラメータ設定
     * @param form フォームオブジェクト
     * @param model Springが用意するViewに渡すModelオブジェクト
     */
    public void setParameter(ForgotForm form, Model model) {
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }
    }

}
