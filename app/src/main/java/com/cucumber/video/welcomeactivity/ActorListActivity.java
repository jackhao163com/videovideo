package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.retrofitutils.L;
import com.squareup.picasso.Picasso;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.header.RecyclerViewHeader;
import org.itheima.recycler.listener.ItemClickSupport;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;

public class ActorListActivity extends AppCompatActivity implements View.OnClickListener {
    BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder;
    PullToLoadMoreRecyclerView pullToLoadMoreRecyclerView;

    @BindView(R.id.recycler_view)
    ItheimaRecyclerView recyclerView;
    @BindView(R.id.order_renqi)
    TextView orderRenqi;
    @BindView(R.id.order_pianliang)
    TextView orderPianliang;
    @BindView(R.id.order_all)
    TextView orderAll;
    @BindView(R.id.order_renqi3)
    TextView orderRenqi3;
    @BindView(R.id.order_cat)
    LinearLayout orderCat;
    @BindView(R.id.togoback)
    ImageView togoback;
    @BindView(R.id.recycler_header)
    RecyclerViewHeader recyclerHeader;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout myswipeRefreshLayout;
    private ItheimaRecyclerView myrecyclerView;
    private TextView currentOrder ;
    private TextView currentCat ;

    Integer pageIndex = 0;
    private int state = 0;
    private static final int STATE_FRESH = 1;
    private static final int STATE_MORE = 2;
    ArrayList<ActorListBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actorlist);
        ButterKnife.bind(this);

        //获取传参
        Intent intent = getIntent();
        String catgoryId = intent.getStringExtra("catgoryId");
        String orderId = intent.getStringExtra("orderId");

        currentOrder = orderRenqi;
        currentCat = orderAll;

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.recycler_header);
        myrecyclerView = (ItheimaRecyclerView) findViewById(R.id.recycler_view);
        header.attachTo(myrecyclerView);

        ItemClickSupport itemClickSupport = new ItemClickSupport(myrecyclerView);
        //点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String actorId = itemsBeanList.get(position).getId();

                Intent intent = new Intent(ActorListActivity.this, UserActivity.class);
                intent.putExtra("actorId", actorId);
                startActivity(intent);

            }
        });

        togoback.setOnClickListener(this);
        orderRenqi.setOnClickListener(this);
        orderPianliang.setOnClickListener(this);
        orderAll.setOnClickListener(this);
        orderRenqi3.setOnClickListener(this);

        getToken();


        pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<ActorListBean>(myswipeRefreshLayout, myrecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.item_actor;
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
                return "getactorlist?pageSize=6&pageIndex=" + pageIndex + "&token=" + token;
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

        pullToLoadMoreRecyclerView.setLoadingDataListener(new PullToLoadMoreRecyclerView.LoadingDataListener<ActorListBean>() {

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
            public void onSuccess(ActorListBean o, Headers headers) {
                //监听http请求成功，如果不需要监听可以不重新该方法
                L.i("setLoadingDataListener onSuccess: " + o);
                List<ActorListBean.DataBean.ItemsBean> itemDatas = o.getItemDatas();
                if (itemDatas.size() == 0) {
                    holder.loadingFinish((String) null);
                    if (myswipeRefreshLayout != null) {
                        myswipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    for (ActorListBean.DataBean.ItemsBean item : itemDatas) {
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
//        pullToLoadMoreRecyclerView.putParam("categoryId",catgoryId);
//        pullToLoadMoreRecyclerView.putParam("orderId",orderId);
        pullToLoadMoreRecyclerView.requestData();
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    @Override
    public void onClick(View v) {
        Log.i("","点击了---"+v.getId());
        switch (v.getId()) {
            case R.id.order_pianliang:
            case R.id.order_renqi:
                refreshPage(v,1);
                break;
            case R.id.order_all:
            case R.id.order_renqi3:
                refreshPage(v,2);
                break;
            case R.id.togoback:
                ActorListActivity.this.finish();
                break;
        }
    }

    private void refreshPage(View v,int group){
        TextView textview = (TextView) v;
        textview.setBackground(getResources().getDrawable(R.drawable.bg_yuan) );
        textview.setTextColor(Color.parseColor("#d2b588"));
        if(group ==1){
            currentOrder.setBackgroundColor(Color.parseColor("#ffffff"));
            currentOrder.setTextColor(Color.parseColor("#808080"));
            currentOrder = textview;
        }else{
            currentCat.setBackgroundColor(Color.parseColor("#ffffff"));
            currentCat.setTextColor(Color.parseColor("#808080"));
            currentCat = textview;
        }
        pullToLoadMoreRecyclerView.mLoadMoreRecyclerViewAdapter.addDatas(false,null);
        pullToLoadMoreRecyclerView.onRefresh();
    }

    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<ActorListBean.DataBean.ItemsBean> {

        @BindView(R.id.actor_image)
        CircleImageView actorImage;
        @BindView(R.id.actor_name)
        TextView actorName;
        int position = 0;


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
            actorName.setText(actorname);

            RelativeLayout parentView = (RelativeLayout) actorName.getParent();
            int randomInt = mPosition%5;//getNum(4);
            int[] colorList = {R.color.random1,R.color.random2,R.color.random3,R.color.random4,R.color.random5};
            parentView.setBackgroundColor(mContext.getResources().getColor(colorList[randomInt]));


            if(!cover.isEmpty()){
                Picasso.with(mContext)
                        .load(cover)
                        .into(actorImage);
            }
            position++;
        }

        public static int getNum(int endNum){
            if(endNum > 0){
                Random random = new Random();
                return random.nextInt(endNum);
            }
            return 0;
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
