package com.example.mobilephonemanager;

import androidx.annotation.Nullable;

import org.litepal.crud.LitePalSupport;

public class AppTotal extends LitePalSupport {
    private int id;
    private String name;
    private int times;
    public int getId(){
        return  id;
    }
    public String getName(){
        return name;
    }
    public int getTimes(){
        return times;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setTimes(int times){
        this.times=times;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof AppTotal)) return false;
        AppTotal appTotal=(AppTotal)obj;
        return this.name.equals(appTotal);
    }
}
