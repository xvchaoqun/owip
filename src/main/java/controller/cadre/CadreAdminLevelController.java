package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreAdminLevel;
import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/6/30.
 */
@Controller
public class CadreAdminLevelController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping(value = "/cadreAdminLevel_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_au(CadreAdminLevel record,
                                     String _sWorkTime,
                                     String _eWorkTime,
                                     HttpServletRequest request) {

        Integer id = record.getId();

        record.setsWorkTime(DateUtils.parseDate(_sWorkTime,
                CmTag.getBoolProperty("postTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM));
        record.seteWorkTime(DateUtils.parseDate(_eWorkTime,
                CmTag.getBoolProperty("postTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM));

        if (id == null) {
            cadreAdminLevelMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加任职级经历：%s", record.getId()));
        } else {
            cadreAdminLevelMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新任职级经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping("/cadreAdminLevel_au")
    public String cadreAdminLevel_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreAdminLevel cadreAdminLevel = cadreAdminLevelMapper.selectByPrimaryKey(id);
            modelMap.put("cadreAdminLevel", cadreAdminLevel);
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreAdminLevel/cadreAdminLevel_au";
    }

/*    @RequiresPermissions("cadreAdminLevel:del")
    @RequestMapping(value = "/cadreAdminLevel_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadreAdminLevelMapper.deleteByPrimaryKey(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除任职级经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreAdminLevel:del")
    @RequestMapping(value = "/cadreAdminLevel_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreAdminLevelService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除任职级经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping("/cadreAdminLevel_addDispatchs")
    public String cadreAdminLevel_addDispatchs(HttpServletResponse response, String cls, String type, int id, int cadreId, ModelMap modelMap) {

        Byte dispatchCadreType = null;

        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已选择的干部发文ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>();
        CadreAdminLevel cadreAdminLevel = cadreAdminLevelMapper.selectByPrimaryKey(id);
        if (StringUtils.equalsIgnoreCase(cls, "start")) {

            dispatchCadreType = DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT;

            if (cadreAdminLevel.getStartDispatchCadreId() != null) {
                Integer startDispatchCadreId = cadreAdminLevel.getStartDispatchCadreId();
                dispatchCadreIdSet.add(startDispatchCadreId);
                relateDispatchCadres.add(CmTag.getDispatchCadre(startDispatchCadreId));
            }
        } else if (StringUtils.equalsIgnoreCase(cls, "end")) {

            //dispatchCadreType = DispatchConstants.DISPATCH_CADRE_TYPE_DISMISS;
            if (cadreAdminLevel.getEndDispatchCadreId() != null) {
                Integer endDispatchCadreId = cadreAdminLevel.getEndDispatchCadreId();
                dispatchCadreIdSet.add(endDispatchCadreId);
                relateDispatchCadres.add(CmTag.getDispatchCadre(endDispatchCadreId));
            }
            dispatchCadreType = null; // 结束文件不限制，可以在全部干部发文中选择
        } else {
            throw new OpException("cls 参数有误");
        }
        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);


        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");
            List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, dispatchCadreType);
            modelMap.put("dispatchCadres", dispatchCadres);
            if (StringUtils.equalsIgnoreCase(cls, "start")) { // 只有始任文件有限制
                // 已被始任文件关联的发文
                Set<Integer> otherDispatchCadreRelateSet = cadreAdminLevelService.findOtherDispatchCadreRelateSet(cadreId, id);
                modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
            }
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadreAdminLevel/cadreAdminLevel_addDispatchs";
    }

    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping(value = "/cadreAdminLevel_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_addDispatch(HttpServletRequest request, String cls, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadreAdminLevel record = cadreAdminLevelMapper.selectByPrimaryKey(id);
        if (StringUtils.equalsIgnoreCase(cls, "start")) {
            record.setStartDispatchCadreId(dispatchCadreId);
            if (dispatchCadreId == null) {

                record.setsDispatchId(null);
                record.setsWorkTime(null);
                record.setsPost(null);
            } else {

                DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
                Dispatch dispatch = dispatchCadre.getDispatch();
                record.setsDispatchId(dispatch.getId());
                record.setsWorkTime(dispatch.getWorkTime());
                record.setsPost(dispatchCadre.getPost());
            }
        } else if (StringUtils.equalsIgnoreCase(cls, "end")) {
            record.setEndDispatchCadreId(dispatchCadreId);
            if (dispatchCadreId == null) {

                record.seteDispatchId(null);
                record.seteWorkTime(null);
            } else {

                DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
                Dispatch dispatch = dispatchCadre.getDispatch();
                record.seteDispatchId(dispatch.getId());
                record.seteWorkTime(dispatch.getWorkTime());
            }
        } else {
            throw new OpException("cls 参数错误");
        }

        cadreAdminLevelMapper.updateByPrimaryKey(record); // 可以删除
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改任职级经历%s %s-文号：%s", id, cls, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreAdminLevel:import")
    @RequestMapping("/cadreAdminLevel_import")
    public String cadreAdminLevel_import(ModelMap modelMap) {

        return "cadre/cadreAdminLevel/cadreAdminLevel_import";
    }

    @RequiresPermissions("cadreAdminLevel:import")
    @RequestMapping(value = "/cadreAdminLevel_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_import(HttpServletRequest request, Boolean isMainPost) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CadreAdminLevel> records = new ArrayList<>();
        int row = 1;
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            CadreView cv = cadreService.dbFindByUserId(userId);
            if (cv == null) {
                throw new OpException("第{0}行工作证号[{1}]不在干部库中", row, userCode);
            }
            int cadreId = cv.getId();

            System.out.println("StringUtils.trimToNull(xlsRow.get(2) = " + StringUtils.trimToNull(xlsRow.get(2)));
            Date viceStartDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(2)));
            Date mainStartDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(3)));
            Date viceEndDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(4)));
            if (viceEndDate == null) viceEndDate = mainStartDate;
            Date mainEndDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(5)));
            String vicePost = StringUtils.trimToNull(xlsRow.get(6));
            String mainPost = StringUtils.trimToNull(xlsRow.get(7));
            if (viceStartDate != null || viceEndDate != null || vicePost!=null) {

                CadreAdminLevel record = new CadreAdminLevel();
                record.setCadreId(cadreId);
                record.setAdminLevel(CmTag.getMetaTypeByCode("mt_admin_level_vice").getId());
                record.setsPost(vicePost);
                record.setsWorkTime(viceStartDate);
                record.seteWorkTime(viceEndDate);
                records.add(record);
            }

            if (mainStartDate != null || mainEndDate != null|| mainPost!=null) {

                CadreAdminLevel record = new CadreAdminLevel();
                record.setCadreId(cadreId);
                record.setAdminLevel(CmTag.getMetaTypeByCode("mt_admin_level_main").getId());
                record.setsPost(mainPost);
                record.setsWorkTime(mainStartDate);
                record.seteWorkTime(mainEndDate);
                records.add(record);
            }
        }

        int addCount = cadreAdminLevelService.batchImport(records);
        int totalCount = records.size();
        resultMap.put("successCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部任职级情况成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
