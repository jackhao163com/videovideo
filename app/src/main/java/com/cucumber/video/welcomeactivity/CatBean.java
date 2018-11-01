package com.cucumber.video.welcomeactivity;

import java.util.List;

public class CatBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","name":"娱乐","icon":"jiemu-icon.png","sequence":null,"cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-yule.png","ishot":"0","isdeleted":"0"},{"id":"2","name":"网络电影","icon":"jiemu-icon.png","sequence":null,"cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-wldy.png","ishot":"0","isdeleted":"0"},{"id":"3","name":"电视剧","icon":"jiemu-icon.png","sequence":null,"cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-dsj.png","ishot":"0","isdeleted":"0"},{"id":"4","name":"综艺","icon":"jiemu-icon.png","sequence":null,"cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-zongyi.png","ishot":"0","isdeleted":"0"},{"id":"5","name":"少儿","icon":"jiemu-icon.png","sequence":null,"cover":"http://hgmovie.joysw.win:82/uploads/others/zxpy2.png","ishot":"0","isdeleted":"0"},{"id":"6","name":"动漫","icon":"jiemu-icon.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"},{"id":"7","name":"片花","icon":"jiemu-icon.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"},{"id":"8","name":"全部","icon":"all.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"}]
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
         * name : 娱乐
         * icon : jiemu-icon.png
         * sequence : null
         * cover : http://hgmovie.joysw.win:82/uploads/others/fenlei-yule.png
         * ishot : 0
         * isdeleted : 0
         */

        private String id;
        private String name;
        private String icon;
        private Object sequence;
        private String cover;
        private String ishot;
        private String isdeleted;

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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getSequence() {
            return sequence;
        }

        public void setSequence(Object sequence) {
            this.sequence = sequence;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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
    }
}
