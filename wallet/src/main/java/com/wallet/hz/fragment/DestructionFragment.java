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
 * @ClassName: DestructionFragment
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 18:36
 */
public class DestructionFragment extends BaseAppMVPFragment<AwardPresenter> implements AwardContract.View {

    @BindView(R.id.tv_destruction_amount)
    TextView tvDestructionAmount;
    @BindView(R.id.tv_destruction_rule)
    TextView tvDestructionRule;

    public static DestructionFragment newInstance() {
        Bundle args = new Bundle();
        DestructionFragment fragment = new DestructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_destruction_pool;
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

    }

    @Override
    public void onDestruction(double destruction) {
        tvDestructionAmount.setText(BigDecimalUtils.formatServiceNumber(destruction));
    }
}
