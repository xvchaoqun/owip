package sys.helper;

import domain.member.MemberApply;
import domain.member.MemberApplyView;
import domain.party.Branch;
import domain.party.Party;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.party.BranchMemberService;
import service.party.BranchService;
import service.party.PartyMemberService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class PartyHelper {

    private static Logger logger = LoggerFactory.getLogger(PartyHelper.class);

    private static PartyService partyService = CmTag.getBean(PartyService.class);
    private static BranchService branchService = CmTag.getBean(BranchService.class);
    private static PartyMemberService partyMemberService = CmTag.getBean(PartyMemberService.class);
    private static BranchMemberService branchMemberService = CmTag.getBean(BranchMemberService.class);

    public static Party getParty(Integer partyId) {

        return partyService.findAll().get(partyId);
    }

    public static void checkAuth(int partyId) {

       checkAuth(partyId, null);
    }

    public static void checkAuth(int partyId, Integer branchId) {

        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = isPresentPartyAdmin(loginUserId, partyId);

            if (!isAdmin && branchId != null) { // 只有支部管理员或分党委管理员可以维护党员信息
                isAdmin = isPresentBranchAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }
    }

    public static Boolean isPresentBranchAdmin(Integer userId, Integer partyId, Integer branchId) {

        if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) return true;

        return branchMemberService.isPresentAdmin(userId, partyId, branchId);
    }

    public static Boolean isPresentPartyAdmin(Integer userId, Integer partyId) {

        if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) return true;

        return partyMemberService.isPresentAdmin(userId, partyId);
    }

    // 是否直属党支部
    public static Boolean isDirectBranch(Integer partyId) {
        if (partyId == null) return false;
        return partyService.isDirectBranch(partyId);
    }

    // 是否分党委
    public static Boolean isParty(Integer partyId) {
        return partyService.isParty(partyId);
    }

    // 是否党总支
    public static Boolean isPartyGeneralBranch(Integer partyId) {
        return partyService.isPartyGeneralBranch(partyId);
    }

    // 用于jsp页面显示党组织名称
    public static String displayParty(Integer partyId, Integer branchId) {



        String html = "<span class=\"{0}\">{1}<span><span class=\"{2}\">{3}<span>";
        Party party = null;
        Branch branch = null;
        if (partyId != null) {
            Map<Integer, Party> partyMap = partyService.findAll();
            party = partyMap.get(partyId);
        }
        if (branchId != null) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            branch = branchMap.get(branchId);
        }

        return MessageFormat.format(html, (party != null && party.getIsDeleted()) ? "delete" : "", party != null ? party.getName() : "",
                (branch != null && branch.getIsDeleted()) ? "delete" : "", branch != null ? (party != null ? " - " : "") + branch.getName() : "");
    }

    public static String getApplyStatus(MemberApplyView memberApplyView) {

        MemberApply memberApply = new MemberApply();
        try {
            PropertyUtils.copyProperties(memberApply, memberApplyView);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("异常", e);
        }

        return getApplyStatus(memberApply);
    }
    public static String getApplyStatus(MemberApply memberApply) {
        String stage = "";
        switch (memberApply.getStage()) {
            case OwConstants.OW_APPLY_STAGE_INIT:
                stage = "待支部审核";
                break;
            case OwConstants.OW_APPLY_STAGE_DENY:
                stage = "申请未通过";
                break;
            case OwConstants.OW_APPLY_STAGE_PASS:
                stage = "申请通过，待支部确定为入党积极分子";
                break;
            case OwConstants.OW_APPLY_STAGE_ACTIVE:
                if (memberApply.getCandidateStatus() == null || memberApply.getCandidateTime() == null) {
                    stage = "待支部确定为发展对象";
                } else if (memberApply.getCandidateStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                } else if (memberApply.getCandidateStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                if (memberApply.getPlanStatus() == null || memberApply.getPlanTime() == null) {
                    stage = "待支部列入发展计划";
                } else if (memberApply.getPlanStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                } else if (memberApply.getPlanStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_PLAN:
                if (memberApply.getDrawStatus() == null || memberApply.getDrawTime() == null) {
                    stage = "待分党委提交领取志愿书";
                } /*else if (memberApply.getDrawStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "待分党委审核";
                } else if (memberApply.getDrawStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }*/
                break;
            case OwConstants.OW_APPLY_STAGE_DRAW:
                if (memberApply.getGrowStatus() == null) {
                    stage = "待组织部审核";
                } else if (memberApply.getGrowStatus() == OwConstants.OW_APPLY_STATUS_OD_CHECKED) {
                    stage = "组织部已审核，待支部发展为预备党员";
                } else if (memberApply.getGrowStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_GROW:
                if (memberApply.getPositiveStatus() == null || memberApply.getPositiveTime() == null) {
                    stage = "待支部提交预备党员转正";
                } else if (memberApply.getPositiveStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                } else if (memberApply.getPositiveStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = "分党委已审核，待组织部审核";
                } else if (memberApply.getPositiveStatus() == OwConstants.OW_APPLY_STATUS_OD_CHECKED) {
                    stage = "已审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_POSITIVE:
                stage = "已转正";
                break;
        }
        return stage;
    }
}
