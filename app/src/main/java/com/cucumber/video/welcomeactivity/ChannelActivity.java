package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.itheima.loopviewpager.LoopViewPager;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ChannelActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ImageView mSettings;
    private String token;
    private MyRecycleAdapter mAdapter;
    private LoopViewPager loopViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        getToken();

        loopViewPager = (LoopViewPager) findViewById(R.id.lvp_pager);
        loopViewPager.setImgData(imgListString());
        loopViewPager.setTitleData(titleListString());
        loopViewPager.start();

//        mSettings = (ImageView)findViewById(R.id.setting);
//        mSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ChannelActivity.this, SettingActivity.class));
//            }
//        });

        //开始请求
        Request request = ItheimaHttp.newGetRequest("getChannelData?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<ChannelBean>() {

            @Override
            public void onResponse(ChannelBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(ChannelActivity.this, LinearLayoutManager.VERTICAL, false));
                mAdapter = new MyRecycleAdapter(ChannelActivity.this, bean);
                mRecyclerView.setAdapter(mAdapter);
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

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(1);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_user) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent i;
                    i = new Intent(ChannelActivity.this, UserActivity.class);
                    startActivity(i);
                } else if (tabId == R.id.tab_channel) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                } else if (tabId == R.id.tab_find) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent i = new Intent(ChannelActivity.this, FindActivity.class);
                    startActivity(i);
                } else if (tabId == R.id.tab_home) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent i = new Intent(ChannelActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
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
        titleData.add("");
        titleData.add("");
        titleData.add("");
        titleData.add("");
        titleData.add("");
        return titleData;
    }

    public class MyRecycleAdapter extends RecyclerView.Adapter<ChannelActivity.MyRecycleViewHolder> {
        private LayoutInflater mInflater;
        private Context context;
        private ChannelBean userdata;


        public MyRecycleAdapter(Context context, ChannelBean userdata) {
            this.userdata = userdata;
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyRecycleViewHolder vh = null;
            switch (viewType) {
                case ChannelBean.TYPE_HOT:
                    vh = new ChannelActivity.HotHolder(mInflater.inflate(R.layout.activity_channel_hot, parent, false));
                    break;
                case ChannelBean.TYPE_BANNER:
                    vh = new ChannelActivity.BannerHolder(mInflater.inflate(R.layout.activity_channel_banner,parent, false));
                    break;
                case ChannelBean.TYPE_TUIJIAN:
                    vh = new ChannelActivity.TuijianHolder(mInflater.inflate(R.layout.activity_channel_tuijian, parent, false));
                    break;
                case ChannelBean.TYPE_LIKE:
                    vh = new ChannelActivity.LikeHolder(mInflater.inflate(R.layout.activity_channel_like, parent, false));
                    break;
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
            holder.bindHolder(userdata, holder);
            //判断不同的ViewHolder做不同的处理
        }

        @Override
        public int getItemViewType(int position) {

            if (position == 0) {//第0个位置是头部信息
                return ChannelBean.TYPE_TUIJIAN;
            } else if (position == 1) {//第一个是横向布局
                return ChannelBean.TYPE_BANNER;
            } else if (position == 2) {//第2个位置是历史布局
                return ChannelBean.TYPE_TUIJIAN;
            } else if (position == 3) {//第2个位置是历史布局
                return ChannelBean.TYPE_LIKE;
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    public abstract class MyRecycleViewHolder extends RecyclerView.ViewHolder {

        public MyRecycleViewHolder(View itemView) {
            super(itemView);

        }

        public abstract void bindHolder(ChannelBean data, MyRecycleViewHolder holder);
    }


    public class BannerHolder extends ChannelActivity.MyRecycleViewHolder {

        public BannerHolder(View itemView) {
            super(itemView);
//            mImg = (ImageView) itemView.findViewById(R.id.fruit_img);
//            mTextView = (TextView) itemView.findViewById(R.id.fruit_text);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(ChannelBean data, MyRecycleViewHolder holder) {
            BannerHolder girlHolder = (BannerHolder) holder;

            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<ChannelBean.DataBean.BlistBean> aitemDatas = data.getBItemDatas();
            for (ChannelBean.DataBean.BlistBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", (item.getUrl()));
                map.put("name", item.getName());
                map.put("hid", item.getId());
                mDataList.add(map);
            }

        }

    }

    public class TuijianHolder extends MyRecycleViewHolder {

        @BindView(R.id.tuijian)
        GridView gridView;
//        R.layout.activity_channel_tuijian

        public TuijianHolder(View itemView) {
            super(itemView);
//            mImg = (ImageView) itemView.findViewById(R.id.fruit_img);
//            mTextView = (TextView) itemView.findViewById(R.id.fruit_text);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(ChannelBean data, MyRecycleViewHolder holder) {
            TuijianHolder girlHolder = (TuijianHolder) holder;
            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<ChannelBean.DataBean.ClistBean> aitemDatas = data.getCItemDatas();
            for (ChannelBean.DataBean.ClistBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", (item.getCover()));
                map.put("name", item.getName());
                map.put("hid", item.getId());
                mDataList.add(map);
            }
            final String[] from = {"cover", "name"};
            final int[] to = {R.id.img, R.id.text};
            SimpleAdapter gridAdapter = new SimpleAdapter(ChannelActivity.this, mDataList, R.layout.gridviewitem, from, to);
            girlHolder.gridView.setAdapter(gridAdapter);
            girlHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    String categoryId = "";//(String) CatIdList[position];
                    Intent i = new Intent(ChannelActivity.this, MovieListActivity.class);
                    i.putExtra("catgoryId", categoryId);
                    startActivity(i);
                }
            });
        }
    }

    public class HotHolder extends MyRecycleViewHolder {

        @BindView(R.id.hotgridview)
        GridView gridView;
//        R.layout.activity_channel_tuijian

        public HotHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(ChannelBean data, MyRecycleViewHolder holder) {
            TuijianHolder girlHolder = (TuijianHolder) holder;
            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<ChannelBean.DataBean.TlistBean> aitemDatas = data.getTItemDatas();
            for (ChannelBean.DataBean.TlistBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", (item.getCover()));
                map.put("name", item.getTagname());
                map.put("hid", item.getId());
                mDataList.add(map);
            }
            final String[] from = {"cover", "name"};
            final int[] to = {R.id.img, R.id.text};
            SimpleAdapter gridAdapter = new SimpleAdapter(ChannelActivity.this, mDataList, R.layout.gridviewitem, from, to);
            girlHolder.gridView.setAdapter(gridAdapter);
            girlHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    String categoryId = "";//(String) CatIdList[position];
                    Intent i = new Intent(ChannelActivity.this, MovieListActivity.class);
                    i.putExtra("catgoryId", categoryId);
                    startActivity(i);
                }
            });
        }
    }


    public class LikeHolder extends MyRecycleViewHolder {

        @BindView(R.id.likegridview)
        GridView gridView;
//        R.layout.activity_channel_tuijian

        public LikeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(ChannelBean data, MyRecycleViewHolder holder) {
            TuijianHolder girlHolder = (TuijianHolder) holder;
            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<ChannelBean.DataBean.LlistBean> aitemDatas = data.getLItemDatas();
            for (ChannelBean.DataBean.LlistBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", (item.getCover()));
                map.put("name", item.getTagname());
                map.put("hid", item.getId());
                mDataList.add(map);
            }
            final String[] from = {"cover", "name"};
            final int[] to = {R.id.img, R.id.text};
            SimpleAdapter gridAdapter = new SimpleAdapter(ChannelActivity.this, mDataList, R.layout.gridviewitem, from, to);
            girlHolder.gridView.setAdapter(gridAdapter);
            girlHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    String categoryId = "";//(String) CatIdList[position];
                    Intent i = new Intent(ChannelActivity.this, MovieListActivity.class);
                    i.putExtra("catgoryId", categoryId);
                    startActivity(i);
                }
            });
        }
    }
}
