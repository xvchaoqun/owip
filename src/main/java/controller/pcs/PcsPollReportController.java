package controller.pcs;

import domain.pcs.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_data")
    @ResponseBody
    public void pcsPollReport_data(HttpServletResponse response,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   Byte stage,
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
                //委员会委员候选人推荐人选汇总表 模板7
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
        if (ids != null && ids.length > 0) {
            userIdList.addAll(Arrays.asList(ids));
        }

        int count = 0;
        count = iPcsMapper.countReport(type, configId, stage, userIdList, partyId, branchId, partyIdList, branchIdList);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsFinalResult> records = iPcsMapper.selectReport(type, configId, stage, userIdList, partyId, branchId, partyIdList, branchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        if (export == 1) {
            pcsPollReport_export(records, stage, response);
            return;
        }

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

    public void pcsPollReport_export(List<PcsFinalResult> records, byte stage, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"学工号|120", "姓名|100", "所在单位|252", "推荐人类型|100", "推荐提名党支部数|100",
                "推荐提名党员数|100", "推荐提名正式党员数|100", "推荐提名预备党员数|100", "不同意票数|100", "弃权票数|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsFinalResult record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getRealname(),
                    record.getUnit(),
                    PcsConstants.PCS_USER_TYPE_MAP.get(record.getType()),
                    record.getBranchNum() + "",
                    record.getSupportNum() + "",
                    record.getPositiveBallot() + "",
                    record.getGrowBallot() + "",
                    stage==PcsConstants.PCS_POLL_FIRST_STAGE?"--":record.getNotSupportNum() + "",
                    stage==PcsConstants.PCS_POLL_FIRST_STAGE?"--":record.getNotVoteNum() + ""
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
        int rowInsert = reportList.size()/2;
        ExcelUtils.insertRow(wb, sheet, startRow, rowInsert - 1);
        int rowCount = 3;
        boolean flag = false;


        for (PcsPollReport report : reportList) {
            if (rowCount >= rowInsert+3) {
                flag = true;
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
