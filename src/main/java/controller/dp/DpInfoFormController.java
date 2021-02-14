package controller.dp;

import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 基本情况登记表
@Controller
@RequestMapping("/dp")
public class DpInfoFormController extends DpBaseController {

    @RequiresPermissions("dpInfoForm:list")
    @RequestMapping("/dpInfoForm_page")
    public String dpInfoForm_page(Integer userId, ModelMap modelMap) {

        modelMap.put("bean", dpInfoFormService.getDpInfoForm(userId));
        return "dp/dpInfoForm/dpInfoForm_page";
    }

    // 基本情况登记表下载
    @RequiresPermissions("dpInfoForm:download")
    @RequestMapping("/dpInfoForm_download")
    public void dpInfoForm_page(Integer userId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(userId == null) return;
        Integer userIds[] = {userId};

        dpInfoFormService.export(userIds, request, response);
    }

}
