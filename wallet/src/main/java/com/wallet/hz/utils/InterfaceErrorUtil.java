package com.wallet.hz.utils;

import android.content.Context;

import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: InterfaceErrorUtil
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/20 16:59
 */
public class InterfaceErrorUtil {

    /**
     * 根据用户的语言选择更换接口回调的提示信息
     * @param context
     * @param errorMsg
     * @return
     */
    public static String changeLanguageErrorString(Context context, String errorMsg) {
        String text = errorMsg;
        if (StringUtil.isEqual(ApiConstant.codesno, errorMsg)) {
            text = context.getString(R.string.interface_error_codesno);
        } else if (StringUtil.isEqual(ApiConstant.cophone, errorMsg)) {
            text = context.getString(R.string.interface_error_cophone);
        } else if (StringUtil.isEqual(ApiConstant.duishuliang, errorMsg)) {
            text = context.getString(R.string.interface_error_duishuliang);
        } else if (StringUtil.isEqual(ApiConstant.duixiaoyu, errorMsg)) {
            text = context.getString(R.string.interface_error_duixiaoyu);
        } else if (StringUtil.isEqual(ApiConstant.emailcodes, errorMsg)) {
            text = context.getString(R.string.interface_error_emailcodes);
        } else if (StringUtil.isEqual(ApiConstant.exchange, errorMsg)) {
            text = context.getString(R.string.interface_error_exchange);
        } else if (StringUtil.isEqual(ApiConstant.getcodes, errorMsg)) {
            text = context.getString(R.string.interface_error_getcodes);
        } else if (StringUtil.isEqual(ApiConstant.gucodes, errorMsg)) {
            text = context.getString(R.string.interface_error_gucodes);
        } else if (StringUtil.isEqual(ApiConstant.noaddress, errorMsg)) {
            text = context.getString(R.string.interface_error_noaddress);
        } else if (StringUtil.isEqual(ApiConstant.noavailabs, errorMsg)) {
            text = context.getString(R.string.interface_error_noavailabs);
        } else if (StringUtil.isEqual(ApiConstant.nocapital, errorMsg)) {
            text = context.getString(R.string.interface_error_nocapital);
        } else if (StringUtil.isEqual(ApiConstant.nocode, errorMsg)) {
            text = context.getString(R.string.interface_error_nocode);
        } else if (StringUtil.isEqual(ApiConstant.nocodes, errorMsg)) {
            text = context.getString(R.string.interface_error_nocodes);
        } else if (StringUtil.isEqual(ApiConstant.nocunzai, errorMsg)) {
            text = context.getString(R.string.interface_error_nocunzai);
        } else if (StringUtil.isEqual(ApiConstant.nologinpassword, errorMsg)) {
            text = context.getString(R.string.interface_error_nologinpassword);
        } else if (StringUtil.isEqual(ApiConstant.nominables, errorMsg)) {
            text = context.getString(R.string.interface_error_nominables);
        } else if (StringUtil.isEqual(ApiConstant.nooldpassword, errorMsg)) {
            text = context.getString(R.string.interface_error_nooldpassword);
        } else if (StringUtil.isEqual(ApiConstant.nopassword, errorMsg)) {
            text = context.getString(R.string.interface_error_nopassword);
        } else if (StringUtil.isEqual(ApiConstant.noworkorder, errorMsg)) {
            text = context.getString(R.string.interface_error_noworkorder);
        } else if (StringUtil.isEqual(ApiConstant.nozhujici, errorMsg)) {
            text = context.getString(R.string.interface_error_nozhujici);
        } else if (StringUtil.isEqual(ApiConstant.oldnewpassword, errorMsg)) {
            text = context.getString(R.string.interface_error_oldnewpassword);
        } else if (StringUtil.isEqual(ApiConstant.passworderror, errorMsg)) {
            text = context.getString(R.string.interface_error_passworderror);
        } else if (StringUtil.isEqual(ApiConstant.phonecodes, errorMsg)) {
            text = context.getString(R.string.interface_error_phonecodes);
        } else if (StringUtil.isEqual(ApiConstant.success, errorMsg)) {
            text = context.getString(R.string.interface_error_success);
        } else if (StringUtil.isEqual(ApiConstant.tibierror, errorMsg)) {
            text = context.getString(R.string.interface_error_tibierror);
        } else if (StringUtil.isEqual(ApiConstant.yichang, errorMsg)) {
            text = context.getString(R.string.interface_error_yichang);
        } else if (StringUtil.isEqual(ApiConstant.yue, errorMsg)) {
            text = context.getString(R.string.interface_error_yue);
        } else if (StringUtil.isEqual(ApiConstant.yuyue, errorMsg)) {
            text = context.getString(R.string.interface_error_yuyue);
        } else if (StringUtil.isEqual(ApiConstant.yuyuejine, errorMsg)) {
            text = context.getString(R.string.interface_error_yuyuejine);
        } else if (StringUtil.isEqual(ApiConstant.zerocapital, errorMsg)) {
            text = context.getString(R.string.interface_error_zerocapital);
        } else if (StringUtil.isEqual(ApiConstant.zeroparticipate, errorMsg)) {
            text = context.getString(R.string.interface_error_zeroparticipate);
        } else if (StringUtil.isEqual(ApiConstant.sendcodefail, errorMsg)) {
            text = context.getString(R.string.interface_error_send_code);
        } else if (StringUtil.isEqual(ApiConstant.phonealreadbind, errorMsg)) {
            text = context.getString(R.string.interface_error_phone_alread_bind);
        } else if (StringUtil.isEqual(ApiConstant.mailalreadbind, errorMsg)) {
            text = context.getString(R.string.interface_error_mail_alread_bind);
        } else if (StringUtil.isEqual(ApiConstant.yuyuetime, errorMsg)) {
            text = context.getString(R.string.interface_error_yuyue_time);
        }
        return text;
    }
}
