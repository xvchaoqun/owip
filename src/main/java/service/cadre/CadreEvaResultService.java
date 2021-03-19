package service.cadre;

import domain.cadre.CadreEvaResult;
import domain.cadre.CadreEvaResultExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreEvaResultService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer cadreId, int year,String groupName){

        CadreEvaResultExample example = new CadreEvaResultExample();
        CadreEvaResultExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(year).andGroupNameEqualTo(groupName);
        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreEvaResultMapper.countByExample(example) > 0;
    }
    public CadreEvaResult get(int cadreId, int year, String groupName){

        CadreEvaResultExample example = new CadreEvaResultExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andYearEqualTo(year).andGroupNameEqualTo(groupName);

        List<CadreEvaResult> cadreEvaResults = cadreEvaResultMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreEvaResults.size()>0?cadreEvaResults.get(0):null;
    }
    @Transactional
    public void insertSelective(CadreEvaResult record){

        record.setSortOrder(getNextSortOrder("cadre_eva_result", null));
        cadreEvaResultMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cadreEvaResultMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreEvaResultExample example = new CadreEvaResultExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEvaResultMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadreEvaResult record){

        cadreEvaResultMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreEvaResult> findAll() {

        CadreEvaResultExample example = new CadreEvaResultExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<CadreEvaResult> records = cadreEvaResultMapper.selectByExample(example);
        Map<Integer, CadreEvaResult> map = new LinkedHashMap<>();
        for (CadreEvaResult record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    @Transactional
    public int batchImport(List<CadreEvaResult> records) {

        int addCount = 0;
        for (CadreEvaResult record : records) {

            int cadreId = record.getCadreId();
            int year = record.getYear();
            String groupName = record.getGroupName();
            CadreEvaResult cadreEvaResult = get(cadreId, year,groupName);
            if(cadreEvaResult==null) {
                insertSelective(record);
                addCount++;
            }else{
                int id = cadreEvaResult.getId();
                record.setId(id);
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

}
