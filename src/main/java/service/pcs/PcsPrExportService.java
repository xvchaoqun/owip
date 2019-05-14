package service.pcs;

import domain.cadre.CadreView;
import domain.pcs.*;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.pcs.common.PcsBranchBean;
import persistence.pcs.common.PcsPartyBean;
import persistence.pcs.common.PcsPrPartyBean;
import service.analysis.StatService;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.sys.StudentInfoService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import sys.constants.CadreConstants;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/8/27.
 */
@Service
public class PcsPrExportService extends PcsBaseMapper {

    @Autowired
    private PcsPartyViewService pcsPartyViewService;
    @Autowired
    protected StatService statService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected TeacherInfoService teacherService;
    @Autowired
    protected StudentInfoService studentService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected PcsPrAlocateService pcsPrAlocateService;
    @Autowired
    protected PcsPrCandidateService pcsPrCandidateService;
    @Autowired
    protected PcsPrPartyService pcsPrPartyService;
    @Autowired
    protected PcsPrListService pcsPrListService;

    /**
     * 附件7. 全校党员参与推荐代表候选人情况统计表（组织部汇总）
     */
    public XSSFWorkbook exportSchoolRecommend(int configId, byte stage) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/pr-7.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue().replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
        cell.setCellValue(str);

        int memberCount = 0;
        int positiveCount = 0;
        int expectMemberCount = 0;
        int expectPositiveMemberCount = 0;
        int actualMemberCount = 0;
        int actualPositiveMemberCount = 0;

        Map<Integer, PcsPartyBean> partyMemberCountMap = getPartyMemberCountMap(configId, stage);

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeanList(configId, stage, null, null, null, new RowBounds());
        int startRow = 3;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrPartyBean bean = records.get(i);

            PcsPartyBean pcsPartyBean = partyMemberCountMap.get(bean.getId());
            if(pcsPartyBean!=null) {
                memberCount += NumberUtils.trimToZero(pcsPartyBean.getMemberCount());
                positiveCount += NumberUtils.trimToZero(pcsPartyBean.getPositiveCount());
            }


            expectMemberCount += NumberUtils.trimToZero(bean.getExpectMemberCount());
            expectPositiveMemberCount += NumberUtils.trimToZero(bean.getExpectPositiveMemberCount());
            actualMemberCount += NumberUtils.trimToZero(bean.getActualMemberCount());
            actualPositiveMemberCount += NumberUtils.trimToZero(bean.getActualPositiveMemberCount());

