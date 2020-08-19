package service.pcs;

import domain.party.Party;
import domain.pcs.PcsConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.pcs.common.IPcsCandidate;
import persistence.pcs.common.PcsBranchBean;
import persistence.pcs.common.PcsPartyBean;
import service.analysis.StatService;
import service.party.PartyService;
import service.sys.SysConfigService;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.NumberUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lm on 2017/8/27.
 */
@Service
public class PcsExportService extends PcsBaseMapper {

    @Autowired
    private PartyService partyService;
    @Autowired
    protected StatService statService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected PcsConfigService pcsConfigService;


    public String getSchoolName(){

        return sysConfigService.getSchoolName();
    }
    /**
     * 附表5-1. 党委委员候选人推荐提名汇总表（报上级用）
     * 附表5-2. 纪委委员候选人推荐提名汇总表（报上级用）
     */
    public XSSFWorkbook exportPartyCandidates2(int configId, byte stage, byte type) throws IOException {

        List<IPcsCandidate> candidates =
                iPcsMapper.selectPartyCandidateList(null, null, configId, stage, type, new RowBounds());

        String pcsNum=pcsConfigService.getPcsNum(configId);

        String title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届委员会委员";
        if (type == PcsConstants.PCS_USER_TYPE_JW) {
            title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届纪律检查委员会委员";
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/wy-5-1.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        wb.setSheetName(0, PcsConstants.PCS_USER_TYPE_MAP.get(type));
        XSSFSheet sheet = wb.getSheetAt(0);

        String stageStr = "";
        switch (stage) {
            case PcsConstants.PCS_STAGE_FIRST:
                stageStr = "一上";
                break;
            case PcsConstants.PCS_STAGE_SECOND:
                stageStr = "二上";
                break;
        }

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", title)
                .replace("stage", stageStr);
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            IPcsCandidate bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 推荐提名的党支部数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getBranchCount()));

