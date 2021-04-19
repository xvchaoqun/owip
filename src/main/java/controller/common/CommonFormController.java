package controller.common;

import controller.member.MemberController;
import controller.party.PartyController;
import domain.member.Member;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.*;
import domain.sys.SysUserView;
import domain.unit.Unit;
import ext.service.ExtService;
import interceptor.OrderParam;
import mixin.MemberMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.member.MemberViewMapper;
import persistence.party.BranchMapper;
import persistence.party.BranchViewMapper;
import persistence.party.PartyMapper;
import persistence.party.PartyViewMapper;
import persistence.sys.common.ISysMapper;
import persistence.unit.UnitMapper;
import service.LoginUserService;
import service.SpringProps;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.pcs.PcsConfigService;
import service.sys.SysUserService;
import service.unit.UnitService;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class CommonFormController implements HttpResponseMethod {

    @Autowired
    private MemberController memberController;

    @Autowired
    private PartyController partyController;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private SpringProps springProps;

    @Autowired
    private MemberViewMapper memberViewMapper;

    @Autowired
    private PartyService partyService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private PartyViewMapper partyViewMapper;

    @Autowired
    private BranchViewMapper branchViewMapper;

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private ISysMapper iSysMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ExtService extService;
    
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UnitService unitService;
    
/***********************************************************************************************/
/**                                      常用表格下载                                           **/
/***********************************************************************************************/

    /***********************************************************************************************/
    /**                                   党员（含预备党员）                                         **/
    /***********************************************************************************************/
    @RequiresPermissions("commonSheet:list")
    @RequestMapping("commonSheet/csMember")
    public String partyInfoPage(){

        return "party/commonSheet/csMember";
    }
    /***********************************************************************************************/
    /**                               党员（含预备党员）页面效果展示                                   **/
    /***********************************************************************************************/
    @RequestMapping("/csMember_data")
    public void csMember_data(HttpServletResponse response,
                              String sort,
                              @OrderParam(required = false, defaultValue = "desc") String order,
                              @RequestParam(defaultValue = "1") int cls,
                              Boolean _integrity,
                              Integer userId,
                              String realname, // 姓名或学工号
                              Byte userType,
                              Integer unitId,
                              Integer partyId,
                              Integer branchId,
                              Byte politicalStatus,
                              Byte gender,
                              String[] nation,
                              String[] nativePlace,
                              @RequestDateRange DateRange _growTime,
                              @RequestDateRange DateRange _positiveTime,
                              @RequestDateRange DateRange _outHandleTime,
                              Byte userSource, // 账号来源
                              String idcard,

                              /**学生党员**/
                              String grade,
                              String studentType,
                              String eduLevel,
                              String eduType,

                              /** 教职工党员**/
                              String education,
                              String postClass,
                              String staffStatus,

                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer[] ids, // 导出的记录
                              Integer[] cols, // 选择导出的列
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();

        String orderStr = "user_type,";
        if (StringUtils.equalsIgnoreCase(sort, "birth")) {
            if (StringUtils.equalsIgnoreCase(order, "desc")){
                order = "asc";
            }else {
                order = "desc";
            }
            example.setOrderByClause(String.format("birth %s", order));
        }else if (StringUtils.equalsIgnoreCase(sort, "party")) {
            example.setOrderByClause(String.format("party_sort_order , branch_sort_order %s," + orderStr + " grow_time desc", order));
        } else if (StringUtils.equalsIgnoreCase(sort, "growTime")) {
            example.setOrderByClause(String.format("grow_time %s", order));
        }else if (StringUtils.equalsIgnoreCase(sort, "positiveTime")) {
            example.setOrderByClause(String.format("positive_time %s", order));
        }else if (StringUtils.equalsIgnoreCase(sort, "outHandleTime")) {
            example.setOrderByClause(String.format("out_handle_time %s", order));
        }else if (StringUtils.equalsIgnoreCase(sort,"integrity")){
            example.setOrderByClause(String.format("integrity %s", order));
        }else{
            example.setOrderByClause(String.format("party_sort_order desc, branch_sort_order desc," + orderStr + "user_id", order));
        }

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andCodeOrRealnameLike(realname);
        }

        if(userType!=null){
            criteria.andUserTypeEqualTo(userType);
        }

        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (politicalStatus != null) {
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }
        if (nativePlace != null) {
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            criteria.andNativePlaceIn(selectNativePlaces);
        }

        if (StringUtils.isNotBlank(grade)) {
            criteria.andGradeLike(SqlUtils.trimLike(grade));
        }
        if (StringUtils.isNotBlank(studentType)) {
            criteria.andStudentTypeLike(SqlUtils.trimLike(studentType));
        }
        if (StringUtils.isNotBlank(eduLevel)) {
            criteria.andEduLevelLike(SqlUtils.like(eduLevel));
        }
        if (StringUtils.isNotBlank(eduType)) {
            criteria.andEduTypeLike(SqlUtils.like(eduType));
        }

        if (StringUtils.isNotBlank(education)) {
            criteria.andEducationEqualTo(education);
        }
        if (StringUtils.isNotBlank(postClass)) {
            criteria.andPostClassEqualTo(postClass);
        }
        if (StringUtils.isNotBlank(staffStatus)) {
            criteria.andStaffStatusEqualTo(staffStatus);
        }

        if (_growTime.getStart() != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd() != null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }
        if (_positiveTime.getStart() != null) {
            criteria.andPositiveTimeGreaterThanOrEqualTo(_positiveTime.getStart());
        }
        if (_positiveTime.getEnd() != null) {
            criteria.andPositiveTimeLessThanOrEqualTo(_positiveTime.getEnd());
        }
        if (_outHandleTime.getStart() != null) {
            criteria.andOutHandleTimeGreaterThanOrEqualTo(_outHandleTime.getStart());
        }
        if (_outHandleTime.getEnd() != null) {
            criteria.andOutHandleTimeLessThanOrEqualTo(_outHandleTime.getEnd());
        }

        if (userSource != null) {
            criteria.andUserSourceEqualTo(userSource);
        }

        if (_integrity != null){

            if (_integrity){
                criteria.andIntegrityEqualTo(new BigDecimal(1));
            }else {
                criteria.andIntegrityNotEqualTo(new BigDecimal(1));
            }
        }
        if (StringUtils.isNotBlank(idcard)){
            criteria.andIdcardEqualTo(idcard.trim());
        }


        long count = memberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberView> records = memberViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(MemberView.class, MemberMixin.class);
        baseMixins.remove(MemberView.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    /***********************************************************************************************/
    /**                                  导出党员（含预备党员）                                       **/
    /***********************************************************************************************/
    @RequiresPermissions("csMember:*")
    @RequestMapping("commonSheet/downloadPartyInfo")
    public String downloadPartyInfo(MemberViewExample example, Integer[] cols, HttpServletResponse response){
        partyInfo_export(example, cols, response);
        return "party/commonSheet/csMember";
    }
    private List<String> getPartyInfoExportTitles() {
        return new ArrayList<>(Arrays.asList(new String[]{"学号/职工号|100", "姓名|80", "性别|50","身份证号|150", "出生日期|100", "年龄|50",
                "民族|100", "籍贯|150", "所属" + CmTag.getStringProperty("partyName", "党委") + "|350|left", "所属党支部|350|left",
                "入党成熟度|100", "入党申请时间|100", "确定为入党积极分子时间|200", "确定为发展对象时间|150", "入党时间|100", "转正时间|150",
                "人员类别|100", "从事专业技术职务|150", "专业技术职务级别|150", "党政职务|150","党政职务级别|100","党支部任职|150","人员状态|150",
                "联系地址|350|left", "移动电话|120", "毕业院校|100", "专业|100", "最高学历|100", "学位|120", "所在单位|150", "党支部类型|150",
                "停止党籍或出国关系保留|150"}));
    }

    public void partyInfo_export(MemberViewExample example, Integer[] cols, HttpServletResponse response) {
        Map<Integer, Unit> unitMap = unitService.findAll();
        List<MemberView> records = memberViewMapper.selectByExample(example);
        int rownum = records.size();
        List<String> exportTitles = getPartyInfoExportTitles();
        if (cols != null && cols.length > 0) {
            // 选择导出列
            List<String> _titles = new ArrayList<>();
            for (int col : cols) {
                _titles.add(exportTitles.get(col));
            }
            exportTitles.clear();
            exportTitles.addAll(_titles);
        }
        List<List<String>> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberView record = records.get(i);
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Integer userId = record.getUserId();
            Integer unitId = record.getUnitId();
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    record.getCode(),//学号/职工号
                    record.getRealname(),//姓名
                    gender == null ? "" : SystemConstants.GENDER_MAP.get(gender),//性别
                    record.getIdcard(),//身份证号
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),//出生日期
                    record.getBirth() != null ? DateUtils.intervalYearsUntilNow(record.getBirth()) + "" : "",//年龄
                    record.getNation(),//民族
                    record.getNativePlace(),//籍贯
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),//所属分党委
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),//所属党支部
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()), // 入党成熟度 ：政治面貌
                    DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYYMMDD_DOT),//入党申请时间
                    DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYYMMDD_DOT),//确定为入党积极分子时间
                    DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYYMMDD_DOT),//确定为发展对象时间
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYYMMDD_DOT),//入党时间
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYYMMDD_DOT),//转正时间
                    SystemConstants.USER_TYPE_MAP.get(record.getUserType()),//人员类别
                    record.getProPost(),//从事专业技术职务
                    SystemConstants.PRO_POST_LEVEL_MAP.get(record.getProPostLevel()),//专业技术职务级别
                    record.getPartyPost(),//党政职务
                    record.getPostLevel(),//党政职务级别
                    "",//党支部任职
                    MemberConstants.MEMBER_STATUS_MAP.get(record.getStatus()),//人员状态
                    partyId == null ? "" : partyService.findAll().get(partyId).getAddress(),//联系地址
                    record.getMobile(),//移动电话
                    record.getSchool(),//毕业院校
                    record.getMajor(),//专业
                    record.getEducation(),//最高学历
                    record.getDegree(),//学位
                    unitMapper.selectByPrimaryKey(unitId).getName(),//所在单位
                    record.getBranchName()/*branchId == null ? "" : branchService.findAll().get(branchId).getTypes()*/,//党支部类型
                    MemberConstants.MEMBER_STATUS_MAP.get(record.getStatus())//停止党籍或出国关系保留
            }));
            if (cols != null && cols.length > 0) {
                // 选择导出列
                List<String> _values = new ArrayList<>();
                for (int col : cols) {
                    _values.add(values.get(col));
                }
                values.clear();
                values.addAll(_values);
            }
            valuesList.add(values);
        }
        String fileName = "党员（含预备党员）信息(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(exportTitles, valuesList, fileName, response);
    }

    /***********************************************************************************************/
    /**                                  搜索党员（含预备党员）                                       **/
    /***********************************************************************************************/
    @RequestMapping("/csMember_selects")
    @ResponseBody
    public Map csMember_selects(Integer pageSize,
                              Integer partyId,
                              Integer branchId,
                              Byte type, // 人员类别
                              Byte[] types, // 人员类别
                              Boolean isRetire,
                              Byte politicalStatus,
                              Byte[] status, // 党员状态
                              Boolean noAuth, // 默认需要读取权限
                              Integer[] excludeUserIds, // 排除账号
                              @RequestParam(defaultValue = "0", required = false) boolean needPrivate,
                              Integer pageNo,
                              String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();

        example.setOrderByClause("sort_order desc, convert(realname using gbk) asc");

        List<Integer> adminPartyIdList = null;
        List<Integer> adminBranchIdList = null;
        if (BooleanUtils.isNotTrue(noAuth)){
            adminPartyIdList = loginUserService.adminPartyIdList();
            adminBranchIdList = loginUserService.adminBranchIdList();

            criteria.addPermits(adminPartyIdList, adminBranchIdList);
        }

        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }

        if(type!=null){
            if(type == MemberConstants.MEMBER_TYPE_TEACHER){
                criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_JZG,
                        SystemConstants.USER_TYPE_RETIRE));
            }else{
                criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_BKS,
                        SystemConstants.USER_TYPE_SS, SystemConstants.USER_TYPE_BS));
            }
        }
        if(types!=null){
            criteria.andUserTypeIn(Arrays.asList(types));
        }

        if(isRetire!=null){
            if(isRetire) {
                criteria.andUserTypeEqualTo(SystemConstants.USER_TYPE_RETIRE);
            }else{
                criteria.andUserTypeNotEqualTo(SystemConstants.USER_TYPE_RETIRE);
            }
        }

        if(politicalStatus!=null){
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }

        if(status!=null && status.length>0){
            criteria.andStatusIn(Arrays.asList(status));
        }

        if(excludeUserIds!=null && excludeUserIds.length>0){
            criteria.andUserIdNotIn(Arrays.asList(excludeUserIds));
        }

        searchStr = ContentUtils.trimAll(searchStr);
        if (searchStr != null) {
            criteria.andUserLike(searchStr);
        }

        int count = (int) memberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberView> members = memberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != members && members.size() > 0) {

            for (MemberView member : members) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(member.getUserId());
                option.put("id", member.getUserId() + "");
                option.put("text", member.getRealname());

                option.put("username", member.getUsername());
                option.put("locked", uv.getLocked());
                option.put("code", member.getCode());
                option.put("realname", member.getRealname());
                option.put("gender", member.getGender());
                option.put("birth", member.getBirth());
                option.put("nation", member.getNation());

                if(needPrivate) {
                    option.put("idcard", member.getIdcard());
                    option.put("politicalStatus", member.getPoliticalStatus());
                    option.put("mobile", member.getMobile());

                    Party party = CmTag.getParty(member.getPartyId());
                    if(party!=null){
                        option.put("partyName", party.getName());
                        option.put("partyAddress", party.getAddress());
                        option.put("partyPhone", party.getPhone());
                        option.put("partyFax", party.getFax());
                    }
                }
                //option.put("user", userBeanService.get(member.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("unit", extService.getUnit(uv.getId()));
                }

                /*String branchName = StringUtils.defaultIfBlank(member.getBranchName(), member.getPartyName());
                String schoolName = CmTag.getSysConfig().getSchoolName();
                String schoolShortName = CmTag.getSysConfig().getSchoolShortName();
                branchName = RegExUtils.replaceFirst(branchName,
                        "[中共|中国共产党]"+ "["+ schoolShortName +"|"+ schoolName +"]", "");
                option.put("unit", branchName);*/

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }


    @RequestMapping("/csMember")
    public String csMemberAppply(ModelMap modelMap ,
                                Integer userId,
                                Integer status,
                                Integer partyId,
                                Integer branchId,
                                @RequestParam(required = false, defaultValue = "1") int cls) {

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (status != null){
            modelMap.put(status.toString(), OwConstants.OW_APPLY_STAGE_MAP.get(status));
        }
        if (partyId != null){
            Party party = CmTag.getParty(partyId);
            modelMap.put("party",party);
        }
        if (branchId != null){
            Branch branch = CmTag.getBranch(branchId);
            modelMap.put("branch",branch);
        }
        return "party/commonSheet/csMember";
    }

}



















