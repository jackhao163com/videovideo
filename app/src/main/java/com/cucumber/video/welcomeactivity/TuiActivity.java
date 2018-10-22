package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TuiActivity extends AppCompatActivity {
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.tomytui)
    TextView tomytui;
    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.textView_phone)
    TextView textViewPhone;
    @BindView(R.id.user_code)
    TextView userCode;
    @BindView(R.id.icon_erweima)
    ImageView iconErweima;
    @BindView(R.id.curlevelicon)
    ImageView curlevelicon;
    @BindView(R.id.curlevelname)
    TextView curlevelname;
    @BindView(R.id.levelnums)
    TextView levelnums;
    @BindView(R.id.nextlevelicon)
    ImageView nextlevelicon;
    @BindView(R.id.nextlevelname)
    TextView nextlevelname;
    @BindView(R.id.view_num)
    TextView viewNum;
    @BindView(R.id.remain_num)
    TextView remainNum;
    @BindView(R.id.totuiguang)
    Button totuiguang;

    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui);
        ButterKnife.bind(this);
        getToken();

        //开始请求
        Request request = ItheimaHttp.newGetRequest("getuserdetail?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<UserBean>() {

            @Override
            public void onResponse(UserBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                if(bean.getStatus() == 1){
                    UserBean.DataBean userinfo = bean.getData();
                    Picasso.with(TuiActivity.this)
                            .load(userinfo.getAvatar())
                            .into(userImage);
                    textViewPhone.setText(userinfo.getUsername());
                    userCode.setText(userinfo.getRand_code());
                    int[] levels = {R.mipmap.huizhang_rumen,R.mipmap.huizhang_rumen,R.mipmap.huizhang_jinjie,R.mipmap.huizhang_daren,R.mipmap.huizhang_zhuanjia,R.mipmap.huizhang_jiaoshou};
                    int level = Integer.parseInt(userinfo.getLevel().equals("")?"0":userinfo.getLevel());
                    try{
                        curlevelicon.setImageResource(levels[level]);
                        curlevelname.setText(userinfo.getLevelname());
                    }catch (Exception ex){

                    }

                    try
                    {
                        String nextname = userinfo.getNextlevelname().equals("") ? "" : userinfo.getNextlevelname().toString();
                        int nextlevel = Integer.parseInt(userinfo.getNextlevel().equals("")?"0":userinfo.getNextlevel().toString());
                        nextlevelicon.setImageResource(levels[nextlevel]);
                        nextlevelname.setText(nextname);
                    }catch (Exception ex){

                    }
                    viewNum.setText(userinfo.getViewnums());
                    remainNum.setText(userinfo.getRemainnums());

                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(TuiActivity.this)
                            .title("温馨提示")
                            .content(bean.getMsg())
                            .positiveText("确定")
                            .show();
                }

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

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TuiActivity.this.finish();
            }
        });

        totuiguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TuiActivity.this, TuiGuangActivity.class));
            }
        });

        tomytui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TuiActivity.this, TuiGuangActivity.class));
            }
        });
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }
}
