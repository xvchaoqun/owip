package controller.cla;

import bean.ShortMsgBean;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.shiro.CurrentUser;
import sys.utils.FormUtils;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/cla")
public class ClaShortMsgController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/shortMsg_view")
    public String shortMsg_view(@CurrentUser SysUserView loginUser,
                                String type,
                                Integer id, ModelMap modelMap) {

        ShortMsgBean shortMsgBean = claShortMsgService.getShortMsgBean(loginUser.getId(), null, type, id);
        modelMap.put("shortMsgBean", shortMsgBean);

        return "cla/shortMsg_view";
    }

    @RequiresPermissions("ShortMsg:send")
    @RequestMapping(value = "/shortMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsg(@CurrentUser SysUserView loginUser, String type, Integer id, HttpServletRequest request) {

        ShortMsgBean shortMsgBean = claShortMsgService.getShortMsgBean(loginUser.getId(), null, type, id);

        if(shortMsgService.send(shortMsgBean, IpUtils.getRealIp(request))){

            logger.info(addLog(LogConstants.LOG_ADMIN, "发送短信：%s", shortMsgBean.getContent()));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }
}
