package controller.sys;

import controller.BaseController;
import domain.sys.Feedback;
import domain.sys.FeedbackExample;
import domain.sys.FeedbackExample.Criteria;
import org.apache.commons.lang3.StringUtils;
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
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FeedbackController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("feedback:list")
    @RequestMapping("/feedback")
    public String feedback() {

        return "index";
    }
    @RequiresPermissions("feedback:list")
    @RequestMapping("/feedback_page")
    public String feedback_page(Integer userId,ModelMap modelMap) {

        if (userId!=null) {
           modelMap.put("user", sysUserService.findById(userId));
        }

        return "sys/feedback/feedback_page";
    }

    @RequiresPermissions("feedback:list")
    @RequestMapping("/feedback_data")
    public void feedback_data(HttpServletResponse response,
                                    Integer userId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        FeedbackExample example = new FeedbackExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        int count = feedbackMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Feedback> records= feedbackMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(feedback.class, feedbackMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("feedback:edit")
    @RequestMapping(value = "/feedback_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_feedback_au(Feedback record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setUserId(ShiroHelper.getCurrentUserId());
            record.setCreateTime(new Date());
            record.setIp(ContextHelper.getRealIp());
            feedbackService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_USER, "添加系统反馈意见：%s", record.getId()));
        } else {

            feedbackService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_USER, "更新系统反馈意见：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("feedback:edit")
    @RequestMapping("/feedback_au")
    public String feedback_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Feedback feedback = feedbackMapper.selectByPrimaryKey(id);
            modelMap.put("feedback", feedback);
        }
        return "sys/feedback/feedback_au";
    }

    @RequiresPermissions("feedback:del")
    @RequestMapping(value = "/feedback_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_feedback_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            feedbackService.del(id);
            logger.info(addLog( SystemConstants.LOG_USER, "删除系统反馈意见：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("feedback:del")
    @RequestMapping(value = "/feedback_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            feedbackService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_USER, "批量删除系统反馈意见：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
