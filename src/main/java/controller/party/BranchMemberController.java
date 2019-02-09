package controller.party;

import controller.BaseController;
import domain.base.MetaType;
import domain.party.*;
import domain.party.BranchMemberExample.Criteria;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.party.BranchExportService;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class BranchMemberController extends BaseController {

    @Autowired
    private BranchExportService branchExportService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("branchMember:list")
    @RequestMapping("/branchMember")
    public String branchMember(Integer groupId, ModelMap modelMap) {

        if (groupId != null) {
            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(groupId);
            modelMap.put("branchMemberGroup", branchMemberGroup);
        }

        return "party/branchMember/branchMember_page";
    }

    @RequiresPermissions("branchMember:list")
    @RequestMapping("/branchMember_data")
    public void branchMember_data(HttpServletResponse response,
                               Integer groupId,
                               Integer userId,
                               Integer typeId,
                               Boolean isAdmin,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchMemberViewExample example = new BranchMemberViewExample();
        BranchMemberViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_sort_order desc, branch_sort_order desc, sort_order desc");

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (groupId != null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (isAdmin != null) {
            criteria.andIsAdminEqualTo(isAdmin);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            branchMember_export(example, response);
            return;
        }

        int count = (int) branchMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchMemberView> records = branchMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_au(BranchMember record, HttpServletRequest request) {

        Integer id = record.getId();

        if (branchMemberService.idDuplicate(id, record.getGroupId(), record.getUserId(), record.getTypeId())) {
            return failed("添加重复【每个委员会的人员不可重复，并且书记只有一个】");
        }
        boolean autoAdmin = false;
        Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes("mc_branch_member_type");
        MetaType metaType = metaTypeMap.get(record.getTypeId());
        Boolean boolAttr = metaType.getBoolAttr();
        if (boolAttr != null && boolAttr) {
            autoAdmin = true;
        }
        if (id == null) {
            branchMemberService.insertSelective(record, autoAdmin);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加支部成员：%s", record.getId()));
        } else {

            branchMemberService.updateByPrimaryKeySelective(record, autoAdmin);
            logger.info(addLog(LogConstants.LOG_PARTY, "更新支部成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:edit")
    @RequestMapping("/branchMember_au")
    public String branchMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
            modelMap.put("branchMember", branchMember);
        }
        return "party/branchMember/branchMember_au";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map branchAdmin_del(@CurrentUser SysUserView loginUser, Integer userId, Integer branchId) {

        // 权限控制
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {
            // 要求是分党委管理员
            Branch branch = branchService.findAll().get(branchId);
            int partyId = branch.getPartyId();
            if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
                throw new UnauthorizedException();
            }

            if (userId.intValue() == loginUser.getId()) {
                return failed("不能删除自己");
            }
        }

        branchMemberService.delAdmin(userId, branchId);
        logger.info(addLog(LogConstants.LOG_PARTY, "删除支部管理员权限，userId=%s, branchId=%s", userId, branchId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:del")
    @RequestMapping(value = "/branchMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            branchMemberService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除支部成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:del")
    @RequestMapping(value = "/branchMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            branchMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量删除支部成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:changeOrder")
    @RequestMapping(value = "/branchMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "支部成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map branchMember_admin(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        if (id != null) {

            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

            Subject subject = SecurityUtils.getSubject();
            if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                    && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {
                if (branchMember.getUserId().intValue() == loginUser.getId()) {
                    return failed("不能删除自己");
                }
            }

            branchMemberAdminService.toggleAdmin(branchMember);

            String op = branchMember.getIsAdmin() ? "删除" : "添加";
            logger.info(addLog(LogConstants.LOG_PARTY, "%s党支部委员管理员权限，memberId=%s", op, id));
        }
        return success(FormUtils.SUCCESS);
    }

    public void branchMember_export(BranchMemberViewExample example, HttpServletResponse response) {

        SXSSFWorkbook wb = branchExportService.export(example);
        String fileName = CmTag.getSysConfig().getSchoolName()
                + "支部委员(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    @RequestMapping("/branchMember_selects")
    @ResponseBody
    public Map branchMember_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchMemberExample example = new BranchMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        int count = (int) branchMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchMember> branchMembers = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != branchMembers && branchMembers.size() > 0) {

            for (BranchMember branchMember : branchMembers) {

                Select2Option option = new Select2Option();
                //option.setText(branchMember.getName());
                option.setId(branchMember.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
