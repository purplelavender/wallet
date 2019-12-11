package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.AwardDestruction;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: AwardPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 18:30
 */
public class AwardPresenter extends BasePresenter<AwardContract.View> implements AwardContract.Presenter {

    private Context mContext;

    public AwardPresenter(AwardContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getPoolInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.get(TAG, ApiConstant.URL_MODEL_AWARD_DESTRUCTION_POOL, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    AwardDestruction awardDestruction = JSON.parseObject(response, AwardDestruction.class);
                    getView().onAwardSuccess(awardDestruction.getBonusMoney());
                    getView().onDestruction(awardDestruction.getDestroyMoney());
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().onAwardSuccess(0.0000);
                    getView().onDestruction(0.0000);
                }
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
                getView().onAwardSuccess(0.0000);
                getView().onDestruction(0.0000);
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onAwardSuccess(0.0000);
                getView().onDestruction(0.0000);
            }
        });
    }
}
