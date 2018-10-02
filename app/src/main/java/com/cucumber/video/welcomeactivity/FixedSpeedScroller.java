package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class FixedSpeedScroller extends Scroller {
    private Context context;
    private int mDuration = 500;
    public FixedSpeedScroller(Context context) {
        super(context);
        this.context=context;
    }
    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.context=context;
    }
    /**
     *  设置改变ViewPager的滑动时间
     * @param vp  ViewPager 对象
     * @param time  时间
     */
    public void setDuration(ViewPager vp, int time) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            this.setmDuration(time);//设置翻动时间
            field.set(vp, this);
        } catch (Exception e) {

        }
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        //System.out.println("startScroll1");
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        //System.out.println("startScroll2");
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
    public void setmDuration(int time) {
        mDuration = time;
    }
    public int getmDuration() {
        return mDuration;
    }
}
