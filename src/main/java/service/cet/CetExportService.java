package service.cet;

import domain.cadre.CadreView;
import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;
import persistence.cet.common.TrainRecord;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.sys.SysUserService;
import sys.constants.CetConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@Service
public class CetExportService extends CetBaseMapper {
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CetProjectObjService cetProjectObjService;
    @Autowired
    private CetProjectPlanService cetProjectPlanService;
    @Autowired
    private CetTraineeCourseService cetTraineeCourseService;
    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;
    @Autowired
    private CetAnnualObjService cetAnnualObjService;
    
    /**
     * 学时情况.xlsx
     */
    public void exportFinishPeriod(int projectId, HttpServletResponse response) throws IOException {
        
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/finish_period.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        String projectName = cetProject.getName();
        
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("name", projectName);
        cell.setCellValue(str);
        
        Map<Integer, CetProjectPlan> projectPlanMap = cetProjectPlanService.findAll(projectId);
        row = sheet.getRow(1);
        int column = 5;
        for (CetProjectPlan cetProjectPlan : projectPlanMap.values()) {
            
            cell = row.getCell(column++);
            cell.setCellValue(CetConstants.CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.getType()));
        }
        
        List<CetProjectObj> cetProjectObjs = cetProjectObjService.cetProjectObjs(projectId);
        
        int startRow = 2;
        int rowCount = cetProjectObjs.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {
            
            CetProjectObj cetProjectObj = cetProjectObjs.get(i);
            SysUserView uv = sysUserService.findById(cetProjectObj.getUserId());
            CadreView cv = cadreService.dbFindByUserId(cetProjectObj.getUserId());
            
            column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);
            
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(uv.getRealname());
            
            // 工号
            cell = row.getCell(column++);
            cell.setCellValue(uv.getCode());
            
            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(cv == null ? "" : StringUtils.trimToEmpty(cv.getTitle()));
            
            Map<Integer, BigDecimal> periodMap = cetProjectObjService.getObjPlanFinishPeriodMap(cetProjectObj.getId());
            
            cell = row.getCell(column++);
            BigDecimal total = periodMap.get(0);
            cell.setCellValue(total == null ? "" : total.stripTrailingZeros().toPlainString());
            periodMap.remove(0);
            
