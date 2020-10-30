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
        if(!hasBranchAuth(loginUserId, partyId, branchId)){
            throw new UnauthorizedException();
        }
    }

    public static Boolean hasBranchAuth(Integer userId, Integer partyId, Integer branchId) {

        if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)
                || partyMemberService.isPresentAdmin(userId, partyId))
            return true;

        return branchMemberService.isPresentAdmin(userId, partyId, branchId);
    }

    public static Boolean hasPartyAuth(Integer userId, Integer partyId) {

        return partyMemberService.hasAdminAuth(userId, partyId);
    }

    // 是否直属党支部
    public static Boolean isDirectBranch(Integer partyId) {
        if (partyId == null) return false;
        return partyService.isDirectBranch(partyId);
    }

    // 是否分党委
    public static Boolean isParty(int partyId) {
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
    // 用于jsp页面显示支部名称
    public static String displayBranch(Integer partyId, Integer branchId) {

        String html = "<span class=\"{0}\">{1}<span>";
        String branchName = null;
        boolean isDeleted = false;
        Branch branch = null;
        if (branchId != null) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            branch = branchMap.get(branchId);
            branchName = branch.getName();
            isDeleted = branch.getIsDeleted();
        }else{
            Map<Integer, Party> partyMap = partyService.findAll();
           Party party = partyMap.get(partyId);
           branchName = party.getName();
           isDeleted = party.getIsDeleted();
        }

        return MessageFormat.format(html, isDeleted ? "delete" : "", branchName);
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

        String partyName = CmTag.getStringProperty("partyName");
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
                    stage = "支部已提交，待"+ partyName +"审核";
                } else if (memberApply.getCandidateStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                if (memberApply.getPlanStatus() == null || memberApply.getPlanTime() == null) {
                    stage = "待支部列入发展计划";
                } else if (memberApply.getPlanStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待"+ partyName +"审核";
                } else if (memberApply.getPlanStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_PLAN:
                if (memberApply.getDrawStatus() == null || memberApply.getDrawTime() == null) {
                    stage = "待"+ partyName +"提交领取志愿书";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_DRAW:
                if (memberApply.getGrowStatus() == null) {
                    stage = "待组织部审核";
                } else if (memberApply.getGrowStatus() == OwConstants.OW_APPLY_STATUS_OD_CHECKED) {
                    stage = "待支部发展为预备党员";
                } else if (memberApply.getGrowStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待"+ partyName +"审核";
                }
                break;
            case OwConstants.OW_APPLY_STAGE_GROW:
                if (memberApply.getPositiveStatus() == null || memberApply.getPositiveTime() == null) {
                    stage = "待支部提交预备党员转正";
                } else if (memberApply.getPositiveStatus() == OwConstants.OW_APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待"+ partyName +"审核";
                } else if (memberApply.getPositiveStatus() == OwConstants.OW_APPLY_STATUS_CHECKED) {
                    stage = partyName +"已审核，待组织部审核";
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
