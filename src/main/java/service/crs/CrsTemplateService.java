package service.crs;

import domain.crs.CrsTemplate;
import domain.crs.CrsTemplateExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrsTemplateService extends CrsBaseMapper {

    @Transactional
    @CacheEvict(value="CrsTemplates", allEntries = true)
    public void insertSelective(CrsTemplate record){

        crsTemplateMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CrsTemplates", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrsTemplateExample example = new CrsTemplateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsTemplateMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CrsTemplates", allEntries = true)
    public int updateByPrimaryKeySelective(CrsTemplate record){
        return crsTemplateMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CrsTemplates", key = "#type")
    public Map<Integer, CrsTemplate> findAll(byte type) {

        CrsTemplateExample example = new CrsTemplateExample();
        example.createCriteria().andTypeEqualTo(type);
        List<CrsTemplate> crsTemplatees = crsTemplateMapper.selectByExample(example);
        Map<Integer, CrsTemplate> map = new LinkedHashMap<>();
        for (CrsTemplate crsTemplate : crsTemplatees) {
            map.put(crsTemplate.getId(), crsTemplate);
        }

        return map;
    }
}
