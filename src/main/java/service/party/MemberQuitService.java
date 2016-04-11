package service.party;

import domain.MemberQuit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

@Service
public class MemberQuitService extends BaseMapper {

    @Autowired
    private MemberService memberService;

    @Transactional
    public void quit(MemberQuit record){

        if(memberQuitMapper.insertSelective(record)==0)
            throw new DBErrorException("出党操作失败");

        memberService.quit(record.getUserId(), SystemConstants.MEMBER_STATUS_QUIT);
    }
    @Transactional
    public void del(Integer userId){

        memberQuitMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberQuit record){
        return memberQuitMapper.updateByPrimaryKeySelective(record);
    }
}
