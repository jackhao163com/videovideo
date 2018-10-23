package com.cucumber.video.welcomeactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class YijianActivity extends AppCompatActivity{

    private ViewPager my_viewpager;
    private TabLayout my_tab;
    private ImageView gotoback;
    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_yijian);
        initView();
    }

    private void initView() {
        gotoback = (ImageView) findViewById(R.id.gotoback);
        gotoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SettingActivity.this, MainActivity.class));

                YijianActivity.this.finish();
            }
        });
        my_tab = (TabLayout) findViewById(R.id.my_tab);
        my_viewpager = (ViewPager) findViewById(R.id.my_viewpager);
        fragments = new ArrayList<>();       //碎片的集合
        fragments.add(new FragmentYijianSubmit());
        fragments.add(new FragmentMyYijian());

        titles = new ArrayList<>();     //标题的集合
        titles.add("意见反馈");
        titles.add("我的反馈");
        my_tab.setTabMode(TabLayout.MODE_FIXED);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        my_viewpager.setAdapter(adapter);
        my_tab.setupWithViewPager(my_viewpager);    //关联TabLayout和ViewPager

    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {     //适配器
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);  //返回碎片集合的第几项
        }

        @Override
        public int getCount() {
            return fragments.size();    //返回碎片集合大小
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);    //返回标题的第几项
        }
    }
}


