package service.cet;

import domain.cadre.CadreView;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectPlan;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTraineeCourseView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import service.cadre.CadreService;
import service.sys.SysUserService;
import sys.constants.CetConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CetExportService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CetProjectObjService cetProjectObjService;
    @Autowired
    private CetProjectPlanService cetProjectPlanService;
    @Autowired
    private CetTraineeCourseService cetTraineeCourseService;

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
        Map<Integer, Map<Integer, BigDecimal>> objFinishPeriodMap = cetProjectObjService.getObjFinishPeriodMap(projectId);

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
            cell.setCellValue(cv==null?"":StringUtils.trimToEmpty(cv.getTitle()));

            Map<Integer, BigDecimal> periodMap = objFinishPeriodMap.get(cetProjectObj.getId());

            cell = row.getCell(column++);
            BigDecimal total = periodMap.get(0);
            cell.setCellValue(total == null ? "" : total.stripTrailingZeros().toPlainString());
            periodMap.remove(0);

            for (CetProjectPlan cetProjectPlan : projectPlanMap.values()) {
                cell = row.getCell(column++);
                if(cell != null) {
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
            cell.setCellValue(cv==null?"":StringUtils.trimToEmpty(cv.getTitle()));

            // 手机号码
            cell = row.getCell(column++);
            cell.setCellValue(uv.getMobile());

            // 选课时间
            cell = row.getCell(column++);
            cell.setCellValue(teev==null?"":DateUtils.formatDate(teev.getChooseTime(), "yyyy-MM-dd HH:mm:ss"));
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

}
