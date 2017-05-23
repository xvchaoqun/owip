package controller.cpc;

import controller.BaseController;
import domain.base.MetaType;
import domain.cpc.CpcAllocation;
import domain.cpc.CpcAllocationExample;
import domain.cpc.CpcAllocationExample.Criteria;
import domain.unit.Unit;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.utils.JSONUtils;

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

        return "cpc/cpcAllocation/cpcAllocationSetting";
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
    public String cpcAllocation_page(HttpServletResponse response,
    @SortParam(required = false, defaultValue = "sort_order", tableName = "cpc_allocation") String sort,
    @OrderParam(required = false, defaultValue = "desc") String order,
        Integer unitId,
        Integer postId,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "cpc/cpcAllocation/cpcAllocation_page";
    }

    @RequiresPermissions("cpcAllocation:list")
    @RequestMapping("/cpcAllocation_data")
    public void cpcAllocation_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "cpc_allocation") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer unitId,
                                    Integer postId,
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
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
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
    }

    @RequiresPermissions("cpcAllocation:edit")
    @RequestMapping(value = "/cpcAllocation_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cpcAllocation_au(CpcAllocation record, HttpServletRequest request) {

        Integer id = record.getId();


        if (id == null) {
            cpcAllocationService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加干部职数配置情况：%s", record.getId()));
        } else {

            cpcAllocationService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新干部职数配置情况：%s", record.getId()));
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
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除干部职数配置情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cpcAllocation:del")
    @RequestMapping(value = "/cpcAllocation_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cpcAllocationService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除干部职数配置情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cpcAllocation_export(CpcAllocationExample example, HttpServletResponse response) {

        List<CpcAllocation> records = cpcAllocationMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"单位","行政级别","数量"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CpcAllocation record = records.get(i);
            String[] values = {
                record.getUnitId()+"",
                            record.getPostId()+"",
                            record.getNum()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部职数配置情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
