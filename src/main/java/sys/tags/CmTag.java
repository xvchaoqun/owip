package sys.tags;

import bean.ApplySelfModifyBean;
import bean.ApproverTypeBean;
import domain.abroad.ApplySelf;
import domain.abroad.ApprovalLog;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawFile;
import domain.abroad.SafeBox;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdminLevel;
import domain.cadre.CadreEdu;
import domain.cadre.CadreFamliy;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cis.CisInspectorView;
import domain.crs.CrsPost;
import domain.crs.CrsRequireRule;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import domain.dispatch.DispatchType;
import domain.dispatch.DispatchUnit;
import domain.member.MemberApplyView;
import domain.modify.ModifyCadreAuth;
import domain.party.Branch;
import domain.party.Party;
import domain.party.RetireApply;
import domain.sys.HtmlFragment;
import domain.sys.SysResource;
import domain.sys.SysUserView;
import domain.train.TrainEvaNorm;
import domain.train.TrainEvaRank;
import domain.unit.Unit;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import persistence.abroad.PassportMapper;
import persistence.common.IAbroadMapper;
import persistence.sys.HtmlFragmentMapper;
import service.abroad.ApplySelfService;
import service.abroad.ApprovalLogService;
import service.abroad.PassportDrawService;
import service.abroad.SafeBoxService;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.cadre.CadreAdditionalPostService;
import service.cadre.CadreAdminLevelService;
import service.cadre.CadreEduService;
import service.cadre.CadreFamliyService;
import service.cadre.CadrePostService;
import service.cadre.CadreService;
import service.cis.CisInspectObjService;
import service.cis.CisInspectorService;
import service.crs.CrsPostService;
import service.crs.CrsRequireRuleService;
import service.dispatch.DispatchCadreRelateService;
import service.dispatch.DispatchCadreService;
import service.dispatch.DispatchService;
import service.dispatch.DispatchTypeService;
import service.dispatch.DispatchUnitService;
import service.global.CacheService;
import service.modify.ModifyCadreAuthService;
import service.party.BranchMemberService;
import service.party.BranchService;
import service.party.PartyMemberService;
import service.party.PartyService;
import service.party.RetireApplyService;
import service.sys.HtmlFragmentService;
import service.sys.SysResourceService;
import service.sys.SysUserService;
import service.train.TrainCourseService;
import service.train.TrainEvaNormService;
import service.train.TrainEvaRankService;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CmTag {

    static ApplicationContext context = ApplicationContextSupport.getContext();
    static CacheService cacheService = (CacheService) context.getBean("cacheService");
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
    // getBean("IAbroadMapper")， I要大写？
    static IAbroadMapper iAbroadMapper = (IAbroadMapper) context.getBean("IAbroadMapper");
    static CadreAdditionalPostService cadreAdditionalPostService = (CadreAdditionalPostService) context.getBean("cadreAdditionalPostService");
    static CadreEduService cadreEduService = (CadreEduService) context.getBean("cadreEduService");
    static DispatchCadreRelateService dispatchCadreRelateService = (DispatchCadreRelateService) context.getBean("dispatchCadreRelateService");
    static ModifyCadreAuthService modifyCadreAuthService = (ModifyCadreAuthService) context.getBean("modifyCadreAuthService");

    static CisInspectorService cisInspectorService = (CisInspectorService) context.getBean("cisInspectorService");
    static CisInspectObjService cisInspectObjService = (CisInspectObjService) context.getBean("cisInspectObjService");
    static TrainEvaNormService trainEvaNormService = (TrainEvaNormService) context.getBean("trainEvaNormService");
    static TrainEvaRankService trainEvaRankService = (TrainEvaRankService) context.getBean("trainEvaRankService");
    static TrainCourseService trainCourseService = (TrainCourseService) context.getBean("trainCourseService");

    static CrsRequireRuleService crsRequireRuleService = (CrsRequireRuleService) context.getBean("crsRequireRuleService");
    static CrsPostService crsPostService = (CrsPostService) context.getBean("crsPostService");

    public static String toJSONObject(Object obj) {

        if (obj == null) return "{}";
        String jsonStr = JSONUtils.toString(obj);

        return StringUtils.isBlank(jsonStr) ? "{}" : jsonStr;
    }

    public static String toJSONArray(List list) {

        if (list == null) return "[]";
        String jsonStr = JSONUtils.toString(list);

        return StringUtils.isBlank(jsonStr) ? "[]" : jsonStr;
    }

    // 获取菜单显示处理数量
    public static Integer getMenuCacheCount(String countCacheKeys) {

        return cacheService.getCacheCount(countCacheKeys);
    }

    public static String getJsFilePath(String jsFileName) {

        return cacheService.getJsFolder() + File.separator + jsFileName;
    }

    public static String getCssFilePath(String cssFileName) {

        return cacheService.getCssFolder() + File.separator + cssFileName;
    }

    public static HtmlFragment getHtmlFragment(String code) {

        return htmlFragmentService.codeKeyMap().get(code);
    }

    public static HtmlFragment getHtmlFragment(Integer id) {

        return htmlFragmentMapper.selectByPrimaryKey(id);
    }

    // 用于jsp页面显示党组织名称
    public static String displayParty(Integer partyId, Integer branchId) {

        String html = "<span class=\"{0}\">{1}<span><span class=\"{2}\">{3}<span>";
        Party party = null;
        Branch branch = null;
        if (partyId != null) {
            Map<Integer, Party> partyMap = partyService.findAll();
            party = partyMap.get(partyId);
        }
        if (branchId != null) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            branch = branchMap.get(branchId);
        }

        return MessageFormat.format(html, (party != null && party.getIsDeleted()) ? "delete" : "", party != null ? party.getName() : "",
                (branch != null && branch.getIsDeleted()) ? "delete" : "", branch != null ? (party != null ? " - " : "") + branch.getName() : "");
    }

    public static String getApplyStatus(MemberApplyView memberApply) {
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
                if (memberApply.getGrowStatus() == null) {
                    stage = "待组织部审核";
                } else if (memberApply.getGrowStatus() == SystemConstants.APPLY_STATUS_OD_CHECKED) {
                    stage = "组织部已审核，待支部发展为预备党员";
                } else if (memberApply.getGrowStatus() == SystemConstants.APPLY_STATUS_UNCHECKED) {
                    stage = "支部已提交，待分党委审核";
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

    public static List<SysResource> getSysResourcePath(Integer id) {

        List<SysResource> sysResources = new ArrayList<>();
        if (id != null && id > 1) { // 不包含顶级节点
            Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources();
            SysResource sysResource = resourceMap.get(id);
            String parentIds = sysResource.getParentIds();
            if (StringUtils.isNotBlank(parentIds)) {
                for (String _parentId : parentIds.split("/")) {
                    Integer parentId = Integer.valueOf(_parentId);
                    if (parentId > 1) { // 不包含顶级节点
                        SysResource _sysResource = resourceMap.get(parentId);
                        if (_sysResource != null) sysResources.add(_sysResource);
                    }
                }
            }
            if (sysResource != null) sysResources.add(sysResource);
        }
        return sysResources;
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

    public static Set getParentSet(String _path) {

        Set parentSet = new LinkedHashSet();
        SysResource sysResource = sysResourceService.getByUrl(_path);
        if (sysResource == null) return parentSet;

        Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources();

        String parentIds = sysResource.getParentIds();
        for (String id : parentIds.split("/")) {
            SysResource _sysResource = resourceMap.get(Integer.valueOf(id));
            if (_sysResource != null) parentSet.add(_sysResource);
        }
        return parentSet;
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

    public static CadreView getCadreById(Integer id) {

        Map<Integer, CadreView> cadreMap = cadreService.findAll();
        return cadreMap.get(id);
    }

    public static CadreView getCadreByUserId(Integer userId) {

        return cadreService.dbFindByUserId(userId);
    }

    public static List<CadreAdditionalPost> getCadreAdditionalPosts(Integer cadreId) {

        return cadreAdditionalPostService.findCadrePosts(cadreId);
    }

    // 主职
    public static CadrePost getCadreMainCadrePostById(Integer id) {
        return cadrePostService.getCadreMainCadrePostById(id);
    }

    public static CadrePost getCadreMainCadrePost(int caderId) {
        return cadrePostService.getCadreMainCadrePost(caderId);
    }

    // 现任职务
    public static CadreAdminLevel getPresentByCadreId(int caderId) {
        CadrePost mainCadrePost = getCadreMainCadrePost(caderId);
        return cadreAdminLevelService.getPresentByCadreId(caderId,
                mainCadrePost != null ? mainCadrePost.getAdminLevelId() : null);
    }

    public static CadreFamliy getCadreFamliy(Integer id) {
        return cadreFamliyService.get(id);
    }

    public static SysUserView getUserById(Integer id) {

        if (id == null) return null;

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

    public static Boolean isPresentBranchAdmin(Integer userId, Integer partyId, Integer branchId) {
        return branchMemberService.isPresentAdmin(userId, partyId, branchId);
    }

    public static Boolean isPresentPartyAdmin(Integer userId, Integer partyId) {
        return partyMemberService.isPresentAdmin(userId, partyId);
    }

    // 是否直属党支部
    public static Boolean isDirectBranch(Integer partyId) {
        if (partyId == null) return false;
        return partyService.isDirectBranch(partyId);
    }

    // 是否分党委
    public static Boolean isParty(Integer partyId) {
        return partyService.isParty(partyId);
    }

    // 是否党总支
    public static Boolean isPartyGeneralBranch(Integer partyId) {
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

    public static Dispatch getDispatch(Integer dispatchId) {

        return dispatchService.findAll().get(dispatchId);
    }

    public static DispatchCadre getDispatchCadre(Integer dispatchCadreId) {

        return dispatchCadreService.findAll().get(dispatchCadreId);
    }

    public static Integer getDispatchCadreCount(Integer dispatchId, Byte type) {
        return dispatchCadreService.count(dispatchId, type);
    }

    public static List<DispatchCadreRelate> findDispatchCadreRelates(Integer relateId, Byte relateType) {

        return dispatchCadreRelateService.findDispatchCadreRelates(relateId, relateType);
    }

    public static DispatchUnit getDispatchUnit(Integer dispatchUnitId) {
        return dispatchUnitService.findAll().get(dispatchUnitId);
    }

    // 发文号
    public static String getDispatchCode(Integer code, Integer dispatchTypeId, Integer year) {

        if (code == null || dispatchTypeId == null || year == null) return null;

        String numStr = NumberUtils.frontCompWithZore(code, 2);
        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        DispatchType dispatchType = dispatchTypeMap.get(dispatchTypeId);
        return String.format("%s[%s]%s号", dispatchType.getName(), year, numStr);
    }

    public static DispatchType getDispatchType(Integer dispatchTypeId) {

        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        return dispatchTypeMap.get(dispatchTypeId);
    }

    public static ApplySelf getApplySelf(Integer applyId) {
        return applySelfService.get(applyId);
    }

    public static List<ApplySelfModifyBean> getApplySelfModifyList(Integer applyId) {
        return iAbroadMapper.getApplySelfModifyList(applyId);
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

    public static Map<Integer, SafeBox> getSafeBoxMap() {

        return safeBoxService.findAll();
    }

    // 证件
    public static Passport getPassport(Integer id) {

        return passportMapper.selectByPrimaryKey(id);
    }
    // 拒绝归还证件借出记录
    public static PassportDraw getRefuseReturnPassportDraw(Integer passportId) {

        return passportDrawService.getRefuseReturnPassportDraw(passportId);
    }

    public static List<PassportDrawFile> getPassportDrawFiles(Integer id) {

        return passportDrawService.getPassportDrawFiles(id);
    }

    public static ApprovalLog getApprovalLog(Integer applySelfId, Integer approverTypeId) {

        return approvalLogService.getApprovalLog(applySelfId, approverTypeId);
    }

    // 判断干部是否拥有直接修改本人干部信息的权限
    public static Boolean hasDirectModifyCadreAuth(Integer cadreId) {

        String today = DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD);
        List<ModifyCadreAuth> modifyCadreAuths = modifyCadreAuthService.findAll(cadreId);
        if (modifyCadreAuths == null || modifyCadreAuths.size() == 0) return false;

        for (ModifyCadreAuth modifyCadreAuth : modifyCadreAuths) {

            // 永久有效
            if (BooleanUtils.isTrue(modifyCadreAuth.getIsUnlimited())) return true;

            if (modifyCadreAuth.getStartTime() == null && modifyCadreAuth.getEndTime() == null) {
                continue; // 不可能出现的情况，因为不是永久有效，则一定要有开始或结束时间
            }
            if (modifyCadreAuth.getStartTime() != null) {
                String startTime = DateUtils.formatDate(modifyCadreAuth.getStartTime(), DateUtils.YYYY_MM_DD);
                if (startTime.compareTo(today) > 0) continue;
            }
            if (modifyCadreAuth.getEndTime() != null) {
                String endTime = DateUtils.formatDate(modifyCadreAuth.getEndTime(), DateUtils.YYYY_MM_DD);
                if (endTime.compareTo(today) < 0) continue;
            }

            return true;
        }

        return false;
    }

    public static CadreEdu[] getCadreEdus(Integer cadreId) {
        return cadreEduService.getByLearnStyle(cadreId);
    }

    public static List<CisInspectorView> getCisInspectors(Integer objId) {

        return cisInspectObjService.getInspectors(objId);
    }

    public static CisInspectorView getCisInspector(Integer id) {

        return cisInspectorService.getInspector(id);
    }

    public static Map<Integer, TrainEvaNorm> getTrainEvaNorms(Integer evaTableId) {

        return trainEvaNormService.findAll(evaTableId);
    }

    public static Map<Integer, TrainEvaRank> getTrainEvaRanks(Integer evaTableId) {

        return trainEvaRankService.findAll(evaTableId);
    }

    public static Integer evaIsClosed(Integer courseId) {

        return trainCourseService.evaIsClosed(courseId);
    }


    public static Map<Integer, CrsRequireRule> getCrsRequireRules(Integer postRequireId) {

        return crsRequireRuleService.findAll(postRequireId);
    }

    public static CrsPost getCrsPost(Integer id) {

        return crsPostService.get(id);
    }
}
