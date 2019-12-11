package com.wallet.hz.model;

import java.io.Serializable;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: YuyueQueue
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/10 16:11
 */
public class YuyueQueue implements Serializable {

    /**
     * amount = 0;
     *         coinname = "<null>";
     *         createTime = "<null>";
     *         datas = "<null>";
     *         id = "<null>";
     *         rank = 0;
     *
     *         status = "<null>";
     *         type = "<null>";
     *         userId = 3;
     *         waamount = "<null>";
     */

    private double amount;
    private String coinname;
    private String createTime;
    private String datas;
    private int id;
    private int rank;
    private int status;
    private int type;
    private int userId;
    private double waamount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCoinname() {
        return StringUtil.isEmpty(coinname) ? "" : coinname.toUpperCase();
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
}
