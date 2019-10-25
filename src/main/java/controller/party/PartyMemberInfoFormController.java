package controller.party;

import bean.PartyMemberInfoForm;
import controller.BaseController;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.cadre.CadreInfoFormService;
import service.common.FreemarkerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 支部书记信息采集表
@Controller
public class PartyMemberInfoFormController extends BaseController {

    @Autowired
    private CadreInfoFormService cadreInfoFormService;
    @Autowired
    private FreemarkerService freemarkerService;

    @RequiresPermissions("partyMemberInfoForm:list")
    @RequestMapping("/partyMemberInfoForm_page")
    public String partyMemberInfoForm_page(int cadreId, int branchId, ModelMap modelMap) {

        PartyMemberInfoForm partyMemberInfoForm = cadreInfoFormService.getPartyMemberInfoForm(cadreId, branchId);
        modelMap.put("bean", partyMemberInfoForm);

        return "party/partyMember/partyMemberInfoForm_page";
    }

    // 党委委员/支部书记信息采集表下载
    @RequiresPermissions("partyMemberInfoForm:download")
    @RequestMapping("/partyMemberInfoForm_download")
    public void partyMemberInfoForm_download(Integer cadreId, int branchId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;

        cadreInfoFormService.exportPartyMember(cadreId, branchId, request, response);
    }
}
