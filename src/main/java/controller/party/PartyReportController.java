package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.party.Branch;
import domain.party.Party;
import domain.party.PartyReport;
import domain.party.PartyReportExample;
import domain.party.PartyReportExample.Criteria;
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

public class PartyReportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyReport:list")
    @RequestMapping("/partyReport")
    public String partyReport(Integer partyId,Integer branchId, ModelMap modelMap) {
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));
        return "party/partyReport/partyReport_page";
    }

    @RequiresPermissions("partyReport:list")
    @RequestMapping("/partyReport_data")
    @ResponseBody
    public void partyReport_data(HttpServletResponse response,
                                 Integer year,
                                 Integer partyId,
                                 Integer branchId,
                                 Byte status,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
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
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
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

        if (partyReportService.idDuplicate(id, record.getPartyId(),record.getBranchId(), record.getYear())) {
            return failed("添加重复，每年度每个党支部只能添加一条考核记录");
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
    @RequiresPermissions("partyReport:edit")
    @RequestMapping("/partyReport_file")
    public String partyReport_file(Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyReport partyReport = partyReportMapper.selectByPrimaryKey(id);
            modelMap.put("partyReport", partyReport);
        }
        return "party/partyReport/partyReport_file";
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping(value = "/partyReport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyReportService.del(id);
            logger.info(log(LogConstants.LOG_MEMBER, "删除党支部考核：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping(value = "/partyReport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyReport_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            partyReportService.batchDel(ids);
            logger.info(log(LogConstants.LOG_MEMBER, "批量删除党支部考核：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping(value = "/partyReport_delFile", method = RequestMethod.POST)
    @ResponseBody
    public Map partyReport_delFile(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            partyReportService.delFile(ids);
            logger.info(log(LogConstants.LOG_MEMBER, "批量删除党支部考核结果及文件：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:edit")
    @RequestMapping(value = "/partyReport_report", method = RequestMethod.POST)
    @ResponseBody
    public Map partyReport_report(Integer[] ids, @RequestParam(required = false, defaultValue = "0") Integer back, HttpServletRequest request, ModelMap modelMap) {

        Byte status=null;
        if (ids != null) {
            if (back == 1) {
                status=OW_REPORT_STATUS_UNREPORT;
            } else {
                status=OW_REPORT_STATUS_REPORT;
            }
            partyReportService.batchReport(ids,status);
            logger.info(log(LogConstants.LOG_MEMBER, "报送党支部考核：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReport:base")
    @RequestMapping("/partyReport_import")
    public String memberReport_import(ModelMap modelMap) {

        return "party/partyReport/partyReport_import";
    }

    // 导入党支部考核
    @RequiresPermissions("partyReport:base")
    @RequestMapping(value = "/partyReport_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReport_import(HttpServletRequest request) throws InvalidFormatException, IOException, InterruptedException {

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

        logger.info(log(LogConstants.LOG_PARTY,
                "导入考核记录成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, successCount));

        return resultMap;
    }

    private Map<String, Object> importReport(List<Map<Integer, String>> xlsRows,
                                             Map<String, Party> runPartyMap,
                                             Map<String, Branch> runBranchMap,
                                             Map<String, Byte> evaResult) throws InterruptedException {

        List<PartyReport> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            PartyReport record = new PartyReport();
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

            String _status = StringUtils.trimToNull(xlsRow.get(5));
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

        int successCount = partyReportService.partyReportImport(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    public void partyReport_export(PartyReportExample example, HttpServletResponse response) {

        List<PartyReport> records = partyReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100", "所属二级单位党组织|300", "党支部|300", "考核结果|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyReport record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getParty().getName() + "",
                    record.getBranch()==null?"":record.getBranch().getName(),
                    OW_PARTY_EVA_MAP.get(record.getEvaResult())==null?"":OW_PARTY_EVA_MAP.get(record.getEvaResult())
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
