package com.cucumber.video.welcomeactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    @BindView(R.id.row_avtar)
    RelativeLayout rowAvtar;
    @BindView(R.id.row_nick)
    RelativeLayout rowNick;
    @BindView(R.id.row_sex)
    RelativeLayout rowSex;
    @BindView(R.id.row_pd)
    RelativeLayout rowPd;
    private String token;
    private Uri imageUri;
    private Uri mCutUri;
    private int curState = 1;


    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private static String path = "/sdcard/myHead/";// sd路径
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

        rowAvtar.setOnClickListener(this);
        rowNick.setOnClickListener(this);
        rowPd.setOnClickListener(this);
        rowSex.setOnClickListener(this);
    }

    private void initActivity() {
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getUserDetail?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<UserBean>() {

            @Override
            public void onResponse(UserBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                if (bean.getStatus() == 1) {
                    UserBean.DataBean data = bean.getData();
                    if (!data.getAvatar().isEmpty()) {
                        Picasso.with(AccountActivity.this)
                                .load(data.getAvatar())
                                .into(userImg);
                    }
                    nickname.setText(data.getUsername());
                    gengdername.setText(data.getGendername());
                    String gender = data.getGender();
                    if (gender.equals("1")) {
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

    private void updateAction(final String key, final String value) {
        //开始请求
        String url = "updateUserInfo";
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        if (!key.isEmpty()) {
            map.put(key, value);
        }
        Request request = ItheimaHttp.newPostRequest(url);//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonBean>() {

            @Override
            public void onResponse(CommonBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                switch (key) {
                    case "nickname":
                        nickname.setText(value);
                        break;
                    case "gender":
                        gengdername.setText(value.equals("1") ? "女" : "男");
                        genderImg.setImageResource(value.equals("1") ? R.mipmap.female : R.mipmap.male);
                        break;
                }
                Toast.makeText(AccountActivity.this, bean.getMsg(), Toast.LENGTH_LONG).show();
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
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        if (!data.isEmpty()) {
            map.put("imgdata", data);
        }
        Request request = ItheimaHttp.newPostRequest(url);//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonBean>() {

            @Override
            public void onResponse(CommonBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                Toast.makeText(AccountActivity.this, bean.getMsg(), Toast.LENGTH_LONG).show();
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
            case R.id.row_pd:
                i = new Intent(AccountActivity.this, PasswordRecoveryActivity.class);
                startActivity(i);
                break;
            case R.id.row_nick:
                updateNickname();
                break;
            case R.id.row_sex:
                updateGender();
                break;
            case R.id.row_avtar:
                updateAvtar();
                break;
            case R.id.setting_back:
                AccountActivity.this.finish();
                break;
            case R.id.login_btn:
                saveToken("");
                i = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(i);
                AccountActivity.this.finish();
                break;
        }
    }

    private void saveToken(String mToken) {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        helper.putValues(new SharedPreferencesUtils.ContentValue("token", mToken));
    }

    private void updateNickname() {
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

    private void updateGender() {
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
                        int gender = which;
                        updateAction("gender", gender + "");
                        return true;
                    }
                })
                .show();
    }

    private void updateAvtar() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("修改头像")
                .negativeText("取消")
                .items(R.array.avtaritems)
                .itemsIds(R.array.genderitemids)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int select = view.getId();
                        if (select == 0) {//相册选择
                            choseHeadImageFromGallery();
                        } else {//拍照
                            try {
                                requestPermissionCamera();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
            Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case 1:
                try {
                    curState = 1;
                    mCutUri = intent.getData();
                    requestPermission();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                if (hasSdcard()) {
                    CutForCamera();
                } else {
                    Toast.makeText(this, "没有SDCard!", Toast.LENGTH_LONG).show();
                }

                break;

            case 3:
                if (intent != null) {
                    try {
                        setImageToHeadView(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) throws IOException {

        Intent intent = new Intent("com.android.camera.action.CROP");
//设置裁剪之后的图片路径文件
        File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                "cutcamera.png"); //随便命名一个
        if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
            cutfile.delete();
        }
        cutfile.createNewFile();
        //初始化 uri
        Uri imageUri = uri; //返回来的 uri
        Uri outputUri = null; //真实的 uri
        Log.d("", "CutForPhoto: " + cutfile);
        outputUri = Uri.fromFile(cutfile);
        mCutUri = outputUri;
        Log.d("", "mCameraUri: " + mCutUri);
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", true);
        // aspectX,aspectY 是宽高的比例，这里设置正方形
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置要裁剪的宽高
        intent.putExtra("outputX", 40); //200dp
        intent.putExtra("outputY", 40);
        intent.putExtra("scale", true);
        //如果图片过大，会导致oom，这里设置为false
        intent.putExtra("return-data", false);
        if (imageUri != null) {
            intent.setDataAndType(imageUri, "image/*");
        }
        if (outputUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        }
        intent.putExtra("noFaceDetection", true);
        //压缩图片
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, 3);
    }

    /**
     * 拍照之后，启动裁剪
     *
     * @return
     */
    @NonNull
    private Intent CutForCamera() {
        try {
            String camerapath = AccountActivity.this.getExternalCacheDir().getPath();
            String imgname = "output.png";

            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = null; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
            File camerafile = new File(camerapath, imgname);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(AccountActivity.this,
                        "com.cucumber.video.welcomeactivity.fileprovider",
                        camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            //outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = imageUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, 3);
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;
    public static final int CAMERA_CODE = 11;

    public void requestPermission() throws IOException {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        } else {
            cropRawPhoto(mCutUri);
        }
    }

    public void requestPermissionCamera() throws IOException {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_CODE);
            }
        } else {
            choseHeadImageFromCameraCapture();
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
                    try {
                        cropRawPhoto(mCutUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
            case CAMERA_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    choseHeadImageFromCameraCapture();
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }


    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) throws FileNotFoundException {
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            photo = extras.getParcelable("data");
//            userImg.setImageBitmap(photo);
//            fileUpload(getBase64(photo));
//        }
        Bitmap photo = BitmapFactory.decodeStream(
                AccountActivity.this.getContentResolver().openInputStream(mCutUri));
        userImg.setImageBitmap(photo);
        fileUpload(getBase64(photo));
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
        // 设置文件类型
//        intentFromGallery.setType("image/*");
//        intentFromGallery.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);

        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);

    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
//        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cutcamera.png")));
//        startActivityForResult(intent2, 2);// 采用ForResult打开
        //创建一个file，用来存储拍照后的照片
        File outputfile = new File(AccountActivity.this.getExternalCacheDir(), "output.png");
        try {
            if (outputfile.exists()) {
                outputfile.delete();//删除
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageuri;
        if (Build.VERSION.SDK_INT >= 24) {
            imageuri = FileProvider.getUriForFile(AccountActivity.this,
                    "com.cucumber.video.welcomeactivity.fileprovider", //可以是任意字符串
                    outputfile);
        } else {
            imageuri = Uri.fromFile(outputfile);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, 2);
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
