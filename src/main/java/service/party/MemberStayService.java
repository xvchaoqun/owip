package service.party;

import domain.MemberStay;
import domain.MemberStayExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberStayService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberStayMapper.countByExample(example) > 0;
    }

    public MemberStay get(int userId) {

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberStay> memberStays = memberStayMapper.selectByExample(example);
        if(memberStays.size()>0) return memberStays.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberStay memberStay = get(userId);
        if(memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_SELF_BACK);

        memberStayMapper.updateByPrimaryKeySelective(record);
    }

    // 不通过
    @Transactional
    public void deny(int userId, String reason){

        MemberStay memberStay = get(userId);
        if(memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_BACK);
        record.setReason(reason);

        memberStayMapper.updateByPrimaryKeySelective(record);
    }

    // 党总支、直属党支部审核通过
    @Transactional
    public void check1(int userId){

        MemberStay memberStay = get(userId);
        if(memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);

        memberStayMapper.updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void checkByParty(int userId, boolean isDirect){

        check1(userId);
        check2(userId, isDirect);
    }

    // 组织部审核通过
    @Transactional
    public void check2(int userId, boolean isDirect){

        MemberStay memberStay = get(userId);

        if(isDirect && memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
            throw new DBErrorException("状态异常");

        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY);
        memberStayMapper.updateByPrimaryKeySelective(record);
    }
    @Transactional
    public int insertSelective(MemberStay record){

        return memberStayMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberStayMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberStayMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberStay record){
        return memberStayMapper.updateByPrimaryKeySelective(record);
    }
}
