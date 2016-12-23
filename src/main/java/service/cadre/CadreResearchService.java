package service.cadre;

import domain.cadre.CadreResearch;
import domain.cadre.CadreResearchExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreResearchService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreResearch record) {

        return cadreResearchMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cadreResearchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;

        {
            // 干部信息本人直接修改数据校验
            CadreResearchExample example = new CadreResearchExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreResearchMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadreResearchExample example = new CadreResearchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreResearchMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreResearch record) {
        return cadreResearchMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreResearch> findAll() {

        CadreResearchExample example = new CadreResearchExample();
        example.setOrderByClause("id desc");
        List<CadreResearch> cadreResearches = cadreResearchMapper.selectByExample(example);
        Map<Integer, CadreResearch> map = new LinkedHashMap<>();
        for (CadreResearch cadreResearch : cadreResearches) {
            map.put(cadreResearch.getId(), cadreResearch);
        }

        return map;
    }


}
