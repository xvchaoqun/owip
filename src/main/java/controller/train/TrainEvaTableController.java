package controller.train;

import controller.TrainBaseController;
import domain.train.TrainEvaTable;
import domain.train.TrainEvaTableExample;
import domain.train.TrainEvaTableExample.Criteria;
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
import sys.tool.jackson.Select2Option;
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
public class TrainEvaTableController extends TrainBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("trainEvaTable:list")
    @RequestMapping("/trainEvaTable")
    public String trainEvaTable(HttpServletResponse response,
        String name,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "train/trainEvaTable/trainEvaTable_page";
    }

    @RequiresPermissions("trainEvaTable:list")
    @RequestMapping("/trainEvaTable_data")
    public void trainEvaTable_data(HttpServletResponse response,
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

        TrainEvaTableExample example = new TrainEvaTableExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            trainEvaTable_export(example, response);
            return;
        }

        int count = trainEvaTableMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TrainEvaTable> records= trainEvaTableMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(TrainEvaTable.class, TrainEvaTableMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("trainEvaTable:edit")
    @RequestMapping(value = "/trainEvaTable_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaTable_au(TrainEvaTable record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(SystemConstants.AVAILABLE);
            trainEvaTableService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加评估表：%s", record.getId()));
        } else {

            trainEvaTableService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新评估表：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaTable:edit")
    @RequestMapping("/trainEvaTable_au")
    public String trainEvaTable_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            TrainEvaTable trainEvaTable = trainEvaTableMapper.selectByPrimaryKey(id);
            modelMap.put("trainEvaTable", trainEvaTable);
        }
        return "train/trainEvaTable/trainEvaTable_au";
    }

    @RequiresPermissions("trainEvaTable:edit")
    @RequestMapping("/trainEvaTable_preview")
    public String trainEvaTable_preview(Integer id, ModelMap modelMap) {

        if (id != null) {
            TrainEvaTable trainEvaTable = trainEvaTableMapper.selectByPrimaryKey(id);
            modelMap.put("trainEvaTable", trainEvaTable);
        }
        return "train/trainEvaTable/trainEvaTable_preview";
    }

    @RequiresPermissions("trainEvaTable:del")
    @RequestMapping(value = "/trainEvaTable_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaTable_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //trainEvaTableService.del(id);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除评估表：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaTable:del")
    @RequestMapping(value = "/trainEvaTable_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            trainEvaTableService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除评估表：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainEvaTable:changeOrder")
    @RequestMapping(value = "/trainEvaTable_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainEvaTable_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        trainEvaTableService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_ADMIN, "评估表调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void trainEvaTable_export(TrainEvaTableExample example, HttpServletResponse response) {

        List<TrainEvaTable> records = trainEvaTableMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"评估表名称","备注","排序","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            TrainEvaTable record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getRemark(),
                            record.getSortOrder()+"",
                            record.getStatus()+""
            };
            valuesList.add(values);
        }
        String fileName = "评估表_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/trainEvaTable_selects")
    @ResponseBody
    public Map trainEvaTable_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        TrainEvaTableExample example = new TrainEvaTableExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = trainEvaTableMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<TrainEvaTable> trainEvaTables = trainEvaTableMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != trainEvaTables && trainEvaTables.size()>0){

            for(TrainEvaTable trainEvaTable:trainEvaTables){

                Select2Option option = new Select2Option();
                option.setText(trainEvaTable.getName());
                option.setId(trainEvaTable.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
