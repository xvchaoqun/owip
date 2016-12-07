package controller.user.modify;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.ext.ExtJzg;
import domain.modify.ModifyBaseApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserModifyTableApplyController extends BaseController {

    // 撤销申请
    @RequiresRoles("cadre")
    @RequestMapping(value = "/modifyTableApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map back(Integer id,
                    HttpServletRequest request) {

        try {
            modifyTableApplyService.back(id);
        } catch (Exception ex) {
            return failed(ex.getMessage());
        }

        return success();
    }
}
