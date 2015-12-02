package service.party;

import domain.MemberOut;
import domain.MemberOutExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class MemberOutService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberOutMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(MemberOut record){

        return memberOutMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberOutMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberOutExample example = new MemberOutExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberOutMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberOut record){
        return memberOutMapper.updateByPrimaryKeySelective(record);
    }

}
