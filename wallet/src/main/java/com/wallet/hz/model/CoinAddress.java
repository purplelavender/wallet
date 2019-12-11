package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: CoinAddress
 * @Description: 提币地址信息
 * @Author: ZL
 * @CreateDate: 2019/11/05 09:56
 */
public class CoinAddress implements Serializable {

    /**
     *  "address": "string",
     *   "createTime": "2019-11-05T01:40:11.915Z",
     *   "id": 0,
     *   "remarks": "string",
     *   "userId": 0
     */

    private String address;
    private String createTime;
    private int id;
    private String remarks;
    private int userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
