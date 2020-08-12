package controller.qy;

import domain.qy.*;
import domain.qy.QyYearRewardExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class QyYearRewardController extends QyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyYearReward")
    public String qyYearReward(Integer yearId){

        return "qy/qyYearReward/qyYearReward_page";
    }

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyYearReward_data")
    @ResponseBody
    public void qyYearReward_data(HttpServletResponse response,
                                    Integer yearId,
                                    Integer rewardId,
                                
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        QyYearRewardExample example = new QyYearRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (yearId!=null) {
            criteria.andYearIdEqualTo(yearId);
        }
        if (rewardId!=null) {
            criteria.andRewardIdEqualTo(rewardId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            qyYearReward_export(example, response);
            return;
        }

        long count = qyYearRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<QyYearReward> records= qyYearRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(qyYearReward.class, qyYearRewardMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYearReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYearReward_au(QyYearReward record, HttpServletRequest request) {

        Integer id = record.getId();
        Integer yearId = record.getYearId();
        Integer rewardId = record.getRewardId();
        if (qyYearRewardService.idDuplicate(id, yearId,rewardId)) {
            return failed("添加重复");
        }
        if (id == null) {
            
            qyYearRewardService.insertSelective(record);
            logger.info(log( LogConstants.LOG_QY, "添加七一表彰_每年度奖项：{0}", record.getId()));
        } else {

            qyYearRewardService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_QY, "更新七一表彰_每年度奖项：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping("/qyYearReward_au")
    public String qyYearReward_au(Integer id, Integer yearId,ModelMap modelMap) {

        if (id != null) {
            QyYearReward qyYearReward = qyYearRewardMapper.selectByPrimaryKey(id);
            modelMap.put("qyYearReward", qyYearReward);
        }
        if (yearId != null) {
            QyYear qyYear = qyYearMapper.selectByPrimaryKey(yearId);
            modelMap.put("qyYear", qyYear);
        }
        return "qy/qyYearReward/qyYearReward_au";
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYearReward_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYearReward_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            qyYearRewardService.del(id);
            logger.info(log( LogConstants.LOG_QY, "删除七一表彰_每年度奖项：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYearReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map qyYearReward_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            qyYearRewardService.batchDel(ids);
            logger.info(log( LogConstants.LOG_QY, "批量删除七一表彰_每年度奖项：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYearReward_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYearReward_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        qyYearRewardService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_QY, "七一表彰_每年度奖项调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void qyYearReward_export(QyYearRewardExample example, HttpServletResponse response) {

        List<QyYearReward> records = qyYearRewardMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","奖项id|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            QyYearReward record = records.get(i);
            String[] values = {
                record.getYearId()+"",
                            record.getRewardId()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("七一表彰_每年度奖项(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/qyYearReward_selects")
    @ResponseBody
    public Map qyYearReward_selects(Byte type,Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        QyYearRewardExample example = new QyYearRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/
        if(type!=null){
            List<Integer> rewardIds=new ArrayList<>();
            QyRewardExample rewardexample = new QyRewardExample();
            rewardexample.createCriteria().andTypeEqualTo(type);
            List<QyReward> qyRewards= qyRewardMapper.selectByExample(rewardexample);
            for (QyReward qyReward : qyRewards) {
                rewardIds.add(qyReward.getId());
            }
            if(rewardIds.size()>0){
                criteria.andRewardIdIn(rewardIds);
            }else{
                criteria.andRewardIdIsNull();
            }
        }
        long count = qyYearRewardMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<QyYearReward> records = qyYearRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(QyYearReward record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getQyYear().getYear()+ "--"+record.getQyReward().getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
