package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends FragmentActivity implements Runnable,ViewPager.OnPageChangeListener {
	public static final int VERSION = 1;
	public static final boolean hasLoged = true;
	public static SharedPreferences sp;
	private MyImgScroll viewPager;
	private List<View> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		if(isFirst()){
			// 启动一个延迟线程
			new Thread(this).start();
			initView();
		}
	}
	private void initView() {
		// 实例化ViewPager
		viewPager = findViewById(R.id.welcomeViewPager);

		InitViewPager();
		viewPager.start(this, mList, 1200, null,
				0, 0,
				0, 0);
	}
	private void InitViewPager() {
		mList = new ArrayList<View>();
		int[] imageResId = new int[] { R.mipmap.start1,R.mipmap.start2,R.mipmap.start3 ,R.mipmap.start1,R.mipmap.start2};
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			if (i == imageResId.length - 1) {
			   imageView.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {// 设置图片点击事件
						// 读取SharedPreferences中需要的数据
						sp = getSharedPreferences("Y_Setting", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = sp.edit();
						editor.putInt("VERSION", VERSION);
						editor.commit();
						/**
						 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
						 */
							if(sp.getBoolean("hasLoged",false) != hasLoged){
								startActivity(new Intent(WelcomeActivity.this,
										UserActivity.class));
							}
							else {
								startActivity(new Intent(WelcomeActivity.this,
										MainActivity.class));
							}

						finish();
					}
				});
			}
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			mList.add(imageView);
		}
	}
	/**
	 * 滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	public  boolean isFirst(){
		sp = getSharedPreferences("Y_Setting", Context.MODE_PRIVATE);
		/**
		 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
		 */
		if (sp.getInt("VERSION", 0) != VERSION) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("VERSION", VERSION);
			editor.commit();
			return true;
//			startActivity(new Intent(WelcomeActivity.this,
//					GuideActivity.class));
		} else {
			if(sp.getBoolean("hasLoged",false) != hasLoged){
				startActivity(new Intent(WelcomeActivity.this,
						UserActivity.class));
			}
			else {
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
			}
		}
		return false;
	}
	/**
	 * 当前页面滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 新的页面被选中时调用
	 */
	@Override
	public void onPageSelected(int arg0) {
	}
	private  class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		private  final int NUM_PAGES = 3;
		private  final int[] COLOR = {
				0xff00cc00, 0xff1144aa, 0xffd2006b
		};
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ScreenSlidePageFragment.create(position, COLOR[position % COLOR.length]);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}


	}
	@Override
	public void run() {
		try {
			// 延迟两秒时间
			Thread.sleep(3000);
			// 读取SharedPreferences中需要的数据
			sp = getSharedPreferences("Y_Setting", Context.MODE_PRIVATE);
			/**
			 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
			 */
			if (sp.getInt("VERSION", 0) != VERSION) {
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("VERSION", VERSION);
				editor.commit();
//				startActivity(new Intent(WelcomeActivity.this,
//						GuideActivity.class));
			} else {
				if(sp.getBoolean("hasLoged",false) != hasLoged){
					startActivity(new Intent(WelcomeActivity.this,
							UserActivity.class));
				}
				else {
					startActivity(new Intent(WelcomeActivity.this,
							MainActivity.class));
				}
			}
			finish();

		} catch (Exception e) {
		}
	}
}
