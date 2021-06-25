package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CetConstants {

    //二级党委培训管理员类型
    public final static byte CET_PARTY_ADMIN_SECRETARY= 0;
    public final static byte CET_PARTY_ADMIN_VICE_SECRETARY= 1;
    public final static byte CET_PARTY_ADMIN_COMMITTEE_MEMBER= 2;
    public final static byte CET_PARTY_ADMIN_NORMAL = 3;
    public final static Map<Byte, String> CET_PARTY_ADMIN_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_PARTY_ADMIN_MAP.put(CET_PARTY_ADMIN_SECRETARY, "书记");
        CET_PARTY_ADMIN_MAP.put(CET_PARTY_ADMIN_VICE_SECRETARY, "副书记");
        CET_PARTY_ADMIN_MAP.put(CET_PARTY_ADMIN_COMMITTEE_MEMBER, "委员");
        CET_PARTY_ADMIN_MAP.put(CET_PARTY_ADMIN_NORMAL, "其他管理员");
    }

    //补录状态
    public final static byte CET_UNITTRAIN_RERECORD_PASS = 0;
    public final static byte CET_UNITTRAIN_RERECORD_PARTY = 1;
    public final static byte CET_UNITTRAIN_RERECORD_CET = 2;
    public final static byte CET_UNITTRAIN_RERECORD_SAVE = 3;
    public final static Map<Byte, String> CET_UNITTRAIN_RERECORD_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_UNITTRAIN_RERECORD_MAP.put(CET_UNITTRAIN_RERECORD_PASS, "审核通过");
        CET_UNITTRAIN_RERECORD_MAP.put(CET_UNITTRAIN_RERECORD_PARTY, "待二级党委审批");
        CET_UNITTRAIN_RERECORD_MAP.put(CET_UNITTRAIN_RERECORD_CET, "待组织部审批");
        CET_UNITTRAIN_RERECORD_MAP.put(CET_UNITTRAIN_RERECORD_SAVE, "暂存");
    }

    //上级调训-组织部派出-添加方式
    public final static byte CET_UPPERTRAIN_AU_TYPE_SINGLE = 0;
    public final static byte CET_UPPERTRAIN_AU_TYPE_BATCH = 1;
    public final static Map<Byte, String> CET_UPPERTRAIN_AU_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_UPPERTRAIN_AU_TYPE_MAP.put(CET_UPPERTRAIN_AU_TYPE_SINGLE, "个别添加");
        CET_UPPERTRAIN_AU_TYPE_MAP.put(CET_UPPERTRAIN_AU_TYPE_BATCH, "批量导入");
    }

    // 负责单位类型（分组讨论），1 党委组织部 2 内设机构  3 院系级党委 4 二级党校
    public final static byte CET_DISCUSS_UNIT_TYPE_OW = 1;
    public final static byte CET_DISCUSS_UNIT_TYPE_UNIT = 2;
    public final static byte CET_DISCUSS_UNIT_TYPE_PARTY = 3;
    public final static byte CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL = 4;
    public static Map<Byte, String> CET_DISCUSS_UNIT_TYPE_MAP = new LinkedHashMap<Byte, String>();
    static {
        CET_DISCUSS_UNIT_TYPE_MAP.put(CET_DISCUSS_UNIT_TYPE_OW, "党委组织部");
        CET_DISCUSS_UNIT_TYPE_MAP.put(CET_DISCUSS_UNIT_TYPE_UNIT, "内设机构");
        CET_DISCUSS_UNIT_TYPE_MAP.put(CET_DISCUSS_UNIT_TYPE_PARTY, "院系级党委");
        CET_DISCUSS_UNIT_TYPE_MAP.put(CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL, "二级党校");
    }

    // 专家类型， 1 校内专家 2 校外专家
    public final static byte CET_EXPERT_TYPE_IN = 1;
    public final static byte CET_EXPERT_TYPE_OUT = 2;
    public static Map<Byte, String> CET_EXPERT_TYPE_MAP = new LinkedHashMap<Byte, String>();
    static {
        CET_EXPERT_TYPE_MAP.put(CET_EXPERT_TYPE_IN, "校内专家");
        CET_EXPERT_TYPE_MAP.put(CET_EXPERT_TYPE_OUT, "校外专家");
    }

    // 培训类型， 1 专题培训 2 日常培训
    public final static byte CET_PROJECT_TYPE_SPECIAL = 1;
    public final static byte CET_PROJECT_TYPE_DAILY = 2;
    public static Map<Byte, String> CET_PROJECT_TYPE_MAP = new LinkedHashMap<Byte, String>();
    static {
        CET_PROJECT_TYPE_MAP.put(CET_PROJECT_TYPE_SPECIAL, "专题培训");
        CET_PROJECT_TYPE_MAP.put(CET_PROJECT_TYPE_DAILY, "日常培训");
    }

    public final static byte CET_PROJECT_TYPE_CLS_1 = 1;
    public final static byte CET_PROJECT_TYPE_CLS_2 = 2;
    public final static byte CET_PROJECT_TYPE_CLS_3 = 3;
    public final static byte CET_PROJECT_TYPE_CLS_4 = 4;

    // 专题培训-培训形式，1 线下培训  2 线上培训  3 上级网上专题班  4 分组研讨  5 实践教学  6 自主学习  7 其他单位主办  8 撰写心得体会
    public final static byte CET_PROJECT_PLAN_TYPE_OFFLINE = 1;
    public final static byte CET_PROJECT_PLAN_TYPE_ONLINE = 2;
    public final static byte CET_PROJECT_PLAN_TYPE_SPECIAL = 3;
    public final static byte CET_PROJECT_PLAN_TYPE_GROUP = 4;
    public final static byte CET_PROJECT_PLAN_TYPE_PRACTICE = 5;
    public final static byte CET_PROJECT_PLAN_TYPE_SELF = 6;
    //public final static byte CET_PROJECT_PLAN_TYPE_OTHER_UNIT = 7;
    public final static byte CET_PROJECT_PLAN_TYPE_WRITE = 8;
    public final static Map<Byte, String> CET_PROJECT_PLAN_TYPE_MAP = new LinkedHashMap();
    static {
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_OFFLINE, "线下培训");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_ONLINE, "线上培训");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_SPECIAL, "上级网上专题班");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_GROUP, "分组研讨");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_PRACTICE, "实践教学");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_SELF, "自主学习");
        /*CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_OTHER_UNIT, "其他单位主办");*/
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_WRITE, "撰写心得体会");
    }

    // 课程中心 类型，0 线下课程 1 线上课程 2 自主学习 3 实践教学 4 网上专题培训班
    public final static byte CET_COURSE_TYPE_OFFLINE = 0;
    public final static byte CET_COURSE_TYPE_ONLINE = 1;
    public final static byte CET_COURSE_TYPE_SELF = 2;
    public final static byte CET_COURSE_TYPE_PRACTICE = 3;
    public final static byte CET_COURSE_TYPE_SPECIAL = 4;
    public static Map<Byte, String> CET_COURSE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_COURSE_TYPE_MAP.put(CET_COURSE_TYPE_OFFLINE, "线下课程");
        CET_COURSE_TYPE_MAP.put(CET_COURSE_TYPE_ONLINE, "线上课程");
        CET_COURSE_TYPE_MAP.put(CET_COURSE_TYPE_SELF, "自主学习");
        CET_COURSE_TYPE_MAP.put(CET_COURSE_TYPE_PRACTICE, "实践教学");
        CET_COURSE_TYPE_MAP.put(CET_COURSE_TYPE_SPECIAL, "网上专题培训班");
    }

    // 培训课程 选课/退课状态，0：由培训班的选课时间决定  1： 已关闭选课  2： 已关闭退课  3： 已关闭选课和退课
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT = 0;
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY = 1;
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT = 2;
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL = 3;
    public static Map<Byte, String> CET_TRAIN_COURSE_APPLY_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT, "与选课时间一致");
        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY, "单独关闭选课");
        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT, "单独关闭退课");
        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL, "关闭选课和退课");
    }

    // 签到类型， 1 手动签到 2 批量导入 3 刷卡签到
    public final static byte CET_TRAINEE_SIGN_TYPE_MANUAL = 1;
    public final static byte CET_TRAINEE_SIGN_TYPE_IMPORT = 2;
    public final static byte CET_TRAINEE_SIGN_TYPE_CARD = 3;
    public final static byte CET_TRAINEE_SIGN_TYPE_QRCODE = 4;
    public static Map<Byte, String> CET_TRAINEE_SIGN_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_MANUAL, "手动签到");
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_IMPORT, "批量导入");
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_CARD, "刷卡签到");
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_QRCODE, "二维码签到");
    }

    // 干部培训 评课账号的状态
    public final static byte CET_TRAIN_INSPECTOR_STATUS_INIT = 0;
    public final static byte CET_TRAIN_INSPECTOR_STATUS_ABOLISH = 1;
    public static Map<Byte, String> CET_TRAIN_INSPECTOR_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_INSPECTOR_STATUS_MAP.put(CET_TRAIN_INSPECTOR_STATUS_INIT, "未完成");
        CET_TRAIN_INSPECTOR_STATUS_MAP.put(CET_TRAIN_INSPECTOR_STATUS_ABOLISH, "已作废");
    }

    // 干部培训 评课账号的某门课程的评课状态
    public final static byte CET_TRAIN_INSPECTOR_COURSE_STATUS_SAVE = 0;
    public final static byte CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH = 1;
    public static Map<Byte, String> CET_TRAIN_INSPECTOR_COURSE_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_INSPECTOR_COURSE_STATUS_MAP.put(CET_TRAIN_INSPECTOR_COURSE_STATUS_SAVE, "暂存");
        CET_TRAIN_INSPECTOR_COURSE_STATUS_MAP.put(CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH, "已完成");
    }

    // 评课账号修改密码类型
    public final static byte CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF = 1;
    public final static byte CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET = 2;
    public static Map<Byte, String> CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF, "本人修改");
        CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP.put(CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET, "管理员重置");
    }


    // 上级调训类型，1 组织部派出  2 其他部门派出  8 出国研修
    public final static byte CET_UPPER_TRAIN_TYPE_OW = 1;
    public final static byte CET_UPPER_TRAIN_TYPE_UNIT = 2;
    public final static byte CET_UPPER_TRAIN_TYPE_ABROAD = 8;
    public final static byte CET_UPPER_TRAIN_TYPE_SCHOOL = 10; // 党校其他培训

    // 上级调训 审批状态，0 待审批 1 审批通过 2 审批不通过
    public final static byte CET_UPPER_TRAIN_STATUS_INIT = 0; // 待单位审批
    public final static byte CET_UPPER_TRAIN_STATUS_PASS = 1; // 单位审批通过，isValid->待组织部确认
    public final static byte CET_UPPER_TRAIN_STATUS_UNPASS = 2;
    public static Map<Byte, String> CET_UPPER_TRAIN_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_UPPER_TRAIN_STATUS_MAP.put(CET_UPPER_TRAIN_STATUS_INIT, "待审批");
        CET_UPPER_TRAIN_STATUS_MAP.put(CET_UPPER_TRAIN_STATUS_PASS, "审批通过");
        CET_UPPER_TRAIN_STATUS_MAP.put(CET_UPPER_TRAIN_STATUS_UNPASS, "审批不通过");
    }

    // 上级调训 添加类型，1 本人填写 2 单位填写 3 组织部填写
    public final static byte CET_UPPER_TRAIN_ADD_TYPE_SELF = 1;
    public final static byte CET_UPPER_TRAIN_ADD_TYPE_UNIT = 2;
    public final static byte CET_UPPER_TRAIN_ADD_TYPE_OW = 3;
    public static Map<Byte, String> CET_UPPER_TRAIN_ADD_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_UPPER_TRAIN_ADD_TYPE_MAP.put(CET_UPPER_TRAIN_ADD_TYPE_SELF, "本人填写");
        CET_UPPER_TRAIN_ADD_TYPE_MAP.put(CET_UPPER_TRAIN_ADD_TYPE_UNIT, "单位填写");
        CET_UPPER_TRAIN_ADD_TYPE_MAP.put(CET_UPPER_TRAIN_ADD_TYPE_OW, "组织部填写");
    }

    // 二级党委培训 审批状态，0 待报送 1 已报送 2 审批通过 3 审批未通过（退回） 4 已删除
    public final static byte CET_UNIT_PROJECT_STATUS_UNREPORT = 0;
    public final static byte CET_UNIT_PROJECT_STATUS_REPORT = 1;
    public final static byte CET_UNIT_PROJECT_STATUS_PASS = 2;
    public final static byte CET_UNIT_PROJECT_STATUS_UNPASS = 3;
    public final static byte CET_UNIT_PROJECT_STATUS_DELETE = 4;
    public static Map<Byte, String> CET_UNIT_PROJECT_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_UNIT_PROJECT_STATUS_MAP.put(CET_UNIT_PROJECT_STATUS_UNREPORT, "未报送");
        CET_UNIT_PROJECT_STATUS_MAP.put(CET_UNIT_PROJECT_STATUS_REPORT, "已报送");
        CET_UNIT_PROJECT_STATUS_MAP.put(CET_UNIT_PROJECT_STATUS_PASS, "审批通过");
        CET_UNIT_PROJECT_STATUS_MAP.put(CET_UNIT_PROJECT_STATUS_UNPASS, "审批不通过");
        CET_UNIT_PROJECT_STATUS_MAP.put(CET_UNIT_PROJECT_STATUS_DELETE, "已删除");
    }

    // 过程培训 审批状态，0 待报送 1 已报送 2 审批通过 3 审批未通过（退回）
    public final static byte CET_PROJECT_STATUS_UNREPORT = 0;
    public final static byte CET_PROJECT_STATUS_REPORT = 1;
    public final static byte CET_PROJECT_STATUS_PASS = 2;
    public final static byte CET_PROJECT_STATUS_UNPASS = 3;
    public static Map<Byte, String> CET_PROJECT_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_UNREPORT, "未报送");
        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_REPORT, "已报送");
        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_PASS, "审批通过");
        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_UNPASS, "审批不通过");
    }

    // 培训类型（用于年度学习档案统计）
    public final static byte CET_TYPE_SPECIAL = 1; // CET_PROJECT_TYPE_SPECIAL
    public final static byte CET_TYPE_DAILY = 2; // CET_PROJECT_TYPE_DAILY
    public final static byte CET_TYPE_PARTY_SPECIAL = 3;
    public final static byte CET_TYPE_PARTY_DAILY = 5;
    public final static byte CET_TYPE_UPPER = 4;
    public static Map<Byte, String> CET_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_TYPE_MAP.put(CET_TYPE_UPPER, "上级调训");
        CET_TYPE_MAP.put(CET_TYPE_SPECIAL, "党校专题培训");
        CET_TYPE_MAP.put(CET_TYPE_DAILY, "党校日常培训");
        CET_TYPE_MAP.put(CET_TYPE_PARTY_SPECIAL, "二级党委专题培训");
        CET_TYPE_MAP.put(CET_TYPE_PARTY_DAILY, "二级党委日常培训");
    }

    // 培训记录来源类别（用于年度学习档案统计），1 上级调训  2 过程培训 3 二级党校历史培训
    public final static byte CET_SOURCE_TYPE_UPPER = 1; // 上级调训 cet_upper_train
    public final static byte CET_SOURCE_TYPE_PROJECT = 2;  // 过程培训  cet_project_obj
    public final static byte CET_SOURCE_TYPE_UNIT = 3; // 二级党校历史培训 cet_unit_train

    // 培训记录转移类型  1 ： 党校其他培训  2：二级党委培训
    public final static byte CET_TYPE_T_PARTY_SCHOOL = 1;
    public final static byte CET_TYPE_T_PARTY = 2;
    public final static Map<Byte, String> CET_TYPE_T_MAP = new LinkedHashMap<Byte, String>();
    static {
        CET_TYPE_T_MAP.put(CET_TYPE_T_PARTY_SCHOOL, "党校培训");
        CET_TYPE_T_MAP.put(CET_TYPE_T_PARTY, "二级党委培训");
    }

    // 参训人员类别
    public final static byte CET_USER_TYPE_TEACHER = 1; //教工
    public final static byte CET_USER_TYPE_STUDENT = 2; //学生
    public final static Map<Byte, String> CET_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        CET_USER_TYPE_MAP.put(CET_USER_TYPE_TEACHER, "教职工");
        CET_USER_TYPE_MAP.put(CET_USER_TYPE_STUDENT, "学生");
    }

    //选课人员签到情况
    public final static byte CET_FINISHED_STATUS_NOT = 0;//未签到
    public final static byte CET_FINISHED_STATUS_YES = 1;//已签到
    public final static byte CET_FINISHED_STATUS_REST = 2;//已请假
    public final static Map<Byte, String> CET_FINISHED_STATUS_MAP = new LinkedHashMap<>();

    static {
        CET_FINISHED_STATUS_MAP.put(CET_FINISHED_STATUS_NOT, "未签到");
        CET_FINISHED_STATUS_MAP.put(CET_FINISHED_STATUS_YES, "已签到");
        CET_FINISHED_STATUS_MAP.put(CET_FINISHED_STATUS_REST, "已请假");
    }
}
