package controller.member;

import domain.party.RetireApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

// 党员退休（弃用）
//@Controller
public class RetireApplyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/retireApply")
    public String retireApply(int userId, ModelMap modelMap) {

        RetireApply retireApply = retireApplyMapper.selectByPrimaryKey(userId);
        modelMap.put("retireApply", retireApply);

        if(retireApply!=null){
            modelMap.put("party", partyService.findAll().get(retireApply.getPartyId()));
            if(retireApply.getBranchId()!=null){
                modelMap.put("branch", branchService.findAll().get(retireApply.getBranchId()));
            }
            if(retireApply.getStatus()==SystemConstants.RETIRE_APPLY_STATUS_UNCHECKED){

                return "party/retireApply/retireApply_verify";  // 审核
            }
        }

        return "party/retireApply/retireApply_au"; // 提交或修改
    }

    @RequestMapping(value = "/retireApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_retireApply_au(@CurrentUser SysUserView loginUser, RetireApply retireApply, HttpServletRequest request) {

        //操作人应该是申请人所在党支部或直属党支部管理员
        int loginUserId = loginUser.getId();
        Integer branchId = retireApply.getBranchId();
        Integer partyId = retireApply.getPartyId();
        boolean branchAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
        boolean partyAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectBranch(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        retireApply.setApplyId(loginUser.getId());
        retireApply.setCreateTime(new Date());
        retireApply.setStatus(SystemConstants.RETIRE_APPLY_STATUS_UNCHECKED);

        retireApplyService.insertSelective(retireApply);
        logger.info(addLog(SystemConstants.LOG_OW, "党员退休-提交"));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/retireApply_verify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_retireApply_verify(@CurrentUser SysUserView loginUser, int userId, HttpServletRequest request) {

        //该分党委管理员应是申请人所在的分党委
        int loginUserId = loginUser.getId();
        RetireApply retireApply = retireApplyService.get(userId);
        Integer partyId = retireApply.getPartyId();
        if(!partyMemberService.isPresentAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        retireApplyService.verify(userId, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "党员退休-审核"));
        return success(FormUtils.SUCCESS);
    }
}
