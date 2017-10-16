package service.crs;

import domain.cadre.CadreView;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import domain.crs.CrsPost;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
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
import service.cadre.CadreInfoFormService;
import service.common.FreemarkerService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/10/14.
 */
@Service
public class CrsExportService extends BaseMapper{

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private CadreInfoFormService cadreInfoFormService;

    /**
     * 党委宣传部副部长兼新闻中心副主任应聘报名统计表.xlsx
     */
    public void exportApplicants(int postId, CrsApplicantViewExample example, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/crs/applicant_list.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        String postName = crsPost.getName();
        List<CrsApplicantView> records = crsApplicantViewMapper.selectByExample(example);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", postName);
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            CrsApplicantView bean = records.get(i);
            SysUserView uv = bean.getUser();
            CadreView cv = bean.getCadre();
            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(uv.getRealname());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (uv.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(uv.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 出生年月
            String birth = DateUtils.formatDate(uv.getBirth(), "yyyy.MM");
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 民族
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(uv.getNation()));

            // 政治面貌
            String political = "";
            long cadreDpType = cv.getCadreDpType();
            if(cadreDpType==0){
                political = "中共党员";
            }else if(cadreDpType>0){
                Integer dpTypeId = cv.getDpTypeId();
                political = metaTypeService.getName(dpTypeId);
            }
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(political));

            // 党派加入时间
            Date cadreGrowTime = cv.getCadreGrowTime();
            cell = row.getCell(column++);
            cell.setCellValue(cadreGrowTime==null?"":DateUtils.formatDate(cadreGrowTime, "yyyy.MM"));

            // 最高学历学位
            String edu = "";
            Integer eduId = cv.getEduId();
            if(eduId!=null) {
                edu += metaTypeService.getName(eduId);
            }
            if(StringUtils.isNotBlank(cv.getDegree())) {
                edu += "\r\n" + StringUtils.trimToEmpty(cv.getDegree());
            }
            cell = row.getCell(column++);
            cell.setCellValue(edu);

            // 毕业院校
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cv.getSchool()));
            // 所学专业
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cv.getMajor()));
            // 到校日期
            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(cv.getArriveTime(), "yyyy.MM"));
            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cv.getTitle()));

            // 专业技术职务
            String proPost = StringUtils.trimToEmpty(cv.getProPost());
            if(StringUtils.isNotBlank(cv.getProPostLevel())){
                proPost += "\r\n" + StringUtils.trimToEmpty(cv.getProPostLevel());
            }
            cell = row.getCell(column++);
            cell.setCellValue(proPost);

            // 专业技术职务评定时间
            String proPostTime = DateUtils.formatDate(cv.getProPostTime(), "yyyy.MM");
            if(cv.getProPostLevelTime()!=null){
                proPostTime += "\r\n" + DateUtils.formatDate(cv.getProPostLevelTime(), "yyyy.MM");
            }
            cell = row.getCell(column++);
            cell.setCellValue(proPostTime);

            // 推荐/自荐
            String recommend = "";
            if(BooleanUtils.isTrue(bean.getIsRecommend())){
                List<String> list = new ArrayList<>();
                if(StringUtils.isNotBlank(bean.getRecommendOw()))
                    list.add(bean.getRecommendOw().trim());
                if(StringUtils.isNotBlank(bean.getRecommendCadre()))
                    list.add(bean.getRecommendCadre().trim());
                if(StringUtils.isNotBlank(bean.getRecommendCrowd()))
                    list.add(bean.getRecommendCrowd().trim());
                recommend = StringUtils.join(list, ",");
            }else{
                recommend = "个人报名";
            }
            cell = row.getCell(column++);
            cell.setCellValue(recommend);
        }

        String fileName = String.format("%s应聘报名统计表", postName);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    /**
     * xxx处级干部应聘报名表.doc
     * @param ids
     * @param out
     */
    public void process(Integer[] ids, PrintWriter out) throws IOException, TemplateException {

        if(ids==null || ids.length==0) return;

        List applicants = new ArrayList<>();
        for (int id : ids) {

            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(id);
            String postName = crsApplicant.getPost().getName();
            CadreView cadre = crsApplicant.getCadre();
            int cadreId = cadre.getId();
            Map<String, Object> applicantDataMap = cadreInfoFormService.getDataMap(cadreId);
            applicantDataMap.put("postName", postName);
            applicantDataMap.put("applicantReport", freemarkerService.genTextareaSegment(crsApplicant.getReport()));

            applicants.add(applicantDataMap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("applicants", applicants);

        freemarkerService.process("/crs/form.ftl", dataMap, out);
    }
}
