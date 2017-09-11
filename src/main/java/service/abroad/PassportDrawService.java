package service.abroad;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.abroad.ApplySelf;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.abroad.PassportDrawFile;
import domain.abroad.PassportDrawFileExample;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.ShortMsgService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PassportDrawService extends BaseMapper {

    @Autowired
    protected ShortMsgService shortMsgService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
    自动发送，发送时间为上午10点，每三天发一次，直到将证件交回。
	比如，应交组织部日期为2016年9月1日，那么从第二天9月2日开始发，每三天发一次，直到交回证件为止。
	 */
    public void sendReturnMsg() {

        logger.debug("====领取证件之后催交证件短信通知...start====");
        int count = 0;
        Date today = new Date();
        // 查找已领取证件，但还未归还（该证件昨天应归还）的记录
        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andIsDeletedEqualTo(false).
                andDrawStatusEqualTo(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW)
                .andUsePassportNotEqualTo(SystemConstants.PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN) // 拒不交回不需要短信提醒
                .andReturnDateLessThan(today);
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
        for (PassportDraw passportDraw : passportDraws) {

            Passport passport = passportDraw.getPassport();
            if (passport.getType() == SystemConstants.PASSPORT_TYPE_KEEP
                    || (passport.getType() == SystemConstants.PASSPORT_TYPE_CANCEL
                    && passport.getCancelConfirm() == false)) { // 集中管理的 或 未确认的取消集中管理证件，才需要短信提醒

                Date returnDate = passportDraw.getReturnDate(); // 应归还时间
                Period p = new Period(new DateTime(returnDate), new DateTime(today), PeriodType.days());
                int days = p.getDays();
                if ((days - 1) % 3 == 0) {  // 间隔第1,4,7...天应发短信提醒

                    ShortMsgBean shortMsgBean = shortMsgService.getShortMsgBean(null, null, "passportDrawReturn", passportDraw.getId());
                    try {
                        boolean ret = shortMsgService.send(shortMsgBean, "127.0.0.1");
                        logger.info(String.format("系统发送短信[%s]：%s", ret ? "成功" : "失败", shortMsgBean.getContent()));
                        if (ret) count++;
                    } catch (Exception ex) {
                        logger.error("领取证件之后催交证件短信失败", ex);
                    }
                }
            }
        }
        logger.info(String.format("领取证件之后催交证件短信通知，发送成功%s/%s条", count, passportDraws.size()));

        logger.debug("====领取证件之后催交证件短信通知...end====");
    }

    // 拒绝归还证件借出记录
    public PassportDraw getRefuseReturnPassportDraw(int passportId) {

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andPassportIdEqualTo(passportId).andIsDeletedEqualTo(false)
                .andDrawStatusEqualTo(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW)
                .andUsePassportEqualTo(SystemConstants.PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN);
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);

        Assert.isTrue(passportDraws.size()<=1, "证件拒绝归还状态异常");

        return (passportDraws.size()==1)?passportDraws.get(0):null;
    }

    public List<PassportDrawFile> getPassportDrawFiles(int drawId) {

        PassportDrawFileExample example = new PassportDrawFileExample();
        example.createCriteria().andDrawIdEqualTo(drawId);
        return passportDrawFileMapper.selectByExample(example);
    }

    @Transactional
    public int insertSelective(PassportDraw record) {

        record.setIsDeleted(false);
        record.setApplyDate(new Date());
        record.setCreateTime(new Date());
        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_INIT);
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
        record.setJobCertify(false);
        return passportDrawMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        passportDrawMapper.deleteByPrimaryKey(id);
    }

    // 删除（默认逻辑删除），真删除只有在逻辑删除之后
    @Transactional
    public void batchDel(Integer[] ids, boolean isReal) {

        if (ids == null || ids.length == 0) return;

        if (isReal) { // 删除已经[逻辑删除]，且未审批的记录
            for (Integer id : ids) {
                PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
                if (passportDraw.getStatus() == SystemConstants.PASSPORT_DRAW_STATUS_INIT
                        && passportDraw.getIsDeleted()) {

                    PassportDrawFileExample example = new PassportDrawFileExample();
                    example.createCriteria().andDrawIdEqualTo(id);
                    passportDrawFileMapper.deleteByExample(example); // 先删除相关材料

                    passportDrawMapper.deleteByPrimaryKey(id);
                } else {
                    throw new OpException("该记录已经审批，不可以删除");
                }
            }
        } else {
            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));

            PassportDraw record = new PassportDraw();
            record.setIsDeleted(true);
            passportDrawMapper.updateByExampleSelective(record, example);
        }
    }

    @Transactional
    public void batchUnDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PassportDraw record = new PassportDraw();
        record.setIsDeleted(false);
        passportDrawMapper.updateByExampleSelective(record, example);
    }


    // 领取证件
    @Transactional
    public void drawPassport(PassportDraw record) {

        updateByPrimaryKeySelective(record);

        // 将证件标记为已借出
        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        if (passport.getIsLent()) {
            throw new OpException("该证件已经借出");
        }
        Passport _record = new Passport();
        _record.setId(passport.getId());
        _record.setIsLent(true);
        passportMapper.updateByPrimaryKeySelective(_record);
    }

    // 归还证件
    @Transactional
    public void returnPassport(PassportDraw record) {

        updateByPrimaryKeySelective(record);

        if(record.getUsePassport() != SystemConstants.PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN) {
            // 将证件标记为未借出
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
            Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
            if (!passport.getIsLent()) {
                throw new OpException("该证件未借出");
            }
            Passport _record = new Passport();
            _record.setId(passport.getId());
            _record.setIsLent(false);
            passportMapper.updateByPrimaryKeySelective(_record);

            // 归还证件后通知本人
            ShortMsgBean shortMsgBean = shortMsgService.getShortMsgBean(ShiroHelper.getCurrentUserId(),
                    null, "passportDrawReturnSuccess", passportDraw.getId());
            shortMsgService.send(shortMsgBean, IpUtils.getRealIp(ContextHelper.getRequest()));
        }
    }

    // 重置归还状态为 “未归还”
    public void resetReturnPassport(int id) {

        iAbroadMapper.resetReturnPassport(id);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportDraw record) {
        return passportDrawMapper.updateByPrimaryKeySelective(record);
    }

    // 使用记录导出
    public void passportDraw_export(byte exportType, PassportDrawExample example, HttpServletResponse response) {

        String type = "因私出国（境）";
        if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_TW) {
            type = "因公赴台、长期因公出国";
        } else if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_OTHER) {
            type = "处理其他事务";
        } else {
            exportType = SystemConstants.PASSPORT_DRAW_TYPE_SELF;
        }

        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
        long rownum = passportDrawMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        int rowNum = 0;
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            XSSFFont font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + "中层干部" + type + "证件使用记录");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 14));
            rowNum++;
        }

        XSSFRow firstRow = (XSSFRow) sheet.createRow(rowNum++);
        String[] titles = null;
        if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_SELF) {
            titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                    "证件号码", "申请日期", "申请编码", "因私出国（境）行程", /*"是否签注",*/
                    "出行时间", "回国时间", "前往国家或地区", "因私出国境事由", "借出日期",
                    "归还日期"};

            sheet.setColumnWidth(0, (short) (35.7 * 50));
            sheet.setColumnWidth(1, (short) (35.7 * 100));
            sheet.setColumnWidth(2, (short) (35.7 * 50));
            sheet.setColumnWidth(3, (short) (35.7 * 250));
            sheet.setColumnWidth(4, (short) (35.7 * 150));

            sheet.setColumnWidth(5, (short) (35.7 * 100));
            sheet.setColumnWidth(6, (short) (35.7 * 100));
            sheet.setColumnWidth(7, (short) (35.7 * 100));
            sheet.setColumnWidth(8, (short) (35.7 * 180));
            //sheet.setColumnWidth(9, (short) (35.7 * 100));

            sheet.setColumnWidth(9, (short) (35.7 * 100));
            sheet.setColumnWidth(10, (short) (35.7 * 100));
            sheet.setColumnWidth(11, (short) (35.7 * 150));
            sheet.setColumnWidth(12, (short) (35.7 * 150));
            sheet.setColumnWidth(13, (short) (35.7 * 100));

            sheet.setColumnWidth(14, (short) (35.7 * 100));

        } else if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_TW) {
            titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                    "证件号码", "申请日期", "申请编码", "申请类型", "出行时间",
                    "回国时间", "出行天数", "因公事由", "费用来源", "是否签注",
                    "借出日期", "归还日期"};

            sheet.setColumnWidth(0, (short) (35.7 * 50));
            sheet.setColumnWidth(1, (short) (35.7 * 100));
            sheet.setColumnWidth(2, (short) (35.7 * 50));
            sheet.setColumnWidth(3, (short) (35.7 * 250));
            sheet.setColumnWidth(4, (short) (35.7 * 150));

            sheet.setColumnWidth(5, (short) (35.7 * 100));
            sheet.setColumnWidth(6, (short) (35.7 * 100));
            sheet.setColumnWidth(7, (short) (35.7 * 100));
            sheet.setColumnWidth(8, (short) (35.7 * 130));
            sheet.setColumnWidth(9, (short) (35.7 * 100));

            sheet.setColumnWidth(10, (short) (35.7 * 100));
            sheet.setColumnWidth(11, (short) (35.7 * 100));
            sheet.setColumnWidth(12, (short) (35.7 * 180));
            sheet.setColumnWidth(13, (short) (35.7 * 180));
            sheet.setColumnWidth(14, (short) (35.7 * 100));

            sheet.setColumnWidth(15, (short) (35.7 * 100));
            sheet.setColumnWidth(16, (short) (35.7 * 100));

        } else if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_OTHER) {
            titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                    "证件号码", "申请日期", "申请编码", "使用时间", "归还时间",
                    "使用天数", "事由", "借出日期", "归还日期"};

            sheet.setColumnWidth(0, (short) (35.7 * 50));
            sheet.setColumnWidth(1, (short) (35.7 * 100));
            sheet.setColumnWidth(2, (short) (35.7 * 50));
            sheet.setColumnWidth(3, (short) (35.7 * 250));
            sheet.setColumnWidth(4, (short) (35.7 * 150));

            sheet.setColumnWidth(5, (short) (35.7 * 100));
            sheet.setColumnWidth(6, (short) (35.7 * 100));
            sheet.setColumnWidth(7, (short) (35.7 * 100));
            sheet.setColumnWidth(8, (short) (35.7 * 100));
            sheet.setColumnWidth(9, (short) (35.7 * 100));

            sheet.setColumnWidth(10, (short) (35.7 * 100));
            sheet.setColumnWidth(11, (short) (35.7 * 150));
            sheet.setColumnWidth(12, (short) (35.7 * 100));
            sheet.setColumnWidth(13, (short) (35.7 * 100));
        }
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));
        }

        MetaType normalPassport = CmTag.getMetaTypeByCode("mt_passport_normal");
        for (int i = 0; i < rownum; i++) {
            PassportDraw passportDraw = passportDraws.get(i);
            CadreView cadre = passportDraw.getCadre();
            SysUserView uv = passportDraw.getUser();
            Passport passport = passportDraw.getPassport();
            ApplySelf applySelf = passportDraw.getApplySelf();
            String xingcheng = "";
            String needSign = "";
            String startDate = "";
            String endDate = "";
            String toCountry = "";
            String reason = passportDraw.getReason();

            if (passportDraw.getType() == SystemConstants.PASSPORT_DRAW_TYPE_SELF) {
                xingcheng = "S" + applySelf.getId();
                if (passport.getClassId().intValue() != normalPassport.getId()) {
                    needSign = BooleanUtils.isTrue(passportDraw.getNeedSign()) ? "是" : "否";
                }
                startDate = DateUtils.formatDate(applySelf.getApplyDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(applySelf.getEndDate(), DateUtils.YYYY_MM_DD);
                toCountry = applySelf.getToCountry();
                reason = applySelf.getReason();
            } else if (passportDraw.getType() == SystemConstants.PASSPORT_DRAW_TYPE_TW) {
                //xingcheng = "T"+passportDraw.getId();
                if (passport.getClassId().intValue() != normalPassport.getId()) {
                    needSign = BooleanUtils.isTrue(passportDraw.getNeedSign()) ? "是" : "否";
                }
                startDate = DateUtils.formatDate(passportDraw.getStartDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD);
                //toCountry="台湾";
            } else if (passportDraw.getType() == SystemConstants.PASSPORT_DRAW_TYPE_OTHER) {
                //xingcheng = "Q"+passportDraw.getId();
                startDate = DateUtils.formatDate(passportDraw.getStartDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD);
            }
            String[] values = null;
            if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_SELF) {
                values = new String[]{
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passport.getPassportClass().getName(),

                        passport.getCode(),
                        DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                        String.format("D%s", passportDraw.getId()),
                        xingcheng,
                        // needSign,

                        startDate,
                        endDate, toCountry, StringUtils.replace(reason, "+++", ","),
                        DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),

                        DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
                };
            } else if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_TW) {
                /*titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                        "证件号码", "申请日期", "申请编码", "申请类型", "出行时间",
                        "回国时间", "出行天数", "因公事由", "费用来源", "是否签注",
                        "借出日期", "归还日期"};*/
                values = new String[]{
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passport.getPassportClass().getName(),

                        passport.getCode(),
                        DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                        String.format("D%s", passportDraw.getId()),
                        SystemConstants.PASSPORT_DRAW_TYPE_MAP.get(passportDraw.getType()),
                        startDate,

                        endDate, DateUtils.getDayCountBetweenDate(passportDraw.getStartDate(), passportDraw.getEndDate()) + "",
                        reason, passportDraw.getCostSource(), needSign,

                        DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
                };
            } else if (exportType == SystemConstants.PASSPORT_DRAW_TYPE_OTHER) {

                /*titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                        "证件号码", "申请日期", "申请编码", "使用时间", "归还时间",
                        "使用天数", "事由",  "借出日期", "归还日期"};*/
                values = new String[]{
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passport.getPassportClass().getName(),

                        passport.getCode(),
                        DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                        String.format("D%s", passportDraw.getId()),
                        startDate,
                        endDate,

                        DateUtils.getDayCountBetweenDate(passportDraw.getStartDate(), passportDraw.getEndDate()) + "",
                        reason,
                        DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
                };
            }

            Row row = sheet.createRow(rowNum++);

            for (int j = 0; j < titles.length; j++) {
                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(ExportHelper.getBodyStyle(wb));
            }
        }

        String fileName = type + "证件使用记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
