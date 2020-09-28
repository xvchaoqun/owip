package controller.pcs;

import domain.party.Branch;
import domain.party.Party;
import domain.pcs.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import persistence.pcs.common.IPcsCandidate;
import persistence.pcs.common.PcsBranchBean;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsPartyController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPartyList:list")
    @RequestMapping("/pcsPartyList")
    public String pcsPartyList(@RequestParam(required = false, defaultValue = "1") byte cls,Integer partyId,ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));

        if (cls != 1) {
            return "forward:/pcs/pcsBranch";
        }
        return "pcs/pcsParty/pcsParty_page";
    }

    @RequiresPermissions("pcsPartyList:list")
    @RequestMapping("/pcsPartyList_data")
    @ResponseBody
    public void pcsPartyList_data(HttpServletResponse response,
                              Integer partyId,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer[] ids, // 导出的记录
                              Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsPartyExample example = new PcsPartyExample();
        PcsPartyExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId);
        example.setOrderByClause("sort_order desc");

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            // 有查看基层党组织的权限的话，则可以查看所有的
            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            if (partyIdList.size() > 0)
                criteria.andPartyIdIn(partyIdList);
            else criteria.andPartyIdIsNull();
        }

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        long count = pcsPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsParty> records= pcsPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsParty.class, pcsPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping(value = "/pcsParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsParty_au(PcsParty record, HttpServletRequest request) {

        pcsPartyService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_PCS, "更新召开党代会的支部：{0}", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping("/pcsParty_au")
    public String pcsParty_au(int id, ModelMap modelMap) {


        PcsParty pcsParty = pcsPartyMapper.selectByPrimaryKey(id);
        modelMap.put("pcsParty", pcsParty);

        return "pcs/pcsParty/pcsParty_au";
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping(value = "/pcsParty_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsParty_sync(Integer pcsPartyId,Integer pcsBranchId) {

        pcsPartyService.syncPcsPartyAndBranch(pcsPartyId, pcsBranchId);
        logger.info(log( LogConstants.LOG_PCS, "同步当前党组织 "));

        return success(FormUtils.SUCCESS);
    }

  /*  @RequestMapping(value = "/pcsParty_batchSync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsParty_batchSync(HttpServletRequest request, Integer[] ids) {

        pcsPartyService.batchSync(ids);
        logger.info(log( LogConstants.LOG_PCS, "同步当前党组织 "));

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping(value = "/pcsParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsParty_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pcsPartyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量删除党代会分党委：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsParty:list")
    @RequestMapping("/pcsParty_export")
    public String pcsParty_export(String file, byte stage, Byte type, HttpServletResponse response) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();
        //Party party = partyService.findAll().get(partyId);

        XSSFWorkbook wb = null;
        String fileName = null;
        switch (file){
            case "1-1":
                wb = pcsExportService.exportIssueCandidates(configId, stage, type);
                String stageStr = "";
                if(stage== PcsConstants.PCS_STAGE_FIRST)
                    stageStr = "二下";
                if(stage==PcsConstants.PCS_STAGE_SECOND)
                    stageStr = "三下";
                fileName = String.format("%s候选人初步人选名册（“%s”名单，党支部用）",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type), stageStr);
                break;
            case "2-1":
                wb = pcsExportService.exportBranchCandidates(configId, stage, type, partyId);
                fileName = String.format("%s候选人初步人选推荐提名汇总表（“%s”阶段）",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type), PcsConstants.PCS_STAGE_MAP.get(stage));
                break;
            case "3":
                wb = pcsExportService.exportRecommends_3(configId, stage, partyId);
                fileName = String.format("参加两委委员候选人推荐提名情况汇总表（“%s”阶段）", PcsConstants.PCS_STAGE_MAP.get(stage));
                break;
        }

        if(wb!=null)
            ExportHelper.output(wb, fileName + ".xlsx", response);

        return null;
    }

    @RequiresPermissions("pcsParty:list")
    @RequestMapping("/pcsParty")
    public String pcsParty(@RequestParam(required = false, defaultValue = "1") byte cls,
                           @RequestParam(required = false,
                                   defaultValue = PcsConstants.PCS_USER_TYPE_DW+"") byte type,
                           Integer userId,
                           ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/pcs/pcsParty_branch_table_page";
        } else if (cls == 3) {
            return "forward:/pcs/pcsParty_report_page";
        }
        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("type", type);
        return "pcs/pcsParty/pcsParty_candidate_page";
    }

    @RequiresPermissions("pcsParty:list")
    @RequestMapping("/pcsParty_branch_table_page")
    public String pcsParty_branch_table_page(byte stage,ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        List<PcsBranchBean> records = iPcsMapper.selectPcsBranchBeanList(configId, stage, partyId, null, null,null, new RowBounds());
        modelMap.put("records", records);

        return "pcs/pcsParty/pcsParty_branch_table_page";
    }

    @RequiresPermissions("pcsParty:list")
    @RequestMapping("/pcsParty_report_page")
    public String pcsParty_report_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();

        PcsAdminReportExample example = new PcsAdminReportExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andConfigIdEqualTo(configId)
                .andStageEqualTo(stage);
        modelMap.put("hasReport", pcsAdminReportMapper.countByExample(example)>0);

        modelMap.put("allowModify", pcsPartyService.allowModify(partyId, configId, stage));

        return "pcs/pcsParty/pcsParty_report_page";
    }

    @RequiresPermissions("pcsParty:report")
    @RequestMapping(value = "/pcsParty_report_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsParty_report_check(byte stage) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        List<PcsBranchBean> pcsBranchBeans = pcsPartyService.notFinishedPcsBranchBeans(partyId, configId, stage);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("beans", pcsBranchBeans);

        return resultMap;
    }

    @RequiresPermissions("pcsParty:report")
    @RequestMapping(value = "/pcsParty_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsParty_report(byte stage) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if(!pcsPartyService.allowModify(partyId, configId, stage)){
            return failed("您已经报送，请勿重复操作。");
        }

        pcsPartyService.report(partyId, configId, stage);

        logger.info(addLog(LogConstants.LOG_PCS, "[分党委管理员]报送-%s(%s)", currentPcsConfig.getName(),
                PcsConstants.PCS_STAGE_MAP.get(stage)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsParty:list")
    @RequestMapping("/pcsParty_candidate_data")
    public void pcsParty_candidate_data(HttpServletResponse response,
                                            byte stage,
                                            Integer userId,
                                            @RequestParam(required = false,
                                                    defaultValue = PcsConstants.PCS_USER_TYPE_DW+"") byte type,
                                            Integer pageSize, Integer pageNo) throws IOException {


        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        int count = iPcsMapper.countBranchCandidateList(userId, configId, stage, type, partyId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<IPcsCandidate> records = iPcsMapper.selectBranchCandidateList(userId, configId, stage, type, partyId,
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


    @RequiresPermissions("pcsParty:list")
    @RequestMapping("/pcsParty_branchs")
    public String pcsParty_branchs(String partyIds, String branchIds, ModelMap modelMap) {

        if(StringUtils.isNotBlank(branchIds)) {

            List<Branch> branchList = new ArrayList<>();
            Map<Integer, Branch> branchMap = branchService.findAll();
            String[] branchIdStrs = branchIds.split(",");
            for (String branchIdStr : branchIdStrs) {
                try {
                    int branchId = Integer.parseInt(branchIdStr);
                    branchList.add(branchMap.get(branchId));
                } catch (Exception e) {
                    logger.debug("wrong branchId:" + branchIdStr, e);
                }
            }
            modelMap.put("branchList", branchList);
        }

        // 处理直属党支部
        if(StringUtils.isNotBlank(partyIds)){

            List<Party> partyList = new ArrayList<>();
            Map<Integer, Party> partyMap = partyService.findAll();
            String[] partyIdStrs = partyIds.split(",");
            for (String partyIdStr : partyIdStrs) {
                try {
                    int partyId = Integer.parseInt(partyIdStr);
                    partyList.add(partyMap.get(partyId));
                } catch (Exception e) {
                    logger.debug("wrong partyId:" + partyIdStr, e);
                }
            }
            modelMap.put("partyList", partyList);
        }

        return "pcs/pcsParty/pcsParty_branchs";
    }
}
