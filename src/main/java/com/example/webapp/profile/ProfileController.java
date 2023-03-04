package com.example.webapp.profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonController;
import com.example.webapp.CommonService;
import com.example.webapp.access.AccessController;
import com.example.webapp.main.MainController;
import com.example.webapp.passchange.PassChangeController;
import com.example.webapp.result.ResultController;
import com.example.webapp.search.SearchController;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    CommonService commonService;

    @Autowired
    ProfileService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "profile";

    /** 画面名*/
    private static final String GAMEN_NAME = "Trang cá nhân";

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
    ProfileValidator validator;

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
    public String init(@ModelAttribute ProfileForm form, BindingResult result, Model model, RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);

        // 初期設定
        setInit(form);

        return "profile";
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
    public String doHomeBtn(@ModelAttribute ProfileForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/main";
    }

    /**
     *  検索ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "searchBtn", method = RequestMethod.POST)
    public String doSearchBtn(ProfileForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.USER_ID.name(), form.getUserId());

        // 画面遷移
        return "redirect:/search";
    }

    /**
     *  Profileボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "accessBtn", method = RequestMethod.POST)
    public String doAccessBtn(@ModelAttribute ProfileForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(AccessController.RedirectAttrsKey.USER_ID.name(), form.getUserId());

        // 画面遷移
        return "redirect:/access";
    }

    /**
     *  戻るボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "backBtn", method = RequestMethod.POST)
    public String doBackBtn(@ModelAttribute ProfileForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 画面遷移
        if (StringUtils.isNotBlank(form.getSenimotoGamenId())) {
            return "redirect:/" + form.getSenimotoGamenId();
        } else {
            return "redirect:/login";
        }
    }

    /**
     *  Profileボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "submitBtn", method = RequestMethod.POST)
    public String doEditBtn(@ModelAttribute ProfileForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        LocalDateTime updTmstmp = LocalDateTime.now();
        try {
            service.updUserInfo(form, updTmstmp);
            // 遷移パラメータ設定
            attrs.addFlashAttribute(ResultController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
            attrs.addFlashAttribute(ResultController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);
            return "redirect:/result";
        } catch (Exception e) {
            System.out.println("Exceptionが発生しました...");
            // エラー・メッセージを出力
            System.out.print(e.toString());
            return "profile";
        }

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
    public String doPassChangeBtn(@ModelAttribute ProfileForm form, BindingResult result, Model model,
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
    public String doLogoutBtn(@ModelAttribute ProfileForm form, BindingResult result, Model model,
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
    public void setParameter(ProfileForm form, Model model) {
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
    public void setInit(ProfileForm form) {
        // 画面項目の初期設定
        form.setGamenNm(GAMEN_NAME);
        String userId = CommonController.getUserId();
        List<ProfileShowForm> getInfoList = new ArrayList<>();
        form.getOriginalMap().put("cnUserKbn", CommonController.getUserKbn());

        // ログイン中のユーザーIDは空文字、NULLでない場合はユーザー情報取得
        if (StringUtils.isNotBlank(userId)) {
            form.setUserName(commonService.getUserName(userId));
            form.setNickName(commonService.getNickName(userId));

            // ユーザー情報取得
            getInfoList = service.getUserInfo(userId);

        }

        // ユーザー情報を取得できる場合
        if (!CollectionUtils.isEmpty(getInfoList)) {
            form.setRoleId(getInfoList.get(0).getRoleId());
            form.setUserId(getInfoList.get(0).getUserId());
            form.setUserName(getInfoList.get(0).getUserName());
            form.setNickName(getInfoList.get(0).getNickName());
            form.setMail(getInfoList.get(0).getMail());
            form.setPhoneNumber(getInfoList.get(0).getPhoneNumber());
            form.setRegstTmstmp(getInfoList.get(0).getRegstTmstmp());
            form.setUpdTmstmp(getInfoList.get(0).getUpdTmstmp());
        }

    }

}
