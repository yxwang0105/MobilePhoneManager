package com.example.mobilephonemanager;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * 我们首先考虑一下我们会怎样使用查询功能呢？
 * 一般来说我们大概会问如下几种问题
 * 1、“用备忘录查询一下我的钥匙在哪”
 * 2、“我的钥匙在哪”
 * 3、“查询备忘录中我的钥匙在哪”
 * 4、“用备忘录查询关于我钥匙的情报”
 * 5、“将备忘录中所有内容读出来”
 * 注意，在这里面，我们一般不考虑类似于此类的问题“我最近有没有什么安排”，因为备忘录的主要作用应该是记录不包含命中时间的一般性情报，那些较为特殊的应该由主程序判断交给闹钟等程序来主动提醒或者被动读取
 * 其次，在这里面我们也不考虑第二种情况，因为水平着实不太够（笑）
 * 所以，内容就很明确了，首先我们可以意识到重要的内容应该是“查询”或者“备忘录”之后的内容，这两种都比较符合常用语序
 * 那么之后的内容我们将数据库中所有的内容提取，用语义上的匹配来完成就可以了，匹配度最高的大概就是了。
 * 但是这其中也有一些问题，比如关于钥匙的情报有很多，匹配度最高的不一定就是想要的答案。
 * 最好的办法就是划定一个大致的范围，然后在范围中取前几名返回结果
 * 那么这个范围该如何划分呢
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
                        String save=MemorandumAdapter.getSaveContent(saying);
                        Log.d("aaaasasa",save);
                        Memorandum.getDatabase();
                        SimpleDateFormat formatter=new SimpleDateFormat   ("yyyy年MM月dd日HH:mm:ss");
                        Date curDate =  new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        Memorandum.add(str,save);
                    }else if(Double.parseDouble(nlp.sameScore(QUERY,list.get(i)))>0.5){

                    }else if(Double.parseDouble(nlp.sameScore(DELETE,list.get(i)))>0.5){

                    }

                }
            }
        }).start();

    }

}
