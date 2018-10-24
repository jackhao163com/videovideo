package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hedan.textdrawablelibrary.TextViewDrawable;
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
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

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
    @BindView(R.id.tab_home)
    TextViewDrawable tabHome;
    @BindView(R.id.tab_channel)
    TextViewDrawable tabChannel;
    @BindView(R.id.tab_find)
    TextViewDrawable tabFind;
    @BindView(R.id.tab_user)
    TextViewDrawable tabUser;
    private ItheimaRecyclerView myrecyclerView;
    private String token;
    Integer pageIndex = 0;
    private int state = 0;
    private static final int STATE_FRESH = 1;
    private static final int STATE_MORE = 2;
    ArrayList<FindBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();

    private ArrayList<String> datas;
    private JCVideoPlayerStandard currPlayer;
    private RecyclerView.OnScrollListener onScrollListener;
    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        getToken();

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.recycler_header);
        myrecyclerView = (ItheimaRecyclerView) findViewById(R.id.recycler_view);
        header.attachTo(myrecyclerView);

        setTabClick();
        initListener();

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
            public void setLoadingDataListener(LoadingDataListener<FindBean> loadingDataListener) {
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
                        item.setIsdeleted(token);
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

    private void setTabClick() {
        Drawable top = getResources().getDrawable(R.drawable.tab3_1);
        tabFind.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);

        tabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomBarTabClick(0);
            }
        });
        tabChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomBarTabClick(1);
            }
        });
        tabFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomBarTabClick(2);
            }
        });
        tabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomBarTabClick(3);
            }
        });
    }

    private void BottomBarTabClick(int tab) {
        Intent i;
        switch (tab) {
            case 0:
                i = new Intent(FindActivity.this, MainActivity.class);
                startActivity(i);
                return;
            case 1:
                i = new Intent(FindActivity.this, ChannelActivity.class);
                startActivity(i);
                return;
            case 2:
                i = new Intent(FindActivity.this, FindActivity.class);
                startActivity(i);
                return;
            case 3:
                i = new Intent(FindActivity.this, UserActivity.class);
                startActivity(i);
                return;
        }
    }

    /**
     * 滑动监听
     */
    private void initListener() {
        onScrollListener = new ItheimaRecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        break;

                    case RecyclerView.SCROLL_STATE_IDLE:
                        //滑动停止自动播放视频
                        ItheimaRecyclerView nrecyclerView = (ItheimaRecyclerView) recyclerView;
                        autoPlayVideo(nrecyclerView);
                        break;

                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager) recyclerView.getLayoutManager();
                int adapterNowPos = l.findFirstVisibleItemPosition();
                if (firstVisible == adapterNowPos) {
                    return;
                }

                firstVisible = adapterNowPos;
                visibleCount = l.getItemCount();
            }
        };

        myrecyclerView.setOnScrollListener(onScrollListener);
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(ItheimaRecyclerView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.player_list_video) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.player_list_video);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
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
        //        @BindView(R.id.img)
//        ImageView img;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.player_list_video)
        JCVideoPlayerStandard player;
//        R.layout.item_find

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
            String videoPath = mData.getPath().equals("") ? "" : mData.getPath();
            String movieid = mData.getId();
            String token = mData.getIsdeleted();
            text.setText(actorname);
            if (player != null) {
                player.release();
            }
            boolean setUp = player.setUp(videoPath, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if (setUp) {
//            Glide.with(mContext).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
                player.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(mContext)
                        .load(cover)
                        .into(player.thumbImageView);
            }
            initClick(movieid,token);
        }

        private void initClick(final String movieid,final String token){
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeAction(movieid,token);
                }
            });
            downloads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    downloadAction(movieid,token);
                }
            });
        }

        private void likeAction(String movieid,final String token){

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
                        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
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
                    }else
                    {
                        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
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
        private void downloadAction(String movieid,final String token){

            final Map<String,Object> map = new HashMap<>();
            map.put("token", mContext);
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
                        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
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
                    }else
                    {
                        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
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
        /**
         * 给按钮添加点击事件（button改成你要添加点击事件的id）
         * @param v
         */
//        @OnClick(R.id.button)
//        public void click(View v) {
//        }
    }
}