package service.cet;

import domain.cet.CetAnnualRequire;
import domain.cet.CetAnnualRequireExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CetAnnualRequireService extends CetBaseMapper {

    @Transactional
    public void insertSelective(CetAnnualRequire record){

        cetAnnualRequireMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetAnnualRequireMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetAnnualRequireExample example = new CetAnnualRequireExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetAnnualRequireMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetAnnualRequire record){
        return cetAnnualRequireMapper.updateByPrimaryKeySelective(record);
    }
}
