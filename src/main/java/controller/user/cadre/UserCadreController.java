package controller.user.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.sys.SysUserView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fafa on 2016/12/16.
 */
@Controller
@RequestMapping("/user")
public class UserCadreController extends BaseController {

    @RequestMapping("/cadre")
    public String cadre() {

        return "index";
    }

    @RequestMapping("/cadre_page")
    public String cadre_page(@CurrentUser SysUserView loginUser, HttpServletRequest request) {

        int userId = loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);

        return "forward:/cadre_view?cadreId="+cadre.getId()+"&_auth=self";
    }
}
