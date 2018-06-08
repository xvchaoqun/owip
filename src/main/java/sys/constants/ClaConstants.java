package sys.constants;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ClaConstants {

    // 申请类别，1：因公请假 2: 因私请假
    public final static byte CLA_APPLY_TYPE_PUBLIC = 1;
    public final static byte CLA_APPLY_TYPE_PRIVATE = 2;
    public final static Map<Byte, String> CLA_APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        CLA_APPLY_TYPE_MAP.put(CLA_APPLY_TYPE_PUBLIC, "因公请假");
        CLA_APPLY_TYPE_MAP.put(CLA_APPLY_TYPE_PRIVATE, "因私请假");
    }

    //行程修改类别
    public final static byte CLA_APPLY_MODIFY_TYPE_ORIGINAL = 0;
    public final static byte CLA_APPLY_MODIFY_TYPE_MODIFY = 1;
    public final static Map<Byte, String> CLA_APPLY_MODIFY_TYPE_MAP = new LinkedHashMap<>();

    static {
        CLA_APPLY_MODIFY_TYPE_MAP.put(CLA_APPLY_MODIFY_TYPE_ORIGINAL, "首次提交申请");
        CLA_APPLY_MODIFY_TYPE_MAP.put(CLA_APPLY_MODIFY_TYPE_MODIFY, "行程修改");
    }

    // 因私出国审批人类别  1本单位正职 2分管校领导 3 书记 4 校长
    public final static byte CLA_APPROVER_TYPE_UNIT = 1;
    public final static byte CLA_APPROVER_TYPE_LEADER = 2;
    public final static byte CLA_APPROVER_TYPE_SECRETARY = 3;
    public final static byte CLA_APPROVER_TYPE_MASTER = 4;
    public final static byte CLA_APPROVER_TYPE_OTHER = 20;
    public final static Map<Byte, String> CLA_APPROVER_TYPE_MAP = new LinkedHashMap<>();

    static {
        CLA_APPROVER_TYPE_MAP.put(CLA_APPROVER_TYPE_UNIT, "本单位正职");
        CLA_APPROVER_TYPE_MAP.put(CLA_APPROVER_TYPE_LEADER, "分管校领导");
        CLA_APPROVER_TYPE_MAP.put(CLA_APPROVER_TYPE_SECRETARY, "书记");
        CLA_APPROVER_TYPE_MAP.put(CLA_APPROVER_TYPE_MASTER, "校长");
        CLA_APPROVER_TYPE_MAP.put(CLA_APPROVER_TYPE_OTHER, "其他");
    }

    public final static int CLA_APPROVER_TYPE_ID_OD_FIRST = -1; // 初审管理员，伪ID
    public final static int CLA_APPROVER_TYPE_ID_OD_LAST = 0; // 终审管理员，伪ID

    // 管理员审批类型，0初审，1终审（type_id为null时）
    public final static byte CLA_APPROVER_LOG_OD_TYPE_FIRST = 0;
    public final static byte CLA_APPROVER_LOG_OD_TYPE_LAST = 1;

    public final static Set<Byte> CLA_APPLICAT_CADRE_STATUS_SET = new HashSet<>(); // 因私申请人要求的干部状态

    static {
        CLA_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_MIDDLE);
        CLA_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER);
        CLA_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER_LEAVE);
    }
}
