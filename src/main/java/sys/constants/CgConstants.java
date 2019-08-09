package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CgConstants {
    //委员会和领导小组类型，1、委员会 2、领导小组
    public final static byte CG_TEAM_TYPE_MEMBER = 1;
    public final static byte CG_TEAM_TYPE_GROUP = 2;
    public final static Map<Byte, String> CG_TEAM_TYPE_MAP = new LinkedHashMap<>();

    static {
        CG_TEAM_TYPE_MAP.put(CG_TEAM_TYPE_MEMBER, "委员会");
        CG_TEAM_TYPE_MAP.put(CG_TEAM_TYPE_GROUP, "领导小组");
    }

    //委员会和领导小组成员类型 1、现任干部 2、各类代表
    public final static byte CG_MEMBER_TYPE_CADRE = 1;
    public final static byte CG_MEMBER_TYPE_USER = 2;
    public final static Map<Byte, String> CG_MEMBER_TYPE_MAY = new LinkedHashMap<>();

    static {
        CG_MEMBER_TYPE_MAY.put(CG_MEMBER_TYPE_CADRE, "现任干部");
        CG_MEMBER_TYPE_MAY.put(CG_MEMBER_TYPE_USER, "各类代表");
    }
}
