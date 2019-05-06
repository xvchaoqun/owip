package controller.cadre;

import bean.XlsUpload;
import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import domain.unit.UnitPost;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.cadre.CadreAdformService;
import service.cadre.CadreInfoFormService;
import service.cadre.CadreService;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CadreController extends BaseController {

    @Autowired
    private CadreAdformService cadreAdformService;
    @Autowired
    private CadreInfoFormService cadreInfoFormService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadre")
    public String cadre_page(@RequestParam(required = false, defaultValue = CadreConstants.CADRE_STATUS_MIDDLE + "") Byte status,
                             @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes,
                             @RequestParam(required = false, value = "unitIds") Integer[] unitIds,
                             @RequestParam(required = false, value = "unitTypes") Integer[] unitTypes,
                             @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                             @RequestParam(required = false, value = "maxEdus") Integer[] maxEdus,
                             @RequestParam(required = false, value = "postTypes") Integer[] postTypes,
                             @RequestParam(required = false, value = "proPosts") String[] proPosts,
                             @RequestParam(required = false, value = "proPostLevels") String[] proPostLevels,
                             @RequestParam(required = false, value = "leaderTypes") Byte[] leaderTypes,
                             Integer cadreId, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREARCHIVE)) {
            throw new UnauthorizedException("没有权限访问");
        }

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
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
        if (postTypes != null) {
            modelMap.put("selectPostTypes", Arrays.asList(postTypes));
        }

        modelMap.put("proPosts", iPropertyMapper.teacherProPosts());
        modelMap.put("proPostLevels", iPropertyMapper.teacherProPostLevels());
        if (proPosts != null) {
            modelMap.put("selectProPosts", Arrays.asList(proPosts));
        }
        if (proPostLevels != null) {
            modelMap.put("selectProPostLevels", Arrays.asList(proPostLevels));
        }
        if (leaderTypes != null) {
            modelMap.put("selectLeaderTypes", Arrays.asList(leaderTypes));
        }

        // 导出的列名字
        List<String> titles = cadreExportService.getTitles();
        modelMap.put("titles", titles);

        return "cadre/cadre_page";
    }

    @RequestMapping("/cadre_data")
    public void cadre_data(HttpServletResponse response,
                           HttpServletRequest request,
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
                           @RequestParam(required = false, value = "postTypes") Integer[] postTypes, // 职务属性
                           @RequestParam(required = false, value = "proPosts") String[] proPosts, // 专业技术职务
                           @RequestParam(required = false, value = "proPostLevels") String[] proPostLevels, // 专技岗位等级
                           Boolean isPrincipalPost, // 是否正职
                           @RequestParam(required = false, value = "leaderTypes") Byte[] leaderTypes, // 是否班子负责人
                           Boolean isDouble, // 是否双肩挑
                           Byte type,
                           Boolean state,
                           @RequestParam(required = false, defaultValue = "0") int export,
                           @RequestParam(required = false, defaultValue = "1") int format, // 导出格式
                           @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                           @RequestParam(required = false) Integer[] cols, // 选择导出的列
                           Integer pageSize, Integer pageNo) throws IOException, TemplateException {

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREARCHIVE)) {
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
        example.setOrderByClause("sort_order desc");

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
            //  >= 不含（减一）
            criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endAge + 1)));
        }
        if (startAge != null) {
            // <= 包含
            criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startAge));
        }
        if (endDpAge != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endDpAge + 1)));
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
            criteria.andAdminLevelIn(Arrays.asList(adminLevels));
        }
        if (maxEdus != null) {
            criteria.andEduIdIn(Arrays.asList(maxEdus));
        }
        if (postTypes != null) {
            criteria.andPostTypeIn(Arrays.asList(postTypes));
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
        if (leaderTypes != null) {
            criteria.andLeaderTypeIn(Arrays.asList(leaderTypes));
        }
        if (isDouble != null) {
            criteria.andIsDoubleEqualTo(isDouble);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (state != null) {
            criteria.andStateEqualTo(state);
        }

        if (export == 1) {
            // 判断导出权限
            SecurityUtils.getSubject().checkPermission("cadre:export");

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            // 一览表
            cadre_export(format, status, cols, example, response);
            return;
        } else if (export == 2 || export == 3) {

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            List<CadreView> records = cadreViewMapper.selectByExample(example);
            Integer[] cadreIds = records.stream().map(CadreView::getId)
                    .collect(Collectors.toList()).stream().toArray(Integer[]::new);
            if (export == 2) {
                // 干部任免审批表
                cadreAdformService.export(cadreIds, request, response);
            } else {
                // 干部信息采集表
                cadreInfoFormService.export(cadreIds, request, response);
            }
            return;
        } else if (export == 4) {

            // 批量排序表
            exportForBatchSort(status, response);
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
                    "idcard", "birth", "eduId", "proPost", "lpWorkTime", "unitId",
                    "unit", "unit.unitType", "unit.unitType.name",
                    "unit.name", "title", "adminLevel", "postType", "dpTypeId", "dpGrowTime", "isOw", "owGrowTime", "mobile", "email");
        }
    }

    private void cadre_export(int format, Byte status, Integer[] cols, CadreViewExample example, HttpServletResponse response) throws IOException {

        SXSSFWorkbook wb = null;
        if (format == 1) {
            // 一览表
            wb = cadreExportService.export(status, example, ShiroHelper.isPermitted("cadre:list") ? 0 : 1, cols);
            String cadreType = CadreConstants.CADRE_STATUS_MAP.get(status);
            String fileName = CmTag.getSysConfig().getSchoolName() + cadreType + "(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
            ExportHelper.output(wb, fileName + ".xlsx", response);

        } else {
            // 名单
            cadreExportService.exportCadres(example, response);
        }
    }

    private void exportForBatchSort(Byte status, HttpServletResponse response) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        List<CadreView> records = cadreViewMapper.selectByExample(example);

        int rownum = records.size();
        String[] titles = {"工作证号|120", "姓名|100", "所在单位及职务|220|left", "岗位编号(主职)|120", "岗位名称|300|left", "岗位是否已撤销|80"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            CadreView cv = records.get(i);

            String unitPostCode = "--";
            String unitPostName = "--";
            String unitPostStatus = "--";
            CadrePost cadrePost = cadrePostService.getCadreMainCadrePost(cv.getId());
            if (cadrePost != null) {
                Integer unitPostId = cadrePost.getUnitPostId();
                if (unitPostId != null) {
                    UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostId);
                    if (unitPost != null) {
                        unitPostCode = unitPost.getCode();
                        unitPostName = unitPost.getName();
                        unitPostStatus = (unitPost.getStatus() == SystemConstants.UNIT_POST_STATUS_NORMAL) ? "否" : "是";
                    }
                }
            }

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    unitPostCode,
                    unitPostName,
                    unitPostStatus

            };
            valuesList.add(values);
        }
        String fileName = String.format("干部批量排序表(%s)",
                CadreConstants.CADRE_STATUS_MAP.get(status));

        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 批量排序
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_batchSort")
    public String cadre_batchSort(byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadre_batchSort";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_batchSort", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_batchSort(byte status, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);

        List<Integer> cadreIdList = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {
            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            CadreView cadre = cadreService.dbFindByUserId(uv.getId());
            if (cadre == null) {
                throw new OpException("第{0}行[{1}]不是{2}", row, userCode,
                        CadreConstants.CADRE_STATUS_MAP.get(status));
            }
            cadreIdList.add(cadre.getId());
        }

        Collections.reverse(cadreIdList); // 逆序导入

        cadreService.batchSort(status, cadreIdList);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        return resultMap;
    }

    @RequiresPermissions("cadre:view")
    @RequestMapping("/cadre_view")
    public String cadre_view(HttpServletResponse response,
                             Integer cadreId,
                             String to, // 默认跳转到基本信息
                             ModelMap modelMap) {

        if (StringUtils.isBlank(to)) {
            to = "cadre_base";
            String def = PropertiesUtils.getString("sys.settings.cadreView.default");
            if (StringUtils.isNotBlank(def) && !StringUtils.equals(to, def)
                    && ShiroHelper.isPermitted("cadreAdform:list")) {
                to = def;
            }
        }
        modelMap.put("to", to);

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        return "cadre/cadre_view";
    }

    // 基本信息
    @RequiresPermissions("cadre:view")
    @RequestMapping("/cadre_base")
    public String cadre_base(int cadreId, ModelMap modelMap) {

        cadreCommonService.cadreBase(cadreId, modelMap);

        return "cadre/cadre_base";
    }

    // 提任（干部->校领导）
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_promote")
    public String cadre_promote(int id, ModelMap modelMap) {

        CadreView cadre = iCadreMapper.getCadre(id);
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

        CadreView cadre = iCadreMapper.getCadre(id);
        modelMap.put("cadre", cadre);

        TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, DispatchConstants.DISPATCH_CADRE_TYPE_DISMISS);
        modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));

        // 获取该干部的所有岗位
        CadrePost cadreMainCadrePost = cadrePostService.getCadreMainCadrePost(id);
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(id);
        List<CadrePost> cadrePosts = new ArrayList<>();
        if (cadreMainCadrePost != null && cadreMainCadrePost.getUnitPostId() != null) {
            cadrePosts.add(cadreMainCadrePost);
        }
        for (CadrePost subCadrePost : subCadrePosts) {
            if (subCadrePost.getUnitPostId() != null) {
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

    // 在“离任干部库”和“离任校领导干部库”中加一个按钮“重新任用”，点击这个按钮，可以转移到“考察对象”中去。
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

        record.setState(BooleanUtils.isTrue(record.getState()));

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
        if (!CadreConstants.CADRE_STATUS_SET.contains(cadreStatus) ||
                !CadreConstants.CADRE_STATUS_SET.contains(status)) {
            return failed("不允许转移。");
        }
        if (cadreStatus == status) {
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

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);

        List<Cadre> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            Cadre record = new Cadre();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            record.setUserId(userId);

            // 干部类型，仅针对干部
            String _type = StringUtils.trim(xlsRow.get(2));
            if (status == CadreConstants.CADRE_STATUS_MIDDLE
                    || status == CadreConstants.CADRE_STATUS_MIDDLE_LEAVE) {
                if (StringUtils.contains(_type, "科级")) {
                    record.setType((byte) 2);
                } else {
                    record.setType((byte) 1); // 默认是处级
                }
            }

            String _state = StringUtils.trim(xlsRow.get(3));
            record.setState(StringUtils.equals(_state, "是"));

            String adminLevel = StringUtils.trimToNull(xlsRow.get(4));
            MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", adminLevel);
            if (adminLevelType == null) throw new OpException("第{0}行行政级别[{1}]不存在", row, adminLevel);
            record.setAdminLevel(adminLevelType.getId());

            String _postType = StringUtils.trimToNull(xlsRow.get(5));
            MetaType postType = CmTag.getMetaTypeByName("mc_post", _postType);
            if (postType == null) throw new OpException("第{0}行职务属性[{1}]不存在", row, _postType);
            record.setPostType(postType.getId());

            String unitCode = StringUtils.trimToNull(xlsRow.get(6));
            if (StringUtils.isBlank(unitCode)) {
                throw new OpException("第{0}行单位编号为空", row);
            }
            Unit unit = unitService.findUnitByCode(unitCode);
            if (unit == null) {
                throw new OpException("第{0}行单位编号[{1}]不存在", row, unitCode);
            }
            record.setUnitId(unit.getId());

            record.setTitle(StringUtils.trimToNull(xlsRow.get(7)));
            record.setPost(StringUtils.trimToNull(xlsRow.get(8)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(9)));

            record.setStatus(status);
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int successCount = cadreService.batchImport(records);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }
}
