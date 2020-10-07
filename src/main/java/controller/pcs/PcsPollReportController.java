package controller.pcs;

import controller.global.OpException;
import domain.pcs.*;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import persistence.pcs.common.PcsFinalResult;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/pcs")
public class PcsPollReportController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPollReport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollReport(HttpServletRequest request,
                                Integer[] ids,//对应查出来结果的userId
                                Boolean isCandidate,
                                int pollId,
                                byte type) {

        if (null != ids && ids.length > 0) {
            pcsPollReportService.batchInsertOrUpdate(ids, isCandidate, pollId, type);
            logger.info(log(LogConstants.LOG_PCS, "批量{1}：{0}", StringUtils.join(ids, ","),
                    isCandidate ? "设置候选人" : "取消候选人资格"));
        }

        return success(FormUtils.SUCCESS);
    }

    //党代会投票只需要统计结果时的页面
    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReportList")
    public String pcsPollReportList(@RequestParam(required = false, defaultValue = PcsConstants.PCS_USER_TYPE_DW+"") byte type,
                                    Integer pollId,
                                    Integer userId,
                                    ModelMap modelMap) {

        modelMap.put("type", type);

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        Byte stage = pcsPoll.getStage();
        modelMap.put("stage", stage);

        if (stage != PcsConstants.PCS_POLL_THIRD_STAGE) {
            List<PcsPollReport> prReportList = pcsPollReportService.getReport(pcsPoll, PcsConstants.PCS_USER_TYPE_PR);
            modelMap.put("prCount", prReportList.size());
        }

        List<PcsPollReport> dwReportList = pcsPollReportService.getReport(pcsPoll, PcsConstants.PCS_USER_TYPE_DW);
        modelMap.put("dwCount", dwReportList.size());
        List<PcsPollReport> jwReportList = pcsPollReportService.getReport(pcsPoll, PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("jwCount", jwReportList.size());

        modelMap.put("pcsPoll", pcsPoll);

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "pcs/pcsPoll/pcsPollReport1/pcsPollReport_page";
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_data")
    @ResponseBody
    public void pcsPollReport_data(HttpServletResponse response,
                                   Integer userId,
                                   @RequestParam(required = false, defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
                                   Integer pollId,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer[] ids, // 导出的记录 userIds
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        PcsPollReportExample example = new PcsPollReportExample();
        PcsPollReportExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("ballot desc,positive_ballot desc");

        if (pollId != null) {
            criteria.andPollIdEqualTo(pollId);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {

            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            if (type==PcsConstants.PCS_USER_TYPE_PR) {
                pcsPollReport_prExport(example, pollId, response);//代表候选人推荐人选统计结果 模板6
            }else if (type!=PcsConstants.PCS_USER_TYPE_PR) {
                //委员会委员候选人推荐人选汇总表 模板8
                //纪律检查委员会委员候选人推荐人选汇总表 模板8
                pcsPollReport_otherExport(example, pollId, type, response);
            }
            return;
        }

        long count = pcsPollReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPollReport> records = pcsPollReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPollReport.class, pcsPollReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);

        return;
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_list_data")
    @ResponseBody
    public void pcsPollReport_list_data(HttpServletResponse response,
                                        Integer userId,
                                        Integer partyId,
                                        Integer branchId,
                                        byte stage,
                                        Byte type,
                                        Integer pollId,
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

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        Integer configId = pcsConfig.getId();
        List<Integer> partyIdList = new ArrayList<>();
        List<Integer> branchIdList = new ArrayList<>();

        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            partyIdList = loginUserService.adminPartyIdList();
            branchIdList = loginUserService.adminBranchIdList();
        }

        List<Integer> userIdList = new ArrayList<>();
        if (userId != null) {
            userIdList.addAll(Arrays.asList(userId));
        }

        if (export == 1) {
            List<PcsFinalResult> records = iPcsMapper.selectReport(type, configId, stage, userIdList, partyId, branchId, partyIdList, branchIdList,
                new RowBounds());
            pcsPollReport_export(records, stage, response);
            return;
        }

        int count = 0;
        count = iPcsMapper.countReport(type, configId, stage, userIdList, partyId, branchId, partyIdList, branchIdList);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsFinalResult> records = iPcsMapper.selectReport(type, configId, stage, userIdList, partyId, branchId, partyIdList, branchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPollReport.class, pcsPollReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);

        return;
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_import")
    public String pcsPollReport_import(Integer pollId, ModelMap modelMap) {

        if (pollId != null){
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
            modelMap.put("stage", pcsPoll.getStage());
        }
        return "pcs/pcsPoll/pcsPollReport1/pcsPollReport_import";
    }

    @RequiresPermissions("pcsPollReport:import")
    @RequestMapping(value = "/pcsPollReport_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollReport_import(Integer pollId, Byte type, HttpServletRequest request)
            throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PcsPollReport> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            PcsPollReport record = new PcsPollReport();
            row++;

            String usercode = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(usercode)){
                throw new OpException("第{0}行学工号[{1}]为空", row, usercode);
            }
            SysUserView uv = sysUserService.findByCode(usercode);
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, usercode);
            }
            record.setUserId(uv.getUserId());
            record.setCode(usercode);
            record.setRealname(uv.getRealname());
            record.setUnit(uv.getUnit());

            String ballot = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(ballot)){
                throw new OpException("第{0}行推荐提名党员数为空", row);
            }
            String positiveBallot = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(positiveBallot)){
                throw new OpException("第{0}行推荐提名正式党员数为空", row);
            }
            String growBallot = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isBlank(growBallot)){
                throw new OpException("第{0}行推荐提名预备党员数为空", row);
            }
            try {
                record.setBallot(Integer.valueOf(ballot));
                record.setPositiveBallot(Integer.valueOf(positiveBallot));
                record.setGrowBallot(Integer.valueOf(growBallot));
            }catch (Exception e){
                throw new OpException("票数请填写阿拉伯数字");
            }

            record.setPollId(pollId);
            record.setType(type);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = pcsPollReportService.bacthImport(records, pollId, type);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_PCS,
                "导入党代会候选人名单成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, addCount));

        return resultMap;
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/user_search")
    public String user_search() {

        return "pcs/pcsPoll/pcsPollReport1/user_search";
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping(value = "/user_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_user_search(String reportName) {

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andRealnameLike(SqlUtils.trimLike(reportName));
        List<SysUserView> records = sysUserViewMapper.selectByExample(example);
        int count = (int) sysUserViewMapper.countByExample(example);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("records", records);
        resultMap.put("count", count);
        return resultMap;
    }

    public void pcsPollReport_export(List<PcsFinalResult> records, byte stage, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"学工号|120", "姓名|100", "推荐提名党支部数|100","推荐提名党员数|100", "推荐提名正式党员数|100",
                "推荐提名预备党员数|100", "不同意票数|100", "弃权票数|100", "推荐人类型|100", "所在单位|252"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsFinalResult record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getRealname(),
                    record.getBranchNum() + "",
                    record.getSupportNum() + "",
                    record.getPositiveBallot() + "",
                    record.getGrowBallot() + "",
                    stage==PcsConstants.PCS_POLL_FIRST_STAGE?"--":record.getNotSupportNum() + "",
                    stage==PcsConstants.PCS_POLL_FIRST_STAGE?"--":record.getNotVoteNum() + "",
                    PcsConstants.PCS_USER_TYPE_MAP.get(record.getType()),
                    record.getUnit()
            };
            valuesList.add(values);
        }
        String fileName = String.format("%s投票报送结果(%s)", PcsConstants.PCS_POLL_STAGE_MAP.get(stage),
                DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void pcsPollReport_prExport(PcsPollReportExample example, Integer pollId, HttpServletResponse response) throws IOException {

        List<PcsPollReport> reportList = pcsPollReportMapper.selectByExample(example);
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/pcs/pcs_poll_6.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName()+pcsConfig.getName());
        cell.setCellValue(str);

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        Integer partyId = pcsPoll.getPartyId();
        PcsParty pcsParty = pcsPartyService.get(pcsPoll.getConfigId(), partyId);
        Integer branchId = pcsPoll.getBranchId();
        PcsBranch pcsBranch  =  pcsBranchService.get(pcsPoll.getConfigId(), pcsPoll.getPartyId(), branchId);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("branchName", pcsBranch!=null?pcsBranch.getName():pcsParty.getName());
        cell.setCellValue(str);

        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("should", pcsPoll.getExpectMemberCount()==null?"":pcsPoll.getExpectMemberCount()+"");
        cell.setCellValue(str);
        cell = row.getCell(3);
        str = cell.getStringCellValue()
                .replace("real", pcsPoll.getActualMemberCount()==null?"":pcsPoll.getActualMemberCount()+"");
        cell.setCellValue(str);

        int sep = 1;
        int startRow = 3;
        float reportSize = reportList.size();
        int rowInsert = reportList.size()/2 + (int)(reportSize%2F>0F?1:0);
        ExcelUtils.insertRow(wb, sheet, startRow, rowInsert - 1);
        int rowCount = 3;
        boolean flag = false;


        for (PcsPollReport report : reportList) {
            if (rowCount >= rowInsert+3) {
                flag = true;//true导入第二列结果数据 false导入第一列结果数据
                rowCount = 3;
            }
            row = sheet.getRow(rowCount++);
            int column = flag?3:0;

            cell = row.getCell(column++);
            cell.setCellValue(sep++);

            cell = row.getCell(column++);
            cell.setCellValue(report.getUser().getRealname());

            cell = row.getCell(column++);
            cell.setCellValue(report.getBallot());//提名党员数
        }

        String fileName = String.format("代表候选人统计结果");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    public void pcsPollReport_otherExport(PcsPollReportExample example, Integer pollId, byte type, HttpServletResponse response) throws IOException {

        List<PcsPollReport> reportList = pcsPollReportMapper.selectByExample(example);
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        InputStream is = null;
        String fileName = "";
        if (type == PcsConstants.PCS_USER_TYPE_DW) {
            fileName = String.format("委员会委员候选人推荐人选汇总");
            is = new FileInputStream(ResourceUtils
                    .getFile("classpath:xlsx/pcs/pcs_poll_8.xlsx"));
        }else if (type == PcsConstants.PCS_USER_TYPE_JW){
            fileName = String.format("纪律检查委员会委员候选人推荐人选汇总");
            is = new FileInputStream(ResourceUtils
                    .getFile("classpath:xlsx/pcs/pcs_poll_8.xlsx"));
        }
        //纪律检查委员会
        //委员
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName()+pcsConfig.getName())
                .replace("type", type == PcsConstants.PCS_USER_TYPE_DW?"委员会委员":"纪律检查委员会委员");

        cell.setCellValue(str);

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        Integer partyId = pcsPoll.getPartyId();
        PcsParty pcsParty = pcsPartyService.get(pcsPoll.getConfigId(), partyId);
        Integer branchId = pcsPoll.getBranchId();
        PcsBranch pcsBranch  =  pcsBranchService.get(pcsPoll.getConfigId(), pcsPoll.getPartyId(), branchId);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("branchName", pcsBranch!=null?pcsBranch.getName():pcsParty.getName());
        cell.setCellValue(str);
        row = sheet.getRow(2);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("allCount", pcsBranch==null?"":pcsBranch.getMemberCount()+"")//支部党员数
                .replace("positiveCount",pcsBranch==null?"":pcsBranch.getPositiveCount()+"");//支部正式党员数
        cell.setCellValue(str);
        row = sheet.getRow(3);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("insFinishNum",  pcsPoll.getInspectorFinishNum()+"")//参加推荐提名党员数
                .replace("insPositiveNum",pcsPoll.getPositiveFinishNum()+"");//其中参加推荐提名正式党员数
        cell.setCellValue(str);


        int sep = 1;
        int startRow = 5;
        int rowInsert =  reportList.size()+3;
        ExcelUtils.insertRow(wb, sheet, startRow, rowInsert);
        int rowCount = 5;

        for (PcsPollReport report : reportList) {
            row = sheet.getRow(rowCount++);
            int column = 0;

            cell = row.getCell(column++);
            cell.setCellValue(sep++);

            cell = row.getCell(column++);
            cell.setCellValue(report.getUser().getRealname());

            cell = row.getCell(column++);
            cell.setCellValue(report.getUnit());

            cell = row.getCell(column++);
            cell.setCellValue(report.getBallot());//提名党员数

            cell = row.getCell(column++);
            cell.setCellValue(report.getPositiveBallot());//提名正式党员数
        }

        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
