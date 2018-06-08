package controller.abroad.user;

import controller.abroad.AbroadBaseController;
import controller.global.OpException;
import domain.abroad.ApplySelf;
import domain.abroad.ApplySelfExample;
import domain.abroad.ApplySelfExample.Criteria;
import domain.abroad.ApplySelfFile;
import domain.abroad.ApplySelfFileExample;
import domain.abroad.ApprovalLogExample;
import domain.abroad.Passport;
import domain.abroad.PassportDrawExample;
import domain.base.Country;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
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
import persistence.abroad.common.ApprovalResult;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.AbroadHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user/abroad")
public class UserApplySelfController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

/*    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/applySelf_download")
    public void applySelf_download(@CurrentUser SysUserView loginUser,
                                   Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);
        
        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        Integer applyId = applySelfFile.getApplyId();
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        if(applySelf.getCadreId().intValue() != cadre.getId().intValue()) {
            throw new UnauthorizedException();
        }

        DownloadUtils.download(request, response,
                springProps.uploadPath + applySelfFile.getFilePath(), applySelfFile.getFileName());
    }*/

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/applySelf_view")
    public String applySelf_view(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
        if(applySelf.getCadreId().intValue() != cadre.getId().intValue()) {
            throw new UnauthorizedException();
        }
        modelMap.put("sysUser", loginUser);
        modelMap.put("cadre", cadre);
        modelMap.put("applySelf", applySelf);

        List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
        modelMap.put("files", files);

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

        // 本年度的申请记录
        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadre.getId());
        Integer currentYear = DateUtils.getYear(applySelf.getApplyDate());
        criteria.andApplyDateBetween(DateUtils.parseDate(currentYear +"-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(currentYear +"-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");
        List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
        modelMap.put("currentYear", currentYear);
        modelMap.put("applySelfs", applySelfs);

        modelMap.put("justView", true);

        return "abroad/user/applySelf/applySelf_view";
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/applySelf")
    public String applySelf(ModelMap modelMap) {

        return "abroad/user/applySelf/applySelf_page";
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/applySelf_data")
    @ResponseBody
    public void applySelf_data(@CurrentUser SysUserView loginUser,
                               @RequestDateRange DateRange _applyDate,
                               Byte type, // 出行时间范围
                                 Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        example.setOrderByClause("create_time desc");

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());
        if (_applyDate.getStart()!=null) {
            criteria.andApplyDateGreaterThanOrEqualTo(_applyDate.getStart());
        }

        if (_applyDate.getEnd()!=null) {
            criteria.andApplyDateLessThanOrEqualTo(_applyDate.getEnd());
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        long count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applySelfs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping(value = "/applySelf_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);

            Integer firstTrialStatus = AbroadHelper.getAdminFirstTrialStatus(applySelf.getId());
            if(applySelf.getCadreId().intValue() != cadre.getId().intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许删除
                return failed("只有新提交的申请可以撤销");
            }
            {
                PassportDrawExample example = new PassportDrawExample();
                example.createCriteria().andApplyIdEqualTo(id);
                if(passportDrawMapper.countByExample(example)>0){
                    return failed("该行程已经申请使用证件，不允许撤销");
                }

               /* // 删除行程下面的申请使用证件
                PassportDrawExample example = new PassportDrawExample();
                example.createCriteria().andApplyIdEqualTo(id);
                passportDrawMapper.deleteByExample(example);*/
            }

            ApplySelfFileExample example = new ApplySelfFileExample();
            example.createCriteria().andApplyIdEqualTo(id);
            applySelfFileMapper.deleteByExample(example); // 先删除相关材料

            applySelfService.del(id);
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除因私出国申请：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/applySelf_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_au(
                               Integer cadreId,
                               ApplySelf record,
                               String _startDate,
                               String _endDate,
                               @RequestParam(value = "_files[]") MultipartFile[] _files,
                               HttpServletRequest request) {

        // 是否本人操作
        boolean self = false;
        if(cadreId==null || ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            self = true;
        }
        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);

        List<ApplySelfFile> applySelfFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "apply_self" + FILE_SEPARATOR + cadre.getUserId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            ApplySelfFile applySelfFile = new ApplySelfFile();
            applySelfFile.setFileName(originalFilename);
            applySelfFile.setFilePath(savePath);
            applySelfFile.setCreateTime(new Date());

            applySelfFiles.add(applySelfFile);
        }

        record.setApplyDate(new Date());

        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        if(record.getStartDate().after(record.getEndDate())){
           return failed("出发日期不能晚于回国日期");
        }

        if(record.getId()==null) {

            record.setCadreId(cadreId);
            record.setCreateTime(new Date());
            record.setIp(IpUtils.getRealIp(request));

            record.setStatus(true);// 提交
            record.setFlowNode(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST);

            applySelfService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "提交因私出国申请：%s", record.getId()));

            // 给干部管理员发短信提醒
            abroadShortMsgService.sendApplySelfSubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_APPLYSELF,
                    "提交因私出国申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }else{

            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(record.getId());
            Integer firstTrialStatus = AbroadHelper.getAdminFirstTrialStatus(record.getId());
            if(applySelf.getCadreId().intValue() != cadreId.intValue()
                || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
               return failed("不允许更新");
            }
            record.setCadreId(null);
            record.setStatus(true);// 重新提交
            record.setFlowNode(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST);
            record.setIsFinish(false);

            ApprovalLogExample example = new ApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            approvalLogMapper.deleteByExample(example);

            applySelfService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_APPLYSELF,
                    "修改因私出国申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(applySelf, MixinUtils.baseMixins(), false));
        }
        Integer applyId = record.getId();
        for (ApplySelfFile applySelfFile : applySelfFiles) {
            applySelfFile.setApplyId(applyId);
            applySelfFileMapper.insert(applySelfFile);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("applyId", applyId);
        return resultMap;
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping(value = "/applySelfFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelfFile_del(@CurrentUser SysUserView loginUser, Integer id) {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);
        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfFile.getApplyId());

        Integer firstTrialStatus = AbroadHelper.getAdminFirstTrialStatus(applySelf.getId());
        if(applySelf.getCadreId().intValue() != cadre.getId().intValue()
                || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许删除
           return failed("不允许删除");
        }

        applySelfFileMapper.deleteByPrimaryKey(id);

        logger.info(addLog(LogConstants.LOG_ABROAD, "删除因私出国文件：%s", applySelfFile.getFilePath()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/applySelf_au")
    public String applySelf_au(Integer cadreId, Integer id, ModelMap modelMap) {

        if(cadreId==null || ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            modelMap.put("applySelf", applySelf);

            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            Integer firstTrialStatus = AbroadHelper.getAdminFirstTrialStatus(id);
            if(applySelf.getCadreId().intValue() != cadre.getId().intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
                throw new OpException("不允许更新");
            }

            List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
            modelMap.put("files", files);
        }

        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        modelMap.put("passportMap", passportMap);

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "abroad/user/applySelf/applySelf_au";
    }
}
