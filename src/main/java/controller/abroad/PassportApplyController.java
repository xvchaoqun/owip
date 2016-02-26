package controller.abroad;

import controller.BaseController;
import domain.Cadre;
import domain.PassportApply;
import domain.PassportApplyExample;
import domain.PassportApplyExample.Criteria;
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
public class PassportApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply")
    public String passportApply() {

        return "index";
    }
    @RequiresPermissions("passportApply:list")
    @RequestMapping("/passportApply_page")
    public String passportApply_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Integer classId,
                                    String applyDate,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

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

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        /*if (StringUtils.isNotBlank(applyDate)) {
            criteria.andApplyDateLike("%" + applyDate + "%");
        }*/

        if (export == 1) {
            passportApply_export(example, response);
            return null;
        }

        int count = passportApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportApply> passportApplys = passportApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passportApplys", passportApplys);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (classId!=null) {
            searchStr += "&classId=" + classId;
        }
        if (StringUtils.isNotBlank(applyDate)) {
            searchStr += "&applyDate=" + applyDate;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "abroad/passportApply/passportApply_page";
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

            passportApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新申请办理因私出国证件：%s", record.getId()));
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
