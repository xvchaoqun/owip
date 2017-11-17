package controller.party;

import controller.BaseController;
import domain.base.MetaType;
import domain.ext.ExtJzg;
import domain.member.Member;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.BranchExample.Criteria;
import domain.party.BranchMember;
import domain.party.BranchMemberExample;
import domain.party.BranchMemberGroup;
import domain.party.BranchView;
import domain.party.BranchViewExample;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        if (presentGroup != null) {
            BranchMemberExample example = new BranchMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<BranchMember> BranchMembers = branchMemberMapper.selectByExample(example);
            modelMap.put("branchMembers", BranchMembers);
        }
        modelMap.put("adminIds", iPartyMapper.findBranchAdmin(id));

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_member_type"));
        return "party/branch/branch_base";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_view")
    public String branch_show_page(HttpServletResponse response, ModelMap modelMap) {

        return "party/branch/branch_view";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch")
    public String branch(Integer partyId,
                              @RequestParam(required = false, defaultValue = "1") Byte status,
                              ModelMap modelMap) {

        modelMap.put("status", status);

        if (partyId != null) {
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
                            @RequestParam(required = false, defaultValue = "1") Byte status,
                            String code,
                            String name,
                            Integer partyId,
                            Integer typeId,
                            Integer unitTypeId,
                            @RequestDateRange DateRange _foundTime,
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

        BranchViewExample example = new BranchViewExample();
        BranchViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andIsDeletedEqualTo(status == -1);

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (partyId != null) {
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

        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (unitTypeId != null) {
            criteria.andUnitTypeIdEqualTo(unitTypeId);
        }
        if (isStaff != null) {
            criteria.andIsStaffEqualTo(isStaff);
        }
        if (isPrefessional != null) {
            criteria.andIsPrefessionalEqualTo(isPrefessional);
        }
        if (isBaseTeam != null) {
            criteria.andIsBaseTeamEqualTo(isBaseTeam);
        }

        if (_foundTime.getStart()!=null) {
            criteria.andFoundTimeGreaterThanOrEqualTo(_foundTime.getStart());
        }

        if (_foundTime.getEnd()!=null) {
            criteria.andFoundTimeLessThanOrEqualTo(_foundTime.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            if (StringUtils.equals(exportType, "secretary")) { // 导出支部书记
                branch_secretary_export(example, response);
            } else {
                branch_export(example, response);
            }
            return;
        }

        int count = branchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchView> Branchs = branchViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Branchs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
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

        if (StringUtils.isNotBlank(_foundTime)) {
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsEnterpriseBig((record.getIsEnterpriseBig() == null) ? false : record.getIsEnterpriseBig());
        record.setIsEnterpriseNationalized((record.getIsEnterpriseNationalized() == null) ? false : record.getIsEnterpriseNationalized());
        record.setIsUnion((record.getIsUnion() == null) ? false : record.getIsUnion());
        record.setIsStaff((record.getIsStaff() == null) ? false : record.getIsStaff());
        record.setIsPrefessional((record.getIsPrefessional() == null) ? false : record.getIsPrefessional());
        record.setIsBaseTeam((record.getIsBaseTeam() == null) ? false : record.getIsBaseTeam());
        if (!record.getIsStaff()) {
            record.setIsPrefessional(false);
        }
        if (!record.getIsPrefessional()) {
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

            if (branch != null) {
                Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                modelMap.put("party", party);
            }
        }

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));

        return "party/branch/branch_au";
    }

    /*@RequiresPermissions("branch:del")
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
    }*/

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            branchService.batchDel(ids, isDeleted);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除党支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("branch:changeOrder")
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
    }*/

    @RequiresPermissions("branch:transfer")
    @RequestMapping(value = "/branch_batchTransfer")
    public String branch_batchTransfer(){

        return "party/branch/branch_batchTransfer";
    }

    //批量转移支部
    @RequiresPermissions("branch:transfer")
    @RequestMapping(value = "/branch_batchTransfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_batchTransfer(@RequestParam(value = "ids[]") Integer[] ids,
                                       int partyId,
                                       String remark) {

        branchService.batchTransfer(ids, partyId, remark);
        return success();
    }

    public void branch_export(BranchViewExample example, HttpServletResponse response) {

        List<BranchView> records = branchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号", "名称", "简称", "所属分党委", "类别",
                "党员总数", "在职教职工数量", "离退休党员数量", "学生数量", "委员会总数",
                "是否已设立现任委员会", "是否是教工党支部", "是否是专业教师党支部", "是否建立在团队",
                "单位属性", "联系电话", "传真", "邮箱", "成立时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchView record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    record.getShortName(),
                    record.getPartyId() == null ? "" : partyService.findAll().get(record.getPartyId()).getName(),
                    metaTypeService.getName(record.getTypeId()),
                    record.getMemberCount()==null?"0":record.getMemberCount()+"",
                    record.getTeacherMemberCount()==null?"0":record.getTeacherMemberCount()+"",
                    record.getRetireMemberCount()==null?"0":record.getRetireMemberCount()+"",
                    record.getStudentMemberCount()==null?"0":record.getStudentMemberCount()+"",
                    record.getGroupCount()==null?"0":record.getGroupCount()+"",
                    (record.getPresentGroupCount()!=null &&record.getPresentGroupCount() > 0) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsStaff()) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsPrefessional()) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsBaseTeam()) ? "是" : "否",
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
    /*public void branch_secretary_export(BranchViewExample example, HttpServletResponse response) {

        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        List<BranchView> records = branchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名", "工号", "所在单位", "联系电话", "所属分党委", "所属党支部", "党支部类别"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchView record = records.get(i);
            List<BranchMember> branchSecretary = commonMapper.findBranchMembers(secretaryType.getId(), record.getId());

            if (branchSecretary.size() > 0) {
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
    }*/

    // 导出支部书记
    public void branch_secretary_export(BranchViewExample example, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        List<BranchView> records = branchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号","姓名","编制类别","人员类别","人员状态","在岗情况","岗位类别", "主岗等级",
                "性别","出生日期", "年龄","年龄范围","民族", "国家/地区", "证件号码",
                "政治面貌","所在分党委、党总支、直属党支部","所在党支部", "所在单位", "入党时间","到校日期",
                "专业技术职务","专技岗位等级","管理岗位等级","任职级别","行政职务","学历","学历毕业学校","学位授予学校",
                "学位","学员结构", "人才类型", "人才称号", "籍贯","转正时间","手机号码","电子邮箱"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchView branch = records.get(i);
            List<BranchMember> branchSecretary = iPartyMapper.findBranchMembers(secretaryType.getId(), branch.getId());

            if (branchSecretary.size() > 0) {
                Integer userId = branchSecretary.get(0).getUserId();
                SysUserView uv = sysUserService.findById(userId);
                ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                Date birth = (extJzg!=null)?extJzg.getCsrq():null;
                String ageRange = "";
                if(birth!=null){
                    byte memberAgeRange = SystemConstants.getMemberAgeRange(DateUtils.getYear(birth));
                    if(memberAgeRange>0)
                        ageRange = SystemConstants.MEMBER_AGE_MAP.get(memberAgeRange);
                }
                Member member = memberService.get(userId);
                Integer partyId = (member!=null)?member.getPartyId():null;
                Integer branchId = (member!=null)?member.getBranchId():null;
                String[] values = {
                        uv.getCode(),
                        uv.getRealname(),
                        extJzg==null?"":extJzg.getBzlx(),
                        extJzg==null?"":extJzg.getRylx(),
                        extJzg==null?"":extJzg.getRyzt(), // 人员状态
                        extJzg==null?"":extJzg.getSfzg(), // 在岗情况
                        extJzg==null?"":extJzg.getGwlb(), // 岗位类别
                        extJzg==null?"":extJzg.getGwjb(), // 主岗等级--岗位级别
                        extJzg==null?"":extJzg.getXb(),
                        DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                        birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                        ageRange, // 年龄范围
                        extJzg==null?"":extJzg.getMz(),
                        extJzg==null?"":extJzg.getGj(), // 国家/地区
                        extJzg==null?"":extJzg.getSfzh(), // 证件号码
                        member==null?"":SystemConstants.MEMBER_POLITICAL_STATUS_MAP.get(member.getPoliticalStatus()), // 政治面貌
                        partyId==null?"":partyMap.get(partyId).getName(),
                        branchId==null?"":branchMap.get(branchId).getName(),
                        //unitMap.get(record.getUnitId()).getName(),
                        extJzg==null?"":extJzg.getDwmc(),
                        member==null?"":DateUtils.formatDate(member.getGrowTime(), DateUtils.YYYY_MM_DD),
                        extJzg==null?"":DateUtils.formatDate(extJzg.getLxrq(), DateUtils.YYYY_MM_DD), // 到校日期
                        "", // 专业技术职务
                        extJzg==null?"":extJzg.getZjgwdj(), //专技岗位等级
                        extJzg==null?"":extJzg.getGlgwdj(), // 管理岗位等级
                        extJzg==null?"":extJzg.getXzjb(), // 任职级别 -- 行政级别
                        extJzg==null?"":extJzg.getZwmc(), // 行政职务 -- 职务
                        extJzg==null?"":extJzg.getZhxlmc(), // 学历
                        extJzg==null?"":extJzg.getXlbyxx(), // 学历毕业学校
                        extJzg==null?"":extJzg.getXwsyxx(), // 学位授予学校
                        extJzg==null?"":extJzg.getZhxw(), // 学位
                        extJzg==null?"":extJzg.getXyjg(), // 学员结构 (学位授予国家)
                        extJzg==null?"":extJzg.getRclx(),
                        extJzg==null?"":extJzg.getRclx(),
                        extJzg==null?"":extJzg.getRcch(),
                        member==null?"":DateUtils.formatDate(member.getPositiveTime(), DateUtils.YYYY_MM_DD),
                        uv.getMobile(),
                        extJzg==null?"":extJzg.getDzxx()
                };
                valuesList.add(values);
            }
        }
        String fileName = "党支部书记_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/branch_selects")
    @ResponseBody
    public Map branch_selects(Integer pageSize, Boolean auth, Boolean del,
                              Integer pageNo, Integer partyId, String searchStr) throws IOException {

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

        if (del != null) {
            criteria.andIsDeletedEqualTo(del);
        }

        if (partyId == null) criteria.andIdIsNull(); // partyId肯定存在

        criteria.andPartyIdEqualTo(partyId);

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }

        //===========权限
        if (BooleanUtils.isTrue(auth)) {
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
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Branch> branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        /*List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != branchs && branchs.size()>0){

            for(Branch branch:branchs){

                Select2Option option = new Select2Option();
                option.setText(branch.getName());
                option.setId(branch.getId() + "");

                options.add(option);
            }
        }*/

        List<Map<String, Object>> options = new ArrayList<>();
        for (Branch branch : branchs) {
            Map<String, Object> option = new HashMap<>();
            option.put("text", branch.getName());
            option.put("id", branch.getId());
            option.put("del", branch.getIsDeleted());
            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
