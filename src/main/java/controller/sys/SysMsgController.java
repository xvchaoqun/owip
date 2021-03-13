package controller.sys;

import controller.BaseController;
import domain.sys.SysMsg;
import domain.sys.SysMsgExample;
import domain.sys.SysMsgExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.sys.SysMsgMapper;
import service.sys.SysMsgService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class SysMsgController extends BaseController {

    @Autowired
    private SysMsgMapper sysMsgMapper;
    @Autowired
    private SysMsgService sysMsgService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysMsg:list")
    @RequestMapping("/sysMsg")
    public String sysMsg(@RequestParam(required = false, defaultValue = "1") Integer cls,
                         @RequestParam(required = false, defaultValue = "1") Integer page,
                         Integer userId,
                         Integer sendUserId,
                         ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }
        if (sendUserId != null) {
            modelMap.put("sendUser", CmTag.getUserById(sendUserId));
        }
        modelMap.put("page", page);
        modelMap.put("cls", cls);

        SysMsgExample acceptMsgs = new SysMsgExample();
        acceptMsgs.createCriteria().andUserIdEqualTo(ShiroHelper.getCurrentUserId());
        modelMap.put("acceptMsg", sysMsgMapper.countByExample(acceptMsgs));

        SysMsgExample sendMsgs = new SysMsgExample();
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
            sendMsgs.createCriteria().andSendUserIdEqualTo(ShiroHelper.getCurrentUserId());
        }
        modelMap.put("sendMsg", sysMsgMapper.countByExample(sendMsgs));

        return "sys/sysMsg/sysMsg_page";
    }

    @RequiresPermissions("sysMsg:list")
    @RequestMapping("/sysMsg_data")
    @ResponseBody
    public void sysMsg_data(HttpServletResponse response,
                            Integer userId,
                            Integer sendUserId,
                            String title,
                            String content,
                            Byte status,
                            @RequestParam(required = false, defaultValue = "1") Integer page,//1接收 2发送
                            @RequestParam(required = false, defaultValue = "1") Integer cls,
                            @RequestParam(required = false, defaultValue = "0") int export,
                            Integer[] ids, // 导出的记录
                            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysMsgExample example = new SysMsgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("send_time desc");

        if (cls == 2) {
            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
                if (page == 1) {
                    criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
                } else if (page == 2) {
                    criteria.andSendUserIdEqualTo(ShiroHelper.getCurrentUserId());
                }
            } else {
                if (page == 1) {
                    criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
                }
            }
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (sendUserId != null) {
            criteria.andSendUserIdEqualTo(sendUserId);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.trimLike(title));
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.trimLike(content));
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        long count = sysMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysMsg> records = sysMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("sysMsg:edit")
    @RequestMapping(value = "/sysMsg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysMsg_au(SysMsg record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            sysMsgService.insertSelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "添加系统提醒：{0}", record.getId()));
        } else {

            sysMsgService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "更新系统提醒：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysMsg:edit")
    @RequestMapping("/sysMsg_au")
    public String sysMsg_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysMsg sysMsg = sysMsgMapper.selectByPrimaryKey(id);
            modelMap.put("sysMsg", sysMsg);
            SysUserView sysUser = CmTag.getUserById(sysMsg.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "sys/sysMsg/sysMsg_au";
    }

    @RequiresPermissions("sysMsg:list")
    @RequestMapping(value = "/sysMsg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map sysMsg_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            sysMsgService.batchDel(ids);
            logger.info(log(LogConstants.LOG_PARTY, "批量删除未确认的系统提醒：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("sysMsg:list")
    @RequestMapping("/sysMsg_view")
    public String sysMsg_view(Integer id, Integer cls, ModelMap modelMap) {

        if (id != null) {
            SysMsg sysMsg = sysMsgMapper.selectByPrimaryKey(id);
            modelMap.put("sysMsg", sysMsg);

            if (cls == 2) {

                sysMsg.setStatus(SystemConstants.SYS_MSG_STATUS_CONFIRM);
                sysMsgService.updateByPrimaryKeySelective(sysMsg);
            }
        }

        modelMap.put("cls", cls);
        return "sys/sysMsg/sysMsg_view";
    }*/

    @RequiresPermissions("sysMsg:list")
    @RequestMapping(value = "/sysMsg_confirm", method = RequestMethod.POST)
    @ResponseBody
    public Map sysMsg_confirm(Integer[] ids) {

        if (ids != null && ids.length > 0) {
            sysMsgService.batchConfirm(ids);
            logger.info(addLog(LogConstants.LOG_USER, "批量确认提醒：%s", StringUtils.join(ids, ",")));
        }

        return success();
    }

    @RequiresPermissions("sysMsg:list")
    @RequestMapping(value = "/sysMsg_partyRemind", method = RequestMethod.POST)
    @ResponseBody
    public Map sysMsg_partyRemind(Integer[] ids) {

        if (ids != null && ids.length > 0) {
            sysMsgService.partyRemind(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量提醒领导班子换届：%s", StringUtils.join(ids, ",")));
        }

        return success();
    }

    @RequiresPermissions("sysMsg:list")
    @RequestMapping(value = "/sysMsg_branchRemind", method = RequestMethod.POST)
    @ResponseBody
    public Map sysMsg_branchRemind(Integer[] ids) {

        if (ids != null && ids.length > 0) {
            sysMsgService.branchRemind(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量提醒支部委员会换届：%s", StringUtils.join(ids, ",")));
        }

        return success();
    }
}
