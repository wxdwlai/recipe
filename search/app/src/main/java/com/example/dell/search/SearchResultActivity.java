package com.example.dell.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.comment.CommentActivity;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import httpClient.MyZoomImageView;
import httpClient.webPic;
import httpClient.webShouCang;
import httpClient.webStore;
import picture.ImageControl;

/*
 *在搜索结果里面实现收藏菜谱的功能
 * 这里就需要提供三个主要的信息：
 * 1 当前用户
 *   通过useId来找到当前的用户，这个很容易实现，在本代码里面用userId标注当前用户信息
 *   （直接通过person的个人主页就可以找到用户的userId）
 *
 * 2 当前菜谱
 *   通过foodId来确定当前菜谱是什么，这个也比较容易实现，只要在服务器端返回的数据里面找到菜谱的Id即可
 *   在本代码里面用foodId标注当前的菜谱
 *   （在查找菜谱的做法以及菜谱的图片的同时查找出菜谱的foodId）
 *
 * 3 当前菜谱的收藏状态
 *   这个通过当前的userId和foodId来确定是否已经收藏，这里要访问网络资源
 *   因为用户信息存储到了相应的数据库里面，所以要通过网络传递当前的userId和foodId，然后在服务器的相应位置去查找是否有匹配的结果
 *   如果找到匹配项，那么设置当前的收藏状态为“已收藏”，否则的话设置为“未收藏”
 *
 *   收藏的主要难点在于第三点，前面两条很容易实现
 *   要编写相应的类用来访问网络资源
 */
public class SearchResultActivity extends AppCompatActivity {

    private EditText address;
    private ImageView pic;
//    private ImageControl pic;
    private TextView kinds;
    private TextView result;
//    private ListView result;
    private static String keyWord = "";
    private static String info;
    private static Handler handler = new Handler();
    private Drawable drawable = null;

    //4.16新添加
    private TextView title;
    private Button shoucang;
    private TextView st;
    private String userId;
    private String foodId;

    private Button com;

    //4.23日修改
    private TextView foodKinds,foodMake;
    private String ings;

    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init();
        new Thread(new MyThread()).start();



