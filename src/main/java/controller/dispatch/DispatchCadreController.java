package controller.dispatch;

import controller.BaseController;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import domain.dispatch.DispatchCadreExample.Criteria;
import domain.cadre.Cadre;
import domain.dispatch.DispatchType;
import domain.sys.SysUser;
import domain.unit.Unit;
import mixin.DispatchMixin;
import mixin.UnitMixin;
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
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class DispatchCadreController extends BaseController {

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
    public String dispatch_cadres_admin(Integer dispatchId, ModelMap modelMap) {

        if(dispatchId!=null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(dispatchId);
            modelMap.put("dispatch", dispatch);

            DispatchCadreExample example = new DispatchCadreExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            List<DispatchCadre> dispatchCadres = dispatchCadreMapper.selectByExample(example);
            modelMap.put("dispatchCadres", dispatchCadres);
        }

        return "dispatch/dispatchCadre/dispatch_cadres_admin";
    }

    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatchCadre")
    public String dispatchCadre() {

        return "index";
    }

    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatchCadre_page")
    public String dispatchCadre_page(HttpServletResponse response,
                                     Integer dispatchId,
                                    /*Integer typeId,*/
                                    Integer wayId,
                                    Integer procedureId,
                                    Integer cadreId,
                                    /*String name,*/
                                    Integer adminLevelId,
                                    Integer unitId,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (dispatchId!=null) {
            modelMap.put("dispatch", dispatchMapper.selectByPrimaryKey(dispatchId));
        }
        if (cadreId!=null) {

            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUser sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "dispatch/dispatchCadre/dispatchCadre_page";
    }
    @RequiresPermissions("dispatchCadre:list")
    @RequestMapping("/dispatchCadre_data")
    @ResponseBody
    public void dispatchCadre_data(HttpServletResponse response,
                                 /*@SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch_cadre") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,*/
                                    Integer dispatchId,
                                    /*Integer typeId,*/
                                    Integer wayId,
                                    Integer procedureId,
                                    Integer cadreId,
                                    /*String name,*/
                                    Integer adminLevelId,
                                    Integer unitId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchCadreExample example = new DispatchCadreExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (dispatchId!=null) {
            criteria.andDispatchIdEqualTo(dispatchId);
        }
        /*if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }*/
        if (wayId!=null) {
            criteria.andWayIdEqualTo(wayId);
        }
        if (procedureId!=null) {
            criteria.andProcedureIdEqualTo(procedureId);
        }
        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        /*if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }*/
        if (adminLevelId!=null) {
            criteria.andAdminLevelIdEqualTo(adminLevelId);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            dispatchCadre_export(example, response);
            return;
        }

        int count = dispatchCadreMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<DispatchCadre> DispatchCadres =
                selectMapper.selectDispatchCadrePage(dispatchId, wayId, procedureId, cadreId, adminLevelId, unitId,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", DispatchCadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(Dispatch.class, DispatchMixin.class);
        sourceMixins.put(Unit.class, UnitMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("dispatchCadre:edit")
    @RequestMapping(value = "/dispatchCadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchCadre_au(DispatchCadre record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            dispatchCadreService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部发文：%s", record.getId()));
        } else {

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
            modelMap.put("dispatch", dispatchMapper.selectByPrimaryKey(dispatchCadre.getDispatchId()));

            Cadre cadre = cadreService.findAll().get(dispatchCadre.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
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

    public void dispatchCadre_export(DispatchCadreExample example, HttpServletResponse response) {

        List<DispatchCadre> records = dispatchCadreMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"发文号","任免方式","任免程序","工作证号","姓名","行政级别","所属单位","备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchCadre record = records.get(i);
            Dispatch dispatch = record.getDispatch();
            SysUser sysUser =  record.getUser();
            String[] values = {
                    CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear()),
                    metaTypeService.getName(record.getWayId()),
                    metaTypeService.getName(record.getProcedureId()),
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    metaTypeService.getName(record.getAdminLevelId()),
                    record.getUnitId()==null?"":record.getUnit().getName(),
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
