package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import domain.sys.SysUser;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
                                    Integer cadreId, ModelMap modelMap) {

        List<CadreCourse> cadreCourses = new ArrayList<>();
        {
            CadreCourseExample example = new CadreCourseExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            example.setOrderByClause("id desc");
            cadreCourses = cadreCourseMapper.selectByExample(example);
        }
        modelMap.put("cadreCourses", cadreCourses);

        List<CadreReward> cadreRewards = new ArrayList<>();
        {
            CadreRewardExample example = new CadreRewardExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_TEACH);
            example.setOrderByClause("sort_order desc");
            cadreRewards = cadreRewardMapper.selectByExample(example);
        }
        modelMap.put("cadreRewards", cadreRewards);

        modelMap.put("cadreMap", cadreService.findAll());

        return "cadre/cadreCourse/cadreCourse_page";
    }

    @RequiresPermissions("cadreCourse:edit")
    @RequestMapping(value = "/cadreCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCourse_au(CadreCourse record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadreCourseService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部教学课程：%s", record.getId()));
        } else {

            cadreCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部教学课程：%s", record.getId()));
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
        return "cadre/cadreCourse/cadreCourse_au";
    }

    @RequiresPermissions("cadreCourse:del")
    @RequestMapping(value = "/cadreCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreCourseService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部教学课程：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCourse:del")
    @RequestMapping(value = "/cadreCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreCourseService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部教学课程：%s", StringUtils.join(ids, ",")));
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
