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
public class CrsPostDetailStep3Controller extends BaseController {

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

        return "crs/crsPost/crsPost_detail/step3_meeting";
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step3_meeting", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step3_meeting(int id, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date meetingTime,
                                String meetingAddress,
                                HttpServletRequest request) {

        CrsPost record = new CrsPost();
        record.setId(id);
        record.setMeetingTime(meetingTime);
        record.setMeetingAddress(meetingAddress);

        crsPostService.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "设定招聘会时间和地点：%s", id));
        return success(FormUtils.SUCCESS);
    }

}
