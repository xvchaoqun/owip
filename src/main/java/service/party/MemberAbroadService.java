package service.party;

import domain.MemberAbroad;
import domain.MemberAbroadExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberAbroadService extends BaseMapper {

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
