package com.wallet.hz.utils;

import android.content.Context;

import com.wallet.hz.constant.SharedPrefKey;
import com.wallet.hz.model.ServiceAgreement;

import java.util.ArrayList;

import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: AppSpUtil
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 16:50
 */
public class AppSpUtil {

    /**
     * 保存用户名称
     * @param context
     * @param userName
     */
    public static void setUserName(Context context, String userName) {
        SpUtil.put(context, SharedPrefKey.USER_NAME, userName);
    }

    /**
     * 获取用户名称
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_NAME, "");
    }

    /**
     * 保存用户密码
     * @param context
     * @param userPassword
     */
    public static void setUserPassword(Context context, String userPassword) {
        SpUtil.put(context, SharedPrefKey.USER_PASSWORD, userPassword);
    }

    /**
     * 获取用户密码
     * @param context
     * @return
     */
    public static String getUserPassword(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_PASSWORD, "");
    }

    /**
     * 保存用户token
     * @param context
     * @param userToken
     */
    public static void setUserToken(Context context, String userToken) {
        SpUtil.put(context, SharedPrefKey.USER_TOKEN, userToken);
    }

    /**
     * 获取用户token
     * @param context
     * @return
     */
    public static String getUserToken(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_TOKEN, "");
    }

    /**
     * 保存用户ID
     * @param context
     * @param userId
     */
    public static void setUserId(Context context, int userId) {
        SpUtil.put(context, SharedPrefKey.USER_ID, userId);
    }

    /**
     * 获取用户ID
     * @param context
     * @return
     */
    public static int getUserId(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_ID, 0);
    }

    /**
     * 保存用户是否设置资金密码
     * @param context
     * @param userAvatar
     */
    public static void setUserAvatar(Context context, boolean userAvatar) {
        SpUtil.put(context, SharedPrefKey.USER_AVATAR, userAvatar);
    }

    /**
     * 获取用户是否设置资金密码
     * @param context
     * @return
     */
    public static boolean getUserAvatar(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_AVATAR, false);
    }

    /**
     * 保存用户手机号码
     * @param context
     * @param userMobile
     */
    public static void setUserMobile(Context context, String userMobile) {
        SpUtil.put(context, SharedPrefKey.USER_MOBILE, userMobile);
    }

    /**
     * 获取用户手机号码
     * @param context
     * @return
     */
    public static String getUserMobile(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_MOBILE, "");
    }

    /**
     * 保存用户邮箱
     * @param context
     * @param userMail
     */
    public static void setUserMail(Context context, String userMail) {
        SpUtil.put(context, SharedPrefKey.USER_MAIL, userMail);
    }

    /**
     * 获取用户邮箱
     * @param context
     * @return
     */
    public static String getUserMail(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_MAIL, "");
    }

    /**
     * 保存用户是否绑定谷歌
     * @param context
     * @param userGoogle
     */
    public static void setUserGoogle(Context context, boolean userGoogle) {
        SpUtil.put(context, SharedPrefKey.USER_GOOGLE, userGoogle);
    }

    /**
     * 获取用户是否绑定谷歌
     * @param context
     * @return
     */
    public static boolean getUserGoogle(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_GOOGLE, false);
    }

    /**
     * 保存用户邀请码
     * @param context
     * @param userCode
     */
    public static void setUserCode(Context context, String userCode) {
        SpUtil.put(context, SharedPrefKey.USER_CODE, userCode);
    }

    /**
     * 获取用户邀请码
     * @param context
     * @return
     */
    public static String getUserCode(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_CODE, "");
    }

    /**
     * 保存用户邀请人邀请码
     * @param context
     * @param userParentCode
     */
    public static void setUserParentCode(Context context, String userParentCode) {
        SpUtil.put(context, SharedPrefKey.USER_PARENT_CODE, userParentCode);
    }

    /**
     * 获取用户邀请人邀请码
     * @param context
     * @return
     */
    public static String getUserParentCode(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_PARENT_CODE, "");
    }

    /**
     * 保存用户二维码地址
     * @param context
     * @param userQRCodeUrl
     */
    public static void setUserQRCodeUrl(Context context, String userQRCodeUrl) {
        SpUtil.put(context, SharedPrefKey.USER_QRCODE_URL, userQRCodeUrl);
    }

    /**
     * 获取用户二维码地址
     * @param context
     * @return
     */
    public static String getUserQRCodeUrl(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_QRCODE_URL, "");
    }

    /**
     * 保存用户助记词
     * @param context
     * @param userMnemonic
     */
    public static void setUserMnemonic(Context context, String userMnemonic) {
        SpUtil.put(context, SharedPrefKey.USER_MNEMONIC, userMnemonic);
    }

    /**
     * 获取用户助记词
     * @param context
     * @return
     */
    public static String getUserMnemonic(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_MNEMONIC, "");
    }

    /**
     * 保存汇率
     * @param context
     * @param rate
     */
    public static void setUserRate(Context context, String rate) {
        SpUtil.put(context, SharedPrefKey.USER_RATE, rate);
    }

    /**
     * 获取汇率
     * @param context
     * @return
     */
    public static String getUserRate(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_RATE, "");
    }

    /**
     * 保存资产
     * @param context
     * @param amount
     */
    public static void setUserAmount(Context context, String amount) {
        SpUtil.put(context, SharedPrefKey.USER_AMOUNT, amount);
    }

    /**
     * 获取资产
     * @param context
     * @return
     */
    public static String getUserAmount(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_AMOUNT, "");
    }

    /**
     * 保存用户节点等级
     * @param context
     * @param level
     */
    public static void setUserLevel(Context context, int level) {
        SpUtil.put(context, SharedPrefKey.USER_LEVEL, level);
    }

    /**
     * 获取节点等级
     * @param context
     * @return
     */
    public static int getUserLevel(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_LEVEL, 0);
    }

    /**
     * 保存用户邀请地址
     * @param context
     * @param inviteUrl
     */
    public static void setUserInviteUrl(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_INVITE_URL, inviteUrl);
    }

    /**
     * 获取邀请地址
     * @param context
     * @return
     */
    public static String getUserInviteUrl(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_INVITE_URL, "");
    }

    /**
     * 保存用户简体中文协议
     * @param context
     * @param inviteUrl
     */
    public static void setUserJianTiInfo(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_JIANTI_INFO, inviteUrl);
    }

    /**
     * 获取简体中文协议
     * @param context
     * @return
     */
    public static String getUserJianTiInfo(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_JIANTI_INFO, "");
    }

    /**
     * 保存用户繁体中文协议
     * @param context
     * @param inviteUrl
     */
    public static void setUserFanTiInfo(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_FANTI_INFO, inviteUrl);
    }

    /**
     * 获取繁体中文协议
     * @param context
     * @return
     */
    public static String getUserFanTiInfo(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_FANTI_INFO, "");
    }

    /**
     * 保存用户英文协议
     * @param context
     * @param inviteUrl
     */
    public static void setUserEnglishInfo(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_ENGLISH_INFO, inviteUrl);
    }

    /**
     * 获取英文协议
     * @param context
     * @return
     */
    public static String getUserEnglishInfo(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_ENGLISH_INFO, "");
    }

    /**
     * 保存用户简体中文条文
     * @param context
     * @param inviteUrl
     */
    public static void setUserJianTiPaper(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_JIANTI_PAPER, inviteUrl);
    }

    /**
     * 获取简体中文条文
     * @param context
     * @return
     */
    public static String getUserJianTiPaper(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_JIANTI_PAPER, "");
    }

    /**
     * 保存用户繁体中文条文
     * @param context
     * @param inviteUrl
     */
    public static void setUserFanTiPaper(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_FANTI_PAPER, inviteUrl);
    }

    /**
     * 获取繁体中文条文
     * @param context
     * @return
     */
    public static String getUserFanTiPaper(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_FANTI_PAPER, "");
    }

    /**
     * 保存用户英文条文
     * @param context
     * @param inviteUrl
     */
    public static void setUserEnglishPaper(Context context, String inviteUrl) {
        SpUtil.put(context, SharedPrefKey.USER_ENGLISH_PAPER, inviteUrl);
    }

    /**
     * 获取英文条文
     * @param context
     * @return
     */
    public static String getUserEnglishPaper(Context context) {
        return SpUtil.get(context, SharedPrefKey.USER_ENGLISH_PAPER, "");
    }

    /**
     * 保存协议及条文
     * @param context
     * @param infos
     */
    public static void saveServiceInfo(Context context, ArrayList<ServiceAgreement> infos) {
        if (infos != null && infos.size() > 0) {
            setUserJianTiInfo(context, infos.get(5).getContents());
            setUserFanTiInfo(context, infos.get(3).getContents());
            setUserEnglishInfo(context, infos.get(4).getContents());
            setUserJianTiPaper(context, infos.get(1).getContents());
            setUserFanTiPaper(context, infos.get(2).getContents());
            setUserEnglishPaper(context, infos.get(0).getContents());
        }
    }

    /**
     * 是否登陆
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        String name = getUserName(context);
        String password = getUserPassword(context);
        String token = getUserToken(context);
        return !StringUtil.isEmpty(name) && !StringUtil.isEmpty(password) && !StringUtil.isEmpty(token);
    }

    /**
     * 登出清空token
     * @param context
     */
    public static void logout(Context context) {
//        setUserName(context, "");
        setUserPassword(context, "");
        setUserToken(context, "");
        setUserMnemonic(context, "");
        setUserParentCode(context, "");
        setUserCode(context, "");
        setUserLevel(context, 0);
        setUserGoogle(context, false);
        setUserMobile(context, "");
        setUserMail(context, "");
        setUserAmount(context, "");
        setUserId(context, 0);
        setUserAvatar(context, false);
        setUserQRCodeUrl(context, "");
        setUserRate(context, "");
    }
}
