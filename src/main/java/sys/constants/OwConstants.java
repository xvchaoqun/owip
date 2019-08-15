package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/4/14.
 */
public class OwConstants {


    // 党组织管理员类别，1 分党委管理员  2 支部管理员  3 民主党派管理员
    public final static byte OW_ORG_ADMIN_PARTY = 1;
    public final static byte OW_ORG_ADMIN_BRANCH = 2;
    public final static byte OW_ORG_ADMIN_DPPARTY = 3;
    public final static Map<Byte, String> OW_ORG_ADMIN_MAP = new LinkedHashMap<>();

    static {
        OW_ORG_ADMIN_MAP.put(OW_ORG_ADMIN_PARTY, "分党委管理员");
        OW_ORG_ADMIN_MAP.put(OW_ORG_ADMIN_BRANCH, "支部管理员");
        OW_ORG_ADMIN_MAP.put(OW_ORG_ADMIN_DPPARTY, "民主党派管理员");
    }

    // 权限开通申请状态，0申请 1本人撤销 2 打回 3通过；
    public final static byte OW_ENTER_APPLY_STATUS_APPLY = 0;
    public final static byte OW_ENTER_APPLY_STATUS_SELF_ABORT = 1;
    public final static byte OW_ENTER_APPLY_STATUS_ADMIN_ABORT = 2;
    public final static byte OW_ENTER_APPLY_STATUS_PASS = 3;
    public final static Map<Byte, String> OW_ENTER_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        OW_ENTER_APPLY_STATUS_MAP.put(OW_ENTER_APPLY_STATUS_APPLY, "申请");
        OW_ENTER_APPLY_STATUS_MAP.put(OW_ENTER_APPLY_STATUS_SELF_ABORT, "本人撤销");
        OW_ENTER_APPLY_STATUS_MAP.put(OW_ENTER_APPLY_STATUS_ADMIN_ABORT, "打回");
        OW_ENTER_APPLY_STATUS_MAP.put(OW_ENTER_APPLY_STATUS_PASS, "通过");
    }

    // 权限开通申请类别，1申请入党 2 留学归国申请 3转入申请  4 流入申请
    public final static byte OW_ENTER_APPLY_TYPE_MEMBERAPPLY = 1;
    public final static byte OW_ENTER_APPLY_TYPE_RETURN = 2;
    public final static byte OW_ENTER_APPLY_TYPE_MEMBERIN = 3;
    public final static byte OW_ENTER_APPLY_TYPE_MEMBERINFLOW = 4;
    public final static Map<Byte, String> OW_ENTER_APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        OW_ENTER_APPLY_TYPE_MAP.put(OW_ENTER_APPLY_TYPE_MEMBERAPPLY, "申请入党");
        OW_ENTER_APPLY_TYPE_MAP.put(OW_ENTER_APPLY_TYPE_RETURN, "留学归国党员申请");
        OW_ENTER_APPLY_TYPE_MAP.put(OW_ENTER_APPLY_TYPE_MEMBERIN, "组织关系转入");
        OW_ENTER_APPLY_TYPE_MAP.put(OW_ENTER_APPLY_TYPE_MEMBERINFLOW, "流入党员申请");
    }

    // 申请入党类型
    public final static byte OW_APPLY_TYPE_TEACHER = 1; // 教职工
    public final static byte OW_APPLY_TYPE_STU = 2; // 学生
    public final static Map<Byte, String> OW_APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        OW_APPLY_TYPE_MAP.put(OW_APPLY_TYPE_STU, "学生");
        OW_APPLY_TYPE_MAP.put(OW_APPLY_TYPE_TEACHER, "教职工");
    }

    // 申请入党阶段
    //0不通过 1申请  2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书 6预备党员 7正式党员
    public final static byte OW_APPLY_STAGE_OUT = -2; // 已转出的申请
    public final static byte OW_APPLY_STAGE_DENY = -1; // 未通过
    public final static byte OW_APPLY_STAGE_INIT = 0; // 申请
    public final static byte OW_APPLY_STAGE_PASS = 1; // 通过
    public final static byte OW_APPLY_STAGE_ACTIVE = 2; // 积极分子
    public final static byte OW_APPLY_STAGE_CANDIDATE = 3; // 发展对象
    public final static byte OW_APPLY_STAGE_PLAN = 4; // 列入发展计划
    public final static byte OW_APPLY_STAGE_DRAW = 5; // 领取志愿书
    public final static byte OW_APPLY_STAGE_GROW = 6; // 预备党员
    public final static byte OW_APPLY_STAGE_POSITIVE = 7; // 正式党员
    public final static Map<Byte, String> OW_APPLY_STAGE_MAP = new LinkedHashMap<>();

    static {
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_DENY, "未通过");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_INIT, "申请");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_ACTIVE, "积极分子");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_CANDIDATE, "发展对象");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_PLAN, "列入发展计划");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_DRAW, "领取志愿书");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_GROW, "预备党员");
        OW_APPLY_STAGE_MAP.put(OW_APPLY_STAGE_POSITIVE, "正式党员");
    }

    // 申请入党审核状态
    public final static byte OW_APPLY_STATUS_UNCHECKED = 0; // 未审核
    public final static byte OW_APPLY_STATUS_CHECKED = 1; // 已审核
    public final static byte OW_APPLY_STATUS_OD_CHECKED = 2; // 组织部已审核，成为预备党员和正式党员时


    // 党员退休审核状态
    public final static byte OW_RETIRE_APPLY_STATUS_UNCHECKED = 0; // 未审核
    public final static byte OW_RETIRE_APPLY_STATUS_CHECKED = 1; // 已审核

    // 党员公示类别 1 发展党员公示 2 党员转正公示
    public final static byte OW_PARTY_PUBLIC_TYPE_GROW = 1;
    public final static byte OW_PARTY_PUBLIC_TYPE_POSITIVE = 2;
    public final static Map<Byte, String> OW_PARTY_PUBLIC_TYPE_MAP = new LinkedHashMap<>();

    static {
        OW_PARTY_PUBLIC_TYPE_MAP.put(OW_PARTY_PUBLIC_TYPE_GROW, "发展党员公示");
        OW_PARTY_PUBLIC_TYPE_MAP.put(OW_PARTY_PUBLIC_TYPE_POSITIVE, "党员转正公示");
    }

    // 组织员类型 1 校级组织员 2 院系级组织员
    public final static byte OW_ORGANIZER_TYPE_SCHOOL = 1;
    public final static byte OW_ORGANIZER_TYPE_UNIT = 2;
    public final static Map<Byte, String> OW_ORGANIZER_TYPE_MAP = new LinkedHashMap<>();

    static {
        OW_ORGANIZER_TYPE_MAP.put(OW_ORGANIZER_TYPE_SCHOOL, "校级组织员");
        OW_ORGANIZER_TYPE_MAP.put(OW_ORGANIZER_TYPE_UNIT, "院系级组织员");
    }
    // 组织员状态
    public final static byte OW_ORGANIZER_STATUS_NOW = 1;
    public final static byte OW_ORGANIZER_STATUS_LEAVE = 2;
    public final static byte OW_ORGANIZER_STATUS_HISTORY = 3;
    public final static Map<Byte, String> OW_ORGANIZER_STATUS_MAP = new LinkedHashMap<>();

    static {
        OW_ORGANIZER_STATUS_MAP.put(OW_ORGANIZER_STATUS_NOW, "现任");
        OW_ORGANIZER_STATUS_MAP.put(OW_ORGANIZER_STATUS_LEAVE, "离任");
        OW_ORGANIZER_STATUS_MAP.put(OW_ORGANIZER_STATUS_HISTORY, "历任");
    }
    // 组织关系状态
    public final static byte OW_OR_STATUS_OUT = 1;
    public final static byte OW_OR_STATUS_NOT_OUT = 2;
    public final static Map<Byte, String> OW_OR_STATUS_MAP = new LinkedHashMap<>();

    static {
        OW_OR_STATUS_MAP.put(OW_OR_STATUS_OUT, "已转出");
        OW_OR_STATUS_MAP.put(OW_OR_STATUS_NOT_OUT, "未转出");
    }

    // 党员各类申请的审批记录类型 1党员发展 2 留学归国申请 3 组织关系转入 4 流入党员申请 5 流出党员申请 6 组织关系转出 7 留学归国党员
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY = 1;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD = 2;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN = 3;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW = 4;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW = 5;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT = 6;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN = 7;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER = 8;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_USER_REG = 10;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT = 11;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT = 12;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY = 13;
    public final static byte OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CHECK = 14;
    public final static Map<Byte, String> OW_APPLY_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();

    static {
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "申请入党");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD, "留学归国申请");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, "组织关系转入");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, "流入党员申请");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW, "流出党员申请");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, "组织关系转出");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, "留学归国党员申请");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, "校内组织关系转接");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "用户注册");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, "党员出党");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT, "流入党员转出");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, "党员出国（境）申请组织关系暂留");
        OW_APPLY_APPROVAL_LOG_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CHECK, "党员信息修改申请");
    }

    // 党员各类申请的操作人类别
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF = 0; // 本人
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH = 1; // 党支部
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY = 2; // 分党委
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_OW = 3; // 组织部
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY = 4; // 转出分党委
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY = 5; // 转入分党委
    public final static byte OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN = 10;// 后台操作
    public final static Map<Byte, String> OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF, "本人");
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH, "党支部");
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY, "分党委");
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_OW, "组织部");
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY, "转出分党委");
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY, "转入分党委");
        OW_APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN, "后台操作");
    }

    // 党员各类申请的审批结果
    public final static byte OW_APPLY_APPROVAL_LOG_STATUS_DENY = 0;
    public final static byte OW_APPLY_APPROVAL_LOG_STATUS_PASS = 1;
    public final static byte OW_APPLY_APPROVAL_LOG_STATUS_BACK = 2;
    public final static byte OW_APPLY_APPROVAL_LOG_STATUS_NONEED = 3; // 直接通过，不需要审核
    public final static Map<Byte, String> OW_APPLY_APPROVAL_LOG_STATUS_MAP = new LinkedHashMap<>();

    static {
        OW_APPLY_APPROVAL_LOG_STATUS_MAP.put(OW_APPLY_APPROVAL_LOG_STATUS_DENY, "不通过");
        OW_APPLY_APPROVAL_LOG_STATUS_MAP.put(OW_APPLY_APPROVAL_LOG_STATUS_PASS, "通过");
        OW_APPLY_APPROVAL_LOG_STATUS_MAP.put(OW_APPLY_APPROVAL_LOG_STATUS_BACK, "打回");
        OW_APPLY_APPROVAL_LOG_STATUS_MAP.put(OW_APPLY_APPROVAL_LOG_STATUS_NONEED, "-");
    }
}
