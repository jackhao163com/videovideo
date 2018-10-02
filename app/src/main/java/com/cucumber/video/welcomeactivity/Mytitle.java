package com.cucumber.video.welcomeactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Mytitle extends RelativeLayout {
    private onsetHuida onsethuida;
    private EditText et;
    private ImageView img;
    private TextView tvv;






public Mytitle(Context context) {
 this(context, null);
 }


 public Mytitle(Context context, AttributeSet attrs) {
 this(context, attrs, 0);
 }


 public Mytitle( Context context, AttributeSet attrs, int defStyleAttr) {
 super(context, attrs, defStyleAttr);
 LayoutInflater from = LayoutInflater.from(context);
 View inflate = from.inflate(R.layout.title_main, this, true);


 img = (ImageView)inflate.findViewById(R.id.img);
tvv = inflate.findViewById(R.id.tvv);




 et = (EditText)inflate.findViewById(R.id.et);




tvv.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View v) {
onsethuida.huida(et.getText().toString());
}
});






 }




 public interface onsetHuida{
 void huida(String aa);


 }


public void setJiekou(onsetHuida onsethuida){
 this.onsethuida=onsethuida;
 }
}

