package com.example.mobilephonemanager;

import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * 目前存在一个问题就是怎么才能实现尽可能少的使用getDatabase这个方法
 */
public class Memorandum {
    public static void getDatabase(){
        LitePal.getDatabase();
    }
    public static void add(String buildTime,String content){
        if(buildTime==null||content==null)
            return;
        MemorandumData data=new MemorandumData();
        data.setBuildTime(buildTime);
        data.setContent(content);
        data.save();
    }
    public static void delete(long id){
        LitePal.delete(MemorandumData.class,id);
    }
    public static void deleteAll(String[] conditions){
        LitePal.deleteAll(MemorandumData.class,conditions);
    }
    public static List<MemorandumData> findAll(){
        List<MemorandumData> datas=LitePal.findAll(MemorandumData.class);
        return datas;
    }
    public static MemorandumData findById(long id){
        MemorandumData data=LitePal.find(MemorandumData.class,id);
        return data;
    }

}
