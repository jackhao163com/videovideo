package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class ActorListBean extends BasePageBean<ActorListBean.DataBean.ItemsBean> {

    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"id":"1","name":"刘德华","subtitle":"刘德华刘德华刘刘德华刘德华刘德华刘刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华德华德华刘德华","cover":"http://puui.qpic.cn/media_img/0/null1515381699/0","likenum":"20","areaid":"0","ishot":"1","createtime":"1567778000","sequence":"30","isdeleted":"0"},{"id":"2","name":"黄渤","subtitle":"黄渤黄渤黄渤黄渤黄渤黄渤","cover":"http://puui.qpic.cn/media_img/0/null1516009359/0","likenum":"230","areaid":"0","ishot":"1","createtime":"1567778000","sequence":"30","isdeleted":"0"},{"id":"3","name":"凡彬彬","subtitle":"刘德华刘德华刘德华刘德华","cover":"http://puui.qpic.cn/media_img/0/null1515381699/0","likenum":"20","areaid":"0","ishot":"1","createtime":"1567778000","sequence":"30","isdeleted":"0"}],"totalpage":4,"maxtime":1539147486}
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
         * items : [{"id":"1","name":"刘德华","subtitle":"刘德华刘德华刘刘德华刘德华刘德华刘刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华德华德华刘德华","cover":"http://puui.qpic.cn/media_img/0/null1515381699/0","likenum":"20","areaid":"0","ishot":"1","createtime":"1567778000","sequence":"30","isdeleted":"0"},{"id":"2","name":"黄渤","subtitle":"黄渤黄渤黄渤黄渤黄渤黄渤","cover":"http://puui.qpic.cn/media_img/0/null1516009359/0","likenum":"230","areaid":"0","ishot":"1","createtime":"1567778000","sequence":"30","isdeleted":"0"},{"id":"3","name":"凡彬彬","subtitle":"刘德华刘德华刘德华刘德华","cover":"http://puui.qpic.cn/media_img/0/null1515381699/0","likenum":"20","areaid":"0","ishot":"1","createtime":"1567778000","sequence":"30","isdeleted":"0"}]
         * totalpage : 4
         * maxtime : 1539147486
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
             * id : 1
             * name : 刘德华
             * subtitle : 刘德华刘德华刘刘德华刘德华刘德华刘刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华德华德华刘德华
             * cover : http://puui.qpic.cn/media_img/0/null1515381699/0
             * likenum : 20
             * areaid : 0
             * ishot : 1
             * createtime : 1567778000
             * sequence : 30
             * isdeleted : 0
             */

            private String id;
            private String name;
            private String subtitle;
            private String cover;
            private String likenum;
            private String areaid;
            private String ishot;
            private String createtime;
            private String sequence;
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

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getLikenum() {
                return likenum;
            }

            public void setLikenum(String likenum) {
                this.likenum = likenum;
            }

            public String getAreaid() {
                return areaid;
            }

            public void setAreaid(String areaid) {
                this.areaid = areaid;
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

            public String getSequence() {
                return sequence;
            }

            public void setSequence(String sequence) {
                this.sequence = sequence;
            }

            public String getIsdeleted() {
                return isdeleted;
            }

            public void setIsdeleted(String isdeleted) {
                this.isdeleted = isdeleted;
            }
        }
    }
}
