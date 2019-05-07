package controller.member;

import controller.global.OpException;
import domain.sys.StudentInfo;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 用于在学生党员中修改注册账号的基本信息及人事信息
 */
@Controller
public class MemberStudentInfoController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberStudent:edit")
    @RequestMapping(value = "/member_studentInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_studentInfo_au(StudentInfo studentInfo, SysUserInfo userInfo, String _birth,
                                        @DateTimeFormat(pattern = DateUtils.YYYYMM)Date _actualEnrolTime,
                                        @DateTimeFormat(pattern = DateUtils.YYYYMM)Date _expectGraduateTime,
                                        @DateTimeFormat(pattern = DateUtils.YYYYMM)Date actualGraduateTime,
                                        HttpServletRequest request) {

        Integer userId = studentInfo.getUserId();

        SysUserView sysUser = sysUserService.findById(userId);
        Byte source = sysUser.getSource();
        if(source==SystemConstants.USER_SOURCE_BKS || source==SystemConstants.USER_SOURCE_YJS
                || source==SystemConstants.USER_SOURCE_JZG){
            return failed("只能修改系统注册账号的基本信息");
        }

        if(StringUtils.isNotBlank(_birth)){
            userInfo.setBirth(DateUtils.parseDate(_birth, DateUtils.YYYY_MM_DD));
        }

        studentInfo.setIsFullTime(BooleanUtils.isTrue(studentInfo.getIsFullTime()));

        studentInfo.setActualEnrolTime(_actualEnrolTime);
        studentInfo.setExpectGraduateTime(_expectGraduateTime);
        studentInfo.setActualGraduateTime(actualGraduateTime);

        if (userId == null) {
            studentInfoService.insertSelective(studentInfo);
            logger.info(addLog(LogConstants.LOG_MEMBER, "添加学生党员基本信息：%s", studentInfo.getUserId()));
        } else {
            studentInfoService.updateByPrimaryKeySelective(studentInfo);
            logger.info(addLog(LogConstants.LOG_MEMBER, "更新学生党员基本信息：%s", studentInfo.getUserId()));
        }

        // 更新基本信息
        sysUserService.insertOrUpdateUserInfoSelective(userInfo);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStudent:edit")
    @RequestMapping("/member_studentInfo_au")
    public String member_studentInfo_au(int userId, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        Byte source = sysUser.getSource();
        if(source==SystemConstants.USER_SOURCE_BKS || source==SystemConstants.USER_SOURCE_YJS
                || source==SystemConstants.USER_SOURCE_JZG){
            throw new OpException("只能修改系统注册账号的基本信息");
        }

        StudentInfo student = studentInfoMapper.selectByPrimaryKey(userId);
        modelMap.put("student", student);

        return "sys/userInfo/member_studentInfo_au";
    }
}
