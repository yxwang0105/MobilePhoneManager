package com.example.mobilephonemanager;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataBaseUtils {
    public static void addAppItems(String name){
        App app=new App();
        app.setName(name);
        String time=getCurrentTime();
        app.setTime(time);
        app.save();
        List<AppTotal> allApp = LitePal.findAll(AppTotal.class);
        AppTotal appTotal=null;
        for(int i=0;i<allApp.size();i++){
            if(allApp.get(i).getName().equals(name))
                appTotal=allApp.get(i);
        }
        if(appTotal==null){
            appTotal=new AppTotal();
            appTotal.setName(name);
            appTotal.setTimes(1);
            appTotal.save();
        }
        else{
            appTotal.setTimes(appTotal.getTimes()+1);
            appTotal.save();
        }
    }
    private static String getCurrentTime(){
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time=formatter.format(date);
        return time;
    }
}
