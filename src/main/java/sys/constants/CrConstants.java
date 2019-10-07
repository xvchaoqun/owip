package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrConstants {
    
    // 干部招聘 岗位状态，0 未发布、1正在招聘、2完成招聘、3 已作废
    public final static byte CR_INFO_STATUS_INIT = 0;
    public final static byte CR_INFO_STATUS_NORMAL = 1;
    public final static byte CR_INFO_STATUS_FINISH = 2;
    public final static byte CR_INFO_STATUS_ABOLISH = 4;
    public static Map<Byte, String> CR_INFO_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CR_INFO_STATUS_MAP.put(CR_INFO_STATUS_INIT, "未发布");
        CR_INFO_STATUS_MAP.put(CR_INFO_STATUS_NORMAL, "正在招聘");
        CR_INFO_STATUS_MAP.put(CR_INFO_STATUS_FINISH, "完成招聘");
        CR_INFO_STATUS_MAP.put(CR_INFO_STATUS_ABOLISH, "已作废");
    }
    
    // 干部招聘 招聘岗位规则类别
    public final static byte CR_POST_RULE_TYPE_XL = 1;
    public final static byte CR_POST_RULE_TYPE_RZNL = 2;
    public final static byte CR_POST_RULE_TYPE_ZZMM = 3;
    public final static byte CR_POST_RULE_TYPE_ZZJS = 4;
    public final static byte CR_POST_RULE_TYPE_GLGW = 5;
    public final static byte CR_POST_RULE_TYPE_ZCJ = 6;
    public final static byte CR_POST_RULE_TYPE_FCJ = 7;
    public final static byte CR_POST_RULE_TYPE_GZ = 8;
    public final static byte CR_POST_RULE_TYPE_BXGZ = 9;
    public final static byte CR_POST_RULE_TYPE_BZLB = 10;
    public static Map<Byte, String> CR_POST_RULE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_XL, "学历");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_RZNL, "任职最高年龄");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_ZZMM, "政治面貌和党龄");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_ZZJS, "专业技术职务及任职年限");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_GLGW, "管理岗位等级及任职年限");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_ZCJ, "正处级任职年限");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_FCJ, "副处级任职年限");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_GZ, "参加工作年限");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_BXGZ, "本校工作年限");
        CR_POST_RULE_TYPE_MAP.put(CR_POST_RULE_TYPE_BZLB, "编制类别");
    }

    // 招聘岗位 报名人员 资格审核状态，0 待审核 1 通过 2 未通过
    public final static byte CR_REQUIRE_CHECK_STATUS_INIT = 0;
    public final static byte CR_REQUIRE_CHECK_STATUS_PASS = 1;
    public final static byte CR_REQUIRE_CHECK_STATUS_UNPASS = 2;
    public static Map<Byte, String> CR_REQUIRE_CHECK_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CR_REQUIRE_CHECK_STATUS_MAP.put(CR_REQUIRE_CHECK_STATUS_INIT, "待审核");
        CR_REQUIRE_CHECK_STATUS_MAP.put(CR_REQUIRE_CHECK_STATUS_PASS, "通过");
        CR_REQUIRE_CHECK_STATUS_MAP.put(CR_REQUIRE_CHECK_STATUS_UNPASS, "未通过");
    }
}
