package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class NoticeBean extends BasePageBean<NoticeBean.DataBean.ItemsBean>{


    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"id":"187","userid":"","formuserid":"","username":"","isread":"1","title":"悼开拓者老板，微软联合创始人保罗-艾伦先生4","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""},{"id":"185","userid":"","formuserid":"","username":"","isread":"1","title":"悼开拓者老板，微软联合创始人保罗-艾伦先生3","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""},{"id":"184","userid":"","formuserid":"","username":"","isread":"1","title":"悼开拓者老板，微软联合创始人保罗-艾伦先生2","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""}],"totalpage":2,"maxtime":1539881693}
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
         * items : [{"id":"187","userid":"","formuserid":"","username":"","isread":"1","title":"悼开拓者老板，微软联合创始人保罗-艾伦先生4","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""},{"id":"185","userid":"","formuserid":"","username":"","isread":"1","title":"悼开拓者老板，微软联合创始人保罗-艾伦先生3","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""},{"id":"184","userid":"","formuserid":"","username":"","isread":"1","title":"悼开拓者老板，微软联合创始人保罗-艾伦先生2","content":"在保护用户数据上，苹果一直都表现的很强势，这是不允许触碰的底线，即以用户数据安全为第一选择。现在，苹果官网上线了\"隐私页面\" 页面，具体来说应该是补充了更多这个内容的解释，并且苹果还畅谈了如何保障用户信息，具体来说有四点：","createtime":"1539151194","toname":""}]
         * totalpage : 2
         * maxtime : 1539881693
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
             * id : 187
             * userid :
             * formuserid :
             * username :
             * isread : 1
             * title : 悼开拓者老板，微软联合创始人保罗-艾伦先生4
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
}
