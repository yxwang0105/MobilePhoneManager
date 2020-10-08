package com.example.mobilephonemanager;

public class AppItem {
    private String name;
    private int times;
    private String time;
    public AppItem(String name,int times,String time){
        this.name=name;
        this.time=time;
        this.times=times;
    }
    public String getName(){
        return name;
    }
    public int getTimes(){
        return times;
    }
    public String getTime(){
        return time;
    }
}
