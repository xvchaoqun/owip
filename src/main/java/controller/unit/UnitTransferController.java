package controller.unit;

import controller.BaseController;
import domain.dispatch.DispatchUnit;
import domain.unit.UnitTransfer;
import domain.unit.UnitTransferExample;
import domain.unit.UnitTransferExample.Criteria;
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
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UnitTransferController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("unitTransfer:edit")
    @RequestMapping("/unitTransfer_addDispatchs")
    public String unitTransfer_addDispatchs(HttpServletResponse response,
                                            int id, ModelMap modelMap) {

        Set<Integer> unitDispatchIdSet = new HashSet<>();
        UnitTransfer unitTransfer = unitTransferMapper.selectByPrimaryKey(id);

        String dispatchs = unitTransfer.getDispatchs();
        if (StringUtils.isNotBlank(dispatchs)) {
            for (String str : dispatchs.split(",")) {
                try {
                    unitDispatchIdSet.add(Integer.valueOf(str));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        modelMap.put("unitDispatchIdSet", unitDispatchIdSet);

        List<DispatchUnit> dispatchUnits = commonMapper.selectDispatchUnitList(unitTransfer.getUnitId());
        modelMap.put("dispatchUnits", dispatchUnits);

        return "unit/unitTransfer/unitTransfer_addDispatchs";
    }

    @RequestMapping(value = "/unitTransfer_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransfer_addDispatchs(HttpServletRequest request,
                                            int id,
                                            @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        UnitTransfer record = new UnitTransfer();
        record.setId(id);
        record.setDispatchs("-1");
        if (null != ids && ids.length > 0) {
            record.setDispatchs(StringUtils.join(ids, ","));
        }
        unitTransferMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改单位发文%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransfer:list")
    @RequestMapping("/unitTransfer")
    public String unitTransfer(HttpServletResponse response,
                               @SortParam(required = false, defaultValue = "sort_order", tableName = "unit_transfer") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,
                               Integer unitId,
                               String subject,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitTransferExample example = new UnitTransferExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (StringUtils.isNotBlank(subject)) {
            criteria.andSubjectLike("%" + subject + "%");
        }

        if (export == 1) {
            unitTransfer_export(example, response);
            return null;
        }

        int count = unitTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitTransfer> UnitTransfers = unitTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("unitTransfers", UnitTransfers);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (unitId != null) {
            searchStr += "&unitId=" + unitId;
        }
        if (StringUtils.isNotBlank(subject)) {
            searchStr += "&subject=" + subject;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        modelMap.put("unitMap", unitService.findAll());

        return "unit/unitTransfer/unitTransfer_page";
    }

    @RequiresPermissions("unitTransfer:edit")
    @RequestMapping(value = "/unitTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransfer_au(UnitTransfer record, String _pubTime, HttpServletRequest request) {

        Integer id = record.getId();

        record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));

        if (id == null) {
            unitTransferService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加单位变更：%s", record.getId()));
        } else {

            unitTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新单位变更：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransfer:edit")
    @RequestMapping("/unitTransfer_au")
    public String unitTransfer_au(Integer id, int unitId, ModelMap modelMap) {

        modelMap.put("unitId", unitId);
        if (id != null) {
            UnitTransfer unitTransfer = unitTransferMapper.selectByPrimaryKey(id);
            modelMap.put("unitTransfer", unitTransfer);
            modelMap.put("unitId", unitTransfer.getUnitId());
        }
        modelMap.put("unitMap", unitService.findAll());

        return "unit/unitTransfer/unitTransfer_au";
    }

    @RequiresPermissions("unitTransfer:del")
    @RequestMapping(value = "/unitTransfer_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransfer_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitTransferService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除单位变更：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransfer:del")
    @RequestMapping(value = "/unitTransfer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            unitTransferService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除单位变更：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransfer:changeOrder")
    @RequestMapping(value = "/unitTransfer_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransfer_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitTransferService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "单位变更调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitTransfer_export(UnitTransferExample example, HttpServletResponse response) {

        List<UnitTransfer> unitTransfers = unitTransferMapper.selectByExample(example);
        int rownum = unitTransferMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属单位", "文件主题"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitTransfer unitTransfer = unitTransfers.get(i);
            String[] values = {
                    unitTransfer.getUnitId() + "",
                    unitTransfer.getSubject()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "单位变更_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    @RequestMapping("/unitTransfer_selects")
    @ResponseBody
    public Map unitTransfer_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitTransferExample example = new UnitTransferExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andSubjectLike("%" + searchStr + "%");
        }

        int count = unitTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitTransfer> unitTransfers = unitTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != unitTransfers && unitTransfers.size() > 0) {

            for (UnitTransfer unitTransfer : unitTransfers) {

                Select2Option option = new Select2Option();
                option.setText(unitTransfer.getSubject());
                option.setId(unitTransfer.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
