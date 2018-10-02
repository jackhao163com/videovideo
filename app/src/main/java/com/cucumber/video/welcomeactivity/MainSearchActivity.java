package com.cucumber.video.welcomeactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class MainSearchActivity extends Activity {
private String mNames[] = {
 " 励志 "," 暧昧 "," 奇幻 ",
    " 年轻 "," 江湖 "," 奇思妙想 ",
        " 奇思妙想 "," 江湖 "," 年轻 ",
        " 奇幻 "," 暧昧 "," 励志 "
       };


 private XCFlowLayout mFlowLayout;
        private ListView lv;
        private Mytitle mtl;
         private Button bt;
         private EditText mEdit;
         private LinearLayout mLinear;
         private List<String> list=new ArrayList<String>();
         @Override
 protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
             getWindow().setFormat(PixelFormat.RGBA_8888);  //或者PixelFormat.TRANSLUCENT
             getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
             requestWindowFeature(Window.FEATURE_NO_TITLE);
             getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
         setContentView(R.layout.search_main);
         mFlowLayout = (XCFlowLayout) findViewById(R.id.flowlayout);
         lv = (ListView) findViewById(R.id.lv);
       //  mtl = (Mytitle) findViewById(R.id.mtl);
         bt = (Button) findViewById(R.id.bt);
         mEdit = findViewById(R.id.search_content);
         mLinear = findViewById(R.id.two);
         mLinear.setOnTouchListener(new View.OnTouchListener() {

             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 mLinear.setFocusable(true);
                 mLinear.setFocusableInTouchMode(true);
                 mLinear.requestFocus();
                 InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                 imm.hideSoftInputFromWindow(mEdit.getWindowToken(), 0);
                 return false;
             }
         });

         mEdit.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view) {
                 Log.e("chumingchao","onclick");
                 EditText textView = (EditText) view;
                 textView.setFocusable(true);
                 textView.setFocusableInTouchMode(true);
                 textView.requestFocus();
             }
         });
         mEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View view, boolean hasFocus) {
                 EditText textView = (EditText) view;
                 String hint;
                 Log.e("chumingchao","hasfoucs:"+hasFocus);
                 if (hasFocus) {
                     hint = textView.getHint().toString();
                     textView.setTag(hint);
                     textView.setCursorVisible(true);
                     textView.setHint("");
                 } else {
                     textView.setHint("输入关键词查找片源");
                 }
             }});
         findViewById(R.id.search_cancle).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainSearchActivity.this, MainActivity.class));
                 finish();//关闭页面
             }
         });



         bt.setOnClickListener(new View.OnClickListener() {
         @Override
 public void onClick(View v) {
            list.clear();
            lv.setAdapter(new myBaseAdapter());
         }
         });
         initChildViews();


         jilu();

         }

         public void initChildViews() {
         // TODO Auto-generated method stub


       ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       lp.leftMargin = 30;
        lp.rightMargin = 5;
         lp.topMargin = 10;
         lp.bottomMargin = 5;
         for( int i = 0; i < mNames.length; i ++){
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.parseColor("#CC9966"));
            //view.setPadding(10,4,10,4);
             view.setGravity(Gravity.CENTER);
            view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.type_bg));
            mFlowLayout.addView(view,lp);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
        @Override
public void onClick(View v) {
                list.add(mNames[finalI]);
                lv.setAdapter(new myBaseAdapter());
        }
        });


        }




        }
        public void jilu(){


   /*    mtl.setJiekou(new Mytitle.onsetHuida() {
       @Override
 public void huida(String aa) {
            Toast.makeText(MainSearchActivity.this, aa+"", Toast.LENGTH_SHORT).show();
            list.add(aa);
            lv.setAdapter(new myBaseAdapter());
         }


        });*/
        lv.setAdapter(new myBaseAdapter());




        }




       class myBaseAdapter extends BaseAdapter{


         @Override
 public int getCount() {
         return list.size();
         }


         @Override
 public Object getItem(int position) {
  return null;
  }


@Override
public long getItemId(int position) {
            return 0;
        }


         @Override
 public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold=null;
     if(convertView==null){
      if(hold==null){
        hold=new ViewHold();
        convertView=View.inflate(MainSearchActivity.this,R.layout.listviewitem_search,null);
        hold.tv = convertView.findViewById(R.id.tvv1);
    }
    convertView.setTag(hold);
   }else{
         hold = (ViewHold) convertView.getTag();
 }


   hold.tv.setText(list.get(position));




 return convertView;
  }
 }
  class ViewHold{
TextView tv;
}


}