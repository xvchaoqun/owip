package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.party.*;
import domain.party.BranchMemberExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.party.BranchExportService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
                                  Integer types,
                                  Boolean isAdmin,
                                  Boolean isDeleted,
                                  Boolean isHistory,
                                  Boolean isDoubleLeader,

                                  //党支部中的字段
                                  Integer[] branchTypes,
                                  Integer unitTypeId,
                                  Boolean isStaff,
                                  Boolean isPrefessional,
                                  Boolean isBaseTeam,

                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer[] ids, // 导出的记录
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
        example.setOrderByClause("party_sort_order desc, branch_sort_order desc, is_history asc, sort_order desc");

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (isDeleted != null) {
            criteria.andIsDeletedEqualTo(isDeleted);
        }

        if (groupId != null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (types != null) {
            criteria.andTypesLike("%" + types + "%");
        }
        if (isAdmin != null) {
            criteria.andIsAdminEqualTo(isAdmin);
        }
        if (isHistory != null) {
            criteria.andIsHistoryEqualTo(isHistory);
        }
        if (isDoubleLeader != null) {
            criteria.andIsDoubleLeaderEqualTo(isDoubleLeader);
        }
        BranchViewExample branchViewExample = new BranchViewExample();
        BranchViewExample.Criteria branchCriteria = branchViewExample.createCriteria();
        if (branchTypes != null) {
            branchCriteria.andTypesContain(new HashSet<>(Arrays.asList(branchTypes)));
        }
        if (unitTypeId != null) {
            branchCriteria.andUnitTypeIdEqualTo(unitTypeId);
        }
        if (isStaff != null) {
            branchCriteria.andIsStaffEqualTo(isStaff);
        }
        if (isPrefessional != null) {
            branchCriteria.andIsPrefessionalEqualTo(isPrefessional);
        }
        if (isBaseTeam != null) {
            branchCriteria.andIsBaseTeamEqualTo(isBaseTeam);
        }
        List<BranchView> branchViewList = branchViewMapper.selectByExample(branchViewExample);
        if (branchViewList != null && branchViewList.size() > 0){
            List<Integer> branchIdList = branchViewList.stream().map(BranchView::getId).collect(Collectors.toList());
            criteria.andGroupBranchIdIn(branchIdList);
        }
        if ((branchTypes != null || unitTypeId != null || isStaff != null || isPrefessional != null || isBaseTeam != null)
                && branchViewList != null && branchViewList.size() == 0){
            criteria.andGroupBranchIdIsNull();//根据委员会对应的的branchId
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_au(BranchMember record,Integer[] types, HttpServletRequest request) {

        Integer id = record.getId();

        if (branchMemberService.idDuplicate(id, record.getGroupId(), record.getUserId(), types)) {
            return failed("添加重复【每个委员会的人员不可重复，并且书记只有一个】");
        }
        record.setIsDoubleLeader(BooleanUtils.isTrue(record.getIsDoubleLeader()));

        boolean autoAdmin = false;
        Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes("mc_branch_member_type");

        for (int typeId : types) {
            MetaType metaType = metaTypeMap.get(typeId);
            if (BooleanUtils.isTrue(metaType.getBoolAttr())) {
                autoAdmin = true;
                break;
            }
        }
        if (id == null) {
            branchMemberService.insertSelective(record, autoAdmin);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加支部成员：%s", record.getId()));
        } else {
            record.setUserId(null);
            branchMemberService.updateByPrimaryKeySelective(record, autoAdmin);
            logger.info(addLog(LogConstants.LOG_PARTY, "更新支部成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping("/branchMember_au")
    public String branchMember_au(Integer id, Integer groupId, ModelMap modelMap) {

        if (id != null) {
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
            modelMap.put("branchMember", branchMember);
            groupId = branchMember.getGroupId();
        }
        modelMap.put("groupId", groupId);

        return "party/branchMember/branchMember_au";
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map branchAdmin_del(Integer userId, Integer branchId, Boolean normal) {

        // 权限控制
        Branch branch = branchService.findAll().get(branchId);
        int partyId = branch.getPartyId();
        if (!partyMemberService.hasAdminAuth(ShiroHelper.getCurrentUserId(), partyId)) {
            throw new UnauthorizedException();
        }
        if (userId.intValue() == ShiroHelper.getCurrentUserId()) {
            return failed("不能删除自己");
        }

        branchMemberService.delAdmin(userId, branchId, normal);
        logger.info(addLog(LogConstants.LOG_PARTY, "删除支部管理员权限，userId=%s, branchId=%s", userId, branchId));
        return success(FormUtils.SUCCESS);
    }

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

    @RequiresPermissions("branchMember:del")
    @RequestMapping(value = "/branchMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            branchMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量删除支部成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:changeOrder")
    @RequestMapping(value = "/branchMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "支部成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping("/branchMember_dismiss")
    public String branchMember_dismiss(int id, ModelMap modelMap) {

        BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
        modelMap.put("branchMember", branchMember);
        modelMap.put("sysUser", CmTag.getUserById(branchMember.getUserId()));

        return "party/branchMember/branchMember_dismiss";
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_dismiss", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_dismiss(Integer id,
                                      Boolean dismiss,
                                      @DateTimeFormat(pattern = DateUtils.YYYYMM) Date dismissDate,
                                      @DateTimeFormat(pattern = DateUtils.YYYYMM) Date assignDate,
                                      HttpServletRequest request) {

        branchMemberService.dismiss(id, dismiss, dismissDate, assignDate);

        logger.info(addLog(LogConstants.LOG_PARTY, "基层党组织成员离任：%s,%s", id,
                DateUtils.formatDate(dismissDate, DateUtils.YYYYMM)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map branchMember_admin(HttpServletRequest request, Integer id, Boolean isAdmin) {

        if (id != null) {

            int userId = ShiroHelper.getCurrentUserId();
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

                BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
                Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
                 if(!partyMemberService.hasAdminAuth(userId, branch.getPartyId())
                     && branchMember.getIsAdmin() && branchMember.getUserId().intValue() == userId) {
                    return failed("无法撤销本人的权限");
                }
            }

            branchAdminService.setBranchAdmin(id, isAdmin);

            String op = isAdmin ? "删除" : "添加";
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
        Criteria criteria = example.createCriteria().andIsHistoryEqualTo(false);
        example.setOrderByClause("sort_order desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
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

    @RequiresPermissions("branchMember:edit")
    @RequestMapping("/branchMember_import")
    public String branchMember_import(ModelMap modelMap) {

        return "party/branchMember/branchMember_import";
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<BranchMember> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            BranchMember record = new BranchMember();
            row++;

            String partyCode = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行所属分党委编码为空", row);
            }
            Party party = partyService.getByCode(partyCode);
            if (party == null) {
                throw new OpException("第{0}行所属分党委编码[{1}]不存在", row, partyCode);
            }

            PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(party.getId());
            if (presentGroup == null) continue; // 如果分党委还未设置当前班子，则忽略导入；
            record.setGroupId(presentGroup.getId());

            String branchCode = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(branchCode)){
                throw new OpException("第{0}行所属党支部编码为空", row);
            }
            Branch branch = branchService.getByCode(branchCode);
            if (branch == null) {
                throw new OpException("第{0}行所属党支部编码[{1}]不存在", row, branchCode);
            }
            BranchMemberGroup branchMemberGroup = branchMemberGroupService.getPresentGroup(branch.getId());
            if (branchMemberGroup == null) continue;// 如果党支部还未设置当前委员会，则忽略导入；
            record.setGroupId(branchMemberGroup.getId());

            String userCode = StringUtils.trim(xlsRow.get(5));
            if (StringUtils.isBlank(userCode)) {
                continue; // 学工号为空则忽略该行
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            record.setUserId(userId);

            String _type = StringUtils.trim(xlsRow.get(6));
            if (StringUtils.isBlank(_type)){
                throw new OpException("第{0}行职务为空", row);
            }
            MetaType postType = CmTag.getMetaTypeByName("mc_branch_member_type", _type);
            if (postType == null) {
                throw new OpException("第{0}行职务[{1}]不存在", row, _type);
            }else if (postType != null) {
                    record.setTypes(postType.getId()+"");
            }
            record.setIsAdmin(StringUtils.equalsIgnoreCase(StringUtils.trim(xlsRow.get(7)), "是"));

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = branchMemberService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入支部委员会成员成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
