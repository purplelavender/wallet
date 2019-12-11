package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: WalletToatl
 * @Description: 钱包首页的总资产
 * @Author: ZL
 * @CreateDate: 2019/11/07 20:35
 */
public class WalletToatl implements Serializable {

    /**
     * "usdtto":0
     */
    private double usdtto;

    public double getUsdtto() {
        return usdtto;
    }

    public void setUsdtto(double usdtto) {
        this.usdtto = usdtto;
    }
}
