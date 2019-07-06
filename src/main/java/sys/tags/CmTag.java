package sys.tags;

import domain.base.ContentTpl;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.*;
import domain.dispatch.*;
import domain.modify.ModifyCadreAuth;
import domain.party.Branch;
import domain.party.Party;
import domain.party.RetireApply;
import domain.ps.PsInfo;
import domain.sys.HtmlFragment;
import domain.sys.SysConfig;
import domain.sys.SysResource;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import persistence.dispatch.DispatchCadreMapper;
import persistence.dispatch.DispatchMapper;
import persistence.dispatch.DispatchUnitMapper;
import persistence.sys.HtmlFragmentMapper;
import service.base.ContentTplService;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.cadre.*;
import service.dispatch.DispatchCadreRelateService;
import service.dispatch.DispatchCadreService;
import service.dispatch.DispatchTypeService;
import service.ext.ExtService;
import service.global.CacheService;
import service.member.RetireApplyService;
import service.modify.ModifyCadreAuthService;
import service.party.BranchService;
import service.party.PartyService;
import service.ps.PsInfoService;
import service.sys.*;
import service.unit.UnitService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import java.io.File;
import java.util.*;

public class CmTag {

    private static Logger logger = LoggerFactory.getLogger(CmTag.class);

    public static ApplicationContext context = ApplicationContextSupport.getContext();
    static CacheService cacheService = context.getBean(CacheService.class);
    static HtmlFragmentService htmlFragmentService = context.getBean(HtmlFragmentService.class);
    static HtmlFragmentMapper htmlFragmentMapper = context.getBean(HtmlFragmentMapper.class);
    static ContentTplService contentTplService = context.getBean(ContentTplService.class);
    static SysConfigService sysConfigService = context.getBean(SysConfigService.class);
    static SysPropertyService sysPropertyService = context.getBean(SysPropertyService.class);

    static SysUserService sysUserService = context.getBean(SysUserService.class);
    static SysResourceService sysResourceService = context.getBean(SysResourceService.class);
    static MetaTypeService metaTypeService = context.getBean(MetaTypeService.class);
    static MetaClassService metaClassService = context.getBean(MetaClassService.class);

    static ExtService extService = context.getBean(ExtService.class);

    static UnitService unitService = context.getBean(UnitService.class);
    static PartyService partyService = context.getBean(PartyService.class);
    static BranchService branchService = context.getBean(BranchService.class);
    static PsInfoService psInfoService = context.getBean(PsInfoService.class);

    static CadreService cadreService = context.getBean(CadreService.class);
    static CadrePostService cadrePostService = context.getBean(CadrePostService.class);
    static CadreAdminLevelService cadreAdminLevelService = context.getBean(CadreAdminLevelService.class);
    static CadreFamilyService cadreFamilyService = context.getBean(CadreFamilyService.class);
    static CadreEduService cadreEduService = context.getBean(CadreEduService.class);

    public static <T> T getBean(Class<T> cls){

        T bean = null;
        try {

            bean = context.getBean(cls);
        }catch (Exception ex){
            logger.warn(ex.getMessage());
        }

        return bean;
    }

    public static SysConfig getSysConfig() {

        return sysConfigService.get();
    }

    public static Boolean isSuperAccount(String username){

        String users = CmTag.getStringProperty("superUsers", "zzbgz");
        if(StringUtils.isBlank(users)) return false;

        Set<String> userSet = new HashSet<>();
        userSet.addAll(Arrays.asList(users.split(",")));

        return userSet.contains(username);
    }

    // 获取菜单显示处理数量
    public static Integer getMenuCacheCount(String countCacheKeys) {

        return cacheService.getCacheCount(countCacheKeys);
    }

