package service.cadre;

import domain.cadre.CadrePostPro;
import domain.cadre.CadrePostProExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadrePostProService extends BaseMapper {
    
    @Transactional
    public int insertSelective(CadrePostPro record){
        
        return cadrePostProMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadrePostProExample example = new CadrePostProExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadrePostProMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadrePostProExample example = new CadrePostProExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostProMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadrePostPro record){
        return cadrePostProMapper.updateByPrimaryKeySelective(record);
    }
}
