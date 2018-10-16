package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    private String token;
    private String movieid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        ButterKnife.bind(this);

        //获取传参
        Intent intent = getIntent();
        movieid = intent.getStringExtra("movieId");
        getToken();
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
                map.put("likemovieviews", item.getViews()+"次");
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
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dm.widthPixels,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            likeMovieList.setLayoutParams(params);
            likeMovieList.setColumnWidth(dm.widthPixels);
            likeMovieList.setAdapter(gridAdapter);
        }
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }
}
