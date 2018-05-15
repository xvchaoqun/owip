package service.cet;

import domain.cadre.CadreView;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
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
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CetExportService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;

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
