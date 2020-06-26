package controller.cet.user;

import controller.cet.CetBaseController;
import controller.global.OpException;
import domain.cet.*;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/cet")
public class UserCetProjectPlanController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCetProject:*list")
    @RequestMapping("/cetProjectPlan_detail")
    public String cetProjectPlan_detail(int planId, ModelMap modelMap) {

        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
        modelMap.put("cetProject", cetProject);
        int userId = ShiroHelper.getCurrentUserId();

        byte planType = cetProjectPlan.getType();
        switch (planType){
            case CetConstants.CET_PROJECT_PLAN_TYPE_OFFLINE: // 线下培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_ONLINE: // 线上培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_PRACTICE: // 实践教学

                List<Integer> trainIds = iCetMapper.selectTrainIds(userId, planId);
                long trainCount = trainIds.size();
                if(trainCount==0){
                    throw new OpException("暂无培训班。");
                }else if(trainCount==1){
                    // 只有一个培训班时，直接跳转
                    return "forward:/user/cet/cetTrain_detail?cls=3&trainId=" + trainIds.get(0);
                }
                return "forward:/user/cet/cetTrain";

            case CetConstants.CET_PROJECT_PLAN_TYPE_SELF: // 自主学习
            case CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL: // 上级网上专题班
                return "forward:/user/cet/cetPlanCourse";

            case CetConstants.CET_PROJECT_PLAN_TYPE_GROUP: // 分组讨论
                return "forward:/user/cet/cetDiscussGroup";

            case CetConstants.CET_PROJECT_PLAN_TYPE_WRITE:
                Integer projectId = cetProjectPlan.getProjectId();
                return "forward:/user/cet/cetProjectObj_write?projectId="+projectId;
        }

        return null;
    }

    // 学员 第二级：培训方案
    @RequiresPermissions("userCetProject:*list")
    @RequestMapping("/cetProjectPlan")
    public String cetProjectPlan(HttpServletRequest request,
                                 int projectId, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        request.setAttribute("userId", userId);
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);
        CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
        modelMap.put("cetProjectObj", cetProjectObj);

        return "cet/user/cetProjectPlan_page";
    }

    @RequiresPermissions("userCetProject:*list")
    @RequestMapping("/cetProjectPlan_data")
    @ResponseBody
    public void cetProjectPlan_data(HttpServletRequest request,
                                    int projectId,
                                    Integer pageSize, Integer pageNo)  throws IOException{

        int userId = ShiroHelper.getCurrentUserId();
        // 判断访问权限
        CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
        if(cetProjectObj==null){
            throw new UnauthorizedException();
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetProjectPlanExample example = new CetProjectPlanExample();
        CetProjectPlanExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("sort_order asc");

        long count = cetProjectPlanMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectPlan> records= cetProjectPlanMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        request.setAttribute("objId", cetProjectObj.getId());

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProjectPlan.class, cetProjectPlanMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