     // 获取系统的属性值（字符串类型）
    public static String getStringProperty(String key){
        return sysPropertyService.findAll().get(key);
    }
    // 获取系统的属性值（字符串类型）
    public static String getStringProperty(String key, String defaultStr){
        if(StringUtils.isBlank(key)) return defaultStr;
        String str = sysPropertyService.findAll().get(key);

        return StringUtils.defaultIfBlank(str, defaultStr);
    }
    // 获取系统的属性值（整数）
    public static Integer getIntProperty(String key){
        return Integer.valueOf(getStringProperty(key));
    }
    // 获取系统的属性值（布尔类型）
    public static boolean getBoolProperty(String key){
        return Boolean.valueOf(getStringProperty(key));
    }
    // 获取系统的属性值（日期）
    public static Date getDateProperty(String key){
        return DateUtils.parseStringToDate(getStringProperty(key));
    }

    public static boolean validMobile(String mobile){

        return FormUtils.match(CmTag.getStringProperty("mobileRegex"), mobile);
    }
    public static boolean validUsername(String username){
        return FormUtils.match(CmTag.getStringProperty("usernameRegex"), username);
    }
    public static boolean validPasswd(String passwd){
        return FormUtils.match(CmTag.getStringProperty("passwdRegex"), passwd);
    }

    public static String getJsFolder(){

        return ConfigUtil.defaultHomePath() + File.separator + "js";
    }

    public static String getImgFolder(){

        return ConfigUtil.defaultHomePath() + File.separator + "img" + File.separator;
    }

    public static String getAbsolutePath(String relativePath) {

        try {
            return new File(ConfigUtil.defaultHomePath() + File.separator + relativePath).getCanonicalPath();
        }catch (Exception e){
            logger.error("异常", e);
        }

        return null;
    }

    public static ContentTpl getContentTpl(String code) {

        return contentTplService.codeKeyMap().get(code);
    }

    public static HtmlFragment getHtmlFragment(String code) {

        return htmlFragmentService.codeKeyMap().get(code);
    }

    public static HtmlFragment getHtmlFragment(Integer id) {

        return htmlFragmentMapper.selectByPrimaryKey(id);
    }

