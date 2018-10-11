package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.retrofitutils.L;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.header.RecyclerViewHeader;
import org.itheima.recycler.listener.ItemClickSupport;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;

public class FindActivity extends AppCompatActivity {
    BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder;
    PullToLoadMoreRecyclerView pullToLoadMoreRecyclerView;
    @BindView(R.id.recycler_view)
    ItheimaRecyclerView recyclerView;
    @BindView(R.id.et_bg)
    TextView etBg;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.recycler_header)
    RecyclerViewHeader recyclerHeader;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout myswipeRefreshLayout;
    private ItheimaRecyclerView myrecyclerView;
    private String token;
    Integer pageIndex = 0;
    private int state = 0;
    private static final int STATE_FRESH = 1;
    private static final int STATE_MORE = 2;
    ArrayList<FindBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        getToken();

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.recycler_header);
        myrecyclerView = (ItheimaRecyclerView) findViewById(R.id.recycler_view);
        header.attachTo(myrecyclerView);

        ItemClickSupport itemClickSupport = new ItemClickSupport(myrecyclerView);
        //点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String actorId = itemsBeanList.get(position).getId();

                Intent intent = new Intent(FindActivity.this, UserActivity.class);
                intent.putExtra("actorId", actorId);
                startActivity(intent);

            }
        });

        pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<FindBean>(myswipeRefreshLayout, myrecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.item_find;
            }

            @Override
            public String getApi() {
                switch (state) {
                    case STATE_FRESH:
                        pageIndex = 0;
                        break;
                    case STATE_MORE:
                        pageIndex++;
                        break;
                }
                //接口
                return "getfindmovielist?pageIndex=" + pageIndex + "&token=" + token;
            }

            //            //是否加载更多的数据，根据业务逻辑自行判断，true表示有更多的数据，false表示没有更多的数据，如果不需要监听可以不重写该方法
            @Override
            public boolean isMoreData(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder1) {
                System.out.println("isMoreData---------------------" + holder1);
                holder = holder1;
                state = STATE_MORE;
                return true;
            }
        };

        pullToLoadMoreRecyclerView.setLoadingDataListener(new PullToLoadMoreRecyclerView.LoadingDataListener<FindBean>() {

            @Override
            public void onRefresh() {
                //监听下啦刷新，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onRefresh");
                state = STATE_FRESH;
            }

            @Override
            public void onStart() {
                //监听http请求开始，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onStart");
            }

            @Override
            public void onSuccess(FindBean o, Headers headers) {
                //监听http请求成功，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onSuccess: " + o);
                List<FindBean.DataBean.ItemsBean> itemDatas = o.getItemDatas();
                if (itemDatas.size() == 0) {
                    holder.loadingFinish((String) null);
                    if (myswipeRefreshLayout != null) {
                        myswipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    for (FindBean.DataBean.ItemsBean item : itemDatas) {
                        itemsBeanList.add(item);
                    }
                }
            }

            @Override
            public void onFailure() {
                //监听http请求失败，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onFailure");
            }
        });

        pullToLoadMoreRecyclerView.setPageSize(6);
        pullToLoadMoreRecyclerView.requestData();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(2);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_user) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent i;
                    i = new Intent(FindActivity.this, UserActivity.class);
                    startActivity(i);
                } else if (tabId == R.id.tab_channel) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent i = new Intent(FindActivity.this, ChannelActivity.class);
                    startActivity(i);
                } else if (tabId == R.id.tab_find) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                } else if (tabId == R.id.tab_home) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent i = new Intent(FindActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<FindBean.DataBean.ItemsBean> {


        @BindView(R.id.like)
        ImageView like;
        @BindView(R.id.download)
        ImageView downloads;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.text)
        TextView text;

        //换成你布局文件中的id
        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
        }

        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {
            String actorname = mData.getName().equals("") ? "" : mData.getName();
            String cover = mData.getCover().equals("") ? "" : mData.getCover();
            text.setText(actorname);
            if (!cover.isEmpty()) {
                Picasso.with(mContext)
                        .load(cover)
                        .into(img);
            }
        }

        /**
         * 给按钮添加点击事件（button改成你要添加点击事件的id）
         * @param v
         */
//        @OnClick(R.id.button)
//        public void click(View v) {
//        }
    }
}