package com.example.dell.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.searchview.ICallBack;
import com.example.searchview.SearchView;
import com.example.searchview.bCallBack;

public class HistorySearchActivity extends AppCompatActivity {

    private SearchView search_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search);

        init();
        search_view.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

            }
        });

        search_view.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }

    void init(){
        search_view = (SearchView)findViewById(R.id.search_view);
    }
}
