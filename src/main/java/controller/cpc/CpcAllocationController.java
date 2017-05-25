package controller.cpc;

import controller.BaseController;
import domain.base.MetaType;
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
import service.cpc.CpcAllocationBean;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CpcAllocationController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cpcAllocation:list")
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

    @RequiresPermissions("cpcAllocation:list")
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

                String total = request.getParameter("total_" + _unit.getKey() + "_" + adminLevel.getId());
                if (NumberUtils.isDigits(total)) {
                    Integer num = Integer.valueOf(total);
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

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation_selectUnits")
    public String cpcAllocation_selectUnits() {

        return "cpc/cpcAllocation/cpcAllocation_selectUnits";
    }

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation")
    public String cpcAllocation() {

        return "index";
    }

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation_page")
    public String cpcAllocation_page( @RequestParam(required = false, defaultValue = "0")int export,
                                      ModelMap modelMap, HttpServletResponse response) throws IOException {


        if(export==1){
            XSSFWorkbook wb = cpcAllocationService.toXlsx();
            try {
                String fileName = "北京师范大学内设机构干部配备情况（" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "）";
                ServletOutputStream outputStream = response.getOutputStream();
                fileName = new String(fileName.getBytes(), "ISO8859_1");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
                wb.write(outputStream);
                outputStream.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        List<CpcAllocationBean> beans =cpcAllocationService.statCpc();
        modelMap.put("beans", beans);

        return "cpc/cpcAllocation/cpcAllocation_page";
    }

    /*@RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation_data")
    public void cpcAllocation_data(HttpServletResponse response,
                                    Integer unitId,
                                    Integer adminLevelId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CpcAllocationExample example = new CpcAllocationExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (adminLevelId!=null) {
            criteria.andAdminLevelIdEqualTo(adminLevelId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cpcAllocation_export(example, response);
            return;
        }

        long count = cpcAllocationMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CpcAllocation> records= cpcAllocationMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cpcAllocation.class, cpcAllocationMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }*/

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping(value = "/cpcAllocation_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cpcAllocation_au(CpcAllocation record, HttpServletRequest request) {

        Integer id = record.getId();


        if (id == null) {
            cpcAllocationService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部职数配置情况：%s", record.getId()));
        } else {

            cpcAllocationService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部职数配置情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping("/cpcAllocation_au")
    public String cpcAllocation_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CpcAllocation cpcAllocation = cpcAllocationMapper.selectByPrimaryKey(id);
            modelMap.put("cpcAllocation", cpcAllocation);
        }
        return "cpc/cpcAllocation/cpcAllocation_au";
    }

    @RequiresPermissions("cpcAllocation:del")
    @RequestMapping(value = "/cpcAllocation_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cpcAllocation_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cpcAllocationService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部职数配置情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cpcAllocation:del")
    @RequestMapping(value = "/cpcAllocation_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cpcAllocationService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部职数配置情况：%s", StringUtils.join(ids, ",")));
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
