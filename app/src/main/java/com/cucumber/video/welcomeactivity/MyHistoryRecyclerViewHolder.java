package com.cucumber.video.welcomeactivity;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;

public class MyHistoryRecyclerViewHolder extends BaseRecyclerViewHolder<MovieBean.DataBean.ItemsBean> {

    private ImageView cover;
    private TextView name;
    private TextView movieviews;

    //换成你布局文件中的id
    public MyHistoryRecyclerViewHolder(ViewGroup parentView, int itemResId) {
        super(parentView, itemResId);
        try{
            cover = (ImageView) parentView.findViewById(R.id.likecover);
            name = (TextView) parentView.findViewById(R.id.name);
            movieviews = (TextView) parentView.findViewById(R.id.likemovieviews);
        }
        catch (Exception ex){

        }
    }

    /**
     * 绑定数据的方法，在mData获取数据（mData声明在基类中）
     */
    @Override
    public void onBindRealData() {
        try{
            String actorname = mData.getName().equals("") ? "" : mData.getName();
            String coverurl = mData.getCover().equals("") ? "" : mData.getCover();
            String views = mData.getViews().equals("") ? "" : mData.getViews();
            name.setText(actorname);
            movieviews.setText(views+"次播放");
//                Picasso.with(getActivity())
//                        .load(coverurl)
//                        .into(cover);
        }
        catch (Exception ex){
            Log.i("",ex.toString());
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
