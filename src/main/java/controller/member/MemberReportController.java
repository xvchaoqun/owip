package controller.member;

import domain.member.MemberReport;
import domain.member.MemberReportExample;
import domain.member.MemberReportExample.Criteria;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static sys.constants.OwConstants.OW_REPORT_STATUS_REPORT;
import static sys.constants.OwConstants.OW_REPORT_STATUS_UNREPORT;

@Controller
@RequestMapping("/member")
public class MemberReportController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberReport:list")
    @RequestMapping("/memberReport")
    public String memberReport(@RequestParam(required = false, defaultValue = "1") Integer cls, Integer partyId, ModelMap modelMap) {
        modelMap.put("cls", cls);
        if (cls != 1) {
            return "forward:/partyReport";
        }
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        return "member/memberReport/memberReport_page";
    }

    @RequiresPermissions("memberReport:list")
    @RequestMapping("/memberReport_data")
    @ResponseBody
    public void memberReport_data(HttpServletResponse response,
                                  Integer year,
                                  Integer partyId,
                                  Integer userId,
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

        MemberReportExample example = new MemberReportExample();
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
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            memberReport_export(example, response);
            return;
        }

        long count = memberReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberReport> records = memberReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(memberReport.class, memberReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberReport:edit")
    @RequestMapping(value = "/memberReport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReport_au(MemberReport record, MultipartFile _reportFile, MultipartFile _evaFile, HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        Integer userId = record.getUserId();
        Integer year = record.getYear();
        if (memberReportService.idDuplicate(id, userId, year)) {
            return failed("添加重复");
        }
        record.setReportFile(upload(_reportFile, "owMemberReport"));
        record.setEvaFile(upload(_evaFile, "owMemberReport"));
        if (id == null) {
            record.setStatus(OW_REPORT_STATUS_UNREPORT);
            memberReportService.insertSelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "添加党组织书记考核：{0}", record.getId()));
        } else {

            memberReportService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "更新党组织书记考核：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReport:edit")
    @RequestMapping("/memberReport_au")
    public String memberReport_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReport memberReport = memberReportMapper.selectByPrimaryKey(id);
            modelMap.put("memberReport", memberReport);
        }
        return "member/memberReport/memberReport_au";
    }

    @RequiresPermissions("memberReport:edit")
    @RequestMapping(value = "/memberReport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberReportService.del(id);
            logger.info(log(LogConstants.LOG_MEMBER, "删除党组织书记考核：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReport:edit")
    @RequestMapping(value = "/memberReport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map memberReport_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            memberReportService.batchDel(ids);
            logger.info(log(LogConstants.LOG_MEMBER, "批量删除党组织书记考核：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReport:edit")
    @RequestMapping(value = "/memberReport_report", method = RequestMethod.POST)
    @ResponseBody
    public Map memberReport_report(MemberReport record, @RequestParam(required = false, defaultValue = "0") Integer back, HttpServletRequest request, ModelMap modelMap) {

        if (record.getId() != null) {
            if (back == 1) {
                record.setStatus(OW_REPORT_STATUS_UNREPORT);
            } else {
                record.setStatus(OW_REPORT_STATUS_REPORT);
            }
            memberReportService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "报送/退回党支部考核：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberReport_export(MemberReportExample example, HttpServletResponse response) {

        List<MemberReport> records = memberReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100", "用户id|100", "所属分党委|100", "述职报告|100", "考核结果|100", "考核结果文件|100", "状态  1未报送  2 已报送|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberReport record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getUserId() + "",
                    record.getPartyId() + "",
                    record.getReportFile(),
                    record.getEvaResult() + "",
                    record.getEvaFile(),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = String.format("党组织书记考核(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pb_member_selects")
    @ResponseBody
    public Map pb_member_selects(Integer partyId, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Integer[] ids = iMemberMapper.getPbMemberSelects(partyId);
        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria().andIdIn(Arrays.asList(ids));

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andRealnameLike(SqlUtils.like(searchStr));
        }

        long count = sysUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> records = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List options = new ArrayList<>();
        if (null != records && records.size() > 0) {

            for (SysUserView record : records) {

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getRealname() + "");
                option.put("code", record.getCode());
                option.put("id", record.getId() + "");
                option.put("unit", record.getUnit());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
