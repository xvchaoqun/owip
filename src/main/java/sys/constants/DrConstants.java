package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class DrConstants {

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
}
