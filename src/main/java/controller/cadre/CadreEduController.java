package controller.cadre;

import controller.BaseController;
import domain.Cadre;
import domain.CadreEdu;
import domain.CadreEduExample;
import domain.CadreEduExample.Criteria;
import domain.SysUser;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreEduController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreEdu:list")
    @RequestMapping("/cadreEdu")
    public String cadreEdu() {

        return "index";
    }
    @RequiresPermissions("cadreEdu:list")
    @RequestMapping("/cadreEdu_page")
    public String cadreEdu_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_cadre_edu") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreEduExample example = new CadreEduExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {

            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);

            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreEdu_export(example, response);
            return null;
        }

        int count = cadreEduMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreEdu> CadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreEdus", CadreEdus);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("cadreTutors", cadreTutorService.findAll(cadreId).values());

        return "cadre/cadreEdu/cadreEdu_page";
    }

    @RequiresPermissions("cadreEdu:edit")
    @RequestMapping(value = "/cadreEdu_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_au(CadreEdu record, String _enrolTime, String _finishTime, String _degreeTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_enrolTime)){
            record.setEnrolTime(DateUtils.parseDate(_enrolTime, "yyyy.MM"));
        }
        if(StringUtils.isNotBlank(_finishTime)){
            record.setFinishTime(DateUtils.parseDate(_finishTime, "yyyy.MM"));
        }
        if(StringUtils.isNotBlank(_degreeTime)){
            record.setDegreeTime(DateUtils.parseDate(_degreeTime, DateUtils.YYYY_MM_DD));
        }
        record.setHasDegree((record.getHasDegree() == null) ? false : record.getHasDegree());
        if(!record.getHasDegree()){
            record.setDegree(""); // 没有获得学位，清除学位名称
        }
        if(record.getSchoolType()==SystemConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL ||
                record.getSchoolType()==SystemConstants.CADRE_SCHOOL_TYPE_DOMESTIC){
            record.setDegreeCountry("中国");
        }

        record.setIsHighEdu((record.getIsHighEdu() == null) ? false : record.getIsHighEdu());
        record.setIsHighDegree((record.getIsHighDegree() == null) ? false : record.getIsHighDegree());


        if (id == null) {
            cadreEduService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部学习经历：%s", record.getId()));
        } else {

            cadreEduService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部学习经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEdu:edit")
    @RequestMapping("/cadreEdu_au")
    public String cadreEdu_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(id);
            modelMap.put("cadreEdu", cadreEdu);
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreEdu/cadreEdu_au";
    }

    @RequiresPermissions("cadreEdu:del")
    @RequestMapping(value = "/cadreEdu_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreEduService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部学习经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEdu:del")
    @RequestMapping(value = "/cadreEdu_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreEduService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部学习经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEdu:changeOrder")
    @RequestMapping(value = "/cadreEdu_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_changeOrder(Integer id, int cadreId,  Integer addNum, HttpServletRequest request) {

        cadreEduService.changeOrder(id, cadreId, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部学习经历调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreEdu_export(CadreEduExample example, HttpServletResponse response) {

        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);
        int rownum = cadreEduMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","学历","毕业学校","院系","入学时间","毕业时间","学位"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreEdu cadreEdu = cadreEdus.get(i);
            String[] values = {
                        cadreEdu.getCadreId()+"",
                                            cadreEdu.getEduId()+"",
                                            cadreEdu.getSchool(),
                                            cadreEdu.getDep(),
                                            DateUtils.formatDate(cadreEdu.getEnrolTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(cadreEdu.getFinishTime(), DateUtils.YYYY_MM_DD),
                                            cadreEdu.getDegree()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部学习经历_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
