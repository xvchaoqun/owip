package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class PsInfoConstants {
    //党校管理员类别，1、二级党校管理员 2、院系级党委管理员
    public final static byte PS_ADMIN_TYPE_PARTY = 1;
    public final static byte PS_ADMIN_TYPE_FACULTY = 2;
    public final static Map<Byte, String> PS_ADMIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        PS_ADMIN_TYPE_MAP.put(PS_ADMIN_TYPE_PARTY, "二级党校管理员");
        PS_ADMIN_TYPE_MAP.put(PS_ADMIN_TYPE_FACULTY, "院系级党委管理员");
    }

    //组织员状态
    public final static boolean PS_STATUS_IS_HISTORY = true;//已停止使用,历史记录
    public final static boolean PS_STATUS_NOT_HISTORY = false;//正在使用,未停止记录

    //二级党校管理员角色
    public final static String ROLE_ALLPS_ADMIN = "role_allPs_Admin";//全部的二级党校管理员
    public final static String ROLE_PS_ADMIN = "role_ps_admin"; //二级党校管理员

}
