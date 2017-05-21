package controller.global;

/**
 * Created by fafa on 2017/5/21.
 */

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequiresPermissions("admin:sys")
    @RequestMapping("/admin")
    public String admin() {

        return "index";
    }

    @RequiresPermissions("admin:sys")
    @RequestMapping("/admin_page")
    public String admin_page() {

        return "sys/admin_page";
    }
}
