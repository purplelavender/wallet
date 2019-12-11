package com.wallet.hz.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.AwardContract;
import com.wallet.hz.presenter.AwardPresenter;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.utils.BigDecimalUtils;

/**
 * @ClassName: AwardFragment
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 18:33
 */
public class AwardFragment extends BaseAppMVPFragment<AwardPresenter> implements AwardContract.View {

    @BindView(R.id.tv_award_amount)
    TextView tvAwardAmount;
    @BindView(R.id.tv_award_rule)
    TextView tvAwardRule;

    public static AwardFragment newInstance() {
        Bundle args = new Bundle();
        AwardFragment fragment = new AwardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_award_pool;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void processLogic() {
        getPresenter().getPoolInfo();
    }

    @Override
    public AwardPresenter injectDependencies() {
        return new AwardPresenter(this, mAppContext);
    }

    @Override
    public void onAwardSuccess(double award) {
        tvAwardAmount.setText(BigDecimalUtils.formatServiceNumber(award));
    }

    @Override
    public void onDestruction(double destruction) {

    }
}
