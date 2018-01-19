package service.sc;

import domain.sc.ScMatter;
import domain.sc.ScMatterExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatter record){

        scMatterMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterExample example = new ScMatterExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatter record){
        return scMatterMapper.updateByPrimaryKeySelective(record);
    }
}
