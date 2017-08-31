package controller.pcs;

import controller.BaseController;
import domain.party.Party;
import domain.pcs.PcsConfig;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
import persistence.common.bean.PcsPartyBean;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsOwController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_export")
    public String pcsOw_export(String file, Integer partyId, byte stage, Byte type,
                               HttpServletResponse response) throws IOException {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        XSSFWorkbook wb = null;
        String fileName = null;
        switch (file) {
            case "2-1":
                Party party = partyService.findAll().get(partyId);
                wb = pcsExportService.exportBranchCandidates(configId, stage, type, partyId);
                fileName = SystemConstants.PCS_USER_TYPE_MAP.get(type)
                        + String.format("附表2-%s. 党委委员候选人推荐提名汇总表（院系级党组织用）（%s）", type, party.getName());
                break;
            case "4-1":
                wb = pcsExportService.exportPartyCandidates(null, configId, stage, type);
                fileName = String.format("附表4-%s. %s候选人推荐提名汇总表（组织部用）", type,
                        SystemConstants.PCS_USER_TYPE_MAP.get(type));
                break;
            case "5-1":
                wb = pcsExportService.exportPartyCandidates2(configId, stage, type);
                fileName = String.format("附表5-%s. %s候选人推荐提名汇总表（报上级用）", type,
                        SystemConstants.PCS_USER_TYPE_MAP.get(type));
                break;
            case "6":
                wb = pcsExportService.exportRecommends_6(configId, stage);
                fileName = "附表6. 参加两委委员候选人推荐提名情况汇总表（组织部用）";
                break;
            case "7-1":
                wb = pcsExportService.exportPartyCandidates(true, configId, stage, type);
                fileName = String.format("附表7-%s. %s候选人推荐提名汇总表（组织部用）", type,
                        SystemConstants.PCS_USER_TYPE_MAP.get(type));
                break;
        }

        if(wb!=null) {
            ExportHelper.output(wb, fileName + ".xlsx", response);
        }

        return null;
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw")
    public String pcsOw(@RequestParam(required = false, defaultValue = "1") byte cls,
                        Integer partyId,
                        ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2 || cls == 4) {
            return "forward:/pcsOw_stat_candidate_page";
        } else if (cls == 3) {
            return "forward:/pcsOw_party_table_page";
        }

        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        return "pcs/pcsOw/pcsOw_party_page";
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_stat_candidate_page")
    public String pcsOw_stat_candidate_page(
            byte stage,
            @RequestParam(required = false,
                    defaultValue = SystemConstants.PCS_USER_TYPE_DW + "") byte type,
            Integer userId,
            ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("type", type);

        modelMap.put("hasIssue", pcsOwService.hasIssue(configId, stage));

        return "pcs/pcsOw/pcsOw_stat_candidate_page";
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_party_table_page")
    public String pcsOw_party_table_page(byte stage, ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        List<PcsPartyBean> records = iPcsMapper.selectPcsPartyBeans(configId, stage, null, new RowBounds());
        modelMap.put("records", records);

        return "pcs/pcsOw/pcsOw_party_table_page";
    }

    // 单个分党委下的汇总情况
    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_candidate_page")
    public String pcsOw_candidate_page(int partyId,
                                       Integer userId,
                                       @RequestParam(required = false,
                                               defaultValue = SystemConstants.PCS_USER_TYPE_DW + "") byte type,
                                       ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("party", partyService.findAll().get(partyId));
        modelMap.put("type", type);

        return "pcs/pcsOw/pcsOw_candidate_page";
    }

    @RequiresPermissions("pcsOw:report")
    @RequestMapping(value = "/pcsOw_issue", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsOw_issue(byte stage) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        pcsOwService.issue(configId, stage);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "[组织部管理员]下发名单-%s-%s", configId, stage));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsOw:report")
    @RequestMapping(value = "/pcsOw_choose", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsOw_choose(@RequestParam(value = "ids[]") Integer[] ids, // userIds
                               byte stage,
                               byte type,
                               Boolean isChosen) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        isChosen = BooleanUtils.isTrue(isChosen);
        pcsOwService.choose(ids, isChosen, configId, stage, type);
        logger.info(addLog(SystemConstants.LOG_ADMIN,
                "[组织部管理员]%s名单-%s", isChosen ? "选入" : "删除", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsOw:pcsCandidateChosen:changeOrder")
    @RequestMapping(value = "/pcsCandidateChosen_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCandidateChosen_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {
        // chosenId
        pcsOwService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "入选名单调序：%s, %s", id, addNum));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_stat_candidate_data")
    public void pcsOw_stat_candidate_data(HttpServletResponse response,
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

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_candidate_data")
    public void pcsOw_candidate_data(HttpServletResponse response,
                                     int partyId,
                                     byte stage,
                                     Integer userId,
                                     @RequestParam(required = false,
                                             defaultValue = SystemConstants.PCS_USER_TYPE_DW + "") byte type,
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

        int count = iPcsMapper.countBranchCandidates(userId, configId, stage, type, partyId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<IPcsCandidateView> records = iPcsMapper.selectBranchCandidates(userId, configId, stage, type, partyId,
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

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_party_data")
    public void pcsOw_party_data(HttpServletResponse response,
                                 byte stage,
                                 Integer partyId,
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

        int count = iPcsMapper.countPcsPartyBeans(partyId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPartyBean> records = iPcsMapper.selectPcsPartyBeans(configId, stage, partyId,
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
