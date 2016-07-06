package controller.unit;

import controller.BaseController;
import domain.unit.UnitAdminGroup;
import domain.unit.UnitAdminGroupExample;
import domain.unit.UnitAdminGroupExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UnitAdminGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unitAdminGroup:list")
    @RequestMapping("/unitAdminGroup")
    public String unitAdminGroup() {

        return "index";
    }
    @RequiresPermissions("unitAdminGroup:list")
    @RequestMapping("/unitAdminGroup_page")
    public String unitAdminGroup_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_unit_admin_group") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String name,
                                    int unitId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitAdminGroupExample example = new UnitAdminGroupExample();
        Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            unitAdminGroup_export(example, response);
            return null;
        }

        int count = unitAdminGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitAdminGroup> unitAdminGroups = unitAdminGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("unitAdminGroups", unitAdminGroups);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "unit/unitAdminGroup/unitAdminGroup_page";
    }

    @RequiresPermissions("unitAdminGroup:edit")
    @RequestMapping(value = "/unitAdminGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitAdminGroup_au(UnitAdminGroup record,
                                    String _tranTime,
                                    String _actualTranTime,
                                    String _appointTime,
                                    HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_tranTime)){
            record.setTranTime(DateUtils.parseDate(_tranTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_actualTranTime)){
            record.setActualTranTime(DateUtils.parseDate(_actualTranTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_appointTime)){
            record.setAppointTime(DateUtils.parseDate(_appointTime, DateUtils.YYYY_MM_DD));
        }
        record.setIsPresent((record.getIsPresent() == null) ? false : record.getIsPresent());

        if (id == null) {
            unitAdminGroupService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加单位行政班子：%s", record.getId()));
        } else {

            if(record.getFid()!=null && record.getFid().intValue()==record.getId()){
                return failed("不能选择自身为上一届班子");
            }

            unitAdminGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新单位行政班子：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitAdminGroup:edit")
    @RequestMapping("/unitAdminGroup_au")
    public String unitAdminGroup_au(Integer id,int unitId, ModelMap modelMap) {

        if (id != null) {
            UnitAdminGroup unitAdminGroup = unitAdminGroupMapper.selectByPrimaryKey(id);
            unitId = unitAdminGroup.getUnitId();
            modelMap.put("unitAdminGroup", unitAdminGroup);
            if(unitAdminGroup.getFid()!=null){
                modelMap.put("fUnitAdminGroup", unitAdminGroupMapper.selectByPrimaryKey(unitAdminGroup.getFid()));
            }

        }

        modelMap.put("unit", unitService.findAll().get(unitId));
        return "unit/unitAdminGroup/unitAdminGroup_au";
    }

    @RequiresPermissions("unitAdminGroup:del")
    @RequestMapping(value = "/unitAdminGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitAdminGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitAdminGroupService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除单位行政班子：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitAdminGroup:del")
    @RequestMapping(value = "/unitAdminGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            unitAdminGroupService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除单位行政班子：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitAdminGroup:changeOrder")
    @RequestMapping(value = "/unitAdminGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitAdminGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitAdminGroupService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "单位行政班子调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitAdminGroup_export(UnitAdminGroupExample example, HttpServletResponse response) {

        List<UnitAdminGroup> unitAdminGroups = unitAdminGroupMapper.selectByExample(example);
        int rownum = unitAdminGroupMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"上一届","名称","应换届时间","实际换届时间","任命时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitAdminGroup unitAdminGroup = unitAdminGroups.get(i);
            String[] values = {
                        unitAdminGroup.getFid()+"",
                                            unitAdminGroup.getName(),
                                            DateUtils.formatDate(unitAdminGroup.getTranTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(unitAdminGroup.getActualTranTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(unitAdminGroup.getAppointTime(), DateUtils.YYYY_MM_DD)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "单位行政班子_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/unitAdminGroup_selects")
    @ResponseBody
    public Map unitAdminGroup_selects(Integer pageSize, Integer pageNo, int unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitAdminGroupExample example = new UnitAdminGroupExample();
        Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = unitAdminGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<UnitAdminGroup> unitAdminGroups = unitAdminGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != unitAdminGroups && unitAdminGroups.size()>0){

            for(UnitAdminGroup unitAdminGroup:unitAdminGroups){

                Select2Option option = new Select2Option();
                option.setText(unitAdminGroup.getName());
                option.setId(unitAdminGroup.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
