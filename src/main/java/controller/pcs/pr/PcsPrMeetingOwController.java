package controller.pcs.pr;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lm on 2017/9/4.
 */
public class PcsPrMeetingOwController extends BaseController{

    @RequiresPermissions("pcsPrMeetingOw:list")
    @RequestMapping("/pcsPrMeetingOw")
    public String pcsPrMeetingOw(@RequestParam(required = false, defaultValue = "1") byte cls,
                          Integer partyId,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            // 全校情况
            return "pcs/pcsPrMeetingOw/pcsPrMeetingOw_page";
        }

        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        return "pcs/pcsPrMeetingOw/pcsPrMeetingOw_party_page";
    }
}
