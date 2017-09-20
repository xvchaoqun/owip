package controller.crs.crsPostDetail;

import controller.CrsBaseController;
import domain.base.ContentTpl;
import domain.crs.CrsApplicantView;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import domain.crs.CrsShortMsg;
import domain.crs.CrsShortMsgExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.SystemConstants;
import sys.security.Base64Utils;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CrsPostDetailStep3Controller extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_expert")
    public String step3_expert(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        return "crs/crsPost/crsPost_detail/step3_expert";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_material")
    public String step3_material(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        return "crs/crsPost/crsPost_detail/step3_material";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_meeting")
    public String step3_meeting(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        List<ContentTpl> tplList = new ArrayList<>();
        for (String key : SystemConstants.CRS_SHORT_MSG_TPL_MAP.keySet()) {

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
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date quitDeadline,
                                String meetingAddress,
                                HttpServletRequest request) {

        CrsPost record = new CrsPost();
        record.setId(id);
        record.setMeetingTime(meetingTime);
        record.setMeetingAddress(meetingAddress);
        record.setQuitDeadline(quitDeadline);

        crsPostService.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "设定招聘会时间和地点：%s", id));
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
    public String step3_shortMsg(int postId, String tplKey,
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
        example.createCriteria().andPostIdEqualTo(postId).andTplKeyEqualTo(tplKey);
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

        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/更新干部招聘短信内容：%s", postId));
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

        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部招聘，发送短信：%s, %s", postId, tplKey));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", send);
        return resultMap;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step3_stat")
    public String step3_stat(Integer postId, Boolean isUpdate, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        List<CrsApplicantView> crsApplicants = crsApplicantService.getPassedCrsApplicants(postId);
        modelMap.put("crsApplicants", crsApplicants);

        if (BooleanUtils.isTrue(isUpdate)) {
            return "crs/crsPost/crsPost_detail/step3_stat_au";
        }
        return "crs/crsPost/crsPost_detail/step3_stat";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_stat", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_stat(String jsonResult, MultipartFile statFile) throws Exception {

        jsonResult = new String(Base64Utils.decode(jsonResult), "utf-8");

        String savePath = null;
        String originalFilename = null;
        if (statFile != null && !statFile.isEmpty()) {

            String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");
            String realPath = FILE_SEPARATOR
                    + "crs_post_stat" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                    + "file" + FILE_SEPARATOR
                    + UUID.randomUUID().toString();
            originalFilename = statFile.getOriginalFilename();
            savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(statFile, new File(springProps.uploadPath + savePath));
        }

        crsPostService.stat(jsonResult, savePath, originalFilename);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "专家组推荐意见汇总：%s", jsonResult));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_finish(int postId) throws Exception {


        CrsPost record = new CrsPost();
        record.setMeetingStatus(true);
        record.setStatus(SystemConstants.CRS_POST_STATUS_FINISH);

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdEqualTo(postId)
                .andStatusEqualTo(SystemConstants.CRS_POST_STATUS_NORMAL);

        crsPostMapper.updateByExampleSelective(record, example);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "招聘会完成：%s", postId));
        return success(FormUtils.SUCCESS);
    }

}
