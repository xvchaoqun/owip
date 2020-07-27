package controller.sys;

import controller.BaseController;
import domain.sys.SysConfigLoginMsg;
import domain.sys.SysConfigLoginMsgExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SysConfigLoginMsgController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysConfig:list")
    @RequestMapping("/sysConfigLoginMsg")
    public String sysConfigLoginMsg(ModelMap modelMap) {

        SysConfigLoginMsgExample example = new SysConfigLoginMsgExample();
        example.setOrderByClause("create_time desc");
        List<SysConfigLoginMsg> sysConfigLoginMsgs = sysConfigLoginMsgMapper.selectByExample(example);
        modelMap.put("sysConfigLoginMsgs", sysConfigLoginMsgs);

        return "sys/sysConfig/sysConfigLoginMsg";
    }

    @RequiresPermissions("sysConfig:list")
    @RequestMapping("/sysConfigLoginMsg_au")
    public String sysConfigLoginMsg_au(Integer id, ModelMap modelMap) {

        if(id!=null){
            SysConfigLoginMsg sysConfigLoginMsg = sysConfigLoginMsgMapper.selectByPrimaryKey(id);
            modelMap.put("sysConfigLoginMsg", sysConfigLoginMsg);
        }
        return "sys/sysConfig/sysConfigLoginMsg_au";
    }

    @RequiresPermissions("sysConfig:edit")
    @RequestMapping(value = "/sysConfigLoginMsg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysConfigLoginMsg_au(SysConfigLoginMsg record) {

        record.setCreateTime(new Date());
        record.setLoginMsg(record.getLoginMsg());

        if(record.getId()==null) {
            sysConfigLoginMsgMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "保存常用登录公告：%s", record.getId()));
        }else{
            sysConfigLoginMsgMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新常用登录公告：%s", record.getId()));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysConfig:edit")
    @RequestMapping(value = "/sysConfigLoginMsg_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysConfigLoginMsg_del(int id) {

        sysConfigLoginMsgMapper.deleteByPrimaryKey(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "删除常用登录公告：%s", id));

        return success(FormUtils.SUCCESS);
    }
}
