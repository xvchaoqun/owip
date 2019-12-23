package controller.party;

import controller.BaseController;
import domain.party.Party;
import domain.party.PartyReport;
import domain.party.PartyReportExample;
import domain.party.PartyReportExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static sys.constants.OwConstants.OW_REPORT_STATUS_REPORT;
import static sys.constants.OwConstants.OW_REPORT_STATUS_UNREPORT;

@Controller

public class PartyReportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyReport:list")
    @RequestMapping("/partyReport")
    public String partyReport(Integer partyId, ModelMap modelMap) {
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        return "party/partyReport/partyReport_page";
    }

    @RequiresPermissions("partyReport:list")
    @RequestMapping("/partyReport_data")
    @ResponseBody
    public void partyReport_data(HttpServletResponse response,
                                 Integer year,
                                 Integer partyId,
                                 Byte status,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyReportExample example = new PartyReportExample();
        Criteria criteria = example.createCriteria();
        criteria.addPermits(loginUserService.adminPartyIdList());
        example.setOrderByClause("id desc");

        if (ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)) {
            criteria.andStatusEqualTo(OW_REPORT_STATUS_REPORT);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            partyReport_export(example, response);
            return;
        }

        long count = partyReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyReport> records = partyReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyReport.class, partyReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping(value = "/partyReport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReport_au(PartyReport record, MultipartFile _reportFile, MultipartFile _evaFile, HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (partyReportService.idDuplicate(id, record.getPartyId(), record.getYear())) {
            return failed("添加重复");
        }
        record.setReportFile(upload(_reportFile, "owPartyReport"));
        record.setEvaFile(upload(_evaFile, "owPartyReport"));
        if (id == null) {
            record.setStatus(OW_REPORT_STATUS_UNREPORT);
            partyReportService.insertSelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "添加党支部考核：{0}", record.getId()));
        } else {

            partyReportService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "更新党支部考核：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping("/partyReport_au")
    public String partyReport_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyReport partyReport = partyReportMapper.selectByPrimaryKey(id);
            modelMap.put("partyReport", partyReport);
        }
        return "party/partyReport/partyReport_au";
    }

    @RequiresPermissions("partyReport:del")
    @RequestMapping(value = "/partyReport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyReportService.del(id);
            logger.info(log(LogConstants.LOG_MEMBER, "删除党支部考核：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:del")
    @RequestMapping(value = "/partyReport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyReport_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            partyReportService.batchDel(ids);
            logger.info(log(LogConstants.LOG_MEMBER, "批量删除党支部考核：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping(value = "/partyReport_report", method = RequestMethod.POST)
    @ResponseBody
    public Map partyReport_report(PartyReport record, @RequestParam(required = false, defaultValue = "0") Integer back, HttpServletRequest request, ModelMap modelMap) {


        if (record.getId() != null) {
            if (back == 1) {
                record.setStatus(OW_REPORT_STATUS_UNREPORT);
            } else {
                record.setStatus(OW_REPORT_STATUS_REPORT);
            }
            partyReportService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "报送党支部考核：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    public void partyReport_export(PartyReportExample example, HttpServletResponse response) {

        List<PartyReport> records = partyReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100", "所属分党委|100", "工作总结|100", "考核结果|100", "考核结果文件|100", "状态  1未报送  2 已报送|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyReport record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getPartyId() + "",
                    record.getReportFile(),
                    record.getEvaResult() + "",
                    record.getEvaFile(),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = String.format("党支部考核(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/partyReport_selects")
    @ResponseBody
    public Map partyReport_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyReportExample example = new PartyReportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

      /*  if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = partyReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyReport> records = partyReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List options = new ArrayList<>();
        if (null != records && records.size() > 0) {

            for (PartyReport record : records) {

                Map<String, Object> option = new HashMap<>();
                /* option.put("text", record.getName());*/
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
