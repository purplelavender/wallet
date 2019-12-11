package com.wallet.hz.model;

import android.content.Context;

import com.wallet.hz.R;

import java.io.Serializable;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: ExchangeInfo
 * @Description: 兑换增值列表信息
 * @Author: ZL
 * @CreateDate: 2019/11/04 14:15
 */
public class ExchangeInfo implements Serializable {

    /**
     * "coinName": "string",
     *   "createTime": "2019-11-04T03:49:10.062Z",
     *   "endTime": "2019-11-04T03:49:10.062Z",
     *   "exchangeCount": 0, 兑换数量， 放大数量
     *   "id": 0,
     *   "incomeCount": 0,  兑换到账数量， 放大到账数量
     *   "retes": "string",
     *   "status": 0,
     *   "type": 0,
     *   "userId": 0
     *   "damount":163.93442622, 提取到账数量
     *   "huilv":"0.03050000", 汇率（提取列表）
     *   "tamount":5.00000000, 提取数量
     *   waamount  挖取数量
     *   amount    参与数量
     */
    private String coinName;
    private String createTime;
    private String endTime;
    private double exchangeCount;
    private int id;
    private double incomeCount;
    private String retes;
    private String huilv;
    private int status;
    private int type;
    private int userId;
    private double waamount;
    private double amount;
    private double damount;
    private double tamount;

    public String getCoinName() {
        return StringUtil.isEmpty(coinName) ? "" : coinName.toUpperCase();
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(double exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncomeCount() {
        return incomeCount;
    }

    public void setIncomeCount(double incomeCount) {
        this.incomeCount = incomeCount;
    }

    public String getRetes() {
        return retes;
    }

    public void setRetes(String retes) {
        this.retes = retes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getWaamount() {
        return waamount;
    }

    public void setWaamount(double waamount) {
        this.waamount = waamount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHuilv() {
        return huilv;
    }

    public void setHuilv(String huilv) {
        this.huilv = huilv;
    }

    public double getDamount() {
        return damount;
    }

    public void setDamount(double damount) {
        this.damount = damount;
    }

    public double getTamount() {
        return tamount;
    }

    public void setTamount(double tamount) {
        this.tamount = tamount;
    }

    /**
     * 获取参与方式
     * @param context
     * @return
     */
    public String getJoinTypeText(Context context) {
        String text = "";
        switch (getType()) {
            case 1:
                text = context.getString(R.string.state_jin_xi);
                break;
            case 2:
                text = context.getString(R.string.state_xi_no_jin);
                break;
            case 3:
                text = context.getString(R.string.state_no_xi_no_jin);
                break;
                default:
                    text = context.getString(R.string.state_jin_xi);
                    break;
        }
        return text;
    }

    /**
     * 是否是锁定状态
     * @return
     */
    public boolean isLocked() {
        return getStatus() == 1;
    }
}
