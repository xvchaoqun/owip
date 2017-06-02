package controller.user.sys;

import controller.BaseController;
import domain.sys.Feedback;
import domain.sys.FeedbackExample;
import org.apache.ibatis.session.RowBounds;
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
import sys.utils.ContextHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserFeedbackController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/feedback")
    public String feedback(ModelMap modelMap) {

      /*  FeedbackExample example = new FeedbackExample();
        example.createCriteria().andUserIdEqualTo(ShiroHelper.getCurrentUserId());
        example.setOrderByClause("create_time desc");
        List<Feedback> feedbacks = feedbackMapper.selectByExample(example);
        modelMap.put("feedbacks", feedbacks);*/

        return "user/sys/feedback/feedback_page";
    }

    @RequestMapping("/feedback_list")
    public String feedback_list(Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = /*springProps.pageSize*/5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        FeedbackExample example = new FeedbackExample();
        FeedbackExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
        example.setOrderByClause("create_time desc");

        int count = feedbackMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Feedback> feedbacks = feedbackMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("feedbacks", feedbacks);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("metaClassMap", metaClassService.findAll());

        return "user/sys/feedback/feedback_list";
    }

    @RequestMapping(value = "/feedback_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_feedback_au(String content, HttpServletRequest request) {

        Feedback record = new Feedback();
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());
        feedbackService.insertSelective(record);
        logger.info(addLog(SystemConstants.LOG_USER, "添加系统反馈意见：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }
}
