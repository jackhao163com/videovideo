package com.cucumber.video.welcomeactivity;

public class RegisterBean {

    /**
     * status : 1
     * msg : 登录成功！
     * token : 36a13f4a18d5e0bae1c43420b12f06fe
     * data : {"uid":"16","username":"18612345678","password":"e10adc3949ba59abbe56e057f20f883e","level":null,"apptype":"android","nextlevel":null,"rand_code":"123456","email":null,"mobile":"18612345678","eastlongitude":null,"northlatitude":null,"gender":"1","avatar":null,"idauthtype":"0","phoneauthtype":"0","skillauthtype":"0","realname":null,"promuid":"0","status":"1","token":"5b43e2d726495697e9253de6303d5178","createtime":"1537685488"}
     */

    private int status;
    private String msg;
    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 16
         * username : 18612345678
         * password : e10adc3949ba59abbe56e057f20f883e
         * level : null
         * apptype : android
         * nextlevel : null
         * rand_code : 123456
         * email : null
         * mobile : 18612345678
         * eastlongitude : null
         * northlatitude : null
         * gender : 1
         * avatar : null
         * idauthtype : 0
         * phoneauthtype : 0
         * skillauthtype : 0
         * realname : null
         * promuid : 0
         * status : 1
         * token : 5b43e2d726495697e9253de6303d5178
         * createtime : 1537685488
         */

        private String uid;
        private String username;
        private String password;
        private Object level;
        private String apptype;
        private Object nextlevel;
        private String rand_code;
        private Object email;
        private String mobile;
        private Object eastlongitude;
        private Object northlatitude;
        private String gender;
        private Object avatar;
        private String idauthtype;
        private String phoneauthtype;
        private String skillauthtype;
        private Object realname;
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

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public String getApptype() {
            return apptype;
        }

        public void setApptype(String apptype) {
            this.apptype = apptype;
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

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
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
    }
}
