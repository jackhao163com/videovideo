package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.L;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.adapter.BaseRecyclerAdapter;
import org.itheima.recycler.header.RecyclerViewHeader;
import org.itheima.recycler.listener.ItemClickSupport;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MyStorageListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.footer)
    RelativeLayout footer;
    private String token;
    private ItheimaRecyclerView myrecyclerView;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    private MyStorageAdapater adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    Integer pageIndex = 0;
    private int state = 0;
    private static final int STATE_FRESH = 1;
    private static final int STATE_MORE = 2;
    private String url ;
    ArrayList<MovieStorageBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    List<Map<String,Object>> mDataList = new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystorage);
        getToken();

        //获取传参
        Intent intent = getIntent();
        final String search = intent.getStringExtra("search");

        ButterKnife.bind(this);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyStorageListActivity.this.finish();
            }
        });

        initRefreshLayout();
        getData(1);
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }


    public void showEmpty(){
        if (itemsBeanList.size() == 0) {
            footer.setVisibility(View.VISIBLE);
        }
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(MyStorageListActivity.this);
    }

    private void getData(final int flag) {
        //开始请求
        url = "getMyMovieStorageList?pageIndex=" + pageIndex + "&token=" + token;
        Request request = ItheimaHttp.newGetRequest(url);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<MovieStorageBean>() {

            @Override
            public void onResponse(MovieStorageBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                List<Map<String,Object>> newDataList = new ArrayList<Map<String,Object>>();
                if(bean.getItemDatas().size() > 0){
                    for (MovieStorageBean.DataBean.ItemsBean item : bean.getItemDatas()) {
                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("cover", (item.getCover()));
                        map.put("name",item.getName());
                        map.put("videoSize",item.getVideosize());
                        map.put("type",item.getType());
                        map.put("movieid",item.getMovieid());
                        map.put("curSize",item.getSize());
                        mDataList.add(map);
                        newDataList.add(map);
                    }
                }



                if(flag == 2){
                    if (!newDataList.isEmpty() && newDataList.size() > 0) {
                        adapter.updateList(newDataList, true);
                    } else {
                        adapter.updateList(null, false);
                    }
                } else{
                    initRecyclerView();
                    if(newDataList.size() ==0){
                        showEmpty();
                    }
                }
            }

            /**
             * 可以不重写失败回调
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                System.out.println("print data fail");
            }
        });
    }
    private void initRecyclerView() {
        adapter = new MyStorageAdapater(mDataList, this, mDataList.size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView();
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView();
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void updateRecyclerView() {
        pageIndex++;
       getData(2);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        pageIndex = 0;
        getData(2);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
