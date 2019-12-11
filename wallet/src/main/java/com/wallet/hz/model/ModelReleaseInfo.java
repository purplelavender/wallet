package com.wallet.hz.model;

import android.content.Context;

import com.wallet.hz.R;

import java.io.Serializable;

/**
 * @ClassName: ModelReleaseInfo
 * @Description: 模式收益记录信息
 * @Author: ZL
 * @CreateDate: 2019/11/04 11:08
 */
public class ModelReleaseInfo implements Serializable {

    /**
     * amount = 3000;  参与价值
     *             cdamount = 0;   赏金池
     *             coinname = "<null>";
     *             createTime = "2019-11-16 17:17:03";
     *             datas = "<null>";
     *             ends = "<null>";
     *             huilv = "<null>";
     *             id = "<null>";
     *             mobile = "<null>";
     *             sfamount = "9450.000094499999";   转入钱包
     *             status = "<null>";
     *             type = 1;  参与方式
     *             usdtsfamount = 0;    存储本金
     *             userId = "<null>";
     *             waamount = 300;   挖取数量
     *             xhamount = 0;   销毁池
     */

    private double amount;
    private double cdamount;
    private String coinname;
    private String createTime;
    private String datas;
    private String ends;
    private String mobile;
    private int id;
    private int status;
    private int type;
    private int userId;
    private double huilv;
    private double sfamount;
    private double usdtsfamount;
    private double waamount;
    private double xhamount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCdamount() {
        return cdamount;
    }

    public void setCdamount(double cdamount) {
        this.cdamount = cdamount;
    }

    public String getCoinname() {
        return coinname;
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

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getHuilv() {
        return huilv;
    }

    public void setHuilv(double huilv) {
        this.huilv = huilv;
    }

    public double getSfamount() {
        return sfamount;
    }

    public void setSfamount(double sfamount) {
        this.sfamount = sfamount;
    }

    public double getUsdtsfamount() {
        return usdtsfamount;
    }

    public void setUsdtsfamount(double usdtsfamount) {
        this.usdtsfamount = usdtsfamount;
    }

    public double getWaamount() {
        return waamount;
    }

    public void setWaamount(double waamount) {
        this.waamount = waamount;
    }

    public double getXhamount() {
        return xhamount;
    }

    public void setXhamount(double xhamount) {
        this.xhamount = xhamount;
    }

    /**
     * 获取参与方式
     * @param context
     * @return
     */
    public String getJoinTypeText(Context context) {
        String text = "";
        switch (getType()) {
            case 1:
                text = context.getString(R.string.state_jin_xi);
                break;
            case 2:
                text = context.getString(R.string.state_xi_no_jin);
                break;
            case 3:
                text = context.getString(R.string.state_no_xi_no_jin);
                break;
            default:
                text = context.getString(R.string.state_jin_xi);
                break;
        }
        return text;
    }

    /**
     * 获取参与方式图标
     * @param context
     * @return
     */
    public int getJoinTypeIcon(Context context) {
        int text = 0;
        switch (getType()) {
            case 1:
                text = R.drawable.icon_shouyi_lianbendaixi;
                break;
            case 2:
                text = R.drawable.icon_shouyi_chuxi;
                break;
            case 3:
                text = R.drawable.icon_shouyi_cunbencunxi;
                break;
            default:
                text = R.drawable.icon_shouyi_lianbendaixi;
                break;
        }
        return text;
    }
}
