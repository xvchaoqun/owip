package controller.cpc;

import controller.BaseController;
import domain.base.MetaType;
import domain.cadre.CadrePost;
import domain.cpc.CpcAllocation;
import domain.cpc.CpcAllocationExample;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import service.cpc.CpcInfoBean;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CpcAllocationController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping("/cpcAllocationSetting")
    public String cpcAllocationSetting(@RequestParam(required = false, value = "unitIds") Integer[] unitIds, ModelMap modelMap) {

        Map<Integer, Unit> unitMap = unitService.findAll();
        List<Unit> unitList = new ArrayList<>();
        for (Integer unitId : unitIds) {
            unitList.add(unitMap.get(unitId));
        }
        modelMap.put("units", unitList);

        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        List<MetaType> adminLevels = new ArrayList<>();
        adminLevels.add(metaTypeMap.get("mt_admin_level_main"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_vice"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_none"));
        modelMap.put("adminLevels", adminLevels);

        CpcAllocationExample example = new CpcAllocationExample();
        example.createCriteria().andUnitIdIn(Arrays.asList(unitIds));
        List<CpcAllocation> cpcAllocations = cpcAllocationMapper.selectByExample(example);
        for (CpcAllocation cpcAllocation : cpcAllocations) {

            modelMap.put("total_" + cpcAllocation.getUnitId() + "_" + cpcAllocation.getAdminLevelId(), cpcAllocation.getNum());
        }

        return "cpc/cpcAllocation/cpcAllocationSetting";
    }

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping(value = "/cpcAllocationSetting", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cpcAllocationSetting(HttpServletRequest request) {

        Map<Integer, Unit> unitMap = unitService.findAll();

        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        List<MetaType> adminLevels = new ArrayList<>();
        adminLevels.add(metaTypeMap.get("mt_admin_level_main"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_vice"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_none"));

        List<CpcAllocation> records = new ArrayList<>();
        for (Map.Entry<Integer, Unit> _unit : unitMap.entrySet()) {
            for (MetaType adminLevel : adminLevels) {

                String _num = request.getParameter("total_" + _unit.getKey() + "_" + adminLevel.getId());
                if (NumberUtils.isDigits(_num)) {
                    Integer num = Integer.valueOf(_num);
                    if (num >= 0) {
                        CpcAllocation record = new CpcAllocation();
                        record.setNum(num);
                        record.setUnitId(_unit.getKey());
                        record.setAdminLevelId(adminLevel.getId());

                        records.add(record);
                    }
                }
            }
        }

        cpcAllocationService.update(records);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部职数配置情况：%s", JSONUtils.toString(records)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping("/cpcAllocation_selectUnits")
    public String cpcAllocation_selectUnits() {

        return "cpc/cpcAllocation/cpcAllocation_selectUnits";
    }

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation")
    public String cpcAllocation(
            @RequestParam(required = false, defaultValue = "1") Byte type,
            @RequestParam(required = false, defaultValue = "0") int export,
            ModelMap modelMap, HttpServletResponse response) throws IOException {

        modelMap.put("type", type);

        if (type == 1) {
            if (export == 1) {
                XSSFWorkbook wb = cpcAllocationService.cpcInfo_Xlsx();

                String fileName = "北京师范大学内设机构干部配备情况（" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "）";
                ExportHelper.output(wb, fileName + ".xlsx", response);
                return null;
            }

            List<CpcInfoBean> beans = cpcAllocationService.cpcInfo_data();
            modelMap.put("beans", beans);
        } else if (type == 2) {

            if (export == 1) {
                XSSFWorkbook wb = cpcAllocationService.cpcStat_Xlsx();

                String fileName = "北京师范大学内设机构干部配备统计表（" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "）";
                ExportHelper.output(wb, fileName + ".xlsx", response);
                return null;
            }

            Map<String, List<Integer>> cpcStatDataMap = cpcAllocationService.cpcStat_data();
            modelMap.put("jgList", cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_JG));
            modelMap.put("xyList", cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_XY));
            modelMap.put("fsList", cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_FS));
            modelMap.put("totalList", cpcStatDataMap.get("total"));
        }

        return "cpc/cpcAllocation/cpcAllocation_page";
    }

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation_cadres")
    public String cpcAllocation_cadres(Integer adminLevelId, boolean isMainPost, String unitType, ModelMap modelMap) {

        List<CadrePost> cadrePosts = iCadreMapper.findCadrePostsByUnitType(adminLevelId, isMainPost, unitType.trim());
        modelMap.put("cadrePosts", cadrePosts);

        modelMap.put("unitType", SystemConstants.UNIT_TYPE_ATTR_MAP.get(unitType.trim()));
        modelMap.put("adminLevel", metaTypeService.findAll().get(adminLevelId));
        modelMap.put("isMainPost", isMainPost);

        return "cpc/cpcAllocation/cpcAllocation_cadres";
    }

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation_selectUnits_tree")
    @ResponseBody
    public Map cpcAllocation_selectUnits_tree() throws IOException {

        Map<Integer, Map<Integer, Integer>> unitAdminLevelMap = cpcAllocationService.getUnitAdminLevelMap();
        TreeNode tree = unitService.getTree(SystemConstants.UNIT_STATUS_RUN, unitAdminLevelMap.keySet());

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping(value = "/cpcAllocation_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cpcAllocation_au(int unitId, HttpServletRequest request) {


        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        List<MetaType> adminLevels = new ArrayList<>();
        adminLevels.add(metaTypeMap.get("mt_admin_level_main"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_vice"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_none"));

        List<CpcAllocation> records = new ArrayList<>();

        for (MetaType adminLevel : adminLevels) {

            int num = 0;
            String _num = request.getParameter("adminLevel_" + adminLevel.getId());
            if (NumberUtils.isDigits(_num)) {
                num = Integer.valueOf(_num);
            }

            CpcAllocation record = new CpcAllocation();
            record.setNum(Math.max(num, 0));
            record.setUnitId(unitId);
            record.setAdminLevelId(adminLevel.getId());

            records.add(record);
        }

        cpcAllocationService.update(records);

        Unit unit = unitService.findAll().get(unitId);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部职数配置情况【%s】：%s", unit.getName(), JSONUtils.toString(records)));


        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping("/cpcAllocation_au")
    public String cpcAllocation_au(int unitId, ModelMap modelMap) {

        Map<Integer, Integer> cpcAdminLevelMap = cpcAllocationService.getCpcAdminLevelMap(unitId);
        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        /*MetaType mainMetaType = metaTypeMap.get("mt_admin_level_main");
        MetaType viceMetaType = metaTypeMap.get("mt_admin_level_vice");
        MetaType noneMetaType = metaTypeMap.get("mt_admin_level_none");

        modelMap.put("main_num", unitAdminLevelMap.get(unitId + "_" + mainMetaType.getId()));
        modelMap.put("vice_num", unitAdminLevelMap.get(unitId + "_" + viceMetaType.getId()));
        modelMap.put("none_num", unitAdminLevelMap.get(unitId + "_" + noneMetaType.getId()));*/

        List<MetaType> adminLevels = new ArrayList<>();
        adminLevels.add(metaTypeMap.get("mt_admin_level_main"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_vice"));
        adminLevels.add(metaTypeMap.get("mt_admin_level_none"));
        modelMap.put("adminLevels", adminLevels);

        modelMap.put("unit", unitService.findAll().get(unitId));

        modelMap.put("cpcAdminLevelMap", cpcAdminLevelMap);

        return "cpc/cpcAllocation/cpcAllocation_au";
    }

   /* @RequiresPermissions("cpcAllocation:del")
    @RequestMapping(value = "/cpcAllocation_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cpcAllocation_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cpcAllocationService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部职数配置情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cpcAllocation:del")
    @RequestMapping(value = "/cpcAllocation_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "unitIds") Integer[] unitIds, ModelMap modelMap) {


        if (null != unitIds && unitIds.length > 0) {
            cpcAllocationService.batchDel(unitIds);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部职数配置情况：%s", StringUtils.join(unitIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cpcAllocation_export(CpcAllocationExample example, HttpServletResponse response) {

        List<CpcAllocation> records = cpcAllocationMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"单位", "行政级别", "数量"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CpcAllocation record = records.get(i);
            String[] values = {
                    record.getUnitId() + "",
                    record.getAdminLevelId() + "",
                    record.getNum() + ""
            };
            valuesList.add(values);
        }
        String fileName = "干部职数配置情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
