package controller.abroad;

import bean.PassportStatByClassBean;
import bean.PassportStatByPostBean;
import bean.XlsPassport;
import bean.XlsUpload;
import controller.BaseController;
import domain.*;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
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
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

@Controller
public class PassportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passport:list")
    @RequestMapping("/passport")
    public String passport() {

        return "index";
    }
    @RequiresPermissions("passport:list")
    @RequestMapping("/passport_page")
    public String passport_page(// 1:集中管理证件 2:取消集中保管证件 3:丢失证件 4：作废证件 5：保险柜管理
                                    @RequestParam(required = false, defaultValue = "0") byte status,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    HttpServletResponse response,
                                    ModelMap modelMap){

        if (export == 1) {
            safeBoxPassport_export(response);
            return null;
        }

        modelMap.put("status", status);
        if(status==0){
            return "forward:/passport_stat";
        }else if(status==5) {
            return "forward:/safeBox_page";
        }else{
            return "forward:/passportList_page";
        }
    }
    @RequiresPermissions("passport:list")
    @RequestMapping("/passport_stat")
    public String passport_stat(ModelMap modelMap){

        List<PassportStatByClassBean> classBeans = selectMapper.passportStatByClass();
        List<PassportStatByPostBean> postBeans = selectMapper.passportStatByPost();

        modelMap.put("totalCount", selectMapper.passportCount());
        modelMap.put("classBeans", classBeans);
        modelMap.put("postBeans", postBeans);

        return "abroad/passport/passport_stat";
    }
    @RequiresPermissions("passport:list")
    @RequestMapping("/passportList_page")
    public String passportList_page(HttpServletResponse response,
                                @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport") String sort,
                                @OrderParam(required = false, defaultValue = "desc") String order,
                                Integer cadreId,
                                Integer classId,
                                String code,
                                Integer safeBoxId,
                                // 1:集中管理证件 2:取消集中保管证件 3:丢失证件 4：作废证件 5 保险柜管理
                                @RequestParam(required = false, defaultValue = "1") byte status,
                                Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        modelMap.put("status", status);
        Boolean abolish = (status == 4);
        Byte type = null;
        if (status < 4) {
            type = status;
        }

        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        code = StringUtils.trimToNull(code);

        int count = selectMapper.countPassport(cadreId, classId, code, type, safeBoxId, null, abolish);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Passport> passports = selectMapper.selectPassportList
                (cadreId, classId, code, type, safeBoxId, null, abolish, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passports", passports);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId != null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (classId != null) {
            searchStr += "&classId=" + classId;
        }
        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }

        searchStr += "&status=" + status;

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "abroad/passport/passport_page";
    }

    @RequiresPermissions("passport:list")
    @RequestMapping("/safeBoxPassportList")
    public String safeBoxPassportList(HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Byte type,
                                    Byte cancelConfirm,
                                    Integer safeBoxId,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = selectMapper.countPassport(null, null, null, type, safeBoxId, cancelConfirm, false);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<Passport> passports = selectMapper.selectPassportList
                (null, null, null, type, safeBoxId, cancelConfirm, false, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passports", passports);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (type != null) {
            searchStr += "&type=" + type;
        }
        if (cancelConfirm != null) {
            searchStr += "&cancelConfirm=" + cancelConfirm;
        }
        if (safeBoxId!=null) {
            searchStr += "&safeBoxId=" + safeBoxId;
        }

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "abroad/passport/safeBoxPassportList";
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping(value = "/passport_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_cancel(int id, MultipartFile _cancelPic, HttpServletRequest request) {

        if (_cancelPic == null || _cancelPic.isEmpty()) throw new RuntimeException("请选择确认文件");

        String fileName = UUID.randomUUID().toString();
        String realPath = File.separator
                + "passport_cancel" + File.separator
                + fileName;
        String ext = FileUtils.getExtention(_cancelPic.getOriginalFilename());
        String savePath = realPath + ext;
        FileUtils.copyFile(_cancelPic, new File(springProps.uploadPath + savePath));

        Passport record = new Passport();
        record.setId(id);
        record.setCancelPic(savePath);
        record.setCancelTime(new Date());
        record.setCancelConfirm(true);

        passportService.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "确认取消集中管理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping("/passport_cancel")
    public String passport_cancel(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/passport_cancel";
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping(value = "/passport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_au(Passport record, Integer applyId,
                              String _issueDate, String _expiryDate, String _keepDate,
                              Byte type,
                              MultipartFile _lostProof,
                              HttpServletRequest request) {

        Integer id = record.getId();

        MetaType passportType = CmTag.getMetaType("mc_passport_type", record.getClassId());
        if (passportService.idDuplicate(id, record.getCadreId(), record.getClassId(), record.getCode())) {
            return failed(passportType.getName() + "重复，请先作废现有的" + passportType.getName());
        }

        if (StringUtils.isNotBlank(_issueDate)) {
            record.setIssueDate(DateUtils.parseDate(_issueDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_expiryDate)) {
            record.setExpiryDate(DateUtils.parseDate(_expiryDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_keepDate)) {
            record.setKeepDate(DateUtils.parseDate(_keepDate, DateUtils.YYYY_MM_DD));
        }

        if(type!=null && type==SystemConstants.PASSPORT_TYPE_LOST) {

            if (_lostProof == null || _lostProof.isEmpty()) throw new RuntimeException("请选择丢失证明文件");
            String fileName = UUID.randomUUID().toString();
            String realPath = File.separator
                    + "passport_cancel" + File.separator
                    + fileName;
            String ext = FileUtils.getExtention(_lostProof.getOriginalFilename());
            String savePath = realPath + ext;
            FileUtils.copyFile(_lostProof, new File(springProps.uploadPath + savePath));
            record.setLostProof(savePath);
        }

        if (id == null) {
            if(type==null)
                record.setType(SystemConstants.PASSPORT_TYPE_KEEP);
            else
                record.setType(type);

            record.setCancelConfirm(false);
            record.setAbolish(false);
            record.setCreateTime(new Date());
            passportService.add(record, applyId);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加证件：%s", record.getId()));
        } else {

            passportService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:download")
    @RequestMapping("/passport_lostProof_download")
    public void passport_lostProof_download(Integer id,
                                  HttpServletResponse response) throws IOException {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        String lostProof = passport.getLostProof();
        String filePath = springProps.uploadPath + lostProof;
        byte[] bytes = FileUtils.getBytes(filePath);

        MetaType passportType = CmTag.getMetaType("mc_passport_type", passport.getClassId());
        Cadre cadre = cadreService.findAll().get(passport.getCadreId());
        SysUser sysUser = sysUserService.findById(cadre.getUserId());

        String fileName = URLEncoder.encode( sysUser.getRealname()+ "-"+ passportType.getName() +"（丢失证明）"+FileUtils.getExtention(lostProof), "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping("/passport_au")
    public String passport_au(Integer id, Integer applyId, ModelMap modelMap) {

        if (id != null) {
            Passport passport = passportMapper.selectByPrimaryKey(id);
            modelMap.put("passport", passport);

            Cadre cadre = cadreService.findAll().get(passport.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);

        } else if (applyId != null) {
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(applyId);
            Passport passport = new Passport();
            passport.setCadreId(passportApply.getCadreId());
            passport.setClassId(passportApply.getClassId());
            modelMap.put("passport", passport);

            Cadre cadre = cadreService.findAll().get(passport.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passport/passport_au";
    }

    @RequiresPermissions("passport:abolish")
    @RequestMapping(value = "/passport_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_abolish(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            passportService.abolish(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "作废证件：%s",  StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:del")
    @RequestMapping(value = "/passport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:del")
    @RequestMapping(value = "/passport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void safeBoxPassport_export(HttpServletResponse response) {

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        Map<Integer, MetaType> passportType = metaTypeService.metaTypes("mc_passport_type");
        Map<Integer, SafeBox> safeBoxMap = safeBoxService.findAll();
        int rowNum = 0;
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //sheet.setDefaultColumnWidth(12);
        //sheet.setDefaultRowHeight((short)(20*60));
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            XSSFFont font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue("北京师范大学干部因私出国（境）证件一览表");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        for (SafeBox safeBox : safeBoxMap.values()) {

            Integer safeBoxId = safeBox.getId();
            List<Passport> passports = selectMapper.selectPassportList(null, null, null, null,
                    safeBoxId, null, false, new RowBounds());
            int size = passports.size();
            if(size==0) continue;

            PassportExample example = new PassportExample();
            example.createCriteria().andSafeBoxIdEqualTo(safeBoxId).
                    andTypeEqualTo(SystemConstants.PASSPORT_TYPE_KEEP).andAbolishEqualTo(false);
            int keepCount = passportMapper.countByExample(example);

            Row header = sheet.createRow(rowNum);
            header.setHeight((short)(35.7*18));
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue(String.format("保险柜%s：证件总数%s本，其中集中管理%s本，取消集中管理（未确认）%s本。",
                    safeBox.getCode(), size, keepCount, size-keepCount));
            headerCell.setCellStyle(getBgColorStyle(wb));

            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
            XSSFRow firstRow = (XSSFRow) sheet.createRow(rowNum++);
            firstRow.setHeight((short)(35.7*12));
            String[] titles = {"序号", "工作证号", "姓名", "所在单位及职务", "证件名称", "证件号码",
                    "发证件日期", "有效期", "证件状态", "是否借出"};
            for (int i = 0; i < titles.length; i++) {
                XSSFCell cell = firstRow.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(getHeadStyle(wb));

                //sheet.setColumnWidth(i, (short) (35.7*100));
            }
            sheet.setColumnWidth(0, (short) (35.7*50));
            sheet.setColumnWidth(1, (short) (35.7*100));
            sheet.setColumnWidth(2, (short) (35.7*50));
            sheet.setColumnWidth(3, (short) (35.7*300));
            sheet.setColumnWidth(4, (short) (35.7*150));
            sheet.setColumnWidth(5, (short) (35.7*100));
            sheet.setColumnWidth(6, (short) (35.7*100));
            sheet.setColumnWidth(7, (short) (35.7*100));
            sheet.setColumnWidth(8, (short) (35.7*120));
            sheet.setColumnWidth(9, (short) (35.7*100));

            for (int i = 0; i < size; i++) {
                Passport passport = passports.get(i);
                Cadre cadre = cadreMap.get(passport.getCadreId());
                SysUser sysUser = sysUserService.findById(cadre.getUserId());

                String[] values = {
                        String.valueOf(i+1),
                        sysUser.getCode(),
                        sysUser.getRealname(),
                        cadre.getTitle(),
                        passportType.get(passport.getClassId()).getName(),
                        passport.getCode(),
                        DateUtils.formatDate(passport.getIssueDate(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD),
                        SystemConstants.PASSPORT_TYPE_MAP.get(passport.getType()),
                        BooleanUtils.isTrue(passport.getIsLent())?"借出":"-"
                };

                Row row = sheet.createRow(rowNum++);
                row.setHeight((short)(35.7*18));
                for (int j = 0; j < titles.length; j++) {

                    XSSFCell cell = (XSSFCell) row.createCell(j);
                    cell.setCellValue(values[j]);
                    cell.setCellStyle(getBodyStyle(wb));
                }
            }

        }
        try {
            String fileName = "北京师范大学干部因私出国（境）证件一览表(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb)
    {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        //font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static XSSFCellStyle getHeadStyle(XSSFWorkbook wb)
    {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
       /* cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);*/
        return cellStyle;
    }

    private static XSSFCellStyle getBgColorStyle(XSSFWorkbook wb){

        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        cellStyle.setFillForegroundColor(new XSSFColor( new Color(141, 180, 226)));
        //cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        //cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 300);
        cellStyle.setFont(font);


        // 设置单元格边框为细线条
       /* cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);*/
        return cellStyle;
    }

    @RequiresPermissions("passport:import")
    @RequestMapping("/passport_import")
    public String passport_import(ModelMap modelMap) {

        return "abroad/passport/passport_import";
    }

    @RequiresPermissions("passport:import")
    @RequestMapping(value="/passport_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_passport_import( HttpServletRequest request) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsPassport> passports = new ArrayList<XlsPassport>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            XSSFSheet sheet = workbook.getSheetAt(k);

            String sheetName = sheet.getSheetName();

            if(StringUtils.equals(sheetName, "证件")){

                passports.addAll(XlsUpload.fetchPassports(sheet));
            }
        }

        int successCount = passportService.importPassports(passports, SystemConstants.PASSPORT_TYPE_KEEP);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", passports.size());

        return resultMap;
    }

    /*@RequestMapping("/passport_selects")
    @ResponseBody
    public Map passport_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportExample example = new PassportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = passportMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Passport> passports = passportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != passports && passports.size()>0){

            for(Passport passport:passports){

                Select2Option option = new Select2Option();
                option.setText(passport.getName());
                option.setId(passport.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
