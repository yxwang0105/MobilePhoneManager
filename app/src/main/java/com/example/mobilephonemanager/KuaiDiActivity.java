package com.example.mobilephonemanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KuaiDiActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private EditText input;
    private TextView output;
    private Button confirm;
    private RadioGroup kuaidis;
    private String kuaidi;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            output.setText(msg.obj.toString());
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuaidi);
        input=(EditText) findViewById(R.id.input_content);
        output=(TextView)findViewById(R.id.output_content);
        confirm=(Button)findViewById(R.id.query);
        kuaidis=(RadioGroup)findViewById(R.id.radioGroup);
        confirm.setOnClickListener(this);
        kuaidis.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        final String danhao=this.input.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
                if(kuaidi==null)
                    kuaidi="STO";
                try {
                    String content="";
                    String jsonData = api.getOrderTracesByJson(kuaidi, danhao);
                    List<String> list = getJson(jsonData);
                    for(int i=0;i<list.size();i++){
                        content+=list.get(i)+"\n";
                    }
                    Message message=new Message();
                    message.obj=content;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.getStackTrace();
                }
            }
        }).start();

    }

        @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.YTO:
                kuaidi="YTO";
                break;
            case R.id.STO:
                kuaidi="STO";
                break;
            case R.id.HTKY:
                kuaidi="HTKY";
                break;
            case R.id.HHTT:
                kuaidi="HHTT";
                break;
            default:
                break;

        }
    }
    private List<String> getJson(String jsonData){
        List<String> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            String Traces=jsonObject.getString("Traces");
            JSONArray TraceArray=new JSONArray(Traces);
            for(int i=0;i<TraceArray.length();i++){
                JSONObject target=TraceArray.getJSONObject(i);
                String AcceptStation=target.getString("AcceptStation");
                String AcceptTime=target.getString("AcceptTime");
                String content=AcceptStation+" "+AcceptTime;
                list.add(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
