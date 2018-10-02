package com.cucumber.video.welcomeactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 
 * @author
 * @功能描述：引导界面activity类
 *
 */
public class GuideActivity extends FragmentActivity implements OnPageChangeListener {
	// 定义ViewPager对象
	public static ViewPager viewPager;

	// 定义开始按钮对象
	private PagerAdapter mPagerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();

	}

	/**
	 * 初始化
	 */
	private void initView() {
		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		// 设置监听
		viewPager.addOnPageChangeListener(this);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		mPagerAdapter = GuideActivity.this.new ScreenSlidePagerAdapter(this.getSupportFragmentManager());
		// 设置适配器数据
		viewPager.setAdapter(mPagerAdapter);
	}

	/**
	 * 滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

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
}