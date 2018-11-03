package com.cucumber.video.welcomeactivity;

import java.util.List;

public class TagBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"8","tagname":"汽车","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-qiche.png","categoryid":"2","tag_type":"0","sequence":"8","ishot":"1","createtime":"1567778000","tag_code":"31"},{"id":"7","tagname":"健康","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-jiankang.png","categoryid":"1","tag_type":"0","sequence":"7","ishot":"1","createtime":"1567778000","tag_code":"30"},{"id":"6","tagname":"母婴","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-muying.png","categoryid":"2","tag_type":"0","sequence":"6","ishot":"1","createtime":"1567778000","tag_code":"31"},{"id":"5","tagname":"教育","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-jiaoyu.png","categoryid":"1","tag_type":"0","sequence":"5","ishot":"1","createtime":"1567778000","tag_code":"30"},{"id":"4","tagname":"公益","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-gongyi.png","categoryid":"2","tag_type":"0","sequence":"4","ishot":"1","createtime":"1567778000","tag_code":"31"},{"id":"13","tagname":"趣味","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-gongyi.png","categoryid":"2","tag_type":null,"sequence":"3","ishot":"0","createtime":null,"tag_code":null},{"id":"3","tagname":"军事","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-junshi.png","categoryid":"1","tag_type":"0","sequence":"3","ishot":"1","createtime":"1567778000","tag_code":"30"},{"id":"12","tagname":"奇幻","cover":"http://hgmovie.joysw.win:82/uploads/others/fenlei-gongyi.png","categoryid":"2","tag_type":"0","sequence":"2","ishot":"0","createtime":"1567778000","tag_code":"31"}]
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
         * id : 8
         * tagname : 汽车
         * cover : http://hgmovie.joysw.win:82/uploads/others/fenlei-qiche.png
         * categoryid : 2
         * tag_type : 0
         * sequence : 8
         * ishot : 1
         * createtime : 1567778000
         * tag_code : 31
         */

        private String id;
        private String tagname;
        private String cover;
        private String categoryid;
        private String tag_type;
        private String sequence;
        private String ishot;
        private String createtime;
        private String tag_code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTagname() {
            return tagname;
        }

        public void setTagname(String tagname) {
            this.tagname = tagname;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getTag_type() {
            return tag_type;
        }

        public void setTag_type(String tag_type) {
            this.tag_type = tag_type;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTag_code() {
            return tag_code;
        }

        public void setTag_code(String tag_code) {
            this.tag_code = tag_code;
        }
    }
}
