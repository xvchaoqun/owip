package controller.user.crs;

import controller.crs.CrsBaseController;
import domain.member.MemberTeacher;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;

/**
 * Created by lm on 2017/8/18.
 */
@Controller
@RequestMapping("/user")
public class UserCrsInfoController extends CrsBaseController {

    @RequiresPermissions("userCrsInfo:*")
    @RequestMapping("/crsInfo")
    public String crsInfo(ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        return "user/crs/crsInfo";
    }
}
