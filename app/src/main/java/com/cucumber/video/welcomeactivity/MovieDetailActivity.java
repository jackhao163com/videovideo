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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.L;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.squareup.picasso.Picasso;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
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
    @BindView(R.id.comment_detail_wrapper)
    RelativeLayout commentDetailWrapper;
    @BindView(R.id.comment_input)
    TextView commentInput;
    private String token;
    private String movieid;

    private List<Map<String, Object>> commentDataList;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 3;
    private int pageIndex = 1;
    private GridLayoutManager mLayoutManager;
    private CommentListAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder;
    PullToLoadMoreRecyclerView pullToLoadMoreRecyclerView;
    SwipeRefreshLayout myswipeRefreshLayout;
    private ItheimaRecyclerView myrecyclerView;
    ArrayList<subCommentBean.DataBean.ItemsBean> subitemsBeanList = new ArrayList<>();
    Integer subPageIndex = 0;
    private int state = 0;
    private static final int STATE_FRESH = 1;
    private static final int STATE_MORE = 2;

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
        } else {
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
//        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (adapter.getHasMore()) {
                        updateRecyclerView(1, 1);//调用刷新控件对应的加载更多方法
                    }
                }
            }
        });

    }

    private void initRecyclerView(MovieDetailBean bean) {

        commentInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieDetailActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                openCommentDialog();
            }
        });

        adapter = new CommentListAdapter(bean.getCommentlist(), this, MovieDetailActivity.this, bean.getCommentlist().size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//         recyclerView.getLayoutManager().setAutoMeasureEnabled(false);
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
                if (bean.getStatus() == 1) {
                    if (bean.getItemDatas().size() > 0) {
                        adapter.updateList(bean.getItemDatas(), true);
                    } else {
                        adapter.updateList(null, false);
                    }
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("温馨提示")
                            .content(bean.getMsg())
                            .positiveText("确定")
                            .show();
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

    public void openCommentDialog() {
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("发表评论")
                .customView(R.layout.item_movie_detail_submit,true)
                .positiveText("确认")
                .negativeText("取消").show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        View customeView = dialog.getCustomView();

        ImageView button = (ImageView) customeView.findViewById(R.id.submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MovieDetailActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void initSubCommentListView(String commentid) {

        commentDetailWrapper.setVisibility(View.VISIBLE);
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.recycler_header_subcomment);
        myrecyclerView = (ItheimaRecyclerView) findViewById(R.id.recycler_view_subcomment);
        myswipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_subcomment);
        header.attachTo(myrecyclerView);

        RelativeLayout cdHeader = (RelativeLayout) findViewById(R.id.cdHeader);
        cdHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentDetailWrapper.setVisibility(View.GONE);
                clearRecyclerViewData();
            }
        });

        final String cid = commentid;

        ItemClickSupport itemClickSupport = new ItemClickSupport(myrecyclerView);
        //点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String cId = subitemsBeanList.get(position).getId();
            }
        });

        pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<subCommentBean>(myswipeRefreshLayout, myrecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.item_movie_comment;
            }

            @Override
            public String getApi() {
                switch (state) {
                    case STATE_FRESH:
                        subPageIndex = 0;
                        break;
                    case STATE_MORE:
                        subPageIndex++;
                        break;
                }
                //接口
                return "getSubCommentList?pageIndex=" + subPageIndex + "&token=" + token + "&commentid=" + cid + "&movieid=" + movieid;
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

        pullToLoadMoreRecyclerView.setLoadingDataListener(new PullToLoadMoreRecyclerView.LoadingDataListener<subCommentBean>() {

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
            public void onSuccess(subCommentBean o, Headers headers) {
                //监听http请求成功，如果不需要监听可以不重新该方法

                L.i("setLoadingDataListener onSuccess: " + o);
                List<subCommentBean.DataBean.ItemsBean> itemDatas = o.getItemDatas();
                if (itemDatas.size() == 0) {
                    holder.loadingFinish((String) null);
                    if (myswipeRefreshLayout != null) {
                        myswipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    for (subCommentBean.DataBean.ItemsBean item : itemDatas) {
                        subitemsBeanList.add(item);
                    }
                }
            }

            @Override
            public void onFailure() {
                //监听http请求失败，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onFailure");
                myswipeRefreshLayout.setRefreshing(false);
            }
        });

        pullToLoadMoreRecyclerView.setPageSize(6);
//        pullToLoadMoreRecyclerView.putParam("categoryId",catgoryId);
//        pullToLoadMoreRecyclerView.putParam("orderId",orderId);
        pullToLoadMoreRecyclerView.requestData();
    }


    public void clearRecyclerViewData() {
        subitemsBeanList.clear();
        subPageIndex = 0;
        state = 0;
        pullToLoadMoreRecyclerView.free();
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<subCommentBean.DataBean.ItemsBean> {


        @BindView(R.id.comment_avtar)
        CircleImageView commentAvtar;
        @BindView(R.id.comment_mobile)
        TextView commentMobile;
        @BindView(R.id.comment_genderImg)
        ImageView commentGenderImg;
        @BindView(R.id.comment_time)
        TextView commentTime;
        @BindView(R.id.comment_content)
        TextView commentContent;

        //换成你布局文件中的id
        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
//            R.layout.item_movie_comment
        }

        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {

            String fromname = mData.getFromusername().equals("") ? "" : mData.getFromusername();
            String toname = mData.getTousername().equals("") ? "" : mData.getTousername();
            String cover = mData.getAvatar().equals("") ? "" : mData.getAvatar();
            String gender = mData.getGender().equals("") ? "0" : mData.getGender();
            String content = mData.getContent().equals("") ? "0" : mData.getContent();
            String time = mData.getCreatetime().equals("") ? "0" : mData.getCreatetime();
            commentMobile.setText(fromname);
            commentContent.setText(content);
            commentGenderImg.setImageResource(gender.equals("1") ? R.mipmap.female : R.mipmap.male);
            commentTime.setText(DateUtils.timeDate(time));


            if (!cover.isEmpty()) {
                Picasso.with(mContext)
                        .load(cover)
                        .into(commentAvtar);
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
