package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.wallet.hz.R;
import com.wallet.hz.adapter.TabLayoutViewPagerAdapter;
import com.wallet.hz.fragment.AwardFragment;
import com.wallet.hz.fragment.DestructionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.widget.HackyViewPager;

/**
 * @ClassName: AwardDestructionActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 18:39
 */
public class AwardDestructionActivity extends BaseAppActivity {

    @BindView(R.id.tab_award_destruction)
    TabLayout tabAward;
    @BindView(R.id.vp_award_destruction)
    HackyViewPager vpAward;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, AwardDestructionActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_award_destruction_pool;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
        initFragment();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    /**
     * 初始化底部Tab栏
     */
    private void initFragment() {
        titles.add(getString(R.string.modle_award_pool));
        titles.add(getString(R.string.modle_destruction_pool));

        fragments.add(AwardFragment.newInstance());
        fragments.add(DestructionFragment.newInstance());

        TabLayoutViewPagerAdapter adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpAward.setAdapter(adapter);
        vpAward.setOffscreenPageLimit(fragments.size());
        tabAward.setupWithViewPager(vpAward);
        tabAward.getTabAt(0).select();
    }
}
