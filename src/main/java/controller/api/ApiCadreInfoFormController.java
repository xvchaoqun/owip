package controller.api;

import controller.BaseController;
import domain.cadre.CadreView;
import interceptor.NeedSign;
import interceptor.SignParam;
import interceptor.SignParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.cadre.CadreInfoFormService;


@Controller
@RequestMapping("/api")
public class ApiCadreInfoFormController extends BaseController {

    @Autowired
    private CadreInfoFormService cadreInfoFormService;

    @NeedSign
    @RequestMapping("/getCadreInfoForm")
    public String getCadreInfoForm(@SignParam(value = "code") String code, ModelMap modelMap, @RequestParam("app")String app){


        CadreView cadreView =  iCadreMapper.getCadreByCode(code);
        if(cadreView!=null){
            modelMap.put("bean", cadreInfoFormService.getCadreInfoForm(cadreView.getId()));
            modelMap.put("hideDownloadBtn",true);

            return "cadre/cadreInfoForm/cadreInfoForm_page";
        }else{
            throw new SignParamsException("信息采集表不存在");
        }
    }
}
