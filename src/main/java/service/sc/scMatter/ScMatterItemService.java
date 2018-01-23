package service.sc.scMatter;

import domain.sc.scMatter.ScMatterItem;
import domain.sc.scMatter.ScMatterItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterItemService extends BaseMapper {


    @Transactional
    public void insertSelective(ScMatterItem record){

        scMatterItemMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterItemExample example = new ScMatterItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterItemMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterItem record){
        return scMatterItemMapper.updateByPrimaryKeySelective(record);
    }
}
