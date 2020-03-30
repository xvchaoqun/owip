package sys.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lm on 2018/4/14.
 */
public class DispatchConstants {

    // 发文  文件属性，1 干部任免文件 2 内设机构调整文件 3 组织机构调整文件
    public static final byte DISPATCH_CATEGORY_CADER = 1;
    public static final byte DISPATCH_CATEGORY_UNIT = 2;
    public static final byte DISPATCH_CATEGORY_PARTY = 3;
    public final static Map<Byte, String> DISPATCH_CATEGORY_MAP = new HashMap();

    static {
        DISPATCH_CATEGORY_MAP.put(DISPATCH_CATEGORY_CADER, "干部任免文件");
        DISPATCH_CATEGORY_MAP.put(DISPATCH_CATEGORY_UNIT, "内设机构调整文件");
        DISPATCH_CATEGORY_MAP.put(DISPATCH_CATEGORY_PARTY, "组织机构调整文件");
    }

    // 干部任免类别
    public static final byte DISPATCH_CADRE_TYPE_APPOINT = 1;
    public static final byte DISPATCH_CADRE_TYPE_DISMISS = 2;
    public final static Map<Byte, String> DISPATCH_CADRE_TYPE_MAP = new HashMap();

    static {
        DISPATCH_CADRE_TYPE_MAP.put(DISPATCH_CADRE_TYPE_APPOINT, "任命");
        DISPATCH_CADRE_TYPE_MAP.put(DISPATCH_CADRE_TYPE_DISMISS, "免职");
    }

    // 干部任免关联模块
    public static final byte DISPATCH_CADRE_RELATE_TYPE_WORK = 1;
    public static final byte DISPATCH_CADRE_RELATE_TYPE_POST = 2;
    public final static Map<Byte, String> DISPATCH_CADRE_RELATE_TYPE_MAP = new HashMap();

    static {
        DISPATCH_CADRE_RELATE_TYPE_MAP.put(DISPATCH_CADRE_RELATE_TYPE_WORK, "工作经历");
        DISPATCH_CADRE_RELATE_TYPE_MAP.put(DISPATCH_CADRE_RELATE_TYPE_POST, "任职情况(主职/兼职)");
    }
}
