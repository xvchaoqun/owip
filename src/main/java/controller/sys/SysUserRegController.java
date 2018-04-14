package controller.sys;

import controller.BaseController;
import controller.global.OpException;
import domain.party.Party;
import domain.sys.SysUserReg;
import domain.sys.SysUserRegExample;
import domain.sys.SysUserRegExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.member.ApplyApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SysUserRegController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<SysUserReg> checkVerityAuth2(int id){
        SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(sysUserReg, sysUserReg.getPartyId());
    }

    @RequiresPermissions("sysUserReg:list")
    @RequestMapping("/sysUserReg")
    public String sysUserReg(@RequestParam(defaultValue = "1")Integer cls,
                                  Integer userId,
                                  Integer partyId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", sysUserRegService.count(null));

        return "sys/sysUserReg/sysUserReg_page";
    }

    @RequiresPermissions("sysUserReg:list")
    @RequestMapping("/sysUserReg_data")
    public void sysUserReg_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                    Integer userId,
                                    String username,
                                    Integer partyId,
                                    String idcard,
                                     Byte type,
                                    String realname,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysUserRegExample example = new SysUserRegExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if(partyId!=null)
            criteria.andPartyIdEqualTo(partyId);

        criteria.addPermits(loginUserService.adminPartyIdList());

        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike("%" + realname + "%");
        }
        if(StringUtils.isNotBlank(idcard)){
            criteria.andIdcardLike("%" + idcard + "%");
        }

        if(cls==1){
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);
        }else if(cls==2){
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_DENY);
        }else {
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_PASS);
        }

        int count = sysUserRegMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserReg> sysUserRegs = sysUserRegMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", sysUserRegs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN,RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("sysUserReg:list")
    @RequestMapping("/sysUserReg_approval")
    public String sysUserReg_approval(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        SysUserReg currentSysUserReg = null;
        if (id != null) {
            currentSysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
            if (currentSysUserReg.getStatus() != SystemConstants.USER_REG_STATUS_APPLY)
                currentSysUserReg = null;
        } else {
            currentSysUserReg = sysUserRegService.next(null);
        }
        if (currentSysUserReg == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("sysUserReg", currentSysUserReg);

        // 是否是当前记录的管理员
        modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentSysUserReg.getPartyId()));

        // 读取总数
        modelMap.put("count", sysUserRegService.count(null));
        // 下一条记录
        modelMap.put("next", sysUserRegService.next(currentSysUserReg));
        // 上一条记录
        modelMap.put("last", sysUserRegService.last(currentSysUserReg));

        return "sys/sysUserReg/sysUserReg_approval";
    }

    @RequiresPermissions("sysUserReg:update")
    @RequestMapping("/sysUserReg_deny")
    public String sysUserReg_deny(Integer id, ModelMap modelMap) {

        SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
        modelMap.put("sysUserReg", sysUserReg);
        Integer userId = sysUserReg.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "sys/sysUserReg/sysUserReg_deny";
    }

    @RequiresPermissions("sysUserReg:update")
    @RequestMapping(value = "/sysUserReg_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserReg_deny(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                  Integer id,
                                  String reason) {

        VerifyAuth<SysUserReg> verifyAuth = checkVerityAuth2(id);
        SysUserReg sysUserReg = verifyAuth.entity;

        int loginUserId = loginUser.getId();
        int userId = sysUserReg.getUserId();

        sysUserRegService.deny(sysUserReg.getId());
        logger.info(addLog(LogConstants.LOG_PARTY, "拒绝用户注册申请：%s", id));

        ApplyApprovalLogService applyApprovalLogService = CmTag.getBean(ApplyApprovalLogService.class);
        applyApprovalLogService.add(sysUserReg.getId(),
                sysUserReg.getPartyId(), null, userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "分党委党总支直属党支部审核" , (byte) 0, reason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserReg:update")
    @RequestMapping(value = "/sysUserReg_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserReg_check(@CurrentUser SysUserView loginUser,HttpServletRequest request,
                                   Integer id) {

        VerifyAuth<SysUserReg> verifyAuth = checkVerityAuth2(id);
        SysUserReg sysUserReg = verifyAuth.entity;

        sysUserRegService.pass(sysUserReg.getId());
        logger.info(addLog(LogConstants.LOG_PARTY, "用户注册-分党委审核：%s", id));

        int loginUserId = loginUser.getId();
        int userId = sysUserReg.getUserId();

        ApplyApprovalLogService applyApprovalLogService = CmTag.getBean(ApplyApprovalLogService.class);
        applyApprovalLogService.add(sysUserReg.getId(),
                sysUserReg.getPartyId(), null, userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "分党委党总支直属党支部审核", (byte) 1, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserReg:edit")
    @RequestMapping(value = "/sysUserReg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserReg_au(SysUserReg record, HttpServletRequest request) {

        Assert.isTrue(record.getId()!=null, "id is null");

        if (sysUserRegService.usernameDuplicate(record.getId(), record.getUserId(), record.getUsername())
                || sysUserService.idDuplicate(record.getUserId(), record.getUsername(), record.getCode())) {
            return failed("用户名或学工号已被注册。");
        }
        if(sysUserRegService.idcardDuplicate(record.getId(), record.getUserId(), record.getIdcard())){
            return failed("身份证已被注册。");
        }

        SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(record.getId());
        Integer partyId = sysUserReg.getPartyId();
        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin) throw new UnauthorizedException();
        }

        sysUserRegService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新申请用户注册：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserReg:edit")
    @RequestMapping("/sysUserReg_au")
    public String sysUserReg_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
            modelMap.put("sysUserReg", sysUserReg);
        }
        return "sys/sysUserReg/sysUserReg_au";
    }

    @RequiresPermissions("sysUserReg:changepw")
    @RequestMapping(value = "/sysUserReg_changepw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserReg_changepw(Integer id, String password, HttpServletRequest request) {

        if(!FormUtils.match(PropertiesUtils.getString("passwd.regex"), password)){
            return failed("密码由6-16位的字母、下划线和数字组成");
        }

        SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
        Integer partyId = sysUserReg.getPartyId();
        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin) throw new UnauthorizedException();
        }

        sysUserRegService.changepw(id ,password);

        logger.info(addLog(LogConstants.LOG_ADMIN, "更新注册用户%s登录密码", sysUserReg.getUsername()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserReg:changepw")
    @RequestMapping("/sysUserReg_changepw")
    public String sysUserReg_changepw(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
            modelMap.put("sysUserReg", sysUserReg);
        }
        return "sys/sysUserReg/sysUserReg_changepw";
    }

    /*@RequiresPermissions("sysUserReg:del")
    @RequestMapping(value = "/sysUserReg_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserReg_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            sysUserRegService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除用户注册：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserReg:del")
    @RequestMapping(value = "/sysUserReg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            sysUserRegService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除用户注册：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
