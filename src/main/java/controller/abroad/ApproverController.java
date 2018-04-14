package controller.abroad;

import domain.abroad.Approver;
import domain.abroad.ApproverExample;
import domain.abroad.ApproverExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/abroad")
public class ApproverController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approver")
    public String approver(HttpServletResponse response,
                           @SortParam(required = false, defaultValue = "sort_order", tableName = "abroad_approver") String sort,
                           @OrderParam(required = false, defaultValue = "desc") String order,
                           Integer cadreId,
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

        ApproverExample example = new ApproverExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            approver_export(example, response);
            return null;
        }

        int count = approverMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Approver> approvers = approverMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approvers", approvers);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId != null) {
            searchStr += "&cadreId=" + cadreId;
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

        return "abroad/approver/approver_page";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approver_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approver_au(Approver record, HttpServletRequest request) {

        Integer id = record.getId();

        if (approverService.idDuplicate(id, record.getCadreId())) {
            return failed("添加重复");
        }
        if (id == null) {
            approverService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "添加审批人：%s", record.getId()));
        } else {

            approverService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新审批人：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approver_au")
    public String approver_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Approver approver = approverMapper.selectByPrimaryKey(id);
            modelMap.put("approver", approver);
        }
        return "abroad/approver/approver_au";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approver_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approver_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            approverService.del(id);
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除审批人：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approver_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            approverService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除审批人：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approver_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approver_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        approverService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ABROAD, "审批人调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void approver_export(ApproverExample example, HttpServletResponse response) {

        List<Approver> approvers = approverMapper.selectByExample(example);
        int rownum = approverMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"干部", "审批人类别"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Approver approver = approvers.get(i);
            String[] values = {
                    approver.getCadreId() + "",
                    approver.getTypeId() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        String fileName = "审批人_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
