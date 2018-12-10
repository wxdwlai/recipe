package com.example.dell.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.search.PersonalActivity;
import com.example.dell.search.R;
import com.example.dell.search.SearchResultActivity;
import com.example.dell.use.Comment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.zip.Inflater;

import adapter.CommentAdapter;


public class CommentActivity extends AppCompatActivity {

    private ListView rolComment;
    private EditText text;
    private Button send;
    private String userId = "001";
    private Calendar calendar;
    private String time;
    private Button backToResult;
    private TextView numOfComment;
    private List<Comment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();//初始化所有的控件
        final CommentAdapter adapter = new CommentAdapter(CommentActivity.this,R.layout.comment,list);
        rolComment.setAdapter(adapter);
        rolComment.setOnItemClickListener(new MyOnItemClickListener());

        //发送评论按钮的监听事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com = text.getText().toString();
                if(com.equals("")){
                    Toast.makeText(getApplicationContext(),"评论不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    Comment comment = new Comment(userId,com,time);
                    list.add(comment);
                    adapter.addComment(comment);
                    adapter.notifyDataSetChanged();
                    text.setText("");
                }
            }
        });

        //返回按钮的监听事件
        backToResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, SearchResultActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    void init()
    {
        rolComment = (ListView) findViewById(R.id.rolComment);
        text = (EditText)findViewById(R.id.text);
        send = (Button)findViewById(R.id.send);
        calendar = Calendar.getInstance();
        int y,m,d;
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH)+1;
        d = calendar.get(Calendar.DAY_OF_MONTH);
        time = y+"-"+m+"-"+d;
        userId = PersonalActivity.getId();

        backToResult = (Button)findViewById(R.id.bactToResult);
        numOfComment = (TextView)findViewById(R.id.numOfComment);
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    }
}
