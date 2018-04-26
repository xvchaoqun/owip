package sys.constants;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lm on 2018/2/8.
 */
public class CrsConstants {

    // 干部招聘，专家组成员类别
    public final static byte CRS_EXPERT_STATUS_NOW = 1;
    public final static byte CRS_EXPERT_STATUS_HISTORY = 2;
    public final static byte CRS_EXPERT_STATUS_DELETE = 3;
    public final static Map<Byte, String> CRS_EXPERT_STATUS_MAP = new LinkedHashMap<>();

    static {
        CRS_EXPERT_STATUS_MAP.put(CRS_EXPERT_STATUS_NOW, "专家组现有成员");
        CRS_EXPERT_STATUS_MAP.put(CRS_EXPERT_STATUS_HISTORY, "专家组过去成员");
        CRS_EXPERT_STATUS_MAP.put(CRS_EXPERT_STATUS_DELETE, "已删除");
    }

    // 干部招聘，会议记录文件类型 1 照片、2 录音
    public final static byte CRS_POST_FILE_TYPE_PIC = 1;
    public final static byte CRS_POST_FILE_TYPE_AUDIO = 2;
    public final static byte CRS_POST_FILE_TYPE_VIDEO = 3;
    public final static Map<Byte, String> CRS_POST_FILE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CRS_POST_FILE_TYPE_MAP.put(CRS_POST_FILE_TYPE_PIC, "照片");
        CRS_POST_FILE_TYPE_MAP.put(CRS_POST_FILE_TYPE_AUDIO, "录音");
        CRS_POST_FILE_TYPE_MAP.put(CRS_POST_FILE_TYPE_VIDEO, "视频");
    }

    // 干部招聘 岗位状态，1正在招聘、2完成招聘、3已删除
    public final static byte CRS_POST_STATUS_NORMAL = 1;
    public final static byte CRS_POST_STATUS_FINISH = 2;
    public final static byte CRS_POST_STATUS_DELETE = 3;
    public static Map<Byte, String> CRS_POST_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_POST_STATUS_MAP.put(CRS_POST_STATUS_NORMAL, "正在招聘");
        CRS_POST_STATUS_MAP.put(CRS_POST_STATUS_FINISH, "完成招聘");
        CRS_POST_STATUS_MAP.put(CRS_POST_STATUS_DELETE, "已删除");
    }

    // 干部招聘 招聘类型
    public final static byte CRS_POST_TYPE_COMPETE = 1;
    public final static byte CRS_POST_TYPE_PUBLIC = 2;
    public static Map<Byte, String> CRS_POST_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_POST_TYPE_MAP.put(CRS_POST_TYPE_COMPETE, "竞争上岗");
        CRS_POST_TYPE_MAP.put(CRS_POST_TYPE_PUBLIC, "公开招聘");
    }

    // 干部招聘 招聘岗位规则类别
    public final static byte CRS_POST_RULE_TYPE_XL = 1;
    public final static byte CRS_POST_RULE_TYPE_RZNL = 2;
    public final static byte CRS_POST_RULE_TYPE_ZZMM = 3;
    public final static byte CRS_POST_RULE_TYPE_ZZJS = 4;
    public final static byte CRS_POST_RULE_TYPE_GLGW = 5;
    public final static byte CRS_POST_RULE_TYPE_ZCJ = 6;
    public final static byte CRS_POST_RULE_TYPE_FCJ = 7;
    public final static byte CRS_POST_RULE_TYPE_GZ = 8;
    public final static byte CRS_POST_RULE_TYPE_BXGZ = 9;
    public static Map<Byte, String> CRS_POST_RULE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_XL, "学历");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_RZNL, "任职最高年龄");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_ZZMM, "政治面貌和党龄");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_ZZJS, "专业技术职务及任职年限");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_GLGW, "管理岗位等级及任职年限");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_ZCJ, "正处级任职年限");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_FCJ, "副处级任职年限");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_GZ, "参加工作年限");
        CRS_POST_RULE_TYPE_MAP.put(CRS_POST_RULE_TYPE_BXGZ, "本校工作年限");
    }

    // 干部招聘 岗位发布状态，0 未发布 1 已发布  2 取消发布
    public final static byte CRS_POST_PUB_STATUS_UNPUBLISHED = 0;
    public final static byte CRS_POST_PUB_STATUS_PUBLISHED = 1;
    public final static byte CRS_POST_PUB_STATUS_CANCEL = 2;
    public static Map<Byte, String> CRS_POST_PUB_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_POST_PUB_STATUS_MAP.put(CRS_POST_PUB_STATUS_UNPUBLISHED, "未发布");
        CRS_POST_PUB_STATUS_MAP.put(CRS_POST_PUB_STATUS_PUBLISHED, "已发布");
        CRS_POST_PUB_STATUS_MAP.put(CRS_POST_PUB_STATUS_CANCEL, "取消发布");
    }

    // 干部招聘 岗位报名状态，0 根据报名时间而定 1 强制开启、2 强制关闭、3 暂停报名
    public final static byte CRS_POST_ENROLL_STATUS_DEFAULT = 0;
    public final static byte CRS_POST_ENROLL_STATUS_OPEN = 1;
    public final static byte CRS_POST_ENROLL_STATUS_CLOSED = 2;
    public final static byte CRS_POST_ENROLL_STATUS_PAUSE = 3;
    public static Map<Byte, String> CRS_POST_ENROLL_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_DEFAULT, "根据报名时间而定");
        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_OPEN, "正在报名");
        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_CLOSED, "报名结束");
        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_PAUSE, "暂停报名");
    }

    // 干部招聘 岗位专家角色， 1 组长 2 校领导 3 成员
    public final static byte CRS_POST_EXPERT_ROLE_HEAD = 1;
    public final static byte CRS_POST_EXPERT_ROLE_LEADER = 2;
    public final static byte CRS_POST_EXPERT_ROLE_MEMBER = 3;
    public static Map<Byte, String> CRS_POST_EXPERT_ROLE_MAP = new LinkedHashMap<Byte, String>();

    static {
        CRS_POST_EXPERT_ROLE_MAP.put(CRS_POST_EXPERT_ROLE_HEAD, "组长");
        CRS_POST_EXPERT_ROLE_MAP.put(CRS_POST_EXPERT_ROLE_LEADER, "校领导");
        CRS_POST_EXPERT_ROLE_MAP.put(CRS_POST_EXPERT_ROLE_MEMBER, "成员");
    }

    // 干部招聘 招聘条件通用模板 类别
    public final static byte CRS_TEMPLATE_TYPE_BASE = 1;
    public final static byte CRS_TEMPLATE_TYPE_POST = 2;
    public final static byte CRS_TEMPLATE_TYPE_MEETINGNOTICE = 3;
    public static Map<Byte, String> CRS_TEMPLATE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_TEMPLATE_TYPE_MAP.put(CRS_TEMPLATE_TYPE_BASE, "基本条件");
        CRS_TEMPLATE_TYPE_MAP.put(CRS_TEMPLATE_TYPE_POST, "任职资格");
        CRS_TEMPLATE_TYPE_MAP.put(CRS_TEMPLATE_TYPE_MEETINGNOTICE, "招聘会公告");
    }

    // 招聘岗位 报名状态，0 暂存 1 已提交 2 已删除
    public final static byte CRS_APPLY_RULE_STATUS_UNPUBLISH = 0;
    public final static byte CRS_APPLY_RULE_STATUS_PUBLISH = 1;
    public final static byte CRS_APPLY_RULE_STATUS_DELETE = 2;
    public static Map<Byte, String> CRS_APPLY_RULE_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {
        CRS_APPLY_RULE_STATUS_MAP.put(CRS_APPLY_RULE_STATUS_UNPUBLISH, "未发布");
        CRS_APPLY_RULE_STATUS_MAP.put(CRS_APPLY_RULE_STATUS_PUBLISH, "已发布");
        CRS_APPLY_RULE_STATUS_MAP.put(CRS_APPLY_RULE_STATUS_DELETE, "已删除");
    }

    // 招聘岗位 报名状态，0 暂存 1 已提交 2 已删除
    public final static byte CRS_APPLICANT_STATUS_SAVE = 0;
    public final static byte CRS_APPLICANT_STATUS_SUBMIT = 1;
    public final static byte CRS_APPLICANT_STATUS_DELETE = 2;
    public static Map<Byte, String> CRS_APPLICANT_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {
        CRS_APPLICANT_STATUS_MAP.put(CRS_APPLICANT_STATUS_SAVE, "暂存");
        CRS_APPLICANT_STATUS_MAP.put(CRS_APPLICANT_STATUS_SUBMIT, "已提交");
        CRS_APPLICANT_STATUS_MAP.put(CRS_APPLICANT_STATUS_DELETE, "已删除");
    }

    // 招聘岗位 报名人员 信息审核状态，0 待审核 1 通过 2 未通过
    public final static byte CRS_APPLICANT_INFO_CHECK_STATUS_INIT = 0;
    public final static byte CRS_APPLICANT_INFO_CHECK_STATUS_PASS = 1;
    public final static byte CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS = 2;
    public static Map<Byte, String> CRS_APPLICANT_INFO_CHECK_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_APPLICANT_INFO_CHECK_STATUS_MAP.put(CRS_APPLICANT_INFO_CHECK_STATUS_INIT, "待审核");
        CRS_APPLICANT_INFO_CHECK_STATUS_MAP.put(CRS_APPLICANT_INFO_CHECK_STATUS_PASS, "审核通过");
        CRS_APPLICANT_INFO_CHECK_STATUS_MAP.put(CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS, "审核未通过");
    }

    // 招聘岗位 报名人员 资格审核状态，0 待审核 1 通过 2 未通过
    public final static byte CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT = 0;
    public final static byte CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS = 1;
    public final static byte CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS = 2;
    public static Map<Byte, String> CRS_APPLICANT_REQUIRE_CHECK_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_APPLICANT_REQUIRE_CHECK_STATUS_MAP.put(CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT, "待审核");
        CRS_APPLICANT_REQUIRE_CHECK_STATUS_MAP.put(CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS, "通过");
        CRS_APPLICANT_REQUIRE_CHECK_STATUS_MAP.put(CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS, "未通过");
    }

    public final static Set<Byte> CRS_EXPERT_CADRE_STATUS_SET = new HashSet<>(); // 干部招聘专家组要求的干部状态
    static {
        CRS_EXPERT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_MIDDLE);
        CRS_EXPERT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER);
    }

    // 补报人员 状态，0 未启动补报 1 正在补报 2 关闭补报窗口 3 完成补报
    public final static byte CRS_APPLY_USER_STATUS_INIT = 0;
    public final static byte CRS_APPLY_USER_STATUS_OPEN = 1;
    public final static byte CRS_APPLY_USER_STATUS_CLOSED = 2;
    public final static byte CRS_APPLY_USER_STATUS_FINISH = 3;
    public final static  Map<Byte, String> CRS_APPLY_USER_STATUS_AMP = new LinkedHashMap<Byte, String>();
    static {
        CRS_APPLY_USER_STATUS_AMP.put(CRS_APPLY_USER_STATUS_INIT, "未启动补报");
        CRS_APPLY_USER_STATUS_AMP.put(CRS_APPLY_USER_STATUS_OPEN, "正在补报");
        CRS_APPLY_USER_STATUS_AMP.put(CRS_APPLY_USER_STATUS_CLOSED, "关闭补报窗口");
        CRS_APPLY_USER_STATUS_AMP.put(CRS_APPLY_USER_STATUS_FINISH, "完成补报");
    }

}
