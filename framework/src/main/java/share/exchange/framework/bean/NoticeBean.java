package share.exchange.framework.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MMM on 2018/03/08.
 */
public class NoticeBean extends BaseBean implements Serializable {

    /**
     * status : 0
     * msg : success
     * data : [{"url":"/article/detail/139.html","title":"Coinbell关于网站全新改版上线的公告","create_time":"2018-05-14 10:58:22"},{"url":"/article/detail/138.html","title":"Coinbell关于市场奖励方案的公告","create_time":"2018-04-20 09:46:08"},{"url":"/article/detail/137.html","title":"Coinbell关于社区经营伙伴奖励方案的公告","create_time":"2018-04-18 14:51:10"},{"url":"/article/detail/136.html","title":"Coinbell关于开放6个交易对的通知","create_time":"2018-04-16 14:05:01"},{"url":"/article/detail/135.html","title":"LTC（莱特币）","create_time":"2018-04-12 11:18:49"},{"url":"/article/detail/134.html","title":"Coinbell关于开放3个币种充币的通知","create_time":"2018-04-12 11:06:25"},{"url":"/article/detail/130.html","title":"Coinbell关于上线3个币种和6个交易对的通知","create_time":"2018-04-11 11:11:46"},{"url":"/article/detail/120.html","title":"Coinbell关于发放交易分红的公告","create_time":"2018-03-22 16:01:08"},{"url":"/article/detail/118.html","title":"Coinbell关于开启实名认证转账的通知","create_time":"2018-03-21 10:52:49"},{"url":"/article/detail/99.html","title":"BITCNY交易简介及操作方法","create_time":"2018-01-20 12:11:07"},{"url":"/article/detail/86.html","title":"Coinbell交易平台上线公告","create_time":"2018-01-08 10:27:47"}]
     * run_time : 0.0088579654693604
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * url : /article/detail/139.html
         * title : Coinbell关于网站全新改版上线的公告
         * create_time : 2018-05-14 10:58:22
         */

        private String url;
        private String title;
        private String create_time;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
