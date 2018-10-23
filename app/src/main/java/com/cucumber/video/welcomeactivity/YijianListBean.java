package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class YijianListBean extends BasePageBean<YijianListBean.DataBean.ItemsBean>{

    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"id":"180","content":"dddddd","typename":"无法播放"}],"totalpage":1,"maxtime":1540271754}
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
         * items : [{"id":"180","content":"dddddd","typename":"无法播放"}]
         * totalpage : 1
         * maxtime : 1540271754
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
             * id : 180
             * content : dddddd
             * typename : 无法播放
             */

            private String id;
            private String content;
            private String typename;
            private String createtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }
        }
    }
}
