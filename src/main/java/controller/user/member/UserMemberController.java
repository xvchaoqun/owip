package controller.user.member;

import controller.MemberBaseController;
import domain.member.MemberStudent;
import domain.member.MemberTeacher;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;

// 权限开通
@Controller
@RequestMapping("/user")
public class UserMemberController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/member")
    public String member(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        Byte type = loginUser.getType();
        if(type==SystemConstants.USER_TYPE_JZG)
            return "forward:/user/teacher_base";
        return "forward:/user/student_base";
    }
    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/student_base")
    public String student_base(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();
        MemberStudent memberStudent = memberStudentService.get(userId);
        modelMap.put("memberStudent", memberStudent);

        return "user/member/member/student_base";
    }
    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/teacher_base")
    public String teacher_base(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        return "user/member/member/teacher_base";
    }
}
