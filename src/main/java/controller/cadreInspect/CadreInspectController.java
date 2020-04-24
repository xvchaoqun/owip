package controller.cadreInspect;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadreInspect.CadreInspect;
import domain.cadreInspect.CadreInspectView;
import domain.cadreInspect.CadreInspectViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import persistence.sc.IScMapper;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreInspectController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInspect:list")
    @RequestMapping("/cadreInspect")
    public String cadreInspect(
            @RequestParam(required = false, defaultValue =
                    CadreConstants.CADRE_INSPECT_STATUS_NORMAL + "") Byte status,
            Integer userId, ModelMap modelMap) {

        modelMap.put("status", status);
        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "cadreInspect/cadreInspect_page";
    }

    @RequiresPermissions("cadreInspect:list")
    @RequestMapping("/cadreInspect_data")
    public void cadreInspect_data(HttpServletResponse response,
                                  @RequestParam(required = false, defaultValue =
                                          CadreConstants.CADRE_INSPECT_STATUS_NORMAL + "") Byte status,
                                  Integer userId,
                                  Integer adminLevel,
                                  Integer postType,
                                  String title,
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

        CadreInspectViewExample example = new CadreInspectViewExample();
        CadreInspectViewExample.Criteria criteria = example.createCriteria()
                .andInspectStatusEqualTo(status);
        example.setOrderByClause("inspect_sort_order asc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (adminLevel != null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (postType != null) {
            criteria.andPostTypeEqualTo(postType);
        }

        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.like(title));
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andInspectIdIn(Arrays.asList(ids));
            cadreInspect_export(example, response);
            return;
        }

        long count = cadreInspectViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreInspectView> Cadres = cadreInspectViewMapper.
                selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }


    // 只有干部库中类型为考察对象时，才可以修改干部库的信息
    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping(value = "/cadreInspect_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_au(int userId,
                                  Integer recordId, Integer assignUnitPostId,
                                  Integer inspectId, String inspectRemark,
                                  Cadre cadreRecord, HttpServletRequest request) {

        CadreInspect record = new CadreInspect();
        record.setId(inspectId);
        record.setRecordId(recordId);
        record.setAssignUnitPostId(assignUnitPostId);
        record.setRemark(inspectRemark);
        if (inspectId == null) {
            record.setRecordUserId(ShiroHelper.getCurrentUserId());
            cadreInspectService.insertOrUpdateSelective(userId, record, cadreRecord);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加考察对象：%s", record.getId()));
        } else {
            cadreInspectService.updateByPrimaryKeySelective(record, cadreRecord);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新考察对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping("/cadreInspect_au")
    public String cadreInspect_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(id);
            modelMap.put("cadreInspect", cadreInspect);
            CadreView cadre = iCadreMapper.getCadre(cadreInspect.getCadreId());
            modelMap.put("cadre", cadre);

            IScMapper iScMapper = CmTag.getBean(IScMapper.class);
            if(iScMapper!=null) {
                Integer recordId = cadreInspect.getRecordId();
                if (recordId != null) {
                    modelMap.put("scRecord", iScMapper.getScRecordView(recordId));
                }
            }

            Integer assignUnitPostId = cadreInspect.getAssignUnitPostId();
            if(assignUnitPostId!=null){
                modelMap.put("assignUnitPost", unitPostMapper.selectByPrimaryKey(assignUnitPostId));
            }
        }

        return "cadreInspect/cadreInspect_au";
    }

    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping("/cadreInspect_pass")
    public String cadreInspect_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(id);
            CadreView cadre = iCadreMapper.getCadre(cadreInspect.getCadreId());
            modelMap.put("cadreInspect", cadreInspect);
            modelMap.put("cadre", cadre);
        }
        return "cadreInspect/cadreInspect_pass";
    }

    // 通过常委会任命
    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping(value = "/cadreInspect_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_pass(Integer inspectId, String inspectRemark,
                                    Cadre cadreRecord, HttpServletRequest request) {

        CadreInspect record = new CadreInspect();
        record.setId(inspectId);
        record.setRemark(inspectRemark);

        Cadre cadre = cadreInspectService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(LogConstants.LOG_ADMIN, "考察对象通过常委会任命：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    // 返回考察对象
    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping(value = "/cadreInspect_rollback", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_rollback(@RequestParam(required = false, defaultValue = "0") Boolean isCadre,
                                        Integer inspectId,HttpServletRequest request) {

        cadreInspectService.rollback(inspectId,isCadre);

        logger.info(addLog(LogConstants.LOG_ADMIN, "常委会任命返回考察对象：%s",inspectId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping("/cadreInspect_rollback")
    public String cadreInspect_rollback(Integer id, ModelMap modelMap) {

        modelMap.put("id",id);
        return "cadreInspect/cadreInspect_rollback";
    }

    @RequiresPermissions("cadreInspect:abolish")
    @RequestMapping(value = "/cadreInspect_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreInspect_abolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreInspectService.abolish(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "撤销考察对象：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:abolish")
    @RequestMapping(value = "/cadreInspect_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreInspect_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            cadreInspectService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除考察对象：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:changeOrder")
    @RequestMapping(value = "/cadreInspect_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreInspectService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "考察对象调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    private void cadreInspect_export(CadreInspectViewExample example, HttpServletResponse response) {

        SXSSFWorkbook wb = cadreInspectExportService.export(example);

        String fileName = CmTag.getSysConfig().getSchoolName() + "考察对象_" + DateUtils.formatDate(new Date(), "yyyyMMdd");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    @RequiresPermissions("cadreInspect:import")
    @RequestMapping("/cadreInspect_import")
    public String cadreInspect_import() {

        return "cadreInspect/cadreInspect_import";
    }

    @RequiresPermissions("cadreInspect:import")
    @RequestMapping(value = "/cadreInspect_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Cadre> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            Cadre record = new Cadre();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(userCode)){
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            record.setUserId(userId);

            record.setTitle(StringUtils.trimToNull(xlsRow.get(2)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(3)));

            records.add(record);
        }

        int addCount = cadreInspectService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入考察对象成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
