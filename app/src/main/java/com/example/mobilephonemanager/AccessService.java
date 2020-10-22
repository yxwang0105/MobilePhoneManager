package com.example.mobilephonemanager;
import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 饿了吗外卖的接入：
 * 1、搜索关键词
 * 2、第一个数据为用户点击位置的第一个逗号之前的信息为店名
 * 3、进入第二个界面后，第二个数据为第一个逗号之前的数据（这个还需要实验加号是否也是），直到用户按下结账的按钮，第三个数据就是总价格，第四个数据就是日期（详细）
 * 那么我们需要思考的是，怎么判断按下的按钮一定是我们想要的界面呢？
 * 第一个界面的判断条件是点击的事件来自于这个应用且第一个括号中含有的所有关键词中含有该关键词，此时增加布尔量已经打开第一个界面
 * 第二个界面的判断机制是第一个布尔量为true，注意把“饿了吗”关键词踢出去，或者将没有“¥”符号的全部踢出去，当用户点击结账或者退出按钮的时候或者各种意外退出的时候第一个布尔量为false
 * 当用户点击结账后，提交数据
 */
public class AccessService extends AccessibilityService {
    public static AccessService mAccservice;
    public static String QQ_saying;
    public static String QQ_people;
    public static String WeChat_saying;
    public static String WeChat_people;
    public static String ELE_saying;
    public static boolean random;
    public static boolean Scan;
    public static boolean Friend;
    private boolean first=true;
    public static boolean isStart() {
        return mAccservice != null;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mAccservice = this;
        Log.d("testELE", "已经连接");
    }

    @Override
    public void onDestroy() {
        Log.d("testELE", "is destory");
        super.onDestroy();
    }

    @Override
    public void onInterrupt() {
        Log.d("testELE", "is interrupt");
    }

    public void clear() {
        QQ_saying = null;
        WeChat_saying = null;
        WeChat_people = null;
        ELE_saying = null;
        random = false;
        first=true;
    }

