package controller.unit;

import controller.BaseController;
import domain.unit.HistoryUnit;
import domain.unit.HistoryUnitExample;
import domain.unit.HistoryUnitExample.Criteria;
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
public class HistoryUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("historyUnit:list")
    @RequestMapping("/historyUnit")
    public String historyUnit(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "unit_history_unit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Integer unitId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        HistoryUnitExample example = new HistoryUnitExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            historyUnit_export(example, response);
            return null;
        }

        int count = historyUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<HistoryUnit> HistoryUnits = historyUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("historyUnits", HistoryUnits);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (unitId!=null) {
            searchStr += "&unitId=" + unitId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "unit/historyUnit/historyUnit_page";
    }

    @RequiresPermissions("historyUnit:edit")
    @RequestMapping(value = "/historyUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_historyUnit_au(HistoryUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (record.getUnitId().intValue() == record.getOldUnitId()) {
            return failed("不能添加本单位为历史单位");
        }

        if (historyUnitService.idDuplicate(id, record.getUnitId(), record.getOldUnitId())) {
            return failed("该历史单位已经存在，请不要重复添加");
        }

        if (id == null) {

            record.setCreateTime(new Date());
            historyUnitService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加历史单位：%s", record.getId()));
        } else {

            HistoryUnit historyUnit = historyUnitMapper.selectByPrimaryKey(id);
            historyUnitService.updateByPrimaryKeySelective(record, historyUnit.getUnitId());
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新历史单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("historyUnit:edit")
    @RequestMapping("/historyUnit_au")
    public String historyUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            HistoryUnit historyUnit = historyUnitMapper.selectByPrimaryKey(id);
            modelMap.put("historyUnit", historyUnit);
        }
        return "unit/historyUnit/historyUnit_au";
    }

    @RequiresPermissions("historyUnit:del")
    @RequestMapping(value = "/historyUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_historyUnit_del(Integer id, HttpServletRequest request) {

        if (id != null) {
            HistoryUnit historyUnit = historyUnitMapper.selectByPrimaryKey(id);
            historyUnitService.del(id, historyUnit.getUnitId());
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除历史单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("historyUnit:del")
    @RequestMapping(value = "/historyUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            historyUnitService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除历史单位：%s", new Object[]{ids}));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("historyUnit:changeOrder")
    @RequestMapping(value = "/historyUnit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_historyUnit_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        HistoryUnit historyUnit = historyUnitMapper.selectByPrimaryKey(id);
        historyUnitService.changeOrder(id, historyUnit.getUnitId(), addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "历史单位调序：%s, %s", id ,addNum));
        return success(FormUtils.SUCCESS);
    }

    public void historyUnit_export(HistoryUnitExample example, HttpServletResponse response) {

        List<HistoryUnit> historyUnits = historyUnitMapper.selectByExample(example);
        int rownum = historyUnitMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"历史单位"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            HistoryUnit historyUnit = historyUnits.get(i);
            String[] values = {
                        historyUnit.getOldUnitId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "历史单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
