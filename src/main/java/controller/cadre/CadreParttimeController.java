package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreInfo;
import domain.cadre.CadreParttime;
import domain.cadre.CadreParttimeExample;
import domain.cadre.CadreParttimeExample.Criteria;
import domain.sys.SysUser;
import domain.sys.SysUserView;
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
public class CadreParttimeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreParttime:list")
    @RequestMapping("/cadreParttime")
    public String cadreParttime() {

        return "index";
    }
    @RequiresPermissions("cadreParttime:list")
    @RequestMapping("/cadreParttime_page")
    public String cadreParttime_page(
            @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {

            CadreParttimeExample example = new CadreParttimeExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            example.setOrderByClause("start_time asc");
            List<CadreParttime> cadreParttimes = cadreParttimeMapper.selectByExample(example);
            modelMap.put("cadreParttimes", cadreParttimes);

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_PARTTIME);
            modelMap.put("cadreInfo", cadreInfo);
        }
        return "cadre/cadreParttime/cadreParttime_page";
    }
    @RequiresPermissions("cadreParttime:list")
    @RequestMapping("/cadreParttime_data")
    public void cadreParttime_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "cadre_parttime") String sort,
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

        CadreParttimeExample example = new CadreParttimeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreParttime_export(example, response);
            return;
        }

        int count = cadreParttimeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreParttime> CadreParttimes = cadreParttimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreParttimes);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;

    }

    @RequiresPermissions("cadreParttime:edit")
    @RequestMapping(value = "/cadreParttime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParttime_au(CadreParttime record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, "yyyy.MM"));
        }
        if(StringUtils.isNotBlank(_endTime)){
            record.setEndTime(DateUtils.parseDate(_endTime, "yyyy.MM"));
        }

        if (id == null) {
            cadreParttimeService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部社会或学术兼职：%s", record.getId()));
        } else {
            // 干部信息本人直接修改数据校验
            CadreParttime _record = cadreParttimeMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }
            cadreParttimeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部社会或学术兼职：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreParttime:edit")
    @RequestMapping("/cadreParttime_au")
    public String cadreParttime_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreParttime cadreParttime = cadreParttimeMapper.selectByPrimaryKey(id);
            modelMap.put("cadreParttime", cadreParttime);
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreParttime/cadreParttime_au";
    }

    /*@RequiresPermissions("cadreParttime:del")
    @RequestMapping(value = "/cadreParttime_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParttime_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreParttimeService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部社会或学术兼职：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreParttime:del")
    @RequestMapping(value = "/cadreParttime_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreParttimeService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部社会或学术兼职：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreParttime:changeOrder")
    @RequestMapping(value = "/cadreParttime_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParttime_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        CadreParttime cadreParttime = cadreParttimeMapper.selectByPrimaryKey(id);
        cadreParttimeService.changeOrder(id, cadreParttime.getCadreId(), addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部社会或学术兼职调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreParttime_export(CadreParttimeExample example, HttpServletResponse response) {

        List<CadreParttime> cadreParttimes = cadreParttimeMapper.selectByExample(example);
        int rownum = cadreParttimeMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"起始时间","结束时间","兼任职务"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreParttime cadreParttime = cadreParttimes.get(i);
            String[] values = {
                        DateUtils.formatDate(cadreParttime.getStartTime(), "yyyy.MM"),
                                            DateUtils.formatDate(cadreParttime.getEndTime(), "yyyy.MM"),
                                            cadreParttime.getPost()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部社会或学术兼职_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
