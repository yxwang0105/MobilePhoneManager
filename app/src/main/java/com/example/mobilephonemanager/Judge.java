package com.example.mobilephonemanager;

import java.util.LinkedList;
import java.util.List;

/**
 * 主要用于判断该使用哪一个应用
 */
public class Judge {
    private static List<String> param=new LinkedList<>();
    public static final String Memorandum="00",Weather="011",QQ="10",WeChat="11",ELE="100",DIAL="101",SMS="110";
    static{
        param.add("备忘录应用");
        param.add("天气");
    }
    public static String judge(String data){
        String judgement="";
        for(int i=0;i<param.size();i++){
            if(data.contains(param.get(i))) {
                judgement += "0";
                judgement+=i;
            }
        }
        judgement+="1";
        if(data.contains("QQ")&&data.contains("发送"))
            judgement+="0";
        if(data.contains("微信")&&data.contains("发送"))
            judgement+="1";
        if(data.contains("外卖")&&!data.contains("备忘录")){
            judgement+="00";
            if(data.contains("点一份外卖"))
                MainActivity.ELE_RANDOM=true;
        }
        if(data.contains("电话")){
            judgement+="01";
        }
        if(data.contains("短信")){
            judgement+="10";
        }
        return judgement;

    }
}
