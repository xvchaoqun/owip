package controller.cadre;

import bean.CadreInfoForm;
import controller.BaseController;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.cadre.CadreInfoFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void cadreInfoForm_download(Integer cadreId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;
        Integer cadreIds[] = {cadreId};

        cadreInfoFormService.export(cadreIds, request, response);
    }

    @RequiresPermissions("cadreInfoForm2:list")
    @RequestMapping("/cadreInfoForm2_page")
    public String cadreInfoForm2_page(int cadreId, ModelMap modelMap) {

        modelMap.put("bean", cadreInfoFormService.getCadreInfoForm(cadreId));
        return "cadre/cadreInfoForm/cadreInfoForm2_page";
    }

    // 干部信息表下载
    @RequiresPermissions("cadreInfoForm2:download")
    @RequestMapping("/cadreInfoForm2_download")
    public void cadreInfoForm2_download(Integer cadreId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;
        Integer cadreIds[] = {cadreId};

        cadreInfoFormService.export2(cadreIds, request, response);
    }

    @RequiresPermissions("partyMemberInfoForm:list")
    @RequestMapping("/partyMemberInfoForm_page")
    public String partyMemberInfoForm_page(int cadreId, ModelMap modelMap) {

        CadreInfoForm cadreInfoForm = cadreInfoFormService.getCadreInfoForm(cadreId);
        cadreInfoForm.setCadreFamilys(null);
        cadreInfoForm.setCadreFamilyAbroads(null);
        cadreInfoForm.setTrainDesc(null);
        cadreInfoForm.setReward(null);
        cadreInfoForm.setCes(null);

        modelMap.put("bean", cadreInfoForm);
        return "cadre/cadreInfoForm/partyMemberInfoForm_page";
    }

    // 党委委员/支部书记信息采集表下载
    @RequiresPermissions("partyMemberInfoForm:download")
    @RequestMapping("/partyMemberInfoForm_download")
    public void partyMemberInfoForm_download(Integer cadreId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;
        Integer cadreIds[] = {cadreId};

        cadreInfoFormService.export3(cadreIds, request, response);
    }
}
