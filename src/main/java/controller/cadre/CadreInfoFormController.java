package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreView;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.cadre.CadreInfoFormService;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 干部信息采集表
@Controller
public class CadreInfoFormController extends BaseController {

    @Autowired
    private CadreInfoFormService cadreInfoFormService;

    @RequiresPermissions("cadreInfoForm:list")
    @RequestMapping("/cadreInfoForm_page")
    public String cadreInfoForm_page(int cadreId, ModelMap modelMap) {

        modelMap.put("bean", cadreInfoFormService.getCadreInfoForm(cadreId));
        return "cadre/cadreInfoForm/cadreInfoForm_page";
    }

    // 干部信息采集表下载
    @RequiresPermissions("cadreInfoForm:download")
    @RequestMapping("/cadreInfoForm_download")
    public void adform(int cadreId, HttpServletResponse response) throws IOException, TemplateException {

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        //输出文件
        String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + " 干部信息采集表 " + cadre.getUser().getRealname();
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
        response.setContentType("application/msword;charset=UTF-8");

        cadreInfoFormService.process(cadreId, response.getWriter());
    }


}
