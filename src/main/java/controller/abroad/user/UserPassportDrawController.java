package controller.abroad.user;

import controller.abroad.AbroadBaseController;
import domain.abroad.*;
import domain.abroad.PassportDrawExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
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
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user/abroad")
public class UserPassportDrawController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping(value = "/passportDraw_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (id != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            if(passportDraw.getStatus()== AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_INIT
                    && passportDraw.getCadreId().intValue() == cadre.getId().intValue()) {

                PassportDrawFileExample example = new PassportDrawFileExample();
                example.createCriteria().andDrawIdEqualTo(id);
                passportDrawFileMapper.deleteByExample(example); // 先删除相关材料

                passportDrawService.del(id);
                logger.info(addLog(LogConstants.LOG_ABROAD, "删除使用证件申请：%s", id));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_select")
    public String passportDraw_select(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        int cadreId = cadre.getId();

        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        modelMap.put("passports", passportMap.values());

        return "abroad/user/passportDraw/passportDraw_select";
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_self")
    public String passportDraw_self(Integer cadreId, Integer pageSize, Integer pageNo, ModelMap modelMap, HttpServletRequest request) {
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        ApplySelfExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        criteria.andCadreIdEqualTo(cadreId);
        // 未出行
        criteria.andStartDateGreaterThan(new Date());

        long count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applySelfs", applySelfs);
        request.setAttribute("isView", false);
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "abroad/user/passportDraw/passportDraw_self";
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_self_select")
    public String passportDraw_self_select(Integer cadreId, int applyId, ModelMap modelMap, HttpServletRequest request) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        modelMap.put("applySelf", applySelf);
        request.setAttribute("isView", false);

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        modelMap.put("passports", passportMap.values());

        return "abroad/user/passportDraw/passportDraw_self_select";
    }

    // 申请签注页面
    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_self_sign")
    public String passportDraw_self_sign(Integer cadreId, String type,
                                         Integer passportId, Integer id, ModelMap modelMap) {

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if(passport==null || passport.getCadreId().intValue() != cadreId.intValue()) throw new UnauthorizedException();
        modelMap.put("passport", passport);

        if(id!=null){
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            modelMap.put("passportDraw", passportDraw);
        }

        if(StringUtils.equals(type, "tw")) {
            /*Passport passportTw = null;
            List<Passport> passports = passportService.findByCadreId(cadre.getId());
            for (Passport passport : passports) {
                if(CmTag.typeEqualsCode(passport.getClassId(), "mt_passport_tw")){
                    passportTw = passport;
                }
            }
            if(passportTw == null)throw new OpException("您还未提交大陆居民往来台湾通行证");
            modelMap.put("passport", passportTw);*/

            return "abroad/user/passportDraw/passportDraw_self_sign_tw";
        }


        if(StringUtils.equals(type, "add"))
            return "abroad/user/passportDraw/passportDraw_self_sign_add";

        return "abroad/user/passportDraw/passportDraw_self_sign";
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_self_confirm")
    public String passportDraw_self_confirm(Integer cadreId, int applyId, int passportId,
                                            HttpServletRequest request, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        modelMap.put("applySelf", applySelf);
        request.setAttribute("isView", false);

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        modelMap.put("passports", passportMap.values());

        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        modelMap.put("passport", passport);

        return "abroad/user/passportDraw/passportDraw_self_confirm";
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw")
    public String passportDraw(@CurrentUser SysUserView loginUser,
                                    @RequestParam(required = false, defaultValue = "1")  Byte type,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        modelMap.put("type", type);

        return "abroad/user/passportDraw/passportDraw_page";
    }
    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/userPassportDraw_data")
    @ResponseBody
    public void userPassportDraw_data(@CurrentUser SysUserView loginUser,
                                    @RequestParam(required = false, defaultValue = "1")  Byte type,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_draw") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
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
        criteria.andIsDeletedEqualTo(false);

        if(type==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF ||
                type==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER){
            criteria.andTypeEqualTo(type);
        }else{ // 因公赴台、长期因公出国、因公出访持因私证件
            criteria.andTypeIn(Arrays.asList(AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW,
                    AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF,
                    AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF));
        }
        example.setOrderByClause(String.format("%s %s", sort, order));

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());

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

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping(value = "/passportDraw_self_sign_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_self_sign_add(@CurrentUser SysUserView loginUser, int id){

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setNeedSign(true);
        passportDrawMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_ABROAD, "申请使用证件（因私出国）-签注"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping(value = "/passportDraw_self_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_self_au(Integer cadreId,
                                  int applyId,
                                  int passportId,
                                  @RequestParam(required = false, defaultValue = "0")boolean needSign,
                                  byte useType,
                                  String remark,
                                  HttpServletRequest request) {

        boolean isSelf = false;
        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            isSelf = true;
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if(applySelf.getCadreId().intValue()!=cadreId || passport.getCadreId().intValue() != cadreId) throw new UnauthorizedException();

        PassportDraw record = new PassportDraw();
        record.setCadreId(cadreId);
        record.setType(AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF);
        record.setApplyId(applyId);
        record.setPassportId(passportId);

        record.setNeedSign(needSign);
        record.setUseType(useType);
        record.setRemark(remark);
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.insertSelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "申请使用证件（因私出国）：%s", record.getId()));

        sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                isSelf?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "申请使用证件", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);

        // 给干部管理员发短信提醒
        abroadShortMsgService.sendPassportDrawSubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));

        return success(FormUtils.SUCCESS);
    }

    // 申请 因公赴台、长期因公出国
    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_tw")
    public String passportDraw_tw(Integer cadreId, ModelMap modelMap) {

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        modelMap.put("passports", passportMap.values());
        modelMap.put("passportTw", passportService.findTwPassport(cadreId));

        return "abroad/user/passportDraw/passportDraw_tw";
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping(value = "/passportDraw_tw_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_tw_au(/*@CurrentUser SysUserView loginUser,*/ Integer cadreId,
                                     Byte type, //2因公赴台  4 长期因公出国
                                     Integer passportId,
                                     String _startDate,
                                     String _endDate,
                                     String reason,
                                     String costSource,
                                       MultipartFile[] _files,
                                       @RequestParam(required = false, defaultValue = "0")boolean needSign,
                                       HttpServletRequest request) throws IOException, InterruptedException {
        boolean isSelf = false;
        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            isSelf = true;
        }

        if(type==null || (type != AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW
                && type != AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF
                && type != AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF)){
           return failed("请选择申请类型");
        }
        if(passportId==null || passportMapper.selectByPrimaryKey(passportId)==null){
           return failed("请选择证件");
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        Passport passportTw = null;
        if(type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW) {

            passportTw = passportService.findTwPassport(cadreId);
            if (passportTw == null) {
               return failed("您还未提交大陆居民往来台湾通行证");
            }
            if(passportId.intValue() != passportTw.getId()){
               return failed("因公赴台，请选择“大陆居民往来台湾通行证”");
            }
            if (passportTw.getCadreId().intValue() != cadreId) throw new UnauthorizedException();
        }

        List<PassportDrawFile> passportDrawFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            /*String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "apply_self" + FILE_SEPARATOR + cadre.getUserId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));*/

            String savePath = upload(_file, "apply_self");

            PassportDrawFile passportDrawFile = new PassportDrawFile();
            passportDrawFile.setFileName(originalFilename);
            passportDrawFile.setFilePath(savePath);
            passportDrawFile.setCreateTime(new Date());

            passportDrawFiles.add(passportDrawFile);
        }

        PassportDraw record = new PassportDraw();
        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        if(record.getStartDate().after(record.getEndDate())){
           return failed("出行时间不能晚于回国时间");
        }

        record.setReason(reason);
        record.setCostSource(costSource);
        record.setCadreId(cadre.getId());
        record.setType(type);
        record.setPassportId(passportId);
        record.setNeedSign(needSign);
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.insertSelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "申请使用证件（%s）：%s",
                AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP.get(type), record.getId()));

        sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                isSelf?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "申请使用证件", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP.get(type));

        // 给干部管理员发短信提醒
        abroadShortMsgService.sendPassportDrawSubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));

        for (PassportDrawFile passportDrawFile : passportDrawFiles) {
            passportDrawFile.setDrawId(record.getId());
            passportDrawFileMapper.insert(passportDrawFile);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping("/passportDraw_other")
    public String passportDraw_other(Integer cadreId, ModelMap modelMap) {

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        modelMap.put("passports", passportMap.values());

        return "abroad/user/passportDraw/passportDraw_other";
    }

    @RequiresPermissions(value = {"userPassportDraw:*", "passportDraw:edit"}, logical = Logical.OR)
    @RequestMapping(value = "/passportDraw_other_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_other_au(Integer cadreId,
                                     int passportId,
                                     String _startDate,
                                     String _endDate,
                                     String reason,
                                     MultipartFile[] _files,
                                     String remark,
                                     HttpServletRequest request) throws IOException, InterruptedException {

        boolean isSelf = false;
        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            isSelf = true;
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);

        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if(passport.getCadreId() != cadreId.intValue()) throw new UnauthorizedException();

        List<PassportDrawFile> passportDrawFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            /*String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "apply_self" + FILE_SEPARATOR + cadre.getUserId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));*/

            String savePath = upload(_file, "apply_self");

            PassportDrawFile passportDrawFile = new PassportDrawFile();
            passportDrawFile.setFileName(originalFilename);
            passportDrawFile.setFilePath(savePath);
            passportDrawFile.setCreateTime(new Date());

            passportDrawFiles.add(passportDrawFile);
        }

        PassportDraw record = new PassportDraw();
        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        if(record.getStartDate().after(record.getEndDate())){
           return failed("开始日期不能晚于结束日期");
        }

        record.setReason(reason);
        record.setCadreId(cadre.getId());
        record.setType(AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER);
        record.setPassportId(passportId);
        record.setRemark(remark);
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.insertSelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "申请使用证件（处理其他事务）：%s", record.getId()));

        sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                isSelf?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "申请使用证件", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);

        // 给干部管理员发短信提醒
        abroadShortMsgService.sendPassportDrawSubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));

        for (PassportDrawFile passportDrawFile : passportDrawFiles) {
            passportDrawFile.setDrawId(record.getId());
            passportDrawFileMapper.insert(passportDrawFile);
        }

        return success(FormUtils.SUCCESS);
    }
}
