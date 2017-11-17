package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdNotifyLog;
import domain.pmd.PmdNotifyLogExample;
import domain.pmd.PmdNotifyLogExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdNotifyLogController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdNotifyLog:list")
    @RequestMapping("/pmdNotifyLog")
    public String pmdNotifyLog() {

        return "pmd/pmdNotifyLog/pmdNotifyLog_page";
    }

    @RequiresPermissions("pmdNotifyLog:list")
    @RequestMapping("/pmdNotifyLog_data")
    public void pmdNotifyLog_data(HttpServletResponse response,
                                  String orderNo,
                                  @DateTimeFormat(pattern = "yyyyMMdd") Date orderDate,
                                  Byte tranStat,
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdNotifyLogExample example = new PmdNotifyLogExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(orderNo)) {
            criteria.andOrderNoLike("%" + orderNo + "%");
        }
        if (orderDate != null) {
            criteria.andOrderDateEqualTo(orderDate);
        }
        if (tranStat != null) {
            criteria.andTranStatEqualTo(tranStat);
        }

        long count = pmdNotifyLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdNotifyLog> records = pmdNotifyLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdNotifyLog.class, pmdNotifyLogMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdNotifyLog:edit")
    @RequestMapping(value = "/pmdNotifyLog_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNotifyLog_au(PmdNotifyLog record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            pmdNotifyLogService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_PMD, "添加支付通知：%s", record.getId()));
        } else {

            pmdNotifyLogService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_PMD, "更新支付通知：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNotifyLog:edit")
    @RequestMapping("/pmdNotifyLog_au")
    public String pmdNotifyLog_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PmdNotifyLog pmdNotifyLog = pmdNotifyLogMapper.selectByPrimaryKey(id);
            modelMap.put("pmdNotifyLog", pmdNotifyLog);
        }
        return "pmd/pmdNotifyLog/pmdNotifyLog_au";
    }

    @RequiresPermissions("pmdNotifyLog:del")
    @RequestMapping(value = "/pmdNotifyLog_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNotifyLog_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdNotifyLogService.del(id);
            logger.info(addLog(SystemConstants.LOG_PMD, "删除支付通知：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNotifyLog:del")
    @RequestMapping(value = "/pmdNotifyLog_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdNotifyLogService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_PMD, "批量删除支付通知：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
