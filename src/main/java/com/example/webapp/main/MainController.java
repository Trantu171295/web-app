package com.example.webapp.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonController;
import com.example.webapp.CommonService;
import com.example.webapp.access.AccessController;
import com.example.webapp.passchange.PassChangeController;
import com.example.webapp.profile.ProfileController;
import com.example.webapp.search.SearchController;
import com.example.webapp.singup.SingupController;
import com.google.gson.Gson;

@Controller
@RequestMapping("main")
public class MainController {

    @Autowired
    CommonService commonService;

    @Autowired
    MainService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "main";

    /** 画面名*/
    private static final String GAMEN_NAME = "Trang Chủ";

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
    MainValidator validator;

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
    public String init(@ModelAttribute MainForm form, BindingResult result, Model model, RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);
        // 初期設定
        setInit(form);
        form.setAccSttFlg("0");
        //  テーブル１情報取得
        getT1(form);

        return "main";
    }

    /**
     *  ユーザー検索ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "userSearchBtn", method = RequestMethod.POST)
    public String doUserSearchBtn(MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 初期設定
        setInit(form);

        //  テーブル１情報取得
        getT1(form);

        // 画面遷移
        return "main";
    }

    /**
     *  アカウン状態ラジオボタンを変更処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @PostMapping("accSttChange")
    @ResponseBody
    public String doAccSttChange(@RequestBody MainForm form, Model model) {
        List<MainFormT1Row> t1 = new ArrayList<MainFormT1Row>();

        String searchUserId = form.getSearchUserId();
        String accSttFlg = form.getAccSttFlg();

        // 登録済のユーザ情報を取得
        if (StringUtils.isNotBlank(accSttFlg)) {
            t1 = service.getUserInfo(searchUserId, accSttFlg);
            form.setT1Cnt(service.getUserInfo(searchUserId, accSttFlg).size());
        }

        Gson gson = new Gson();
        // 画面遷移
        return gson.toJson(t1);
    }

    /**
     *  新規登録ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "adminCreateBtn", method = RequestMethod.POST)
    public String doAdminCreateBtn(@ModelAttribute MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(SingupController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/singup";
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
    public String doSearchBtn(MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/search";
    }

    /**
     *  アクセスボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "accessBtn", method = RequestMethod.POST)
    public String doAccessBtn(MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(AccessController.RedirectAttrsKey.USER_ID.name(), form.getUserId());

        // 画面遷移
        return "redirect:/access";
    }

    /**
     *  Profileボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "profileBtn", method = RequestMethod.POST)
    public String doProfileBtn(@ModelAttribute MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(ProfileController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(ProfileController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/profile";
    }

    /**
     *  削除ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "delBtn", method = RequestMethod.POST)
    public String doDelBtn(@ModelAttribute MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 初期設定
        setInit(form);

        // 更新対象ユーザーリスト
        List<String> userIdList = new ArrayList<>();

        // 検索結果の明細分を繰り返す。
        for (MainFormT1Row row : form.getT1()) {
            // チェックされた明細行のユーザーIDはリストに詰める
            if (StringUtils.equals(row.getCheckBoxFlg(), "1")) {
                if (StringUtils.isNotBlank(row.getUserId())) {
                    userIdList.add(row.getUserId());
                }
            }
        }

        // DB更新
        if (userIdList.size() > 0) {
            LocalDateTime updTmstmp = LocalDateTime.now();
            if (service.delUserInfo(userIdList, updTmstmp)) {
                form.setDelFlg("1");
            } else {
                form.setDelFlg("2");
            }
        }

        // DB更新後、再表示用データを取得
        getT1(form);

        // 画面遷移
        return "main";
    }

    /**
     *  削除ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "refBtn", method = RequestMethod.POST)
    public String doRefBtn(@ModelAttribute MainForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 初期設定
        setInit(form);

        // 更新対象ユーザーリスト
        List<String> userIdList = new ArrayList<>();

        // 検索結果の明細分を繰り返す。
        for (MainFormT1Row row : form.getT1()) {
            // チェックされた明細行のユーザーIDはリストに詰める
            if (StringUtils.equals(row.getCheckBoxFlg(), "1")) {
                if (StringUtils.isNotBlank(row.getUserId())) {
                    userIdList.add(row.getUserId());
                }
            }
        }

        // DB更新
        if (userIdList.size() > 0) {
            LocalDateTime updTmstmp = LocalDateTime.now();
            if (service.refUserInfo(userIdList, updTmstmp)) {
                form.setDelFlg("1");
            } else {
                form.setDelFlg("2");
            }
        }

        // DB更新後、再表示用データを取得
        getT1(form);

        // 画面遷移
        return "main";
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
    public String doPassChangeBtn(@ModelAttribute MainForm form, BindingResult result, Model model,
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
    public String doLogoutBtn(@ModelAttribute MainForm form, BindingResult result, Model model,
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
    public void setParameter(MainForm form, Model model) {

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
    public void setInit(MainForm form) {
        // 画面項目の初期設定
        form.setGamenNm(GAMEN_NAME);
        form.setDelFlg("0");
        form.setAdminFlg(checkAdmin());
        String userId = CommonController.getUserId();
        form.setUserId(userId);
        // アカウン状態
        setCn(form);

        // 新規登録画面から遷移の場合
        //        if (StringUtils.equals("singup", form.getSenimotoGamenId())) {
        //            form.setUserId(userId);
        //        }

        // ログイン中のユーザーIDは空文字、NULLでない場合はユーザー情報取得
        if (StringUtils.isNotBlank(userId)) {
            form.setUserName(commonService.getUserName(userId));
            form.setNickName(commonService.getNickName(userId));

        }
    }

    /**
     * テーブル１の情報取得
     * @param form フォーム情報
     */
    public void getT1(MainForm form) {
        // 登録済のユーザ情報を取得
        if (StringUtils.isNotBlank(form.getAccSttFlg())) {
            List<MainFormT1Row> searchRes = service.getUserInfo(form.getSearchUserId(), form.getAccSttFlg());
            if ((Objects.nonNull(searchRes))) {
                form.setT1(searchRes);
                form.setT1Cnt(searchRes.size());
            }
        }
    }

    /**
     * ADMIN権限が持っているユーザーかをチェック
     * @return チェック結果。TRUE:持っている、FALSE:持っていない
     */
    public String checkAdmin() {
        String checkFlg = "0";

        // ログイン中のユーザーID取得
        String userId = CommonController.getUserId();

        // ユーザーIDが空文字またNULL以外場合のみ、ロールIDからAdminかを判定する
        if (StringUtils.isNotBlank(userId)) {
            if (StringUtils.equals("R99999", service.getRoleId(userId))) {
                checkFlg = "1";
            }
        }

        // チェック結果を返却
        return checkFlg;
    }

    /**
     *  各コンディションの値を設定
     * @param form
     */
    public void setCn(MainForm form) {
        // アカウン状態
        form.getOriginalMap().put("cnAccStt", CommonController.getAccountStatus());
    }

}
