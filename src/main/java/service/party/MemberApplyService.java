package service.party;

import domain.Member;
import domain.MemberApply;
import domain.MemberApplyExample;
import domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Date;

@Service
public class MemberApplyService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MemberService memberService;

    /*@Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int insertSelective(MemberApply record) {
        return memberApplyMapper.insertSelective(record);
    }*/

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
        /*if (memberApply.getType() == SystemConstants.APPLY_TYPE_STU) {
            member.setType(SystemConstants.MEMBER_TYPE_STUDENT); // 学生党员
            // 2. 同步学生信息
            snycStudent(userId, sysUser.getType(), sysUser.getCode());

        } else {
            // ++++++++++++待查询离退休状态
            member.setType(SystemConstants.MEMBER_TYPE_TEACHER); // 教职工党员
            // 2. 同步教职工信息
            snycTeacher(userId, sysUser.getCode());
        }*/
        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setGrowTime(memberApply.getGrowTime());
        //member.setPositiveTime(memberApply.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }

}
