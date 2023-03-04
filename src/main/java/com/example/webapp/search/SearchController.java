package com.example.webapp.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.example.webapp.access.AccessController;
import com.example.webapp.add.AddController;
import com.example.webapp.detail.DetailController;
import com.example.webapp.main.MainController;
import com.example.webapp.passchange.PassChangeController;
import com.example.webapp.profile.ProfileController;

@Controller
@RequestMapping("search")
public class SearchController extends CommonController{

    @Autowired
    CommonService commonService;

    @Autowired
    SearchService service;

    /** 画面ID*/
    private static final String GAMEN_ID = "search";

    /** 画面名*/
    private static final String GAMEN_NAME = "Tìm kiếm";
    
    private Logger log = Logger.getLogger(SearchController.class);

    /**
     * 遷移パラメータ
     */
    public enum RedirectAttrsKey {
        /** ユーザーID */
        USER_ID,
        /** 遷移元画面ID */
        SENIMOTO_GAMEN_ID,
        /** Mã sản phẩm */
        PRODUCT_CD,
        /** Mã sản phẩm */
        PRODUCT_NM,
        /** Mã sản phẩm */
        CATEGORY_CD,
        /** Mã sản phẩm */
        MAKER_NAME

    }

    @Autowired
    SearchValidator validator;

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
    public String init(@ModelAttribute SearchForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        setParameter(form, model);

        // 初期設定
        setInit(form);

        return "search";
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
    public String doHomeBtn(@ModelAttribute SearchForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(MainController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/main";
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
    public String doAccessBtn(SearchForm form, BindingResult result, Model model,
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
    public String doProfileBtn(@ModelAttribute SearchForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 遷移パラメータ設定
        attrs.addFlashAttribute(ProfileController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(ProfileController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/profile";
    }

    /**
     *  商品検索ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "productSearchBtn", method = RequestMethod.POST)
    public String doProductSearchBtn(SearchForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {
        // 初期設定
        setInit(form);

        //
        setAllProduct(form);

        // 画面再表示
        return "search";
    }

    /**
     *  商品追加ボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "addProductBtn", method = RequestMethod.POST)
    public String doAddProductBtn(SearchForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        attrs.addFlashAttribute(AddController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(AddController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/add";
    }

    /**
     *  商品コードボタンを押下処理
     * @param form フォームオブジェクト
     * @param result チェックの検証結果
     * @param model Springが用意するViewに渡すModelオブジェクト
     * @param attrs 遷移先画面に渡すパラメータ
     * @return 遷移先のView名
     */
    @RequestMapping(params = "productCdLink", method = RequestMethod.POST)
    public String doProductCdLink(@ModelAttribute SearchForm form, BindingResult result, Model model,
            RedirectAttributes attrs) {

        String productSubmit = form.getProductCdSubmit();
        if (StringUtils.isEmpty(productSubmit)) {
            productSubmit = "00000000000000000000";
        }

        String categoryCdSubmit = form.getCategoryCdSubmit();
        if (StringUtils.isEmpty(categoryCdSubmit)) {
            categoryCdSubmit = "000";
        }

        // 遷移パラメータ設定
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.PRODUCT_CD.name(), productSubmit);
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.CATEGORY_CD.name(), categoryCdSubmit);
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.SENIMAE_PRODUCT_CD.name(), form.getProductCd());
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.SENIMAE_PRODUCT_NM.name(), form.getProductNm());
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.SENIMAE_CATEGORY_CD.name(), form.getCategoryCd());
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.SENIMAE_MAKER_NAME.name(), form.getMakerName());
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.USER_ID.name(), form.getUserId());
        attrs.addFlashAttribute(DetailController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), GAMEN_ID);

        // 画面遷移
        return "redirect:/detail";
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
    public String doPassChangeBtn(@ModelAttribute SearchForm form, BindingResult result, Model model,
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
    public String doLogoutBtn(@ModelAttribute SearchForm form, BindingResult result, Model model,
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
    public void setParameter(SearchForm form, Model model) {

        // ユーザーID
        if (model.asMap().containsKey(RedirectAttrsKey.USER_ID.name())) {
            form.setUserId((String) model.asMap().get(RedirectAttrsKey.USER_ID.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name())) {
            form.setSenimotoGamenId((String) model.asMap().get(RedirectAttrsKey.SENIMOTO_GAMEN_ID.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.PRODUCT_CD.name())) {
            form.setProductCd((String) model.asMap().get(RedirectAttrsKey.PRODUCT_CD.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.PRODUCT_NM.name())) {
            form.setProductNm((String) model.asMap().get(RedirectAttrsKey.PRODUCT_NM.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.CATEGORY_CD.name())) {
            form.setCategoryCd((String) model.asMap().get(RedirectAttrsKey.CATEGORY_CD.name()));
        }
        // 遷移元画面ID
        if (model.asMap().containsKey(RedirectAttrsKey.MAKER_NAME.name())) {
            form.setMakerName((String) model.asMap().get(RedirectAttrsKey.MAKER_NAME.name()));
        }

    }

    /**
     *  初期表示設定
     * @param form フォーム情報
     */
    public void setInit(SearchForm form) {
        // 画面項目の初期設定
        form.setGamenNm(GAMEN_NAME);
        String userId = CommonController.getUserId();

        // カテゴリー
        setCategoryCn(form);

        if (checkAdmin()) {
            form.setAdminFlg("1");
        } else {
            form.setAdminFlg("0");
        }

        // ログイン中のユーザーIDは空文字、NULLでない場合はユーザー情報取得
        if (StringUtils.isNotBlank(userId)) {
            form.setUserName(commonService.getUserName(userId));
            form.setNickName(commonService.getNickName(userId));
        }

        if (StringUtils.equals(form.getSenimotoGamenId(), "detail")) {
            if (!(StringUtils.isEmpty(form.getProductCd())
                    && StringUtils.isEmpty(form.getProductNm())
                    && StringUtils.equals(form.getCategoryCd(), "000") && StringUtils.isEmpty(form.getMakerName()))) {
                setAllProduct(form);
            }
        }
    }

    /**
     *  Tìm kiếm sản phẩm
     * @return thông tin sản phẩm
     */
    public List<SearchFormT1Row> getAllInfo() {
        List<SearchFormT1Row> infoList = new ArrayList<>();

        return infoList;
    }

    public void setCategoryCn(SearchForm form) {
        List<Map<String, String>> categoryCdList = new ArrayList<Map<String, String>>();

        categoryCdList = service.getCategoryCd();
        log.info("Get category:" + categoryCdList);
        Map<String, String> categoryCn = new HashMap<>();
        categoryCn.put("000", "Vui lòng chọn loại sản phẩm");

        for (Map<String, String> category : categoryCdList) {
            categoryCn.put(category.get("CATEGORY_CD"), category.get("CATEGORY_NM"));
        }

        form.getOriginalMap().put("cnCategoryCd", categoryCn);
    }

    /**
     *  Hiển thị thông tin tìm kiếm
     * @param form
     */
    public void setAllProduct(SearchForm form) {
        List<SearchFormT1Row> t1List = new ArrayList<>();
        t1List = service.getProductInfo(form.getProductCd(), form.getProductNm(), form.getCategoryCd(),
                form.getMakerName());

        form.setT1(t1List);
    }
    
//    @Autowired
//    public boolean checkAdmin() {
//        boolean checkFlg = false;
//
//        String roleId = "R00001";
//        // ログイン中のユーザーID取得
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userId = auth.getName();
//        if (StringUtils.isNotBlank(userId)) {
//            roleId = service.getRoleId(userId);
//        }
//
//        // ロールIDからAdminかを判定する
//        if (StringUtils.equals("R99999", roleId)) {
//            checkFlg = true;
//        }
//
//        // チェック結果を返却
//        return checkFlg;
//    }

}
