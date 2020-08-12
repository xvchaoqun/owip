package controller.abroad;

import controller.global.OpException;
import domain.abroad.*;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
    public Map passportApply_agree(int id, String _expectDate, HttpServletRequest request) {

        PassportApply record = new PassportApply();
        record.setId(id);
        Date date = DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD_CHINA);
        /*if(date==null || date.before(new Date())){
           return failed("证件应交回日期有误");
        }*/
        record.setExpectDate(date);

        record.setStatus(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS);
        record.setUserId(getAbroadApplyConcatUserId());
        record.setOpUserId(ShiroHelper.getCurrentUserId());
        record.setApproveTime(new Date());

        passportApplyService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "批准申请办理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_disagree(int id, String remark, HttpServletRequest request) {

        PassportApply record = new PassportApply();
        record.setId(id);
        record.setRemark(remark);

        record.setStatus(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS);
        record.setUserId(getAbroadApplyConcatUserId());
        record.setOpUserId(ShiroHelper.getCurrentUserId());
        record.setApproveTime(new Date());

        passportApplyService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "批准申请办理证件：%s", record.getId()));

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
        /*String sign = loginUser.getSign();
        if (StringUtils.isBlank(sign)
                || FileUtils.exists(springProps.uploadPath + sign) == false
                || StringUtils.isBlank(loginUser.getMobile())) {
            return "abroad/passportApply/passportApply_sign";
        }*/

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
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
                                   String code, // 已交证件的证件号码
                                   Integer year,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer[] ids, // 导出的记录
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
                criteria.andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS).andHandleDateIsNull().andAbolishEqualTo(false);
            } else if (status == 3) {
                criteria.andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS).andHandleDateIsNotNull();
            } else if (status == 4) {
                criteria.andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS).andAbolishEqualTo(true);
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
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            passportApply_export(example, response);
            return;
        }
        if(StringUtils.isNotBlank(code)){
            criteria.andCodeLike(SqlUtils.like(code));
        }

        int count = (int) passportApplyViewMapper.countByExample(example);
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

    // 添加/修改已交证件的记录
    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_au(PassportApply record, String passportCode, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            if (StringUtils.isBlank(passportCode)) {
                return failed("证件号码不能为空");
            }
            Passport passport = iAbroadMapper.getPassport(passportCode);
            if (passport == null) {
                return failed("证件号码不存在");
            }
            record.setCadreId(passport.getCadreId());
            record.setClassId(passport.getClassId());

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            record.setStatus(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS);
            record.setAbolish(false);
            record.setUserId(getAbroadApplyConcatUserId());
            record.setOpUserId(ShiroHelper.getCurrentUserId());
            record.setHandleUserId(currentUserId);
            record.setIsDeleted(false);
            record.setCreateTime(new Date());
            passportApplyService.add(record, passport);

            logger.info(addLog(LogConstants.LOG_ABROAD, "添加办理证件记录：%s", record.getId()));
        } else {

            record.setCadreId(null);
            record.setClassId(null);

            passportApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新办理证件记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping("/passportApply_au")
    public String passportApply_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PassportApplyView passportApply = iAbroadMapper.getPassportApplyView(id);
            modelMap.put("passportApply", passportApply);

            modelMap.put("approvalUser", passportApply.getApprovalUser());
            modelMap.put("handleUser", sysUserService.findById(passportApply.getHandleUserId()));
        }

        return "abroad/passportApply/passportApply_au";
    }

    // 管理员添加申请
    @RequiresPermissions("passportApply:edit")
    @RequestMapping("/passportApply_add")
    public String passportApply_add() {

        return "abroad/passportApply/passportApply_add";
    }

    // 逻辑删除
    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportApplyService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 修改应交时间
    @RequiresPermissions("passportApply:edit")
    @RequestMapping("/passportApply_update_expectDate")
    public String passportApply_update_expectDate(int id, ModelMap modelMap) {

        modelMap.put("passportApply", passportApplyMapper.selectByPrimaryKey(id));

        return "abroad/passportApply/passportApply_update_expectDate";
    }

    @RequiresPermissions("passportApply:edit")
    @RequestMapping(value = "/passportApply_update_expectDate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_update_expectDate(int id, @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date expectDate) {


        PassportApply record = new PassportApply();
        record.setExpectDate(expectDate);

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS)
                .andHandleDateIsNull().andAbolishEqualTo(false).andIsDeletedEqualTo(false);

        passportApplyMapper.updateByExampleSelective(record, example);

        logger.info(addLog(LogConstants.LOG_ABROAD, "修改应交时间：%s, %s", id, expectDate));

        return success(FormUtils.SUCCESS);
    }

    // 恢复申请
    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportApplyService.batchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量找回申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 真删除，只能删除已标记为“已删除”的记录
    @RequiresPermissions("passportApply:del")
    @RequestMapping(value = "/passportApply_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map passportApply_doBatchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportApplyService.doBatchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "（真删除）批量删除申请办理因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("passportApply:abolish")
    @RequestMapping(value = "/passportApply_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_abolish(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length > 0) {

            passportApplyService.abolish(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "作废申请办理证件：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportApply:import")
    @RequestMapping("/passportApply_import")
    public String passportApply_import(ModelMap modelMap) {

        return "abroad/passportApply/passportApply_import";
    }

    // 导入办理记录
    @RequiresPermissions("passportApply:import")
    @RequestMapping(value = "/passportApply_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        // <rowIdx, >
        List<Map<String, Object>> records = new ArrayList<>();
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Date now = new Date();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            PassportApply record = new PassportApply();

            String passportCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(passportCode)) {
                continue;
            }

            Passport passport = iAbroadMapper.getPassport(passportCode);
            if (passport == null) {
                throw new OpException("第{0}行证件号码[{1}]不存在", row, passportCode);
            }
            record.setCadreId(passport.getCadreId());
            record.setClassId(passport.getClassId());

            int col = 2;

            Date applyDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));
            if (applyDate == null) {
                throw new OpException("第{0}行申办日期为空", row);
            }
            record.setApplyDate(applyDate);

            Date approvalTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));
            Date expectDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setExpectDate(expectDate);
            record.setApproveTime(approvalTime);

            Date handleDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));
            if (handleDate == null) {
                throw new OpException("第{0}行实交组织部日期为空", row);
            }
            record.setHandleDate(handleDate);

            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setCreateTime(now);

            record.setStatus(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS);
            record.setAbolish(false);
            record.setUserId(getAbroadApplyConcatUserId());
            record.setOpUserId(ShiroHelper.getCurrentUserId());
            record.setHandleUserId(currentUserId);
            record.setIsDeleted(false);

            Map<String, Object> recordMap = new HashMap<>();
            recordMap.put("passportApply", record);
            recordMap.put("passport", passport);

            records.add(recordMap);
        }

        int addCount = passportApplyService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ABROAD,
                "导入证件办理记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    public void passportApply_export(PassportApplyViewExample example, HttpServletResponse response) {

        List<PassportApplyView> passportApplys = passportApplyViewMapper.selectByExample(example);
        int rownum = (int) passportApplyViewMapper.countByExample(example);

        String[] titles = {"申请日期|100", "工作证号|100", "姓名|100", "所在单位及职务|300|left",
                "申办证件名称|100", "审批状态|100", "审批人|100",
                "审批日期|100", "应交组织部日期|130", "实交组织部日期|130", "证件号码|100", "接收人|100"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            PassportApplyView passportApply = passportApplys.get(i);
            CadreView cadre = passportApply.getCadre();

            SysUserView handleUser = passportApply.getHandleUser();
            SysUserView approvalUser = passportApply.getApprovalUser();
            MetaType passportType = CmTag.getMetaType(passportApply.getClassId());
            String[] values = {
                    DateUtils.formatDate(passportApply.getApplyDate(), DateUtils.YYYYMMDD_DOT),
                    cadre.getCode(),
                    cadre.getRealname(),
                    cadre.getTitle(),
                    passportType.getName(),
                    AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_MAP.get(passportApply.getStatus()),
                    approvalUser == null ? "" : approvalUser.getRealname(),
                    DateUtils.formatDate(passportApply.getApproveTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(passportApply.getExpectDate(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(passportApply.getHandleDate(), DateUtils.YYYYMMDD_DOT),
                    passportApply.getCode(),
                    handleUser == null ? "" : handleUser.getRealname()
            };

            valuesList.add(values);
        }
        String fileName = "办理证件记录";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
