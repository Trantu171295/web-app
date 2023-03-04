package com.example.webapp.detail;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.CommonController;
import com.example.webapp.CommonService;
import com.example.webapp.search.SearchController;

@Controller
@RequestMapping("detail")
public class DetailController {

    @Autowired
    //    static
    CommonService commonService;

    @Autowired
    DetailService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "detail";

    /** 画面名*/
    private static final String GAMEN_NAME = "Chi tiết sản phẩm";

    /**
     * 遷移パラメータ
     */
    public enum RedirectAttrsKey {
        /** ユーザーID */
        USER_ID,
        /** 遷移元画面ID */
        SENIMOTO_GAMEN_ID,
        /** Mã sản phẩm */
        PRODUCT_CD, CATEGORY_CD,
        /** */
        SENIMAE_PRODUCT_CD,
        /** Mã sản phẩm */
        SENIMAE_PRODUCT_NM,
        /** Mã sản phẩm */
        SENIMAE_CATEGORY_CD,
        /** Mã sản phẩm */
        SENIMAE_MAKER_NAME

    }

    @Autowired
    DetailValidator validator;

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
    public String init(@ModelAttribute DetailForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        // 遷移パラメータ設定
        setParameter(form, model);

        // 初期設定
        setInit(form);

        return "detail";
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
    public String doBackBtn(@ModelAttribute DetailForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        if (StringUtils.isEmpty(form.getSenimaeCategoryCd())) {
            form.setSenimaeCategoryCd("000");
        }

        // 画面遷移のパラメータ設定
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.PRODUCT_CD.name(), form.getSenimaeProductCd());
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.PRODUCT_NM.name(), form.getSenimaeProductNm());
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.CATEGORY_CD.name(), form.getSenimaeCategoryCd());
        attrs.addFlashAttribute(SearchController.RedirectAttrsKey.MAKER_NAME.name(), form.getSenimaeMakerName());
        return "redirect:/search";

    }

    /**
     *  更新ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "updBtn", method = RequestMethod.POST)
    public String doUpdBtn(@ModelAttribute DetailForm form, BindingResult result, Model model,
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
        return "detail";
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
    public String doLogoutBtn(@ModelAttribute DetailForm form, BindingResult result, Model model,
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
    public void setParameter(DetailForm form, Model model) {

        // ユーザーID
        if (model.asMap().containsKey(RedirectAttrsKey.USER_ID.name())) {
            form.setUserId((String) model.asMap().get(RedirectAttrsKey.USER_ID.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }

        // Mã sản phẩm
        if (model.asMap().containsKey(RedirectAttrsKey.PRODUCT_CD.name())) {
            form.setProductCd((String) model.asMap().get(RedirectAttrsKey.PRODUCT_CD.name()));
        }

        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.CATEGORY_CD.name())) {
            form.setCategoryCd((String) model.asMap().get(RedirectAttrsKey.CATEGORY_CD.name()));
        }

        // Mã sản phẩm
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMAE_PRODUCT_CD.name())) {
            form.setSenimaeProductCd((String) model.asMap().get(RedirectAttrsKey.SENIMAE_PRODUCT_CD.name()));
        }
        // Mã sản phẩm
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMAE_PRODUCT_NM.name())) {
            form.setSenimaeProductNm((String) model.asMap().get(RedirectAttrsKey.SENIMAE_PRODUCT_NM.name()));
        }
        // Mã sản phẩm
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMAE_CATEGORY_CD.name())) {
            form.setCategoryCd((String) model.asMap().get(RedirectAttrsKey.SENIMAE_CATEGORY_CD.name()));
        }
        // Mã sản phẩm
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMAE_CATEGORY_CD.name())) {
            form.setSenimaeCategoryCd((String) model.asMap().get(RedirectAttrsKey.SENIMAE_CATEGORY_CD.name()));
        }
        // Mã sản phẩm
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMAE_MAKER_NAME.name())) {
            form.setSenimaeMakerName((String) model.asMap().get(RedirectAttrsKey.SENIMAE_MAKER_NAME.name()));
        }

    }

    /**
     *  初期表示設定
     * @param form フォーム情報
     */
    public void setInit(DetailForm form) {
        // 画面項目の初期設定
        form.setGamenNm(GAMEN_NAME);
        String userId = CommonController.getUserId();
        String productCd = form.getProductCd();

        // ログイン中のユーザーIDは空文字、NULLでない場合はユーザー情報取得
        if (StringUtils.isNotBlank(userId)) {
            form.setUserName(commonService.getUserName(userId));
            form.setNickName(commonService.getNickName(userId));

        }

        if (checkAdmin()) {
            form.setAdminFlg("1");
        } else {
            form.setAdminFlg("0");
        }

        // カテゴリー
        setCategoryCdCn(form);

        //  Lấy thông tin chi tiết sản phẩm
        if (!StringUtils.isEmpty(productCd)) {
            DetailForm getResult = service.getProductInfo(productCd);
            if (Objects.nonNull(getResult)) {
                form.setCategoryCd(getResult.getCategoryCd());
                form.setProductNm(getResult.getProductNm());
                form.setMakerName(getResult.getMakerName());
                form.setPrice(getResult.getPrice());
                form.setQuantity(getResult.getQuantity());
                form.setSaleFlag(getResult.getSaleFlag());
            }
        }

    }

    /**
     *  各コンディションの値を設定
     * @param form
     */
    public void setCategoryCdCn(DetailForm form) {
        List<Map<String, String>> categoryCdList = service.getCategoryCd();
        Map<String, String> categoryCn = new HashMap<>();
        categoryCn.put("000", "Vui lòng chọn loại sản phẩm");

        for (Map<String, String> category : categoryCdList) {
            categoryCn.put(category.get("CATEGORY_CD"), category.get("CATEGORY_NM"));
        }

        // カテゴリー
        form.getOriginalMap().put("cnCategoryCd", categoryCn);
    }

    public boolean checkAdmin() {
        boolean checkFlg = false;

        String roleId = "R00001";
        // ログイン中のユーザーID取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        if (StringUtils.isNotBlank(userId)) {
            roleId = service.getRoleId(userId);
        }

        // ロールIDからAdminかを判定する
        if (StringUtils.equals("R99999", roleId)) {
            checkFlg = true;
        }

        // チェック結果を返却
        return checkFlg;
    }

}
