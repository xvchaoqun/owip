package service.sc;

import domain.sc.ScMatterCheck;
import domain.sc.ScMatterCheckExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterCheckService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatterCheck record){

        scMatterCheckMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterCheckMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterCheckExample example = new ScMatterCheckExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterCheckMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterCheck record){
        return scMatterCheckMapper.updateByPrimaryKeySelective(record);
    }
}
