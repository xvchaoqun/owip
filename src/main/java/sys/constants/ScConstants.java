package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class ScConstants {

    /**
     * 动议
     */
    // 动议形式，党委干部工作小组会、 党委常委会、 其他
    public final static byte SC_MOTION_WAY_GROUP = 1;
    public final static byte SC_MOTION_WAY_COMMITE = 2;
    public final static byte SC_MOTION_WAY_OTHER = 3;
    public final static Map<Byte, String> SC_MOTION_WAY_MAP = new LinkedHashMap();

    static {
        SC_MOTION_WAY_MAP.put(SC_MOTION_WAY_GROUP, "党委干部工作小组会");
        SC_MOTION_WAY_MAP.put(SC_MOTION_WAY_COMMITE, "党委常委会");
        SC_MOTION_WAY_MAP.put(SC_MOTION_WAY_OTHER, "其他");
    }

    /**
     * 新提任干部交证件
     */
    // 添加方式，1 自动识别  2 从干部库中选择 3 从任免文件中提取
    public final static byte SC_PASSPORTHAND_ADDTYPE_AUTO = 1;
    public final static byte SC_PASSPORTHAND_ADDTYPE_CADRE = 2;
    public final static byte SC_PASSPORTHAND_ADDTYPE_DISPATCH = 3;
    public final static Map<Byte, String> SC_PASSPORTHAND_ADDTYPE_MAP = new LinkedHashMap();

    static {
        SC_PASSPORTHAND_ADDTYPE_MAP.put(SC_PASSPORTHAND_ADDTYPE_AUTO, "自动识别");
        SC_PASSPORTHAND_ADDTYPE_MAP.put(SC_PASSPORTHAND_ADDTYPE_CADRE, "从干部库中选择");
        SC_PASSPORTHAND_ADDTYPE_MAP.put(SC_PASSPORTHAND_ADDTYPE_DISPATCH, "从任免文件中提取");
    }

    // 状态，1 未交证件 2 已交证件  3 已撤销
    public final static byte SC_PASSPORTHAND_STATUS_UNHAND = 1;
    public final static byte SC_PASSPORTHAND_STATUS_HAND = 2;
    public final static byte SC_PASSPORTHAND_STATUS_ABOLISH = 3;
    public final static Map<Byte, String> SC_PASSPORTHAND_STATUS_MAP = new LinkedHashMap();

    static {
        SC_PASSPORTHAND_STATUS_MAP.put(SC_PASSPORTHAND_STATUS_UNHAND, "未交证件");
        SC_PASSPORTHAND_STATUS_MAP.put(SC_PASSPORTHAND_STATUS_HAND, "已交证件");
        SC_PASSPORTHAND_STATUS_MAP.put(SC_PASSPORTHAND_STATUS_ABOLISH, "已撤销");
    }

    /**
     * 个人有关事项
     */
    // 认定结果 1 如实填报 2 漏报 3 瞒报
    public final static byte SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_REAL = 1;
    public final static byte SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_FORGET = 2;
    public final static byte SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_NOT = 3;
    public final static Map<Byte, String> SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP = new LinkedHashMap();

    static {
        SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP.put(SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_REAL, "如实填报");
        SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP.put(SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_FORGET, "漏报");
        SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP.put(SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_NOT, "瞒报");
    }
}
