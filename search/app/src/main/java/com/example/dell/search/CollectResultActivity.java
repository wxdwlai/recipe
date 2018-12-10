package com.example.dell.search;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.RecipeAdapter2RycleView;
import httpClient.webLittlePic;
import httpClient.webShouCang;

public class CollectResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Button logout;
    private ListView colRes;
    private RecyclerView colRes0;
    private String userId;
    private TextView noRes;
    private String[] result;
    private List<Recipe> list;
    private Button backToPerson;
    private static Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_result);
        final Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        System.out.println(userId);
//        result = webShouCang.getResult(userId);
//        System.out.println(result);

        init();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        colRes0.setLayoutManager(layoutManager);
        new Thread(new MyThread()).start();
    }

    private void init(){
        logout = (Button)findViewById(R.id.logout);
        colRes = (ListView)findViewById(R.id.colRes);
//        colRes0 = (RecyclerView)findViewById(R.id.colRes0);
        noRes = (TextView)findViewById(R.id.noRes);
        backToPerson = (Button)findViewById(R.id.bactToResult);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class MyThread extends Thread{
        @Override
        public void run() {
//            super.run();
            //返回的是foodId的结果
            result = webShouCang.getResult(userId);
            System.out.println(result);
            list = new ArrayList<Recipe>();
            if(result!=null){
                for(String s : result){
                    list.add(webLittlePic.getCollect(s));
                }
                System.out.println("From CollectResultActivity :"+list.size());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        RecipeAdapter adapter = new RecipeAdapter(CollectResultActivity.this,R.layout.show_pic,list);
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                        colRes.setAdapter(adapter);
                        colRes.setOnItemClickListener(new CollectResultActivity.MyOnItemCilckeListener());

//                        RecipeAdapter2RycleView adapter = new RecipeAdapter2RycleView(list);
//                        colRes0.setAdapter(adapter);
//                        adapter.setOnItemClickListener(new RecipeAdapter2RycleView.OnItemClickListener() {
//                            @Override
//                            public void OnItemClick(View view, int position) {
//                                String str = list.get(position).getName();
//            foodId = list.get(position).getFoodId();
//            System.out.println("foodId+foodId*******:"+foodId+"\tuserId:"+userId);
//            tag = webShouCang.ShowResult(userId, foodId);
//                                if(str.length()>2){
//                                    Intent intent = new Intent(CollectResultActivity.this,SearchResultActivity.class);
//                                    intent.putExtra("keyWord",str);
//                intent.putExtra("result",tag);
//                                    startActivity(intent);
//                                }
//                            }
//                        });
                        noRes.setVisibility(View.GONE);
                    }
                });
            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(CollectResultActivity.this,"没有找到结果",Toast.LENGTH_LONG).show();
                    }
                });
            }
//            for(String s : result){
//               String info = webLittlePic.getCollect(s);
//               String[] re = info.split("\\*");
//                Recipe recipe = new Recipe(re[0],re[1],re[2]);
//                list.add(recipe);
//            }
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    RecipeAdapter adapter = new RecipeAdapter(CollectResultActivity.this,R.layout.show_pic,list);
//                    colRes.setAdapter(adapter);
//                }
//            });
        }
    }

    private class MyOnItemCilckeListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String str = list.get(position).getName();
            if(str.length()>2){
                Intent intent = new Intent(CollectResultActivity.this,SearchResultActivity.class);
                intent.putExtra("keyWord",str);
                startActivity(intent);
            }
        }
    }
}
