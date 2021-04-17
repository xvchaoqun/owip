package sys.constants;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParttimeConstants {

    public final static Set<Byte> PARTTIME_APPLICAT_CADRE_STATUS_SET = new HashSet<>(); // 兼职申报申请人要求的干部状态
    static {
        PARTTIME_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_CJ);
        PARTTIME_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER);
        PARTTIME_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER_LEAVE);
    }

    // 兼职申报审批人类型  1.院级党组织 2.外事部门（有国境外背景）3.分管/联系校领导（正职领导人员）
    public final static byte PARTTIME_APPROVER_TYPE_UNIT = 1;
    public final static byte PARTTIME_APPROVER_TYPE_FOREIGN = 2;
    public final static byte PARTTIME_APPROVER_TYPE_LEADER= 3;
    public final static Map<Byte, String> PARTTIME_APPROVER_TYPE_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_APPROVER_TYPE_MAP.put(PARTTIME_APPROVER_TYPE_UNIT, "院级党组织");
        PARTTIME_APPROVER_TYPE_MAP.put(PARTTIME_APPROVER_TYPE_FOREIGN, "外事部门");
        PARTTIME_APPROVER_TYPE_MAP.put(PARTTIME_APPROVER_TYPE_LEADER, "分管校领导");
    }

    //兼职申报状态 0. 已提交 1. 同意 2. 不同意 3.已删除
    public final static byte PARTTIME_TYPE_APPLY = 0;
    public final static byte PARTTIME_TYPE_PASS = 1;
    public final static byte PARTTIME_TYPE_NOT_PASS = 2;
    public final static byte PARTTIME_TYPE_DELETED = 3;
    public final static Map<Byte, String> PARTTIME_STATUS_TYPE_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_STATUS_TYPE_MAP.put(PARTTIME_TYPE_APPLY, "已提交");
        PARTTIME_STATUS_TYPE_MAP.put(PARTTIME_TYPE_PASS, "同意");
        PARTTIME_STATUS_TYPE_MAP.put(PARTTIME_TYPE_NOT_PASS, "不同意");
        PARTTIME_STATUS_TYPE_MAP.put(PARTTIME_TYPE_DELETED, "已删除");
    }

    //兼职单位类别 0. 社会团体 1.基金会 2.民办非企业单位 3.企业
    public final static byte PARTTIME_TYPE_SOCIETY = 0;
    public final static byte PARTTIME_TYPE_FOUNDATION = 1;
    public final static byte PARTTIME_TYPE_NOT_BUSINESS = 2;
    public final static byte PARTTIME_TYPE_BUSINESS = 3;
    public final static Map<Byte, String> PARTTIME_TYPE_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_TYPE_MAP.put(PARTTIME_TYPE_SOCIETY, "社会团体");
        PARTTIME_TYPE_MAP.put(PARTTIME_TYPE_FOUNDATION, "基金会");
        PARTTIME_TYPE_MAP.put(PARTTIME_TYPE_NOT_BUSINESS, "民办非企业单位");
        PARTTIME_TYPE_MAP.put(PARTTIME_TYPE_BUSINESS, "企业");
    }

    //兼职申报变更记录修改类型 1. 首次提交申请 2. 修改
    public final static byte PARTTIME_APPLY_MODIFY_TYPE_ORIGINAL = 1;
    public final static byte PARTTIME_APPLY_MODIFY_TYPE_MODIFY = 2;
    public final static Map<Byte, String> PARTTIME_APPLY_MODIFY_TYPE_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_APPLY_MODIFY_TYPE_MAP.put(PARTTIME_APPLY_MODIFY_TYPE_ORIGINAL, "首次提交申请");
        PARTTIME_APPLY_MODIFY_TYPE_MAP.put(PARTTIME_APPLY_MODIFY_TYPE_MODIFY, "行程修改");
    }

    public final static int PARTTIME_APPROVER_TYPE_ID_OD_FIRST = -1; // 初审管理员，伪ID
    public final static int PARTTIME_APPROVER_TYPE_ID_OD_LAST = 0; // 终审管理员，伪ID

    //是否首次
    public final static byte PARTTIME_IS_FIRST = 0;
    public final static byte PARTTIME_NO_IS_FIRST = 1;
    public final static Map<Byte, String> PARTTIME_APPLY_FIRST_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_APPLY_FIRST_MAP.put(PARTTIME_IS_FIRST, "首次");
        PARTTIME_APPLY_FIRST_MAP.put(PARTTIME_NO_IS_FIRST, "连任");
    }

    //是否有国境外背景
    public final static byte PARTTIME_IS_BACKGROUND = 1;
    public final static byte PARTTIME_NO_BACKGROUND = 0;
    public final static Map<Byte, String> PARTTIME_APPLY_BACKGROUND_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_APPLY_BACKGROUND_MAP.put(PARTTIME_IS_BACKGROUND, "是");
        PARTTIME_APPLY_BACKGROUND_MAP.put(PARTTIME_NO_BACKGROUND, "否");
    }

    //是否取酬
    public final static byte PARTTIME_HAS_PAY = 1;
    public final static byte PARTTIME_NO_HAS_PAY = 0;
    public final static Map<Byte, String> PARTTIME_APPLY_HAS_PAY_MAP = new LinkedHashMap<>();
    static {
        PARTTIME_APPLY_HAS_PAY_MAP.put(PARTTIME_HAS_PAY, "是");
        PARTTIME_APPLY_HAS_PAY_MAP.put(PARTTIME_NO_HAS_PAY, "否");
    }


    
}
