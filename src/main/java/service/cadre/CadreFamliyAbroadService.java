package service.cadre;

import domain.CadreFamliyAbroad;
import domain.CadreFamliyAbroadExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreFamliyAbroadService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreFamliyAbroad record){

        return cadreFamliyAbroadMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreFamliyAbroadMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreFamliyAbroadMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreFamliyAbroad record){
        return cadreFamliyAbroadMapper.updateByPrimaryKeySelective(record);
    }
}
