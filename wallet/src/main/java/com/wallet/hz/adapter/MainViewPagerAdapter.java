package com.wallet.hz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ClassName: MainViewPagerAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 15:27
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
