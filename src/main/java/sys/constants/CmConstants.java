package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CmConstants {

    // 委员库类别
    public final static byte CM_MEMBER_TYPE_DW = 1;
    public final static byte CM_MEMBER_TYPE_JW = 2;
    public final static byte CM_MEMBER_TYPE_CW = 3;
    public final static Map<Byte, String> CM_MEMBER_TYPE_MAP = new LinkedHashMap<>();

    static {
        CM_MEMBER_TYPE_MAP.put(CM_MEMBER_TYPE_DW, "党委委员");
        CM_MEMBER_TYPE_MAP.put(CM_MEMBER_TYPE_JW, "纪委委员");
        CM_MEMBER_TYPE_MAP.put(CM_MEMBER_TYPE_CW, "党委常委");
    }
}
