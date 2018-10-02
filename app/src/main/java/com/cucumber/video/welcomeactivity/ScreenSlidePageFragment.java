package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * 
 * @author
 * @功能描述：ViewPager适配器，用来绑定数据和view
 *
 */
public class ScreenSlidePageFragment extends Fragment {
	private Button btnStart;
	private Button firstB;
	private Button secondB;
	public static final String PAGE = "page";
	public static final String COLOR = "color";
	private static final int[] mFragmentLayout = {
			R.layout.guide_view1,R.layout.guide_view2,R.layout.guide_view3
	};

	private int mPageNumber;
	private int mColor;

	public static ScreenSlidePageFragment create(int pageNumber, int color) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(PAGE, pageNumber);
		args.putInt(COLOR, color);
		fragment.setArguments(args);
		return fragment;
	}

	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(PAGE);
		mColor = getArguments().getInt(COLOR);
		Log.d("chumingchao",mPageNumber+"");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater
				.inflate(mFragmentLayout[mPageNumber], container, false);
		//rootView.setBackgroundColor(mColor);
		switch(mPageNumber){
			case 0:
			{
				firstB = (Button) rootView.findViewById(R.id.firstBtn);
				// 给开始按钮设置监听
				firstB.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
						scroller.initViewPagerScroll(GuideActivity.viewPager);
						GuideActivity.viewPager.setCurrentItem(GuideActivity.viewPager.getCurrentItem() + 1, true);
					}

				});
			}
			break;
			case 1: {
				secondB = (Button) rootView.findViewById(R.id.secondBtn);
				// 给开始按钮设置监听
				secondB.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
						scroller.initViewPagerScroll(GuideActivity.viewPager);
						GuideActivity.viewPager.setCurrentItem(GuideActivity.viewPager.getCurrentItem() + 1, true);
					}

				});
			}
			break;
			case 2:{
				// 实例化开始按钮
				btnStart = (Button) rootView.findViewById(R.id.startBtn);
				// 给开始按钮设置监听
				btnStart.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// 存入数据并提交
						WelcomeActivity.sp.edit()
								.putInt("VERSION", WelcomeActivity.VERSION).apply();
						startActivity(new Intent(getActivity(), LoginActivity.class));
						getActivity().finish();
					}

				});
				break;

			}
			default:
				break;
		}
		//((TextView) rootView.findViewById(R.id.text)).setText("" + mPageNumber);
		return rootView;
	}
	/**
	 * ViewPager 滚动速度设置
	 *
	 */
	public class ViewPagerScroller extends Scroller {
		private int mScrollDuration = 2000; // 滑动速度

		/**
		 * 设置速度速度
		 *
		 * @param duration
		 */
		public void setScrollDuration(int duration) {
			this.mScrollDuration = duration;
		}

		public ViewPagerScroller(Context context) {
			super(context);
		}


		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}

		public void initViewPagerScroll(ViewPager viewPager) {
			try {
				Field mScroller = ViewPager.class.getDeclaredField("mScroller");
				mScroller.setAccessible(true);
				mScroller.set(viewPager, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
