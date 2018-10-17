package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieDetailBean.DataBean.CommentlistBean> datas; // 数据源
    private Context context;    // 上下文Context
    private MovieDetailActivity activity;    // 上下文Context

    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean isLoadMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public CommentListAdapter(List<MovieDetailBean.DataBean.CommentlistBean> datas, Context context,MovieDetailActivity activity, boolean hasMore) {
        // 初始化变量
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.activity = activity;
    }

    // 获取条目数量，之所以要加1是因为增加了一条footView
    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return datas.size();
    }


    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_comment, parent,false));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview,  parent,false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 如果是正常的imte，直接设置TextView的值
        if (holder instanceof NormalHolder) {
            NormalHolder commentHolder = (NormalHolder) holder;
            String avatar = datas.get(position).getAvatar();
            String nickname = datas.get(position).getUsername();
            String content = datas.get(position).getContent();
            Object dateobj = datas.get(position).getCreatetime();
            String date = "";
            if(dateobj != null){
                date = dateobj.toString();
                date = DateUtils.timedate(date);
            }
            else {
                date = "几天前";
            }
            commentHolder.commentMobile.setText(nickname);
            commentHolder.commentContent.setText(content);
            commentHolder.commentTime.setText(date);
            Picasso.with(context)
                    .load(avatar)
                    .into(commentHolder.commentAvtar);

            //显示两条子评论
            List<MovieDetailBean.DataBean.CommentlistBean.SubitemsBean> subitems = datas.get(position).getSubitems();
            int i = 0;
            if(commentHolder.subcomment.getChildCount() == 0){
                for(MovieDetailBean.DataBean.CommentlistBean.SubitemsBean comment : subitems){
                    if(i > 1)return;
                    View subCommentItem = LayoutInflater.from(context).inflate(R.layout.item_movie_subcomment,null);
                    TextView from = (TextView)subCommentItem.findViewById(R.id.subcomment_from);
                    TextView to = (TextView)subCommentItem.findViewById(R.id.subcomment_to);
                    TextView subcontent = (TextView)subCommentItem.findViewById(R.id.subcomment_content);
                    from.setText(comment.getFromusername());
                    to.setText(comment.getTousername());
                    subcontent.setText(comment.getContent());
                    commentHolder.subcomment.addView(subCommentItem);
                    i++;
                }
            }

            commentHolder.subcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // 点击事件
                    String commentid = datas.get(position).getId();
                    String msg = "点击位置："+position + ",id:"+commentid;
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    activity.initSubCommentListView(commentid);
                }
            });

        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (datas.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (datas.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((FootHolder) holder).tips.setText("没有更多数据了");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 0);
                }
            }
        }
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        datas = new ArrayList<>();
    }

    public boolean getHasMore(){
        return isLoadMore;
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<CommentListBean.DataBean.ItemsBean> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        List<MovieDetailBean.DataBean.CommentlistBean> mDatas = new ArrayList<MovieDetailBean.DataBean.CommentlistBean>();
        if(newDatas != null){
            for (CommentListBean.DataBean.ItemsBean item : newDatas) {
                MovieDetailBean.DataBean.CommentlistBean map = new MovieDetailBean.DataBean.CommentlistBean();
                map = modelA2B(item,MovieDetailBean.DataBean.CommentlistBean.class);
                mDatas.add(map);
            }
        }

        if (newDatas != null) {
            datas.addAll(mDatas);
        }
        this.hasMore = hasMore;
        this.isLoadMore = hasMore;
        notifyDataSetChanged();
    }



    public static MovieDetailBean.DataBean.CommentlistBean modelA2B(CommentListBean.DataBean.ItemsBean modelA, Class<MovieDetailBean.DataBean.CommentlistBean> bClass) {
        try {
            Gson gson = new Gson();
            String gsonA = gson.toJson(modelA);
            MovieDetailBean.DataBean.CommentlistBean instanceB = gson.fromJson(gsonA, bClass);

            Log.d("", "modelA2B A=" + modelA.getClass() + " B=" + bClass + " 转换后=" + instanceB);
            return instanceB;
        } catch (Exception e) {
            Log.e("", "modelA2B Exception=" + modelA.getClass() + " " + bClass + " " + e.getMessage());
            return null;
        }
    }


    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.subcomment)
        LinearLayout subcomment;
        private TextView textView;
//        R.layout.item_movie_comment

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }
}
