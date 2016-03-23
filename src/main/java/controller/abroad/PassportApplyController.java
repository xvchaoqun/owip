package controller.abroad;

import controller.BaseController;
import domain.Cadre;
import domain.PassportApply;
import domain.PassportApplyExample;
import domain.PassportApplyExample.Criteria;
import domain.SysUser;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.PassportApplyMixin;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PassportApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply")
    public String passportApply() {

        return "index";
    }

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply_check")
    public String passportApply_check(int id, ModelMap modelMap) {

        PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
        modelMap.put("passportApply", passportApply);
        modelMap.put("passports", passportService.findByCadreId(passportApply.getCadreId()));

        return "abroad/passportApply/passportApply_check";
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_agree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_agree(@CurrentUser SysUser loginUser, int id, String _expectDate, HttpServletRequest request) {

        PassportApply record = new PassportApply();
        record.setId(id);
        Date date = DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD_CHINA);
        if(date==null || date.before(new Date())){
            throw new RuntimeException("证件应交回日期有误");
        }
        record.setExpectDate(date);

        record.setStatus(SystemConstants.PASSPORT_APPLY_STATUS_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());

        passportApplyService.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批准申请办理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_disagree(@CurrentUser SysUser loginUser, int id, String remark, HttpServletRequest request) {

        PassportApply record = new PassportApply();
        record.setId(id);
        record.setRemark(remark);

        record.setStatus(SystemConstants.PASSPORT_APPLY_STATUS_NOT_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());

        passportApplyService.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批准申请办理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply_page")
    public String passportApply_page(
            @CurrentUser SysUser loginUser,
            // 0：办理证件审批 1：批准办理证件审批（未交证件）3：批准办理证件审批（已交证件）2：未批准办理新证件
            @RequestParam(required = false, defaultValue = "0")  Byte status,
            Integer cadreId,ModelMap modelMap) {

        // 判断下是否上传了签名 和联系电话
        String sign = loginUser.getSign();
        if(StringUtils.isBlank(sign)
                || new File(springProps.uploadPath + sign).exists()==false
                || StringUtils.isBlank(loginUser.getMobile())) {
            return "abroad/passportApply/passportApply_sign";
        }

        modelMap.put("status", status);

        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passportApply/passportApply_page";
    }

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply_data")
    public void passportApply_data(@CurrentUser SysUser loginUser, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_apply") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 // 0：办理证件审批 1：批准办理证件审批（未交证件）3：批准办理证件审批（已交证件）2：未批准办理新证件
                                 @RequestParam(required = false, defaultValue = "0")  Byte status,
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

        PassportApplyExample example = new PassportApplyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if(status==1){
            criteria.andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS).andHandleDateIsNull();
        }else if(status==3){
            criteria.andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS).andHandleDateIsNotNull();
        }else
            criteria.andStatusEqualTo(status);

        if (cadreId!=null) {

            criteria.andCadreIdEqualTo(cadreId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        if(year!=null){
            criteria.andApplyDateBetween(DateUtils.parseDate(year + "0101"), DateUtils.parseDate(year + "1230"));
        }
        if (export == 1) {
            passportApply_export(example, response);
            return;
        }

        int count = passportApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportApply> passportApplys = passportApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", passportApplys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap, PassportApply.class, PassportApplyMixin.class);
        return;
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_au(PassportApply record,String _applyDate, String _expectDate, String _handleDate, HttpServletRequest request) {

        Integer id = record.getId();
        if(StringUtils.isNotBlank(_applyDate)){
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_expectDate)){
            record.setExpectDate(DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleDate)) {
            record.setHandleDate(DateUtils.parseDate(_handleDate, DateUtils.YYYY_MM_DD));
        }
        if (id == null) {
            record.setCreateTime(new Date());
            passportApplyService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加申请办理因私出国证件：%s", record.getId()));
        } else {

           /* passportApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新申请办理因私出国证件：%s", record.getId()));*/
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping("/passportApply_au")
    public String passportApply_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            modelMap.put("passportApply", passportApply);

            Cadre cadre = cadreService.findAll().get(passportApply.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passportApply/passportApply_au";
    }

    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportApplyService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除申请办理因私出国证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            passportApplyService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void passportApply_export(PassportApplyExample example, HttpServletResponse response) {

        List<PassportApply> passportApplys = passportApplyMapper.selectByExample(example);
        int rownum = passportApplyMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"干部","申办证件名称","申办日期","审批状态","审批人","审批时间","应交组织部日期","实交组织部日期","申请时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            PassportApply passportApply = passportApplys.get(i);
            String[] values = {
                        passportApply.getCadreId()+"",
                                            passportApply.getClassId()+"",
                                            DateUtils.formatDate(passportApply.getApplyDate(), DateUtils.YYYY_MM_DD),
                                            passportApply.getStatus()+"",
                                            passportApply.getUserId()+"",
                                            DateUtils.formatDate(passportApply.getApproveTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                                            DateUtils.formatDate(passportApply.getExpectDate(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(passportApply.getHandleDate(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(passportApply.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "申请办理因私出国证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
