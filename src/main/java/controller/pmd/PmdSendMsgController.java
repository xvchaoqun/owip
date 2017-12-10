package controller.pmd;

import bean.ShortMsgBean;
import controller.PmdBaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lm on 2017/12/10.
 */
@Controller
@RequestMapping("/pmd")
public class PmdSendMsgController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notify")
    public String pmdMember_notify(int id, ModelMap modelMap) {

        ShortMsgBean shortMsgBean = pmdSendMsgService.getShortMsgBean(id);
        modelMap.put("shortMsgBean", shortMsgBean);

        return "pmd/sendMsg/pmdSendMsg_notify";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notify(int id, String mobile, HttpServletRequest request) {

        if(StringUtils.isBlank(mobile) || !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误");
        }

        pmdSendMsgService.notify(id, mobile, IpUtils.getRealIp(request));

        logger.info(addLog(SystemConstants.LOG_PMD, "短信催交党费：%s", id));
        return success(FormUtils.SUCCESS);
    }
}
