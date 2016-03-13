package controller.user;

import controller.BaseController;
import domain.*;
import domain.PassportDrawExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserPassportDrawController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportDraw_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_del(@CurrentUser SysUser loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        if (id != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            if(passportDraw.getStatus()==0 && passportDraw.getCadreId().intValue() == cadre.getId().intValue()) {

                PassportDrawFileExample example = new PassportDrawFileExample();
                example.createCriteria().andDrawIdEqualTo(id);
                passportDrawFileMapper.deleteByExample(example); // 先删除相关材料

                passportDrawService.del(id);
                logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除使用证件申请：%s", id));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_select")
    public String passportDraw_select() {

        return "user/passportDraw/passportDraw_select";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_self")
    public String passportDraw_self(@CurrentUser SysUser loginUser, Integer pageSize, Integer pageNo, ModelMap modelMap) {
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

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());
        // 未出行
        criteria.andStartDateGreaterThan(new Date());

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applySelfs", applySelfs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "user/passportDraw/passportDraw_self";
    }
    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_self_select")
    public String passportDraw_self_select(@CurrentUser SysUser loginUser, int applyId, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        modelMap.put("applySelf", applySelf);

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        List<Passport> passports = passportService.findByCadreId(cadre.getId());
        modelMap.put("passports", passports);

        return "user/passportDraw/passportDraw_self_select";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_self_sign")
    public String passportDraw_self_sign(@CurrentUser SysUser loginUser, String type, Integer passportId, ModelMap modelMap) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);

        if(StringUtils.equals(type, "tw")) {
            Passport passportTw = null;
            List<Passport> passports = passportService.findByCadreId(cadre.getId());
            for (Passport passport : passports) {
                if(CmTag.typeEqualsCode(passport.getClassId(), "mt_passport_tw")){
                    passportTw = passport;
                }
            }
            if(passportTw == null)throw new RuntimeException("您还未提交大陆居民往来台湾通行证");
            modelMap.put("passport", passportTw);

            return "user/passportDraw/passportDraw_self_sign_tw";
        }
        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if(passport.getCadreId().intValue() != cadre.getId().intValue()) throw new UnauthorizedException();
        modelMap.put("passport", passport);

        if(StringUtils.equals(type, "add"))
            return "user/passportDraw/passportDraw_self_sign_add";

        return "user/passportDraw/passportDraw_self_sign";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_self_confirm")
    public String passportDraw_self_confirm(@CurrentUser SysUser loginUser, int applyId, int passportId, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        modelMap.put("applySelf", applySelf);

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        List<Passport> passports = passportService.findByCadreId(cadre.getId());
        modelMap.put("passports", passports);

        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        modelMap.put("passport", passport);

        return "user/passportDraw/passportDraw_self_confirm";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_tw")
    public String passportDraw_tw(ModelMap modelMap) {
        return "index";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_tw_page")
    public String passportDraw_tw_page(ModelMap modelMap) {
        return "user/passportDraw/passportDraw_tw";
    }
    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_other")
    public String passportDraw_other(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        List<Passport> passports = passportService.findByCadreId(cadre.getId());
        modelMap.put("passports", passports);

        return "user/passportDraw/passportDraw_other";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw")
    public String passportDraw() {

        return "index";
    }
    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_page")
    public String passportDraw_page(@CurrentUser SysUser loginUser,
                                    @RequestParam(required = false, defaultValue = "1")  Byte type,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_draw") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        modelMap.put("type", type);

        PassportDrawExample example = new PassportDrawExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause(String.format("%s %s", sort, order));

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());

        int count = passportDrawMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passportDraws", passportDraws);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "user/passportDraw/passportDraw_page";
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportDraw_self_sign_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_self_sign_add(@CurrentUser SysUser loginUser, int id){

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setNeedSign(true);
        passportDrawMapper.updateByPrimaryKeySelective(record);

        return success(FormUtils.SUCCESS);
    }


    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportDraw_self_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_self_au(@CurrentUser SysUser loginUser,
                                  int applyId,
                                  int passportId,
                                  @RequestParam(required = false, defaultValue = "0")boolean needSign,
                                  String remark,
                                  HttpServletRequest request) {

        int userId = loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        int cadreId = cadre.getId();

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if(applySelf.getCadreId()!=cadreId || passport.getCadreId() != cadreId) throw new UnauthorizedException();

        PassportDraw record = new PassportDraw();
        record.setCadreId(cadre.getId());
        record.setType(SystemConstants.PASSPORT_DRAW_TYPE_SELF);
        record.setApplyId(applyId);
        record.setPassportId(passportId);
        record.setApplyDate(new Date());
        record.setNeedSign(needSign);
        record.setRemark(remark);

        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_INIT);
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
        record.setJobCertify(false);
        passportDrawService.insertSelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "申请使用证件（因私出国）：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportDraw_tw_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_tw_au(@CurrentUser SysUser loginUser,
                                     String _startDate,
                                     String _endDate,
                                     String reason,
                                     String costSource,
                                       @RequestParam(value = "_files[]") MultipartFile[] _files,
                                       @RequestParam(required = false, defaultValue = "0")boolean needSign,
                                       HttpServletRequest request) {

        int userId= loginUser.getId();
        List<PassportDrawFile> passportDrawFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  File.separator
                    + "apply_self" + File.separator + userId + File.separator
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            PassportDrawFile passportDrawFile = new PassportDrawFile();
            passportDrawFile.setFileName(originalFilename);
            passportDrawFile.setFilePath(savePath);
            passportDrawFile.setCreateTime(new Date());

            passportDrawFiles.add(passportDrawFile);
        }

        Cadre cadre = cadreService.findByUserId(userId);
        int cadreId = cadre.getId();

        Passport passportTw = null;
        MetaType passportTwType = CmTag.getMetaTypeByCode("mt_passport_tw");
        List<Passport> passports = passportService.findByCadreId(cadreId);
        for (Passport passport : passports) {
            if(passport.getClassId().intValue() == passportTwType.getId())
                passportTw = passport;
        }
        if(passportTw==null){
            throw new RuntimeException("您还未提交大陆居民往来台湾通行证");
        }
        if(passportTw.getCadreId().intValue() != cadreId) throw new UnauthorizedException();

        PassportDraw record = new PassportDraw();
        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        record.setReason(reason);
        record.setCostSource(costSource);
        record.setCadreId(cadre.getId());
        record.setType(SystemConstants.PASSPORT_DRAW_TYPE_TW);
        record.setPassportId(passportTw.getId());
        record.setApplyDate(new Date());
        record.setNeedSign(needSign);

        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_INIT);
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
        record.setJobCertify(false);
        passportDrawService.insertSelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "申请使用证件（出访台湾）：%s", record.getId()));

        for (PassportDrawFile passportDrawFile : passportDrawFiles) {
            passportDrawFile.setDrawId(record.getId());
            passportDrawFileMapper.insert(passportDrawFile);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportDraw_other_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_other_au(@CurrentUser SysUser loginUser,
                                     int passportId,
                                     String _startDate,
                                     String _endDate,
                                     String reason,
                                     @RequestParam(value = "_files[]") MultipartFile[] _files,
                                     String remark,
                                     HttpServletRequest request) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        int cadreId = cadre.getId();

        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if(passport.getCadreId() != cadreId) throw new UnauthorizedException();

        List<PassportDrawFile> passportDrawFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  File.separator
                    + "apply_self" + File.separator + userId + File.separator
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

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
        record.setReason(reason);
        record.setCadreId(cadre.getId());
        record.setType(SystemConstants.PASSPORT_DRAW_TYPE_OTHER);
        record.setPassportId(passportId);
        record.setApplyDate(new Date());
        record.setRemark(remark);

        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_INIT);
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
        record.setJobCertify(false);
        passportDrawService.insertSelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "申请使用证件（处理其他事务）：%s", record.getId()));

        for (PassportDrawFile passportDrawFile : passportDrawFiles) {
            passportDrawFile.setDrawId(record.getId());
            passportDrawFileMapper.insert(passportDrawFile);
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresRoles("cadre")
    @RequestMapping("/passportDraw_au")
    public String passportDraw_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            modelMap.put("passportDraw", passportDraw);
        }
        return "user/passportDraw/passportDraw_au";
    }*/
}
