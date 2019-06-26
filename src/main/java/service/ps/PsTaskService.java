package service.ps;

import domain.ps.PsTask;
import domain.ps.PsTaskExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PsTaskService extends PsBaseMapper {

    @Transactional
    public void insertSelective(PsTask record){

        psTaskMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psTaskMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsTaskExample example = new PsTaskExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psTaskMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsTask record){

        psTaskMapper.updateByPrimaryKeySelective(record);
    }
}
