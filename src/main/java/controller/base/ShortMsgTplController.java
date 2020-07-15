package controller.base;

import bean.ShortMsgBean;
import controller.BaseController;
import domain.base.ShortMsg;
import domain.base.ShortMsgExample;
import domain.base.ShortMsgTpl;
import domain.base.ShortMsgTplExample;
import domain.base.ShortMsgTplExample.Criteria;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysRole;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.*;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ShortMsgTplController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 定向消息发送记录
    @RequiresPermissions("shortMsgTpl:list")
    @RequestMapping("/shortMsgTpl_sendList_data")
    @ResponseBody
    public void shortMsgTpl_sendList_data(HttpServletResponse response,
                                          Integer relateId,
                                          Integer receiverId,
                                          String mobile,
                                          String relateSn,
                                          String content,
                                          @RequestDateRange DateRange _sendTime,
                                          Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ShortMsgExample example = new ShortMsgExample();
        ShortMsgExample.Criteria criteria = example.createCriteria()
                .andRelateTypeEqualTo(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL);

        if(!ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_SUPER)) {
            criteria.andSenderIdEqualTo(ShiroHelper.getCurrentUserId());
        }

        example.setOrderByClause("id desc");

        if (relateId != null) {
            criteria.andRelateIdEqualTo(relateId);
        }
        if (receiverId != null) {
            criteria.andReceiverIdEqualTo(receiverId);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike(SqlUtils.like(mobile));
        }
        if (StringUtils.isNotBlank(relateSn)) {
            criteria.andRelateSnEqualTo(relateSn);
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.like(content));
        }
        if (_sendTime.getStart()!=null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(_sendTime.getStart());
        }

        if (_sendTime.getEnd()!=null) {
            criteria.andCreateTimeLessThanOrEqualTo(_sendTime.getEnd());
        }

        long count = shortMsgMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("shortMsgTpl:list")
    @RequestMapping("/shortMsgTpl")
    public String shortMsgTpl(@RequestParam(defaultValue = "1") Integer cls, Integer relateId, Integer receiverId, ModelMap modelMap) {

        if(relateId!=null){
            modelMap.put("relateTpl", shortMsgTplMapper.selectByPrimaryKey(relateId));
        }

        if(receiverId!=null) {
            modelMap.put("receiver", sysUserService.findById(receiverId));
        }

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
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.like(content));
        }

        int count = (int) shortMsgTplMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(shortMsgTpl.class, shortMsgTplMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("shortMsgTpl:edit")
    @RequestMapping(value = "/shortMsgTpl_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsgTpl_au(ShortMsgTpl record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setContent(HtmlUtils.htmlUnescape(record.getContent()));

        if (id == null) {

            record.setAddUserId(ShiroHelper.getCurrentUserId());
            record.setCreateTime(new Date());
            record.setIp(ContextHelper.getRealIp());
            shortMsgTplService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加定向消息模板：%s", record.getName()));
        } else {

            shortMsgTplService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新定向消息模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsgTpl:edit")
    @RequestMapping("/shortMsgTpl_au")
    public String shortMsgTpl_au(Integer id, ModelMap modelMap) {

        List<String> roleList = Arrays.asList(
                RoleConstants.ROLE_ADMIN,
                RoleConstants.ROLE_PARTYADMIN,
                RoleConstants.ROLE_BRANCHADMIN,
                RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_CADREADMIN
        );
        if (ShiroHelper.lackRole(RoleConstants.ROLE_ADMIN)) {
            ShiroUser shiroUser = ShiroHelper.getShiroUser();
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

    // 发送消息
    @RequiresPermissions("shortMsgTpl:send")
    @RequestMapping(value = "/shortMsgTpl_send", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsgTpl_send(int tplId,
                                   Integer receiverId,
                                   String mobile,
                                   String wxTitle,
                                   String content,
                                   String type,
                                   @RequestParam(value="userIds[]", required = false) List<Integer> userIds,
                                   HttpServletRequest request) {

        ShortMsgTpl tpl = shortMsgTplMapper.selectByPrimaryKey(tplId);

        if(tpl.getType()== ContentTplConstants.CONTENT_TPL_TYPE_MSG
                && !StringUtils.equals(type, "batch")) {
            if (!CmTag.validMobile(mobile)) {
                return failed("手机号码有误");
            }
        }
        content = HtmlUtils.htmlUnescape(content);
        if(StringUtils.isBlank(content)){
            return failed("发送内容不能为空");
        }

        Integer roleId = tpl.getRoleId();
        SysRole sysRole = null;
        if (roleId != null) {
            sysRole = sysRoleService.findAll().get(roleId);
        }
        ShortMsgBean bean = new ShortMsgBean();
        shortMsgService.initShortMsgBeanParams(bean, tpl);
        bean.setWxTitle(wxTitle);

        bean.setRelateId(tpl.getId());
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_MSG_TPL);
        bean.setReceiver(receiverId);
        bean.setSender(ShiroHelper.getCurrentUserId());
        bean.setContent(content);
        bean.setMobile(mobile);
        bean.setTypeStr((sysRole != null ? (sysRole.getName() + "-") : "") + tpl.getName());

        if(!StringUtils.equals(type, "batch")) {
            shortMsgService.send(bean, ContextHelper.getRealIp());
        }else{
            shortMsgService.sendBatch(bean, userIds, ContextHelper.getRealIp());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsgTpl:send")
    @RequestMapping("/shortMsgTpl_send")
    public String shortMsgTpl_send(Integer id, ModelMap modelMap) {

        ShortMsgTpl shortMsgTpl = shortMsgTplMapper.selectByPrimaryKey(id);
        modelMap.put("shortMsgTpl", shortMsgTpl);

        return "base/shortMsgTpl/shortMsgTpl_send";
    }

    @RequiresPermissions("shortMsgTpl:send")
    @RequestMapping("/shortMsgTpl_selectCadres_tree")
    @ResponseBody
    public Map shortMsgTpl_selectCadres_tree() throws IOException {

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria().andStatusIn(new ArrayList<>(AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET));
        criteria.andMobileIsNull();
        List<CadreView> cvs = cadreViewMapper.selectByExample(example);
        Set<Integer> disabledIdSet = new HashSet<>();
        for (CadreView cv : cvs) {
            disabledIdSet.add(cv.getUserId());
        }

        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET, null, disabledIdSet, false,
                true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("shortMsgTpl:del")
    @RequestMapping(value = "/shortMsgTpl_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            shortMsgTplService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除定向消息模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsgTpl:edit")
	@RequestMapping(value = "/shortMsgTpl_changeOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map do_shortMsgTpl_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

		shortMsgTplService.changeOrder(id, addNum);
		logger.info(addLog(LogConstants.LOG_ADMIN, "消息模板调序：%s,%s", id, addNum));
		return success(FormUtils.SUCCESS);
	}

	@RequestMapping("/shortMsgTpl_selects")
    @ResponseBody
    public Map shortMsgTpl_selects(Integer pageSize,Integer pageNo, String searchStr) throws IOException {


        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ShortMsgTplExample example = new ShortMsgTplExample();
        ShortMsgTplExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = shortMsgTplMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ShortMsgTpl> records = shortMsgTplMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if(null != records && records.size()>0){

            for(ShortMsgTpl record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getId() + "");
                option.put("text", record.getName());
                option.put("content", record.getContent());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);

        return resultMap;
    }
}
