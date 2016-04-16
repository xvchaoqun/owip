package controller.user;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

// 权限开通
@Controller
@RequestMapping("/user")
public class UserMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequiresRoles("member")
    @RequestMapping("/member")
    public String member(@CurrentUser SysUser loginUser) {

        return "index";
    }
    @RequiresRoles("member")
    @RequestMapping("/member_page")
    public String member_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        Byte type = loginUser.getType();
        if(type==SystemConstants.USER_TYPE_JZG)
            return "forward:/user/teacher_base";
        return "forward:/user/student_base";
    }
    @RequiresRoles("member")
    @RequestMapping("/student_base")
    public String student_base(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();

        MemberStudent memberStudent = memberStudentService.get(userId);
        modelMap.put("memberStudent", memberStudent);

        modelMap.put("GENDER_MALE_MAP", SystemConstants.GENDER_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("MEMBER_POLITICAL_STATUS_MAP", SystemConstants.MEMBER_POLITICAL_STATUS_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        return "user/member/student_base";
    }
    @RequiresRoles("member")
    @RequestMapping("/teacher_base")
    public String teacher_base(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        modelMap.put("GENDER_MALE_MAP", SystemConstants.GENDER_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("MEMBER_POLITICAL_STATUS_MAP", SystemConstants.MEMBER_POLITICAL_STATUS_MAP);

        return "user/member/teacher_base";
    }
}
