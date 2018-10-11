package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<ChannelBean> channel_info;

    public GridViewAdapter(Context context, List<ChannelBean> channel_info) {
        this.context = context;
        this.channel_info = channel_info;

    }

    @Override
    public int getCount() {
        return channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_movie,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

//        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
//        viewHolder.tvChannel.setText(channelInfoBean.getChannel_name());
//        Glide.with(context)
//                .load(SyncStateContract.Constants.BASE_URl_IMAGE +channelInfoBean.getImage())
//                .into(viewHolder.ivChannel);
        return convertView;
    }
    class ViewHolder{
        protected ImageView ivChannel;
        protected TextView tvChannel;
        public ViewHolder(View convertView) {
//            ivChannel = (ImageView) convertView.findViewById(R.id.iv_channel);
//            tvChannel = (TextView) convertView.findViewById(R.id.tv_channel);
        }
    }
}
