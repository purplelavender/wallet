package com.wallet.hz.model;

import java.io.Serializable;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: WalletInfo
 * @Description: 钱包首頁底部货币信息
 * @Author: ZL
 * @CreateDate: 2019/11/06 13:58
 */
public class WalletInfo implements Serializable {

    /**
     * "address": "string",
     *   "balance": 0,
     *   "coinName": "string",
     *   "frozens": 0,
     *   "icons": "string",
     *   "id": 0,
     *   "usdtbalance": 0,
     *   "userId": 0,
     *   "version": 0
     */

    private String address;
    private double balance;
    private String coinName;
    private double frozens;
    private String icons;
    private int id;
    private double usdtbalance;
    private int userId;
    private int version;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCoinName() {
        return StringUtil.isEmpty(coinName) ? "" : coinName.toUpperCase();
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public double getFrozens() {
        return frozens;
    }

    public void setFrozens(double frozens) {
        this.frozens = frozens;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUsdtbalance() {
        return usdtbalance;
    }

    public void setUsdtbalance(double usdtbalance) {
        this.usdtbalance = usdtbalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
