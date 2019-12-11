package com.wallet.hz.model;

import android.content.Context;

import com.wallet.hz.R;

import java.io.Serializable;

import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: CoinDetail
 * @Description: 货币详情对象
 * @Author: ZL
 * @CreateDate: 2019/11/07 15:53
 */
public class CoinDetail implements Serializable {

    /**
     *  address = 0x8563b9208139e517302a16a36708918321934075;  收款地址
     *             amount = "0.55";  金额
     *             coinname = pcs;
     *             createTime = "2019-11-09 20:51:30";
     *             fromaddress = 0x8563b9208139e517302a16a36708918321934075;  出款地址
     *             id = 2;
     *             kgfamount = "<null>";  矿工收益比例
     *             mobile = "<null>";
     *             status = 2;
     *             txash = 0x6ba3b73d4292410c66d1fec2d66e6e6436795ad01606ff9d9a48fb19d1ee87d7;
     *             txheight = 6741262;
     *             type = 2;   0全部 1收款 2转账
     *             userId = 189;
     */

    private String address;
    private String fromaddress;
    private double amount;
    private double kgfamount;
    private String coinname;
    private String createTime;
    private String mobile;
    private int id;
    private int status;
    private String txash;
    private String txheight;
    private int type;
    private int userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCoinname() {
        return StringUtil.isEmpty(coinname) ? "" : coinname.toUpperCase();
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

    public String getTxash() {
        return txash;
    }

    public void setTxash(String txash) {
        this.txash = txash;
    }

    public String getTxheight() {
        return txheight;
    }

    public void setTxheight(String txheight) {
        this.txheight = txheight;
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

    public String getFromaddress() {
        return fromaddress;
    }

    public void setFromaddress(String fromaddress) {
        this.fromaddress = fromaddress;
    }

    public double getKgfamount() {
        return kgfamount;
    }

    public void setKgfamount(double kgfamount) {
        this.kgfamount = kgfamount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 是否是收款
     * @return
     */
    public boolean isIn() {
        return getType() == 1;
    }

    /**
     * 获取类别名
     * @param context
     * @return
     */
    public String getTypeText(Context context) {
        String text = "";
        switch (getType()) {
            case 2:
                text = context.getString(R.string.wallet_transfer);
                break;
            case 1:
                text = context.getString(R.string.wallet_gathering);
                break;
                default:
                    break;
        }
        return text;
    }

    /**
     * 获取类别名
     * @param context
     * @return
     */
    public String getTypeSuccessText(Context context) {
        String text = "";
        switch (getType()) {
            case 2:
                text = context.getString(R.string.wallet_transfer_success);
                break;
            case 1:
                text = context.getString(R.string.wallet_gathering_success);
                break;
            default:
                break;
        }
        return text;
    }

    /**
     * 获取矿工费用
     * @return
     */
    public String getFeeAmount() {
        String text = "";
        switch (getType()) {
            case 2:
                text = BigDecimalUtils.mul(getAmount() + "", getKgfamount() + "");
                break;
            case 1:
                text = getAmount() + "";
                break;
            default:
                break;
        }
        return text;
    }

    /**
     * 获取实际到账金额
     * @return
     */
    public String getTrueAmount() {
        String text = "";
        switch (getType()) {
            case 2:
                text = BigDecimalUtils.sub(getAmount() + "", BigDecimalUtils.mul(getAmount() + "", getKgfamount() + ""));
                break;
            case 1:
                text = getAmount() + "";
                break;
            default:
                break;
        }
        return text;
    }
}
