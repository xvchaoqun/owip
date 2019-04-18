package service.member;

import domain.member.ApplySn;
import domain.member.ApplySnExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ApplySnService extends MemberBaseMapper {

    @Transactional
    public void insertSelective(ApplySn record){

        applySnMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Long[] sns){

        if(sns==null || sns.length==0) return;

        ApplySnExample example = new ApplySnExample();
        example.createCriteria().andSnIn(Arrays.asList(sns));
        applySnMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplySn record){

        return applySnMapper.updateByPrimaryKeySelective(record);
    }
}
