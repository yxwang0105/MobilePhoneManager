package com.example.mobilephonemanager;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public static List<AppItem> getTotalInfo(){
        List<AppItem> list=new ArrayList<>();
        List<AppTotal> allTotalApps = LitePal.findAll(AppTotal.class);
        if(allTotalApps.size()==0)
            return null;
        for(int i=0;i<allTotalApps.size();i++){
            AppItem appItem=new AppItem();
            appItem.setName(allTotalApps.get(i).getName());
            appItem.setTimes(allTotalApps.get(i).getTimes());
            list.add(appItem);
        }
        return list;
    }
    public static List<AppItem> getComprehensiveInfo(String name){
        List<AppItem> list=new ArrayList<>();
        List<App> apps=LitePal.findAll(App.class);
        if(apps.size()==0)
            return null;
        for(int i=0;i<apps.size();i++){
            if(apps.get(i).getName().equals(name)) {
                AppItem appItem = new AppItem();
                appItem.setName(apps.get(i).getName());
                appItem.setTime(apps.get(i).getTime());
                list.add(appItem);
            }
        }
        return list;
    }
}
