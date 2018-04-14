package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetPlanCourse;
import domain.cet.CetPlanCourseObj;
import org.apache.shiro.authz.UnauthorizedException;
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
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user/cet")
public class UserCetPlanCourseObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 上传学习心得
    @RequiresPermissions("userCetProject:edit")
    @RequestMapping(value = "/cetPlanCourseObj_uploadNote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObj_uploadNote(int planCourseId, MultipartFile _file,
                                            HttpServletRequest request) throws IOException, InterruptedException {

        if(_file!=null && !_file.isEmpty()) {

            int userId = ShiroHelper.getCurrentUserId();
            CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.getByUserId(userId, planCourseId);
            if(cetPlanCourseObj==null){
                throw new UnauthorizedException();
            }

            CetPlanCourseObj record = new CetPlanCourseObj();
            record.setId(cetPlanCourseObj.getId());
            record.setNote(uploadDocOrPdf(_file, "cetPlanCourseObj_note"));

            cetPlanCourseObjService.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_CET, "本人上传心得体会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userCetProject:edit")
    @RequestMapping("/cetPlanCourseObj_uploadNote")
    public String cetPlanCourseObj_uploadNote(int planCourseId, ModelMap modelMap) {

        CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
        modelMap.put("cetPlanCourse", cetPlanCourse);

        return "cet/user/cetPlanCourseObj_uploadNote";
    }
}
