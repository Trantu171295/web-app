package com.example.webapp.add;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.example.webapp.CommonController;
import com.example.webapp.CommonService;
import com.example.webapp.search.SearchController;

@Controller
@RequestMapping("add")
public class AddController {

    @Autowired
    CommonService commonService;

    @Autowired
    AddService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "add";

    /** 画面名*/
    private static final String GAMEN_NAME = "Thêm sản phẩm mới";

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
    AddValidator validator;

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
    public String init(@ModelAttribute AddForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);

        // 初期設定
        setInit(form);

        return "add";
    }

    /**
     *  削除ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "insBtn", method = RequestMethod.POST)
    public String doInsBtn(@ModelAttribute AddForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // DB更新
        LocalDateTime tmstmp = LocalDateTime.now();
        if (service.addProductInfo(form, tmstmp)) {
            form.setInsFlg("1");
        } else {
            form.setInsFlg("0");
        }

        setInit(form);

        // 画面再表示
        return "add";
    }

    /**
     *  更新ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "editBtn", method = RequestMethod.POST)
    public String doEditBtn(@ModelAttribute AddForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // DB更新
        LocalDateTime updTmstmp = LocalDateTime.now();
        if (service.updProductInfo(form, updTmstmp)) {
            form.setUpdFlg("1");
        } else {
            form.setUpdFlg("0");
        }

        setInit(form);

        // 画面再表示
        return "add";
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
    public String doBackBtn(@ModelAttribute AddForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 画面遷移のパラメータ設定
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        return "redirect:/search";

    }

    /**
     *  遷移パラメータ設定
     * @param form フォームオブジェクト
     * @param model Springが用意するViewに渡すModelオブジェクト
     */
    public void setParameter(AddForm form, Model model) {

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
    public void setInit(AddForm form) {
        // 画面項目の初期設定
        form.setGamenNm(GAMEN_NAME);
        String userId = CommonController.getUserId();

        // カテゴリー
        setCategoryCn(form);

        // ログイン中のユーザーIDは空文字、NULLでない場合はユーザー情報取得
        if (StringUtils.isNotBlank(userId)) {
            form.setUserName(commonService.getUserName(userId));
            form.setNickName(commonService.getNickName(userId));
        }
    }

    /**
     *  カテゴリードロップダウンリスト作成
     * @param form
     */
    public void setCategoryCn(AddForm form) {
        List<Map<String, String>> categoryCdList = new ArrayList<Map<String, String>>();

        categoryCdList = service.getCategoryCd();
        Map<String, String> categoryCn = new HashMap<>();
        categoryCn.put("000", "Vui lòng chọn loại sản phẩm");

        for (Map<String, String> category : categoryCdList) {
            categoryCn.put(category.get("CATEGORY_CD"), category.get("CATEGORY_NM"));
        }

        form.getOriginalMap().put("cnCategoryCd", categoryCn);
    }

}
