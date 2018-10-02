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
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,TextWatcher {
    private EditText et_name;
    private EditText et_password;
    private ImageView mLoginBtn;
    private CheckBox checkBox_password;
    private TextView passsword_Forget;
    private ImageView iv_see_password;
    private ImageView mBack;
    private TextView username_Login;
    private TextView user_Register;
    MyHandler myHandler;
    private  boolean defaultSee;
    private  final String urlPath = "http://hgmovie.joysw.win:82/index.php/font/app/login";

    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private  String mToken;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_login);
        initViews();
        setupEvents();
        initData();
        myHandler = new MyHandler();
        defaultSee = true;
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
        if(s.toString().length() == 11){
            if(et_name.isFocused()){
                et_name.clearFocus();
                et_password.setFocusable(true);
                et_password.setFocusableInTouchMode(true);
                et_password.requestFocus();
            }
        }
    }
    private void initData() {


        //判断用户第一次登陆
        if (firstLogin()) {
            checkBox_password.setChecked(false);//取消记住密码的复选框
            //checkBox_login.setChecked(false);//取消自动登录的复选框
        }
        //判断是否记住密码
        if (remenberPassword()) {
            checkBox_password.setChecked(true);//勾选记住密码
            setTextNameAndPassword();//把密码和账号输入到输入框中
        } else {
            setTextName();//把用户账号放到输入账号的输入框中
        }

        //判断是否自动登录
       /* if (autoLogin()) {
            checkBox_login.setChecked(true);
            login();//去登录就可以

        }*/
    }

    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        et_name.setText("" + getLocalName());
        et_password.setText("" + getLocalPassword());
    }

    /**
     * 设置数据到输入框中
     */
    public void setTextName() {
        et_name.setText("" + getLocalName());
    }


    /**
     * 获得保存在本地的用户名
     */
    public String getLocalName() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String name = helper.getString("name");
        return name;
    }


    /**
     * 获得保存在本地的密码
     */
    public String getLocalPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        return password;   //解码一下
