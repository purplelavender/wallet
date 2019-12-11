package com.wallet.hz.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName: MarketInfo
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 19:24
 */
public class MarketInfo implements Serializable {

    /**
     * "Status":true,
     *         "Code":200,
     *         "Message":"获取行情成功",
     *         "Data":[
     */

    private boolean Status;
    private int Code;
    private String Message;
    private ArrayList<MarketList> Data;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<MarketList> getData() {
        return Data;
    }

    public void setData(ArrayList<MarketList> data) {
        Data = data;
    }
}
