package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.retrofitutils.L;
import com.squareup.picasso.Picasso;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.listener.ItemClickSupport;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Headers;

public class FragmentMyYijian extends Fragment {

    public static Fragment newInstance() {
        FragmentMyYijian fragment = new FragmentMyYijian();
        return fragment;
    }

    public FragmentMyYijian() {
        // Required empty public constructor
    }

    private ViewGroup mViewGroup;
    SwipeRefreshLayout myswipeRefreshLayout;
    private ItheimaRecyclerView myrecyclerView;
    private RelativeLayout footer;
    BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder;
    PullToLoadMoreRecyclerView pullToLoadMoreRecyclerView;
    private String token;
    Integer pageIndex = 0;
    private int state = 0;
    private static final int STATE_FRESH = 1;
    private static final int STATE_MORE = 2;
    ArrayList<YijianListBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mViewGroup != null) return mViewGroup;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_yijianlist, container, false);
        myrecyclerView = (ItheimaRecyclerView) mViewGroup.findViewById(R.id.recycler_view);
        myswipeRefreshLayout = (SwipeRefreshLayout) mViewGroup.findViewById(R.id.swipe_refresh_layout);
        footer = (RelativeLayout) mViewGroup.findViewById(R.id.footer);

        getToken();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new RecyclerAdapter());
        initPage();
        return mViewGroup;
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(mViewGroup.getContext(), "setting");
        token = helper.getString("token");
    }

    private void initPage() {
        ItemClickSupport itemClickSupport = new ItemClickSupport(myrecyclerView);
        //点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String movieid = itemsBeanList.get(position).getId();

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movieId", movieid);
                startActivity(intent);

            }
        });

        pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<YijianListBean>(myswipeRefreshLayout, myrecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.item_my_yijian;
            }

            @Override
            public void setLoadingDataListener(LoadingDataListener<YijianListBean> loadingDataListener) {
                super.setLoadingDataListener(loadingDataListener);
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
                return "getMyYiJianList?pageIndex=" + pageIndex + "&token=" + token;
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

        pullToLoadMoreRecyclerView.setLoadingDataListener(new PullToLoadMoreRecyclerView.LoadingDataListener<YijianListBean>() {

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
            public void onSuccess(YijianListBean o, Headers headers) {
                //监听http请求成功，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onSuccess: " + o);
                List<YijianListBean.DataBean.ItemsBean> itemDatas = o.getItemDatas();
                if (itemDatas.size() == 0) {
                    if (holder != null) holder.loadingFinish((String) null);
                    if (myswipeRefreshLayout != null) {
                        myswipeRefreshLayout.setRefreshing(false);
                    }
                    if (itemsBeanList.size() == 0) {
                        footer.setVisibility(View.VISIBLE);
                    }
                } else {
                    for (YijianListBean.DataBean.ItemsBean item : itemDatas) {
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
    }


    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<YijianListBean.DataBean.ItemsBean> {


        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;
//        R.layout.item_my_yijian

        //换成你布局文件中的id
        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);

        }

        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {
            try {
                String typename = mData.getTypename().equals("") ? "" : mData.getTypename();
                String contenttext = mData.getContent().equals("") ? "" : mData.getContent();
                String ntime = mData.getCreatetime().equals("") ? "" : mData.getCreatetime();

                title.setText(typename);
                content.setText(contenttext);

                if(ntime == null){
                    time.setText("");
                } else {
                    time.setText(DateUtils.timeDate(ntime.toString()));
                }
            } catch (Exception ex) {
                Log.i("", ex.toString());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
