package com.cucumber.video.welcomeactivity;

public class NoticeDetailBean {

    /**
     * status : 1
     * msg : 成功
     * data : {"id":"180","userid":"","formuserid":"","username":"","isread":"1","title":"苹果更新\u201c隐私页面\u201d 承诺保护用户数据安全性1","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 180
         * userid :
         * formuserid :
         * username :
         * isread : 1
         * title : 苹果更新“隐私页面” 承诺保护用户数据安全性1
         * content : 在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了"隐私页面" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：
         * createtime : 1539151194
         * toname :
         */

        private String id;
        private String userid;
        private String formuserid;
        private String username;
        private String isread;
        private String title;
        private String content;
        private String createtime;
        private String toname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFormuserid() {
            return formuserid;
        }

        public void setFormuserid(String formuserid) {
            this.formuserid = formuserid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIsread() {
            return isread;
        }

        public void setIsread(String isread) {
            this.isread = isread;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getToname() {
            return toname;
        }

        public void setToname(String toname) {
            this.toname = toname;
        }
    }
}
