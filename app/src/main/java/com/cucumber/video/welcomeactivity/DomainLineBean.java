package com.cucumber.video.welcomeactivity;

import java.util.List;

public class DomainLineBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","name":"百度线路","path":"http://www.baidu.com","sequence":"1","ishot":"0","isdeleted":"0","ischecked":false},{"id":"2","name":"谷歌线路","path":"http://www.google.com","sequence":"2","ishot":"0","isdeleted":"0","ischecked":true}]
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
         * name : 百度线路
         * path : http://www.baidu.com
         * sequence : 1
         * ishot : 0
         * isdeleted : 0
         * ischecked : false
         */

        private String id;
        private String name;
        private String path;
        private String sequence;
        private String ishot;
        private String isdeleted;
        private boolean ischecked;

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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getIshot() {
            return ishot;
        }

        public void setIshot(String ishot) {
            this.ishot = ishot;
        }

        public String getIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(String isdeleted) {
            this.isdeleted = isdeleted;
        }

        public boolean isIschecked() {
            return ischecked;
        }

        public void setIschecked(boolean ischecked) {
            this.ischecked = ischecked;
        }
    }
}
