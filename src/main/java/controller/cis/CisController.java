package controller.cis;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CisController extends BaseController {

    @RequiresPermissions("cis:menu")
    @RequestMapping("/cis")
    public String cis() {

        return "index";
    }

    @RequiresPermissions("cis:menu")
    @RequestMapping("/cis_page")
    public String cis_page(HttpServletResponse response,
                           @RequestParam(defaultValue = "1") Integer cls,
                           ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (cls == 1) {
            // 干部考察报告
            return "forward:/cisInspectObj_page";
        } else if (cls == 2) {
            // 现实表现材料和评价
            return "forward:/cisEvaluate_page";
        } else if (cls == 3) {
            // 本人工作总结
            return "forward:/cadreReport_page";
        } else if (cls == 4) {
            // 数据统计
            return "forward:/stat_cis_page";
        }

        return null;
    }
}
