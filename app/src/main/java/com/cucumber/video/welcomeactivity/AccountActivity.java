package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.setting_back)
    ImageView togoback;
    @BindView(R.id.user_img)
    CircleImageView userImg;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.gengdername)
    TextView gengdername;
    @BindView(R.id.genderImg)
    ImageView genderImg;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.editpd)
    ImageView editpd;
    @BindView(R.id.updateAvtar)
    ImageView updateAvtar;
    @BindView(R.id.updateNickname)
    ImageView updateNickname;
    @BindView(R.id.login_btn)
    ImageView logoutBtn;
    private String token;


    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。（生成bitmap貌似有时要报错？可试下把大小弄小点）
    private static int output_X = 60;
    private static int output_Y = 60;
    private Bitmap photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        getToken();
        initActivity();
        togoback.setOnClickListener(this);
        genderImg.setOnClickListener(this);
        editpd.setOnClickListener(this);
        updateAvtar.setOnClickListener(this);
        updateNickname.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    private void initActivity(){
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getUserDetail?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<UserBean>() {

            @Override
            public void onResponse(UserBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                if(bean.getStatus() == 1){
                    UserBean.DataBean data =  bean.getData();
                    if(!data.getAvatar().isEmpty()){
                        Picasso.with(AccountActivity.this)
                                .load(data.getAvatar())
                                .into(userImg);
                    }
                    nickname.setText(data.getUsername());
                    gengdername.setText(data.getGendername());
                    String gender = data.getGender();
                    if(gender.equals("1")){
                        genderImg.setImageResource(R.mipmap.female);
                    }
                    mobile.setText(data.getMobile());

                } else {
                    showToast(bean.getMsg());
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
    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AccountActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    private void updateAction(String key,String value) {
        //开始请求
        String url = "updateUserInfo" ;
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        if(!key.isEmpty()){
            map.put(key,value);
        }
        Request request = ItheimaHttp.newPostRequest(url);//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonBean>() {

            @Override
            public void onResponse(CommonBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                Toast.makeText(AccountActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
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
    }

    private void fileUpload(String data) {
        //开始请求
        String url = "fileUpload";
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        if(!data.isEmpty()){
            map.put("imgdata",data);
        }
        Request request = ItheimaHttp.newPostRequest(url);//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonBean>() {

            @Override
            public void onResponse(CommonBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                Toast.makeText(AccountActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onClick(View v) {
        Log.i("", "点击了---" + v.getId());
        Intent i;
        switch (v.getId()) {
            case R.id.editpd:
                i = new Intent(AccountActivity.this, PasswordRecoveryActivity.class);
                startActivity(i);
                break;
            case R.id.updateNickname:
                updateNickname();
                break;
            case R.id.genderImg:
                    updateGender();
                break;
            case R.id.updateAvtar:
                updateAvtar();
                break;
            case R.id.setting_back:
                AccountActivity.this.finish();
                break;
            case R.id.login_btn:
                i = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(i);
                AccountActivity.this.finish();
                break;
        }
    }

    private void updateNickname(){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("修改昵称")
                .content("2-20个字节，支持中文、英文、数字和下划线")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("昵称", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        updateAction("nickname", input.toString());
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .show();
    }
    private void updateGender(){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("修改性别")
                .content("性别只可以修改一次，设置后不可修改")
                .positiveText("确认")
                .negativeText("取消")
                .items(R.array.genderitems)
                .itemsIds(R.array.genderitemids)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int gender = view.getId();
                        updateAction("gender",gender+"");
                        return true;
                    }
                })
                .show();
    }

    private void updateAvtar(){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("修改头像")
                .negativeText("取消")
                .items(R.array.avtaritems)
                .itemsIds(R.array.genderitemids)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int select = view.getId();
                        if(select == 0){//相册选择
                            choseHeadImageFromGallery();
                        }else{//拍照
                            choseHeadImageFromCameraCapture();
                        }

                        return true;
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this,"取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(this,"没有SDCard!",Toast.LENGTH_LONG).show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            userImg.setImageBitmap(photo);
            fileUpload(getBase64(photo));
        }
    }

    // 将图片转换成base64编码
    public String getBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //压缩的质量为60%
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
        //生成base64字符
        String base = Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
        return base;
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK);
        // 设置文件类型
        intentFromGallery.setType("image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent("android.media.action.IMAGE_CAPTURE");

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

}
