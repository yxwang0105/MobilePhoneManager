package com.example.mobilephonemanager;

import android.util.Log;
import org.json.JSONObject;
import org.litepal.LitePal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 工作流程
 * 首先使用自然语言分析提取出其中的中心词
 * 其次使用自然语言分析将动词与“记录”，“查询”和“删除”这三个动词进行比较
 * 最后根据比对结果，如果相似度大于百分之50就使用其中的某个功能,目前已经做到了这个功能
 * 但是这其中有一个问题就是我们需要启动底层的服务需要判断哪一句才是我们需要存入数据库中的东西
 *有两种做法，第一种就是规定问句格式；第二种就是分析这句话提取出关键句。
 * 首先考虑第二种，就是分析这句话并提取，我们知道这一句话中无论是日常用语还是标准书面语，实际上都包含两个关键字或者与关键字意思相近的词语，“备忘录”，“记录”
 * 且语序基本上大部分人都肯定是“用备忘录记录某某事情”，而不会是“某某事情记录一下用备忘录”，这句话一般而言人与人交流有时都不会听懂，所以我们通常考虑普遍情况，即选择第一种
 * 那么我们分析标准的语序，“用备忘录记录一下某某事情”，首先备忘录可以直接忽略，因为在上一层肯定已经识别过这句话了，“备忘录”失去了意义，然后就是关键词“记录”
 * “记录”或者说“记”在自然语言分析处理时是一个关键词，所以可以很轻松的定位到当前位置，但是之后就会有一个问题，就是我们在日常用语中，在后面会加一些补足语，或者说副词。
 * 目前倾向于在自然语言分析的基础上分析出这些都是什么词，然后直接跳过。进而提取出这后面的所有话
 * 所以接下来的流程应该是
 * 1、新建一个叫MemorandomAdapter类，内含各种判断机制
 * 2、将这句话放入其中得到需要操作的语句
 *
 *
 *
 *
 *
 *
 *
 * 接下来就是查询问题
 * 我们首先知道我们在这里实际上已经进入了备忘录的功能，那么在这里备忘录这个词已经不重要了，接下来对我们重要的“查询”这个词实际上也是已经出现了的，那么这个对我们也不重要了。
 * 我们具体分析其中的内容，我们需要支持几种问法，不可能做出所有的问法，这是首先我们需要知道的问题。
 * 1、用备忘录查询有关于“钥匙”的内容（关键字查找）。
 * 2、用备忘录查询日期为“7月15日”的内容（时间）。
 *
 *
 *
 *
 * 接下来就是删除问题
 * 我们直接规定几种格式来进行删除
 * 1、备忘录删除所有内容
 * 2、备忘录删除有关于“钥匙”的内容（关键字删除）。
 * 3、备忘录删除日期为“2020年7月15日”的内容（时间）。
 * 4、备忘录删除日期为“2020年7月15日”，有关于“钥匙”的内容（双条件）。
 */
public class MemorandumHelper {
    //三个最基本的指令
    private static final String SAVE="记录";
    private static final String QUERY="查询";
    private static final String DELETE="删除";
    private TextToVoice textToVoice;
    public MemorandumHelper(TextToVoice textToVoice ){
        this.textToVoice=textToVoice;
    }
    public void process(final String saying){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(saying==null||"".equals(saying)) {
                    textToVoice.submit("备忘录里没有您所需要的信息");
                    return;
                }
                NLP nlp=new NLP();
                Log.d("testMem","nlp is done");
                JSONObject jsonObject=nlp.getJSONObject(saying);
                List<String> list=new ArrayList<>();
                if(saying.substring(0,6).equals("用备忘录记录"))
                    list.add("记录");
                if(saying.substring(0,6).equals("用备忘录查询"))
                    list.add("查询");
                if(saying.substring(0,6).equals("用备忘录删除"))
                    list.add("删除");
                Log.d("testMem",list.toString()+"  "+saying);
                for(int i=0;i<list.size();i++){
                    if(Double.parseDouble(nlp.sameScore(DELETE,list.get(i)))>0.5){
                        delete(saying);
                    }else if(Double.parseDouble(nlp.sameScore(SAVE,list.get(i)))>0.5){
                        save(saying);
                    }else if(Double.parseDouble(nlp.sameScore(QUERY,list.get(i)))>0.5){
                        query(saying);
                    }
                    else
                        return;
                }
            }
        }).start();

    }
    public void delete(String saying){
        Log.d("testMem",saying);
        int mode=MemorandumAdapter.getDeleteMode(saying);
        if(mode==0) {
            LitePal.deleteAll(MemorandumData.class,"id > ?","0");
            textToVoice.submit("已删除");
        }
        else if(mode==1) {
            String[] data=MemorandumAdapter.getDeleteModeOne(saying);
            LitePal.deleteAll(MemorandumData.class,"buildTime=? and content=?",data[0],data[1]);
            textToVoice.submit("已删除");
        }
        else if(mode==2){
            String data=MemorandumAdapter.getDeleteModeTwo(saying);
            LitePal.deleteAll(MemorandumData.class,"buildTime=?",data);
            textToVoice.submit("已删除");
        }
        else if(mode==3){
            String data=MemorandumAdapter.getDeleteModeThree(saying);
            LitePal.deleteAll(MemorandumData.class,"content=?",data);
            textToVoice.submit("已删除");
        }
        else
            return;
    }
    public void save(String saying){
        Log.d("testMem","save");
        String save=MemorandumAdapter.getSaveContent(saying);
        Log.d("testMem",save);
        SimpleDateFormat formatter=new SimpleDateFormat   ("yyyy年MM月dd日");
        Date curDate =  new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        Memorandum.add(str,save,textToVoice);
        Log.d("testMem",str);
    }
    public void query(String saying){
        String q1=MemorandumAdapter.getQueryDataModeOne(saying);
        String q2=MemorandumAdapter.getQueryDataModeTwo(saying);
        String query=null;
        boolean flag=false;
        if(q1==null&&q2==null)
            return;
        if(q1==null) {
            query = q2;
        }
        else {
            query = q1;
            flag=true;
        }
        List<MemorandumData> query_list=Memorandum.findAll();
        Log.d("testMem",query_list.toString()+query);
        List<String> result=new LinkedList<>();
        if(flag==true){
            for(int j=0;j<query_list.size();j++)
                if(query_list.get(j).getContent().contains(query))
                    result.add(query_list.get(j).getContent());
        }
        else{
            for(int j=0;j<query_list.size();j++)
                if(query_list.get(j).getBuildTime().contains(query))
                    result.add(query_list.get(j).getContent());
        }
        if(result.size()==0) {
            textToVoice.submit("备忘录里没有您所需要的信息");
            return;
        }
        Log.d("testMem", result.toString());
        String final_result = "";
        for (int j = 0; j < result.size(); j++)
            final_result += result.get(j);
        textToVoice.submit(final_result);
    }
}
