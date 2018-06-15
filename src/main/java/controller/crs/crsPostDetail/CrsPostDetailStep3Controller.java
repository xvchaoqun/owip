package controller.crs.crsPostDetail;

import controller.crs.CrsBaseController;
import domain.base.ContentTpl;
import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import domain.crs.CrsPostWithBLOBs;
import domain.crs.CrsShortMsg;
import domain.crs.CrsShortMsgExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.ContentTplConstants;
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.security.Base64Utils;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CrsPostDetailStep3Controller extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step3_expert")
    public String step3_expert(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        return "crs/crsPost/crsPost_detail/step3_expert";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step3_material")
    public String step3_material(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        CrsApplicantViewExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(id)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
        example.setOrderByClause("sort_order desc, enroll_time asc");
        criteria.andIsRequireCheckPassEqualTo(true).andIsQuitEqualTo(false);
        List<CrsApplicantView> crsApplicants = crsApplicantViewMapper.selectByExample(example);
        modelMap.put("crsApplicants", crsApplicants);

        return "crs/crsPost/crsPost_detail/step3_material";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step3_meeting")
    public String step3_meeting(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        List<ContentTpl> tplList = new ArrayList<>();
        for (String key : ContentTplConstants.CONTENT_TPL_CRS_MSG_MAP.keySet()) {

            tplList.add(contentTplMap.get(key));
        }
        modelMap.put("tplList", tplList);

        return "crs/crsPost/crsPost_detail/step3_meeting";
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_meeting", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_meeting(int id,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date meetingTime,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date reportDeadline,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date quitDeadline,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date pptDeadline,
                                String meetingAddress,
                                HttpServletRequest request) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setMeetingTime(meetingTime);
        record.setMeetingAddress(meetingAddress);
        record.setReportDeadline(reportDeadline);
        record.setQuitDeadline(quitDeadline);
        record.setPptDeadline(pptDeadline);

        crsPostService.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_CRS, "设定招聘会时间和地点：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_shortMsg")
    public String step3_shortMsg(String tplKey, ModelMap modelMap) {

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        ContentTpl tpl = contentTplMap.get(tplKey);
        modelMap.put("contentTpl", tpl);

        return "crs/crsPost/crsPost_detail/step3_shortMsg";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_shortMsg_list")
    public String step3_shortMsg_list(int postId,
                                      Integer userId,
                                      @RequestParam(name = "tplKey") String[] tplKey,
                                 Integer pageSize, Integer pageNo,
                                 ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsShortMsgExample example = new CrsShortMsgExample();
        CrsShortMsgExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId).
                andTplKeyIn(Arrays.asList(tplKey));
        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }
        example.setOrderByClause("send_time desc");

        long count = crsShortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<CrsShortMsg> crsShortMsgs = crsShortMsgMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("crsShortMsgs", crsShortMsgs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        String searchStr = "&pageSize=" + pageSize;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "crs/crsPost/crsPost_detail/step3_shortMsg_list";
    }
/*
    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_shortMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_shortMsg(int postId, byte type, String content) {

        crsShortMsgService.insertOrUpdate(postId, type, content);

        logger.info(addLog(LogConstants.LOG_CRS, "添加/更新干部招聘短信内容：%s", postId));
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_shortMsg_send")
    public String step3_shortMsg_send(int postId, String tplKey, ModelMap modelMap) {

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        ContentTpl tpl = contentTplMap.get(tplKey);
        modelMap.put("contentTpl", tpl);
        modelMap.put("msg", crsShortMsgService.getMsg(tpl, crsPostMapper.selectByPrimaryKey(postId)));

        List<CrsApplicantView> applicants = crsApplicantService.getPassedCrsApplicants(postId);
        modelMap.put("applicants", applicants);

        return "crs/crsPost/crsPost_detail/step3_shortMsg_send";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_shortMsg_send", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_shortMsg_send(int postId, String tplKey) {

        int send = crsShortMsgService.send(postId, tplKey);

        logger.info(addLog(LogConstants.LOG_CRS, "干部招聘，发送短信：%s, %s", postId, tplKey));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", send);
        return resultMap;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_stat")
    public String step3_stat(Integer postId, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        List<CrsApplicantView> crsApplicants = crsApplicantService.getPassedCrsApplicants(postId);
        modelMap.put("crsApplicants", crsApplicants);

        modelMap.put("expertCount", crsPostExpertService.getExpertUserIds(postId, null).size());

        modelMap.put("firstUserId", crsCandidateService.getUserId(postId, true));
        modelMap.put("secondUserId", crsCandidateService.getUserId(postId, false));

        return "crs/crsPost/crsPost_detail/step3_stat_au";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_stat", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_stat(String jsonResult, MultipartFile statFile) throws Exception {

        jsonResult = new String(Base64Utils.decode(jsonResult), "utf-8");

        String savePath = null;
        String originalFilename = null;
        if (statFile != null && !statFile.isEmpty()) {

            savePath = uploadPdf(statFile, "crs_post_stat");
            originalFilename = statFile.getOriginalFilename();
        }

        crsPostService.stat(jsonResult, savePath, originalFilename);

        logger.info(addLog(LogConstants.LOG_CRS, "专家组推荐意见汇总：%s", jsonResult));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_finish(int postId) throws Exception {


        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setMeetingStatus(true);
        record.setStatus(CrsConstants.CRS_POST_STATUS_FINISH);

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdEqualTo(postId)
                .andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL);

        crsPostMapper.updateByExampleSelective(record, example);

        logger.info(addLog(LogConstants.LOG_CRS, "招聘会完成：%s", postId));
        return success(FormUtils.SUCCESS);
    }

}
