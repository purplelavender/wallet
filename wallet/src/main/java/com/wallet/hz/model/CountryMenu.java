package com.wallet.hz.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MMM on 2018/1/12.
 * 国家地区分组
 */
public class CountryMenu implements Serializable {

    private String menuName;
    private List<CountryBean> countryBeen;

    // 是否展开，默认展开
    private boolean isExpand = true;

    public CountryMenu(String menuName, List<CountryBean> countryBeen) {
        this.menuName = menuName;
        this.countryBeen = countryBeen;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<CountryBean> getCountryBeen() {
        return countryBeen;
    }

    public void setCountryBeen(List<CountryBean> countryBeen) {
        this.countryBeen = countryBeen;
    }
}
