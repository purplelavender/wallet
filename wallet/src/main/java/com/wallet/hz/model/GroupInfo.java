package com.wallet.hz.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName: GroupInfo
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/08 01:39
 */
public class GroupInfo implements Serializable {

    /**
     * count = 1;
     *         gramount = 0;
     *         jdamount = 0;
     *         pcsamount = 0;
     *         shrecordEntity =         [];
     *         todayamount = 0;
     *         totalusdt = 150;
     *         usdtamount = 0;
     *         username = Super000;
     */

    private int count;
    private double gramount;
    private double jdamount;
    private double pcsamount;
    private ArrayList<GroupInfoData> shrecordEntity;
    private double todayamount;
    private double totalusdt;
    private double usdtamount;
    private String username;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getGramount() {
        return gramount;
    }

    public void setGramount(double gramount) {
        this.gramount = gramount;
    }

    public double getPcsamount() {
        return pcsamount;
    }

    public void setPcsamount(double pcsamount) {
        this.pcsamount = pcsamount;
    }

    public double getTodayamount() {
        return todayamount;
    }

    public void setTodayamount(double todayamount) {
        this.todayamount = todayamount;
    }

    public double getTotalusdt() {
        return totalusdt;
    }

    public void setTotalusdt(double totalusdt) {
        this.totalusdt = totalusdt;
    }

    public double getUsdtamount() {
        return usdtamount;
    }

    public void setUsdtamount(double usdtamount) {
        this.usdtamount = usdtamount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getJdamount() {
        return jdamount;
    }

    public void setJdamount(double jdamount) {
        this.jdamount = jdamount;
    }

    public ArrayList<GroupInfoData> getShrecordEntity() {
        return shrecordEntity;
    }

    public void setShrecordEntity(ArrayList<GroupInfoData> shrecordEntity) {
        this.shrecordEntity = shrecordEntity;
    }
}
