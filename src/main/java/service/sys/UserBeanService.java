package service.sys;

import bean.UserBean;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import sys.constants.SystemConstants;

import java.util.Map;

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
    private BranchService branchService;
    @Autowired
    private PartyService partyService;

    @Autowired
    private MemberService memberService;

    public UserBean get(int userId){

        SysUser sysUser = sysUserService.findById(userId);

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setUsername(sysUser.getUsername());
        userBean.setCode(sysUser.getCode());
        userBean.setType(sysUser.getType());
        userBean.setMobile(sysUser.getMobile());

        if(sysUser.getType()== SystemConstants.USER_TYPE_JZG){
            Teacher teacher = teacherService.get(userId);
            if(teacher!=null) {
                userBean.setBirth(teacher.getBirth());
                userBean.setGender(teacher.getGender());
                userBean.setIdcard(teacher.getIdcard());
                userBean.setNation(teacher.getNation());
                userBean.setNativePlace(teacher.getNativePlace());
                userBean.setRealname(teacher.getRealname());
            }
        }else{

            Student student = studentService.get(userId);
            if(student!=null) {
                userBean.setBirth(student.getBirth());
                userBean.setGender(student.getGender());
                userBean.setIdcard(student.getIdcard());
                userBean.setNation(student.getNation());
                userBean.setNativePlace(student.getNativePlace());
                userBean.setRealname(student.getRealname());
            }
        }

        Member member = memberService.get(userId);
        if(member!=null){ // 如果是党员
            userBean.setPoliticalStatus(member.getPoliticalStatus());
            userBean.setPartyId(member.getPartyId());
            userBean.setBranchId(member.getBranchId());
            userBean.setGrowTime(member.getGrowTime());

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            Integer partyId = member.getPartyId();
            Integer branchId = member.getBranchId();
            if (partyId != null) {
                userBean.setParty(partyMap.get(partyId));
            }
            if (branchId != null) {
                userBean.setBranch(branchMap.get(branchId));
            }
        }

        return userBean;
    }
}
