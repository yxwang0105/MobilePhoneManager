package com.example.mobilephonemanager;

import android.util.Log;

/**
 * 标准格式
 * 给。。。打电话
 * 给。。。发短信，内容为。。。
 */
public class PhoneHelper {
    public static String getPeopleTele(String data){
        if(data==null||data.equals("")) {
            return null;
        }
        int index1=data.indexOf("给")+1;
        int index2=data.indexOf("打电话");
        return data.substring(index1,index2);
    }
    public static String getPeopleSMS(String data){
        if(data==null||data.equals(""))
            return null;
        int index1=data.indexOf("给")+1;
        int index2=data.indexOf("发短信");
        return data.substring(index1,index2);
    }
    public static String getContent(String data){
        if(data==null||data.equals(""))
            return null;
        int index=data.indexOf("内容为")+3;
        return data.substring(index);
    }
}
