package com.cucumber.video.welcomeactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,TextWatcher {
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
    private boolean isAgreed;
    private String mToken;
    private  final String urlPath = "http://hgmovie.joysw.win:82/index.php/font/app/register";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initViews();
        setupEvents();
        myHandler = new MyHandler();
    }
    private void initViews() {
        mBack = findViewById(R.id.login_back);

        mRegisterBtn = findViewById(R.id.register_btn);
        register_inviteNumber = (EditText) findViewById(R.id.register_invite);
        register_phoneNumber = (EditText) findViewById(R.id.register_phoneNumber);
        register_new_password = (EditText) findViewById(R.id.register_password_new);
        register_protocal = (CheckBox) findViewById(R.id.register_protocal);
        mGetCode = findViewById(R.id.register_get_code);
        iv_see_password = (ImageView) findViewById(R.id.register_see);
        mFirstCheckNum = findViewById(R.id.firstCheckNum);
        mSecondCheckNum = findViewById(R.id.secondCheckNum);
        mThirdCheckNum = findViewById(R.id.thirdCheckNum);
        mFourthCheckNum = findViewById(R.id.fourthCheckNum);

        mFirstCheckNum.addTextChangedListener(this);
        mSecondCheckNum.addTextChangedListener(this);
        mThirdCheckNum.addTextChangedListener(this);
        mFourthCheckNum.addTextChangedListener(this);
        register_phoneNumber.addTextChangedListener(this);
    }
    private void setupEvents() {
        mBack.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
        register_protocal.setOnCheckedChangeListener(this);
        mGetCode.setOnClickListener(this);
        iv_see_password.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                register(); //登陆
                break;
            case R.id.login_back:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();//关闭页面
                break;
            case R.id.login_see:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
        }
    }
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }
    public static boolean isPassword(String password){
        Pattern p = Pattern.compile("^([0-9a-zA-Z]){6,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }
    private   String appendCheckNum(){
        return mFirstCheckNum.getText().toString().trim() + mSecondCheckNum.getText().toString().trim() + mThirdCheckNum.getText().toString().trim() + mFourthCheckNum.getText().toString().trim();
    }
    private void register() {
        //showToast("checknum:"+appendCheckNum());
        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
        if (getAccount().isEmpty()){
            showToast("你输入的账号为空！");
            return;
        }

        if (getPassword().isEmpty()){
            showToast("你输入的密码为空！");
            return;
        }
        if(!isAgreed){
            showToast("请先同意用户协议！");
            return;
        }
        if(!isPhone(getAccount())){
            showToast("手机号不符合格式请重新输入");
            return;
        }
        if(!isPassword(getPassword())){
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


                //睡眠3秒
             //   try {
           //         Thread.sleep(3000);
          //      } catch (InterruptedException e) {
           //         e.printStackTrace();
           //     }

                //判断账号和密码
                switch (registerToServer()){
                    case 1:
                    {
                        showToast("注册成功");

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();//关闭页面
                    }
                    break;
                    case -3:
                    {
                        showToast("该手机号码已被注册，请登录");
                    }
                    break;
                    case -4:
                    {
                        showToast("验证码错误，请重新输入");
                    }
                    break;
                    case 9:
                    {
                        showToast("网络错误");
                    }
                    break;

                }

                setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                hideLoading();//隐藏加载框
            }
        };
        loginRunnable.start();


    }
    private int registerToServer() {
              /*   try {
                      new OkHttpClient()
                         HttpClient httpClient = new DefaultHttpClient();
                         String url = "http://203.191.101.122:3222/User/search/";
                         HttpPost httpPost = new HttpPost(url);
                         httpPost.setHeader("Content-Type", "application/json");

                         JSONObject obj = new JSONObject();

                         obj.put("id", "36215897");
                         obj.put("cc", "ad3256");
                         Log.e(Constants.TAG, obj.toString());
                         httpPost.setEntity(new StringEntity(obj.toString()));
                         HttpResponse httpResponse;

                         httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
                         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

                         httpResponse = httpClient.execute(httpPost);
                         int code = httpResponse.getStatusLine().getStatusCode();
                         if ( code == 200 ){
                                 String rev = EntityUtils.toString(httpResponse.getEntity());
                                 Log.e(Constants.TAG, "CODE-RESULT:"+rev);
                             }else{

                                 Log.e(Constants.TAG, "CODE:"+code);
                             }


                     } catch (Exception e) {

                         Log.e(Constants.TAG, "Exception:"+e.toString());
                     }*/

        URL url;
        try {
            url = new URL(urlPath);
            /*封装子对象*/
            JSONObject ClientKey = new JSONObject();
            ClientKey.put("mobile", getAccount());
            ClientKey.put("password", getPassword());
            ClientKey.put("rand_code", appendCheckNum());
            ClientKey.put("apptype", "android");

            /*封装Person数组*/
         //   JSONObject params = new JSONObject();
         //   params.put("Person", ClientKey);
            /*把JSON数据转换成String类型使用输出流向服务器写*/
            String content = String.valueOf(ClientKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);//设置允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
           // conn.setRequestProperty("Charset", encoding);
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
            /*服务器返回的响应码*/

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String retData = null;
                String responseData = "";
                while ((retData = in.readLine()) != null) {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                int status = jsonObject.getInt("status");
                if(status == 1)
                mToken = jsonObject.getString("token");
//System.out.println(result);
             //   String success = succObject.getString("id");

                in.close();
                return status;
//System.out.println(success);
              //  Toast.makeText(RegisterActivity.this, success, Toast.LENGTH_SHORT).show();
             //   Intent intentToLogin = new Intent();
             //   intentToLogin.setClass(Register.this, Login.class);
             //   startActivity(intentToLogin);
            //    finish();
            } else {
                Toast.makeText(getApplicationContext(), "服务器未响应", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
        return 9;
    }
    /**
     * 获取账号
     */
    public String getAccount() {
        return register_phoneNumber.getText().toString().trim();//去掉空格
    }
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
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

        RegisterActivity.this.myHandler.sendMessage(msg);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       /* if (buttonView == checkBox_password) {  //记住密码选框发生改变时
            if (!isChecked) {   //如果取消“记住密码”，那么同样取消自动登陆
                checkBox_login.setChecked(false);
            }
        } else if (buttonView == checkBox_login) {   //自动登陆选框发生改变时
            if (isChecked) {   //如果选择“自动登录”，那么同样选中“记住密码”
                checkBox_password.setChecked(true);
            }
        }*/
       isAgreed = isChecked;
    }
    private void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setImageResource(R.mipmap.un_see);
            //密码不可见
            register_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password.setImageResource(R.mipmap.see);
            //密码可见

            register_new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
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
        if(s.toString().length() == 11){
            if(register_phoneNumber.isFocused()){
                register_phoneNumber.clearFocus();
                mFirstCheckNum.setFocusable(true);
                mFirstCheckNum.setFocusableInTouchMode(true);
                mFirstCheckNum.requestFocus();
            }
        }
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
            RegisterActivity.this.mRegisterBtn.setClickable(ison);

        }
    }
}
