package service.cadreReserve;

import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadrePostService;
import service.unit.UnitService;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CadreReserveExportService extends BaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected CadrePostService cadrePostService;

    //导出优秀年轻干部名单
    public void export2(Byte reserveStatus, Integer reserveType, CadreReserveViewExample example, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cadre/cadres.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String schoolName = CmTag.getSysConfig().getSchoolName();
        String typeStr = "";
        if(reserveType!=null){
            String name = metaTypeService.getName(reserveType);
            if(StringUtils.isNotBlank(name)){
                typeStr = "（" + name + "）";
            }
        }
        String str = cell.getStringCellValue()
                .replace("title", schoolName + "年轻干部名单" + typeStr);
        cell.setCellValue(str);

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        List<CadreReserveView> records = cadreReserveViewMapper.selectByExample(example);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            CadreReserveView cr = records.get(i);
            SysUserView uv = cr.getUser();
            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(CmTag.realnameWithEmpty(uv.getRealname()));

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cr.getTitle()));

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (uv.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(uv.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 民族 （全都去掉“族”， 显示“汉”)
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(StringUtils.replace(uv.getNation(), "族", "")));

            // 出生时间
            String birth = DateUtils.formatDate(uv.getBirth(), birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            String age = cr.getBirth() == null ? "" : DateUtils.yearOffNow(cr.getBirth()) + "";
            cell = row.getCell(column++);
            cell.setCellValue(age);

            Map<String, String> cadreParty = CmTag.getCadreParty(cr.getUserId(), cr.getIsOw(), cr.getOwGrowTime(), cr.getOwPositiveTime(),
                    cr.getDpTypeId(), cr.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");

            // 党派
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(partyName));

            // 最高学历学位
            String edu = "";
            Integer eduId = cr.getEduId();
            if (eduId != null) {
                edu = CmTag.getEduName(eduId);
            }
            if (StringUtils.isNotBlank(cr.getDegree())) {
                edu += "\r\n" + StringUtils.trimToEmpty(cr.getDegree());
            }
            cell = row.getCell(column++);
            cell.setCellValue(edu);

            // 所学专业
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cr.getMajor()));

            // 专业技术职务
            String proPost = StringUtils.defaultString(cr.getProPost(), "--");
            String manageLevel = StringUtils.defaultString(cr.getManageLevel(), "--");
            if (cr.getProPost() != null && cr.getManageLevel() == null) {
                cell = row.getCell(column++);
                cell.setCellValue(proPost);
            } else if (cr.getProPost() == null && cr.getManageLevel() != null) {
                cell = row.getCell(column++);
                cell.setCellValue(manageLevel);
            } else if (cr.getProPost() != null && cr.getManageLevel() != null) {
                cell = row.getCell(column++);
                cell.setCellValue(proPost + "\r\n" + manageLevel);
            } else {
                cell = row.getCell(column++);
                cell.setCellValue("--");
            }

            String postStartTime = ""; // 现职务始任时间
            String postYear = ""; // 现职务始任年限
            String adminLevelStartTime = ""; // 现职级始任时间
            String adminLevelYear = ""; // 任现职级年限

            if(cr.getNpWorkTime()!=null) {
                postStartTime = DateUtils.formatDate(cr.getNpWorkTime(), CmTag.getBoolProperty("postTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM);
                Integer year = DateUtils.intervalYearsUntilNow(cr.getNpWorkTime());
                if (year == 0) postYear = "未满一年";
                else postYear = year + "";
            }

            if(cr.getsWorkTime()!=null) {
                adminLevelStartTime = DateUtils.formatDate(cr.getsWorkTime(), CmTag.getBoolProperty("postTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM);
                Date eWorkTime = cr.geteWorkTime();
                Integer monthDiff = DateUtils.monthDiff(cr.getsWorkTime(), eWorkTime==null?new Date():eWorkTime);
                int year = monthDiff / 12;
                if (year == 0) adminLevelYear = "未满一年";
                else adminLevelYear = year + "";
            }

            // 现职务始任时间
            cell = row.getCell(column++);
            cell.setCellValue(postStartTime);
            // 现职务始任年限
            cell = row.getCell(column++);
            cell.setCellValue(postYear);
            // 现职级始任时间
            cell = row.getCell(column++);
            cell.setCellValue(adminLevelStartTime);
            // 任现职级年限
            cell = row.getCell(column++);
            cell.setCellValue(adminLevelYear);
        }

        String suffix = null;
        if (reserveType != null) {
            suffix = metaTypeService.getName(reserveType);
        }else {
            suffix = CadreConstants.CADRE_RESERVE_STATUS_MAP.get(reserveStatus);
        }
        String fileName = String.format("%s年轻干部名单", schoolName);
        if (StringUtils.isNotBlank(suffix)){
            fileName = String.format("%s年轻干部（" + suffix + "）名单", schoolName);
        }
        ExportHelper.output(wb, fileName + ".xlsx", response);

    }
}
