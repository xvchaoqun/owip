package service.party;

import domain.Member;
import domain.RetireApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Date;

/**
 * Created by fafa on 2015/11/30.
 */
@Service
public class RetireApplyService extends BaseMapper{

    public int insertSelective(RetireApply record){

        return retireApplyMapper.insertSelective(record);
    }

    //审核
    @Transactional
    public void verify(int userId, int verifyId){

        RetireApply record = new RetireApply();
        record.setUserId(userId);
        record.setStatus(SystemConstants.RETIRE_APPLY_STATUS_CHECKED);
        record.setVerifyId(verifyId);
        record.setVerifyTime(new Date());
        retireApplyMapper.updateByPrimaryKeySelective(record);

        Member member = new Member();
        member.setUserId(userId);
        member.setStatus(SystemConstants.MEMBER_STATUS_RETIRE);
        memberMapper.updateByPrimaryKeySelective(member);
    }

}
