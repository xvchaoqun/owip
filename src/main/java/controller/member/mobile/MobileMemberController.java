package controller.member.mobile;

import controller.member.MemberBaseController;
import domain.member.MemberStudent;
import domain.member.MemberTeacher;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;

@Controller
@RequestMapping("/m")
public class MobileMemberController extends MemberBaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("m:member:list")
	@RequestMapping("/member")
	public String member(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:member:list")
	@RequestMapping("/member_page")
	public String member_page() {

		return "member/mobile/member_page";
	}

	@RequiresPermissions("m:member:list")
	@RequestMapping("/member_info")
	public String member_info(Integer userId, ModelMap modelMap) {

		if(userId==null){
			return "member/mobile/teacher_member_info";
		}

		SysUserView uv = sysUserService.findById(userId);
		modelMap.put("uv", uv);
		byte type = uv.getType();
		if(type== SystemConstants.USER_TYPE_JZG){

			MemberTeacher memberTeacher = memberTeacherService.get(userId);
			modelMap.put("memberTeacher", memberTeacher);

			return "member/mobile/teacher_member_info";
		}else {

			MemberStudent memberStudent = memberStudentService.get(userId);
			modelMap.put("memberStudent", memberStudent);

			return "member/mobile/student_member_info";
		}
	}
}
