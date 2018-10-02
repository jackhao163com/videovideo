package com.cucumber.video.welcomeactivity;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private Activity mActivity; // 上下文
    private List<View> mListViews; // 图片组
    public MyPagerAdapter(){
    }
    public MyPagerAdapter(Activity mActivity,List<View> mListViews){
        this.mActivity=mActivity;
        this.mListViews=mListViews;
    }
    public int getCount() {
        if (mListViews.size() == 1) {// 一张图片时不用流动
            return mListViews.size();
        }
        return Integer.MAX_VALUE;
    }
    /**
     返回List中的图片元素装载到控件中
     */
    @Override
    public Object instantiateItem(View v, int i) {

        if (((ViewPager) v).getChildCount() == mListViews.size()) {
            ((ViewPager) v).removeView(mListViews.get(i % mListViews.size()));
        }
          ViewGroup mParent  =  (ViewGroup) mListViews.get(i % mListViews.size()).getParent();
        if(mParent != null) {
            mParent.removeAllViews();
        }
        ((ViewPager) v).addView(mListViews.get(i % mListViews.size()), 0);
        return mListViews.get(i % mListViews.size());
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    public void destroyItem(ViewGroup view, int i, Object object) {
        view.removeView(mListViews.get(i%mListViews.size()));
    }
}
