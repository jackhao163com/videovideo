package com.cucumber.video.welcomeactivity;

public class CommonCommentBean {

    /**
     * status : 1
     * msg : 新增成功了！！！
     * data : {"id":"190","movieid":"1","userid":"17","touserid":"20","parentid":"184","groupid":"184","content":"网络模块","sequence":null,"likenum":"0","createtime":null,"fromusername":"晃儿","tousername":"15688883333","avatar":"http://hgmovie.joysw.win:82/Uploads/2018/10/13/3a95c6ca47d8917b61017ab312199014.jpg","gender":"32767"}
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
         * id : 190
         * movieid : 1
         * userid : 17
         * touserid : 20
         * parentid : 184
         * groupid : 184
         * content : 网络模块
         * sequence : null
         * likenum : 0
         * createtime : null
         * fromusername : 晃儿
         * tousername : 15688883333
         * avatar : http://hgmovie.joysw.win:82/Uploads/2018/10/13/3a95c6ca47d8917b61017ab312199014.jpg
         * gender : 32767
         */

        private String id;
        private String movieid;
        private String userid;
        private String touserid;
        private String parentid;
        private String groupid;
        private String content;
        private Object sequence;
        private String likenum;
        private Object createtime;
        private String fromusername;
        private String tousername;
        private String avatar;
        private String gender;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMovieid() {
            return movieid;
        }

        public void setMovieid(String movieid) {
            this.movieid = movieid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTouserid() {
            return touserid;
        }

        public void setTouserid(String touserid) {
            this.touserid = touserid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getSequence() {
            return sequence;
        }

        public void setSequence(Object sequence) {
            this.sequence = sequence;
        }

        public String getLikenum() {
            return likenum;
        }

        public void setLikenum(String likenum) {
            this.likenum = likenum;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }

        public String getFromusername() {
            return fromusername;
        }

        public void setFromusername(String fromusername) {
            this.fromusername = fromusername;
        }

        public String getTousername() {
            return tousername;
        }

        public void setTousername(String tousername) {
            this.tousername = tousername;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
