package com.example.webapp.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonService;
import com.example.webapp.login.LoginController;

@Controller
@RequestMapping("result")
public class ResultController {
    @Autowired
    CommonService commonService;

    /** 画面ID*/
    private static final String GAMEN_ID = "result";

    /** 画面名*/
    private static final String GAMEN_NAME = "Thông báo";

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
    ResultValidator validator;

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
    @RequestMapping( method = RequestMethod.GET)
    public String init(@ModelAttribute ResultForm form, BindingResult result, Model model, RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);
        form.setGamenNm(GAMEN_NAME);
        form.setNickName(commonService.getNickName(form.getUserId()));

        return "result";
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
    public String doLoginBtn(@ModelAttribute ResultForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 遷移パラメータ設定
        attrs.addFlashAttribute(LoginController.RedirectAttrsKey.USER_ID.name(), form.getUserId());

        // 画面遷移
        return "redirect:/login";

    }

    /**
     *  遷移パラメータ設定
     * @param form フォームオブジェクト
     * @param model Springが用意するViewに渡すModelオブジェクト
     */
    public void setParameter(ResultForm form, Model model) {

        // ユーザーID
        if (model.asMap().containsKey(RedirectAttrsKey.USER_ID.name())) {
            form.setUserId((String) model.asMap().get(RedirectAttrsKey.USER_ID.name()));
        }

        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }
    }
}
