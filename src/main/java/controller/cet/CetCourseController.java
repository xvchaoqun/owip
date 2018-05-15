package controller.cet;

import domain.cet.CetCourse;
import domain.cet.CetCourseExample;
import domain.cet.CetCourseExample.Criteria;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseView;
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
import org.springframework.web.util.HtmlUtils;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

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
public class CetCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetCourse")
    public String cetCourse(@RequestParam(defaultValue = "1") Integer cls, byte type, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/cet/cetColumn?columnType=1&isOnline="+ (type== CetConstants.CET_COURSE_TYPE_ONLINE?1:0);
        }else if (cls == 3) {
            return "forward:/cet/cetColumn?columnType=2&isOnline="+ (type== CetConstants.CET_COURSE_TYPE_ONLINE?1:0);
        }

        return "cet/cetCourse/cetCourse_page";
    }

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetCourse_data")
    public void cetCourse_data(HttpServletResponse response,
                                    byte type,
                                    String name,
                                    @RequestParam(defaultValue = "1") Integer cls,
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

        CetCourseExample example = new CetCourseExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("sort_order desc");

        criteria.andIsDeletedEqualTo(cls==4);

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetCourse_export(example, response);
            return;
        }

        long count = cetCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourse> records= cetCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping(value = "/cetCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourse_au(CetCourse record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetCourseService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加课程中心：%s", record.getId()));
        } else {

            cetCourseService.updateByPrimaryKeySelectiveWithNum(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新课程中心：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping("/cetCourse_au")
    public String cetCourse_au(Integer id, Byte type, ModelMap modelMap) {

        if (id != null) {
            CetCourse cetCourse = cetCourseService.get(id);
            modelMap.put("cetCourse", cetCourse);
            if(cetCourse!=null){
                type = cetCourse.getType();
            }
        }
        modelMap.put("type", type);

        // 线下、线上课程
        if(NumberUtils.contains(type, CetConstants.CET_COURSE_TYPE_ONLINE,
                CetConstants.CET_COURSE_TYPE_OFFLINE)) {
            return "cet/cetCourse/cetCourse_au";
        }else if(type==CetConstants.CET_COURSE_TYPE_SELF){

            return "cet/cetCourse/cetCourse_au_self";
        }else if(type==CetConstants.CET_COURSE_TYPE_PRACTICE){

            return "cet/cetCourse/cetCourse_au_practice";
        }else if(type==CetConstants.CET_COURSE_TYPE_SPECIAL){

            return "cet/cetCourse/cetCourse_au_special";
        }

        return null;
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping("/cetCourse_summary")
    public String cetCourse_summary(Integer id, Boolean view, ModelMap modelMap) {

        if (id != null) {
            CetCourse cetCourse = cetCourseService.get(id);
            modelMap.put("cetCourse", cetCourse);
        }

        if(BooleanUtils.isTrue(view))
            return "cet/cetCourse/cetCourse_summary_view";

        return "cet/cetCourse/cetCourse_summary";
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping("/cetCourse_addToTrain")
    public String cetCourse_addToTrain() {

        return "cet/cetCourse/cetCourse_addToTrain";
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping("/cetCourse_detail")
    public String cetCourse_detail(int courseId, ModelMap modelMap) {

        CetCourse cetCourse = cetCourseService.get(courseId);
        modelMap.put("cetCourse", cetCourse);

        return "cet/cetCourse/cetCourse_detail";
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping(value = "/cetCourse_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourse_summary(Integer id, String summary) {

        CetCourse record = new CetCourse();
        record.setId(id);
        record.setSummary(summary);
        record.setHasSummary(StringUtils.isNotBlank(summary));

        cetCourseService.updateByPrimaryKeySelectiveWithoutNum(record);
        logger.info(addLog(LogConstants.LOG_CET, "更新课程要点：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:del")
    @RequestMapping(value = "/cetCourse_fakeDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetCourse_fakeDel(HttpServletRequest request,
                                 boolean del,
                                 @RequestParam(value = "ids[]") Integer[] ids,
                                 ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetCourseService.fakeDel(ids, del);
            logger.info(addLog(LogConstants.LOG_CET, "批量假删除课程中心：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cetCourse:del")
    @RequestMapping(value = "/cetCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetCourse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetCourseService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除课程中心：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:changeOrder")
    @RequestMapping(value = "/cetCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetCourseService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "课程中心调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetCourse_export(CetCourseExample example, HttpServletResponse response) {

        List<CetCourse> records = cetCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"设立时间|100","课程名称|100","主讲人|100","学时|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetCourse record = records.get(i);
            String[] values = {
                DateUtils.formatDate(record.getFoundDate(), DateUtils.YYYY_MM_DD),
                            record.getName(),
                            record.getExpertId()+"",
                            record.getPeriod()+"",

                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "课程中心_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetCourse_selects")
    @ResponseBody
    public Map cetCourse_selects(Integer pageSize, Integer pageNo, Byte type, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetCourseExample example = new CetCourseExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cetCourseMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetCourse> cetCourses = cetCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cetCourses && cetCourses.size()>0){

            for(CetCourse cetCourse:cetCourses){

                Select2Option option = new Select2Option();
                option.setText(cetCourse.getName());
                option.setId(cetCourse.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequestMapping(value = "/cetCourse_video")
    public String cetCourse_video(int id, Integer trainCourseId, ModelMap modelMap) throws IOException{

        // 已选课情况下，标记为已学习
        if(trainCourseId!=null){
            Integer userId = ShiroHelper.getCurrentUserId();
            if(userId!=null) {
                CetTraineeCourseView cetTraineeCourseView = cetTraineeCourseService.getCetTraineeCourseView(userId, trainCourseId);

                if(cetTraineeCourseView!=null && BooleanUtils.isNotTrue(cetTraineeCourseView.getIsFinished())){

                    CetTraineeCourse record = new CetTraineeCourse();
                    record.setId(cetTraineeCourseView.getId());
                    record.setIsFinished(true);
                    record.setSignTime(new Date());

                    cetTraineeCourseMapper.updateByPrimaryKeySelective(record);
                }
            }
        }

        CetCourse cetCourse = cetCourseMapper.selectByPrimaryKey(id);
        if(StringUtils.isNotBlank(cetCourse.getUrl())) {
            // 去掉html的空格 &nbsp;
            modelMap.put("url", HtmlUtils.htmlUnescape(cetCourse.getUrl()).replaceAll("\\u00A0", ""));
        }

        return "common/video";
    }
}
