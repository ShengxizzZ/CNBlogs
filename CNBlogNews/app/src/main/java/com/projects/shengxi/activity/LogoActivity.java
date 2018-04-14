package com.projects.shengxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.projects.shengxi.cnblognews.R;

/**
 * 中国
 * Created by ShengXi on 2016/6/23.
 */
public class LogoActivity extends AppCompatActivity{



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1){
                Intent in = new Intent(LogoActivity.this,MainActivity.class);
                LogoActivity.this.startActivity(in);

                overridePendingTransition(R.anim.slide_left,R.anim.slide_left);
                finish();
            }
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_logo);
        handler.sendEmptyMessageDelayed(1,3000);

    }

    long first = 0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long second  =first;
        first = System.currentTimeMillis();
        if(first - second <2000){
            finish();
        }else{
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
        }
    }
}
