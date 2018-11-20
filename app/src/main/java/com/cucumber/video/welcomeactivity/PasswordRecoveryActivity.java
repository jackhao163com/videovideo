package com.cucumber.video.welcomeactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PasswordRecoveryActivity extends Activity implements View.OnClickListener,TextWatcher {

    private EditText register_inviteNumber;
    private EditText register_phoneNumber;
    private EditText register_new_password;
    private ImageView mRegisterBtn;
    private EditText mFirstCheckNum;
    private ImageView iv_see_password;
    private ImageView mBack;
    private ImageView mGetCode;
    private EditText mSecondCheckNum;
    private EditText mThirdCheckNum;
    private EditText mFourthCheckNum;
    private CheckBox register_protocal;
    MyHandler myHandler;
    private LoadingDialog mLoadingDialog;
    private String mToken;
    private  final String urlPath = MyToolUtils.getAppDomain() + "index.php/font/app/updatepdGet";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordrecovery);
        initViews();
        setupEvents();
        myHandler = new MyHandler();
    }
    private void initViews() {
        mBack = findViewById(R.id.forget_back);

        mRegisterBtn = findViewById(R.id.forget_passwordfinish);
        register_phoneNumber = (EditText) findViewById(R.id.forget_login);
        register_new_password = (EditText) findViewById(R.id.forget_newpassword);
        //register_protocal = (CheckBox) findViewById(R.id.register_protocal);
        mGetCode = findViewById(R.id.forget_getcode);
        //iv_see_password = (ImageView) findViewById(R.id.register_see);
        mFirstCheckNum = findViewById(R.id.forget_checkone);
        mSecondCheckNum = findViewById(R.id.forget_checktwo);
        mThirdCheckNum = findViewById(R.id.forget_checkthree);
        mFourthCheckNum = findViewById(R.id.forget_checkfour);
        mFirstCheckNum.addTextChangedListener(this);
        mSecondCheckNum.addTextChangedListener(this);
        mThirdCheckNum.addTextChangedListener(this);
        mFourthCheckNum.addTextChangedListener(this);
        register_phoneNumber.addTextChangedListener(this);
    }
    private void setupEvents() {
        mBack.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
        //register_protocal.setOnCheckedChangeListener(this);
        mGetCode.setOnClickListener(this);
       // iv_see_password.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_passwordfinish:
                register(); //登陆
                break;
            case R.id.forget_back:
                startActivity(new Intent(PasswordRecoveryActivity.this, LoginActivity.class));
                finish();//关闭页面
                break;
            case R.id.forget_getcode:
               // setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
        }
    }
    public String getAccount() {
        return register_phoneNumber.getText().toString().trim();//去掉空格
    }
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PasswordRecoveryActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public String getPassword() {
        return register_new_password.getText().toString().trim();//去掉空格
    }
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.registering), false);
        }
        mLoadingDialog.show();
    }
    public void setLoginBtnClickable(boolean clickable) {
        Message msg = new Message();
        Bundle b = new Bundle();// 存放数据
        b.putBoolean("on", clickable);
        msg.setData(b);

        PasswordRecoveryActivity.this.myHandler.sendMessage(msg);
    }
    public Boolean saveUserInfo(String phoneNumber, String password ) {
        if (!phoneNumber.equals("") && !password.equals("")) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            helper.putValues(new SharedPreferencesUtils.ContentValue("name", phoneNumber));
            helper.putValues(new SharedPreferencesUtils.ContentValue("password",password));
            return true;
        }
        return false;

    }
    private   String appendCheckNum(){
        return mFirstCheckNum.getText().toString().trim() + mSecondCheckNum.getText().toString().trim() + mThirdCheckNum.getText().toString().trim() + mFourthCheckNum.getText().toString().trim();
    }
    private void register() {

        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
        if (getAccount().isEmpty()){
            showToast("你输入的账号为空！");
            return;
        }

        if (getPassword().isEmpty()){
            showToast("你输入的密码为空！");
            return;
        }
        if(!RegisterActivity.isEmail(getAccount())){
            showToast("帐号不符合格式请重新输入");
            return;
        }
        if(!RegisterActivity.isPassword(getPassword())){
            showToast("密码不合规范，请重新输入");
            return;
        }
        //登录一般都是请求服务器来判断密码是否正确，要请求网络，要子线程
        showLoading();//显示加载框
        Thread loginRunnable = new Thread() {

            @Override
            public void run() {
                super.run();
                setLoginBtnClickable(false);//点击登录后，设置登录按钮不可点击状态


                registerToServer();

                setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                hideLoading();//隐藏加载框
            }
        };
        loginRunnable.start();


    }
    private int registerToServer() {

        //开始请求
        Request request = ItheimaHttp.newPostRequest("updatepd");//apiUrl格式："xxx/xxxxx"
        Map<String,Object> map = new HashMap<>();
        map.put("mobile", getAccount());
        map.put("newpassword", getPassword());
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<RegisterBean>() {

            @Override
            public void onResponse(RegisterBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " +bean);
                int status = bean.getStatus();
                //判断账号和密码
                switch (status){
                    case 1:
                    {
                        showToast("更新密码成功");
                        hideLoading();//隐藏加载框
                        saveToken("");
                        startActivity(new Intent(PasswordRecoveryActivity.this, LoginActivity.class));
                        finish();//关闭页面
                    }
                    break;
                    default:
                        showToast(bean.getMsg());
                    break;

                }

                setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                hideLoading();//隐藏加载框
            }


            /**
             * 可以不重写失败回调
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                System.out.println("print data fail");
            }
        });


        return 9;
    }

    private  void saveToken(String mToken){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        helper.putValues(new SharedPreferencesUtils.ContentValue("token", mToken));
    }
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,int after)
    {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
    }
    @Override
    public void afterTextChanged(Editable s)
    {
        if(s.toString().length() == 1)
        {
            if(mFirstCheckNum.isFocused())
            {
                mFirstCheckNum.clearFocus();
                mSecondCheckNum.setFocusable(true);
                mSecondCheckNum.setFocusableInTouchMode(true);
                mSecondCheckNum.requestFocus();
            }
            else if(mSecondCheckNum.isFocused())
            {
                mSecondCheckNum.clearFocus();
                mThirdCheckNum.setFocusable(true);
                mThirdCheckNum.setFocusableInTouchMode(true);
                mThirdCheckNum.requestFocus();
            }
            else if(mThirdCheckNum.isFocused())
            {
                mThirdCheckNum.clearFocus();
                mFourthCheckNum.setFocusable(true);
                mFourthCheckNum.setFocusableInTouchMode(true);
                mFourthCheckNum.requestFocus();
            }else if(mFourthCheckNum.isFocused())
            {
                mFourthCheckNum.clearFocus();
                register_new_password.setFocusable(true);
                register_new_password.setFocusableInTouchMode(true);
                register_new_password.requestFocus();
            }
        }
//        if(s.toString().length() == 11){
//            if(register_phoneNumber.isFocused()){
//                register_phoneNumber.clearFocus();
//                mFirstCheckNum.setFocusable(true);
//                mFirstCheckNum.setFocusableInTouchMode(true);
//                mFirstCheckNum.requestFocus();
//            }
//        }
    }
    class MyHandler extends Handler {
        public MyHandler() {}

        public MyHandler(Looper L) {
            super(L);
        }

        // 子类必须重写此方法,接受数据
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            Bundle b = msg.getData();
            Boolean ison = b.getBoolean("on");
            PasswordRecoveryActivity.this.mRegisterBtn.setClickable(ison);

        }
    }
}
