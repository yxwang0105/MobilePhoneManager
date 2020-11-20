package com.example.mobilephonemanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView textView=findViewById(R.id.help_text);
        textView.setText("\"打开...\",\n" +
                "\"搜索以...为关键词的外卖\",\n" +
                "\"用备忘录记录...\",\n" +
                "\"用备忘录查询有关于...(关键词)的内容\",\n" +
                "\"用备忘录查询日期为...(年月日)的内容\",\n" +
                "\"用备忘录删除所有内容\",\n" +
                "\"用备忘录删除有关于...(关键词)的内容\",\n" +
                "\"备忘录删除日期为...(年月日)的内容\",\n" +
                "\"备忘录删除日期为“...”，有关于...的内容\",\n" +
                "\"查询...市的天气\",\n" +
                "\"给...发送QQ消息...\",\n" +
                "\"给...发送微信消息...\",\n" +
                "\"打开微信扫一扫\",\n" +
                "\"打开朋友圈\",\n" +
                "\"给...(联系人)打电话\",\n" +
                "\"给...(联系人)发短信，内容为...\",\n" +
                "\"打开朋友圈\",\n" +
                "\"打开微信扫一扫\",\n"+
                "\"打开课程表\",\n"+
                        "\"查询快递\",\n"+
                "\"注意:当具体操控微信，QQ，饿了吗的时候记住打开无障碍服务\",\n"+
                "\"在第一次启动时需要点击创建数据库\""
                );
    }
}
