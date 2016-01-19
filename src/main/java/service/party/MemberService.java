package service.party;

import domain.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import java.util.Date;

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
        sysUserService.changeRole(userId, SystemConstants.ROLE_GUEST,
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
            teacher.setNativePlace(extJzg.getJg());
            teacher.setNativePlace(extJzg.getMz());
            teacher.setIdcard(extJzg.getSfzh());
            teacher.setEducation(extJzg.getZhxlmc());
            teacher.setDegree(extJzg.getZhxwm());
            //teacher.setDegreeTime(); 学位授予日期
            //teacher.setMajor(extJzg.getz); 所学专业
            teacher.setSchool(extJzg.getXlbyxx());
            //teacher.setSchoolType(); 毕业学校类型
            if(extJzg.getLxrq()!=null)
                teacher.setArriveTime(DateUtils.formatDate(extJzg.getLxrq(), DateUtils.YYYY_MM_DD));
            teacher.setAuthorizedType(extJzg.getBzlx());
            teacher.setStaffType(extJzg.getRylx());
            teacher.setStaffStatus(extJzg.getRyzt());
            teacher.setPostClass(extJzg.getGwlb());
            teacher.setPostType(extJzg.getGwjb());
            teacher.setOnJob(extJzg.getSfzg());
            //teacher.setProPost(); 专业技术职务
            teacher.setProPostLevel(extJzg.getZjgwdj());
            teacher.setTitleLevel(extJzg.getZc()); // 职称级别
            teacher.setManageLevel(extJzg.getGlgwdj());
            //teacher.setOfficeLevel(extJzg.getgq);  工勤岗位等级
            //teacher.setPost(extJzg.getXzjb());  行政职务
            // teacher.setPostLevel(); 任职级别
            teacher.setTalentTitle(extJzg.getRcch());
            // teacher.setAddress(extJzg.getjz); 居住地址
            // teacher.setMaritalStatus(); 婚姻状况
            teacher.setEmail(extJzg.getDzxx());
            teacher.setMobile(extJzg.getYddh());
            teacher.setPhone(extJzg.getJtdh());

            // 是否退休 :在岗，退休，病休，离校，待聘,内退,离休, NULL
            teacher.setIsRetire(!StringUtils.equals(extJzg.getSfzg(), "在岗"));
            //teacher.setRetireTime(); 退休时间
            teacher.setIsHonorRetire(StringUtils.equals(extJzg.getSfzg(), "离休"));

            teacher.setCreateTime(new Date());
        }

        // 在职或退休，如何判断？
        teacher.setIsRetire(false);

        if(teacherMapper.selectByPrimaryKey(userId)==null)
            teacherMapper.insertSelective(teacher);
        else
            teacherMapper.updateByPrimaryKey(teacher);
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
                student.setNation(extBks.getMz());
                student.setNativePlace(extBks.getSf());
                student.setIdcard(extBks.getSfzh());
                student.setType(extBks.getKslb());
                //student.setEduLevel(extBks.getPycc()); 培养层次
                //student.setEduType(extBks.getPylx()); 培养类型
                //student.setEduCategory(extBks.getJylb()); 培养级别
                student.setEduWay(extBks.getPyfs());
                //student.setIsFullTime(extBks.get); 是否全日制
                //student.setEnrolYear(extBks.getZsnd()+""); 招生年度
                //student.setPeriod(extBks.getXz()+""); 学制
                student.setGrade(extBks.getNj());
                //student.setActualEnrolTime(DateUtils.parseDate(extBks.getSjrxny(), DateUtils.YYYYMMDD)); 实际入学年月
                //student.setExpectGraduateTime(DateUtils.parseDate(extBks.getYjbyny(), "yyyyMM")); 预计毕业年月
                //student.setDelayYear(extBks.getYqbynx()); 预计毕业年限
                //student.setActualGraduateTime(DateUtils.parseDate(extBks.getSjbyny(), "yyyyMM")); 实际毕业年月
                student.setSyncSource(SystemConstants.USER_SOURCE_BKS);
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
                student.setBirth(DateUtils.parseDate(StringUtils.substring(extYjs.getCsrq(), 0, 8), "yyyyMMdd"));

                //+++++++++++++ 同步后面一系列属性
                student.setNation(extYjs.getMz());
                //student.setNativePlace(extYjs.get); 籍贯
                student.setIdcard(extYjs.getSfzh());
                student.setType(extYjs.getXslb());
                student.setEduLevel(extYjs.getPycc());
                student.setEduType(extYjs.getPylx());
                student.setEduCategory(extYjs.getJylb());
                student.setEduWay(extYjs.getPyfs());
                //student.setIsFullTime(extYjs.get); 是否全日制
                student.setEnrolYear(extYjs.getZsnd()+"");
                student.setPeriod(extYjs.getXz() + "");
                student.setGrade(extYjs.getNj() + "");

                student.setActualEnrolTime(DateUtils.parseDate(StringUtils.substring(extYjs.getSjrxny(), 0, 6), "yyyyMM"));
                student.setExpectGraduateTime(DateUtils.parseDate(StringUtils.substring(extYjs.getYjbyny(), 0, 6), "yyyyMM"));
                student.setDelayYear(extYjs.getYqbynx());
                student.setActualGraduateTime(DateUtils.parseDate(StringUtils.substring(extYjs.getSjbyny(), 0, 6), "yyyyMM"));
                student.setSyncSource(SystemConstants.USER_SOURCE_YJS);
            }
        }

        if(studentMapper.selectByPrimaryKey(userId)==null)
            studentMapper.insertSelective(student);
        else
            studentMapper.updateByPrimaryKey(student);
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
