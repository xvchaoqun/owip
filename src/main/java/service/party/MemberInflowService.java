package service.party;

import domain.MemberInflow;
import domain.MemberInflowExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberInflowService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){


        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberInflowMapper.countByExample(example) > 0;
    }

    public MemberInflow get(int userId) {

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberInflow> memberReturns = memberInflowMapper.selectByExample(example);
        if(memberReturns.size()>0) return memberReturns.get(0);

        return null;
    }

    @Transactional
    public int insertSelective(MemberInflow record){

        return memberInflowMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberInflowMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberInflowExample example = new MemberInflowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberInflowMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberInflow record){
        return memberInflowMapper.updateByPrimaryKeySelective(record);
    }

}
