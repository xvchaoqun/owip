package controller.base;

import controller.BaseController;
import domain.base.ShortMsgReceiver;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
public class ShortMsgReceiverController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("contentTpl:edit")
    @RequestMapping(value = "/shortMsgReceiver_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsgReceiver_au(ShortMsgReceiver record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(SystemConstants.SHORT_MSG_RECEIVER_STATUS_NORMAL);
            record.setAddTime(new Date());
            record.setAddUserId(ShiroHelper.getCurrentUserId());
            shortMsgReceiverMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加短信接收人：%s", record.getId()));
        } else {

            shortMsgReceiverMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新短信接收人：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("contentTpl:edit")
    @RequestMapping(value = "/shortMsgReceiver_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsgReceiver_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            shortMsgReceiverMapper.deleteByPrimaryKey(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除短信接收人：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
}
