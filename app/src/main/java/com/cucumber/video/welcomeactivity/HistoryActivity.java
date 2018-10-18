package com.cucumber.video.welcomeactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ViewPager my_viewpager;
    private TabLayout my_tab;
    private ImageView gotoback;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_history);
        initView();
    }

    private void initView() {
        gotoback = (ImageView) findViewById(R.id.gotoback);
        gotoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                HistoryActivity.this.finish();
            }
        });
        my_tab = (TabLayout) findViewById(R.id.my_tab);
        my_viewpager = (ViewPager) findViewById(R.id.my_viewpager);
        fragments = new ArrayList<>();       //碎片的集合
        fragments.add(new FragmentToday());
        fragments.add(new FragmentWeek());
        fragments.add(new FragmentAgo());

        titles = new ArrayList<>();     //标题的集合
        titles.add("今日");
        titles.add("七日");
        titles.add("更早");
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
