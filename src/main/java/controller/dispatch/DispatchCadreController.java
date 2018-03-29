package controller.dispatch;

import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import domain.dispatch.DispatchCadreView;
import domain.dispatch.DispatchCadreViewExample;
import domain.dispatch.DispatchType;
import domain.sys.SysUserView;
import mixin.DispatchMixin;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DispatchCadreController extends DispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatch_cadres")
    public String dispatch_cadres(Integer dispatchId, ModelMap modelMap) {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(dispatchId);
        modelMap.put("dispatch", dispatch);

        if(dispatch!=null) {
            Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
            modelMap.put("dispatchType", dispatchTypeMap.get(dispatch.getDispatchTypeId()));
        }

        return "dispatch/dispatchCadre/dispatch_cadres";
    }

    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatch_cadres_admin")
    public String dispatch_cadres_admin(Integer dispatchId, Integer id, ModelMap modelMap) {

        if(dispatchId!=null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(dispatchId);
            modelMap.put("dispatch", dispatch);

            if(id!=null){
                DispatchCadre dispatchCadre = dispatchCadreMapper.selectByPrimaryKey(id);
                modelMap.put("dispatchCadre", dispatchCadre);
            }

            DispatchCadreExample example = new DispatchCadreExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            List<DispatchCadre> dispatchCadres = dispatchCadreMapper.selectByExample(example);
            modelMap.put("dispatchCadres", dispatchCadres);
        }

        return "dispatch/dispatchCadre/dispatch_cadres_admin";
    }

    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatchCadre")
    public String dispatchCadre(@RequestParam(defaultValue = "1") Integer cls,
                                     Integer dispatchId,
                                     Integer dispatchTypeId,
                                     @RequestParam(required = false, value = "wayId")Integer[] wayId,
                                     @RequestParam(required = false, value = "procedureId")Integer[] procedureId,
                                     @RequestParam(required = false, value = "adminLevelId")Integer[] adminLevelId,
                                    Integer cadreId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (dispatchId!=null) {
            modelMap.put("dispatch", dispatchMapper.selectByPrimaryKey(dispatchId));
        }

        if (dispatchTypeId!=null) {
            Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
            modelMap.put("dispatchType", dispatchTypeMap.get(dispatchTypeId));
        }
        if (cadreId!=null) {

            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }
        if (wayId!=null) {
            List<Integer> selectedWayIds = Arrays.asList(wayId);
            modelMap.put("selectedWayIds", selectedWayIds);
        }
        if (procedureId!=null) {
            List<Integer> selectedProcedureIds = Arrays.asList(procedureId);
            modelMap.put("selectedProcedureIds", selectedProcedureIds);
        }
        if (adminLevelId!=null) {
            List<Integer> selectedAdminLevelIds = Arrays.asList(adminLevelId);
            modelMap.put("selectedAdminLevelIds", selectedAdminLevelIds);
        }

        return "dispatch/dispatchCadre/dispatchCadre_page";
    }
    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatchCadre_data")
    @ResponseBody
    public void dispatchCadre_data(HttpServletResponse response,
                                 /*@SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch_cadre") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,*/
                                   Integer year,
                                   Integer startYear,
                                   Integer endYear,
                                   Integer dispatchTypeId,
                                   Integer code,
                                    Integer dispatchId,
                                    Byte type,
                                    /*Integer typeId,*/
                                   @RequestParam(required = false, value = "wayId")Integer[] wayId,
                                   @RequestParam(required = false, value = "procedureId")Integer[] procedureId,
                                   @RequestParam(required = false, value = "adminLevelId")Integer[] adminLevelId,
                                    Integer cadreId,
                                    /*String name,*/
                                    Integer unitId,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchCadreViewExample example = new DispatchCadreViewExample();
        DispatchCadreViewExample.Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (dispatchId!=null) {
            criteria.andDispatchIdEqualTo(dispatchId);
        }

        if (startYear!=null) {
            criteria.andYearGreaterThanOrEqualTo(startYear);
        }
        if (endYear!=null) {
            criteria.andYearLessThanOrEqualTo(endYear);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (dispatchTypeId!=null) {
            criteria.andDispatchTypeIdEqualTo(dispatchTypeId);
        }
        if (code!=null) {
            criteria.andCodeEqualTo(code);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }

        /*if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }*/
        if (wayId!=null) {
            List<Integer> selects = Arrays.asList(wayId);
            criteria.andWayIdIn(selects);
        }
        if (procedureId!=null) {
            List<Integer> selects = Arrays.asList(procedureId);
            criteria.andProcedureIdIn(selects);
        }
        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        /*if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }*/
        if (adminLevelId!=null) {
            List<Integer> selects = Arrays.asList(adminLevelId);
            criteria.andAdminLevelIdIn(selects);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dispatchCadre_export(example, response);
            return;
        }

        int count = dispatchCadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<DispatchCadreView> DispatchCadres =
                dispatchCadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", DispatchCadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(Dispatch.class, DispatchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dispatchCadre:edit")
    @RequestMapping(value = "/dispatchCadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchCadre_au(DispatchCadre record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(record.getDispatchId());
            if(dispatch!=null && dispatch.getHasChecked()){
                return failed("已经复核，不可添加。");
            }
            dispatchCadreService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部发文：%s", record.getId()));
        } else {
            DispatchCadre dispatchCadre = dispatchCadreMapper.selectByPrimaryKey(id);
            if(dispatchCadre!=null && dispatchCadre.getDispatch().getHasChecked()){
                return failed("已经复核，不可修改。");
            }
            record.setDispatchId(null);
            dispatchCadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部发文：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchCadre:edit")
    @RequestMapping("/dispatchCadre_au")
    public String dispatchCadre_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DispatchCadre dispatchCadre = dispatchCadreMapper.selectByPrimaryKey(id);
            modelMap.put("dispatchCadre", dispatchCadre);
            if(dispatchCadre!=null) {
                modelMap.put("dispatch", dispatchMapper.selectByPrimaryKey(dispatchCadre.getDispatchId()));
                CadreView cadre = cadreViewMapper.selectByPrimaryKey(dispatchCadre.getCadreId());
                modelMap.put("cadre", cadre);
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "dispatch/dispatchCadre/dispatchCadre_au";
    }

    @RequiresPermissions("dispatchCadre:del")
    @RequestMapping(value = "/dispatchCadre_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchCadre_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dispatchCadreService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchCadre:del")
    @RequestMapping(value = "/dispatchCadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dispatchCadreService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchCadre:changeOrder")
    @RequestMapping(value = "/dispatchCadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchCadre_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchCadreService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchCadre_export(DispatchCadreViewExample example, HttpServletResponse response) {

        List<DispatchCadreView> records = dispatchCadreViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份", "发文号","任免日期","类别", "任免方式","任免程序",
                "干部类型","工作证号", "姓名", "职务", "职务属性", "行政级别","所属单位","单位类型",
                "发文类型","党委常委会日期", "发文日期", "是否复核", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchCadreView record = records.get(i);
            Dispatch dispatch = record.getDispatch();
            SysUserView uv =  record.getUser();
            String[] values = {
                    record.getYear()+"",
                    CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear()),
                    DateUtils.formatDate(dispatch.getWorkTime(), DateUtils.YYYY_MM_DD),
                    SystemConstants.DISPATCH_CADRE_TYPE_MAP.get(record.getType()),
                    metaTypeService.getName(record.getWayId()),
                    metaTypeService.getName(record.getProcedureId()),
                    metaTypeService.getName(record.getCadreTypeId()),
                    uv.getCode(),
                    uv.getRealname(),
                    record.getPost(),
                    metaTypeService.getName(record.getPostId()),
                    metaTypeService.getName(record.getAdminLevelId()),
                    record.getUnitId()==null?"":record.getUnit().getName(),
                    record.getUnitId()==null?"":metaTypeService.getName(record.getUnit().getTypeId()),
                    dispatch.getDispatchType().getName(),
                    DateUtils.formatDate(dispatch.getMeetingTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(dispatch.getPubTime(), DateUtils.YYYY_MM_DD),
                    dispatch.getHasChecked()?"已复核":"否",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/dispatchCadre_selects")
    @ResponseBody
    public Map dispatchCadre_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchCadreExample example = new DispatchCadreExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = dispatchCadreMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DispatchCadre> dispatchCadres = dispatchCadreMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != dispatchCadres && dispatchCadres.size()>0){

            for(DispatchCadre dispatchCadre:dispatchCadres){

                Select2Option option = new Select2Option();
                option.setText(dispatchCadre.getName());
                option.setId(dispatchCadre.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
