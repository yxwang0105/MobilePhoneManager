package com.example.mobilephonemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
        Log.d("testComprehensiveFreq","successfully entering ComprehensiveFreqActivity");
        initAppInfo();
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.comprehensive_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(appItems==null)
            Log.d("testComprehensiveFreq","appitems have null");
        Log.d("testComprehensiveFreq",appItems.size()+"");
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
