package controller.user.crs;

import controller.CrsBaseController;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import domain.member.MemberTeacher;
import mixin.MixinUtils;
import mixin.UserCrsPostMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/8/18.
 */
@Controller
@RequestMapping("/user")
public class UserCrsPostController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/recommend_form_download")
    public String recommend_form_download() {

        return "user/crs/recommend_form_download";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost_requirement")
    public String crsPost_requirement(int postId, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("title", crsPost.getName() + "基本条件");
        modelMap.put("content", crsPost.getRequirement());

        return "common/popup_note";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost_qualification")
    public String crsPost_qualification(int postId, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("title", crsPost.getName() + "任职资格");
        modelMap.put("content", crsPost.getQualification());

        return "common/popup_note";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost_apply")
    public String crsPost_apply(int postId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        return "user/crs/crsPost_apply";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping(value = "/crsPost_apply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_apply(int postId, HttpServletRequest request) {

        crsApplicantService.apply(postId, ShiroHelper.getCurrentUserId());
        logger.info(addLog(SystemConstants.LOG_USER, "干部应聘报名"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost")
    public String crsPost(ModelMap modelMap) {

        long postCount = 0; // 当前应聘岗位数量
        {
            CrsPostExample example = new CrsPostExample();
            example.createCriteria()
                    .andPubStatusEqualTo(SystemConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                    .andStatusEqualTo(SystemConstants.CRS_POST_STATUS_NORMAL); // 读取已发布、正在招聘的岗位
            postCount = crsPostMapper.countByExample(example);
            modelMap.put("postCount", postCount);
        }

        if (postCount > 0) {
            CrsApplicantExample example = new CrsApplicantExample();
            example.createCriteria().andUserIdEqualTo(ShiroHelper.getCurrentUserId())
                    .andStatusEqualTo(SystemConstants.AVAILABLE);
            List<CrsApplicant> crsApplicants = crsApplicantMapper.selectByExample(example);
            List<Integer> postIds = new ArrayList<>();
            for (CrsApplicant crsApplicant : crsApplicants) {
                postIds.add(crsApplicant.getPostId());
            }
            modelMap.put("postIds", postIds); // 已报名岗位
        }

        return "user/crs/crsPost_page";
    }

    @RequiresPermissions("userCrsPost:list")
    @RequestMapping("/crsPost_data")
    public void crsPost_data(HttpServletResponse response, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostExample example = new CrsPostExample();
        CrsPostExample.Criteria criteria = example.createCriteria()
                .andPubStatusEqualTo(SystemConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                .andStatusEqualTo(SystemConstants.CRS_POST_STATUS_NORMAL); // 读取已发布、正在招聘的岗位
        example.setOrderByClause("create_time desc");

        long count = crsPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPost> records = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(CrsPost.class, UserCrsPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
