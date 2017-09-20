package controller.user.crs;

import controller.CrsBaseController;
import domain.crs.CrsApplicantAdjustView;
import domain.crs.CrsApplicantAdjustViewExample;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
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
import persistence.common.bean.ICrsPost;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserApplicantAdjustController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping("/crsApplicantAdjust")
    public String crsApplicantAdjust(ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        // 已报名岗位
        Map<Integer, ICrsPost> applyPostMap = new HashMap<>();
        {
            List<ICrsPost> applyPosts = iCrsMapper.findUserApplyCrsPosts(userId,
                    SystemConstants.CRS_POST_STATUS_NORMAL, new RowBounds());
            modelMap.put("applyPosts", applyPosts);

            for (ICrsPost crsPost : applyPosts) {
                applyPostMap.put(crsPost.getId(), crsPost);
            }
        }

        // 可选择的岗位
        List<CrsPost> selectablePosts = new ArrayList<>();
        {
            // 全部岗位
            CrsPostExample example = new CrsPostExample();
            CrsPostExample.Criteria criteria = example.createCriteria()
                    .andIsPublishEqualTo(true)
                    .andStatusEqualTo(SystemConstants.CRS_POST_STATUS_NORMAL); // 读取已发布、正在招聘的岗位

            example.setOrderByClause("create_time desc");
            List<CrsPost> crsPosts = crsPostMapper.selectByExample(example);

            for (CrsPost crsPost : crsPosts) {

                if (crsPost.getSwitchStatus() != SystemConstants.CRS_POST_ENROLL_STATUS_OPEN // 必须是可以报名的岗位
                        || applyPostMap.containsKey(crsPost.getId())) continue;
                selectablePosts.add(crsPost);
            }

            modelMap.put("selectablePosts", selectablePosts);
        }

        return "user/crs/crsApplicantAdjust";
    }

    @RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping(value = "/crsApplicantAdjust", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicantAdjust(@RequestParam(value = "applyPostIds[]") Integer[] applyPostIds,
                                     @RequestParam(value = "selectablePostIds[]") Integer[] selectablePostIds,
                                     HttpServletRequest request) {

        crsApplicantService.adjust(applyPostIds, selectablePostIds, ShiroHelper.getCurrentUserId());

        logger.info(addLog(SystemConstants.LOG_USER, "干部招聘-调整岗位"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping("/crsApplicantAdjust_page")
    public String crsApplicantAdjust_page(ModelMap modelMap) {

        CrsApplicantAdjustViewExample example = new CrsApplicantAdjustViewExample();
        example.createCriteria().andUserIdEqualTo(ShiroHelper.getCurrentUserId());
        example.setOrderByClause("adjust_time desc");
        List<CrsApplicantAdjustView> adjusts = crsApplicantAdjustViewMapper.selectByExample(example);

        modelMap.put("adjusts", adjusts);

        return "user/crs/crsApplicantAdjust_page";
    }
}
