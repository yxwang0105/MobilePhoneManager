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
    public AppItem(){

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
    public void setName(String name){
        this.name=name;
    }
    public void setTimes(int times){
        this.times=times;
    }
    public void setTime(String time){
        this.time=time;
    }
}
