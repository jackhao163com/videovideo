package com.cucumber.video.welcomeactivity;

import android.content.Context;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itheima.loopviewpager.LoopViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.itheima.recycler.widget.ItheimaRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private LoopViewPager loopViewPager;
	private  List<Integer> mItems;
	private Context mContext;
	private ItheimaRecyclerView myrecyclerView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		loopViewPager = (LoopViewPager) findViewById(R.id.lvp_pager);
		loopViewPager.setImgData(imgListString());
		loopViewPager.setTitleData(titleListString());
		loopViewPager.start();

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
	private List<String> imgListString() {
		List<String> imageData = new ArrayList<>();
		imageData.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=72b32dc4b719ebc4df787199b227cf79/58ee3d6d55fbb2fb48944ab34b4a20a44723dcd7.jpg");
		imageData.add("http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
		imageData.add("http://pic.4j4j.cn/upload/pic/20130815/5e604404fe.jpg");
		imageData.add("http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg");
		imageData.add("http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg");
		return imageData;
	}

	private List<String> titleListString() {
		List<String> titleData = new ArrayList<>();
		titleData.add("1、在这里等着你");
		titleData.add("2、在你身边");
		titleData.add("3、打电话给你就是想说声“嗨”");
		titleData.add("4、不介意你对我大喊大叫");
		titleData.add("5、期待你总是尽全力");
		return titleData;
	}
    @Override
    public void onClick(View v) {

    }

	public class TwowayRecycleAdapter extends RecyclerView.Adapter<myrecyclerView.viewholder> {

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
			return new SimpleViewHolder(view);
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			View itemView = holder.itemView;
			final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
			switch (getItemViewType(position)) {
				case 1:
//					lp.span = 3;
					if (position % 3 == 0) {
						lp.height = 200;
					} else if (position % 5 == 0) {
						lp.height = 300;
					} else if (position % 7 == 0) {
						lp.height = 500;
					} else {
						lp.height = 400;
					}
					break;
			}
			itemView.setLayoutParams(lp);
		}

		@Override
		public int getItemCount() {
			return mItems.size();
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0){
				return 1;
			}else if (position == 1){
				return 1;
			}else if (2<=position && position <= 7){
				return 1;
			}else if (position == 8){
				return 1;
			}else if (9<=position && position <= 14){
				return 1;
			}else if (15<=position && position <= 18){
				return 1;
			}else {
				return 1;
			}
		}
	}
}