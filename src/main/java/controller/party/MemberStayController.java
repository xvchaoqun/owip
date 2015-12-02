package controller.party;

import controller.BaseController;
import domain.MemberStay;
import domain.MemberStayExample;
import domain.MemberStayExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberStayController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay")
    public String memberStay() {

        return "index";
    }
    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_page")
    public String memberStay_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "id") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberStayExample example = new MemberStayExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            memberStay_export(example, response);
            return null;
        }

        int count = memberStayMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberStay> memberStays = memberStayMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberStays", memberStays);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "party/memberStay/memberStay_page";
    }

    @RequiresPermissions("memberStay:edit")
    @RequestMapping(value = "/memberStay_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_au(MemberStay record, String _growTime,
                                String _abroadTime, String _returnTime, String _payTime,  HttpServletRequest request) {

        Integer id = record.getId();

        if (memberStayService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_abroadTime)) {
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_returnTime)) {
            record.setReturnTime(DateUtils.parseDate(_returnTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            record.setStatus(SystemConstants.MEMBER_STAY_STATUS_APPLY);
            memberStayService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加公派留学生党员申请组织关系暂留：%s", record.getId()));
        } else {

            memberStayService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新公派留学生党员申请组织关系暂留：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:edit")
    @RequestMapping("/memberStay_au")
    public String memberStay_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
            modelMap.put("memberStay", memberStay);
        }
        return "party/memberStay/memberStay_au";
    }

    @RequiresPermissions("memberStay:del")
    @RequestMapping(value = "/memberStay_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberStayService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除公派留学生党员申请组织关系暂留：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:del")
    @RequestMapping(value = "/memberStay_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberStayService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除公派留学生党员申请组织关系暂留：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberStay_export(MemberStayExample example, HttpServletResponse response) {

        List<MemberStay> memberStays = memberStayMapper.selectByExample(example);
        int rownum = memberStayMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","出国时间","预计回国时间","手机号码","状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberStay memberStay = memberStays.get(i);
            String[] values = {
                        memberStay.getUserId()+"",
                                            DateUtils.formatDate(memberStay.getAbroadTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberStay.getReturnTime(), DateUtils.YYYY_MM_DD),
                                            memberStay.getMobile(),
                                            memberStay.getStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "公派留学生党员申请组织关系暂留_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
