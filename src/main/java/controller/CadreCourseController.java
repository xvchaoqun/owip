package controller;

import domain.Cadre;
import domain.CadreCourse;
import domain.CadreCourseExample;
import domain.CadreCourseExample.Criteria;
import domain.SysUser;
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
public class CadreCourseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreCourse:list")
    @RequestMapping("/cadreCourse")
    public String cadreCourse() {

        return "index";
    }
    @RequiresPermissions("cadreCourse:list")
    @RequestMapping("/cadreCourse_page")
    public String cadreCourse_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "id") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
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

        CadreCourseExample example = new CadreCourseExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreCourse_export(example, response);
            return null;
        }

        int count = cadreCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreCourse> CadreCourses = cadreCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreCourses", CadreCourses);

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

        modelMap.put("cadreMap", cadreService.findAll());

        return "cadreCourse/cadreCourse_page";
    }

    @RequiresPermissions("cadreCourse:edit")
    @RequestMapping(value = "/cadreCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCourse_au(CadreCourse record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadreCourseService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部教学课程：%s", record.getId()));
        } else {

            cadreCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部教学课程：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCourse:edit")
    @RequestMapping("/cadreCourse_au")
    public String cadreCourse_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreCourse cadreCourse = cadreCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cadreCourse", cadreCourse);
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadreCourse/cadreCourse_au";
    }

    @RequiresPermissions("cadreCourse:del")
    @RequestMapping(value = "/cadreCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreCourseService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部教学课程：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCourse:del")
    @RequestMapping(value = "/cadreCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreCourseService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部教学课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreCourse_export(CadreCourseExample example, HttpServletResponse response) {

        List<CadreCourse> cadreCourses = cadreCourseMapper.selectByExample(example);
        int rownum = cadreCourseMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","课程名称","类型"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreCourse cadreCourse = cadreCourses.get(i);
            String[] values = {
                        cadreCourse.getCadreId()+"",
                                            cadreCourse.getName(),
                                            cadreCourse.getType() +""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部教学课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
