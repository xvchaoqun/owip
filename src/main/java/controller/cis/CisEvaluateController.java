package controller.cis;

import controller.BaseController;
import domain.cis.CisEvaluate;
import domain.cis.CisEvaluateExample;
import domain.cis.CisEvaluateExample.Criteria;
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
public class CisEvaluateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisEvaluate:list")
    @RequestMapping("/cisEvaluate")
    public String cisEvaluate() {

        return "index";
    }

    @RequiresPermissions("cisEvaluate:list")
    @RequestMapping("/cisEvaluate_page")
    public String cisEvaluate_page(HttpServletResponse response,ModelMap modelMap) {

        return "cis/cisEvaluate/cisEvaluate_page";
    }

    @RequiresPermissions("cisEvaluate:list")
    @RequestMapping("/cisEvaluate_data")
    public void cisEvaluate_data(HttpServletResponse response,
                                 Integer cadreId,
                                 Byte type,
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

        CisEvaluateExample example = new CisEvaluateExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_date desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        int count = cisEvaluateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisEvaluate> cisEvaluates = cisEvaluateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cisEvaluates);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cisEvaluate.class, cisEvaluateMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cisEvaluate:edit")
    @RequestMapping(value = "/cisEvaluate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisEvaluate_au(CisEvaluate record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cisEvaluateService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加现实表现材料和评价：%s", record.getId()));
        } else {

            cisEvaluateService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新现实表现材料和评价：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisEvaluate:edit")
    @RequestMapping("/cisEvaluate_au")
    public String cisEvaluate_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisEvaluate cisEvaluate = cisEvaluateMapper.selectByPrimaryKey(id);
            modelMap.put("cisEvaluate", cisEvaluate);
        }
        return "cis/cisEvaluate/cisEvaluate_au";
    }

    @RequiresPermissions("cisEvaluate:del")
    @RequestMapping(value = "/cisEvaluate_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisEvaluate_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cisEvaluateService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除现实表现材料和评价：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisEvaluate:del")
    @RequestMapping(value = "/cisEvaluate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisEvaluateService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除现实表现材料和评价：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
