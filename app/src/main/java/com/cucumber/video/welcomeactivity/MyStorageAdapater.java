package com.cucumber.video.welcomeactivity;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStorageAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String, Object>> datas; // 数据源
    private Context context;    // 上下文Context
    private MyStorageListActivity activity;

    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private int finishType = 2;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public MyStorageAdapater(List<Map<String, Object>> datas, Context context, boolean hasMore) {
        // 初始化变量
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
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
            String type = datas.get(position).get("type").toString();
            return type.equals("0") ? normalType : finishType;
        }
    }

    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.likecover)
        ImageView moviecover;
        @BindView(R.id.name)
        TextView moviename;
        @BindView(R.id.curvideolength)
        TextView curvideolength;
        @BindView(R.id.videolength)
        TextView videolength;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
//        R.layout.item_storage_ing

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FinishHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.likecover)
        ImageView moviecover;
        @BindView(R.id.name)
        TextView moviename;
        @BindView(R.id.videolength)
        TextView videolength;
//        private TextView textView;
//        R.layout.item_storage_finish

        public FinishHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            textView = (TextView) itemView.findViewById(R.id.tv);
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_storage_ing, null));
        } else if (viewType == finishType) {
            return new FinishHolder(LayoutInflater.from(context).inflate(R.layout.item_storage_finish, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 如果是正常的imte，直接设置TextView的值
        if (holder instanceof NormalHolder) {
            NormalHolder holder1 = (NormalHolder) holder;
            Map<String,Object> map = datas.get(holder.getAdapterPosition());
            String cover = map.get("cover").toString();

            String videoSize = map.get("videoSize").toString();
            videoSize = videoSize.isEmpty() ? "0" : videoSize;
            String videoMB = getNetFileSizeDescription(Long.parseLong(videoSize)*1024);

            String curSize = map.get("curSize").toString();
            curSize = curSize.isEmpty() ? "0" : curSize;
            String curSizeMB = getNetFileSizeDescription(Long.parseLong(curSize)*1024);

            holder1.moviename.setText(map.get("name").toString());
            Picasso.with(context)
                    .load(cover)
                    .into(holder1.moviecover);
            holder1.videolength.setText(videoMB+"");
            holder1.curvideolength.setText(curSizeMB+"");

        } else if (holder instanceof FinishHolder) {
            FinishHolder holder2 = (FinishHolder) holder;
            Map<String,Object> map = datas.get(holder.getAdapterPosition());
            String cover = map.get("cover").toString();

            String videoSize = map.get("videoSize").toString();
            videoSize = videoSize.isEmpty() ? "0" : videoSize;
            String videoMB = getNetFileSizeDescription(Long.parseLong(videoSize)*1024);

            String curSize = map.get("curSize").toString();
            curSize = curSize.isEmpty() ? "0" : curSize;
            String curSizeMB = getNetFileSizeDescription(Long.parseLong(curSize)*1024);

            holder2.moviename.setText(map.get("name").toString());
            Picasso.with(context)
                    .load(cover)
                    .into(holder2.moviecover);
            holder2.videolength.setText(curSizeMB+"");
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
                    }, 500);
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

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<Map<String, Object>> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

}