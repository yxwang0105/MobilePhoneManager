package com.example.mobilephonemanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class Weather {
    public static final String TAG="weather";
    private static final String username="HE2004041333451062";
    private static final String appkey="840b077e05474d5481b3464354cd1075";
    public Weather(){
        HeConfig.init(username,appkey);
        HeConfig.switchToFreeServerNode();//使用免费节点
    }

    /**
     * 测试使用时返回到Logcat中，返回生活指数
     * @param context
     */
    public void getLifeStyle(Context context,String city){
        HeWeather.getWeatherLifeStyle(context,city, Lang.CHINESE_SIMPLIFIED, Unit.METRIC,new HeWeather.OnResultWeatherLifeStyleBeanListener(){
            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "Weather Now onError: ", throwable);
            }

            @Override
            public void onSuccess(Lifestyle lifestyle) {
                Log.i(TAG, " Weather Now onSuccess: " + new Gson().toJson(lifestyle));

                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if ( Code.OK.getCode().equalsIgnoreCase(lifestyle.getStatus()) ){
                    //此时返回数据
                    List<LifestyleBase> list=lifestyle.getLifestyle();
                    list.get(0).getType();
                    Log.d("weather",list.get(0).getTxt());
                } else {
                    //在此查看返回数据失败的原因
                    String status = lifestyle.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });


    }
}
