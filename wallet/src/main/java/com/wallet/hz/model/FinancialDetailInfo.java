package com.wallet.hz.model;

import java.io.Serializable;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: FinancialDetailInfo
 * @Description: 财务明细对象信息
 * @Author: ZL
 * @CreateDate: 2019/11/07 21:20
 */
public class FinancialDetailInfo implements Serializable {

    /**
     * "bzamount":0,
     *         "cdamount":0,
     *         "coinname":"usdt",
     *         "commas":"+",
     *         "createTime":"2019-11-07 22:52:30",
     *         "cyamount":0,
     *         "damount":0,
     *         "dhamount":0.33333333,
     *         "dzamount":0,
     *         "gmamount":0,
     *         "huilv":"0.33333333",
     *         "id":38,
     *         "sfamount":0,
     *         "tqamount":0,
     *         "type":3,
     *         "usdtdsfamount":0,
     *         "usdtsfamount":0,
     *         "userId":4,
     *         "wkamount":0,
     *         "wqamount":0,
     *         "xhamount":0
     */

    private double bzamount;
    private double cdamount;
    private String coinname;
    private String commas;
    private String createTime;
    private double cyamount;
    private double damount;
    private double dhamount;
    private double dzamount;
    private double gmamount;
    private String huilv;
    private double id;
    private double sfamount;
    private double tqamount;
    private int type;
    private double usdtdsfamount;
    private double usdtsfamount;
    private int userId;
    private double wkamount;
    private double wqamount;
    private double xhamount;

    public double getBzamount() {
        return bzamount;
    }

    public void setBzamount(double bzamount) {
        this.bzamount = bzamount;
    }

    public double getCdamount() {
        return cdamount;
    }

    public void setCdamount(double cdamount) {
        this.cdamount = cdamount;
    }

    public String getCoinname() {
        return StringUtil.isEmpty(coinname) ? "" : coinname.toUpperCase();
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getCommas() {
        return commas;
    }

    public void setCommas(String commas) {
        this.commas = commas;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getCyamount() {
        return cyamount;
    }

    public void setCyamount(double cyamount) {
        this.cyamount = cyamount;
    }

    public double getDamount() {
        return damount;
    }

    public void setDamount(double damount) {
        this.damount = damount;
    }

    public double getDhamount() {
        return dhamount;
    }

    public void setDhamount(double dhamount) {
        this.dhamount = dhamount;
    }

    public double getDzamount() {
        return dzamount;
    }

    public void setDzamount(double dzamount) {
        this.dzamount = dzamount;
    }

    public double getGmamount() {
        return gmamount;
    }

    public void setGmamount(double gmamount) {
        this.gmamount = gmamount;
    }

    public String getHuilv() {
        return huilv;
    }

    public void setHuilv(String huilv) {
        this.huilv = huilv;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getSfamount() {
        return sfamount;
    }

    public void setSfamount(double sfamount) {
        this.sfamount = sfamount;
    }

    public double getTqamount() {
        return tqamount;
    }

    public void setTqamount(double tqamount) {
        this.tqamount = tqamount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getUsdtdsfamount() {
        return usdtdsfamount;
    }

    public void setUsdtdsfamount(double usdtdsfamount) {
        this.usdtdsfamount = usdtdsfamount;
    }

    public double getUsdtsfamount() {
        return usdtsfamount;
    }

    public void setUsdtsfamount(double usdtsfamount) {
        this.usdtsfamount = usdtsfamount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getWkamount() {
        return wkamount;
    }

    public void setWkamount(double wkamount) {
        this.wkamount = wkamount;
    }

    public double getWqamount() {
        return wqamount;
    }

    public void setWqamount(double wqamount) {
        this.wqamount = wqamount;
    }

    public double getXhamount() {
        return xhamount;
    }

    public void setXhamount(double xhamount) {
        this.xhamount = xhamount;
    }
}
