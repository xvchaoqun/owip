package controller.user.crs;

import controller.crs.CrsBaseController;
import controller.global.OpException;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
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
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.CrsConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
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
        /*MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);*/

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        CrsApplicant crsApplicant = crsApplicantService.getAvaliable(postId, userId);
        modelMap.put("crsApplicant", crsApplicant);

        return "user/crs/crsPost_apply";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping(value = "/crsPost_apply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_apply(Integer id, int postId, byte status, String report, HttpServletRequest request) {

        crsApplicantService.apply(id, postId, status, report,  ShiroHelper.getCurrentUserId());
        logger.info(addLog(SystemConstants.LOG_USER, "干部应聘报名"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost_apply_detail")
    public String crsPost_apply_detail(int postId, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        return "user/crs/crsPost_apply_detail";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost_apply_notice")
    public String crsPost_apply_notice(int postId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        CrsApplicant crsApplicant = crsApplicantService.getAvaliable(postId, userId);
        modelMap.put("crsApplicant", crsApplicant);

        return "user/crs/crsPost_apply_notice";
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping(value = "/crsPost_apply_ppt", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_apply_ppt(int postId, MultipartFile ppt, HttpServletRequest request) throws IOException, InterruptedException {

        int userId = ShiroHelper.getCurrentUserId();
        CrsApplicant crsApplicant = crsApplicantService.getAvaliable(postId, userId);
        if(crsApplicant==null || ppt==null || ppt.isEmpty()){
            return failed("参数错误。");
        }

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        Date meetingTime = crsPost.getMeetingTime();
        Date reportDeadline = crsPost.getReportDeadline();
        if(reportDeadline!=null && DateUtils.compareDate(new Date(), reportDeadline)){
            throw new OpException("招聘会于{0}召开，{1}之后不可上传应聘PPT。",
                    DateUtils.formatDate(meetingTime, "yyyy年MM月dd日 HH点"),
                    DateUtils.formatDate(reportDeadline, "yyyy年MM月dd日 HH点"));
        }

        String originalFilename = ppt.getOriginalFilename();
        String savePath = upload(ppt, "crsApplicant_ppt");

        CrsApplicant record = new CrsApplicant();
        record.setId(crsApplicant.getId());
        record.setPptName(originalFilename);
        record.setPpt(savePath);
        crsApplicantMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_USER, "上传应聘PPT"));
        return success(FormUtils.SUCCESS);
    }

    // 已报名岗位
    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost_hasApplys")
    @ResponseBody
    public void crsPost_hasApplys(HttpServletResponse response) throws IOException {

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andUserIdEqualTo(ShiroHelper.getCurrentUserId())
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
        List<CrsApplicant> crsApplicants = crsApplicantMapper.selectByExample(example);

        JSONUtils.write(response, crsApplicants, "postId", "isQuit");
        return;
       /* Map<String, Object> resultMap = success();
        resultMap.put("crsApplicants", crsApplicants);
        JSONUtils.write(response, resultMap, "success", "crsApplicants", "crsApplicants.postId", "crsApplicants.isQuit");
        return;*/
    }

    @RequiresPermissions("userCrsPost:*")
    @RequestMapping("/crsPost")
    public String crsPost_page(ModelMap modelMap) {

        long postCount = 0; // 当前应聘岗位数量
        {
            CrsPostExample example = new CrsPostExample();
            example.createCriteria()
                    .andPubStatusEqualTo(CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                    .andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL); // 读取已发布、正在招聘的岗位
            postCount = crsPostMapper.countByExample(example);
            modelMap.put("postCount", postCount);
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
                .andPubStatusEqualTo(CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                .andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL); // 读取已发布、正在招聘的岗位
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
