package controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("member:list")
    @RequestMapping("/member")
    public String member() {

        return "index";
    }
    @RequiresPermissions("member:list")
    @RequestMapping("/member_page")
    public String member_page(HttpServletResponse response,@RequestParam(defaultValue = "1")int type, ModelMap modelMap) {

        modelMap.put("type", type);
        if(type==1) {
            return "forward:/memberStudent_page";
        }
        // type=2教职工  3离退休
        return "forward:/memberTeacher_page";
    }
}
