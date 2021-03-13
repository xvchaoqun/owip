package controller.cadre.mobile;

import controller.BaseController;
import controller.analysis.CadreCategorySearchBean;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.unit.Unit;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.cadre.common.ICadreWorkMapper;
import service.unit.UnitPostAllocationInfoBean;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/m")
public class MobileCadreSearchController extends BaseController {

	@Autowired
	private ICadreWorkMapper iCadreWorkMapper;
	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre")
	public String cadre(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_page")
	public String cadre_page() {

		return "cadre/mobile/cadre_page";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_compare")
	public String cadre_compare(Integer[] cadreIds, ModelMap modelMap) {

		if(cadreIds!=null && cadreIds.length>0) {
			CadreViewExample example = new CadreViewExample();
			example.createCriteria().andIdIn(Arrays.asList(cadreIds));
			List<CadreView> cadres = cadreViewMapper.selectByExample(example);
			modelMap.put("cadres", cadres);
		}

		return "cadre/mobile/cadre_compare";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_compare_result")
	public String cadre_compare_result(Integer[] cadreIds, ModelMap modelMap) {

		if(cadreIds!=null && cadreIds.length>0) {
			CadreViewExample example = new CadreViewExample();
			example.createCriteria().andIdIn(Arrays.asList(cadreIds));
			List<CadreView> cadres = cadreViewMapper.selectByExample(example);

			modelMap.put("cadres", cadres);
		}

		return "cadre/mobile/cadre_compare_result";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_search_byName")
	public String cadre_search_byName(ModelMap modelMap) {

		return "cadre/mobile/cadre_search_byName";
	}
	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_search_byUnit")
	public String cadre_search_byUnit(ModelMap modelMap) {

		return "cadre/mobile/cadre_search_byUnit";
	}
	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_search")
	public String cadre_search(ModelMap modelMap) {

		return "cadre/mobile/cadre_search";
	}

	@RequiresPermissions("m:cadre:view")
	@RequestMapping("/cadre_info")
	public String cadre_info(Integer cadreId, ModelMap modelMap) {

		if(cadreId==null){
			// 默认读取本人信息
			int userId = ShiroHelper.getCurrentUserId();
			CadreView cadreView = cadreService.dbFindByUserId(userId);
			if(cadreView!=null){
				cadreId = cadreView.getId();
			}
		}

		cadreCommonService.cadreBase(cadreId, modelMap);

		return "cadre/mobile/cadre_info";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/unit_cadre_info")
	public String unit_cadre_info(Integer unitId, ModelMap modelMap) {

		if(unitId==null){
			// 默认读取本人信息
			int userId = ShiroHelper.getCurrentUserId();
			CadreView cadreView = cadreService.dbFindByUserId(userId);
			if(cadreView!=null){
				unitId = cadreView.getUnitId();
			}
		}

		if(unitId!=null){

			List<UnitPostAllocationInfoBean> cjCpcInfoBeans
					= unitPostAllocationService.cpcInfo_data(unitId, CadreConstants.CADRE_CATEGORY_CJ, false);
			if(cjCpcInfoBeans.size()==2){
				modelMap.put("cjBean", cjCpcInfoBeans.get(0));
			}
			if(CmTag.getBoolProperty("hasKjCadre")) {
				List<UnitPostAllocationInfoBean> kjCpcInfoBeans
						= unitPostAllocationService.cpcInfo_data(unitId, CadreConstants.CADRE_CATEGORY_KJ, false);
				if (kjCpcInfoBeans.size() == 2) {
					modelMap.put("kjBean", kjCpcInfoBeans.get(0));
				}
			}
		}

		return "cadre/mobile/unit_cadre_info";
	}

	@RequiresPermissions("m:cadreList")
	@RequestMapping("/cadreList")
	public String cadreList() {

		return "mobile/index";
	}

	@RequiresPermissions("m:cadreList")
	@RequestMapping("/cadreList_page")
	public String cadreList_page(HttpServletResponse response,String realnameOrCode,
								 @RequestParam(required = false, defaultValue = "2") Byte type,
								 Integer pageSize, Integer pageNo, ModelMap modelMap) {

		if (null == pageSize) {
			pageSize = 11;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		CadreViewExample example = new CadreViewExample();
		example.setOrderByClause("sort_order desc");
		CadreViewExample.Criteria criteria = example.createCriteria();

		if (type == 1){
			criteria.andStatusEqualTo(CadreConstants.CADRE_STATUS_LEADER);
		}else if (type == 2){
			criteria.andStatusEqualTo(CadreConstants.CADRE_STATUS_CJ_LEAVE);
		}

		if (StringUtils.isNotBlank(realnameOrCode)){
			criteria.andRealnameOrCodeLike(realnameOrCode);
		}

		long count = cadreViewMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}

		List<CadreView> cadreViews = cadreViewMapper.selectByExampleWithRowbounds(example,new RowBounds((pageNo - 1) * pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		modelMap.put("cadres",cadreViews);
		modelMap.put("commonList",commonList);
		modelMap.put("realnameOrCode",realnameOrCode);

		return "cadre/mobile/cadreList_page";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_advanced_search")
	public String cadre_advanced_search(Integer cadreId,
										Integer[] dpTypes,
										String[] nation,
										String[] staffTypes,
										Integer[] labels,
										String[] authorizedTypes,
										Integer[] unitTypes,
										Integer[] unitIds,
										Integer[] adminLevels,
										Integer[] maxEdus,
										Integer[] postTypes,
										String[] proPosts,
										String[] proPostLevels,
										Integer[] workTypes,
										Byte[] leaderTypes,

										ModelMap modelMap) {

		modelMap.put("authorizedTypes", CmTag.getPropertyCaches("authorizedTypes"));

		if (cadreId != null) {
			CadreView cadre = iCadreMapper.getCadre(cadreId);
			modelMap.put("cadre", cadre);
		}

		Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
		Map<Integer, List<Integer>> historyUnitListMap = new LinkedHashMap<>();
		Map<Integer, Unit> unitMap = unitService.findAll();
		for (Unit unit : unitMap.values()) {

			Integer unitTypeId = unit.getTypeId();
			if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY){
				List<Integer> units = historyUnitListMap.get(unitTypeId);
				if (units == null) {
					units = new ArrayList<>();
					historyUnitListMap.put(unitTypeId, units);
				}
				units.add(unit.getId());
			}else {
				List<Integer> units = unitListMap.get(unitTypeId);
				if (units == null) {
					units = new ArrayList<>();
					unitListMap.put(unitTypeId, units);
				}
				units.add(unit.getId());
			}
		}
		modelMap.put("unitListMap", unitListMap);
		modelMap.put("historyUnitListMap", historyUnitListMap);

		modelMap.put("proPosts", CmTag.getPropertyCaches("teacherProPosts"));
		modelMap.put("proPostLevels", SystemConstants.PRO_POST_LEVEL_MAP.values());
		modelMap.put("staffTypes", CmTag.getPropertyCaches("staffTypes"));
		modelMap.put("authorizedTypes", CmTag.getPropertyCaches("authorizedTypes"));

		if (dpTypes != null) {
			modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
		}
		if (nation != null) {
			modelMap.put("selectNation",Arrays.asList(nation));
		}
		if (staffTypes != null) {
			modelMap.put("selectStaffTypes",Arrays.asList(staffTypes));
		}
		if (labels != null) {
			modelMap.put("selectLabels",Arrays.asList(labels));
		}
		if (authorizedTypes != null) {
			modelMap.put("selectAuthorizedTypes", Arrays.asList(authorizedTypes));
		}
		if (unitTypes != null) {
			modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
		}
		if (unitIds != null) {
			modelMap.put("selectUnitIds",Arrays.asList(unitIds));
		}
		if (adminLevels != null) {
			modelMap.put("selectAdminLevels",Arrays.asList(adminLevels));
		}
		if (maxEdus != null) {
			modelMap.put("selectMaxEdus",Arrays.asList(maxEdus));
		}
		if (postTypes != null) {
			modelMap.put("selectPostTypes",Arrays.asList(postTypes));
		}
		if (proPosts != null) {
			modelMap.put("selectProPosts",Arrays.asList(proPosts));
		}
		if (proPostLevels != null) {
			 modelMap.put("selectProPostLevels", Arrays.asList(proPostLevels));
		}
		if (workTypes != null) {
			modelMap.put("selectWorkTypes",Arrays.asList(workTypes));
		}
		if (leaderTypes != null) {
			modelMap.put("selectLeaderTypes",Arrays.asList(leaderTypes));
		}

		return "cadre/mobile/cadre_advanced_search";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_advanced_search_result")
	public String cadre_advanced_search_result(@RequestParam(required = false, defaultValue = CadreConstants.CADRE_STATUS_CJ + "") Byte status,
											   Integer cadreId,
											   Byte gender,
											   Integer startAge,
											   Integer endAge,
											   Integer startDpAge, // 党龄
											   Integer endDpAge, // 党龄
											   Integer startNowPostAge,
											   Integer endNowPostAge,
											   Integer startNowLevelAge,
											   Integer endNowLevelAge,
											   Integer[] workTypes,
											   Boolean andWorkTypes,
											   String major, // 所学专业
											   Boolean isPrincipal, // 是否正职
											   Boolean isDouble, // 是否双肩挑
											   Boolean hasCrp, // 是否有干部挂职经历
											   Boolean hasAbroadEdu, // 是否有国外学习经历
											   Integer type,
											   Byte degreeType,
											   Integer state,
											   String title,
											   Byte firstUnitPost, // 第一主职是否已关联岗位（1：关联 0： 没关联 -1：缺第一主职）
											   @DateTimeFormat(pattern = "YYYY-MM-dd")Date startBirth,
											   @DateTimeFormat(pattern = "YYYY-MM-dd")Date endBirth,
											   @DateTimeFormat(pattern = "YYYY-MM-dd")Date startCadreGrowTime,
											   @DateTimeFormat(pattern = "YYYY-MM-dd")Date endCadreGrowTime,
											   String[] nation,
											   Integer[] dpTypes, // 党派
											   Integer[] unitIds, // 所在单位
											   Integer[] unitTypes, // 部门属性
											   Integer[] adminLevels, // 行政级别
											   Integer[] maxEdus, // 最高学历
											   Integer[] postTypes, // 职务属性
											   String[] proPosts, // 专业技术职务
											   String[] proPostLevels, // 职称级别
											   Byte[] leaderTypes, // 是否班子负责人
											   Integer[] labels, // 标签
											   String[] staffTypes,
											   String[] authorizedTypes,
											   //是否为保留待遇干部信息，指第一主职无关联岗位的干部
											   @RequestParam(required = false, defaultValue = "0") Boolean isKeepSalary,
											   //是否聘任制干部，指无行政级别的干部
											   @RequestParam(required = false, defaultValue = "0") Boolean isEngage,
											   Integer pageSize, Integer pageNo,ModelMap modelMap) {

		if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREARCHIVE)) {
			throw new UnauthorizedException("没有权限访问");
		}

		if (null == pageSize) {
			pageSize = 11;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		CadreViewExample example = new CadreViewExample();
		example.setOrderByClause("sort_order desc");
		CadreViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);

		String searchStr = "&pageSize=" + pageSize;
		searchStr += "&status="+status;
		if (cadreId != null) {
			criteria.andIdEqualTo(cadreId);
			searchStr += "&cadreId="+cadreId;
		}
		if (gender != null) {
			criteria.andGenderEqualTo(gender);
			searchStr += "&gender="+gender;
		}
		if (dpTypes != null) {
			criteria.andDpTypeIdIn(new HashSet<>(Arrays.asList(dpTypes)));
			searchStr += "&dpTypes="+ StringUtils.join(dpTypes,",");
		}
		if (staffTypes != null) {
			criteria.andStaffTypeIn(Arrays.asList(staffTypes));
			searchStr += "&staffTypes="+StringUtils.join(staffTypes,",");
		}
		if (nation != null) {
			Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_nation");
			Set<String> nations = metaTypeMap.values()
					.stream().map(MetaType::getName).collect(Collectors.toSet());

			criteria.andNationIn(Arrays.asList(nation), nations);

			searchStr += "&nation="+ StringUtils.join(nation,",");
		}
		if (StringUtils.isNotBlank(title)) {
			criteria.andTitleLike(SqlUtils.trimLike(title));
			searchStr += "&title="+title;
		}
		if (labels != null) {
			criteria.andLabelsContain(new HashSet<>(Arrays.asList(labels)));
			searchStr += "&labels="+StringUtils.join(labels,",");
		}
		if (state != null) {
			criteria.andStateEqualTo(state);
			searchStr += "&state="+state;
		}
		if (authorizedTypes != null) {
			criteria.andAuthorizedTypeIn(Arrays.asList(authorizedTypes));
			searchStr += "&authorizedTypes="+StringUtils.join(authorizedTypes,",");
		}
		if (unitTypes != null) {
			criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
			searchStr += "&unitTypes="+StringUtils.join(unitTypes,",");
		}
		if (startBirth != null) {
			criteria.andBirthGreaterThanOrEqualTo(startBirth);
			searchStr += "&startBirth="+ DateUtils.formatDate(startBirth,DateUtils.YYYY_MM_DD);
		}
		if (endBirth != null) {
			criteria.andBirthLessThanOrEqualTo(endBirth);
			searchStr += "&endBirth="+ DateUtils.formatDate(endBirth,DateUtils.YYYY_MM_DD);
		}
		if (startCadreGrowTime != null) {
			criteria.andGrowTimeGreaterThanOrEqualTo(startCadreGrowTime);
			searchStr += "&startCadreGrowTime="+ DateUtils.formatDate(startCadreGrowTime,DateUtils.YYYY_MM_DD);
		}
		if (endCadreGrowTime != null) {
			criteria.andGrowTimeLessThanOrEqualTo(endCadreGrowTime);
			searchStr += "&endCadreGrowTime="+ DateUtils.formatDate(endCadreGrowTime,DateUtils.YYYY_MM_DD);
		}
		if (unitIds != null) {
			criteria.andUnitIdIn(Arrays.asList(unitIds));
			searchStr += "&unitIds="+StringUtils.join(unitIds,",");
		}
		if (startAge != null) {
			Date brith= DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startAge);
			Date brith_end=DateUtils.getLastDayOfMonth(brith);
			criteria.andBirthLessThanOrEqualTo(brith_end);
			searchStr += "&startAge="+startAge;
		}
		if (endAge != null) {
			Date brith= DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endAge + 1));
			Date brith_start=DateUtils.getFirstDayOfMonth(brith);
			criteria.andBirthGreaterThanOrEqualTo(brith_start);
			searchStr += "&endAge="+endAge;
		}
		if (startDpAge != null) {
			criteria.andGrowTimeLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startDpAge));
			searchStr += "&startDpAge="+startDpAge;
		}
		if (endDpAge != null) {
			criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endDpAge + 1)));
			searchStr += "&endDpAge="+endDpAge;
		}
		if (adminLevels != null) {
			criteria.andAdminLevelIn(Arrays.asList(adminLevels));
			searchStr += "&adminLevels="+StringUtils.join(adminLevels,",");
		}
		if (maxEdus != null) {
			if(new HashSet<>(Arrays.asList(maxEdus)).contains(-1)){
				criteria.andEduIdIsNull();
			}else {
				criteria.andEduIdIn(Arrays.asList(maxEdus));
			}
			searchStr += "&maxEdus="+StringUtils.join(maxEdus,",");
		}
		if(degreeType!=null){
			if(degreeType==-1){
				criteria.andDegreeTypeIsNull();
			}else{
				criteria.andDegreeTypeEqualTo(degreeType);
			}
			searchStr += "&degreeType="+degreeType;
		}
		if(StringUtils.isNotBlank(major)){
			criteria.andMajorLike(SqlUtils.like(major));
			searchStr += "&major="+major;
		}
		if (postTypes != null) {
			criteria.andPostTypeIn(Arrays.asList(postTypes));
			searchStr += "&postTypes="+StringUtils.join(postTypes,",");
		}
		if (startNowPostAge != null) {
			criteria.andCadrePostYearGreaterThanOrEqualTo(startNowPostAge);
			searchStr += "&startNowPostAge="+startNowPostAge;
		}
		if (endNowPostAge != null) {
			criteria.andCadrePostYearLessThanOrEqualTo(endNowPostAge);
			searchStr += "&endNowPostAge="+endNowPostAge;
		}
		if (proPosts != null) {
			List<String> _proPosts = new ArrayList<String>(Arrays.asList(proPosts));
			if (_proPosts.contains("0")) {
				_proPosts.remove("0");
				criteria.andProPostIsNullOrIn(_proPosts);
			}else {
				criteria.andProPostIn(_proPosts);
			}
			searchStr += "&proPosts="+StringUtils.join(proPosts,",");
		}
		if (proPostLevels != null) {
			criteria.andProPostLevelIn(Arrays.asList(proPostLevels));
			searchStr += "&proPostLevels="+StringUtils.join(proPostLevels,",");
		}
		if (firstUnitPost != null) {
			if(firstUnitPost==-1){ // 缺第一主职
				criteria.andMainCadrePostIdIsNull();
			}else if (firstUnitPost==1){ // 第一主职已关联岗位
				criteria.andMainCadrePostIdGreaterThan(0).andUnitPostIdIsNotNull();
			}else if (firstUnitPost==0){ // 第一主职没关联岗位
				criteria.andMainCadrePostIdGreaterThan(0).andUnitPostIdIsNull();
			}
			searchStr += "&firstUnitPost="+firstUnitPost;
		}
		if (isPrincipal != null) {
			criteria.andIsPrincipalEqualTo(isPrincipal);
			searchStr += "&isPrincipal="+(BooleanUtils.isTrue(isPrincipal)?"1":"0");
		}
		if (startNowLevelAge != null) {
			criteria.andAdminLevelYearGreaterThanOrEqualTo(startNowLevelAge);
			searchStr += "&startNowLevelAge="+startNowLevelAge;
		}
		if (endNowLevelAge != null) {
			criteria.andAdminLevelYearLessThanOrEqualTo(endNowLevelAge);
			searchStr += "&endNowLevelAge="+endNowLevelAge;
		}
		if (isDouble != null) {
			criteria.andIsDoubleEqualTo(isDouble);
			searchStr += "&isDouble="+(BooleanUtils.isTrue(isDouble)?"1":"0");
		}
		if (workTypes != null){
			List<Integer> cadreIds = iCadreWorkMapper.getCadreIdsOfWorkTypes(Arrays.asList(workTypes),
					BooleanUtils.isTrue(andWorkTypes), status);
			if(cadreIds.size()==0){
				criteria.andIdIsNull();
			}else {
				criteria.andIdIn(cadreIds);
			}
			searchStr += "&workTypes="+StringUtils.join(workTypes,",");
			searchStr += "&andWorkTypes="+(BooleanUtils.isTrue(andWorkTypes)?"1":"0");
		}
		if (leaderTypes != null) {
			criteria.andLeaderTypeIn(Arrays.asList(leaderTypes));
			searchStr += "&leaderTypes="+StringUtils.join(leaderTypes,",");
		}
		if(type!=null){
            criteria.andTypeEqualTo(type);
            searchStr += "&type=" + type;
        }

		if (hasCrp != null) {
			criteria.andHasCrpEqualTo(hasCrp);
			searchStr += "&hasCrp="+(BooleanUtils.isTrue(hasCrp)?"1":"0");
		}
		if(hasAbroadEdu!=null){

			CadreCategorySearchBean searchBean = new CadreCategorySearchBean();
			searchBean.setCadreStatus(status);
			List<Integer> cadreIds = iCadreMapper.selectCadreIdListByEdu(CadreConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean);

			if(hasAbroadEdu){
				criteria.andIdIn(cadreIds);
			}else{
				criteria.andIdNotIn(cadreIds);
			}
			searchStr += "&hasAbroadEdu="+(BooleanUtils.isTrue(hasAbroadEdu)?"1":"0");
		}
		/*if (isEngage) {
			Integer adminLevel = CmTag.getMetaTypeByCode("mt_admin_level_none").getId();
			criteria.andAdminLevelEqualTo(adminLevel);
		}

		if (isKeepSalary) { // 保留待遇干部，即第一主职已关联岗位
			criteria.andMainCadrePostIdGreaterThan(0).andUnitPostIdIsNull();
		}
		*/

		long count = cadreViewMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}
		List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

		CommonList commonList = new CommonList(count, pageNo, pageSize);
		commonList.setSearchStr(searchStr);
		modelMap.put("cadres", Cadres);
		modelMap.put("commonList",commonList);

		return "cadre/mobile/cadre_advanced_search_result";
	}
}
