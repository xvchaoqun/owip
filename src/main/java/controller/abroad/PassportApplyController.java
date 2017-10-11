package controller.abroad;

import controller.AbroadBaseController;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportApplyView;
import domain.abroad.PassportApplyViewExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
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
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/abroad")
public class PassportApplyController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply_check")
    public String passportApply_check(int id, ModelMap modelMap) {

        PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
        modelMap.put("passportApply", passportApply);
        Map<Integer, Passport> passportMap = passportService.findByCadreId(passportApply.getCadreId());
        modelMap.put("passports", passportMap.values());

        return "abroad/passportApply/passportApply_check";
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_agree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_agree(@CurrentUser SysUserView loginUser, int id, String _expectDate, HttpServletRequest request) {

        PassportApply record = new PassportApply();
        record.setId(id);
        Date date = DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD_CHINA);
        /*if(date==null || date.before(new Date())){
           return failed("证件应交回日期有误");
        }*/
        record.setExpectDate(date);

        record.setStatus(SystemConstants.PASSPORT_APPLY_STATUS_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());

        passportApplyService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "批准申请办理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_disagree(@CurrentUser SysUserView loginUser, int id, String remark, HttpServletRequest request) {

        PassportApply record = new PassportApply();
        record.setId(id);
        record.setRemark(remark);

        record.setStatus(SystemConstants.PASSPORT_APPLY_STATUS_NOT_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());

        passportApplyService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "批准申请办理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply")
    public String passportApply(
            @CurrentUser SysUserView loginUser,
            // 0：办理证件审批 1：批准办理证件审批（未交证件）3：批准办理证件审批（已交证件）2：未批准办理新证件
            @RequestParam(required = false, defaultValue = "0") Byte status,
            Integer cadreId, ModelMap modelMap) {

        // 判断下是否上传了签名 和联系电话
        String sign = loginUser.getSign();
        if (StringUtils.isBlank(sign)
                || new File(springProps.uploadPath + sign).exists() == false
                || StringUtils.isBlank(loginUser.getMobile())) {
            return "abroad/passportApply/passportApply_sign";
        }

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passportApply/passportApply_page";
    }

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply_data")
    public void passportApply_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                   @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_apply") String sort,
                                   @OrderParam(required = false, defaultValue = "desc") String order,
                                   // -1：已删除 0：办理证件审批 1：批准办理证件审批（未交证件）
                                   // 3：批准办理证件审批（已交证件）
                                   // 4：批准办理证件审批（作废）2：未批准办理新证件
                                   @RequestParam(required = false, defaultValue = "0") Byte status,
                                   Integer cadreId,
                                   Integer classId,
                                   String applyDate,
                                   Integer year,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo) throws IOException {


        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportApplyViewExample example = new PassportApplyViewExample();
        PassportApplyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (status == -1) {
            criteria.andIsDeletedEqualTo(true);
        } else {
            criteria.andIsDeletedEqualTo(false);
            if (status == 1) {
                criteria.andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS).andHandleDateIsNull().andAbolishEqualTo(false);
            } else if (status == 3) {
                criteria.andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS).andHandleDateIsNotNull();
            } else if (status == 4) {
                criteria.andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS).andAbolishEqualTo(true);
            } else criteria.andStatusEqualTo(status);
        }

        if (cadreId != null) {

            criteria.andCadreIdEqualTo(cadreId);
        }
        if (classId != null) {
            criteria.andClassIdEqualTo(classId);
        }
        if (year != null) {
            criteria.andApplyDateBetween(DateUtils.parseDate(year + "0101"), DateUtils.parseDate(year + "1230"));
        }
        if (export == 1) {
            passportApply_export(example, response);
            return;
        }

        int count = passportApplyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportApplyView> passportApplys = passportApplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", passportApplys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 管理员添加申请
    @RequiresPermissions("passportApply:edit")
    @RequestMapping("/passportApply_au")
    public String passportApply_au() {

        return "abroad/passportApply/passportApply_au";
    }

    // 逻辑删除
    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportApplyService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 恢复申请
    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportApplyService.batchUnDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量找回申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 真删除，只能删除已标记为“已删除”的记录
    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_doBatchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportApplyService.doBatchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "（真删除）批量删除申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("passportApply:abolish")
    @RequestMapping(value = "/passportApply_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_abolish(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            passportApplyService.abolish(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "作废申请办理证件：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    public void passportApply_export(PassportApplyViewExample example, HttpServletResponse response) {

        List<PassportApplyView> passportApplys = passportApplyViewMapper.selectByExample(example);
        int rownum = passportApplyViewMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"干部", "申办证件名称", "申办日期", "审批状态", "审批人", "审批时间", "应交组织部日期", "实交组织部日期", "证件号码", "接收人", "申请时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            PassportApplyView passportApply = passportApplys.get(i);
            String handleUser = "";
            Integer handleUserId = passportApply.getHandleUserId();
            if (handleUserId != null) {
                SysUserView uv = sysUserService.findById(handleUserId);
                handleUser = uv != null ? uv.getRealname() : "";
            }
            String[] values = {
                    passportApply.getCadreId() + "",
                    passportApply.getClassId() + "",
                    DateUtils.formatDate(passportApply.getApplyDate(), DateUtils.YYYY_MM_DD),
                    passportApply.getStatus() + "",
                    passportApply.getUserId() + "",
                    DateUtils.formatDate(passportApply.getApproveTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    DateUtils.formatDate(passportApply.getExpectDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(passportApply.getHandleDate(), DateUtils.YYYY_MM_DD),
                    passportApply.getCode(),
                    handleUser,
                    DateUtils.formatDate(passportApply.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        String fileName = "申请办理因私出国证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
