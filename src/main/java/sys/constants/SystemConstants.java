package sys.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemConstants {

    public final static Map<String, String> appKeyMap = new HashMap<>();

    static {

        appKeyMap.put("LXXT", "7507a3c61bf38d9f06d00c3f2fa2de58");
        appKeyMap.put("oa", "b887e286bf5d82b7b9712ed03d3e6e0e");
        appKeyMap.put("zcdy", "5931e054d3b59be97b3481f6e604afe6");
    }

    public static Map loginFailedResultMap(String message) {

        Map<String, Object> resultMap = new HashMap();
        resultMap.put("success", false);
        if ("SystemClosedException".equals(message)) {

            resultMap.put("msg", "参评人员测评未开启");
        } else if ("NeedCASLoginException".equals(message)) {
            resultMap.put("msg", "请点击【单点登录】按钮进行登录");
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
            resultMap.put("msg", "单点登录服务器异常，请稍后重试");
        } else {
            resultMap.put("msg", "系统错误");
        }
        return resultMap;
    }

    public final static Map<String, String> FOREIN_KEY_DEL_MSG_MAP = new LinkedHashMap<>();

    static {
        FOREIN_KEY_DEL_MSG_MAP.put("cet_course", "该课程已被使用， 不可删除。");
    }

    // 字符串分隔符（正则）
    public static final String STRING_SEPARTOR = ",|，|;|；|、|\\|";
    // 账号的角色字符串分隔符
    public static final String USER_ROLEIDS_SEPARTOR = ",";

    // 系统特殊的权限（与数据库对应）
    public static final String PERMISSION_CADREARCHIVE = "cadre:archive"; // 干部档案查看权限
    public static final String PERMISSION_CADREADMIN = "cadre:admin";
    public static final String PERMISSION_CADREADMINSELF = "cadre:adminSelf";
    public static final String PERMISSION_CADREONLYVIEW = "cadre:onlyView"; // 仅允许查看干部信息的权限

    public static final String PERMISSION_PARTYMEMBERARCHIVE = "partyMember:archive"; // 基础党组织成员档案查看权限

    public static final String PERMISSION_PARTYVIEWALL = "party:viewAll"; // 查看所有党委、支部的权限
    public static final String PERMISSION_DPPARTYVIEWALL = "dp:viewAll"; //查看民主党派的权限

    public static final String PERMISSION_PMDVIEWALL = "pmd:viewAll"; // 党费收缴查看所有党委、支部的权限

    public static final String PERMISSION_ABROADADMIN = "abroad:admin"; // 因私管理员权限

    public static final String PERMISSION_CLAADMIN = "cla:admin"; // 请假管理员权限

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
    public static final byte LOGIN_TYPE_APP = 9;
    public static final byte LOGIN_TYPE_WX = 10;
    public static final byte LOGIN_TYPE_SWITCH = 20;
    public static final byte LOGIN_TYPE_DR = 30;
    public static final byte LOGIN_TYPE_PCS = 40;
    public final static Map<Byte, String> LOGIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_NET, "网站");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_NET_REMEBERME, "下次自动登录");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_CAS, "单点登录");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_MOBILE, "移动设备");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_TRAIN_INSPECTOR, "评课账号登录");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_APP, "APP");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_WX, "微信");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_SWITCH, "切换账号");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_DR, "民主推荐登录");
        LOGIN_TYPE_MAP.put(LOGIN_TYPE_PCS, "党代会投票登录");
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

    // 定时任务状态, 1 jobToBeExecuted 2 jobExecutionVetoed 3 jobWasExecuted
    public static final byte SCHEDULER_JOB_TOBEEXECUTED = 1;
    public static final byte SCHEDULER_JOB_EXECUTIONVETOED = 2;
    public static final byte SCHEDULER_JOB_WASEXECUTED = 3;
    public final static Map<Byte, String> SCHEDULER_JOB_MAP = new LinkedHashMap<>();

    static {
        SCHEDULER_JOB_MAP.put(SCHEDULER_JOB_TOBEEXECUTED, "准备执行");
        SCHEDULER_JOB_MAP.put(SCHEDULER_JOB_EXECUTIONVETOED, "已取消");
        SCHEDULER_JOB_MAP.put(SCHEDULER_JOB_WASEXECUTED, "执行完毕");
    }

    // 性别， 1男 2女
    public static final byte GENDER_MALE = 1;
    public static final byte GENDER_FEMALE = 2;
    public final static Map<Byte, String> GENDER_MAP = new LinkedHashMap<>();

    static {
        GENDER_MAP.put(GENDER_MALE, "男");
        GENDER_MAP.put(GENDER_FEMALE, "女");
    }

    //单位状态，1正在运转单位、2历史单位
    public static final byte UNIT_STATUS_RUN = 1;
    public static final byte UNIT_STATUS_HISTORY = 2;

    public final static Map<Byte, String> UNIT_STATUS_MAP = new LinkedHashMap<>();

    static {
        UNIT_STATUS_MAP.put(UNIT_STATUS_RUN, "正在运转单位");
        UNIT_STATUS_MAP.put(UNIT_STATUS_HISTORY, "历史单位");
    }

    //岗位状态，1 正常 2 已撤销 3 已删除
    public static final byte UNIT_POST_STATUS_NORMAL = 1;
    public static final byte UNIT_POST_STATUS_ABOLISH = 2;
    public static final byte UNIT_POST_STATUS_DELETE = 3;

    //岗位是否班子负责人，0 否 1 党委班子负责人 2 行政班子负责人
    public static final byte UNIT_POST_LEADER_TYPE_DW = 1;
    public static final byte UNIT_POST_LEADER_TYPE_XZ = 2;
    public static final byte UNIT_POST_LEADER_TYPE_NOT = 0;
    public final static Map<Byte, String> UNIT_POST_LEADER_TYPE_MAP = new LinkedHashMap<>();

    static {
        UNIT_POST_LEADER_TYPE_MAP.put(UNIT_POST_LEADER_TYPE_DW, "党委班子负责人");
        UNIT_POST_LEADER_TYPE_MAP.put(UNIT_POST_LEADER_TYPE_XZ, "行政班子负责人");
        UNIT_POST_LEADER_TYPE_MAP.put(UNIT_POST_LEADER_TYPE_NOT, "否");
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

    // 学位类型，1 学士 2 硕士 3 博士（仅三类，不允许添加）
    public final static byte DEGREE_TYPE_XS = 1;
    public final static byte DEGREE_TYPE_SS = 2;
    public final static byte DEGREE_TYPE_BS = 3;
    public final static Map<Byte, String> DEGREE_TYPE_MAP = new LinkedHashMap();

    static {
        DEGREE_TYPE_MAP.put(DEGREE_TYPE_XS, "学士");
        DEGREE_TYPE_MAP.put(DEGREE_TYPE_SS, "硕士");
        DEGREE_TYPE_MAP.put(DEGREE_TYPE_BS, "博士");
    }

    // 职称级别，1 初级 2 中级 3 副高 4 正高
    public final static byte PRO_POST_LEVEL_CJ = 1;
    public final static byte PRO_POST_LEVEL_ZJ = 2;
    public final static byte PRO_POST_LEVEL_FG = 3;
    public final static byte PRO_POST_LEVEL_ZG = 4;
    public final static Map<Byte, String> PRO_POST_LEVEL_MAP = new LinkedHashMap();

    static {
        PRO_POST_LEVEL_MAP.put(PRO_POST_LEVEL_CJ, "初级");
        PRO_POST_LEVEL_MAP.put(PRO_POST_LEVEL_ZJ, "中级");
        PRO_POST_LEVEL_MAP.put(PRO_POST_LEVEL_FG, "副高");
        PRO_POST_LEVEL_MAP.put(PRO_POST_LEVEL_ZG, "正高");
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

    // 同步类型，1人事库 2研究库 3本科生库 4教职工党员出国信息库 5 离退休人员党费计算基数 6教职工工资
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
        SYNC_TYPE_MAP.put(SYNC_TYPE_RETIRE_SALARY, "离退休人员党费计算基数");
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
        USER_SOURCE_MAP.put(USER_SOURCE_JZG, "人事库");
        USER_SOURCE_MAP.put(USER_SOURCE_BKS, "本科生库");
        USER_SOURCE_MAP.put(USER_SOURCE_YJS, "研究生库");
        USER_SOURCE_MAP.put(USER_SOURCE_ADMIN, "后台创建");
        USER_SOURCE_MAP.put(USER_SOURCE_REG, "用户注册");
    }


    // 打印类别
    public final static byte JASPER_PRINT_TYPE_LETTER_PRINT = 1;
    public final static byte JASPER_PRINT_TYPE_LETTER_FILL_PRINT = 2;
    public final static byte JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD = 3;
    public final static byte JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL = 4;
    public final static byte JASPER_PRINT_TYPE_MEMBER_CERTIFY = 5;
    public final static Map<Byte, String> JASPER_PRINT_TYPE_MAP = new LinkedHashMap<>();

    static {
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_LETTER_PRINT, "介绍信打印");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_LETTER_FILL_PRINT, "介绍信套打");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD, "出国境组织关系暂留审批表");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL, "国内组织关系暂留审批表");
        JASPER_PRINT_TYPE_MAP.put(JASPER_PRINT_TYPE_MEMBER_CERTIFY, "打印组织关系介绍信");
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
    public final static byte SHORT_MSG_RELATE_TYPE_SHORT_SC_PASSPORT = 6; // 干部选拔任用-新任干部提交证件

    public final static byte SHORT_MSG_RELATE_TYPE_SHORT_CR = 10; // 干部招聘
    public final static Map<Byte, String> SHORT_MSG_RELATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_CONTENT_TPL, "消息模板");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL, "定向消息");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_PCS, "党代会");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_OA, "协同办公");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_CET, "干部教育培训");
        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_SC_PASSPORT, "干部选拔任用-新任干部提交证件");

        SHORT_MSG_RELATE_TYPE_MAP.put(SHORT_MSG_RELATE_TYPE_SHORT_CR, "干部招聘");
    }

    // 系统资源类型，备用字段，1 字符串 2 整数 3 布尔值 4 时间 5 图片
    public final static byte SYS_PROPERTY_TYPE_STRING = 1;
    public final static byte SYS_PROPERTY_TYPE_INT = 2;
    public final static byte SYS_PROPERTY_TYPE_BOOL = 3;
    public final static byte SYS_PROPERTY_TYPE_DATE = 4;
    public final static byte SYS_PROPERTY_TYPE_PIC = 5;

    public final static Map<Byte, String> SYS_PROPERTY_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_PROPERTY_TYPE_MAP.put(SYS_PROPERTY_TYPE_STRING, "字符串");
        SYS_PROPERTY_TYPE_MAP.put(SYS_PROPERTY_TYPE_INT, "整数");
        SYS_PROPERTY_TYPE_MAP.put(SYS_PROPERTY_TYPE_BOOL, "布尔值");
        SYS_PROPERTY_TYPE_MAP.put(SYS_PROPERTY_TYPE_DATE, "时间");
        SYS_PROPERTY_TYPE_MAP.put(SYS_PROPERTY_TYPE_PIC, "图片");
    }

    // 操作记录类型
    public final static byte SYS_APPROVAL_LOG_TYPE_APPLYSELF = 1; // 因私出国
    public final static byte SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT = 2; // 干部招聘-报名审核
    public final static byte SYS_APPROVAL_LOG_TYPE_PMD_MEMBER = 3; // 党费收缴
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_OBJ = 4; // 干部教育培训-参训人员
    public final static byte SYS_APPROVAL_LOG_TYPE_CLA_APPLY = 6; // 干部请假审批人员
    public final static byte SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW = 7; // 领取证件
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN = 8; // 上级调训
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_ANNUAL = 9; // 年度学习档案
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN = 10; // 二级党委培训
    public final static byte SYS_APPROVAL_LOG_TYPE_CET_PROJECT = 11; // 过程培训
    public final static byte SYS_APPROVAL_LOG_TYPE_CR_APPLICANT = 12; // 干部招聘2-报名审核
    public final static byte SYS_APPROVAL_LOG_TYPE_OA_GRID_PARTY = 13;//党统
    public final static byte SYS_APPROVAL_LOG_PM = 20;//三会一课操作
    public final static byte SYS_DP_LOG_TYPE_PARTY = 21;//民主党派操作

    public final static Map<Byte, String> SYS_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_APPLYSELF, "因私出国境审批");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT, "干部招聘");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CR_APPLICANT, "干部招聘2");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_PMD_MEMBER, "党费收缴");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_OBJ, "参训人员");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CLA_APPLY, "干部请假");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW, "领取证件审批");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN, "上级调训");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_ANNUAL, "年度学习档案");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN, "二级党委培训");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_CET_PROJECT, "过程培训");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_TYPE_OA_GRID_PARTY, "党统报送数据");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_APPROVAL_LOG_PM, "三会一课");
        SYS_APPROVAL_LOG_TYPE_MAP.put(SYS_DP_LOG_TYPE_PARTY, "民主党派");
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

    //民主党派操作
    public final static byte SYS_DP_LOG_type_ADD = 0;//添加
    public final static byte SYS_DP_LOG_type_EDIT = 1;//修改
    public final static byte SYS_DP_LOG_type_DEL = 2;//删除
    public final static byte SYS_DP_LOG_type_IMPORT = 3;//导入
    public final static byte SYS_DP_LOG_type_EXPORT = 4;//导出
    public final static byte SYS_DP_LOG_type_CANCEL = 5;//撤销
    public final static byte SYS_DP_LOG_type_RECOVER = 6;//恢复
    public final static Map<Byte, String> SYS_DP_LOG_type_MAP =new LinkedHashMap<>();

    static {
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_ADD, "添加");
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_EDIT, "修改");
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_DEL, "删除");
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_IMPORT, "导入");
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_EXPORT, "导出");
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_CANCEL, "撤销");
        SYS_DP_LOG_type_MAP.put(SYS_DP_LOG_type_RECOVER, "恢复");
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
        SYS_APPROVAL_LOG_STATUS_MAP.put(SYS_APPROVAL_LOG_STATUS_BACK, "退回");
        SYS_APPROVAL_LOG_STATUS_MAP.put(SYS_APPROVAL_LOG_STATUS_NONEED, "-");
    }

    // 年度类型所属模块
    public final static byte ANNUAL_TYPE_MODULE_SUBSIDY = 1; // 干部津贴变动文件 文号
    public final static Map<Byte, String> ANNUAL_TYPE_MODULE_MAP = new LinkedHashMap<>();

    static {
        ANNUAL_TYPE_MODULE_MAP.put(ANNUAL_TYPE_MODULE_SUBSIDY, "文号");
    }

    //系统提醒状态 1.未确认 2.已确认
    public final static byte SYS_MSG_STATUS_UNCONFIRM = 1;
    public final static byte SYS_MSG_STATUS_CONFIRM = 2;
    public final static Map<Byte, String> SYS_MSG_STATUS_MAP = new LinkedHashMap<>();

    static {
        SYS_MSG_STATUS_MAP.put(SYS_MSG_STATUS_UNCONFIRM,"未确认");
        SYS_MSG_STATUS_MAP.put(SYS_MSG_STATUS_CONFIRM,"已确认");
    }

    //角色类型 1.加权限 2.减权限
    public final static byte SYS_ROLE_TYPE_ADD = 1;
    public final static byte SYS_ROLE_TYPE_MINUS = 2;
    public final static Map<Byte, String> SYS_ROLE_TYPE_MAP = new LinkedHashMap<>();

    static {
        SYS_ROLE_TYPE_MAP.put(SYS_ROLE_TYPE_ADD,"加权限");
        SYS_ROLE_TYPE_MAP.put(SYS_ROLE_TYPE_MINUS,"减权限");
    }

    //干部配备一览表显示 1.原版 2.包含空岗 3.不占职数（无行政级别）
    public static final byte UNIT_POST_DISPLAY_NORMAL = 1;
    public static final byte UNIT_POST_DISPLAY_KEEP = 2;
    public static final byte UNIT_POST_DISPLAY_NOT_CPC = 3;
    public final static Map<Byte, String> UNIT_POST_DISPLAY_MAP = new LinkedHashMap<>();

    static {
        UNIT_POST_DISPLAY_MAP.put(UNIT_POST_DISPLAY_NORMAL, "原版");
        UNIT_POST_DISPLAY_MAP.put(UNIT_POST_DISPLAY_KEEP, "包含空岗");
        UNIT_POST_DISPLAY_MAP.put(UNIT_POST_DISPLAY_NOT_CPC, "不占职数（无行政级别");
    }

}
