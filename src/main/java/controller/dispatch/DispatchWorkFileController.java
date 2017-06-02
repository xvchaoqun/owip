package controller.dispatch;

import controller.BaseController;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
import domain.dispatch.DispatchWorkFileExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class DispatchWorkFileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile")
    public String dispatchWorkFile_page(ModelMap modelMap) {

        return "dispatch/dispatchWorkFile/dispatchWorkFile_page";
    }

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile_data")
    public void dispatchWorkFile_data(HttpServletResponse response,
                                      @SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch_work_file") String sort,
                                      @OrderParam(required = false, defaultValue = "desc") String order,
                                      Byte type,
                                      Boolean status,
                                      Integer unitType,
                                      Integer year,
                                      Integer workType,
                                      Integer privacyType,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (unitType != null) {
            criteria.andUnitTypeEqualTo(unitType);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (workType != null) {
            criteria.andWorkTypeEqualTo(workType);
        }
        if (privacyType != null) {
            criteria.andPrivacyTypeEqualTo(privacyType);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            dispatchWorkFile_export(example, response);
            return;
        }

        long count = dispatchWorkFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchWorkFile> records = dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(dispatchWorkFile.class, dispatchWorkFileMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping(value = "/dispatchWorkFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_au(DispatchWorkFile record, HttpServletRequest request) {

        Integer id = record.getId();

        if (dispatchWorkFileService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setStatus(true);
            dispatchWorkFileService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部工作文件：%s", record.getId()));
        } else {

            dispatchWorkFileService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部工作文件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping("/dispatchWorkFile_au")
    public String dispatchWorkFile_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DispatchWorkFile dispatchWorkFile = dispatchWorkFileMapper.selectByPrimaryKey(id);
            modelMap.put("dispatchWorkFile", dispatchWorkFile);
        }
        return "dispatch/dispatchWorkFile/dispatchWorkFile_au";
    }

    @RequiresPermissions("dispatchWorkFile:del")
    @RequestMapping(value = "/dispatchWorkFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchWorkFileService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部工作文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:changeOrder")
    @RequestMapping(value = "/dispatchWorkFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchWorkFileService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部工作文件调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchWorkFile_export(DispatchWorkFileExample example, HttpServletResponse response) {

        List<DispatchWorkFile> records = dispatchWorkFileMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"发文单位", "年度", "所属专项工作", "排序", "发文号", "发文日期", "文件名", "文件", "保密级别", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchWorkFile record = records.get(i);
            String[] values = {
                    record.getUnitType() + "",
                    record.getYear() + "",
                    record.getWorkType() + "",
                    record.getSortOrder() + "",
                    record.getCode(),
                    DateUtils.formatDate(record.getPubDate(), DateUtils.YYYY_MM_DD),
                    record.getFileName(),
                    record.getFilePath(),
                    record.getPrivacyType() + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部工作文件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
