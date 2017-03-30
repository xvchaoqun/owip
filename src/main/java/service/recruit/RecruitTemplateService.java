package service.recruit;

import domain.recruit.RecruitTemplate;
import domain.recruit.RecruitTemplateExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecruitTemplateService extends BaseMapper {

    @Transactional
    @CacheEvict(value="RecruitTemplate:ALL", allEntries = true)
    public void insertSelective(RecruitTemplate record){

        recruitTemplateMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="RecruitTemplate:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        RecruitTemplateExample example = new RecruitTemplateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        recruitTemplateMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="RecruitTemplate:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(RecruitTemplate record){
        return recruitTemplateMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="RecruitTemplate:ALL")
    public Map<Integer, RecruitTemplate> findAll() {

        RecruitTemplateExample example = new RecruitTemplateExample();
        List<RecruitTemplate> recruitTemplatees = recruitTemplateMapper.selectByExample(example);
        Map<Integer, RecruitTemplate> map = new LinkedHashMap<>();
        for (RecruitTemplate recruitTemplate : recruitTemplatees) {
            map.put(recruitTemplate.getId(), recruitTemplate);
        }

        return map;
    }
}
