package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class ScConstants {

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
