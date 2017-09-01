package sys.constants;

import sys.utils.DateUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SystemConstants {

    public final static Map<String, String> appKeyMap = new HashMap<>();

    static {

        appKeyMap.put("LXXT", "7507a3c61bf38d9f06d00c3f2fa2de58");
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

    // 系统角色（与数据库对应的角色字符串不可以修改！）
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_GUEST = "guest";
    public static final String ROLE_REG = "reg"; // 注册用户，未审核通过
    public static final String ROLE_CADRE = "cadre";
    public static final String ROLE_CADRERESERVE = "cadreReserve"; // 后备干部
    public static final String ROLE_CADREINSPECT = "cadreInspect"; // 考察对象
    public static final String ROLE_MEMBER = "member";
    public static final String ROLE_INFLOWMEMBER = "inflowMember";
    public static final String ROLE_PARTYADMIN = "partyAdmin";
    public static final String ROLE_BRANCHADMIN = "branchAdmin";
    public static final String ROLE_ODADMIN = "odAdmin";
    public static final String ROLE_CADREADMIN = "cadreAdmin";

    public static final String ROLE_TEACHER = "role_teacher"; // 教职工
    public static final String ROLE_PCS_ADMIN = "role_pcs_admin"; // 党代会管理员

    public final static Map<String, String> ROLE_MAP = new LinkedHashMap<>();

    static {
        ROLE_MAP.put(ROLE_ADMIN, "系统管理员");
        ROLE_MAP.put(ROLE_GUEST, "非党员");
        ROLE_MAP.put(ROLE_REG, "注册用户");
        ROLE_MAP.put(ROLE_CADRE, "干部");
        ROLE_MAP.put(ROLE_CADRERESERVE, "后备干部");
        ROLE_MAP.put(ROLE_CADREINSPECT, "考察对象");
        ROLE_MAP.put(ROLE_MEMBER, "党员");
        ROLE_MAP.put(ROLE_INFLOWMEMBER, "流入党员");
        ROLE_MAP.put(ROLE_PARTYADMIN, "分党委管理员");
        ROLE_MAP.put(ROLE_BRANCHADMIN, "党支部管理员");
        ROLE_MAP.put(ROLE_ODADMIN, "组织部管理员");
        ROLE_MAP.put(ROLE_CADREADMIN, "干部管理员");

        ROLE_MAP.put(ROLE_TEACHER, "教职工");
        ROLE_MAP.put(ROLE_PCS_ADMIN, "党代会管理员");
    }

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

    // 系统附件类别，1图片 2文档 3视频 4 其他
    public static final byte ATTACH_FILE_TYPE_IMAGE = 1;
    public static final byte ATTACH_FILE_TYPE_DOC = 2;
    public static final byte ATTACH_FILE_TYPE_VIDEO = 3;
    public static final byte ATTACH_FILE_TYPE_OTHER = 4;
    public final static Map<Byte, String> ATTACH_FILE_TYPE_MAP = new LinkedHashMap<>();

    static {
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_IMAGE, "图片");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_DOC, "文档");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_VIDEO, "视频");
        ATTACH_FILE_TYPE_MAP.put(ATTACH_FILE_TYPE_OTHER, "其他");
    }


    //public static final String LOG_LOGIN = "mt_log_login"; 登录单独记录
    public static final String LOG_ADMIN = "mt_log_admin";
    public static final String LOG_OW = "mt_log_ow";
    public static final String LOG_USER = "mt_log_user";
    public static final String LOG_MEMBER = "mt_log_member";
    public static final String LOG_MEMBER_APPLY = "mt_log_member_apply";
    public static final String LOG_ABROAD = "mt_log_abroad";

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

    // 同步类型，1人事库 2研究库 3本科生库 4教职工党员出国信息库
    public final static byte SYNC_TYPE_JZG = 1;
    public final static byte SYNC_TYPE_BKS = 2;
    public final static byte SYNC_TYPE_YJS = 3;
    public final static byte SYNC_TYPE_ABROAD = 4;
    public final static Map<Byte, String> SYNC_TYPE_MAP = new LinkedHashMap();

    static {
        SYNC_TYPE_MAP.put(SYNC_TYPE_JZG, "人事库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_BKS, "本科生库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_YJS, "研究生库");
        SYNC_TYPE_MAP.put(SYNC_TYPE_ABROAD, "教职工党员出国境信息库");
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

    // 党员年龄段
    public final static byte MEMBER_AGE_20 = 1; // 20及以下
    public final static byte MEMBER_AGE_21_30 = 2;
    public final static byte MEMBER_AGE_31_40 = 3;
    public final static byte MEMBER_AGE_41_50 = 4;
    public final static byte MEMBER_AGE_51 = 5;
    public final static byte MEMBER_AGE_0 = 0; // 未知
    public final static Map<Byte, String> MEMBER_AGE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_AGE_MAP.put(MEMBER_AGE_20, "20及以下");
        MEMBER_AGE_MAP.put(MEMBER_AGE_21_30, "21~30");
        MEMBER_AGE_MAP.put(MEMBER_AGE_31_40, "31~40");
        MEMBER_AGE_MAP.put(MEMBER_AGE_41_50, "41~50");
        MEMBER_AGE_MAP.put(MEMBER_AGE_51, "51及以上");
        MEMBER_AGE_MAP.put(MEMBER_AGE_0, "未知");
    }

    public static byte getMemberAgeRange(Integer birthYear) {

        int currentYear = DateUtils.getCurrentYear();
        byte key = SystemConstants.MEMBER_AGE_0; // 未知年龄
        if (birthYear != null) {
            if (birthYear > currentYear - 20) { // 20岁及以下
                key = SystemConstants.MEMBER_AGE_20;
            } else if (birthYear > currentYear - 30) { // 21~30
                key = SystemConstants.MEMBER_AGE_21_30;
            } else if (birthYear > currentYear - 40) { // 31~40
                key = SystemConstants.MEMBER_AGE_31_40;
            } else if (birthYear > currentYear - 50) { // 41~50
                key = SystemConstants.MEMBER_AGE_41_50;
            } else { // 51及以上
                key = SystemConstants.MEMBER_AGE_51;
            }
        }
        return key;
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

    // 党员政治面貌
    public final static byte MEMBER_POLITICAL_STATUS_GROW = 1; //预备党员
    public final static byte MEMBER_POLITICAL_STATUS_POSITIVE = 2; //正式党员
    public final static Map<Byte, String> MEMBER_POLITICAL_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_GROW, "预备党员");
        MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_POSITIVE, "正式党员");
    }

    // 党员类别，用于党员信息、流动党员、校内组织关系互转
    public final static byte MEMBER_TYPE_TEACHER = 1; //教工
    public final static byte MEMBER_TYPE_STUDENT = 2; //学生
    public final static Map<Byte, String> MEMBER_TYPE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_TYPE_MAP.put(MEMBER_TYPE_TEACHER, "教工");
        MEMBER_TYPE_MAP.put(MEMBER_TYPE_STUDENT, "学生");
    }

    // 党员状态, 1正常，2已退休(弃用) 3已出党 4已转出 5暂时转出（外出挂职、休学等）
    public final static byte MEMBER_STATUS_NORMAL = 1; // 正常
    //public final static byte MEMBER_STATUS_RETIRE= 2; // 已退休
    public final static byte MEMBER_STATUS_QUIT = 3; // 已出党
    public final static byte MEMBER_STATUS_TRANSFER = 4; // 已转出
    public final static byte MEMBER_STATUS_TRANSFER_TEMP = 5; // 外出挂职、休学等
    public final static Map<Byte, String> MEMBER_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_NORMAL, "正常");
        //MEMBER_STATUS_MAP.put(MEMBER_STATUS_RETIRE, "已退休");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_QUIT, "已出党");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_TRANSFER, "已转出");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_TRANSFER_TEMP, "外出挂职、休学等");
    }

    // 党员来源
    public final static byte MEMBER_SOURCE_IMPORT = 1; // 2015年底统一导入
    public final static byte MEMBER_SOURCE_GROW = 2; // 本校发展
    public final static byte MEMBER_SOURCE_TRANSFER = 3; // 外校转入
    public final static byte MEMBER_SOURCE_RETURNED = 4; // 归国人员恢复入党
    public final static byte MEMBER_SOURCE_ADMIN = 5; // 后台添加
    public final static Map<Byte, String> MEMBER_SOURCE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_IMPORT, "2015年底统一导入");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_GROW, "本校发展");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_TRANSFER, "外校转入");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_RETURNED, "归国人员恢复入党");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_ADMIN, "后台添加");
    }

    // 党员退休审核状态
    public final static byte RETIRE_APPLY_STATUS_UNCHECKED = 0; // 未审核
    public final static byte RETIRE_APPLY_STATUS_CHECKED = 1; // 已审核

    // 出党类别，1自动退党 2开除党籍 3党员去世
    public final static byte MEMBER_QUIT_TYPE_SELF = 1;
    public final static byte MEMBER_QUIT_TYPE_DISMISS = 2;
    public final static byte MEMBER_QUIT_TYPE_WITHGOD = 3;
    public final static Map<Byte, String> MEMBER_QUIT_TYPE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_QUIT_TYPE_MAP.put(MEMBER_QUIT_TYPE_SELF, "自动退党");
        MEMBER_QUIT_TYPE_MAP.put(MEMBER_QUIT_TYPE_DISMISS, "开除党籍");
        MEMBER_QUIT_TYPE_MAP.put(MEMBER_QUIT_TYPE_WITHGOD, "党员去世");
    }


    // 组织关系状态
    public final static byte OR_STATUS_OUT = 1;
    public final static byte OR_STATUS_NOT_OUT = 2;
    public final static Map<Byte, String> OR_STATUS_MAP = new LinkedHashMap<>();

    static {
        OR_STATUS_MAP.put(OR_STATUS_OUT, "已转出");
        OR_STATUS_MAP.put(OR_STATUS_NOT_OUT, "未转出");
    }

    // 留学归国人员申请恢复组织生活状态
    public final static byte MEMBER_RETURN_STATUS_DENY = -1;
    public final static byte MEMBER_RETURN_STATUS_APPLY = 0;
    public final static byte MEMBER_RETURN_STATUS_BRANCH_VERIFY = 1;
    public final static byte MEMBER_RETURN_STATUS_PARTY_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_RETURN_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_DENY, "不通过");
        MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_APPLY, "申请");
        MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_BRANCH_VERIFY, "支部审核通过");
        MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_PARTY_VERIFY, "分党委审核通过");
    }

    // 党员转入转出类别
    public final static byte MEMBER_INOUT_TYPE_INSIDE = 1;
    public final static byte MEMBER_INOUT_TYPE_OUTSIDE = 2;
    public final static Map<Byte, String> MEMBER_INOUT_TYPE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_INOUT_TYPE_MAP.put(MEMBER_INOUT_TYPE_INSIDE, "京内");
        MEMBER_INOUT_TYPE_MAP.put(MEMBER_INOUT_TYPE_OUTSIDE, "京外");
    }

    // 党员出党状态
    public final static byte MEMBER_QUIT_STATUS_SELF_BACK = -2;
    public final static byte MEMBER_QUIT_STATUS_BACK = -1;
    public final static byte MEMBER_QUIT_STATUS_APPLY = 0;
    public final static byte MEMBER_QUIT_STATUS_BRANCH_VERIFY = 1;
    public final static byte MEMBER_QUIT_STATUS_PARTY_VERIFY = 2;
    public final static byte MEMBER_QUIT_STATUS_OW_VERIFY = 3;
    public final static Map<Byte, String> MEMBER_QUIT_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_SELF_BACK, "本人撤回");
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_BACK, "返回修改");
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_APPLY, "申请");
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_BRANCH_VERIFY, "支部审核通过");
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_OW_VERIFY, "组织部审核通过");
    }

    // 党员转出状态
    public final static byte MEMBER_OUT_STATUS_ABOLISH = -3;
    public final static byte MEMBER_OUT_STATUS_SELF_BACK = -2;
    public final static byte MEMBER_OUT_STATUS_BACK = -1;
    public final static byte MEMBER_OUT_STATUS_APPLY = 0;
    public final static byte MEMBER_OUT_STATUS_PARTY_VERIFY = 1;
    public final static byte MEMBER_OUT_STATUS_OW_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_OUT_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_ABOLISH, "组织部撤销已完成的审批");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_SELF_BACK, "本人撤回");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_BACK, "返回修改");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_APPLY, "申请");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_OW_VERIFY, "组织部审核通过");
    }

    // 党员转入状态
    public final static byte MEMBER_IN_STATUS_SELF_BACK = -2;
    public final static byte MEMBER_IN_STATUS_BACK = -1;
    public final static byte MEMBER_IN_STATUS_APPLY = 0;
    public final static byte MEMBER_IN_STATUS_PARTY_VERIFY = 1;
    public final static byte MEMBER_IN_STATUS_OW_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_IN_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_SELF_BACK, "本人撤回");
        MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_BACK, "返回修改");
        MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_APPLY, "申请");
        MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_OW_VERIFY, "组织部审核通过");
    }

    // 校内组织关系互转状态，-1返回修改 0申请 1转出分党委审批 2转入分党委审批
    public final static byte MEMBER_TRANSFER_STATUS_SELF_BACK = -2;
    public final static byte MEMBER_TRANSFER_STATUS_BACK = -1;
    public final static byte MEMBER_TRANSFER_STATUS_APPLY = 0;
    public final static byte MEMBER_TRANSFER_STATUS_FROM_VERIFY = 1;
    public final static byte MEMBER_TRANSFER_STATUS_TO_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_TRANSFER_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_SELF_BACK, "本人撤回");
        MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_BACK, "返回修改");
        MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_APPLY, "申请");
        MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_FROM_VERIFY, "转出分党委审核通过");
        MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_TO_VERIFY, "转入分党委审核通过");
    }

    // 党员流入状态
    public final static byte MEMBER_INFLOW_STATUS_BACK = -1; // 本人撤回、审核不通过
    public final static byte MEMBER_INFLOW_STATUS_APPLY = 0;
    public final static byte MEMBER_INFLOW_STATUS_BRANCH_VERIFY = 1;
    public final static byte MEMBER_INFLOW_STATUS_PARTY_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_INFLOW_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_BACK, "不通过");
        MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_APPLY, "申请");
        MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_BRANCH_VERIFY, "党支部审核通过");
        MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_PARTY_VERIFY, "分党委审核通过");
    }

    // 党员流入转出状态
    public final static byte MEMBER_INFLOW_OUT_STATUS_SELF_BACK = -2; // 本人撤回
    public final static byte MEMBER_INFLOW_OUT_STATUS_BACK = -1; // 审核不通过
    public final static byte MEMBER_INFLOW_OUT_STATUS_APPLY = 0;
    public final static byte MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY = 1;
    public final static byte MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_INFLOW_OUT_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_SELF_BACK, "本人撤回");
        MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_BACK, "不通过");
        MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_APPLY, "申请");
        MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY, "党支部审核通过");
        MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY, "分党委审核通过");
    }


    // 党员流出状态
    public final static byte MEMBER_OUTFLOW_STATUS_SELF_BACK = -2; // 本人撤回
    public final static byte MEMBER_OUTFLOW_STATUS_BACK = -1; // 审核不通过
    public final static byte MEMBER_OUTFLOW_STATUS_APPLY = 0;
    public final static byte MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY = 1;
    public final static byte MEMBER_OUTFLOW_STATUS_PARTY_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_OUTFLOW_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_SELF_BACK, "本人撤回");
        MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_BACK, "不通过");
        MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_APPLY, "申请");
        MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY, "党支部审核通过");
        MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_PARTY_VERIFY, "分党委审核通过");
    }


    // 暂留组织关系申请 类别
    public final static byte MEMBER_STAY_TYPE_ABROAD = 1; // 出国境
    public final static byte MEMBER_STAY_TYPE_INTERNAL = 2; // 国内
    public final static Map<Byte, String> MEMBER_STAY_TYPE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_STAY_TYPE_MAP.put(MEMBER_STAY_TYPE_ABROAD, "党员出国（境）");
        MEMBER_STAY_TYPE_MAP.put(MEMBER_STAY_TYPE_INTERNAL, "非出国（境）");
    }

    // 暂留组织关系申请    留学方式
    public final static byte MEMBER_STAY_ABROAD_TYPE_MAP_PUB = 1; // 公派
    public final static byte MEMBER_STAY_ABROAD_TYPE_MAP_SELF = 2; // 自费
    public final static Map<Byte, String> MEMBER_STAY_ABROAD_TYPE_MAP_MAP = new LinkedHashMap<>();

    static {
        MEMBER_STAY_ABROAD_TYPE_MAP_MAP.put(MEMBER_STAY_ABROAD_TYPE_MAP_PUB, "公派");
        MEMBER_STAY_ABROAD_TYPE_MAP_MAP.put(MEMBER_STAY_ABROAD_TYPE_MAP_SELF, "自费");
    }

    // 暂留组织关系申请状态
    public final static byte MEMBER_STAY_STATUS_SELF_BACK = -2; // 本人撤回
    public final static byte MEMBER_STAY_STATUS_BACK = -1; // 审核不通过
    public final static byte MEMBER_STAY_STATUS_APPLY = 0;
    public final static byte MEMBER_STAY_STATUS_BRANCH_VERIFY = 1;
    public final static byte MEMBER_STAY_STATUS_PARTY_VERIFY = 2;
    public final static byte MEMBER_STAY_STATUS_OW_VERIFY = 3;
    public final static Map<Byte, String> MEMBER_STAY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_SELF_BACK, "本人撤回");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_BACK, "不通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_APPLY, "申请");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_BRANCH_VERIFY, "党支部审核通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_OW_VERIFY, "组织部审核通过");
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

    public final static Map<Byte, String> CADRE_STATUS_MAP = new LinkedHashMap<>();
    public final static Set<Byte> CADRE_STATUS_SET = new HashSet<>(); // 干部角色对应的所有状态
    public final static Set<Byte> CADRE_STATUS_NOW_SET = new HashSet<>(); // 现任干部角色对应的所有状态
    public final static Set<Byte> CADRE_STATUS_LEAVE_SET = new HashSet<>(); // 离任干部角色对应的所有状态

    public final static Set<Byte> ABROAD_APPLICAT_CADRE_STATUS_SET = new HashSet<>(); // 因私申请人要求的干部状态

    public final static Set<Byte> CRS_EXPERT_CADRE_STATUS_SET = new HashSet<>(); // 干部招聘专家组要求的干部状态

    static {
        CADRE_STATUS_MAP.put(CADRE_STATUS_INSPECT, "考察对象"); // 非干部角色
        CADRE_STATUS_MAP.put(CADRE_STATUS_RESERVE, "后备干部库"); // 非干部角色
        CADRE_STATUS_MAP.put(CADRE_STATUS_MIDDLE_LEAVE, "离任中层干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_MIDDLE, "现任中层干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER_LEAVE, "离任校领导");
        CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER, "现任校领导");

        CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE);
        CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE_LEAVE);
        CADRE_STATUS_SET.add(CADRE_STATUS_LEADER);
        CADRE_STATUS_SET.add(CADRE_STATUS_LEADER_LEAVE);

        CADRE_STATUS_NOW_SET.add(CADRE_STATUS_MIDDLE);
        CADRE_STATUS_NOW_SET.add(CADRE_STATUS_LEADER);

        CADRE_STATUS_LEAVE_SET.add(CADRE_STATUS_MIDDLE_LEAVE);
        CADRE_STATUS_LEAVE_SET.add(CADRE_STATUS_LEADER_LEAVE);

        ABROAD_APPLICAT_CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE);
        ABROAD_APPLICAT_CADRE_STATUS_SET.add(CADRE_STATUS_LEADER);
        ABROAD_APPLICAT_CADRE_STATUS_SET.add(CADRE_STATUS_LEADER_LEAVE);

        CRS_EXPERT_CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE);
        CRS_EXPERT_CADRE_STATUS_SET.add(CADRE_STATUS_LEADER);
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
    public final static Map<Byte, String> DISPATCH_WORK_FILE_TYPE_MAP = new HashMap();

    static {
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_XBRY, "干部选拔任用");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_GLJD, "干部管理监督");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_XYHJ, "机关学院换届");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_DWJS, "干部队伍建设");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_KHGZ, "干部考核工作");
        DISPATCH_WORK_FILE_TYPE_MAP.put(DISPATCH_WORK_FILE_TYPE_JYPX, "干部教育培训");
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
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_PARTTIME, "兼职情况");
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

    // 干部考察，考察组成员类别
    public final static byte CIS_INSPECTOR_STATUS_NOW = 1;
    public final static byte CIS_INSPECTOR_STATUS_HISTORY = 2;
    public final static byte CIS_INSPECTOR_STATUS_DELETE = 3;
    public final static Map<Byte, String> CIS_INSPECTOR_STATUS_MAP = new LinkedHashMap<>();

    static {
        CIS_INSPECTOR_STATUS_MAP.put(CIS_INSPECTOR_STATUS_NOW, "现任考察组成员");
        CIS_INSPECTOR_STATUS_MAP.put(CIS_INSPECTOR_STATUS_HISTORY, "过去考察组成员");
        CIS_INSPECTOR_STATUS_MAP.put(CIS_INSPECTOR_STATUS_DELETE, "已删除");
    }

    // 干部考察 考察主体
    public final static byte CIS_INSPECTOR_TYPE_OW = 1;
    public final static byte CIS_INSPECTOR_TYPE_OTHER = 2;
    public final static Map<Byte, String> CIS_INSPECTOR_TYPE_MAP = new LinkedHashMap<>();

    static {
        CIS_INSPECTOR_TYPE_MAP.put(CIS_INSPECTOR_TYPE_OW, "党委组织部");
        CIS_INSPECTOR_TYPE_MAP.put(CIS_INSPECTOR_TYPE_OTHER, "其他");
    }

    // 干部考察材料 材料类型
    public final static byte CIS_EVALUATE_TYPE_SHOW = 1; // 现实表现材料
    public final static byte CIS_EVALUATE_TYPE_EVA = 2; // 工作评价
    public final static Map<Byte, String> CIS_EVALUATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CIS_EVALUATE_TYPE_MAP.put(CIS_EVALUATE_TYPE_SHOW, "现实表现材料");
        CIS_EVALUATE_TYPE_MAP.put(CIS_EVALUATE_TYPE_EVA, "工作评价");
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

    // 党代会管理，委员分类，  1 党委委员 2 纪委委员
    public final static byte PCS_USER_TYPE_DW = 1;
    public final static byte PCS_USER_TYPE_JW = 2;
    public final static Map<Byte, String> PCS_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_USER_TYPE_MAP.put(PCS_USER_TYPE_DW, "党委委员");
        PCS_USER_TYPE_MAP.put(PCS_USER_TYPE_JW, "纪委委员");
    }

    // 党代会管理员类型， 1 书记 2 副书记 3 普通管理员（通常由书记指定一人）
    public final static byte PCS_ADMIN_TYPE_SECRETARY = 1;
    public final static byte PCS_ADMIN_TYPE_VICE_SECRETARY = 2;
    public final static byte PCS_ADMIN_TYPE_NORMAL = 3;
    public final static Map<Byte, String> PCS_ADMIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_ADMIN_TYPE_MAP.put(PCS_ADMIN_TYPE_SECRETARY, "书记");
        PCS_ADMIN_TYPE_MAP.put(PCS_ADMIN_TYPE_VICE_SECRETARY, "副书记");
        PCS_ADMIN_TYPE_MAP.put(PCS_ADMIN_TYPE_NORMAL, "普通管理员");
    }

    // 党代会阶段，1 一下一上 2 二下二上 3 三下三上
    public final static byte PCS_STAGE_FIRST = 1;
    public final static byte PCS_STAGE_SECOND = 2;
    public final static byte PCS_STAGE_THIRD = 3;
    public final static Map<Byte, String> PCS_STAGE_MAP = new LinkedHashMap<>();

    static {
        PCS_STAGE_MAP.put(PCS_STAGE_FIRST, "一下一上");
        PCS_STAGE_MAP.put(PCS_STAGE_SECOND, "二下二上");
        PCS_STAGE_MAP.put(PCS_STAGE_THIRD, "三下三上");
    }

    // 党代表类型，1 专业技术人员和干部 2 学生代表 3 离退休代表
    public final static byte PCS_PR_TYPE_PRO = 1;
    public final static byte PCS_PR_TYPE_STU = 2;
    public final static byte PCS_PR_TYPE_RETIRE = 3;
    public final static Map<Byte, String> PCS_PR_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_PR_TYPE_MAP.put(PCS_PR_TYPE_PRO, "专业技术人员和干部");
        PCS_PR_TYPE_MAP.put(PCS_PR_TYPE_STU, "学生代表");
        PCS_PR_TYPE_MAP.put(PCS_PR_TYPE_RETIRE, "离退休代表");
    }
    // 党代表用户类型，1 干部 2 普通教师 3 学生
    public final static byte PCS_PR_USER_TYPE_CADRE = 1;
    public final static byte PCS_PR_USER_TYPE_TEACHER = 2;
    public final static byte PCS_PR_USER_TYPE_STU = 3;
    public final static Map<Byte, String> PCS_PR_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_PR_USER_TYPE_MAP.put(PCS_PR_USER_TYPE_CADRE, "干部");
        PCS_PR_USER_TYPE_MAP.put(PCS_PR_USER_TYPE_TEACHER, "普通教师");
        PCS_PR_USER_TYPE_MAP.put(PCS_PR_USER_TYPE_STU, "学生");
    }
    // 党代表名单填报审核情况，0 待审核 1 审核通过 2 审核不通过
    public final static byte PCS_PR_RECOMMEND_STATUS_INIT = 0;
    public final static byte PCS_PR_RECOMMEND_STATUS_PASS = 1;
    public final static byte PCS_PR_RECOMMEND_STATUS_DENY = 2;
    public final static Map<Byte, String> PCS_PR_RECOMMEND_STATUS_MAP = new LinkedHashMap<>();

    static {
        PCS_PR_RECOMMEND_STATUS_MAP.put(PCS_PR_RECOMMEND_STATUS_INIT, "待审核");
        PCS_PR_RECOMMEND_STATUS_MAP.put(PCS_PR_RECOMMEND_STATUS_PASS, "审核通过");
        PCS_PR_RECOMMEND_STATUS_MAP.put(PCS_PR_RECOMMEND_STATUS_DENY, "审核不通过");
    }

    // 干部培训 评课账号的状态
    public final static byte TRAIN_INSPECTOR_STATUS_INIT = 0;
    public final static byte TRAIN_INSPECTOR_STATUS_ABOLISH = 1;
    public final static byte TRAIN_INSPECTOR_STATUS_ALL_FINISH = 2;
    public final static byte TRAIN_INSPECTOR_STATUS_PART_FINISH = 3;
    public static Map<Byte, String> TRAIN_INSPECTOR_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_INIT, "未完成");
        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_ABOLISH, "已作废");
        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_ALL_FINISH, "全部完成");
        TRAIN_INSPECTOR_STATUS_MAP.put(TRAIN_INSPECTOR_STATUS_PART_FINISH, "部分完成");
    }

    // 干部培训 评课账号的某门课程的评课状态
    public final static byte TRAIN_INSPECTOR_COURSE_STATUS_SAVE = 0;
    public final static byte TRAIN_INSPECTOR_COURSE_STATUS_FINISH = 1;
    public static Map<Byte, String> TRAIN_INSPECTOR_COURSE_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        TRAIN_INSPECTOR_COURSE_STATUS_MAP.put(TRAIN_INSPECTOR_COURSE_STATUS_SAVE, "暂存");
        TRAIN_INSPECTOR_COURSE_STATUS_MAP.put(TRAIN_INSPECTOR_COURSE_STATUS_FINISH, "已完成");
    }

    // 评课账号修改密码类型
    public final static byte TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF = 1;
    public final static byte TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET = 2;
    public static Map<Byte, String> TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF, "本人修改");
        TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET, "管理员重置");
    }

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

    // 干部招聘 岗位报名状态，0 根据报名时间而定 1 强制开启、2 强制关闭、3 暂停报名
    public final static byte CRS_POST_ENROLL_STATUS_DEFAULT = 0;
    public final static byte CRS_POST_ENROLL_STATUS_OPEN = 1;
    public final static byte CRS_POST_ENROLL_STATUS_CLOSED = 2;
    public final static byte CRS_POST_ENROLL_STATUS_PAUSE = 3;
    public static Map<Byte, String> CRS_POST_ENROLL_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_DEFAULT, "根据报名时间而定");
        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_OPEN, "开启");
        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_CLOSED, "关闭");
        CRS_POST_ENROLL_STATUS_MAP.put(CRS_POST_ENROLL_STATUS_PAUSE, "暂停");
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
    public static Map<Byte, String> CRS_TEMPLATE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_TEMPLATE_TYPE_MAP.put(CRS_TEMPLATE_TYPE_BASE, "基本条件");
        CRS_TEMPLATE_TYPE_MAP.put(CRS_TEMPLATE_TYPE_POST, "任职资格");
    }

    // 招聘岗位 报名人员 信息审核状态，0 待审核 1 通过 2 未通过
    public final static byte CRS_APPLICANT_INFO_CHECK_STATUS_INIT = 0;
    public final static byte CRS_APPLICANT_INFO_CHECK_STATUS_PASS = 1;
    public final static byte CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS = 2;
    public static Map<Byte, String> CRS_APPLICANT_INFO_CHECK_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CRS_APPLICANT_INFO_CHECK_STATUS_MAP.put(CRS_APPLICANT_INFO_CHECK_STATUS_INIT, "待审核");
        CRS_APPLICANT_INFO_CHECK_STATUS_MAP.put(CRS_APPLICANT_INFO_CHECK_STATUS_PASS, "通过");
        CRS_APPLICANT_INFO_CHECK_STATUS_MAP.put(CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS, "未通过");
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
    public static Map<Byte, String> VERIFY_AGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_ERROR, "阴阳历换算造成误差");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_LARGE, "出生时间改大了，保持现状");
        VERIFY_AGE_TYPE_MAP.put(VERIFY_AGE_TYPE_SMALL, "出生时间改小了，重新认定");
    }

    // 干部档案审核 参加工作时间认定类别  1：阴阳历换算造成误差 2：参加工作时间改大了，保持现状 3：参加工作时间改小了，重新认定
    public final static byte VERIFY_WORK_TIME_TYPE_ERROR = 1;
    public final static byte VERIFY_WORK_TIME_TYPE_LARGE = 2;
    public final static byte VERIFY_WORK_TIME_TYPE_SMALL = 3;
    public static Map<Byte, String> VERIFY_WORK_TIME_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_ERROR, "阴阳历换算造成误差");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_LARGE, "参加工作时间改大了，保持现状");
        VERIFY_WORK_TIME_TYPE_MAP.put(VERIFY_WORK_TIME_TYPE_SMALL, "参加工作时间改小了，重新认定");
    }

    // 基本信息修改请求 审核状态，0 待审核 1 部分审核 2 全部审核 3管理员删除（待审核时才可以删除）
    public final static byte MODIFY_BASE_APPLY_STATUS_APPLY = 0;
    public final static byte MODIFY_BASE_APPLY_STATUS_PART_CHECK = 1;
    public final static byte MODIFY_BASE_APPLY_STATUS_ALL_CHECK = 2;
    public final static byte MODIFY_BASE_APPLY_STATUS_DELETE = 3;
    public final static Map<Byte, String> MODIFY_BASE_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_APPLY, "待审核");
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_PART_CHECK, "部分审核");
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_ALL_CHECK, "全部审核");
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_DELETE, "已删除");
    }

    // 基本信息修改 字段类型
    public final static byte MODIFY_BASE_ITEM_TYPE_STRING = 1;
    public final static byte MODIFY_BASE_ITEM_TYPE_INT = 2;
    public final static byte MODIFY_BASE_ITEM_TYPE_DATE = 3;
    public final static byte MODIFY_BASE_ITEM_TYPE_IMAGE = 4;
    public final static Map<Byte, String> MODIFY_BASE_ITEM_TYPE_MAP = new LinkedHashMap<>();

    static {
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_STRING, "字符串");
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_INT, "数字");
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_DATE, "日期");
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_IMAGE, "图片");
    }


    // 基本信息修改 每个字段的审核状态，0 待审核 1 审核通过 2审核未通过
    public final static byte MODIFY_BASE_ITEM_STATUS_APPLY = 0;
    public final static byte MODIFY_BASE_ITEM_STATUS_PASS = 1;
    public final static byte MODIFY_BASE_ITEM_STATUS_DENY = 2;
    public final static Map<Byte, String> MODIFY_BASE_ITEM_STATUS_MAP = new LinkedHashMap<>();

    static {
        MODIFY_BASE_ITEM_STATUS_MAP.put(MODIFY_BASE_ITEM_STATUS_APPLY, "待审核");
        MODIFY_BASE_ITEM_STATUS_MAP.put(MODIFY_BASE_ITEM_STATUS_PASS, "审核通过");
        MODIFY_BASE_ITEM_STATUS_MAP.put(MODIFY_BASE_ITEM_STATUS_DENY, "审核未通过");
    }

    // 信息修改申请 类别
    public final static byte MODIFY_TABLE_APPLY_TYPE_ADD = 1;
    public final static byte MODIFY_TABLE_APPLY_TYPE_MODIFY = 2;
    public final static byte MODIFY_TABLE_APPLY_TYPE_DELETE = 3;
    public final static Map<Byte, String> MODIFY_TABLE_APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        MODIFY_TABLE_APPLY_TYPE_MAP.put(MODIFY_TABLE_APPLY_TYPE_ADD, "添加");
        MODIFY_TABLE_APPLY_TYPE_MAP.put(MODIFY_TABLE_APPLY_TYPE_MODIFY, "修改");
        MODIFY_TABLE_APPLY_TYPE_MAP.put(MODIFY_TABLE_APPLY_TYPE_DELETE, "删除");
    }

    // 信息修改请求 审核状态，0 待审核 1 审核通过 2 审核未通过 3 管理员删除（待审核时才可以删除）
    public final static byte MODIFY_TABLE_APPLY_STATUS_APPLY = 0;
    public final static byte MODIFY_TABLE_APPLY_STATUS_PASS = 1;
    public final static byte MODIFY_TABLE_APPLY_STATUS_DENY = 2;
    public final static byte MODIFY_TABLE_APPLY_STATUS_DELETE = 3;
    public final static Map<Byte, String> MODIFY_TABLE_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_APPLY, "待审核");
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_PASS, "审核通过");
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_DENY, "审核未通过");
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_DELETE, "已删除");
    }


    // 信息修改申请
    public final static byte RECORD_STATUS_FORMAL = 0; // 正式记录
    public final static byte RECORD_STATUS_MODIFY = 1; // 修改记录

    // 信息修改申请 模块
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_EDU = 1;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_WORK = 2;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK = 3;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY = 4;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE = 5;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER = 6;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME = 7;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH = 9;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH = 11;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER = 12;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN = 10;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT = 8;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN = 13;

    public final static Map<Byte, String> MODIFY_TABLE_APPLY_MODULE_MAP = new LinkedHashMap<>();

    static {
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_EDU, "学习经历");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_WORK, "工作经历");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK, "出版著作情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY, "企业兼职情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE, "教学课程");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER, "发表论文情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME, "社会或学术兼职");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT, "主持科研项目情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN, "参与科研项目情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH, "教学成果及获奖情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH, "科研成果及获奖情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER, "其他奖励情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN, "培训情况");
    }

    //出行时间范围
    public final static byte APPLY_SELF_DATE_TYPE_HOLIDAY = 1;
    public final static byte APPLY_SELF_DATE_TYPE_SCHOOL = 2;
    public final static byte APPLY_SELF_DATE_TYPE_OTHER = 3;
    public final static Map<Byte, String> APPLY_SELF_DATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        APPLY_SELF_DATE_TYPE_MAP.put(APPLY_SELF_DATE_TYPE_HOLIDAY, "公众假期");
        APPLY_SELF_DATE_TYPE_MAP.put(APPLY_SELF_DATE_TYPE_SCHOOL, "寒/暑假");
        APPLY_SELF_DATE_TYPE_MAP.put(APPLY_SELF_DATE_TYPE_OTHER, "其他");
    }

    //行程修改类别
    public final static byte APPLYSELF_MODIFY_TYPE_ORIGINAL = 0;
    public final static byte APPLYSELF_MODIFY_TYPE_MODIFY = 1;
    public final static Map<Byte, String> APPLYSELF_MODIFY_TYPE_MAP = new LinkedHashMap<>();

    static {
        APPLYSELF_MODIFY_TYPE_MAP.put(APPLYSELF_MODIFY_TYPE_ORIGINAL, "首次提交申请");
        APPLYSELF_MODIFY_TYPE_MAP.put(APPLYSELF_MODIFY_TYPE_MODIFY, "行程修改");
    }

    //证件类别 1:集中管理证件 2:取消集中保管证件 3:丢失证件
    public final static byte PASSPORT_TYPE_KEEP = 1;
    public final static byte PASSPORT_TYPE_CANCEL = 2;
    public final static byte PASSPORT_TYPE_LOST = 3;
    public final static Map<Byte, String> PASSPORT_TYPE_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_TYPE_MAP.put(PASSPORT_TYPE_KEEP, "集中管理证件");
        PASSPORT_TYPE_MAP.put(PASSPORT_TYPE_CANCEL, "取消集中保管证件");
        PASSPORT_TYPE_MAP.put(PASSPORT_TYPE_LOST, "丢失证件");
    }

    // 取消集中保管原因 1 证件过期 2 不再担任行政职务 3证件作废 4 其他
    public final static byte PASSPORT_CANCEL_TYPE_EXPIRE = 1;
    public final static byte PASSPORT_CANCEL_TYPE_DISMISS = 2;
    public final static byte PASSPORT_CANCEL_TYPE_ABOLISH = 3;
    public final static byte PASSPORT_CANCEL_TYPE_OTHER = 4;
    public final static Map<Byte, String> PASSPORT_CANCEL_TYPE_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_EXPIRE, "证件过期");
        PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_DISMISS, "不再担任行政职务");
        PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_ABOLISH, "证件作废");
        PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_OTHER, "其他");
    }

    // 丢失来源 1 从集中管理库中转移 2 后台添加
    public final static byte PASSPORT_LOST_TYPE_TRANSFER = 1;
    public final static byte PASSPORT_LOST_TYPE_ADD = 2;
    public final static Map<Byte, String> PASSPORT_LOST_TYPE_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_LOST_TYPE_MAP.put(PASSPORT_LOST_TYPE_TRANSFER, "集中管理证件库");
        PASSPORT_LOST_TYPE_MAP.put(PASSPORT_LOST_TYPE_ADD, "后台添加");
    }

    //领取证件类别
    public final static byte PASSPORT_DRAW_TYPE_SELF = 1;
    public final static byte PASSPORT_DRAW_TYPE_TW = 2;
    public final static byte PASSPORT_DRAW_TYPE_OTHER = 3;
    public final static byte PASSPORT_DRAW_TYPE_LONG_SELF = 4;
    public final static Map<Byte, String> PASSPORT_DRAW_TYPE_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_SELF, "因私出国（境）");
        PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_TW, "因公赴台");
        PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_OTHER, "其他事务");
        PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_LONG_SELF, "长期因公出国");
    }

    //领取证件用途 1 仅签证 2 已签证，本次出境 3 同时签证和出境
    public final static byte PASSPORT_DRAW_USE_TYPE_SIGN = 1;
    public final static byte PASSPORT_DRAW_USE_TYPE_ABROAD = 2;
    public final static byte PASSPORT_DRAW_USE_TYPE_BOTH = 3;
    public final static Map<Byte, String> PASSPORT_DRAW_USE_TYPE_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_DRAW_USE_TYPE_MAP.put(PASSPORT_DRAW_USE_TYPE_SIGN, "仅签证");
        PASSPORT_DRAW_USE_TYPE_MAP.put(PASSPORT_DRAW_USE_TYPE_ABROAD, "已签证，本次出境");
        PASSPORT_DRAW_USE_TYPE_MAP.put(PASSPORT_DRAW_USE_TYPE_BOTH, "同时签证和出境");
    }

    //申办证件审批状态
    public final static byte PASSPORT_APPLY_STATUS_INIT = 0;
    public final static byte PASSPORT_APPLY_STATUS_PASS = 1;
    public final static byte PASSPORT_APPLY_STATUS_NOT_PASS = 2;
    public final static Map<Byte, String> PASSPORT_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_APPLY_STATUS_MAP.put(PASSPORT_APPLY_STATUS_INIT, "待审批");
        PASSPORT_APPLY_STATUS_MAP.put(PASSPORT_APPLY_STATUS_PASS, "批准");
        PASSPORT_APPLY_STATUS_MAP.put(PASSPORT_APPLY_STATUS_NOT_PASS, "未批准");
    }

    //使用证件审批状态
    public final static byte PASSPORT_DRAW_STATUS_INIT = 0;
    public final static byte PASSPORT_DRAW_STATUS_PASS = 1;
    public final static byte PASSPORT_DRAW_STATUS_NOT_PASS = 2;
    public final static Map<Byte, String> PASSPORT_DRAW_STATUS_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_DRAW_STATUS_MAP.put(PASSPORT_DRAW_STATUS_INIT, "待审批");
        PASSPORT_DRAW_STATUS_MAP.put(PASSPORT_DRAW_STATUS_PASS, "审批通过");
        PASSPORT_DRAW_STATUS_MAP.put(PASSPORT_DRAW_STATUS_NOT_PASS, "未通过审批");
    }

    //使用证件领取状态
    public final static byte PASSPORT_DRAW_DRAW_STATUS_UNDRAW = 0;
    public final static byte PASSPORT_DRAW_DRAW_STATUS_DRAW = 1;
    public final static byte PASSPORT_DRAW_DRAW_STATUS_RETURN = 2;
    public final static byte PASSPORT_DRAW_DRAW_STATUS_ABOLISH = 3;
    public final static Map<Byte, String> PASSPORT_DRAW_DRAW_STATUS_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_UNDRAW, "未领取");
        PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_DRAW, "已领取");
        PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_RETURN, "已归还");
        PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_ABOLISH, "已作废");
    }

    // 归还证件处理类别， 因私出国、因公赴台长期（1：持证件出国（境） 0：未持证件出国（境） 2：拒不交回证件）
    //                  处理其他事务（1：违规使用证件出国（境）0：没有使用证件出国（境） 2：拒不交回证件）
    public final static byte PASSPORT_DRAW_USEPASSPORT_UNUSE = 0;
    public final static byte PASSPORT_DRAW_USEPASSPORT_USE = 1;
    public final static byte PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN = 2;
    public final static Map<Byte, String> PASSPORT_DRAW_USEPASSPORT_MAP = new LinkedHashMap<>();

    static {
        PASSPORT_DRAW_USEPASSPORT_MAP.put(PASSPORT_DRAW_USEPASSPORT_USE, "持证件出国（境）/违规使用证件出国（境）");
        PASSPORT_DRAW_USEPASSPORT_MAP.put(PASSPORT_DRAW_USEPASSPORT_UNUSE, "未持证件出国（境）");
        PASSPORT_DRAW_USEPASSPORT_MAP.put(PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN, "拒不交回证件");
    }

    // 因私出国审批人类别  1本单位正职 2分管校领导 3 书记 4 校长
    public final static byte APPROVER_TYPE_UNIT = 1;
    public final static byte APPROVER_TYPE_LEADER = 2;
    public final static byte APPROVER_TYPE_SECRETARY = 3;
    public final static byte APPROVER_TYPE_MASTER = 4;
    public final static byte APPROVER_TYPE_OTHER = 20;
    public final static Map<Byte, String> APPROVER_TYPE_MAP = new LinkedHashMap<>();

    static {
        APPROVER_TYPE_MAP.put(APPROVER_TYPE_UNIT, "本单位正职");
        APPROVER_TYPE_MAP.put(APPROVER_TYPE_LEADER, "分管校领导");
        APPROVER_TYPE_MAP.put(APPROVER_TYPE_SECRETARY, "书记");
        APPROVER_TYPE_MAP.put(APPROVER_TYPE_MASTER, "校长");
        APPROVER_TYPE_MAP.put(APPROVER_TYPE_OTHER, "其他");
    }

    public final static int APPROVER_TYPE_ID_OD_FIRST = -1; // 初审管理员，伪ID
    public final static int APPROVER_TYPE_ID_OD_LAST = 0; // 终审管理员，伪ID

    // 管理员审批类型，0初审，1终审（type_id为null时）
    public final static byte APPROVER_LOG_OD_TYPE_FIRST = 0;
    public final static byte APPROVER_LOG_OD_TYPE_LAST = 1;

    // 因公赴台备案-办理新证件方式，使用组织部函件办理、使用国台办批件办理
    public final static byte TAIWAN_RECORD_HANDLE_TYPE_OW = 1;
    public final static byte TAIWAN_RECORD_HANDLE_TYPE_OFFICE = 2;
    public final static Map<Byte, String> TAIWAN_RECORD_HANDLE_TYPE_MAP = new LinkedHashMap<>();

    static {
        TAIWAN_RECORD_HANDLE_TYPE_MAP.put(TAIWAN_RECORD_HANDLE_TYPE_OW, "使用组织部函件办理");
        TAIWAN_RECORD_HANDLE_TYPE_MAP.put(TAIWAN_RECORD_HANDLE_TYPE_OFFICE, "使用国台办批件办理");
    }

    // 内容模板类别  1 短信
    public final static byte CONTENT_TPL_TYPE_SHORTMSG = 1;
    public final static byte CONTENT_TPL_TYPE_NORMAL = 2;
    public final static Map<Byte, String> CONTENT_TPL_TYPE_MAP = new LinkedHashMap<>();

    static {
        CONTENT_TPL_TYPE_MAP.put(CONTENT_TPL_TYPE_SHORTMSG, "短信");
        CONTENT_TPL_TYPE_MAP.put(CONTENT_TPL_TYPE_NORMAL, "文本");
    }

    // 内容模板内容类型  1 普通文本
    public final static byte CONTENT_TPL_CONTENT_TYPE_STRING = 1;
    public final static byte CONTENT_TPL_CONTENT_TYPE_HTML = 2;
    public final static Map<Byte, String> CONTENT_TPL_CONTENT_TYPE_MAP = new LinkedHashMap<>();

    static {
        CONTENT_TPL_CONTENT_TYPE_MAP.put(CONTENT_TPL_CONTENT_TYPE_STRING, "普通文本");
        CONTENT_TPL_CONTENT_TYPE_MAP.put(CONTENT_TPL_CONTENT_TYPE_HTML, "HTML");
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
    public final static Map<Byte, String> SHORT_MSG_RELATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_CONTENT_TPL, "短信模板");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL, "定向短信");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_PCS, "党代会");
    }

    // 内容模板引擎  1 MessageFormat
    public final static byte CONTENT_TPL_ENGINE_MESSAGEFORMAT = 1;
    public final static Map<Byte, String> CONTENT_TPL_ENGINE_MAP = new LinkedHashMap<>();

    static {
        CONTENT_TPL_ENGINE_MAP.put(CONTENT_TPL_ENGINE_MESSAGEFORMAT, "MessageFormat");
    }

    // 内容模板（与数据库中代码对应）
    public final static String CONTENT_TPL_FIND_PASS = "ct_find_pass"; // 密码找回模板

    public final static String CONTENT_TPL_APPLYSELF_SUBMIT_INFO = "ct_applyself_submit_info"; // 干部提交因私申请，通知管理员
    public final static String CONTENT_TPL_APPLYSELF_PASS_INFO = "ct_applyself_pass_info"; // 干部因私申请通过全部领导审批，通知管理员
    public final static String CONTENT_TPL_PASSPORTDRAW_SUBMIT_INFO = "ct_passportDraw_submit_info"; // 干部提交领取证件，通知管理员
    public final static String CONTENT_TPL_PASSPORT_INFO = "ct_passport_info";
    public final static String CONTENT_TPL_PASSPORT_EXPIRE = "ct_passport_expire";
    public final static String CONTENT_TPL_PASSPORT_DISMISS = "ct_passport_dismiss";
    public final static String CONTENT_TPL_PASSPORT_ABOLISH = "ct_passport_abolish";
    public final static String CONTENT_TPL_PASSPORT_KEEP_ADD = "ct_passport_keep_add";
    public final static String CONTENT_TPL_PASSPORT_KEEP_APPLY = "ct_passport_keep_apply";
    public final static String CONTENT_TPL_APPLYSELF_PASS = "ct_applySelf_pass";
    public final static String CONTENT_TPL_APPLYSELF_UNPASS = "ct_applySelf_unpass";
    public final static String CONTENT_TPL_PASSPORTAPPLY_PASS = "ct_passportApply_pass";
    public final static String CONTENT_TPL_PASSPORTAPPLY_UNPASS = "ct_passportApply_unpass";
    public final static String CONTENT_TPL_PASSPORTAPPLY_DRAW = "ct_passportApply_draw";
    public final static String CONTENT_TPL_TAIWANRECORD_HANDLE = "ct_taiwanrecord_handle";
    public final static String CONTENT_TPL_PASSPORTAPPLY_SUBMIT = "ct_passportApply_submit";
    public final static String CONTENT_TPL_PASSPORTAPPLY_SUBMIT_ADMIN = "ct_passportApply_submit_admin";
    public final static String CONTENT_TPL_PASSPORTDRAW = "ct_passportDraw";
    public final static String CONTENT_TPL_PASSPORTDRAW_RETURN = "ct_passportDraw_return";
    public final static String CONTENT_TPL_PASSPORTDRAW_RETURN_SUCCESS = "ct_passportDraw_return_success";
    public final static String CONTENT_TPL_PASSPORTDRAW_PASS = "ct_passportDraw_pass";
    public final static String CONTENT_TPL_PASSPORTDRAW_PASS_NEEDSIGN = "ct_passportDraw_pass_needsign";
    public final static String CONTENT_TPL_PASSPORTDRAW_UNPASS = "ct_passportDraw_unpass";
    public final static String CONTENT_TPL_PASSPORTDRAW_UNPASS_NEEDSIGN = "ct_passportDraw_unpass_needsign";


    // 本单位正职（一人）审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_1 = "ct_applyself_approval_unit_1";
    // 本单位正职（多人）审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_2 = "ct_applyself_approval_unit_2";
    // 分管校领导审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_LEADER = "ct_applyself_approval_leader";
    // 书记审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_SECRETARY = "ct_applyself_approval_secretary";
    // 校长审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_MASTER = "ct_applyself_approval_master";

    /*******
     * 干部招聘
     *******/
    // 通知1：预通知（没有确定时间和地点）
    public final static String CONTENT_TPL_CRS_MSG_1 = "ct_crs_msg_1";
    // 通知2：预通知（初步确定了时间，未确定地点）
    public final static String CONTENT_TPL_CRS_MSG_2 = "ct_crs_msg_2";
    // 通知3：正式通知（明确了时间和地点）
    public final static String CONTENT_TPL_CRS_MSG_3 = "ct_crs_msg_3";
    // 通知4：招聘会前一天提醒
    public final static String CONTENT_TPL_CRS_MSG_4 = "ct_crs_msg_4";
    // 通知5：招聘会前一小时提醒
    public final static String CONTENT_TPL_CRS_MSG_5 = "ct_crs_msg_5";

    public final static Map<String, String> CRS_SHORT_MSG_TPL_MAP = new LinkedHashMap<>();

    static {
        CRS_SHORT_MSG_TPL_MAP.put(CONTENT_TPL_CRS_MSG_1, "通知1：预通知（没有确定时间和地点）");
        CRS_SHORT_MSG_TPL_MAP.put(CONTENT_TPL_CRS_MSG_2, "通知2：预通知（初步确定了时间，未确定地点）");
        CRS_SHORT_MSG_TPL_MAP.put(CONTENT_TPL_CRS_MSG_3, "通知3：正式通知（明确了时间和地点）");
        CRS_SHORT_MSG_TPL_MAP.put(CONTENT_TPL_CRS_MSG_4, "通知4：招聘会前一天提醒");
        CRS_SHORT_MSG_TPL_MAP.put(CONTENT_TPL_CRS_MSG_5, "通知5：招聘会前一小时提醒");
    }

	/*public final static Map<String, String> SHORT_MSG_KEY_MAP = new LinkedHashMap<>();
    static {
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORT_EXPIRE, "取消集中管理-证件到期");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORT_DISMISS, "取消集中管理-不再担任职务");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_APPLYSELF_PASS, "因私出国申请-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_APPLYSELF_UNPASS, "因私出国申请-不通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_PASS, "申办证件-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_UNPASS, "申办证件-不通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_DRAW, "申办证件-催交证件");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_SUBMIT, "申办证件-提交申请");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW, "因私出国申请-领取证件");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_RETURN, "申请使用证件-催交证件");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_PASS, "申请使用证件-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_PASS_NEEDSIGN, "申请使用证件（需要签注）-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_UNPASS, "申请使用证件-不通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_UNPASS_NEEDSIGN, "申请使用证件（需要签注）-不通过");
	}*/


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

    public final static byte CACHEKEY_PASSPORT_DRAW_TYPE_SELF = 17;
    public final static byte CACHEKEY_PASSPORT_DRAW_TYPE_TW = 18;
    public final static byte CACHEKEY_PASSPORT_DRAW_TYPE_OTHER = 19;
    public final static byte CACHEKEY_PASSPORT_DRAW_TYPE_LONG_SELF = 20;

    public final static byte CACHEKEY_TAIWAN_RECORD_HANDLE_TYPE = 21;

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

        CACHEKEY_MAP.put(CACHEKEY_PASSPORT_DRAW_TYPE_SELF, "因私出国（境）-领取证件");
        CACHEKEY_MAP.put(CACHEKEY_PASSPORT_DRAW_TYPE_TW, "因公赴台-领取证件");
        CACHEKEY_MAP.put(CACHEKEY_PASSPORT_DRAW_TYPE_OTHER, "其他事务-领取证件");
        CACHEKEY_MAP.put(CACHEKEY_PASSPORT_DRAW_TYPE_LONG_SELF, "长期因公出国-领取证件");

        CACHEKEY_MAP.put(CACHEKEY_TAIWAN_RECORD_HANDLE_TYPE, "因公赴台备案-提醒管理员选择办理新证件方式");
    }

    // 审批记录类型
    public final static byte SYS_APPROVAL_LOG_TYPE_APPLYSELF = 1; // 因私出国
    public final static byte SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT = 2; // 干部招聘-报名审核

    public final static Map<Byte, String> SYS_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_APPLYSELF, "因私出国");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT, "干部招聘审核");

    }

    // 操作人类别, 0本人 1 干部管理员 2 因私审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_SELF = 0; // 本人
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_CADRE = 1; // 干部管理员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_APPLYSELF = 2; // 因私审批人员
    public final static byte SYS_APPROVAL_LOG_USER_TYPE_ADMIN = 10;// 后台操作
    public final static Map<Byte, String> SYS_APPROVAL_LOG_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_SELF, "本人");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_CADRE, "干部管理员");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_APPLYSELF, "因私审批人员");
        SYS_APPROVAL_LOG_USER_TYPE_MAP.put(SYS_APPROVAL_LOG_USER_TYPE_ADMIN, "后台操作");
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
