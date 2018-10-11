package com.cucumber.video.welcomeactivity;

import java.util.List;

public class ChannelBean {
    public static  final int TYPE_TUIJIAN =1;
    public static  final int TYPE_BANNER=2;
    public static final int TYPE_HOT=3;
    public static final int TYPE_LIKE=4;
    /**
     * status : 1
     * msg : 成功
     * data : {"clist":[{"id":"1","name":"娱乐","icon":"jiemu-icon.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"},{"id":"2","name":"网络电影","icon":"jiemu-icon.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"},{"id":"3","name":"电视剧","icon":"jiemu-icon.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"},{"id":"4","name":"综艺","icon":"jiemu-icon.png","sequence":null,"cover":"http://s.amazeui.org/media/i/demos/bing-1.jpg","ishot":"0","isdeleted":"0"}],"tlist":[{"id":"2","tagname":"黄渤","cover":"http://puui.qpic.cn/media_img/0/null1516009359/0","categoryid":"2","tag_type":"0","sequence":"2","ishot":"1","createtime":"1567778000","tag_code":"31"},{"id":"1","tagname":"刘德华","cover":"http://puui.qpic.cn/media_img/0/null1515381699/0","categoryid":"1","tag_type":"0","sequence":"1","ishot":"0","createtime":"1567778000","tag_code":"30"}],"llist":[{"id":"2","tagname":"黄渤","cover":"http://puui.qpic.cn/media_img/0/null1516009359/0","categoryid":"2","tag_type":"0","sequence":"2","ishot":"1","createtime":"1567778000","tag_code":"31"},{"id":"1","tagname":"刘德华","cover":"http://puui.qpic.cn/media_img/0/null1515381699/0","categoryid":"1","tag_type":"0","sequence":"1","ishot":"0","createtime":"1567778000","tag_code":"30"}],"blist":[{"id":"1","name":"name1","url":"http://s.amazeui.org/media/i/demos/bing-1.jpg","type":"channel","sequence":"1","isdeleted":"0"},{"id":"2","name":"name2","url":"http://s.amazeui.org/media/i/demos/bing-2.jpg","type":"channel","sequence":"2","isdeleted":"0"},{"id":"5","name":"name2","url":"http://s.amazeui.org/media/i/demos/bing-2.jpg","type":"channel","sequence":"2","isdeleted":"0"}]}
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

    public List<DataBean.ClistBean> getCItemDatas() {
        return data.clist;
    }
    public List<DataBean.TlistBean> getTItemDatas() {
        return data.tlist;
    }
    public List<DataBean.LlistBean> getLItemDatas() {
        return data.llist;
    }
    public List<DataBean.BlistBean> getBItemDatas() {
        return data.blist;
    }
    public static class DataBean {
        private List<ClistBean> clist;
        private List<TlistBean> tlist;
        private List<LlistBean> llist;
        private List<BlistBean> blist;

        public List<ClistBean> getClist() {
            return clist;
        }

        public void setClist(List<ClistBean> clist) {
            this.clist = clist;
        }

        public List<TlistBean> getTlist() {
            return tlist;
        }

        public void setTlist(List<TlistBean> tlist) {
            this.tlist = tlist;
        }

        public List<LlistBean> getLlist() {
            return llist;
        }

        public void setLlist(List<LlistBean> llist) {
            this.llist = llist;
        }

        public List<BlistBean> getBlist() {
            return blist;
        }

        public void setBlist(List<BlistBean> blist) {
            this.blist = blist;
        }

        public static class ClistBean {
            /**
             * id : 1
             * name : 娱乐
             * icon : jiemu-icon.png
             * sequence : null
             * cover : http://s.amazeui.org/media/i/demos/bing-1.jpg
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

        public static class TlistBean {
            /**
             * id : 2
             * tagname : 黄渤
             * cover : http://puui.qpic.cn/media_img/0/null1516009359/0
             * categoryid : 2
             * tag_type : 0
             * sequence : 2
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

        public static class LlistBean {
            /**
             * id : 2
             * tagname : 黄渤
             * cover : http://puui.qpic.cn/media_img/0/null1516009359/0
             * categoryid : 2
             * tag_type : 0
             * sequence : 2
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

        public static class BlistBean {
            /**
             * id : 1
             * name : name1
             * url : http://s.amazeui.org/media/i/demos/bing-1.jpg
             * type : channel
             * sequence : 1
             * isdeleted : 0
             */

            private String id;
            private String name;
            private String url;
            private String type;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
