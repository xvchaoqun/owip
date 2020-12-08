package service.cr;

import domain.cadre.CadreView;
import domain.cr.CrApplicant;
import domain.cr.CrApplicantExample;
import domain.cr.CrInfo;
import domain.cr.CrPost;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrExportService extends CrBaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CrPostService crPostService;
    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private CadreInfoFormService cadreInfoFormService;
    @Autowired
    private CrApplicantService crApplicantService;

    /**
     * 干部竞争上岗报名信息表.xlsx
     */
    public void exportApplicants(int infoId, CrApplicantExample example, HttpServletResponse response) throws IOException, TemplateException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cr/applicants.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);

        Map<Integer, CrPost> postMap = crPostService.getPostMap(infoId);

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        String addDate = DateUtils.formatDate(crInfo.getAddDate(), DateUtils.YYYYMMDD_DOT);
        List<CrApplicant> records = crApplicantMapper.selectByExample(example);

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("date", addDate);
        cell.setCellValue(str);

        int year = crInfo.getYear();
        row = sheet.getRow(1);
        cell = row.getCell(21);
        str = cell.getStringCellValue()
                .replace("evaYear", (year-2003) + "  " + (year-2002) + "  " + (year-2001));
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            CrApplicant bean = records.get(i);
            SysUserView uv = bean.getUser();
            CadreView cv = CmTag.getCadreByUserId(uv.getUserId());
            int column = 0;
            row = sheet.getRow(startRow++);

            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 第一志愿岗位
            cell = row.getCell(column++);
            CrPost firstPost = postMap.get(bean.getFirstPostId());
            if(firstPost!=null) {
                cell.setCellValue(firstPost.getName());
            }

            // 第二志愿岗位
            cell = row.getCell(column++);

            if (bean.getSecondPostId() != null) {
                CrPost secondPost = postMap.get(bean.getSecondPostId());
                if(secondPost!=null) {
                    cell.setCellValue(secondPost.getName());
                }
            }

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(CmTag.realnameWithEmpty(uv.getRealname()));

            // 工号
            cell = row.getCell(column++);
            cell.setCellValue(uv.getCode());

            // 现任岗位
            cell = row.getCell(column++);
            cell.setCellValue(cv.getTitle());

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (uv.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(uv.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 出生年月
            String birth = DateUtils.formatDate(uv.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            cell = row.getCell(column++);
            cell.setCellValue(birth != null ? DateUtils.intervalYearsUntilNow(uv.getBirth()) + "" : "");

            // 民族 （全都去掉“族”， 显示“汉”)
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(StringUtils.replace(uv.getNation(), "族", "")));

            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(),
                    cv.getOwPositiveTime(),"中共党员",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");
            String partyAddTime = cadreParty.get("growTime");

            // 政治面貌
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(partyName));

            // 党派加入时间
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(partyAddTime));

            // 参加工作时间
            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(cv.getWorkTime(), DateUtils.YYYYMM));

            // 专业技术职务、专业技术职务评定时间
            String proPost = StringUtils.defaultString(cv.getProPost(), "--");
            cell = row.getCell(column++);
            cell.setCellValue(proPost);

            String proPostTime = StringUtils.defaultIfBlank(DateUtils.formatDate(cv.getProPostTime(), DateUtils.YYYYMM), "--");
            cell = row.getCell(column++);
            cell.setCellValue(proPostTime);

            // 特长
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(uv.getSpecialty()));

            // 最高学历学位
            String edu = "";
            Integer eduId = cv.getEduId();
            if (eduId != null) {
                edu = CmTag.getEduName(eduId);
            }
            if (StringUtils.isNotBlank(cv.getDegree())) {
                edu += "\r\n" + StringUtils.trimToEmpty(cv.getDegree());
            }
            cell = row.getCell(column++);
            cell.setCellValue(edu);


            Map<String, Object> dataMap = cadreInfoFormService.getDataMap(cv.getId(), false);

            // 全日制教育 学历/学位
            cell = row.getCell(column++);
            cell.setCellValue(dataMap.get("edu")+"" + (dataMap.get("degree")!=null?("\r\n"+dataMap.get("degree")) : ""));

            // 全日制教育 毕业学校专业
            cell = row.getCell(column++);
            String schoolDepMajor1 = StringUtils.trimToEmpty((String) dataMap.get("schoolDepMajor1"));
            schoolDepMajor1 += (BooleanUtils.isTrue((Boolean) dataMap.get("sameSchool"))?"":"\r\n")
                    +StringUtils.trimToEmpty((String) dataMap.get("schoolDepMajor2"));
            cell.setCellValue(schoolDepMajor1);

            // 在职 学历/学位
            cell = row.getCell(column++);
            cell.setCellValue(dataMap.get("inEdu")+"" + (dataMap.get("inDegree")!=null?("\r\n"+dataMap.get("inDegree")) : ""));

            // 在职 毕业学校专业
            cell = row.getCell(column++);
            String inSchoolDepMajor1 = StringUtils.trimToEmpty((String) dataMap.get("inSchoolDepMajor1"));
            inSchoolDepMajor1 += (BooleanUtils.isTrue((Boolean) dataMap.get("sameInSchool"))?"":"\r\n")
                    +StringUtils.trimToEmpty((String) dataMap.get("inSchoolDepMajor2"));
            cell.setCellValue(inSchoolDepMajor1);

            // 考核结果
            String evas = crApplicantService.getEva(infoId, bean.getUserId());
            String[] evaStrs = evas.split(",");
            List<String> evaList = new ArrayList<>();
            for (String eva : evaStrs) {
                evaList.add(metaTypeService.getName(Integer.valueOf(eva)));
            }
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.join(evaList, " "));


            // 手机
            cell = row.getCell(column++);
            cell.setCellValue(cv.getMobile());

            // 编制类别
            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(uv.getId());
            cell = row.getCell(column++);
            if(teacherInfo!=null) {
                 cell.setCellValue(teacherInfo.getAuthorizedType());
            }

            // 个人身份
            cell = row.getCell(column++);
            if(teacherInfo!=null) {
                 cell.setCellValue(teacherInfo.getStaffType());
            }
        }

        String fileName = String.format("干部竞争上岗报名信息表(%s)", addDate);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    /**
     * xxx干部应聘报名表.doc
     *
     * @param infoId
     * @param ids
     * @param out
     */
    public void process(int infoId, Integer[] ids, PrintWriter out) throws IOException, TemplateException {

        if (ids == null || ids.length == 0) {
            CrApplicantExample example = new CrApplicantExample();
            CrApplicantExample.Criteria criteria = example.createCriteria()
                    .andInfoIdEqualTo(infoId)
                    .andHasSubmitEqualTo(true);
            example.setOrderByClause("sort_order desc, enroll_time asc");
            List<CrApplicant> crApplicants = crApplicantMapper.selectByExample(example);
            List<Integer> idList = new ArrayList<>();
            for (CrApplicant crApplicant : crApplicants) {
                idList.add(crApplicant.getId());
            }
            ids = idList.toArray(new Integer[0]);
        }

        Map<Integer, CrPost> postMap = crPostService.getPostMap(infoId);

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        int year = crInfo.getYear();
        List applicants = new ArrayList<>();
        for (int id : ids) {

            CrApplicant crApplicant = crApplicantMapper.selectByPrimaryKey(id);
            CadreView cadre = CmTag.getCadreByUserId(crApplicant.getUserId());

            int cadreId = cadre.getId();
            Map<String, Object> applicantDataMap = cadreInfoFormService.getDataMap(cadreId, false);
            CrPost firstPost = postMap.get(crApplicant.getFirstPostId());
            if(firstPost!=null) {
                applicantDataMap.put("firstPost", firstPost.getName());
            }
            if (crApplicant.getSecondPostId() != null) {
                CrPost secondPost = postMap.get(crApplicant.getSecondPostId());
                if(secondPost!=null) {
                    applicantDataMap.put("secondPost", secondPost.getName());
                }
            }
            applicantDataMap.put("reason", crApplicant.getReason());

            String evas = crApplicantService.getEva(infoId, crApplicant.getUserId());
            String[] evaStrs = evas.split(",");
            int evaLength = evaStrs.length;
            List<String> evaList = new ArrayList<>();
            int i = 0;
            for (String eva : evaStrs) {
                evaList.add((year - evaLength + i) + "年： " + metaTypeService.getName(Integer.valueOf(eva)));
                i++;
            }
            applicantDataMap.put("eva", StringUtils.join(evaList, "， ") + " 。");

            applicants.add(applicantDataMap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("infoName", crInfo.getName());
        dataMap.put("applicants", applicants);
        dataMap.put("applyPostNum", crInfo.getApplyPostNum());

        freemarkerService.process("/cr/form.ftl", dataMap, out);
    }
}
