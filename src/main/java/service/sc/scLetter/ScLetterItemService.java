package service.sc.scLetter;

import domain.sc.scLetter.ScLetterItem;
import domain.sc.scLetter.ScLetterItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScLetterItemService extends BaseMapper {

    @Transactional
    public void insertSelective(ScLetterItem record){

        scLetterItemMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scLetterItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScLetterItemExample example = new ScLetterItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scLetterItemMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScLetterItem record){
        return scLetterItemMapper.updateByPrimaryKeySelective(record);
    }
}
