package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class PmConstants {

    // 三会一课类型
    public final static byte PARTY_MEETING_BRANCH = 1;
    public final static byte PARTY_MEETING_BRANCH_COMMITTEE = 2;
    public final static byte PARTY_MEETING_BRANCH_GROUP = 3;
    public final static byte PARTY_MEETING_BRANCH_CLASS = 4;
    public final static byte PARTY_MEETING_BRANCH_ACTIVITY = 5;
    public final static byte PARTY_MEETING_BRANCH_ORGANIZE = 6;
    public final static byte PARTY_MEETING_BRANCH_DEMOCRACY = 7;
    public final static Map<Byte, String> PARTY_MEETING_MAP = new LinkedHashMap<>();

    static {
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH, "支部党员大会");
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH_COMMITTEE, "支部委员会");
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH_GROUP, "党小组会");
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH_CLASS, "党课");
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH_ACTIVITY, "主题党日活动");
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH_ORGANIZE, "组织生活会");
        PARTY_MEETING_MAP.put(PARTY_MEETING_BRANCH_DEMOCRACY, "民主生活会");
    }

    // 三会一课2类型
    public final static byte PARTY_MEETING2_BRANCH = 1;
    public final static byte PARTY_MEETING2_BRANCH_COMMITTEE = 2;
    public final static byte PARTY_MEETING2_BRANCH_GROUP = 3;
    public final static byte PARTY_MEETING2_BRANCH_CLASS = 4;
    public final static byte PARTY_MEETING2_BRANCH_ACTIVITY = 5;
    public final static Map<Byte, String> PARTY_MEETING2_MAP = new LinkedHashMap<>();

    static {
        PARTY_MEETING2_MAP.put(PARTY_MEETING2_BRANCH, "支部党员大会");
        PARTY_MEETING2_MAP.put(PARTY_MEETING2_BRANCH_COMMITTEE, "支部委员会");
        PARTY_MEETING2_MAP.put(PARTY_MEETING2_BRANCH_GROUP, "党小组会");
        PARTY_MEETING2_MAP.put(PARTY_MEETING2_BRANCH_CLASS, "党课");
        PARTY_MEETING2_MAP.put(PARTY_MEETING2_BRANCH_ACTIVITY, "主题党日活动");
    }
    //季度汇总类型
    public final static byte PARTY_QUARTER_PARTY = 1;
    public final static byte PARTY_QUARTER_BRANCH = 2;
    public final static Map<Byte, String> PARTY_QUARTER_MAP = new LinkedHashMap<>();
    static {
        PARTY_QUARTER_MAP.put(PARTY_QUARTER_PARTY, "分党委季度汇总");
        PARTY_QUARTER_MAP.put(PARTY_QUARTER_BRANCH, "支部季度汇总");
    }

    // 三会一课审核状态， 0 未审核 1 审核通过 2 审核未通过
    public final static byte PM_MEETING_STATUS_INIT = 0;
    public final static byte PM_MEETING_STATUS_PASS = 1;
    public final static byte PM_MEETING_STATUS_DENY = 2;
    public final static Map<Byte, String> PM_MEETING_STATUS_MAP = new LinkedHashMap<>();
    static {
        PM_MEETING_STATUS_MAP.put(PM_MEETING_STATUS_INIT, "未审核");
        PM_MEETING_STATUS_MAP.put(PM_MEETING_STATUS_PASS, "审核通过");
        PM_MEETING_STATUS_MAP.put(PM_MEETING_STATUS_DENY, "审核未通过");
    }
}
