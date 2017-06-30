package controller.user.cadre;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fafa on 2016/12/16.
 */
@Controller
@RequestMapping("/user")
public class UserCadreController extends BaseController {

    @RequestMapping("/cadre")
    public String cadre(@CurrentUser SysUserView loginUser, HttpServletRequest request) {

        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);

        boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadre.getId());
        request.setAttribute("hasDirectModifyCadreAuth", hasDirectModifyCadreAuth);

        return "forward:/cadre_view?cadreId="+cadre.getId()+"&_auth=self";
    }
}
