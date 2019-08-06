package controller.cg;

import domain.cg.CgRule;
import domain.cg.CgRuleExample;
import domain.cg.CgRuleExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cg")
public class CgRuleController extends CgBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cgRule:list")
    @RequestMapping("/cgRule")
    public String cgRule() {

        return "cg/cgRule/cgRule_page";
    }

    @RequiresPermissions("cgRule:list")
    @RequestMapping("/cgRule_data")
    @ResponseBody
    public void cgRule_data(HttpServletResponse response,
                                    Integer teamId,
                                    Byte type,
                                    Date confirmDate,
                                    String content,
                                    String filePath,
                                
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CgRuleExample example = new CgRuleExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (teamId!=null) {
            criteria.andTeamIdEqualTo(teamId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (confirmDate!=null) {
        criteria.andConfirmDateGreaterThan(confirmDate);
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.like(content));
        }
        if (StringUtils.isNotBlank(filePath)) {
            criteria.andFilePathLike(SqlUtils.like(filePath));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cgRule_export(example, response);
            return;
        }

        long count = cgRuleMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgRule> records= cgRuleMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cgRule.class, cgRuleMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cgRule:edit")
    @RequestMapping(value = "/cgRule_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgRule_au(CgRule record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));
        if (cgRuleService.idDuplicate(id, record.getType(), record.getIsCurrent())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            cgRuleService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CG, "添加委员会或领导小组相关规程：{0}", record.getId()));
        } else {

            cgRuleService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CG, "更新委员会或领导小组相关规程：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgRule:edit")
    @RequestMapping("/cgRule_au")
    public String cgRule_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CgRule cgRule = cgRuleMapper.selectByPrimaryKey(id);
            modelMap.put("cgRule", cgRule);
        }
        return "cg/cgRule/cgRule_au";
    }

    @RequiresPermissions("cgRule:del")
    @RequestMapping(value = "/cgRule_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgRule_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cgRuleService.del(id);
            logger.info(log( LogConstants.LOG_CG, "删除委员会或领导小组相关规程：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgRule:del")
    @RequestMapping(value = "/cgRule_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cgRule_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cgRuleService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除委员会或领导小组相关规程：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cgRule:changeOrder")
    @RequestMapping(value = "/cgRule_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgRule_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cgRuleService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_CG, "委员会或领导小组相关规程调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void cgRule_export(CgRuleExample example, HttpServletResponse response) {

        List<CgRule> records = cgRuleMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属委员会或领导小组|100","类型|100","规程确定时间|100","规程内容|100","相关文件|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CgRule record = records.get(i);
            String[] values = {
                record.getTeamId()+"",
                            record.getType()+"",
                            DateUtils.formatDate(record.getConfirmDate(), DateUtils.YYYY_MM_DD),
                            record.getContent(),
                            record.getFilePath(),
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("委员会或领导小组相关规程(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
