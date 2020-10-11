package com.example.mobilephonemanager;

import android.content.Context;

/**
 * 这个类主要是用来辅助Weather类，具体的实现目标是将自然语言翻译，提取关键字，提取城市的关键字
 * 标准格式：查询某某市的天气
 */
public class WeatherHelper {
    private TextToVoice textToVoice;
    private Context context;
    public WeatherHelper(Context context, TextToVoice textToVoice){
        this.textToVoice=textToVoice;
        this.context=context;
    }
    public void process(final String saying){
        new Thread(new Runnable() {
            @Override
            public void run() {
              Weather weather= new Weather(textToVoice);
              weather.getLifeStyle(context,saying);
            }
        }).start();
    }
    public String getCity(String message){
        int loc1=message.indexOf("查询");
        int loc2=message.indexOf("的天气");
        String city=message.substring(loc1+2,loc2);
        return city;
    }
}
