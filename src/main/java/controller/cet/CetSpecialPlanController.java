package controller.cet;

import domain.cet.CetSpecialPlan;
import domain.cet.CetSpecialPlanExample;
import domain.cet.CetSpecialPlanExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetSpecialPlanController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetSpecialPlan:list")
    @RequestMapping("/cetSpecialPlan")
    public String cetSpecialPlan() {

        return "cet/cetSpecialPlan/cetSpecialPlan_page";
    }

    @RequiresPermissions("cetSpecialPlan:list")
    @RequestMapping("/cetSpecialPlan_data")
    public void cetSpecialPlan_data(HttpServletResponse response,
                                    Byte type,
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

        CetSpecialPlanExample example = new CetSpecialPlanExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetSpecialPlan_export(example, response);
            return;
        }

        long count = cetSpecialPlanMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetSpecialPlan> records= cetSpecialPlanMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetSpecialPlan.class, cetSpecialPlanMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetSpecialPlan:edit")
    @RequestMapping(value = "/cetSpecialPlan_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialPlan_au(CetSpecialPlan record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetSpecialPlanService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加培训方案：%s", record.getId()));
        } else {

            cetSpecialPlanService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新培训方案：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialPlan:edit")
    @RequestMapping("/cetSpecialPlan_au")
    public String cetSpecialPlan_au(Integer id,
                                    Integer specialId,
                                    ModelMap modelMap) {

        if (id != null) {
            CetSpecialPlan cetSpecialPlan = cetSpecialPlanMapper.selectByPrimaryKey(id);
            if(cetSpecialPlan!=null)
                specialId = cetSpecialPlan.getSpecialId();
            modelMap.put("cetSpecialPlan", cetSpecialPlan);
        }
        modelMap.put("specialId", specialId);
        return "cet/cetSpecialPlan/cetSpecialPlan_au";
    }

    @RequestMapping("/cetSpecialPlan_summary")
    public String cetSpecialPlan_summary(Integer id, Boolean view, ModelMap modelMap) {

        if (id != null) {
            CetSpecialPlan cetSpecialPlan = cetSpecialPlanMapper.selectByPrimaryKey(id);
            modelMap.put("cetSpecialPlan", cetSpecialPlan);
        }

        if(BooleanUtils.isTrue(view))
            return "cet/cetSpecialPlan/cetSpecialPlan_summary_view";

        return "cet/cetSpecialPlan/cetSpecialPlan_summary";
    }

    @RequiresPermissions("cetSpecialPlan:edit")
    @RequestMapping(value = "/cetSpecialPlan_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialPlan_summary(Integer id, String summary) {

        CetSpecialPlan record = new CetSpecialPlan();
        record.setId(id);
        record.setSummary(summary);
        record.setHasSummary(StringUtils.isNotBlank(summary));
        cetSpecialPlanMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_CET, "更新培训内容：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialPlan:del")
    @RequestMapping(value = "/cetSpecialPlan_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialPlan_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetSpecialPlanService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除培训方案：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialPlan:del")
    @RequestMapping(value = "/cetSpecialPlan_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetSpecialPlan_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetSpecialPlanService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训方案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialPlan:changeOrder")
    @RequestMapping(value = "/cetSpecialPlan_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialPlan_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetSpecialPlanService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_CET, "培训方案调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetSpecialPlan_export(CetSpecialPlanExample example, HttpServletResponse response) {

        List<CetSpecialPlan> records = cetSpecialPlanMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训时间（开始）|100","培训时间（结束）|100","培训形式|100","培训内容|100","学时|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetSpecialPlan record = records.get(i);
            String[] values = {
                DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getType() + "",
                            record.getSummary(),
                            record.getPeriod() + "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "培训方案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
