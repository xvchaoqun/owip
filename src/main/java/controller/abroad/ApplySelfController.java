package controller.abroad;

import controller.BaseController;
import domain.*;
import domain.ApplySelfExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.*;

@Controller
public class ApplySelfController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:approval")
    @RequestMapping("/applySelf_approval")
    public String applySelf_approval() {

        return "abroad/applySelf/applySelf_approval";
    }

    @RequiresPermissions("applySelf:approval")
    @RequestMapping(value = "/applySelf_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_approval(@CurrentUser SysUser loginUser,
                                     int applySelfId, int approvalTypeId,
                                     int status, String remark, HttpServletRequest request) {

        int userId = loginUser.getId();
        if (!applySelfService.canApproval(userId, applySelfId, approvalTypeId))
            throw new RuntimeException("您没有权限进行审批");

        Map<Integer, Integer> approvalResultMap = applySelfService.getApprovalResultMap(applySelfId);
        Integer result = approvalResultMap.get(approvalTypeId);
        if (result != null && result != -1) {
            throw new RuntimeException("重复审批");
        }
        if (result != null && result == -1) {
            throw new RuntimeException("不需该审批人身份进行审批");
        }
        if (approvalTypeId == -1) { // 管理员初审
            Assert.isTrue(result == null);
            SecurityUtils.getSubject().checkRole("cadreAdmin");
        }
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        if (approvalTypeId > 0) {
            for (Map.Entry<Integer, ApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                if (key != approvalTypeId) {
                    Integer preResult = approvalResultMap.get(key);
                    if (preResult == null || preResult == 0)
                        throw new RuntimeException(entry.getValue().getName() + "审批未通过，不允许进行当前审批");
                }
                if (key == approvalTypeId) break;
            }
        }
        if (approvalTypeId == 0) {
            // 验证前面的审批均已完成（通过或未通过）
            for (Map.Entry<Integer, ApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                if (key != 0) {
                    Integer preResult = approvalResultMap.get(key);
                    if (preResult == null && preResult != -1)
                        throw new RuntimeException(entry.getValue().getName() + "未完成审批");
                }
            }
        }

        Cadre cadre = cadreService.findByUserId(userId);
        ApprovalLog record = new ApprovalLog();
        record.setApplyId(applySelfId);
        if (approvalTypeId > 0)
            record.setTypeId(approvalTypeId);
        if (approvalTypeId == -1) {
            record.setOdType((byte) 0); // 初审
        }
        if (approvalTypeId == 0) {
            record.setOdType((byte) 1); // 终审
        }
        record.setStatus(status == 1);
        record.setRemark(remark);
        record.setCadreId(cadre.getId());
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        approvalLogMapper.insert(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf")
    public String applySelf() {

        return "index";
    }

    @RequiresPermissions("applySelf:download")
    @RequestMapping("/applySelf_download")
    public void applySelf_download(@CurrentUser SysUser loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);

        if (!SecurityUtils.getSubject().hasRole("cadreAdmin")) { // 干部管理员有下载权限
            int userId = loginUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            Integer applyId = applySelfFile.getApplyId();
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId); // 本人有下载权限
            if (applySelf.getCadreId().intValue() != cadre.getId().intValue()) {

                Set<Integer> cadreIdSet = applySelfService.findApprovalCadreIdSet(loginUser.getId()); // 审批人有下载权限
                if (!cadreIdSet.contains(applySelf.getCadreId()))
                    throw new UnauthorizedException();
            }
        }

        DownloadUtils.download(request, response,
                springProps.uploadPath + applySelfFile.getFilePath(), applySelfFile.getFileName());
    }

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf_view")
    public String applySelf_view(Integer id, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
        Integer cadreId = applySelf.getCadreId();
        Cadre cadre = cadreService.findAll().get(cadreId);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("applySelf", applySelf);

        List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
        modelMap.put("files", files);

        Map<Integer, Integer> approvalResultMap = applySelfService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);
        modelMap.put("approverTypeMap", approverTypeService.findAll());


        // 本年度的申请记录
        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        Integer currentYear = DateUtils.getYear(applySelf.getApplyDate());
        criteria.andApplyDateBetween(DateUtils.parseDate(currentYear +"-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(currentYear +"-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");
        List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
        modelMap.put("currentYear", currentYear);
        modelMap.put("applySelfs", applySelfs);

        return "user/applySelf/applySelf_view";
    }

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf_page")
    public String applySelf_page(@CurrentUser SysUser loginUser, HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 Integer cadreId,
                                 String _applyDate,
                                 Byte type,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (!SecurityUtils.getSubject().hasRole("cadreAdmin")) { //干部管理员可以全部看到，其他审批人需要过滤

            int userId = loginUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            if (cadre.getStatus() != SystemConstants.CADRE_STATUS_NOW) { // 现任干部才有审批权限
                criteria.andIdIsNull();
            } else {
                Set<Integer> cadreIdSet = applySelfService.findApprovalCadreIdSet(loginUser.getId());
                List<Integer> careIdList = new ArrayList<>();
                careIdList.addAll(cadreIdSet);
                if (cadreIdSet.isEmpty())
                    criteria.andIdIsNull();
                else
                    criteria.andCadreIdIn(careIdList);
            }
        }

        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);

            criteria.andCadreIdEqualTo(cadreId);
        }
        if (StringUtils.isNotBlank(_applyDate)) {
            String applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(applyDateStart)) {
                criteria.andApplyDateGreaterThanOrEqualTo(DateUtils.parseDate(applyDateStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(applyDateEnd)) {
                criteria.andApplyDateLessThanOrEqualTo(DateUtils.parseDate(applyDateEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            applySelf_export(example, response);
            return null;
        }

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applySelfs", applySelfs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId != null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (StringUtils.isNotBlank(_applyDate)) {
            searchStr += "&_applyDate=" + _applyDate;
        }
        if (type != null) {
            searchStr += "&type=" + type;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        modelMap.put("approverTypeMap", approverTypeMap);

        return "abroad/applySelf/applySelf_page";
    }

    @RequiresPermissions("applySelf:edit")
    @RequestMapping(value = "/applySelf_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_au(ApplySelf record, String _applyDate, String _startDate, String _endDate, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_applyDate)) {
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_startDate)) {
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_endDate)) {
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            record.setCreateTime(new Date());
            applySelfService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加因私出国申请：%s", record.getId()));
        } else {

            applySelfService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:edit")
    @RequestMapping("/applySelf_au")
    public String applySelf_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            modelMap.put("applySelf", applySelf);

            Cadre cadre = cadreService.findAll().get(applySelf.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "abroad/applySelf/applySelf_au";
    }

    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applySelfService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除因私出国申请：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applySelfService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除因私出国申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void applySelf_export(ApplySelfExample example, HttpServletResponse response) {

        List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
        int rownum = applySelfMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"干部", "申请日期", "出行时间范围", "出发时间", "返回时间", "前往国家或地区", "出国事由", "同行人员", "费用来源", "所需证件", "创建时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ApplySelf applySelf = applySelfs.get(i);
            String[] values = {
                    applySelf.getCadreId() + "",
                    DateUtils.formatDate(applySelf.getApplyDate(), DateUtils.YYYY_MM_DD),
                    applySelf.getType() + "",
                    DateUtils.formatDate(applySelf.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(applySelf.getEndDate(), DateUtils.YYYY_MM_DD),
                    applySelf.getToCountry(),
                    applySelf.getReason(),
                    applySelf.getPeerStaff(),
                    applySelf.getCostSource(),
                    applySelf.getNeedPassports(),

                    DateUtils.formatDate(applySelf.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "因私出国申请_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
