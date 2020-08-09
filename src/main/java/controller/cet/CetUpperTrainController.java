package controller.cet;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cet.CetProjectType;
import domain.cet.CetTraineeType;
import domain.cet.CetUpperTrain;
import domain.cet.CetUpperTrainExample;
import domain.cet.CetUpperTrainExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
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
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetUpperTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("cetUpperTrain:list")
    @RequestMapping("/cetUpperTrain")
    public String cetUpperTrain(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                byte type,
                                byte addType,
                                Integer unitId,
                                Integer userId,
                                @RequestParam(required = false, value = "identities") Integer[] identities,
                                ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("cetUpperTrain:list")
                && !ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN_UPPER)) {
            throw new UnauthorizedException();
        }

        modelMap.put("cls", cls);

        if (null != identities){
            modelMap.put("selectIdentities", Arrays.asList(identities));
        }
        if (unitId != null) {
            modelMap.put("unit", CmTag.getUnit(unitId));
        }
        if (userId != null) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

         Map<Integer, CetProjectType> specialProjectTypeMap = cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_1);
        modelMap.put("specialProjectTypeMap", specialProjectTypeMap);
        Map<Integer, CetProjectType>  dailyProjectTypeMap = cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_2);
        modelMap.put("dailyProjectTypeMap", dailyProjectTypeMap);

        return "cet/cetUpperTrain/cetUpperTrain_page";
    }

    //@RequiresPermissions("cetUpperTrain:list")
    @RequestMapping("/cetUpperTrain_data")
    @ResponseBody
    public void cetUpperTrain_data(HttpServletResponse response,
                                   byte addType,
                                   @RequestParam(required = false, defaultValue = "1") Byte cls,
                                   byte type,
                                   Integer year,
                                   String title,
                                   @RequestDateRange DateRange startDate,
                                   @RequestDateRange DateRange endDate,
                                   BigDecimal prePeriod,
                                   BigDecimal subPeriod,
                                   String trainName,
                                   @RequestParam(required = false, value = "identities") Integer[] identities,
                                   Integer unitId,
                                   Integer userId,
                                   Integer postType,
                                   Integer organizer,
                                   Integer trainType,
                                   Byte specialType,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetUpperTrainExample example = new CetUpperTrainExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("start_date desc");

        CetUpperTrainExample checkExample = new CetUpperTrainExample();
        Criteria checkCriteria = checkExample.createCriteria()
                .andIsDeletedEqualTo(false);

        criteria.andTypeEqualTo(type);
        checkCriteria.andTypeEqualTo(type);

        if (type==CetConstants.CET_UPPER_TRAIN_TYPE_UNIT) {
             if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
                 // 其他部门派出，单位审核后，组织部待确认
                 checkCriteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS).andIsValidIsNull();
             }else{
                 // 其他部门派出，待单位审核
                 checkCriteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
             }
        } else if (type==CetConstants.CET_UPPER_TRAIN_TYPE_OW
                ||type==CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD
                ||type==CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL) {
            // 组织部派出，组织部待审核
            checkCriteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
        }

        if (cls == 4) {
            criteria.andIsDeletedEqualTo(true);
        } else {
            criteria.andIsDeletedEqualTo(false);
            if (cls == 1) {
                if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
                    criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS).andIsValidIsNotNull();
                } else {
                    criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
                }
            } else if (cls == 2) {

                if (type==CetConstants.CET_UPPER_TRAIN_TYPE_UNIT) {
                    // 其他部门派出，单位审核后，组织部待审核
                    criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS).andIsValidIsNull();
                } else if (type==CetConstants.CET_UPPER_TRAIN_TYPE_OW
                        ||type==CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD
                        ||type==CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL){
                    // 组织部派出，组织部待审核
                    criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
                }
            } else if (cls == 3) {
                criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS);
            }else if(cls==5){
                criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
            } else {
                criteria.andIdIsNull();
            }
        }

        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (postType != null) {
            criteria.andPostTypeEqualTo(postType);
        }
        if (organizer != null) {
            criteria.andOrganizerEqualTo(organizer);
        }
        if (trainType != null) {
            criteria.andTrainTypeEqualTo(trainType);
        }

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);
            if (adminUnitIdSet.size() == 0) {
                throw new UnauthorizedException();
            }

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UPPER);
            criteria.andUnitAdmin(adminUnitIdSet);
            checkCriteria.andUnitAdmin(adminUnitIdSet);
        } else {
            criteria.andIdIsNull();
        }

        if (null != prePeriod){
            criteria.andPeriodGreaterThanOrEqualTo(prePeriod);
        }
        if (null != subPeriod){
            criteria.andPeriodLessThanOrEqualTo(subPeriod);
        }
        if (null != year){
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(title)){
            criteria.andTitleLike(SqlUtils.like(title));
        }
        if (StringUtils.isNotBlank(trainName)){
            criteria.andTrainNameLike(SqlUtils.like(trainName));
        }
        if (null != startDate.getStart()){
            criteria.andStartDateGreaterThanOrEqualTo(startDate.getStart());
        }
        if (null != startDate.getEnd()) {
            criteria.andStartDateLessThanOrEqualTo(startDate.getEnd());
        }
        if (null != endDate.getStart()) {
            criteria.andEndDateGreaterThanOrEqualTo(endDate.getStart());
        }
        if (null != endDate.getEnd()) {
            criteria.andEndDateLessThanOrEqualTo(endDate.getEnd());
        }
        if (null !=identities){
            criteria.andIdentityLike(identities);
        }
        if (specialType != null){
            criteria.andSpecialTypeEqualTo(specialType);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUpperTrain_export(example, type, response);
            return;
        }

        long count = cetUpperTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUpperTrain> records = cetUpperTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("checkCount", cetUpperTrainMapper.countByExample(checkExample));
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetUpperTrain.class, cetUpperTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping(value = "/cetUpperTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_au(CetUpperTrain record,
                                   @RequestParam(value = "identities[]", required = false) Integer[] identities,
                                   MultipartFile _word, MultipartFile _pdf,
                                   Boolean check,// 审批
                                   Byte auType,//添加方式
                                   HttpServletRequest request) throws IOException, InterruptedException, InvalidFormatException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if(auType==null){
            auType = CetConstants.CET_UPPERTRAIN_AU_TYPE_SINGLE;
        }
        CetUpperTrain oldRecord = null;
        Integer id = record.getId();
        byte addType = record.getAddType();
        if (id != null){
            oldRecord = cetUpperTrainMapper.selectByPrimaryKey(id);
            record.setAddType(null);
        }

        if (null != identities)
            Arrays.sort(identities);
        if (CetConstants.CET_UPPERTRAIN_AU_TYPE_BATCH != auType) {
            record.setIdentity(StringUtils.trimToNull(StringUtils.join(identities, ",")) == null
                    ? "" : "," + StringUtils.join(identities, ",") + ",");
        }
        record.setIsOnline(BooleanUtils.isTrue(record.getIsOnline()));

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

            record.setIsValid(BooleanUtils.isTrue(record.getIsValid()));
            if (id == null) {
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
            } else {
                if (BooleanUtils.isFalse(check)) {
                    record.setStatus(null);
                }
            }
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            if (ShiroHelper.isPermitted("cetUpperTrain:unitAdmin")) {
                Integer currentUserId = ShiroHelper.getCurrentUserId();
                Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);

                if (id != null) {
                    Integer oldUnitId = oldRecord.getUnitId();
                    if (oldUnitId == null || !adminUnitIdSet.contains(oldUnitId)) {
                        throw new UnauthorizedException(); // 非单位管理员
                    }
                } else {
                    Integer unitId = record.getUnitId();
                    if (unitId == null || !adminUnitIdSet.contains(unitId)) {
                        throw new UnauthorizedException(); // 非单位管理员
                    }
                }

                SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UPPER);

                record.setIsValid(null);
                if (id == null) {
                    // 单位和组织部提交都直接通过，个人提交需要单位审批
                    record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
                } else {
                    if (oldRecord.getIsValid() != null) { // 组织部确认后，不允许单位修改
                        throw new OpException("党委组织部已确认，不允许修改。");
                    }

                    if (BooleanUtils.isFalse(check)) {
                        record.setStatus(null);
                    }
                }
            }
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF) {

            if (oldRecord != null && oldRecord.getStatus() != null
                    && oldRecord.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS) {
                // 退回后重新提交
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
            } else {
                record.setStatus(null);
            }

            //if(!ShiroHelper.isPermitted("cetUpperTrain:edit")) {

            int currentUserId = ShiroHelper.getCurrentUserId();

            boolean isUnitAdmin = true;
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);
            Integer unitId = record.getUnitId(); // 派出单位
            if (unitId == null || !adminUnitIdSet.contains(unitId)) {
                isUnitAdmin = false;
            }
            if (id != null) {
                Integer oldUnitId = oldRecord.getUnitId();
                if (oldUnitId == null || !adminUnitIdSet.contains(oldUnitId)) {
                    isUnitAdmin = false;
                }
            }

            record.setIsValid(null);
            record.setWordNote(upload(_word, "cetUpperTrain_note"));
            record.setPdfNote(uploadPdf(_pdf, "cetUpperTrain_note"));
            if (isUnitAdmin) {
                SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UPPER);
                if (oldRecord.getIsValid() != null) { // 组织部确认后，不允许单位修改
                    throw new OpException("党委组织部已确认，不允许修改。");
                }
            } else {
                // 本人操作
                if (id == null) {
                    record.setUserId(currentUserId);
                    record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
                } else {
                    if (currentUserId != oldRecord.getUserId()) {
                        throw new UnauthorizedException();
                    }
                    record.setUserId(null);
                    if (oldRecord.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_PASS) {// 审核通过后，不允许本人修改
                        throw new OpException("审核已通过，不允许修改。");
                    }
                }
            }
            //}

        } else {
            throw new UnauthorizedException();
        }

        if (id == null) {
            if (auType == CetConstants.CET_UPPERTRAIN_AU_TYPE_BATCH){

                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile xlsx = multipartRequest.getFile("xlsx");

                OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
                XSSFWorkbook workbook = new XSSFWorkbook(pkg);
                XSSFSheet sheet = workbook.getSheetAt(0);
                List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

                List<CetUpperTrain> records = new ArrayList<>();
                int row = 1;
                for (Map<Integer, String> xlsRow : xlsRows) {

                    CetUpperTrain cetUpperTrain = new CetUpperTrain();
                    PropertyUtils.copyProperties(cetUpperTrain, record);
                    row++;
                    String userCode = StringUtils.trim(xlsRow.get(0));
                    if (StringUtils.isBlank(userCode)) {
                        throw new OpException("第{0}行工作证号为空", row);
                    }
                    SysUserView uv = sysUserService.findByCode(userCode);
                    if (uv == null) {
                        throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
                    }
                    cetUpperTrain.setUserId(uv.getId());
                    cetUpperTrain.setTitle(StringUtils.trimToNull(xlsRow.get(2)));
                    String _identity = StringUtils.trimToNull(xlsRow.get(3));
                    if (StringUtils.isNotBlank(_identity)) {
                        String[] _identities = _identity.split(",|，|、");
                        List<Integer> identityList = new ArrayList<>();
                        for (String s : _identities) {
                            MetaType metaType = metaTypeService.findByName("mc_cet_identity", s);
                            if (metaType != null) {
                                identityList.add(metaType.getId());
                            }
                        }
                        if(identityList.size()>0) {
                            record.setIdentity("," + StringUtils.join(identityList, ",") + ",");
                        }
                    }else {
                        cetUpperTrain.setIdentity("");
                    }

                    records.add(cetUpperTrain);
                }
                Collections.reverse(records); // 逆序排列，保证导入的顺序正确

                cetUpperTrainService.batchImport(records);
                logger.info(log(LogConstants.LOG_CET, "批量添加上级调训或单位培训"));
            }else {
                cetUpperTrainService.insertSelective(record);
                logger.info(log(LogConstants.LOG_CET, "添加上级调训或单位培训：{0}", id));
            }
        } else {
            cetUpperTrainService.updateByPrimaryKeySelective(record, BooleanUtils.isTrue(check));
            logger.info(addLog(LogConstants.LOG_CET, "更新上级调训或单位培训：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping("/cetUpperTrain_au")
    public String cetUpperTrain_au(Integer id,
                                   byte addType,
                                   ModelMap modelMap) {

        if (id != null) {
            CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetUpperTrain", cetUpperTrain);
            modelMap.put("unit", unitService.findAll().get(cetUpperTrain.getUnitId()));
            modelMap.put("sysUser", CmTag.getUserById(cetUpperTrain.getUserId()));
        }
        //modelMap.put("type", BooleanUtils.isTrue(type) ? 1 : 0);
        modelMap.put("addType", addType);

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            // 单位管理员只能从管理的单位中选择派出单位
            int currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);
            if (adminUnitIdSet.size() > 0) {
                List<Unit> upperUnits = new ArrayList<>();
                for (Integer adminUintId : adminUnitIdSet) {
                    Unit unit = CmTag.getUnit(adminUintId);
                    if (unit != null) {
                        upperUnits.add(unit);
                    }
                }
                modelMap.put("upperUnits", upperUnits);
            } else {
                throw new UnauthorizedException();
            }

        }

        Map<Integer, CetProjectType> specialProjectTypeMap = cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_1);
        modelMap.put("specialProjectTypes", new ArrayList<>(specialProjectTypeMap.values()));
        Map<Integer, CetProjectType>  dailyProjectTypeMap = cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_2);
        modelMap.put("dailyProjectTypes", new ArrayList<>(dailyProjectTypeMap.values()));

        return "cet/cetUpperTrain/cetUpperTrain_au";
    }

    //@RequiresPermissions("cetUpperTrain:del")
    @RequestMapping(value = "/cetUpperTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUpperTrain_batchDel(HttpServletRequest request,
                                      @RequestParam(value = "ids[]") Integer[] ids,
                                      Boolean real,
                                      ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetUpperTrainService.batchDel(ids, BooleanUtils.isTrue(real));
            logger.info(addLog(LogConstants.LOG_CET, "批量删除上级调训或单位培训：%s",
                    StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/cetUpperTrain_batchTransfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_batchTransfer(HttpServletRequest request,
                                              @RequestParam(value = "ids[]") Integer[] ids,
                                              Byte cetType,
                                              Byte specialType,
                                              Integer projectId) {

        if (null != ids && ids.length > 0) {
            cetUpperTrainService.batchTransfer(ids, cetType, specialType, projectId);
            logger.info(addLog(LogConstants.LOG_CET, "批量转移上级调训（%s）至%s",
                    StringUtils.join(ids, ","), CetConstants.CET_TYPE_T_MAP.get(cetType)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/cetUpperTrain_batchTransfer")
    public String cetUpperTrain_batchTransfer(Integer id,
                                              byte addType,
                                              ModelMap modelMap) {

        modelMap.put("addType", addType);

        return "cet/cetUpperTrain/cetUpperTrain_batchTransfer";
    }

        // 上传培训总结
    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping(value = "/cetUpperTrain_uploadNote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_uploadNote(int id, byte addType, MultipartFile _word, MultipartFile _pdf,
                                           HttpServletRequest request) throws IOException, InterruptedException {

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            CetUpperTrain oldRecord = cetUpperTrainMapper.selectByPrimaryKey(id);

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Integer unitId = oldRecord.getUnitId();
            Integer leaderUserId = oldRecord.getUserId();

            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);
            if (unitId == null || !adminUnitIdSet.contains(unitId)) {
                throw new UnauthorizedException(); // 非单位管理员
            }

            if (oldRecord.getIsValid() != null) { // 组织部确认后，不允许单位修改
                throw new OpException("党委组织部已确认，不允许修改。");
            }

        } else {
            throw new UnauthorizedException();
        }

        if ((_word != null && !_word.isEmpty()) || (_pdf != null && !_pdf.isEmpty())) {

            CetUpperTrain record = new CetUpperTrain();
            record.setId(id);
            record.setWordNote(upload(_word, "cetUpperTrain_note"));
            record.setPdfNote(uploadPdf(_pdf, "cetUpperTrain_note"));

            cetUpperTrainMapper.updateByPrimaryKeySelective(record);

            CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);
            sysApprovalLogService.add(cetUpperTrain.getId(), cetUpperTrain.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                    "上传培训总结", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);

            logger.info(addLog(LogConstants.LOG_CET, "上传培训总结：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetUpperTrain_uploadNote")
    public String cetUpperTrain_uploadNote(int id, ModelMap modelMap) {

        CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);

        Integer userId = cetUpperTrain.getUserId();

        modelMap.put("cetUpperTrain", cetUpperTrain);
        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetUpperTrain/cetUpperTrain_uploadNote";
    }

    //@RequiresPermissions("cetUpperTrain:*")
    @RequestMapping("/cetUpperTrain_selectCadres_tree")
    @ResponseBody
    public Map cetUpperTrain_selectCadres_tree(Byte addType) throws IOException {

        if (addType == null) return null;

        LinkedHashSet<CadreView> cadreViews = null;
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");
            cadreViews = new LinkedHashSet<CadreView>(cadreService.findAll().values());
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UPPER);

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);
            CadreViewExample example = new CadreViewExample();
            if (adminUnitIdSet.size() > 0) {
                example.createCriteria().andUnitIdIn(new ArrayList<>(adminUnitIdSet));
            } else {
                example.createCriteria().andIdIsNull();
            }
            List<CadreView> cadres = cadreViewMapper.selectByExample(example);

            cadreViews = new LinkedHashSet<CadreView>(cadres);
        }

        TreeNode tree = cadreCommonService.getTree(cadreViews,
                new HashSet<>(Arrays.asList(CadreConstants.CADRE_STATUS_CJ,
                        CadreConstants.CADRE_STATUS_LEADER)), null, null, false, true, cadreViews.size() <= 20);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetUpperTrain:import")
    @RequestMapping("/cetUpperTrain_import")
    public String cetUpperTrain_import(Byte type,
                                       ModelMap modelMap) {

        modelMap.put("type", type);

        return "cet/cetUpperTrain/cetUpperTrain_import";
    }

    @RequiresPermissions("cetUpperTrain:import")
    @RequestMapping(value = "/cetUpperTrain_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_import(Byte type, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CetUpperTrain> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            CetUpperTrain record = new CetUpperTrain();


            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getId());

            int col = 2;
            record.setTitle(StringUtils.trimToNull(xlsRow.get(col++)));
            CadreView cadre = cadreService.dbFindByUserId(uv.getId());
            if (cadre != null) {
                if (record.getTitle() == null) {
                    record.setTitle(cadre.getTitle());
                }
            }
            String _identity = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isNotBlank(_identity)) {
                String[] identities = _identity.split(",|，|、");
                List<Integer> identityList = new ArrayList<>();
                for (String s : identities) {
                    MetaType metaType1 = metaTypeService.findByName("mc_cet_identity", s);
                    if (metaType1 != null) {
                        identityList.add(metaType1.getId());
                    }
                }
                if(identityList.size()>0) {
                    record.setIdentity("," + StringUtils.join(identityList, ",") + ",");
                }
            }else {
                record.setIdentity("");
            }

            CetTraineeType cetTraineeType = cetTraineeTypeService.getByName(StringUtils.trim(xlsRow.get(col++)));
            if(cetTraineeType!=null) {
                record.setTraineeTypeId(cetTraineeType.getId());
            }else{
                record.setTraineeTypeId(0);
                record.setOtherTraineeType(StringUtils.trim(xlsRow.get(col - 1)));
            }

            String _year = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_year) || !NumberUtils.isDigits(_year)) {
                throw new OpException("第{0}行年度有误", row);
            }
            record.setYear(Integer.valueOf(_year));

            if (type == 8){
                String trainName = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(trainName)){
                    throw  new OpException("第{0}行研究方向为空", row);
                }
                record.setTrainName(trainName);
                record.setIsOnline(StringUtils.equals(xlsRow.get(col++), "线上培训"));
                String country = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(country)){
                    throw new OpException("第{0}行前往国家为空", row);
                }
                record.setCountry(country);
                record.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));
                record.setAgency(StringUtils.trimToNull(xlsRow.get(col++)));
                record.setStartDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
                record.setEndDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
                String period = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(period) || !NumberUtils.isCreatable(period)) {
                    throw new OpException("第{0}行培训学时有误", row);
                }
                record.setPeriod(new BigDecimal(period));
                record.setScore(StringUtils.trimToNull(xlsRow.get(col++)));
                record.setIsValid(!StringUtils.equals(StringUtils.trimToNull(xlsRow.get(col++)), "是"));
                record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));
                record.setType(type);
            }else {
                String _organizerType = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(_organizerType)) {
                    throw new OpException("第{0}行培训班主办方为空", row);
                }
                MetaType organizerType = CmTag.getMetaTypeByName("mc_cet_upper_train_organizer", _organizerType);
                if (organizerType == null) {
                    record.setOrganizer(0); // 其他主办方
                    record.setOtherOrganizer(_organizerType.replaceAll("<br/>", ""));
                } else {
                    record.setOrganizer(organizerType.getId());
                }

                String _trainType = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(_trainType)) {
                    throw new OpException("第{0}行培训班类型为空", row);
                }
                MetaType trainType = CmTag.getMetaTypeByName("mc_cet_upper_train_type", _trainType);
                if (trainType == null) {
                    throw new OpException("第{0}行培训班类型不存在", row);
                }
                record.setTrainType(trainType.getId());

                String trainName = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(trainName)) {
                    throw new OpException("第{0}行培训班名称为空", row);
                }
                record.setTrainName(trainName);

                record.setIsOnline(StringUtils.equals(xlsRow.get(col++), "线上培训"));

                record.setStartDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
                record.setEndDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

                String period = StringUtils.trimToNull(xlsRow.get(col++));
                if (StringUtils.isBlank(period) || !NumberUtils.isCreatable(period)) {
                    throw new OpException("第{0}行培训学时有误", row);
                }
                record.setPeriod(new BigDecimal(period));

                record.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));
                record.setScore(StringUtils.trimToNull(xlsRow.get(col++)));
                String _type = StringUtils.trimToNull(xlsRow.get(col++));
                record.setType(StringUtils.equals(_type, "党委组织部")?CetConstants.CET_UPPER_TRAIN_TYPE_OW
                        :CetConstants.CET_UPPER_TRAIN_TYPE_UNIT);
                if (record.getType()==CetConstants.CET_UPPER_TRAIN_TYPE_UNIT) {
                    String unitCode = StringUtils.trimToNull(xlsRow.get(col++));
                    if (StringUtils.isNotBlank(unitCode)) {

                        Unit unit = unitService.findRunUnitByCode(unitCode);
                        if (unit == null) {
                            throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
                        }
                        record.setUnitId(unit.getId());
                    }
                }

                record.setIsValid(!StringUtils.equals(StringUtils.trimToNull(xlsRow.get(col++)), "是"));
                record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));
            }

            records.add(record);
        }

        int addCount = cetUpperTrainService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入上级调训成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    public void cetUpperTrain_export(CetUpperTrainExample example, Byte type, HttpServletResponse response) {

        List<CetUpperTrain> records = cetUpperTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles_ow = {"年度|50","参训工号|100","参训人姓名|100","参训人类型|100","时任单位及职务|200","时任职务属性|100"
                ,"参训人员身份|100","培训班主办方|200","培训班类型|100","培训班名称|200","培训形式|100",
                "培训开始时间|100","培训结束时间|100","培训学时|70","培训地点|150","培训成绩|100","派出单位|150","是否计入年度学习任务|80","备注|150"};
        String[] titles_abroad = {"年度|50","参训工号|100","参训人姓名|100","参训人类型|100","时任单位及职务|200","时任职务属性|100"
                ,"参训人员身份|100","研修方向|200","培训形式|100",
                "培训开始时间|100","培训结束时间|100","培训学时|70","前往国家|50", "培训地点|100","组织培训机构|100","培训成绩|100","派出单位|150","是否计入年度学习任务|80","备注|150"};
        String[] titles_school = {"年度|50","参训工号|100","参训人姓名|100","参训人类型|100","时任单位及职务|200","时任职务属性|100"
                ,"参训人员身份|100","培训类别|100","培训班类型|100","培训班名称|200","培训形式|100",
                "培训开始时间|100","培训结束时间|100","培训学时|70","培训地点|150","培训成绩|100","是否计入年度学习任务|80","备注|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUpperTrain record = records.get(i);
            List<String> _identities = new ArrayList();
            if (StringUtils.isNotBlank(record.getIdentity())) {
                String[] identities = record.getIdentity().split(",");
                for (String identity : identities) {
                    if (StringUtils.trimToNull(identity) == null)
                        continue;
                    _identities.add(metaTypeService.getName(Integer.valueOf(identity)));
                }
            }
            if (type == CetConstants.CET_UPPER_TRAIN_TYPE_OW || type == CetConstants.CET_UPPER_TRAIN_TYPE_UNIT) {
                String[] values = {
                        record.getYear() + "",
                        record.getUser().getCode(),
                        record.getUser().getRealname(),
                        record.getTraineeTypeId() == 0 ? record.getOtherTraineeType() : cetTraineeTypeMapper.selectByPrimaryKey(record.getTraineeTypeId()).getName(),
                        record.getTitle(),
                        record.getPostType() == null ? "" : CmTag.getMetaTypeName(record.getPostType()),
                        record.getIdentity() != "" ? StringUtils.join(_identities, ",") : "",
                        record.getOrganizer() != null ? (record.getOrganizer() == 0 ? record.getOtherOrganizer() : metaTypeService.getName(record.getOrganizer())) : "",
                        metaTypeService.getName(record.getTrainType()),
                        record.getTrainName(),
                        record.getIsOnline() ? "线上培训" : "线下培训",
                        DateUtils.formatDate(record.getStartDate(), DateUtils.YYYYMMDD_DOT),
                        DateUtils.formatDate(record.getEndDate(), DateUtils.YYYYMMDD_DOT),
                        record.getPeriod() + "",
                        record.getAddress(),
                        record.getScore(),
                        record.getUnitId() == null ? "" : record.getUnit().getName(),
                        record.getIsValid() ? "是" : "否",
                        record.getRemark()
                };
                valuesList.add(values);
            }else if (type == CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD){
                String[] values = {
                        record.getYear() + "",
                        record.getUser().getCode(),
                        record.getUser().getRealname(),
                        record.getTraineeTypeId() == 0 ? record.getOtherTraineeType() : cetTraineeTypeMapper.selectByPrimaryKey(record.getTraineeTypeId()).getName(),
                        record.getTitle(),
                        record.getPostType() == null ? "" : CmTag.getMetaTypeName(record.getPostType()),
                        record.getIdentity() != "" ? StringUtils.join(_identities, ",") : "",
                        record.getTrainName(),
                        record.getIsOnline() ? "线上培训" : "线下培训",
                        DateUtils.formatDate(record.getStartDate(), DateUtils.YYYYMMDD_DOT),
                        DateUtils.formatDate(record.getEndDate(), DateUtils.YYYYMMDD_DOT),
                        record.getPeriod() + "",
                        record.getCountry(),
                        record.getAddress(),
                        record.getAgency(),
                        record.getScore(),
                        record.getUnitId() == null ? "" : record.getUnit().getName(),
                        record.getIsValid() ? "是" : "否",
                        record.getRemark()
                };
                valuesList.add(values);
            }else if (type == CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL){
                String[] values = {
                        record.getYear() + "",
                        record.getUser().getCode(),
                        record.getUser().getRealname(),
                        record.getTraineeTypeId() == 0 ? record.getOtherTraineeType() : cetTraineeTypeMapper.selectByPrimaryKey(record.getTraineeTypeId()).getName(),
                        record.getTitle(),
                        record.getPostType() == null ? "" : CmTag.getMetaTypeName(record.getPostType()),
                        record.getIdentity() != "" ? StringUtils.join(_identities, ",") : "",
                        record.getSpecialType() == null ? "" : record.getSpecialType() == CetConstants.CET_PROJECT_TYPE_DAILY ? "日常培训" : "专题培训",
                        metaTypeService.getName(record.getTrainType()),
                        record.getTrainName(),
                        record.getIsOnline() ? "线上培训" : "线下培训",
                        DateUtils.formatDate(record.getStartDate(), DateUtils.YYYYMMDD_DOT),
                        DateUtils.formatDate(record.getEndDate(), DateUtils.YYYYMMDD_DOT),
                        record.getPeriod() + "",
                        record.getAddress(),
                        record.getScore(),
                        record.getIsValid() ? "是" : "否",
                        record.getRemark()
                };
                valuesList.add(values);
            }
        }
        if (type == CetConstants.CET_UPPER_TRAIN_TYPE_OW || type == CetConstants.CET_UPPER_TRAIN_TYPE_UNIT){
            String str = type == CetConstants.CET_UPPER_TRAIN_TYPE_OW ? "上级调训-组织部派出_" : "上级调训-其他部门派出_";
            String fileName = str + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles_ow, valuesList, fileName, response);
        }else if (type == CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD){
            String fileName = "上级调训-出国研修_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles_abroad, valuesList, fileName, response);
        }else if (type == CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL){
            String fileName = "党校培训-其他培训_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles_school, valuesList, fileName, response);
        }

    }
}
