package sys.tags;

import bean.ApproverTypeBean;
import domain.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import persistence.PassportMapper;
import service.abroad.ApplySelfService;
import service.abroad.ApprovalLogService;
import service.abroad.SafeBoxService;
import service.cadre.CadreService;
import service.dispatch.DispatchCadreService;
import service.dispatch.DispatchService;
import service.dispatch.DispatchTypeService;
import service.dispatch.DispatchUnitService;
import service.party.*;
import service.sys.MetaClassService;
import service.sys.MetaTypeService;
import service.sys.SysResourceService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.HtmlEscapeUtils;
import sys.utils.NumberUtils;

import java.util.*;

public class CmTag {

    static ApplicationContext context = ApplicationContextSupport.getContext();
    static SysUserService sysUserService = (SysUserService) context.getBean("sysUserService");
    static CadreService cadreService = (CadreService) context.getBean("cadreService");
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

    public static String getApplyStatus(MemberApply memberApply) {
        String stage = "";
        switch (memberApply.getStage()) {
            case SystemConstants.APPLY_STAGE_INIT:
                stage = "申请";
                break;
            case SystemConstants.APPLY_STAGE_DENY:
                stage = "申请未通过";
                break;
            case SystemConstants.APPLY_STAGE_PASS:
                stage = "申请通过";
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
                if (memberApply.getCandidateStatus() == null || memberApply.getCandidateTime() == null) {
                    stage = "未提交";
                } else if (memberApply.getCandidateStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "未审核";
                } else if (memberApply.getCandidateStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                if (memberApply.getPlanStatus() == null || memberApply.getPlanTime() == null) {
                    stage = "未提交";
                } else if (memberApply.getPlanStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "未审核";
                } else if (memberApply.getPlanStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_PLAN:
                if (memberApply.getDrawStatus() == null || memberApply.getDrawTime() == null) {
                    stage = "未提交";
                } else if (memberApply.getDrawStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "未审核";
                } else if (memberApply.getDrawStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_DRAW:
                if (memberApply.getGrowStatus() == null || memberApply.getGrowTime() == null) {
                    stage = "未提交";
                } else if (memberApply.getGrowStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "未审核";
                } else if (memberApply.getGrowStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "待组织部审核";
                } else if (memberApply.getGrowStatus() == SystemConstants.APPLY_STATUS_OD_CHECKED) {
                    stage = "已审核";
                }
                break;
            case SystemConstants.APPLY_STAGE_GROW:
                if (memberApply.getPositiveStatus() == null || memberApply.getPositiveTime() == null) {
                    stage = "未提交";
                } else if (memberApply.getPositiveStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "未审核";
                } else if (memberApply.getPositiveStatus() == SystemConstants.APPLY_STATUS_CHECKED) {
                    stage = "待组织部审核";
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

    public static MetaType getMetaType(String classCode, Integer id) {

        if (StringUtils.isBlank(classCode) || id == null) return null;

        Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes(classCode);
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

    public static SysUser getUserById(Integer id) {

        if(id==null) return null;

        return sysUserService.findById(id);
    }

    public static SysUser getUserByUsername(String username) {

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

    public static Integer getDispatchCadreCount(Integer dispatchId, Byte type){
        return dispatchCadreService.count(dispatchId, type);
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

    public static String encodeQueryString(String queryString) {

        if (StringUtils.isBlank(queryString)) return "";

        List<String> paramList = new ArrayList<>();
        String[] paramPairs = queryString.split("&");
        for (String paramPair : paramPairs) {
            if (StringUtils.isBlank(paramPair)) continue;
            String[] param = paramPair.split("=");
            if (param.length == 1) {
                paramList.add(HtmlEscapeUtils.escape(param[0]));
                continue;
            }
            String paramName = HtmlEscapeUtils.escape(param[0]);
            String paramValue = HtmlEscapeUtils.escape(param[1]);
            paramList.add(paramName + "=" + paramValue);
        }

        return StringUtils.join(paramList, "&");
    }

    public static ApprovalLog getApprovalLog(Integer applySelfId, Integer approverTypeId) {

        return approvalLogService.getApprovalLog(applySelfId, approverTypeId);
    }
}
