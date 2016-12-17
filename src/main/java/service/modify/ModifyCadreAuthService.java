package service.modify;

import domain.modify.ModifyCadreAuth;
import domain.modify.ModifyCadreAuthExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ModifyCadreAuthService extends BaseMapper {

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#record.cadreId")
    public int insertSelective(ModifyCadreAuth record){
        if(BooleanUtils.isTrue(record.getIsUnlimited())){
            record.setStartTime(null);
            record.setEndTime(null);
        }
        return modifyCadreAuthMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#result.cadreId")
    public ModifyCadreAuth del(Integer id){

        ModifyCadreAuth result = modifyCadreAuthMapper.selectByPrimaryKey(id);
        modifyCadreAuthMapper.deleteByPrimaryKey(id);
        return result;
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ModifyCadreAuthExample example = new ModifyCadreAuthExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        modifyCadreAuthMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#record.cadreId")
    public void updateByPrimaryKeySelective(ModifyCadreAuth record){

        modifyCadreAuthMapper.updateByPrimaryKeySelective(record);

        if(BooleanUtils.isTrue(record.getIsUnlimited())){
            updateMapper.del_ModifyCadreAuth_time(record.getId());
        }
    }

    // 读取干部的所有权限设置
    @Cacheable(value="ModifyCadreAuths", key = "#cadreId")
    public List<ModifyCadreAuth> findAll(int cadreId) {

        ModifyCadreAuthExample example = new ModifyCadreAuthExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);

        return modifyCadreAuthMapper.selectByExample(example);
    }
}
