package controller.abroad;

import domain.abroad.ApproverType;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/abroad")
public class AbroadAuthController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:list")
    @RequestMapping("/approvalAuth")
    public String approvalAuth(@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        switch (cls){
            case 1:
                return "forward:/abroad/applicatType";
            case 2:
                return "forward:/abroad/approverType";
            case 3:
                return "forward:/abroad/approvalAuthList";
        }

        return null;
    }

    @RequiresPermissions("approvalAuth:list")
    @RequestMapping("/approvalAuthList")
    public String approvalAuthList(ModelMap modelMap) {

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        modelMap.put("approverTypeMap",approverTypeMap);

        Map<Integer, Map<Integer, List<SysUserView>>> cadreApproverListMap = abroadService.getCadreApproverListMap();
        modelMap.put("cadreApproverListMap",cadreApproverListMap);

        return "abroad/approvalAuth/approvalAuthList";
    }

}
