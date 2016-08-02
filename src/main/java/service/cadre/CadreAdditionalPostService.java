package service.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdditionalPostExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreAdditionalPostService extends BaseMapper {

    public boolean idDuplicate(Integer id, int cadreId, int unitId){

        {
            CadreAdditionalPostExample example = new CadreAdditionalPostExample();
            CadreAdditionalPostExample.Criteria criteria = example.createCriteria()
                    .andCadreIdEqualTo(cadreId).andUnitIdEqualTo(unitId);
            if (id != null) criteria.andIdNotEqualTo(id);
            if (cadreAdditionalPostMapper.countByExample(example) > 0) return true;
        }
        {
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            // 这个单位是该干部的主职单位
            if(cadre!=null && cadre.getUnitId().intValue()==unitId) return true;
        }

        return false;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="CadreAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="CadreAdditionalPost", key="#record.cadreId")
    })
    public void insertSelective(CadreAdditionalPost record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getUnitId()));
        cadreAdditionalPostMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="CadreAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="CadreAdditionalPost", key="#cadreId")
    })
    public void del(Integer id, int cadreId){
        cadreAdditionalPostMapper.deleteByPrimaryKey(id);
    }

   /* @Transactional
    @CacheEvict(value="CadreAdditionalPost:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreAdditionalPostExample example = new CadreAdditionalPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreAdditionalPostMapper.deleteByExample(example);
    }*/

    /*@Transactional
    @CacheEvict(value="CadreAdditionalPost:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CadreAdditionalPost record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId(), record.getUnitId()));

        return cadreAdditionalPostMapper.updateByPrimaryKeySelective(record);
    }*/

    @Cacheable(value="CadreAdditionalPost", key="#cadreId")
    public List<CadreAdditionalPost> findCadrePosts(int cadreId) {

        CadreAdditionalPostExample example = new CadreAdditionalPostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreAdditionalPost> cadreAdditionalPostes = cadreAdditionalPostMapper.selectByExample(example);

        return cadreAdditionalPostes;
    }

    // key: cadreId+"_"+unitId
    @Cacheable(value="CadreAdditionalPost:ALL")
    public Map<String, CadreAdditionalPost> findAll() {

        CadreAdditionalPostExample example = new CadreAdditionalPostExample();
        List<CadreAdditionalPost> cadreAdditionalPostes = cadreAdditionalPostMapper.selectByExample(example);
        Map<String, CadreAdditionalPost> map = new LinkedHashMap<>();
        for (CadreAdditionalPost cPost : cadreAdditionalPostes) {
            map.put(cPost.getCadreId() + "_" + cPost.getUnitId(), cPost);
        }
        return map;
    }
}
