package controller.cet;

import domain.cet.CetPartyAdmin;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetPartyAdminController extends CetBaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetParty:edit")
    @RequestMapping("/cetPartyAdmin")
    public String cetPartyAdmin(Integer cetPartyId, ModelMap modelMap){

        modelMap.put("cetParty", cetPartyMapper.selectByPrimaryKey(cetPartyId));
        List<CetPartyAdmin> partyAdmins = cetPartyAdminService.findByPartyId(cetPartyId);
        modelMap.put("partyAdmins", partyAdmins);

        return "/cet/cetPartyAdmin/cetPartyAdmin_page";
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping("/cetPartyAdmin_au")
    @ResponseBody
    public Map do_cetPartyAdmin_au(Integer userId, Integer cetPartyId, ModelMap modelMap){

        cetPartyAdminService.insertOrUpdate(cetPartyId, userId);
        logger.info(addLog(LogConstants.LOG_CET, "添加二级党委培训管理员：%s", userId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping("/cetPartyAdmin_del")
    @ResponseBody
    public Map cetPartyAdmin_del(Integer id){

        if (null != id) {
            cetPartyAdminService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除二级党委培训管理员：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    //同步分党委管理员
    @RequiresPermissions("cetParty:edit")
    @RequestMapping(value = "/cetPartyAdmin_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map cetParty_sync(@RequestParam(required = false, value = "ids[]" ) Integer[] ids) {

        cetPartyService.batchSync(ids);
        logger.info(addLog(LogConstants.LOG_CET, "同步分党委管理员为二级党委培训管理员，%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

}
