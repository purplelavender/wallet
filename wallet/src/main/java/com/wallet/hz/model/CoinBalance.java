package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: CoinBalance
 * @Description: 币种汇率
 * @Author: ZL
 * @CreateDate: 2019/11/06 15:17
 */
public class CoinBalance implements Serializable {

    /**
     * {
     *   "balance": 0,
     *   "huilv": 0
     *   "sxfamount":0.00100000
     *   "tbamount":0.00100000
     * }
     */

    private double balance;
    private double huilv;
    private double sxfamount;
    private double tbamount;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getHuilv() {
        return huilv;
    }

    public void setHuilv(double huilv) {
        this.huilv = huilv;
    }

    public double getSxfamount() {
        return sxfamount;
    }

    public void setSxfamount(double sxfamount) {
        this.sxfamount = sxfamount;
    }

    public double getTbamount() {
        return tbamount;
    }

    public void setTbamount(double tbamount) {
        this.tbamount = tbamount;
    }
}
