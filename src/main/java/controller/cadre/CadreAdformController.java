package controller.cadre;

import bean.CadreInfoForm;
import controller.BaseController;
import domain.cadre.CadreView;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.cadre.CadreAdformService;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by fafa on 2016/10/29.
 */
@Controller
public class CadreAdformController extends BaseController {

    @Autowired
    private CadreAdformService cadreAdformService;

    @RequiresPermissions("cadreAdform:list")
    @RequestMapping("/cadreAdform_page")
    public String cadreAdform_page(
            @RequestParam(defaultValue = 1 + "") Byte type,
            int cadreId, ModelMap modelMap) {

        modelMap.put("type", type);

        modelMap.put("bean", cadreAdformService.getCadreAdform(cadreId));

        return "cadre/cadreAdform/cadreAdform_page";
    }

    // 干部任免审批表下载
    @RequiresPermissions("cadreAdform:download")
    @RequestMapping("/cadreAdform_download")
    public void cadreAdform_download(Integer[] cadreIds, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, TemplateException {

        if (cadreIds == null || cadreIds.length == 0) return;

        cadreAdformService.export(cadreIds, request, response);
    }

    // 中组部干部任免审批表下载
    @RequiresPermissions("cadreAdform:download")
    @RequestMapping("/cadreAdform_zzb")
    public void cadreAdform_zzb(int cadreId, HttpServletResponse response) throws IOException, TemplateException, DocumentException {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        //输出文件
        String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + " 干部任免审批表 " + cadre.getUser().getRealname();
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".lrmx").getBytes(), "iso-8859-1"));
        response.setContentType("text/xml;charset=UTF-8");

        CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
        cadreAdformService.zzb(adform, response.getWriter());
    }
}
