package controller.cet;

import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
import domain.train.Train;
import domain.train.TrainEvaTable;
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
public class CetTrainCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse")
    public String cetTrainCourse(int trainId, ModelMap modelMap) {

        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));
        return "cet/cetTrainCourse/cetTrainCourse_page";
    }

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse_data")
    public void cetTrainCourse_data(HttpServletResponse response,
                                 int trainId, /*String name,*/
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainCourseExample example = new CetTrainCourseExample();
        CetTrainCourseExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId)
                .andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order asc");
/*
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }*/

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainCourse_export(example, response);
            return;
        }

        long count = cetTrainCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainCourse.class, cetTrainCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_au(CetTrainCourse record,
                                 String _startTime,
                                 String _endTime,
                                 Boolean isGlobal,
                                 HttpServletRequest request) {

        Integer id = record.getId();
        record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD_HH_MM));
        record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD_HH_MM));
        record.setIsGlobal(BooleanUtils.isTrue(isGlobal));

        if(record.getStartTime()!=null && record.getEndTime()!=null
                && record.getStartTime().after(record.getEndTime())){
            return failed("开始时间不能晚于结束时间");
        }
        if (id == null) {

            cetTrainCourseService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加培训课程：%s", record.getId()));
        } else {

            cetTrainCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训课程：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_au")
    public String cetTrainCourse_au(Integer id, Integer trainId, ModelMap modelMap) {

        if (id != null) {
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourse", cetTrainCourse);

            trainId = cetTrainCourse.getTrainId();
        }
        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));

        return "cet/cetTrainCourse/cetTrainCourse_au";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_evaTable")
    public String cetTrainCourse_evaTable(int trainId, ModelMap modelMap) {

        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));
        //modelMap.put("trainEvaTableMap", trainEvaTableService.findAll());

        return "cet/cetTrainCourse/cetTrainCourse_evaTable";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_evaTable", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_evaTable(int trainId, @RequestParam(value = "ids[]") Integer[] ids, int evaTableId) {

        cetTrainCourseService.evaTable(trainId, ids, evaTableId);
        //logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训课程评估表：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:del")
    @RequestMapping(value = "/cetTrainCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //cetTrainCourseService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除培训课程：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:del")
    @RequestMapping(value = "/cetTrainCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetTrainCourseService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除培训课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:changeOrder")
    @RequestMapping(value = "/cetTrainCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainCourseService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "培训课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainCourse_export(CetTrainCourseExample example, HttpServletResponse response) {

        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次", "课程名称", "教师名称", "开始时间", "结束时间", "评估表", "评课情况（已完成/总数）"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourse record = records.get(i);
            Train train = trainMapper.selectByPrimaryKey(record.getTrainId());
            TrainEvaTable trainEvaTable = trainEvaTableMapper.selectByPrimaryKey(record.getEvaTableId());
            String[] values = {
                    train.getName(),
                    "",
                    "",
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    trainEvaTable.getName(),
                    record.getFinishCount() + "/" + train.getTotalCount()
            };
            valuesList.add(values);
        }
        String fileName = "培训课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetTrainCourse_selects")
    @ResponseBody
    public Map cetTrainCourse_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainCourseExample example = new CetTrainCourseExample();
        CetTrainCourseExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order asc");

        /*if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }*/

        long count = cetTrainCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != cetTrainCourses && cetTrainCourses.size() > 0) {

            for (CetTrainCourse cetTrainCourse : cetTrainCourses) {

                Select2Option option = new Select2Option();
                option.setText("");
                option.setId(cetTrainCourse.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
