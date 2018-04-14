package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/4/14.
 */
public class VerifyConstants {

    // 干部档案审核 认定记录状态  0：正式记录 1：修改记录 2：已删除，每个干部的正式记录只有一条
    public final static byte VERIFY_STATUS_NORMAL = 0;
    public final static byte VERIFY_STATUS_MODIFY = 1;
    public final static byte VERIFY_STATUS_DEL = 2;
    public static Map<Byte, String> VERIFY_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_STATUS_MAP.put(VERIFY_STATUS_NORMAL, "正式记录");
        VERIFY_STATUS_MAP.put(VERIFY_STATUS_MODIFY, "修改记录");
        VERIFY_STATUS_MAP.put(VERIFY_STATUS_DEL, "已删除");
    }

    // 干部档案审核 出生时间认定类别  1：阴阳历换算造成误差 2：出生时间改大了，保持现状 3：出生时间改小了，重新认定
    public final static byte VERIFY_AGE_TYPE_ERROR = 1;
    public final static byte VERIFY_AGE_TYPE_LARGE = 2;
    public final static byte VERIFY_AGE_TYPE_SMALL = 3;
    public final static byte VERIFY_AGE_TYPE_OTHER = 4;
    public static Map<Byte, String> VERIFY_AGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_ERROR, "阴阳历换算造成误差");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_LARGE, "出生时间改大了，保持现状");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_SMALL, "出生时间改小了，重新认定");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_OTHER, "其他情况");
    }

    // 干部档案审核 参加工作时间认定类别  1：阴阳历换算造成误差 2：参加工作时间改大了，保持现状 3：参加工作时间改小了，重新认定
    public final static byte VERIFY_WORK_TIME_TYPE_ERROR = 1;
    public final static byte VERIFY_WORK_TIME_TYPE_LARGE = 2;
    public final static byte VERIFY_WORK_TIME_TYPE_SMALL = 3;
    public final static byte VERIFY_WORK_TIME_TYPE_OTHER = 4;
    public static Map<Byte, String> VERIFY_WORK_TIME_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_ERROR, "阴阳历换算造成误差");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_LARGE, "参加工作时间改大了，保持现状");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_SMALL, "参加工作时间改小了，重新认定");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_OTHER, "其他情况");
    }
}
