package com.example.mobilephonemanager;
import org.litepal.crud.LitePalSupport;

public class App extends LitePalSupport {
    private int id;
    private String name;
    private String time;
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getTime(){
        return time;
    }
    public void setId(int id){this.id=id;}
    public void setName(String name){this.name=name;}
    public void setTime(String time){this.time=time;}
}
