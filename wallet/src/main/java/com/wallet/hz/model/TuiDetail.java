package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: TuiDetail
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/12/16 16:24
 */
public class TuiDetail implements Serializable {

    /**
     * datas = "2019-12-14T16:00:00.000+0000";
     * frommobile = bbb015;  成员名称
     * hsamount = "2252.25225225";  hs收益 hs
     * huilv = "0.0333";
     * id = 1397;
     * jdamount = 0;
     * jdusdt = "<null>";
     * mobile = bbb014;
     * pcsamount = 75;  hs收益 usdt
     * usdtamount = 0;  USDT收益  usdt
     * userId = 492;
     */

    private String datas;
    private String frommobile;
    private String hsamount;
    private String huilv;
    private int id;
    private double jdamount;
    private String jdusdt;
    private String mobile;
    private double pcsamount;
    private double usdtamount;
    private int userId;

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getFrommobile() {
        return frommobile;
    }

    public void setFrommobile(String frommobile) {
        this.frommobile = frommobile;
    }

    public String getHsamount() {
        return hsamount;
    }

    public void setHsamount(String hsamount) {
        this.hsamount = hsamount;
    }

    public String getHuilv() {
        return huilv;
    }

    public void setHuilv(String huilv) {
        this.huilv = huilv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getJdamount() {
        return jdamount;
    }

    public void setJdamount(double jdamount) {
        this.jdamount = jdamount;
    }

    public String getJdusdt() {
        return jdusdt;
    }

    public void setJdusdt(String jdusdt) {
        this.jdusdt = jdusdt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getPcsamount() {
        return pcsamount;
    }

    public void setPcsamount(double pcsamount) {
        this.pcsamount = pcsamount;
    }

    public double getUsdtamount() {
        return usdtamount;
    }

    public void setUsdtamount(double usdtamount) {
        this.usdtamount = usdtamount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
