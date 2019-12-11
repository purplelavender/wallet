package com.wallet.hz.model;

import android.content.Context;

import com.wallet.hz.R;

import java.io.Serializable;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: GroupInfoData
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/09 02:01
 */
public class GroupInfoData implements Serializable {

    /**
     * {
     *                 coinname = usdt;
     *                 datas = "2019-11-09";
     *                 id = 2;
     *                 tamount = 50;
     *                 type = 1;  1推广2节点 ,
     *                 userId = 3;
     *             }
     */

    private String coinname;
    private String datas;
    private int id;
    private double tamount;
    private int type;
    private int userId;

    public String getCoinname() {
        return StringUtil.isEmpty(coinname) ? "" : coinname.toUpperCase();
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
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

    public double getTamount() {
        return tamount;
    }

    public void setTamount(double tamount) {
        this.tamount = tamount;
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

    /**
     * 获取对应的类别名
     * @param mContext
     * @return
     */
    public String getTypeText(Context mContext) {
        String name = "";
        switch (getType()) {
            case 1:
                name = mContext.getString(R.string.state_mingxi_type_tui);
                break;
            case 2:
                name = mContext.getString(R.string.state_mingxi_type_node);
                break;
            case 0:
            default:
                break;
        }
        return name;
    }
}