   /*         memberCount += NumberUtils.trimToZero(bean.getMemberCount());
            positiveCount += NumberUtils.trimToZero(bean.getPositiveCount());
            expectMemberCount += NumberUtils.trimToZero(bean.getExpectMemberCount());
            expectPositiveMemberCount += NumberUtils.trimToZero(bean.getExpectPositiveMemberCount());
            actualMemberCount += NumberUtils.trimToZero(bean.getActualMemberCount());
            actualPositiveMemberCount += NumberUtils.trimToZero(bean.getActualPositiveMemberCount());*/

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 分党委名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getName());

            // 党员总数
            cell = row.getCell(column++);
            if(pcsPartyBean!=null)
                cell.setCellValue(NumberUtils.trimToEmpty(pcsPartyBean.getMemberCount()));

            // 正式党员数
            cell = row.getCell(column++);
            if(pcsPartyBean!=null)
                cell.setCellValue(NumberUtils.trimToEmpty(pcsPartyBean.getPositiveCount()));

            // 党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getExpectMemberCount()));
            // 正式党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getExpectPositiveMemberCount()));
            // 党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualMemberCount()));
            // 正式党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualPositiveMemberCount()));

            // 参与比率
            cell = row.getCell(column++);
            cell.setCellValue(percent(bean.getActualMemberCount(), bean.getExpectMemberCount()));
        }

        // 合计
        row = sheet.getRow(startRow++);
        int column = 2;

        // 党员总数
        cell = row.getCell(column++);
        cell.setCellValue(memberCount);

        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(positiveCount);

        // 党员数
        cell = row.getCell(column++);
        cell.setCellValue(expectMemberCount);
        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(expectPositiveMemberCount);
        // 党员数
        cell = row.getCell(column++);
        cell.setCellValue(actualMemberCount);
        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(actualPositiveMemberCount);

        // 参与比率
        cell = row.getCell(column++);
        cell.setCellValue(percent(actualMemberCount, expectMemberCount));

        row = sheet.getRow(startRow);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }

    /**
     * 附件6. 各分党委酝酿代表候选人初步人选统计表（组织部汇总）
     */
    public XSSFWorkbook exportSchoolAllocate(int configId, byte stage) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/pr-6.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        String title = "";
        String stageStr = "";
        switch (stage) {
            case PcsConstants.PCS_STAGE_FIRST:
                title = "初步";
                stageStr = "一上";
                break;
            case PcsConstants.PCS_STAGE_SECOND:
                title = "预备";
                stageStr = "二上";
                break;
            case PcsConstants.PCS_STAGE_THIRD:
                stageStr = "三上";
                break;
        }

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue().replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
        cell.setCellValue(str);
        //cell.setCellValue(UnderLineIndex(str, getFont(wb)));

        Map<String, String> schoolMemberCountMap = getSchoolMemberCountMap(configId, stage);
        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("mc", schoolMemberCountMap.get("mc"))
                .replace("tc", schoolMemberCountMap.get("tc"))
                .replace("sc", schoolMemberCountMap.get("sc"))
                .replace("rc", schoolMemberCountMap.get("rc"));
        cell.setCellValue(str);

        row = sheet.getRow(5);
        cell = row.getCell(1);
        str = cell.getStringCellValue().replace("stage", stageStr);
        cell.setCellValue(str);

        PcsPrAllocate pcsPrAllocate = iPcsMapper.schoolPcsPrAllocate(configId);
        PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, stage, null, null);
        renderParty(sheet, 4, 3, pcsPrAllocate, realPcsPrAllocate);

        row = sheet.getRow(8);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }

    /**
     * 附件4. 分党委酝酿代表候选人初步人选统计表（分党委报送组织部）
     */
    public XSSFWorkbook exportPartyAllocate(int configId, byte stage, int partyId) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/pr-4_9.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        String title = "";
        String deadline = "";
        String stageStr = "";
        switch (stage) {
            case PcsConstants.PCS_STAGE_FIRST:
                title = "初步";
                deadline = "9月6号前";
                stageStr = "一上";
                break;
            case PcsConstants.PCS_STAGE_SECOND:
                title = "预备";
                deadline = "9月11日前";
                stageStr = "二上";
                break;
            case PcsConstants.PCS_STAGE_THIRD:
                stageStr = "三上";
                deadline = "9月18日前";
                break;
        }

        PcsPartyView pv = pcsPartyViewService.get(partyId);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue().replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage))
                .replace("deadline", deadline);
        cell.setCellValue(str);
        //cell.setCellValue(UnderLineIndex(str, getFont(wb)));

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("party", pv.getName());
        cell.setCellValue(str);

        row = sheet.getRow(2);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("mc", pv.getMemberCount() + "")
                .replace("tc", pv.getTeacherMemberCount() + "")
                .replace("sc", pv.getStudentMemberCount() + "")
                .replace("rc", pv.getRetireMemberCount() + "");
        cell.setCellValue(str);

        row = sheet.getRow(6);
        cell = row.getCell(1);
        str = cell.getStringCellValue().replace("stage", stageStr);
        cell.setCellValue(str);

        PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);
        PcsPrAllocate realPcsPrAllocate = null;
        if (stage == PcsConstants.PCS_STAGE_THIRD)
            realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, PcsConstants.PCS_STAGE_SECOND, partyId, true);
        else
            realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, stage, partyId, null);

        renderParty(sheet, 5, 3, pcsPrAllocate, realPcsPrAllocate);

        row = sheet.getRow(9);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }

    // 获取学历、工作时间、职别、职务
    public Map<String, String> getUserInfoMap(int userId) {
        String edu = "-";
        String workTime = "-";
        String proPost = "-";
        String post = "-";
        SysUserView uv = sysUserService.findById(userId);
        if (uv.getType() == SystemConstants.USER_TYPE_JZG) {

            TeacherInfo teacherInfo = teacherService.get(userId);
            CadreView cv = cadreService.dbFindByUserId(userId);
            if (cv != null && CadreConstants.CADRE_STATUS_NOW_SET.contains(cv.getStatus())) {
                // 是干部
                edu = metaTypeService.getName(cv.getEduId());
                workTime = DateUtils.formatDate(cv.getWorkTime(), DateUtils.YYYYMM);
                post = cv.getPost();
                proPost = "干部";
            } else {
                // 是普通教师
                edu = teacherInfo.getEducation();
                workTime = DateUtils.formatDate(teacherInfo.getWorkTime(), DateUtils.YYYYMM);
                proPost = teacherInfo.getProPost();
            }
        } else {
            StudentInfo studentInfo = studentService.get(userId);
            // 学生
            proPost = studentInfo.getEduLevel();
        }

        Map<String, String> map = new HashMap<>();
        map.put("edu", edu);
        map.put("workTime", workTime);
        map.put("proPost", proPost);
        map.put("post", post);

        return map;
    }

    // 获得完成推荐的支部（排除之后的新建支部）的分党委的党员数量统计（与两委统计数据保持一致）
    public Map<Integer, PcsPartyBean> getPartyMemberCountMap(int configId, byte stage) {


        // 分党委数量统计
        Map<Integer, PcsPartyBean> partyBeanMap = new HashMap<>();

        // 获得完成推荐的支部（排除之后的新建支部）（与两委统计数据保持一致）
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, null, null, true, new RowBounds());

        for (PcsBranchBean bean : pcsBranchBeans) {

            int _mc = NumberUtils.trimToZero(bean.getMemberCount());
            int _pc = NumberUtils.trimToZero(bean.getPositiveCount());
            int _sc = NumberUtils.trimToZero(bean.getStudentMemberCount());
            int _tc = NumberUtils.trimToZero(bean.getTeacherMemberCount());
            int _rc = NumberUtils.trimToZero(bean.getRetireMemberCount());

            Integer partyId = bean.getPartyId();
            PcsPartyBean pcsPartyBean = partyBeanMap.get(partyId);
            if (pcsPartyBean == null) {
                pcsPartyBean = new PcsPartyBean();
                partyBeanMap.put(partyId, pcsPartyBean);
            }
            pcsPartyBean.setMemberCount(NumberUtils.trimToZero(pcsPartyBean.getMemberCount()) + _mc);
            pcsPartyBean.setPositiveCount(NumberUtils.trimToZero(pcsPartyBean.getPositiveCount()) + _pc);
            pcsPartyBean.setStudentMemberCount(NumberUtils.trimToZero(pcsPartyBean.getStudentMemberCount()) + _sc);
            pcsPartyBean.setTeacherMemberCount(NumberUtils.trimToZero(pcsPartyBean.getTeacherMemberCount()) + _tc);
            pcsPartyBean.setRetireMemberCount(NumberUtils.trimToZero(pcsPartyBean.getRetireMemberCount()) + _rc);
        }

       return partyBeanMap;
    }

    public Map<String, String> getSchoolMemberCountMap(int configId, byte stage) {


        //int totalMemberCount = 0;

        // 获得完成推荐的支部（排除之后的新建支部）（与两委统计数据保持一致）
        List<PcsBranchBean> pcsBranchBeans =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, null, null, true, new RowBounds());

        int memberCount = 0;
        int studentMemberCount = 0;
        int teacherMemberCount = 0;
        int retireMemberCount = 0;
        for (PcsBranchBean bean : pcsBranchBeans) {

            memberCount += NumberUtils.trimToZero(bean.getMemberCount());
            studentMemberCount += NumberUtils.trimToZero(bean.getStudentMemberCount());
            teacherMemberCount += NumberUtils.trimToZero(bean.getTeacherMemberCount());
            retireMemberCount += NumberUtils.trimToZero(bean.getRetireMemberCount());
        }

        Map<String, String> map = new HashMap<>();
        String mc = memberCount + "";
        String tc = teacherMemberCount + "";
        String sc = studentMemberCount+ "";
        String rc = retireMemberCount + "";

        map.put("mc", mc);
        map.put("tc", tc);
        map.put("sc", sc);
        map.put("rc", rc);

        return map;
    }

    public void renderParty(XSSFSheet sheet,
                            int startRow, int startColomn, PcsPrAllocate pcsPrAllocate,
                            PcsPrAllocate realPcsPrAllocate) {

        XSSFRow row = null;
        XSSFCell cell = null;
        int colomn = startColomn;
        int expectTotal = 0;
        if (pcsPrAllocate != null) {
            row = sheet.getRow(startRow);
            expectTotal = NumberUtils.trimToZero(pcsPrAllocate.getProCount())
                    + NumberUtils.trimToZero(pcsPrAllocate.getStuCount())
                    + NumberUtils.trimToZero(pcsPrAllocate.getRetireCount());
            cell = row.getCell(colomn++);
            if (expectTotal > 0)
                cell.setCellValue(expectTotal);
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(pcsPrAllocate.getProCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(pcsPrAllocate.getStuCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(pcsPrAllocate.getRetireCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(pcsPrAllocate.getFemaleCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(pcsPrAllocate.getMinorityCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(pcsPrAllocate.getUnderFiftyCount()));
        }
        int actualTotal = 0;
        if (realPcsPrAllocate != null) {
            row = sheet.getRow(startRow + 1);
            actualTotal = NumberUtils.trimToZero(realPcsPrAllocate.getProCount())
                    + NumberUtils.trimToZero(realPcsPrAllocate.getStuCount())
                    + NumberUtils.trimToZero(realPcsPrAllocate.getRetireCount());
            colomn = startColomn;
            cell = row.getCell(colomn++);
            if (actualTotal > 0)
                cell.setCellValue(actualTotal);
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(realPcsPrAllocate.getProCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(realPcsPrAllocate.getStuCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(realPcsPrAllocate.getRetireCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(realPcsPrAllocate.getFemaleCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(realPcsPrAllocate.getMinorityCount()));
            cell = row.getCell(colomn++);
            cell.setCellValue(NumberUtils.trimToEmpty(realPcsPrAllocate.getUnderFiftyCount()));
        }


        String _balance = "-";
        String _percent = "-";
        if(pcsPrAllocate != null && expectTotal > 0 && realPcsPrAllocate != null){
            int balance =  actualTotal - expectTotal;
            _balance = balance + "";
            _percent = percent(balance, expectTotal) + "";
        }

        row = sheet.getRow(startRow + 2);
        //差额
        cell = row.getCell(startColomn);
        cell.setCellValue(_balance);

        for (int i = 0; i < 6; i++) {
            cell = row.getCell(startColomn + i + 1);
            cell.setCellValue("-");
        }

        row = sheet.getRow(startRow + 3);
        // 差额比率
        cell = row.getCell(startColomn);
        cell.setCellValue(_percent);

        for (int i = 0; i < 6; i++) {
            cell = row.getCell(startColomn + i + 1);
            cell.setCellValue("-");
        }

    }

    /**
     * 附件：各分党委酝酿代表候选人初步人选统计表（组织部汇总）
     */
    public XSSFWorkbook exportAllPartyAllocate(int configId, byte stage) throws IOException {

        Boolean isChosen = null;
        String filename = "pr-ow.xlsx";
        if(stage == PcsConstants.PCS_STAGE_THIRD){
            filename = "pr-ow3.xlsx";
            stage = PcsConstants.PCS_STAGE_SECOND;
            isChosen = true;
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/" + filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet copySheet = wb.getSheetAt(0);// 模板
        XSSFSheet sheet = wb.getSheetAt(1);

        XSSFRow row = null;
        XSSFCell cell = null;
        String str = null;
        String stageStr = "";

        if(stage != PcsConstants.PCS_STAGE_THIRD) {
            String title = "";

            switch (stage) {
                case PcsConstants.PCS_STAGE_FIRST:
                    title = "初步";
                    stageStr = "一上";
                    break;
                case PcsConstants.PCS_STAGE_SECOND:
                    title = "预备";
                    stageStr = "二上";
                    break;
            }

            row = sheet.getRow(0);
            cell = row.getCell(0);
            str = cell.getStringCellValue()
                    .replace("title", title)
                    .replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
            cell.setCellValue(str);
        }else{

            stageStr = "三上";
        }

        Map<String, String> schoolMemberCountMap = getSchoolMemberCountMap(configId, stage);
        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("mc", schoolMemberCountMap.get("mc"))
                .replace("tc", schoolMemberCountMap.get("tc"))
                .replace("sc", schoolMemberCountMap.get("sc"))
                .replace("rc", schoolMemberCountMap.get("rc"));
        cell.setCellValue(str);

        PcsPartyViewExample example = new PcsPartyViewExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("sort_order desc");
        List<PcsPartyView> pcsPartyViews = pcsPartyViewMapper.selectByExample(example);

        int startRow = 4;
        for (int i = 0; i < pcsPartyViews.size(); i++) {

            ExcelUtils.copyRows(5, 8, startRow, copySheet, sheet);

            PcsPartyView pv = pcsPartyViews.get(i);
            int partyId = pv.getId();
            String partyName = pv.getName();

            int colomn = 0;
            row = sheet.getRow(startRow);
            // 序号
            cell = row.getCell(colomn++);
            cell.setCellValue((i + 1));
            // 分党委名称
            cell = row.getCell(colomn++);
            cell.setCellValue(partyName);

            row = sheet.getRow(startRow + 1);
            cell = row.getCell(2);
            str = cell.getStringCellValue().replace("stage", stageStr);
            cell.setCellValue(str);

            PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);
            PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, stage, partyId, isChosen);
            renderParty(sheet, startRow, 4, pcsPrAllocate, realPcsPrAllocate);

            startRow += 4;
        }

        {
            ExcelUtils.copyRows(9, 12, startRow, copySheet, sheet);

            row = sheet.getRow(startRow + 1);
            cell = row.getCell(2);
            str = cell.getStringCellValue().replace("stage", stageStr);
            cell.setCellValue(str);

            // 全校汇总
            PcsPrAllocate pcsPrAllocate = iPcsMapper.schoolPcsPrAllocate(configId);
            PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, stage, null, isChosen);
            renderParty(sheet, startRow, 4, pcsPrAllocate, realPcsPrAllocate);

            startRow += 4;
        }

        ExcelUtils.copyRows(13, 13, startRow, copySheet, sheet);
        row = sheet.getRow(startRow);
        try {
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow, 0, startRow, row.getLastCellNum() - 1));
        } catch (Exception e) {
        }
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        // 删除模板
        wb.removeSheetAt(0);
        return wb;
    }

    private int getIntVal(Map<String, BigDecimal> map, String key) {
        if (map == null || !map.containsKey(key)) return 0;
        BigDecimal val = map.get(key);
        return val == null ? 0 : val.intValue();
    }

    public static String percent(Integer count, Integer total) {

        if (count == null || total == null  || total == 0) return "";

        return NumberUtils.formatDoubleFixed((count * 100.0) / total, 2) + "%";
    }

    // 二下二上，附表1. 代表候选人推荐票（党员推荐用，报党支部）
    public XSSFWorkbook exportPartyCandidates1_stage2(int configId, int partyId) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/pr-2-1_2.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        PcsPrCandidateViewExample example = pcsPrCandidateService.createExample(configId,
                PcsConstants.PCS_STAGE_FIRST, partyId, null);

        List<PcsPrCandidateView> candidates = pcsPrCandidateViewMapper.selectByExample(example);

        XSSFRow row = null;
        XSSFCell cell = null;

        int startRow = 5;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrCandidateView bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 候选人初步人选
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));
        }

     /*   row = sheet.getRow(startRow + 1 + (rowCount == 0 ? 1 : 0));
        cell = row.getCell(0);
        cell.setCellValue(DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));*/

        return wb;
    }

    // 二下二上，附表2. 党支部酝酿代表候选人提名汇总表（党支部汇总用表，报分党委）
    public XSSFWorkbook exportPartyCandidates2_stage2(int configId, int partyId) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/pr-2-2_3.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        PcsPrCandidateViewExample example = pcsPrCandidateService.createExample(configId,
                PcsConstants.PCS_STAGE_FIRST, partyId, null);
        List<PcsPrCandidateView> candidates = pcsPrCandidateViewMapper.selectByExample(example);

        XSSFRow row = null;
        XSSFCell cell = null;

        int startRow = 7;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrCandidateView bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 候选人初步人选
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));
        }

  /*      row = sheet.getRow(startRow + 1 + (rowCount == 0 ? 1 : 0));
        cell = row.getCell(0);
        cell.setCellValue(DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
*/
        return wb;
    }

    /**
     * 附件3. 分党委酝酿代表候选人初步名单（分党委报送组织部）
     * 附件5. 各分党委酝酿代表候选人初步名单汇总表（组织部汇总）
     */
    public XSSFWorkbook exportPartyCandidates(int configId, byte stage, Integer partyId) throws IOException {

        String filename = "pr-3_8.xlsx";
        if (partyId == null) {
            filename = "pr-5_1.xlsx";
        }
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/" + filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        String title = "";
        String deadline = "";
        String rate = "";
        String nextStageStr = "";
        switch (stage) {
            case PcsConstants.PCS_STAGE_FIRST:
                title = "初步";
                rate = "30%";
                deadline = "9月6号前";
                nextStageStr = "二下";
                break;
            case PcsConstants.PCS_STAGE_SECOND:
                title = "预备";
                rate = "20%";
                deadline = "9月11日前";
                nextStageStr = "三下";
                break;
        }

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", title)
                .replace("stage", PcsConstants.PCS_STAGE_MAP.get(stage));
        if (partyId != null) {
            str = str.replace("deadline", deadline);
        }
        cell.setCellValue(str);
        //cell.setCellValue(UnderLineIndex(str, getFont(wb)));

        String mc = "";
        String tc = "";
        String sc = "";
        String rc = "";
        int countRow = 1;
        int nextRow = 2;
        int startRow = 4;
        if (partyId != null) {

            PcsPartyView pv = pcsPartyViewService.get(partyId);
            mc = pv.getMemberCount() + "";
            tc = pv.getTeacherMemberCount() + "";
            sc = pv.getStudentMemberCount() + "";
            rc = pv.getRetireMemberCount() + "";
            row = sheet.getRow(1);
            cell = row.getCell(0);
            str = cell.getStringCellValue().replace("party", pv.getName());
            cell.setCellValue(str);

            countRow = 2;
            nextRow = 3;
            startRow = 5;
        } else {
            // 全校
            Map<String, String> schoolMemberCountMap = getSchoolMemberCountMap(configId, stage);
            mc = schoolMemberCountMap.get("mc");
            tc = schoolMemberCountMap.get("tc");
            sc = schoolMemberCountMap.get("sc");
            rc = schoolMemberCountMap.get("rc");
        }

        row = sheet.getRow(countRow);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("mc", mc)
                .replace("tc", tc)
                .replace("sc", sc)
                .replace("rc", rc);
        cell.setCellValue(str);

        row = sheet.getRow(nextRow);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("rate", rate)
                .replace("nextStageShort", nextStageStr);
        cell.setCellValue(str);

        PcsPrCandidateViewExample example = pcsPrCandidateService.createExample(configId, stage, partyId, null);
        List<PcsPrCandidateView> candidates = pcsPrCandidateViewMapper.selectByExample(example);

        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrCandidateView bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 党代表类型
            cell = row.getCell(column++);
            cell.setCellValue(PcsConstants.PCS_PR_TYPE_MAP.get(bean.getType()));

            if (partyId != null) {
                // 工作证号
                cell = row.getCell(column++);
                cell.setCellValue(bean.getCode());
            }

            // 被推荐人姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            if (partyId == null) {
                // 单位
                cell = row.getCell(column++);
                cell.setCellValue(StringUtils.trimToEmpty(bean.getUnitName()));
            }

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 出生年月
            String birth = DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            cell = row.getCell(column++);
            cell.setCellValue(birth != null ? DateUtils.intervalYearsUntilNow(bean.getBirth()) + "" : "");

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getNation()));

            Map<String, String> userInfoMap = getUserInfoMap(bean.getUserId());

            // 学历
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("edu"));
/*
            // 参加工作时间
            cell = row.getCell(column++);
            cell.setCellValue(workTime);*/

            // 入党时间
            String growTime = DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(growTime));

            // 职别
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("proPost"));

            // 职务
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("post"));

            // 票数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getVote()));
        }

        if (partyId != null) {
            row = sheet.getRow(startRow);
            cell = row.getCell(0);
            cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        }

        return wb;
    }

    /**
     * 附表1. 党代表名单（分党委上报组织部）
     */
    public XSSFWorkbook exportPartyList(int configId, int partyId) throws IOException {

        XSSFRow row = null;
        XSSFCell cell = null;
        String str = null;

        String mt = "";
        String ma = "";
        String mc = "";
        String pc = "";
        String ec = "";
        String ac = "";

        String filename = "prList-1_1.xlsx";

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_THIRD, partyId);
        if(pcsPrRecommend!=null) {
            mt = DateUtils.formatDate(pcsPrRecommend.getMeetingTime(), "yyyy年MM月dd日HH:mm");
            ma = StringUtils.trimToEmpty(pcsPrRecommend.getMeetingAddress());
            ec = pcsPrRecommend.getExpectPositiveMemberCount() + "";
            ac = pcsPrRecommend.getActualPositiveMemberCount() + "";

            // 不自动统计，读取分党委填写的
            pc = pcsPrRecommend.getVoteMemberCount() + "";

            if(pcsPrRecommend.getMeetingType()==2){
                filename = "prList-1_2.xlsx";
            }
        }


        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/" + filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        PcsPartyView pv = pcsPartyViewService.get(partyId);
        mc = pv.getMemberCount() + "";
        //pc = pv.getPositiveCount() + "";

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("party", pv.getName());
        cell.setCellValue(str);


        row = sheet.getRow(2);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("mt", mt)
                .replace("ma", ma)
                .replace("mc", mc)
                .replace("pc", pc)
                .replace("ec", ec)
                .replace("ac", ac);
        cell.setCellValue(str);

        int startRow = 4;
        List<PcsPrCandidateView> candidates = pcsPrListService.getList2(configId, partyId, true);
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrCandidateView bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 党代表类型
            cell = row.getCell(column++);
            cell.setCellValue(PcsConstants.PCS_PR_TYPE_MAP.get(bean.getType()));

            // 工作证号
            cell = row.getCell(column++);
            cell.setCellValue(bean.getCode());

            // 被推荐人姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 出生年月
            String birth = DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            cell = row.getCell(column++);
            cell.setCellValue(birth != null ? DateUtils.intervalYearsUntilNow(bean.getBirth()) + "" : "");

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getNation()));

            Map<String, String> userInfoMap = getUserInfoMap(bean.getUserId());

            // 学历
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("edu"));
/*
            // 参加工作时间
            cell = row.getCell(column++);
            cell.setCellValue(workTime);*/

            // 入党时间
            String growTime = DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(growTime));

            // 职别
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("proPost"));

            // 职务
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("post"));

            // 票数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getVote3()));
            // 手机号
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getMobile()));
            // 邮箱
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getEmail()));
        }

        row = sheet.getRow(startRow);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }

    /**
     * 附表3. 全校党代表汇总表（组织部汇总）
     */
    public XSSFWorkbook exportSchoolList(int configId) throws IOException {

        String filename = "prList-3_1.xlsx";
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/" + filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        // 使用阶段二的数据？
        Map<String, String> schoolMemberCountMap = getSchoolMemberCountMap(configId,
                PcsConstants.PCS_STAGE_SECOND);

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("mc", schoolMemberCountMap.get("mc") + "")
                .replace("tc", schoolMemberCountMap.get("tc") + "")
                .replace("sc", schoolMemberCountMap.get("sc") + "")
                .replace("rc", schoolMemberCountMap.get("rc") + "");
        cell.setCellValue(str);

        int startRow = 3;
        List<PcsPrCandidateView> candidates = pcsPrListService.getList2(configId, null, true);
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrCandidateView bean = candidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 党代表类型
            cell = row.getCell(column++);
            cell.setCellValue(PcsConstants.PCS_PR_TYPE_MAP.get(bean.getType()));

            // 工作证号
            cell = row.getCell(column++);
            cell.setCellValue(bean.getCode());

            // 被推荐人姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());

            // 单位
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getUnitName()));

            // 岗位类别
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getProPost()));

            // 岗位子类别
            /*cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getUnit()));*/

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (bean.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(bean.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 出生年月
            String birth = DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            cell = row.getCell(column++);
            cell.setCellValue(birth != null ? DateUtils.intervalYearsUntilNow(bean.getBirth()) + "" : "");

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getNation()));


            Map<String, String> userInfoMap = getUserInfoMap(bean.getUserId());
            // 学历
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("edu"));
/*
            // 参加工作时间
            cell = row.getCell(column++);
            cell.setCellValue(workTime);*/

            // 入党时间
            String growTime = DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(growTime));

            // 职别
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("proPost"));

            // 职务
            cell = row.getCell(column++);
            cell.setCellValue(userInfoMap.get("post"));

            // 票数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getVote3()));
            // 手机号
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getMobile()));
            // 邮箱
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(bean.getEmail()));
        }

        row = sheet.getRow(startRow);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }

    // 党代表数据统计表
    public XSSFWorkbook exportAllocate(int configId) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/prList-4.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        Map<String, String> schoolMemberCountMap = getSchoolMemberCountMap(configId, PcsConstants.PCS_STAGE_THIRD);

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("mc", schoolMemberCountMap.get("mc") + "")
                .replace("tc", schoolMemberCountMap.get("tc") + "")
                .replace("sc", schoolMemberCountMap.get("sc") + "")
                .replace("rc", schoolMemberCountMap.get("rc") + "");
        cell.setCellValue(str);


        PcsPrAllocate pcsPrAllocate = iPcsMapper.schoolPcsPrAllocate(configId);
        PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId,
                PcsConstants.PCS_STAGE_SECOND, null, true);

        renderParty(sheet, 4, 3, pcsPrAllocate, realPcsPrAllocate);

        row = sheet.getRow(8);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }

    public XSSFWorkbook exportPartyStat(int configId) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/prList-5.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = null;
        XSSFCell cell = null;

        int positiveCount = 0;
        int expectPositiveMemberCount = 0;
        int actualPositiveMemberCount = 0;

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeanList(configId, PcsConstants.PCS_STAGE_THIRD,
                null, null, null, new RowBounds());
        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsPrPartyBean bean = records.get(i);

            positiveCount += NumberUtils.trimToZero(bean.getPositiveCount());
            expectPositiveMemberCount += NumberUtils.trimToZero(bean.getExpectPositiveMemberCount());
            actualPositiveMemberCount += NumberUtils.trimToZero(bean.getActualPositiveMemberCount());

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 分党委名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getName());

            // 正式党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getPositiveCount()));

            // 应参会正式党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getExpectPositiveMemberCount()));

            // 实参会正式党员数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getActualPositiveMemberCount()));

            // 参与比率
            cell = row.getCell(column++);
            cell.setCellValue(percent(bean.getActualPositiveMemberCount(), bean.getExpectPositiveMemberCount()));
        }

        // 合计
        row = sheet.getRow(startRow++);
        int column = 2;


        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(positiveCount);

        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(expectPositiveMemberCount);

        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(actualPositiveMemberCount);

        // 参与比率
        cell = row.getCell(column++);
        cell.setCellValue(percent(actualPositiveMemberCount, expectPositiveMemberCount));

        row = sheet.getRow(startRow);
        cell = row.getCell(0);
        cell.setCellValue("日期：  " + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        return wb;
    }
}
