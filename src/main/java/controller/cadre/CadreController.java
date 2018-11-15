package controller.cadre;

import bean.XlsCadre;
import bean.XlsUpload;
import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.unit.Unit;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import service.cadre.CadreService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadre")
    public String cadre_page(@RequestParam(required = false, defaultValue = CadreConstants.CADRE_STATUS_MIDDLE + "") Byte status,

                             @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes,
                             @RequestParam(required = false, value = "unitIds") Integer[] unitIds,
                             @RequestParam(required = false, value = "unitTypes") Integer[] unitTypes,
                             @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                             @RequestParam(required = false, value = "maxEdus") Integer[] maxEdus,
                             @RequestParam(required = false, value = "postIds") Integer[] postIds,
                             @RequestParam(required = false, value = "proPosts") String[] proPosts,
                             @RequestParam(required = false, value = "proPostLevels") String[] proPostLevels,
                             Integer cadreId, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("cadre:list") && !ShiroHelper.isPermitted("cadre:list2")) {
            throw new UnauthorizedException("没有权限访问");
        }

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
        }

        // MAP<unitTypeId, List<unitId>>
        Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
        Map<Integer, Unit> unitMap = unitService.findAll();
        for (Unit unit : unitMap.values()) {

            if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY) continue;
            Integer unitTypeId = unit.getTypeId();
            List<Integer> units = unitListMap.get(unitTypeId);
            if (units == null) {
                units = new ArrayList<>();
                unitListMap.put(unitTypeId, units);
            }
            units.add(unit.getId());
        }
        modelMap.put("unitListMap", unitListMap);

        if (dpTypes != null) {
            modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
        }
        if (unitIds != null) {
            modelMap.put("selectUnitIds", Arrays.asList(unitIds));
        }
        if (unitTypes != null) {
            modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }

        if (maxEdus != null) {
            modelMap.put("selectMaxEdus", Arrays.asList(maxEdus));
        }
        if (postIds != null) {
            modelMap.put("selectPostIds", Arrays.asList(postIds));
        }

        modelMap.put("proPosts", IPropertyMapper.teacherProPosts());
        modelMap.put("proPostLevels", IPropertyMapper.teacherProPostLevels());
        if (proPosts != null) {
            modelMap.put("selectProPosts", Arrays.asList(proPosts));
        }
        if (proPostLevels != null) {
            modelMap.put("selectProPostLevels", Arrays.asList(proPostLevels));
        }

        return "cadre/cadre_page";
    }

    @RequestMapping("/cadre_data")
    public void cadre_data(HttpServletResponse response,
                           @SortParam(required = false, defaultValue = "sort_order", tableName = "cadre") String sort,
                           @OrderParam(required = false, defaultValue = "desc") String order,
                           @RequestParam(required = false, defaultValue = CadreConstants.CADRE_STATUS_MIDDLE + "") Byte status,
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
                           @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes, // 党派

                           @RequestDateRange DateRange _birth,
                           @RequestDateRange DateRange _cadreGrowTime,
                           @RequestParam(required = false, value = "unitIds") Integer[] unitIds, // 所在单位
                           @RequestParam(required = false, value = "unitTypes") Integer[] unitTypes, // 部门属性
                           @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels, // 行政级别
                           @RequestParam(required = false, value = "maxEdus") Integer[] maxEdus, // 最高学历
                           @RequestParam(required = false, value = "postIds") Integer[] postIds, // 职务属性
                           @RequestParam(required = false, value = "proPosts") String[] proPosts, // 专业技术职务
                           @RequestParam(required = false, value = "proPostLevels") String[] proPostLevels, // 专技岗位等级
                           Boolean isPrincipalPost, // 是否正职
                           Boolean isDouble, // 是否双肩挑
                           @RequestParam(required = false, defaultValue = "0") int export,
                           @RequestParam(required = false, defaultValue = "1") int format, // 导出格式
                           @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                           Integer pageSize, Integer pageNo) throws IOException {

        if (!ShiroHelper.isPermitted("cadre:list") && !ShiroHelper.isPermitted("cadre:list2")) {
            throw new UnauthorizedException("没有权限访问");
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andIdEqualTo(cadreId);
        }
        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (_birth.getStart() != null) {
            criteria.andBirthGreaterThanOrEqualTo(_birth.getStart());
        }

        if (_birth.getEnd() != null) {
            criteria.andBirthLessThanOrEqualTo(_birth.getEnd());
        }
        if (_cadreGrowTime.getStart() != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_cadreGrowTime.getStart());
        }

        if (_cadreGrowTime.getEnd() != null) {
            criteria.andGrowTimeLessThanOrEqualTo(_cadreGrowTime.getEnd());
        }

        if (endAge != null) {
            criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * endAge));
        }
        if (startAge != null) {
            criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startAge));
        }
        if (endDpAge != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * endDpAge));
        }
        if (startDpAge != null) {
            criteria.andGrowTimeLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startDpAge));
        }

        if (endNowPostAge != null) {
            criteria.andCadrePostYearLessThanOrEqualTo(endNowPostAge);
        }
        if (startNowPostAge != null) {
            criteria.andCadrePostYearGreaterThanOrEqualTo(startNowPostAge);
        }
        if (endNowLevelAge != null) {
            criteria.andAdminLevelYearLessThanOrEqualTo(endNowLevelAge);
        }
        if (startNowLevelAge != null) {
            criteria.andAdminLevelYearGreaterThanOrEqualTo(startNowLevelAge);
        }

        if (unitIds != null) {
            criteria.andUnitIdIn(Arrays.asList(unitIds));
        }
        if (unitTypes != null) {
            criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            criteria.andTypeIdIn(Arrays.asList(adminLevels));
        }
        if (maxEdus != null) {
            criteria.andEduIdIn(Arrays.asList(maxEdus));
        }
        if (postIds != null) {
            criteria.andPostIdIn(Arrays.asList(postIds));
        }
        if (proPosts != null) {
            criteria.andProPostIn(Arrays.asList(proPosts));
        }
        if (proPostLevels != null) {
            criteria.andProPostLevelIn(Arrays.asList(proPostLevels));
        }
        if (dpTypes != null) {
            criteria.andDpTypeIdIn(new HashSet<>(Arrays.asList(dpTypes)));
        }

        if (isPrincipalPost != null) {
            criteria.andIsPrincipalPostEqualTo(isPrincipalPost);
        }
        if (isDouble != null) {
            criteria.andIsDoubleEqualTo(isDouble);
        }

        if (export == 1) {
            // 判断导出权限
            SecurityUtils.getSubject().checkPermission("cadre:export");

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cadre_export(format, status, example, response);
            return;
        }

        long count = cadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        if (ShiroHelper.isPermitted("cadre:list")) {
            JSONUtils.jsonp(resultMap);
        } else {
            // 没有干部管理员的权限，只能看到部分字段
            JSONUtils.jsonpAntPathFilters(resultMap, "id", "code", "realname", "gender",
                    "idcard", "birth", "eduId", "proPost", "lpWorkTime","unitId",
                    "unit", "unit.unitType", "unit.unitType.name",
                    "unit.name", "title", "typeId", "postId", "dpTypeId", "dpGrowTime", "isOw", "owGrowTime", "mobile", "email");
        }
    }

    private void cadre_export(int format, Byte status, CadreViewExample example, HttpServletResponse response) throws IOException {

        SXSSFWorkbook wb = null;
        if(format==1) {
            // 一览表
            wb = cadreExportService.export(status, example, ShiroHelper.isPermitted("cadre:list") ? 0 : 1);
            String cadreType = CadreConstants.CADRE_STATUS_MAP.get(status);
            String fileName = CmTag.getSysConfig().getSchoolName() + cadreType + "(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
            ExportHelper.output(wb, fileName + ".xlsx", response);

        }else{
            // 名单
            cadreExportService.exportCadres(example, response);
        }
    }

    @RequiresPermissions("cadre:view")
    @RequestMapping("/cadre_view")
    public String cadre_view(HttpServletResponse response,
                             Integer cadreId,
                             String to, // 默认跳转到基本信息
                             ModelMap modelMap) {

        if(StringUtils.isBlank(to)) {
            to = "cadre_base";
            String def = PropertiesUtils.getString("sys.settings.cadreView.default");
            if(StringUtils.isNotBlank(def) && !StringUtils.equals(to, def)
                    && ShiroHelper.isPermitted("cadreAdform:list")){
                to = def;
            }
        }
        modelMap.put("to", to);

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        modelMap.put("cadre", cadre);
        return "cadre/cadre_view";
    }

    // 基本信息
    @RequiresPermissions("cadre:view")
    @RequestMapping("/cadre_base")
    public String cadre_base(int cadreId, ModelMap modelMap) {

        cadreBase(cadreId, modelMap);

        return "cadre/cadre_base";
    }

    // 提任（中层->校领导）
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_promote")
    public String cadre_promote(int id, ModelMap modelMap) {

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(id);
        modelMap.put("cadre", cadre);

        return "cadre/cadre_promote";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_promote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_promote(int id, String title, HttpServletRequest request) {

        cadreService.promote(id, StringUtils.trimToNull(title));

        logger.info(addLog(LogConstants.LOG_ADMIN, "干部提任：%s", id));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);

        return resultMap;
    }
    // 离任
    @RequiresPermissions("cadre:leave")
    @RequestMapping("/cadre_leave")
    public String cadre_leave(int id, ModelMap modelMap) {

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(id);
        modelMap.put("cadre", cadre);

        TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, DispatchConstants.DISPATCH_CADRE_TYPE_DISMISS);
        modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));

        // 获取该干部的所有岗位
        CadrePost cadreMainCadrePost = cadrePostService.getCadreMainCadrePost(id);
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(id);
        List<CadrePost> cadrePosts = new ArrayList<>();
        if(cadreMainCadrePost!=null && cadreMainCadrePost.getUnitPostId()!=null){
            cadrePosts.add(cadreMainCadrePost);
        }
        for (CadrePost subCadrePost : subCadrePosts) {
            if(subCadrePost.getUnitPostId()!=null){
                cadrePosts.add(subCadrePost);
            }
        }
        modelMap.put("cadrePosts", cadrePosts);

        return "cadre/cadre_leave";
    }

    @RequiresPermissions("cadre:leave")
    @RequestMapping(value = "/cadre_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_leave(int id, String title, Integer dispatchCadreId,
                              @RequestParam(value = "postIds[]", required = false) Integer[] postIds,
                              HttpServletRequest request) {

        //if(status==null) status=CadreConstants.CADRE_STATUS_LEAVE;

        byte status = cadreService.leave(id, StringUtils.trimToNull(title), dispatchCadreId, postIds);

        logger.info(addLog(LogConstants.LOG_ADMIN, "干部离任：%s", id));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("status", status);

        return resultMap;
    }

    // 在“离任中层干部库”和“离任校领导干部库”中加一个按钮“重新任用”，点击这个按钮，可以转移到“考察对象”中去。
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_re_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_re_assign(@RequestParam(value = "ids[]") Integer[] ids) {

        cadreService.re_assign(ids);

        logger.info(addLog(LogConstants.LOG_ADMIN, "干部重新任用：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    // for test 给所有的干部加上干部身份
    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/cadre_addAllCadreRole")
    @ResponseBody
    public Map do_cadre_addAllCadreRole() {

        Map<Integer, CadreView> cadreMap = cadreService.findAll();
        for (CadreView cadre : cadreMap.values()) {
            // 添加干部身份
            sysUserService.addRole(cadre.getUserId(), RoleConstants.ROLE_CADRE);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_au(Cadre record, HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            cadreService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部：%s", record.getId()));
        } else {
            record.setUserId(null); // 不能修改账号、干部类别
            record.setStatus(null);
            cadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_au")
    public String cadre_au(Integer id, Byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        if (id != null) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            modelMap.put("cadre", cadre);
            modelMap.put("status", cadre.getStatus());

            modelMap.put("sysUser", sysUserService.findById(cadre.getUserId()));
        }
        if (id != null && (status == CadreConstants.CADRE_STATUS_MIDDLE_LEAVE || status == CadreConstants.CADRE_STATUS_LEADER_LEAVE)) {
            TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, DispatchConstants.DISPATCH_CADRE_TYPE_DISMISS);
            modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));
        }

        return "cadre/cadre_au";
    }

    // 干部库转移
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_transfer")
    public String cadre_transfer() {

        return "cadre/cadre_transfer";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_transfer(int cadreId, byte status) {

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        byte cadreStatus = cadre.getStatus();
        if(!CadreConstants.CADRE_STATUS_SET.contains(cadreStatus) ||
                !CadreConstants.CADRE_STATUS_SET.contains(status)){
            return failed("不允许转移。");
        }
        if(cadreStatus == status){
            return failed("不允许转移至相同的干部库。");
        }

        Cadre record = new Cadre();
        record.setId(cadre.getId());
        record.setStatus(status);
        record.setSortOrder(getNextSortOrder(CadreService.TABLE_NAME, "status=" + status));
        cadreService.updateByPrimaryKeySelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:changeOrder")
    @RequestMapping(value = "/cadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping("/cadre_import")
    public String cadre_import(byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadre_import";
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping(value = "/cadre_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_import(HttpServletRequest request, Byte status) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsCadre> cadres = new ArrayList<XlsCadre>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            XSSFSheet sheet = workbook.getSheetAt(k);
            cadres.addAll(XlsUpload.fetchCadres(sheet));
        }

        int successCount = cadreService.importCadres(cadres, status);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", cadres.size());

        return resultMap;
    }
}
