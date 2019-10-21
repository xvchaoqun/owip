package controller.qy;

import domain.qy.QyReward;
import domain.qy.QyRewardExample;
import domain.qy.QyRewardExample.Criteria;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class QyRewardController extends QyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyReward")
    public String qyReward(@RequestParam(defaultValue = "1")Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
          return "qy/qyReward/qyReward_page";
    }

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyReward_data")
    @ResponseBody
    public void qyReward_data(HttpServletResponse response,
                                    String name,
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

        QyRewardExample example = new QyRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            qyReward_export(example, response);
            return;
        }

        long count = qyRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<QyReward> records= qyRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(qyReward.class, qyRewardMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyReward_au(QyReward record, HttpServletRequest request) {

        Integer id = record.getId();


        if (id == null) {
            
            qyRewardService.insertSelective(record);
            logger.info(log( LogConstants.LOG_QY, "添加七一表彰_奖项：{0}", record.getId()));
        } else {

            qyRewardService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_QY, "更新七一表彰_奖项：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping("/qyReward_au")
    public String qyReward_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            QyReward qyReward = qyRewardMapper.selectByPrimaryKey(id);
            modelMap.put("qyReward", qyReward);
        }
        return "qy/qyReward/qyReward_au";
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyReward_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyReward_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            qyRewardService.del(id);
            logger.info(log( LogConstants.LOG_QY, "删除七一表彰_奖项：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map qyReward_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            qyRewardService.batchDel(ids);
            logger.info(log( LogConstants.LOG_QY, "批量删除七一表彰_奖项：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void qyReward_export(QyRewardExample example, HttpServletResponse response) {

        List<QyReward> records = qyRewardMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"奖项名称|100","奖励对象 1 分党委 2 党支部  3 党员  4 党日活动 |100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            QyReward record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getType()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("七一表彰_奖项(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/qyReward_selects")
    @ResponseBody
    public Map qyReward_selects(Byte type,Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        QyRewardExample example = new QyRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");
        if (null != type) {
            criteria.andTypeEqualTo(type);
        }
        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = qyRewardMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<QyReward> records = qyRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(QyReward record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
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
