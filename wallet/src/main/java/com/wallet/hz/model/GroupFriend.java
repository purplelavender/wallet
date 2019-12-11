package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: GroupFriend
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/08 02:16
 */
public class GroupFriend implements Serializable {

    /**
     * "sqamount":0,
     *         "todayamount":28000,
     *         "totalusdt":280000,
     *         "username":"ccc0009"
     */

    private double todayamount;
    private double totalusdt;
    private double sqamount;
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getSqamount() {
        return sqamount;
    }

    public void setSqamount(double sqamount) {
        this.sqamount = sqamount;
    }
}
