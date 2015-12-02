package service.party;

import domain.MemberIn;
import domain.MemberInExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class MemberInService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberInMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(MemberIn record){

        return  memberInMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberInMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberInExample example = new MemberInExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberInMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberIn record){
        return memberInMapper.updateByPrimaryKeySelective(record);
    }
}
