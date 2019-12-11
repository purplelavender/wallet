package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: WorkOrder
 * @Description: 工单
 * @Author: ZL
 * @CreateDate: 2019/10/30 13:45
 */
public class WorkOrder implements Serializable {

    /**
     * "answerTime": "2019-10-30T02:59:39.275Z",
     * "contents": "string",
     * "createTime": "2019-10-30T02:59:39.275Z",
     * "id": 0,
     * "imgs": "string",
     * "kinds": 0,
     * "parentId": 0,
     * "status": 0,
     * "title": "string",
     * "type": 0,
     * "userId": 0
     */

    private String answerTime;
    private String contents;
    private String createTime;
    private int id;
    private String imgs;
    private int kinds;
    private int parentId;
    private int status;
    private String title;
    private int type;
    private int userId;

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getKinds() {
        return kinds;
    }

    public void setKinds(int kinds) {
        this.kinds = kinds;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 工单是否以结束
     *
     * @return
     */
    public boolean isEnd() {
        return getStatus() == 0;
    }

    /**
     * 当前工单是否是客服回复内容
     * @return
     */
    public boolean isCustom() {
        return getType() == 0;
    }
}
