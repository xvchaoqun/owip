package controller.dispatch;

import controller.BaseController;
import domain.*;
import domain.DispatchCadreExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.DispatchMixin;
import mixin.UnitMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                    @RequestParam(required = false, defaultValue = "0") int export,
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
                                 /*@SortParam(required = false, defaultValue = "sort_order", tableName = "base_dispatch_cadre") String sort,
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
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部发文：%s", record.getId()));
        } else {

            dispatchCadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部发文：%s", record.getId()));
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
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchCadre:del")
    @RequestMapping(value = "/dispatchCadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dispatchCadreService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchCadre:changeOrder")
    @RequestMapping(value = "/dispatchCadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchCadre_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchCadreService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchCadre_export(DispatchCadreExample example, HttpServletResponse response) {

        List<DispatchCadre> dispatchCadres = dispatchCadreMapper.selectByExample(example);
        int rownum = dispatchCadreMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"发文号","任免方式","任免程序","工作证号","姓名","行政级别","所属单位","备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Cadre> cadreMap = cadreService.findAll();

        Map<Integer, MetaType> wayMap = metaTypeService.metaTypes("mc_dispatch_cadre_way");
        Map<Integer, MetaType> procedureMap = metaTypeService.metaTypes("mc_dispatch_cadre_procedure");
        //Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        Map<Integer, MetaType> adminLevelMap = metaTypeService.metaTypes("mc_admin_level");
        for (int i = 0; i < rownum; i++) {

            DispatchCadre dispatchCadre = dispatchCadres.get(i);
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(dispatchCadre.getDispatchId());
            Unit unit = unitMap.get(dispatchCadre.getUnitId());
            Cadre cadre = cadreMap.get(dispatchCadre.getCadreId());
            SysUser user = sysUserService.findById(cadre.getUserId());
            String[] values = {
                                            dispatch.getCode() + "",
                                            wayMap.get(dispatchCadre.getWayId()).getName(),
                                            procedureMap.get(dispatchCadre.getProcedureId()).getName(),
                                            user.getCode(),
                    user.getRealname(),
                                            adminLevelMap.get(dispatchCadre.getAdminLevelId()).getName(),
                                            unit.getName(),
                                            dispatchCadre.getRemark()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
