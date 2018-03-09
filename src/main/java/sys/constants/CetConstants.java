package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CetConstants {

    // 参训人员类型模板
    public final static byte CET_TRAINEE_TYPE_TEMPLATE_A = 1;
    public final static byte CET_TRAINEE_TYPE_TEMPLATE_B = 2;
    public final static byte CET_TRAINEE_TYPE_TEMPLATE_C = 3;
    public final static Map<Byte, String> CET_TRAINEE_TYPE_TEMPLATE_MAP = new LinkedHashMap();

    static {
        CET_TRAINEE_TYPE_TEMPLATE_MAP.put(CET_TRAINEE_TYPE_TEMPLATE_A, "模板A（中层干部、后备干部）");
        CET_TRAINEE_TYPE_TEMPLATE_MAP.put(CET_TRAINEE_TYPE_TEMPLATE_B, "模板B（分党委班子成员、党支部班子成员、组织员）");
        CET_TRAINEE_TYPE_TEMPLATE_MAP.put(CET_TRAINEE_TYPE_TEMPLATE_C, "模板C（入党积极分子）：待定");
    }

    // 培训班 发布状态，0 未发布 1 已发布  2 取消发布
    public final static byte CET_TRAIN_PUB_STATUS_UNPUBLISHED = 0;
    public final static byte CET_TRAIN_PUB_STATUS_PUBLISHED = 1;
    public final static byte CET_TRAIN_PUB_STATUS_CANCEL = 2;
    public static Map<Byte, String> CET_TRAIN_PUB_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_PUB_STATUS_MAP.put(CET_TRAIN_PUB_STATUS_UNPUBLISHED, "未发布");
        CET_TRAIN_PUB_STATUS_MAP.put(CET_TRAIN_PUB_STATUS_PUBLISHED, "已发布");
        CET_TRAIN_PUB_STATUS_MAP.put(CET_TRAIN_PUB_STATUS_CANCEL, "取消发布");
    }

    // 培训班 选课状态，0 根据选课时间而定 1 强制开启、2 强制关闭、3 暂停选课
    public final static byte CET_TRAIN_ENROLL_STATUS_DEFAULT = 0;
    public final static byte CET_TRAIN_ENROLL_STATUS_OPEN = 1;
    public final static byte CET_TRAIN_ENROLL_STATUS_CLOSED = 2;
    public final static byte CET_TRAIN_ENROLL_STATUS_PAUSE = 3;
    public static Map<Byte, String> CET_TRAIN_ENROLL_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_DEFAULT, "根据选课时间而定");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_OPEN, "正在选课");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_CLOSED, "选课结束");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_PAUSE, "暂停选课");
    }
}
