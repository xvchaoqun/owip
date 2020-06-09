package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class RoleConstants {

    // 系统角色（与数据库对应的角色字符串不可以修改！）
    public static final String ROLE_ADMIN = "admin"; // 系统管理员
    public static final String ROLE_SUPER = "super"; // 控制台
    public static final String ROLE_GUEST = "guest";
    public static final String ROLE_REG = "reg"; // 注册用户，未审核通过
    public static final String ROLE_LEADER = "leader";
    public static final String ROLE_CADRE_CJ = "cadre";
    public static final String ROLE_CADRE_KJ = "kjCadre";
    public static final String ROLE_CADRE_DP = "cadreDp"; // 民主党派干部库
    public static final String ROLE_CADRERESERVE = "cadreReserve"; // 优秀年轻干部
    public static final String ROLE_CADREINSPECT = "cadreInspect"; // 考察对象
    public static final String ROLE_CADRERECRUIT = "cadreRecruit"; // 应聘干部（普通教师）
    public static final String ROLE_MEMBER = "member";
    public static final String ROLE_INFLOWMEMBER = "inflowMember";
    public static final String ROLE_PARTYADMIN = "partyAdmin";
    public static final String ROLE_BRANCHADMIN = "branchAdmin";
    public static final String ROLE_DP_ADMIN = "role_dp_admin";
    public static final String ROLE_DP_PARTY = "role_dp_party";
    // 三大管理员
    public static final String ROLE_ODADMIN = "odAdmin";
    public static final String ROLE_CADREADMIN = "cadreAdmin";
    public static final String ROLE_CET_ADMIN = "cet_admin"; // 干部培训管理员

    public static final String ROLE_TEACHER = "role_teacher"; // 教职工
    public static final String ROLE_PCS_ADMIN = "role_pcs_admin"; // 党代会-分党委管理员
    public static final String ROLE_PCS_ADMIN2 = "role_pcs_admin2"; // 党代会-组织部管理员（党代表）
    public static final String ROLE_PCS_PR = "role_pcs_pr"; // 党代表
    public static final String ROLE_PCS_VOTE_DW = "role_pcs_vote_dw"; // 党代会-两委选举-党委录入
    public static final String ROLE_PCS_VOTE_JW = "role_pcs_vote_jw"; // 党代会-两委选举-纪委录入

    public static final String ROLE_OA_USER = "role_oa_user"; // 协同办公用户
    public static final String ROLE_OA_ADMIN = "role_oa_admin"; // 协同办公管理员

    public static final String ROLE_PMD_OW = "role_pmd_ow"; // 党费收缴-组织部管理员
    public static final String ROLE_PMD_PARTY = "role_pmd_party"; // 党费收缴-分党委管理员
    public static final String ROLE_PMD_BRANCH = "role_pmd_branch"; // 党费收缴-支部管理员

    public static final String ROLE_CET_ADMIN_UPPER = "cet_admin_upper"; // 单位管理员（上级调训）-干部教育培训
    public static final String ROLE_CET_ADMIN_PARTY = "cet_admin_party"; // 二级党委管理员-干部教育培训
    public static final String ROLE_CET_ADMIN_PS = "cet_admin_ps"; // 二级党校管理员-干部教育培训 （弃用）

    public final static Map<String, String> ROLE_MAP = new LinkedHashMap<>();

    static {
        ROLE_MAP.put(ROLE_ADMIN, "系统管理员");
        ROLE_MAP.put(ROLE_GUEST, "非党员");
        ROLE_MAP.put(ROLE_REG, "注册用户");
        ROLE_MAP.put(ROLE_LEADER, "校领导");
        ROLE_MAP.put(ROLE_CADRE_CJ, "处级干部");
        ROLE_MAP.put(ROLE_CADRE_KJ, "科级干部");
        ROLE_MAP.put(ROLE_CADRERESERVE, "优秀年轻干部");
        ROLE_MAP.put(ROLE_CADREINSPECT, "考察对象");
        ROLE_MAP.put(ROLE_CADRERECRUIT, "应聘干部");
        ROLE_MAP.put(ROLE_MEMBER, "党员");
        ROLE_MAP.put(ROLE_INFLOWMEMBER, "流入党员");
        ROLE_MAP.put(ROLE_PARTYADMIN, "分党委管理员");
        ROLE_MAP.put(ROLE_BRANCHADMIN, "党支部管理员");
        ROLE_MAP.put(ROLE_ODADMIN, "组织部管理员");
        ROLE_MAP.put(ROLE_CADREADMIN, "干部管理员");

        ROLE_MAP.put(ROLE_TEACHER, "教职工");
        ROLE_MAP.put(ROLE_PCS_ADMIN, "党代会管理员");
        ROLE_MAP.put(ROLE_PCS_PR, "党代表");

        ROLE_MAP.put(ROLE_OA_USER, "协同办公负责人");

        ROLE_MAP.put(ROLE_PMD_PARTY, "分党委管理员(党费收缴)");
        ROLE_MAP.put(ROLE_PMD_BRANCH, "支部管理员(党费收缴)");

        ROLE_MAP.put(ROLE_CET_ADMIN, "干部教育培训管理员");

        ROLE_MAP.put(ROLE_DP_ADMIN, "统战部管理员");
        ROLE_MAP.put(ROLE_DP_PARTY, "民主党派管理员");
    }
}
