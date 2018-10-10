package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class MovieBean extends BasePageBean<MovieBean.DataBean.ItemsBean>{

    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"id":"4","name":"谜巢谜巢谜巢谜巢2","subtitle":"《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人","desc":"马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系\u2026","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0","path":"http://jiahui.joyvc.com/upload_file/video/test.mp4","actorlist":"","likenum":"2","views":"3","ishot":"1","createtime":"1567777888","sequence":"10","isdeleted":"0"},{"id":"5","name":"一出好戏3","subtitle":"《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人","desc":"马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系\u2026","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg","path":"http://jiahui.joyvc.com/upload_file/video/test.mp4","actorlist":"","likenum":"2","views":"3","ishot":"1","createtime":"1567777880","sequence":"10","isdeleted":"0"},{"id":"6","name":"谜巢谜巢谜巢谜巢6","subtitle":"《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人","desc":"马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系\u2026","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0","path":"http://jiahui.joyvc.com/upload_file/video/test.mp4","actorlist":"","likenum":"2","views":"3","ishot":"1","createtime":"1567777888","sequence":"10","isdeleted":"0"}],"totalpage":4,"maxtime":1539163004}
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
    public List<MovieBean.DataBean.ItemsBean> getItemDatas() {
        return data.items;
    }

    public static class DataBean {
        /**
         * items : [{"id":"4","name":"谜巢谜巢谜巢谜巢2","subtitle":"《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人","desc":"马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系\u2026","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0","path":"http://jiahui.joyvc.com/upload_file/video/test.mp4","actorlist":"","likenum":"2","views":"3","ishot":"1","createtime":"1567777888","sequence":"10","isdeleted":"0"},{"id":"5","name":"一出好戏3","subtitle":"《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人","desc":"马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系\u2026","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg","path":"http://jiahui.joyvc.com/upload_file/video/test.mp4","actorlist":"","likenum":"2","views":"3","ishot":"1","createtime":"1567777880","sequence":"10","isdeleted":"0"},{"id":"6","name":"谜巢谜巢谜巢谜巢6","subtitle":"《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人","desc":"马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系\u2026","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0","path":"http://jiahui.joyvc.com/upload_file/video/test.mp4","actorlist":"","likenum":"2","views":"3","ishot":"1","createtime":"1567777888","sequence":"10","isdeleted":"0"}]
         * totalpage : 4
         * maxtime : 1539163004
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
             * id : 4
             * name : 谜巢谜巢谜巢谜巢2
             * subtitle : 《我就是演员》是浙江卫视推出、腾讯视频独家播出的演技竞演类励志综艺，由章子怡、徐峥、吴秀波担任常驻导师，张国立担任推荐人
             * desc : 马进欠下债务，与远房表弟小兴在底层社会摸爬滚打，习惯性的买彩票，企图一夜爆富，并迎娶自己的同事姗姗。一日，公司全体员工出海团建，途中，马进收到了彩票中头奖的信息，六千万！就在马进狂喜自己翻身的日子终于到来之际，一场突如其来的滔天巨浪打破了一切。苏醒过来的众人发现身处荒岛，丧失了一切与外界的联系…
             * cover : http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0
             * path : http://jiahui.joyvc.com/upload_file/video/test.mp4
             * actorlist :
             * likenum : 2
             * views : 3
             * ishot : 1
             * createtime : 1567777888
             * sequence : 10
             * isdeleted : 0
             */

            private String id;
            private String name;
            private String subtitle;
            private String desc;
            private String cover;
            private String path;
            private String actorlist;
            private String likenum;
            private String views;
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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getActorlist() {
                return actorlist;
            }

            public void setActorlist(String actorlist) {
                this.actorlist = actorlist;
            }

            public String getLikenum() {
                return likenum;
            }

            public void setLikenum(String likenum) {
                this.likenum = likenum;
            }

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
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
