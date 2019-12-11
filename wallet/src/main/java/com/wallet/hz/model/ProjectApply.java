package com.wallet.hz.model;

import android.content.Context;

import com.wallet.hz.R;

import java.io.Serializable;

import share.exchange.framework.utils.ResourcesUtil;

/**
 * @ClassName: ProjectApply
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 23:49
 */
public class ProjectApply implements Serializable {


    /**
     * "baipishu":"123456789",
     *         "contents":"啊啊啊啊啊啊啊   啊啊啊",
     *         "createTime":"2019-11-07T17:01:09.000+0000",
     *         "emais":"null",
     *         "guanwang":"123456789",
     *         "id":3,
     *         "jiaoyisuo":"火币",
     *         "names":"测试项目",
     *         "status":1,   //1  审核中  2通过   3失败
     *         "type":1,   //1  代币   2  公链
     *         "userId":4
     */

    private String baipishu;
    private String contents;
    private String createTime;
    private String emais;
    private String guanwang;
    private int id;
    private String jiaoyisuo;
    private String names;
    private int status;
    private int type;
    private int userId;

    public String getBaipishu() {
        return baipishu;
    }

    public void setBaipishu(String baipishu) {
        this.baipishu = baipishu;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmais() {
        return emais;
    }

    public void setEmais(String emais) {
        this.emais = emais;
    }

    public String getGuanwang() {
        return guanwang;
    }

    public void setGuanwang(String guanwang) {
        this.guanwang = guanwang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJiaoyisuo() {
        return jiaoyisuo;
    }

    public void setJiaoyisuo(String jiaoyisuo) {
        this.jiaoyisuo = jiaoyisuo;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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

    /**
     * 获取项目审核状态文字
     * @param context
     */
    public String getStateText(Context context) {
        String text = "";
        switch (getStatus()) {
            case 1:
                text = context.getString(R.string.state_project_wait);
                break;
            case 2:
                text = context.getString(R.string.state_project_success);
                break;
            case 3:
                text = context.getString(R.string.state_project_failed);
                break;
            default:
                text = context.getString(R.string.state_project_wait);
                break;
        }
        return text;
    }

    /**
     * 获取项目审核状态文字颜色
     * @param context
     */
    public int getStateTextColor(Context context) {
         int text = 0;
        switch (getStatus()) {
            case 1:
                text = ResourcesUtil.getColor(context, R.color.color_yellow);
                break;
            case 2:
                text = ResourcesUtil.getColor(context, R.color.color_green);
                break;
            case 3:
                text = ResourcesUtil.getColor(context, R.color.tab_selected_color);
                break;
            default:
                text = ResourcesUtil.getColor(context, R.color.color_yellow);
                break;
        }
        return text;
    }
}
