package controller.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainExample;
import domain.cet.CetTrainExample.Criteria;
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
@RequestMapping("/cet")
public class CetTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:list")
    @RequestMapping("/cetTrain")
    public String cetTrain(@RequestParam(defaultValue = "1")Boolean isOnCampus,
                           ModelMap modelMap) {

        return isOnCampus?"cet/cetTrain/cetTrain_page":"cet/cetTrain/cetTrain_off_page";
    }

    @RequiresPermissions("cetTrain:list")
    @RequestMapping("/cetTrain_data")
    public void cetTrain_data(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1")Boolean isOnCampus,
                                    Integer planId, // 对外培训为空
                                    String name,
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

        CetTrainExample example = new CetTrainExample();
        CetTrainExample.Criteria criteria = example.createCriteria()
                .andIsOnCampusEqualTo(isOnCampus);
        example.setOrderByClause("create_time desc");

        if(planId!=null){
            criteria.andPlanIdEqualTo(planId);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrain_export(example, response);
            return;
        }

        long count = cetTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrain> records= cetTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrain.class, cetTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_au(CetTrain record,
                              HttpServletRequest request) {

        Integer id = record.getId();
        record.setIsOnCampus(BooleanUtils.isTrue(record.getIsOnCampus()));
        record.setIsFinished(BooleanUtils.isTrue(record.getIsFinished()));

        /*if(record.getEndDate()!=null && record.getEndDate().before(new Date())
        && !record.getIsFinished()){
            return failed("结课日期已过，不可变更为未结课状态。");
        }*/

        if (id == null) {

            cetTrainService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加培训班：%s", record.getId()));
        } else {

            cetTrainMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新培训班：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_au")
    public String cetTrain_au(Integer id,
                              Integer planId,
                              @RequestParam(defaultValue = "1")Boolean isOnCampus,
                              ModelMap modelMap) {
        if (id != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrain", cetTrain);
            planId = cetTrain.getPlanId();
        }

        modelMap.put("planId", planId);
        if(isOnCampus) {
            return "cet/cetTrain/cetTrain_au";
        }

        return "cet/cetTrain/cetTrain_au_off";
    }


    //@RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_summary")
    public String cetTrain_summary(Integer id, Boolean view, ModelMap modelMap) {

        if (id != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrain", cetTrain);
        }

        if(BooleanUtils.isTrue(view))
            return "cet/cetTrain/cetTrain_summary_view";

        return "cet/cetTrain/cetTrain_summary";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_summary(Integer id, String summary) {

        CetTrain record = new CetTrain();
        record.setId(id);
        record.setSummary(summary);
        record.setHasSummary(StringUtils.isNotBlank(summary));
        cetTrainMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CET, "更新内容简介：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_evaNote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_evaNote(int trainId, String evaNote) {

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setEvaNote(evaNote);

        cetTrainMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新培训评课说明：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_evaNote")
    public String cetTrain_evaNote(Integer trainId, ModelMap modelMap) {

        if (trainId != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            modelMap.put("cetTrain", cetTrain);
        }
        return "cet/cetTrain/cetTrain_evaNote";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_evaCloseTime")
    public String cetTrain_evaCloseTime(Integer trainId, ModelMap modelMap) {

        if (trainId != null) {
            modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));
        }
        return "cet/cetTrain/cetTrain_evaCloseTime";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_evaCloseTime", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_evaCloseTime(int trainId, Boolean evaClosed, String _evaCloseTime) {

        Date evaCloseTime = DateUtils.parseDate(_evaCloseTime, DateUtils.YYYY_MM_DD_HH_MM);

        /*if(BooleanUtils.isFalse(evaClosed)){
            if(openTime!=null && closeTime!=null && openTime.after(closeTime)){
                return failed("评课开启时间不能晚于关闭时间");
            }
        }*/

        cetTrainService.updateEvaCloseTime(trainId, BooleanUtils.isTrue(evaClosed), evaCloseTime);
        //logger.info(addLog(LogConstants.LOG_ADMIN, "更新培训评课关闭时间：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_inspectors")
    public String cetTrain_inspectors(Integer id, ModelMap modelMap) {

        if (id != null) {
            modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(id));
        }
        return "cet/cetTrain/cetTrain_inspectors";
    }

    @RequiresPermissions("cetTrain:pub")
    @RequestMapping(value = "/cetTrain_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_finish(HttpServletRequest request,
                                  @RequestParam(required = false, defaultValue = "1") Boolean isFinished, Integer id) {

        if (id != null) {

            CetTrain record = new CetTrain();
            record.setId(id);
            record.setIsFinished(BooleanUtils.isTrue(isFinished));
            cetTrainMapper.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_CET,
                    "培训班结课：%s %s", id, isFinished));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:del")
    @RequestMapping(value = "/cetTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrain_batchDel(HttpServletRequest request,
                                 Integer planId,
                                 @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainService.batchDel(ids, planId);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训班：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetTrain_export(CetTrainExample example, HttpServletResponse response) {

        List<CetTrain> records = cetTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|100","简介|200","开始日期|100","结束日期|100","备注|100","创建时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrain record = records.get(i);
            String[] values = {
                    record.getName(),
                    record.getSummary(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    record.getRemark(),
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "培训班次_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetTrain_selects")
    @ResponseBody
    public Map cetTrain_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainExample example = new CetTrainExample();
        Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("create_time desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = cetTrainMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetTrain> cetTrains = cetTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != cetTrains && cetTrains.size()>0){

            for(CetTrain cetTrain:cetTrains){

                Map<String, Object> option = new HashMap<>();
                option.put("text", cetTrain.getName());
                option.put("id", cetTrain.getId());
                option.put("sn", null);

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
