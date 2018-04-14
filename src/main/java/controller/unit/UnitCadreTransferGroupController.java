package controller.unit;

import controller.BaseController;
import domain.unit.UnitCadreTransferGroup;
import domain.unit.UnitCadreTransferGroupExample;
import domain.unit.UnitCadreTransferGroupExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UnitCadreTransferGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unitCadreTransferGroup:list")
    @RequestMapping("/unitCadreTransferGroup")
    public String unitCadreTransferGroup(HttpServletResponse response,
                                         @SortParam(required = false, defaultValue = "sort_order", tableName = "unit_cadre_transfer_group") String sort,
                                         @OrderParam(required = false, defaultValue = "desc") String order,
                                         Integer unitId,
                                         String name,
                                         @RequestParam(required = false, defaultValue = "0") int export,
                                         Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitCadreTransferGroupExample example = new UnitCadreTransferGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            unitCadreTransferGroup_export(example, response);
            return null;
        }

        int count = unitCadreTransferGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitCadreTransferGroup> UnitCadreTransferGroups = unitCadreTransferGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("unitCadreTransferGroups", UnitCadreTransferGroups);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (unitId != null) {
            searchStr += "&unitId=" + unitId;
        }
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

        modelMap.put("unitMap", unitService.findAll());

        return "unit/unitCadreTransferGroup/unitCadreTransferGroup_page";
    }

    @RequiresPermissions("unitCadreTransferGroup:edit")
    @RequestMapping(value = "/unitCadreTransferGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransferGroup_au(UnitCadreTransferGroup record, HttpServletRequest request) {

        Integer id = record.getId();

        if (unitCadreTransferGroupService.idDuplicate(id, record.getUnitId(), record.getName())) {
            return failed("添加重复");
        }
        if (id == null) {
            unitCadreTransferGroupService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加单位任免分组：%s", record.getId()));
        } else {

            unitCadreTransferGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新单位任免分组：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransferGroup:edit")
    @RequestMapping("/unitCadreTransferGroup_au")
    public String unitCadreTransferGroup_au(Integer id, int unitId, ModelMap modelMap) {

        modelMap.put("unitId", unitId);
        if (id != null) {
            UnitCadreTransferGroup unitCadreTransferGroup = unitCadreTransferGroupMapper.selectByPrimaryKey(id);
            modelMap.put("unitCadreTransferGroup", unitCadreTransferGroup);
            modelMap.put("unitId", unitCadreTransferGroup.getUnitId());
        }
        modelMap.put("unitMap", unitService.findAll());
        return "unit/unitCadreTransferGroup/unitCadreTransferGroup_au";
    }

    @RequiresPermissions("unitCadreTransferGroup:del")
    @RequestMapping(value = "/unitCadreTransferGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransferGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitCadreTransferGroupService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除单位任免分组：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransferGroup:del")
    @RequestMapping(value = "/unitCadreTransferGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            unitCadreTransferGroupService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除单位任免分组：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransferGroup:changeOrder")
    @RequestMapping(value = "/unitCadreTransferGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransferGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitCadreTransferGroupService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "单位任免分组调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitCadreTransferGroup_export(UnitCadreTransferGroupExample example, HttpServletResponse response) {

        List<UnitCadreTransferGroup> unitCadreTransferGroups = unitCadreTransferGroupMapper.selectByExample(example);
        int rownum = unitCadreTransferGroupMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属单位", "分组名称"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitCadreTransferGroup unitCadreTransferGroup = unitCadreTransferGroups.get(i);
            String[] values = {
                    unitCadreTransferGroup.getUnitId() + "",
                    unitCadreTransferGroup.getName()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "单位任免分组_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    @RequestMapping("/unitCadreTransferGroup_selects")
    @ResponseBody
    public Map unitCadreTransferGroup_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitCadreTransferGroupExample example = new UnitCadreTransferGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }

        int count = unitCadreTransferGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitCadreTransferGroup> unitCadreTransferGroups = unitCadreTransferGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != unitCadreTransferGroups && unitCadreTransferGroups.size() > 0) {

            for (UnitCadreTransferGroup unitCadreTransferGroup : unitCadreTransferGroups) {

                Select2Option option = new Select2Option();
                option.setText(unitCadreTransferGroup.getName());
                option.setId(unitCadreTransferGroup.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
