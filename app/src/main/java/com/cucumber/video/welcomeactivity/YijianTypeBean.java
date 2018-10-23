package com.cucumber.video.welcomeactivity;

import java.util.List;

public class YijianTypeBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","name":"无法播放"},{"id":"180","name":"播放卡断"},{"id":"181","name":"标签错误"},{"id":"182","name":"分类错误"},{"id":"183","name":"搜索不准"},{"id":"184","name":"推荐不准"},{"id":"185","name":"无法下载"},{"id":"186","name":"视频不全"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 无法播放
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
