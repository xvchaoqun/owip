package sys.tags;

import bean.ApplySelfModifyBean;
import bean.ApproverTypeBean;
import domain.abroad.*;
import domain.cadre.*;
import domain.dispatch.*;
import domain.member.MemberApply;
import domain.party.Branch;
import domain.party.Party;
import domain.party.RetireApply;
import domain.sys.*;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import persistence.abroad.PassportMapper;
import persistence.common.SelectMapper;
import persistence.sys.HtmlFragmentMapper;
import service.abroad.ApplySelfService;
import service.abroad.ApprovalLogService;
import service.abroad.PassportDrawService;
import service.abroad.SafeBoxService;
import service.cadre.*;
import service.dispatch.*;
import service.party.*;
import service.sys.*;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.HtmlEscapeUtils;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

import java.text.MessageFormat;
import java.util.*;

public class CmTag {

    static ApplicationContext context = ApplicationContextSupport.getContext();
    static HtmlFragmentService htmlFragmentService = (HtmlFragmentService) context.getBean("htmlFragmentService");
    static HtmlFragmentMapper htmlFragmentMapper = (HtmlFragmentMapper) context.getBean("htmlFragmentMapper");
    static SysUserService sysUserService = (SysUserService) context.getBean("sysUserService");
    static CadreService cadreService = (CadreService) context.getBean("cadreService");
    static CadrePostService cadrePostService = (CadrePostService) context.getBean("cadrePostService");
    static CadreAdminLevelService cadreAdminLevelService = (CadreAdminLevelService) context.getBean("cadreAdminLevelService");
    static CadreFamliyService cadreFamliyService = (CadreFamliyService) context.getBean("cadreFamliyService");
    static SysResourceService sysResourceService = (SysResourceService) context.getBean("sysResourceService");
    static MetaTypeService metaTypeService = (MetaTypeService) context.getBean("metaTypeService");
    static MetaClassService metaClassService = (MetaClassService) context.getBean("metaClassService");
    static RetireApplyService retireApplyService = (RetireApplyService) context.getBean("retireApplyService");
    static DispatchService dispatchService = (DispatchService) context.getBean("dispatchService");
    static DispatchCadreService dispatchCadreService = (DispatchCadreService) context.getBean("dispatchCadreService");
    static DispatchUnitService dispatchUnitService = (DispatchUnitService) context.getBean("dispatchUnitService");
    static DispatchTypeService dispatchTypeService = (DispatchTypeService) context.getBean("dispatchTypeService");
    static ApprovalLogService approvalLogService = (ApprovalLogService) context.getBean("approvalLogService");
    static PassportMapper passportMapper = (PassportMapper) context.getBean("passportMapper");
    static ApplySelfService applySelfService = (ApplySelfService) context.getBean("applySelfService");
    static UnitService unitService = (UnitService) context.getBean("unitService");
    static PartyService partyService = (PartyService) context.getBean("partyService");
    static PartyMemberService partyMemberService = (PartyMemberService) context.getBean("partyMemberService");
    static BranchService branchService = (BranchService) context.getBean("branchService");
    static BranchMemberService branchMemberService = (BranchMemberService) context.getBean("branchMemberService");
    static SafeBoxService safeBoxService = (SafeBoxService) context.getBean("safeBoxService");
    static PassportDrawService passportDrawService = (PassportDrawService) context.getBean("passportDrawService");
    static SelectMapper selectMapper = (SelectMapper) context.getBean("selectMapper");
    static CadreAdditionalPostService cadreAdditionalPostService = (CadreAdditionalPostService) context.getBean("cadreAdditionalPostService");
    static DispatchCadreRelateService dispatchCadreRelateService = (DispatchCadreRelateService) context.getBean("dispatchCadreRelateService");

    public static String toJSONObject(Object obj){

        if(obj==null) return "{}";
        String jsonStr = JSONUtils.toString(obj);

        return StringUtils.isBlank(jsonStr)?"{}":jsonStr;
    }

    public static String toJSONArray(List list){

        if(list==null) return "[]";
        String jsonStr = JSONUtils.toString(list);

        return StringUtils.isBlank(jsonStr)?"[]":jsonStr;
    }

    public static HtmlFragment getHtmlFragment(String code){

        return htmlFragmentService.codeKeyMap().get(code);
    }

    public static HtmlFragment getHtmlFragment(Integer id){

        return htmlFragmentMapper.selectByPrimaryKey(id);
    }

    // 用于jsp页面显示党组织名称
    public static String displayParty(Integer partyId, Integer branchId){

        String html = "<span class=\"{0}\">{1}<span><span class=\"{2}\">{3}<span>";
        Party party =null;
        Branch branch = null;
        if(partyId!=null){
            Map<Integer, Party> partyMap = partyService.findAll();
            party = partyMap.get(partyId);
        }
        if(branchId!=null){
            Map<Integer, Branch> branchMap = branchService.findAll();
            branch = branchMap.get(branchId);
        }

        return MessageFormat.format(html, (party!=null&&party.getIsDeleted())?"delete":"", party!=null?party.getName():"",
                (branch!=null&&branch.getIsDeleted())?"delete":"", branch!=null?(party!=null?" - ":"") + branch.getName():"");
    }

