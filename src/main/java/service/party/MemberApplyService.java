package service.party;

import domain.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.ext.ExtService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Date;

@Service
public class MemberApplyService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ExtService extService;

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int insertSelective(MemberApply record) {
        return memberApplyMapper.insertSelective(record);
    }

    @Cacheable(value = "MemberApply", key = "#userId")
    public MemberApply get(int userId) {

        return memberApplyMapper.selectByPrimaryKey(userId);
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int updateByPrimaryKeySelective(MemberApply record) {

        return memberApplyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public int updateByExampleSelective(int userId, MemberApply record, MemberApplyExample example) {

        return memberApplyMapper.updateByExampleSelective(record, example);
    }
    // 成为正式党员
    @Transactional
    public void memberPositive(int userId){

        MemberApply memberApply = get(userId);
        if(memberApply==null) throw new DBErrorException("系统错误");

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_POSITIVE);
        record.setPositiveStatus(SystemConstants.APPLY_STATUS_OD_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_GROW)
                .andPositiveStatusEqualTo(SystemConstants.APPLY_STATUS_CHECKED);

        // 1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new DBErrorException("系统错误");

        Member member = new Member();
        member.setUserId(userId);
        member.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE);
        member.setPositiveTime(memberApply.getPositiveTime());
        // 2. 更新党员政治面貌
        if(memberMapper.updateByPrimaryKeySelective(member) == 0)
            throw new DBErrorException("系统错误");
    }

    // 成为预备党员
    @Transactional
    public void memberGrow(int userId) {

        SysUser sysUser = sysUserService.findById(userId);
        MemberApply memberApply = get(userId);
        if(sysUser==null || memberApply==null) throw new DBErrorException("系统错误");

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_GROW);
        record.setGrowStatus(SystemConstants.APPLY_STATUS_OD_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_DRAW)
                .andGrowStatusEqualTo(SystemConstants.APPLY_STATUS_CHECKED);

        //1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new DBErrorException("系统错误");

        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberApply.getPartyId());
        member.setBranchId(memberApply.getBranchId());
        member.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_GROW); // 预备党员

        Assert.isTrue(memberApply.getType() == SystemConstants.APPLY_TYPE_STU
                || memberApply.getType() == SystemConstants.APPLY_TYPE_TECHER);
        if (memberApply.getType() == SystemConstants.APPLY_TYPE_STU) {
            member.setType(SystemConstants.MEMBER_TYPE_STUDENT); // 学生党员

            // 2. 同步学生信息
            snycStudent(userId, sysUser.getType(), sysUser.getCode());

        } else {
            // ++++++++++++待查询离退休状态
            member.setType(SystemConstants.MEMBER_TYPE_TEACHER); // 教职工党员
            // 2. 同步教职工信息
            snycTeacher(userId, sysUser.getCode());
        }
        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setGrowTime(memberApply.getGrowTime());
        //member.setPositiveTime(memberApply.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        if (memberMapper.insertSelective(member) == 0)
            throw new DBErrorException("系统错误");

        // 4. 更新系统角色  访客->党员
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

        teacherMapper.insertSelective(teacher);
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

        studentMapper.insertSelective(student);
    }




}
