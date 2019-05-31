package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreStatHistory;
import domain.cadre.CadreStatHistoryExample;
import mixin.MixinUtils;
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
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreStatHistoryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreStatHistory:edit")
    @RequestMapping(value = "/cadreStatHistory", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreStatHistory(byte type) throws IOException {

        cadreStatHistoryService.saveExport(type);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreStatHistory:list")
    @RequestMapping("/cadreStatHistory")
    public String cadreStatHistory(@RequestParam(required = false, defaultValue = "1") Byte status,
                                   ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadreStatHistory/cadreStatHistory_page";
    }

    @RequiresPermissions("cadreStatHistory:list")
    @RequestMapping("/cadreStatHistory_data")
    @ResponseBody
    public void cadreStatHistory_data(HttpServletResponse response,
                                      HttpServletRequest request,
                                      Byte type,
                                      @RequestDateRange DateRange _statDate,
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

        CadreStatHistoryExample example = new CadreStatHistoryExample();
        CadreStatHistoryExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (_statDate.getStart() != null) {
            criteria.andStatDateGreaterThanOrEqualTo(_statDate.getStart());
        }

        if (_statDate.getEnd() != null) {
            criteria.andStatDateLessThanOrEqualTo(_statDate.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            batch_export(example, request, response);
            return;
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    private void batch_export(CadreStatHistoryExample example, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        List<CadreStatHistory> cadreStatHistories = cadreStatHistoryMapper.selectByExample(example);

        Map<String, File> fileMap = new LinkedHashMap<>();
        for (CadreStatHistory record : cadreStatHistories) {

            String filePath = record.getSavePath();
            Byte type = record.getType();
            String typeName = CadreConstants.CADRE_STAT_HISTORY_TYPE_MAP.get(type);

            fileMap.put(typeName + "(" + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD_DOT) + ").xlsx",
                    new File(springProps.uploadPath + filePath));
        }

        DownloadUtils.addFileDownloadCookieHeader(response);

        DownloadUtils.zip(fileMap, "历史信息", request, response);
    }

    @RequiresPermissions("cadreStatHistory:del")
    @RequestMapping(value = "/cadreStatHistory_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            CadreStatHistoryExample example = new CadreStatHistoryExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cadreStatHistoryMapper.deleteByExample(example);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部历史数据：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
