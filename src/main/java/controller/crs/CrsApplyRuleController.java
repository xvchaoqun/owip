package controller.crs;

import controller.CrsBaseController;
import domain.crs.CrsApplyRule;
import domain.crs.CrsApplyRuleExample;
import domain.crs.CrsApplyRuleExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CrsApplyRuleController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsApplyRule:list")
    @RequestMapping("/crsApplyRule")
    public String crsApplyRule(@RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "crs/crsApplyRule/crsApplyRule_page";
    }

    @RequiresPermissions("crsApplyRule:list")
    @RequestMapping("/crsApplyRule_data")
    @ResponseBody
    public void crsApplyRule_data(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                  Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsApplyRuleExample example = new CrsApplyRuleExample();
        Criteria criteria = example.createCriteria();

        Date now = new Date();
        if(cls==1){
            // 有效规则
            criteria.andStatusNotEqualTo(SystemConstants.CRS_APPLY_RULE_STATUS_DELETE);
            criteria.andEndTimeGreaterThanOrEqualTo(now);
        }else if(cls==2){
            // 过期规则
            criteria.andStatusNotEqualTo(SystemConstants.CRS_APPLY_RULE_STATUS_DELETE);
            criteria.andEndTimeLessThan(now);
        }else if(cls==3){
            criteria.andStatusEqualTo(SystemConstants.CRS_APPLY_RULE_STATUS_DELETE);
        }

        example.setOrderByClause("start_time asc, end_time asc");

        long count = crsApplyRuleMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsApplyRule> records= crsApplyRuleMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsApplyRule.class, crsApplyRuleMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsApplyRule:edit")
    @RequestMapping(value = "/crsApplyRule_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplyRule_au(CrsApplyRule record,
                                  HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            crsApplyRuleService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加报名规则：%s", record.getId()));
        } else {

            crsApplyRuleService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新报名规则：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplyRule:edit")
    @RequestMapping("/crsApplyRule_au")
    public String crsApplyRule_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsApplyRule crsApplyRule = crsApplyRuleMapper.selectByPrimaryKey(id);
            modelMap.put("crsApplyRule", crsApplyRule);
        }
        return "crs/crsApplyRule/crsApplyRule_au";
    }

    @RequiresPermissions("crsApplyRule:del")
    @RequestMapping(value = "/crsApplyRule_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplyRule_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            crsApplyRuleService.del(id);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除报名规则：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplyRule:del")
    @RequestMapping(value = "/crsApplyRule_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            crsApplyRuleService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除报名规则：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