    public boolean isselected() {
        Log.d("testELE", "正在判断");
        if (QQ_saying == null && WeChat_people == null && WeChat_people == null && ELE_saying == null&&Scan==false&&Friend ==false)
            return false;
        return true;
    }

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        if (!isselected()) {
            return;
        }
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                if ("com.tencent.mobileqq".equals(event.getPackageName()) && QQ_saying != null && QQ_people != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        Log.d("testQQ", "正在进入QQ");
                        WechatUtils.findTextAndClick(mAccservice, "联系人");
                        WechatUtils.findTextAndClick(mAccservice, "搜索");
                        AccessibilityNodeInfo QQNodeInfo = mAccservice.getRootInActiveWindow();
                        List<AccessibilityNodeInfo> edit_list = QQNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/et_search_keyword");
                        if (edit_list.size() != 0) {
                            AccessibilityNodeInfo edit_node = edit_list.get(0);
                            Bundle arguments = new Bundle();
                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, QQ_people);
                            edit_node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        List<AccessibilityNodeInfo> contactList = QQNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/image");
                        if (contactList.size() != 0) {
                            AccessibilityNodeInfo contact = contactList.get(0).getParent();
                            WechatUtils.performClick(contact);
                        }
                        List<AccessibilityNodeInfo> textInfoList = QQNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/input");
                        List<AccessibilityNodeInfo> buttonInfoList = QQNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/fun_btn");
                        if (textInfoList.size() != 0 && buttonInfoList.size() != 0) {
                            AccessibilityNodeInfo textInfo = textInfoList.get(0);
                            Bundle arguments = new Bundle();
                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, QQ_saying);
                            Log.d("testQQ",textInfo.toString());
                            Log.d("testQQ",QQ_saying);
                            textInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                            Log.d("testQQ", buttonInfoList.get(0).toString());
                            AccessibilityNodeInfo buttonInfo = buttonInfoList.get(0);
                            buttonInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            clear();
                        }

                    }
                }
                if ("com.tencent.mm".equals(event.getPackageName()) && WeChat_people != null && WeChat_saying != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        WechatUtils.findTextAndClick(mAccservice, "通讯录");
                        WechatUtils.findViewIdAndClick(mAccservice, "com.tencent.mm:id/dn7");
                        try {
                            Thread.sleep(1000);
                            AccessibilityNodeInfo WeChatNodeInfo = mAccservice.getRootInActiveWindow();
                            List<AccessibilityNodeInfo> searchList = WeChatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bhn");
                            if (searchList.size() != 0) {
                                Bundle arguments = new Bundle();
                                arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, WeChat_people);
                                searchList.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                            }
                            Thread.sleep(1000);
                            List<AccessibilityNodeInfo> contactList = WeChatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/tm");
                            if (contactList.size() != 0) {
                                AccessibilityNodeInfo node = contactList.get(0).getParent();
                                WechatUtils.performClick(node);
                            }
                            List<AccessibilityNodeInfo> textList = WeChatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/al_");
                            Log.d("testWwechat", textList.size() + "");
                            if (textList.size() != 0) {
                                AccessibilityNodeInfo textInfo = textList.get(0);
                                Bundle arguments = new Bundle();
                                arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, WeChat_saying);
                                textInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                                List<AccessibilityNodeInfo> buttonList = WeChatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/anv");
                                AccessibilityNodeInfo buttonInfo = buttonList.get(0);
                                buttonInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                clear();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if ("com.tencent.mm".equals(event.getPackageName()) && Scan == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        Log.d("testScan","正在打开扫一扫");
                        AccessibilityNodeInfo WechatNodeInfo = mAccservice.getRootInActiveWindow();
                        List<AccessibilityNodeInfo> faxians = WechatNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cn_");
                        if (faxians.size() != 0) {
                            Log.d("testScan","发现已找到");
                            AccessibilityNodeInfo faxian = faxians.get(0);
                            WechatUtils.performClick(faxian);
                            WechatUtils.findTextAndClick(mAccservice, "扫一扫");
                            Log.d("testScan","已打开扫一扫");
                        }
                        Scan = false;
                    }
                }
                if ("com.tencent.mm".equals(event.getPackageName()) && Friend == true) {
                    Log.d("testFriend", "正在打开扫一扫");
                    WechatUtils.findTextAndClick(mAccservice, "发现");
                    WechatUtils.findTextAndClick(mAccservice, "朋友圈");
                    Friend = false;
                }
                if ("me.ele".equals(event.getPackageName()) && ELE_saying != null) {
                    if(first) {
                        WechatUtils.findViewIdAndClick(mAccservice, "me.ele:id/asb");
                        first=false;
                    }
                    AccessibilityNodeInfo eleNodeInfo = mAccservice.getRootInActiveWindow();
                    List<AccessibilityNodeInfo> editNodeList = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        editNodeList = eleNodeInfo.findAccessibilityNodeInfosByViewId("me.ele:id/a15");
                    }
                    if (editNodeList.size() != 0) {
                        AccessibilityNodeInfo editInfo = editNodeList.get(0);
                        Bundle arguments = new Bundle();
                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, ELE_saying);
                        editInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (random == false) {
                            Log.d("testEle", "正在搜索");
                            WechatUtils.findTextAndClick(mAccservice, "搜索");
                            this.storeRandom(ELE_saying);
                        } else {
                            WechatUtils.findViewIdAndClick(mAccservice, "me.ele:id/a3m");
                            this.storeRandom(ELE_saying);
                        }
                        clear();
                    }
                }
        }
    }

    public void storeRandom(String store){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        String time = formatter.format(date);
        String[] times = time.split("/");
        int year = Integer.parseInt(times[0]);
        int month = Integer.parseInt(times[1]);
        int day = Integer.parseInt(times[2]);
        int hour = Integer.parseInt(times[3]);
        int min = Integer.parseInt(times[4]);
        TakeOutFood takeOutFood = new TakeOutFood();
        takeOutFood.setStore(store);
        takeOutFood.setYear(year);
        takeOutFood.setMonth(month);
        takeOutFood.setDay(day);
        takeOutFood.setHour(hour);
        takeOutFood.setMin(min);
        takeOutFood.save();
        Log.d("testEle",store+"已存储");
    }
}




