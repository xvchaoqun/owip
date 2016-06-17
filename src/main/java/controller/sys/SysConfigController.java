package controller.sys;

import controller.BaseController;
import domain.SysConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import java.util.Map;

/**
 * Created by fafa on 2016/6/17.
 */
@Controller
public class SysConfigController extends BaseController {

    @RequiresPermissions("SysConfig:note")
    @RequestMapping("/sysConfig_note")
    public String sysConfig_note_au(Byte type, ModelMap modelMap) {

        SysConfig SysConfig = sysConfigService.get();
        modelMap.put("sysConfig", SysConfig);

        modelMap.put("type", type);

        return "sys/sysConfig/sysConfig_note_au";
    }

    @RequiresPermissions("SysConfig:note")
    @RequestMapping(value = "/sysConfig_note", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysConfig_note_au(String note, Byte type, ModelMap modelMap) {

        if(type == SystemConstants.SYS_CONFIG_APPLY_SELF_NOTE)
            sysConfigService.updateApplySelfNote(note);
        if(type == SystemConstants.SYS_CONFIG_APPLY_SELF_APPROVAL_NOTE)
            sysConfigService.updateApplySelfApprovalNote(note);

        return success(FormUtils.SUCCESS);
    }
}
