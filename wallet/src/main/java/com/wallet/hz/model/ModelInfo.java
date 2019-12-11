package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: ModelInfo
 * @Description: 模式主页数据
 * @Author: ZL
 * @CreateDate: 2019/11/04 09:54
 */
public class ModelInfo implements Serializable {

    /**
     *  "availabs": 0, 可用
     *   "capital": 0, 资金
     *   "id": 0,
     *   "minables": 0, 可挖的
     *   "released": 0, 释放
     *   "userId": 0
     */


    private double availabs;
    private double capital;
    private int id;
    private double minables;
    private double released;
    private int userId;

    public double getAvailabs() {
        return availabs;
    }

    public void setAvailabs(double availabs) {
        this.availabs = availabs;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMinables() {
        return minables;
    }

    public void setMinables(double minables) {
        this.minables = minables;
    }

    public double getReleased() {
        return released;
    }

    public void setReleased(double released) {
        this.released = released;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
