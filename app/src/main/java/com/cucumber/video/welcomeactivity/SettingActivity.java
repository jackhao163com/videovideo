package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_back)
    ImageView settingBack;
    @BindView(R.id.huancun)
    TextView clearCache;
    @BindView(R.id.toXieYi)
    ImageView toXieYi;
    @BindView(R.id.toUserAccount)
    ImageView toUserAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);

        try {
            clearCache.setText(DataCleanManager.getCacheSize(getCacheDir()));//将获取到的大小set进去
        } catch (Exception e) {
            e.printStackTrace();
        }


        settingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                SettingActivity.this.finish();
            }
        });
        toUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, AccountActivity.class));
                SettingActivity.this.finish();
            }
        });
        toXieYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, XieYiActivity.class));
                SettingActivity.this.finish();
            }
        });
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDialog dialog = new MaterialDialog.Builder(view.getContext())
                        .title(R.string.setting_clearcache_title)
                        .content(R.string.setting_clearcache_confirm)
                        .positiveText(R.string.agree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                DataCleanManager.cleanInternalCache(SettingActivity.this);
                                DataCleanManager.cleanDatabases(SettingActivity.this);
                                DataCleanManager.cleanSharedPreference(SettingActivity.this);
                                clearCache.setText("0.0MB");
                            }
                        })
                        .show();
            }
        });
    }


    /**
     * 对内存进行计算和单位的转换
     *
     * @param size
     * @return
     * @throws Exception
     */

    public static String getFormatSize(double size) throws Exception {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}
