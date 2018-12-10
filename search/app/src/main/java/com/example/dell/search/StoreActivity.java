package com.example.dell.search;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;

public class StoreActivity extends AppCompatActivity {

    private Button menu1;
    private Button menu2;
    private Button menu3;
    private Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        //找出“主页”并设置相应的点击事件
        menu1 = (Button)findViewById(R.id.menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        menu3 = (Button)findViewById(R.id.menu3);
        menu3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str = Login.getInfo();
                if(str==null){
                    Intent intent = new Intent(StoreActivity.this,Login.class);
                    startActivity(intent);
                    finish();//2.15 java课修改
                }
                else{
                    Intent intent = new Intent(StoreActivity.this,PersonalActivity.class);
                    intent.putExtra("account",str);
                    startActivity(intent);
                    finish();//2.15 java课修改
                }
            }
        });

        jump = (Button)findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://www.taobao.com/"));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
    }
}
