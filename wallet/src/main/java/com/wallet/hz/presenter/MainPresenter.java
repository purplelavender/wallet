package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.model.UserInfo;
import com.wallet.hz.model.VersionInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.io.File;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.http.response.IResponseDownloadHandler;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: MainPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 11:36
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private Context mContext;

    public MainPresenter(MainContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getCoinBalance() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", "hs");
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_COIN_BALANCE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    CoinBalance coinBalance = JSON.parseObject(response, CoinBalance.class);
                    if (coinBalance != null) {
                        AppSpUtil.setUserRate(mContext, coinBalance.getHuilv() + "");
                        AppSpUtil.setUserAmount(mContext, coinBalance.getBalance() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
            }
        });
    }

    @Override
    public void getUserInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_USERINFO, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    UserInfo userInfo = JSON.parseObject(response, UserInfo.class);
                    if (userInfo != null) {
                        getView().onMineSuccess(userInfo);
                        AppSpUtil.setUserMail(mContext, userInfo.getEmail());
                        AppSpUtil.setUserMobile(mContext, userInfo.getPhone());
                        AppSpUtil.setUserGoogle(mContext, StringUtil.isEqual("y", userInfo.getGuges()));
                        AppSpUtil.setUserLevel(mContext, userInfo.getStarts());
                        AppSpUtil.setUserCode(mContext, userInfo.getCodes());
                        AppSpUtil.setUserParentCode(mContext, userInfo.getParentCodes());
                        AppSpUtil.setUserMnemonic(mContext, userInfo.getZhujici());
                        AppSpUtil.setUserAvatar(mContext, StringUtil.isEqual("y", userInfo.getAvatar()));
                        AppSpUtil.setUserQRCodeUrl(mContext, userInfo.getQrcodeurl());
                        AppSpUtil.setUserId(mContext, userInfo.getUserId());
                    } else {
                        getView().onMineSuccess(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().onMineSuccess(null);
                }
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onMineSuccess(null);
            }
        });
    }

    @Override
    public void checkVersion() {
        int type = 1;
        if (SpUtil.getLanguage(mContext) == LanguageEnums.ENGLISH.getKey()) {
            type = 1;
        } else if (SpUtil.getLanguage(mContext) == LanguageEnums.TRADITIONAL_CHINESE.getKey()) {
            type = 2;
        } else if (SpUtil.getLanguage(mContext) == LanguageEnums.SIMPLIFIED_CHINESE.getKey()) {
            type = 3;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("languages", type);
        apiManager.get(TAG, ApiConstant.URL_CHECK_VERSION, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    VersionInfo versionInfo = JSON.parseObject(response, VersionInfo.class);
                    getView().onVersionSuccess(versionInfo);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNoLogin() {

            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void downApk(String url, String path) {
        apiManager.down(TAG, url, path, new IResponseDownloadHandler() {
            @Override
            public void onFinish(File downloadFile) {
                getView().installNewApk(downloadFile);
                AppManager.getAppManager().appExit();
            }

            @Override
            public void onProgress(long progress, long total) {
                long jindu = progress * 100 / total;
                getView().showLoading( jindu + "%", true);
            }

            @Override
            public void onFailed(String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

}
