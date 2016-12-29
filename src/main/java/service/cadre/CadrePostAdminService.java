package service.cadre;

import domain.cadre.CadrePostAdmin;
import domain.cadre.CadrePostAdminExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadrePostAdminService extends BaseMapper {
    
    @Transactional
    public int insertSelective(CadrePostAdmin record){
        
        return cadrePostAdminMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadrePostAdminExample example = new CadrePostAdminExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadrePostAdminMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadrePostAdminExample example = new CadrePostAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostAdminMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadrePostAdmin record){
        return cadrePostAdminMapper.updateByPrimaryKeySelective(record);
    }
}
