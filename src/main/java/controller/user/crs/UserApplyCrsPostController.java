package controller.user.crs;

import controller.CrsBaseController;
import domain.crs.CrsApplicant;
import mixin.MixinUtils;
import mixin.UserCrsPostMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.common.bean.ICrsPost;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/8/18.
 */
@Controller
@RequestMapping("/user")
public class UserApplyCrsPostController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping("/apply_crsPost")
    public String apply_crsPost(@RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "user/crs/apply_crsPost_page";
    }

    @RequiresPermissions("userApplyCrsPost:list")
    @RequestMapping("/apply_crsPost_data")
    public void apply_crsPost_data(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                   HttpServletResponse response, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int userId = ShiroHelper.getCurrentUserId();

        byte postStatus = SystemConstants.CRS_POST_STATUS_NORMAL;
        if(cls != 1){
            // 查看应聘历史记录，此时读取已完成招聘的岗位（排除正在招聘和已删除的岗位）
            postStatus = SystemConstants.CRS_POST_STATUS_FINISH;
        }

        long count = iCrsMapper.countUserApplyCrsPosts(userId, postStatus);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ICrsPost> records = iCrsMapper.findUserApplyCrsPosts(userId, postStatus,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(ICrsPost.class, UserCrsPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping(value = "/crsPost_start", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_start(HttpServletRequest request) {

        int cadreId = crsApplicantService.start();


        logger.info(addLog(SystemConstants.LOG_USER, "干部招聘-开始采集信息"));

        boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadreId);

        Map<String, Object> result = success(FormUtils.SUCCESS);
        result.put("cadreId", cadreId);
        result.put("hasDirectModifyCadreAuth", hasDirectModifyCadreAuth);

        return result;
    }

    // 管理员也拥有该权限
    //@RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping(value = "/crsPost_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_quit(int postId, Integer applicantId, HttpServletRequest request) {

        Integer userId = null;
        if(applicantId == null){
            SecurityUtils.getSubject().checkPermission("userApplyCrsPost:*");
            userId = ShiroHelper.getCurrentUserId();
        }else{
            SecurityUtils.getSubject().checkPermission("crsPost:list"); // 招聘管理 权限
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
            userId = crsApplicant.getUserId();
        }

        crsApplicantService.quit(postId, userId);

        logger.info(addLog(SystemConstants.LOG_USER, "干部招聘-退出竞聘"));
        return success(FormUtils.SUCCESS);
    }

    // 管理员也拥有该权限
    //@RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping(value = "/crsPost_reApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_reApply(int postId, Integer applicantId, HttpServletRequest request) {

        Integer userId = null;
        if(applicantId == null){
            SecurityUtils.getSubject().checkPermission("userApplyCrsPost:*");
            userId = ShiroHelper.getCurrentUserId();
        }else{
            SecurityUtils.getSubject().checkPermission("crsPost:list"); // 招聘管理 权限
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
            userId = crsApplicant.getUserId();
        }

        crsApplicantService.reApply(postId, userId);

        logger.info(addLog(SystemConstants.LOG_USER, "干部招聘-重新报名"));
        return success(FormUtils.SUCCESS);
    }
}
