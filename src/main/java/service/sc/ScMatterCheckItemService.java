package service.sc;

import domain.sc.ScMatterCheckItem;
import domain.sc.ScMatterCheckItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterCheckItemService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatterCheckItem record){

        scMatterCheckItemMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterCheckItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterCheckItemExample example = new ScMatterCheckItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterCheckItemMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterCheckItem record){
        return scMatterCheckItemMapper.updateByPrimaryKeySelective(record);
    }
}
