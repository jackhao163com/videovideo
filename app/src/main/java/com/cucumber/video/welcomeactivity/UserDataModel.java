package com.cucumber.video.welcomeactivity;

import java.util.List;

public class UserDataModel {
    public static  final int TYPE_TOP=1;
    public static  final int TYPE_HENGXIANG=2;
    public static final int TYPE_HISTORY=3;


    /**
     * status : 1
     * msg : 成功
     * data : {"userinfo":{"uid":"16","username":"18612345678","password":"e10adc3949ba59abbe56e057f20f883e","level":null,"nextlevel":null,"rand_code":"VumxcQ","apptype":"","email":null,"mobile":"18612345678","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":"http://puui.qpic.cn/media_img/0/null1515381699/0","idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"0","status":"1","token":"f1406ad365391d3fedbbf810f2315575","createtime":"1537685541","viewmum":"4"},"hlist":[{"id":"49","movieid":"4","moviename":"谜巢谜巢谜巢谜巢2","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0"},{"id":"48","movieid":"3","moviename":"一出好戏2","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg"},{"id":"47","movieid":"2","moviename":"谜巢谜巢谜巢谜巢","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0"}]}
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

    public List<DataBean.HlistBean> getHItemDatas() {
        return data.hlist;
    }

    public DataBean.UserinfoBean getUItemDatas() {
        return data.userinfo;
    }

    public static class DataBean {
        /**
         * userinfo : {"uid":"16","username":"18612345678","password":"e10adc3949ba59abbe56e057f20f883e","level":null,"nextlevel":null,"rand_code":"VumxcQ","apptype":"","email":null,"mobile":"18612345678","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":"http://puui.qpic.cn/media_img/0/null1515381699/0","idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"0","status":"1","token":"f1406ad365391d3fedbbf810f2315575","createtime":"1537685541","viewmum":"4"}
         * hlist : [{"id":"49","movieid":"4","moviename":"谜巢谜巢谜巢谜巢2","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0"},{"id":"48","movieid":"3","moviename":"一出好戏2","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/f/fgqtuu38z91hfyw_y.jpg"},{"id":"47","movieid":"2","moviename":"谜巢谜巢谜巢谜巢","cover":"http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0"}]
         */

        private UserinfoBean userinfo;
        private List<HlistBean> hlist;

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public List<HlistBean> getHlist() {
            return hlist;
        }

        public void setHlist(List<HlistBean> hlist) {
            this.hlist = hlist;
        }

        public static class UserinfoBean {
            /**
             * uid : 16
             * username : 18612345678
             * password : e10adc3949ba59abbe56e057f20f883e
             * level : null
             * nextlevel : null
             * rand_code : VumxcQ
             * apptype :
             * email : null
             * mobile : 18612345678
             * eastlongitude : null
             * northlatitude : null
             * gender : 1
             * avatar : http://puui.qpic.cn/media_img/0/null1515381699/0
             * idauthtype : 0
             * phoneauthtype : 0
             * skillauthtype : 0
             * realname : null
             * promuid : 0
             * status : 1
             * token : f1406ad365391d3fedbbf810f2315575
             * createtime : 1537685541
             * viewmum : 4
             */

            private String uid;
            private String username;
            private String password;
            private String level;
            private String levelname;
            private Object nextlevel;
            private String rand_code;
            private String apptype;
            private Object email;
            private String mobile;
            private Object eastlongitude;
            private Object northlatitude;
            private String gender;
            private String avatar;
            private String idauthtype;
            private String phoneauthtype;
            private String skillauthtype;
            private Object realname;
            private String promuid;
            private String status;
            private String token;
            private String createtime;
            private String viewmums;
            private String viewmum;
            private String remainnums;
            private String needcount;
            private String storagenums;
            private String likenums;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getLevelName() {
                return levelname;
            }

            public void setLevelName(String levelname) {
                this.levelname = levelname;
            }

            public Object getNextlevel() {
                return nextlevel;
            }

            public void setNextlevel(Object nextlevel) {
                this.nextlevel = nextlevel;
            }

            public String getRand_code() {
                return rand_code;
            }

            public void setRand_code(String rand_code) {
                this.rand_code = rand_code;
            }

            public String getApptype() {
                return apptype;
            }

            public void setApptype(String apptype) {
                this.apptype = apptype;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Object getEastlongitude() {
                return eastlongitude;
            }

            public void setEastlongitude(Object eastlongitude) {
                this.eastlongitude = eastlongitude;
            }

            public Object getNorthlatitude() {
                return northlatitude;
            }

            public void setNorthlatitude(Object northlatitude) {
                this.northlatitude = northlatitude;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getIdauthtype() {
                return idauthtype;
            }

            public void setIdauthtype(String idauthtype) {
                this.idauthtype = idauthtype;
            }

            public String getPhoneauthtype() {
                return phoneauthtype;
            }

            public void setPhoneauthtype(String phoneauthtype) {
                this.phoneauthtype = phoneauthtype;
            }

            public String getSkillauthtype() {
                return skillauthtype;
            }

            public void setSkillauthtype(String skillauthtype) {
                this.skillauthtype = skillauthtype;
            }

            public Object getRealname() {
                return realname;
            }

            public void setRealname(Object realname) {
                this.realname = realname;
            }

            public String getPromuid() {
                return promuid;
            }

            public void setPromuid(String promuid) {
                this.promuid = promuid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getViewmums() {
                return viewmums;
            }

            public void setViewmums(String viewmums) {
                this.viewmums = viewmums;
            }

            public String getViewmum() {
                return viewmum;
            }

            public void setViewmum(String viewmum) {
                this.viewmum = viewmum;
            }

            public String getRemainnums() {
                return remainnums;
            }

            public void setRemainnums(String remainnums) {
                this.remainnums = remainnums;
            }

            public String getLikenums() {
                return likenums;
            }

            public void setLikenums(String likenums) {
                this.likenums = likenums;
            }

            public String getNeedcount() {
                return needcount;
            }

            public void setNeedcount(String needcount) {
                this.needcount = needcount;
            }

            public String getStoragenums() {
                return storagenums;
            }

            public void setStoragenums(String storagenums) {
                this.storagenums = storagenums;
            }
        }

        public static class HlistBean {
            /**
             * id : 49
             * movieid : 4
             * moviename : 谜巢谜巢谜巢谜巢2
             * cover : http://puui.qpic.cn/videovert/0/vnewpictag_5_1376_1537538688559637_20817/0
             */

            private String id;
            private String movieid;
            private String moviename;
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

            public String getMovieName() {
                return moviename;
            }

            public void setMovieName(String moviename) {
                this.moviename = moviename;
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
