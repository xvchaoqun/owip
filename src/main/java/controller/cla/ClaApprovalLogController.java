package controller.cla;

import domain.cla.ClaApprovalLog;
import domain.cla.ClaApprovalLogExample;
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
import sys.utils.ExportHelper;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cla")
public class ClaApprovalLogController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalLog:list")
    @RequestMapping("/claApprovalLog")
    public String claApprovalLog(HttpServletResponse response,
                              @SortParam(required = false, defaultValue = "create_time", tableName = "cla_approval_log") String sort,
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

        ClaApprovalLogExample example = new ClaApprovalLogExample();
        ClaApprovalLogExample.Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            approvalLog_export(example, response);
            return null;
        }

        long count = claApprovalLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApprovalLog> approvalLogs = claApprovalLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approvalLogs", approvalLogs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId != null) {
            searchStr += "&userId=" + userId;
        }
        if (typeId != null) {
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
        return "cla/claApprovalLog/claApprovalLog_page";
    }

    public void approvalLog_export(ClaApprovalLogExample example, HttpServletResponse response) {

        List<ClaApprovalLog> approvalLogs = claApprovalLogMapper.selectByExample(example);
        long rownum = claApprovalLogMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"申请记录", "审批人", "审批人类别", "审批状态", "备注", "审批时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ClaApprovalLog approvalLog = approvalLogs.get(i);
            String[] values = {
                    approvalLog.getApplyId() + "",
                    approvalLog.getUserId() + "",
                    approvalLog.getTypeId() + "",
                    approvalLog.getStatus() + "",
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
        String fileName = "干部请假审批记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
