package controller.abroad;

import controller.global.OpException;
import domain.abroad.*;
import domain.abroad.PassportDrawExample.Criteria;
import domain.base.ContentTpl;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import shiro.ShiroHelper;
import sys.constants.*;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Controller
@RequestMapping("/abroad")
public class PassportDrawController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_check")
    public String passportDraw_check(int id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        return "abroad/passportDraw/passportDraw_check";
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_agree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_agree(@CurrentUser SysUserView loginUser, int id, String remark, HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setApproveRemark(StringUtils.trimToNull(remark));

        record.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
        sysApprovalLogService.add(record.getId(), passportDraw.getCadre().getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "审核", SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS, null);

        logger.info(addLog(LogConstants.LOG_ABROAD, "批准申请使用证件（通过）：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_disagree(@CurrentUser SysUserView loginUser, int id, String remark, HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setApproveRemark(StringUtils.trimToNull(remark));
        if (record.getApproveRemark() == null) {
            return failed("请输入原因");
        }
        record.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
        sysApprovalLogService.add(record.getId(), passportDraw.getCadre().getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "审核", SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY, null);

        logger.info(addLog(LogConstants.LOG_ABROAD, "批准申请使用证件（未通过）：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw")
    public String passportDraw(Integer cadreId,
                               @RequestParam(required = false, defaultValue = "1") byte type,
                               ModelMap modelMap) {
        modelMap.put("type", type);
        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "abroad/passportDraw/passportDraw_page";
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_data")
    public void passportDraw_data(HttpServletResponse response,
                                  @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_draw") String sort,
                                  @OrderParam(required = false, defaultValue = "desc") String order,
                                  Integer cadreId,
                                  Integer passportId,
                                  Integer year,
                                  // -1:已删除
                                  @RequestParam(required = false, defaultValue = "1") byte type,
                                  @RequestDateRange DateRange _applyDate,
                                  Byte status,
                                  Byte drawStatus,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  // 导出类型：1：因私出国境 2： 台湾、长期 3： 处理其他事务
                                  @RequestParam(required = false, defaultValue = "1") byte exportType,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportDrawExample example = new PassportDrawExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (passportId != null) { // 查询特定证件的使用记录
            criteria.andPassportIdEqualTo(passportId);
        } else {

            if (type == -1) {
                criteria.andIsDeletedEqualTo(true);
            } else {
                criteria.andIsDeletedEqualTo(false);

                if (type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF ||
                        type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {
                    criteria.andTypeEqualTo(type);
                } else { // 因公赴台、长期因公出国
                    criteria.andTypeIn(Arrays.asList(AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW,
                            AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF));
                }
            }
        }
        if (year != null) {
            criteria.andApplyDateBetween(DateUtils.parseDate(year + "0101"), DateUtils.parseDate(year + "1230"));
        }
        if (_applyDate.getStart() != null) {
            criteria.andApplyDateGreaterThanOrEqualTo(_applyDate.getStart());
        }

        if (_applyDate.getEnd() != null) {
            criteria.andApplyDateLessThanOrEqualTo(_applyDate.getEnd());
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (drawStatus != null) {
            criteria.andDrawStatusEqualTo(drawStatus);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            passportDrawService.passportDraw_export(exportType, example, response);
            return;
        }

        long count = passportDrawMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", passportDraws);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_import")
    public String passportDraw_import(ModelMap modelMap) {

        return "abroad/passportDraw/passportDraw_import";
    }

    // 添加修改证件使用记录
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_au(PassportDraw record,
                                  String passportCode,
                                  Byte dateType,
                                  String toCountry,
                                  String peerStaff,
                                     @RequestParam(value = "_files[]") MultipartFile[] _files,
                                  MultipartFile _attachment,
                                  HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        byte type = record.getType();
        Cadre cadre = null;
        Integer classId = null;
        if (id == null) {

            if (StringUtils.isBlank(passportCode)) {
                return failed("证件号码不能为空");
            }
            Passport passport = iAbroadMapper.getPassport(passportCode);
            if (passport == null) {
                return failed("证件号码不存在");
            }
            classId = passport.getClassId();
            cadre = passport.getCadre();
            record.setCadreId(cadre.getId());
            record.setPassportId(passport.getId());

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            if (record.getUserId() == null) {
                record.setUserId(currentUserId);
            }
            if (record.getDrawUserId() == null) {
                record.setDrawUserId(currentUserId);
            }
        }else{
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            cadre = passportDraw.getCadre();
        }

        ApplySelf applySelf = null;
        if (type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {

            applySelf = new ApplySelf();
            applySelf.setType(dateType);
            applySelf.setApplyDate(record.getApplyDate());
            applySelf.setCadreId(cadre.getId());
            applySelf.setStartDate(record.getRealStartDate());
            applySelf.setEndDate(record.getRealEndDate());
            applySelf.setToCountry(toCountry);

            applySelf.setReason(record.getReason());
            record.setReason(null);

            applySelf.setPeerStaff(peerStaff);
            applySelf.setCostSource(record.getCostSource());
            applySelf.setNeedPassports(classId + "");
            applySelf.setCreateTime(new Date());
            applySelf.setStatus(true);
            applySelf.setIsFinish(true);
            applySelf.setFlowNode(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST);

            applySelf.setIsAgreed(true);
            applySelf.setRemark(record.getRemark());
        }

        List<PassportDrawFile> passportDrawFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {

            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "apply_self" + FILE_SEPARATOR + cadre.getUserId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            PassportDrawFile passportDrawFile = new PassportDrawFile();
            passportDrawFile.setFileName(originalFilename);
            passportDrawFile.setFilePath(savePath);
            passportDrawFile.setCreateTime(new Date());

            passportDrawFiles.add(passportDrawFile);
        }

        if (_attachment != null) {
            String ext = FileUtils.getExtention(_attachment.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
                return failed("附件格式错误，请上传pdf文件");
            }
            String originalFilename = _attachment.getOriginalFilename();
            record.setAttachmentFilename(originalFilename);
            record.setAttachment(uploadPdf(_attachment, "passportDraw-attachment"));
        }

        passportDrawService.addOrUpdate(record, applySelf);

         for (PassportDrawFile passportDrawFile : passportDrawFiles) {
            passportDrawFile.setDrawId(record.getId());
            passportDrawFileMapper.insert(passportDrawFile);
        }

        logger.info(addLog(LogConstants.LOG_ABROAD, "添加/更新办理证件记录：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_au")
    public String passportDraw_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            modelMap.put("passportDraw", passportDraw);
            String reasons = passportDraw.getReason();
            if(passportDraw.getApplyId()!=null){
                ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
                reasons = applySelf.getReason();
                modelMap.put("applySelf", applySelf);
            }
            modelMap.put("reasons", reasons);

            modelMap.put("approvalUser", CmTag.getUserById(passportDraw.getUserId()));
            modelMap.put("drawUser", CmTag.getUserById(passportDraw.getDrawUserId()));
        }

        modelMap.put("countryList", JSONUtils.toString(countryService.getCountryList()));

        return "abroad/passportDraw/passportDraw_au";
    }

    // 导入使用记录
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        // <rowIdx, >
        List<Map<String, Object>> records = new ArrayList<>();

        Date now = new Date();
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            PassportDraw passportDraw = new PassportDraw();

            String passportCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(passportCode)) {
                continue;
            }

            Passport passport = iAbroadMapper.getPassport(passportCode);
            if (passport == null) {
                throw new OpException("第{0}行证件号码[{1}]不存在", row, passportCode);
            }
            passportDraw.setPassportId(passport.getId());
            passportDraw.setCadreId(passport.getCadreId());

            int col = 2;

            String _type = StringUtils.trim(xlsRow.get(col++));
            Byte type = null;
            if (StringUtils.isNumeric(_type)) {
                type = Byte.parseByte(_type);
            }
            if (type == null || !AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP.containsKey(type)) {
                throw new OpException("第{0}行使用类别[{1}]不存在", row, _type);
            }
            passportDraw.setType(type);

            String _useType = StringUtils.trim(xlsRow.get(col++));
            Byte useType = null;
            if (StringUtils.isNumeric(_useType)) {
                useType = Byte.parseByte(_useType);
            }
            if (useType != null && !AbroadConstants.ABROAD_PASSPORT_DRAW_USE_TYPE_MAP.containsKey(useType)) {
                throw new OpException("第{0}行用途[{1}]不存在", row, _useType);
            }
            passportDraw.setUseType(useType);

            // 事由
            String reason = StringUtils.trim(xlsRow.get(col++));

            boolean needSign = StringUtils.contains(StringUtils.trim(xlsRow.get(col++)), "是");
            passportDraw.setNeedSign(needSign); // 因私&台湾

            passportDraw.setApproveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            passportDraw.setDrawTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            String _dateType = StringUtils.trim(xlsRow.get(col++));
            Byte dateType = null;
            if (StringUtils.isNumeric(_dateType)) {
                dateType = Byte.parseByte(_dateType);
            }
            if (dateType != null && !AbroadConstants.ABROAD_APPLY_SELF_DATE_TYPE_MAP.containsKey(dateType)) {
                throw new OpException("第{0}行出行时间范围[{1}]不存在", row, _dateType);
            }
            //
            String realToCountry = StringUtils.trim(xlsRow.get(col++));
            passportDraw.setRealToCountry(realToCountry); // 因私
            String peerStaff = StringUtils.trim(xlsRow.get(col++));
            String costSource = StringUtils.trim(xlsRow.get(col++));
            passportDraw.setCostSource(costSource); // 台湾

            Date realStartDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));
            if (realStartDate == null) {
                throw new OpException("第{0}行使用开始日期为空", row);
            }

            Date realEndDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));
            Date realReturnDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++)));

            if(passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW
            || passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF
            || passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {

                passportDraw.setReason(reason); // 台湾
                passportDraw.setStartDate(realStartDate);
                passportDraw.setEndDate(realEndDate);
            }else{

                passportDraw.setRealStartDate(realStartDate);
                passportDraw.setRealEndDate(realEndDate);
            }

            passportDraw.setRealReturnDate(realReturnDate);
            passportDraw.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));

            passportDraw.setCreateTime(now);

            passportDraw.setApplyDate(passportDraw.getApproveTime() == null ? now : passportDraw.getApproveTime());
            passportDraw.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS);
            passportDraw.setUserId(currentUserId);
            passportDraw.setDrawUserId(currentUserId);
            passportDraw.setApproveRemark("批量导入");

            passportDraw.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN);
            passportDraw.setReturnRemark("批量导入");

            Map<String, Object> recordMap = new HashMap<>();
            recordMap.put("passportDraw", passportDraw);

            if (type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {
                ApplySelf applySelf = new ApplySelf();

                applySelf.setType(dateType);
                applySelf.setApplyDate(passportDraw.getApplyDate());
                applySelf.setCadreId(passport.getCadreId());
                applySelf.setStartDate(realStartDate);
                applySelf.setEndDate(realEndDate);
                applySelf.setToCountry(realToCountry);
                applySelf.setReason(reason);
                applySelf.setPeerStaff(peerStaff);
                applySelf.setCostSource(costSource);
                applySelf.setNeedPassports(passport.getClassId() + "");
                applySelf.setCreateTime(now);
                applySelf.setStatus(true);
                applySelf.setIsFinish(true);
                applySelf.setFlowNode(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST);

                /*applySelf.setFlowNodes(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST
                        + "," + AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST);
                applySelf.setFlowUsers(currentUserId
                        + "," + currentUserId);*/

                applySelf.setIsAgreed(true);
                applySelf.setRemark("批量导入使用记录");

                recordMap.put("applySelf", applySelf);
            }

            records.add(recordMap);
        }

        int addCount = passportDrawService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ABROAD,
                "导入证件使用记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    // 管理员添加申请
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_add")
    public String passportDraw_add(Byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        return "abroad/passportDraw/passportDraw_add";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_draw")
    public String passportDraw_draw(Integer id, HttpServletRequest request, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {

            request.setAttribute("isView", true);
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        ContentTpl shortMsgTpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PASSPORTDRAW);
        Integer userId = passport.getUser().getId();
        String msgTitle = userBeanService.getMsgTitle(userId);
        String shortMsg = MessageFormat.format(shortMsgTpl.getContent(), msgTitle,
                passport.getPassportClass().getName());
        ;
        modelMap.put("shortMsg", shortMsg);
        modelMap.put("mobile", userBeanService.getMsgMobile(userId));

        return "abroad/passportDraw/passportDraw_draw";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_draw(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                    MultipartFile _drawRecord, String _returnDate, Integer id) {

        PassportDraw record = new PassportDraw();

       /* if (_drawRecord == null || _drawRecord.isEmpty()) {
           return failed("请选择证件拍照");
        }*/
        record.setId(id);
        record.setReturnDate(DateUtils.parseDate(_returnDate, DateUtils.YYYY_MM_DD));

        if (_drawRecord != null && !_drawRecord.isEmpty()) {
            String originalFilename = _drawRecord.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "draw" + FILE_SEPARATOR + "draw" + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_drawRecord, new File(springProps.uploadPath + savePath));

            record.setDrawRecord(savePath);
        }
        record.setDrawUserId(loginUser.getId());
        record.setDrawTime(new Date());
        record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW);

        passportDrawService.drawPassport(record);

        logger.info(addLog(LogConstants.LOG_ABROAD, "领取证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_return")
    public String passportDraw_return(Integer id, HttpServletRequest request, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {

            request.setAttribute("isView", true);
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        modelMap.put("countryList", JSONUtils.toString(countryService.getCountryList()));

        if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {
            return "abroad/passportDraw/passportDraw_other_return";
        }

        return "abroad/passportDraw/passportDraw_return";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_return(@CurrentUser SysUserView loginUser,
                                      HttpServletRequest request,
                                      byte usePassport,
                                      String _realReturnDate,
                                      MultipartFile _attachment,
                                      MultipartFile _useRecord,
                                      String _base64,
                                      @RequestParam(required = false, defaultValue = "0") Integer _rotate,
                                      String _realStartDate,
                                      String _realEndDate,
                                      String realToCountry, String remark, Integer id) throws IOException, InterruptedException {

        PassportDraw record = new PassportDraw();

        /*if (_useRecord == null || _useRecord.isEmpty()) {
           return failed("请选择证件使用记录拍照");
        }*/

        if (_attachment != null) {
            String ext = FileUtils.getExtention(_attachment.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
                return failed("附件格式错误，请上传pdf文件");
            }
            String originalFilename = _attachment.getOriginalFilename();
            record.setAttachmentFilename(originalFilename);
            record.setAttachment(uploadPdf(_attachment, "passportDraw-attachment"));
        }

        record.setId(id);
        record.setUsePassport(usePassport);
        record.setRealReturnDate(DateUtils.parseDate(_realReturnDate, DateUtils.YYYY_MM_DD));
        record.setRealStartDate(DateUtils.parseDate(_realStartDate, DateUtils.YYYY_MM_DD));
        record.setRealEndDate(DateUtils.parseDate(_realEndDate, DateUtils.YYYY_MM_DD));

        if (record.getRealStartDate() != null && record.getRealEndDate() != null
                && record.getRealStartDate().after(record.getRealEndDate())) {
            return failed("实际出行时间有误。");
        }

        record.setRealToCountry(StringUtils.trimToNull(realToCountry));

        if (_useRecord != null && !_useRecord.isEmpty()) {
            String originalFilename = _useRecord.getOriginalFilename();
            String savePath = FILE_SEPARATOR
                    + "draw" + FILE_SEPARATOR + "use" + FILE_SEPARATOR
                    + UUID.randomUUID().toString() + FileUtils.getExtention(originalFilename);

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(_useRecord.getInputStream())
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);

            record.setUseRecord(savePath);
        } else if (StringUtils.isNotBlank(_base64)) {

            String savePath = FILE_SEPARATOR
                    + "draw" + FILE_SEPARATOR + "use" + FILE_SEPARATOR + UUID.randomUUID().toString() + ".jpg";

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(ImageUtils.decodeBase64ToBufferedImage(_base64.split("base64,")[1]))
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);
            //ImageUtils.decodeBase64ToImage(_base64.split("base64,")[1], springProps.uploadPath + realPath, fileName);

            record.setUseRecord(savePath);
        }

        record.setReturnRemark(remark);
        if (record.getRealReturnDate() == null) // 默认当天为归还时间
            record.setRealReturnDate(new Date());

        if (usePassport != AbroadConstants.ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN)
            record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN);

        passportDrawService.returnPassport(record);

        logger.info(addLog(LogConstants.LOG_ABROAD, "归还证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    // 修改归还日期
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/reset_passportDraw_returnDate")
    public String reset_passportDraw_returnDate(Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);

        return "abroad/passportDraw/reset_passportDraw_returnDate";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/reset_passportDraw_returnDate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_reset_passportDraw_returnDate(Integer id, String _returnDate) throws IOException {

        PassportDraw record = new PassportDraw();
        record.setReturnDate(DateUtils.parseDate(_returnDate, DateUtils.YYYY_MM_DD));

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS)
                .andDrawStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW);
        passportDrawMapper.updateByExampleSelective(record, example);

        logger.info(addLog(LogConstants.LOG_ABROAD, "修改归还日期：%s", id));
        return success(FormUtils.SUCCESS);
    }

    // 重置归还状态
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/reset_passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_reset_passportDraw_return(@CurrentUser SysUserView loginUser, Integer id) throws IOException {

        passportDrawService.resetReturnPassport(id);

        logger.info(addLog(LogConstants.LOG_ABROAD, "重置归还证件的归还状态为未归还：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_view")
    public String passportDraw_view(Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        CadreView cadre = iCadreMapper.getCadre(passportDraw.getCadreId());
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("passportDraw", passportDraw);


        if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        return "abroad/user/passportDraw/passportDraw_view";
    }

    /*@RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportDrawService.del(id);
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除领取证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    // 删除（默认逻辑删除）
    @RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        Boolean isDeleted,
                        Boolean isReal, // 是否真删除
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            isDeleted = BooleanUtils.isTrue(isDeleted);
            isReal = BooleanUtils.isTrue(isReal);
            passportDrawService.batchDel(ids, isDeleted, isReal);
            String op = isReal?"真删除":(isDeleted?"逻辑删除":"恢复");

            logger.info(log(LogConstants.LOG_ABROAD, "批量{0}领取证件申请：{1}", op, StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/attach/passportDrawFile")
    public void attach_passportDrawFile(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PassportDrawFile passportDrawFile = passportDrawFileMapper.selectByPrimaryKey(id);
        if (passportDrawFile != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(passportDrawFile.getDrawId());
            if (passportDraw.getCadre().getUserId().intValue() != loginUser.getId()) {
                // 本人、干部管理员或管理员才可以下载
                if (!ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN)) {
                    throw new UnauthorizedException();
                }
            }

            String path = springProps.uploadPath + passportDrawFile.getFilePath();
            DownloadUtils.download(request, response, path, passportDrawFile.getFileName());
        }
    }

}
