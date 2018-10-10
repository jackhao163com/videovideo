package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.itheima.loopviewpager.LoopViewPager;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements MyItemClickListener {

	private LoopViewPager loopViewPager;
	private  List<Integer> mItems;
	private Context mContext;
	private ItheimaRecyclerView myrecyclerView;
	RecyclerView recyclerView;
	MainAdapter Adapter_GridLayout;
	private ArrayList<HashMap<String, Object>> listItem;


	private  ViewGroup mViewRoot;
	private List<View> mList;
	private List<String> mImageUrl;
	private List<String> mIconList;
	private GridView gridView;
	private List<Map<String, Object>> dataList;
	private SimpleAdapter adapter;
	private GridView moreGrideView;
	private List<Map<String, Object>> moreDataList;
	private List<Map<String, Object>> actorDataList;
	private SimpleAdapter moreAdapter;
	private ImageView mSearch;
	private ImageView mDownload;
	private ImageView mHistory;
	private EditText mEdit;
	private Thread bannerThread;
	private Thread hotmovieThread;
	private Thread hotsingerThread;
	private ListView mListView;
    private String[] CatIdList;
	private  String token ;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		loopViewPager = (LoopViewPager) findViewById(R.id.lvp_pager);
		loopViewPager.setImgData(imgListString());
		loopViewPager.setTitleData(titleListString());
		loopViewPager.start();

		getToken();

		Log.e("chumingchao","onefragment create");
		mImageUrl = new ArrayList<String>();
		mIconList = new ArrayList<String>();
		hotmovieThread = new Thread();
		hotsingerThread = new Thread();
		hotmovieThread.start();
		hotsingerThread.start();


		initView();
		initData();
		initevent();
		final String[] from={"img","text"};
		final String[] moreFrom = {"moreImg","moreText"};

		final int[] to={R.id.img,R.id.text};


		//开始请求
		Request request = ItheimaHttp.newGetRequest("getindexdata?token="+token);//apiUrl格式："xxx/xxxxx"
		Call call = ItheimaHttp.send(request, new HttpResponseListener<CategoryBean>() {

			@Override
			public void onResponse(CategoryBean bean, Headers headers) {
				System.out.println("print data");
				System.out.println("print data -- " +bean);

				List<CategoryBean.DataBean.MlistBean> mitemDatas = bean.getItemDatas();
				List<CategoryBean.DataBean.AlistBean> aitemDatas = bean.getAItemDatas();

				moreDataList = new ArrayList<Map<String,Object>>();
				for (CategoryBean.DataBean.MlistBean item : mitemDatas) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("moreImg", (item.getCover()));
					map.put("moreText",item.getName());
					moreDataList.add(map);
				}

				actorDataList = new ArrayList<Map<String,Object>>();
				for (CategoryBean.DataBean.AlistBean item : aitemDatas) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("cover", (item.getCover()));
					map.put("name",item.getName());
					map.put("likenum",item.getLikenum());
					map.put("subtitle",item.getSubtitle());
					actorDataList.add(map);
				}

				adapter=new SimpleAdapter(MainActivity.this, dataList, R.layout.gridviewitem, from, to);
				moreAdapter = new SimpleAdapter(MainActivity.this,moreDataList,R.layout.gridviewitemmore, moreFrom,to);
				moreAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
					@Override
					public boolean setViewValue(View view, Object data,
												String textRepresentation) {
						if (view instanceof ImageView) {
							String url = (String)data;
							ImageView iv = (ImageView) view;
							Picasso.with(MainActivity.this)
									.load(url)
									.into(iv);
							return true;
						} else
							return false;
					}
				});


				gridView.setAdapter(adapter);
				moreGrideView.setAdapter(moreAdapter);
				mListView.setAdapter(new MyBaseAdapter());
				//获得ListView的高度
				Utility.setListViewHeightBasedOnChildren(mListView);

				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position,
											long arg3) {
						String categoryId = (String) CatIdList[position];
						Intent i = new Intent(MainActivity.this,MovieListActivity.class);
						i.putExtra("catgoryId", categoryId);
						startActivity(i);
					}
				});
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



		BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
		bottomBar.selectTabAtPosition(0);
		bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_home) {
//                    // The tab with id R.id.tab_favorites was selected,
//                    // change your content accordingly.
//                    Intent i = new Intent(HomeActivity.this,MainActivity.class);
//                    startActivity(i);
//                } else
				if (tabId == R.id.tab_channel) {
					// The tab with id R.id.tab_favorites was selected,
					// change your content accordingly.
					Intent i;
					i = new Intent(MainActivity.this,ChannelActivity.class);
					startActivity(i);
				} else if (tabId == R.id.tab_find) {
					// The tab with id R.id.tab_favorites was selected,
					// change your content accordingly.
					Intent i = new Intent(MainActivity.this,FindActivity.class);
					startActivity(i);
				} else if (tabId == R.id.tab_user) {
					// The tab with id R.id.tab_favorites was selected,
					// change your content accordingly.
					Intent i = new Intent(MainActivity.this,UserActivity.class);
					startActivity(i);
				}
			}
		});
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	private List<String> imgListString() {
		List<String> imageData = new ArrayList<>();
		imageData.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=72b32dc4b719ebc4df787199b227cf79/58ee3d6d55fbb2fb48944ab34b4a20a44723dcd7.jpg");
		imageData.add("http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
		imageData.add("http://pic.4j4j.cn/upload/pic/20130815/5e604404fe.jpg");
		imageData.add("http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg");
		imageData.add("http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg");
		return imageData;
	}

	private  void getToken(){
		SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
		token = helper.getString("token");
	}

	private List<String> titleListString() {
		List<String> titleData = new ArrayList<>();
		titleData.add("");
		titleData.add("");
		titleData.add("");
		titleData.add("");
		titleData.add("");
		return titleData;
	}

	@Override
	public void onItemClick(View view, int postion) {
		System.out.println("点击了第"+postion+"行");
		Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT).show();
	}

	private  void initevent(){
		mSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, MainSearchActivity.class));
				MainActivity.this.finish();//关闭页面
			}
		});
		mEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mEdit.setFocusable(false);
				startActivity(new Intent(MainActivity.this, MainSearchActivity.class));
				MainActivity.this.finish();//关闭页面
			}
		});
		LinearLayout tomovielist = findViewById(R.id.tomovielist);
		tomovielist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, MovieListActivity.class));
				MainActivity.this.finish();//关闭页面
			}
		});

		LinearLayout toactorlist = findViewById(R.id.toactorlist);
		toactorlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, ActorListActivity.class));
				MainActivity.this.finish();//关闭页面
			}
		});
	}
	public String getAccount() {
		return mEdit.getText().toString().trim();//去掉空格
	}
	void initView(){
		gridView = findViewById(R.id.gridview);
		mSearch = findViewById(R.id.search);
		mDownload = findViewById(R.id.download);
		mHistory = findViewById(R.id.history);
		mEdit = findViewById(R.id.search_content);
		moreGrideView = findViewById(R.id.moregridview);
		mListView = findViewById(R.id.listview_actor);
	}
	void initData() {
		//图标
		int icno[] = { R.mipmap.jiemu_icon, R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.jiemu_icon,R.mipmap.all};
		//图标下的文字
		String name[]={"娱乐","网络电影","电视剧","综艺","魔幻","游戏","片花","全部"};
		CatIdList= new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
		dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i <icno.length; i++) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("img", icno[i]);
			map.put("text",name[i]);
			dataList.add(map);
		}
	}
	private void InitViewPager() {
		mList = new ArrayList<View>();
		int[] imageResId = new int[] { R.mipmap.cainixihuan2,R.mipmap.index_banner1,R.mipmap.cainixihuan2 ,R.mipmap.index_banner1};
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(MainActivity.this);
			imageView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {// 设置图片点击事件
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			mList.add(imageView);
		}
	}
	public  void freshBanner(){}
	private  void getBanner(){
		URL url;
		SharedPreferencesUtils helper = new SharedPreferencesUtils(MainActivity.this, "setting");
		String mToken = helper.getString("token");
		try {
			url = new URL("http://hgmovie.joysw.win:82/index.php/font/app/getBannerList");
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
					Toast.makeText(MainActivity.this, "token过期，请重新登录", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(MainActivity.this, "服务器未响应", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {

		}
	}
	private  void getMovieType(){
		URL url;
		SharedPreferencesUtils helper = new SharedPreferencesUtils(MainActivity.this, "setting");
		String mToken = helper.getString("token");
		try {
			url = new URL("http://hgmovie.joysw.win:82/index.php/font/app/getMovieCategoryList");
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
					Toast.makeText(MainActivity.this, "token过期，请重新登录", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(MainActivity.this, "服务器未响应", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {

		}
	}
	class MyBaseAdapter extends BaseAdapter {


		@Override
		public int getCount() {
			return actorDataList.size();
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
			ViewHold hold=null;
			if(convertView==null){
				convertView=View.inflate(MainActivity.this,R.layout.listview_item,null);
				if(hold==null){
					hold=new ViewHold();
					hold.number = convertView.findViewById(R.id.actor_number);
					hold.actor_pic = convertView.findViewById(R.id.actor_image);
					hold.actor_name = convertView.findViewById(R.id.actor_name);
					hold.actor_introduction = convertView.findViewById(R.id.actor_introduction);
				}
				convertView.setTag(hold);
			}else{
				hold = (ViewHold) convertView.getTag();
			}
            Map<String,Object> map = actorDataList.get(position);
			hold.number.setText((String)map.get("likenum") + "部电影");
			hold.actor_name.setText((String)map.get("name"));
			hold.actor_introduction.setText((String)map.get("subtitle"));
			String url = (String)map.get("cover");
			Picasso.with(MainActivity.this).load(url).into(hold.actor_pic);




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