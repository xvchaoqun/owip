package controller.party;

import controller.BaseController;
import domain.ApplyLog;
import domain.ApplyLogExample;
import domain.ApplyLogExample.Criteria;
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
public class ApplyLogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applyLog:list")
    @RequestMapping("/applyLog")
    public String applyLog() {

        return "index";
    }
    @RequiresPermissions("applyLog:list")
    @RequestMapping("/applyLog_person")
    public String applyLog_person(int userId, ModelMap modelMap){

        ApplyLogExample example = new ApplyLogExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("create_time desc");

        List<ApplyLog> applyLogs = applyLogMapper.selectByExample(example);

        modelMap.put("applyLogs", applyLogs);

        return "party/applyLog/applyLog_person";
    }

    @RequiresPermissions("applyLog:list")
    @RequestMapping("/applyLog_page")
    public String applyLog_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "ow_apply_log") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte stage,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplyLogExample example = new ApplyLogExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            criteria.andUserIdEqualTo(userId);
        }
        if (stage!=null) {
            criteria.andStageEqualTo(stage);
        }

        if (export == 1) {
            applyLog_export(example, response);
            return null;
        }

        int count = applyLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplyLog> ApplyLogs = applyLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applyLogs", ApplyLogs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            searchStr += "&userId=" + userId;
        }
        if (stage!=null) {
            searchStr += "&stage=" + stage;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "party/applyLog/applyLog_page";
    }

    @RequiresPermissions("applyLog:edit")
    @RequestMapping(value = "/applyLog_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applyLog_au(ApplyLog record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            applyLogService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "添加入党申请操作日志：%s", record.getId()));
        } else {

            applyLogService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "更新入党申请操作日志：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applyLog:edit")
    @RequestMapping("/applyLog_au")
    public String applyLog_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplyLog applyLog = applyLogMapper.selectByPrimaryKey(id);
            modelMap.put("applyLog", applyLog);
        }
        return "party/applyLog/applyLog_au";
    }

    @RequiresPermissions("applyLog:del")
    @RequestMapping(value = "/applyLog_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applyLog_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applyLogService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "删除入党申请操作日志：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applyLog:del")
    @RequestMapping(value = "/applyLog_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            applyLogService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "批量删除入党申请操作日志：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void applyLog_export(ApplyLogExample example, HttpServletResponse response) {

        List<ApplyLog> applyLogs = applyLogMapper.selectByExample(example);
        int rownum = applyLogMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","当前阶段","操作内容","时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ApplyLog applyLog = applyLogs.get(i);
            String[] values = {
                        applyLog.getUserId()+"",
                                            SystemConstants.APPLY_STAGE_MAP.get(applyLog.getStage()),
                                            applyLog.getContent(),
                                            DateUtils.formatDate(applyLog.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "入党申请操作日志_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
