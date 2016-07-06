package controller.abroad;

import controller.BaseController;
import domain.abroad.ApprovalLog;
import domain.abroad.ApprovalLogExample;
import domain.abroad.ApprovalLogExample.Criteria;
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
import org.springframework.web.bind.annotation.RequestParam;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
public class ApprovalLogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalLog:list")
    @RequestMapping("/approvalLog")
    public String approvalLog() {

        return "index";
    }
    @RequiresPermissions("approvalLog:list")
    @RequestMapping("/approvalLog_page")
    public String approvalLog_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_approval_log") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Integer typeId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApprovalLogExample example = new ApprovalLogExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            approvalLog_export(example, response);
            return null;
        }

        int count = approvalLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApprovalLog> approvalLogs = approvalLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approvalLogs", approvalLogs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            searchStr += "&userId=" + userId;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "abroad/approvalLog/approvalLog_page";
    }

    /*@RequiresPermissions("approvalLog:edit")
    @RequestMapping(value = "/approvalLog_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approvalLog_au(ApprovalLog record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(false);
            record.setCreateTime(new Date());
            approvalLogService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "添加因私出国审批记录：%s", record.getId()));
        } else {

            approvalLogService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "更新因私出国审批记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalLog:edit")
    @RequestMapping("/approvalLog_au")
    public String approvalLog_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApprovalLog approvalLog = approvalLogMapper.selectByPrimaryKey(id);
            modelMap.put("approvalLog", approvalLog);
        }
        return "abroad/approvalLog/approvalLog_au";
    }*/

    /*@RequiresPermissions("approvalLog:del")
    @RequestMapping(value = "/approvalLog_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approvalLog_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            approvalLogService.del(id);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "删除因私出国审批记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalLog:del")
    @RequestMapping(value = "/approvalLog_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            approvalLogService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除因私出国审批记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    public void approvalLog_export(ApprovalLogExample example, HttpServletResponse response) {

        List<ApprovalLog> approvalLogs = approvalLogMapper.selectByExample(example);
        int rownum = approvalLogMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"申请记录","审批人","审批人类别","审批状态","备注","审批时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ApprovalLog approvalLog = approvalLogs.get(i);
            String[] values = {
                        approvalLog.getApplyId()+"",
                                            approvalLog.getUserId()+"",
                                            approvalLog.getTypeId()+"",
                                            approvalLog.getStatus()+"",
                                            approvalLog.getRemark(),
                                            DateUtils.formatDate(approvalLog.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "因私出国审批记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
