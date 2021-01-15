package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.party.*;
import domain.unit.Unit;
import interceptor.OrderParam;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.party.PartyExportService;
import service.pcs.PcsConfigService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PartyExportService partyExportService;

    // 基本信息
    @RequiresPermissions("party:list")
    @RequestMapping("/party_base")
    public String party_base(Integer id, ModelMap modelMap) {

        Party party = partyMapper.selectByPrimaryKey(id);
        modelMap.put("party", party);
        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(id);
        modelMap.put("presentGroup", presentGroup);

        if(presentGroup!=null) {

            PartyMemberExample example = new PartyMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<PartyMember> PartyMembers = partyMemberMapper.selectByExample(example);
            modelMap.put("partyMembers", PartyMembers);
        }
        modelMap.put("adminIds", partyAdminService.adminPartyUserIdList(id));

        return "party/party_base";
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/party_view")
    public String party_view(int id, HttpServletResponse response,  ModelMap modelMap) {

        Party party = partyMapper.selectByPrimaryKey(id);

        int type = party.getFid() != null?1:0;
        modelMap.put("type", type);

        modelMap.put("party", party);

        return "party/party_view";
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/party")
    public String party(ModelMap modelMap,
                        @RequestParam(required = false, defaultValue = "0") int export,
                        HttpServletResponse response,
                        @RequestParam(required = false, defaultValue = "0")Byte type,
                        @RequestParam(required = false, defaultValue = "1")Byte cls) throws IOException {

        if(export==2){
            XSSFWorkbook wb = partyExportService.toXlsx();
            ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName() +
                    "各分党委、党总支、直属党支部基本情况表.xlsx", response);
            return null;
        }

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        return "party/party_page";
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/party_data")
    public void party_data(HttpServletResponse response,
                                    @RequestParam(required = false, defaultValue = "1")Byte cls,
                                    @RequestParam(required = false, defaultValue = "0")Byte type,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Boolean _integrity,
                                    String sort,
                                    String code,
                                    String name,
                                    Integer unitId,
                                    Integer classId,
                                    Integer typeId,
                                    Integer unitTypeId,
                                    Boolean isEnterpriseBig,
                                    Boolean isEnterpriseNationalized,
                                    Boolean isSeparate,
                                    Boolean isPycj,
                                    Boolean isBg,
                                    @RequestDateRange DateRange _foundTime,
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

        PartyViewExample example = new PartyViewExample();
        PartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.equalsIgnoreCase(sort,"integrity")){
            example.setOrderByClause(String.format("integrity %s, sort_order desc",order));
        }

        //分党委管理员管理自己的内设党总支
        List<Integer> partyIdList = loginUserService.adminPartyIdList();
        PartyExample partyExample = new PartyExample();
        partyExample.createCriteria().andFidIsNotNull().andFidIn(partyIdList);
        List<Party> partyList = partyMapper.selectByExample(partyExample);
        List<Integer> pgbList = partyList.stream().map(Party::getId).collect(Collectors.toList());
        partyIdList.addAll(pgbList);
        criteria.addPermits(partyIdList);

        criteria.andIsDeletedEqualTo(cls==2);

        if (type == 1){
            criteria.andFidIsNotNull();
        }else {
            criteria.andFidIsNull();
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (unitTypeId!=null) {
            criteria.andUnitTypeIdEqualTo(unitTypeId);
        }
        if(isEnterpriseBig!=null){
            criteria.andIsEnterpriseBigEqualTo(isEnterpriseBig);
        }
        if(isEnterpriseNationalized!=null){
            criteria.andIsEnterpriseNationalizedEqualTo(isEnterpriseNationalized);
        }
        if(isSeparate!=null){
            criteria.andIsSeparateEqualTo(isSeparate);
        }
        if(isPycj!=null){
            criteria.andIsPycjEqualTo(isPycj);
        }
        if(isBg!=null){
            criteria.andIsBgEqualTo(isBg);
        }
        if (_foundTime.getStart()!=null) {
            criteria.andFoundTimeGreaterThanOrEqualTo(_foundTime.getStart());
        }

        if (_foundTime.getEnd()!=null) {
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
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            party_export(example, response);
            return;
        }

        long count = partyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyView> Partys = partyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Partys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresPermissions("party:edit")
    @RequestMapping(value = "/party_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_au(Party record, HttpServletRequest request) {

        Integer id = record.getId();
        if (partyService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }

        record.setIsEnterpriseBig((record.getIsEnterpriseBig()==null)?false:record.getIsEnterpriseBig());
        record.setIsEnterpriseNationalized((record.getIsEnterpriseNationalized() == null) ? false : record.getIsEnterpriseNationalized());
        record.setIsSeparate((record.getIsSeparate() == null) ? false : record.getIsSeparate());

        if (id == null) {
            ShiroHelper.checkPermission("party:add");

            record.setCreateTime(new Date());
            partyService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加基层党组织：%s", record.getId()));
        } else {

            partyService.updateByPrimaryKeySelective(record);
            Party party = partyMapper.selectByPrimaryKey(id);
            if (party.getFid() == null) {
                if (!PartyHelper.isDirectBranch(id) && party.getBranchType() != null) {
                    commonMapper.excuteSql("update ow_party set branch_type = null where id=" + id);
                }
            }
            logger.info(addLog(LogConstants.LOG_PARTY, "更新基层党组织：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("party:edit")
    @RequestMapping("/party_au")
    public String party_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Party party = partyMapper.selectByPrimaryKey(id);
            modelMap.put("party", party);
        }

        return "party/party_au";
    }

    @RequiresPermissions("party:del")
    @RequestMapping(value = "/party_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "1")boolean isDeleted,
                        Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyService.batchDel(ids, isDeleted);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量删除基层党组织：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("party:changeOrder")
    @RequestMapping(value = "/party_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_changeOrder(Integer id, Integer addNum, byte type, HttpServletRequest request) {

        partyService.changeOrder(id, addNum, type);
        logger.info(addLog(LogConstants.LOG_PARTY, (type==1?"内设党总支调序":"基层党组织调序")+"：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("party:edit")
    @RequestMapping("/party_import")
    public String party_import(ModelMap modelMap) {

        return "party/party_import";
    }

    @RequiresPermissions("party:edit")
    @RequestMapping(value = "/party_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Party> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            Party record = new Party();
            row++;
            record.setCode(StringUtils.trimToNull(xlsRow.get(0)));

            String name = StringUtils.trimToNull(xlsRow.get(1));
             if(StringUtils.isBlank(name)){
                throw new OpException("第{0}行名称为空", row);
            }
            Party party = partyService.getByName(name);
             if (party != null){
                 record.setId(party.getId());
             }
            record.setName(name);

            String shortName = StringUtils.trimToNull(xlsRow.get(2));
            record.setShortName(shortName);

            String foundTime = StringUtils.trimToNull(xlsRow.get(3));
            record.setFoundTime(DateUtils.parseStringToDate(foundTime));

            String unitCode = StringUtils.trimToNull(xlsRow.get(4));
            if(StringUtils.isNotBlank(unitCode)){
                Unit unit = unitService.findRunUnitByCode(unitCode);
                if(unit==null){
                    throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
                }
                record.setUnitId(unit.getId());
            }

            String _partyClass = StringUtils.trimToNull(xlsRow.get(5));
            MetaType partyClass = CmTag.getMetaTypeByName("mc_party_class", _partyClass);
            if (partyClass == null) throw new OpException("第{0}行党总支类别[{1}]不存在", row, _partyClass);
            record.setClassId(partyClass.getId());

            String _partyType = StringUtils.trimToNull(xlsRow.get(6));
            MetaType partyType = CmTag.getMetaTypeByName("mc_party_type", _partyType);
            if (partyType == null) throw new OpException("第{0}行组织类别[{1}]不存在", row, _partyType);
            record.setTypeId(partyType.getId());

            String _partyUnitType = StringUtils.trimToNull(xlsRow.get(7));
            MetaType partyUnitType = CmTag.getMetaTypeByName("mc_party_unit_type", _partyUnitType);
            if (partyUnitType == null) throw new OpException("第{0}行所在单位属性[{1}]不存在", row, _partyUnitType);
            record.setUnitTypeId(partyUnitType.getId());

            record.setIsEnterpriseBig(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(8)), "是"));
            record.setIsEnterpriseNationalized(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(9)), "是"));
            record.setIsSeparate(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(10)), "是"));

            record.setPhone(StringUtils.trimToNull(xlsRow.get(11)));
            record.setFax(StringUtils.trimToNull(xlsRow.get(12)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(13)));

            record.setCreateTime(new Date());
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = partyService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    public void party_export(PartyViewExample example, HttpServletResponse response) {

        List<PartyView> records = partyViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|200","名称|350|left","简称|250|left","所属单位|250|left","党总支类别|100",
                "支部数量","党员总数","在职教职工数量|100","离退休党员数量|100","学生数量","是否已设立现任委员会|80",
                "任命时间|100","应换届时间|100", "实际换届时间|100",
                "是否大中型|100","是否国有独资|100","是否独立法人|100",
                "组织类别|100","所在单位属性|100","联系电话","传真","邮箱", "成立时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyView record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    record.getShortName(),
                    record.getUnitId()==null?"":unitService.findAll().get(record.getUnitId()).getName(),
                    metaTypeService.getName(record.getClassId()),

                    record.getBranchCount()==null?"0":record.getBranchCount()+"",
                    record.getMemberCount()==null?"0":record.getMemberCount()+"",
                    record.getTeacherMemberCount()==null?"0":record.getTeacherMemberCount()+"",
                    record.getRetireMemberCount()==null?"0":record.getRetireMemberCount()+"",
                    record.getStudentMemberCount()==null?"0":record.getStudentMemberCount()+"",
                    (record.getPresentGroupId()!=null &&record.getPresentGroupId() > 0) ? "是" : "否",

                    DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getTranTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYYMMDD_DOT),

                    BooleanUtils.isTrue(record.getIsEnterpriseBig()) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsEnterpriseNationalized()) ? "是" : "否",
                    BooleanUtils.isTrue(record.getIsSeparate()) ? "是" : "否",

                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getUnitTypeId()),
                    record.getPhone(),
                    record.getFax(),
                    record.getEmail(),
                    DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMM),
            };
            valuesList.add(values);
        }
        String fileName = "基层党组织(" + DateUtils.formatDate(new Date(), "yyyyMMddHH") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/party_selects")
    @ResponseBody
    public Map party_selects(Integer pageSize, Boolean auth, Boolean notDirect,
                             @RequestParam(required = false, defaultValue = "0") Byte type,
                             Boolean del,
                             Boolean notBranchAdmin,
                             Boolean isPcs, // 是否党代会
                             Integer pageNo, Integer classId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyViewExample example = new PartyViewExample();
        PartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_deleted asc, sort_order desc");

        if(del!=null){
            criteria.andIsDeletedEqualTo(del);
        }

        if(classId!=null) criteria.andClassIdEqualTo(classId);

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }
        if(BooleanUtils.isTrue(notDirect)){
            MetaType mtDirectBranch = CmTag.getMetaTypeByCode("mt_direct_branch");
            criteria.andClassIdNotEqualTo(mtDirectBranch.getId());
        }

        //===========权限
        if(BooleanUtils.isTrue(auth)) {
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

                List<Integer> partyIdList = loginUserService.adminPartyIdList();
                if(BooleanUtils.isNotTrue(notBranchAdmin)) { // 读取管理党支部所属的分党委，供查询；
                                                             // 如果账号只是支部管理员，无需读取分党委，则设置notBranchAdmin=1；
                    List<Integer> branchIdList = loginUserService.adminBranchIdList();
                    Map<Integer, Branch> branchMap = branchService.findAll();
                    for (Integer branchId : branchIdList) {
                        Branch branch = branchMap.get(branchId);
                        if (branch != null) {
                            partyIdList.add(branch.getPartyId());
                        }
                    }
                }

                //管理的所有party
                PartyExample partyExample = new PartyExample();
                partyExample.createCriteria().andFidIsNotNull().andFidIn(partyIdList);
                List<Party> partyList = partyMapper.selectByExample(partyExample);
                List<Integer> pgbList = partyList.stream().map(Party::getId).collect(Collectors.toList());
                partyIdList.addAll(pgbList);

                if (partyIdList.size() > 0)
                    criteria.andIdIn(partyIdList);
                else
                    criteria.andIdIsNull();
            }
        }

        //分开分党委和内设党总支
        if (type == 1){
            criteria.andFidIsNotNull();
        }else {
            criteria.andFidIsNull();
        }

        // 党代会筛选（必须同时是党代会分党委管理员和系统分党委的管理员）
        if(BooleanUtils.isTrue(isPcs)){

            List<Integer> partyIdList = new ArrayList<>();
            PcsConfigService pcsConfigService = CmTag.getBean(PcsConfigService.class);
            if(pcsConfigService!=null) {
                partyIdList = pcsConfigService.getPartyIdList();
            }
            if(partyIdList.size()>0){
                criteria.andIdIn(partyIdList);
            }else{
                criteria.andIdIsNull();
            }
        }

        long count = partyViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartyView> records = partyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
        List<Map<String, Object>> options = new ArrayList<>();
        for(PartyView party:records){

            Map<String, Object> option = new HashMap<>();
            option.put("text", party.getName());
            option.put("id", party.getId());
            option.put("class", party.getClassId());
            option.put("del", party.getIsDeleted());

            option.put("party", party);

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/party_integrity")
    public String party_integrity(Integer partyId,ModelMap modelMap){

        PartyView partyView = partyService.getPartyView(partyId);
        modelMap.put("partyView",partyView);
        return "party/party_integrity";
    }

    @RequiresPermissions("party:list")
    @RequestMapping(value = "/party_integrity", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_integrity(Integer partyId){

        PartyView partyView = partyService.getPartyView(partyId);
        partyService.checkIntegrity(partyView);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/stat_integrity")
    public String stat_integrity(Integer partyId,Integer cls,ModelMap modelMap){

        Integer userId = ShiroHelper.getCurrentUserId();
        modelMap.put("cls",cls);

        if (cls == 1){

            if (!ShiroHelper.isPermitted("partyIntegrity:*")){
                throw new OpException("您没有权限查看");
            }
            Map integrityMap = partyService.getPartyIntegrity();
            modelMap.put("integrityMap",integrityMap);
            return "party/stat_integrity_party";
        }
        if (cls == 2){

            if (!ShiroHelper.isPermitted("branchIntegrity:*")){
                throw new OpException("您没有权限查看");
            }

            //查询所管理的分党委，如果是党建管理员则查询所有分党委
            List<Party> parties = partyService.getPartysByUserId(userId);
            modelMap.put("partys",parties);

            //
            if (parties.size()>0 && partyId==null)
                partyId = parties.get(0).getId();

            Map integrityMap = partyService.getBranchIntegrity(partyId);
            modelMap.put("integrityMap",integrityMap);

            modelMap.put("partyId",partyId);
            return "party/stat_integrity_branch";
        }

        return "";
    }

    //批量删除内设党总支
    @RequiresPermissions("party:del")
    @RequestMapping(value = "/pgb_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pgb_batchDel(Integer[] ids){

        if (null != ids && ids.length>0){
            partyService.batchDelPgb(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量删除内设党总支：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
