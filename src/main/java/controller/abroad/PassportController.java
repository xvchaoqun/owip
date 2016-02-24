package controller.abroad;

import controller.BaseController;
import domain.Cadre;
import domain.Passport;
import domain.PassportExample;
import domain.PassportExample.Criteria;
import domain.SysUser;
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
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public String passport_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Integer classId,
                                    String code,
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

        PassportExample example = new PassportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            passport_export(example, response);
            return null;
        }

        int count = passportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Passport> passports = passportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passports", passports);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (classId!=null) {
            searchStr += "&classId=" + classId;
        }
        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if (type!=null) {
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
        return "abroad/passport/passport_page";
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping(value = "/passport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_au(Passport record, String _issueDate, String _expiryDate, String _keepDate, HttpServletRequest request) {

        Integer id = record.getId();

        if (passportService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_issueDate)){
            record.setIssueDate(DateUtils.parseDate(_issueDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_expiryDate)){
            record.setExpiryDate(DateUtils.parseDate(_expiryDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_keepDate)){
            record.setKeepDate(DateUtils.parseDate(_keepDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            passportService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加因私出国证件：%s", record.getId()));
        } else {

            passportService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新因私出国证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping("/passport_au")
    public String passport_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Passport passport = passportMapper.selectByPrimaryKey(id);
            modelMap.put("passport", passport);

            Cadre cadre = cadreService.findAll().get(passport.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passport/passport_au";
    }

    @RequiresPermissions("passport:del")
    @RequestMapping(value = "/passport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除因私出国证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:del")
    @RequestMapping(value = "/passport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            passportService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除因私出国证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void passport_export(PassportExample example, HttpServletResponse response) {

        List<Passport> passports = passportMapper.selectByExample(example);
        int rownum = passportMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"干部","证件名称","证件号码","发证机关","发证日期","有效期","集中保管日期","存放保险柜编号","是否借出","类型","取消集中保管原因","创建时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Passport passport = passports.get(i);
            String[] values = {
                        passport.getCadreId()+"",
                                            passport.getClassId()+"",
                                            passport.getCode(),
                                            passport.getAuthority(),
                                            DateUtils.formatDate(passport.getIssueDate(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(passport.getKeepDate(), DateUtils.YYYY_MM_DD),
                                            passport.getSafeCode(),
                                            passport.getIsLent()+"",
                                            passport.getType()+"",
                                            passport.getCancelType()+"",
                                            DateUtils.formatDate(passport.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "因私出国证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
