package controller.pmd;

import controller.global.OpException;
import domain.party.Party;
import domain.pmd.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pmd")
public class PmdBranchController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdBranch:list")
    @RequestMapping("/pmdBranch")
    public String pmdBranch(@RequestParam(required = false, defaultValue = "1") Byte cls,
                            Integer monthId,
                            Integer partyId,
                            Integer branchId,
                            ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("party", partyService.findAll().get(partyId));
        modelMap.put("branch", branchService.findAll().get(branchId));

        if (cls == 2) {
            // 分党委管理员访问支部列表
            PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
            modelMap.put("pmdParty", pmdParty);
            return "pmd/pmdBranch/pmdBranch_party_page";
        }

        // 组织部管理员或支部管理员访问
        return "pmd/pmdBranch/pmdBranch_page";
    }

    @RequiresPermissions("pmdBranch:list")
    @RequestMapping("/pmdBranch_data")
    public void pmdBranch_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               @DateTimeFormat(pattern = "yyyy-MM") Date payMonth,
                               Boolean hasReport,
                               Integer monthId,
                               Integer partyId,
                               Integer branchId,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdBranchViewExample example = new PmdBranchViewExample();
        PmdBranchViewExample.Criteria criteria = example.createCriteria()
                .andMonthStatusNotEqualTo(PmdConstants.PMD_MONTH_STATUS_INIT);
        example.setOrderByClause("pay_month desc, party_sort_order desc, sort_order desc");

        if (payMonth != null) {
            criteria.andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        }
        if (hasReport != null) {
            criteria.andHasReportEqualTo(hasReport);
        }

        if (cls == 1) {

            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());

            criteria.addPermits(adminPartyIds, adminBranchIds);
        } else if (cls == 2) {
            // 此时必须传入monthId和partyId
            criteria.andMonthIdEqualTo(monthId);

            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PMDVIEWALL)) {
                List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                Set<Integer> adminPartyIdSet = new HashSet<>();
                adminPartyIdSet.addAll(adminPartyIds);
                if (adminPartyIdSet.contains(partyId)) {
                    criteria.andPartyIdEqualTo(partyId);
                } else {
                    criteria.andPartyIdIsNull();
                }
            }else{
                criteria.andPartyIdEqualTo(partyId);
            }
        } else {
            criteria.andIdIsNull();
        }

        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }

        long count = pmdBranchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdBranchView> records = pmdBranchViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdBranch.class, pmdBranchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdBranch:report")
    @RequestMapping(value = "/pmdBranch_unreport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranch_unreport(int id, HttpServletRequest request) {

        pmdBranchService.unreport(id);
        logger.info(addLog(LogConstants.LOG_PMD, "撤销党支部报送：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranch:report")
    @RequestMapping(value = "/pmdBranch_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranch_report(int id, Boolean update, HttpServletRequest request) {

        if(BooleanUtils.isTrue(update)){
            
            ShiroHelper.checkPermission(SystemConstants.PERMISSION_PMDVIEWALL);
        
            pmdBranchService.updateReport(id);
            logger.info(addLog(LogConstants.LOG_PMD, "更新党支部报送：%s", id));
        }else {
            PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(id);
            if (pmdBranch.getHasReport()) {
                return failed("已经报送。");
            }
    
            pmdBranchService.report(id);
            logger.info(addLog(LogConstants.LOG_PMD, "党支部报送：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranch:report")
    @RequestMapping("/pmdBranch_report")
    public String pmdBranch_report(int id, ModelMap modelMap) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(id);
        modelMap.put("pmdBranch", pmdBranch);
        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(pmdBranch.getMonthId());
        modelMap.put("pmdMonth", pmdMonth);

        return "pmd/pmdBranch/pmdBranch_report";
    }

    // 支部批量延迟缴费
    @RequiresPermissions("pmdBranch:delay")
    @RequestMapping(value = "/pmdBranch_delay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranch_delay(int id, String delayReason, HttpServletRequest request) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(id);
        if(pmdBranchService.canReport(pmdBranch.getMonthId(), pmdBranch.getPartyId(), pmdBranch.getBranchId())){
            return success(FormUtils.SUCCESS);
        }else if(pmdBranch.getHasReport()){
            return  failed("已经报送。");
        }
        pmdPayService.delayAll(pmdBranch.getPartyId(), pmdBranch.getBranchId(), delayReason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranch:delay")
    @RequestMapping("/pmdBranch_delay")
    public String pmdBranch_delay(int id, ModelMap modelMap) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(id);
        modelMap.put("pmdBranch", pmdBranch);

        return "pmd/pmdBranch/pmdBranch_delay";
    }

    @RequiresPermissions("pmdBranch:delay")
    @RequestMapping(value = "/pmdBranch_checkDelay", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdBranch_checkDelay(int id, HttpServletRequest request) {

        List<PmdMember> pmdMembers = pmdBranchService.listUnsetDuepayMembers(id);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("unsetDuepayMembers", pmdMembers);

        return resultMap;
    }


    @RequiresPermissions("pmdBranch:edit")
    @RequestMapping(value = "/pmdBranch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranch_au(PmdBranch record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            int partyId = record.getPartyId();
            int branchId = record.getBranchId();

            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
            PmdBranchExample example = new PmdBranchExample();
            example.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId()).andPartyIdEqualTo(partyId)
                    .andBranchIdEqualTo(branchId);
            if ((int)pmdBranchMapper.countByExample(example) > 0)
                throw new OpException("添加重复");
            record.setMonthId(currentPmdMonth.getId());
            pmdBranchService.insertSelective(record, currentPmdMonth);
            logger.info(addLog(LogConstants.LOG_PMD, "添加每月参与线上收费的党支部：%s", branchId));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranch:edit")
    @RequestMapping("/pmdBranch_au")
    public String pmdBranch_au(Integer partyId, ModelMap modelMap) {

        if (partyId != null) {
            Party party = partyService.findAll().get(partyId);
            modelMap.put("party", party);
        }
        return "pmd/pmdBranch/pmdBranch_au";
    }
    // 删除当月的缴费党支部（不包含直属党支部），（如果党支部下存在已缴费的记录，则只删除该党支部下的未缴费的记录，党支部仍然保留）
    @RequiresPermissions("pmdBranch:del")
    @RequestMapping(value = "/pmdBranch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranch_del(Integer id) {

        if (id != null) {

            pmdBranchService.del(id);
            logger.info(addLog(LogConstants.LOG_PMD, "删除当月参与线上收费的党支部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranch:del")
    @RequestMapping(value = "/pmdBranch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdBranch_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdBranchService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PMD, "批量删除当月参与线上收费的党支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
