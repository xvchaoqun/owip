package service.party;

import domain.MemberReturn;
import domain.MemberReturnExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class MemberReturnService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberReturnMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(MemberReturn record){

        return memberReturnMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberReturnMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberReturnExample example = new MemberReturnExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberReturnMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberReturn record){
        return memberReturnMapper.updateByPrimaryKeySelective(record);
    }
}
