package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreStatHistory;
import domain.cadre.CadreStatHistoryExample;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreStatHistoryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreStatHistory:edit")
    @RequestMapping(value = "/cadreStatHistory", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreStatHistory() throws IOException {

        cadreStatHistoryService.asyncStatAll();

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreStatHistory:list")
    @RequestMapping("/cadreStatHistory")
    public String cadreStatHistory() {

        return "index";
    }

    @RequiresPermissions("cadreStatHistory:list")
    @RequestMapping("/cadreStatHistory_page")
    public String cadreStatHistory_page(@RequestParam(required = false, defaultValue = "1") Byte status,
                                        ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadreStatHistory/cadreStatHistory_page";
    }

    @RequiresPermissions("cadreStatHistory:list")
    @RequestMapping("/cadreStatHistory_data")
    @ResponseBody
    public void cadreStatHistory_data(HttpServletResponse response, Byte type,
                                      String _statDate,
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreStatHistoryExample example = new CadreStatHistoryExample();
        CadreStatHistoryExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(_statDate)) {
            String start = _statDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _statDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andStatDateGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andStatDateLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        int count = cadreStatHistoryMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreStatHistory> records = cadreStatHistoryMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreStatHistory:del")
    @RequestMapping(value = "/cadreStatHistory_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            CadreStatHistoryExample example = new CadreStatHistoryExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cadreStatHistoryMapper.deleteByExample(example);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部历史数据：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
