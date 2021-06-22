package service.pm;

import bean.PmStat;
import controller.global.OpException;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pm.Pm3Meeting;
import domain.pm.Pm3MeetingExample;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import persistence.pm.common.PmMeetingStat;
import service.common.FreemarkerService;
import service.party.PartyAdminService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.Pm3Constants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Pm3MeetingService extends PmBaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private PartyAdminService partyAdminService;
    @Autowired
    private FreemarkerService freemarkerService;
    @Transactional
    public void insertSelective(Pm3Meeting record){

        record.setStatus(Pm3Constants.PM_3_STATUS_SAVE);
        record.setIsBack(false);
        record.setIsDelete(false);
        pm3MeetingMapper.insertSelective(record);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "添加组织生活", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                null);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        Pm3Meeting record = new Pm3Meeting();
        record.setIsDelete(true);

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pm3MeetingMapper.updateByExampleSelective(record, example);

        for (Integer id : ids) {
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    "删除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    null);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(Pm3Meeting record){

        pm3MeetingMapper.updateByPrimaryKeySelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "修改组织生活", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                null);
    }

    public Map<Integer, Pm3Meeting> findAll() {

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Pm3Meeting> records = pm3MeetingMapper.selectByExample(example);
        Map<Integer, Pm3Meeting> map = new LinkedHashMap<>();
        for (Pm3Meeting record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void submit(Integer id) {

        if (id == null) return;

        boolean owPermits = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PMVIEWALL);

        Pm3Meeting record = pm3MeetingMapper.selectByPrimaryKey(id);
        if(!owPermits && !PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }
        if (PartyHelper.isDirectBranch(record.getPartyId())){
            record.setStatus(Pm3Constants.PM_3_STATUS_OW);
        }else {
            record.setStatus(Pm3Constants.PM_3_STATUS_PARTY);
        }
        pm3MeetingMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "报送", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                null);

    }

    @Transactional
    public void check(Integer[] ids, boolean check, String checkOpinion) {

        int currentUserId = ShiroHelper.getCurrentUserId();
        boolean owPermits = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PMVIEWALL);

        for (Integer id : ids) {

            Pm3Meeting pm3Meeting = pm3MeetingMapper.selectByPrimaryKey(id);
            Branch branch = pm3Meeting.getBranch();
            int partyId = pm3Meeting.getPartyId();

            if(!owPermits && !partyAdminService.adminParty(currentUserId, partyId)){
                throw new OpException("权限不足");
            }
            boolean isStaff = PartyHelper.isDirectBranch(partyId)
                    || BooleanUtils.isTrue(branch.getIsStaff()); // 直属党支部默认为教工党支部

            Pm3Meeting record = new Pm3Meeting();
            record.setId(id);
            if (check){
                if (owPermits){
                    // 组织部或学工部审核
                    record.setStatus(Pm3Constants.PM_3_STATUS_PASS);
                }else{
                    // 分党委审核
                    record.setStatus(isStaff?Pm3Constants.PM_3_STATUS_OW:Pm3Constants.PM_3_STATUS_STU);
                }
                record.setIsBack(false);
            }else {

                // 审核不通过均返回待审核
                record.setStatus(Pm3Constants.PM_3_STATUS_SAVE);
                record.setIsBack(true);
            }

            record.setCheckOpinion(checkOpinion);

            pm3MeetingMapper.updateByPrimaryKeySelective(record);
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    record.getIsBack()?"退回":"审批通过",
                    record.getIsBack()?SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK
                            :SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS,
                    checkOpinion);
        }
    }

    public List<MemberView> getMemberList(String absents) {
        if (StringUtils.isBlank(absents)) return null;

        String[] absent = absents.split(",");
        List<Integer> attendUserIdList= new ArrayList();
        for(String a:absent) {
            attendUserIdList.add(Integer.valueOf(a.trim()));
        }
        MemberViewExample example = new MemberViewExample();
        example.createCriteria().andUserIdIn(attendUserIdList);
        return  memberViewMapper.selectByExample(example);

    }

    public void download(int id, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        Pm3Meeting pm3Meeting = pm3MeetingMapper.selectByPrimaryKey(id);

        boolean odPermits = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PMVIEWALL);
        if(!odPermits && !PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),pm3Meeting.getPartyId(), pm3Meeting.getBranchId())){
            throw new UnauthorizedException();
        }

        Party party = pm3Meeting.getParty();
        Branch branch = null;
        if (!PartyHelper.isDirectBranch(party.getId())){
            branch = pm3Meeting.getBranch();
        }
        //输出文件
        String filename = party.getName() + (branch!=null?branch.getName():"") + pm3Meeting.getYear() + "年"
                + pm3Meeting.getMonth() + "月支部组织生活记录" + ".doc";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
        response.setContentType("application/msword;charset=UTF-8");

        processPm3(id, response.getWriter());
    }

    @Transactional
    public void batchBack(Integer[] ids) {

        Pm3Meeting record = new Pm3Meeting();
        record.setStatus(Pm3Constants.PM_3_STATUS_SAVE);
        record.setIsBack(true);

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pm3MeetingMapper.updateByExampleSelective(record, example);

        for (Integer id : ids) {
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    "退回", SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK,
                    null);
        }
    }

    // 输出支部书记信息采集表
    public void processPm3(int id, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMapOfPm3(id);
        Pm3Meeting pm3Meeting = pm3MeetingMapper.selectByPrimaryKey(id);
        if (pm3Meeting.getType()==Pm3Constants.PM_3_BRANCH_COMMITTEE){
            freemarkerService.process("/pm/pm3Meeting_1.ftl", dataMap, out);
        }else {
            freemarkerService.process("/pm/pm3Meeting_2.ftl", dataMap, out);
        }
    }

    private Map<String, Object> getDataMapOfPm3(int id) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();
        Pm3Meeting bean = pm3MeetingMapper.selectByPrimaryKey(id);
        dataMap.put("type", Pm3Constants.PM_3_BRANCH_MAP.get(bean.getType()));
        dataMap.put("partyName", bean.getParty().getName());
        dataMap.put("branchName", bean.getBranch().getName());
        dataMap.put("name", bean.getName());
        dataMap.put("year", bean.getYear());
        dataMap.put("month", bean.getMonth());
        dataMap.put("time", DateUtils.formatDate(bean.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM));
        dataMap.put("address", bean.getAddress());
        dataMap.put("presenter", bean.getPresenterUser().getRealname());
        dataMap.put("recorder", bean.getRecorderUser().getRealname());
        dataMap.put("dueNum", bean.getDueNum());
        dataMap.put("attendNum", bean.getAttendNum());
        String absentName = "";
        if (StringUtils.isNotBlank(bean.getAbsents())){
            List<String> realnameList = bean.getAbsentList().stream().map(MemberView::getRealname).collect(Collectors.toList());
            absentName = StringUtils.join(realnameList, ",");
        }

        String absentReason = bean.getAbsentReason();
        if (StringUtils.isBlank(absentName)){
            dataMap.put("absent", absentReason);
        }else {
            absentReason = StringUtils.isNotBlank(absentReason)?("(" + absentReason + ")"):"";
            dataMap.put("absent", absentName + absentReason);
        }

        dataMap.put("remark", bean.getRemark());
        dataMap.put("content", freemarkerService.genTextareaSegment(bean.getContent(), "/common/editor2.ftl"));

        return dataMap;
    }

    //需要通知的支部
    public List<Integer> getBranchIds(Date meetingMonth){

        if (meetingMonth==null) return null;

        List<Integer> branchIdList = new ArrayList<>();

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andYearEqualTo(DateUtils.getYear(meetingMonth)).andMonthEqualTo(DateUtils.getMonth(meetingMonth))
                .andBranchIdIsNotNull().andStatusNotEqualTo(Pm3Constants.PM_3_STATUS_SAVE).andIsDeleteEqualTo(false);
        List<Pm3Meeting> records = pm3MeetingMapper.selectByExample(example);

        if (records != null && records.size() > 0) {
            List<Integer> _branchIdList = records.stream().map(Pm3Meeting::getBranchId).collect(Collectors.toList());
            BranchExample branchExample = new BranchExample();
            branchExample.createCriteria().andIdNotIn(_branchIdList).andIsDeletedEqualTo(false);
            List<Branch> branchList = branchMapper.selectByExample(branchExample);
            branchIdList = branchList.stream().map(Branch::getId).collect(Collectors.toList());
        }
        return branchIdList;
    }

    //需要通知的直属党支部
    public List<Integer> getPartyIds(Date meetingMonth){

        if (meetingMonth==null) return null;

        List<Integer> partyIdList = new ArrayList<>();

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andYearEqualTo(DateUtils.getYear(meetingMonth)).andMonthEqualTo(DateUtils.getMonth(meetingMonth))
                .andBranchIdIsNull().andStatusNotEqualTo(Pm3Constants.PM_3_STATUS_SAVE).andIsDeleteEqualTo(false);
        List<Pm3Meeting> records = pm3MeetingMapper.selectByExample(example);

        if (records != null && records.size() > 0) {
            List<Integer> _partyIdList = records.stream().map(Pm3Meeting::getPartyId).collect(Collectors.toList());
            PartyExample partyExample = new PartyExample();
            partyExample.createCriteria().andClassIdEqualTo(CmTag.getMetaTypeByCode("mt_direct_branch").getId()).andIsDeletedEqualTo(false)
                    .andIdNotIn(_partyIdList);
            List<Party> partyList = partyMapper.selectByExample(partyExample);
            partyIdList = partyList.stream().map(Party::getId).collect(Collectors.toList());
        }
        return partyIdList;
    }

    public XSSFWorkbook annualStatExport(ModelMap modelMap) {

        InputStream is = null;
        try {
            int startRow = 3;
            is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pm/annual_stat.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);

            XSSFRow row = sheet.getRow(3);
            XSSFCell cell = row.getCell(0);

            XSSFCellStyle style2 = wb.createCellStyle();// 设置这些样式
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            XSSFFont font2 = wb.createFont();// 生成一个字体
            font2.setFontHeightInPoints((short) 11);
            font2.setFontName("宋体");
            style2.setFont(font2);// 把字体应用到当前的样式

            List<PmStat> pmStatList = (List<PmStat>) modelMap.get("pmStatList");
            for (PmStat record : pmStatList) {
                int startCol = 0;
                row = sheet.createRow(startRow);
                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(record.getPartyName());

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(record.getBranchName());

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(record.getType());

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcJau()==null?"":record.getBcJau()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcFeb()==null?"":record.getBcFeb()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcMar()==null?"":record.getBcMar()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcApr()==null?"":record.getBcApr()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcMay()==null?"":record.getBcMay()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcJun()==null?"":record.getBcJun()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcJul()==null?"":record.getBcJul()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcAug()==null?"":record.getBcAug()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcSept()==null?"":record.getBcSept()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcOct()==null?"":record.getBcOct()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcNov()==null?"":record.getBcNov()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcDec()==null?"":record.getBcDec()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcHoldTime()==null?"":record.getBcHoldTime()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcFinishPercent()==null?"":record.getBcFinishPercent()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaJau()==null?"":record.getGaJau()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaFeb()==null?"":record.getGaFeb()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaMar()==null?"":record.getGaMar()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaApr()==null?"":record.getGaApr()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaMay()==null?"":record.getGaMay()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaJun()==null?"":record.getGaJun()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaJul()==null?"":record.getGaJul()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaAug()==null?"":record.getGaAug()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaSept()==null?"":record.getGaSept()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getBcOct()==null?"":record.getBcOct()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaNov()==null?"":record.getGaNov()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaDec()==null?"":record.getGaDec()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaHoldTime()==null?"":record.getGaHoldTime()));

                cell = row.createCell(startCol++);
                cell.setCellStyle(style2);
                cell.setCellValue(String.valueOf(record.getGaFinishPercent()==null?"":record.getGaFinishPercent()));

                startRow++;
            }

            return wb;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getstatData(ModelMap modelMap) {

        PartyExample example = new PartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        List<Party> partyList = partyMapper.selectByExample(example);

        List<PmStat> pmStatList = new ArrayList<>();
        List<PmMeetingStat> records = iPmMapper.selectPm3MeetingStat((byte) 1,DateUtils.getCurrentYear(),null,null,null,null,Pm3Constants.PM_3_STATUS_PASS, false, null, null);
        for (Party party : partyList) {
            Integer partyId = party.getId();

            if (!PartyHelper.isDirectBranch(partyId)) {
                BranchExample branchExample = new BranchExample();
                branchExample.createCriteria().andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
                List<Branch> branchList = branchMapper.selectByExample(branchExample);
                for (Branch branch : branchList) {
                    PmStat pmStat = new PmStat();
                    pmStat.setPartyId(partyId);
                    pmStat.setPartyName(party.getName());
                    for (PmMeetingStat record : records) {
                        if (record.getBranchId()==null||!record.getBranchId().equals(branch.getId())) continue;
                        pmStat.setBranchId(branch.getId());
                        pmStat.setBranchName(branch.getName());
                        if (StringUtils.isNotBlank(branch.getTypes())) {
                            pmStat.setType(CmTag.getMetaTypeName(Integer.valueOf(branch.getTypes())));
                        }
                        dealPmStat(pmStat, record);
                    }
                    if (pmStat.getBranchId()==null)continue;
                    pmStatList.add(pmStat);
                }
            }else {
                PmStat pmStat = new PmStat();
                pmStat.setPartyId(partyId);
                pmStat.setPartyName(party.getName());
                for (PmMeetingStat record : records) {
                    if (!record.getPartyId().equals(partyId)) continue;
                    pmStat.setType(CmTag.getMetaTypeName(party.getBranchType()));
                    dealPmStat(pmStat, record);
                }
                pmStatList.add(pmStat);
            }
        }
        modelMap.put("pmStatList", pmStatList);
    }

    // 处理每个月份的会议次数
    public void dealPmStat(PmStat pmStat, PmMeetingStat record){
        Integer bc = record.getCount1();// 支委会次数
        Integer ga = record.getCount2()+record.getCount3()+record.getCount4()+record.getCount5()+record.getCount6();//党员集体活动次数
        switch (record.getMonth()){
            case 1:
                pmStat.setBcJau(bc);
                pmStat.setGaJau(ga);
                break;
            case 2:
                pmStat.setBcFeb(bc);
                pmStat.setGaFeb(ga);
                break;
            case 3:
                pmStat.setBcMar(bc);
                pmStat.setGaMar(ga);
                break;
            case 4:
                pmStat.setBcApr(bc);
                pmStat.setGaApr(ga);
                break;
            case 5:
                pmStat.setBcMay(bc);
                pmStat.setGaMay(ga);
                break;
            case 6:
                pmStat.setBcJun(bc);
                pmStat.setGaJun(ga);
                break;
            case 7:
                pmStat.setBcJul(bc);
                pmStat.setGaJul(ga);
                break;
            case 8:
                pmStat.setBcAug(bc);
                pmStat.setGaAug(ga);
                break;
            case 9:
                pmStat.setBcSept(bc);
                pmStat.setGaSept(ga);
                break;
            case 10:
                pmStat.setBcOct(bc);
                pmStat.setGaOct(ga);
                break;
            case 11:
                pmStat.setBcNov(bc);
                pmStat.setGaNov(ga);
                break;
            case 12:
                pmStat.setBcDec(bc);
                pmStat.setGaDec(ga);
                break;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal num2 = new BigDecimal(DateUtils.getMonth(new Date()));
        if (bc > 0) {
            pmStat.setBcHoldTime(pmStat.getBcHoldTime()==null?1: pmStat.getBcHoldTime()+1);
            BigDecimal num1 = new BigDecimal(pmStat.getBcHoldTime());
            pmStat.setBcFinishPercent(df.format((num1.divide(num2, 10, RoundingMode.HALF_UP).doubleValue() * 100)) + "%");
        }
        if (ga > 0) {
            pmStat.setGaHoldTime(pmStat.getGaHoldTime()==null?1:pmStat.getGaHoldTime()+1);
            BigDecimal num1 = new BigDecimal(pmStat.getGaHoldTime());
            pmStat.setGaFinishPercent(df.format((num1.divide(num2, 10, RoundingMode.HALF_UP).doubleValue() * 100)) + "%");
        }
    }
}
