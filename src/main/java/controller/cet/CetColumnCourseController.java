package controller.cet;

import domain.cet.CetColumn;
import domain.cet.CetColumnCourse;
import domain.cet.CetColumnCourseExample;
import domain.cet.CetColumnCourseView;
import domain.cet.CetColumnCourseViewExample;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetColumnCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetColumn:course")
    @RequestMapping("/cetColumnCourse")
    public String cetColumnCourse(Integer columnId, ModelMap modelMap) {

        CetColumn cetColumn = cetColumnMapper.selectByPrimaryKey(columnId);
        modelMap.put("cetColumn", cetColumn);

        return "cet/cetColumnCourse/cetColumnCourse_page";
    }

    @RequiresPermissions("cetColumn:course")
    @RequestMapping("/cetColumnCourse_data")
    public void cetColumnCourse_data(HttpServletResponse response,
                                    Integer columnId,
                                    Integer courseId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetColumnCourseViewExample example = new CetColumnCourseViewExample();
        CetColumnCourseViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (columnId!=null) {
            criteria.andColumnIdEqualTo(columnId);
        }
        if (courseId!=null) {
            criteria.andCourseIdEqualTo(courseId);
        }

        long count = cetColumnCourseViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetColumnCourseView> records= cetColumnCourseViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetColumnCourse.class, cetColumnCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetColumn:course")
    @RequestMapping(value = "/cetColumnCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetColumnCourse_au(CetColumnCourse record, HttpServletRequest request) {


        if(cetColumnCourseService.idDuplicate(record.getColumnId(), record.getCourseId())){

            return failed("添加重复。");
        }

        cetColumnCourseService.insertSelective(record);
        logger.info(addLog( SystemConstants.LOG_CET, "添加课程栏目包含课程：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetColumn:course")
    @RequestMapping("/cetColumnCourse_au")
    public String cetColumnCourse_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetColumnCourse cetColumnCourse = cetColumnCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetColumnCourse", cetColumnCourse);
        }
        return "cet/cetColumnCourse/cetColumnCourse_au";
    }

    @RequiresPermissions("cetColumn:course")
    @RequestMapping(value = "/cetColumnCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetColumnCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetColumnCourseService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除课程栏目包含课程：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetColumn:course")
    @RequestMapping(value = "/cetColumnCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetColumnCourse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetColumnCourseService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除课程栏目包含课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetColumn:course")
    @RequestMapping(value = "/cetColumnCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetColumnCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetColumnCourseService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_CET, "课程栏目包含课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetColumnCourse_export(CetColumnCourseExample example, HttpServletResponse response) {

        List<CetColumnCourse> records = cetColumnCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"栏目|100","课程|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetColumnCourse record = records.get(i);
            String[] values = {
                record.getColumnId()+"",
                            record.getCourseId()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "课程栏目包含课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
