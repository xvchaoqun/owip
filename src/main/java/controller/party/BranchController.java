package controller.party;

import controller.BaseController;
import domain.party.*;
import domain.party.BranchExample.Criteria;
import domain.sys.MetaType;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.BranchMixin;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ExportHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class BranchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 基本信息
    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_base")
    public String branch_base(Integer id, ModelMap modelMap) {

        Branch branch = branchMapper.selectByPrimaryKey(id);
        modelMap.put("branch", branch);
        BranchMemberGroup presentGroup = branchMemberGroupService.getPresentGroup(id);
        if(presentGroup!=null) {
            BranchMemberExample example = new BranchMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<BranchMember> BranchMembers = branchMemberMapper.selectByExample(example);
            modelMap.put("branchMembers", BranchMembers);
        }
        modelMap.put("adminIds", commonMapper.findBranchAdmin(id));

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_member_type"));
        return "party/branch/branch_base";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_view")
    public String branch_show_page(HttpServletResponse response,  ModelMap modelMap) {

        return "party/branch/branch_view";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch")
    public String branch() {

        return "index";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_page")
    public String branch_page(Integer partyId, ModelMap modelMap) {

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));

        if(partyId!=null) {
            Party party = partyMapper.selectByPrimaryKey(partyId);
            modelMap.put("party", party);
        }

        return "party/branch/branch_page";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_data")
    public void branch_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_branch") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String code,
                                    String name,
                                    Integer partyId,
                                    Integer typeId,
                                    Integer unitTypeId,
                                    String _foundTime,
                                    Boolean isStaff,
                                    Boolean isPrefessional,
                                    Boolean isBaseTeam,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 String exportType,
                            @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchExample example = new BranchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        //===========权限（只有分党委管理员，才可以管理党支部）
        //criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {
            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            criteria.andPartyIdIn(partyIdList);
        }

        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (unitTypeId!=null) {
            criteria.andUnitTypeIdEqualTo(unitTypeId);
        }
        if(isStaff!=null){
            criteria.andIsStaffEqualTo(isStaff);
        }
        if(isPrefessional!=null){
            criteria.andIsPrefessionalEqualTo(isPrefessional);
        }
        if(isBaseTeam!=null){
            criteria.andIsBaseTeamEqualTo(isBaseTeam);
        }
        if(StringUtils.isNotBlank(_foundTime)) {
            String foundTimeStart = _foundTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String foundTimeEnd = _foundTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(foundTimeStart)) {
                criteria.andFoundTimeGreaterThanOrEqualTo(DateUtils.parseDate(foundTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(foundTimeEnd)) {
                criteria.andFoundTimeLessThanOrEqualTo(DateUtils.parseDate(foundTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));

            if(StringUtils.equals(exportType, "secretary")){ // 导出支部书记
                branch_secretary_export(example, response);
            }else {
                branch_export(example, response);
            }
            return;
        }

        int count = branchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<Branch> Branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Branchs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(Branch.class, BranchMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("branch:edit")
    @RequestMapping(value = "/branch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_au(@CurrentUser SysUserView loginUser, Branch record, String _foundTime, HttpServletRequest request) {

        Integer id = record.getId();

        // 权限控制
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {
            // 要求是分党委管理员
            Integer partyId = record.getPartyId();
            if (id != null) {
                Branch branch = branchService.findAll().get(id);
                partyId = branch.getPartyId();
            }
            if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        if(StringUtils.isNotBlank(_foundTime)){
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsEnterpriseBig((record.getIsEnterpriseBig()==null)?false:record.getIsEnterpriseBig());
        record.setIsEnterpriseNationalized((record.getIsEnterpriseNationalized() == null) ? false : record.getIsEnterpriseNationalized());
        record.setIsUnion((record.getIsUnion() == null) ? false : record.getIsUnion());
        record.setIsStaff((record.getIsStaff() == null) ? false : record.getIsStaff());
        record.setIsPrefessional((record.getIsPrefessional() == null) ? false : record.getIsPrefessional());
        record.setIsBaseTeam((record.getIsBaseTeam() == null) ? false : record.getIsBaseTeam());
        if(!record.getIsStaff()){
            record.setIsPrefessional(false);
        }
        if(!record.getIsPrefessional()){
            record.setIsBaseTeam(false);
        }

        if (id == null) {
            record.setCreateTime(new Date());
            branchService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加党支部：%s", record.getId()));
        } else {
            record.setCode(null); // 不修改编号
            branchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新党支部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:edit")
    @RequestMapping("/branch_au")
    public String branch_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Branch branch = branchMapper.selectByPrimaryKey(id);
            modelMap.put("branch", branch);

            if(branch!=null) {
                Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                modelMap.put("party", party);
            }
        }

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));

        return "party/branch/branch_au";
    }

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        // 权限控制
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {
            // 要求是分党委管理员
            Branch branch = branchService.findAll().get(id);
            int partyId = branch.getPartyId();
            if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        if (id != null) {
            branchService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除党支部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            branchService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除党支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:changeOrder")
    @RequestMapping(value = "/branch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_changeOrder(@CurrentUser SysUserView loginUser, Integer id, Integer addNum, HttpServletRequest request) {

        // 权限控制
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {
            // 要求是分党委管理员
            Branch branch = branchService.findAll().get(id);
            int partyId = branch.getPartyId();
            if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        branchService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_OW, "党支部调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void branch_export(BranchExample example, HttpServletResponse response) {

        List<Branch> records = branchMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号","名称","简称","所属分党委","类别","单位属性","联系电话","传真","邮箱","成立时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Branch record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    record.getShortName(),
                    record.getPartyId()==null?"":partyService.findAll().get(record.getPartyId()).getName(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getUnitTypeId()),
                    record.getPhone(),
                    record.getFax(),
                    record.getEmail(),
                    DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "党支部_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 导出支部书记
    public void branch_secretary_export(BranchExample example, HttpServletResponse response) {

        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        List<Branch> records = branchMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名","工号","所在单位","联系电话","所属分党委","所属党支部","党支部类别"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Branch record = records.get(i);
            List<BranchMember> branchSecretary = commonMapper.findBranchSecretary(secretaryType.getId(), record.getId());

            if(branchSecretary.size()>0) {
                Integer userId = branchSecretary.get(0).getUserId();
                SysUserView sysUser = sysUserService.findById(userId);
                String unit = sysUserService.getUnit(sysUser);
                String[] values = {
                        sysUser.getRealname(),
                        sysUser.getCode(),
                        StringUtils.trimToEmpty(unit),
                        sysUser.getMobile(),
                        record.getPartyId() == null ? "" : partyService.findAll().get(record.getPartyId()).getName(),
                        record.getName(),
                        metaTypeService.getName(record.getTypeId())
                };
                valuesList.add(values);
            }
        }
        String fileName = "党支部书记_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/branch_selects")
    @ResponseBody
    public Map branch_selects(Integer pageSize, Boolean auth, Integer pageNo, Integer partyId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchExample example = new BranchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(partyId==null)criteria.andIdIsNull(); // partyId肯定存在

        criteria.andPartyIdEqualTo(partyId);

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        //===========权限
        if(BooleanUtils.isTrue(auth)) {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                    && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

                List<Integer> partyIdList = loginUserService.adminPartyIdList();
                Set<Integer> partyIdSet = new HashSet<>();
                partyIdSet.addAll(partyIdList);
                if (!partyIdSet.contains(partyId)) { // 当前partyId不是管理员，则只能在管理的党支部中选择
                    List<Integer> branchIdList = loginUserService.adminBranchIdList();
                    if (branchIdList.size() > 0)
                        criteria.andIdIn(branchIdList);
                    else
                        criteria.andIdIsNull();
                }
            }
        }

        int count = branchMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Branch> branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != branchs && branchs.size()>0){

            for(Branch branch:branchs){

                Select2Option option = new Select2Option();
                option.setText(branch.getName());
                option.setId(branch.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
