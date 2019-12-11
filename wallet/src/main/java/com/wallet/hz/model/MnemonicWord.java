package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: MnemonicWord
 * @Description: 助记词
 * @Author: ZL
 * @CreateDate: 2019/11/01 16:51
 */
public class MnemonicWord implements Serializable {

    private String zhujici;

    public String getZhujici() {
        return zhujici;
    }

    public void setZhujici(String zhujici) {
        this.zhujici = zhujici;
    }
}
