package controller.cadre;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CadrePostInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostInfo_page")
    public String cadrePostInfo_page(int cadreId, @RequestParam(defaultValue = "1") Byte type, ModelMap modelMap) {

        modelMap.put("type", type);

        String name = null;
        switch (type){
            case 1:
                name = "post_pro";
                break;
            case 2:
                name = "post_admin";
                break;
            case 3:
                name = "post_work";
                break;
        }

        modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
        modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));

        return "cadre/cadrePostInfo/cadrePostInfo_page";
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/cadrePostInfo_snyc", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePostInfo_snyc(HttpServletRequest request) {

        // 先清空原来的数据
        commonMapper.excuteSql("update sys_teacher_info set pro_post=null, " +
                        "pro_post_time=null, pro_post_level=null, pro_post_level_time=null," +
                        "manage_level=null, manage_level_time=null, office_level=null,office_level_time=null");

        cadrePostProService.syncAllCadrePost();
        cadrePostAdminService.syncAllCadrePost();
        cadrePostWorkService.syncAllCadrePost();

        cacheService.clearCadreCache();

        logger.info(addLog(LogConstants.LOG_ADMIN, "同步覆盖所有的系统设定的当前岗位，作为干部档案页的岗位信息"));

        return success(FormUtils.SUCCESS);
    }
}
