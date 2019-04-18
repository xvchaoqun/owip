package service.member;

import domain.member.ApplySnRange;
import domain.member.ApplySnRangeExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ApplySnRangeService extends MemberBaseMapper {

    @Transactional
    public void insertSelective(ApplySnRange record){

        applySnRangeMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplySnRangeExample example = new ApplySnRangeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applySnRangeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplySnRange record){
        return applySnRangeMapper.updateByPrimaryKeySelective(record);
    }
}
