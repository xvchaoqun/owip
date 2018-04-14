package controller.cadre;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CadreInfoCheckController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInfo:check")
    @RequestMapping("/cadreInfoCheck_table")
    public String cadreInfoCheck_table() {

        return "cadre/cadreInfoCheck/cadreInfoCheck_table";
    }

    @RequiresPermissions("cadreInfo:check")
    @RequestMapping(value = "/cadreInfoCheck_update", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfoCheck_update(int cadreId, String name, boolean isChecked, HttpServletRequest request) {

        if(!cadreInfoCheckService.canUpdateInfoCheck(cadreId, name)) {

            return failed("当前不可进行此操作");
        }

        cadreInfoCheckService.update(cadreId, name, isChecked);
        logger.info(addLog(LogConstants.LOG_ADMIN, "添加/更新干部信息检查：%s, %s, %s", cadreId, name, isChecked));
        return success(FormUtils.SUCCESS);
    }
}
