package com.cucumber.video.welcomeactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
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

import java.io.IOException;
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
    EditText commentInput;
    private String token;
    private String movieid;
    private String moviepath;

    private List<Map<String, Object>> commentDataList;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 3;
    private int pageIndex = 1;
    private GridLayoutManager mLayoutManager;
    private CommentListAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int isDownload = 0;

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
        initClick();
        getData();
    }

    private void initClick(){
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    likeAction();
                }catch (Exception ex){
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("添加喜爱提示")
                            .content("添加喜爱失败了！！！")
                            .positiveText("确认")
                            .show();
                }
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    downloadAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void likeAction(){

        if(like.getTag() != null && like.getTag().toString().equals("like")){
            return;
        }
        final Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("movieid",movieid);
        //开始请求
        Request request = ItheimaHttp.newPostRequest("addMovieLike");//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonBean>() {

            @Override
            public void onResponse(final CommonBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " +bean);
                int status = bean.getStatus();
                //判断账号和密码
                if(status == 1){
                    like.setImageResource(R.mipmap.xihuan2);
                    like.setTag("like");
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("添加喜爱提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            //点击事件添加 方式1
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                } else if(status == -1 || status == -2){
                    startActivity(new Intent(MovieDetailActivity.this, LoginActivity.class));
                }
                else
                {
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("添加喜爱提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
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


    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    public void requestPermission() throws IOException {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"please give me the permission",Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        } else{
            startToDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    startToDownload();
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }

    private void startToDownload(){

        String downloadPath = Environment.getExternalStorageDirectory() + "/Download/" + "movie"+movieid+"_xx.mp4";
        Aria.download(MovieDetailActivity.this)
                .load(moviepath)
                .setFilePath(downloadPath)
                .start();

        final Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("movieid",movieid);
        //开始请求
        Request request = ItheimaHttp.newPostRequest("addMovieStorage");//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonBean>() {

            @Override
            public void onResponse(final CommonBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " +bean);
                int status = bean.getStatus();
                //判断账号和密码
                if(status == 1){
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("离线缓存提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            //点击事件添加 方式1
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                } else if(status == -1 || status == -2){
                    startActivity(new Intent(MovieDetailActivity.this, LoginActivity.class));
                }
                else
                {
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("离线缓存提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
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

    private void downloadAction() throws IOException {
        if(isDownload == 0){
            requestPermission();
        }

    }

    @Download.onTaskStart void taskStart(DownloadTask task) {
//        mAdapter.updateBtState(task.getKey(), false);
        MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                .title("温馨提示")
                .content("开始下载了！")
                .show();

        isDownload = 1;
    }

    @Download.onTaskResume void taskResume(DownloadTask task) {
//        mAdapter.updateBtState(task.getKey(), false);
        Toast.makeText(MovieDetailActivity.this, "taskResume", Toast.LENGTH_SHORT).show();
    }

    @Download.onTaskStop void taskStop(DownloadTask task) {
//        mAdapter.updateBtState(task.getKey(), true);
        Toast.makeText(MovieDetailActivity.this, "taskStop", Toast.LENGTH_SHORT).show();
    }

    @Download.onTaskCancel void taskCancel(DownloadTask task) {
//        mAdapter.updateBtState(task.getKey(), true);
        Toast.makeText(MovieDetailActivity.this, "taskCancel", Toast.LENGTH_SHORT).show();
    }

    @Download.onTaskFail void taskFail(DownloadTask task) {
//        mAdapter.updateBtState(task.getKey(), true);
        Toast.makeText(MovieDetailActivity.this, "taskFail", Toast.LENGTH_SHORT).show();
    }

    @Download.onTaskComplete void taskComplete(DownloadTask task){
//        Log.d(TAG, FileUtil.getFileMD5(new File(task.getDownloadPath())));
        Toast.makeText(MovieDetailActivity.this, "taskComplete", Toast.LENGTH_SHORT).show();
    }

    private void getData() {
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getMoiveDetailData?token=" + token + "&movieid=" + movieid);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<MovieDetailBean>() {

            @Override
            public void onResponse(final MovieDetailBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                if(bean.getStatus() == 1){
                    MovieDetailBean.DataBean.DetailBean detailInfo = bean.getMovieDetailData();
                    String videoPath = detailInfo.getPath().equals("") ? "" : detailInfo.getPath();
                    if (playerVideo != null) {
                        playerVideo.release();
                    }
                    boolean setUp = playerVideo.setUp(videoPath, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                    if (setUp) {
                        playerVideo.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Picasso.with(MovieDetailActivity.this)
                                .load(detailInfo.getCover())
                                .into(playerVideo.thumbImageView);
                    }
                    moviepath = videoPath;
                    moviename.setText(detailInfo.getName());
                    movieviews.setText(detailInfo.getViews() +"次");
                    movietime.setText(DateUtils.timeDate(detailInfo.getCreatetime()));
                    movieIntroduction.setText(detailInfo.getSubtitle());
                    commentnum.setText(detailInfo.getCommentnums() + "条热评");
                    String islike = detailInfo.getIsLike();
                    if(islike.equals("like")){
                        like.setImageResource(R.mipmap.xihuan2);
                        like.setTag("like");
                    }
                    setActorInfo(bean);
                    setLikeListInfo(bean);
                    setCommentListInfo(bean);
                }  else if(bean.getStatus() == -1 || bean.getStatus() == -2){
                    startActivity(new Intent(MovieDetailActivity.this, LoginActivity.class));
                }  else {
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("温馨提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            //点击事件添加 方式1
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if(bean.getStatus() == -99){
                                        Intent i = new Intent(MovieDetailActivity.this, MainActivity.class);
                                        startActivity(i);
                                        MovieDetailActivity.this.finish();
                                    }
                                }
                            })
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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        commentDetailWrapper.getLayoutParams().height = dm.heightPixels - playerVideo.getLayoutParams().height-40;

    }

    private void initRecyclerView(MovieDetailBean bean) {

        commentInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieDetailActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                openCommentDialog("","","","",-1);
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
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
//                            }
//                        }, 500);
//                    }
//
//                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
//                            }
//                        }, 500);
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
//            }
//        });
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

    public void openCommentDialog(final String parentid, final String groupid, String toname, final String toid,final  int position) {

        if(token == null || token.length() == 0){
            startActivity(new Intent(MovieDetailActivity.this, LoginActivity.class));
            return;
        }

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.item_movie_detail_submit,true)
//                .positiveText("确认")
//                .negativeText("取消")
                .show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.show();
        View customeView = dialog.getCustomView();
        final EditText comment_cotent = (EditText) customeView.findViewById(R.id.add_comment);
        final SpannableString s = new SpannableString("回复"+toname);
        comment_cotent.setHint(s);


        ImageView button = (ImageView) customeView.findViewById(R.id.submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MovieDetailActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                Map<String,Object> map = new HashMap<>();
                map.put("token", token);
                map.put("movieid", movieid);
                map.put("parentid", parentid);
                map.put("groupid", groupid);
                map.put("content", comment_cotent.getText());
                map.put("touserid", toid);
                releyAction(map,position);
            }
        });

    }

    public void releyAction(final Map<String,Object> map, final int position){
        //开始请求
        Request request = ItheimaHttp.newPostRequest("replyComment");//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonCommentBean>() {

            @Override
            public void onResponse(final CommonCommentBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " +bean);
                int status = bean.getStatus();
                //判断账号和密码
                if(status == 1){
                    MaterialDialog dialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                            .title("新增评论提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            //点击事件添加 方式1
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        //列表中新增一条记录
                                        if(map.get("parentid").equals("")){
                                            //如果是主记录
                                            MovieDetailBean.DataBean.CommentlistBean newBean = new MovieDetailBean.DataBean.CommentlistBean();
                                            newBean = CommentListAdapter.modelC2B(bean.getData(),MovieDetailBean.DataBean.CommentlistBean.class);
                                            adapter.addItem(newBean);
                                        } else if(map.get("parentid").toString().equals(map.get("groupid").toString())){
                                            //主页面子评论
                                            MovieDetailBean.DataBean.CommentlistBean.SubitemsBean newsBean = new MovieDetailBean.DataBean.CommentlistBean.SubitemsBean();
                                            newsBean = CommentListAdapter.modelD2B(bean.getData(),MovieDetailBean.DataBean.CommentlistBean.SubitemsBean.class);
                                            adapter.addSubItem(newsBean,position);
                                        } else {
                                            //自评论列表
                                            ArrayList<subCommentBean.DataBean.ItemsBean> sublist = new ArrayList<subCommentBean.DataBean.ItemsBean>();
                                            subCommentBean.DataBean.ItemsBean newsBean = new subCommentBean.DataBean.ItemsBean();
                                            newsBean = CommentListAdapter.modelE2B(bean.getData(),subCommentBean.DataBean.ItemsBean.class);
                                            sublist.add(newsBean);
                                            pullToLoadMoreRecyclerView.mLoadMoreRecyclerViewAdapter.addDatas(true,sublist);
                                        }
                                    }

                                }
                            })
                            .show();
                }else if(status == -1 || status == -2)
                {
                    startActivity(new Intent(MovieDetailActivity.this, LoginActivity.class));
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


    public void initSubCommentListView(final String commentid) {

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

        ItemClickSupport itemClickSupport = new ItemClickSupport(myrecyclerView);
        //点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String cId = subitemsBeanList.get(position).getId();
                String username = subitemsBeanList.get(position).getFromusername();
                String userid = subitemsBeanList.get(position).getUserid();
                openCommentDialog(cId,commentid,username,userid,position);
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
                return "getSubCommentList?pageIndex=" + subPageIndex + "&token=" + token + "&commentid=" + commentid + "&movieid=" + movieid;
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
                    if(holder != null)holder.loadingFinish((String) null);
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
            String content = mData.getContent().equals("") ? "" : mData.getContent();
            String time = mData.getCreatetime().equals("") ? "" : mData.getCreatetime();
            try{
                long timedate =  Long.parseLong(time);
                time = DateUtils.getChatTimeStr(timedate);
            } catch (Exception ex0){
                Log.i("",ex0.toString());
            }
            commentMobile.setText(fromname);
            commentContent.setText(content);
            commentGenderImg.setImageResource(gender.equals("1") ? R.mipmap.female : R.mipmap.male);
            commentTime.setText(time);


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
