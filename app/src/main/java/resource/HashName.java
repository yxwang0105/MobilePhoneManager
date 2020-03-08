package resource;

import android.app.MediaRouteActionProvider;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashName {
    public static Map<String,String> maps = new HashMap<>();
    static {
        maps.put("系统浏览器",ProgrammePackageName.BROWSER);
        maps.put("QQ",ProgrammePackageName.QQ);
        maps.put("QQ浏览器",ProgrammePackageName.QQ_SURFING);
        maps.put("QQ视频",ProgrammePackageName.QQ_VIDEO);
        maps.put("QQ邮箱",ProgrammePackageName.QQ_MAIL);
        maps.put("QQ教育",ProgrammePackageName.QQ_EDUCATION);
        maps.put("微信",ProgrammePackageName.WE_CHAT);
    }
}
