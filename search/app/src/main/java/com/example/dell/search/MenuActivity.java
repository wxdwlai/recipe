package com.example.dell.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button menu1;
    private Button menu2;
    private  Button menu3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memulayout);

        //找出“主页”并设置相应的点击事件
        menu1 = (Button)findViewById(R.id.menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        menu2 = (Button)findViewById(R.id.menu2);
        menu2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MenuActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });

        menu3 = (Button)findViewById(R.id.menu3);
        menu3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent intent  = new Intent(MenuActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
