package controller.member.mobile;

import controller.member.MemberBaseController;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.RoleConstants;

// 权限开通
@Controller
@RequestMapping("/m")
public class MobileMemberTransferController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_MEMBER)
	@RequestMapping("/memberTransfer")
	public String memberTransfer() {
		return "mobile/index";
	}

    @RequiresRoles(RoleConstants.ROLE_MEMBER)
    @RequestMapping("/memberTransfer_page")
    public String memberTransfer_page() {

        return "forward:/user/memberTransfer?isMobile=1";
    }
}
