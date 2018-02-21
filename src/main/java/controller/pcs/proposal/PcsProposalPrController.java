package controller.pcs.proposal;

import controller.pcs.PcsBaseController;
import domain.pcs.PcsConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by lm on 2017/9/20.
 */
@Controller
public class PcsProposalPrController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 党代表附议
    @RequiresPermissions("pcsProposalPr:seconder")
    @RequestMapping(value = "/pcsProposal_seconder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposal_seconder(int id) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        if(DateUtils.compareDate(new Date(), currentPcsConfig.getProposalSupportTime())){

            return failed("征集附议人截止时间为"
                    + DateUtils.formatDate(currentPcsConfig.getProposalSupportTime(), DateUtils.YYYY_MM_DD_HH_MM));
        }

        pcsProposalService.seconder(id);
        return success(FormUtils.SUCCESS);
    }
}
