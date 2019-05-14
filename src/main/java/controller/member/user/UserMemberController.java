package controller.member.user;

import controller.member.MemberBaseController;
import domain.member.MemberView;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;

// 权限开通
@Controller
@RequestMapping("/user")
public class UserMemberController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_MEMBER)
    @RequestMapping("/member")
    public String member(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();
        MemberView memberView = iMemberMapper.getMemberView(userId);
        modelMap.put("member", memberView);

        Byte type = loginUser.getType();
        if(type==SystemConstants.USER_TYPE_JZG)
            return "member/user/member/teacher_base";

         return "member/user/member/student_base";
    }
}
