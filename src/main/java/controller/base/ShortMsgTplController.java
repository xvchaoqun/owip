package controller.base;

import bean.ShortMsgBean;
import controller.BaseController;
import domain.base.ShortMsg;
import domain.base.ShortMsgExample;
import domain.base.ShortMsgTpl;
import domain.base.ShortMsgTplExample;
import domain.base.ShortMsgTplExample.Criteria;
import domain.sys.SysRole;
import mixin.ShortMsgMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ShortMsgTplController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("shortMsgTpl:list")
    @RequestMapping("/shortMsgTpl")
    public String shortMsgTpl() {

        return "index";
    }


    @RequiresPermissions("shortMsgTpl:list")
    @RequestMapping("/shortMsgTpl_sendList_data")
    @ResponseBody
    public void shortMsgTpl_sendList_data(HttpServletResponse response,
                                          Integer receiverId,
                                          String mobile,
                                          String content,
                                          String _sendTime,
                                          Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ShortMsgExample example = new ShortMsgExample();
        ShortMsgExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (receiverId != null) {
            criteria.andReceiverIdEqualTo(receiverId);
        }
        criteria.andSenderIdEqualTo(ShiroHelper.getCurrentUserId());
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike("%" + mobile + "%");
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        if (StringUtils.isNotBlank(_sendTime)) {
            String start = _sendTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _sendTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andCreateTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andCreateTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        int count = shortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ShortMsg> shortMsgs = shortMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", shortMsgs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ShortMsg.class, ShortMsgMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("shortMsgTpl:list")
    @RequestMapping("/shortMsgTpl_page")
    public String shortMsgTpl_page(@RequestParam(defaultValue = "1") Integer cls, Integer receiverId, ModelMap modelMap) {

        if(receiverId!=null)
            modelMap.put("receiver", sysUserService.findById(receiverId));

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "base/shortMsgTpl/shortMsgTpl_sendList_page";
        }

        return "base/shortMsgTpl/shortMsgTpl_page";
    }

    @RequiresPermissions("shortMsgTpl:list")
    @RequestMapping("/shortMsgTpl_data")
    public void shortMsgTpl_data(HttpServletResponse response,
                                 String content,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ShortMsgTplExample example = new ShortMsgTplExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike("%" + content + "%");
        }

        int count = shortMsgTplMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ShortMsgTpl> records = shortMsgTplMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(shortMsgTpl.class, shortMsgTplMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("shortMsgTpl:edit")
    @RequestMapping(value = "/shortMsgTpl_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsgTpl_au(ShortMsgTpl record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setAddUserId(ShiroHelper.getCurrentUserId());
            record.setCreateTime(new Date());
            record.setIp(ContextHelper.getRealIp());
            shortMsgTplService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加定向短信模板：%s", record.getId()));
        } else {

            shortMsgTplService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新定向短信模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsgTpl:edit")
    @RequestMapping("/shortMsgTpl_au")
    public String shortMsgTpl_au(Integer id, ModelMap modelMap) {

        List<String> roleList = Arrays.asList(
                SystemConstants.ROLE_ADMIN,
                SystemConstants.ROLE_PARTYADMIN,
                SystemConstants.ROLE_BRANCHADMIN,
                SystemConstants.ROLE_ODADMIN,
                SystemConstants.ROLE_CADREADMIN
        );
        if (ShiroHelper.lackRole(SystemConstants.ROLE_ADMIN)) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Set<String> roles = shiroUser.getRoles();
            roles.retainAll(roleList);

            roleList = new ArrayList<>(roles);
        }
        List<SysRole> sysRoles = new ArrayList<>();
        for (String role : roleList) {
            sysRoles.add(sysRoleService.getByRole(role));
        }
        modelMap.put("sysRoles", sysRoles);

        if (id != null) {
            ShortMsgTpl shortMsgTpl = shortMsgTplMapper.selectByPrimaryKey(id);
            modelMap.put("shortMsgTpl", shortMsgTpl);
        }
        return "base/shortMsgTpl/shortMsgTpl_au";
    }

    // 发送短信
    @RequiresPermissions("shortMsgTpl:send")
    @RequestMapping(value = "/shortMsgTpl_send", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsgTpl_send(int tplId, Integer receiverId, String mobile, HttpServletRequest request) {

        if (!FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)) {
            throw new RuntimeException("手机号码有误");
        }

        ShortMsgTpl shortMsgTpl = shortMsgTplMapper.selectByPrimaryKey(tplId);
        Integer roleId = shortMsgTpl.getRoleId();
        SysRole sysRole = null;
        if (roleId != null) {
            sysRole = sysRoleService.findAll().get(roleId);
        }
        ShortMsgBean shortMsg = new ShortMsgBean();
        shortMsg.setReceiver(receiverId);
        shortMsg.setSender(ShiroHelper.getCurrentUserId());
        shortMsg.setContent(shortMsgTpl.getContent());
        shortMsg.setMobile(mobile);
        shortMsg.setType((sysRole != null ? (sysRole.getDescription() + "-") : "") + shortMsgTpl.getName());

        shortMsgService.send(shortMsg, ContextHelper.getRealIp());
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsgTpl:send")
    @RequestMapping("/shortMsgTpl_send")
    public String shortMsgTpl_send(Integer id, ModelMap modelMap) {

        ShortMsgTpl shortMsgTpl = shortMsgTplMapper.selectByPrimaryKey(id);
        modelMap.put("shortMsgTpl", shortMsgTpl);

        return "base/shortMsgTpl/shortMsgTpl_send";
    }

    @RequiresPermissions("shortMsgTpl:del")
    @RequestMapping(value = "/shortMsgTpl_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            shortMsgTplService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除定向短信模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
