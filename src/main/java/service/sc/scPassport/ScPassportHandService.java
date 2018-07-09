package service.sc.scPassport;

import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportHandExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScPassportHandService extends BaseMapper {


    @Transactional
    public void insertSelective(ScPassportHand record){

        scPassportHandMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scPassportHandMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScPassportHandExample example = new ScPassportHandExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scPassportHandMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScPassportHand record){

        return scPassportHandMapper.updateByPrimaryKeySelective(record);
    }
}
