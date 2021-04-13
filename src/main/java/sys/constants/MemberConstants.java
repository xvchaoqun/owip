package sys.constants;

import sys.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class MemberConstants {

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
        MEMBER_AGE_MAP.put(MEMBER_AGE_0, "无数据");
    }

    public static byte getMemberAgeRange(Date birth) {

        Date now = new Date();
        byte key = MEMBER_AGE_0; // 未知年龄
        if (birth != null) {
            if(birth.after(DateUtils.getDateBeforeOrAfterYears(now, -21))){ // 20及以下
                key = MEMBER_AGE_20;
            } else if (birth.after(DateUtils.getDateBeforeOrAfterYears(now, -31))) { // 21~30
                key = MEMBER_AGE_21_30;
            } else if (birth.after(DateUtils.getDateBeforeOrAfterYears(now, -41))) { // 31~40
                key = MEMBER_AGE_31_40;
            } else if (birth.after(DateUtils.getDateBeforeOrAfterYears(now, -51))) { // 41~50
                key = MEMBER_AGE_41_50;
            } else { // 51及以上
                key = MEMBER_AGE_51;
            }
        }
        return key;
    }

    public static byte getMemberAgeRange(Integer age) {

        byte key = MEMBER_AGE_0; // 未知年龄
        if (age != null) {
            if (age <= 20) { // 20岁及以下
                key = MEMBER_AGE_20;
            } else if (age <= 30) { // 21~30
                key = MEMBER_AGE_21_30;
            } else if (age <= 40) { // 31~40
                key = MEMBER_AGE_31_40;
            } else if (age <= 50) { // 31~40
                key = MEMBER_AGE_41_50;
            } else { // 51及以上
                key = MEMBER_AGE_51;
            }
        }
        return key;
    }

    // 党员政治面貌
    public final static byte MEMBER_POLITICAL_STATUS_GROW = 1; //预备党员
    public final static byte MEMBER_POLITICAL_STATUS_POSITIVE = 2; //正式党员
    public final static Map<Byte, String> MEMBER_POLITICAL_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_GROW, "预备党员");
        MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_POSITIVE, "正式党员");
    }

    // 党员状态, 1正常 3已出党 4已转出
    public final static byte MEMBER_STATUS_NORMAL = 1; // 正常
    public final static byte MEMBER_STATUS_HISTORY = 2; // 已转移至历史党员库
    public final static byte MEMBER_STATUS_QUIT = 3; // 已减员
    public final static byte MEMBER_STATUS_OUT = 4; // 已转出
    public final static byte MEMBER_STATUS_STAY = 5; // 暂留
    public final static Map<Byte, String> MEMBER_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_NORMAL, "正常");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_HISTORY, "已转移至历史党员库");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_QUIT, "已减员");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_OUT, "已转出");
        MEMBER_STATUS_MAP.put(MEMBER_STATUS_STAY, "暂留");
    }

    // 党员来源
    public final static byte MEMBER_SOURCE_IMPORT = 1; // 导入
    public final static byte MEMBER_SOURCE_GROW = 2; // 本校发展
    public final static byte MEMBER_SOURCE_TRANSFER = 3; // 外校转入
    public final static byte MEMBER_SOURCE_RETURNED = 4; // 归国人员恢复入党
    public final static byte MEMBER_SOURCE_ADMIN = 5; // 后台添加
    public final static Map<Byte, String> MEMBER_SOURCE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_IMPORT, "导入");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_GROW, "本校发展");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_TRANSFER, "外校转入");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_RETURNED, "归国人员恢复入党");
        MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_ADMIN, "后台添加");
    }

    // 党员信息修改申请状态
    public final static byte MEMBER_CHECK_STATUS_BACK = -1;
    public final static byte MEMBER_CHECK_STATUS_APPLY = 0;
    public final static byte MEMBER_CHECK_STATUS_PASS = 1;
    public final static Map<Byte, String> MEMBER_CHECK_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_CHECK_STATUS_MAP.put(MEMBER_CHECK_STATUS_BACK, "返回修改");
        MEMBER_CHECK_STATUS_MAP.put(MEMBER_CHECK_STATUS_APPLY, "申请");
        MEMBER_CHECK_STATUS_MAP.put(MEMBER_CHECK_STATUS_PASS, "审核通过");
    }

    // 党员大类别
    public final static byte MEMBER_TYPE_TEACHER = 1; // 教职工
    public final static byte MEMBER_TYPE_STUDENT = 2; // 学生
    public final static Map<Byte, String> MEMBER_TYPE_MAP = new LinkedHashMap<>();

    static {
        MEMBER_TYPE_MAP.put(MEMBER_TYPE_TEACHER, "教职工");
        MEMBER_TYPE_MAP.put(MEMBER_TYPE_STUDENT, "学生");
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
        MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_PARTY_VERIFY, "审核通过");
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
        MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_OW_VERIFY, "审核通过");
    }

    // 党员转出状态
    public final static byte MEMBER_OUT_STATUS_ABOLISH = -3;
    public final static byte MEMBER_OUT_STATUS_SELF_BACK = -2;
    public final static byte MEMBER_OUT_STATUS_BACK = -1;
    public final static byte MEMBER_OUT_STATUS_APPLY = 0;
    public final static byte MEMBER_OUT_STATUS_PARTY_VERIFY = 1;
    public final static byte MEMBER_OUT_STATUS_OW_VERIFY = 2;
    public final static byte MEMBER_OUT_STATUS_ARCHIVE = 10; // 已转出的记录归档
    public final static Map<Byte, String> MEMBER_OUT_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_ABOLISH, "撤销已完成的审批");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_SELF_BACK, "本人撤回");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_BACK, "返回修改");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_APPLY, "申请");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_OW_VERIFY, "审核通过");
        MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_ARCHIVE, "已归档");
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
        MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_OW_VERIFY, "审核通过");
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
    public final static byte MEMBER_STAY_STATUS_ARCHIVE = 10; // 已归档
    public final static Map<Byte, String> MEMBER_STAY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_SELF_BACK, "本人撤回");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_BACK, "不通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_APPLY, "申请");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_BRANCH_VERIFY, "党支部审核通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_OW_VERIFY, "审核通过");
        MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_ARCHIVE, "已归档");
    }

    // 组织关系介绍信状态
    public final static byte MEMBER_CERTIFY_STATUS_BACK = -1;
    public final static byte MEMBER_CERTIFY_STATUS_APPLY = 0;
    public final static byte MEMBER_CERTIFY_STATUS_PARTY_VERIFY = 1;
    public final static byte MEMBER_CERTIFY_STATUS_OW_VERIFY = 2;
    public final static Map<Byte, String> MEMBER_CERTIFY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MEMBER_CERTIFY_STATUS_MAP.put(MEMBER_CERTIFY_STATUS_BACK, "已撤销");
        MEMBER_CERTIFY_STATUS_MAP.put(MEMBER_CERTIFY_STATUS_APPLY, "申请");
        MEMBER_CERTIFY_STATUS_MAP.put(MEMBER_CERTIFY_STATUS_PARTY_VERIFY, "分党委审核通过");
        MEMBER_CERTIFY_STATUS_MAP.put(MEMBER_CERTIFY_STATUS_OW_VERIFY, "审批完成");
    }
}
