package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
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
public class CadreCourseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreCourse:list")
    @RequestMapping("/cadreCourse")
    public String cadreCourse() {

        return "index";
    }
    @RequiresPermissions("cadreCourse:list")
    @RequestMapping("/cadreCourse_page")
    public String cadreCourse_page(
            @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {
            modelMap.put("bksCadreCourses", cadreCourseService.find(cadreId, SystemConstants.CADRE_COURSE_TYPE_BKS));
            modelMap.put("ssCadreCourses", cadreCourseService.find(cadreId, SystemConstants.CADRE_COURSE_TYPE_SS));
            modelMap.put("bsCadreCourses", cadreCourseService.find(cadreId, SystemConstants.CADRE_COURSE_TYPE_BS));
            {
                CadreRewardExample example = new CadreRewardExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andRewardTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_TEACH);
                example.setOrderByClause("reward_time asc");
                List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
                modelMap.put("cadreRewards", cadreRewards);
            }

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_TEACH);
            modelMap.put("cadreInfo", cadreInfo);
        }
        return "cadre/cadreCourse/cadreCourse_page";
    }
    @RequiresPermissions("cadreCourse:list")
    @RequestMapping("/cadreCourse_data")
    public void cadreCourse_data(HttpServletResponse response,
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

        CadreCourseExample example = new CadreCourseExample();
        CadreCourseExample.Criteria criteria = example.createCriteria();
        // 先按照课程类型排序，依次为“本科生课程、硕士生课程、博士生课程”。然后每种类型中课程的顺序按照添加时的排序。
        example.setOrderByClause("type asc, sort_order asc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreCourse_export(example, response);
            return;
        }

        int count = cadreCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreCourse> CadreCourses = cadreCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreCourses);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreCourse:edit")
    @RequestMapping(value = "/cadreCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCourse_au(CadreCourse record, String names,  HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            cadreCourseService.batchAdd(record, names);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量添加干部教学课程：%s, %s",
                    SystemConstants.CADRE_COURSE_TYPE_MAP.get(record.getType()), names));
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

    @RequiresPermissions("cadreCourse:changeOrder")
    @RequestMapping(value = "/cadreCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreCourseService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部教学课程调序：%s,%s", id, addNum));
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
