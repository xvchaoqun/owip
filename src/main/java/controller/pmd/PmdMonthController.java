package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdMonth;
import domain.pmd.PmdMonthExample;
import mixin.MixinUtils;
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
import sys.tool.fancytree.TreeNode;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/pmd")
public class PmdMonthController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdMonth:list")
    @RequestMapping("/pmdMonth")
    public String pmdMonth(ModelMap modelMap) {

        PmdMonth currentMonth = pmdMonthService.getMonth(new Date());
        modelMap.put("currentMonth", currentMonth);
        return "pmd/pmdMonth/pmdMonth_page";
    }

    @RequiresPermissions("pmdMonth:list")
    @RequestMapping("/pmdMonth_data")
    public void pmdMonth_data(HttpServletResponse response,
                               @DateTimeFormat(pattern = "yyyy-MM") Date payMonth,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdMonthExample example = new PmdMonthExample();
        PmdMonthExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (payMonth != null) {
            criteria.andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        }

        long count = pmdMonthMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdMonth> records = pmdMonthMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdMonth.class, pmdMonthMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_create", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_create(HttpServletRequest request) {

        pmdMonthService.create();

        logger.info(addLog(SystemConstants.LOG_PMD, "新建缴费月份"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_selectParties", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_selectParties(int monthId, @RequestParam(value = "partyIds[]")Integer[] partyIds,
                                          HttpServletRequest request) {

        pmdMonthService.updatePartyIds(monthId, partyIds);

        logger.info(addLog(SystemConstants.LOG_PMD, "设置/更新缴费月份的分党委"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_selectParties")
    public String pmdMonth_selectParties(Integer monthId, ModelMap modelMap) {

        if (monthId != null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
            modelMap.put("pmdMonth", pmdMonth);
        }
        return "pmd/pmdMonth/pmdMonth_selectParties";
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_selectParties_tree")
    @ResponseBody
    public TreeNode pmdMonth_selectParties_tree(int monthId) throws IOException {

        TreeNode tree = pmdMonthService.getPartyTree(monthId);

        return tree;
        /*Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;*/
    }

    // 启动
    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_start", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_start(int monthId, HttpServletRequest request) {

        pmdMonthService.start(monthId);

        logger.info(addLog(SystemConstants.LOG_PMD, "启动缴费， %s", monthId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_start")
    public String pmdMonth_start(Integer monthId, ModelMap modelMap) {

        if (monthId != null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
            modelMap.put("pmdMonth", pmdMonth);

            Set<Integer> partyIdSet = pmdMonthService.getMonthPartyIdSet(monthId);
            modelMap.put("partyIdSet", partyIdSet);
        }
        return "pmd/pmdMonth/pmdMonth_start";
    }

    // 结算
    @RequiresPermissions("pmdMonth:end")
    @RequestMapping(value = "/pmdMonth_end", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_end(int monthId, HttpServletRequest request) {

        pmdMonthService.end(monthId);

        logger.info(addLog(SystemConstants.LOG_PMD, "结算缴费， %s", monthId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:end")
    @RequestMapping("/pmdMonth_end")
    public String pmdMonth_end(Integer monthId, ModelMap modelMap) {

        if (monthId != null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
            modelMap.put("pmdMonth", pmdMonth);
        }

        return "pmd/pmdMonth/pmdMonth_end";
    }

    /*@RequiresPermissions("pmdMonth:del")
    @RequestMapping(value = "/pmdMonth_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdMonthService.del(id);
            logger.info(addLog(SystemConstants.LOG_PMD, "删除每月党费缴纳配置：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:del")
    @RequestMapping(value = "/pmdMonth_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdMonthService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_PMD, "批量删除每月党费缴纳配置：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
