package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TagListActivity extends AppCompatActivity {
    @BindView(R.id.cateList)
    LinearLayout cateList;
    @BindView(R.id.flowlayout)
    XCFlowLayout flowlayout;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.setting)
    ImageView setting;
    private String token;
    private String curCatId;
    private TextView currentCat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_taglist);
        ButterKnife.bind(this);
        getToken();
        initView();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TagListActivity.this.finish();
            }
        });
    }

    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        token = helper.getString("token");
    }

    private void initView() {
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getMovieCategoryList?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CatBean>() {

            @Override
            public void onResponse(CatBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                if (bean.getStatus() == 1) {
                    List<CatBean.DataBean> taglistdata = bean.getData();
                    if (taglistdata.size() > 0) {
                        for (CatBean.DataBean tag : taglistdata) {
                            TextView tagdiv = (TextView) LayoutInflater.from(TagListActivity.this).inflate(R.layout.item_order_div, null);
//                    TextView tagdiv = (TextView)tagItem.findViewById(R.id.tagid);
                            tagdiv.setText(tag.getName());
                            tagdiv.setTag(tag.getId());
                            final String catid = tag.getId();
                            tagdiv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setSelected(view);
                                    getTagList(catid);
                                }
                            });
                            if (curCatId == null) {
                                curCatId = tag.getId();
                                getTagList(curCatId);
                                currentCat = tagdiv;
                                setSelected(tagdiv);
                            }
                            cateList.addView(tagdiv);
                        }
                    }
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(TagListActivity.this)
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


    private void setSelected(View v) {
        TextView textview = (TextView) v;
        textview.setBackground(getResources().getDrawable(R.drawable.bg_yuan));
        textview.setTextColor(Color.parseColor("#d2b588"));
        currentCat.setBackgroundColor(Color.parseColor("#ffffff"));
        currentCat.setTextColor(Color.parseColor("#808080"));
        currentCat = textview;
    }

    private void getTagList(String catid) {
        flowlayout.removeAllViews();
        //开始请求
        Request request = ItheimaHttp.newGetRequest("getTagListByCat?token=" + token + "&categoryid=" + catid);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<TagBean>() {

            @Override
            public void onResponse(TagBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);

                if (bean.getStatus() == 1) {
                    List<TagBean.DataBean> taglistdata = bean.getData();
                    try{
                    if (taglistdata.size() > 0) {
                        for (TagBean.DataBean tag : taglistdata) {
                            TextView tagdiv = (TextView) LayoutInflater.from(TagListActivity.this).inflate(R.layout.item_order_div, null);
//                    TextView tagdiv = (TextView)tagItem.findViewById(R.id.tagid);
                            tagdiv.setText(tag.getTagname());
                            tagdiv.setTag(tag.getId());
                            final String tagId = tag.getId();
                            tagdiv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //跳转到搜索结果页
                                    Intent i = new Intent(TagListActivity.this, MovieResultActivity.class);
                                    i.putExtra("tagId", tagId);
                                    startActivity(i);
                                }
                            });
                            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            container.addView(tagdiv);
                            flowlayout.addView(tagdiv,lp);
                        }
                    }
                    }
                    catch (Exception ex){

                    }
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(TagListActivity.this)
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
}
