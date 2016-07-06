package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreUnderEdu;
import domain.cadre.CadreUnderEduExample;
import domain.cadre.CadreUnderEduExample.Criteria;
import domain.sys.SysUser;
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
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreUnderEduController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*@RequiresPermissions("cadreUnderEdu:list")
    @RequestMapping("/cadreUnderEdu")
    public String cadreUnderEdu() {

        return "index";
    }
    @RequiresPermissions("cadreUnderEdu:list")
    @RequestMapping("/cadreUnderEdu_page")
    public String cadreUnderEdu_page(HttpServletResponse response,
                                @SortParam(required = false, defaultValue = "sort_order", tableName = "base_cadre_edu") String sort,
                                @OrderParam(required = false, defaultValue = "desc") String order,
                                Integer cadreId,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (cadreId!=null) {

            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        modelMap.put("cadreTutors", cadreTutorService.findAll(cadreId).values());
        return "cadre/cadreUnderEdu/cadreUnderEdu_page";
    }*/
    @RequiresPermissions("cadreUnderEdu:list")
    @RequestMapping("/cadreUnderEdu_data")
    @ResponseBody
    public void cadreUnderEdu_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_cadre_edu") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreUnderEduExample example = new CadreUnderEduExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreUnderEdu_export(example, response);
            return;
        }

        int count = cadreUnderEduMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreUnderEdu> CadreUnderEdus = cadreUnderEduMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", CadreUnderEdus);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreUnderEdu:edit")
    @RequestMapping(value = "/cadreUnderEdu_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreUnderEdu_au(CadreUnderEdu record, String _enrolTime, String _finishTime, String _degreeTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_enrolTime)){
            record.setEnrolTime(DateUtils.parseDate(_enrolTime, "yyyy.MM"));
        }

        if (id == null) {
            cadreUnderEduService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部在读学习经历：%s", record.getId()));
        } else {

            cadreUnderEduService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部在读学习经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreUnderEdu:edit")
    @RequestMapping("/cadreUnderEdu_au")
    public String cadreUnderEdu_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreUnderEdu cadreUnderEdu = cadreUnderEduMapper.selectByPrimaryKey(id);
            modelMap.put("cadreUnderEdu", cadreUnderEdu);
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreUnderEdu/cadreUnderEdu_au";
    }

    // 认定规则
    @RequiresPermissions("cadreUnderEdu:edit")
    @RequestMapping("/cadreUnderEdu_rule")
    public String cadreUnderEdu_rule() {

        return "cadre/cadreUnderEdu/cadreUnderEdu_rule";
    }

    @RequiresPermissions("cadreUnderEdu:del")
    @RequestMapping(value = "/cadreUnderEdu_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreUnderEdu_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreUnderEduService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部在读学习经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreUnderEdu:del")
    @RequestMapping(value = "/cadreUnderEdu_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreUnderEduService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部在读学习经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreUnderEdu:changeOrder")
    @RequestMapping(value = "/cadreUnderEdu_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreUnderEdu_changeOrder(Integer id, int cadreId,  Integer addNum, HttpServletRequest request) {

        cadreUnderEduService.changeOrder(id, cadreId, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部在读学习经历调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreUnderEdu_export(CadreUnderEduExample example, HttpServletResponse response) {

        List<CadreUnderEdu> cadreUnderEdus = cadreUnderEduMapper.selectByExample(example);
        int rownum = cadreUnderEduMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","在读学历","在读学校","院系","所学专业", "入学时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreUnderEdu cadreUnderEdu = cadreUnderEdus.get(i);
            String[] values = {
                        cadreUnderEdu.getCadreId()+"",
                                            cadreUnderEdu.getEduId()+"",
                                            cadreUnderEdu.getSchool(),
                                            cadreUnderEdu.getDep(),
                                            cadreUnderEdu.getMajor(),
                                            DateUtils.formatDate(cadreUnderEdu.getEnrolTime(), DateUtils.YYYY_MM_DD)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部在读学习经历_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
