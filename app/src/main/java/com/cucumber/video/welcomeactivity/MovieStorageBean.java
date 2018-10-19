package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class MovieStorageBean extends BasePageBean<MovieStorageBean.DataBean.ItemsBean>{

    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"id":"46","movieid":"1","name":"一出好戏","videosize":"0","size":"1024","type":"0","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg"}],"totalpage":1,"maxtime":1539933245}
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
         * items : [{"id":"46","movieid":"1","name":"一出好戏","videosize":"0","size":"1024","type":"0","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg"}]
         * totalpage : 1
         * maxtime : 1539933245
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
             * id : 46
             * movieid : 1
             * name : 一出好戏
             * videosize : 0
             * size : 1024
             * type : 0
             * cover : http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg
             */

            private String id;
            private String movieid;
            private String name;
            private String videosize;
            private String size;
            private String type;
            private String cover;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideosize() {
                return videosize;
            }

            public void setVideosize(String videosize) {
                this.videosize = videosize;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }
        }
    }
}
