package controller.member;

import controller.BaseController;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用于在教职工党员中修改注册账号的基本信息及人事信息
 */
@Controller
public class MemberTeacherInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTeacher:edit")
    @RequestMapping(value = "/member_teacherInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_teacherInfo_au(TeacherInfo teacherInfo, SysUserInfo userInfo,
                                 String _birth, String _degreeTime,
                                 String _arriveTime, String _retireTime, HttpServletRequest request) {

        Integer userId = teacherInfo.getUserId();

        SysUserView sysUser = sysUserService.findById(userId);
        Byte source = sysUser.getSource();
        if(source==SystemConstants.USER_SOURCE_BKS || source==SystemConstants.USER_SOURCE_YJS
                || source==SystemConstants.USER_SOURCE_JZG){
            throw new RuntimeException("只能修改非人事库的账号信息");
        }

        if(StringUtils.isNotBlank(_birth)){
            userInfo.setBirth(DateUtils.parseDate(_birth, DateUtils.YYYY_MM_DD));
        }

        if(StringUtils.isNotBlank(_degreeTime)){
            teacherInfo.setDegreeTime(DateUtils.parseDate(_degreeTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_arriveTime)){
            teacherInfo.setArriveTime(DateUtils.parseDate(_arriveTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_retireTime)){
            teacherInfo.setRetireTime(DateUtils.parseDate(_retireTime, DateUtils.YYYY_MM_DD));
        }
        teacherInfo.setIsRetire((teacherInfo.getIsRetire() == null) ? false : teacherInfo.getIsRetire());
        teacherInfo.setIsHonorRetire((teacherInfo.getIsHonorRetire() == null) ? false : teacherInfo.getIsHonorRetire());

        if (userId == null) {
            teacherService.insertSelective(teacherInfo);
            logger.info(addLog(SystemConstants.LOG_OW, "添加教职工党员信息：%s", teacherInfo.getUserId()));
        } else {
            teacherService.updateByPrimaryKeySelective(teacherInfo);
            logger.info(addLog(SystemConstants.LOG_OW, "更新教职工党员信息：%s", teacherInfo.getUserId()));
        }

        // 更新基本信息
        sysUserService.insertOrUpdateUserInfoSelective(userInfo);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTeacher:edit")
    @RequestMapping("/member_teacherInfo_au")
    public String member_teacherInfo_au(int userId, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        Byte source = sysUser.getSource();
        if(source==SystemConstants.USER_SOURCE_BKS || source==SystemConstants.USER_SOURCE_YJS
                || source==SystemConstants.USER_SOURCE_JZG){
            throw new RuntimeException("只能修改非人事库的账号信息");
        }

        TeacherInfo teacher = teacherInfoMapper.selectByPrimaryKey(userId);
        modelMap.put("teacher", teacher);

        return "sys/teacherInfo/member_teacherInfo_au";
    }
}
