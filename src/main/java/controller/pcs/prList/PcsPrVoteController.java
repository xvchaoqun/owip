package controller.pcs.prList;

import controller.global.OpException;
import controller.pcs.PcsBaseController;
import controller.pcs.pr.PcsPrCandidateFormBean;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPartyView;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrRecommend;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.gson.GsonUtils;
import sys.utils.ContentTypeUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrVoteController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("pcsPrVote:list")
    @RequestMapping("/pcsPrVote")
    public String pcsPrVote(Integer partyId, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("pcsPrListOw:admin")) {

            SecurityUtils.getSubject().checkPermission("pcsPrVote:list");
        }

        if(partyId==null){ // 党代会管理员同时也可以是某个分党委管理员

            PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin == null) {
                throw new UnauthorizedException();
            }
            partyId = pcsAdmin.getPartyId();
        }


        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        PcsPartyView pcsPartyView = pcsPartyViewService.get(partyId);
        modelMap.put("pcsPartyView", pcsPartyView);

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId,
                PcsConstants.PCS_STAGE_THIRD, partyId);
        modelMap.put("pcsPrRecommend", pcsPrRecommend);

        // 在第三阶段，共用第二阶段的候选人
        List<PcsPrCandidateView> candidates = pcsPrListService.getList(configId, partyId, null);
        modelMap.put("candidates", candidates);

        boolean hasSort = pcsPrListService.hasSort(configId, partyId);
        modelMap.put("hasSort", hasSort);
        modelMap.put("allowModify", pcsPrPartyService.allowModify(partyId, configId,
                PcsConstants.PCS_STAGE_THIRD) && hasSort);

        return "pcs/pcsPrVote/pcsPrVote_page";
    }

    @RequiresPermissions("pcsPrVote:edit")
    @RequestMapping(value = "/pcsPrVote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrVote(PcsPrRecommend record,
                            String items,
                            MultipartFile _file,
                            HttpServletRequest request) throws IOException, InterruptedException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        if(_file!=null && !_file.isEmpty()) {
            String originalFilename = _file.getOriginalFilename();
            String ext = FileUtils.getExtention(originalFilename);
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                    && !ContentTypeUtils.isFormat(_file, "pdf")) {
                throw new OpException("文件格式错误，请上传pdf文件");
            }
            String savePath = uploadPdf(_file, "pcsPrVote");
            record.setReportFilePath(savePath);
        }

        List<PcsPrCandidateFormBean> beans = GsonUtils.toBeans(items, PcsPrCandidateFormBean.class);
        pcsPrPartyService.submit3(configId, partyId, record, beans);

        logger.info(addLog(LogConstants.LOG_PCS, "上传党员大会选举情况：%s", JSONUtils.toString(record, false)));
        return success(FormUtils.SUCCESS);
    }

}
