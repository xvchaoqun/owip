package service.cis;

import domain.cis.CisEvaluate;
import domain.cis.CisEvaluateExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CisEvaluateService extends CisBaseMapper {

    @Transactional
    public void insertSelective(CisEvaluate record){

        cisEvaluateMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cisEvaluateMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CisEvaluateExample example = new CisEvaluateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cisEvaluateMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CisEvaluate record){
        return cisEvaluateMapper.updateByPrimaryKeySelective(record);
    }
}
