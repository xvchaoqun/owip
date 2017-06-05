package service.member;

import domain.member.MemberAbroad;
import domain.member.MemberAbroadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.party.PartyService;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberAbroadService extends BaseMapper {
    @Autowired
    private PartyService partyService;
    @Transactional
    public int insertSelective(MemberAbroad record){

        return memberAbroadMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer userId){

        memberAbroadMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public void batchDel(Integer[] userIds){

        MemberAbroadExample example = new MemberAbroadExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        memberAbroadMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberAbroad record){

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            updateMapper.updateToDirectBranch("ow_member_abroad", "id", record.getId(), record.getPartyId());
        }
        return memberAbroadMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, MemberAbroad> findAll() {

        MemberAbroadExample example = new MemberAbroadExample();
        example.setOrderByClause("sort_order desc");
        List<MemberAbroad> memberAbroades = memberAbroadMapper.selectByExample(example);
        Map<Integer, MemberAbroad> map = new LinkedHashMap<>();
        for (MemberAbroad memberAbroad : memberAbroades) {
            map.put(memberAbroad.getId(), memberAbroad);
        }

        return map;
    }
}
