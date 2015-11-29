package controller.unit;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UnitAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unitAdmin:list")
    @RequestMapping("/unitAdmin")
    public String unitAdmin() {

        return "index";
    }
    @RequiresPermissions("unitAdmin:list")
    @RequestMapping("/unitAdmin_page")
    public String unitAdmin_page(HttpServletResponse response,
                                    int groupId, ModelMap modelMap) {

        UnitAdminExample example = new UnitAdminExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order desc");

        List<UnitAdmin> unitAdmins = unitAdminMapper.selectByExample(example);
        modelMap.put("unitAdmins", unitAdmins);

        modelMap.put("cadreMap", cadreService.findAll());
        modelMap.put("postTypeMap", metaTypeService.metaTypes("mc_post"));

        return "unit/unitAdmin/unitAdmin_page";
    }

    @RequiresPermissions("unitAdmin:edit")
    @RequestMapping(value = "/unitAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitAdmin_au(UnitAdmin record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            unitAdminService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加行政班子成员信息：%s", record.getId()));
        } else {

            unitAdminService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新行政班子成员信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitAdmin:edit")
    @RequestMapping("/unitAdmin_au")
    public String unitAdmin_au(Integer id,int groupId, ModelMap modelMap) {

        if (id != null) {
            UnitAdmin unitAdmin = unitAdminMapper.selectByPrimaryKey(id);
            modelMap.put("unitAdmin", unitAdmin);
            groupId = unitAdmin.getGroupId();
            Cadre cadre = cadreService.findAll().get(unitAdmin.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        UnitAdminGroup unitAdminGroup = unitAdminGroupService.findAll().get(groupId);
        modelMap.put("unitAdminGroup", unitAdminGroup);
        modelMap.put("unit", unitService.findAll().get(unitAdminGroup.getUnitId()));

        return "unit/unitAdmin/unitAdmin_au";
    }

    @RequiresPermissions("unitAdmin:del")
    @RequestMapping(value = "/unitAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitAdmin_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitAdminService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除行政班子成员信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitAdmin:del")
    @RequestMapping(value = "/unitAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            unitAdminService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除行政班子成员信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitAdmin:changeOrder")
    @RequestMapping(value = "/unitAdmin_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitAdmin_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitAdminService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "行政班子成员信息调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitAdmin_export(UnitAdminExample example, HttpServletResponse response) {

        List<UnitAdmin> unitAdmins = unitAdminMapper.selectByExample(example);
        int rownum = unitAdminMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属班子","关联干部","是否管理员"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            UnitAdmin unitAdmin = unitAdmins.get(i);
            String[] values = {
                        unitAdmin.getGroupId()+"",
                                            unitAdmin.getCadreId()+"",
                                            unitAdmin.getIsAdmin()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "行政班子成员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
