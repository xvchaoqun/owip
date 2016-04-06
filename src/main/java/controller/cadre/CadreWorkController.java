package controller.cadre;

import controller.BaseController;
import domain.*;
import domain.CadreWorkExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.constants.DispatchConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                                 @SortParam(required = false, defaultValue = "id", tableName = "base_cadre_work") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Integer fid,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreWorkExample example = new CadreWorkExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));
        if(fid!=null){
            criteria.andFidEqualTo(fid);
        }else{
            criteria.andFidIsNull();
        }
        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreWork_export(example, response);
            return null;
        }

        int count = cadreWorkMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreWork> cadreWorks = cadreWorkMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreWorks", cadreWorks);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        if(fid!=null){
            searchStr += "&fid=" + fid;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        if(fid!=null)
            return "cadre/cadreWork/cadreWork_during_page";

        return "cadre/cadreWork/cadreWork_page";
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_au(CadreWork record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endTime)){
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreWorkService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加工作经历：%s", record.getId()));
        } else {

            cadreWorkService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新工作经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_au")
    public String cadreWork_au(Integer id,  int cadreId, Integer fid, ModelMap modelMap) {

        if (id != null) {
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreWork", cadreWork);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreWork/cadreWork_au";
    }

    @RequiresPermissions("cadreWork:del")
    @RequestMapping(value = "/cadreWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreWorkService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除工作经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:del")
    @RequestMapping(value = "/cadreWork_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreWorkService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除工作经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_addDispatchs")
    public String cadreWork_addDispatchs(HttpServletResponse response, int id, int cadreId, ModelMap modelMap) {

        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        String dispatchs = cadreWork.getDispatchs();
        if(StringUtils.isNotBlank(dispatchs)) {
            for (String str : dispatchs.split(",")) {
                try {
                    cadreDispatchIdSet.add(Integer.valueOf(str));
                }catch (Exception ex){ex.printStackTrace();}
            }
            modelMap.put("cadreDispatchIdSet", cadreDispatchIdSet);
        }

        List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId);
        modelMap.put("dispatchCadres", dispatchCadres);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));

        return "cadre/cadreWork/cadreWork_addDispatchs";
    }
    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_addDispatchs(HttpServletRequest request, int id, @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        CadreWork record = new CadreWork();
        record.setId(id);
        record.setDispatchs("-1");
        if (null != ids && ids.length>0){
            record.setDispatchs(StringUtils.join(ids, ","));
        }
        cadreWorkService.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "修改工作经历%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    public void cadreWork_export(CadreWorkExample example, HttpServletResponse response) {

        List<CadreWork> cadreWorks = cadreWorkMapper.selectByExample(example);
        int rownum = cadreWorkMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","开始日期","结束日期","工作单位","担任职务或者专技职务","行政级别","院系/机关工作经历"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreWork cadreWork = cadreWorks.get(i);
            String[] values = {
                        cadreWork.getCadreId()+"",
                                            DateUtils.formatDate(cadreWork.getStartTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(cadreWork.getEndTime(), DateUtils.YYYY_MM_DD),
                                            cadreWork.getUnit(),
                                            cadreWork.getPost(),
                                            cadreWork.getTypeId()+"",
                                            cadreWork.getWorkType()+""
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
