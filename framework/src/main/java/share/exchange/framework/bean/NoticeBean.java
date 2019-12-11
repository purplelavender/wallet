package share.exchange.framework.bean;

import java.io.Serializable;

/**
 * Created by MMM on 2018/03/08.
 */
public class NoticeBean implements Serializable {

    /**
     * "contents": "string",
     *   "createTime": "2019-10-31T02:55:58.723Z",
     *   "id": 0,
     *   "title": "string"
     */

    private String contents;
    private String title;
    private String createTime;
    private String id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 是否已读
     * @return
     */
    public boolean isRead() {
        return false;
    }
}
