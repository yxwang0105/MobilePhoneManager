package com.example.mobilephonemanager;

import android.util.Log;
import org.litepal.LitePal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 格式为搜索以。。。为关键词的外卖
 */
public class EleHelper {
    public static String getKey(String saying){
        int loc1=saying.indexOf("以");
        int loc2=saying.indexOf("为关键词的外卖");
        String key=null;
        if(loc1!=-1&&loc2!=-1) {
            key = saying.substring(loc1+1, loc2);
            Log.d("asasas",key);
        }
        return key;
    }
    private static String analyse(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        String time = formatter.format(date);
        String hour_s=time.split("/")[3];
        int currentHour=Integer.parseInt(hour_s);
        int currentSection=getSection(currentHour);
        List<TakeOutFood> storeList= LitePal.findAll(TakeOutFood.class);
        List<TakeOutFood> temp=new ArrayList<>();
        for(int i=0;i<storeList.size();i++){
            if(getSection(storeList.get(i).getHour())==currentSection)
                temp.add(storeList.get(i));
        }
        storeList=temp;
        if(storeList.size()==0)
            return null;
        else{
            return getMostFre(storeList);
        }
    }
    private static int getSection(int hour){
        if(hour>=0&&hour<=6)
            return 0;//凌晨
        if(hour>=6&&hour<=8)
            return 1;//清晨
        if(hour>=8&&hour<=12)
            return 2;//上午
        if(hour>=12&&hour<=14)
            return 3;//中午
        if(hour>=14&&hour<=18)
            return 4;//下午
        if(hour>=18&&hour<=21)
            return 5;//晚上
        if(hour>=21&&hour<=24)
            return 6;//深夜
        return -1;
    }
    private static String getMostFre(List<TakeOutFood> storeList){
        Map<String,Integer> storeMap=new HashMap<>();
        for(int i=0;i<storeList.size();i++){
            if(storeMap.containsKey(storeList.get(i).getStore()))
                storeMap.put(storeList.get(i).getStore(),storeMap.get(storeList.get(i))+1);
            else
                storeMap.put(storeList.get(i).getStore(),1);
        }
        Set<String> storeSet=storeMap.keySet();
        if(storeSet.size()==0)
            return null;
        else{
            String[] storeArray=(String[]) storeSet.toArray();
            int max=0;
            for(int i=0;i<storeArray.length-1;i++){
                if(storeMap.get(storeArray[i+1])>=storeMap.get(storeArray[i]))
                 max=i+1;
            }
            String target=storeArray[max];
            return target;

        }
    }
}
