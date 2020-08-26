package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.party.*;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.pcs.PcsConfigService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.spring.UserResUtils;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
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
        modelMap.put("presentGroup", presentGroup);

        if (presentGroup != null) {
            BranchMemberExample example = new BranchMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<BranchMember> BranchMembers = branchMemberMapper.selectByExample(example);
            modelMap.put("branchMembers", BranchMembers);
        }

        modelMap.put("adminIds", branchAdminService.adminBranchUserIdList(id));

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_member_type"));
        return "party/branch/branch_base";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_view")
    public String branch_view(int id, HttpServletResponse response, ModelMap modelMap) {

        Branch branch = branchMapper.selectByPrimaryKey(id);
        modelMap.put("branch", branch);

        return "party/branch/branch_view";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch")
    public String branch(Integer partyId,
                         Integer[] types,
                         @RequestParam(required = false, defaultValue = "1") Byte cls,
                         ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(types!=null){
            modelMap.put("selectTypes", Arrays.asList(types));
        }

        if (partyId != null) {
            Party party = partyMapper.selectByPrimaryKey(partyId);
            modelMap.put("party", party);
        }

        return "party/branch/branch_page";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_data")
    public void branch_data(HttpServletResponse response,
                            @RequestParam(required = false, defaultValue = "1") Byte cls,
                            @OrderParam(required = false, defaultValue = "desc") String order,
                            String sort,
                            String code,
                            String name,
                            Integer partyId,
                            Integer[] types,
                            Integer unitTypeId,
                            @RequestDateRange DateRange _foundTime,
                            Boolean isStaff,
                            Boolean isPrefessional,
                            Boolean isBaseTeam,
                            Boolean _integrity,
                            @RequestParam(required = false, defaultValue = "0") int export,
                            String exportType,
                            Integer[] ids, // 导出的记录
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
        example.setOrderByClause("party_sort_order desc, sort_order desc");

        if (StringUtils.equalsIgnoreCase(sort,"integrity")){
            example.setOrderByClause(String.format("integrity %s,party_sort_order desc, sort_order desc",order));
        }

        criteria.andIsDeletedEqualTo(cls == 2);

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        //===========权限（只有分党委管理员，才可以管理党支部）
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        /*if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            if (partyIdList.size() > 0)
                criteria.andPartyIdIn(partyIdList);
            else criteria.andPartyIdIsNull();
        }*/

        if (types != null) {
            criteria.andTypesContain(new HashSet<>(Arrays.asList(types)));
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

        if (_foundTime.getStart() != null) {
            criteria.andFoundTimeGreaterThanOrEqualTo(_foundTime.getStart());
        }

        if (_foundTime.getEnd() != null) {
            criteria.andFoundTimeLessThanOrEqualTo(_foundTime.getEnd());
        }

        if (_integrity != null){

            if (_integrity){
                criteria.andIntegrityEqualTo(new BigDecimal(1));
            }else {
                criteria.andIntegrityNotEqualTo(new BigDecimal(1));
            }
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            if (StringUtils.equals(exportType, "secretary")) { // 导出支部书记
                extService.branch_secretary_export(example, response);
            }else if (StringUtils.equals(exportType, "groupMember")){// 导出党小组成员
                branchGroupService.branch_groupMember_export(example, response);
            }else {
                branch_export(example, response);
            }
            return;
        }

        int count = (int) branchViewMapper.countByExample(example);
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

    //@RequiresPermissions("branch:edit")
    @RequestMapping(value = "/branch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_au(@CurrentUser SysUserView loginUser, Branch record, String _foundTime, HttpServletRequest request) {

        Integer id = record.getId();

        // 权限控制
        Integer partyId = record.getPartyId();
        if (id != null) {
            Branch branch = branchService.findAll().get(id);
            partyId = branch.getPartyId();
        }
        int loginUserId = loginUser.getId();
        if (!PartyHelper.hasBranchAuth(loginUserId, partyId, id))
            throw new UnauthorizedException();

        if (StringUtils.isNotBlank(_foundTime)) {
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsEnterpriseBig(BooleanUtils.isTrue(record.getIsEnterpriseBig()));
        record.setIsEnterpriseNationalized(BooleanUtils.isTrue(record.getIsEnterpriseNationalized()));
        record.setIsUnion(BooleanUtils.isTrue(record.getIsUnion()));
        record.setIsStaff(BooleanUtils.isTrue(record.getIsStaff()));
        record.setIsPrefessional(BooleanUtils.isTrue(record.getIsPrefessional()));
        record.setIsBaseTeam(BooleanUtils.isTrue(record.getIsBaseTeam()));

        /*if (!record.getIsStaff()) {
            record.setIsPrefessional(false);
        }
        if (!record.getIsPrefessional()) {
            record.setIsBaseTeam(false);
        }*/

        if (id == null) {

            SecurityUtils.getSubject().checkPermission("branch:add");

            record.setCreateTime(new Date());
            branchService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加党支部：%s", record.getId()));
        } else {

            SecurityUtils.getSubject().checkPermission("branch:edit");

            if (!CmTag.isSuperAccount(ShiroHelper.getCurrentUsername())) {
                record.setCode(null); // 不修改编号
            }
            branchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "更新党支部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("branch:edit")
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
        return "party/branch/branch_au";
    }

    @RequiresPermissions("branch:import")
    @RequestMapping("/branch_import")
    public String branch_import(ModelMap modelMap) {

        return "party/branch/branch_import";
    }

    @RequiresPermissions("branch:import")
    @RequestMapping(value = "/branch_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Branch> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            Branch record = new Branch();
            row++;

            String name = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(name)) {
                throw new OpException("第{0}行党支部名称为空", row);
            }
            record.setName(name);

            String shortName = StringUtils.trimToNull(xlsRow.get(1));
            record.setShortName(shortName);

            String _foundTime = StringUtils.trimToNull(xlsRow.get(2));
            Date foundTime = DateUtils.parseStringToDate(_foundTime);
            if (foundTime == null) {
                throw new OpException("第{0}行党支部成立时间为空", row);
            }
            record.setFoundTime(foundTime);

            String partyCode = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行所属分党委编码为空", row);
            }
            Party party = partyService.getByCode(partyCode);
            if (party == null) {
                throw new OpException("第{0}行所属分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());

            int startRow = 5;
            String _branchType = StringUtils.trimToNull(xlsRow.get(startRow++));
            MetaType branchType = CmTag.getMetaTypeByName("mc_branch_type", _branchType);
            if (branchType == null) throw new OpException("第{0}行党支部类别[{1}]不存在", row, _branchType);
            record.setTypes(branchType.getId()+"");

            String _branchUnitType = StringUtils.trimToNull(xlsRow.get(startRow++));
            MetaType branchUnitType = CmTag.getMetaTypeByName("mc_branch_unit_type", _branchUnitType);
            if (branchUnitType == null) throw new OpException("第{0}行所在单位属性[{1}]不存在", row, _branchUnitType);
            record.setUnitTypeId(branchUnitType.getId());

            record.setIsStaff(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(startRow++)), "是"));
            record.setIsPrefessional(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(startRow++)), "是"));
            record.setIsBaseTeam(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(startRow++)), "是"));
            record.setIsEnterpriseBig(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(startRow++)), "是"));
            record.setIsEnterpriseNationalized(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(startRow++)), "是"));
            record.setIsUnion(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(startRow++)), "是"));

            record.setPhone(StringUtils.trimToNull(xlsRow.get(startRow++)));
            record.setFax(StringUtils.trimToNull(xlsRow.get(startRow++)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(startRow++)));

            record.setCreateTime(new Date());
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = branchService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入党支部成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    /*@RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        // 权限控制
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            // 要求是分党委管理员
            Branch branch = branchService.findAll().get(id);
            int partyId = branch.getPartyId();
            if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        if (id != null) {
            branchService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除党支部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                        Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            branchService.batchDel(ids, isDeleted);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量撤销党支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:changeOrder")
    @RequestMapping(value = "/branch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_changeOrder(@CurrentUser SysUserView loginUser, Integer id, Integer addNum, HttpServletRequest request) {

        // 权限控制
        Branch branch = branchService.findAll().get(id);
        int partyId = branch.getPartyId();
        if (!partyMemberService.hasAdminAuth(loginUser.getId(), partyId))
            throw new UnauthorizedException();


        branchService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "党支部调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:transfer")
    @RequestMapping(value = "/branch_batchTransfer")
    public String branch_batchTransfer() {

        return "party/branch/branch_batchTransfer";
    }

    //批量转移支部
    @RequiresPermissions("branch:transfer")
    @RequestMapping(value = "/branch_batchTransfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_batchTransfer(Integer[] ids,
                                       int partyId,
                                       String remark) {

        branchService.batchTransfer(ids, partyId, remark);
        return success();
    }

    public void branch_export(BranchViewExample example, HttpServletResponse response) {

        List<BranchView> records = branchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|100", "名称|200|left", "简称|150|left", "所属分党委|300|left", "类别|100",
                "党员总数", "在职教职工数量", "离退休党员数量", "学生数量", "委员会总数",
                "是否已设立现任委员会", "任命时间", "应换届时间", "实际换届时间", "是否是教工党支部", "是否一线教学科研党支部", "是否建立在团队",
                "单位属性", "联系电话|100", "传真|100", "邮箱|150", "成立时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchView record = records.get(i);

            Set<Integer> typeIds = NumberUtils.toIntSet(record.getTypes(), ",");
            List<String> types = new ArrayList<>();
            for (Integer typeId : typeIds) {
                String name = metaTypeService.getName(typeId);
                if(StringUtils.isNotBlank(name)){
                    types.add(name);
                }
            }
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    record.getShortName(),
                    record.getPartyId() == null ? "" : partyService.findAll().get(record.getPartyId()).getName(),
                    StringUtils.join(types, ","),
                    record.getMemberCount() == null ? "0" : record.getMemberCount() + "",
                    record.getTeacherMemberCount() == null ? "0" : record.getTeacherMemberCount() + "",
                    record.getRetireMemberCount() == null ? "0" : record.getRetireMemberCount() + "",
                    record.getStudentMemberCount() == null ? "0" : record.getStudentMemberCount() + "",
                    record.getGroupCount() == null ? "0" : record.getGroupCount() + "",
                    (record.getPresentGroupId() != null && record.getPresentGroupId() > 0) ? "是" : "否",

                    DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getTranTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYYMMDD_DOT),

                    BooleanUtils.isTrue(record.getIsStaff()) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsPrefessional()) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsBaseTeam()) ? "是" : "否",
                    metaTypeService.getName(record.getUnitTypeId()),
                    record.getPhone(),
                    record.getFax(),
                    record.getEmail(),
                    DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMMDD_DOT)
            };
            valuesList.add(values);
        }
        String fileName = "党支部(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/branch_selects")
    @ResponseBody
    public Map branch_selects(Integer pageSize, Boolean auth, Boolean del,
                              Boolean isPcs, // 是否党代会
                              Integer pageNo, Integer partyId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchViewExample example = new BranchViewExample();
        BranchViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_deleted asc, sort_order desc");

        if (del != null) {
            criteria.andIsDeletedEqualTo(del);
        }

        if (partyId == null) {
            criteria.andIdIsNull(); // partyId肯定存在
        } else {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        //===========权限
        if (BooleanUtils.isTrue(auth)) {

            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

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

        // 党代会筛选
        if(BooleanUtils.isTrue(isPcs)){

            List<Integer> branchIdList = new ArrayList<>();
            PcsConfigService pcsConfigService = CmTag.getBean(PcsConfigService.class);
            if(pcsConfigService!=null) {
                branchIdList = pcsConfigService.getBranchIdList(partyId);
            }
            if(branchIdList.size()>0){
                criteria.andIdIn(branchIdList);
            }else{
                criteria.andIdIsNull();
            }
        }

        int count = (int) branchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchView> branchs = branchViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<>();
        for (BranchView branch : branchs) {
            Map<String, Object> option = new HashMap<>();
            option.put("text", branch.getName());
            option.put("id", branch.getId());
            option.put("del", branch.getIsDeleted());

            option.put("branch", branch);

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_integrity_view")
    public String member_integrity_view(Integer branchId,ModelMap modelMap){

        BranchView branchView = branchService.getBranchView(branchId);
        modelMap.put("branchView",branchView);
        return "party/branch/branch_integrity";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping(value = "/branch_integrity", method = RequestMethod.POST)
    @ResponseBody
    public Map branch_integrity(Integer branchId){

        BranchView branchView = branchService.getBranchView(branchId);
        branchService.checkIntegrity(branchView);
        return success(FormUtils.SUCCESS);
    }

    // 抽取党支部编码，根据党支部名称导出带编码的列表
    @RequiresPermissions("branch:codeExport")
    @RequestMapping("/branchPbCodeExport")
    public String branchPbCodeExport(ModelMap modelMap) {

        return "party/branch/branch_pb_export";
    }

    @RequiresPermissions("branch:codeExport")
    @RequestMapping(value = "/branchPbCodeExport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchPbCodeExport(
            int branchNameCol, //党支部名称列
            Integer bCodeAddCol, // 党支部编码列
            @RequestParam(required = false, defaultValue = "1") int sheetNo,
            HttpServletRequest request, HttpServletResponse response)
            throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(sheetNo-1);

        int firstNotEmptyRowNum = 0;
        XSSFRow firstRow = sheet.getRow(firstNotEmptyRowNum++);
        while (firstRow==null){
            if(firstNotEmptyRowNum>=100) break;
            firstRow = sheet.getRow(firstNotEmptyRowNum++);
        }
        if(firstRow==null){
            return failed("该文件前100行数据为空，无法导出");
        }

        int cellNum = firstRow.getLastCellNum() - firstRow.getFirstCellNum(); // 列数

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            // 行数据如果为空，不处理
            if (row == null) continue;

            XSSFCell cell = row.getCell(branchNameCol - 1);
            String branchName = ExcelUtils.getCellValue(cell);
            if(StringUtils.isBlank(branchName))
                throw new OpException("第{0}行党支部名称为空", row);

            // 去掉所有空格
            branchName = branchName.replaceAll("\\s*", "");
            branchName = branchName.replace("委员会", "");
            branchName = branchName.replace("党", "");

            BranchExample example = new BranchExample();
            example.createCriteria().andNameLike(SqlUtils.trimLike(branchName));
            List<Branch> branchs = branchMapper.selectByExample(example);
            if (branchs.size() > 0) {
                int count = 0;
                for (Branch branch : branchs) {
                    Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                    cell = row.createCell(cellNum + count++);
                    cell.setCellValue(branch.getCode());
                    if (null != party) {
                        cell = row.createCell(cellNum + count++);
                        cell.setCellValue(party.getCode());
                    }
                }
            }else {
                continue;
            }
        }

        String savePath = FILE_SEPARATOR + "_branchCodeExport"
                + FILE_SEPARATOR + DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + ".xlsx";
        FileUtils.mkdirs(springProps.uploadPath + savePath, true);

        ExportHelper.save(workbook, springProps.uploadPath + savePath);

        Map<String, Object> resultMap = success();
        resultMap.put("file", UserResUtils.sign(savePath));
        resultMap.put("filename", xlsx.getOriginalFilename());

        return resultMap;
    }
}
