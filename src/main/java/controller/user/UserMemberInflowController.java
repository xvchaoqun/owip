package controller.user;

import controller.BaseController;
import domain.MemberInflow;
import domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import shiro.CurrentUser;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserMemberInflowController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("inflowMember")
    @RequestMapping("/memberInflow_base")
    public String memberInflow_base() {

        return "index";
    }

    @RequiresRoles("inflowMember")
    @RequestMapping("/memberInflow_base_page")
    public String memberInflow_base_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();
        MemberInflow memberInflow = memberInflowService.get(userId);
        modelMap.put("memberInflow", memberInflow);

        return "user/memberInflow/memberInflow_base";
    }

}
