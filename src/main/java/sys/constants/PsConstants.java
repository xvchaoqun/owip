package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class PsConstants {
    //党校管理员类别，1、二级党校管理员 2、院系级党委管理员
    public final static byte PS_ADMIN_TYPE_PARTY = 1;
    public final static byte PS_ADMIN_TYPE_UNIT = 2;
    public final static Map<Byte, String> PS_ADMIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        PS_ADMIN_TYPE_MAP.put(PS_ADMIN_TYPE_PARTY, "二级党校管理员");
        PS_ADMIN_TYPE_MAP.put(PS_ADMIN_TYPE_UNIT, "院系级党委管理员");
    }

    //组织员状态
    public final static boolean PS_STATUS_IS_HISTORY = true;//已停止使用,历史记录
    public final static boolean PS_STATUS_NOT_HISTORY = false;//正在使用,未停止记录

    //二级党校管理员角色
    public final static String ROLE_PS_PARTY = "role_ps_party";//二级党校管理员
    public final static String ROLE_PS_UNIT = "role_ps_unit";//二级党校管理员（院系）
    public final static String ROLE_PS_ADMIN = "role_ps_admin"; //二级党校管理员（组织部）

    public final static String TEACHERNUMBER = "teacherNumber";//二级党校教师总人数
    public final static String STUDENTNUMBER = "studentNumber";//二级党校学生总人数
    public final static String COUNTNUMBER = "countNumber";//二级党校总人数
    public final static String RETIRENUMBER = "retireNumber";//二级党校离退休总人数

}
