package com.example.mobilephonemanager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FreqActivity extends AppCompatActivity {
    private List<AppItem> appItems=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq);
        initAppInfo();
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FreqAdapter freqAdapter=new FreqAdapter(appItems);
        recyclerView.setAdapter(freqAdapter);
    }
    public void initAppInfo(){

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