            // 推荐党支部所含党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualMemberCount()));
        }

        return wb;
    }

    /**
     * 附表4-1. 党委委员候选人推荐提名汇总表（组织部用）
     * 附表4-2. 纪委委员候选人推荐提名汇总表（组织部用）
     */
    public XSSFWorkbook exportPartyCandidates(Boolean isChosen,
                                              int configId,
                                              byte stage,
                                              byte type) throws IOException {

        List<IPcsCandidate> candidates =
                iPcsMapper.selectPartyCandidateList(null, isChosen, configId, stage, type, new RowBounds());

        String pcsNum=pcsConfigService.getPcsNum(configId);

        String title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届委员会委员";
        if (type == PcsConstants.PCS_USER_TYPE_JW) {
            title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届纪律检查委员会委员";
        }

        String stage_s = "";
        switch (stage) {
            case PcsConstants.PCS_STAGE_FIRST:
                stage_s = "二下";
                break;
            case PcsConstants.PCS_STAGE_SECOND:
                stage_s = "三下";
                break;
        }

        String filename = "wy-4-1_1.xlsx";
        if (BooleanUtils.isTrue(isChosen))
            filename = "wy-7-1_4.xlsx";

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/" + filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        wb.setSheetName(0, PcsConstants.PCS_USER_TYPE_MAP.get(type));
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", title)
                .replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage))
                .replace("ss", stage_s);
        cell.setCellValue(str);

        // 全校分党委数：pc    全校党支部数：bc     全校党员总数（含预备党员）：mc      应参会党员总数：emc      实参会党员总数：amc

        // 获得完成推荐的支部（含直属党支部）
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, null, null, true, new RowBounds());

        int branchCount = pcsBranchBeans.size();
        int memberCount = 0;
        Set<Integer> partyIdSet = new HashSet<>();
        for (PcsBranchBean bean : pcsBranchBeans) {
            if (bean.getMemberCount() != null) {
                memberCount += bean.getMemberCount();
            }
            partyIdSet.add(bean.getPartyId());
        }
        int partyCount = partyIdSet.size();

        Map<String, BigDecimal> schoolMemberCount = iPcsMapper.schoolMemberCount(configId, stage);
        int expect = (schoolMemberCount == null || schoolMemberCount.get("expect") == null)
                ? 0 : schoolMemberCount.get("expect").intValue();
        int actual = (schoolMemberCount == null || schoolMemberCount.get("actual") == null)
                ? 0 : schoolMemberCount.get("actual").intValue();

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("pc", partyCount + "")
                .replace("bc", branchCount + "")
                .replace("mc", memberCount + "")
                .replace("ec", expect + "")
                .replace("ac", actual + "");
        cell.setCellValue(str);

        int startRow = 4;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();

        for (int i = 0; i < rowCount; i++) {

            IPcsCandidate bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 工作证号
            cell = row.getCell(column++);
            cell.setCellValue(bean.getCode());

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getNation()));

            // 职称
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getProPost()));

            // 出生年月
            String birth = DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            cell = row.getCell(column++);
            Integer age=pcsConfigService.getCandidateAge(pcsConfig.getCreateTime(),bean.getBirth());
            cell.setCellValue(birth != null ? age + "" : "");

            // 入党时间
            String growTime = DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(growTime));

            /*// 参加工作时间
            String workTime = DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(workTime));*/

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.defaultIfBlank(bean.getTitle(),
                    StringUtils.trimToEmpty(bean.getExtUnit())));

            // 推荐提名的党支部数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getBranchCount()));

            // 推荐党支部实参会党员数（推荐提名的党员数）
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualMemberCount()));
        }

        return wb;
    }

    /**
     * 附表3. 参加两委委员候选人推荐提名情况汇总表（院系级党组织用）
     */
    public XSSFWorkbook exportRecommends_3(int configId, byte stage, int partyId) throws IOException {

        Party party = partyService.findAll().get(partyId);
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, partyId, null, null, new RowBounds());

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/re-3_5.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("name", party.getName());
        cell.setCellValue(str);

        // 汇总
        int memberCount = 0;
        int expectMemberCount = 0;
        int actualMemberCount = 0;

        int startRow = 3;
        int rowCount = pcsBranchBeans.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsBranchBean bean = pcsBranchBeans.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);

            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 党支部名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getName());

            // 党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getMemberCount()));

            // 应参会党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getExpectMemberCount()));

            // 实参会党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualMemberCount()));

            // 参与比率
            cell = row.getCell(column++);
            cell.setCellValue(percent(bean.getActualMemberCount(), bean.getExpectMemberCount()));

            memberCount += NumberUtils.trimToZero(bean.getMemberCount());
            expectMemberCount += NumberUtils.trimToZero(bean.getExpectMemberCount());
            actualMemberCount += NumberUtils.trimToZero(bean.getActualMemberCount());
        }

        if (rowCount > 0) {
            row = sheet.getRow(startRow++);
            int column = 2;
            cell = row.getCell(column++);
            cell.setCellValue(memberCount);
            cell = row.getCell(column++);
            cell.setCellValue(expectMemberCount);
            cell = row.getCell(column++);
            cell.setCellValue(actualMemberCount);
            cell = row.getCell(column++);
            cell.setCellValue(percent(actualMemberCount, expectMemberCount));
        }

   /*     row = sheet.getRow(startRow + 2 + (rowCount == 0 ? 1 : 0));
        cell = row.getCell(0);
        cell.setCellValue(DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));*/

        return wb;
    }

    /**
     * 附表6. 参加两委委员候选人推荐提名情况汇总表（组织部用）
     */
    public XSSFWorkbook exportRecommends_6(int configId, byte stage) throws IOException {

        List<PcsPartyBean> records = iPcsMapper.selectPcsPartyBeanList(configId, stage, null, null, new RowBounds());

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/re-6_3.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
        cell.setCellValue(str);

        Map<Integer, Integer> partyMemberCountMap = new HashMap<>();
        // 获得完成推荐的支部（排除之后的新建支部）
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, null, null, true, new RowBounds());
        for (PcsBranchBean pcsBranchBean : pcsBranchBeans) {

            Integer partyId = pcsBranchBean.getPartyId();
            Integer memberCount = NumberUtils.trimToZero(partyMemberCountMap.get(partyId));
            Integer _memberCount = NumberUtils.trimToZero(pcsBranchBean.getMemberCount());

            partyMemberCountMap.put(partyId, memberCount + _memberCount);
        }

        // 汇总
        int memberCount = 0;
        int expectMemberCount = 0;
        int actualMemberCount = 0;

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPartyBean bean = records.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);

            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 分党委名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getName());

            // 党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(partyMemberCountMap.get(bean.getPartyId())));

            // 应参会党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getExpectMemberCount()));

            // 实参会党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualMemberCount()));

            // 参与比率
            cell = row.getCell(column++);
            cell.setCellValue(percent(bean.getActualMemberCount(), bean.getExpectMemberCount()));

            memberCount += NumberUtils.trimToZero(partyMemberCountMap.get(bean.getPartyId()));
            expectMemberCount += NumberUtils.trimToZero(bean.getExpectMemberCount());
            actualMemberCount += NumberUtils.trimToZero(bean.getActualMemberCount());
        }

        if (rowCount > 0) {
            row = sheet.getRow(startRow++);
            int column = 2;
            cell = row.getCell(column++);
            cell.setCellValue(memberCount);
            cell = row.getCell(column++);
            cell.setCellValue(expectMemberCount);
            cell = row.getCell(column++);
            cell.setCellValue(actualMemberCount);
            cell = row.getCell(column++);
            cell.setCellValue(percent(actualMemberCount, expectMemberCount));
        }

        return wb;
    }

    public static String percent(Integer count, Integer total) {

        if (count == null || total == null /*|| count <= 0 || total <= 0*/|| total == 0) return "0.00%";

        return NumberUtils.formatDoubleFixed((count * 100.0) / total, 2) + "%";
    }

    /**
     * 附表2-1. 党委委员候选人推荐提名汇总表（院系级党组织用）
     * 附表2-2. 纪委委员候选人推荐提名汇总表（院系级党组织用）
     */
    public XSSFWorkbook exportBranchCandidates(int configId, byte stage, byte type, int partyId) throws IOException {

        Party party = partyService.findAll().get(partyId);
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, partyId, null, null, new RowBounds());
        int branchCount = pcsBranchBeans.size();
        int memberCount = 0;
        int expectMemberCount = 0;
        int actualMemberCount = 0;
        for (PcsBranchBean pcsBranchBean : pcsBranchBeans) {
            memberCount += pcsBranchBean.getMemberCount() == null ? 0 : pcsBranchBean.getMemberCount();
            expectMemberCount += pcsBranchBean.getExpectMemberCount() == null ? 0 : pcsBranchBean.getExpectMemberCount();
            actualMemberCount += pcsBranchBean.getActualMemberCount() == null ? 0 : pcsBranchBean.getActualMemberCount();
        }

        List<IPcsCandidate> candidates =
                iPcsMapper.selectBranchCandidateList(null, configId, stage, type, partyId, new RowBounds());

        switch (stage) {
            case PcsConstants.PCS_STAGE_FIRST:
                break;
            case PcsConstants.PCS_STAGE_SECOND:
                break;
        }

        String pcsNum=pcsConfigService.getPcsNum(configId);
        
        String title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届委员会委员";
        if (type == PcsConstants.PCS_USER_TYPE_JW) {
            title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届纪律检查委员会委员";
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/wy-2-1_3.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        wb.setSheetName(0, PcsConstants.PCS_USER_TYPE_MAP.get(type));
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", title)
                .replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("name", party.getName());
        cell.setCellValue(str);

        row = sheet.getRow(2);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("branch", branchCount + "")
                .replace("member", memberCount + "")
                .replace("expect", expectMemberCount + "")
                .replace("actual", actualMemberCount + "");
        cell.setCellValue(str);

        int startRow = 5;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            IPcsCandidate bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 工作证号
            cell = row.getCell(column++);
            cell.setCellValue(bean.getCode());

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getNation()));

            // 职称
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getProPost()));

            // 出生年月
            String birth = DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 入党时间
            String growTime = DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(growTime));

          /*  // 参加工作时间
            String workTime = DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(workTime));*/

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.defaultIfBlank(bean.getTitle(),
                    StringUtils.trimToEmpty(bean.getExtUnit())));

            // 推荐提名的党支部数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToZero(bean.getBranchCount()));

            // 推荐党支部实参会党员数（推荐提名的党员数）
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToZero(bean.getActualMemberCount()));
        }

        startRow = startRow + 2 + (rowCount == 0 ? 1 : 0);
        row = sheet.getRow(startRow);

        cell = row.getCell(0);
        cell.setCellValue(DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        try {
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow - 1, 0, startRow - 1, row.getLastCellNum() - 1));
        } catch (Exception e) {

        }
        try {
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow, 0, startRow, row.getLastCellNum() - 1));
        } catch (Exception e) {

        }

        return wb;
    }

    /**
     * 二下二上， “二下”名单
     * 附表1-1. 党委委员候选人推荐提名汇总表（党支部用）修改
     * 附表1-2. 纪委委员候选人推荐提名汇总表（党支部用）
     */
    public XSSFWorkbook exportIssueCandidates(int configId, byte stage, byte type) throws IOException {

        List<IPcsCandidate> candidates =
                iPcsMapper.selectPartyCandidateList(null, true, configId, stage, type, new RowBounds());

        String pcsNum=pcsConfigService.getPcsNum(configId);

        String title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届委员会委员";
        if (type == PcsConstants.PCS_USER_TYPE_JW) {
            title = "中国共产党"+getSchoolName()+"第"+ pcsNum +"届纪律检查委员会委员";
        }

        String filename = "wy-1-1_6.xlsx";
        if (stage == PcsConstants.PCS_STAGE_SECOND) {
            filename = "wy-3-1_1.xlsx"; // 三下名单
        }

        String typeName = PcsConstants.PCS_USER_TYPE_MAP.get(type);
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/" + filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        wb.setSheetName(0, typeName);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", title)
                .replace("stage", PcsConstants.PCS_STAGE_MAP.get((byte)(stage+1))); // 下一个阶段
        cell.setCellValue(str);

        //if (stage == PcsConstants.PCS_STAGE_FIRST) {
            String count = "30";
            if (type == PcsConstants.PCS_USER_TYPE_JW)
                count = "16";
            row = sheet.getRow(2);
            cell = row.getCell(0);
            str = cell.getStringCellValue()
                    .replaceAll("type", typeName)
                    .replace("count", count);
            cell.setCellValue(str);
        //}

        row = sheet.getRow(3);
        cell = row.getCell(1);
        str = cell.getStringCellValue()
                .replace("type", typeName);
        cell.setCellValue(str);

        int startRow = 5;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();

        for (int i = 0; i < rowCount; i++) {

            IPcsCandidate bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

        /*    // 工作证号
            cell = row.getCell(column++);
            cell.setCellValue(bean.getCode());*/

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getNation()));
            /*// 籍贯
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(""));
            // 学历学位
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(""));*/

            // 职称
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getProPost()));


            /*
            // 出生年月
            String birth = DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));*/

            // 年龄
            cell = row.getCell(column++);
            Integer age=pcsConfigService.getCandidateAge(pcsConfig.getCreateTime(),bean.getBirth());
            cell.setCellValue(bean.getBirth() != null ? age + "" : "");

            // 入党时间
            String growTime = DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(growTime));

           /* // 参加工作时间
            String workTime = DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(workTime));*/

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.defaultIfBlank(bean.getTitle(),
                    StringUtils.trimToEmpty(bean.getExtUnit())));
        }

        /*row = sheet.getRow(startRow + 2 + (rowCount == 0 ? 1 : 0));
        cell = row.getCell(0);
        cell.setCellValue(DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));*/

        return wb;
    }
}
