package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.UserInfo;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;
import share.exchange.framework.widget.CustomCountDownTimer;

/**
 * @ClassName: ForgotPasswordPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:36
 */
public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.View> implements ForgotPasswordContract.Presenter {

    private Context mContext;
    private CodeTimer mCodeTimer;

    public ForgotPasswordPresenter(ForgotPasswordContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void destroyView() {
        super.destroyView();
        stopTimer();
    }

    @Override
    public void getPhoneCode(String phone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        apiManager.post(TAG, ApiConstant.URL_PHONECODES_ZHAOHUI, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_send_success));
                startTimer();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void getMailCode(String mail) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", mail);
        apiManager.post(TAG, ApiConstant.URL_MAILCODES_ZHAOHUI, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_send_success));
                startTimer();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void findPasswordByPhoneMail(final String name, String phone, String mail, String code, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", name);
        map.put("codes", code);
        map.put("phone", phone);
        map.put("email", mail);
        map.put("password", password);
        apiManager.post(TAG, ApiConstant.URL_PHONEMAIL_ZHAOHUI_PASSWORD, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String  response) {
                getView().onFindSuccess(name);
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void mnemonicFindPassword(String mnemonic, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("zhujici", mnemonic);
        map.put("password", password);
        apiManager.post(TAG, ApiConstant.URL_ZHUJICI_ZHAOHUI_PASSWORD, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    UserInfo userInfo = JSON.parseObject(response, UserInfo.class);
                    getView().onFindSuccess(userInfo == null ? "" : userInfo.getUsername());
                } catch (Exception e) {
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

    public class CodeTimer extends CustomCountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getView().changeCodeText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            getView().changeCodeText(mContext.getString(R.string.bind_get_code_again));
        }
    }

    private void startTimer() {
        if (mCodeTimer == null) {
            mCodeTimer = new CodeTimer(60000, 1000);
        }
        mCodeTimer.start();
    }

    private void stopTimer() {
        if (mCodeTimer != null) {
            mCodeTimer.cancel();
            mCodeTimer = null;
        }
    }
}
