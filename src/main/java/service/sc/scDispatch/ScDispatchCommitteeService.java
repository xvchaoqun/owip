package service.sc.scDispatch;

import domain.sc.scDispatch.ScDispatchCommittee;
import domain.sc.scDispatch.ScDispatchCommitteeExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScDispatchCommitteeService extends BaseMapper {

    @Transactional
    public void insertSelective(ScDispatchCommittee record){

        scDispatchCommitteeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scDispatchCommitteeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScDispatchCommitteeExample example = new ScDispatchCommitteeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scDispatchCommitteeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScDispatchCommittee record){
        return scDispatchCommitteeMapper.updateByPrimaryKeySelective(record);
    }
}
