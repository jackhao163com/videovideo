package com.cucumber.video.welcomeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class FragmentYijianSubmit extends Fragment {
    private String token;
    private ViewGroup mViewGroup;
    private List<Map<String, Object>> moreDataList;
    private SimpleAdapter adapter;
    private GridView gridView;
    private ImageView submitBtn;
    private TextView contentView;
    private String typeid ;
    private TextView curSelected;

    public static Fragment newInstance() {
        FragmentYijianSubmit fragment = new FragmentYijianSubmit();
        return fragment;
    }

    public FragmentYijianSubmit() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mViewGroup != null) return mViewGroup;
        mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_yijian_submit, container, false);
        gridView = mViewGroup.findViewById(R.id.gridview);
        submitBtn = mViewGroup.findViewById(R.id.yijiansubmit);
        contentView = mViewGroup.findViewById(R.id.content);
        typeid = "0";
        getToken();
        initPage();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yijianSubmitAction();
            }
        });
        return mViewGroup;
    }

    private void initPage() {

        //开始请求
        Request request = ItheimaHttp.newGetRequest("getYijianTypeList?token=" + token);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<YijianTypeBean>() {

            @Override
            public void onResponse(YijianTypeBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " + bean);
                if(bean.getStatus() == 1){
                   List<YijianTypeBean.DataBean> typelist = bean.getData();
                    moreDataList = new ArrayList<Map<String,Object>>();
                    for(YijianTypeBean.DataBean type :typelist){
                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("name",type.getName());
                        map.put("id",type.getId());
                        moreDataList.add(map);
                    }

                    final String[] from={"name"};
                    final int[] to={R.id.typename};

                    adapter=new SimpleAdapter(getActivity(), moreDataList, R.layout.item_yijian_type, from, to);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view, int position,
                                                long arg3) {

                            String categoryId = (String) moreDataList.get(position).get("id");
                            typeid = categoryId;
                            TextView textview = (TextView) view;
                            textview.setClickable(true);
                            if(textview != curSelected &&  curSelected != null){
                                curSelected.setBackground(getResources().getDrawable(R.drawable.border_yellow));
                            }
                            textview.setBackground(getResources().getDrawable(R.drawable.border_yellow_select));
                            curSelected = textview;
                        }
                    });
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                            .title("温馨提示")
                            .content(bean.getMsg())
                            .positiveText("确定")
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
    private void getToken() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(mViewGroup.getContext(), "setting");
        token = helper.getString("token");
    }

    private void yijianSubmitAction(){
        String submitContent = contentView.getText().toString();
        final Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("content",submitContent);
        map.put("type",typeid);
        //开始请求
        Request request = ItheimaHttp.newPostRequest("addYijian");//apiUrl格式："xxx/xxxxx"
        request.putParamsMap(map);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<CommonCommentBean>() {

            @Override
            public void onResponse(final CommonCommentBean bean, Headers headers) {
                System.out.println("print data");
                System.out.println("print data -- " +bean);
                int status = bean.getStatus();
                //判断账号和密码
                if(status == 1){
                    MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                            .title("新增反馈提示")
                            .content(bean.getMsg())
                            .positiveText("确认")
                            //点击事件添加 方式1
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        //列表中新增一条记录
                                        try{
                                            ViewPager viewPager = (ViewPager) mViewGroup.getParent();
                                            viewPager.setCurrentItem(1);
                                        } catch (Exception ex){
                                            Intent i = new Intent(getActivity(), UserActivity.class);
                                            startActivity(i);
                                        }
                                    }

                                }
                            })
                            .show();
                }else
                {

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
