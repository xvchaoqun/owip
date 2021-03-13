package controller.crs.user;

import controller.crs.CrsBaseController;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantWithBLOBs;
import domain.crs.CrsPost;
import domain.sys.SysApprovalLog;
import domain.sys.SysApprovalLogExample;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
import mixin.UserCrsPostMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import persistence.crs.common.ICrsPost;
import service.cadre.CadreInfoFormService;
import shiro.ShiroHelper;
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DownloadUtils;
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
public class UserApplyCrsPostController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CadreInfoFormService cadreInfoFormService;

    @RequiresPermissions("userApplyCrsPost:list")
    @RequestMapping("/apply_crsPost")
    public String apply_crsPost(@RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "crs/user/apply_crsPost_page";
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
        Boolean isQuit = null;
        List<Byte> postStatusList = new ArrayList<>();
        if(cls==1){
            isQuit = false;
            postStatusList.add(CrsConstants.CRS_POST_STATUS_NORMAL);
        }else if(cls==2){
            // 显示所有报名的记录，包括参加答辩的、退出的、资格审核不通过的（排除已删除的岗位）
            postStatusList.add(CrsConstants.CRS_POST_STATUS_NORMAL);
            postStatusList.add(CrsConstants.CRS_POST_STATUS_FINISH);
        }

        long count = iCrsMapper.countUserApplyCrsPostList(userId, isQuit, postStatusList);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ICrsPost> records = iCrsMapper.selectUserApplyCrsPostList(userId, isQuit, postStatusList,
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

    @RequiresPermissions("userApplyCrsPost:list")
    @RequestMapping("/applicant_log")
    public String applicant_log_page(Integer postId, ModelMap modelMap) {

        if (postId != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/user/applicant_log_page";
    }

    @RequiresPermissions("userApplyCrsPost:list")
    @RequestMapping("/applicant_log_data")
    @ResponseBody
    public void applicant_log_data(Integer postId, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysApprovalLogExample example = new SysApprovalLogExample();
        SysApprovalLogExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        int userId = ShiroHelper.getCurrentUserId();
        criteria.andTypeEqualTo(SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT);
        if (postId != null) {

            CrsApplicant crsApplicant = crsApplicantService.getAvaliable(postId, userId);
            int applicantId = crsApplicant.getId();

            criteria.andRecordIdEqualTo(applicantId);
        }
        criteria.andUserIdEqualTo(userId);

        long count = sysApprovalLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysApprovalLog> records = sysApprovalLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("userApplyCrsPost:edit")
    @RequestMapping(value = "/crsPost_start", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_start(HttpServletRequest request) {

        int cadreId = crsApplicantService.start(ShiroHelper.getCurrentUserId());
        logger.info(addLog(LogConstants.LOG_USER, "干部招聘-开始采集信息"));

        boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadreId);

        Map<String, Object> result = success(FormUtils.SUCCESS);
        result.put("cadreId", cadreId);
        result.put("hasDirectModifyCadreAuth", hasDirectModifyCadreAuth);

        return result;
    }

    // 预览应聘人报名表
    //@RequiresPermissions("userApplyCrsPost:list")
    @RequestMapping("/crsApplicant_preview")
    public String crsApplicant_preview(int applicantId, ModelMap modelMap) {

        CrsApplicantWithBLOBs crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
        modelMap.put("crsApplicant", crsApplicant);
        if(!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREADMIN)){
            ShiroHelper.checkPermission("userApplyCrsPost:list");
            if (crsApplicant == null || crsApplicant.getUserId().intValue() != ShiroHelper.getCurrentUserId()) {
                throw new UnauthorizedException();
            }
        }
        int cadreId = crsApplicant.getCadre().getId();
        modelMap.put("bean", cadreInfoFormService.getCadreInfoForm(cadreId));

        return "crs/user/crsApplicant_preview";
    }

    // 导出应聘人报名表
    @RequiresPermissions("userApplyCrsPost:export")
    @RequestMapping("/crsApplicant_export")
    public void crsApplicant_export(int applicantId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        //输出文件
        String filename = CmTag.getSysConfig().getSchoolName() + "处级干部应聘人报名表";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
        if(crsApplicant==null || crsApplicant.getUserId().intValue()!=ShiroHelper.getCurrentUserId()){
            throw new UnauthorizedException();
        }

        crsExportService.process(crsApplicant.getPostId(), new Integer[]{applicantId}, response.getWriter());
    }

    //目前仅用于管理员退出界面
    @RequestMapping("/crsPost_quit")
    public String crsPost_quit(int postId, Integer applicantId, ModelMap modelMap) {

        if(applicantId == null){
            ShiroHelper.checkPermission("userApplyCrsPost:edit");
        }else{
            ShiroHelper.checkPermission("crsPost:list"); // 招聘管理 权限
        }

        return "crs/user/crsPost_quit";
    }

    // 管理员也拥有该权限
    //@RequiresPermissions("userApplyCrsPost:edit")
    @RequestMapping(value = "/crsPost_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_quit(int postId, Integer applicantId,
                               MultipartFile _quitProof,
                               HttpServletRequest request) throws IOException, InterruptedException {

        Integer userId = null;
        if(applicantId == null){
            ShiroHelper.checkPermission("userApplyCrsPost:edit");
            userId = ShiroHelper.getCurrentUserId();
        }else{
            ShiroHelper.checkPermission("crsPost:list"); // 招聘管理 权限
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
            userId = crsApplicant.getUserId();
        }

        String quitProof = null;
        if(_quitProof!=null && !_quitProof.isEmpty()){
            quitProof = uploadPdf(_quitProof, "crsPost");
        }

        crsApplicantService.quit(postId, userId, quitProof);

        logger.info(addLog(LogConstants.LOG_USER, "干部招聘-退出竞聘"));
        return success(FormUtils.SUCCESS);
    }

    // 管理员也拥有该权限
    //@RequiresPermissions("userApplyCrsPost:edit")
    @RequestMapping(value = "/crsPost_reApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_reApply(int postId, Integer applicantId, HttpServletRequest request) {

        Integer userId = null;
        if(applicantId == null){
            ShiroHelper.checkPermission("userApplyCrsPost:edit");
            userId = ShiroHelper.getCurrentUserId();
        }else{
            ShiroHelper.checkPermission("crsPost:list"); // 招聘管理 权限
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
            userId = crsApplicant.getUserId();
        }

        crsApplicantService.reApply(postId, userId);

        logger.info(addLog(LogConstants.LOG_USER, "干部招聘-重新报名"));
        return success(FormUtils.SUCCESS);
    }
}
