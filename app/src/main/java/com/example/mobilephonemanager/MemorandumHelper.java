package com.example.mobilephonemanager;

import org.json.JSONObject;

import java.util.List;

/**
 * 工作流程
 * 首先使用自然语言分析提取出其中的中心词
 * 其次使用自然语言分析将动词与“记录”，“查询”和“删除”这三个动词进行比较
 * 最后根据比对结果，如果相似度大于百分之50就使用其中的某个功能,目前已经做到了这个功能
 * 这其中都有一个前提，这个软件需要一个总体的判断，判断出一定是使用备忘录这一个功能
 * 目前希望做出一个总体的判断器，比如，当我们说“用备忘录记一下笔记本放在了抽屉里”，就会使用备忘录。但是这样的话当我们说“记一下笔记本放在了抽屉里”就没有办法了
 *
 */
public class MemorandumHelper {
    //三个最基本的指令
    private static final String SAVE="记录";
    private static final String QUERY="查询";
    private static final String DELETE="删除";
    public void process(final String saying){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(saying==null||"".equals(saying))
                    return;
                NLP nlp=new NLP();
                JSONObject jsonObject=nlp.getJSONObject(saying);
                List<String> list=nlp.getDeprel(saying,"HED");
                for(int i=0;i<list.size();i++){
                    if(Double.parseDouble(nlp.sameScore(SAVE,list.get(i)))>0.5){

                    }else if(Double.parseDouble(nlp.sameScore(QUERY,list.get(i)))>0.5){

                    }else if(Double.parseDouble(nlp.sameScore(DELETE,list.get(i)))>0.5){

                    }

                }
            }
        }).start();

    }

}
