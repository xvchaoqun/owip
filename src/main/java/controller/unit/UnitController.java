package controller.unit;

import controller.BaseController;
import domain.*;
import domain.UnitExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    // 基本信息
    @RequiresPermissions("unit:info")
    @RequestMapping("/unit_base")
    public String unit_base(Integer id, ModelMap modelMap) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        modelMap.put("unit", unit);
        // 正在运转单位
        modelMap.put("runUnits", unitService.findRunUnits(id));
        // 历史单位
        modelMap.put("historyUnits", unitService.findHistoryUnits(id));

        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_unit_type"));

        return "unit/unit_base";
    }

    @RequiresPermissions("unit:info")
    @RequestMapping("/unit_view")
    public String unit_show_page(HttpServletResponse response,  ModelMap modelMap) {

        return "unit/unit_view";
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/unit")
    public String unit() {

        return "index";
    }
    @RequiresPermissions("unit:list")
    @RequestMapping("/unit_page")
    public String unit_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_unit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(required = false, defaultValue = "1")Byte status,
                                    String code,
                                    String name,
                                    Integer typeId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        modelMap.put("status", status);

        UnitExample example = new UnitExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            unit_export(example, response);
            return null;
        }

        int count = unitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Unit> Units = unitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("units", Units);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        if (status!=null) {
            searchStr += "&status=" + status;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_unit_type"));

        return "unit/unit_page";
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping(value = "/unit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_au(Unit record, String _workTime, HttpServletRequest request) {

        Integer id = record.getId();
        if(StringUtils.isNotBlank(_workTime)){
            record.setWorkTime(DateUtils.parseDate(_workTime, DateUtils.YYYY_MM_DD));
        }

        if (unitService.idDuplicate(id, record.getCode())) {
            return failed("单位编码重复");
        }

        if (id == null) {

            record.setCreateTime(new Date());
            record.setStatus(SystemConstants.UNIT_STATUS_RUN);
            unitService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加单位：%s", record.getId()));
        } else {

            unitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping("/unit_au")
    public String unit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Unit unit = unitMapper.selectByPrimaryKey(id);
            modelMap.put("unit", unit);
        }
        return "unit/unit_au";
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            unitService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("unit:abolish")
    @RequestMapping(value = "/unit_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_abolish(Integer id) {

        if (id != null) {

            Unit record = new Unit();
            record.setId(id);
            record.setStatus(SystemConstants.UNIT_STATUS_HISTORY);
            unitService.updateByPrimaryKeySelective(record);

            logger.info("abolish Unit:" + id);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            
            unitService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:changeOrder")
    @RequestMapping(value = "/unit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_changeOrder(Integer id, byte status, Integer addNum, HttpServletRequest request) {

        unitService.changeOrder(id, status, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "单位调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unit_export(UnitExample example, HttpServletResponse response) {

        List<Unit> units = unitMapper.selectByExample(example);
        int rownum = unitMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"单位编号","单位名称","单位类型","成立时间","单位网址","备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Unit unit = units.get(i);
            String[] values = {
                        unit.getCode(),
                        unit.getName(),
                        unit.getTypeId()+"",
                        DateUtils.formatDate(unit.getWorkTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                        unit.getUrl(),
                        unit.getRemark()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequiresPermissions("unit:history")
    @RequestMapping("/unit_history")
    public String unit_history(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            Unit unit = unitMapper.selectByPrimaryKey(id);
            modelMap.put("unit", unit);

            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            HistoryUnitExample example = new HistoryUnitExample();
            HistoryUnitExample.Criteria criteria = example.createCriteria().andUnitIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "sort_order", "desc"));

            int count = historyUnitMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<HistoryUnit> HistoryUnits = historyUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("historyUnits", HistoryUnits);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&unitId=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            modelMap.put("unitMap", unitService.findAll());
        }

        return "unit/unit_history";
    }

    @RequestMapping("/unit_selects")
    @ResponseBody
    public Map unit_selects(Integer pageSize, Integer pageNo, Byte status, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitExample example = new UnitExample();
        Criteria criteria = example.createCriteria();
        if(status!=null) criteria.andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = unitMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Unit> units = unitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        Map<Integer, MetaType> unitTypeMap = metaTypeService.metaTypes("mc_unit_type");
        List<Map<String, Object> > options = new ArrayList<>();
        if(null != units && units.size()>0){
            for(Unit unit:units){
                Map<String, Object> option = new HashMap<>();
                option.put("text", unit.getName());
                option.put("id", unit.getId());
                option.put("type", unitTypeMap.get(unit.getTypeId()).getName());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
