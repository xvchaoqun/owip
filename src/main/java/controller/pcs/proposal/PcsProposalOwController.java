package controller.pcs.proposal;

import controller.pcs.PcsBaseController;
import domain.base.MetaType;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsProposalView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.tags.CmTag;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lm on 2017/9/20.
 */
@Controller
@RequestMapping("/pcs")
public class PcsProposalOwController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsProposalOw:changeOrder")
    @RequestMapping(value = "/pcsProposal_candidateChangeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposal_candidateChangeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        // candidateId
        pcsProposalOwService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PCS, "党代表名单调序：%s, %s", id, addNum));

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("pcsProposalOw:check")
    @RequestMapping("/pcsProposal_check")
    public String pcsProposal_check(Integer id,
                                    // type=0 查看 type =1 审核 type=2 附议
                                    @RequestParam(required = false, defaultValue = "0") int type,
                                    ModelMap modelMap) {

        if(type==1){
            ShiroHelper.checkPermission("pcsProposalOw:*");
        }else if(type==2){
            ShiroHelper.checkPermission("pcsProposalPr:*");
        }else if(type==0){
            if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                    && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
                throw new UnauthenticatedException();
            }
        }else{
            throw new UnauthenticatedException();
        }

        PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(id);
        modelMap.put("pcsProposal", pcsProposal);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        // 被邀请附议人
        modelMap.put("inviteSeconders", pcsProposalService.getInviteCandidates(configId, pcsProposal));

        // 附议人
        modelMap.put("seconders", pcsProposalService.getSeconderCandidates(configId, pcsProposal));

        // 提案人
        int userId = pcsProposal.getUserId();
        PcsPrCandidate pcsPrCandidate =
                pcsPrCandidateService.find(userId, configId, PcsConstants.PCS_STAGE_SECOND);
        modelMap.put("candidate", pcsPrCandidate);

        // 提案类型列表
        Map<Integer, MetaType> prTypes = CmTag.getMetaTypes("mc_pcs_proposal");
        modelMap.put("prTypes", prTypes.values());

        return "pcs/pcsProposal/pcsProposal_check";
    }

    @RequiresPermissions("pcsProposalOw:check")
    @RequestMapping(value = "/pcsProposal_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposal_check(int id, boolean status, ModelMap modelMap) {

        if(pcsProposalOwService.check(id, status)>0 && status){

            // 短信通知被邀请附议人
            PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(id);
            PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
            int configId = currentPcsConfig.getId();

            pcsProposalService.infoInviteSeconders(configId, pcsProposal);
        }

        return success(FormUtils.SUCCESS);
    }
}
