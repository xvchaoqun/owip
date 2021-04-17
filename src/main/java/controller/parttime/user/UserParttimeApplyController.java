package controller.parttime.user;

import controller.global.OpException;
import controller.parttime.ParttimeBaseController;
import domain.cadre.CadreView;
import domain.parttime.*;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import persistence.parttime.common.ParttimeApprovalResult;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.helper.ParttimeHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user/parttime")
public class UserParttimeApplyController extends ParttimeBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userparttimeApply:*")
    @RequestMapping("/parttimeApply_view")
    public String parttimeApply_view(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
        if(parttimeApply.getCadreId().intValue() != cadre.getId().intValue()) {
            throw new UnauthorizedException();
        }
        modelMap.put("sysUser", loginUser);
        modelMap.put("cadre", cadre);
        modelMap.put("parttimeApply", parttimeApply);

        List<ParttimeApplyFile> files = parttimeApplyService.getFiles(parttimeApply.getId());
        modelMap.put("files", files);

        Map<Integer, ParttimeApprovalResult> approvalResultMap = parttimeApplyService.getApprovalResultMap(id);
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

        return "parttime/user/parttimeApply/parttimeApply_view";
    }

    @RequiresPermissions("userParttimeApply:*")
    @RequestMapping("/parttimeApply")
    public String parttimeApply(ModelMap modelMap) {

        return "parttime/user/parttimeApply/parttimeApply_page";
    }

    @RequiresPermissions("userParttimeApply:*")
    @RequestMapping("/parttimeApply_data")
    @ResponseBody
    public void parttimeApply_data(@CurrentUser SysUserView loginUser,
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

        ParttimeApplyExample example = new ParttimeApplyExample();
        ParttimeApplyExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        example.setOrderByClause("create_time desc");

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());
        if (_applyDate.getStart()!=null) {
            criteria.andApplyTimeGreaterThanOrEqualTo(_applyDate.getStart());
        }

        if (_applyDate.getEnd()!=null) {
            criteria.andApplyTimeLessThanOrEqualTo(_applyDate.getEnd());
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        long count = parttimeApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApply> record = parttimeApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("userParttimeApply:*")
    @RequestMapping(value = "/parttimeApply_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_parttimeApply_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (id != null) {
            ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);

            Integer firstTrialStatus = ParttimeHelper.getParttimeAdminFirstTrialStatus(parttimeApply.getId());
            if(parttimeApply.getCadreId().intValue() != cadre.getId().intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许删除
                return failed("只有新提交的申请可以撤销");
            }

            ParttimeApplyFileExample example = new ParttimeApplyFileExample();
            example.createCriteria().andApplyIdEqualTo(id);
            parttimeApplyFileMapper.deleteByExample(example); // 先删除相关材料
            parttimeApplyMapper.deleteByPrimaryKey(id);
            logger.info(addLog(LogConstants.LOG_CLA, "删除干部请假申请：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/parttimeApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_parttimeApply_au(Integer cadreId,
                               ParttimeApply record,
                               MultipartFile[] _files,
                               HttpServletRequest request) throws IOException, InterruptedException {

        // 是否本人操作
        boolean self = false;
        if(cadreId==null || !ShiroHelper.isPermitted(RoleConstants.PERMISSION_CLAADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            self = true;
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);

        List<ParttimeApplyFile> parttimeApplyFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {

            String originalFilename = _file.getOriginalFilename();
            /*String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "cla_apply" + FILE_SEPARATOR + cadre.getUserId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));*/

            String savePath = upload(_file, "parttime_apply");

            ParttimeApplyFile parttimeApplyFile = new ParttimeApplyFile();
            parttimeApplyFile.setFileName(FileUtils.getFileName(originalFilename));
            parttimeApplyFile.setFilePath(savePath);
            parttimeApplyFile.setCreateTime(new Date());

            parttimeApplyFiles.add(parttimeApplyFile);
        }

        if(record.getStartTime().after(record.getEndTime())){
           return failed("出发时间不能晚于返校时间");
        }

        if(record.getId()==null) {

            record.setCadreId(cadreId);
            Date date = new Date();
            record.setCreateTime(date);
            record.setApplyTime(date);
            record.setIp(IpUtils.getRealIp(request));

            record.setStatus(true);// 提交
            record.setFlowNode(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST);

            parttimeApplyMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "提交干部请假申请：%s", record.getId()));

            // 给干部管理员发消息提醒
            parttimeShortMsgService.sendApplySubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                    "提交干部请假申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }else{

            ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(record.getId());
            Integer firstTrialStatus = ParttimeHelper.getParttimeAdminFirstTrialStatus(record.getId());
            if(parttimeApply.getCadreId().intValue() != cadreId.intValue()
                || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
               return failed("不允许更新");
            }
            record.setCadreId(null);
            record.setStatus(true);// 重新提交
            record.setFlowNode(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST);
            record.setIsFinish(false);

            ParttimeApprovalLogExample example = new ParttimeApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            parttimeApprovalLogMapper.deleteByExample(example);

            parttimeApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "更新兼职申报申请：%s", record.getId()));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                    "修改兼职申报申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(parttimeApply, MixinUtils.baseMixins(), false));
        }
        Integer applyId = record.getId();
        for (ParttimeApplyFile parttimeApplyFile : parttimeApplyFiles) {
            parttimeApplyFile.setApplyId(applyId);
            parttimeApplyFileMapper.insert(parttimeApplyFile);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("applyId", applyId);
        return resultMap;
    }

    @RequiresPermissions("userParttimeApply:*")
    @RequestMapping(value = "/parttimeApplyFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_parttimeApplyFile_del(@CurrentUser SysUserView loginUser, Integer id) {

        ParttimeApplyFile parttimeApplyFile = parttimeApplyFileMapper.selectByPrimaryKey(id);
        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(parttimeApplyFile.getApplyId());

        Integer firstTrialStatus = ParttimeHelper.getParttimeAdminFirstTrialStatus(parttimeApply.getId());
        if(parttimeApply.getCadreId().intValue() != cadre.getId().intValue()
                || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许删除
           return failed("不允许删除");
        }

        parttimeApplyFileMapper.deleteByPrimaryKey(id);

        logger.info(addLog(LogConstants.LOG_CLA, "删除干部请假文件：%s", parttimeApplyFile.getFilePath()));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/parttimeApply_au")
    public String parttimeApply_au(Integer cadreId, Integer id, ModelMap modelMap) {

        if(cadreId==null || !ShiroHelper.isPermitted(RoleConstants.PERMISSION_COMPANYAPPLY)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        if (id != null) {
            ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
            modelMap.put("parttimeApply", parttimeApply);

            CadreView cadre = iCadreMapper.getCadre(cadreId);
            Integer firstTrialStatus = ParttimeHelper.getParttimeAdminFirstTrialStatus(id);
            if(parttimeApply.getCadreId().intValue() != cadre.getId().intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
                throw new OpException("不允许更新");
            }

            List<ParttimeApplyFile> files = parttimeApplyService.getFiles(parttimeApply.getId());
            modelMap.put("files", files);
        }

        //Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        //modelMap.put("passportMap", passportMap);

        modelMap.put("countryList", JSONUtils.toString(countryService.getCountryList()));

        return "parttime/user/parttimeApply/parttimeApply_au";
    }
}
