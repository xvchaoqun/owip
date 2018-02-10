package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class TrainConstants {

    // 干部培训 评课账号的状态
    public final static byte TRAIN_INSPECTOR_STATUS_INIT = 0;
    public final static byte TRAIN_INSPECTOR_STATUS_ABOLISH = 1;
    public final static byte TRAIN_INSPECTOR_STATUS_ALL_FINISH = 2;
    public final static byte TRAIN_INSPECTOR_STATUS_PART_FINISH = 3;
    public static Map<Byte, String> TRAIN_INSPECTOR_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_INIT, "未完成");
        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_ABOLISH, "已作废");
        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_ALL_FINISH, "全部完成");
        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_PART_FINISH, "部分完成");
    }

    // 干部培训 评课账号的某门课程的评课状态
    public final static byte TRAIN_INSPECTOR_COURSE_STATUS_SAVE = 0;
    public final static byte TRAIN_INSPECTOR_COURSE_STATUS_FINISH = 1;
    public static Map<Byte, String> TRAIN_INSPECTOR_COURSE_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        TRAIN_INSPECTOR_COURSE_STATUS_MAP.put(TRAIN_INSPECTOR_COURSE_STATUS_SAVE, "暂存");
        TRAIN_INSPECTOR_COURSE_STATUS_MAP.put(TRAIN_INSPECTOR_COURSE_STATUS_FINISH, "已完成");
    }

    // 评课账号修改密码类型
    public final static byte TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF = 1;
    public final static byte TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET = 2;
    public static Map<Byte, String> TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF, "本人修改");
        TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET, "管理员重置");
    }
}
