package controller.member.mobile;

import controller.member.MemberBaseController;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;

import javax.servlet.http.HttpServletResponse;

// 权限开通
@Controller
@RequestMapping("/m")
public class MobileApplyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_GUEST)
	@RequestMapping("/apply")
	public String apply() {
		return "mobile/index";
	}

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/apply_page")
    public String apply_page(HttpServletResponse response, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        Byte currentApplyType = enterApplyService.getCurrentApplyType(userId);
        if(currentApplyType==null) {
            modelMap.put("member", memberService.get(userId));
            return "member/mobile/apply";
        }

        switch (currentApplyType){
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY:
                return "forward:/user/memberApply?isMobile=1";
            case OwConstants.OW_ENTER_APPLY_TYPE_RETURN:
                return "forward:/user/memberReturn?isMobile=1";
             case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERIN:
                return "forward:/user/memberIn?isMobile=1";
             case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERINFLOW:
                return "forward:/user/memberInflow?isMobile=1";
        }

        return null;
    }
}
