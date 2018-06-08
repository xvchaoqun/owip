package sys.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemConstants {

    public final static Map<String, String> appKeyMap = new HashMap<>();
    static {

        appKeyMap.put("LXXT", "7507a3c61bf38d9f06d00c3f2fa2de58");
        appKeyMap.put("oa", "b887e286bf5d82b7b9712ed03d3e6e0e");
    }

    public static Map loginFailedResultMap(String message) {

        Map<String, Object> resultMap = new HashMap();
        resultMap.put("success", false);
        if ("SystemClosedException".equals(message)) {

            resultMap.put("msg", "参评人员测评未开启");
        } else if ("IncorrectCredentialsException".equals(message)) {
            resultMap.put("msg", "账号或密码错误");
        } else if ("UnknownAccountException".equals(message)) {
            resultMap.put("msg", "账号或密码错误");
        } else if ("IncorrectCaptchaException".equals(message)) {
            resultMap.put("msg", "验证码错误");
        } else if ("LockedAccountException".equals(message)) {
            resultMap.put("msg", "账号被锁定");
        } else if ("ExcessiveAttemptsException".equals(message)) {
            resultMap.put("msg", "登录过于频繁，请稍后再试");
        } else if ("InspectorFinishException".equals(message)) {
            resultMap.put("msg", "该账号已经测评完成");
        } else if ("SSOException".equals(message)) {
            resultMap.put("msg", "单点登录服务器错误，请稍后重试");
        } else {
            resultMap.put("msg", "系统错误");
        }
        return resultMap;
    }

    public final static Map<String, String> FOREIN_KEY_DEL_MSG_MAP = new LinkedHashMap<>();
    static {
        FOREIN_KEY_DEL_MSG_MAP.put("cet_course", "该课程已被使用， 不可删除。");
    }

    // 账号的角色字符串分隔符
    public static final String USER_ROLEIDS_SEPARTOR = ",";

    // 系统特殊的权限（与数据库对应）
    public static final String PERMISSION_CADREADMIN = "cadre:admin";
    public static final String PERMISSION_CADREADMINSELF = "cadre:adminSelf";

    // 系统配置字段：
    // 因私出国（境）申请说明
    public static final String HTML_FRAGMENT_APPLY_SELF_NOTE = "hf_apply_self_note";
    // 因私出国（境）审批说明
    public static final String HTML_FRAGMENT_APPLY_SELF_APPROVAL_NOTE = "hf_apply_self_approval_note";
    // 申请使用因私出国（境）证件说明
    public static final String HTML_FRAGMENT_PASSPORT_DRAW_NOTE = "hf_passport_draw_note";
    // 组织关系转入说明
    public static final String HTML_FRAGMENT_MEMBER_IN_NOTE_FRONT_TEACHER = "hf_member_in_note_front_teacher";
    public static final String HTML_FRAGMENT_MEMBER_IN_NOTE_FRONT_STUDENT = "hf_member_in_note_front_student";
    public static final String HTML_FRAGMENT_MEMBER_IN_NOTE_BACK = "hf_member_in_note_back";
    // 帮助文档
    public static final String HTML_FRAGMENT_HELP_DOC = "hf_help_doc";
/*	public final static Map<String, String> HTML_FRAGMENT_MAP = new LinkedHashMap<>();
    static {
		HTML_FRAGMENT_MAP.put(HTML_FRAGMENT_APPLY_SELF_NOTE, "因私出国境申请说明");
		HTML_FRAGMENT_MAP.put(HTML_FRAGMENT_APPLY_SELF_APPROVAL_NOTE, "因私出国境审批说明");
	}*/

    // 登录类型：1 网站 2 下次自动登录 2 CAS 3 移动设备
    public static final byte LOGIN_TYPE_NET = 1;
    public static final byte LOGIN_TYPE_NET_REMEBERME = 2;
    public static final byte LOGIN_TYPE_CAS = 3;
    public static final byte LOGIN_TYPE_MOBILE = 4;
    public static final byte LOGIN_TYPE_TRAIN_INSPECTOR = 5;
    public final static Map<Byte, String> LOGIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_NET, "网站");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_NET_REMEBERME, "下次自动登录");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_CAS, "单点登录");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_MOBILE, "移动设备");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_TRAIN_INSPECTOR, "评课账号登录");
    }

    // 系统附件类别
    public static final byte ATTACH_FILE_TYPE_IMAGE = 1;
    public static final byte ATTACH_FILE_TYPE_DOC = 2;
    public static final byte ATTACH_FILE_TYPE_EXCEL = 3;
    public static final byte ATTACH_FILE_TYPE_PDF = 4;
    public static final byte ATTACH_FILE_TYPE_VIDEO = 5;
    public final static Map<Byte, String> ATTACH_FILE_TYPE_MAP = new LinkedHashMap<>();

    static {
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_IMAGE, "图片");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_DOC, "WORD");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_EXCEL, "EXCEL");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_PDF, "PDF");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_VIDEO, "视频");
    }

    public static final String RESOURCE_TYPE_FUNCTION = "function";
    public static final String RESOURCE_TYPE_URL = "url";
    public static final String RESOURCE_TYPE_MENU = "menu";

    public static final byte AVAILABLE = 1;
    public static final byte UNAVAILABLE = 0;

    // 性别， 1男 2女 0未知
    public static final byte GENDER_MALE = 1;
    public static final byte GENDER_FEMALE = 2;
    public static final byte GENDER_UNKNOWN = 0;
    public final static Map<Byte, String> GENDER_MAP = new LinkedHashMap<>();

    static {
        GENDER_MAP.put(GENDER_MALE, "男");
        GENDER_MAP.put(GENDER_FEMALE, "女");
        GENDER_MAP.put(GENDER_UNKNOWN, "未知");
    }

    //单位状态，1正在运转单位、2历史单位
    public static final byte UNIT_STATUS_RUN = 1;
    public static final byte UNIT_STATUS_HISTORY = 2;

    // 单位类型附加属性
    public final static String UNIT_TYPE_ATTR_XY = "xy";
    public final static String UNIT_TYPE_ATTR_JG = "jg";
    public final static String UNIT_TYPE_ATTR_FS = "fs";
    public final static Map<String, String> UNIT_TYPE_ATTR_MAP = new LinkedHashMap<>();

    static {
        UNIT_TYPE_ATTR_MAP.put(UNIT_TYPE_ATTR_XY, "学部、院、系所");
        UNIT_TYPE_ATTR_MAP.put(UNIT_TYPE_ATTR_JG, "机关部处及直属、教辅单位");
        UNIT_TYPE_ATTR_MAP.put(UNIT_TYPE_ATTR_FS, "附属单位");
    }


    // 账号来源 0 后台创建 1人事库、2本科生库 3 研究生库
    public final static byte USER_REG_STATUS_APPLY = 0;
    public final static byte USER_REG_STATUS_DENY = -1;
    public final static byte USER_REG_STATUS_PASS = 1;
    public final static Map<Byte, String> USER_REG_STATUS_MAP = new LinkedHashMap();

    static {
        USER_REG_STATUS_MAP.put(USER_REG_STATUS_APPLY, "申请");
        USER_REG_STATUS_MAP.put(USER_REG_STATUS_DENY, "申请不通过");
        USER_REG_STATUS_MAP.put(USER_REG_STATUS_PASS, "申请通过");
    }

    // 账号类别，1教职工 2本科生 3研究生
    public final static byte USER_TYPE_JZG = 1;
    public final static byte USER_TYPE_BKS = 2;
    public final static byte USER_TYPE_YJS = 3;
    public final static Map<Byte, String> USER_TYPE_MAP = new LinkedHashMap();

    static {
        USER_TYPE_MAP.put(USER_TYPE_JZG, "教职工");
        USER_TYPE_MAP.put(USER_TYPE_BKS, "本科生");
        USER_TYPE_MAP.put(USER_TYPE_YJS, "研究生");
    }

    // 同步类型，1人事库 2研究库 3本科生库 4教职工党员出国信息库 5 离退休费 6教职工工资
    public final static byte SYNC_TYPE_JZG = 1;
    public final static byte SYNC_TYPE_BKS = 2;
    public final static byte SYNC_TYPE_YJS = 3;
    public final static byte SYNC_TYPE_ABROAD = 4;
    public final static byte SYNC_TYPE_RETIRE_SALARY = 5;
    public final static byte SYNC_TYPE_JZG_SALARY = 6;
    public final static Map<Byte, String> SYNC_TYPE_MAP = new LinkedHashMap();

    static {
        SYNC_TYPE_MAP.put(SYNC_TYPE_JZG, "人事库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_BKS, "本科生库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_YJS, "研究生库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_ABROAD, "教职工党员出国境信息库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_RETIRE_SALARY, "离退休费");
        SYNC_TYPE_MAP.put(SYNC_TYPE_JZG_SALARY, "教职工工资");
    }

    // 账号来源 0 后台创建 1人事库、2本科生库 3 研究生库
    public final static byte USER_SOURCE_ADMIN = 0;
    public final static byte USER_SOURCE_JZG = 1;
    public final static byte USER_SOURCE_BKS = 2;
    public final static byte USER_SOURCE_YJS = 3;
    public final static byte USER_SOURCE_REG = 4;
    public final static Map<Byte, String> USER_SOURCE_MAP = new LinkedHashMap();

    static {
        USER_SOURCE_MAP.put(USER_SOURCE_ADMIN, "后台创建");
        USER_SOURCE_MAP.put(USER_SOURCE_JZG, "人事库");
        USER_SOURCE_MAP.put(USER_SOURCE_BKS, "本科生库");
        USER_SOURCE_MAP.put(USER_SOURCE_YJS, "研究生库");
        USER_SOURCE_MAP.put(USER_SOURCE_REG, "用户注册");
    }


    // 打印类别
    public final static byte JASPER_PRINT_TYPE_INSIDE = 1;
    public final static byte JASPER_PRINT_TYPE_OUTSIDE = 2;
    public final static byte JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD = 3;
    public final static byte JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL = 4;
    public final static Map<Byte, String> JASPER_PRINT_TYPE_MAP = new LinkedHashMap<>();

    static {
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_INSIDE, "京内打印");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_OUTSIDE, "京外套打");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD, "出国境组织关系暂留审批表");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL, "国内组织关系暂留审批表");
    }

    public final static Map<Byte, String> PIE_COLOR_MAP = new LinkedHashMap<>();

    static {
        PIE_COLOR_MAP.put((byte) 0, "#999");
        PIE_COLOR_MAP.put((byte) 1, "#68BC31");
        PIE_COLOR_MAP.put((byte) 2, "#2091CF");
        PIE_COLOR_MAP.put((byte) 3, "#AF4E96");
        PIE_COLOR_MAP.put((byte) 4, "#DA5430");
        PIE_COLOR_MAP.put((byte) 5, "#FEE074");
    }

    // 信息修改申请
    public final static byte RECORD_STATUS_FORMAL = 0; // 正式记录
    public final static byte RECORD_STATUS_MODIFY = 1; // 修改记录
    public final static byte RECORD_STATUS_APPROVAL = 2; // 已审核的修改记录

    // 短信接收人状态
    public final static byte SHORT_MSG_RECEIVER_STATUS_NORMAL = 0;
    public final static byte SHORT_MSG_RECEIVER_STATUS_DELETE = 1;
    public final static Map<Byte, String> SHORT_MSG_RECEIVER_STATUS_MAP = new LinkedHashMap<>();

    static {
        SHORT_MSG_RECEIVER_STATUS_MAP.put(SHORT_MSG_RECEIVER_STATUS_NORMAL, "正常接收");
        SHORT_MSG_RECEIVER_STATUS_MAP.put(SHORT_MSG_RECEIVER_STATUS_DELETE, "已失效");
    }

    // 短信关联类型
    public final static byte SHORT_MSG_RELATE_TYPE_CONTENT_TPL = 1;
    public final static byte SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL = 2;
    public final static byte SHORT_MSG_RELATE_TYPE_SHORT_PCS = 3; // 党代会
    public final static byte SHORT_MSG_RELATE_TYPE_SHORT_OA = 4; // 协同办公
    public final static byte SHORT_MSG_RELATE_TYPE_SHORT_CET = 5; // 干部教育培训
    public final static Map<Byte, String> SHORT_MSG_RELATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_CONTENT_TPL, "短信模板");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL, "定向短信");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_PCS, "党代会");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_OA, "协同办公");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_CET, "干部教育培训");
    }

    // 审批记录类型
    public final static byte SYS_APPROVAL_LOG_TYPE_APPLYSELF = 1; // 因私出国
    public final static byte SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT = 2; // 干部招聘-报名审核
    public final static byte SYS_APPROVAL_LOG_TYPE_PMD_MEMBER = 3; // 党费收缴
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_TRAINEE = 4; // 干部教育培训-参训人
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ = 5; // 干部教育培训-培训对象
    public final static byte SYS_APPROVAL_LOG_TYPE_CLA_APPLY = 6; // 干部请假审批人员

    public final static Map<Byte, String> SYS_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_APPLYSELF, "因私出国");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT, "干部招聘");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_PMD_MEMBER, "党费收缴");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_TRAINEE, "参训人");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ, "培训对象");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CLA_APPLY, "干部请假");
    }

    // 操作人类别, 0本人 1 干部管理员 2 因私审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_SELF = 0; // 本人
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_CADRE = 1; // 干部管理员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_APPLYSELF = 2; // 因私审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_CLA_APPLY = 3; // 干部请假审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_NOT_SELF = 5;// 他人操作
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_ADMIN = 10;// 后台操作
    public final static Map<Byte, String> SYS_APPROVAL_LOG_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_SELF, "本人");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_CADRE, "干部管理员");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_APPLYSELF, "因私审批人员");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_CLA_APPLY, "干部请假审批人员");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_ADMIN, "后台操作");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_NOT_SELF, "他人操作");
    }

    // 党员各类申请的审批结果
    public final static byte SYS_APPROVAL_LOG_STATUS_DENY = 0;
    public final static byte SYS_APPROVAL_LOG_STATUS_PASS = 1;
    public final static byte SYS_APPROVAL_LOG_STATUS_BACK = 2;
    public final static byte SYS_APPROVAL_LOG_STATUS_NONEED = 3; // 直接通过，不需要审核
    public final static Map<Byte, String> SYS_APPROVAL_LOG_STATUS_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_STATUS_MAP.put(SYS_APPROVAL_LOG_STATUS_DENY, "不通过");
        SYS_APPROVAL_LOG_STATUS_MAP.put(SYS_APPROVAL_LOG_STATUS_PASS, "通过");
        SYS_APPROVAL_LOG_STATUS_MAP.put(SYS_APPROVAL_LOG_STATUS_BACK, "打回");
        SYS_APPROVAL_LOG_STATUS_MAP.put(SYS_APPROVAL_LOG_STATUS_NONEED, "-");
    }
}