        //收藏按钮设置监听事件
        //并为状态的改变设置相应的操作
        shoucang.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                if(userId==null){
                    Intent intent = new Intent(SearchResultActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                }else{
//                    new Thread(){
//                        public void run(){
//                            String r1 = st.getText().toString();
//                            String r2 = "已收藏";
//                            if(r1==r2){
////                                st.setText("收藏");
//                                try {
//                                    String s = "收藏";
//                                    webShouCang.ChangeState(userId,foodId,s);//-------------------------*4
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                System.out.println("USERID:"+userId+"\tFOODID:"+foodId+"\tSTATUS:"+st.getText().toString());
//                            }else{
////                                st.setText("已收藏");
//                                try {
//                                    String s = "已收藏";
//                                    webShouCang.ChangeState(userId,foodId,s);//-------------------------*4
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                System.out.println("USERID:"+userId+"\tFOODID:"+foodId+"\tSTATUS:"+st.getText().toString());
//                            }
//                        }
//                    }.start();
                    String r1 = st.getText().toString();
                    String r2 = "已收藏";
                    if(r1==r2){
                        st.setText("收藏");
                        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            webShouCang.ChangeState(userId,foodId,st.getText().toString());//-------------------------*4
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("USERID:"+userId+"\tFOODID:"+foodId+"\tSTATUS:"+st.getText().toString());
                    }else{
                        st.setText(r2);
                        try {
                            webShouCang.ChangeState(userId,foodId,st.getText().toString());//-------------------------*4
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("USERID:"+userId+"\tFOODID:"+foodId+"\tSTATUS:"+st.getText().toString());
                    }
                }

            }
        });


        //为评论按钮设置点击事件
        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this,CommentActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化
    //初始化的时候完成了userId的初始化
    public void init(){
//        address = (EditText)findViewById(R.id.address);
        pic = (ImageView)findViewById(R.id.pic);
//        pic = (ImageControl)findViewById(R.id.pic);
        result = (TextView)findViewById(R.id.result);
//        result = (ListView)findViewById(R.id.result);
        kinds = (TextView)findViewById(R.id.kinds);
        Intent intent = getIntent();
        keyWord = intent.getStringExtra("keyWord");

        title = (TextView)findViewById(R.id.title);
        title.setText(keyWord.replaceAll("[\\s]*",""));
        System.out.println(keyWord.replaceAll("[\\s]",""));
        shoucang = (Button)findViewById(R.id.shoucang);
        st = (TextView)findViewById(R.id.st);

        //下面为了判断用户是否登陆成功
        //登陆成功的用户的用户名一定不为空
        userId = PersonalActivity.getId();//-------------------------------------------------*1

        com = (Button)findViewById(R.id.com);

        foodKinds = (TextView)findViewById(R.id.food_kinds);
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
//            super.run();

//            List<String> list = new ArrayList<String>();
//            list = webStore.excuteHttpGet(keyWord);
            info = webStore.excuteGetSteps(keyWord);
            final Bitmap bm = webPic.ShowPic(getPath(info));
            String str = "images/\\d{1,}.jpg";
            Pattern pattern = Pattern.compile(str);
            Matcher matcher = pattern.matcher(info);
            if(matcher.find()){
                //获取当前菜谱的图片资源
                str = matcher.group();
                info = info.replace(str,"");
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //找出菜谱的标签
                    String kindsString = getKinds(info);
                    info = info.replace(kindsString,"");
                    kindsString = kindsString.replace("*","");
                    String[] kStr = kindsString.split("[\\s]+");

                    //找出菜谱的Id
                    foodId = getFoodId(info); //----------------------------------------------------*2
                    info = info.replace(foodId,"");
                    foodId = foodId.replace("Id","");
                    System.out.println("info:"+info+"\tkinds:"+kindsString+"foodId:"+foodId+"\tuserId:"+userId);

                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    boolean bool = webShouCang.ShowResult(userId,foodId);//------------------------------------*3
                    System.out.println("是否已经收藏："+bool);

                    //找到菜谱对应的食材
                    //**********************未完成,格式很难看***********************
                    ings = getIngs(info);
                    info = info.replace(ings,"");
                    ings = ings.replace("--","");
                    if(bool==true){
                        st.setText("已收藏");
                    }

                    //过滤掉菜谱的标签和Id,剩下菜谱的制作步骤
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
                    for(String s:kStr)
                        kinds.append(s+"·");
//                    kinds.setText(kindsString);
                    foodKinds.append(ings);
//                    String[] ob = info.split("[\\s]*");
//                    ArrayAdapter adapter = new ArrayAdapter<String>(SearchResultActivity.this,android.R.layout.simple_list_item_1,ob);

//                    result.setAdapter(adapter);
                    result.append(info);
                    pic.setImageBitmap(bm);
                }
            });
        }
    }

    public String getPath(String str){
        String path = null;
        String pattern = "images/\\d{1,}+.jpg";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(str);
        if(matcher.find())
        path = matcher.group();
        return path;
    }

    public String getMetheod(String str){
        String result = null;
        String pattern = "=+re";
        return result;
    }

    public String getKinds(String str){
        String string = null;
        String pattern = "\\*{1,}+[\\s\\S]*[\\u4E00-\\u9FA5]*+\\*{1,}";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(str);
        if(matcher.find())
            string = matcher.group(0);
        return string;
    }

    public String getFoodId(String str){
        String string  = null;
        String pattern = "(Id){1}+[\\s\\S]*[\\u4E00-\\u9FA5]*+(Id)";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(str);
        if(matcher.find())
            string = matcher.group();
        return string;
    }

    public String getIngs(String str){
        String string  = null;
        String pattern = "-{2}+[\\s\\S]*";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(str);
        if(matcher.find())
            string  = matcher.group();
        return string;
    }

    public Bitmap getRoundRectBitmap(Bitmap bitmap, int radius) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        int bmWidth = bitmap.getWidth();
        int bmHeight = bitmap.getHeight();
        final RectF rectF = new RectF(0, 0, bmWidth, bmHeight);

        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(null);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rectF, paint);

        return bitmap;
    }
}
