package com.cucumber.video.welcomeactivity;

import android.app.Application;
import android.os.StrictMode;

import com.itheima.retrofitutils.ItheimaHttp;

public class MyApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
//        ItheimaHttp.init(this, "https://www.oschina.net/");
        ItheimaHttp.init(this, "http://hgapi.qipaifun.co:82/Font/App/");
    }

}
