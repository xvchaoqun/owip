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

    //委员会和领导小组规程类型 1、人员组成规则 2、工作职责 3、议事规则
    public final static byte CG_RULE_TYPE_STAFF = 1;
    public final static byte CG_RULE_TYPE_JOB = 2;
    public final static byte CG_RULE_TYPE_DEBATE = 3;
    public final static Map<Byte,String> CG_RULE_TYPE_MAP = new LinkedHashMap<>();

    static  {
        CG_RULE_TYPE_MAP.put(CG_RULE_TYPE_STAFF,"组成原则");
        CG_RULE_TYPE_MAP.put(CG_RULE_TYPE_JOB,"工作职责");
        CG_RULE_TYPE_MAP.put(CG_RULE_TYPE_DEBATE,"议事规则");
    }

    //委员会和领导小组职务
    public final static String CG_TEAM_RECTOR = "mt_0kdzma";//校长
    public final static String CG_TEAM_MANAGING_VICE_PRESIDENT = "mt_lvylno";//常务副校长
    public final static String CG_TEAM_VICE_PRESIDENT = "mt_nwn6kp";//副校长
    public final static String CG_TEAM_MEMBER = "mt_gssgbp";//委员
    public final static String CG_TEAM_OFFICE_DIRECTOR = "mt_nqnphg";//委员
}
