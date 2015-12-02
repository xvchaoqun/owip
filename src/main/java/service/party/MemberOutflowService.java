package service.party;

import domain.MemberOutflow;
import domain.MemberOutflowExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class MemberOutflowService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){


        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberOutflowMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(MemberOutflow record){

        return memberOutflowMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberOutflowMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberOutflowExample example = new MemberOutflowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberOutflowMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberOutflow record){
        return memberOutflowMapper.updateByPrimaryKeySelective(record);
    }
}
