package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: ServiceAgreement
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/11 16:30
 */
public class ServiceAgreement implements Serializable {

    /**
     * "contents": "string",
     *   "id": 0,
     *   "type": 0
     */

    private String contents;
    private int id;
    private int type;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
