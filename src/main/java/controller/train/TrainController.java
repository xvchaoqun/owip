package controller.train;

import controller.BaseController;
import domain.train.Train;
import domain.train.TrainExample;
import domain.train.TrainExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.tool.jackson.Select2Option;
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
public class TrainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("train:list")
    @RequestMapping("/train")
    public String train(HttpServletResponse response,
        String name,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "train/train/train_page";
    }

    @RequiresPermissions("train:list")
    @RequestMapping("/train_data")
    public void train_data(HttpServletResponse response,
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

        TrainExample example = new TrainExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("start_date desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            train_export(example, response);
            return;
        }

        int count = trainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Train> records= trainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(train.class, trainMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("train:edit")
    @RequestMapping(value = "/train_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_train_au(Train record,
                           String _startDate, String _endDate,
                           HttpServletRequest request) {

        Integer id = record.getId();
        record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        record.setCreateTime(new Date());
        if (id == null) {
            trainService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加培训班次：%s", record.getId()));
        } else {

            trainService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新培训班次：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("train:edit")
    @RequestMapping("/train_au")
    public String train_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Train train = trainMapper.selectByPrimaryKey(id);
            modelMap.put("train", train);
        }
        return "train/train/train_au";
    }

    @RequiresPermissions("train:edit")
    @RequestMapping(value = "/train_note", method = RequestMethod.POST)
    @ResponseBody
    public Map do_train_note(int id,
                                   String note) {

        Train record = new Train();
        record.setId(id);
        record.setNote(note);

        trainService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训评课说明：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("train:edit")
    @RequestMapping("/train_note")
    public String train_note(Integer id, ModelMap modelMap) {

        if (id != null) {
            Train train = trainMapper.selectByPrimaryKey(id);
            modelMap.put("train", train);
        }
        return "train/train/train_note";
    }

    @RequiresPermissions("train:edit")
    @RequestMapping("/train_evaCloseTime")
    public String train_evaCloseTime(Integer id, ModelMap modelMap) {

        if (id != null) {
            modelMap.put("train", trainMapper.selectByPrimaryKey(id));
        }
        return "train/train/train_evaCloseTime";
    }

    @RequiresPermissions("train:edit")
    @RequestMapping(value = "/train_evaCloseTime", method = RequestMethod.POST)
    @ResponseBody
    public Map do_train_evaCloseTime(int id, Boolean isClosed, String _closeTime) {

        Date closeTime = DateUtils.parseDate(_closeTime, DateUtils.YYYY_MM_DD_HH_MM);

        /*if(BooleanUtils.isFalse(isClosed)){
            if(openTime!=null && closeTime!=null && openTime.after(closeTime)){
                return failed("评课开启时间不能晚于关闭时间");
            }
        }*/

        trainService.updateEvaCloseTime(id, BooleanUtils.isTrue(isClosed), closeTime);
        //logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训评课关闭时间：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("train:edit")
    @RequestMapping("/train_inspectors")
    public String train_inspectors(Integer id, ModelMap modelMap) {

        if (id != null) {
            modelMap.put("train", trainMapper.selectByPrimaryKey(id));
        }
        return "train/train/train_inspectors";
    }

    @RequiresPermissions("train:del")
    @RequestMapping(value = "/train_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_train_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //trainService.del(id);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除培训班次：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("train:del")
    @RequestMapping(value = "/train_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            trainService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除培训班次：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void train_export(TrainExample example, HttpServletResponse response) {

        List<Train> records = trainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称","简介","开始日期","结束日期","备注","创建时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Train record = records.get(i);
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

    @RequestMapping("/train_selects")
    @ResponseBody
    public Map train_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        TrainExample example = new TrainExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = trainMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Train> trains = trainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != trains && trains.size()>0){

            for(Train train:trains){

                Select2Option option = new Select2Option();
                option.setText(train.getName());
                option.setId(train.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
