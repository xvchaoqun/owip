package service.sc;

import domain.sc.ScMatterAccess;
import domain.sc.ScMatterAccessExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterAccessService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatterAccess record){

        scMatterAccessMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterAccessMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterAccessExample example = new ScMatterAccessExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterAccessMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterAccess record){
        return scMatterAccessMapper.updateByPrimaryKeySelective(record);
    }
}
