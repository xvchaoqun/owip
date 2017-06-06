package controller.dispatch;

import controller.BaseController;
import domain.dispatch.DispatchUnitRelate;
import domain.dispatch.DispatchUnitRelateExample;
import domain.dispatch.DispatchUnitRelateExample.Criteria;
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
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DispatchUnitRelateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchUnitRelate:list")
    @RequestMapping("/dispatchUnitRelate")
    public String dispatchUnitRelate(HttpServletResponse response,
                                     @SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch_unit_relate") String sort,
                                     @OrderParam(required = false, defaultValue = "desc") String order,
                                     Integer dispatchUnitId,
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

        DispatchUnitRelateExample example = new DispatchUnitRelateExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (dispatchUnitId != null) {
            criteria.andDispatchUnitIdEqualTo(dispatchUnitId);
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            dispatchUnitRelate_export(example, response);
            return null;
        }

        int count = dispatchUnitRelateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchUnitRelate> DispatchUnitRelates = dispatchUnitRelateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("dispatchUnitRelates", DispatchUnitRelates);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (dispatchUnitId != null) {
            searchStr += "&dispatchUnitId=" + dispatchUnitId;
        }
        if (unitId != null) {
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
        return "dispatch/dispatchUnitRelate/dispatchUnitRelate_page";
    }

    @RequiresPermissions("dispatchUnitRelate:edit")
    @RequestMapping(value = "/dispatchUnitRelate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchUnitRelate_au(DispatchUnitRelate record, HttpServletRequest request) {

        Integer id = record.getId();

        if (dispatchUnitRelateService.idDuplicate(id, record.getDispatchUnitId(), record.getUnitId())) {
            return failed("添加重复");
        }
        if (id == null) {

            dispatchUnitRelateService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加单位发文关联单位：%s", record.getId()));
        } else {

            dispatchUnitRelateService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新单位发文关联单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchUnitRelate:edit")
    @RequestMapping("/dispatchUnitRelate_au")
    public String dispatchUnitRelate_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DispatchUnitRelate dispatchUnitRelate = dispatchUnitRelateMapper.selectByPrimaryKey(id);
            modelMap.put("dispatchUnitRelate", dispatchUnitRelate);
        }
        return "dispatch/dispatchUnitRelate/dispatchUnitRelate_au";
    }

    @RequiresPermissions("dispatchUnitRelate:del")
    @RequestMapping(value = "/dispatchUnitRelate_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchUnitRelate_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dispatchUnitRelateService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除单位发文关联单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchUnitRelate:del")
    @RequestMapping(value = "/dispatchUnitRelate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchUnitRelateService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除单位发文关联单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchUnitRelate:changeOrder")
    @RequestMapping(value = "/dispatchUnitRelate_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchUnitRelate_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        DispatchUnitRelate dispatchUnitRelate = dispatchUnitRelateMapper.selectByPrimaryKey(id);
        dispatchUnitRelateService.changeOrder(id, dispatchUnitRelate.getDispatchUnitId(), addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "单位发文关联单位调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchUnitRelate_export(DispatchUnitRelateExample example, HttpServletResponse response) {

        List<DispatchUnitRelate> dispatchUnitRelates = dispatchUnitRelateMapper.selectByExample(example);
        int rownum = dispatchUnitRelateMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"单位发文", "关联单位"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            DispatchUnitRelate dispatchUnitRelate = dispatchUnitRelates.get(i);
            String[] values = {
                    dispatchUnitRelate.getDispatchUnitId() + "",
                    dispatchUnitRelate.getUnitId() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "单位发文关联单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    /*@RequestMapping("/dispatchUnitRelate_selects")
    @ResponseBody
    public Map dispatchUnitRelate_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchUnitRelateExample example = new DispatchUnitRelateExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = dispatchUnitRelateMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DispatchUnitRelate> dispatchUnitRelates = dispatchUnitRelateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != dispatchUnitRelates && dispatchUnitRelates.size()>0){

            for(DispatchUnitRelate dispatchUnitRelate:dispatchUnitRelates){

                Select2Option option = new Select2Option();
                option.setText(dispatchUnitRelate.getName());
                option.setId(dispatchUnitRelate.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
