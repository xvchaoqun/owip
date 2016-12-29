package service.cadre;

import domain.cadre.CadrePostWork;
import domain.cadre.CadrePostWorkExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadrePostWorkService extends BaseMapper {
    
    @Transactional
    public int insertSelective(CadrePostWork record){
        
        return cadrePostWorkMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadrePostWorkExample example = new CadrePostWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadrePostWorkMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadrePostWorkExample example = new CadrePostWorkExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostWorkMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadrePostWork record){
        return cadrePostWorkMapper.updateByPrimaryKeySelective(record);
    }
}
