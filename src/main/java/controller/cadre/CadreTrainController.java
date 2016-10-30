package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreInfo;
import domain.cadre.CadreTrain;
import domain.cadre.CadreTrainExample;
import domain.cadre.CadreTrainExample.Criteria;
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
public class CadreTrainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreTrain:list")
    @RequestMapping("/cadreTrain")
    public String cadreTrain() {

        return "index";
    }
    @RequiresPermissions("cadreTrain:list")
    @RequestMapping("/cadreTrain_page")
    public String cadreTrain_page(
            @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {

            CadreTrainExample example = new CadreTrainExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            example.setOrderByClause("start_time asc");
            List<CadreTrain> cadreTrains = cadreTrainMapper.selectByExample(example);
            modelMap.put("cadreTrains", cadreTrains);

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_TRAIN);
            modelMap.put("cadreInfo", cadreInfo);
        }
        return "cadre/cadreTrain/cadreTrain_page";
    }
    @RequiresPermissions("cadreTrain:list")
    @RequestMapping("/cadreTrain_data")
    public void cadreTrain_data(HttpServletResponse response,
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

        CadreTrainExample example = new CadreTrainExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreTrain_export(example, response);
            return;
        }

        int count = cadreTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreTrain> CadreTrains = cadreTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreTrains);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreTrain:edit")
    @RequestMapping(value = "/cadreTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTrain_au(CadreTrain record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, "yyyy.MM.dd"));
        }
        if(StringUtils.isNotBlank(_endTime)){
            record.setEndTime(DateUtils.parseDate(_endTime, "yyyy.MM.dd"));
        }

        if (id == null) {
            cadreTrainService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部社会或学术兼职：%s", record.getId()));
        } else {

            cadreTrainService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部社会或学术兼职：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTrain:edit")
    @RequestMapping("/cadreTrain_au")
    public String cadreTrain_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreTrain cadreTrain = cadreTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cadreTrain", cadreTrain);
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreTrain/cadreTrain_au";
    }

    @RequiresPermissions("cadreTrain:del")
    @RequestMapping(value = "/cadreTrain_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTrain_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreTrainService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部社会或学术兼职：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTrain:del")
    @RequestMapping(value = "/cadreTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreTrainService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部社会或学术兼职：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("cadreTrain:changeOrder")
    @RequestMapping(value = "/cadreTrain_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTrain_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        CadreTrain cadreTrain = cadreTrainMapper.selectByPrimaryKey(id);
        cadreTrainService.changeOrder(id, cadreTrain.getCadreId(), addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部社会或学术兼职调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }*/

    public void cadreTrain_export(CadreTrainExample example, HttpServletResponse response) {

        List<CadreTrain> cadreTrains = cadreTrainMapper.selectByExample(example);
        int rownum = cadreTrainMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"起始时间","结束时间","培训内容"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreTrain cadreTrain = cadreTrains.get(i);
            String[] values = {
                        DateUtils.formatDate(cadreTrain.getStartTime(), "yyyy.MM"),
                                            DateUtils.formatDate(cadreTrain.getEndTime(), "yyyy.MM"),
                                            cadreTrain.getContent()
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