    public static List<SysResource> getSysResourcePath(Integer id, Boolean isMobile) {

        List<SysResource> sysResources = new ArrayList<>();
        if (id != null && id > 1) { // 不包含顶级节点
            Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources(isMobile);
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

    // 只用于网站导航
    public static SysResource getSysResource(Integer id, Boolean isMobile) {

        Map<Integer, SysResource> sortedSysResources = sysResourceService.getSortedSysResources(isMobile);

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

        Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources(sysResource.getIsMobile());

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

    public static Unit getUnitById(Integer id) {

        if(id==null) return null;

        Map<Integer, Unit> unitMap = unitService.findAll();
        return unitMap.get(id);
    }

    public static CadreView getCadreById(Integer id) {

        if(id==null) return null;

        Map<Integer, CadreView> cadreMap = cadreService.findAll();
        return cadreMap.get(id);

        /*CadreViewMapper cadreViewMapper = getBean(CadreViewMapper.class);
        return  iCadreMapper.getCadre(id);*/
    }

    public static CadreView getCadreByUserId(Integer userId) {

        if(userId==null) return null;

        return cadreService.dbFindByUserId(userId);
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
                mainCadrePost != null ? mainCadrePost.getAdminLevel() : null);
    }

    public static CadreFamily getCadreFamily(Integer id) {
        return cadreFamilyService.get(id);
    }

    public static Party getParty(Integer id) {

        if (id == null) return null;

        return partyService.findAll().get(id);
    }

    public static Branch getBranch(Integer id) {

        if (id == null) return null;

        return branchService.findAll().get(id);
    }
    public static PsInfo getPsInfo(Integer id) {

        if (id == null) return null;

        return psInfoService.findAll().get(id);
    }
    public static SysUserView getUserById(Integer id) {

        if (id == null) return null;

        return sysUserService.findById(id);
    }

    public static SysUserView getUserByCode(String code) {

        if(StringUtils.isBlank(code)) return null;

        return sysUserService.findByCode(code);
    }

    public static SysUserView getUserByUsername(String username) {

        if(StringUtils.isBlank(username)) return null;

        return sysUserService.findByUsername(username);
    }

    public static Set<String> findRoles(String username) {

        return sysUserService.findRoles(username);
    }
    public static Boolean hasRole(String username, String role) {

        Set<String> roles = sysUserService.findRoles(username);
        return (roles!=null && StringUtils.isNotBlank(role))?roles.contains(role):false;
    }

    public static Set<String> findPermissions(String username, Boolean isMobile) {

        return sysUserService.findPermissions(username, isMobile);
    }

    public static String getUserUnit(Integer userId) {

        return extService.getUnit(userId);
    }

    public static Unit getUnit(Integer unitId) {

        if(unitId==null) return null;

        return unitService.findAll().get(unitId);
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

        RetireApplyService retireApplyService = getBean(RetireApplyService.class);

        return retireApplyService.get(userId);
    }

    public static Dispatch getDispatch(Integer dispatchId) {

        DispatchMapper dispatchMapper = getBean(DispatchMapper.class);
        if(dispatchMapper==null) return null;
        return dispatchMapper.selectByPrimaryKey(dispatchId);
    }

    public static DispatchCadre getDispatchCadre(Integer dispatchCadreId) {

        DispatchCadreMapper dispatchCadreMapper = getBean(DispatchCadreMapper.class);
        if(dispatchCadreMapper==null) return null;
        return dispatchCadreMapper.selectByPrimaryKey(dispatchCadreId);
    }

    public static Integer getDispatchCadreCount(Integer dispatchId, Byte type) {

        DispatchCadreService dispatchCadreService = getBean(DispatchCadreService.class);
        if(dispatchCadreService==null) return null;
        return dispatchCadreService.count(dispatchId, type);
    }

    public static List<DispatchCadreRelate> findDispatchCadreRelates(Integer relateId, Byte relateType) {

        DispatchCadreRelateService dispatchCadreRelateService = getBean(DispatchCadreRelateService.class);
        if(dispatchCadreRelateService==null) return null;
        return dispatchCadreRelateService.findDispatchCadreRelates(relateId, relateType);
    }

    public static DispatchUnit getDispatchUnit(Integer dispatchUnitId) {

        DispatchUnitMapper dispatchUnitMapper = getBean(DispatchUnitMapper.class);
        if(dispatchUnitMapper==null) return null;
        return dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
    }

    // 发文号
    public static String getDispatchCode(String code, Integer dispatchTypeId, Integer year) {

        if (dispatchTypeId == null || year == null) return null;
        /*String numStr = null;
        if(code!=null) {
            numStr = NumberUtils.frontCompWithZore(code, 2);
        }*/

        DispatchTypeService dispatchTypeService = getBean(DispatchTypeService.class);
        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        DispatchType dispatchType = dispatchTypeMap.get(dispatchTypeId);

        return String.format("%s〔%s〕%s号", dispatchType.getName(), year, StringUtils.trimToEmpty(code));
    }

    public static DispatchType getDispatchType(Integer dispatchTypeId) {

        DispatchTypeService dispatchTypeService = getBean(DispatchTypeService.class);
        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        return dispatchTypeMap.get(dispatchTypeId);
    }

    public static Boolean canUpdate(Integer cadreId, String name) {

        CadreInfoCheckService cadreInfoCheckService = getBean(CadreInfoCheckService.class);
        return cadreInfoCheckService.canUpdate(cadreId, name);
    }

    public static Byte cadreInfoCheck(Integer cadreId, String name, Integer type){

        CadreInfoCheckService cadreInfoCheckService = getBean(CadreInfoCheckService.class);
        if(type==1)
            return cadreInfoCheckService.baseCheck(cadreId, name);
        else if(type==2)
            return cadreInfoCheckService.staffCheck(cadreId, name);
        else if(type==7)
            return cadreInfoCheckService.cadreCheck(cadreId, name);
        else if(type==3)
            return cadreInfoCheckService.cadreInfoModifyCheck(cadreId, name);
        else if(type==4)
            return cadreInfoCheckService.cadreInfoExistCheck(cadreId, name);
        else if(type==5)
            return cadreInfoCheckService.cadreHighEduCheck(cadreId);
        else if(type==6)
            return cadreInfoCheckService.cadreHighDegreeCheck(cadreId);
        else if(type==8)
            return cadreInfoCheckService.familyCheck(cadreId);
        else if(type==9)
            return cadreInfoCheckService.tutorCheck(cadreId);

        return null;
    }
    public static Byte cadreResearchCheck(Integer cadreId, Byte type){

        CadreInfoCheckService cadreInfoCheckService = getBean(CadreInfoCheckService.class);
        return cadreInfoCheckService.cadreResearchCheck(cadreId, type);
    }

    public static Byte cadreRewardCheck(Integer cadreId, Byte type){

        CadreInfoCheckService cadreInfoCheckService = getBean(CadreInfoCheckService.class);
        return cadreInfoCheckService.cadreRewardCheck(cadreId, type);
    }

    public static Boolean canDirectUpdateCadreInfo(Integer caderId){
        // 拥有管理干部信息或管理干部本人信息的权限，不允许提交申请
        return ((ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN) &&
                !ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREONLYVIEW))
                || (ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMINSELF)
                && CmTag.hasDirectModifyCadreAuth(caderId)));
    }
    // 判断干部是否拥有直接修改本人干部信息的权限
    public static Boolean hasDirectModifyCadreAuth(Integer cadreId) {

        if(cadreId==null) return false;

        ModifyCadreAuthService modifyCadreAuthService = getBean(ModifyCadreAuthService.class);
        if(modifyCadreAuthService==null) return false;

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

    // 如果是两个字的名字，则中间空2个空字符
    public static String realnameWithEmpty(String realname){

        if(realname.length()==2){
            realname = realname.charAt(0) + "  " + realname.charAt(1);
        }

        return realname;
    }

    // 读取学历名称，用于任免审批表
    public static String getEduName(int eduId){

        MetaType bk = CmTag.getMetaTypeByCode("mt_edu_bk");
        MetaType master = CmTag.getMetaTypeByCode("mt_edu_master");
        MetaType doctor = CmTag.getMetaTypeByCode("mt_edu_doctor");

        if(eduId == bk.getId()){
            return "大学";
        }else if(eduId == master.getId() || eduId == doctor.getId()){
            return "研究生";
        }

        MetaType edu = CmTag.getMetaType(eduId);
        return edu.getName();
    }

    // 获取干部的党派
    public static Map<String, String> getCadreParty(Boolean isOw, Date owGrowTime,
                                                    String defaultOwName,
                                                    Integer dpTypeId,
                                                    Date dpGrowTime,
                                                    Boolean useDpShortName){
        String partyName = null;// 党派
        String partyAddTime = null; // 入党时间
        isOw = BooleanUtils.isTrue(isOw);
        useDpShortName = BooleanUtils.isTrue(useDpShortName);

        if(isOw){

            partyName = defaultOwName;
            partyAddTime = (owGrowTime==null)?"-":DateUtils.formatDate(owGrowTime, DateUtils.YYYYMM);
        }

        if(dpTypeId!=null && dpTypeId>0){

            MetaType metaType = CmTag.getMetaType(dpTypeId);
            partyName = ((partyName!=null)?(partyName+","):"")
                    + (useDpShortName?StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName()):metaType.getName());

            if(isOw){
                partyAddTime = partyAddTime+"," + ((dpGrowTime == null)?"-":DateUtils.formatDate(dpGrowTime, DateUtils.YYYYMM));
            }else{
                partyAddTime = (dpGrowTime == null)?"-":DateUtils.formatDate(dpGrowTime, DateUtils.YYYYMM);
            }
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("partyName", partyName);
        resultMap.put("growTime", partyAddTime);

        return resultMap;
    }

    public static CadreEdu[] getCadreEdus(Integer cadreId) {
        return cadreEduService.getByLearnStyle(cadreId);
    }

    public static String getShortPic(String picPath) {

        return (picPath.contains(".") ? picPath.substring(0, picPath.lastIndexOf(".")) : picPath) + "_s.jpg";
    }
}
