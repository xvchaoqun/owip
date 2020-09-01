package controller.member;

import domain.cadre.CadreView;
import domain.sys.StudentInfo;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.sys.AvatarService;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 党员人事基础信息维护
 */
@Controller
public class MemberBaseInfoController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected AvatarService avatarService;

    // 修改
    @RequiresPermissions("memberBaseInfo:edit")
    @RequestMapping("/memberBaseInfo_au")
    public String memberBaseInfo_au(int userId, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {
            // 如果是现任干部，不允许修改籍贯、户籍地、出生地、手机号
            CadreView cv = CmTag.getCadreByUserId(userId);
            if(cv!=null && NumberUtils.contains(cv.getStatus(),
                CadreConstants.CADRE_STATUS_NOW_SET.toArray())) {

                modelMap.put("cadre", cv);
            }
        }

        if (sysUser.isCasUser()) {

            // 门户账号基础信息维护
            SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("ui", ui);

            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("teacherInfo", teacherInfo);

            return "sys/userInfo/baseInfo_au";
        }else if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {

            // 系统教职工账号（注册或后台添加）基础信息维护
            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("teacherInfo", teacherInfo);

            return "sys/userInfo/teacherInfo_au";
        } else {
             // 系统学生账号（注册或后台添加）基础信息维护
            StudentInfo student = studentInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("student", student);

            return "sys/userInfo/studentInfo_au";
        }
    }

    @RequiresPermissions("memberBaseInfo:edit")
    @RequestMapping(value = "/baseInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_baseInfo_au(int userId, SysUserInfo record, TeacherInfo teacherInfo, String _arriveTime, MultipartFile _avatar) throws IOException {

        record.setUserId(userId);

        String avatar = avatarService.uploadAvatar(_avatar);
        record.setAvatar(avatar);

        filterCadreReserveInfo(userId, record);

        if(teacherInfo!=null) {
           teacherInfo.setUserId(userId);
           teacherInfo.setIsRetire(BooleanUtils.isTrue(teacherInfo.getIsRetire()));
           if (StringUtils.isNotBlank(_arriveTime)) {
                teacherInfo.setArriveTime(DateUtils.parseDate(_arriveTime, DateUtils.YYYY_MM_DD));
           }
        }

        sysUserService.insertOrUpdateUserInfoSelective(record, teacherInfo);

        memberService.addModify(userId, "修改账号基本信息");

        return success(FormUtils.SUCCESS);
    }

    // 如果是现任干部，不允许修改籍贯、户籍地、出生地、手机号
    private void filterCadreReserveInfo(int userId, SysUserInfo record){

        SysUserView sysUser = sysUserService.findById(userId);
        if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {

            CadreView cv = CmTag.getCadreByUserId(userId);
            if(cv!=null && NumberUtils.contains(cv.getStatus(),
                CadreConstants.CADRE_STATUS_NOW_SET.toArray())) {

                record.setNativePlace(null);
                record.setHomeplace(null);
                record.setHousehold(null);
                record.setMobile(null);
            }
        }
    }

    @RequiresPermissions("memberBaseInfo:edit")
    @RequestMapping(value = "/studentInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_studentInfo_au(StudentInfo record, SysUserInfo userInfo,
                                 @DateTimeFormat(pattern = DateUtils.YYYYMM) Date _actualEnrolTime,
                                 @DateTimeFormat(pattern = DateUtils.YYYYMM) Date _expectGraduateTime,
                                 @DateTimeFormat(pattern = DateUtils.YYYYMM) Date actualGraduateTime,
                                 HttpServletRequest request) {

        int userId = record.getUserId();

        SysUserView sysUser = sysUserService.findById(userId);
        Byte source = sysUser.getSource();
        if (source == SystemConstants.USER_SOURCE_BKS || source == SystemConstants.USER_SOURCE_YJS
                || source == SystemConstants.USER_SOURCE_JZG) {
            return failed("只能修改系统注册账号的基本信息");
        }

        record.setActualEnrolTime(_actualEnrolTime);
        record.setExpectGraduateTime(_expectGraduateTime);
        record.setActualGraduateTime(actualGraduateTime);

        studentInfoService.updateByPrimaryKeySelective(record);
        logger.info(log(LogConstants.LOG_MEMBER, "更新学生党员基本信息：{0}", record.getUserId()));

        // 更新基本信息
        sysUserService.insertOrUpdateUserInfoSelective(userInfo);

        memberService.addModify(userId, "修改账号基本信息");

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberBaseInfo:edit")
    @RequestMapping(value = "/teacherInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_teacherInfo_au(TeacherInfo record, SysUserInfo userInfo, String _degreeTime,
                                 String _arriveTime, String _retireTime, HttpServletRequest request) {

        int userId = record.getUserId();
        SysUserView sysUser = sysUserService.findById(userId);
        Byte source = sysUser.getSource();
        if (source == SystemConstants.USER_SOURCE_BKS || source == SystemConstants.USER_SOURCE_YJS
                || source == SystemConstants.USER_SOURCE_JZG) {
            return failed("只能修改非人事库的账号信息");
        }

        if (StringUtils.isNotBlank(_degreeTime)) {
            record.setDegreeTime(DateUtils.parseDate(_degreeTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_arriveTime)) {
            record.setArriveTime(DateUtils.parseDate(_arriveTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_retireTime)) {
            record.setRetireTime(DateUtils.parseDate(_retireTime, DateUtils.YYYY_MM_DD));
        }
        record.setIsRetire((record.getIsRetire() == null) ? false : record.getIsRetire());
        record.setIsHonorRetire((record.getIsHonorRetire() == null) ? false : record.getIsHonorRetire());

        teacherInfoService.updateByPrimaryKeySelective(record);
        logger.info(log(LogConstants.LOG_MEMBER, "更新教职工党员信息：{0}", record.getUserId()));

        // 更新基本信息
        filterCadreReserveInfo(userId, userInfo);
        sysUserService.insertOrUpdateUserInfoSelective(userInfo);

        memberService.addModify(userId, "修改账号基本信息");

        return success(FormUtils.SUCCESS);
    }
}
