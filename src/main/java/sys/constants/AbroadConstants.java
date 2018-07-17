package sys.constants;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lm on 2018/2/8.
 */
public class AbroadConstants {

    //出行时间范围
    public final static byte ABROAD_APPLY_SELF_DATE_TYPE_HOLIDAY = 1;
    public final static byte ABROAD_APPLY_SELF_DATE_TYPE_SCHOOL = 2;
    public final static byte ABROAD_APPLY_SELF_DATE_TYPE_OTHER = 3;
    public final static Map<Byte, String> ABROAD_APPLY_SELF_DATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_APPLY_SELF_DATE_TYPE_MAP.put(ABROAD_APPLY_SELF_DATE_TYPE_HOLIDAY, "公众假期");
        ABROAD_APPLY_SELF_DATE_TYPE_MAP.put(ABROAD_APPLY_SELF_DATE_TYPE_SCHOOL, "寒/暑假");
        ABROAD_APPLY_SELF_DATE_TYPE_MAP.put(ABROAD_APPLY_SELF_DATE_TYPE_OTHER, "其他");
    }

    //行程修改类别
    public final static byte ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL = 0;
    public final static byte ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY = 1;
    public final static Map<Byte, String> ABROAD_APPLYSELF_MODIFY_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_APPLYSELF_MODIFY_TYPE_MAP.put(ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL, "首次提交申请");
        ABROAD_APPLYSELF_MODIFY_TYPE_MAP.put(ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY, "行程修改");
    }

    //证件类别 1:集中管理证件 2:取消集中保管证件 3:丢失证件
    public final static byte ABROAD_PASSPORT_TYPE_KEEP = 1;
    public final static byte ABROAD_PASSPORT_TYPE_CANCEL = 2;
    public final static byte ABROAD_PASSPORT_TYPE_LOST = 3;
    public final static Map<Byte, String> ABROAD_PASSPORT_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_TYPE_MAP.put(ABROAD_PASSPORT_TYPE_KEEP, "集中管理证件");
        ABROAD_PASSPORT_TYPE_MAP.put(ABROAD_PASSPORT_TYPE_CANCEL, "取消集中保管证件");
        ABROAD_PASSPORT_TYPE_MAP.put(ABROAD_PASSPORT_TYPE_LOST, "丢失证件");
    }

    // 取消集中保管原因 1 证件过期 2 不再担任行政职务 3证件作废 4 其他
    public final static byte ABROAD_PASSPORT_CANCEL_TYPE_EXPIRE = 1;
    public final static byte ABROAD_PASSPORT_CANCEL_TYPE_DISMISS = 2;
    public final static byte ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH = 3;
    public final static byte ABROAD_PASSPORT_CANCEL_TYPE_OTHER = 4;
    public final static Map<Byte, String> ABROAD_PASSPORT_CANCEL_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_CANCEL_TYPE_MAP.put(ABROAD_PASSPORT_CANCEL_TYPE_EXPIRE, "证件过期");
        ABROAD_PASSPORT_CANCEL_TYPE_MAP.put(ABROAD_PASSPORT_CANCEL_TYPE_DISMISS, "不再担任行政职务");
        ABROAD_PASSPORT_CANCEL_TYPE_MAP.put(ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH, "证件作废");
        ABROAD_PASSPORT_CANCEL_TYPE_MAP.put(ABROAD_PASSPORT_CANCEL_TYPE_OTHER, "其他");
    }

    // 丢失来源 1 从集中管理库中转移 2 后台添加
    public final static byte ABROAD_PASSPORT_LOST_TYPE_TRANSFER = 1;
    public final static byte ABROAD_PASSPORT_LOST_TYPE_ADD = 2;
    public final static Map<Byte, String> ABROAD_PASSPORT_LOST_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_LOST_TYPE_MAP.put(ABROAD_PASSPORT_LOST_TYPE_TRANSFER, "集中管理证件库");
        ABROAD_PASSPORT_LOST_TYPE_MAP.put(ABROAD_PASSPORT_LOST_TYPE_ADD, "后台添加");
    }

    //领取证件类别
    public final static byte ABROAD_PASSPORT_DRAW_TYPE_SELF = 1;
    public final static byte ABROAD_PASSPORT_DRAW_TYPE_TW = 2;
    public final static byte ABROAD_PASSPORT_DRAW_TYPE_OTHER = 3;
    public final static byte ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF = 4;
    public final static Map<Byte, String> ABROAD_PASSPORT_DRAW_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_DRAW_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_TYPE_SELF, "因私出国（境）");
        ABROAD_PASSPORT_DRAW_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_TYPE_TW, "因公赴台");
        ABROAD_PASSPORT_DRAW_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_TYPE_OTHER, "其他事务");
        ABROAD_PASSPORT_DRAW_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF, "长期因公出国");
    }

    //领取证件用途 1 仅签证 2 已签证，本次出境 3 同时签证和出境
    public final static byte ABROAD_PASSPORT_DRAW_USE_TYPE_SIGN = 1;
    public final static byte ABROAD_PASSPORT_DRAW_USE_TYPE_ABROAD = 2;
    public final static byte ABROAD_PASSPORT_DRAW_USE_TYPE_BOTH = 3;
    public final static Map<Byte, String> ABROAD_PASSPORT_DRAW_USE_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_DRAW_USE_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_USE_TYPE_SIGN, "仅签证");
        ABROAD_PASSPORT_DRAW_USE_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_USE_TYPE_ABROAD, "已签证，本次出境");
        ABROAD_PASSPORT_DRAW_USE_TYPE_MAP.put(ABROAD_PASSPORT_DRAW_USE_TYPE_BOTH, "同时签证和出境");
    }

    //申办证件审批状态
    public final static byte ABROAD_PASSPORT_APPLY_STATUS_INIT = 0;
    public final static byte ABROAD_PASSPORT_APPLY_STATUS_PASS = 1;
    public final static byte ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS = 2;
    public final static Map<Byte, String> ABROAD_PASSPORT_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_APPLY_STATUS_MAP.put(ABROAD_PASSPORT_APPLY_STATUS_INIT, "待审批");
        ABROAD_PASSPORT_APPLY_STATUS_MAP.put(ABROAD_PASSPORT_APPLY_STATUS_PASS, "批准");
        ABROAD_PASSPORT_APPLY_STATUS_MAP.put(ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS, "未批准");
    }

    //使用证件审批状态
    public final static byte ABROAD_PASSPORT_DRAW_STATUS_INIT = 0;
    public final static byte ABROAD_PASSPORT_DRAW_STATUS_PASS = 1;
    public final static byte ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS = 2;
    public final static Map<Byte, String> ABROAD_PASSPORT_DRAW_STATUS_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_STATUS_INIT, "待审批");
        ABROAD_PASSPORT_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_STATUS_PASS, "审批通过");
        ABROAD_PASSPORT_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS, "未通过审批");
    }

    //使用证件领取状态
    public final static byte ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW = 0;
    public final static byte ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW = 1;
    public final static byte ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN = 2;
    public final static byte ABROAD_PASSPORT_DRAW_DRAW_STATUS_ABOLISH = 3;
    public final static Map<Byte, String> ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW, "未领取");
        ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW, "已领取");
        ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN, "已归还");
        ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP.put(ABROAD_PASSPORT_DRAW_DRAW_STATUS_ABOLISH, "已作废");
    }

    // 归还证件处理类别， 因私出国、因公赴台长期（1：持证件出国（境） 0：未持证件出国（境） 2：拒不交回证件）
    //                  处理其他事务（1：违规使用证件出国（境）0：没有使用证件出国（境） 2：拒不交回证件）
    public final static byte ABROAD_PASSPORT_DRAW_USEPASSPORT_UNUSE = 0;
    public final static byte ABROAD_PASSPORT_DRAW_USEPASSPORT_USE = 1;
    public final static byte ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN = 2;
    public final static Map<Byte, String> ABROAD_PASSPORT_DRAW_USEPASSPORT_MAP = new LinkedHashMap<>();

    static {
        ABROAD_PASSPORT_DRAW_USEPASSPORT_MAP.put(ABROAD_PASSPORT_DRAW_USEPASSPORT_USE, "持证件出国（境）/违规使用证件出国（境）");
        ABROAD_PASSPORT_DRAW_USEPASSPORT_MAP.put(ABROAD_PASSPORT_DRAW_USEPASSPORT_UNUSE, "未持证件出国（境）");
        ABROAD_PASSPORT_DRAW_USEPASSPORT_MAP.put(ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN, "拒不交回证件");
    }

    // 因私出国审批人类别  1本单位正职 2校领导 3 书记 4 校长
    public final static byte ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL = 1;
    public final static byte ABROAD_APPROVER_TYPE_LEADER = 2;
    public final static byte ABROAD_APPROVER_TYPE_SECRETARY = 3;
    public final static byte ABROAD_APPROVER_TYPE_MASTER = 4;
    public final static byte ABROAD_APPROVER_TYPE_UNIT = 5;
    public final static byte ABROAD_APPROVER_TYPE_OTHER = 20;
    public final static Map<Byte, String> ABROAD_APPROVER_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_APPROVER_TYPE_MAP.put(ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL, "本单位正职");
        ABROAD_APPROVER_TYPE_MAP.put(ABROAD_APPROVER_TYPE_UNIT, "本单位人员");
        ABROAD_APPROVER_TYPE_MAP.put(ABROAD_APPROVER_TYPE_LEADER, "校领导");
        ABROAD_APPROVER_TYPE_MAP.put(ABROAD_APPROVER_TYPE_SECRETARY, "书记");
        ABROAD_APPROVER_TYPE_MAP.put(ABROAD_APPROVER_TYPE_MASTER, "校长");
        ABROAD_APPROVER_TYPE_MAP.put(ABROAD_APPROVER_TYPE_OTHER, "其他");
    }

    public final static int ABROAD_APPROVER_TYPE_ID_OD_FIRST = -1; // 初审管理员，伪ID
    public final static int ABROAD_APPROVER_TYPE_ID_OD_LAST = 0; // 终审管理员，伪ID

    // 管理员审批类型，0初审，1终审（type_id为null时）
    public final static byte ABROAD_APPROVER_LOG_OD_TYPE_FIRST = 0;
    public final static byte ABROAD_APPROVER_LOG_OD_TYPE_LAST = 1;

    // 因公赴台备案-办理新证件方式，使用组织部函件办理、使用国台办批件办理
    public final static byte ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OW = 1;
    public final static byte ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE = 2;
    public final static Map<Byte, String> ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP = new LinkedHashMap<>();

    static {
        ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP.put(ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OW, "使用组织部函件办理");
        ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP.put(ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE, "使用国台办批件办理");
    }

    public final static Set<Byte> ABROAD_APPLICAT_CADRE_STATUS_SET = new HashSet<>(); // 因私申请人要求的干部状态

    static {
        ABROAD_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_MIDDLE);
        ABROAD_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER);
        ABROAD_APPLICAT_CADRE_STATUS_SET.add(CadreConstants.CADRE_STATUS_LEADER_LEAVE);
    }
}
