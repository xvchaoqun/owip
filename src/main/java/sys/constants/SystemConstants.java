package sys.constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

    /** 系统日志分类 **/
    public static final int LOG_ADMIN = 10; // 系统管理
    public static final int LOG_PARTY = 20; // 党建
    public static final int LOG_USER = 30; // 用户操作
    public static final int LOG_MEMBER = 40; // 党员信息
    public static final int LOG_CADRE = 50; // 干部信息
    //public static final int LOG_MEMBER = "mt_log_member_apply"; // 申请入党
    public static final int LOG_ABROAD = 60; // 因私出国
    public static final int LOG_PCS = 70; // 党代会
    public static final int LOG_OA = 80; // 协同办公
    public static final int LOG_PMD = 90; // 党费收缴
    public static final int LOG_CPC = 100; // 干部职数
    public static final int LOG_CRS = 110; // 干部竞争上岗
    public static final int LOG_SC_MATTER = 120; // 干部选拔任用-个人有关事项
    public static final int LOG_SC_LETTER = 130; // 干部选拔任用-纪委函询管理
    public static final int LOG_SC_GROUP = 140; // 干部选拔任用-干部小组会议题
    public static final int LOG_SC_COMMITTEE = 150; // 干部选拔任用-党委常委会议题
    public static final int LOG_SC_PUBLIC = 160; // 干部选拔任用-干部任前公示
    public static final int LOG_SC_DISPATCH = 170; // 干部选拔任用-文件起草签发
    public static final int LOG_SC_AD = 180; // 干部选拔任用-干部任免审批表

    public static final int LOG_CET = 190; // 干部教育培训

    public final static Map<Integer, String> LOG_MAP = new LinkedHashMap<>();
    static {
        LOG_MAP.put(LOG_ADMIN, "系统管理");
        LOG_MAP.put(LOG_PARTY, "党建");
        LOG_MAP.put(LOG_USER, "用户操作");
        LOG_MAP.put(LOG_MEMBER, "党员信息");
        LOG_MAP.put(LOG_CADRE, "干部信息");

        LOG_MAP.put(LOG_ABROAD, "因私出国");
        LOG_MAP.put(LOG_PCS, "党代会");
        LOG_MAP.put(LOG_OA, "协同办公");
        LOG_MAP.put(LOG_PMD, "党费收缴");
        LOG_MAP.put(LOG_CPC, "干部职数");

        LOG_MAP.put(LOG_CRS, "干部竞争上岗");
        LOG_MAP.put(LOG_SC_MATTER, "个人有关事项");
        LOG_MAP.put(LOG_SC_LETTER, "纪委函询管理");
        LOG_MAP.put(LOG_SC_GROUP, "干部小组会议题");
        LOG_MAP.put(LOG_SC_COMMITTEE, "党委常委会议题");

        LOG_MAP.put(LOG_SC_PUBLIC, "干部任前公示");
        LOG_MAP.put(LOG_SC_DISPATCH, "文件起草签发");
        LOG_MAP.put(LOG_SC_AD, "干部任免审批表");
        LOG_MAP.put(LOG_CET, "干部教育培训");
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

    // 权限开通申请状态，0申请 1本人撤销 2 管理员撤回 3通过；
    public final static byte ENTER_APPLY_STATUS_APPLY = 0;
    public final static byte ENTER_APPLY_STATUS_SELF_ABORT = 1;
    public final static byte ENTER_APPLY_STATUS_ADMIN_ABORT = 2;
    public final static byte ENTER_APPLY_STATUS_PASS = 3;
    public final static Map<Byte, String> ENTER_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_APPLY, "申请");
        ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_SELF_ABORT, "本人撤销");
        ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_ADMIN_ABORT, "管理员撤回");
        ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_PASS, "通过");
    }

    // 权限开通申请类别，1申请入党 2 留学归国申请 3转入申请  4 流入申请
    public final static byte ENTER_APPLY_TYPE_MEMBERAPPLY = 1;
    public final static byte ENTER_APPLY_TYPE_RETURN = 2;
    public final static byte ENTER_APPLY_TYPE_MEMBERIN = 3;
    public final static byte ENTER_APPLY_TYPE_MEMBERINFLOW = 4;
    public final static Map<Byte, String> ENTER_APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_MEMBERAPPLY, "申请入党");
        ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_RETURN, "留学归国党员申请");
        ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_MEMBERIN, "组织关系转入");
        ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_MEMBERINFLOW, "流入党员申请");
    }

    // 申请入党类型
    public final static byte APPLY_TYPE_TEACHER = 1; // 教职工
    public final static byte APPLY_TYPE_STU = 2; // 学生
    public final static Map<Byte, String> APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        APPLY_TYPE_MAP.put(APPLY_TYPE_STU, "学生");
        APPLY_TYPE_MAP.put(APPLY_TYPE_TEACHER, "教职工");
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

    // 申请入党阶段
    //0不通过 1申请  2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书 6预备党员 7正式党员
    public final static byte APPLY_STAGE_OUT = -2; // 已转出的申请
    public final static byte APPLY_STAGE_DENY = -1; // 未通过
    public final static byte APPLY_STAGE_INIT = 0; // 申请
    public final static byte APPLY_STAGE_PASS = 1; // 通过
    public final static byte APPLY_STAGE_ACTIVE = 2; // 积极分子
    public final static byte APPLY_STAGE_CANDIDATE = 3; // 发展对象
    public final static byte APPLY_STAGE_PLAN = 4; // 列入发展计划
    public final static byte APPLY_STAGE_DRAW = 5; // 领取志愿书
    public final static byte APPLY_STAGE_GROW = 6; // 预备党员
    public final static byte APPLY_STAGE_POSITIVE = 7; // 正式党员
    public final static Map<Byte, String> APPLY_STAGE_MAP = new LinkedHashMap<>();

    static {
        APPLY_STAGE_MAP.put(APPLY_STAGE_DENY, "未通过");
        APPLY_STAGE_MAP.put(APPLY_STAGE_INIT, "申请");
        APPLY_STAGE_MAP.put(APPLY_STAGE_ACTIVE, "积极分子");
        APPLY_STAGE_MAP.put(APPLY_STAGE_CANDIDATE, "发展对象");
        APPLY_STAGE_MAP.put(APPLY_STAGE_PLAN, "列入发展计划");
        APPLY_STAGE_MAP.put(APPLY_STAGE_DRAW, "领取志愿书");
        APPLY_STAGE_MAP.put(APPLY_STAGE_GROW, "预备党员");
        APPLY_STAGE_MAP.put(APPLY_STAGE_POSITIVE, "正式党员");
    }

    // 申请入党审核状态
    public final static byte APPLY_STATUS_UNCHECKED = 0; // 未审核
    public final static byte APPLY_STATUS_CHECKED = 1; // 已审核
    public final static byte APPLY_STATUS_OD_CHECKED = 2; // 组织部已审核，成为预备党员和正式党员时


    // 党员退休审核状态
    public final static byte RETIRE_APPLY_STATUS_UNCHECKED = 0; // 未审核
    public final static byte RETIRE_APPLY_STATUS_CHECKED = 1; // 已审核

    // 组织关系状态
    public final static byte OR_STATUS_OUT = 1;
    public final static byte OR_STATUS_NOT_OUT = 2;
    public final static Map<Byte, String> OR_STATUS_MAP = new LinkedHashMap<>();

    static {
        OR_STATUS_MAP.put(OR_STATUS_OUT, "已转出");
        OR_STATUS_MAP.put(OR_STATUS_NOT_OUT, "未转出");
    }

    // 党员各类申请的审批记录类型 1入党申请 2 留学归国申请 3 组织关系转入 4 流入党员申请 5 流出党员申请 6 组织关系转出 7 留学归国党员
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY = 1;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD = 2;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_IN = 3;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW = 4;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW = 5;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT = 6;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN = 7;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER = 8;
    public final static byte APPLY_APPROVAL_LOG_TYPE_USER_REG = 10;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT = 11;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT = 12;
    public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY = 13;
    public final static Map<Byte, String> APPLY_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();

    static {
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "申请入党");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD, "留学归国申请");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, "组织关系转入");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, "流入党员申请");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW, "流出党员申请");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, "组织关系转出");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, "留学归国党员申请");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, "校内组织关系转接");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_USER_REG, "用户注册");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, "党员出党");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT, "流入党员转出");
        APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, "党员出国（境）申请组织关系暂留");
    }

    // 党员各类申请的操作人类别
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_SELF = 0; // 本人
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_BRANCH = 1; // 党支部
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_PARTY = 2; // 分党委
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_OW = 3; // 组织部
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY = 4; // 转出分党委
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY = 5; // 转入分党委
    public final static byte APPLY_APPROVAL_LOG_USER_TYPE_ADMIN = 10;// 后台操作
    public final static Map<Byte, String> APPLY_APPROVAL_LOG_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_SELF, "本人");
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_BRANCH, "党支部");
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_PARTY, "分党委");
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_OW, "组织部");
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY, "转出分党委");
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY, "转入分党委");
        APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_ADMIN, "后台操作");
    }

    // 党员各类申请的审批结果
    public final static byte APPLY_APPROVAL_LOG_STATUS_DENY = 0;
    public final static byte APPLY_APPROVAL_LOG_STATUS_PASS = 1;
    public final static byte APPLY_APPROVAL_LOG_STATUS_BACK = 2;
    public final static byte APPLY_APPROVAL_LOG_STATUS_NONEED = 3; // 直接通过，不需要审核
    public final static Map<Byte, String> APPLY_APPROVAL_LOG_STATUS_MAP = new LinkedHashMap<>();

    static {
        APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_DENY, "不通过");
        APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_PASS, "通过");
        APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_BACK, "打回");
        APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_NONEED, "-");
    }

    // 干部库类别
    public final static byte CADRE_STATUS_INSPECT = 2;
    public final static byte CADRE_STATUS_RESERVE = 5;
    public final static byte CADRE_STATUS_MIDDLE_LEAVE = 3;
    public final static byte CADRE_STATUS_MIDDLE = 1;
    public final static byte CADRE_STATUS_LEADER_LEAVE = 4;
    public final static byte CADRE_STATUS_LEADER = 6;
    public final static byte CADRE_STATUS_RECRUIT = 7; // 应聘干部

    public final static Map<Byte, String> CADRE_STATUS_MAP = new LinkedHashMap<>();
    public final static Set<Byte> CADRE_STATUS_SET = new HashSet<>(); // 干部角色对应的所有状态
    public final static Set<Byte> CADRE_STATUS_NOW_SET = new HashSet<>(); // 现任干部角色对应的所有状态
    public final static Set<Byte> CADRE_STATUS_LEAVE_SET = new HashSet<>(); // 离任干部角色对应的所有状态

    static {
        CADRE_STATUS_MAP.put(CADRE_STATUS_INSPECT, "考察对象"); // 非干部角色
        CADRE_STATUS_MAP.put(CADRE_STATUS_RESERVE, "后备干部库"); // 非干部角色
        CADRE_STATUS_MAP.put(CADRE_STATUS_MIDDLE_LEAVE, "离任中层干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_MIDDLE, "现任中层干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER_LEAVE, "离任校领导");
        CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER, "现任校领导");
        CADRE_STATUS_MAP.put(CADRE_STATUS_RECRUIT, "应聘干部");

        CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE);
        CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE_LEAVE);
        CADRE_STATUS_SET.add(CADRE_STATUS_LEADER);
        CADRE_STATUS_SET.add(CADRE_STATUS_LEADER_LEAVE);

        CADRE_STATUS_NOW_SET.add(CADRE_STATUS_MIDDLE);
        CADRE_STATUS_NOW_SET.add(CADRE_STATUS_LEADER);

        CADRE_STATUS_LEAVE_SET.add(CADRE_STATUS_MIDDLE_LEAVE);
        CADRE_STATUS_LEAVE_SET.add(CADRE_STATUS_LEADER_LEAVE);
    }

    // 干部党派类别
    public final static byte CADRE_PARTY_TYPE_NONE = 0; // 无，此时党派由党员库确认
    public final static byte CADRE_PARTY_TYPE_DP = 1; // 民主党派
    public final static byte CADRE_PARTY_TYPE_OW = 2; // 中共党员
    public final static Map<Byte, String> CADRE_PARTY_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_PARTY_TYPE_MAP.put(CADRE_PARTY_TYPE_NONE, "无");
        CADRE_PARTY_TYPE_MAP.put(CADRE_PARTY_TYPE_DP, "民主党派");
        CADRE_PARTY_TYPE_MAP.put(CADRE_PARTY_TYPE_OW, "中共党员");

    }

    // 干部历史数据类别
    public final static byte CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE = 1;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CADRE = 2;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CPC = 3;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT = 4;
    public final static Map<Byte, String> CADRE_STAT_HISTORY_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE, "中层干部信息表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CADRE, "中层干部情况统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CPC, "干部职数配置情况统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT, "内设机构干部配备统计表");

    }

    // 后备干部库类别
    public final static byte CADRE_RESERVE_TYPE_SCHOOL = 1;
    public final static byte CADRE_RESERVE_TYPE_ADMIN_CHIEF = 2;
    public final static byte CADRE_RESERVE_TYPE_ADMIN_VICE = 3;
    public final static byte CADRE_RESERVE_TYPE_COLLEGE_CHIEF = 4;
    public final static byte CADRE_RESERVE_TYPE_COLLEGE_VICE = 5;
    public final static Map<Byte, String> CADRE_RESERVE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_SCHOOL, "校级");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_ADMIN_CHIEF, "机关正处级");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_ADMIN_VICE, "机关副处级");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_COLLEGE_CHIEF, "院系正处级");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_COLLEGE_VICE, "学院副处级");
    }
    /*static {
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_SCHOOL, "校级后备干部库");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_ADMIN_CHIEF, "机关正处级后备干部库");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_ADMIN_VICE, "机关副处级后备干部库");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_COLLEGE_CHIEF, "学院正级后备干部库");
        CADRE_RESERVE_TYPE_MAP.put(CADRE_RESERVE_TYPE_COLLEGE_VICE, "学院副处级后备干部库");
    }*/

    // 后备干部库状态 1 后备干部 2 后备干部已使用 3 已撤销资格
    public final static byte CADRE_RESERVE_STATUS_NORMAL = 1;
    public final static byte CADRE_RESERVE_STATUS_TO_INSPECT = 2;
    public final static byte CADRE_RESERVE_STATUS_ASSIGN = 3;
    public final static byte CADRE_RESERVE_STATUS_ABOLISH = 4;
    public final static Map<Byte, String> CADRE_RESERVE_STATUS_MAP = new LinkedHashMap<>();

    static {
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_NORMAL, "后备干部");
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_TO_INSPECT, "已列为考察对象");
        //CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_ASSIGN, "后备干部已使用");
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_ASSIGN, "已使用");
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_ABOLISH, "已撤销资格");
    }

    // 干部任免操作类别
    public final static byte CADRE_AD_LOG_MODULE_CADRE = 1;
    public final static byte CADRE_AD_LOG_MODULE_INSPECT = 2;
    public final static byte CADRE_AD_LOG_MODULE_RESERVE = 3;
    public final static Map<Byte, String> CADRE_AD_LOG_MODULE_MAP = new LinkedHashMap<>();

    static {
        CADRE_AD_LOG_MODULE_MAP.put(CADRE_AD_LOG_MODULE_CADRE, "干部库");
        CADRE_AD_LOG_MODULE_MAP.put(CADRE_AD_LOG_MODULE_INSPECT, "考察对象");
        CADRE_AD_LOG_MODULE_MAP.put(CADRE_AD_LOG_MODULE_RESERVE, "后备干部");
    }

    // 考察对象类别，保留字段
    public final static byte CADRE_INSPECT_TYPE_DEFAULT = 1;

    // 考察对象状态 1考察对象 2 考察对象已任命 3 已撤销资格
    public final static byte CADRE_INSPECT_STATUS_NORMAL = 1;
    public final static byte CADRE_INSPECT_STATUS_ASSIGN = 2;
    public final static byte CADRE_INSPECT_STATUS_ABOLISH = 3;
    public final static Map<Byte, String> CADRE_INSPECT_STATUS_MAP = new LinkedHashMap<>();

    static {
        CADRE_INSPECT_STATUS_MAP.put(CADRE_INSPECT_STATUS_NORMAL, "考察对象");
        CADRE_INSPECT_STATUS_MAP.put(CADRE_INSPECT_STATUS_ASSIGN, "通过常委会任命");
        CADRE_INSPECT_STATUS_MAP.put(CADRE_INSPECT_STATUS_ABOLISH, "已撤销资格");
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

    // 干部干部信息采集表 类型，1 工作经历 2 兼职情况
    public final static byte CADRE_INFO_TYPE_WORK = 1;
    public final static byte CADRE_INFO_TYPE_PARTTIME = 2;
    public final static byte CADRE_INFO_TYPE_TRAIN = 3;
    public final static byte CADRE_INFO_TYPE_TEACH = 4;
    public final static byte CADRE_INFO_TYPE_REWARD_OTHER = 6;

    public final static byte CADRE_INFO_TYPE_RESEARCH = 5;
    public final static byte CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY = 7;
    public final static byte CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY = 8;
    public final static byte CADRE_INFO_TYPE_BOOK_SUMMARY = 9;
    public final static byte CADRE_INFO_TYPE_PAPER_SUMMARY = 11;
    public final static byte CADRE_INFO_TYPE_RESEARCH_REWARD = 12;

    public final static byte CADRE_INFO_TYPE_EDU = 10;

    public final static Map<Byte, String> CADRE_INFO_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_WORK, "工作经历");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_PARTTIME, "社会或学术兼职");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_TRAIN, "培训情况");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_TEACH, "教学经历");// 包含教学成果及获奖情况

        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_REWARD_OTHER, "其他奖励情况");

        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY, "参与科研项目");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY, "主持科研项目");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_BOOK_SUMMARY, "出版著作");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_PAPER_SUMMARY, "发表论文");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH_REWARD, "科研成果及获奖");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH, "科研情况");

        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_EDU, "学习经历");
    }

    // 干部企业兼职情况  兼职类型 企业兼职or社团兼职or其他
    public final static byte CADRE_COMPANY_TYPE_ENTERPRISE = 1;
    public final static byte CADRE_COMPANY_TYPE_LEAGUE = 2;
    public final static byte CADRE_COMPANY_TYPE_OTHER = 3;
    public final static Map<Byte, String> CADRE_COMPANY_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_COMPANY_TYPE_MAP.put(CADRE_COMPANY_TYPE_ENTERPRISE, "企业兼职");
        CADRE_COMPANY_TYPE_MAP.put(CADRE_COMPANY_TYPE_LEAGUE, "社团兼职");
        CADRE_COMPANY_TYPE_MAP.put(CADRE_COMPANY_TYPE_OTHER, "其他");
    }

    // 干部学习经历 学校类型 1本校 2境内 3境外
    public final static byte CADRE_SCHOOL_TYPE_THIS_SCHOOL = 1;
    public final static byte CADRE_SCHOOL_TYPE_DOMESTIC = 2;
    public final static byte CADRE_SCHOOL_TYPE_ABROAD = 3;
    public final static Map<Byte, String> CADRE_SCHOOL_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_THIS_SCHOOL, "本校");
        CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_DOMESTIC, "境内");
        CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_ABROAD, "境外");
    }

    // 干部学习经历 导师类型 1硕士研究生导师 2 博士研究生导师
    public final static byte CADRE_TUTOR_TYPE_MASTER = 1;
    public final static byte CADRE_TUTOR_TYPE_DOCTOR = 2;
    public final static Map<Byte, String> CADRE_TUTOR_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_TUTOR_TYPE_MAP.put(CADRE_TUTOR_TYPE_MASTER, "硕士研究生导师");
        CADRE_TUTOR_TYPE_MAP.put(CADRE_TUTOR_TYPE_DOCTOR, "博士研究生导师");
    }

    // 干部教学课程类别
    public final static byte CADRE_COURSE_TYPE_BKS = 1;
    public final static byte CADRE_COURSE_TYPE_SS = 2;
    public final static byte CADRE_COURSE_TYPE_BS = 3;
    public final static Map<Byte, String> CADRE_COURSE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_BKS, "本科生课程");
        CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_SS, "硕士生课程");
        CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_BS, "博士生课程");
    }

    // 干部获奖类别 1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况
    public final static byte CADRE_REWARD_TYPE_TEACH = 1;
    public final static byte CADRE_REWARD_TYPE_RESEARCH = 2;
    public final static byte CADRE_REWARD_TYPE_OTHER = 3;

    // 干部科研项目类别 1,主持 2 参与
    public final static byte CADRE_RESEARCH_TYPE_DIRECT = 1;
    public final static byte CADRE_RESEARCH_TYPE_IN = 2;

    // 干部出版著作类别 独著、译著、合著
    public final static byte CADRE_BOOK_TYPE_ALONE = 1;
    public final static byte CADRE_BOOK_TYPE_TRANSLATE = 2;
    public final static byte CADRE_BOOK_TYPE_COAUTHOR = 3;
    public final static Map<Byte, String> CADRE_BOOK_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_BOOK_TYPE_MAP.put(CADRE_BOOK_TYPE_ALONE, "独著");
        CADRE_BOOK_TYPE_MAP.put(CADRE_BOOK_TYPE_TRANSLATE, "译著");
        CADRE_BOOK_TYPE_MAP.put(CADRE_BOOK_TYPE_COAUTHOR, "合著");
    }

    // 称谓，1父亲，2母亲， 3配偶， 4儿子， 5女儿
    public final static byte CADRE_FAMLIY_TITLE_FATHER = 1;
    public final static byte CADRE_FAMLIY_TITLE_MOTHER = 2;
    public final static byte CADRE_FAMLIY_TITLE_MATE = 3;
    public final static byte CADRE_FAMLIY_TITLE_SON = 4;
    public final static byte CADRE_FAMLIY_TITLE_DAUGHTER = 5;
    public final static Map<Byte, String> CADRE_FAMLIY_TITLE_MAP = new LinkedHashMap<>();

    static {
        CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_FATHER, "父亲");
        CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_MOTHER, "母亲");
        CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_MATE, "配偶");
        CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_SON, "儿子");
        CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_DAUGHTER, "女儿");
    }

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

    // 干部档案审核 认定记录状态  0：正式记录 1：修改记录 2：已删除，每个干部的正式记录只有一条
    public final static byte VERIFY_STATUS_NORMAL = 0;
    public final static byte VERIFY_STATUS_MODIFY = 1;
    public final static byte VERIFY_STATUS_DEL = 2;
    public static Map<Byte, String> VERIFY_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_STATUS_MAP.put(VERIFY_STATUS_NORMAL, "正式记录");
        VERIFY_STATUS_MAP.put(VERIFY_STATUS_MODIFY, "修改记录");
        VERIFY_STATUS_MAP.put(VERIFY_STATUS_DEL, "已删除");
    }

    // 干部档案审核 出生时间认定类别  1：阴阳历换算造成误差 2：出生时间改大了，保持现状 3：出生时间改小了，重新认定
    public final static byte VERIFY_AGE_TYPE_ERROR = 1;
    public final static byte VERIFY_AGE_TYPE_LARGE = 2;
    public final static byte VERIFY_AGE_TYPE_SMALL = 3;
    public final static byte VERIFY_AGE_TYPE_OTHER = 4;
    public static Map<Byte, String> VERIFY_AGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_ERROR, "阴阳历换算造成误差");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_LARGE, "出生时间改大了，保持现状");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_SMALL, "出生时间改小了，重新认定");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_OTHER, "其他情况");
    }

    // 干部档案审核 参加工作时间认定类别  1：阴阳历换算造成误差 2：参加工作时间改大了，保持现状 3：参加工作时间改小了，重新认定
    public final static byte VERIFY_WORK_TIME_TYPE_ERROR = 1;
    public final static byte VERIFY_WORK_TIME_TYPE_LARGE = 2;
    public final static byte VERIFY_WORK_TIME_TYPE_SMALL = 3;
    public final static byte VERIFY_WORK_TIME_TYPE_OTHER = 4;
    public static Map<Byte, String> VERIFY_WORK_TIME_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_ERROR, "阴阳历换算造成误差");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_LARGE, "参加工作时间改大了，保持现状");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_SMALL, "参加工作时间改小了，重新认定");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_OTHER, "其他情况");
    }

    // 信息修改申请
    public final static byte RECORD_STATUS_FORMAL = 0; // 正式记录
    public final static byte RECORD_STATUS_MODIFY = 1; // 修改记录

    // 干部信息检查结果
    public final static byte CADRE_INFO_CHECK_RESULT_NOT_EXIST = 0;
    public final static byte CADRE_INFO_CHECK_RESULT_EXIST = 1;
    public final static byte CADRE_INFO_CHECK_RESULT_MODIFY = 2;  // 存在修改申请
    public final static Map<Byte, String> CADRE_INFO_CHECK_RESULT_MAP = new LinkedHashMap<>();

    static {
        CADRE_INFO_CHECK_RESULT_MAP.put(CADRE_INFO_CHECK_RESULT_NOT_EXIST, "否");
        CADRE_INFO_CHECK_RESULT_MAP.put(CADRE_INFO_CHECK_RESULT_EXIST, "是");
        CADRE_INFO_CHECK_RESULT_MAP.put(CADRE_INFO_CHECK_RESULT_MODIFY, "已申请修改，待审核");
    }

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
    public final static Map<Byte, String> SHORT_MSG_RELATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_CONTENT_TPL, "短信模板");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL, "定向短信");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_PCS, "党代会");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_OA, "协同办公");
    }

    // 菜单关联缓存数量( cache name: cache_counts)
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_EDU = 1;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_WORK = 2;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK = 3;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY = 4;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE = 5;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER = 6;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME = 7;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH = 9;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH = 11;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER = 12;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN = 10;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT = 8;
    public final static byte CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN = 13;
    public final static byte CACHEKEY_MODIFY_BASE_APPLY = 14;
    public final static byte CACHEKEY_ABROAD_PASSPORT_APPLY = 15;
    public final static byte CACHEKEY_ABROAD_APPLY_SELF = 16;

    public final static byte CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_SELF = 17;
    public final static byte CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_TW = 18;
    public final static byte CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER = 19;
    public final static byte CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF = 20;

    public final static byte CACHEKEY_TAIWAN_RECORD_HANDLE_TYPE = 21;

    public final static byte CACHEKEY_CADRE_PARTY_TO_REMOVE = 22;

    public final static Map<Byte, String> CACHEKEY_MAP = new LinkedHashMap<>();

    static {
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_EDU, "学习经历-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_WORK, "工作经历-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK, "出版著作情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY, "企业兼职情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE, "教学课程-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER, "发表论文情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME, "社会或学术兼职-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT, "主持科研项目情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN, "参与科研项目情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH, "教学成果及获奖情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH, "科研成果及获奖情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER, "其他奖励情况-干部信息修改申请");
        CACHEKEY_MAP.put(CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN, "培训情况-干部信息修改申请");

        CACHEKEY_MAP.put(CACHEKEY_MODIFY_BASE_APPLY, "干部基本信息修改");
        CACHEKEY_MAP.put(CACHEKEY_ABROAD_PASSPORT_APPLY, "办理证件审批");
        CACHEKEY_MAP.put(CACHEKEY_ABROAD_APPLY_SELF, "因私出国境审批（管理员）");

        CACHEKEY_MAP.put(CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_SELF, "因私出国（境）-领取证件");
        CACHEKEY_MAP.put(CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_TW, "因公赴台-领取证件");
        CACHEKEY_MAP.put(CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER, "其他事务-领取证件");
        CACHEKEY_MAP.put(CACHEKEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF, "长期因公出国-领取证件");

        CACHEKEY_MAP.put(CACHEKEY_TAIWAN_RECORD_HANDLE_TYPE, "因公赴台备案-提醒管理员选择办理新证件方式");

        CACHEKEY_MAP.put(CACHEKEY_CADRE_PARTY_TO_REMOVE, "特殊党员干部库-已存在于党员信息库");
    }

    // 审批记录类型
    public final static byte SYS_APPROVAL_LOG_TYPE_APPLYSELF = 1; // 因私出国
    public final static byte SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT = 2; // 干部招聘-报名审核
    public final static byte SYS_APPROVAL_LOG_TYPE_PMD_MEMBER = 3; // 党费收缴
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_TRAINEE = 4; // 干部教育培训-可选课人员
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ = 5; // 干部教育培训-培训对象

    public final static Map<Byte, String> SYS_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_APPLYSELF, "因私出国");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT, "干部招聘");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_PMD_MEMBER, "党费收缴");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_TRAINEE, "参训人员操作记录(干部教育培训)");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ, "培训对象操作记录(干部教育培训)");
    }

    // 操作人类别, 0本人 1 干部管理员 2 因私审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_SELF = 0; // 本人
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_CADRE = 1; // 干部管理员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_APPLYSELF = 2; // 因私审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_NOT_SELF = 5;// 他人操作
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_ADMIN = 10;// 后台操作
    public final static Map<Byte, String> SYS_APPROVAL_LOG_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_SELF, "本人");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_CADRE, "干部管理员");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_APPLYSELF, "因私审批人员");
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
