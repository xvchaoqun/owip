package controller;

import domain.UnitCadreTransferItem;
import domain.UnitCadreTransferItemExample;
import domain.UnitCadreTransferItemExample.Criteria;
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
public class UnitCadreTransferItemController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unitCadreTransferItem:list")
    @RequestMapping("/unitCadreTransferItem")
    public String unitCadreTransferItem() {

        return "index";
    }
    @RequiresPermissions("unitCadreTransferItem:list")
    @RequestMapping("/unitCadreTransferItem_page")
    public String unitCadreTransferItem_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "id") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer transferId,
                                    Integer dispatchCadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitCadreTransferItemExample example = new UnitCadreTransferItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (transferId!=null) {
            criteria.andTransferIdEqualTo(transferId);
        }
        if (dispatchCadreId!=null) {
            criteria.andDispatchCadreIdEqualTo(dispatchCadreId);
        }

        if (export == 1) {
            unitCadreTransferItem_export(example, response);
            return null;
        }

        int count = unitCadreTransferItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitCadreTransferItem> UnitCadreTransferItems = unitCadreTransferItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("unitCadreTransferItems", UnitCadreTransferItems);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (transferId!=null) {
            searchStr += "&transferId=" + transferId;
        }
        if (dispatchCadreId!=null) {
            searchStr += "&dispatchCadreId=" + dispatchCadreId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "unitCadreTransferItem/unitCadreTransferItem_page";
    }

    @RequiresPermissions("unitCadreTransferItem:edit")
    @RequestMapping(value = "/unitCadreTransferItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransferItem_au(UnitCadreTransferItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (unitCadreTransferItemService.idDuplicate(id, record.getTransferId(), record.getDispatchCadreId())) {
            return failed("添加重复");
        }
        if (id == null) {
            unitCadreTransferItemService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加单位任免记录关联发文：%s", record.getId()));
        } else {

            unitCadreTransferItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新单位任免记录关联发文：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransferItem:edit")
    @RequestMapping("/unitCadreTransferItem_au")
    public String unitCadreTransferItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            UnitCadreTransferItem unitCadreTransferItem = unitCadreTransferItemMapper.selectByPrimaryKey(id);
            modelMap.put("unitCadreTransferItem", unitCadreTransferItem);
        }
        return "unitCadreTransferItem/unitCadreTransferItem_au";
    }

    @RequiresPermissions("unitCadreTransferItem:del")
    @RequestMapping(value = "/unitCadreTransferItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransferItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitCadreTransferItemService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除单位任免记录关联发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransferItem:del")
    @RequestMapping(value = "/unitCadreTransferItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            unitCadreTransferItemService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除单位任免记录关联发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

/*
    @RequiresPermissions("unitCadreTransferItem:changeOrder")
    @RequestMapping(value = "/unitCadreTransferItem_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransferItem_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitCadreTransferItemService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "单位任免记录关联发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
*/

    public void unitCadreTransferItem_export(UnitCadreTransferItemExample example, HttpServletResponse response) {

        List<UnitCadreTransferItem> unitCadreTransferItems = unitCadreTransferItemMapper.selectByExample(example);
        int rownum = unitCadreTransferItemMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属任免","干部发文"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitCadreTransferItem unitCadreTransferItem = unitCadreTransferItems.get(i);
            String[] values = {
                        unitCadreTransferItem.getTransferId()+"",
                                            unitCadreTransferItem.getDispatchCadreId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "单位任免记录关联发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
