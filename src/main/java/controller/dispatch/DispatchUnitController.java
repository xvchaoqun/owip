package controller.dispatch;

import controller.BaseController;
import domain.dispatch.*;
import domain.dispatch.DispatchUnitExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.DispatchMixin;
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
import sys.tags.CmTag;
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
public class DispatchUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchUnit:list")
    @RequestMapping("/dispatchUnit")
    public String dispatchUnit(ModelMap modelMap) {

        return "dispatch/dispatchUnit/dispatchUnit_page";
    }
    @RequiresPermissions("dispatchUnit:list")
    @RequestMapping("/dispatchUnit_data")
    public void dispatchUnit_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch_unit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer year,
                                    Integer unitId,
                                    Integer typeId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchUnitExample example = new DispatchUnitExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            dispatchUnit_export(example, response);
            return;
        }

        int count = dispatchUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchUnit> DispatchUnits = dispatchUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", DispatchUnits);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(Dispatch.class, DispatchMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("dispatchUnit:edit")
    @RequestMapping(value = "/dispatchUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchUnit_au(DispatchUnit record, HttpServletRequest request, ModelMap modelMap) {

        Integer id = record.getId();

        if (id == null) {
            dispatchUnitService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加单位发文：%s", record.getId()));
        } else {

            dispatchUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新单位发文：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchUnit:edit")
    @RequestMapping("/dispatchUnit_au")
    public String dispatchUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(id);
            modelMap.put("dispatchUnit", dispatchUnit);

            modelMap.put("dispatch", dispatchMapper.selectByPrimaryKey(dispatchUnit.getDispatchId()));
        }
        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch_unit"));
        return "dispatch/dispatchUnit/dispatchUnit_au";
    }

    @RequiresPermissions("dispatchUnit:del")
    @RequestMapping(value = "/dispatchUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dispatchUnitService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除单位发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchUnit:del")
    @RequestMapping(value = "/dispatchUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dispatchUnitService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除单位发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchUnit:changeOrder")
    @RequestMapping(value = "/dispatchUnit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchUnit_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchUnitService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "单位发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchUnit_export(DispatchUnitExample example, HttpServletResponse response) {

        List<DispatchUnit> records = dispatchUnitMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"发文号", "所属单位","类型","年份","备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchUnit record = records.get(i);
            Dispatch dispatch = record.getDispatch();
            String[] values = {
                    CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear()),
                    record.getUnitId()==null?"":unitService.findAll().get(record.getUnitId()).getName(),
                    metaTypeService.getName(record.getTypeId()),
                    record.getYear()+"",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "单位发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("dispatchUnitRelate:list")
    @RequestMapping("/dispatchUnit_relate")
    public String dispatchUnit_relate(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            DispatchUnitRelateExample example = new DispatchUnitRelateExample();
            DispatchUnitRelateExample.Criteria criteria = example.createCriteria().andDispatchUnitIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "sort_order", "desc"));

            int count = dispatchUnitRelateMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<DispatchUnitRelate> dispatchUnitRelates = dispatchUnitRelateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("dispatchUnitRelates", dispatchUnitRelates);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(id);
            modelMap.put("dispatchUnit", dispatchUnit);
            modelMap.put("dispatch", dispatchService.findAll().get(dispatchUnit.getDispatchId()));
            modelMap.put("unitMap", unitService.findAll());
        }

        return "dispatch/dispatchUnit/dispatchUnit_relate";
    }

    @RequestMapping("/dispatchUnit_selects")
    @ResponseBody
    public Map dispatchUnit_selects(Integer pageSize, Integer pageNo, int unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if(searchStr!= null) searchStr = "%"+searchStr+"%";

        int count = commonMapper.countDispatchByCodeUnit(searchStr, unitId);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DispatchUnit> dispatchUnits = commonMapper.selectDispatchUnitByCodeList(searchStr, unitId, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map<Integer, Dispatch> dispatchMap = dispatchService.findAll();
        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if(null != dispatchUnits && dispatchUnits.size()>0){

            for(DispatchUnit dispatchUnit:dispatchUnits){
                Map<String, String> option = new HashMap<>();
                Dispatch dispatch = dispatchMap.get(dispatchUnit.getDispatchId());
                option.put("id", dispatchUnit.getId() + "");
                option.put("text", CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear()));
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
