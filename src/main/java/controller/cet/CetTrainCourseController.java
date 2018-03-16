package controller.cet;

import domain.cet.CetCourse;
import domain.cet.CetCourseType;
import domain.cet.CetExpert;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
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
public class CetTrainCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse")
    public String cetTrainCourse(int trainId, ModelMap modelMap) {

        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));

        Map<Integer, CetCourseType> courseTypeMap = cetCourseTypeService.findAll();
        modelMap.put("courseTypeMap", courseTypeMap);

        return "cet/cetTrainCourse/cetTrainCourse_page";
    }

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse_data")
    public void cetTrainCourse_data(HttpServletResponse response,
                                 int trainId,
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
        CetTrainCourseExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId);
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
    @RequestMapping(value = "/cetTrainCourse_selectCourses", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_selectCourses(int trainId,
                                               @RequestParam(value = "courseIds[]") Integer[] courseIds,
                                               HttpServletRequest request) {

        cetTrainCourseService.selectCourses(trainId, courseIds);
        logger.info(addLog(SystemConstants.LOG_CET, "添加培训班课程：%s, %s",trainId, StringUtils.join(courseIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_selectCourses")
    public String cetTrainCourse_selectCourses(int trainId, Integer expertId, ModelMap modelMap) {

        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));
        if(expertId!=null)
            modelMap.put("cetExpert", cetExpertMapper.selectByPrimaryKey(expertId));

        Map<Integer, CetCourseType> courseTypeMap = cetCourseTypeService.findAll();
        modelMap.put("courseTypeMap", courseTypeMap);

        return "cet/cetTrainCourse/cetTrainCourse_selectCourses";
    }

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetTrainCourse_selectCourses_data")
    public void cetTrainCourse_selectCourses_data(HttpServletResponse response,
                                                  int trainId, Integer expertId, String name,
                                                  Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iCetMapper.cetTrainCourse_countCourses(trainId, expertId, name);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourse> records= iCetMapper.cetTrainCourse_selectCourses(trainId, expertId, name,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetCourse.class, cetCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_info", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_info(CetTrainCourse record, HttpServletRequest request) {

        cetTrainCourseService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_CET, "更新培训班课程信息：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_info")
    public String cetTrainCourse_info(int trainCourseId, ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));
        return "cet/cetTrainCourse/cetTrainCourse_info";
    }

    @RequiresPermissions("cetTrainCourse:del")
    @RequestMapping(value = "/cetTrainCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainCourse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cetTrainCourseService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_CET, "批量删除培训课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:changeOrder")
    @RequestMapping(value = "/cetTrainCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainCourseService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_CET, "培训课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainCourse_export(CetTrainCourseExample example, HttpServletResponse response) {

        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次|200", "课程名称|300", "教师名称", "开始时间|200", "结束时间|200", "选课情况"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourse record = records.get(i);
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(record.getTrainId());
            CetCourse cetCourse = record.getCetCourse();
            CetExpert cetExpert = cetCourse.getCetExpert();
            String[] values = {
                    cetTrain.getName(),
                    cetCourse.getName(),
                    cetExpert.getRealname(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    record.getTraineeCount()+""
            };
            valuesList.add(values);
        }
        String fileName = "培训班课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
