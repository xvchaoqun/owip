package controller.base;

import controller.BaseController;
import domain.base.LayerType;
import domain.base.LayerTypeExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.LogConstants;
import sys.tool.jackson.Select2Option;
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
public class LayerTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("layerType:list")
    @RequestMapping("/layerType")
    public String layerType(ModelMap modelMap) {

        return "base/layerType/layerType_page";
    }

    @RequiresPermissions("layerType:list")
    @RequestMapping("/layerType_detail")
    public String layerType_detail(int fid,
                                   Integer id,
                                   Boolean popup,
                                   Integer pageSize, Integer pageNo,
                                   ModelMap modelMap) {

        LayerType topLayerType = layerTypeMapper.selectByPrimaryKey(fid);
        modelMap.put("topLayerType", topLayerType);

        if(topLayerType.getFid()!=null){
            modelMap.put("fLayerType", layerTypeMapper.selectByPrimaryKey(topLayerType.getFid()));
        }

        if(BooleanUtils.isTrue(popup)){

            if(id!=null){
                LayerType layerType = layerTypeMapper.selectByPrimaryKey(id);
                modelMap.put("layerType", layerType);
            }

            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            LayerTypeExample example = new LayerTypeExample();
            example.createCriteria().andFidEqualTo(fid);
            example.setOrderByClause("sort_order asc");

            long count = layerTypeMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<LayerType> layerTypes = layerTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("layerTypes", layerTypes);

            CommonList commonList = new CommonList(count, pageNo, pageSize);
            String searchStr = "&pageSize=" + pageSize + "&fid=" + fid;
            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            return "base/layerType/layerType_popup";
        }else{
            return "base/layerType/layerType_detail";
        }
    }

    @RequiresPermissions("layerType:list")
    @RequestMapping("/layerType_data")
    public void layerType_data(HttpServletResponse response,
                               Integer fid,
                               String name,
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

        LayerTypeExample example = new LayerTypeExample();
        LayerTypeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (fid != null){
            criteria.andFidEqualTo(fid);
        }else{
            criteria.andFidIsNull();
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            layerType_export(example, response);
            return;
        }

        long count = layerTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<LayerType> records = layerTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(layerType.class, layerTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("layerType:edit")
    @RequestMapping(value = "/layerType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_layerType_au(LayerType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            layerTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加类别：%s", record.getId()));
        } else {

            layerTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新类别：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("layerType:edit")
    @RequestMapping("/layerType_au")
    public String layerType_au(Integer id,
                               Integer fid,
                               ModelMap modelMap) {
        if (id != null) {
            LayerType layerType = layerTypeMapper.selectByPrimaryKey(id);
            modelMap.put("layerType", layerType);
            fid = layerType.getFid();
        }
        modelMap.put("fid", fid);
        if(fid != null){
            LayerType topLayerType = layerTypeMapper.selectByPrimaryKey(fid);
            modelMap.put("topLayerType", topLayerType);
        }

        return "base/layerType/layerType_au";
    }

    @RequiresPermissions("layerType:del")
    @RequestMapping(value = "/layerType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_layerType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            layerTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除类别：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("layerType:del")
    @RequestMapping(value = "/layerType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            layerTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除类别：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("layerType:changeOrder")
    @RequestMapping(value = "/layerType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_layerType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        layerTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "类别调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void layerType_export(LayerTypeExample example, HttpServletResponse response) {

        List<LayerType> records = layerTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"上级类别", "排序", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            LayerType record = records.get(i);
            String[] values = {
                    record.getFid() + "",
                    record.getSortOrder() + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "类别_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/layerType_selects")
    @ResponseBody
    public Map layerType_selects(Integer pageSize,
                                 Integer fid,
                                 Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        LayerTypeExample example = new LayerTypeExample();
        LayerTypeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (fid != null) {
            criteria.andFidEqualTo(fid);
        } else {
            criteria.andFidIsNull();
        }

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }

        long count = layerTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<LayerType> layerTypes = layerTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != layerTypes && layerTypes.size() > 0) {

            for (LayerType layerType : layerTypes) {

                Select2Option option = new Select2Option();
                option.setText(layerType.getName());
                option.setId(layerType.getId() + "");
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
