package service.cadre;

import domain.CadreWork;
import domain.CadreWorkExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreWorkService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreWork record){
        return cadreWorkMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreWorkMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreWorkExample example = new CadreWorkExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreWorkMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreWork record){
        return cadreWorkMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreWork> findAll() {

        CadreWorkExample example = new CadreWorkExample();
        example.setOrderByClause("sort_order desc");
        List<CadreWork> cadreWorkes = cadreWorkMapper.selectByExample(example);
        Map<Integer, CadreWork> map = new LinkedHashMap<>();
        for (CadreWork cadreWork : cadreWorkes) {
            map.put(cadreWork.getId(), cadreWork);
        }

        return map;
    }
}
