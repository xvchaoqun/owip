package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/4/14.
 */
public class CrpConstants {

    // 干部考核管理，干部挂职锻炼分类，  1 校外挂职锻炼 2 校内挂职锻炼 3 外单位到本校挂职
    public final static byte CRP_RECORD_TYPE_OUT = 1;
    public final static byte CRP_RECORD_TYPE_IN = 2;
    public final static byte CRP_RECORD_TYPE_TRANSFER = 3;
    public final static Map<Byte, String> CRP_RECORD_TYPE_MAP = new LinkedHashMap<>();

    static {
        CRP_RECORD_TYPE_MAP.put(CRP_RECORD_TYPE_OUT, "校外挂职");
        CRP_RECORD_TYPE_MAP.put(CRP_RECORD_TYPE_IN, "校内挂职");
        CRP_RECORD_TYPE_MAP.put(CRP_RECORD_TYPE_TRANSFER, "外单位到本校挂职");
    }
}
