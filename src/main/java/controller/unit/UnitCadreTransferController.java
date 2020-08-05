package controller.unit;

import controller.BaseController;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.unit.UnitCadreTransfer;
import domain.unit.UnitCadreTransferExample;
import domain.unit.UnitCadreTransferExample.Criteria;
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
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UnitCadreTransferController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("unitCadreTransfer:edit")
    @RequestMapping("/unitCadreTransfer_addDispatchs")
    public String unitCadreTransfer_addDispatchs(HttpServletResponse response,
                                                 int id, int unitId, ModelMap modelMap) {

        Set<Integer> unitCadreDispatchIdSet = new HashSet<>();
        UnitCadreTransfer unitCadreTransfer = unitCadreTransferMapper.selectByPrimaryKey(id);
        modelMap.put("unitCadreTransfer", unitCadreTransfer);
        String dispatchs = unitCadreTransfer.getDispatchs();
        if (StringUtils.isNotBlank(dispatchs)) {
            for (String str : dispatchs.split(",")) {
                try {
                    unitCadreDispatchIdSet.add(Integer.valueOf(str));
                } catch (Exception ex) {
                    logger.error("异常", ex);
                }
            }
        }
        modelMap.put("unitCadreDispatchIdSet", unitCadreDispatchIdSet);

        List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreByUnitIdList(unitId);
        modelMap.put("dispatchCadres", dispatchCadres);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));

        return "unit/unitCadreTransfer/unitCadreTransfer_addDispatchs";
    }

    @RequestMapping(value = "/unitCadreTransfer_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransfer_addDispatchs(HttpServletRequest request,
                                                 int id,
                                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        UnitCadreTransfer record = new UnitCadreTransfer();
        record.setId(id);
        record.setDispatchs("-1");
        if (null != ids && ids.length > 0) {
            record.setDispatchs(StringUtils.join(ids, ","));
        }
        unitCadreTransferMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改单位干部发文%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("unitCadreTransfer:list")
    @RequestMapping("/unitCadreTransfer")
    public String unitCadreTransfer(HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "sort_order", tableName = "unit_cadre_transfer") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer groupId,
                                    Integer cadreId,
                                    @RequestDateRange DateRange _appointTime,
                                    @RequestDateRange DateRange _dismissTime,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitCadreTransferExample example = new UnitCadreTransferExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (groupId != null) {
            modelMap.put("unitCadreTransferGroup", unitCadreTransferGroupMapper.selectByPrimaryKey(groupId));
            criteria.andGroupIdEqualTo(groupId);
        }
        if (cadreId != null) {
            modelMap.put("cadre", iCadreMapper.getCadre(cadreId));
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (_appointTime.getStart() != null) {
            criteria.andAppointTimeGreaterThanOrEqualTo(_appointTime.getStart());
        }

        if (_appointTime.getEnd() != null) {
            criteria.andAppointTimeLessThanOrEqualTo(_appointTime.getEnd());
        }
        if (_dismissTime.getStart() != null) {
            criteria.andDismissTimeGreaterThanOrEqualTo(_dismissTime.getStart());
        }

        if (_dismissTime.getEnd() != null) {
            criteria.andDismissTimeLessThanOrEqualTo(_dismissTime.getEnd());
        }

        if (export == 1) {
            unitCadreTransfer_export(example, response);
            return null;
        }

        int count = unitCadreTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitCadreTransfer> UnitCadreTransfers = unitCadreTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("unitCadreTransfers", UnitCadreTransfers);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (groupId != null) {
            searchStr += "&groupId=" + groupId;
        }
        if (cadreId != null) {
            searchStr += "&cadreId=" + cadreId;
        }

        searchStr += "&_appointTime=" + _appointTime.toString();

        searchStr += "&_dismissTime=" + _dismissTime.toString();

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("groupMap", unitCadreTransferGroupService.findAll());

        return "unit/unitCadreTransfer/unitCadreTransfer_page";
    }

    @RequiresPermissions("unitCadreTransfer:edit")
    @RequestMapping(value = "/unitCadreTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransfer_au(UnitCadreTransfer record, String _appointTime,
                                       String _dismissTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (unitCadreTransferService.idDuplicate(id, record.getGroupId(), record.getCadreId())) {
            return failed("添加重复");
        }

        record.setAppointTime(DateUtils.parseDate(_appointTime, DateUtils.YYYY_MM_DD));
        record.setDismissTime(DateUtils.parseDate(_dismissTime, DateUtils.YYYY_MM_DD));

        if (id == null) {
            unitCadreTransferService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加单位任免记录：%s", record.getId()));
        } else {

            unitCadreTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新单位任免记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransfer:edit")
    @RequestMapping("/unitCadreTransfer_au")
    public String unitCadreTransfer_au(Integer id, int groupId, ModelMap modelMap) {

        if (id != null) {
            UnitCadreTransfer unitCadreTransfer = unitCadreTransferMapper.selectByPrimaryKey(id);
            modelMap.put("unitCadreTransfer", unitCadreTransfer);
            groupId = unitCadreTransfer.getGroupId();
            modelMap.put("cadre", iCadreMapper.getCadre(unitCadreTransfer.getCadreId()));
        }
        modelMap.put("unitCadreTransferGroup", unitCadreTransferGroupMapper.
                selectByPrimaryKey(groupId));
        return "unit/unitCadreTransfer/unitCadreTransfer_au";
    }

    @RequiresPermissions("unitCadreTransfer:del")
    @RequestMapping(value = "/unitCadreTransfer_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransfer_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitCadreTransferService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除单位任免记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransfer:del")
    @RequestMapping(value = "/unitCadreTransfer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            unitCadreTransferService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除单位任免记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitCadreTransfer:changeOrder")
    @RequestMapping(value = "/unitCadreTransfer_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitCadreTransfer_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitCadreTransferService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "单位任免记录调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitCadreTransfer_export(UnitCadreTransferExample example, HttpServletResponse response) {

        List<UnitCadreTransfer> unitCadreTransfers = unitCadreTransferMapper.selectByExample(example);
        int rownum = unitCadreTransferMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属分组", "关联干部", "姓名", "免职日期", "备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitCadreTransfer unitCadreTransfer = unitCadreTransfers.get(i);
            String[] values = {
                    unitCadreTransfer.getGroupId() + "",
                    unitCadreTransfer.getCadreId() + "",
                    unitCadreTransfer.getName(),
                    DateUtils.formatDate(unitCadreTransfer.getDismissTime(), DateUtils.YYYY_MM_DD),
                    unitCadreTransfer.getRemark()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "单位任免记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    @RequestMapping("/unitCadreTransfer_selects")
    @ResponseBody
    public Map unitCadreTransfer_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitCadreTransferExample example = new UnitCadreTransferExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        int count = unitCadreTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitCadreTransfer> unitCadreTransfers = unitCadreTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != unitCadreTransfers && unitCadreTransfers.size() > 0) {

            for (UnitCadreTransfer unitCadreTransfer : unitCadreTransfers) {

                Select2Option option = new Select2Option();
                option.setText(unitCadreTransfer.getName());
                option.setId(unitCadreTransfer.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("unitTransfer:list")
    @RequestMapping("/unitCadreTransfer/dispatchDownload")
    public void dispatch_download(HttpServletRequest request,
                                  Integer id,
                                  boolean isPpt,
                                  HttpServletResponse response) throws IOException {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);

        if (dispatch != null) {
            String path = "";
            String filename = "";
            if (isPpt) {
                path = HtmlUtils.htmlEscape(dispatch.getPpt());
                filename = HtmlUtils.htmlEscape(dispatch.getPptName());
            }else {
                path = HtmlUtils.htmlUnescape(dispatch.getFile());
                filename = HtmlUtils.htmlUnescape(dispatch.getFileName());
            }
            DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
        }
    }
}
