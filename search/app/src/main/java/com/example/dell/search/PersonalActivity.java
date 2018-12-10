package com.example.dell.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PersonalActivity extends AppCompatActivity {

    private Button menu1;
    private Button menu2;
    private static TextView userId;
    private static String Id;
//    private Button collect;
    private TextView collect;

    private Button set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        //找到登陆界面每个按钮
        inti();
//        Id = userId.getText().toString();

        menu1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menu2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this,StoreActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        Intent intent = getIntent();
        String user = intent.getStringExtra("account");
        userId.setText(user);
        Id = user;

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this,CollectResultActivity.class);
                intent.putExtra("userId",Id);
                startActivity(intent);
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this,PersonalSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void inti(){
        menu1 = (Button)findViewById(R.id.menu1);
        menu2 = (Button)findViewById(R.id.menu2);
        userId = (TextView)findViewById(R.id.userId);
//        collect = (Button)findViewById(R.id.collect);
        collect = (TextView) findViewById(R.id.collect);
        set = (Button)findViewById(R.id.set);
    }

    public static String getId()
    {
        return Id;
    }
}
