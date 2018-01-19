package service.sc;

import domain.sc.ScMatterTransferItem;
import domain.sc.ScMatterTransferItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterTransferItemService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatterTransferItem record){

        scMatterTransferItemMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterTransferItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterTransferItemExample example = new ScMatterTransferItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterTransferItemMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterTransferItem record){
        return scMatterTransferItemMapper.updateByPrimaryKeySelective(record);
    }
}
