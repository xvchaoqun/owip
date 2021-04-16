package sys.constants;

import shiro.ShiroHelper;

/**
 * Created by lm on 2018/2/8.
 */
public class RoleConstants {

    // 后台赋予的角色（与数据库对应的角色字符串不可以修改！）
    public static final String ROLE_SUPER = "super"; // 控制台
    public static final String ROLE_ADMIN = "admin"; // 系统管理员
    public static final String ROLE_ODADMIN = "odAdmin"; // 党建管理员
    public static final String ROLE_CET_ADMIN = "cet_admin"; // 培训管理员
    public static final String ROLE_PMD_OW = "role_pmd_ow"; // 党费管理员
    public static final String ROLE_OA_ADMIN = "role_oa_admin"; // 协同办公管理员
    public static final String ROLE_PM_ADMIN = "role_pm_admin"; // 组织生活管理员
    public static final String ROLE_DP_ADMIN = "role_dp_admin"; // 统战管理员
    public static final String ROLE_PCS_ADMIN = "role_pcs_admin"; // 党代会管理员
    public static final String ROLE_UNIT_ADMIN = "role_unit_admin"; // 班子负责人

    public static boolean isCadreAdmin(){
        return ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREADMIN);
    }
/*
    public static boolean isOwAdmin(){
        return ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
    }
    public static boolean isDpAdmin(){
        return ShiroHelper.isPermitted(RoleConstants.PERMISSION_DPPARTYVIEWALL);
    }
*/

    // 系统特殊的权限（与数据库对应）
    public static final String PERMISSION_CADREADMIN = "cadre:admin"; // 干部管理员
    public static final String PERMISSION_CADREARCHIVE = "cadre:archive"; // 干部档案查看权限
    public static final String PERMISSION_CADREONLYVIEW = "cadre:onlyView"; // 仅允许查看干部信息的权限
    public static final String PERMISSION_CADREADMINSELF = "cadre:adminSelf"; // 仅允许管理本人干部信息的权限
    public static final String PERMISSION_PARTYVIEWALL = "party:viewAll"; // 查看所有基层党组织的权限
    public static final String PERMISSION_PMVIEWALL = "pm:viewAll"; // 查看所有组织生活审核的权限
    public static final String PERMISSION_PARTYMEMBERARCHIVE = "partyMember:archive"; // 基础党组织成员档案查看权限
    public static final String PERMISSION_DPPARTYVIEWALL = "dp:viewAll"; //看所有民主党派的权限
    public static final String PERMISSION_PMDVIEWALL = "pmd:viewAll"; // 党费收缴查看所有党委、支部的权限
    public static final String PERMISSION_ABROADADMIN = "abroad:admin"; // 因私管理员权限
    public static final String PERMISSION_CLAADMIN = "cla:admin"; // 请假管理员权限
    public static final String PERMISSION_COMPANYAPPLY = "parttime:companyApply";//兼职申报权限

    //系统自动赋予的角色
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
    public static final String ROLE_DP_PARTY = "role_dp_party";
    public static final String ROLE_DP_MEMBER = "dp_member";
    public static final String ROLE_TEACHER = "role_teacher"; // 教职工

    public static final String ROLE_PCS_ADMIN2 = "role_pcs_admin2"; // 党代会-组织部管理员（党代表）
    public static final String ROLE_PCS_BRANCH = "role_pcs_branch"; // 党代会-支部管理员
    public static final String ROLE_PCS_PR = "role_pcs_pr"; // 党代表
    public static final String ROLE_PCS_VOTE_DW = "role_pcs_vote_dw"; // 党代会-两委选举-党委录入
    public static final String ROLE_PCS_VOTE_JW = "role_pcs_vote_jw"; // 党代会-两委选举-纪委录入

    public static final String ROLE_OA_USER = "role_oa_user"; // 协同办公用户

    public static final String ROLE_PMD_PARTY = "role_pmd_party"; // 党费收缴-分党委管理员
    public static final String ROLE_PMD_BRANCH = "role_pmd_branch"; // 党费收缴-支部管理员

    public static final String ROLE_CET_ADMIN_UPPER = "cet_admin_upper"; // 单位管理员（上级调训）-干部教育培训
    public static final String ROLE_CET_ADMIN_PARTY = "cet_admin_party"; // 二级党委管理员-干部教育培训
    public static final String ROLE_CET_ADMIN_UNIT_PARTY = "cet_admin_unit_party"; // 二级单位审核员-干部教育培训
}
