package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class DrConstants {

    public final static String DR_ONLINE_URL = "localhost:8080/dr/drOnline/login";

    //参评人
    public final static Byte ROLE_INSPECTOR = 4;

    // 推荐组成员类别
    public final static byte DR_MEMBER_STATUS_NOW = 1;
    public final static byte DR_MEMBER_STATUS_HISTORY = 2;
    public final static byte DR_MEMBER_STATUS_DELETE = 3;
    public final static Map<Byte, String> DR_MEMBER_STATUS_MAP = new LinkedHashMap<>();

    static {
        DR_MEMBER_STATUS_MAP.put(DR_MEMBER_STATUS_NOW, "现任推荐组成员");
        DR_MEMBER_STATUS_MAP.put(DR_MEMBER_STATUS_HISTORY, "过去推荐组成员");
        DR_MEMBER_STATUS_MAP.put(DR_MEMBER_STATUS_DELETE, "已删除");
    }

    //线上民主推荐批次管理状态
    public final static byte DR_ONLINE_NOT_RELEASE = 0;
    public final static byte DR_ONLINE_RELEASE = 1;
    public final static byte DR_ONLINE_WITHDRAW = 2;
    public final static byte DR_ONLINE_FINISH = 3;
    public final static Map<Byte, String> DR_ONLINE_MAP = new LinkedHashMap<>();

    static {
        DR_ONLINE_MAP.put(DR_ONLINE_NOT_RELEASE, "未发布");
        DR_ONLINE_MAP.put(DR_ONLINE_RELEASE, "已发布");
        DR_ONLINE_MAP.put(DR_ONLINE_WITHDRAW, "已撤回");
        DR_ONLINE_MAP.put(DR_ONLINE_FINISH, "已完成");

    }

    //线上民主推荐参评人类型的状态
    public final static byte DR_ONLINE_INSPECTOR_TYPE_FORMAL = 0;
    public final static byte DR_ONLINE_INSPECTOR_TYPE_LOCK = 1;
    public final static byte DR_ONLINE_INSPECTOR_TYPE_CANCEL= 2;
    public final static Map<Byte, String> DR_ONLINE_INSPECTOR_TYPE_MAP= new LinkedHashMap<>();

    static {
        DR_ONLINE_MAP.put(DR_ONLINE_INSPECTOR_TYPE_FORMAL, "可用");
        DR_ONLINE_MAP.put(DR_ONLINE_INSPECTOR_TYPE_LOCK, "锁定");
        DR_ONLINE_MAP.put(DR_ONLINE_INSPECTOR_TYPE_CANCEL, "作废");

    }

    // 参评人账号状态
    public final static byte INSPECTOR_STATUS_INIT = 0;
    public final static byte INSPECTOR_STATUS_ABOLISH = 1;
    public final static byte INSPECTOR_STATUS_FINISH = 2;
    public final static byte INSPECTOR_STATUS_SAVE = 3;
    public static Map<Byte, String> INSPECTOR_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        INSPECTOR_STATUS_MAP.put(INSPECTOR_STATUS_INIT, "可用");
        INSPECTOR_STATUS_MAP.put(INSPECTOR_STATUS_ABOLISH, "已作废");
        INSPECTOR_STATUS_MAP.put(INSPECTOR_STATUS_FINISH, "已完成");
        INSPECTOR_STATUS_MAP.put(INSPECTOR_STATUS_SAVE, "暂存");
    }

    // 参评人账号状态
    public final static byte INSPECTOR_PUB_STATUS_NOT_RELEASE = 0;
    public final static byte INSPECTOR_PUB_STATUS_RELEASE = 1;
    public static Map<Byte, String> INSPECTOR_PUB_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        INSPECTOR_STATUS_MAP.put(INSPECTOR_PUB_STATUS_RELEASE, "已分发");
        INSPECTOR_STATUS_MAP.put(INSPECTOR_PUB_STATUS_NOT_RELEASE, "未分发");
    }

    //参评人密码修改类型行
    public final static byte INSPECTOR_PASSWD_CHANGE_TYPE_SELF = 1;
    public final static byte INSPECTOR_PASSWD_CHANGE_TYPE_ADMIN = 2;
    public static Map<Byte, String> INSPECTOR_PASSWD_CHANGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(INSPECTOR_PASSWD_CHANGE_TYPE_SELF, "本人修改");
        INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(INSPECTOR_PASSWD_CHANGE_TYPE_ADMIN, "系统管理员修改");
    }

}
