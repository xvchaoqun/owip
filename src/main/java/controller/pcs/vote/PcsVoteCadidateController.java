package controller.pcs.vote;

import controller.pcs.PcsBaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsVoteCandidate;
import domain.pcs.PcsVoteGroup;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
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
import sys.utils.ExportHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsVoteCadidateController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsVoteStat:candidate")
    @RequestMapping("/pcsVoteCandidate")
    public String pcsVoteCandidate(byte type, ModelMap modelMap) {

        modelMap.put("type", type);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("currentPcsConfig", currentPcsConfig);

        PcsVoteGroup pcsVoteGroup = iPcsMapper.statPcsVoteGroup(type);
        modelMap.put("pcsVoteGroup", pcsVoteGroup);

        List<PcsVoteCandidate> pcsVoteCandidates = iPcsMapper.selectVoteCandidateStatList(type, null, null);
        modelMap.put("candidates", pcsVoteCandidates);

        return "pcs/pcsVoteCandidate/pcsVoteCandidate_page";
    }

    @RequiresPermissions("pcsVoteStat:candidate")
    @RequestMapping(value = "/pcsVoteCandidate_choose", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteCandidate_choose(Integer[] ids, // userIds
                               byte type, Boolean isChosen) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        isChosen = BooleanUtils.isTrue(isChosen);
        pcsVoteCandidateService.choose(ids, isChosen, configId, type);
        logger.info(addLog(LogConstants.LOG_PCS,
                "[组织部管理员]%s当选名单-%s", isChosen ? "选入" : "删除", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }


    //@RequiresPermissions("pcsVoteStat:candidate")
    @RequestMapping("/pcsVoteCandidate_export")
    public String pcsVoteCandidate_export(byte cls, Byte type, Integer groupId,
                               HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = null;
        String fileName = null;

        switch (cls) {
            case 0:
                PcsVoteGroup pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(groupId);
                int recordUserId = pcsVoteGroup.getRecordUserId();
                if(recordUserId!= ShiroHelper.getCurrentUserId() && !ShiroHelper.isPermitted("pcsVoteStat:candidate")){
                    throw new UnauthorizedException();
                }
                type = pcsVoteGroup.getType();
                wb = pcsVoteExportService.vote_jp(pcsVoteGroup);
                fileName = String.format("各计票组计票汇总：%s（%s）",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type), pcsVoteGroup.getName());
                break;
            case 1:
                ShiroHelper.checkPermission("pcsVoteStat:candidate");
                wb = pcsVoteExportService.vote(type);
                fileName = String.format("计票汇总用：%s",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type));
                break;
            case 2:
                ShiroHelper.checkPermission("pcsVoteStat:candidate");
                wb = pcsVoteExportService.vote_zj(type);
                fileName = String.format("报总监票人：%s选举结果报告单",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type));
                break;
        }

        if (wb != null) {
            ExportHelper.output(wb, fileName + ".xlsx", response);
        }

        return null;
    }

}
