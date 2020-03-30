package controller.member;

import controller.global.OpException;
import domain.member.MemberReport;
import domain.member.MemberReportExample;
import domain.member.MemberReportExample.Criteria;
import domain.party.Branch;
import domain.party.BranchMemberView;
import domain.party.BranchMemberViewExample;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.helper.PartyHelper;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static sys.constants.OwConstants.*;

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
        criteria.addPermits(loginUserService.adminPartyIdList(),loginUserService.adminBranchIdList());
        example.setOrderByClause("id desc");
       /* if (ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)) {
            criteria.andStatusEqualTo(OW_REPORT_STATUS_REPORT);
        }*/
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
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
            BranchMemberViewExample example = new BranchMemberViewExample();
            example.createCriteria().andUserIdEqualTo(record.getUserId()).andGroupPartyIdEqualTo(record.getPartyId());
            List<BranchMemberView> branchMembers=branchMemberViewMapper.selectByExample(example);
            if(branchMembers.size()>0){
                int branchId=branchMembers.get(0).getGroupBranchId();
                record.setBranchId(branchId);
            }
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
    @RequestMapping("/memberReport_result")
    public String memberReport_result(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReport memberReport = memberReportMapper.selectByPrimaryKey(id);
            modelMap.put("memberReport", memberReport);
        }
        return "member/memberReport/memberReport_result";
    }
    @RequiresPermissions("memberReport:edit")
    @RequestMapping("/memberReport_file")
    public String memberReport_file(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReport memberReport = memberReportMapper.selectByPrimaryKey(id);
            modelMap.put("memberReport", memberReport);
        }
        return "member/memberReport/memberReport_file";
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
    public Map memberReport_report(@RequestParam(value = "ids[]") Integer[] ids, @RequestParam(required = false, defaultValue = "0") Integer back, HttpServletRequest request, ModelMap modelMap) {
        Byte status=null;
        if (ids != null) {
            if (back == 1) {
                status=OW_REPORT_STATUS_UNREPORT;
            } else {
                status=OW_REPORT_STATUS_REPORT;
            }
            memberReportService.batchReport(ids,status);
            logger.info(log(LogConstants.LOG_MEMBER, "报送/退回党支部考核：{0}",StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReport:base")
    @RequestMapping("/memberReport_import")
    public String memberReport_import(ModelMap modelMap) {

        return "member/memberReport/memberReport_import";
    }

    // 导入党组织书记考核
    @RequiresPermissions("memberReport:base")
    @RequestMapping(value = "/memberReport_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReport_import(HttpServletRequest request) throws InvalidFormatException, IOException, InterruptedException {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if (BooleanUtils.isNotTrue(party.getIsDeleted())) {
                runPartyMap.put(party.getCode(), party);
            }
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<String, Branch> runBranchMap = new HashMap<>();
        for (Branch branch : branchMap.values()) {
            if (BooleanUtils.isNotTrue(branch.getIsDeleted())) {
                runBranchMap.put(branch.getCode(), branch);
            }
        }

        Map<String, Byte> evaResult = new HashMap<>();
        for (Map.Entry<Byte, String> entry : OW_PARTY_EVA_MAP.entrySet()) {

            evaResult.put(entry.getValue(), entry.getKey());
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;
        resultMap = importReport(xlsRows, runPartyMap, runBranchMap, evaResult);

        int successCount = (int) resultMap.get("successCount");
        int totalCount = (int) resultMap.get("total");

        logger.info(log(LogConstants.LOG_MEMBER,
                "导入考核记录成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, successCount));

        return resultMap;
    }

    private Map<String, Object> importReport(List<Map<Integer, String>> xlsRows,
                                              Map<String, Party> runPartyMap,
                                              Map<String, Branch> runBranchMap,
                                              Map<String, Byte> evaResult) throws InterruptedException {

        List<MemberReport> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            MemberReport record = new MemberReport();
            Integer _year = Integer.valueOf(xlsRow.get(0));
            if (_year==null) {
                throw new OpException("第{0}行年度为空", row);
            }
            record.setYear(_year);
            String partyCode = StringUtils.trim(xlsRow.get(1));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),party.getId())){
                throw new OpException("您没有权限导入第{0}行党支部数据", row);
            }
            record.setPartyId(party.getId());
            if (!partyService.isDirectBranch(party.getId())) {

                String branchCode = StringUtils.trim(xlsRow.get(3));
                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行党支部编码为空", row);
                }
                Branch branch = runBranchMap.get(branchCode);
                if (branch == null) {
                    throw new OpException("第{0}行党支部编码[{1}]不存在", row, partyCode);
                }
                record.setBranchId(branch.getId());
            }

            String userCode = StringUtils.trimToNull(xlsRow.get(5));
            if (StringUtils.isBlank(userCode)) {
                continue;
            }

            SysUserView user = sysUserService.findByCode(userCode);
            if (user == null) {
                throw new OpException("第{0}行工号[{1}]不存在", row, userCode);
            }
            record.setUserId(user.getId());

            String _status = StringUtils.trimToNull(xlsRow.get(7));
            if (StringUtils.isBlank(_status)) {
                throw new OpException("第{0}行考核结果为空", row);
            }

            Byte result = evaResult.get(_status);
            if (result == null) {
                throw new OpException("第{0}行考核结果[{1}]有误", row, _status);
            }
            record.setEvaResult(result);
            record.setStatus(OW_REPORT_STATUS_UNREPORT);
            records.add(record);
        }

        int successCount = memberReportService.memberReportImport(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    public void memberReport_export(MemberReportExample example, HttpServletResponse response) {

        List<MemberReport> records = memberReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","所属二级单位党组织|300", "姓名|100", "考核结果|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberReport record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getParty().getName() + "",
                    record.getUser().getRealname() + "",
                    OW_PARTY_EVA_MAP.get(record.getEvaResult())==null?"":OW_PARTY_EVA_MAP.get(record.getEvaResult())
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
