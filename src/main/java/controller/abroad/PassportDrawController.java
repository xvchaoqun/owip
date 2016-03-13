package controller.abroad;

import controller.BaseController;
import domain.*;
import domain.PassportDrawExample.Criteria;
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
import org.springframework.web.multipart.MultipartFile;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class PassportDrawController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw")
    public String passportDraw() {

        return "index";
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_check")
    public String passportDraw_check(int id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        return "abroad/passportDraw/passportDraw_check";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_agree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_agree(@CurrentUser SysUser loginUser, int id, /*String _expectDate,*/ HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        //record.setExpectDate(DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD_CHINA));

        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批准申请使用证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_disagree(@CurrentUser SysUser loginUser, int id, String remark, HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setApproveRemark(remark);

        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_NOT_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批准申请使用证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_page")
    public String passportDraw_page(HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_draw") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    @RequestParam(required = false, defaultValue = "1") byte type,
                                    String _applyDate,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportDrawExample example = new PassportDrawExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
            criteria.andCadreIdEqualTo(cadreId);
        }

        criteria.andTypeEqualTo(type);
        modelMap.put("type", type);

        if(StringUtils.isNotBlank(_applyDate)) {
            String applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(applyDateStart)) {
                criteria.andApplyDateGreaterThanOrEqualTo(DateUtils.parseDate(applyDateStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(applyDateEnd)) {
                criteria.andApplyDateLessThanOrEqualTo(DateUtils.parseDate(applyDateEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (export == 1) {
            passportDraw_export(example, response);
            return null;
        }

        int count = passportDrawMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passportDraws", passportDraws);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId != null) {
            searchStr += "&cadreId=" + cadreId;
        }

        searchStr += "&type=" + type;


        if (StringUtils.isNotBlank(_applyDate)) {
            searchStr += "&_applyDate=" + _applyDate;
        }

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "abroad/passportDraw/passportDraw_page";
    }

    /*@RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_au(PassportDraw record, String _applyDate,
                                  String _startDate, String _endDate, String _expectDate,
                                  String _handleDate, HttpServletRequest request) {

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
        if (StringUtils.isNotBlank(_expectDate)) {
            record.setExpectDate(DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_handleDate)) {
            record.setHandleDate(DateUtils.parseDate(_handleDate, DateUtils.YYYY_MM_DD));
        }
        if (id == null) {
            record.setCreateTime(new Date());
            record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_INIT);
            record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
            passportDrawService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加领取证件：%s", record.getId()));
        } else {

            passportDrawService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新领取证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_au")
    public String passportDraw_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            modelMap.put("passportDraw", passportDraw);

            Cadre cadre = cadreService.findAll().get(passportDraw.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passportDraw/passportDraw_au";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_draw")
    public String passportDraw_draw(Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        return "abroad/passportDraw/passportDraw_draw";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_draw(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                    MultipartFile _drawRecord, String _retrunDate, Integer id) {

        PassportDraw record = new PassportDraw();

        if (_drawRecord == null || _drawRecord.isEmpty()) {
            throw new RuntimeException("请选择证件拍照");
        }
        record.setId(id);
        record.setReturnDate(DateUtils.parseDate(_retrunDate, DateUtils.YYYY_MM_DD));

        String originalFilename = _drawRecord.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String realPath = File.separator
                + "draw" + File.separator + "draw" + File.separator
                + fileName;
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(_drawRecord, new File(springProps.uploadPath + savePath));

        record.setDrawRecord(savePath);
        record.setDrawUserId(loginUser.getId());
        record.setDrawTime(new Date());
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW);

        passportDrawService.updateByPrimaryKeySelective(record);

        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "领取证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_return")
    public String passportDraw_return(Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_SELF){

            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "abroad/passportDraw/passportDraw_return";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_return(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                    MultipartFile _useRecord, String _realStartDate,
                                    String _realEndDate, String realToCountry, String remark, Integer id) {

        PassportDraw record = new PassportDraw();

        if (_useRecord == null || _useRecord.isEmpty()) {
            throw new RuntimeException("请选择证件使用记录拍照");
        }
        record.setId(id);
        record.setRealStartDate(DateUtils.parseDate(_realStartDate, DateUtils.YYYY_MM_DD));
        record.setRealEndDate(DateUtils.parseDate(_realEndDate, DateUtils.YYYY_MM_DD));
        record.setRealToCountry(realToCountry);

        String originalFilename = _useRecord.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String realPath = File.separator
                + "draw" + File.separator + "use" + File.separator
                + fileName;
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(_useRecord, new File(springProps.uploadPath + savePath));

        record.setUseRecord(savePath);
        record.setReturnRemark(remark);
        record.setRealReturnDate(new Date());
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_RETURN);

        passportDrawService.updateByPrimaryKeySelective(record);

        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "归还证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_view")
    public String passportDraw_view( Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        Cadre cadre = cadreService.findAll().get(passportDraw.getCadreId());
        SysUser sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("passportDraw", passportDraw);


        if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_SELF){
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        return "user/passportDraw/passportDraw_view";
    }

    @RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportDrawService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除领取证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportDrawService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除领取证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void passportDraw_export(PassportDrawExample example, HttpServletResponse response) {

        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
        int rownum = passportDrawMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"申请人", "类型", "因私出国申请", "证件", "申请日期", "出发时间", "返回时间", "出国事由", "是否申请签注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            PassportDraw passportDraw = passportDraws.get(i);
            String[] values = {
                    passportDraw.getCadreId() + "",
                    passportDraw.getType() + "",
                    passportDraw.getApplyId() + "",
                    passportDraw.getPassportId() + "",
                    DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(passportDraw.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD),
                    passportDraw.getReason(),
                    passportDraw.getNeedSign() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "领取证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
