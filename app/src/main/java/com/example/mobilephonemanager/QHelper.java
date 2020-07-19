package com.example.mobilephonemanager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * 格式为“给某某某发送QQ消息。。。。。。”
 */
public class QHelper {
    public static String getPeople(String saying){
        int loc1=saying.indexOf("给")+1;
        if(loc1==-1)
            loc1=saying.indexOf("向")+1;
        int loc2=saying.indexOf("发送");
        String people=saying.substring(loc1,loc2);
        return people;
    }
    public static String getContent(String saying){
        int loc=saying.indexOf("消息")+2;
        String content=saying.substring(loc);
        return content;
    }
    public static void openQQ(Context mContext, int type, String qq) {
        if (checkApk(mContext, "com.tencent.mobileqq")) {
            String url = null;
            switch (type) {
                case 1:
                    //  进入QQ聊天
                    url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                    break;
                case 2:
                    //  个人介绍界面
                    url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + qq
                            + "&card_type=person&source=qrcode";
                    break;
                case 3:
                    // QQ群介绍界面
                    url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + qq
                            + "&card_type=group&source=qrcode";
                    break;
                case 4:
                    // QQ公众号
                    url = "mqq://im/chat?chat_type=crm&uin=" + qq;
                    break;
                default:
                    break;
            }
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
        Toast.makeText(mContext, "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
    }
    }
    /**
     * 检查apk是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApk(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
