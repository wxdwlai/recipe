package com.example.dell.search;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import httpClient.webLittlePic;
import httpClient.webShouCang;
import httpClient.webStore;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button back;
    private Button sousuo;
    private EditText search;
    private ListView listView;
    private TextView result;
    //设置ListView的测试值

    private String info;
//    private List<String> list = new ArrayList<String>();
//    private String[] data = new String [10];
    private List<Recipe> list;
//    private Recipe[] data = new Recipe[10];


//    private static String userId;
    private static String foodId;
    private static boolean tag = false;//标志是否被收藏

    private ProgressBar pbar;
    private TextView ptx;

    private TextView noList;
    private static Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置标题栏不可见
        setContentView(R.layout.search_activity);

        init();
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
//        Toast.makeText(SearchActivity.this,data,Toast.LENGTH_SHORT).show();
        back = (Button)findViewById(R.id.back);//back键未完成设置监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        search = (EditText)findViewById(R.id.search);
//        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_SEARCH){
//                    ((InputMethodManager)search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS
//                    );//隐藏软键盘，当有输入的时候才打开键盘，否则键盘处于关闭状态
//                    search();
//                }
//                return false;
//            }
//        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().trim().isEmpty()){
                    search();
                }
            }
        });



        //为搜索键设置监听事件,返回APP主页
        sousuo = (Button)findViewById(R.id.sousuo);
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString()!=null){
                    pbar.setVisibility(View.VISIBLE);
                    ptx.setText("努力加载中...");
                }

                search();
            }
        });

        listView = (ListView)findViewById(R.id.listViewS);
        listView.setOnItemClickListener(new MyOnItemCilckeListener());
    }

    private void init(){
        pbar = (ProgressBar)findViewById(R.id.pbar);
        ptx = (TextView)findViewById(R.id.ptx);
//        noList = (TextView)findViewById(R.id.noList);
    }

    public void onBackPressed(){
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //实现搜索方法
    private void search() {
        String keyWord = search.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            Toast.makeText(getApplication(), "输入为空，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new MyThread()).start();
        }
//        new Thread(new MyThread()).start();
//        Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
//        intent.putExtra("keyWord",keyWord);
//        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
                Toast.LENGTH_SHORT).show();
    }


    public class MyThread extends Thread{
        @Override
        public void run() {
            if(search.getText().toString()!=null){
                list = new ArrayList<>(webLittlePic.excuteHttpGet(search.getText().toString()));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,data);
                        pbar.setVisibility(View.GONE);
                        ptx.setText("");
                        ptx.setVisibility(View.GONE);
                        RecipeAdapter adapter = new RecipeAdapter(SearchActivity.this,R.layout.show_pic,list);
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                        listView.setAdapter(adapter);
                    }
                });
            }
//            data = webStore.excuteHttpGet(search.getText().toString());
//
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,data);
//                    listView.setAdapter(adapter);
//                    data = null;
//                }
//            });
        }
    }

    public String getTitle(String str){
        String title = null;
        String ruler = "([\\s\\S]*)+t";
        Pattern pattern = Pattern.compile(ruler);
        Matcher matcher = pattern.matcher(str);
        if(matcher.find())
            title = matcher.group();
        return title;
    }

//    private class MyOnItemCilckeListener implements AdapterView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            String str = (String) parent.getItemAtPosition(position);
//            if(str.length()>2){
//                System.out.println(str.length());
//                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
//                intent.putExtra("keyWord",str);
//                startActivity(intent);
//            }
//        }
//    }



    private class MyOnItemCilckeListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String str = list.get(position).getName();
//            foodId = list.get(position).getFoodId();
//            System.out.println("foodId+foodId*******:"+foodId+"\tuserId:"+userId);
//            tag = webShouCang.ShowResult(userId, foodId);
            if(str.length()>2){
                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra("keyWord",str);
//                intent.putExtra("result",tag);
                startActivity(intent);
            }
        }
    }

}
