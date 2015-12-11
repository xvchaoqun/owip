package service.sys;

import bean.UserBean;
import domain.Member;
import domain.Student;
import domain.SysUser;
import domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.party.MemberService;
import sys.constants.SystemConstants;

/**
 * Created by fafa on 2015/12/11.
 */
@Service
public class UserBeanService extends BaseMapper{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MemberService memberService;

    public UserBean get(int userId){

        SysUser sysUser = sysUserService.findById(userId);

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setCode(sysUser.getCode());
        userBean.setType(sysUser.getType());

        if(sysUser.getType()== SystemConstants.USER_TYPE_JZG){
            Teacher teacher = teacherService.get(userId);
            userBean.setBirth(teacher.getBirth());
            userBean.setGender(teacher.getGender());
            userBean.setIdcard(teacher.getIdcard());
            userBean.setNation(teacher.getNation());
            userBean.setNativePlace(teacher.getNativePlace());
            userBean.setRealname(teacher.getRealname());
        }else{

            Student student = studentService.get(userId);
            userBean.setBirth(student.getBirth());
            userBean.setGender(student.getGender());
            userBean.setIdcard(student.getIdcard());
            userBean.setNation(student.getNation());
            userBean.setNativePlace(student.getNativePlace());
            userBean.setRealname(student.getRealname());
        }

        Member member = memberService.get(userId);
        if(member!=null){ // 如果是党员
            userBean.setPoliticalStatus(member.getPoliticalStatus());
            userBean.setPartyId(member.getPartyId());
            userBean.setBranchId(member.getBranchId());
            userBean.setGrowTime(member.getGrowTime());
        }

        return userBean;
    }
}
