package controller.cet;

import domain.base.ContentTpl;
import domain.cet.CetShortMsg;
import domain.cet.CetShortMsgExample;
import domain.cet.CetTrain;
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
import sys.constants.CetConstants;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
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
        }
        return "cet/cetTrain/cetTrain_detail";
    }

    // 开课
    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/begin")
    public String begin(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail/begin";
    }

    // 通知
    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg")
    public String msg(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        List<ContentTpl> tplList = new ArrayList<>();
        for (String key : ContentTplConstants.CONTENT_TPL_CET_MSG_MAP.keySet()) {

            tplList.add(contentTplMap.get(key));
        }
        modelMap.put("tplList", tplList);

        return "cet/cetTrain/cetTrain_detail/msg";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg_au")
    public String msg_au(String tplKey, ModelMap modelMap) {

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        ContentTpl tpl = contentTplMap.get(tplKey);
        modelMap.put("contentTpl", tpl);

        return "cet/cetTrain/cetTrain_detail/msg_au";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/msg_list")
    public String msg_list(int trainId, String tplKey,
                                 Integer pageSize, Integer pageNo,
                                 ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetShortMsgExample example = new CetShortMsgExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andTplKeyEqualTo(tplKey);
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
    public String msg_send(int trainId, String tplKey, ModelMap modelMap) {

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        ContentTpl tpl = contentTplMap.get(tplKey);
        modelMap.put("contentTpl", tpl);
        //modelMap.put("msg", cetShortMsgService.getMsg(tpl, cetTrainMapper.selectByPrimaryKey(trainId)));
        
        return "cet/cetTrain/cetTrain_detail/msg_send";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail/msg_send_1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_msg_send_1(int traineeId) {

        boolean ret = cetShortMsgService.sendMsg_1(traineeId);
        logger.info(addLog(SystemConstants.LOG_CET, "干部教育培训，发送短信：%s, %s", traineeId, ret));

        if(!ret) return failed("发送失败。");

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail/msg_send_2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_msg_send_2(int traineeId, int trainCourseId) {

        boolean ret = cetShortMsgService.sendMsg_2(traineeId, trainCourseId);
        logger.info(addLog(SystemConstants.LOG_CET, "干部教育培训，发送短信：%s, %s, %s",
                traineeId, trainCourseId, ret));

        if(!ret) return failed("发送失败。");

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/stat")
    public String stat(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail/stat";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/trainee")
    public String trainee(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        Map<Integer, Object> yearPeriodMap = cetTrainService.traineeYearPeriodMap(trainId);
        modelMap.put("yearPeriodMap", yearPeriodMap);

        return "cet/cetTrain/cetTrain_detail/trainee";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/time")
    public String time(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail/time";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail/time", method = RequestMethod.POST)
    @ResponseBody
    public Map do_time(int trainId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime,
                             HttpServletRequest request) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setStartTime(startTime);
        record.setEndTime(endTime);

        cetTrainService.updateBase(record);

        logger.info(addLog(SystemConstants.LOG_CET, "更新培训班[{%s}]报名时间：%s~%s",
                cetTrain.getName(), DateUtils.formatDate(startTime, DateUtils.YYYY_MM_DD_HH_MM),
                DateUtils.formatDate(endTime, DateUtils.YYYY_MM_DD_HH_MM)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail/enrollStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map do_enrollStatus(int trainId,
                             byte enrollStatus, // 开启、关闭、暂停报名
                             HttpServletRequest request) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setEnrollStatus(enrollStatus);

        cetTrainService.updateBase(record);

        logger.info(addLog(SystemConstants.LOG_CET, "更新培训班[{%s}]报名开关：%s",
                cetTrain.getName(), CetConstants.CET_TRAIN_ENROLL_STATUS_MAP.get(enrollStatus)));

        return success(FormUtils.SUCCESS);
    }
}
