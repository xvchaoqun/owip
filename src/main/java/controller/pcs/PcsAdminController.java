package controller.pcs;

import controller.global.OpException;
import domain.party.Branch;
import domain.party.Party;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsAdminExample.Criteria;
import domain.pcs.PcsConfig;
import domain.sys.SysUserView;
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
import persistence.party.common.OwAdmin;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.PcsConstants.PCS_BRANCH_ADMIN;
import static sys.constants.PcsConstants.PCS_PARTY_ADMIN;

@Controller
@RequestMapping("/pcs")
public class PcsAdminController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsAdmin:list")
    @RequestMapping("/pcsAdmin")
    public String pcsAdmin(Integer partyId, Integer userId, ModelMap modelMap) {

        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if(partyId!=null){
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        return "pcs/pcsAdmin/pcsAdmin_page";
    }

    @RequiresPermissions("pcsAdmin:list")
    @RequestMapping("/pcsAdmin_data")
    public void pcsAdmin_data(HttpServletResponse response,
                              Integer partyId,
                              Integer userId,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer[] ids, // 导出的记录
                              Integer pageSize, Integer pageNo) throws IOException {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsAdminExample example = new PcsAdminExample();
        Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andCategoryEqualTo(PCS_PARTY_ADMIN);
        example.setOrderByClause("party_id asc");

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsAdmin_export(example, response);
            return;
        }*/

        long count = pcsAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsAdmin> records = pcsAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsAdmin.class, pcsAdminMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_sync() {

        /*pcsAdminService.syncCurrentPcsAdmin();
        logger.info(addLog(LogConstants.LOG_PCS, "同步党代会二级党组织管理员"));*/

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping("/pcsAdmin_au")
    public String pcsAdmin_au(Integer id, ModelMap modelMap) {

        if(id!=null) {
            PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);
            modelMap.put("pcsAdmin", pcsAdmin);
            SysUserView sysUser = sysUserService.findById(pcsAdmin.getUserId());
            modelMap.put("sysUser", sysUser);
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("party", partyMap.get(pcsAdmin.getPartyId()));

        }

        return "pcs/pcsAdmin/pcsAdmin_au";
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_add(PcsAdmin record, HttpServletRequest request) {

        if(StringUtils.isNotBlank(record.getMobile()) && !CmTag.validMobile(record.getMobile())){
            return failed("手机号码有误："+ record.getMobile());
        }

        List<OwAdmin> owAdmins = partyAdminService.getOwAdmins(record.getUserId());
        if(owAdmins.size()==0) {
            throw new OpException("该账号不是党代会管理员");
        }

        OwAdmin owAdmin = owAdmins.get(0); // 按二级党组织顺序仅读取管理的第一个二级党组织
        int partyId = owAdmin.getPartyId();

        record.setPartyId(partyId);

        pcsAdminService.addOrUpdate(record);
        logger.info(addLog(LogConstants.LOG_PCS, "添加/修改党代会二级党组织管理员补充信息：%s-%s",
                JSONUtils.toString(record, false), record.getMobile()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping("/pcsAdmin_add")
    public String pcsAdmin_add(ModelMap modelMap) {

        return "pcs/pcsAdmin/pcsAdmin_add";
    }

    // 未报送短信
    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_msg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_msg(byte type, // type=1 两委委员 type=2 党代表
                               byte stage,
                               String mobile,
                               String msg,
                               HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile) && !CmTag.validMobile(mobile)){
            return failed("手机号码有误："+ mobile);
        }
        msg = HtmlUtils.htmlUnescape(msg);
        Map<String, Integer> result = pcsAdminService.sendMsg(type, stage, mobile, msg);
        logger.info(addLog(LogConstants.LOG_PCS, "发送信息给二级党组织管理员：%s-%s", msg, mobile));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_msg2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_msg2(
                               String mobile,
                               String msg,
                               HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile) && !CmTag.validMobile(mobile)){
            return failed("手机号码有误："+ mobile);
        }
        msg = HtmlUtils.htmlUnescape(msg);
        Map<String, Integer> result = pcsAdminService.sendMsg2( mobile, msg);
        logger.info(addLog(LogConstants.LOG_PCS, "两委委员-下发名单通知，发送给全部的二级党组织管理员：%s-%s", msg, mobile));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_msg3", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_msg3(Integer partyId,
                               String mobile,
                               String msg,
                               HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile) && !CmTag.validMobile(mobile)){
            return failed("手机号码有误："+ mobile);
        }
        if(partyId==null&&StringUtils.isBlank(mobile)){
            return failed("参数有误");
        }
        msg = HtmlUtils.htmlUnescape(msg);
        Map<String, Integer> result = pcsAdminService.sendMsg3(partyId, mobile, msg);
        logger.info(addLog(LogConstants.LOG_PCS, "党代表给单个二级党组织的所有管理员发送审核通知，分为审核通过/审核不通过：%s-%s", msg, mobile));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping("/pcsAdmin_msg")
    public String pcsAdmin_msg(@RequestParam(required = false, defaultValue = "1") byte cls,
                               Integer partyId, ModelMap modelMap) {

        if(cls==1) {
            // 发送给还未报送的两委或党代表的二级党组织管理员
            return "pcs/pcsAdmin/pcsAdmin_msg";
        }else if(cls==2){
            // 两委委员-下发名单信息通知，发送给全部的二级党组织管理员
            return "pcs/pcsAdmin/pcsAdmin_msg2";
        }else if(cls==3){
            // 党代表给单个二级党组织的所有管理员发送审核通知，分为审核通过/审核不通过
            if(partyId!=null){
                modelMap.put("party", partyService.findAll().get(partyId));
            }
            return "pcs/pcsAdmin/pcsAdmin_msg3";
        }
        return null;
    }

    @RequiresPermissions("pcsAdmin:del")
    @RequestMapping(value = "/pcsAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsAdminService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除党代会二级党组织管理员信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsBranch:admin")
    @RequestMapping("/pcs_admin")
    public String pcs_admin(Integer partyId,Integer branchId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (partyId != null || branchId != null) {
            if (null == pageSize) {
                pageSize = 5;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            PcsAdminExample example = new PcsAdminExample();
            Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andCategoryEqualTo(PCS_BRANCH_ADMIN);
            if (partyId != null) {
                criteria.andPartyIdEqualTo(partyId);
            }
            if (branchId != null) {
                criteria.andBranchIdEqualTo(branchId);
            }
            modelMap.put("pcsBranch", pcsBranchService.get(configId,partyId,branchId));
            long count = pcsAdminMapper.countByExample(example);

            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            List<PcsAdmin> records = pcsAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("pcsAdmins", records);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (partyId != null) {
                searchStr += "&partyId=" + partyId;
            }
            if (branchId != null) {
                searchStr += "&branchId=" + branchId;
            }
            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }
        return "pcs/pcsAdmin/pcs_admin";
    }

    @RequiresPermissions("pcsPartyList:admin")
    @RequestMapping(value = "/pcs_admin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map pcs_admin_au(PcsAdmin record, HttpServletRequest request) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();


        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();

        PartyHelper.checkAuth(partyId, branchId);
        if(pcsAdminService.idDuplicate(record.getId(),record.getUserId(),partyId)){
            throw new OpException("添加重复，已经是其他支部管理员。");
        }

        SysUserView sysUser = sysUserService.findById(record.getUserId());

        // 没有"党代会-支部管理员"角色，则添加
        if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_PCS_BRANCH)) {
            sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_PCS_BRANCH);
        }

        record.setConfigId(configId);
        record.setCategory(PCS_BRANCH_ADMIN);
        pcsAdminMapper.insertSelective(record);
        /*logger.info(addLog(LogConstants.LOG_PARTY, "添加党支部管理员：%s， %s", uv.getCode(), branch.getName()));*/


        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPartyList:admin")
    @RequestMapping(value = "/pcs_admin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map pcs_admin_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        if (id != null) {
            PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);
            if (pcsAdmin != null) {
                if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
                    if (pcsAdmin.getUserId().intValue() == loginUser.getId()) {
                        return failed("不能删除自己");
                    }
                }
                SysUserView uv = sysUserService.findById(pcsAdmin.getUserId());
                Party party = null;
                Integer partyId = pcsAdmin.getPartyId();
                if (partyId != null) {

                    party = partyService.findAll().get(partyId);
                    PartyHelper.checkAuth(partyId);
                }
                Branch branch = null;
                Integer branchId = pcsAdmin.getBranchId();
                if (branchId != null) {

                    branch = branchService.findAll().get(branchId);
                    PartyHelper.checkAuth(branch.getPartyId(), branchId);
                }

                pcsAdminMapper.deleteByPrimaryKey(id);
                logger.info(addLog(LogConstants.LOG_PARTY, "删除党组织管理员：%s, %s%s"
                        , uv.getCode(), party == null ? "" : party.getName(), branch == null ? "" : branch.getName()));
            }
        }
        return success(FormUtils.SUCCESS);
    }
}
