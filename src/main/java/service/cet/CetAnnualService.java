package service.cet;

import domain.cet.CetAnnual;
import domain.cet.CetAnnualExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CetAnnualService extends CetBaseMapper {

    @Transactional
    public void insertSelective(CetAnnual record){

        cetAnnualMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetAnnualMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetAnnualExample example = new CetAnnualExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetAnnualMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetAnnual record){
        return cetAnnualMapper.updateByPrimaryKeySelective(record);
    }
}
