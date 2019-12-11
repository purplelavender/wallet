package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: GoogleStepOne
 * @Description: 谷歌第一步接口获取的谷歌码对象
 * @Author: ZL
 * @CreateDate: 2019/11/01 15:25
 */
public class GoogleStepOne implements Serializable {

    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
