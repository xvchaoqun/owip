package controller.user.crs;

import controller.CrsBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("/user")
public class UserApplicantAdjustController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*@RequiresPermissions("userApplyCrsPost:*")
    @RequestMapping("/crsApplicantAdjust")
    public String crsApplicantAdjust(ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        // 已报名岗位
        Map<Integer, ICrsPost> applyPostMap = new HashMap<>();
        {
            List<ICrsPost> applyPosts = iCrsMapper.findUserApplyCrsPosts(userId,
                    CrsConstants.CRS_POST_STATUS_NORMAL, new RowBounds());
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
                    .andPubStatusEqualTo(CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                    .andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL); // 读取已发布、正在招聘的岗位

            example.setOrderByClause("create_time desc");
            List<CrsPost> crsPosts = crsPostMapper.selectByExample(example);

            for (CrsPost crsPost : crsPosts) {

                if (crsPost.getSwitchStatus() != CrsConstants.CRS_POST_ENROLL_STATUS_OPEN // 必须是可以报名的岗位
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
    }*/
}
