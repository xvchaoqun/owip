package controller;

import domain.UnitTransferItem;
import domain.UnitTransferItemExample;
import domain.UnitTransferItemExample.Criteria;
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
public class UnitTransferItemController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unitTransferItem:list")
    @RequestMapping("/unitTransferItem")
    public String unitTransferItem() {

        return "index";
    }
    @RequiresPermissions("unitTransferItem:list")
    @RequestMapping("/unitTransferItem_page")
    public String unitTransferItem_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer transferId,
                                    Integer dispatchUnitId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitTransferItemExample example = new UnitTransferItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (transferId!=null) {
            modelMap.put("unitTransfer", unitTransferService.findAll().get(transferId));
            criteria.andTransferIdEqualTo(transferId);
        }
        if (dispatchUnitId!=null) {
            criteria.andDispatchUnitIdEqualTo(dispatchUnitId);
        }

        if (export == 1) {
            unitTransferItem_export(example, response);
            return null;
        }

        int count = unitTransferItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitTransferItem> UnitTransferItems = unitTransferItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("unitTransferItems", UnitTransferItems);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (transferId!=null) {
            searchStr += "&transferId=" + transferId;
        }
        if (dispatchUnitId!=null) {
            searchStr += "&dispatchUnitId=" + dispatchUnitId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "unitTransferItem/unitTransferItem_page";
    }

    @RequiresPermissions("unitTransferItem:edit")
    @RequestMapping(value = "/unitTransferItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransferItem_au(UnitTransferItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (unitTransferItemService.idDuplicate(id, record.getTransferId(), record.getDispatchUnitId())) {
            return failed("添加重复");
        }
        if (id == null) {
            unitTransferItemService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加单位变更关联的单位发文：%s", record.getId()));
        } else {

            unitTransferItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新单位变更关联的单位发文：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransferItem:edit")
    @RequestMapping("/unitTransferItem_au")
    public String unitTransferItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            UnitTransferItem unitTransferItem = unitTransferItemMapper.selectByPrimaryKey(id);
            modelMap.put("unitTransferItem", unitTransferItem);

            modelMap.put("unitTransfer", unitTransferService.findAll().get(unitTransferItem.getTransferId()));
        }
        return "unitTransferItem/unitTransferItem_au";
    }

    @RequiresPermissions("unitTransferItem:del")
    @RequestMapping(value = "/unitTransferItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransferItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitTransferItemService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除单位变更关联的单位发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransferItem:del")
    @RequestMapping(value = "/unitTransferItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            unitTransferItemService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除单位变更关联的单位发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitTransferItem:changeOrder")
    @RequestMapping(value = "/unitTransferItem_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTransferItem_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitTransferItemService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "单位变更关联的单位发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitTransferItem_export(UnitTransferItemExample example, HttpServletResponse response) {

        List<UnitTransferItem> unitTransferItems = unitTransferItemMapper.selectByExample(example);
        int rownum = unitTransferItemMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属单位变更","相关发文"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitTransferItem unitTransferItem = unitTransferItems.get(i);
            String[] values = {
                        unitTransferItem.getTransferId()+"",
                                            unitTransferItem.getDispatchUnitId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "单位变更关联的单位发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
