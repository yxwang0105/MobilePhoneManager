package com.example.mobilephonemanager;

import java.util.LinkedList;
import java.util.List;

/**
 * 主要用于判断该使用哪一个应用
 */
public class Judge {
    private static List<String> param=new LinkedList<>();
    public static final String Memorandum="00",Weather="01";
    static{
        param.add("备忘录应用");
        param.add("天气应用");
    }
    public static String judge(String data){
        String judgement="";
        for(int i=0;i<param.size();i++){
            if(data.contains(param.get(i))) {
                judgement += "0";
                judgement+=i;
            }
        }
        if(judgement.equals(""))
            judgement+="1";
        return judgement;

    }
}
