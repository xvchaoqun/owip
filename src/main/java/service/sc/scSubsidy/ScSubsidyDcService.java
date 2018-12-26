package service.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyDc;
import domain.sc.scSubsidy.ScSubsidyDcExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScSubsidyDcService extends ScBaseMapper {

    @Transactional
    public void insertSelective(ScSubsidyDc record){

        scSubsidyDcMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scSubsidyDcMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScSubsidyDcExample example = new ScSubsidyDcExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scSubsidyDcMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScSubsidyDc record){

        return scSubsidyDcMapper.updateByPrimaryKeySelective(record);
    }
}
