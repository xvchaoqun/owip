package controller.cet;

import domain.base.ContentTpl;
import domain.cet.*;
import org.apache.commons.lang3.StringUtils;
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
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainDetailController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail")
    public String cetTrain_detail(Integer trainId, ModelMap modelMap) {

        if (trainId != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            modelMap.put("cetTrain", cetTrain);

            Integer planId = cetTrain.getPlanId();
            if(planId!=null) {
                CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                modelMap.put("cetProjectPlan", cetProjectPlan);
                CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
                modelMap.put("cetProject", cetProject);
            }
        }

        return "cet/cetTrain/cetTrain_detail/menu";
    }

    // 通知
    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg")
    public String msg(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        List<ContentTpl> tplList = new ArrayList<>();
        tplList.add(CmTag.getContentTpl(ContentTplConstants.CONTENT_TPL_CET_MSG_2));
        modelMap.put("tplList", tplList);

        return "cet/cetTrain/cetTrain_detail/msg";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg_au")
    public String msg_au(String tplKey, ModelMap modelMap) {

        ContentTpl tpl = CmTag.getContentTpl(tplKey);
        modelMap.put("contentTpl", tpl);

        return "cet/cetTrain/cetTrain_detail/msg_au";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg_list")
    public String msg_list(Integer recordId, String tplKey,
                                 Integer pageSize, Integer pageNo,
                                 ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = 10;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetShortMsgExample example = new CetShortMsgExample();
        CetShortMsgExample.Criteria criteria = example.createCriteria().andTplKeyEqualTo(tplKey);
        if(recordId!=null) {
            criteria.andRecordIdContain(recordId + "");
        }
        example.setOrderByClause("send_time desc");

        long count = cetShortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<CetShortMsg> cetShortMsgs = cetShortMsgMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cetShortMsgs", cetShortMsgs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        String searchStr = "&pageSize=" + pageSize;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "cet/cetTrain/cetTrain_detail/msg_list";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg_send")
    public String msg_send(String tplKey, Integer projectId, Integer trainId, ModelMap modelMap) {

        ContentTpl tpl = CmTag.getContentTpl(tplKey);
        modelMap.put("name", tpl.getName());
        modelMap.put("content", tpl.getContent());

        if(StringUtils.equals(tplKey, ContentTplConstants.CONTENT_TPL_CET_MSG_1)){

            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            String name = cetProject.getName();
            String startDate = DateUtils.formatDate(cetProject.getStartDate(), DateUtils.YYYY_MM_DD_CHINA);
            String endDate = DateUtils.formatDate(cetProject.getEndDate(), DateUtils.YYYY_MM_DD_CHINA);

            String openTime = DateUtils.formatDate(cetProject.getOpenTime(), "MM月dd日 HH:mm");
            String openAddress = cetProject.getOpenAddress();
            String msg = MessageFormat.format(tpl.getContent(), startDate, endDate,
                    name, openTime, openAddress);

            modelMap.put("cetProject", cetProject);
            modelMap.put("content", msg);
        }else  if(StringUtils.equals(tplKey, ContentTplConstants.CONTENT_TPL_CET_MSG_2)) {
            // 发送前预览一下待发送的课程
            List<CetTrainCourse> todayTrainCourseList = iCetMapper.getTodayTrainCourseList(trainId);
            modelMap.put("todayTrainCourseList", todayTrainCourseList);
        }


        return "cet/cetTrain/cetTrain_detail/msg_send";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail/msg_send", method = RequestMethod.POST)
    @ResponseBody
    public Map do_msg_send(Integer projectId, Integer trainId, String tplKey, String mobile) {

        int successCount = 0;
        if(StringUtils.equals(tplKey, ContentTplConstants.CONTENT_TPL_CET_MSG_1)) {

            successCount = cetShortMsgService.projectOpenMsg(projectId, mobile);
            logger.info(addLog(LogConstants.LOG_CET, "第二天开班通知"));

        }else  if(StringUtils.equals(tplKey, ContentTplConstants.CONTENT_TPL_CET_MSG_2)) {

            if(StringUtils.isNotBlank(mobile)){
                successCount = cetShortMsgService.todayCourseSingle(trainId, mobile);
            }else {
                successCount = cetShortMsgService.todayCourse(trainId);
            }
            logger.info(addLog(LogConstants.LOG_CET, "当天开课通知"));
        }
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        return resultMap;
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/time")
    public String time(Integer trainId, Integer projectId, ModelMap modelMap) {

        Date startTime;
        Date endTime;
        if(trainId!=null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            startTime = cetTrain.getStartTime();
            endTime = cetTrain.getEndTime();
        }else{
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            startTime = cetProject.getStartTime();
            endTime = cetProject.getEndTime();
        }

        modelMap.put("startTime", startTime);
        modelMap.put("endTime", endTime);

        return "cet/cetTrain/cetTrain_detail/time";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail/time", method = RequestMethod.POST)
    @ResponseBody
    public Map do_time(Integer trainId, Integer projectId , @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime,
                             HttpServletRequest request) {

        if(trainId!=null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            CetTrain record = new CetTrain();
            record.setId(trainId);
            record.setStartTime(startTime);
            record.setEndTime(endTime);
            cetTrainMapper.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_CET, "更新培训班[{%s}]选课时间：%s~%s",
                    cetTrain.getName(), DateUtils.formatDate(startTime, DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(endTime, DateUtils.YYYY_MM_DD_HH_MM)));
        }else{

            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            CetProject record = new CetProject();
            record.setId(projectId);
            record.setStartTime(startTime);
            record.setEndTime(endTime);

            record.setHasArchive(false);
            cetProjectMapper.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_CET, "更新培训项目[{%s}]选课时间：%s~%s",
                    cetProject.getName(), DateUtils.formatDate(startTime, DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(endTime, DateUtils.YYYY_MM_DD_HH_MM)));
        }

        return success(FormUtils.SUCCESS);
    }
}
