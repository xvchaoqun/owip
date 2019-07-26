package controller.unit;

import controller.BaseController;
import domain.base.MetaType;
import domain.dispatch.DispatchUnit;
import domain.dispatch.DispatchUnitView;
import domain.dispatch.DispatchUnitViewExample;
import domain.unit.*;
import domain.unit.UnitExample.Criteria;
import ext.service.ExtUnitService;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.unit.UnitExportService;
import shiro.ShiroHelper;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UnitExportService unitExportService;
    @Autowired
    private ExtUnitService extUnitService;

    // 基本信息
    @RequiresPermissions("unit:view")
    @RequestMapping("/unit_base")
    public String unit_base(Integer id, ModelMap modelMap) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        modelMap.put("unit", unit);

        Integer dispatchUnitId = unit.getDispatchUnitId();
        if (dispatchUnitId != null) {
            DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
            if (dispatchUnit != null)
                modelMap.put("dispatch", dispatchUnit.getDispatch());
        }

        // 正在运转单位
        //modelMap.put("runUnits", unitService.findRunUnits(id));
        // 历史单位
        modelMap.put("historyUnits", unitService.findHistoryUnits(id));

        return "unit/unit_base";
    }

    @RequiresPermissions("unit:view")
    @RequestMapping("/unit_view")
    public String unit_view(HttpServletResponse response, int id, ModelMap modelMap) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        modelMap.put("unit", unit);

        return "unit/unit_view";
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/unit")
    public String unit(@RequestParam(required = false, defaultValue = "1") Byte status,
                       @RequestParam(required = false, defaultValue = "0") int export,
                       ModelMap modelMap, HttpServletResponse response) throws IOException {

        modelMap.put("status", status);
        if (status == 3) {

            if (export == 1) {

                XSSFWorkbook wb = unitExportService.exportSchoolUnits();
                ExportHelper.output(wb, "学校单位列表.xlsx", response);
                return null;
            }
            modelMap.put("schoolUnits", extUnitService.getSchoolUnitMap().values());

            return "unit/school_units";
        }

        return "unit/unit_page";
    }

    @RequiresPermissions("unit:list")
    @RequestMapping(value = "/refreshSchoolUnits", method = RequestMethod.POST)
    @ResponseBody
    public Map do_refreshSchoolUnits() {

        extUnitService.refreshSchoolUnits();
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/unit_data")
    @ResponseBody
    public void unit_data(HttpServletResponse response,
                          @RequestParam(required = false, defaultValue = "1") Byte status,
                          String code,
                          String name,
                          Integer typeId,
                          @RequestParam(required = false, defaultValue = "0") int export,
                          @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                          Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitViewExample example = new UnitViewExample();
        UnitViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            XSSFWorkbook wb = unitExportService.toXlsx(example);
            ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName() + "单位一览表.xlsx", response);
            return;
        } else if (export == 2) {

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            unit_export(example, response);
            return;
        }

        long count = unitViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<UnitView> records = unitViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping(value = "/unit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_au(Unit record, String _workTime, HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        if (StringUtils.isNotBlank(_workTime)) {
            record.setWorkTime(DateUtils.parseDate(_workTime, DateUtils.YYYY_MM_DD));
        }

        if (StringUtils.isNotBlank(record.getCode())
                && unitService.idDuplicate(id, record.getCode())) {
            return failed("单位编码重复");
        }

        if (id == null) {

            record.setCreateTime(new Date());
            record.setStatus(SystemConstants.UNIT_STATUS_RUN);
            unitService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加单位：%s", record.getId()));
        } else {

            unitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping("/unit_au")
    public String unit_au(Integer id, Boolean update, ModelMap modelMap) {

        if (id != null) {
            Unit unit = unitMapper.selectByPrimaryKey(id);
            modelMap.put("unit", unit);
        }
        if (BooleanUtils.isTrue(update)) {
            DispatchUnitViewExample example = new DispatchUnitViewExample();
            example.createCriteria().andCategoryContain(DispatchConstants.DISPATCH_CATEGORY_UNIT, true)
                    .andUnitIdContain(Arrays.asList(id));
            example.setOrderByClause("pub_time asc");
            List<DispatchUnitView> dispatchUnits = dispatchUnitViewMapper.selectByExample(example);
            modelMap.put("dispatchUnits", dispatchUnits);

            return "unit/unit_update";
        }

        modelMap.put("schoolUnits", extUnitService.getSchoolUnitMap().values());

        return "unit/unit_au";
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            unitService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:abolish")
    @RequestMapping(value = "/unit_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_abolish(@RequestParam(value = "ids[]") Integer[] ids,
                               @RequestParam(required = false, defaultValue = "1") boolean isAbolish) {

        unitService.abolish(ids, isAbolish);
        logger.info("abolish Unit:" + StringUtils.join(ids, ","));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map unit_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {

            unitService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:changeOrder")
    @RequestMapping(value = "/unit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_changeOrder(Integer id, byte status, Integer addNum, HttpServletRequest request) {

        unitService.changeOrder(id, status, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "单位调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:history")
    @RequestMapping("/unit_history")
    public String unit_history(Integer id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

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

            if (id != null) {
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
        if (status != null) criteria.andStatusEqualTo(status);
        example.setOrderByClause("status asc, sort_order asc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = unitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Unit> units = unitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map<Integer, MetaType> unitTypeMap = metaTypeService.metaTypes("mc_unit_type");
        List<Map<String, Object>> options = new ArrayList<>();
        if (null != units && units.size() > 0) {
            for (Unit unit : units) {
                Map<String, Object> option = new HashMap<>();
                option.put("text", unit.getName());
                option.put("del", unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY);
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

    public void unit_export(UnitViewExample example, HttpServletResponse response) {

        List<UnitView> records = unitViewMapper.selectByExample(example);
        int rownum = records.size();
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"单位编号|100", "单位名称|350|left", "单位类型|150"}));

        boolean isUnitPostAdmin = ShiroHelper.isPermitted("unitPost:*");
        boolean hasKjCadre = CmTag.getBoolProperty("hasKjCadre");
        if (isUnitPostAdmin) {
            titles.addAll(new ArrayList<>(Arrays.asList(new String[]{"正处级岗位数|70",
                    "副处级岗位数|70", "无行政级别岗位数|70"})));
            if (hasKjCadre) {
                titles.addAll(new ArrayList<>(Arrays.asList(new String[]{"正科级岗位数|70", "副科级岗位数|70"})));
            }
            titles.addAll(new ArrayList<>(Arrays.asList(new String[]{"正处级干部职数|70",
                    "副处级干部职数|70", "无行政级别干部职数|90"})));
            if (hasKjCadre) {
                titles.addAll(new ArrayList<>(Arrays.asList(new String[]{"正科级干部职数|70", "副科级干部职数|70"})));
            }
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            UnitView record = records.get(i);
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    record.getCode(),
                    record.getName(),
                    metaTypeService.getName(record.getTypeId())
            }));

            if (isUnitPostAdmin) {
                values.addAll(new ArrayList<>(Arrays.asList(new String[]{
                        NumberUtils.trimToZero(record.getMainPostCount()) + "",
                        NumberUtils.trimToZero(record.getVicePostCount()) + "",
                        NumberUtils.trimToZero(record.getNonePostCount()) + ""})));
                if (hasKjCadre) {
                    values.addAll(new ArrayList<>(Arrays.asList(new String[]{
                            NumberUtils.trimToZero(record.getMainKjPostCount()) + "",
                            NumberUtils.trimToZero(record.getViceKjPostCount()) + ""})));
                }
                values.addAll(new ArrayList<>(Arrays.asList(new String[]{
                        NumberUtils.trimToZero(record.getMainCount()) + "",
                        NumberUtils.trimToZero(record.getViceCount()) + "",
                        NumberUtils.trimToZero(record.getNoneCount()) + ""})));
                if (hasKjCadre) {
                    values.addAll(new ArrayList<>(Arrays.asList(new String[]{
                            NumberUtils.trimToZero(record.getMainKjCount()) + "",
                            NumberUtils.trimToZero(record.getMainKjCount()) + ""})));
                }
            }

            valuesList.add(values);
        }

        String fileName = "单位列表(" + DateUtils.formatDate(new Date(), "yyyyMMddHH") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 导入单位
    @RequiresPermissions("unit:edit")
    @RequestMapping("/unit_import")
    public String unit_import() {

        return "unit/unit_import";
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping(value = "/unit_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);


        Map<String, Object> retMap = unitService.batchImport(xlsRows);
        int successCount = (int) retMap.get("success");
        List<Map<Integer, String>> failedXlsRows = (List<Map<Integer, String>>) retMap.get("failedXlsRows");
        int totalCount = xlsRows.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("failedXlsRows", failedXlsRows);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入单位成功，总共{0}条记录，其中成功导入{1}条记录，{2}条失败",
                totalCount, successCount, failedXlsRows.size()));

        return resultMap;
    }

    // 批量导入更新单位编码
    @RequiresPermissions("unit:edit")
    @RequestMapping("/unit_importCodes")
    public String unit_importCodes() {

        return "unit/unit_importCodes";
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping(value = "/unit_importCodes", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_importCodes(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);


        Map<String, Object> retMap = unitService.batchImportCodes(xlsRows);
        int successCount = (int) retMap.get("success");
        int totalCount = xlsRows.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "批量更新单位编码成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, successCount));

        return resultMap;
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/selectUnits_tree")
    @ResponseBody
    public Map selectUnits_tree(Byte status) throws IOException {

        TreeNode tree = unitService.getTree(status, null);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

}
