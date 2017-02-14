package controller.train;

import controller.BaseController;
import domain.train.TrainEvaNorm;
import domain.train.TrainEvaNormExample;
import domain.train.TrainEvaNormExample.Criteria;
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
public class TrainEvaNormController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("trainEvaNorm:list")
    @RequestMapping("/trainEvaNorm")
    public String trainEvaNorm() {

        return "index";
    }
    @RequiresPermissions("trainEvaNorm:list")
    @RequestMapping("/trainEvaNorm_page")
    public String trainEvaNorm_page(int evaTableId, ModelMap modelMap) {

        modelMap.put("trainEvaTable", trainEvaTableMapper.selectByPrimaryKey(evaTableId));

        return "train/trainEvaNorm/trainEvaNorm_page";
    }

    @RequiresPermissions("trainEvaNorm:list")
    @RequestMapping("/trainEvaNorm_data")
    public void trainEvaNorm_data(HttpServletResponse response,
                                   int evaTableId,
                                  Integer fid, // fid=null时，读取评估内容；fid<=0时，读取全部 fid>0 读取评估指标
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

        TrainEvaNormExample example = new TrainEvaNormExample();
        Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
        example.setOrderByClause("sort_order desc");
        if (fid != null) {
            if (fid > 0)
                criteria.andFidEqualTo(fid);
        } else {
            criteria.andFidIsNull();
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            trainEvaNorm_export(example, response);
            return;
        }

        int count = trainEvaNormMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TrainEvaNorm> records= trainEvaNormMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(trainEvaNorm.class, trainEvaNormMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("trainEvaNorm:edit")
    @RequestMapping(value = "/trainEvaNorm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaNorm_au(TrainEvaNorm record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            trainEvaNormService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加评估指标：%s", record.getId()));
        } else {

            trainEvaNormService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新评估指标：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaNorm:edit")
    @RequestMapping("/trainEvaNorm_au")
    public String trainEvaNorm_au(Integer id, Integer fid, Integer evaTableId, ModelMap modelMap) {

        if (id != null) {
            TrainEvaNorm trainEvaNorm = trainEvaNormMapper.selectByPrimaryKey(id);
            modelMap.put("trainEvaNorm", trainEvaNorm);

            evaTableId = trainEvaNorm.getEvaTableId();
            fid = trainEvaNorm.getFid();
        }
        modelMap.put("fid", fid);
        if(fid!=null) {
            modelMap.put("topTrainEvaNorm", trainEvaNormMapper.selectByPrimaryKey(fid));
        }
        modelMap.put("trainEvaTable", trainEvaTableMapper.selectByPrimaryKey(evaTableId));
        return "train/trainEvaNorm/trainEvaNorm_au";
    }

    @RequiresPermissions("trainEvaNorm:del")
    @RequestMapping(value = "/trainEvaNorm_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaNorm_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //trainEvaNormService.del(id);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除评估指标：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaNorm:del")
    @RequestMapping(value = "/trainEvaNorm_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            trainEvaNormService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除评估指标：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaNorm:changeOrder")
    @RequestMapping(value = "/trainEvaNorm_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaNorm_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        trainEvaNormService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_ADMIN, "评估指标调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void trainEvaNorm_export(TrainEvaNormExample example, HttpServletResponse response) {

        List<TrainEvaNorm> records = trainEvaNormMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属评估表","父指标","指标名称","备注","排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            TrainEvaNorm record = records.get(i);
            String[] values = {
                record.getEvaTableId()+"",
                            record.getFid()+"",
                            record.getName(),
                            record.getRemark(),
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "评估指标_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
