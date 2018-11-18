package com.cucumber.video.welcomeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneFragment extends Fragment{
    private  ViewGroup mViewRoot;
    private MyImgScroll mImgScroller;
    private List<View> mList;
    private List<String> mImageUrl;
    private List<String> mIconList;
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private GridView moreGrideView;
    private List<Map<String, Object>> moreDataList;
    private SimpleAdapter moreAdapter;
    private ImageView mSearch;
    private ImageView mDownload;
    private ImageView mHistory;
    private EditText mEdit;
    private Thread bannerThread;
    private Thread hotmovieThread;
    private Thread hotsingerThread;
    private ListView mListView;
    private List<String> actor_numberList;
    private List<String> actor_nameList;
    private List<String> actor_introductionList;
   // private List
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chumingchao","onefragment create");
        mImageUrl = new ArrayList<String>();
        mIconList = new ArrayList<String>();
        bannerThread = new Thread(){
            @Override
            public void run() {
                super.run();
                getBanner();
                Message m = new Message();
                m.what = 0;
                //MainActivity.myHandler.sendMessage(m);
            }
        };
        hotmovieThread = new Thread();
        hotsingerThread = new Thread();
        bannerThread.start();
        hotmovieThread.start();
        hotsingerThread.start();

    }

    @Override
    public void onStop() {
        mImgScroller.stopTimer();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewRoot = (ViewGroup) inflater.inflate(R.layout.fragment_one, container, false);
        initView();
        initData();
        InitViewPager();
        initListView();
        initevent();
        String[] from={"img","text"};
        String[] moreFrom = {"moreImg","moreText"};

        int[] to={R.id.img,R.id.text};

        adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridviewitem, from, to);
        moreAdapter = new SimpleAdapter(getActivity(),moreDataList,R.layout.gridviewitemmore, moreFrom,to);

        gridView.setAdapter(adapter);
        moreGrideView.setAdapter(moreAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
				/*AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();*/
            }
        });


        return  mViewRoot;
    }
    private  void initevent(){
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainSearchActivity.class));
                getActivity().finish();//关闭页面
            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdit.setFocusable(false);
                startActivity(new Intent(getActivity(), MainSearchActivity.class));
                getActivity().finish();//关闭页面
            }
        });
    }
    public String getAccount() {
        return mEdit.getText().toString().trim();//去掉空格
    }
    void initView(){
        mImgScroller = mViewRoot.findViewById(R.id.bannerViewPager);
        gridView = mViewRoot.findViewById(R.id.gridview);
        mSearch = mViewRoot.findViewById(R.id.search);
        mDownload = mViewRoot.findViewById(R.id.download);
        mHistory = mViewRoot.findViewById(R.id.history);
        mEdit = mViewRoot.findViewById(R.id.search_content);
        moreGrideView = mViewRoot.findViewById(R.id.moregridview);
        mListView = mViewRoot.findViewById(R.id.listview_actor);
    }
    private void initListView(){
        actor_numberList = new ArrayList<>();
        actor_nameList = new ArrayList<>();
        actor_introductionList = new ArrayList<>();
        actor_numberList.add("3部电影");
        actor_numberList.add("1部电影");
        actor_nameList.add("李三");
        actor_nameList.add("王四");
        actor_introductionList.add("打发阿发发生大发沙发沙发");
        actor_introductionList.add("affasfasfasfsasfasf");
        mListView.setAdapter(new MyBaseAdapter());

    }
    void initData() {
        //图标
        int icno[] = { R.mipmap.jiemu_icon, R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.all};
        //图标下的文字
        String name[]={"娱乐","网络电影","电视剧","综艺","魔幻","游戏","片花","全部"};
        int icon[] = {R.mipmap.zxpy1,R.mipmap.zxpy2};
        String moreName[] = {"好声音了解下","突发意外邓紫棋心疼流泪"};
        dataList = new ArrayList<Map<String, Object>>();
        moreDataList = new ArrayList<Map<String,Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text",name[i]);
            dataList.add(map);
        }
        for(int i = 0; i < icon.length;i++){
            Map<String,Object> map= new HashMap<String, Object>();
            map.put("moreImg",icon[i]);
            map.put("moreText",moreName[i]);
            moreDataList.add(map);
        }
    }
    private void InitViewPager() {
        mList = new ArrayList<View>();
        int[] imageResId = new int[] { R.mipmap.cainixihuan2,R.mipmap.index_banner1,R.mipmap.cainixihuan2 ,R.mipmap.index_banner1};
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {// 设置图片点击事件
                    Toast.makeText(getActivity(),
                            "点击了:" + mImgScroller.getCurIndex(), Toast.LENGTH_SHORT)
                            .show();
                }
            });
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mList.add(imageView);
        }
        mImgScroller.start(getActivity(), mList, 4000, null,
                0, 0,
                0, 0);
    }
    public  void freshBanner(){}
    private  void getBanner(){
        URL url;
        SharedPreferencesUtils helper = new SharedPreferencesUtils(getActivity(), "setting");
        String mToken = helper.getString("token");
        try {
            url = new URL(MyToolUtils.getAppDomain() + "index.php/font/app/getBannerList");
            /*封装子对象*/
            JSONObject ClientKey = new JSONObject();

            ClientKey.put("token", mToken);

            /*封装Person数组*/
            //   JSONObject params = new JSONObject();
            //   params.put("Person", ClientKey);
            /*把JSON数据转换成String类型使用输出流向服务器写*/
            String content = String.valueOf(ClientKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);//设置允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Charset", encoding);
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
            /*服务器返回的响应码*/

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String retData = null;
                String responseData = "";
                JSONArray jsonArray = null;
                while ((retData = in.readLine()) != null) {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                int status = jsonObject.getInt("status");
                if(status == 1) {
                    jsonArray = jsonObject.getJSONArray("data");
                    JSONObject data = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        data = jsonArray.getJSONObject(i);
                        mImageUrl.add(data.getString("url"));

                    }
                }else{
                    Toast.makeText(getActivity(), "token过期，请重新登录", Toast.LENGTH_SHORT).show();
                }
//System.out.println(result);
                //   String success = succObject.getString("id");

                in.close();

//System.out.println(success);
                //  Toast.makeText(RegisterActivity.this, success, Toast.LENGTH_SHORT).show();
                //   Intent intentToLogin = new Intent();
                //   intentToLogin.setClass(Register.this, Login.class);
                //   startActivity(intentToLogin);
                //    finish();
            } else {
                Toast.makeText(getActivity(), "服务器未响应", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }
    private  void getMovieType(){
        URL url;
        SharedPreferencesUtils helper = new SharedPreferencesUtils(getActivity(), "setting");
        String mToken = helper.getString("token");
        try {
            url = new URL(MyToolUtils.getAppDomain() + "index.php/font/app/getMovieCategoryList");
            /*封装子对象*/
            JSONObject ClientKey = new JSONObject();

            ClientKey.put("token", mToken);

            /*封装Person数组*/
            //   JSONObject params = new JSONObject();
            //   params.put("Person", ClientKey);
            /*把JSON数据转换成String类型使用输出流向服务器写*/
            String content = String.valueOf(ClientKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);//设置允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Charset", encoding);
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
            /*服务器返回的响应码*/

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String retData = null;
                String responseData = "";
                JSONArray jsonArray = null;
                while ((retData = in.readLine()) != null) {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                int status = jsonObject.getInt("status");
                if(status == 1) {
                    jsonArray = jsonObject.getJSONArray("data");
                    JSONObject data = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        data = jsonArray.getJSONObject(i);
                        mIconList.add(data.getString("icon"));

                    }
                }else{
                    Toast.makeText(getActivity(), "token过期，请重新登录", Toast.LENGTH_SHORT).show();
                }
//System.out.println(result);
                //   String success = succObject.getString("id");

                in.close();

//System.out.println(success);
                //  Toast.makeText(RegisterActivity.this, success, Toast.LENGTH_SHORT).show();
                //   Intent intentToLogin = new Intent();
                //   intentToLogin.setClass(Register.this, Login.class);
                //   startActivity(intentToLogin);
                //    finish();
            } else {
                Toast.makeText(getActivity(), "服务器未响应", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }
    class MyBaseAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return actor_nameList.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OneFragment.ViewHold hold=null;
            if(convertView==null){
                convertView=View.inflate(getActivity(),R.layout.listview_item,null);
                if(hold==null){
                    hold=new OneFragment.ViewHold();
                    hold.number = convertView.findViewById(R.id.actor_number);
                    hold.actor_pic = convertView.findViewById(R.id.actor_image);
                    hold.actor_name = convertView.findViewById(R.id.actor_name);
                    hold.actor_introduction = convertView.findViewById(R.id.actor_introduction);
                }
                convertView.setTag(hold);
            }else{
                hold = (OneFragment.ViewHold) convertView.getTag();
            }

            hold.number.setText(actor_numberList.get(position));
            hold.actor_name.setText(actor_nameList.get(position));
            hold.actor_introduction.setText(actor_introductionList.get(position));




            return convertView;
        }
    }
    class ViewHold{
        TextView number;
        ImageView actor_pic;
        TextView actor_name;
        TextView actor_introduction;
    }
}
