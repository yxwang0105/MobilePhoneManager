package com.example.mobilephonemanager;

import android.util.Log;

import java.util.List;

public class MemorandumAdapter {
    /**
     * 根据分析，想要得到保存内容一共就两种情况，要么就是中心词加补足语，要么就是只有中心词
     * 在分析时，先分析第一种情况，因为第一种约束性更强一些，没有补足语，就一定是中心词
     * @param data
     * @return
     */
    public static String getSaveContent(String data){
      String save1=getSaveContentFromModeOne(data);
      String save2=getSaveContentFromModeTwo(data);
      if(save1==null)
          return save2;
      else
          return save1;
    }

    /**
     * 分析中心词加补足语CMP的情况，但是这里也有一个问题就是在保存文本中若是有补足语就会有问题
     * 所以只有一种情况才会满足要求，就是补足语距离中心词不可以很远，且补足语取第一个
     * @param data
     * @return
     */
    public static String getSaveContentFromModeOne(String data){
        NLP nlp=new NLP();
        List<String> list=nlp.getDeprel(data,"HED");
        String hed=list.get(0);//一般来说第一个就是我们需要找的词
        if(hed==null)
            return null;
        list=nlp.getDeprel(data,"CMP");
        String cmp=list.get(0);
        if(cmp==null)
            return null;
        int hedPosition=data.indexOf(hed);
        int cmpPosition=data.indexOf(cmp,hedPosition);//补足语必须在它后面
        if(cmpPosition==-1||cmpPosition-hedPosition>3)
            return null;
        else {
            return data.substring(cmpPosition+1);
        }
    }

    /**
     * 这个就是Mode1的阉割版，直接取中心词之后的子串就可以
     * @param data
     * @return
     */
    public static String getSaveContentFromModeTwo(String data){
        NLP nlp=new NLP();
        List<String> list=nlp.getDeprel("data","HED");
        String hed=list.get(0);
        if(hed==null)
            return null;
        int hed_position=data.indexOf(hed);
        int size=hed.length();
        if(hed_position==-1)
            return null;
        else
            return data.substring(hed_position+size);
    }
}
