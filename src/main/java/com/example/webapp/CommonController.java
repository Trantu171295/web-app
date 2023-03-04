/**
 *  共通コントローラークラス
 */

package com.example.webapp;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonController {

    @Autowired
    static CommonService commonservice;
    @Autowired
    static CommonSqlMapper sqlMapper;

    /**
     *  パスワード変更ボタン押下処理のパラメータ設定
     * @param userId ユーザーID
     * @param senimotoGamenId 遷移元画面ID
     * @param attrs 遷移先画面に渡すパラメータ
     */
    //    public static void setParameterPassChangeBtn(RedirectAttributes attrs, String userId, String senimotoGamenId) {
    //        // 遷移パラメータ設定
    //        attrs.addFlashAttribute(PassChangeController.RedirectAttrsKey.USER_ID.name(), userId);
    //        attrs.addFlashAttribute(PassChangeController.RedirectAttrsKey.SENIMOTO_GAMEN_ID.name(), senimotoGamenId);
    //    }
    //
    //    /**
    //     *  連絡先ボタン押下処理のパラメータ設定
    //     * @param userId ユーザーID
    //     * @param attrs 遷移先画面に渡すパラメータ
    //     */
    //    public static void setParameterContactBtn(RedirectAttributes attrs, String userId) {
    //        // 遷移パラメータ設定
    //        attrs.addFlashAttribute(PassChangeController.RedirectAttrsKey.USER_ID.name(), userId);
    //    }
    
    private Logger log = Logger.getLogger(CommonController.class);
    /** ユーザー区分のコンディション  */
    public static Map<String, String> getUserKbn() {
        Map<String, String> userKbn = new HashMap<>();
        userKbn.put("Người dùng thông thường", "R00001");
        userKbn.put("Quản lý", "R99999");

        return userKbn;
    }

    /** アカウン状態のコンディション  */
    public static Map<String, String> getAccountStatus() {
        Map<String, String> accStt = new HashMap<>();
        accStt.put("有効", "0");
        accStt.put("無効", "1");

        return accStt;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    /**
     * ADMIN権限が持っているユーザーかをチェック
     * @return チェック結果。TRUE:持っている、FALSE:持っていない
     */
    public static boolean checkAdmin() {
        boolean checkFlg = false;

        String roleId = "R00001";
        // ログイン中のユーザーID取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        if (StringUtils.isNotBlank(userId)) {
            roleId = sqlMapper.getRoleId(userId);
        }

        // ロールIDからAdminかを判定する
        if (StringUtils.equals("R99999", roleId)) {
            checkFlg = true;
        }

        // チェック結果を返却
        return checkFlg;
    }

    /**
     *  ログイン中のユーザーIDを取得する
     * @return ログイン中のユーザーID
     */
    public static String getUserId() {
        String userId = "";
        // ログイン中のユーザー情報取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (StringUtils.isNotBlank(auth.getName())) {
            userId = auth.getName();
        }
        return userId;
    }

    /**
     *  カテゴリーコンディション作成ため、値を取得
     * @param form
     */
    public static Map<String, String> getCategoryInfo() {
        List<Map<String, String>> categoryCdList = new ArrayList<Map<String, String>>();

        categoryCdList = commonservice.getCategoryCd();
        Map<String, String> categoryCn = new HashMap<>();
        categoryCn.put("000", "Vui lòng chọn loại sản phẩm");

        for (Map<String, String> category : categoryCdList) {
            categoryCn.put(category.get("CATEGORY_CD"), category.get("CATEGORY_NM"));
        }

        return categoryCn;
    }

}
