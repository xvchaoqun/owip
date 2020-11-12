package sys.constants;

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
    public static final String ROLE_CADRE_LEAVE = "cadre_leave";
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
    public static final String ROLE_DP_MEMBER = "dp_member";
    // 三大管理员
    public static final String ROLE_ODADMIN = "odAdmin";
    public static final String ROLE_CADREADMIN = "cadreAdmin";
    public static final String ROLE_CET_ADMIN = "cet_admin"; // 干部培训管理员

    public static final String ROLE_TEACHER = "role_teacher"; // 教职工
    public static final String ROLE_PCS_ADMIN = "role_pcs_admin"; // 党代会-组织部管理员
    public static final String ROLE_PCS_ADMIN2 = "role_pcs_admin2"; // 党代会-组织部管理员（党代表）
    public static final String ROLE_PCS_BRANCH = "role_pcs_branch"; // 党代会-支部管理员
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
}
