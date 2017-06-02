package controller.train;

import controller.BaseController;
import domain.train.TrainEvaRank;
import domain.train.TrainEvaRankExample;
import domain.train.TrainEvaRankExample.Criteria;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class TrainEvaRankController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("trainEvaRank:list")
    @RequestMapping("/trainEvaRank")
    public String trainEvaRank(int evaTableId, ModelMap modelMap) {

        modelMap.put("trainEvaTable", trainEvaTableMapper.selectByPrimaryKey(evaTableId));
        return "train/trainEvaRank/trainEvaRank_page";
    }

    @RequiresPermissions("trainEvaRank:list")
    @RequestMapping("/trainEvaRank_data")
    public void trainEvaRank_data(HttpServletResponse response,
                                  int evaTableId, String name,
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

        TrainEvaRankExample example = new TrainEvaRankExample();
        Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            trainEvaRank_export(example, response);
            return;
        }

        int count = trainEvaRankMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TrainEvaRank> records= trainEvaRankMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(trainEvaRank.class, trainEvaRankMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("trainEvaRank:edit")
    @RequestMapping(value = "/trainEvaRank_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaRank_au(TrainEvaRank record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            trainEvaRankService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加评估等级：%s", record.getId()));
        } else {

            trainEvaRankService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新评估等级：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaRank:edit")
    @RequestMapping("/trainEvaRank_au")
    public String trainEvaRank_au(Integer id, Integer evaTableId, ModelMap modelMap) {

        if (id != null) {
            TrainEvaRank trainEvaRank = trainEvaRankMapper.selectByPrimaryKey(id);
            modelMap.put("trainEvaRank", trainEvaRank);

            evaTableId = trainEvaRank.getEvaTableId();
        }

        modelMap.put("trainEvaTable", trainEvaTableMapper.selectByPrimaryKey(evaTableId));

        return "train/trainEvaRank/trainEvaRank_au";
    }

    @RequiresPermissions("trainEvaRank:del")
    @RequestMapping(value = "/trainEvaRank_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaRank_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            trainEvaRankService.del(id);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除评估等级：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaRank:del")
    @RequestMapping(value = "/trainEvaRank_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            trainEvaRankService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除评估等级：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaRank:changeOrder")
    @RequestMapping(value = "/trainEvaRank_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaRank_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        trainEvaRankService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_ADMIN, "评估等级调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void trainEvaRank_export(TrainEvaRankExample example, HttpServletResponse response) {

        List<TrainEvaRank> records = trainEvaRankMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属评估表","名称","得分","得分显示内容","备注","排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            TrainEvaRank record = records.get(i);
            String[] values = {
                record.getEvaTableId()+"",
                            record.getName(),
                            record.getScore()+"",
                            record.getScoreShow(),
                            record.getRemark(),
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "评估等级_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
