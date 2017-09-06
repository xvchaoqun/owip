package controller.pcs.pr;

import controller.BaseController;
import domain.party.Party;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsPrPartyBean;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrOwController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_export")
    public String pcsPrOw_export(String file, Integer partyId, byte stage,
                                 HttpServletResponse response) throws IOException {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        XSSFWorkbook wb = null;
        String fileName = null;
        switch (file) {
            case "3":
                Party party = partyService.findAll().get(partyId);
                wb = pcsPrExportService.exportPartyCandidates(configId, stage, partyId);
                fileName = String.format("分党委酝酿代表候选人%s名单（%s）",
                        stage==SystemConstants.PCS_STAGE_FIRST?"初步":"预备",
                        party.getName());
                break;
            case "4":
                party = partyService.findAll().get(partyId);
                wb = pcsPrExportService.exportPartyAllocate(configId, stage, partyId);
                fileName = String.format("分党委酝酿代表候选人%s人选统计表（%s）",
                        stage==SystemConstants.PCS_STAGE_FIRST?"初步":"预备",
                        party.getName());
                break;
            case "5":
                wb = pcsPrExportService.exportPartyCandidates(configId, stage, null);
                fileName =String.format("各分党委酝酿代表候选人%s名单汇总表",
                        stage==SystemConstants.PCS_STAGE_FIRST?"初步":"预备");
                break;
            case "6":
                wb = pcsPrExportService.exportSchoolAllocate(configId, stage);
                fileName =String.format("各分党委酝酿代表候选人%s人选统计表",
                        stage==SystemConstants.PCS_STAGE_FIRST?"初步":"预备");
                break;
            case "7":
                wb = pcsPrExportService.exportSchoolRecommend(configId, stage);
                fileName = "全校党员参与推荐代表候选人情况统计表";
                break;
            case "ow":
                wb = pcsPrExportService.exportAllPartyAllocate(configId, stage);
                fileName =String.format("各分党委酝酿代表候选人%s人选统计表",
                        stage==SystemConstants.PCS_STAGE_FIRST?"初步":"预备");
                break;
        }

        if (wb != null) {
            ExportHelper.output(wb, fileName + ".xlsx", response);
        }

        return null;
    }

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw")
    public String pcsPrOw(@RequestParam(required = false, defaultValue = "1") byte cls,
                          byte stage,
                          Integer partyId,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            // 全校情况
            return "pcs/pcsPrOw/pcsPrOw_page";
        }

        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        int hasReportCount = iPcsMapper.countPcsPrPartyBeans(configId, stage, null, true, null);
        int passCount = iPcsMapper.countPcsPrPartyBeans(configId, stage, null, true,
                SystemConstants.PCS_PR_RECOMMEND_STATUS_PASS);
        int hasNotReportCount = iPcsMapper.countPcsPrPartyBeans(configId, stage, null, false, null);
        modelMap.put("hasReportCount", NumberUtils.trimToZero(hasReportCount));
        modelMap.put("hasNotReportCount", NumberUtils.trimToZero(hasNotReportCount));
        modelMap.put("passCount", NumberUtils.trimToZero(passCount));

        return "pcs/pcsPrOw/pcsPrOw_party_page";
    }

    // 全校党员参与推荐情况
    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_allocate_table_page")
    public String pcsPrOw_allocate_page(
            byte stage,
            Integer userId,
            ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeans(configId, stage, null,
                null, null, new RowBounds());
        modelMap.put("records", records);

        return "pcs/pcsPrOw/pcsPrOw_allocate_table_page";
    }

    @RequiresPermissions("pcsPrOw:check")
    @RequestMapping("/pcsPrOw_check")
    public String pcsPrOw_check(int partyId, ModelMap modelMap) {

        modelMap.put("party", partyService.findAll().get(partyId));

        return "pcs/pcsPrOw/pcsPrOw_check";
    }

    @RequiresPermissions("pcsPrOw:report")
    @RequestMapping(value = "/pcsPrOw_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrOw_check(int partyId, byte stage, Boolean status, String remark) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        pcsPrOwService.checkPartyRecommend(configId, stage, partyId,
                BooleanUtils.isTrue(status) ? SystemConstants.PCS_PR_RECOMMEND_STATUS_PASS
                        : SystemConstants.PCS_PR_RECOMMEND_STATUS_DENY, remark);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "[组织部管理员]审核分党委推荐-%s-%s-%s-%s",
                configId, stage, partyId, status));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_stat_candidate_data")
    public void pcsPrOw_stat_candidate_data(HttpServletResponse response,
                                            byte stage,
                                            Integer userId,
                                            @RequestParam(required = false, defaultValue = "1") byte cls,
                                            @RequestParam(required = false,
                                                    defaultValue = SystemConstants.PCS_USER_TYPE_DW + "") byte type,
                                            Integer pageSize, Integer pageNo) throws IOException {

        // cls=2时读取全部
        Boolean isChosen = null;
        if (cls == 4) isChosen = true;

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        int count = iPcsMapper.countPartyCandidates(userId, isChosen, configId, stage, type);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<IPcsCandidateView> records = iPcsMapper.selectPartyCandidates(userId, isChosen, configId, stage, type,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 单个分党委下的推荐情况
    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_party_detail")
    public String pcsPrOw_party_detail(int partyId,
                                       ModelMap modelMap) {

        modelMap.put("party", partyService.findAll().get(partyId));
        return "pcs/pcsPrOw/pcsPrOw_party_detail";
    }

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_party_candidate_page")
    public String pcsPrOw_party_candidate_page(Integer partyId,
                                               Integer userId,
                                               ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        return "pcs/pcsPrOw/pcsPrOw_party_candidate_page";
    }

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_party_candidate_data")
    public void pcsPrOw_party_candidate_data(HttpServletResponse response,
                                       Integer partyId,
                                       byte stage,
                                       Integer userId,
                                       Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsPrCandidateViewExample example = pcsPrCandidateService.createExample(configId, stage, partyId, userId);

        long count = pcsPrCandidateViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrCandidateView> records = pcsPrCandidateViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 党代表候选人初步人选数据统计
    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_party_table_page")
    public String pcsPrOw_party_table_page(Integer partyId, byte stage, ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if(partyId!=null) {
            // 单个分党委
            PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);
            modelMap.put("pcsPrAllocate", pcsPrAllocate);
        }else{
            // 全校
            PcsPrAllocate pcsPrAllocate = iPcsMapper.schoolPcsPrAllocate(configId);
            modelMap.put("pcsPrAllocate", pcsPrAllocate);
        }

        PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, stage, partyId);
        modelMap.put("realPcsPrAllocate", realPcsPrAllocate);

        return "pcs/pcsPrOw/pcsPrOw_party_table_page";
    }

    // 各分党委推荐情况
    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrOw_party_data")
    public void pcsPrOw_party_data(HttpServletResponse response,
                                   byte stage,
                                   Integer partyId,
                                   Boolean hasReport,
                                   Byte recommendStatus,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        int count = iPcsMapper.countPcsPrPartyBeans(configId, stage, partyId, hasReport, recommendStatus);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeans(configId, stage, partyId, hasReport, recommendStatus,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
