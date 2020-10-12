package com.example.mobilephonemanager;

import android.util.Log;

import java.util.List;

/**
 *用备忘录记录。。。
 *
 */
public class MemorandumAdapter {
    /**
     * 根据分析，想要得到保存内容一共就两种情况，要么就是中心词加补足语，要么就是只有中心词
     * 在分析时，先分析第一种情况，因为第一种约束性更强一些，没有补足语，就一定是中心词
     * @param data
     * @return
     */
    public static String getSaveContent(String data){
        int index=data.indexOf("记录")+2;
        Log.d("testMem",index+"");
        return data.substring(index);
    }

    public static String getQueryDataModeOne(String data){
        int loc1=data.indexOf("有关于")+3;
        int loc2=data.indexOf("的内容");
        Log.d("testMem",loc1+" "+loc2);
        if(loc1==2||loc2==-1)
            return null;
        String data1=data.substring(loc1,loc2);
        return data1;
    }
    public static String getQueryDataModeTwo(String data){
        int loc1=data.indexOf("日期为")+3;
        int loc2=data.indexOf("的内容");
        if(loc1==-1||loc2==-1)
            return null;
        String data2=data.substring(loc1,loc2);
        return data2;
    }
    public static int getDeleteMode(String data){
        if(data.contains("删除所有内容"))
            return 0;
        if(data.contains("日期为")&&data.contains("关于"))
            return 1;
        if(data.contains("日期为"))
            return 2;
        if(data.contains("关于"))
            return 3;
        return -1;
    }
    public static String[] getDeleteModeOne(String data){
        String[] result=new String[2];
        int loc1=data.indexOf("日期为");
        int loc2=data.indexOf("有关于");
        result[0]=data.substring(loc1,loc2);
        result[1]=data.substring(loc2);
        return result;
    }
    public static String getDeleteModeTwo(String data){
        int loc=data.indexOf("日期为");
        String result=data.substring(loc);
        return result;
    }
    public static String getDeleteModeThree(String data){
        int loc=data.indexOf("有关于");
        String result=data.substring(loc);
        return result;
    }
}
