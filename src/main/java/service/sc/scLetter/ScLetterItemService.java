package service.sc.scLetter;

import domain.sc.scLetter.ScLetterItem;
import domain.sc.scLetter.ScLetterItemExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScLetterItemService extends ScBaseMapper {

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

    // 函询复用
    @Transactional
    public void reuse(int itemId, Integer[] recordIds) {

        if(recordIds!=null && recordIds.length>0){

            ScLetterItem record = new ScLetterItem();
            record.setId(itemId);
            record.setRecordIds(StringUtils.join(recordIds, ","));
            scLetterItemMapper.updateByPrimaryKeySelective(record);
        }else{
            // 不传则清空
            commonMapper.excuteSql("update sc_letter_item set record_ids=null where id="+ itemId);
        }
    }
}
