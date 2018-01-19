package service.sc;

import domain.sc.ScMatterTransfer;
import domain.sc.ScMatterTransferExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMatterTransferService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatterTransfer record){

        scMatterTransferMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterTransferMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterTransferExample example = new ScMatterTransferExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterTransferMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterTransfer record){
        return scMatterTransferMapper.updateByPrimaryKeySelective(record);
    }
}
