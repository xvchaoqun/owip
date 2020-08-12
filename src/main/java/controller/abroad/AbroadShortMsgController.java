package controller.abroad;

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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.utils.FormUtils;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/abroad")
public class AbroadShortMsgController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/shortMsg_view")
    public String shortMsg_view(@CurrentUser SysUserView loginUser,
                                String type, // passport
                                Integer id, ModelMap modelMap) {

        ShortMsgBean shortMsgBean = abroadShortMsgService.getShortMsgBean(loginUser.getId(), null, type, id);
        modelMap.put("shortMsgBean", shortMsgBean);

        return "abroad/shortMsg_view";
    }

    @RequiresPermissions("ShortMsg:send")
    @RequestMapping(value = "/shortMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsg(String type, Integer id, HttpServletRequest request) {

        ShortMsgBean shortMsgBean = abroadShortMsgService.getShortMsgBean(ShiroHelper.getCurrentUserId(), null, type, id);

        if(shortMsgService.send(shortMsgBean, IpUtils.getRealIp(request))){

            logger.info(addLog(LogConstants.LOG_ADMIN, "发送信息：%s", shortMsgBean.getContent()));
            return success(FormUtils.SUCCESS);
        }

        return failed("发送失败（接口错误或设定了禁止发送）");
    }

    // 批量发送证件信息
    @RequiresPermissions("ShortMsg:send")
    @RequestMapping(value = "/shortMsg_batch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsg_batch(String type, Integer[] ids, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        List<SysUserView> failedUsers = new ArrayList<>();

        for (Integer id : ids) {

            ShortMsgBean shortMsgBean = abroadShortMsgService.getShortMsgBean(userId, null, type, id);
            if(shortMsgService.send(shortMsgBean, IpUtils.getRealIp(request))){
                logger.info(addLog(LogConstants.LOG_ADMIN, "发送信息：%s", shortMsgBean.getContent()));
            }else{
                Integer receiver = shortMsgBean.getReceiver();
                failedUsers.add(CmTag.getUserById(receiver));
            }
        }

        Map<String, Object> resultMap = success();
        resultMap.put("failedUsers", failedUsers);

        return resultMap;
    }
}
