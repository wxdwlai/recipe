package com.example.dell.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import httpClient.webGet;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button menu1;
    private Button menu2;
    private EditText account;
    private EditText password;
    private Button login;
    private Button sign;
    private TextView infotext;
    private static String info = null;
    private ProgressDialog dialog;

    private static String user;

    private static Handler handler = new Handler();

    public static String getInfo(){
        return user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //找到登陆界面每个按钮
        inti();

        menu1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,StoreActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(this);
        sign.setOnClickListener(this);

    }

    public void inti(){
       account = (EditText)findViewById(R.id.account);
       password = (EditText)findViewById(R.id.password);
       login = (Button)findViewById(R.id.login);
       sign = (Button)findViewById(R.id.sign);
       menu1 = (Button)findViewById(R.id.menu1);
       menu2 = (Button)findViewById(R.id.menu2);
       infotext = (TextView)findViewById(R.id.infotext);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login:
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //创建子线程，进行Get传输
                new Thread(new MyThread()).start();
                break;
                case R.id.sign:
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    public class MyThread extends Thread{
        @Override
        public void run() {
//            super.run();
            try {
                info = webGet.excuteHttpGet(account.getText().toString(), password.getText().toString());
//                System.out.print(info);
                if(info.contains("登陆成功")){
                    //获取当前登陆的用户名
                    user = account.getText().toString();
                    Intent intent = new Intent(Login.this,PersonalActivity.class);
                    intent.putExtra("account",account.getText().toString());
                    startActivity(intent);
                    account.clearComposingText();
                    password.clearComposingText();
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
                    infotext.setText(info);
                    dialog.dismiss();
                    Toast toast = Toast.makeText(Login.this, info, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }
}
