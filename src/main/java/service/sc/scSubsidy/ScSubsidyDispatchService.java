package service.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyDispatch;
import domain.sc.scSubsidy.ScSubsidyDispatchExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScSubsidyDispatchService extends BaseMapper {

    @Transactional
    public void insertSelective(ScSubsidyDispatch record){

        scSubsidyDispatchMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scSubsidyDispatchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScSubsidyDispatchExample example = new ScSubsidyDispatchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scSubsidyDispatchMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScSubsidyDispatch record){
        return scSubsidyDispatchMapper.updateByPrimaryKeySelective(record);
    }
}
