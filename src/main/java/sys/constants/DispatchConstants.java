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


    // 干部工作文件类别  1 干部选拔任用  2 干部管理监督  3 机关学院换届  4 干部队伍建设 5 干部考核工作 6  干部教育培训
    public static final byte DISPATCH_WORK_FILE_TYPE_XBRY = 1;
    public static final byte DISPATCH_WORK_FILE_TYPE_GLJD = 2;
    public static final byte DISPATCH_WORK_FILE_TYPE_XYHJ = 3;
    public static final byte DISPATCH_WORK_FILE_TYPE_DWJS = 4;
    public static final byte DISPATCH_WORK_FILE_TYPE_KHGZ = 5;
    public static final byte DISPATCH_WORK_FILE_TYPE_JYPX = 6;
    // 党建工作文件 类别 11 专题教育活动 12 基层党组织建设 13 党员队伍建设 14 党内民主建设
    public static final byte DISPATCH_WORK_FILE_TYPE_ZTJY = 11;
    public static final byte DISPATCH_WORK_FILE_TYPE_JCDJ = 12;
    public static final byte DISPATCH_WORK_FILE_TYPE_DYDW = 13;
    public static final byte DISPATCH_WORK_FILE_TYPE_DNMZ = 14;
    public final static Map<Byte, String> DISPATCH_WORK_FILE_TYPE_MAP = new HashMap();

    static {
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_XBRY, "干部选拔任用");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_GLJD, "干部管理监督");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_XYHJ, "机关学院换届");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_DWJS, "干部队伍建设");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_KHGZ, "干部考核工作");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_JYPX, "干部教育培训");

        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_ZTJY, "专题教育活动");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_JCDJ, "基层党组织建设");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_DYDW, "党员队伍建设");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_DNMZ, "党内民主建设");
    }
}
