package controller.cla.user;

import controller.cla.ClaBaseController;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cla.*;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import persistence.cla.common.ClaApprovalResult;
import shiro.ShiroHelper;
import sys.constants.ClaConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.ClaHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user/cla")
public class UserClaApplyController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/claApply_view")
    public String claApply_view(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
        if(claApply.getCadreId().intValue() != cadre.getId().intValue()) {
            throw new UnauthorizedException();
        }
        modelMap.put("sysUser", loginUser);
        modelMap.put("cadre", cadre);
        modelMap.put("claApply", claApply);

        List<ClaApplyFile> files = claApplyService.getFiles(claApply.getId());
        modelMap.put("files", files);

        Map<Integer, ClaApprovalResult> approvalResultMap = claApplyService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

        // 本年度的申请记录
        /*ClaApplyExample example = new ClaApplyExample();
        ClaApplyExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadre.getId());
        Integer currentYear = DateUtils.getYear(claApply.getApplyDate());
        criteria.andApplyDateBetween(DateUtils.parseDate(currentYear +"-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(currentYear +"-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");
        List<ClaApply> claApplys = claApplyMapper.selectByExample(example);
        modelMap.put("currentYear", currentYear);
        modelMap.put("claApplys", claApplys);*/

        modelMap.put("justView", true);

        return "cla/user/claApply/claApply_view";
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/claApply")
    public String claApply(ModelMap modelMap) {

        return "cla/user/claApply/claApply_page";
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping("/claApply_data")
    @ResponseBody
    public void claApply_data(@CurrentUser SysUserView loginUser,
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

        ClaApplyExample example = new ClaApplyExample();
        ClaApplyExample.Criteria criteria = example.createCriteria();
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

        long count = claApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApply> record = claApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", record);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping(value = "/claApply_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApply_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (id != null) {
            ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);

            Integer firstTrialStatus = ClaHelper.getClaAdminFirstTrialStatus(claApply.getId());
            if(claApply.getCadreId().intValue() != cadre.getId().intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许删除
                return failed("只有新提交的申请可以撤销");
            }

            ClaApplyFileExample example = new ClaApplyFileExample();
            example.createCriteria().andApplyIdEqualTo(id);
            claApplyFileMapper.deleteByExample(example); // 先删除相关材料

            claApplyService.del(id);
            logger.info(addLog(LogConstants.LOG_CLA, "删除干部请假申请：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/claApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApply_au(Integer cadreId,
                               ClaApply record,
                               @RequestParam(value = "_files[]") MultipartFile[] _files,
                               HttpServletRequest request) {

        // 是否本人操作
        boolean self = false;
        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_CLAADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            self = true;
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);

        List<ClaApplyFile> claApplyFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "cla_apply" + FILE_SEPARATOR + cadre.getUserId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            ClaApplyFile claApplyFile = new ClaApplyFile();
            claApplyFile.setFileName(FileUtils.getFileName(originalFilename));
            claApplyFile.setFilePath(savePath);
            claApplyFile.setCreateTime(new Date());

            claApplyFiles.add(claApplyFile);
        }

        record.setApplyDate(new Date());

        if(record.getStartTime().after(record.getEndTime())){
           return failed("出发时间不能晚于返校时间");
        }

        if(record.getId()==null) {

            record.setCadreId(cadreId);
            record.setCreateTime(new Date());
            record.setIp(IpUtils.getRealIp(request));

            record.setStatus(true);// 提交
            record.setFlowNode(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST);

            claApplyService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "提交干部请假申请：%s", record.getId()));

            // 给干部管理员发短信提醒
            claShortMsgService.sendApplySubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                    "提交干部请假申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }else{

            ClaApply claApply = claApplyMapper.selectByPrimaryKey(record.getId());
            Integer firstTrialStatus = ClaHelper.getClaAdminFirstTrialStatus(record.getId());
            if(claApply.getCadreId().intValue() != cadreId.intValue()
                || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
               return failed("不允许更新");
            }
            record.setCadreId(null);
            record.setStatus(true);// 重新提交
            record.setFlowNode(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST);
            record.setIsFinish(false);

            ClaApprovalLogExample example = new ClaApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            claApprovalLogMapper.deleteByExample(example);

            claApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "更新干部请假申请：%s", record.getId()));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                    "修改干部请假申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(claApply, MixinUtils.baseMixins(), false));
        }
        Integer applyId = record.getId();
        for (ClaApplyFile claApplyFile : claApplyFiles) {
            claApplyFile.setApplyId(applyId);
            claApplyFileMapper.insert(claApplyFile);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("applyId", applyId);
        return resultMap;
    }

    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequestMapping(value = "/claApplyFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApplyFile_del(@CurrentUser SysUserView loginUser, Integer id) {

        ClaApplyFile claApplyFile = claApplyFileMapper.selectByPrimaryKey(id);
        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        ClaApply claApply = claApplyMapper.selectByPrimaryKey(claApplyFile.getApplyId());

        Integer firstTrialStatus = ClaHelper.getClaAdminFirstTrialStatus(claApply.getId());
        if(claApply.getCadreId().intValue() != cadre.getId().intValue()
                || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许删除
           return failed("不允许删除");
        }

        claApplyFileMapper.deleteByPrimaryKey(id);

        logger.info(addLog(LogConstants.LOG_CLA, "删除干部请假文件：%s", claApplyFile.getFilePath()));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/claApply_au")
    public String claApply_au(Integer cadreId, Integer id, ModelMap modelMap) {

        if(cadreId==null || !ShiroHelper.isPermitted(SystemConstants.PERMISSION_CLAADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        if (id != null) {
            ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
            modelMap.put("claApply", claApply);

            CadreView cadre = iCadreMapper.getCadre(cadreId);
            Integer firstTrialStatus = ClaHelper.getClaAdminFirstTrialStatus(id);
            if(claApply.getCadreId().intValue() != cadre.getId().intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
                throw new OpException("不允许更新");
            }

            List<ClaApplyFile> files = claApplyService.getFiles(claApply.getId());
            modelMap.put("files", files);
        }

        //Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        //modelMap.put("passportMap", passportMap);

        modelMap.put("countryList", JSONUtils.toString(countryService.getCountryList()));

        return "cla/user/claApply/claApply_au";
    }
}
