package service;

import domain.CadreFamliy;
import domain.CadreFamliyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CadreFamliyService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreFamliy record){

        return cadreFamliyMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreFamliyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreFamliyExample example = new CadreFamliyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreFamliyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreFamliy record){
        return cadreFamliyMapper.updateByPrimaryKeySelective(record);
    }

}
