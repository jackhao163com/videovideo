package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hedan.textdrawablelibrary.TextViewDrawable;
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
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tab_home)
    TextViewDrawable tabHome;
    @BindView(R.id.tab_channel)
    TextViewDrawable tabChannel;
    @BindView(R.id.tab_find)
    TextViewDrawable tabFind;
    @BindView(R.id.tab_user)
    TextViewDrawable tabUser;
    private ImageView mSettings;
    private String token;
    private MyRecycleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        getToken();
        setTabClick();

        //开始请求
        Request request = ItheimaHttp.newGetRequest("getuserindexdata?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<UserDataModel>() {

            @Override
            public void onResponse(UserDataModel bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                if (bean.getStatus() == 1) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this, LinearLayoutManager.VERTICAL, false));
                    mAdapter = new MyRecycleAdapter(UserActivity.this, bean);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else if(bean.getStatus() == -1){
                    startActivity(new Intent(UserActivity.this, LoginActivity.class));
                } else{
                    MaterialDialog dialog = new MaterialDialog.Builder(UserActivity.this)
                            .title("温馨提示")
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

    private void setTabClick() {
        Drawable top = getResources().getDrawable(R.drawable.tab4_1);
        tabUser.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);

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
                i = new Intent(UserActivity.this, MainActivity.class);
                startActivity(i);
                return;
            case 1:
                i = new Intent(UserActivity.this, ChannelActivity.class);
                startActivity(i);
                return;
            case 2:
                i = new Intent(UserActivity.this, FindActivity.class);
                startActivity(i);
                return;
            case 3:
                return;
        }
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }


    public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleViewHolder> {
        private LayoutInflater mInflater;
        private Context context;
        private UserDataModel userdata;


        public MyRecycleAdapter(Context context, UserDataModel userdata) {
            this.userdata = userdata;
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyRecycleViewHolder vh = null;
            switch (viewType) {
                case UserDataModel.TYPE_TOP:
                    vh = new UserInfoHolder(mInflater.inflate(R.layout.item_user_top, parent, false));
                    break;
                case UserDataModel.TYPE_HENGXIANG:
                    vh = new HengXiangHolder(mInflater.inflate(R.layout.activity_user_hengxiang, parent, false));
                    break;
                case UserDataModel.TYPE_HISTORY:
                    vh = new HistoryHolder(mInflater.inflate(R.layout.activity_user_history, parent, false));
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
                return UserDataModel.TYPE_TOP;
            } else if (position == 1) {//第一个是横向布局
                return UserDataModel.TYPE_HENGXIANG;
            } else if (position == 2) {//第2个位置是历史布局
                return UserDataModel.TYPE_HISTORY;
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public abstract class MyRecycleViewHolder extends RecyclerView.ViewHolder {

        public MyRecycleViewHolder(View itemView) {
            super(itemView);

        }

        public abstract void bindHolder(UserDataModel data, MyRecycleViewHolder holder);
    }

    public class UserInfoHolder extends MyRecycleViewHolder {
        @BindView(R.id.actor_image)
        CircleImageView actorImage;
        @BindView(R.id.textView_level)
        TextView textViewLevel;
        @BindView(R.id.textView_phone)
        TextView textViewPhone;
        @BindView(R.id.remain_num)
        TextView remainNum;
        @BindView(R.id.textView4)
        TextView needPerson;
        @BindView(R.id.levelimg)
        ImageView levelimg;
        @BindView(R.id.setting)
        ImageView mSettings;
//        R.layout.item_user_top

        public UserInfoHolder(View itemView) {
            super(itemView);
//            mImg = (ImageView) itemView.findViewById(R.id.fruit_img);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(UserDataModel data, MyRecycleViewHolder holder) {
//            mImg.setImageResource(data.mImg);
            UserDataModel.DataBean.UserinfoBean userinfo = data.getUItemDatas();
            String url = (String) userinfo.getAvatar();
            Picasso.with(UserActivity.this)
                    .load(url)
                    .into(actorImage);
            textViewLevel.setText(userinfo.getLevelName());
            textViewPhone.setText(userinfo.getMobile());
            remainNum.setText(userinfo.getRemainnums() + "/" + userinfo.getViewnums());
            needPerson.setText("下一等级还差" + userinfo.getNeedcount() + "人");

            mSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(UserActivity.this, SettingActivity.class));
                }
            });

            //levelimg.setImageDrawable(getResources().getDrawable((R.drawable.remind2)));
        }
    }

    public class HengXiangHolder extends MyRecycleViewHolder {
        private String[] CatIdList;
        private List<Map<String, Object>> dataList;
        @BindView(R.id.hengxianggridview)
        GridView gridView;

        public HengXiangHolder(View itemView) {
            super(itemView);
//            mImg = (ImageView) itemView.findViewById(R.id.fruit_img);
//            mTextView = (TextView) itemView.findViewById(R.id.fruit_text);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(UserDataModel data, MyRecycleViewHolder holder) {
//            mImg.setImageResource(data.mImg);
//            mTextView.setText(data.mName);
            HengXiangHolder girlHolder = (HengXiangHolder) holder;
            initData();
            final String[] from = {"cover", "name"};
            final int[] to = {R.id.img, R.id.text};
            SimpleAdapter gridAdapter = new SimpleAdapter(UserActivity.this, dataList, R.layout.gridviewitem, from, to);
            girlHolder.gridView.setAdapter(gridAdapter);
            girlHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    String categoryId = (String) CatIdList[position];
                    Intent i;
                    switch (categoryId) {
                        case "3":
                            i = new Intent(UserActivity.this, NoticyActivity.class);
                            i.putExtra("type", 0);
                            startActivity(i);
                            break;
                        case "1":
                            i = new Intent(UserActivity.this, TuiActivity.class);
                            i.putExtra("type", 0);
                            startActivity(i);
                            break;
                        case "2":
                            i = new Intent(UserActivity.this, YijianActivity.class);
                            i.putExtra("type", 0);
                            startActivity(i);
                            break;
                    }
                }
            });
        }

        void initData() {
            //图标
            int icno[] = {R.mipmap.tuiguang, R.mipmap.fankui, R.mipmap.tongzhi, R.mipmap.jiaoliuqun};
            //图标下的文字
            String name[] = {"我要分享", "意见反馈", "通知", "火爆交流群"};
            CatIdList = new String[]{"1", "2", "3", "4"};
            dataList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < icno.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", icno[i]);
                map.put("name", name[i]);
                map.put("id", CatIdList[i]);
                dataList.add(map);
            }
        }
    }

    public class HistoryHolder extends MyRecycleViewHolder {

        @BindView(R.id.viewnums)
        TextView viewnums;
        @BindView(R.id.historyGridView)
        GridView historyGridView;
        @BindView(R.id.huancunNums)
        TextView huancunNums;
        @BindView(R.id.likenums)
        TextView likenums;
        @BindView(R.id.toHistoryList)
        ImageView toHistoryList;
        @BindView(R.id.toStorageList)
        ImageView toStorageList;
        @BindView(R.id.toLikeList)
        ImageView toLikeList;

//        R.layout.activity_user_history

        public HistoryHolder(View itemView) {
            super(itemView);
//            mImg = (ImageView) itemView.findViewById(R.id.fruit_img);
//            mTextView = (TextView) itemView.findViewById(R.id.fruit_text);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindHolder(UserDataModel data, MyRecycleViewHolder holder) {

            toHistoryList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(UserActivity.this, HistoryActivity.class));
                }
            });

            toStorageList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(UserActivity.this, MyStorageListActivity.class));
                }
            });

            toLikeList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(UserActivity.this, LikeActivity.class));
                }
            });
            UserDataModel.DataBean.UserinfoBean userinfo = data.getUItemDatas();
            viewnums.setText("目前历史观看过" + userinfo.getMovieviewmums() + "部");
            likenums.setText("目前已有喜欢" + userinfo.getLikenums() + "部");
            huancunNums.setText("目前本地大片有" + userinfo.getStoragenums() + "部");

            HistoryHolder girlHolder = (HistoryHolder) holder;
            final List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            List<UserDataModel.DataBean.HlistBean> aitemDatas = data.getHItemDatas();
            for (UserDataModel.DataBean.HlistBean item : aitemDatas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cover", (item.getCover()));
                map.put("moviename", item.getMovieName());
                map.put("movieid", item.getMovieid());
                map.put("hid", item.getId());
                mDataList.add(map);
            }
            final String[] from = {"cover", "moviename"};
            final int[] to = {R.id.img, R.id.text};
            SimpleAdapter gridAdapter = new SimpleAdapter(UserActivity.this, mDataList, R.layout.item_common, from, to);
            gridAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    if (view instanceof ImageView) {
                        String url = (String) data;
                        ImageView iv = (ImageView) view;
                        Picasso.with(UserActivity.this)
                                .load(url)
                                .into(iv);
                        return true;
                    } else
                        return false;
                }
            });
            setGridView(gridAdapter, mDataList.size());
            girlHolder.historyGridView.setAdapter(gridAdapter);

            girlHolder.historyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    String movieId = (String) mDataList.get(position).get("movieid");
                    Intent i = new Intent(UserActivity.this, MovieDetailActivity.class);
                    i.putExtra("movieId", movieId);
                    startActivity(i);
                }
            });
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
            historyGridView.setLayoutParams(params);
            historyGridView.setColumnWidth(itemWidth);
            historyGridView.setStretchMode(GridView.NO_STRETCH);
            historyGridView.setNumColumns(count);
        }
    }

}
