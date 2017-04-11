package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreInfo;
import domain.cadre.CadreView;
import domain.cadre.CadreWork;
import domain.cadre.CadreWorkExample;
import domain.cadre.CadreWorkExample.Criteria;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.BooleanUtils;
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
import java.util.*;

@Controller
public class CadreWorkController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreWork:list")
    @RequestMapping("/cadreWork")
    public String cadreWork() {

        return "index";
    }

    @RequiresPermissions("cadreWork:list")
    @RequestMapping("/cadreWork_page")
    public String cadreWork_page(HttpServletResponse response,
                                 @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
                                 @SortParam(required = false, defaultValue = "id", tableName = "cadre_work") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Integer cadreId,
                                 Integer fid,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {
        modelMap.put("type", type);
        if (type == 2) {
            List<CadreWork> cadreWorks = cadreWorkService.findByCadre(cadreId);
            modelMap.put("cadreWorks", cadreWorks);
            CadreInfo cadreInfo = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_WORK);
            modelMap.put("cadreInfo", cadreInfo);
        }

        return "cadre/cadreWork/cadreWork_page";
    }

    @RequiresPermissions("cadreWork:list")
    @RequestMapping("/cadreWork_data")
    public void cadreWork_data(HttpServletResponse response,
                              /* @SortParam(required = false, defaultValue = "id", tableName = "cadre_work") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,*/
                              Integer cadreId,
                              Integer fid, // fid=null时，读取工作经历；fid<=0时，读取全部 fid>0 读取期间工作
                              Boolean isCadre,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreWorkExample example = new CadreWorkExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("start_time asc");
        if (fid != null) {
            if (fid > 0)
                criteria.andFidEqualTo(fid);
        } else {
            criteria.andFidIsNull();
        }
        if(isCadre!=null){
            criteria.andIsCadreEqualTo(isCadre);
        }

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreWork_export(example, response);
            return;
        }

        int count = cadreWorkMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreWork> cadreWorks = cadreWorkMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        /*if(fid!=null)
            return "cadre/cadreWork/cadreWork_during_page";

        return "cadre/cadreWork/cadreWork_page";*/

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreWorks);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入

            CadreWork record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, "yyyy.MM"));
        }
        if (StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, "yyyy.MM"));
        }
        record.setIsCadre(BooleanUtils.isTrue(record.getIsCadre()));
        if (id == null) {

            if(!toApply) {
                cadreWorkService.insertSelective(record);
                logger.info(addLog(SystemConstants.LOG_ADMIN, "添加工作经历：%s", record.getId()));
            }else{
                cadreWorkService.modifyApply(record, null, false);
                logger.info(addLog(SystemConstants.LOG_USER, "提交添加申请-工作经历：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            if(cadreWork.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }

            if(!toApply) {
                cadreWorkService.updateByPrimaryKeySelective(record);
                logger.info(addLog(SystemConstants.LOG_ADMIN, "更新工作经历：%s", record.getId()));
            }else{
                if(_isUpdate==false) {
                    cadreWorkService.modifyApply(record, id, false);
                    logger.info(addLog(SystemConstants.LOG_USER, "提交修改申请-工作经历：%s", record.getId()));
                }else{
                    // 更新修改申请的内容
                    cadreWorkService.updateModify(record, applyId);
                    logger.info(addLog(SystemConstants.LOG_USER, "修改申请内容-工作经历：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_au")
    public String cadreWork_au(Integer id, int cadreId,
                               Integer fid, ModelMap modelMap) {

        if (id != null) {
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreWork", cadreWork);
        }
        if (fid != null) {
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(fid);
            modelMap.put("topCadreWork", cadreWork);
        }
        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreWork/cadreWork_au";
    }

    // （在菜单【任职情况】中，对没有干部管理权限的角色隐藏）
    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_updateUnitId", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_updateUnitId(int id, Integer unitId, HttpServletRequest request) {

        if(unitId==null){

            updateMapper.del_cadreWork_unitId(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除对应工作单位：%s", id));
        }else {
            CadreWork record = new CadreWork();
            record.setId(id);
            record.setUnitId(unitId);
            cadreWorkService.updateByPrimaryKeySelective(record);

            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新对应工作单位：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_updateUnitId")
    public String cadreWork_updateUnitId(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreWork", cadreWork);
            if(cadreWork.getUnitId()!=null){
                modelMap.put("unit", unitService.findAll().get(cadreWork.getUnitId()));
            }
        }
        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreWork/cadreWork_updateUnitId";
    }
/*
    @RequiresPermissions("cadreWork:del")
    @RequestMapping(value = "/cadreWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreWorkService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除工作经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreWork:del")
    @RequestMapping(value = "/cadreWork_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部信息本人直接修改数据校验
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreWorkService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除工作经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_addDispatchs")
    public String cadreWork_addDispatchs(HttpServletResponse response, int id, int cadreId, String type, ModelMap modelMap) {

        // 已关联的发文
        Set<Integer> dispatchCadreIdSet = new HashSet<>();
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>();
        Map<Integer, DispatchCadre> dispatchCadreMap = dispatchCadreService.findAll();
        List<DispatchCadreRelate> dispatchCadreRelates = dispatchCadreRelateService.findDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(dispatchCadreMap.get(dispatchCadreId));
        }
        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");
            List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId, null);
            modelMap.put("dispatchCadres", dispatchCadres);

            Set<Integer> otherDispatchCadreRelateSet = dispatchCadreRelateService.findOtherDispatchCadreRelateSet(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
            modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadreWork/cadreWork_addDispatchs";
    }

    // 不提供给干部本人操作
    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_addDispatchs(HttpServletRequest request, int id, @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        //if (ids != null && ids.length > 0) { // 可以删除
        dispatchCadreRelateService.updateDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK, ids);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改工作经历%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        //}
        return success(FormUtils.SUCCESS);
    }

    public void cadreWork_export(CadreWorkExample example, HttpServletResponse response) {

        List<CadreWork> cadreWorks = cadreWorkMapper.selectByExample(example);
        int rownum = cadreWorkMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部", "开始日期", "结束日期", "工作单位", "担任职务或者专技职务", "行政级别", "院系/机关工作经历"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreWork cadreWork = cadreWorks.get(i);
            String[] values = {
                    cadreWork.getCadreId() + "",
                    DateUtils.formatDate(cadreWork.getStartTime(), "yyyy.MM"),
                    DateUtils.formatDate(cadreWork.getEndTime(), "yyyy.MM"),
                    cadreWork.getUnit(),
                    cadreWork.getPost(),
                    cadreWork.getTypeId() + "",
                    cadreWork.getWorkType() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "工作经历_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
