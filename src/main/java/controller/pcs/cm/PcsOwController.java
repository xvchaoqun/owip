package controller.pcs.cm;

import controller.PcsBaseController;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.pcs.PcsAdminReport;
import domain.pcs.PcsCandidateView;
import domain.pcs.PcsConfig;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsBranchBean;
import persistence.common.bean.PcsPartyBean;
import shiro.ShiroHelper;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class PcsOwController extends PcsBaseController {

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
                fileName = String.format("附表2-%s. %s候选人推荐提名汇总表（院系级党组织用）（%s）",
                        type, PcsConstants.PCS_USER_TYPE_MAP.get(type), party.getName());
                break;
            case "4-1":
                wb = pcsExportService.exportPartyCandidates(null, configId, stage, type);
                fileName = String.format("%s候选人初步人选推荐提名汇总表（“%s”阶段）",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type), PcsConstants.PCS_STAGE_MAP.get(stage));
                break;
            case "5-1":
                wb = pcsExportService.exportPartyCandidates2(configId, stage, type);
                fileName = String.format("附表5-%s. %s候选人推荐提名汇总表（报上级用）", type,
                        PcsConstants.PCS_USER_TYPE_MAP.get(type));
                break;
            case "6":
                wb = pcsExportService.exportRecommends_6(configId, stage);
                fileName = String.format("参加两委委员候选人推荐提名情况汇总表（“%s”阶段）", PcsConstants.PCS_STAGE_MAP.get(stage));
                break;
            case "7-1":
                wb = pcsExportService.exportPartyCandidates(true, configId, stage, type);
                String stageStr = "";
                if (stage == PcsConstants.PCS_STAGE_FIRST)
                    stageStr = "二下";
                if (stage == PcsConstants.PCS_STAGE_SECOND)
                    stageStr = "三下";
                fileName = String.format("%s候选人初步人选名册（“%s”名单）",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type), stageStr);
                break;
        }

        if (wb != null) {
            ExportHelper.output(wb, fileName + ".xlsx", response);
        }

        return null;
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw")
    public String pcsOw(@RequestParam(required = false, defaultValue = "1") byte cls,
                        byte stage,
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

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        int hasReportCount = iPcsMapper.countPcsPartyBeans(configId, stage, null, true);
        int hasNotReportCount = iPcsMapper.countPcsPartyBeans(configId, stage, null, false);
        modelMap.put("hasReportCount", NumberUtils.trimToZero(hasReportCount));
        modelMap.put("hasNotReportCount", NumberUtils.trimToZero(hasNotReportCount));

        return "pcs/pcsOw/pcsOw_party_page";
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_stat_candidate_page")
    public String pcsOw_stat_candidate_page(
            byte stage,
            @RequestParam(required = false,
                    defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
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
        List<PcsPartyBean> records = iPcsMapper.selectPcsPartyBeans(configId, stage, null, null, new RowBounds());
        modelMap.put("records", records);

        Map<Integer, Integer> partyMemberCountMap = new HashMap<>();
        // 获得完成推荐的支部（排除之后的新建支部）
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeans(configId, stage, null, null, true, new RowBounds());
        for (PcsBranchBean pcsBranchBean : pcsBranchBeans) {

            Integer partyId = pcsBranchBean.getPartyId();
            Integer memberCount = NumberUtils.trimToZero(partyMemberCountMap.get(partyId));
            Integer _memberCount = NumberUtils.trimToZero(pcsBranchBean.getMemberCount());

            partyMemberCountMap.put(partyId, memberCount + _memberCount);
        }
        modelMap.put("partyMemberCountMap", partyMemberCountMap);

        return "pcs/pcsOw/pcsOw_party_table_page";
    }

    // 单个分党委下的详情
    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_party_detail_page")
    public String pcsOw_party_detail_page(int partyId, ModelMap modelMap) {

        modelMap.put("party", partyService.findAll().get(partyId));
        return "pcs/pcsOw/pcsOw_party_detail_page";
    }

    // 单个分党委下的汇总情况
    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_party_candidate_page")
    public String pcsOw_candidate_page(int partyId,
                                       Integer userId,
                                       @RequestParam(required = false,
                                               defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
                                       ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("party", partyService.findAll().get(partyId));
        modelMap.put("type", type);

        return "pcs/pcsOw/pcsOw_party_candidate_page";
    }

    // 组织部管理员退回报送
    @RequiresPermissions("pcsOw:admin")
    @RequestMapping(value = "/pcsOw_party_report_back", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsOw_party_report_back(int reportId, byte stage) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        PcsAdminReport pcsAdminReport = pcsAdminReportMapper.selectByPrimaryKey(reportId);
        Assert.isTrue(pcsAdminReport.getConfigId() == configId && pcsAdminReport.getStage() == stage);

        pcsOwService.reportBack(reportId);

        logger.info(addLog(SystemConstants.LOG_PCS, "[组织部管理员]退回报送-%s", JSONUtils.toString(pcsAdminReport, false)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsOw:admin")
    @RequestMapping(value = "/pcsOw_issue", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsOw_issue(byte stage) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        pcsOwService.issue(configId, stage);

        logger.info(addLog(SystemConstants.LOG_PCS, "[组织部管理员]下发名单-%s-%s", configId, stage));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsOw:admin")
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
        logger.info(addLog(SystemConstants.LOG_PCS,
                "[组织部管理员]%s名单-%s", isChosen ? "选入" : "删除", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsOw:admin")
    @RequestMapping(value = "/pcsCandidateChosen_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCandidateChosen_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {
        // chosenId
        pcsOwService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_PCS, "入选名单调序：%s, %s", id, addNum));

        return success(FormUtils.SUCCESS);
    }

    // 推荐详情
    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_recommend_detail")
    public String pcsOw_recommend_detail(byte stage,
                                int userId,
                                @RequestParam(required = false,
                                        defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
                                ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        List<IPcsCandidateView> records = iPcsMapper.selectPartyCandidates(userId, null, configId, stage, type,
                new RowBounds());

        IPcsCandidateView candidate = records.get(0);
        //modelMap.put("candidate", candidate);

        // 获得完成推荐的支部（排除之后的新建支部）
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeans(configId, stage, null, null, true, new RowBounds());
        Map<Integer, Set<Integer>> recommendPartyIdMap = new HashMap<>();
        Set<Integer> recommendDirectPartyIdSet = new HashSet<>();
        for (PcsBranchBean b : pcsBranchBeans) {

            Integer partyId = b.getPartyId();
            Integer branchId = b.getBranchId();

            if(branchId!=null){ // 处理不是直属支部的分党委
                Set<Integer> branchIdSet = recommendPartyIdMap.get(partyId);
                if(branchIdSet==null) branchIdSet = new HashSet<>();
                branchIdSet.add(branchId);

                recommendPartyIdMap.put(partyId, branchIdSet); // 分党委ID - 已推荐支部ID<SET>
            }else{
                recommendDirectPartyIdSet.add(partyId); // 直属党支部ID<SET>
            }
        }

        String partyIdsStr = candidate.getPartyIds(); // 已选的分党委（包含直属党支部）
        //String branchIdsStr = candidate.getBranchIds(); // 已选的支部 group_concat有长度限制！！
        Set<Integer> selectedPartyIdSet = new HashSet<>();
        if (StringUtils.isNotBlank(partyIdsStr)) {
            for (String partyIdStr : partyIdsStr.split(",")) {
                selectedPartyIdSet.add(Integer.parseInt(partyIdStr));
            }
        }
        Set<Integer> selectedBranchIdSet = new HashSet<>();
        List<Integer> _branchIds = iPcsMapper.selectCandidateBranchIds(userId, configId, stage, type);
        if(_branchIds!=null && _branchIds.size()>0)
            selectedBranchIdSet.addAll(_branchIds);


        List<PcsOwBranchBean> beans = new ArrayList<>();

        List<PcsPartyBean> pcsPartyBeans = iPcsMapper.selectPcsPartyBeans(configId, stage, null, true, new RowBounds());
        for (PcsPartyBean pcsPartyBean : pcsPartyBeans) {

            //if(pcsPartyBean.getIsDeleted()) continue;

            Integer partyId = pcsPartyBean.getId();
            boolean directBranch = partyService.isDirectBranch(partyId);
            PcsOwBranchBean bean = new PcsOwBranchBean();
            bean.setPartyId(partyId);
            bean.setPartyName(pcsPartyBean.getName());
            bean.setIsRecommend(selectedPartyIdSet.contains(partyId));
            bean.setIsDirectBranch(directBranch);

            Set<Integer> branchIds = new HashSet<>();
            Set<Integer> notbranchIds = new HashSet<>();
            if(!directBranch){

                Set<Integer> branchIdSet = recommendPartyIdMap.get(partyId);
                bean.setTotalBranchIds(branchIdSet);

                Set<Integer> result = new HashSet<Integer>();
                // 交集 就是获得推荐的支部
                result.clear();
                result.addAll(branchIdSet);
                result.retainAll(selectedBranchIdSet);
                branchIds.addAll(result);

                // 差集 就是未获得推荐的支部
                result.clear();
                result.addAll(branchIdSet);
                result.removeAll(selectedBranchIdSet);
                notbranchIds.addAll(result);

                bean.setBranchIds(branchIds);
                bean.setNotbranchIds(notbranchIds);
            }

            beans.add(bean);

        }

        modelMap.put("beans", beans);

        return "/pcs/pcsOw/pcsOw_recommend_detail";
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_recommend_detail_branchs")
    public String pcsOw_recommend_detail_branchs(int partyId, String branchIds, ModelMap modelMap) {

        modelMap.put("party", partyService.findAll().get(partyId));

        List<Integer> selectedBranchIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(branchIds)) {
            for (String branchIdStr : branchIds.split(",")) {
                selectedBranchIdList.add(Integer.parseInt(branchIdStr));
            }
        }

        BranchExample example = new BranchExample();
        example.createCriteria().andIdIn(selectedBranchIdList);
        List<Branch> branchs = branchMapper.selectByExample(example);
        modelMap.put("branchs", branchs);

        return "pcs/pcsOw/pcsOw_recommend_detail_branchs";
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_stat_candidate_data")
    public void pcsOw_stat_candidate_data(HttpServletResponse response,
                                          byte stage,
                                          Integer userId,
                                          @RequestParam(required = false, defaultValue = "1") byte cls,
                                          @RequestParam(required = false,
                                                  defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
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
                                             defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
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
                                 Boolean hasReport,
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

        int count = iPcsMapper.countPcsPartyBeans(configId, stage, partyId, hasReport);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPartyBean> records = iPcsMapper.selectPcsPartyBeans(configId, stage, partyId, hasReport,
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

    // 单个分党委下的支部推荐情况
    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_party_branch_page")
    public String pcsOw_party_branch_page(int partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("partyId", partyId);
        modelMap.put("isDirectBranch", partyService.isDirectBranch(partyId));

        if (branchId != null) {
            modelMap.put("branch", branchService.findAll().get(branchId));
        }
        return "pcs/pcsOw/pcsOw_party_branch_page";
    }

    @RequiresPermissions("pcsOw:list")
    @RequestMapping("/pcsOw_party_branch_data")
    public void pcsOw_party_branch_data(HttpServletResponse response,
                                        int partyId,
                                        byte stage,
                                        Integer branchId,
                                        @RequestParam(required = false, defaultValue = "0") int export,
                                        @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                        Integer pageSize, Integer pageNo) throws IOException {

        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iPcsMapper.countPcsBranchBeans(configId, stage, partyId, branchId, null);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsBranchBean> records =
                iPcsMapper.selectPcsBranchBeans(configId, stage, partyId, branchId, null,
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
    @RequestMapping("/pcsOw_party_branch_detail")
    public String pcsOw_party_branch_detail(byte stage, int partyId, Integer branchId, ModelMap modelMap) {

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        modelMap.put("party", partyService.findAll().get(partyId));

        PcsBranchBean record = new PcsBranchBean();
        record.setPartyId(partyId);
        record.setBranchId(branchId);

        PcsBranchBean pcsBranchBean = pcsRecommendService.get(partyId, branchId, pcsConfig.getId(), stage);
        if (pcsBranchBean != null) {
            try {
                PropertyUtils.copyProperties(record, pcsBranchBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        record.setConfigId(configId);
        modelMap.put("pcsRecommend", record);

        // 读取党委委员、纪委委员
        List<PcsCandidateView> dwCandidates =
                pcsCandidateService.find(record.getPartyId(),
                        record.getBranchId(), configId, stage, PcsConstants.PCS_USER_TYPE_DW);
        List<PcsCandidateView> jwCandidates =
                pcsCandidateService.find(record.getPartyId(),
                        record.getBranchId(), configId, stage, PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("dwCandidates", dwCandidates);
        modelMap.put("jwCandidates", jwCandidates);

        // 干部管理员才可以进行修改
        modelMap.put("allowModify", ShiroHelper.isPermitted("pcsOw:admin"));
        /*
        modelMap.put("allowModify", pcsPartyService.allowModify(partyId,
                pcsConfigService.getCurrentPcsConfig().getId(), stage));*/

        return "pcs/pcsRecommend/pcsRecommend_au";
    }
}
