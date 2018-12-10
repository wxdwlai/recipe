package com.example.dell.search;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.view.Window;
import android.view.LayoutInflater;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private EditText editText;
    private Button button1;
    private Button menu1;
    private Button menu2;
    private Button menu3;
    private List<View> listViews = null;
    private int[] imgs = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5 };
    private int index = 0;
    private MainPageAdapter adapter;

    private ImageButton recommend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        viewPager =  findViewById(R.id.viewpager);

        listViews = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.item1, null);
            ImageView iv = (ImageView) view.findViewById(R.id.view_image);
            iv.setBackgroundResource(imgs[i]);
            listViews.add(view);
        }

        adapter = new MainPageAdapter(listViews);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new PageChangeListener());

        //以上为viewpager部分
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.search);

        //为搜索框设置点击监听事件，如果点击了则跳转到搜索界面
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                finish();//2.15
            }
        });

        //获取“商城”按钮并且设置点击事件
        menu2 = (Button)findViewById(R.id.menu2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StoreActivity.class);
                startActivity(intent);
                finish();//2.15 java课修改
            }
        });

        //获取“我的”按钮并设置相应的点击事件
        menu3 = (Button)findViewById(R.id.menu3);
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = Login.getInfo();
                if(str==null){
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                    finish();//2.15
                }
                else{
                    Intent intent = new Intent(MainActivity.this,PersonalActivity.class);
                    intent.putExtra("account",str);
                    startActivity(intent);
                    finish();//2.15
                }
            }
        });

        /*推荐系统*/
        /*这里的推荐直接借用了收藏模块的功能，因为目前我们的推荐系统和收藏功能十分的相似*/
        /*现在还没测试，不知道是否正确？？？------5.21日晚*/
        recommend = (ImageButton)findViewById(R.id.recommend);
        recommend.setOnClickListener(new View.OnClickListerner(){
            @Override
            public void onClick(View v) {
                String str = Login.getInfo();
                if(str!=null){
                    Intent intent = new Intent(MainActivity.this,CollectResultActivity.class);
                    startActivity(intent);
                    finish();//5.21
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.zhuye:
                Toast.makeText(MainActivity.this,"主页",Toast.LENGTH_SHORT).show();
                break;
            case R.id.shequ:
                Toast.makeText(MainActivity.this,"社区",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    private class PageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            Toast.makeText(getApplicationContext(), arg0 + "", 0).show();
            index = arg0;
        }

    }


}

