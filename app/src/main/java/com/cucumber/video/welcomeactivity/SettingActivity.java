package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

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
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog dialog = new MaterialDialog.Builder(view.getContext())
                        .title(R.string.setting_clearcache_title)
                        .content(R.string.setting_clearcache_content)
                        .positiveText(R.string.agree)
                        .show();
            }
        });
    }
}
