package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import domain.cadre.CadreLeaderExample.Criteria;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.CadreMixin;
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
public class CadreLeaderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreLeaderInfo:list")
    @RequestMapping("/cadreLeaderInfo")
    public String cadreLeaderInfo() {

        return "index";
    }
    @RequiresPermissions("cadreLeaderInfo:list")
    @RequestMapping("/cadreLeaderInfo_page")
    public String cadreLeaderInfo_page(@RequestParam(required = false,
            defaultValue = SystemConstants.CADRE_STATUS_LEADER+"")Byte status,
                             Integer cadreId,ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "cadre/cadreLeader/cadreLeaderInfo_page";
    }


    @RequiresPermissions("cadreLeader:list")
    @RequestMapping("/cadreLeader")
    public String cadreLeader() {

        return "index";
    }
    @RequiresPermissions("cadreLeader:list")
    @RequestMapping("/cadreLeader_page")
    public String cadreLeader_page(HttpServletResponse response,
                              @SortParam(required = false, defaultValue = "sort_order", tableName = "cadre_leader") String sort,
                              @OrderParam(required = false, defaultValue = "desc") String order,
                              Integer cadreId,
                              Integer typeId,
                              String job,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer pageSize, Integer pageNo, ModelMap modelMap) {
        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if (cadre != null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "cadre/cadreLeader/cadreLeader_page";
    }
    @RequiresPermissions("cadreLeader:list")
    @RequestMapping("/cadreLeader_data")
    @ResponseBody
    public void cadreLeader_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "cadre_leader") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Integer typeId,
                                    String job,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreLeaderExample example = new CadreLeaderExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (StringUtils.isNotBlank(job)) {
            criteria.andJobLike("%" + job + "%");
        }

        if (export == 1) {
            cadreLeader_export(example, response);
            return ;
        }

        int count = cadreLeaderMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreLeader> Leaders = cadreLeaderMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Leaders);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(Cadre.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreLeader:edit")
    @RequestMapping(value = "/cadreLeader_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeader_au(CadreLeader record, ModelMap modelMap, HttpServletRequest request) {

        Integer id = record.getId();

        if (cadreLeaderService.idDuplicate(id, record.getCadreId(), record.getTypeId())) {
            return failed("添加重复");
        }

        if (id == null) {
            cadreLeaderService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加校领导：%s", record.getId()));
        } else {

            cadreLeaderService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新校领导：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeader:edit")
    @RequestMapping("/cadreLeader_au")
    public String cadreLeader_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreLeader cadreLeader = cadreLeaderMapper.selectByPrimaryKey(id);
            modelMap.put("cadreLeader", cadreLeader);
            Cadre cadre = cadreService.findAll().get(cadreLeader.getCadreId());
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "cadre/cadreLeader/cadreLeader_au";
    }

    @RequiresPermissions("cadreLeader:del")
    @RequestMapping(value = "/cadreLeader_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeader_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            cadreLeaderService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除校领导：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeader:del")
    @RequestMapping(value = "/cadreLeader_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreLeaderService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除校领导：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeader:changeOrder")
    @RequestMapping(value = "/cadreLeader_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeader_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreLeaderService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "校领导调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreLeader_export(CadreLeaderExample example, HttpServletResponse response) {

        List<CadreLeader> cadreLeaders = cadreLeaderMapper.selectByExample(example);
        int rownum = cadreLeaderMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"校领导","类别","分管工作"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreLeader cadreLeader = cadreLeaders.get(i);
            String[] values = {
                        cadreLeader.getCadreId()+"",
                                            cadreLeader.getTypeId()+"",
                                            cadreLeader.getJob()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "校领导_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequiresPermissions("cadreLeader:unit")
    @RequestMapping("/cadreLeader_unit")
    public String unit_history(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            CadreLeaderUnitExample example = new CadreLeaderUnitExample();
            CadreLeaderUnitExample.Criteria criteria = example.createCriteria().andLeaderIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "id", "desc"));

            int count = cadreLeaderUnitMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<CadreLeaderUnit> cadreLeaderUnits = cadreLeaderUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("cadreLeaderUnits", cadreLeaderUnits);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            CadreLeader cadreLeader = cadreLeaderMapper.selectByPrimaryKey(id);
            modelMap.put("cadreLeader", cadreLeader);
        }

        return "cadre/cadreLeader/cadreLeader_unit";
    }
}
