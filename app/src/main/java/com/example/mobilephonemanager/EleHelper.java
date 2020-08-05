package com.example.mobilephonemanager;

/**
 * 格式为搜索以。。。为关键词的外卖
 */
public class EleHelper {
    public static String getKey(String saying){
        int loc1=saying.indexOf("以");
        int loc2=saying.indexOf("为关键词的外卖");
        String key=null;
        if(loc1!=-1&&loc2!=-1)
            key=saying.substring(loc1,loc2);
        return key;
    }
}
