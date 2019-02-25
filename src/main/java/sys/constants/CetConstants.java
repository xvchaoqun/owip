package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CetConstants {

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

    // 培训类型， 1 专题培训 2 年度培训
    public final static byte CET_PROJECT_TYPE_ZT = 1;
    public final static byte CET_PROJECT_TYPE_ND = 2;
    public static Map<Byte, String> CET_PROJECT_TYPE_MAP = new LinkedHashMap<Byte, String>();
    static {
        CET_PROJECT_TYPE_MAP.put(CET_PROJECT_TYPE_ZT, "专题培训");
        CET_PROJECT_TYPE_MAP.put(CET_PROJECT_TYPE_ND, "年度培训");
    }

    // 专题培训-培训形式，1 线下培训  2 线上培训  3 上级网上专题班  4 分组研讨  5 实践教学  6 自主学习  7 其他单位主办  8 撰写心得体会
    public final static byte CET_PROJECT_PLAN_TYPE_OFFLINE = 1;
    public final static byte CET_PROJECT_PLAN_TYPE_ONLINE = 2;
    public final static byte CET_PROJECT_PLAN_TYPE_SPECIAL = 3;
    public final static byte CET_PROJECT_PLAN_TYPE_GROUP = 4;
    public final static byte CET_PROJECT_PLAN_TYPE_PRACTICE = 5;
    public final static byte CET_PROJECT_PLAN_TYPE_SELF = 6;
    public final static byte CET_PROJECT_PLAN_TYPE_OTHER_UNIT = 7;
    public final static byte CET_PROJECT_PLAN_TYPE_WRITE = 8;
    public final static Map<Byte, String> CET_PROJECT_PLAN_TYPE_MAP = new LinkedHashMap();
    static {
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_OFFLINE, "线下培训");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_ONLINE, "线上培训");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_SPECIAL, "上级网上专题班");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_GROUP, "分组研讨");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_PRACTICE, "实践教学");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_SELF, "自主学习");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_OTHER_UNIT, "其他单位主办");
        CET_PROJECT_PLAN_TYPE_MAP.put(CET_PROJECT_PLAN_TYPE_WRITE, "撰写心得体会");
    }
    // 参训人员类型模板
    public final static byte CET_TRAINEE_TYPE_TEMPLATE_A = 1;
    public final static byte CET_TRAINEE_TYPE_TEMPLATE_B = 2;
    public final static byte CET_TRAINEE_TYPE_TEMPLATE_C = 3;
    public final static Map<Byte, String> CET_TRAINEE_TYPE_TEMPLATE_MAP = new LinkedHashMap();

    static {
        CET_TRAINEE_TYPE_TEMPLATE_MAP.put(CET_TRAINEE_TYPE_TEMPLATE_A, "模板A（干部、后备干部）");
        CET_TRAINEE_TYPE_TEMPLATE_MAP.put(CET_TRAINEE_TYPE_TEMPLATE_B, "模板B（分党委班子成员、党支部班子成员、组织员）");
        CET_TRAINEE_TYPE_TEMPLATE_MAP.put(CET_TRAINEE_TYPE_TEMPLATE_C, "模板C（入党积极分子）：待定");
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

    // 培训计划 状态，0 未启动、 1 正在进行、 2 已结束
    public final static byte CET_PROJECT_STATUS_INIT = 0;
    public final static byte CET_PROJECT_STATUS_START = 1;
    public final static byte CET_PROJECT_STATUS_FINISH = 2;
    public static Map<Byte, String> CET_PROJECT_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_INIT, "未启动");
        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_START, "正在进行");
        CET_PROJECT_STATUS_MAP.put(CET_PROJECT_STATUS_FINISH, "已结束");
    }

    // 培训计划 发布状态，0 未发布 1 已发布  2 取消发布
    public final static byte CET_PROJECT_PUB_STATUS_UNPUBLISHED = 0;
    public final static byte CET_PROJECT_PUB_STATUS_PUBLISHED = 1;
    public final static byte CET_PROJECT_PUB_STATUS_CANCEL = 2;
    public static Map<Byte, String> CET_PROJECT_PUB_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_PROJECT_PUB_STATUS_MAP.put(CET_PROJECT_PUB_STATUS_UNPUBLISHED, "未发布");
        CET_PROJECT_PUB_STATUS_MAP.put(CET_PROJECT_PUB_STATUS_PUBLISHED, "已发布");
        CET_PROJECT_PUB_STATUS_MAP.put(CET_PROJECT_PUB_STATUS_CANCEL, "取消发布");
    }

    // 培训班 发布状态，0 未发布 1 已发布  2 取消发布
    public final static byte CET_TRAIN_PUB_STATUS_UNPUBLISHED = 0;
    public final static byte CET_TRAIN_PUB_STATUS_PUBLISHED = 1;
    public final static byte CET_TRAIN_PUB_STATUS_CANCEL = 2;
    public static Map<Byte, String> CET_TRAIN_PUB_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_PUB_STATUS_MAP.put(CET_TRAIN_PUB_STATUS_UNPUBLISHED, "未发布");
        CET_TRAIN_PUB_STATUS_MAP.put(CET_TRAIN_PUB_STATUS_PUBLISHED, "已发布");
        CET_TRAIN_PUB_STATUS_MAP.put(CET_TRAIN_PUB_STATUS_CANCEL, "取消发布");
    }

    // 培训班 选课状态，0 根据选课时间而定 1 正在选课、2 选课结束、3 暂停选课 4 未启动选课
    public final static byte CET_TRAIN_ENROLL_STATUS_DEFAULT = 0;
    public final static byte CET_TRAIN_ENROLL_STATUS_OPEN = 1;
    public final static byte CET_TRAIN_ENROLL_STATUS_CLOSED = 2;
    public final static byte CET_TRAIN_ENROLL_STATUS_PAUSE = 3;
    public final static byte CET_TRAIN_ENROLL_STATUS_NOT_BEGIN = 4;
    public static Map<Byte, String> CET_TRAIN_ENROLL_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_DEFAULT, "根据选课时间而定");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_OPEN, "正在选课");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_CLOSED, "选课结束");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_PAUSE, "暂停选课");
        CET_TRAIN_ENROLL_STATUS_MAP.put(CET_TRAIN_ENROLL_STATUS_NOT_BEGIN, "未启动选课");
    }

    // 培训课程 选课/退课状态，0：由培训班的选课时间决定  1： 已关闭选课  2： 已关闭退课  3： 已关闭选课和退课
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT = 0;
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY = 1;
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT = 2;
    public final static byte CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL = 3;
    public static Map<Byte, String> CET_TRAIN_COURSE_APPLY_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT, "根据选课开关而定");
        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY, "单独关闭选课");
        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT, "单独关闭退课");
        CET_TRAIN_COURSE_APPLY_STATUS_MAP.put(CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL, "关闭选课和退课");
    }

    // 签到类型， 1 手动签到 2 批量导入 3 刷卡签到
    public final static byte CET_TRAINEE_SIGN_TYPE_MANUAL = 1;
    public final static byte CET_TRAINEE_SIGN_TYPE_IMPORT = 2;
    public final static byte CET_TRAINEE_SIGN_TYPE_CARD = 3;
    public static Map<Byte, String> CET_TRAINEE_SIGN_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_MANUAL, "手动签到");
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_IMPORT, "批量导入");
        CET_TRAINEE_SIGN_TYPE_MAP.put(CET_TRAINEE_SIGN_TYPE_CARD, "刷卡签到");
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


    // 组织单位类型，1 上级调训  2 二级单位组织培训
    public final static byte CET_UPPER_TRAIN_UPPER = 1;
    public final static byte CET_UPPER_TRAIN_UNIT = 2;
    public static Map<Byte, String> CET_UPPER_TRAIN_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_UPPER_TRAIN_MAP.put(CET_UPPER_TRAIN_UPPER, "上级调训");
        CET_UPPER_TRAIN_MAP.put(CET_UPPER_TRAIN_UNIT, "二级单位组织培训");
    }

    // 上级调训 审批状态，0 待审批 1 审批通过 2 审批不通过
    public final static byte CET_UPPER_TRAIN_STATUS_INIT = 0;
    public final static byte CET_UPPER_TRAIN_STATUS_PASS = 1;
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
    
    // 培训类型，1 党校专题培训  2 党校日常培训 3 二级党校/党委培训 4 上级调训 5 二级单位培训
    public final static byte CET_TYPE_SPECIAL = 1;
    public final static byte CET_TYPE_DAILY = 2;
    public final static byte CET_TYPE_PARTY = 3;
    public final static byte CET_TYPE_UNIT = 4;
    public final static byte CET_TYPE_UPPER = 5;
    public static Map<Byte, String> CET_TYPE_MAP = new LinkedHashMap<Byte, String>();

    static {
        CET_TYPE_MAP.put(CET_TYPE_SPECIAL, "党校专题培训");
        CET_TYPE_MAP.put(CET_TYPE_DAILY, "党校日常培训");
        CET_TYPE_MAP.put(CET_TYPE_PARTY, "二级党校/党委培训");
        CET_TYPE_MAP.put(CET_TYPE_UNIT, "二级单位培训");
        CET_TYPE_MAP.put(CET_TYPE_UPPER, "上级调训");
    }

}
