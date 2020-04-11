package com.example.mobilephonemanager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.aip.nlp.AipNlp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 使用自然语言分析处理时一定需要记得使用子线程，要不然会报网络错误
 */
public class NLP {
    public static final String APP_ID = "18950708";
    public static final String API_KEY = "0awPzjj4qVDaC9XmZ8iD6785";
    public static final String SECRET_KEY = "gkNxQkTunBGsTMf2jV1KtQoizByX7Zrl";
    private static AipNlp client;
    public NLP(){
        client=getInstance();
    }
    private static AipNlp getInstance(){
        if(client==null){
            client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
            //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
            //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
            return client;
        }
        else
            return client;
    }
    public JSONObject getJSONObject(String text) {
        JSONObject res = client.lexer(text,null);
        Log.d("acca",res.toString());
        return res;
    }

    /**
     *
     * @param jsonData JSONObject格式数据
     * @param pos  //把这句话中词性为pos的词提取出来
     * @return 返回List对象
     */
    public List<String> fromPos(JSONObject jsonData, String pos) {
        List<String> list=new ArrayList<>();
        try {
            JSONArray jsonArray=jsonData.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                String pos_object=object.getString("pos");
                if(pos_object.equals(pos)) {
                    list.add(object.getString("item"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public String sameScore(String text1,String text2) {
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("model", "CNN");
        // 短文本相似度
        JSONObject res = client.simnet(text1, text2, options);

        try {
            String score=res.getString("score");
            return score;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 做依存句法分析找出不同句法的词
     */
    public List<String> getDeprel(String text,String deprel){
        List<String> list=new ArrayList<>();
        if(deprel==null||"".equals(deprel)||text==null||"".equals(text))
            return null;
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("mode", 1);

        // 依存句法分析
        JSONObject res = client.depParser(text, options);
        try {
            JSONArray jsonArray=res.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(deprel.equals(jsonObject.getString("deprel")))
                    list.add(jsonObject.getString("word"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