            for (CetProjectPlan cetProjectPlan : projectPlanMap.values()) {
                cell = row.getCell(column++);
                if (cell != null) {
                    BigDecimal period = periodMap.get(cetProjectPlan.getId());
                    cell.setCellValue(period == null ? "" : period.stripTrailingZeros().toPlainString());
                }
            }
        }
        
        String fileName = String.format("《%s》学时情况", projectName);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
    
    /**
     * 已选课学员统计表.xlsx
     */
    public void exportChosenObjs(int trainCourseId, HttpServletResponse response) throws IOException {
        
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/obj_list.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        
        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        String courseName = HtmlUtils.htmlUnescape(cetTrainCourse.getCetCourse().getName());
        
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", courseName);
        cell.setCellValue(str);
        
        List<Integer> applyUserIds = iCetMapper.applyUserIds(trainCourseId);
        
        int startRow = 2;
        int rowCount = applyUserIds.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {
            
            Integer userId = applyUserIds.get(i);
            SysUserView uv = sysUserService.findById(userId);
            CadreView cv = cadreService.dbFindByUserId(userId);
            
            CetTraineeCourseView teev = cetTraineeCourseService.getCetTraineeCourseView(userId, trainCourseId);
            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);
            
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(uv.getRealname());
            
            // 工号
            cell = row.getCell(column++);
            cell.setCellValue(uv.getCode());
            
            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(cv == null ? "" : StringUtils.trimToEmpty(cv.getTitle()));
            
            // 手机号码
            cell = row.getCell(column++);
            cell.setCellValue(uv.getMobile());
            
            // 选课时间
            cell = row.getCell(column++);
            cell.setCellValue(teev == null ? "" : DateUtils.formatDate(teev.getChooseTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        
        String fileName = String.format("已选课学员统计表[%s]", courseName);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
    
    
    /**
     * 附件7. xxx党校培训情况统计表.xlsx
     */
    public XSSFWorkbook cetTrainStatExport(int trainId) throws IOException {
        
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/train-stat.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);
        
        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        
        row = sheet.getRow(2);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("sn", /*cetTrain.getSn()*/ "");
        cell.setCellValue(str);
        
        row = sheet.getRow(3);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("trainName", cetTrain.getName());
        cell.setCellValue(str);
        
        row = sheet.getRow(4);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("time", DateUtils.formatDate(cetTrain.getStartDate(), DateUtils.YYYY_MM_DD_CHINA)
                        + "——" + DateUtils.formatDate(cetTrain.getEndDate(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);


        /*List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeanList(configId, stage, null, null, null, new RowBounds());
        int startRow = 10;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 分党委名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getName());
        }*/
        
        // 合计
        /*row = sheet.getRow(startRow++);
        int column = 2;

        // 党员总数
        cell = row.getCell(column++);
        cell.setCellValue(memberCount);

        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(positiveCount);
        */
        
        return wb;
    }
    
    
    /**
     * xxxx中层干部年度培训学习情况统计表.xlsx
     */
    public void cetAnnual_exportObjs(int annualId, HttpServletResponse response) throws IOException {
        
        //Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        Integer traineeTypeId = cetAnnual.getTraineeTypeId();
        CetTraineeType cetTraineeType = cetTraineeTypeService.findAll().get(traineeTypeId);
        String typeName = cetTraineeType.getName();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/cet_annual_objs.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school_type", CmTag.getSysConfig().getSchoolName() + typeName);
        cell.setCellValue(str);
        
        row = sheet.getRow(1);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("type", typeName);
        cell.setCellValue(str);
        cell = row.getCell(13);
        str = cell.getStringCellValue()
                .replace("year", cetAnnual.getYear() + "");
        cell.setCellValue(str);
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        CetAnnualObjExample.Criteria criteria = example.createCriteria()
                .andAnnualIdEqualTo(annualId)
                .andIsQuitEqualTo(false);
        example.setOrderByClause("sort_order desc");
        
        List<CetAnnualObj> records = cetAnnualObjMapper.selectByExample(example);
        
        int startRow = 4;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {
            
            CetAnnualObj obj = records.get(i);
            Map<String, BigDecimal> r = obj.getR();
            SysUserView uv = obj.getUser();
            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);
            
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(uv.getRealname());
            
            // 工号
            cell = row.getCell(column++);
            cell.setCellValue(uv.getCode());
            
            // 时任单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(obj.getTitle());
            
            // 行政级别
            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(obj.getAdminLevel()));
            
            // 职务属性
            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(obj.getPostType()));
            
            // 年度学习任务
            BigDecimal period = NumberUtils.trimToZero(obj.getPeriod());
            cell = row.getCell(column++);
            cell.setCellValue(period.toString());
            
            // 已完成学时数
            BigDecimal finishPeriod = NumberUtils.trimToZero(getFinishPeriod(obj, r));
            cell = row.getCell(column++);
            cell.setCellValue(finishPeriod.toString());
    
            // 完成百分比
            String rate = "--";
            if(period.compareTo(BigDecimal.ZERO)>0) {
                BigDecimal divide = finishPeriod.divide(period, 5, RoundingMode.HALF_UP );
                NumberFormat percent = NumberFormat.getPercentInstance();
                percent.setMaximumFractionDigits(1);
                rate = percent.format(divide.doubleValue());
            }
            cell = row.getCell(column++);
            cell.setCellValue(rate);
            
            // 党校专题
            cell = row.getCell(column++);
            cell.setCellValue(getSpecialPeriod(obj, r).toString());
            
            // 党校日常
            cell = row.getCell(column++);
            cell.setCellValue(getDailyPeriod(obj, r).toString());
            
            // 二级党校
            cell = row.getCell(column++);
            cell.setCellValue(getPartyPeriod(obj, r).toString());
            
            // 二级单位
            cell = row.getCell(column++);
            cell.setCellValue(getUnitPeriod(obj, r).toString());
            
            // 上级调训
            cell = row.getCell(column++);
            cell.setCellValue(getUpperPeriod(obj, r).toString());
            
            // 备注
            cell = row.getCell(column++);
            cell.setCellValue(obj.getRemark());
        }
        
       ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName() + typeName + cetAnnual.getYear() +
                "年度培训学习情况统计表.xlsx", response);
    }
    
    // 最终的党校专题学时
    private BigDecimal getSpecialPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r){
        
         BigDecimal period = null;
         if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
                period = NumberUtils.trimToZero(cetAnnualObj.getSpecialPeriod());
         }else {
                period = NumberUtils.trimToZero(r.get("specialPeriod"));
         }
         
         BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxSpecialPeriod());
         return period.compareTo(max)>0?max:period;
    }
    
    // 最终的党校日常学时
    private BigDecimal getDailyPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r){
        
         BigDecimal period = null;
         if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
                period = NumberUtils.trimToZero(cetAnnualObj.getDailyPeriod());
         }else {
                period = NumberUtils.trimToZero(r.get("dailyPeriod"));
         }
         
         BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxDailyPeriod());
         return period.compareTo(max)>0?max:period;
    }
    
    // 最终的二级党委学时
    private BigDecimal getPartyPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r){
        
         BigDecimal period = null;
         if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
                period = NumberUtils.trimToZero(cetAnnualObj.getPartyPeriod());
         }else {
                period = NumberUtils.trimToZero(r.get("partyPeriod"));
         }
         
         BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxPartyPeriod());
         return period.compareTo(max)>0?max:period;
    }
    
    // 最终的二级单位学时
    private BigDecimal getUnitPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r){
        
         BigDecimal period = null;
         if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
                period = NumberUtils.trimToZero(cetAnnualObj.getUnitPeriod());
         }else {
                period = NumberUtils.trimToZero(r.get("unitPeriod"));
         }
         
         BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxUnitPeriod());
         return period.compareTo(max)>0?max:period;
    }
    
    // 最终的上级调训学时
    private BigDecimal getUpperPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r){
        
         BigDecimal period = null;
         if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
                period = NumberUtils.trimToZero(cetAnnualObj.getUpperPeriod());
         }else {
                period = NumberUtils.trimToZero(r.get("upperPeriod"));
         }
         
         BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxUpperPeriod());
         return period.compareTo(max)>0?max:period;
    }
    
    // 最终的已完成学时
    private BigDecimal getFinishPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
         BigDecimal finishPeriod = BigDecimal.ZERO;
         
         finishPeriod = finishPeriod.add(getSpecialPeriod(cetAnnualObj, r));
         finishPeriod = finishPeriod.add(getDailyPeriod(cetAnnualObj, r));
         finishPeriod = finishPeriod.add(getPartyPeriod(cetAnnualObj, r));
         finishPeriod = finishPeriod.add(getUnitPeriod(cetAnnualObj, r));
         finishPeriod = finishPeriod.add(getUpperPeriod(cetAnnualObj, r));
       
         return finishPeriod;
    }
    
    /**
     * xxxx中层干部2018年度培训学习明细表.xlsx
     */
    public void cetAnnual_exportObjDetails(int objId, HttpServletResponse response) throws IOException {
    
        
        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        int userId = cetAnnualObj.getUserId();
        Integer annualId = cetAnnualObj.getAnnualId();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        int year = cetAnnual.getYear();
    
        BigDecimal finishPeriod = NumberUtils.trimToZero(getFinishPeriod(cetAnnualObj, cetAnnualObj.getR()));
        BigDecimal period = NumberUtils.trimToZero(cetAnnualObj.getPeriod());
        String rate = "--";
            if(period.compareTo(BigDecimal.ZERO)>0) {
                BigDecimal divide = finishPeriod.divide(period, 5, RoundingMode.HALF_UP );
                NumberFormat percent = NumberFormat.getPercentInstance();
                percent.setMaximumFractionDigits(1);
                rate = percent.format(divide.doubleValue());
            }
            
        SysUserView uv = cetAnnualObj.getUser();
    
        Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
        Integer traineeTypeId = cetAnnual.getTraineeTypeId();
        CetTraineeType cetTraineeType = cetTraineeTypeService.findAll().get(traineeTypeId);
        String typeName = cetTraineeType.getName();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/cet_annual_obj_details.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school_type_year", CmTag.getSysConfig().getSchoolName() + typeName + year);
        cell.setCellValue(str);
        
        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("code", uv.getCode())
                .replace("realname", uv.getRealname())
                .replace("title", cetAnnualObj.getTitle())
                .replace("adminLevel", metaTypeService.getName(cetAnnualObj.getAdminLevel()));
        cell.setCellValue(str);
        cell = row.getCell(3);
        str = cell.getStringCellValue()
                .replace("period", period.toString())
                .replace("finishPeriod", finishPeriod.toString())
                .replace("finishRate", rate);
        cell.setCellValue(str);
        
      
        List<TrainRecord> records = cetAnnualObjService.getTrainRecords(userId, year, true);

        int startRow = 3;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {
            
            TrainRecord record = records.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);
            
            // 培训时间
            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(record.getStartDate(), "yyyy.MM.dd")
                + "~" + DateUtils.formatDate(record.getEndDate(), "yyyy.MM.dd"));
            
            // 培训班名称
            cell = row.getCell(column++);
            cell.setCellValue(record.getName());
            
            // 培训类型
            cell = row.getCell(column++);
            cell.setCellValue(CetConstants.CET_TYPE_MAP.get(record.getType()));
            
            // 培训班主办方
            cell = row.getCell(column++);
            cell.setCellValue(record.getOrganizer());
            
            // 完成学时数
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToZero(record.getPeriod()).toString());
            
            // 是否已结业
            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getIsGraduate())?"是":"否");
            
            // 备注
            cell = row.getCell(column++);
            cell.setCellValue("");
        }
        
       ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName() + typeName + cetAnnual.getYear() +
                "年度培训学习明细表.xlsx", response);
    }
    
}