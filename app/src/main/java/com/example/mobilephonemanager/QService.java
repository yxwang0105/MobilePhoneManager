package com.example.mobilephonemanager;
import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class QService extends AccessibilityService {
    public static QService mQservice;
    public static String saying;
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mQservice=this;
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType=event.getEventType();
        switch (eventType){
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                AccessibilityNodeInfo accessibilityNodeInfo = mQservice.getRootInActiveWindow();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    List<AccessibilityNodeInfo> textInfoList=accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/input");
                    List<AccessibilityNodeInfo> buttonInfoList=accessibilityNodeInfo.findAccessibilityNodeInfosByText("发送");
                    if(textInfoList.size()!=0&&buttonInfoList.size()!=0) {
                        AccessibilityNodeInfo textInfo = textInfoList.get(0);
                        Bundle arguments = new Bundle();
                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, saying);
                        textInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                        AccessibilityNodeInfo buttonInfo=buttonInfoList.get(0);
                        buttonInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            default:
                    break;
        }
    }

    @Override
    public void onInterrupt() {
        mQservice=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQservice=null;
    }

    public static boolean isStart() {
        return mQservice!=null;
    }
}
