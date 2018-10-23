package com.cucumber.video.welcomeactivity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TuiMaActivity extends AppCompatActivity {
    @BindView(R.id.erweima_save)
    ImageView erweimaSave;
    @BindView(R.id.erweima_copy)
    ImageView erweimaCopy;
    @BindView(R.id.setting_back)
    ImageView setting;
    @BindView(R.id.erweima)
    ImageView erweima;
    @BindView(R.id.code)
    TextView code;
    private String token;
    private String tuicode;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private  Bitmap finalBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erweima);
        ButterKnife.bind(this);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        getToken();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TuiMaActivity.this.finish();
            }
        });

        String content = "http://hgweb.joysw.win:82/#/movielist";
        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtils.create2DCode(content);
            erweima.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //开始请求
        Request request = ItheimaHttp.newGetRequest("getuserdetail?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<UserBean>() {

            @Override
            public void onResponse(UserBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                if(bean.getStatus() == 1){
                    UserBean.DataBean userinfo = bean.getData();
                    code.setText(userinfo.getRand_code().toUpperCase());
                    tuicode = userinfo.getRand_code();

                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(TuiMaActivity.this)
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

        finalBitmap = bitmap;
        erweimaSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestPermission();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        erweimaCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String copytext = "1、黄瓜视频App，永久免费在线观看，无需翻墙！ \n" +
                       "\n" +
                       "2、超快加载，海量正版高清片源，支持后台缓存下载！ \n" +
                       "\n" +
                       "3、使用推广码 XXXXX 还可以获取专属观影特权 \n" +
                       "\n" +
                       "下载请戳我， ↓↓↓ ↓↓ http://hgmovie.joysw.win:82/uploads/apk/huanggua.apk?inviteCode="+tuicode;
               copy(view,copytext);

            }
        });
    }

    private void  saveErweima(){
        try {
            ImgUtils.saveImageToGallery(TuiMaActivity.this, finalBitmap);
        } catch (Exception ex)
        {

        }
    }
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    public void requestPermission() throws IOException {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"please give me the permission",Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        } else{
            saveErweima();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    saveErweima();
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }
    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    public void copy(View view,String copyField){
        String text = copyField.toString();
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Text Copied",
                Toast.LENGTH_SHORT).show();
    }
}
