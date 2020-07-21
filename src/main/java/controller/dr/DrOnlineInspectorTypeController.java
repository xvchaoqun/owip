package controller.dr;

import domain.dr.DrOnlineInspectorType;
import domain.dr.DrOnlineInspectorTypeExample;
import domain.dr.DrOnlineInspectorTypeExample.Criteria;
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
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dr")
public class DrOnlineInspectorTypeController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineParam:menu")
    @RequestMapping("/drOnlineParam")
    public String drOnlineParam(ModelMap modelMap,
                                @RequestParam(required = false, defaultValue = "2") Byte cls) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "dr/drOnline/drOnlineInspectorType/drOnlineInspectorType_page";
        }
        return "dr/drOnline/drOnlineNotice/drOnlineNotice_page";
    }

    @RequiresPermissions("drOnlineInspectorType:list")
    @RequestMapping("/drOnlineInspectorType")
    public String drOnlineInspectorType(ModelMap modelMap,
                                @RequestParam(required = false, defaultValue = "1") Byte cls) {

        modelMap.put("cls", cls);
        return "dr/drOnline/drOnlineInspectorType/drOnlineInspectorType_page";
    }

    @RequiresPermissions("drOnlineInspectorType:list")
    @RequestMapping("/drOnlineInspectorType_data")
    @ResponseBody
    public void drOnlineInspectorType_data(HttpServletResponse response,
                                           Integer id,
                                           String type,
                                           Byte status,
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

        DrOnlineInspectorTypeExample example = new DrOnlineInspectorTypeExample();
        Criteria criteria = example.createCriteria().andStatusNotEqualTo(DrConstants.DR_ONLINE_INSPECTOR_TYPE_delete);
        example.setOrderByClause("status asc,sort_order desc");

        if (null != id){
            criteria.andIdEqualTo(id);
        }
        if (StringUtils.isNotBlank(type)){
            criteria.andTypeLike(SqlUtils.like(type));
        }
        if (status != null){
            criteria.andStatusEqualTo(status);
        }

        long count = drOnlineInspectorTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnlineInspectorType> records= drOnlineInspectorTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineInspectorType.class, drOnlineInspectorTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnlineInspectorType:edit")
    @RequestMapping(value = "/drOnlineInspectorType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineInspectorType_au(DrOnlineInspectorType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            //record.setStatus(true);
            drOnlineInspectorTypeService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DR, "添加参评人身份类型：{0}", record.getType()));
        } else {

            drOnlineInspectorTypeService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DR, "更新参评人身份类型：{0}", record.getType()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorType:edit")
    @RequestMapping("/drOnlineInspectorType_au")
    public String drOnlineInspectorType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrOnlineInspectorType drOnlineInspectorType = drOnlineInspectorTypeMapper.selectByPrimaryKey(id);
            modelMap.put("drOnlineInspectorType", drOnlineInspectorType);
        }
        return "dr/drOnline/drOnlineInspectorType/drOnlineInspectorType_au";
    }

    @RequiresPermissions("drOnlineInspectorType:edit")
    @RequestMapping(value = "/drOnlineInspectorType_change", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineInspectorType_change(Byte status,
                                            HttpServletRequest request,
                                            @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlineInspectorTypeService.changeStatus(status, ids);
            logger.info(log( LogConstants.LOG_DR, "修改参评人身份类型的状态为" + DrConstants.DR_ONLINE_MAP.get(status) + "：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorType:del")
    @RequestMapping(value = "/drOnlineInspectorType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineInspectorType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlineInspectorTypeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除参评人身份类型：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorType:changeOrder")
    @RequestMapping(value = "/drOnlineInspectorType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineInspectorType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        drOnlineInspectorTypeService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DR, "参评人身份类型调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/drOnlineInspectorType_selects")
    @ResponseBody
    public Map drOnlineInspectorType_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrOnlineInspectorTypeExample example = new DrOnlineInspectorTypeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(DrConstants.DR_ONLINE_INSPECTOR_TYPE_FORMAL);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andTypeLike(SqlUtils.like(searchStr));
        }

        long count = drOnlineInspectorTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DrOnlineInspectorType> records = drOnlineInspectorTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DrOnlineInspectorType record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getType());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

}
