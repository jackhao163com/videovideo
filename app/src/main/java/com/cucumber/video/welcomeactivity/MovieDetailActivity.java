package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.player_list_video)
    JCVideoPlayerStandard playerVideo;
    @BindView(R.id.moviename)
    TextView moviename;
    @BindView(R.id.movietime)
    TextView movietime;
    @BindView(R.id.movieviews)
    TextView movieviews;
    @BindView(R.id.movie_introduction)
    TextView movieIntroduction;
    @BindView(R.id.openJianjie)
    TextView openJianjie;
    @BindView(R.id.openJianjieImg)
    ImageView openJianjieImg;
    @BindView(R.id.commentnum)
    TextView commentnum;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.download)
    ImageView download;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.actor_number)
    TextView actorNumber;
    @BindView(R.id.actor_image)
    CircleImageView actorImage;
    @BindView(R.id.actor_name)
    TextView actorName;
    @BindView(R.id.actor_introduction)
    TextView actorIntroduction;
    @BindView(R.id.actorGridView)
    GridView actorGridView;
    @BindView(R.id.likeMovieList)
    GridView likeMovieList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.actorwrapper)
    RelativeLayout actorwrapper;
    @BindView(R.id.likewrapper)
    RelativeLayout likewrapper;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    private String token;
    private String movieid;

    private List<Map<String, Object>> commentDataList;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 3;
    private int pageIndex = 1;
    private GridLayoutManager mLayoutManager;
    private CommentListAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        ButterKnife.bind(this);

        //获取传参
        Intent intent = getIntent();
        movieid = intent.getStringExtra("movieId");
        getToken();
        initRefreshLayout();
        getData();
    }

    private void getData() {
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getMoiveDetailData?token=" + token + "&movieid=" + movieid);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<MovieDetailBean>() {

            @Override
            public void onResponse(MovieDetailBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                MovieDetailBean.DataBean.DetailBean detailInfo = bean.getMovieDetailData();
                String videoPath = detailInfo.getPath().equals("") ? "" : detailInfo.getPath();
                if (playerVideo != null) {
                    playerVideo.release();
                }
                boolean setUp = playerVideo.setUp(videoPath, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                if (setUp) {
                    playerVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Picasso.with(MovieDetailActivity.this)
                            .load(detailInfo.getCover())
                            .into(playerVideo.thumbImageView);
                }
                moviename.setText(detailInfo.getName());
                movieviews.setText(detailInfo.getViews());
                movietime.setText(DateUtils.timeDate(detailInfo.getCreatetime()));
                movieIntroduction.setText(detailInfo.getSubtitle());
                commentnum.setText(detailInfo.getViews() + "条热评");
                setActorInfo(bean);
                setLikeListInfo(bean);
                setCommentListInfo(bean);
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

    private void setActorInfo(MovieDetailBean bean) {
        MovieDetailBean.DataBean.ActorBean actorData = bean.getActorData();

        if (actorData != null) {
            actorName.setText(actorData.getName());
            actorIntroduction.setText(actorData.getSubtitle());
            actorNumber.setText(actorData.getLikenum() + "部电影");
            Picasso.with(MovieDetailActivity.this)
                    .load(actorData.getCover())
                    .into(actorImage);

            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<MovieDetailBean.DataBean.ActorMListBean> aitemDatas = bean.getActorMList();
            for (MovieDetailBean.DataBean.ActorMListBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", (item.getCover()));
                map.put("moviename", item.getMoviename());
                mDataList.add(map);
            }
            final String[] from = {"cover", "moviename"};
            final int[] to = {R.id.img, R.id.text};
            SimpleAdapter gridAdapter = new SimpleAdapter(MovieDetailActivity.this, mDataList, R.layout.item_common, from, to);
            gridAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    if (view instanceof ImageView) {
                        String url = (String) data;
                        ImageView iv = (ImageView) view;
                        Picasso.with(MovieDetailActivity.this)
                                .load(url)
                                .into(iv);
                        return true;
                    } else
                        return false;
                }
            });
            setGridView(gridAdapter, mDataList.size());
            actorGridView.setAdapter(gridAdapter);
        } else{
            actorwrapper.setVisibility(View.GONE);
        }
    }

    private void setGridView(SimpleAdapter adapter, int count) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int shownum = 3;
        int spcing = 30;
        int itemWidth = (dm.widthPixels - (shownum - 1) * spcing) / shownum;
        int gridviewWidth = (count * (itemWidth)) + ((count - 1) * spcing);
//            historyGridView.setAdapter(adapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        actorGridView.setLayoutParams(params);
        actorGridView.setColumnWidth(itemWidth);
        actorGridView.setStretchMode(GridView.NO_STRETCH);
        actorGridView.setNumColumns(count);
    }


    private void setLikeListInfo(MovieDetailBean bean) {
        List<MovieDetailBean.DataBean.LikelistBean> likeList = bean.getLikelist();

        if (likeList.size() > 0) {
            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<MovieDetailBean.DataBean.LikelistBean> aitemDatas = bean.getLikelist();
            for (MovieDetailBean.DataBean.LikelistBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("likecover", (item.getCover()));
                map.put("name", item.getName());
                map.put("likemovieviews", item.getViews() + "次");
                mDataList.add(map);
            }
            final String[] from = {"likecover", "name", "likemovieviews"};
            final int[] to = {R.id.likecover, R.id.name, R.id.likemovieviews};
            SimpleAdapter gridAdapter = new SimpleAdapter(MovieDetailActivity.this, mDataList, R.layout.item_movie_like, from, to);
            gridAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    if (view instanceof ImageView) {
                        String url = (String) data;
                        ImageView iv = (ImageView) view;
                        Picasso.with(MovieDetailActivity.this)
                                .load(url)
                                .into(iv);
                        return true;
                    } else
                        return false;
                }
            });
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dm.widthPixels,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            likeMovieList.setLayoutParams(params);
            likeMovieList.setColumnWidth(dm.widthPixels);
            likeMovieList.setAdapter(gridAdapter);
        } else {
            likewrapper.setVisibility(View.GONE);
        }
    }


    private void setCommentListInfo(MovieDetailBean bean) {

        initRecyclerView(bean);
    }

    private void initRefreshLayout() {
//        refreshLayout.setOnRefreshListener(MovieDetailActivity.this);
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if(adapter.getHasMore()){
                        updateRecyclerView(1,1);//调用刷新控件对应的加载更多方法
                    }
                }
            }
        });

    }

    private void initRecyclerView(MovieDetailBean bean) {
        adapter = new CommentListAdapter(bean.getCommentlist(), this, bean.getCommentlist().size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.getLayoutManager().setAutoMeasureEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
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


    private void updateRecyclerView(int fromIndex, int toIndex) {
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getCommentList");//apiUrl格式："xxx/xxxxx"
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("movieid", movieid);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", PAGE_COUNT);
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommentListBean>() {

            @Override
            public void onResponse(CommentListBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                pageIndex++;
                if (bean.getItemDatas().size() > 0) {
                    adapter.updateList(bean.getItemDatas(), true);
                } else {
                    adapter.updateList(null, false);
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




    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }
}
