package service;

import domain.CadreEdu;
import domain.CadreEduExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CadreEduService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreEdu record){

        return cadreEduMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEduMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreEdu record){
        return cadreEduMapper.updateByPrimaryKeySelective(record);
    }
}
