package service.cadre;

import domain.CadreInfo;
import domain.CadreInfoExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreInfoService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreInfo record){

        return cadreInfoMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer cadreId){

        cadreInfoMapper.deleteByPrimaryKey(cadreId);
    }

    @Transactional
    public void batchDel(Integer[] cadreIds){

        if(cadreIds==null || cadreIds.length==0) return;

        CadreInfoExample example = new CadreInfoExample();
        example.createCriteria().andCadreIdIn(Arrays.asList(cadreIds));
        cadreInfoMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreInfo record){
        return cadreInfoMapper.updateByPrimaryKeySelective(record);
    }
}
