package controller.unit;

import controller.BaseController;
import domain.base.MetaType;
import domain.unit.HistoryUnit;
import domain.unit.HistoryUnitExample;
import domain.unit.Unit;
import domain.unit.UnitExample;
import domain.unit.UnitExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    // 基本信息
    @RequiresPermissions("unit:info")
    @RequestMapping("/unit_base")
    public String unit_base(Integer id, ModelMap modelMap) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        modelMap.put("unit", unit);
        // 正在运转单位
        modelMap.put("runUnits", unitService.findRunUnits(id));
        // 历史单位
        modelMap.put("historyUnits", unitService.findHistoryUnits(id));

        return "unit/unit_base";
    }

    @RequiresPermissions("unit:info")
    @RequestMapping("/unit_view")
    public String unit_show_page(HttpServletResponse response,  ModelMap modelMap) {

        return "unit/unit_view";
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/unit")
    public String unit(@RequestParam(required = false, defaultValue = "1")Byte status,
                            ModelMap modelMap) {

        modelMap.put("status", status);
        return "unit/unit_page";
    }
    @RequiresPermissions("unit:list")
    @RequestMapping("/unit_data")
    @ResponseBody
    public void unit_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "unit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(required = false, defaultValue = "1")Byte status,
                                    String code,
                                    String name,
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

        UnitExample example = new UnitExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            unit_export(example, response);
            return;
        }

        int count = unitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Unit> Units = unitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Units);
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
    public Map do_unit_au(Unit record, String _workTime, HttpServletRequest request) {

        Integer id = record.getId();
        if(StringUtils.isNotBlank(_workTime)){
            record.setWorkTime(DateUtils.parseDate(_workTime, DateUtils.YYYY_MM_DD));
        }

        if (unitService.idDuplicate(id, record.getCode())) {
            return failed("单位编码重复");
        }

        if (id == null) {

            record.setCreateTime(new Date());
            record.setStatus(SystemConstants.UNIT_STATUS_RUN);
            unitService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加单位：%s", record.getId()));
        } else {

            unitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping("/unit_au")
    public String unit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Unit unit = unitMapper.selectByPrimaryKey(id);
            modelMap.put("unit", unit);
        }
        return "unit/unit_au";
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            unitService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("unit:abolish")
    @RequestMapping(value = "/unit_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_abolish(@RequestParam(value = "ids[]") Integer[] ids) {

        unitService.abolish(ids);
        logger.info("abolish Unit:" + StringUtils.join(ids, ","));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            
            unitService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:changeOrder")
    @RequestMapping(value = "/unit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unit_changeOrder(Integer id, byte status, Integer addNum, HttpServletRequest request) {

        unitService.changeOrder(id, status, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "单位调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unit_export(UnitExample example, HttpServletResponse response) {

        List<Unit> records = unitMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"单位编号","单位名称","单位类型","成立时间","单位网址","备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Unit record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    metaTypeService.getName(record.getTypeId()),
                    DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    record.getUrl(),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("unit:history")
    @RequestMapping("/unit_history")
    public String unit_history(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

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

            if (id!=null) {
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
        if(status!=null) criteria.andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = unitMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Unit> units = unitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        Map<Integer, MetaType> unitTypeMap = metaTypeService.metaTypes("mc_unit_type");
        List<Map<String, Object> > options = new ArrayList<>();
        if(null != units && units.size()>0){
            for(Unit unit:units){
                Map<String, Object> option = new HashMap<>();
                option.put("text", unit.getName());
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
}
