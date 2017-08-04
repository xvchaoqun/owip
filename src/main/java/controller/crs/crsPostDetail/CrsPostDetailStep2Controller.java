package controller.crs.crsPostDetail;

import controller.BaseController;
import domain.crs.CrsPost;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
public class CrsPostDetailStep2Controller extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step2_time")
    public String step2_time(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        return "crs/crsPost/crsPost_detail/step2_time";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step2_time", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step2_time(int id, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime,
                             byte enrollStatus,
                             HttpServletRequest request) {

        CrsPost record = new CrsPost();
        record.setId(id);
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        record.setEnrollStatus(enrollStatus);

        crsPostService.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新岗位报名时间：%s", id));
        return success(FormUtils.SUCCESS);
    }
}
