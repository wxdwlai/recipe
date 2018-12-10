package com.example.dell.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import httpClient.webSign;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView account;
    private TextView password;
    private TextView password1;
    private Button sign;
    private Button back;
    private TextView infotext;
    private String info;
    private ProgressDialog dialog;
    private static Handler handler = new Handler();

    private int ResultCode = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inti();
        sign.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void inti(){
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        password1 = (EditText)findViewById(R.id.password1);
        sign = (Button)findViewById(R.id.sign);
        back = (Button)findViewById(R.id.back);
        infotext = (TextView)findViewById(R.id.infotext);

    }

    public boolean IsSame(String s1,String s2){
        return s1.equals(s2);
    }

    public void onClick(View v) {
        if(false==IsSame(password.getText().toString(),password1.getText().toString())){
            Toast toast = Toast.makeText(Register.this, "两次密码不一致", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{
            dialog = new ProgressDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage("正在注册，请稍后...");
            dialog.setCancelable(false);
            dialog.show();
            new Thread(new MyThread()).start();
        }
//        dialog = new ProgressDialog(this);
//        dialog.setTitle("提示");
//        dialog.setMessage("正在注册，请稍后...");
//        dialog.setCancelable(false);
//        dialog.show();
//        new Thread(new MyThread()).start();
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
//            super.run();
            try {
                info = webSign.excuteHttpGet(account.getText().toString(), password.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
                    infotext.setText(info);
                    dialog.dismiss();
                    Toast toast = Toast.makeText(Register.this, info, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }
}
