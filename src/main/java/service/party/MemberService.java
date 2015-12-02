package service.party;

import domain.Member;
import org.springframework.stereotype.Service;
import service.BaseMapper;

@Service
public class MemberService extends BaseMapper {

    public Member get(int userId){

         return memberMapper.selectByPrimaryKey(userId);
    }
}
