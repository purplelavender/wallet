package com.wallet.hz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 * @ClassName:      TabLayoutViewPagerAdapter
 * @Description:    java类作用描述
 * @Author:         ZL
 * @CreateDate:     2019/10/24 14:24
 */
public class TabLayoutViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titleList;

    /**
     * 构造方法
     *
     * @param manager
     * @param fragmentList
     */
    public TabLayoutViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList, List<String> titleList) {
        super(manager);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        if (null != fragmentList) {
            return fragmentList.size();
        }
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        if (null != fragmentList) {
            return fragmentList.get(position);
        }
        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titleList) {
            return titleList.get(position);
        }
        return "";
    }
}
