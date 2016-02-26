package controller.abroad;

import controller.BaseController;
import domain.Cadre;
import domain.PassportDraw;
import domain.PassportDrawExample;
import domain.PassportDrawExample.Criteria;
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
public class PassportDrawController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw")
    public String passportDraw() {

        return "index";
    }
    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_page")
    public String passportDraw_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Byte type,
                                    Integer passportId,
                                    String applyDate,
                                    String startDate,
                                    String endDate,
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

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        /*if (StringUtils.isNotBlank(type)) {
            criteria.andTypeLike("%" + type + "%");
        }*/
        if (passportId!=null) {
            criteria.andPassportIdEqualTo(passportId);
        }
        /*if (StringUtils.isNotBlank(applyDate)) {
            criteria.andApplyDateLike("%" + applyDate + "%");
        }
        if (StringUtils.isNotBlank(startDate)) {
            criteria.andStartDateLike("%" + startDate + "%");
        }
        if (StringUtils.isNotBlank(endDate)) {
            criteria.andEndDateLike("%" + endDate + "%");
        }*/

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

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        /*if (StringUtils.isNotBlank(type)) {
            searchStr += "&type=" + type;
        }*/
        if (passportId!=null) {
            searchStr += "&passportId=" + passportId;
        }
        if (StringUtils.isNotBlank(applyDate)) {
            searchStr += "&applyDate=" + applyDate;
        }
        if (StringUtils.isNotBlank(startDate)) {
            searchStr += "&startDate=" + startDate;
        }
        if (StringUtils.isNotBlank(endDate)) {
            searchStr += "&endDate=" + endDate;
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

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_au(PassportDraw record, String _applyDate,
                                  String _startDate, String _endDate,String _expectDate,
                                  String _handleDate, HttpServletRequest request) {

        Integer id = record.getId();
        if(StringUtils.isNotBlank(_applyDate)){
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_expectDate)){
            record.setExpectDate(DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleDate)){
            record.setHandleDate(DateUtils.parseDate(_handleDate, DateUtils.YYYY_MM_DD));
        }
        if (id == null) {
            record.setCreateTime(new Date());
            record.setStatus((byte)0);
            passportDrawService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加领取证件：%s", record.getId()));
        } else {

            passportDrawService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新领取证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

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


        if (null != ids && ids.length>0){
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

        String[] titles = {"申请人","类型","因私出国申请","证件","申请日期","出发时间","返回时间","出国事由","是否申请签注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            PassportDraw passportDraw = passportDraws.get(i);
            String[] values = {
                        passportDraw.getCadreId()+"",
                                            passportDraw.getType()+"",
                                            passportDraw.getApplyId()+"",
                                            passportDraw.getPassportId()+"",
                                            DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(passportDraw.getStartDate(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD),
                                            passportDraw.getReason(),
                                            passportDraw.getNeedSign()+""
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
