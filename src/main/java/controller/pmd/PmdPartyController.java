package controller.pmd;

import domain.pmd.PmdMember;
import domain.pmd.PmdMonth;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyView;
import domain.pmd.PmdPartyViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
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
import sys.constants.RoleConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdPartyController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdParty:list")
    @RequestMapping("/pmdParty")
    public String pmdParty(@RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(cls==2) {
            // 组织部管理员访问分党委列表
            return "pmd/pmdParty/pmdParty_ow_page";
        }

        // 组织部管理员或党委管理员访问
        return "pmd/pmdParty/pmdParty_page";
    }

    @RequiresPermissions("pmdParty:list")
    @RequestMapping("/pmdParty_data")
    public void pmdParty_data(HttpServletResponse response,
                              @RequestParam(required = false, defaultValue = "1") Byte cls,
                              @DateTimeFormat(pattern = "yyyy-MM") Date payMonth,
                              Boolean hasReport,
                              Integer monthId,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdPartyViewExample example = new PmdPartyViewExample();
        PmdPartyViewExample.Criteria criteria = example.createCriteria()
                .andMonthStatusNotEqualTo(PmdConstants.PMD_MONTH_STATUS_INIT);
        example.setOrderByClause("pay_month desc, sort_order desc");

        if(payMonth!=null){
            criteria.andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        }
        if (hasReport != null) {
            criteria.andHasReportEqualTo(hasReport);
        }

        if(cls==1) {
            if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)){

                int userId = ShiroHelper.getCurrentUserId();
                List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
                if (adminPartyIds.size() > 0) {
                    criteria.andPartyIdIn(adminPartyIds);
                } else {
                    criteria.andPartyIdIsNull();
                }
            }
        }else if(cls==2){

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_PMD_OW);
            criteria.andMonthIdEqualTo(monthId);
        }else {
            criteria.andIdIsNull();
        }

        /*if (monthId != null) {
            criteria.andMonthIdEqualTo(monthId);
        }*/

        long count = pmdPartyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdPartyView> records = pmdPartyViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdParty.class, pmdPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdParty:report")
    @RequestMapping(value = "/pmdParty_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdParty_report(int id, HttpServletRequest request) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        if(pmdParty.getHasReport()){
            return  failed("已经报送。");
        }

        pmdPartyService.report(id);
        logger.info(addLog(LogConstants.LOG_PMD, "党委报送：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdParty:report")
    @RequestMapping("/pmdParty_report")
    public String pmdParty_report(int id, ModelMap modelMap) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        modelMap.put("pmdParty", pmdParty);
        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(pmdParty.getMonthId());
        modelMap.put("pmdMonth", pmdMonth);

        return "pmd/pmdParty/pmdParty_report";
    }

    // 强制报送
    @RequiresPermissions("pmdParty:forceReport")
    @RequestMapping(value = "/pmdParty_checkForceReport", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdParty_checkForceReport(int id, HttpServletRequest request) {

        List<PmdMember> pmdMembers = pmdPartyService.listUnsetDuepayMembers(id);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("unsetDuepayMembers", pmdMembers);

        return resultMap;
    }
    @RequiresPermissions("pmdParty:forceReport")
    @RequestMapping(value = "/pmdParty_forceReport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdParty_forceReport(int id, HttpServletRequest request) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        if(pmdParty.getHasReport()){
            return  failed("已经报送。");
        }

        pmdPartyService.forceReport(id);
        logger.info(addLog(LogConstants.LOG_PMD, "党委强制报送：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdParty:forceReport")
    @RequestMapping("/pmdParty_forceReport")
    public String pmdParty_forceReport(int id, ModelMap modelMap) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        modelMap.put("pmdParty", pmdParty);
        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(pmdParty.getMonthId());
        modelMap.put("pmdMonth", pmdMonth);

        return "pmd/pmdParty/pmdParty_forceReport";
    }

    @RequiresPermissions("pmdParty:export")
    @RequestMapping("/pmdParty_export")
    public String pmdParty_export(int id, HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = pmdPartyService.export(id);
        if (wb != null) {
            ExportHelper.output(wb, "【党费报表】.xlsx", response);
        }

        return null;
    }

    // 分党委批量延迟缴费
    @RequiresPermissions("pmdParty:delay")
    @RequestMapping(value = "/pmdParty_delay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdParty_delay(int id, String delayReason, HttpServletRequest request) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        pmdPayService.delayAll(pmdParty.getPartyId(), null, delayReason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdParty:delay")
    @RequestMapping("/pmdParty_delay")
    public String pmdParty_delay(int id, ModelMap modelMap) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        modelMap.put("pmdParty", pmdParty);

        return "pmd/pmdParty/pmdParty_delay";
    }

    /*@RequiresPermissions("pmdParty:edit")
    @RequestMapping(value = "/pmdParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdParty_au(PmdParty record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            pmdPartyService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PMD, "添加每月参与线上收费的分党委：%s", record.getId()));
        } else {

            pmdPartyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PMD, "更新每月参与线上收费的分党委：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdParty:edit")
    @RequestMapping("/pmdParty_au")
    public String pmdParty_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
            modelMap.put("pmdParty", pmdParty);
        }
        return "pmd/pmdParty/pmdParty_au";
    }

    @RequiresPermissions("pmdParty:del")
    @RequestMapping(value = "/pmdParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdPartyService.del(id);
            logger.info(addLog(LogConstants.LOG_PMD, "删除每月参与线上收费的分党委：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdParty:del")
    @RequestMapping(value = "/pmdParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdPartyService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PMD, "批量删除每月参与线上收费的分党委：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
