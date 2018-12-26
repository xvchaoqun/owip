package service.sc.scBorder;

import domain.sc.scBorder.ScBorderItem;
import domain.sc.scBorder.ScBorderItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScBorderItemService extends ScBaseMapper {

    public boolean idDuplicate(Integer id, int borderId, int cadreId){

        ScBorderItemExample example = new ScBorderItemExample();
        ScBorderItemExample.Criteria criteria = example.createCriteria()
                .andBorderIdEqualTo(borderId)
                .andCadreIdEqualTo(cadreId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scBorderItemMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScBorderItem record){

        Assert.isTrue(!idDuplicate(null, record.getBorderId(), record.getCadreId()), "duplicate");
        scBorderItemMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scBorderItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScBorderItemExample example = new ScBorderItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scBorderItemMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScBorderItem record){
        if(record.getCadreId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getBorderId(), record.getCadreId()), "duplicate");
        return scBorderItemMapper.updateByPrimaryKeySelective(record);
    }
}