//       return password;   //解码一下

    }

    /**
     * 判断是否自动登录
     */
    private boolean autoLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean autoLogin = helper.getBoolean("autoLogin", false);
        return autoLogin;
    }

    /**
     * 判断是否记住密码
     */
    private boolean remenberPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean remenberPassword = helper.getBoolean("remenberPassword", false);
        return remenberPassword;
    }


    private void initViews() {
        mBack = findViewById(R.id.login_back);

        mLoginBtn = findViewById(R.id.login_btn);
        et_name = (EditText) findViewById(R.id.mobile_login);
        et_password = (EditText) findViewById(R.id.mobile_password);
        checkBox_password = (CheckBox) findViewById(R.id.password_remember);
        passsword_Forget =  findViewById(R.id.password_forget);
        iv_see_password = (ImageView) findViewById(R.id.login_see);
        username_Login = findViewById(R.id.username_login);
        user_Register = findViewById(R.id.register);
    }

    private void setupEvents() {
        mBack.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        checkBox_password.setOnCheckedChangeListener(this);
        passsword_Forget.setOnClickListener(this);
        iv_see_password.setOnClickListener(this);
        user_Register.setOnClickListener(this);
        username_Login.setOnClickListener(this);
        et_name.addTextChangedListener(this);
    }

    /**
     * 判断是否是第一次登陆
     */
    private boolean firstLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean first = helper.getBoolean("first", true);
        if (first) {
            //创建一个ContentVa对象（自定义的）设置不是第一次登录，,并创建记住密码和自动登录是默认不选，创建账号和密码为空
            helper.putValues(new SharedPreferencesUtils.ContentValue("first", false),
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("token",""),
                    new SharedPreferencesUtils.ContentValue("name", ""),
                    new SharedPreferencesUtils.ContentValue("password", ""));

            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                loadUserName();    //无论如何保存一下用户名
                login(); //登陆
                break;
            case R.id.login_see:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
            case R.id.login_back:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();//关闭页面
                break;
            case R.id.password_forget:
                startActivity(new Intent(LoginActivity.this, PasswordRecoveryActivity.class));
                finish();//关闭页面
                break;
            case R.id.username_login:
                startActivity(new Intent(LoginActivity.this, UserNameLoginActivity.class));
                finish();//关闭页面
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
              //  overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                finish();//关闭页面
                break;
        }
    }

    /**
     * 模拟登录情况
     * 用户名csdn，密码123456，就能登录成功，否则登录失败
     */
    private void login() {

        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
        if (getAccount().isEmpty()){
            showToast("你输入的账号为空！");
            return;
        }

        if (getPassword().isEmpty()){
            showToast("你输入的密码为空！");
            return;
        }
        if(!RegisterActivity.isPhone(getAccount())){
            showToast("手机号不符合格式请重新输入");
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


                switch (registerToServer()){
                    case 1:
                    {
                        showToast("登录成功");

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();//关闭页面
                    }
                    break;
                    case -3:
                    {
                        showToast("您被禁止登录");
                    }
                    break;
                    case -2:
                    {
                        showToast("手机号或密码错误");
                    }
                    break;
                    case -1:
                    {
                        showToast("传入的参数有误");
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
            ClientKey.put("username", getAccount());
            ClientKey.put("password", getPassword());
         //   ClientKey.put("rand_code", appendCheckNum());
         //   ClientKey.put("apptype", "android");

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
         //return conn.getResponseCode();
         /*   if (code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
                String retData = null;
                String responseData = "";
                while ((retData = in.readLine()) != null) {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject succObject = jsonObject.getJSONObject("regsucc");
//System.out.println(result);
                String success = succObject.getString("id");

                in.close();
//System.out.println(success);
                Toast.makeText(Register.this, success, Toast.LENGTH_SHORT).show();
                Intent intentToLogin = new Intent();
                intentToLogin.setClass(Register.this, Login.class);
                startActivity(intentToLogin);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "数据提交失败", Toast.LENGTH_SHORT).show();
            }*/
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String retData = null;
                String responseData = "";
                while ((retData = in.readLine()) != null) {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                int status = jsonObject.getInt("status");
                if(status == 1){
                    mToken = jsonObject.getString("token");
                    saveToken();
                }
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
private  void saveToken(){
    SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
    helper.putValues(new SharedPreferencesUtils.ContentValue("token", mToken));
}
    /**
     * 保存用户账号
     */
    public void loadUserName() {
        if (!getAccount().equals("") || !getAccount().equals("请输入您的手机号")) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            helper.putValues(new SharedPreferencesUtils.ContentValue("name", getAccount()));
        }

    }

    /**
     * 设置密码可见和不可见的相互转换
     */
    private void setPasswordVisibility() {
        if (defaultSee) {
            iv_see_password.setImageResource(R.mipmap.un_see);
            //密码不可见
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            defaultSee = false;

        } else {
            iv_see_password.setImageResource(R.mipmap.see);
            //密码可见

            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            defaultSee = true;
        }

    }

    /**
     * 获取账号
     */
    public String getAccount() {
        return et_name.getText().toString().trim();//去掉空格
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return et_password.getText().toString().trim();//去掉空格
    }


    /**
     * 保存用户选择“记住密码”和“自动登陆”的状态
     */
    private void loadCheckBoxState() {
        loadCheckBoxState(checkBox_password);
    }

    /**
     * 保存按钮的状态值
     */
    public void loadCheckBoxState(CheckBox checkBox_password) {

        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");

        //如果设置自动登录
       /* if (checkBox_login.isChecked()) {
            //创建记住密码和自动登录是都选择,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", true),
                    new SharedPreferencesUtils.ContentValue("password", Base64Utils.encryptBASE64(getPassword())));

        } else */
       if (!checkBox_password.isChecked()) { //如果没有保存密码，那么自动登录也是不选的
            //创建记住密码和自动登录是默认不选,密码为空
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", ""));
        } else if (checkBox_password.isChecked()) {   //如果保存密码，没有自动登录
            //创建记住密码为选中和自动登录是默认不选,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password",getPassword()));
        }
    }

    /**
     * 是否可以点击登录按钮
     *
     * @param clickable
     */
    public void setLoginBtnClickable(boolean clickable) {
        Message msg = new Message();
        Bundle b = new Bundle();// 存放数据
        b.putBoolean("on", clickable);
        msg.setData(b);

        LoginActivity.this.myHandler.sendMessage(msg);
    }


    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
        }
        mLoadingDialog.show();
    }


    /**
     * 隐藏加载的进度框
     */
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


    /**
     * CheckBox点击时的回调方法 ,不管是勾选还是取消勾选都会得到回调
     *
     * @param buttonView 按钮对象
     * @param isChecked  按钮的状态
     */
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
    }


    /**
     * 监听回退键
     */
    @Override
    public void onBackPressed() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            } else {
                finish();
            }
        } else {
            finish();
        }

    }

    /**
     * 页面销毁前回调的方法
     */
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
        super.onDestroy();
    }


    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

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
            LoginActivity.this.mLoginBtn.setClickable(ison);

}
}


}
