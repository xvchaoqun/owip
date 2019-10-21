package controller.qy;

import domain.qy.*;
import domain.qy.QyRewardRecordExample.Criteria;
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

public class QyRewardRecordController extends QyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyRewardRecord")
    public String qyRewardRecord(Byte type ,Integer rewardId,ModelMap modelMap) {
        QyReward qyReward=qyRewardMapper.selectByPrimaryKey(rewardId);
        modelMap.put("qyReward",qyReward);
        return "qy/qyRewardRecord/qyRewardRecord_page";
    }

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyRewardRecord_data")
    @ResponseBody
    public void qyRewardRecord_data(HttpServletResponse response,
                                    Integer yearRewardId,
                                    Byte type,
                                    Integer year,
                                    Integer rewardId,
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

        QyRewardRecordViewExample example = new QyRewardRecordViewExample();
        QyRewardRecordViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (yearRewardId!=null) {
            criteria.andYearRewardIdEqualTo(yearRewardId);
        }
        if (type!=null) {
            criteria.andRewardTypeEqualTo(type);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (rewardId!=null) {
            criteria.andRewardIdEqualTo(rewardId);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            qyRewardRecord_export(example, response);
            return;
        }

        long count = qyRewardRecordViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<QyRewardRecordView> records= qyRewardRecordViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(qyRewardRecord.class, qyRewardRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardRecord_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyRewardRecord_au(QyRewardRecord record, HttpServletRequest request) {

        Integer id = record.getId();

        if (qyRewardRecordService.idDuplicate(id, record.getYearRewardId())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            qyRewardRecordService.insertSelective(record);
            logger.info(log( LogConstants.LOG_QY, "添加七一表彰_获奖记录：{0}", record.getId()));
        } else {

            qyRewardRecordService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_QY, "更新七一表彰_获奖记录：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping("/qyRewardRecord_au")
    public String qyRewardRecord_au(Integer id,Byte type, ModelMap modelMap) {

        if (id != null) {
            QyRewardRecordViewExample example = new QyRewardRecordViewExample();
            example.createCriteria().andIdEqualTo(id);
            List<QyRewardRecordView> QyRewardRecordViews = qyRewardRecordViewMapper.selectByExample(example);
           if(QyRewardRecordViews.size()>0){
            QyRewardRecordView qyRewardRecordView=QyRewardRecordViews.get(0);
               modelMap.put("qyRewardRecordView", qyRewardRecordView);
           }
        }
        return "qy/qyRewardRecord/qyRewardRecord_au";
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping("/qyRewardRecord_obj")
    public String qyRewardRecord_obj(Integer recordId,Byte type,Integer pageSize, Integer pageNo, HttpServletResponse response, ModelMap modelMap) {

        if (recordId != null) {

            if (null == pageSize) {
                pageSize = 5;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            QyRewardObjViewExample example = new QyRewardObjViewExample();
            QyRewardObjViewExample.Criteria criteria=example.createCriteria();
            example.setOrderByClause("sort_order desc");
            if(recordId!=null){

                criteria.andRecordIdEqualTo(recordId);
            }

            int count = (int) qyRewardObjViewMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<QyRewardObjView> qyRewardObjView = qyRewardObjViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("qyRewardObjView", qyRewardObjView);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (recordId!=null) {
                searchStr += "&recordId=" + recordId;
            }
            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }
        return "qy/qyRewardRecord/qyRewardRecord_obj";
    }
    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardRecord_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyRewardRecord_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            qyRewardRecordService.del(id);
            logger.info(log( LogConstants.LOG_QY, "删除七一表彰_获奖记录：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardRecord_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map qyRewardRecord_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            qyRewardRecordService.batchDel(ids);
            logger.info(log( LogConstants.LOG_QY, "批量删除七一表彰_获奖记录：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void qyRewardRecord_export(QyRewardRecordViewExample example, HttpServletResponse response) {

        List<QyRewardRecordView> records = qyRewardRecordViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度奖项id|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            QyRewardRecordView record = records.get(i);
            String[] values = {
                record.getYearRewardId()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("七一表彰_获奖记录(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/qyRewardRecord_selects")
    @ResponseBody
    public Map qyRewardRecord_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        QyRewardRecordExample example = new QyRewardRecordExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = qyRewardRecordMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<QyRewardRecord> records = qyRewardRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(QyRewardRecord record:records){

                Map<String, Object> option = new HashMap<>();
               // option.put("text", record.getName());
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
