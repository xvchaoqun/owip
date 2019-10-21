package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class QyConstants {

    // 三会一课类型
    public final static byte QY_REWARD_PARTY = 1;
    public final static byte QY_REWARD_BRANCH = 2;
    public final static byte QY_REWARD_MEMBER = 3;
    public final static byte QY_REWARD_MEETING = 4;
    public final static Map<Byte, String> QY_REWARD_MAP = new LinkedHashMap<>();

    static {
        QY_REWARD_MAP.put(QY_REWARD_PARTY, "院系级党委");
        QY_REWARD_MAP.put(QY_REWARD_BRANCH, "党支部");
        QY_REWARD_MAP.put(QY_REWARD_MEMBER, "党员");
        QY_REWARD_MAP.put(QY_REWARD_MEETING, "党日活动");
    }

}
