package controller.cet;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cet.CetTraineeType;
import domain.cet.CetTraineeTypeExample;
import domain.cet.CetUpperTrain;
import domain.cet.CetUpperTrainExample;
import domain.cet.CetUpperTrainExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MixinUtils;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetUpperTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("cetUpperTrain:list")
    @RequestMapping("/cetUpperTrain")
    public String cetUpperTrain(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                Boolean type,
                                byte upperType,
                                byte addType,
                                Integer unitId,
                                Integer userId,
                                ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("cetUpperTrain:list")
                && !ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CET_ADMIN_UPPER,
                RoleConstants.ROLE_CET_ADMIN_UNIT)) {
            throw new UnauthorizedException();
        }

        modelMap.put("cls", cls);
        modelMap.put("type", type);
        modelMap.put("upperType", upperType);
        modelMap.put("addType", addType);

        if (unitId != null) {
            modelMap.put("unit", CmTag.getUnit(unitId));
        }
        if (userId != null) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        return "cet/cetUpperTrain/cetUpperTrain_page";
    }

    //@RequiresPermissions("cetUpperTrain:list")
    @RequestMapping("/cetUpperTrain_data")
    @ResponseBody
    public void cetUpperTrain_data(HttpServletResponse response,
                                   byte upperType,
                                   byte addType,
                                   @RequestParam(required = false, defaultValue = "1") Byte cls,
                                   String title,
                                   Boolean type,
                                   Integer unitId,
                                   Integer userId,
                                   Integer postId,
                                   Integer organizer,
                                   Integer trainType,
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
        Criteria criteria = example.createCriteria().andUpperTypeEqualTo(upperType);
        example.setOrderByClause("id desc");

        CetUpperTrainExample checkExample = new CetUpperTrainExample();
        Criteria checkCriteria = checkExample.createCriteria()
                .andUpperTypeEqualTo(upperType)
                .andIsDeletedEqualTo(false);

        if (type != null) {
            criteria.andTypeEqualTo(type);
            checkCriteria.andTypeEqualTo(type);

            if (type) {
                // 其他部门派出，单位审核后，组织部待审核
                checkCriteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS).andIsValidIsNull();
            } else {
                // 组织部派出，组织部待审核
                checkCriteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
            }

        } else {
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
                if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {// 此时type肯定不为空
                    if (type) {
                        // 其他部门派出，单位审核后，组织部待审核
                        criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS).andIsValidIsNull();
                    } else {
                        // 组织部派出，组织部待审核
                        criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
                    }
                } else {
                    criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
                }
            } else if (cls == 3) {
                criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS);
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
        if (organizer != null) {
            criteria.andOrganizerEqualTo(organizer);
        }
        if (trainType != null) {
            criteria.andTrainTypeEqualTo(trainType);
        }

        if (postId != null) {
            criteria.andPostIdEqualTo(postId);
        }

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
            if (adminUnitIdSet.size() == 0) {
                throw new UnauthorizedException();
            }

            SecurityUtils.getSubject().checkRole(upperType == CetConstants.CET_UPPER_TRAIN_UPPER
                    ? RoleConstants.ROLE_CET_ADMIN_UPPER : RoleConstants.ROLE_CET_ADMIN_UNIT);
            criteria.andUnitAdmin(adminUnitIdSet);
            checkCriteria.andUnitAdmin(adminUnitIdSet);
        } else {
            criteria.andIdIsNull();
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUpperTrain_export(example, response);
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
                                   @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                   MultipartFile _word, MultipartFile _pdf,
                                   Boolean check,// 审批
                                   HttpServletRequest request) throws IOException, InterruptedException {

        byte upperType = record.getUpperType();
        CetUpperTrain oldRecord = null;
        Integer id = record.getId();
        byte addType = record.getAddType();
        if (id == null) {
            record.setType(BooleanUtils.isTrue(record.getType()));
        } else {
            oldRecord = cetUpperTrainMapper.selectByPrimaryKey(id);
            record.setAddType(null);
        }

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

            if (ShiroHelper.isPermitted("cetUpperTrain:edit")) {
                Integer currentUserId = ShiroHelper.getCurrentUserId();
                Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);

                if (id != null) {
                    Integer oldUnitId = oldRecord.getUnitId();
                    if (oldUnitId == null || !adminUnitIdSet.contains(oldUnitId)) {
                        throw new UnauthorizedException(); // 非单位管理员
                    }
                } else {
                    Integer unitId = record.getUnitId();
                    List<Integer> leaderUserIds = userIds == null ? new ArrayList<>() : Arrays.asList(userIds);
                    if ((unitId != null || leaderUserIds.size() > 0) && (unitId == null || !adminUnitIdSet.contains(unitId))) {
                        throw new UnauthorizedException(); // 非单位管理员
                    }
                }

                SecurityUtils.getSubject().checkRole(upperType == CetConstants.CET_UPPER_TRAIN_UPPER
                        ? RoleConstants.ROLE_CET_ADMIN_UPPER : RoleConstants.ROLE_CET_ADMIN_UNIT);

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
                // 打回后重新提交
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
            } else {
                record.setStatus(null);
            }

            //if(!ShiroHelper.isPermitted("cetUpperTrain:edit")) {

            int currentUserId = ShiroHelper.getCurrentUserId();

            boolean isUnitAdmin = true;
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
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
                SecurityUtils.getSubject().checkRole(upperType == CetConstants.CET_UPPER_TRAIN_UPPER
                        ? RoleConstants.ROLE_CET_ADMIN_UPPER : RoleConstants.ROLE_CET_ADMIN_UNIT);
                if (oldRecord.getIsValid() != null) { // 组织部确认后，不允许单位修改
                    throw new OpException("党委组织部已确认，不允许修改。");
                }
            } else {
                SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_TRAINEE);

                if (id == null) {
                    userIds = new Integer[]{currentUserId};
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
            cetUpperTrainService.insertSelective(record, userIds);
            logger.info(log(LogConstants.LOG_CET, "添加上级调训或单位培训：{0}", id));
        } else {
            cetUpperTrainService.updateByPrimaryKeySelective(record, BooleanUtils.isTrue(check));
            logger.info(addLog(LogConstants.LOG_CET, "更新上级调训或单位培训：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping("/cetUpperTrain_au")
    public String cetUpperTrain_au(Integer id,
                                   byte upperType,
                                   byte addType,
                                   ModelMap modelMap) {

        if (id != null) {
            CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);
            upperType = cetUpperTrain.getUpperType();
            modelMap.put("cetUpperTrain", cetUpperTrain);

            modelMap.put("sysUser", CmTag.getUserById(cetUpperTrain.getUserId()));
        }
        //modelMap.put("type", BooleanUtils.isTrue(type) ? 1 : 0);
        modelMap.put("upperType", upperType);
        modelMap.put("addType", addType);

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {
            int currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
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

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW
                || addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF) {

            if(ShiroHelper.isPermitted("cet:menu:dispatch")) {

                modelMap.put("upperUnits", unitService.findAll().values());
            }else {

                List<Unit> upperUnits = iCetMapper.findUpperUnits(upperType);
                modelMap.put("upperUnits", upperUnits);
            }
        } else {
            throw new UnauthorizedException();
        }

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

    // 上传培训总结
    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping(value = "/cetUpperTrain_uploadNote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_uploadNote(int id, byte upperType, byte addType, MultipartFile _word, MultipartFile _pdf,
                                           HttpServletRequest request) throws IOException, InterruptedException {

        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            CetUpperTrain oldRecord = cetUpperTrainMapper.selectByPrimaryKey(id);

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Integer unitId = oldRecord.getUnitId();
            Integer leaderUserId = oldRecord.getUserId();

            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
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
    public Map cetUpperTrain_selectCadres_tree(Byte addType, byte upperType) throws IOException {

        if (addType == null) return null;

        LinkedHashSet<CadreView> cadreViews = null;
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");
            cadreViews = new LinkedHashSet<CadreView>(cadreService.findAll().values());
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            SecurityUtils.getSubject().checkRole(upperType == CetConstants.CET_UPPER_TRAIN_UPPER
                    ? RoleConstants.ROLE_CET_ADMIN_UPPER : RoleConstants.ROLE_CET_ADMIN_UNIT);

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
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
    public String cetUpperTrain_import() {

        return "cet/cetUpperTrain/cetUpperTrain_import";
    }

    @RequiresPermissions("cetUpperTrain:import")
    @RequestMapping(value = "/cetUpperTrain_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_import(byte upperType,
                                       HttpServletRequest request) throws InvalidFormatException, IOException {

        if(!CetConstants.CET_UPPER_TRAIN_MAP.containsKey(upperType)){
            return failed("参数有误。");
        }
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
            record.setUpperType(upperType);

            String _upperTrainType = StringUtils.trim(xlsRow.get(0));
            CetTraineeTypeExample example = new CetTraineeTypeExample();
            example.createCriteria().andNameEqualTo(_upperTrainType);
            List<CetTraineeType> upperTrainTypes = cetTraineeTypeMapper.selectByExample(example);
            if(upperTrainTypes.size() == 1) {
                record.setUpperTrainTypeId(upperTrainTypes.get(0).getId());
            }

            String userCode = StringUtils.trim(xlsRow.get(1));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getId());

            String title = StringUtils.trimToNull(xlsRow.get(3));
            record.setTitle(title);
            String _postType = StringUtils.trimToNull(xlsRow.get(4));
            MetaType postType = CmTag.getMetaTypeByName("mc_post", _postType);
            if (StringUtils.isNotBlank(_postType) && postType == null) {
                throw new OpException("第{0}行职务属性[{1}]不存在", row, _postType);
            }
            if (postType != null) {
                record.setPostId(postType.getId());
            }

            CadreView cadre = cadreService.dbFindByUserId(uv.getId());
            if (cadre != null) {
                if (record.getTitle() == null) {
                    record.setTitle(cadre.getTitle());
                }
                if (record.getPostId() == null) {
                    record.setPostId(cadre.getPostType());
                }
            }
            String _year = StringUtils.trimToNull(xlsRow.get(5));
            if (StringUtils.isBlank(_year) || !NumberUtils.isDigits(_year)) {
                throw new OpException("第{0}行年度有误", row);
            }
            record.setYear(Integer.valueOf(_year));

            String _organizerType = StringUtils.trimToNull(xlsRow.get(6));
            if (StringUtils.isBlank(_organizerType)) {
                throw new OpException("第{0}行培训班主办方为空", row);
            }
            MetaType organizerType = CmTag.getMetaTypeByName(
                    "mc_cet_upper_train_organizer" + (upperType == CetConstants.CET_UPPER_TRAIN_UPPER ? "" : "2"), _organizerType);
            if (organizerType == null) {
                record.setOrganizer(0);
                record.setOtherOrganizer(_organizerType);
            }

            String _trainType = StringUtils.trimToNull(xlsRow.get(7));
            if (StringUtils.isBlank(_trainType)) {
                throw new OpException("第{0}行培训班类型为空", row);
            }
            MetaType trainType = CmTag.getMetaTypeByName(
                    "mc_cet_upper_train_type" + (upperType == CetConstants.CET_UPPER_TRAIN_UPPER ? "" : "2"), _trainType);
            if (trainType == null) {
                throw new OpException("第{0}行培训班类型不存在", row);
            }
            record.setTrainType(trainType.getId());

            String trainName = StringUtils.trimToNull(xlsRow.get(8));
            if (StringUtils.isBlank(trainName)) {
                throw new OpException("第{0}行培训班名称为空", row);
            }
            record.setTrainName(trainName);

            record.setStartDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(9))));
            record.setEndDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(10))));

            String period = StringUtils.trimToNull(xlsRow.get(11));
            if (StringUtils.isBlank(period) || !NumberUtils.isCreatable(period)) {
                throw new OpException("第{0}行培训学时有误", row);
            }
            record.setPeriod(new BigDecimal(period));
            record.setAddress(StringUtils.trimToNull(xlsRow.get(12)));

            String _type = StringUtils.trimToNull(xlsRow.get(13));
            record.setType(!StringUtils.equals(_type, "党委组织部"));
            if (record.getType()) {
                String unitCode = StringUtils.trimToNull(xlsRow.get(14));
                if (StringUtils.isBlank(unitCode)) {
                    throw new OpException("第{0}行单位编码为空", row);
                }
                Unit unit = unitService.findRunUnitByCode(unitCode);
                if (unit == null) {
                    throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
                }
                record.setUnitId(unit.getId());
            }

            record.setIsValid(!StringUtils.equals(StringUtils.trimToNull(xlsRow.get(15)), "是"));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(16)));

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

    public void cetUpperTrain_export(CetUpperTrainExample example, HttpServletResponse response) {

        List<CetUpperTrain> records = cetUpperTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"派出类型|100", "派出单位|100", "参训人|100", "时任单位及职务|100|left",
                "职务属性|100", "培训班主办方|100", "培训班类型|100",
                "培训班名称|100", "培训开始时间|100", "培训结束时间|100", "培训学时|100", "是否计入年度学习任务|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUpperTrain record = records.get(i);
            String[] values = {
                    record.getType() + "",
                    record.getUnitId() + "",
                    record.getUserId() + "",
                    record.getTitle(),
                    record.getPostId() + "",
                    record.getOrganizer() + "",
                    record.getTrainType() + "",
                    record.getTrainName(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    record.getPeriod() + "",
                    record.getIsValid() + ""
            };
            valuesList.add(values);
        }
        String fileName = "上级调训或单位培训_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
