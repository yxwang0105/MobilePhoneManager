package com.example.mobilephonemanager;
import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.LocusId;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.litepal.LitePal;

import java.util.List;
public class AccessService extends AccessibilityService {
    public static AccessService mAccservice;
    public static String QQ_saying;
    public static String WeChat_saying;
    public static String WeChat_people;

    public static boolean isStart() {
        return mAccservice != null;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mAccservice = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                //AccessibilityNodeInfo QQNodeInfo = mAccservice.getRootInActiveWindow();
                //List<AccessibilityNodeInfo> textInfoList = QQNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/input");
                //List<AccessibilityNodeInfo> buttonInfoList = QQNodeInfo.findAccessibilityNodeInfosByText("发送");
                //if (textInfoList.size() != 0 && buttonInfoList.size() != 0) {
                //AccessibilityNodeInfo textInfo = textInfoList.get(0);
                //Bundle arguments = new Bundle();
                //arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, QQ_saying);
                //textInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                //AccessibilityNodeInfo buttonInfo = buttonInfoList.get(0);
                //  buttonInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //}
                //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                //WechatUtils.findTextAndClick(mAccservice, "通讯录");
                //WechatUtils.findTextAndClick(mAccservice, WeChat_people);
                //WechatUtils.findTextAndClick(mAccservice, "发消息");
                //AccessibilityNodeInfo WeChatNodeInfo = mAccservice.getRootInActiveWindow();
                //List<AccessibilityNodeInfo> textList = WeChatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ajs");
                //List<AccessibilityNodeInfo> buttonList = WeChatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/amb");
                //if (textList.size() != 0 && buttonList.size() != 0) {
                //AccessibilityNodeInfo textInfo = textList.get(0);
                //AccessibilityNodeInfo buttonInfo = buttonList.get(0);
                //Bundle arguments = new Bundle();
                //arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,WeChat_saying);
                //textInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                //  buttonInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //}
                if (WechatUtils.findViewIdAndClick(mAccservice, "me.ele:id/aex")) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AccessibilityNodeInfo eleNodeInfo = mAccservice.getRootInActiveWindow();
                    List<AccessibilityNodeInfo> editNodeList = eleNodeInfo.findAccessibilityNodeInfosByViewId("me.ele:id/a15");
                    if(editNodeList.size()!=0) {
                        AccessibilityNodeInfo editInfo=editNodeList.get(0);
                        Bundle arguments = new Bundle();
                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,"麻辣烫");
                        editInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                        WechatUtils.findTextAndClick(mAccservice,"搜索");
                    }
                }
        }
    }
}