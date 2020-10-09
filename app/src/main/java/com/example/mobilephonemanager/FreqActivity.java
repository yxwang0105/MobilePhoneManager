package com.example.mobilephonemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FreqActivity extends AppCompatActivity {
    private List<AppItem> appItems;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("testFreq","before create FreqActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq);
        Log.d("testFreq","before recycler");
        initAppInfo();
        if(appItems.size()==0) {
            Log.d("testFreq","appItem's value equals zero");
            return;
        }
        Log.d("testFreq","appItem's value not equals zero");
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FreqAdapter freqAdapter=new FreqAdapter(appItems, FreqActivity.this, new OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                String name=appItems.get(pos).getName();
                if(name==null||name.equals(""))
                    return;
                List<AppItem> appItems=DataBaseUtils.getComprehensiveInfo(name);
                if(appItems==null||appItems.size()==0)
                    return;
                Toast.makeText(FreqActivity.this, "正在为您显示"+name+"的最近使用时间", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FreqActivity.this,ComprehensiveFreqActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(freqAdapter);
    }
    public void initAppInfo(){
        this.appItems=DataBaseUtils.getTotalInfo();
        if(this.appItems==null||this.appItems.size()==0)
            this.appItems = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
