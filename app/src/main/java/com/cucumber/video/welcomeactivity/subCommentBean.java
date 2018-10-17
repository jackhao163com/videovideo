package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class subCommentBean extends BasePageBean<subCommentBean.DataBean.ItemsBean>{

    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"id":"183","movieid":"1","userid":"19","touserid":"16","parentid":"182","groupid":"182","content":"暗藏玄机黑黑","sequence":null,"likenum":"20","createtime":"1539118151","fromusername":"18612347777","tousername":"刘德华","avatar":"http://puui.qpic.cn/media_img/0/null1515381699/0","gender":"1"},{"id":"185","movieid":"1","userid":"17","touserid":"19","parentid":"183","groupid":"182","content":"暗藏玄机黑黑暗藏玄机黑黑","sequence":null,"likenum":"3","createtime":"1539118151","fromusername":"晃儿","tousername":"18612347777","avatar":"http://hgmovie.joysw.win:82/Uploads/2018/10/13/3a95c6ca47d8917b61017ab312199014.jpg","gender":"32767"}],"totalpage":2,"maxtime":1539779578}
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

    @Override
    public List<DataBean.ItemsBean> getItemDatas() {
        return data.items;
    }

    public static class DataBean {
        /**
         * items : [{"id":"183","movieid":"1","userid":"19","touserid":"16","parentid":"182","groupid":"182","content":"暗藏玄机黑黑","sequence":null,"likenum":"20","createtime":"1539118151","fromusername":"18612347777","tousername":"刘德华","avatar":"http://puui.qpic.cn/media_img/0/null1515381699/0","gender":"1"},{"id":"185","movieid":"1","userid":"17","touserid":"19","parentid":"183","groupid":"182","content":"暗藏玄机黑黑暗藏玄机黑黑","sequence":null,"likenum":"3","createtime":"1539118151","fromusername":"晃儿","tousername":"18612347777","avatar":"http://hgmovie.joysw.win:82/Uploads/2018/10/13/3a95c6ca47d8917b61017ab312199014.jpg","gender":"32767"}]
         * totalpage : 2
         * maxtime : 1539779578
         */

        private int totalpage;
        private int maxtime;
        private List<ItemsBean> items;

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getMaxtime() {
            return maxtime;
        }

        public void setMaxtime(int maxtime) {
            this.maxtime = maxtime;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 183
             * movieid : 1
             * userid : 19
             * touserid : 16
             * parentid : 182
             * groupid : 182
             * content : 暗藏玄机黑黑
             * sequence : null
             * likenum : 20
             * createtime : 1539118151
             * fromusername : 18612347777
             * tousername : 刘德华
             * avatar : http://puui.qpic.cn/media_img/0/null1515381699/0
             * gender : 1
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
            private String createtime;
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

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
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
}
