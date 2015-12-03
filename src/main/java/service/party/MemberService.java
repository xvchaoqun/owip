package service.party;

import domain.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.ext.ExtService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Arrays;

@Service
public class MemberService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ExtService extService;

    public Member get(int userId){

         return memberMapper.selectByPrimaryKey(userId);
    }

    @Transactional
    public void add(Member record){

        Integer userId = record.getUserId();
        SysUser sysUser = sysUserService.findById(userId);
        Byte type = sysUser.getType();
        if(type== SystemConstants.USER_TYPE_JZG){

            // 同步教职工信息到ow_member_teacher表
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER); // 教职工党员
            snycTeacher(userId, sysUser.getCode());
        }else if(type==SystemConstants.USER_TYPE_BKS){

            // 同步本科生信息到 ow_member_student表
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT); // 学生党员
            snycStudent(userId, type, sysUser.getCode());
        }else if(type==SystemConstants.USER_TYPE_YJS){

            // 同步研究生信息到 ow_member_student表
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT); // 学生党员
            snycStudent(userId, type, sysUser.getCode());
        }else{
            throw new DBErrorException("添加失败，该账号不是教工或学生");
        }

        Assert.isTrue(memberMapper.insertSelective(record)==1);

        // 更新系统角色  访客->党员
        sysUserService.changeRole(sysUser.getId(), SystemConstants.ROLE_GUEST,
                SystemConstants.ROLE_MEMBER, sysUser.getUsername());
    }


    // 同步教职工党员信息
    public  void snycTeacher(int userId ,String code){

        Teacher teacher = new Teacher();
        teacher.setUserId(userId);
        teacher.setCode(code);

        ExtJzg extJzg = extService.getExtJzg(code);
        if(extJzg!=null){
            teacher.setRealname(extJzg.getXm());
            // 性别
            String xbm = extJzg.getXbm();
            if(StringUtils.equals(xbm, "1"))
                teacher.setGender(SystemConstants.GENDER_MALE);
            else if(StringUtils.equals(xbm, "2"))
                teacher.setGender(SystemConstants.GENDER_FEMALE);
            else
                teacher.setGender(SystemConstants.GENDER_UNKNOWN);

            // 出生年月
            teacher.setBirth(extJzg.getCsrq());

            //+++++++++++++ 同步后面一系列属性
        }

        // 在职或退休，如何判断？
        teacher.setIsRetire(false);

        Assert.isTrue(teacherMapper.insertSelective(teacher) == 1);
    }

    // 同步学生党员信息
    public void snycStudent(int userId, byte userType, String code){

        Student student = new Student();
        student.setUserId(userId);
        student.setCode(code);

        if(userType==SystemConstants.USER_TYPE_BKS){  // 同步本科生信息
            ExtBks extBks = extService.getExtBks(code);
            if(extBks!=null){
                student.setRealname(extBks.getXm());
                // 性别
                String xb = extBks.getXb();
                if(StringUtils.equals(xb, "男"))
                    student.setGender(SystemConstants.GENDER_MALE);
                else if(StringUtils.equals(xb, "女"))
                    student.setGender(SystemConstants.GENDER_FEMALE);
                else
                    student.setGender(SystemConstants.GENDER_UNKNOWN);

                // 出生年月
                student.setBirth(DateUtils.parseDate(extBks.getCsrq(), "yyyyMMdd"));

                //+++++++++++++ 同步后面一系列属性
            }
        }

        if(userType==SystemConstants.USER_TYPE_YJS){  // 同步研究生信息
            ExtYjs extYjs = extService.getExtYjs(code);
            if(extYjs!=null){
                student.setRealname(extYjs.getXm());
                // 性别
                String xbm = extYjs.getXbm();
                if(StringUtils.equals(xbm, "1"))
                    student.setGender(SystemConstants.GENDER_MALE);
                else if(StringUtils.equals(xbm, "2"))
                    student.setGender(SystemConstants.GENDER_FEMALE);
                else
                    student.setGender(SystemConstants.GENDER_UNKNOWN);

                // 出生年月
                student.setBirth(DateUtils.parseDate(extYjs.getCsrq(), "yyyyMMdd"));

                //+++++++++++++ 同步后面一系列属性
            }
        }
        Assert.isTrue(studentMapper.insertSelective(student)==1);
    }

    @Transactional
    public void del(Integer userId){

        memberMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public void batchDel(Integer[] userIds){

        if(userIds==null || userIds.length==0) return;

        MemberExample example = new MemberExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        memberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Member record){

        return memberMapper.updateByPrimaryKeySelective(record);
    }
}
