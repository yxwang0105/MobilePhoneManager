package com.example.mobilephonemanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ComprehensiveFreqActivity extends AppCompatActivity {
    private List<AppItem> appItems;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprehensive_freq);
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.comprehensive_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ComprehensiveFreqAdapter comprehensiveFreqAdapter=new ComprehensiveFreqAdapter(appItems,ComprehensiveFreqActivity.this);
        recyclerView.setAdapter(comprehensiveFreqAdapter);
    }
    public void initAppInfo(){
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        this.appItems=DataBaseUtils.getComprehensiveInfo(name);
        if(this.appItems==null||this.appItems.size()==0)
            this.appItems = new ArrayList<>();
    }
}
