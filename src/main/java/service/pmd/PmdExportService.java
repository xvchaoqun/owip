package service.pmd;

import domain.party.Party;
import domain.pmd.*;
import domain.sys.SysUserView;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.pmd.common.PmdExcelReportBean;
import persistence.pmd.common.PmdFeeStatBean;
import service.sys.SysConfigService;
import service.sys.SysUserService;
import sys.constants.PmdConstants;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PmdExportService extends PmdBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected PmdFeeService pmdFeeService;

    public String getSchoolName(){
        return sysConfigService.getSchoolName();
    }

    // 组织部报表
    public XSSFWorkbook reportOw(int pmdMonthId) throws IOException {

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(pmdMonthId);
        if(pmdMonth.getStatus()!= PmdConstants.PMD_MONTH_STATUS_END) return null;

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pmd/report_ow.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        String str = null;

        String schoolName = getSchoolName();
        row = sheet.getRow(0);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("school", schoolName);
        cell.setCellValue(str);

        // 月份
        String month = DateUtils.formatDate(pmdMonth.getPayMonth(), "yyyy年MM月");
        row = sheet.getRow(1);
        cell = row.getCell(1);
        cell.setCellValue(month);

        // 党委数
        {
            PmdPartyExample example = new PmdPartyExample();
            example.createCriteria().andMonthIdEqualTo(pmdMonthId);
            long partyCount = pmdPartyMapper.countByExample(example);
            row = sheet.getRow(2);
            cell = row.getCell(1);
            cell.setCellValue(partyCount);
        }

        // 结算人
        SysUserView uv = sysUserService.findById(pmdMonth.getEndUserId());
        row = sheet.getRow(2);
        cell = row.getCell(4);
        cell.setCellValue(uv.getRealname());

        // 启动时间
        String startTime = DateUtils.formatDate(pmdMonth.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM);
        row = sheet.getRow(3);
        cell = row.getCell(1);
        cell.setCellValue(startTime);

        // 结算时间
        String endTime = DateUtils.formatDate(pmdMonth.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM);
        row = sheet.getRow(3);
        cell = row.getCell(4);
        cell.setCellValue(endTime);

        // 一、本月党员缴费详情

        // 党员总数
        row = sheet.getRow(5);
        cell = row.getCell(1);
        cell.setCellValue(pmdMonth.getMemberCount());

        // 本月按时缴纳党费党员数
        row = sheet.getRow(5);
        cell = row.getCell(3);
        cell.setCellValue(pmdMonth.getFinishMemberCount());

        // 本月线上缴纳党费党员数
        row = sheet.getRow(5);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getOnlineFinishMemberCount());

        // 本月现金缴纳党费党员数
        row = sheet.getRow(6);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getFinishMemberCount() - pmdMonth.getOnlineFinishMemberCount());

        // 本月延迟缴纳党费党员数
        row = sheet.getRow(7);
        cell = row.getCell(3);
        cell.setCellValue(pmdMonth.getDelayMemberCount());

        // 二、本月缴纳党费详情

        // 本月应缴纳党费数
        row = sheet.getRow(9);
        cell = row.getCell(1);
        cell.setCellValue(pmdMonth.getDuePay().toString());

        // 本月按时缴纳党费数
        row = sheet.getRow(9);
        cell = row.getCell(3);
        cell.setCellValue(pmdMonth.getRealPay().toString());

        // 本月线上缴纳党费数
        row = sheet.getRow(9);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getOnlineRealPay().toString());

        // 本月现金缴纳党费数
        row = sheet.getRow(10);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getCashRealPay().toString());

        // 本月延迟缴纳党费数
        row = sheet.getRow(11);
        cell = row.getCell(3);
        cell.setCellValue(pmdMonth.getDelayPay().toString());

        // 三、往月补缴党费详情

        // 按月补缴统计
        BigDecimal fee = BigDecimal.ZERO;
        Map<Byte, PmdFeeStatBean> feeStatMap = pmdFeeService.statPmdFee(null, pmdMonth.getPayMonth());
        for (PmdFeeStatBean statBean : feeStatMap.values()) {
            fee = fee.add(statBean.getAmt());
        }

        // 往月应补缴党费数
        row = sheet.getRow(13);
        cell = row.getCell(1);
        cell.setCellValue(pmdMonth.getHistoryDelayPay().add(fee).toString());

        // 往月实补缴党费数
        row = sheet.getRow(13);
        cell = row.getCell(3);
        cell.setCellValue(pmdMonth.getRealDelayPay().add(fee).toString());

        // 往月线上补缴党费数
        row = sheet.getRow(13);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getOnlineRealDelayPay().add(fee).toString());

        // 往月现金缴纳党费数
        row = sheet.getRow(14);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getCashRealDelayPay().toString());

        // 四、线上缴纳党费详情

        // 线上缴纳党费总额
        row = sheet.getRow(16);
        cell = row.getCell(1);
        cell.setCellValue((pmdMonth.getOnlineRealPay().add(pmdMonth.getOnlineRealDelayPay())).toString());

        // 本月线上缴纳党费数
        row = sheet.getRow(16);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getOnlineRealPay().toString());

        // 往月线上补缴党费数
        row = sheet.getRow(17);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getOnlineRealDelayPay().toString());


        // 五、现金缴纳党费详情

        // 现金缴纳党费总额
        row = sheet.getRow(19);
        cell = row.getCell(1);
        cell.setCellValue((pmdMonth.getCashRealPay().add(pmdMonth.getCashRealDelayPay())).toString());

        // 本月现金缴纳党费数
        row = sheet.getRow(19);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getCashRealPay().toString());

        // 往月现金缴纳党费数
        row = sheet.getRow(20);
        cell = row.getCell(5);
        cell.setCellValue(pmdMonth.getCashRealDelayPay().toString());

        row = sheet.getRow(21);
        cell = row.getCell(4);
        str = cell.getStringCellValue().replace("school", schoolName);
        cell.setCellValue(str);

        return wb;
    }

    // 单个分党委报表
    public XSSFWorkbook reportParty(int pmdPartyId) throws IOException {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(pmdPartyId);
        // 报送后允许导出
        if(!pmdParty.getHasReport()) return null;

        int monthId = pmdParty.getMonthId();
        int partyId = pmdParty.getPartyId();
        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        String month = DateUtils.formatDate(pmdMonth.getPayMonth(), "yyyy年MM月");

        Party party = partyMapper.selectByPrimaryKey(partyId);
        String partyName = party.getName();

        // 按月补缴统计
        BigDecimal fee = BigDecimal.ZERO;
        Map<Byte, PmdFeeStatBean> feeStatMap = pmdFeeService.statPmdFee(partyId, pmdMonth.getPayMonth());
        PmdFeeStatBean teacherFee = feeStatMap.get(PmdConstants.PMD_USER_TYPE_TEACHER);
        PmdFeeStatBean retireFee = feeStatMap.get(PmdConstants.PMD_USER_TYPE_RETIRE);
        PmdFeeStatBean studentFee = feeStatMap.get(PmdConstants.PMD_USER_TYPE_STU);
        int teacherFeeCount = 0;
        int retireFeeCount = 0;
        int studentFeeCount = 0;
        if(teacherFee!=null){
            fee = fee.add(teacherFee.getAmt());
            teacherFeeCount = teacherFee.getNum();
        }
        if(retireFee!=null){
            fee = fee.add(retireFee.getAmt());
            retireFeeCount = retireFee.getNum();
        }
        if(studentFee!=null){
            fee = fee.add(studentFee.getAmt());
            studentFeeCount = studentFee.getNum();
        }
        int totalCount = teacherFeeCount+retireFeeCount+studentFeeCount;

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pmd/report_party.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        String str = null;

        row = sheet.getRow(0);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("month", month);
        cell.setCellValue(str);
        cell = row.getCell(7);
        str = cell.getStringCellValue().replace("month", month);
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("party", partyName);
        cell.setCellValue(str);
        cell = row.getCell(7);
        str = cell.getStringCellValue().replace("party", partyName);
        cell.setCellValue(str);

        int rowCount = 4; // 插入总行数
        int startRow = 3; // 起始插入行（从0开始计数）

        // 填充数据
        for (int i = 0; i < rowCount; i++) {

            PmdExcelReportBean bean = iPmdMapper.getPmdExcelReportBean(monthId, partyId, i);
            int column = 2;

            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(bean.getTotal() + totalCount);
            cell = row.getCell(column++);
            cell.setCellValue(bean.getZj()+bean.getGl()+bean.getGq()+bean.getXp()+bean.getXszl()
                    +bean.getOther() + teacherFeeCount);
            cell = row.getCell(column++);
            cell.setCellValue(bean.getLt() + retireFeeCount);
            cell = row.getCell(column++);
            cell.setCellValue(bean.getBdx()+bean.getDx() + studentFeeCount);

            column += 3;
            cell = row.getCell(column++);
            cell.setCellValue(bean.getTotal());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getZj()+bean.getGl()+bean.getGq()+bean.getXp()+bean.getXszl()
                    +bean.getOther());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getLt());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getBdx()+bean.getDx());
        }

        startRow = 9;
        row = sheet.getRow(startRow++);
        BigDecimal _onlineRealPay = pmdParty.getOnlineRealPay().add(pmdParty.getOnlineRealDelayPay()).add(fee);
        cell = row.getCell(2);
        cell.setCellValue(_onlineRealPay.toString());
        cell = row.getCell(9);
        cell.setCellValue(_onlineRealPay.toString());

        cell = row.getCell(5);
        cell.setCellValue(pmdParty.getOnlineRealPay().toString());
        cell = row.getCell(12);
        cell.setCellValue(pmdParty.getOnlineRealPay().toString());

        String onlineRealDelayPay = pmdParty.getOnlineRealDelayPay().add(fee).toString();
        row = sheet.getRow(startRow++);
        cell = row.getCell(5);
        cell.setCellValue(onlineRealDelayPay);
        cell = row.getCell(12);
        cell.setCellValue(onlineRealDelayPay);


        String delayPay = pmdParty.getDelayPay().toString();
        row = sheet.getRow(startRow++);
        cell = row.getCell(2);
        cell.setCellValue(delayPay);
        cell = row.getCell(9);
        cell.setCellValue(delayPay);

        String schoolName = getSchoolName();
        row = sheet.getRow(14);
        cell = row.getCell(3);
        str = cell.getStringCellValue().replace("school", schoolName);
        cell.setCellValue(str);
        cell = row.getCell(10);
        str = cell.getStringCellValue().replace("school", schoolName);
        cell.setCellValue(str);

        return wb;
    }

    // 每月分党委报表
    public XSSFWorkbook reportParties(int monthId, boolean isDetail) throws IOException {

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        if (pmdMonth.getStatus() != PmdConstants.PMD_MONTH_STATUS_END) return null;

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pmd/report_parties"
                +(isDetail?"_detail":"")+".xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        String str = null;

        String schoolName = getSchoolName();
        row = sheet.getRow(0);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("school", schoolName);
        cell.setCellValue(str);

        // 月份
        String month = DateUtils.formatDate(pmdMonth.getPayMonth(), "yyyy年MM月");
        row = sheet.getRow(1);
        cell = row.getCell(0);
        cell.setCellValue(month);


        PmdPartyViewExample example = new PmdPartyViewExample();
        example.createCriteria().andMonthIdEqualTo(monthId);
        example.setOrderByClause("sort_order desc");
        List<PmdPartyView> pmdPartyViews = pmdPartyViewMapper.selectByExample(example);
        int rowCount = pmdPartyViews.size();
        int startRow = isDetail?4:3;
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);

        BigDecimal realOnlinePayTotal = BigDecimal.ZERO;

        BigDecimal _onlineRealPayTotal = BigDecimal.ZERO;
        BigDecimal _cashRealPayTotal = BigDecimal.ZERO;
        BigDecimal duePayTotal = BigDecimal.ZERO;
        int finishMemberCountTotal = 0;

        BigDecimal onlineRealPayTotal = BigDecimal.ZERO;
        BigDecimal cashRealPayTotal = BigDecimal.ZERO;

        int delayMemberCountTotal = 0;
        BigDecimal delayPayTotal = BigDecimal.ZERO;
        BigDecimal historyDelayPayTotal = BigDecimal.ZERO;
        BigDecimal onlineRealDelayPayTotal = BigDecimal.ZERO;
        BigDecimal cashRealDelayPayTotal = BigDecimal.ZERO;

        for (int i = 0; i < rowCount; i++) {

            PmdPartyView bean = pmdPartyViews.get(i);

            // 按月补缴统计
            BigDecimal fee = BigDecimal.ZERO;
            Map<Byte, PmdFeeStatBean> feeStatMap = pmdFeeService.statPmdFee(bean.getPartyId(), pmdMonth.getPayMonth());
            for (PmdFeeStatBean statBean : feeStatMap.values()) {
                fee = fee.add(statBean.getAmt());
            }

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 党委名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getPartyName());

            if(!isDetail) {
                // 线上缴纳党费总额
                BigDecimal onlineRealPay = bean.getOnlineRealPay().add(bean.getOnlineRealDelayPay()).add(fee);
                cell = row.getCell(column++);
                cell.setCellValue(onlineRealPay.toString());
                realOnlinePayTotal = realOnlinePayTotal.add(onlineRealPay);
            }else{

                // 线上缴纳党费总数
                BigDecimal _onlineRealPay = bean.getOnlineRealPay().add(bean.getOnlineRealDelayPay()).add(fee);
                cell = row.getCell(column++);
                cell.setCellValue(_onlineRealPay.toString());
                _onlineRealPayTotal = _onlineRealPayTotal.add(_onlineRealPay);

                // 现金缴纳党费总数
                BigDecimal _cashRealPay = bean.getCashRealPay().add(bean.getCashRealDelayPay());
                cell = row.getCell(column++);
                cell.setCellValue(_cashRealPay.toString());
                _cashRealPayTotal = _cashRealPayTotal.add(_cashRealPay);

                //  ↓↓ 本月 ↓↓
                // 应缴纳党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getDuePay().toString());
                duePayTotal = duePayTotal.add(bean.getDuePay());

                // 按时缴纳党费党员数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getFinishMemberCount());
                finishMemberCountTotal += bean.getFinishMemberCount();

                // 线上缴纳党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getOnlineRealPay().toString());
                onlineRealPayTotal = onlineRealPayTotal.add(bean.getOnlineRealPay());

                // 现金缴纳党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getCashRealPay().toString());
                cashRealPayTotal = cashRealPayTotal.add(bean.getCashRealPay());

                // 延迟缴纳党费党员数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getDelayMemberCount());
                delayMemberCountTotal += bean.getDelayMemberCount();

                // 延迟缴纳党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getDelayPay().toString());
                delayPayTotal = delayPayTotal.add(bean.getDelayPay());

                //  ↓↓ 往月 ↓↓

                // 应补缴党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getHistoryDelayPay().add(fee).toString());
                historyDelayPayTotal = historyDelayPayTotal.add(bean.getHistoryDelayPay().add(fee));

                // 线上补缴党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getOnlineRealDelayPay().add(fee).toString());
                onlineRealDelayPayTotal = onlineRealDelayPayTotal.add(bean.getOnlineRealDelayPay().add(fee));

                // 现金缴纳党费数
                cell = row.getCell(column++);
                cell.setCellValue(bean.getCashRealDelayPay().toString());
                cashRealDelayPayTotal = cashRealDelayPayTotal.add(bean.getCashRealDelayPay());
            }
        }

        // 合计
        int column = 2;
        row = sheet.getRow(startRow++);
        if(!isDetail) {
            cell = row.getCell(column);
            cell.setCellValue(realOnlinePayTotal.toString());
        }else{

            cell = row.getCell(column++);
            cell.setCellValue(_onlineRealPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(_cashRealPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(duePayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(finishMemberCountTotal);

            cell = row.getCell(column++);
            cell.setCellValue(onlineRealPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(cashRealPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(delayMemberCountTotal);

            cell = row.getCell(column++);
            cell.setCellValue(delayPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(historyDelayPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(onlineRealDelayPayTotal.toString());

            cell = row.getCell(column++);
            cell.setCellValue(cashRealDelayPayTotal.toString());
        }

        row = sheet.getRow(startRow);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("school", schoolName);
        cell.setCellValue(str);

        //sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow, 0, startRow, !isDetail?2:12));
        return wb;
    }
}
