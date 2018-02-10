package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class PmdConstants {

    // 党费收缴，月份缴费状态，0 创建 1 启动缴费 2 已结算
    public final static byte PMD_MONTH_STATUS_INIT = 0;
    public final static byte PMD_MONTH_STATUS_START = 1;
    public final static byte PMD_MONTH_STATUS_END = 2;
    public final static Map<Byte, String> PMD_MONTH_STATUS_MAP = new LinkedHashMap<>();
    static {
        PMD_MONTH_STATUS_MAP.put(PMD_MONTH_STATUS_INIT, "创建");
        PMD_MONTH_STATUS_MAP.put(PMD_MONTH_STATUS_START, "启动缴费");
        PMD_MONTH_STATUS_MAP.put(PMD_MONTH_STATUS_END, "已结算");
    }


    // 党费收缴，收费标准状态， 0 未启用  1 启用  2 作废 3 已删除 ， 状态只能按 0 - 1 - 2的顺序变更
    public final static byte PMD_NORM_STATUS_INIT = 0;
    public final static byte PMD_NORM_STATUS_USE = 1;
    public final static byte PMD_NORM_STATUS_ABOLISH = 2;
    public final static byte PMD_NORM_STATUS_DELETE = 3;
    public final static Map<Byte, String> PMD_NORM_STATUS_MAP = new LinkedHashMap<>();
    static {
        PMD_NORM_STATUS_MAP.put(PMD_NORM_STATUS_INIT, "未启用");
        PMD_NORM_STATUS_MAP.put(PMD_NORM_STATUS_USE, "已启用");
        PMD_NORM_STATUS_MAP.put(PMD_NORM_STATUS_ABOLISH, "已作废");
        PMD_NORM_STATUS_MAP.put(PMD_NORM_STATUS_DELETE, "已删除");
    }


    // 党费收缴，标准类型， 1 党费计算方法  2 党费减免标准
    public final static byte PMD_NORM_TYPE_PAY = 1;
    public final static byte PMD_NORM_TYPE_REDUCE = 2;
    public final static Map<Byte, String> PMD_NORM_TYPE_MAP = new LinkedHashMap<>();
    static {
        PMD_NORM_TYPE_MAP.put(PMD_NORM_TYPE_PAY, "党费计算方法");
        PMD_NORM_TYPE_MAP.put(PMD_NORM_TYPE_REDUCE, "党费减免标准");
    }

    // 党费收缴，额度设定类型，（3属于减免标准 4属于党费计算方法） 1 统一标准  2 基层党委设定 3 免交  4 公式
    public final static byte PMD_NORM_SET_TYPE_FIXED = 1;
    public final static byte PMD_NORM_SET_TYPE_SET = 2;
    public final static byte PMD_NORM_SET_TYPE_FREE = 3;
    public final static byte PMD_NORM_SET_TYPE_FORMULA = 4;
    public final static Map<Byte, String> PMD_NORM_SET_TYPE_MAP = new LinkedHashMap<>();
    static {
        PMD_NORM_SET_TYPE_MAP.put(PMD_NORM_SET_TYPE_FIXED, "统一标准");
        PMD_NORM_SET_TYPE_MAP.put(PMD_NORM_SET_TYPE_SET, "基层党委设定");
        PMD_NORM_SET_TYPE_MAP.put(PMD_NORM_SET_TYPE_FREE, "免交");
        PMD_NORM_SET_TYPE_MAP.put(PMD_NORM_SET_TYPE_FORMULA, "公式");
    }

    // 党费收缴，公式类别， 1 在职在编教职工 2 校聘教职工 3 离退休教职工
    public final static byte PMD_FORMULA_TYPE_ONJOB = 1;
    public final static byte PMD_FORMULA_TYPE_EXTERNAL = 2;
    public final static byte PMD_FORMULA_TYPE_RETIRE = 3;
    public final static Map<Byte, String> PMD_FORMULA_TYPE_MAP = new LinkedHashMap<>();
    static {
        PMD_FORMULA_TYPE_MAP.put(PMD_FORMULA_TYPE_ONJOB, "在职在编教职工");
        PMD_FORMULA_TYPE_MAP.put(PMD_FORMULA_TYPE_EXTERNAL, "校聘教职工");
        PMD_FORMULA_TYPE_MAP.put(PMD_FORMULA_TYPE_RETIRE, "离退休教职工");
    }

    // 党费收缴，党员类别，1 在职教职工 2 离退休教职工 3 学生 4 附属学校
    public final static byte PMD_MEMBER_TYPE_ONJOB = 1;
    public final static byte PMD_MEMBER_TYPE_RETIRE = 2;
    public final static byte PMD_MEMBER_TYPE_STUDENT = 3;
    public final static byte PMD_MEMBER_TYPE_OTHER = 4;
    public final static Map<Byte, String> PMD_MEMBER_TYPE_MAP = new LinkedHashMap<>();
    static {
        PMD_MEMBER_TYPE_MAP.put(PMD_MEMBER_TYPE_ONJOB, "在职教职工");
        PMD_MEMBER_TYPE_MAP.put(PMD_MEMBER_TYPE_RETIRE, "离退休教职工");
        PMD_MEMBER_TYPE_MAP.put(PMD_MEMBER_TYPE_STUDENT, "学生");
        PMD_MEMBER_TYPE_MAP.put(PMD_MEMBER_TYPE_OTHER, "附属学校");
    }

    // 党费支付渠道，1 校园统一支付平台  2 校园卡
    public final static byte PMD_PAY_WAY_WSZF = 1;
    public final static byte PMD_PAY_WAY_CAMPUSCARD = 2;
    public final static Map<Byte, String> PMD_PAY_WAY_MAP = new LinkedHashMap<>();
    static {
        PMD_PAY_WAY_MAP.put(PMD_PAY_WAY_WSZF, "校园统一支付平台");
        PMD_PAY_WAY_MAP.put(PMD_PAY_WAY_CAMPUSCARD, "校园卡");
    }

    // 党费收缴管理员类型， 1 书记 2副书记 3 组织委员 4 普通管理员
    public final static byte PMD_ADMIN_TYPE_SECRETARY = 1;
    public final static byte PMD_ADMIN_TYPE_VICE_SECRETARY = 2;
    public final static byte PMD_ADMIN_TYPE_COMMISSARY = 3;
    public final static byte PMD_ADMIN_TYPE_NORMAL = 4;
    public final static Map<Byte, String> PMD_ADMIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        PMD_ADMIN_TYPE_MAP.put(PMD_ADMIN_TYPE_SECRETARY, "书记");
        PMD_ADMIN_TYPE_MAP.put(PMD_ADMIN_TYPE_VICE_SECRETARY, "副书记");
        PMD_ADMIN_TYPE_MAP.put(PMD_ADMIN_TYPE_COMMISSARY, "组织委员");
        PMD_ADMIN_TYPE_MAP.put(PMD_ADMIN_TYPE_NORMAL, "普通管理员");
    }
}
