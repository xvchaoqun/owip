package controller.party;

import controller.BaseController;
import domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.SystemConstants;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("member:list")
    @RequestMapping("/member")
    public String member() {

        return "index";
    }
    @RequiresPermissions("member:list")
    @RequestMapping("/member_page")
    public String member_page(HttpServletResponse response,@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(cls==1) { // => member.type=3 member.status=1
            return "forward:/memberStudent_page";
        }
        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                4应退休   =>  member.type=2 member.status=1
                5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
         */
        return "forward:/memberTeacher_page";
    }

 /*   @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_view(HttpServletResponse response, int userId, ModelMap modelMap) {

        return "index";
    }*/

    @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_show_page(HttpServletResponse response, int userId, ModelMap modelMap) {

        SysUser sysUser = sysUserService.findById(userId);
        if(sysUser.getType() == SystemConstants.MEMBER_TYPE_TEACHER)  // 这个地方的判断可能有问题，应该用党员信息里的类别++++++++++++
            return "party/member/teacher_view";
        return "party/member/student_view";
    }
}
