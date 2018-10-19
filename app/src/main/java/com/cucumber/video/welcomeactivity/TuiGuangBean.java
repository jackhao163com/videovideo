package com.cucumber.video.welcomeactivity;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

public class TuiGuangBean extends BasePageBean<TuiGuangBean.DataBean.ItemsBean>{

    /**
     * status : 1
     * msg : 成功
     * data : {"items":[{"uid":"21","username":"15002052397","password":"e10adc3949ba59abbe56e057f20f883e","level":"6","nextlevel":null,"rand_code":"kZQjnc","apptype":null,"email":null,"mobile":"15002052397","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":null,"idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"16","status":"1","token":"7530ef4d63d388629f34dd681de62a41","createtime":"1539151194"},{"uid":"20","username":"15688883333","password":"e10adc3949ba59abbe56e057f20f883e","level":"5","nextlevel":null,"rand_code":"gWJA44","apptype":null,"email":null,"mobile":"15688883333","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":null,"idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"16","status":"1","token":"a634a9d1306e8c23a148f6ec70ee9789","createtime":"1539118151"},{"uid":"16","username":"刘德华","password":"e10adc3949ba59abbe56e057f20f883e","level":"1","nextlevel":null,"rand_code":"VumxcQ","apptype":"","email":null,"mobile":"18612345678","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":"http://hgmovie.joysw.win:82/Uploads/2018/10/12/e267f6f14945e43cc1e820ed627b4a1c.jpg","idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"16","status":"1","token":"f1406ad365391d3fedbbf810f2315575","createtime":"1537685541"}],"totalpage":1,"maxtime":1539933649}
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
         * items : [{"uid":"21","username":"15002052397","password":"e10adc3949ba59abbe56e057f20f883e","level":"6","nextlevel":null,"rand_code":"kZQjnc","apptype":null,"email":null,"mobile":"15002052397","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":null,"idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"16","status":"1","token":"7530ef4d63d388629f34dd681de62a41","createtime":"1539151194"},{"uid":"20","username":"15688883333","password":"e10adc3949ba59abbe56e057f20f883e","level":"5","nextlevel":null,"rand_code":"gWJA44","apptype":null,"email":null,"mobile":"15688883333","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":null,"idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"16","status":"1","token":"a634a9d1306e8c23a148f6ec70ee9789","createtime":"1539118151"},{"uid":"16","username":"刘德华","password":"e10adc3949ba59abbe56e057f20f883e","level":"1","nextlevel":null,"rand_code":"VumxcQ","apptype":"","email":null,"mobile":"18612345678","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":"http://hgmovie.joysw.win:82/Uploads/2018/10/12/e267f6f14945e43cc1e820ed627b4a1c.jpg","idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"16","status":"1","token":"f1406ad365391d3fedbbf810f2315575","createtime":"1537685541"}]
         * totalpage : 1
         * maxtime : 1539933649
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
             * uid : 21
             * username : 15002052397
             * password : e10adc3949ba59abbe56e057f20f883e
             * level : 6
             * nextlevel : null
             * rand_code : kZQjnc
             * apptype : null
             * email : null
             * mobile : 15002052397
             * eastlongitude : null
             * northlatitude : null
             * gender : 1
             * avatar : null
             * idauthtype : 0
             * phoneauthtype : 0
             * skillauthtype : 0
             * realname : null
             * promuid : 16
             * status : 1
             * token : 7530ef4d63d388629f34dd681de62a41
             * createtime : 1539151194
             */

            private String uid;
            private String username;
            private String password;
            private String level;
            private String mobile;
            private String gender;
            private String idauthtype;
            private String phoneauthtype;
            private String skillauthtype;
            private String promuid;
            private String status;
            private String token;
            private String createtime;

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


            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
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
        }
    }
}
