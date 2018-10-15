package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
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
    @BindView(R.id.historyGridView)
    GridView likeGridView;
    private String token;
    private String movieid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        ButterKnife.bind(this);

        //获取传参
        Intent intent = getIntent();
        movieid = intent.getStringExtra("movieid");;
        getToken();
    }

    private void getData(){
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getMoiveDetailData?token=" + token+"&movieid=" + movieid);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<MovieDetailBean>() {

            @Override
            public void onResponse(MovieDetailBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

               MovieDetailBean.DataBean.DetailBean detailInfo = bean.getMovieDetailData();
               moviename.setText(detailInfo.getName());
               movieviews.setText(detailInfo.getViews());
               movietime.setText(DateUtils.timeDate(detailInfo.getCreatetime()));
               movieIntroduction.setText(detailInfo.getSubtitle());
                commentnum.setText(detailInfo.getViews()+"条热评");
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
