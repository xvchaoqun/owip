package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetPlanCourse;
import domain.cet.CetPlanCourseExample;
import domain.cet.CetPlanCourseExample.Criteria;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/cet")
public class UserCetPlanCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCetProject:list")
    @RequestMapping("/cetPlanCourse")
    public String cetPlanCourse(ModelMap modelMap) {

        /*CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        Integer projectId = cetProjectPlan.getProjectId();
        modelMap.put("cetProject", cetProjectService.getView(projectId));*/

        return "cet/user/cetPlanCourse_page";
    }

    @RequiresPermissions("userCetProject:list")
    @RequestMapping("/cetPlanCourse_data")
    @ResponseBody
    public void cetPlanCourse_data(HttpServletRequest request,
                                   int planId,
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPlanCourseExample example = new CetPlanCourseExample();
        Criteria criteria = example.createCriteria().andPlanIdEqualTo(planId);
        example.setOrderByClause("sort_order asc");

        long count = cetPlanCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPlanCourse> records= cetPlanCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        request.setAttribute("userId", ShiroHelper.getCurrentUserId());

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetPlanCourse.class, cetPlanCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