    public static String getApplyStatus(MemberApply memberApply) {
        String stage = "";
        switch (memberApply.getStage()) {
            case SystemConstants.APPLY_STAGE_INIT:
                stage = "待支部审核";
                break;
            case SystemConstants.APPLY_STAGE_DENY:
                stage = "申请未通过";
                break;
            case SystemConstants.APPLY_STAGE_PASS:
                stage = "申请通过，待支部确定为入党积极分子";
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
                if (memberApply.getCandidateStatus() == null || memberApply.getCandidateTime() == null) {
                    stage = "待支部确定为发展对象";
                } else if (memberApply.getCandidateStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                } else if (memberApply.getCandidateStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                if (memberApply.getPlanStatus() == null || memberApply.getPlanTime() == null) {
                    stage = "待支部列入发展计划";
                } else if (memberApply.getPlanStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                } else if (memberApply.getPlanStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_PLAN:
                if (memberApply.getDrawStatus() == null || memberApply.getDrawTime() == null) {
                    stage = "待分党委提交领取志愿书";
                } /*else if (memberApply.getDrawStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "待分党委审核";
                } else if (memberApply.getDrawStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }*/
                break;
            case SystemConstants.APPLY_STAGE_DRAW:
                if (memberApply.getGrowStatus() == null ) {
                    stage = "待组织部审核";
                } else if (memberApply.getGrowStatus() == SystemConstants.APPLY_STATUS_OD_CHECKED
                        || memberApply.getGrowTime() == null) {
                    stage = "组织部已审核，待分党委发展为预备党员";
                }
                break;
            case SystemConstants.APPLY_STAGE_GROW:
                if (memberApply.getPositiveStatus() == null || memberApply.getPositiveTime() == null) {
                    stage = "待支部提交预备党员转正";
                } else if (memberApply.getPositiveStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
                } else if (memberApply.getPositiveStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "分党委已审核，待组织部审核";
                } else if (memberApply.getPositiveStatus() == SystemConstants.APPLY_STATUS_OD_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_POSITIVE:
                stage = "已转正";
                break;
        }
        return stage;
    }

    public static SysResource getSysResource(Integer id) {

        Map<Integer, SysResource> sortedSysResources = sysResourceService.getSortedSysResources();

        return sortedSysResources.get(id);
    }

    public static SysResource getCurrentSysResource(String _path) {

        return sysResourceService.getByUrl(_path);
    }

    public static Map<Integer, MetaType> getMetaTypes(String classCode) {

        if (StringUtils.isBlank(classCode)) return null;

        return metaTypeService.metaTypes(classCode);
    }

    public static MetaType getMetaType(Integer id) {

        if (id == null) return null;

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        return metaTypeMap.get(id);
    }

    public static MetaClass getMetaClassByCode(String code) {

        if (StringUtils.isBlank(code)) return null;
        Map<String, MetaClass> metaClassMap = metaClassService.codeKeyMap();
        return metaClassMap.get(code);
    }

    public static MetaType getMetaTypeByName(String classCode, String name) {

        if (StringUtils.isBlank(name)) return null;

        return metaTypeService.findByName(classCode, StringUtils.trim(name));
    }

    public static MetaType getMetaTypeByCode(String code) {

        if (StringUtils.isBlank(code)) return null;

        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        return metaTypeMap.get(code);
    }

    public static Set getParentIdSet(String _path) {

        Set parentIdSet = new LinkedHashSet();
        SysResource sysResource = sysResourceService.getByUrl(_path);
        if (sysResource == null) return parentIdSet;

        String parentIds = sysResource.getParentIds();
        for (String str : parentIds.split("/")) {
            parentIdSet.add(Integer.parseInt(str));
        }
        return parentIdSet;
    }

    public static Cadre getCadreById(Integer id) {

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        return cadreMap.get(id);
    }
    public static Cadre getCadreByUserId(Integer userId) {

        return cadreService.findByUserId(userId);
    }

    public static List<CadreAdditionalPost> getCadreAdditionalPosts(Integer cadreId){

        return cadreAdditionalPostService.findCadrePosts(cadreId);
    }
    // 主职
    public static CadrePost getCadreMainCadrePost(int caderId){
        return cadrePostService.getCadreMainCadrePost(caderId);
    }
    // 现任职务
    public static CadreAdminLevel getPresentByCadreId(int caderId) {
        CadrePost mainCadrePost = getCadreMainCadrePost(caderId);
        return cadreAdminLevelService.getPresentByCadreId(caderId,
                mainCadrePost != null ? mainCadrePost.getAdminLevelId() : null);
    }

    public static CadreFamliy getCadreFamliy(Integer id){
        return cadreFamliyService.get(id);
    }

    public static SysUserView getUserById(Integer id) {

        if(id==null) return null;

        return sysUserService.findById(id);
    }

    public static SysUserView getUserByUsername(String username) {

        return sysUserService.findByUsername(username);
    }

    public static ApproverTypeBean getApproverTypeBean(Integer userId) {

       return applySelfService.getApproverTypeBean(userId);
    }
    public static Set<String> findRoles(String username) {

        return sysUserService.findRoles(username);
    }
    public static Set<String> findPermissions(String username) {

        return sysUserService.findPermissions(username);
    }

    public static Unit getUnit(Integer unitId) {

        return unitService.findAll().get(unitId);
    }

    public static Party getParty(Integer partyId) {

        return partyService.findAll().get(partyId);
    }

    public static Boolean isPresentBranchAdmin(Integer userId, Integer partyId, Integer branchId){
        return branchMemberService.isPresentAdmin(userId, partyId, branchId);
    }
    public static Boolean isPresentPartyAdmin(Integer userId, Integer partyId){
        return partyMemberService.isPresentAdmin(userId, partyId);
    }
    // 是否直属党支部
    public static Boolean isDirectBranch(Integer partyId){
        if(partyId==null) return false;
        return partyService.isDirectBranch(partyId);
    }
    // 是否分党委
    public static Boolean isParty(Integer partyId){
        return partyService.isParty(partyId);
    }
    // 是否党总支
    public static Boolean isPartyGeneralBranch(Integer partyId){
        return partyService.isPartyGeneralBranch(partyId);
    }

    public static Unit findUnitByCode(String code) {

        return unitService.findUnitByCode(StringUtils.trim(code));
    }


    // 判断类别ID和代码是否匹配，比如判断党组织是否是直属党支部
    public static Boolean typeEqualsCode(Integer typeId, String code) {

        if (StringUtils.isBlank(code) || typeId == null) return false;

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        MetaType metaType = metaTypeMap.get(typeId);

        return StringUtils.equalsIgnoreCase(metaType.getCode(), code);
    }

    @Deprecated
    public static Boolean classEqualsCode(Integer classId, String code) {

        if (StringUtils.isBlank(code) || classId == null) return false;

        Map<Integer, MetaClass> metaClassMap = metaClassService.findAll();
        MetaClass metaClass = metaClassMap.get(classId);

        return StringUtils.equalsIgnoreCase(metaClass.getCode(), code);
    }

    public static RetireApply getRetireApply(Integer userId) {

        return retireApplyService.get(userId);
    }

    public static Dispatch getDispatch(Integer dispatchId){

        return dispatchService.findAll().get(dispatchId);
    }

    public static DispatchCadre getDispatchCadre(Integer dispatchCadreId){

        return dispatchCadreService.findAll().get(dispatchCadreId);
    }

    public static Integer getDispatchCadreCount(Integer dispatchId, Byte type){
        return dispatchCadreService.count(dispatchId, type);
    }

    public static  List<DispatchCadreRelate> findDispatchCadreRelates(Integer relateId, Byte relateType){

        return dispatchCadreRelateService.findDispatchCadreRelates(relateId, relateType);
    }

    public static DispatchUnit getDispatchUnit(Integer dispatchUnitId){
        return dispatchUnitService.findAll().get(dispatchUnitId);
    }
    // 发文号
    public static String getDispatchCode(Integer code, Integer dispatchTypeId, Integer year) {

        if(code==null || dispatchTypeId == null || year==null) return null;

        String numStr = NumberUtils.frontCompWithZore(code, 2);
        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        DispatchType dispatchType = dispatchTypeMap.get(dispatchTypeId);
        return String.format("%s[%s]%s号", dispatchType.getName(), year, numStr);
    }

    public static DispatchType getDispatchType(Integer dispatchTypeId) {

        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        return dispatchTypeMap.get(dispatchTypeId);
    }

    public static ApplySelf getApplySelf(Integer applyId){
        return applySelfService.get(applyId);
    }

    public static List<ApplySelfModifyBean> getApplySelfModifyList(Integer applyId){
        return selectMapper.getApplySelfModifyList(applyId);
    }

    // 获取因私出国申请记录 初审 结果
    public static Integer getAdminFirstTrialStatus(Integer applyId) {
        return approvalLogService.getAdminFirstTrialStatus(applyId);
    }

    public static Map getApprovalTdBeanMap(Integer applySelfId) {

        return applySelfService.getApprovalTdBeanMap(applySelfId);
    }

    // 获取因私出国申请记录 的评审log
    public static List<ApprovalLog> findApprovalLogs(Integer applyId) {
        return approvalLogService.findByApplyId(applyId);
    }

    public static Map<Integer, SafeBox> getSafeBoxMap(){

        return safeBoxService.findAll();
    }
    // 证件
    public static Passport getPassport(Integer id) {

        return passportMapper.selectByPrimaryKey(id);
    }

    public static List<PassportDrawFile> getPassportDrawFiles(Integer id) {

        return passportDrawService.getPassportDrawFiles(id);
    }

    public static ApprovalLog getApprovalLog(Integer applySelfId, Integer approverTypeId) {

        return approvalLogService.getApprovalLog(applySelfId, approverTypeId);
    }
}
