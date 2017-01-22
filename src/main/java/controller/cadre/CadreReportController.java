package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreReport;
import domain.cadre.CadreReportExample;
import domain.cadre.CadreReportExample.Criteria;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreReportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreReport:list")
    @RequestMapping("/cadreReport")
    public String cadreReport() {

        return "index";
    }

    @RequiresPermissions("cadreReport:list")
    @RequestMapping("/cadreReport_page")
    public String cadreReport_page(HttpServletResponse response, ModelMap modelMap) {

        return "cadre/cadreReport/cadreReport_page";
    }

    @RequiresPermissions("cadreReport:list")
    @RequestMapping("/cadreReport_data")
    public void cadreReport_data(HttpServletResponse response,
                                 Integer cadreId,
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

        CadreReportExample example = new CadreReportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_date desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        int count = cadreReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReport> cadreReports = cadreReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreReports);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cadreReport.class, cadreReportMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreReport:edit")
    @RequestMapping(value = "/cadreReport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReport_au(CadreReport record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadreReportService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部工作总结：%s", record.getId()));
        } else {

            cadreReportService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部工作总结：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReport:edit")
    @RequestMapping("/cadreReport_au")
    public String cadreReport_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreReport cadreReport = cadreReportMapper.selectByPrimaryKey(id);
            modelMap.put("cadreReport", cadreReport);
        }
        return "cadre/cadreReport/cadreReport_au";
    }

    @RequiresPermissions("cadreReport:del")
    @RequestMapping(value = "/cadreReport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreReportService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部工作总结：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReport:del")
    @RequestMapping(value = "/cadreReport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreReportService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部工作总结：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
