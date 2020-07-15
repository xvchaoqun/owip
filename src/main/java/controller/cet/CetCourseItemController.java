package controller.cet;

import domain.cet.CetCourseItem;
import domain.cet.CetCourseItemExample;
import domain.cet.CetCourseItemExample.Criteria;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetCourseItemController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetCourseItem")
    public String cetCourseItem(Integer courseId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetCourseItemExample example = new CetCourseItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (courseId!=null) {
            criteria.andCourseIdEqualTo(courseId);
        }

        long count = cetCourseItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourseItem> records= cetCourseItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cetCourseItems", records);
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        modelMap.put("commonList", commonList);

        return "cet/cetCourseItem/cetCourseItem_page";
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping(value = "/cetCourseItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseItem_au(CetCourseItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetCourseItemService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加课程专题班：%s", record.getName()));
        } else {

            cetCourseItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新课程专题班：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping("/cetCourseItem_au")
    public String cetCourseItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetCourseItem cetCourseItem = cetCourseItemMapper.selectByPrimaryKey(id);
            modelMap.put("cetCourseItem", cetCourseItem);
        }
        return "cet/cetCourseItem/cetCourseItem_au";
    }

    @RequiresPermissions("cetCourse:del")
    @RequestMapping(value = "/cetCourseItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetCourseItemService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除课程专题班：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:changeOrder")
    @RequestMapping(value = "/cetCourseItem_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseItem_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetCourseItemService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "课程专题班调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetCourseItem_export(CetCourseItemExample example, HttpServletResponse response) {

        List<CetCourseItem> records = cetCourseItemMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属课程|100","专题班名称|100","学时|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetCourseItem record = records.get(i);
            String[] values = {
                record.getCourseId()+"",
                            record.getName(),
                            record.getPeriod()+"",
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "课程专题班_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
