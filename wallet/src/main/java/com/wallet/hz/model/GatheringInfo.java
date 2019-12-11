package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: GatheringInfo
 * @Description: 货币收款信息
 * @Author: ZL
 * @CreateDate: 2019/11/07 16:57
 */
public class GatheringInfo implements Serializable {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
