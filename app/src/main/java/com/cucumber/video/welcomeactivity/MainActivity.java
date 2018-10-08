package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private TextView item_shouye, item_pindao, item_faxian, item_me;
	private ImageView firstImage, secondeImage, thirdImage, fourthImage;
	private ViewPager vp;
	private OneFragment oneFragment;
	private TwoFragment twoFragment;
	private ThreeFragment threeFragment;
	private FouthFragment fouthFragmen;
	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;
	private int mPos;
	private boolean isStart;
	public static MyHandler myHandler;
	private ImageView mPopwindow;
	private PopupWindow mPopwindow_w;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);  //或者PixelFormat.TRANSLUCENT
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		setContentView(R.layout.activity_main);
		initViews();


		WelcomeActivity.sp.edit()
				.putBoolean("hasLoged", false).commit();
		myHandler = new MyHandler();
		Thread th = new Thread() {

			@Override
			public void run() {
				super.run();
				while (!isStart) {
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 1;

				MainActivity.this.myHandler.sendMessage(msg);
			}
		};
		th.start();

		BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
		bottomBar.selectTabAtPosition(0);
		bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_home) {
//                    // The tab with id R.id.tab_favorites was selected,
//                    // change your content accordingly.
//                    Intent i = new Intent(HomeActivity.this,MainActivity.class);
//                    startActivity(i);
//                } else
				if (tabId == R.id.tab_channel) {
					// The tab with id R.id.tab_favorites was selected,
					// change your content accordingly.
					Intent i;
					i = new Intent(MainActivity.this,UserActivity.class);
					startActivity(i);
				} else if (tabId == R.id.tab_find) {
					// The tab with id R.id.tab_favorites was selected,
					// change your content accordingly.
					Intent i = new Intent(MainActivity.this,UserActivity.class);
					startActivity(i);
				} else if (tabId == R.id.tab_user) {
					// The tab with id R.id.tab_favorites was selected,
					// change your content accordingly.
					Intent i = new Intent(MainActivity.this,UserActivity.class);
					startActivity(i);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//checkifpopwindow();
	}

	private void checkifpopwindow() {
		// 用于PopupWindow的View
		View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow, null, false);
		// 创建PopupWindow对象，其中：
		// 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
		// 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
		mPopwindow = contentView.findViewById(R.id.popwindow);
		mPopwindow.setOnClickListener(this);
		mPopwindow_w = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 设置PopupWindow的背景
		//     window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 设置PopupWindow是否能响应外部点击事件
		mPopwindow_w.setOutsideTouchable(true);
		// 设置PopupWindow是否能响应点击事件
		mPopwindow_w.setTouchable(true);
		mPopwindow_w.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

		// 显示PopupWindow，其中：
		// 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
		//window.showAsDropDown(anchor, xoff, yoff);
		// 或者也可以调用此方法显示PopupWindow，其中：// 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
		// 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
		//  window.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 初始化布局View
	 */
	private void initViews() {
//		title = (TextView) findViewById(R.id.title);
//		item_shouye = (TextView) findViewById(R.id.item_shouye);
//		item_pindao = (TextView) findViewById(R.id.item_pindao);
//		item_faxian = (TextView) findViewById(R.id.item_faxian);
//		item_me = (TextView) findViewById(R.id.item_me);
//		firstImage = findViewById(R.id.firstImageView);
//		secondeImage = findViewById(R.id.secondImageView);
//		thirdImage = findViewById(R.id.thirdImageView);
//		fourthImage = findViewById(R.id.fourthImageView);

//		item_shouye.setOnClickListener(this);
//		item_pindao.setOnClickListener(this);
//		item_faxian.setOnClickListener(this);
//		item_me.setOnClickListener(this);
//		firstImage.setOnClickListener(this);
//		secondeImage.setOnClickListener(this);
//		thirdImage.setOnClickListener(this);
//		fourthImage.setOnClickListener(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (vp.getCurrentItem()){
			case 0:
				oneFragment.getActivity().finish();
			case 1:
				twoFragment.onKeyDown(keyCode,event);
			case 2:
				threeFragment.onKeyDown(keyCode,event);
			case 3:
				thirdImage.onKeyDown(keyCode,event);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 点击底部Text 动态修改ViewPager的内容
	 */
	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.item_shouye:
//			case R.id.firstImageView:
//				vp.setCurrentItem(0, true);
//				break;
//			case R.id.secondImageView:
//			case R.id.item_pindao:
//				vp.setCurrentItem(1, true);
//				break;
//			case R.id.item_faxian:
//			case R.id.thirdImageView:
//				vp.setCurrentItem(2, true);
//				break;
//			case R.id.item_me:
//			case R.id.fourthImageView:
//				vp.setCurrentItem(3, true);
//				break;
//			case R.id.popwindow:
//				mPopwindow_w.dismiss();
//				break;
//		}
	}


	public class FragmentAdapter extends FragmentPagerAdapter {

		List<Fragment> fragmentList = new ArrayList<Fragment>();

		public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

	}

	/*
	 *由ViewPager的滑动修改底部导航Text的颜色
	 */
	private void changeTextColor(int position) {
		switch (mPos) {
			case 0:
				firstImage.setImageResource(R.mipmap.tab1_2);
				break;
			case 1:
				secondeImage.setImageResource(R.mipmap.tab2_2);
				break;
			case 2:
				thirdImage.setImageResource(R.mipmap.tab3_2);
				break;
			case 3:
				fourthImage.setImageResource(R.mipmap.tab4_2);
				break;
		}
		if (position == 0) {
			item_shouye.setTextColor(Color.parseColor("#66CDAA"));
			item_pindao.setTextColor(Color.parseColor("#CCCCCC"));
			item_faxian.setTextColor(Color.parseColor("#CCCCCC"));
			item_me.setTextColor(Color.parseColor("#CCCCCC"));
			firstImage.setImageResource(R.mipmap.tab1_1);
		} else if (position == 1) {
			item_pindao.setTextColor(Color.parseColor("#66CDAA"));
			item_shouye.setTextColor(Color.parseColor("#CCCCCC"));
			item_faxian.setTextColor(Color.parseColor("#CCCCCC"));
			item_me.setTextColor(Color.parseColor("#CCCCCC"));
			secondeImage.setImageResource(R.mipmap.tab2_1);
		} else if (position == 2) {
			item_faxian.setTextColor(Color.parseColor("#66CDAA"));
			item_shouye.setTextColor(Color.parseColor("#ffffff"));
			item_pindao.setTextColor(Color.parseColor("#ffffff"));
			item_me.setTextColor(Color.parseColor("#ffffff"));
			thirdImage.setImageResource(R.mipmap.tab3_1);
		} else if (position == 3) {
			item_me.setTextColor(Color.parseColor("#66CDAA"));
			item_shouye.setTextColor(Color.parseColor("#ffffff"));
			item_pindao.setTextColor(Color.parseColor("#ffffff"));
			item_faxian.setTextColor(Color.parseColor("#ffffff"));
			fourthImage.setImageResource(R.mipmap.tab4_1);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		isStart = true;
	}

	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:
					MainActivity.this.checkifpopwindow();
					break;
				case 0:
					oneFragment.freshBanner();


			}
		}
	}
}