package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import org.itheima.recycler.header.RecyclerViewHeader;
import org.itheima.recycler.listener.ItemClickSupport;
import org.itheima.recycler.widget.ItheimaRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class DomainLineActiviy extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    ItheimaRecyclerView myrecyclerView;
    @BindView(R.id.recycler_header)
    RecyclerViewHeader recyclerHeader;
    @BindView(R.id.setting_back)
    ImageView settingBack;
    private List<DomainLineBean.DataBean> mitemDatas;
    private String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domainline);
        ButterKnife.bind(this);

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.recycler_header);
        myrecyclerView = (ItheimaRecyclerView) findViewById(R.id.recycler_view);
        header.attachTo(myrecyclerView);

        getToken();
        getData();

        settingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                DomainLineActiviy.this.finish();
            }
        });

        ItemClickSupport itemClickSupport = new ItemClickSupport(myrecyclerView);
        //点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                String movieId = itemsBeanList.get(position).getId();
//                Intent i = new Intent(MovieListActivity.this,MovieDetailActivity.class);
//                i.putExtra("movieId", movieId);
//                startActivity(i);
            }
        });

    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    private void getData() {
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getDomainLineList?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<DomainLineBean>() {

            @Override
            public void onResponse(DomainLineBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                if (bean.getStatus() == 1) {

                    mitemDatas = bean.getData();
                    RecyclerviewAdapter adapter = new RecyclerviewAdapter(DomainLineActiviy.this, mitemDatas);
                    myrecyclerView.setLayoutManager(new LinearLayoutManager(DomainLineActiviy.this));
                    myrecyclerView.setAdapter(adapter);


                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(DomainLineActiviy.this)
                            .title("温馨提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            .show();
                }
            }

            public void margin(View v, int l, int t, int r, int b) {
                if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                    p.setMargins(l, t, r, b);
                    v.requestLayout();
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


    private int updateDomainLine(String domainId) {

        //开始请求
        Request request = ItheimaHttp.newPostRequest("updateDomainline");//apiUrl格式："xxx/xxxxx"
        Map<String,Object> map = new HashMap<>();
        map.put("domainid", domainId);
        map.put("token", token);
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<RegisterBean>() {

            @Override
            public void onResponse(RegisterBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " +bean);
                int status = bean.getStatus();
                if (status == 1) {
                    MaterialDialog dialog = new MaterialDialog.Builder(DomainLineActiviy.this)
                            .title("温馨提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            .show();

                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(DomainLineActiviy.this)
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


        return 9;
    }

    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

        private Context context;
        private List<DomainLineBean.DataBean> data;

        public RecyclerviewAdapter(Context context, List<DomainLineBean.DataBean> data) {
            this.context = context;
            this.data = data;

        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_domain_line, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            try {
                holder.domainname.setText(data.get(position).getName());
                if (data.get(position).isIschecked()) {
                    holder.isChecked.setVisibility(View.VISIBLE);
                } else {
                    holder.isChecked.setVisibility(View.GONE);
                }

                holder.rowLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("这里是点击每一行item的响应事件", "" + position);

                        for (int i = 0; i < data.size(); i++) {
                            if (i == position) {
                                data.get(i).setIschecked(true);
                            } else {
                                data.get(i).setIschecked(false);
                            }
                        }
                        updateDomainLine(data.get(position).getId());
                        notifyDataSetChanged();

                    }
                });
            } catch (Exception ex) {

            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.domainname)
            TextView domainname;
            @BindView(R.id.isChecked)
            ImageView isChecked;
            @BindView(R.id.row_line)
            RelativeLayout rowLine;
//            R.layout.item_domain_line

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
