package com.example.mobilephonemanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

public class Phone {
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhoneJump(String phoneNum,Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public static void callPhone(String phoneNum,Context context){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
    public static void callPhoneThroughContacts(String name,Context context){
        if(name==null||name.equals(""))
            return;
        ArrayList<MyContacts> arrayList=getAllContacts(context);
        String phone=null;
        for(int i=0;i<arrayList.size();i++){
            if(ChineseToEnglish.getPingYin(name).equals(ChineseToEnglish.getPingYin(arrayList.get(i).name)))
            phone=arrayList.get(i).phone;
        }
        if(phone!=null)
            callPhone(phone,context);
    }
    public static void sendSMS(final String name,final String content,final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SmsManager smsManager = SmsManager.getDefault();
                ArrayList<String> list = smsManager.divideMessage(content);
                String phone=getPhoneNumber(name,context);
                if(phone!=null) {
                    for (int i = 0; i < list.size(); i++) {
                        smsManager.sendTextMessage(phone, null, list.get(i), null, null);
                    }
                }
            }
        }).start();
    }
    private static String getPhoneNumber(String name,Context context){
        if(name==null||name.equals("")) {
            Log.d("testPhone","name is null");
            return null;
        }
        ArrayList<MyContacts> arrayList=getAllContacts(context);
        String phone=null;
        for(int i=0;i<arrayList.size();i++){
            if(ChineseToEnglish.getPingYin(name).equals(ChineseToEnglish.getPingYin(arrayList.get(i).name)))
                phone=arrayList.get(i).phone;
        }
        return phone;
    }
    private static ArrayList<MyContacts> getAllContacts(Context context) {
        ArrayList<MyContacts> contacts = new ArrayList<MyContacts>();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            MyContacts temp = new MyContacts();
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            temp.name = name;

            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                temp.phone = phone;
            }

            //获取联系人备注信息
            Cursor noteCursor = context.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Nickname.NAME},
                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE + "'",
                    new String[]{contactId}, null);
            if (noteCursor.moveToFirst()) {
                do {
                    String note = noteCursor.getString(noteCursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    temp.note = note;
                    Log.i("note:", note);
                } while (noteCursor.moveToNext());
            }
            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
            noteCursor.close();
        }
        cursor.close();
        return contacts;
    }
}
