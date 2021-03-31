package controller.pcs.prList;

import controller.pcs.PcsBaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import mixin.MixinUtils;
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
import persistence.pcs.common.PcsPrPartyBean;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
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
@RequestMapping("/pcs")
public class PcsPrListOwController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw_export")
    public String pcsPrListOw_export(String file,
                                     HttpServletResponse response) throws IOException {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        XSSFWorkbook wb = null;
        String fileName = null;
        switch (file) {
            case "3":
                wb = pcsPrExportService.exportSchoolList(configId);
                fileName = "全校党代表汇总表";
                break;
            case "4":
                wb = pcsPrExportService.exportAllocate(configId);
                fileName = "党代表数据统计表";
                break;
            case "5":
                wb = pcsPrExportService.exportPartyStat(configId);
                fileName = "党员参与情况表";
                break;
        }

        if (wb != null) {
            ExportHelper.output(wb, fileName + ".xlsx", response);
        }

        return null;
    }

    // 各二级党组织党员大会
    @RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw_party")
    public String pcsPrListOw_party_page(ModelMap modelMap) {

        byte stage = PcsConstants.PCS_STAGE_THIRD;
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        int hasReportCount = iPcsMapper.countPcsPrPartyBeanList(configId, stage, null, true, null);
        int passCount = iPcsMapper.countPcsPrPartyBeanList(configId, stage, null, true,
                PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS);
        int hasNotReportCount = iPcsMapper.countPcsPrPartyBeanList(configId, stage, null, false, null);
        modelMap.put("hasReportCount", NumberUtils.trimToZero(hasReportCount));
        modelMap.put("hasNotReportCount", NumberUtils.trimToZero(hasNotReportCount));
        modelMap.put("passCount", NumberUtils.trimToZero(passCount));

        return "pcs/pcsPrListOw/pcsPrListOw_party_page";
    }

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrListOw_party_data")
    public void pcsPrListOw_party_data(HttpServletResponse response,
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

        byte stage = PcsConstants.PCS_STAGE_THIRD;

        int count = iPcsMapper.countPcsPrPartyBeanList(configId, stage, partyId, hasReport, recommendStatus);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeanList(configId, stage, partyId, hasReport, recommendStatus,
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

    //@RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw")
    public String pcsPrListOw(@RequestParam(required = false, defaultValue = "1") byte cls,
                              Integer userId,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(cls==1){

            PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
            int configId = currentPcsConfig.getId();
            modelMap.put("notPassPCSPrRecommendsCount",
                    pcsPrOwService.notPassPCSPrRecommendsCount(configId, PcsConstants.PCS_STAGE_THIRD));

        }else if (cls == 2) {
            // 全校党代表数据统计
            return "forward:/pcs/pcsPrListOw_table_page";
        } else if (cls == 3) {
            // 全校党员参与情况
            return "forward:/pcs/pcsPrListOw_party_table_page";
        }
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        if(cls==4){
            ShiroHelper.checkPermission("pcsProposalOw:*");
        }else{
            ShiroHelper.checkPermission("pcsPrListOw:list");
        }

        // cls=1 全校党代表名单  cls=4 提案党代表名单
        return "pcs/pcsPrListOw/pcsPrListOw_candidate_page";
    }

    // 全校党代表数据统计
    @RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw_table_page")
    public String pcsPrListOw_candidate_table_page(Integer partyId, ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (partyId != null) {
            // 单个二级党组织
            PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);
            modelMap.put("pcsPrAllocate", pcsPrAllocate);
        } else {
            // 全校
            PcsPrAllocate pcsPrAllocate = iPcsMapper.schoolPcsPrAllocate(configId);
            modelMap.put("pcsPrAllocate", pcsPrAllocate);
        }

        PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId,
                PcsConstants.PCS_STAGE_SECOND, partyId, true);
        modelMap.put("realPcsPrAllocate", realPcsPrAllocate);

        return "pcs/pcsPrListOw/pcsPrListOw_table_page";
    }

    // 全校党员参与情况
    @RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw_party_table_page")
    public String pcsPrListOw_party_table_page(ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeanList(configId, PcsConstants.PCS_STAGE_THIRD, null,
                null, null, new RowBounds());
        modelMap.put("records", records);

        return "pcs/pcsPrListOw/pcsPrListOw_party_table_page";
    }

    // 全校党代表名单
    //@RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw_candidate_data")
    public void pcsPrListOw_candidate_data(HttpServletResponse response,
                                           @RequestParam(required = false, defaultValue = "1") byte cls,
                                           Integer userId,
                                           Integer pageSize, Integer pageNo) throws IOException {

        if(cls==4){
            // 提案党代表名单
            ShiroHelper.checkPermission("pcsProposalOw:*");
        }else{
            ShiroHelper.checkPermission("pcsPrListOw:list");
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        PcsPrCandidateExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andStageEqualTo(PcsConstants.PCS_STAGE_SECOND)
                .andIsChosenEqualTo(true);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(cls==4){
            criteria.andIsProposalEqualTo(true);
            example.setOrderByClause("proposal_sort_order asc");
        }else {
            example.setOrderByClause("party_sort_order desc, type asc, realname_sort_order asc");
        }

        long count = pcsPrCandidateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrCandidate> records = pcsPrCandidateMapper.selectByExampleWithRowbounds(example,
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

    // 单个二级党组织下的详情
    @RequiresPermissions("pcsPrListOw:list")
    @RequestMapping("/pcsPrListOw_party_detail_page")
    public String pcsPrListOw_party_detail_page(int partyId, ModelMap modelMap) {

        modelMap.put("party", partyService.findAll().get(partyId));
        return "pcs/pcsPrListOw/pcsPrListOw_party_detail_page";
    }

    // 同步全校党代表名单至提案党代表名单
    @RequiresPermissions("pcsPrListOw:sync")
    @RequestMapping(value = "/pcsPrListOw_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrListOw_sync() {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        pcsPrOwService.sync(configId);

        logger.info(addLog(LogConstants.LOG_PCS, "同步全校党代表名单至提案党代表名单"));
        return success(FormUtils.SUCCESS);
    }
}
